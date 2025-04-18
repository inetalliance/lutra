package net.inetalliance.lutra;

import net.inetalliance.lutra.elements.*;
import net.inetalliance.lutra.elements.Attribute;
import net.inetalliance.lutra.elements.Element;
import net.inetalliance.lutra.listeners.DocumentParseListener;
import net.inetalliance.lutra.rules.ValidationErrors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.parser.Parser;
import org.jsoup.select.NodeVisitor;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import javax.xml.stream.Location;
import javax.xml.stream.events.Comment;
import javax.xml.stream.events.EntityReference;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@SuppressWarnings("this-escape")
public class DocumentBuilder implements ReloadableFile {
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

	public DocumentBuilder(final File file) {
		this.file = file;
		reloader = file == null ? null : new FileReloader(file, this);
		stack = new Stack<>();
		byId = new HashMap<>(0);
	}


	@SuppressWarnings("unused")
  private static String read(final String resource) {
		InputStream resourceAsStream = DocumentBuilder.class.getResourceAsStream(resource);
		if (resourceAsStream == null) {
			throw new NullPointerException();
		}
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(resourceAsStream))) {
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

	@SuppressWarnings("unused")
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


	public void startDocument() {
		stack.clear();
		byId.clear();
		head = null;
		title = null;
		body = null;
	}

	@SuppressWarnings("unused")
  public void endDocument() {
	}

	@SuppressWarnings("unused")
  static class EventLocator implements Locator {

		private final Location l;

		EventLocator(XMLEvent e) {
			this.l = e.getLocation();
		}

		@Override
		public String getPublicId() {
			return l.getPublicId();
		}

		@Override
		public String getSystemId() {
			return l.getSystemId();
		}

		@Override
		public int getLineNumber() {
			return l.getLineNumber();
		}

		@Override
		public int getColumnNumber() {
			return l.getColumnNumber();
		}
	}

	public void endElement() {
		if (!stack.isEmpty()) {
			stack.pop();
		}
	}

	public void startElement(final String qName, final Attributes attributes) {
		final ElementType type = ElementType.fromName(qName);
		if (type == null) {
			throw new RuntimeException(String.format("Unknown element type \"%s\"", qName));
		}
		final Element element = type.create();
		if (stack.isEmpty()) {
			root = element;
		} else {
			stack.peek().appendChild(element);
		}
		for (org.jsoup.nodes.Attribute nv : attributes) {
			final String attributeName = nv.getKey();
			final String attributeValue = nv.getValue();
			final Attribute attribute = Attribute.fromName(attributeName);
			if (attribute == null) {
				if (Element.metaAttributes.matcher(attributeName).find()) {
					element.put(attributeName, attributeValue);
				}
				continue;
			} else if (attribute == Attribute.ID) {
				if (byId.put(attributeValue, element) != null) {
					throw new RuntimeException(
							String.format("Duplicate %s attribute: \"%s\"", Attribute.ID, attributeValue));
				}
			}
			element.setAttribute(attribute, attributeValue);
		}
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


	public void cdata(final String data) {
		if (stack.isEmpty()) {
			return;
		}
		final Element parent = stack.peek();
		if (parent != null) {
			parent.appendChild(new TextContent(data));
		}
	}
	public void characters(String content) {
		if (stack.isEmpty()) {
			return;
		}
		final Element parent = stack.peek();
		if (parent != null) {
			if (!content.trim().isEmpty()) {
				final Element lastChild = parent.getLastChild();
				if (lastChild instanceof TextContent) {
					final String previousContent = lastChild.getText();
					final TextContent newChild = new TextContent(previousContent + content);
					parent.replaceChild(lastChild, newChild);
					newChild.setLocation(lastChild.getLocation());
				} else {
					final TextContent textContent = new TextContent(content);
					parent.appendChild(textContent);
				}
			}
		}
	}

	public void parse()
			throws SAXException, IOException {
		try (InputStream input = new FileInputStream(file)) {
			load(input);
		} catch (IOException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


	public void load(final InputStream inputStream) throws IOException {
		var doc = Jsoup.parse(inputStream, StandardCharsets.UTF_8.toString(), "", Parser.htmlParser());
		doc.outputSettings().escapeMode(Entities.EscapeMode.xhtml);
		load(doc);
}
	public List<Element> load(final String fragment) {
		var doc = Jsoup.parseBodyFragment(fragment);
		doc.outputSettings().escapeMode(Entities.EscapeMode.xhtml);
		load(doc);
		return body.getChildren();
	}

	public void load(final org.jsoup.nodes.Document doc) {
    //noinspection NullableProblems
    doc.traverse(new NodeVisitor() {
			@Override
			public void head(Node node, int depth) {
				if(node instanceof org.jsoup.nodes.Document) {
					startDocument();
				} else if(node instanceof DataNode) {
					cdata(((DataNode)node).getWholeData());
				} else if(node instanceof TextNode) {
					characters(((TextNode)node).getWholeText());
				 } else if (node instanceof Comment) {
					characters(((Comment)node).getText());
				}  else if(node instanceof org.jsoup.nodes.Element){
					startElement(node.nodeName(), node.attributes());
				}
			}
			@Override
			public void tail(Node node, int depth) {
				if(node instanceof org.jsoup.nodes.Element) {
					endElement();
				}
			}
		});
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
	}

	@SuppressWarnings("unused")
  private void entity(EntityReference entity) {
		var parent = stack.peek();
		if (parent != null) {
			var e = new Entity(entity.getName());
			parent.appendChild(e);
		}
	}

	public ValidationErrors validate() {
		return validate(true);
	}

	public ValidationErrors validate(final boolean strict) {
		return root.validate(strict);
	}


}
