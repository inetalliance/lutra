package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.Document;
import net.inetalliance.lutra.filters.IdPredicate;
import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;
import net.inetalliance.lutra.rules.MayHaveAttribute;
import net.inetalliance.lutra.rules.MustHaveChildrenInOrder;

import java.io.File;
import java.util.EnumSet;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static net.inetalliance.funky.Funky.stream;
import static net.inetalliance.lutra.elements.Attribute.*;
import static net.inetalliance.lutra.elements.ElementType.BODY;
import static net.inetalliance.lutra.elements.ElementType.HEAD;

public class HtmlElement extends Element implements Document {

	private static final ChildRule[] childRules =
		{
			new MustHaveChildrenInOrder(HEAD, BODY)
		};
	private static final AttributeRule[] attributeRules =
		{
			new MayHaveAttribute(EnumSet.of(XMLNS, XMLNS_FB, XML_LANG, LANG, ITEMPROP, ITEMTYPE))
		};

	public HtmlElement(final HtmlElementChild... children) {
		super(ElementType.HTML, childRules, attributeRules, children);
		setAttribute(XMLNS, "http://www.w3.org/1999/xhtml");
	}

	@Override
	public HtmlElement copy() {
		return (HtmlElement) copyWithListeners();
	}

	@Override
	public HtmlElement setClass(final String value) {
		return this;  // do nothing. classes not allowed on this type
	}

	@Override
	public HtmlElement setClass(final Enum<?>... cssClasses) {
		return this;  // do nothing. classes not allowed on this type
	}

	@Override
	public HtmlElement setId(final String value) {
		setAttribute(ID, value);
		return this;
	}

	public HtmlElement setLang(final String value) {
		setAttribute(LANG, value);
		return this;
	}

	public HtmlElement setXmlLang(final String value) {
		setAttribute(XML_LANG, value);
		return this;
	}

	@Override
	public HtmlElement setText(final String text) {
		setTextContent(text);
		return this;
	}

	@Override
	public Element getRoot() {
		return this;
	}

	@Override
	public HeadElement getHead() {
		return (HeadElement) getChildren().get(0);
	}

	@Override
	public BodyElement getBody() {
		return (BodyElement) getChildren().get(1);
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

	@Override
	public Element getById(final String id) {
		return stream(getRoot().getTree()).filter(new IdPredicate(id)).findFirst().orElse(null);
	}

	@Override
	public File getFile() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, Element> getIdMap() {
		return stream(getRoot().getTree()).collect(toMap(Element::getId, identity()));
	}
}