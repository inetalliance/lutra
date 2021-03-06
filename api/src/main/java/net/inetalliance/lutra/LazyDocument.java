package net.inetalliance.lutra;

import net.inetalliance.lutra.elements.*;
import net.inetalliance.lutra.listeners.IdMapper;
import net.inetalliance.lutra.listeners.TypeListener;
import net.inetalliance.lutra.rules.ValidationErrors;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.stream.StreamSupport;

import static java.util.regex.Pattern.*;
import static net.inetalliance.lutra.elements.Attribute.*;

public class LazyDocument
	implements Cloneable,
	           Document {

	public final Element root;
	public final Map<String, Element> byId;
	public final TitleElement title;
	public final HeadElement head;
	public final BodyElement body;
	private File file;
	public static final String FILE_PATH_FIELD = "FILE_PATH";

	public LazyDocument(final DocumentBuilder builder) {
		byId = new TreeMap<>();
		final TypeListener headListener = new TypeListener(ElementType.HEAD);
		final TypeListener titleListener = new TypeListener(ElementType.TITLE);
		final TypeListener bodyListener = new TypeListener(ElementType.BODY);
		this.root = builder.getRoot()
			.copyWithListeners(List.of(new IdMapper(byId), headListener, titleListener, bodyListener));
		this.title = (TitleElement) titleListener.getElement();
		this.head = (HeadElement) headListener.getElement();
		this.body = (BodyElement) bodyListener.getElement();
	}

	public Map<String, Element> getIdMap() {
		return byId;
	}

	@Override
	public Element getRoot() {
		return root;
	}

	@Override
	public HeadElement getHead() {
		return head;
	}

	@Override
	public BodyElement getBody() {
		return body;
	}

	final void setFile(final File file) {
		this.file = file;
	}

	public final File getFile() {
		return file;
	}

	public void setTitle(final String title) {
		if (this.title != null) {
			this.title.setText(title);
		}
	}

	public void reindex() {
		StreamSupport.stream(body.getDescendants().spliterator(), false)
			.filter(ID.has)
			.forEach(element -> byId.put(element.getId(), element));
	}

	@Override
	public String toString() {
		return toString(false);
	}

	public void write(final Appendable appendable)
		throws IOException {
		root.toString(false);
	}

	public String toString(final boolean pretty) {
		try {
			return root.toString(pretty);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Element getById(final String id) {
		return byId.get(id);
	}

	public ValidationErrors validate() {
		return validate(true);
	}

	public ValidationErrors validate(final boolean strict) {
		return root.validate(strict);
	}

	@Override
	public Object clone()
		throws CloneNotSupportedException {
		return super.clone();
	}

	public void addMetaTag(final String name, final String content) {
		if (content != null) {
			if ("page title".equals(name)) {
				setTitle(content);
			} else {
				head.appendChild(
					new MetaElement().
						setName(name).
						setContent(stripTags(content)));
			}
		}
	}

	private static Pattern tags = compile("(<([^>]+)>)");
	private static Pattern br = compile("(<[bB][rR]([^>]+)>)");

	private static String stripTags(final String arg) {

		String result = br.matcher(arg).replaceAll(" ");
		result = tags.matcher(result).replaceAll("");
		return result;
	}

	@Override
	public void addOnLoad(final String javascript) {
		Document.Impl.addOnLoad(this, javascript);
	}

	@Override
	public void addOnLoad(final String javascript, final Object... parameters) {
		Document.Impl.addOnLoad(this, javascript, parameters);
	}

	@Override
	public void addJqueryOnLoad(final CharSequence javascript) {
		Document.Impl.addJqueryOnLoad(this, javascript);
	}

	@Override
	public void addJqueryOnLoad(final CharSequence javascript, final Object... parameters) {
		Document.Impl.addJqueryOnLoad(this, javascript, parameters);
	}
}