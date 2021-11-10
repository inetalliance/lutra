package net.inetalliance.lutra;

import net.inetalliance.lutra.elements.*;
import net.inetalliance.lutra.listeners.DocumentParseListener;
import net.inetalliance.lutra.rules.ValidationErrors;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class DocumentBuilder
	extends DefaultHandler
	implements ReloadableFile {
	public static final Map<String, String> entityFiles;
	private static final Collection<DocumentParseListener> parseListeners = new ArrayList<>(0);
	private final Stack<Element> stack;
	private final FileReloader reloader;
	private final File file;
	private final Map<String, Element> byId;
	private Element root;
	private TitleElement title;
	private HeadElement head;
	private BodyElement body;
	private Constructor<?> cachedConstructor;
	private transient Locator locator;

	public DocumentBuilder(final File file) {
		this.file = file;
		reloader = file == null ? null : new FileReloader(file, this);
		stack = new Stack<>();
		byId = new HashMap<>(0);
	}

	private static String read(final String resource) {
		try (BufferedReader reader = new BufferedReader(
			new InputStreamReader(DocumentBuilder.class.getResourceAsStream(resource)))) {
			final StringBuilder s = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				s.append(line).append('\n');
			}
			return s.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	static {
		entityFiles = new HashMap<>(5);

		entityFiles.put("-//W3C//DTD XHTML 1.0 Strict//EN", read("/xhtml1-strict.dtd"));
		entityFiles.put("-//W3C//ENTITIES Latin 1 for XHTML//EN", read("/xhtml-lat1.ent"));
		entityFiles.put("-//W3C//ENTITIES Symbols for XHTML//EN", read("/xhtml-symbol.ent"));
		entityFiles.put("-//W3C//ENTITIES Special for XHTML//EN", read("/xhtml-special.ent"));
		entityFiles.put("-//W3C//DTD XHTML 1.0 Transitional//EN", read("/xhtml1-transitional.dtd"));
	}

	public static void addParseListener(final DocumentParseListener listener) {
		parseListeners.add(listener);
	}

	public static void validate(final File file, final Collection<String> parseErrors,
		final Collection<String> validationErrors)
		throws IOException {
		final DocumentBuilder builder = new DocumentBuilder(file);
		try {
			builder.parse();
		} catch (SAXException e) {
			parseErrors.add(e.getMessage());
			return;
		}
		final ValidationErrors errors = builder.validate(false);
		errors.toString(validationErrors);
	}

	public final Element getRoot() {
		return root;
	}

	public final BodyElement getBody() {
		return body;
	}

	public final Map<String, Element> getById() {
		return byId;
	}

	public final HeadElement getHead() {
		return head;
	}

	public final TitleElement getTitle() {
		return title;
	}

	public <D extends LazyDocument> D build(final Class<D> type)
		throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
		if (cachedConstructor == null) {
			cachedConstructor = type.getConstructor(DocumentBuilder.class);
		}
		if (reloader != null) {
			reloader.reloadIfNeeded();
		}
		final D document = type.cast(cachedConstructor.newInstance(this));
		document.setFile(file);
		return document;
	}

	public LazyDocument buildGeneric() {
		if (reloader != null) {
			reloader.reloadIfNeeded();
		}
		final LazyDocument document = new LazyDocument(this);
		document.setFile(file);
		return document;
	}

	// --------------------- Interface ContentHandler ---------------------
	@Override
	public void setDocumentLocator(final Locator locator) {
		this.locator = locator;
	}

	@Override
	public void startDocument()
		throws SAXException {
		stack.clear();
		byId.clear();
		head = null;
		title = null;
		body = null;
	}

	@Override
	public void endDocument()
		throws SAXException {
	}

	@SuppressWarnings({"EnumSwitchStatementWhichMissesCases"})
	@Override
	public void startElement(final String uri, final String localName, final String qName,
		final Attributes attributes)
		throws SAXException {
		final ElementType type = ElementType.fromName(qName);
		if (type == null) {
			throw new SAXParseException(String.format("Unknown element type \"%s\"", qName), locator);
		}
		final Element element = type.create();
		if (stack.isEmpty()) {
			root = element;
		} else {
			stack.peek().appendChild(element);
		}
		for (int i = 0; i < attributes.getLength(); i++) {
			final String attributeName = attributes.getQName(i);
			final String attributeValue = attributes.getValue(i);
			final Attribute attribute = Attribute.fromName(attributeName);
			if (attribute == null) {
				if (Element.metaAttributes.matcher(attributeName).find()) {
					element.put(attributeName, attributeValue);
				}
				continue;
			} else if (attribute == Attribute.ID) {
				if (byId.put(attributeValue, element) != null) {
					throw new SAXParseException(
						String.format("Duplicate %s attribute: \"%s\"", Attribute.ID, attributeValue),
						locator);
				}
			}
			element.setAttribute(attribute, attributeValue);
		}
		element.setLocation(
			new Element.DocumentLocation(file == null ? null : file.getAbsolutePath(), locator.getLineNumber(),
				locator.getColumnNumber()));
		stack.push(element);
		switch (type) {
			case TITLE:
				title = (TitleElement) element;
				break;
			case HEAD:
				head = (HeadElement) element;
				break;
			case BODY:
				body = (BodyElement) element;
				break;
		}
	}

	@Override
	public void endElement(final String uri, final String localName, final String qName)
		throws SAXException {
		super.endElement(uri, localName, qName);
		stack.pop();
	}

	@Override
	public void characters(final char[] ch, final int start, final int length)
		throws SAXException {
		final Element parent = stack.peek();
		if (parent != null) {
			final String content = new String(ch, start, length);
			if (content.trim().length() > 0) {
				final Element lastChild = parent.getLastChild();
				if (lastChild instanceof TextContent) {
					final String previousContent = lastChild.getText();
					final TextContent newChild = new TextContent(previousContent + content);
					parent.replaceChild(lastChild, newChild);
					newChild.setLocation(lastChild.getLocation());
				} else {
					final TextContent textContent = new TextContent(content);
					parent.appendChild(textContent);
					textContent.setLocation(
						new Element.DocumentLocation(file == null ? null : file.getAbsolutePath(), locator.getLineNumber(),
							locator.getColumnNumber()));
				}
			}
		}
	}

	public void parse()
		throws SAXException, IOException {
		final InputStream input = new FileInputStream(file);
		try {
			load(input);
		} catch (IOException e) {
			throw e;
		} catch (SAXParseException e) {
			throw new SAXException(
				String.format("%s:%s [%s] - %s", file.getAbsolutePath(), e.getLineNumber(), e.getColumnNumber(),
					e.getMessage()));
		} catch (SAXException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			input.close();
		}
	}

	@Override
	public InputSource resolveEntity(final String publicId, final String systemId)
		throws IOException, SAXException {
		final String file = entityFiles.get(publicId);
		if (file != null) {
			return new InputSource(new StringReader(file));
		} else {
			return super.resolveEntity(publicId, systemId);
		}
	}

	public void load(final InputStream inputStream)
		throws Exception {
		try {
			final XMLReader xr = SAXParserFactory.newDefaultInstance().newSAXParser().getXMLReader();
			xr.setContentHandler(this);
			xr.setErrorHandler(this);
			xr.setFeature("http://xml.org/sax/features/namespaces", false);
			xr.setFeature("http://xml.org/sax/features/validation", false);
			xr.setEntityResolver(this);
			xr.parse(new InputSource(inputStream));
			if (reloader != null) {
				reloader.setLastRead(System.currentTimeMillis());
			}
			try {
				for (final DocumentParseListener listener : parseListeners) {
					listener.documentParsed(this);
				}
			} catch (Exception e) {
				if (reloader != null) {
					reloader.forceReload();
				}
				throw e;
			}
		} finally {
			locator = null;
		}

	}

	public ValidationErrors validate() {
		return validate(true);
	}

	public ValidationErrors validate(final boolean strict) {
		return root.validate(strict);
	}
}
