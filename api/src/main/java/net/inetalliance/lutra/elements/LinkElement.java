package net.inetalliance.lutra.elements;

import net.inetalliance.funky.StringFun;
import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;
import net.inetalliance.lutra.rules.MayHaveAttribute;
import net.inetalliance.types.www.ContentType;

import java.util.EnumSet;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;
import static net.inetalliance.funky.StringFun.secureUrl;
import static net.inetalliance.types.www.ContentType.CSS;

public class LinkElement extends CommonAbstractElement<LinkElement> implements HeadElementChild {
	private static final AttributeRule[] attributeRules =
		{
			new MayHaveAttribute(Attribute.union(Attribute.COMMON,
				EnumSet.of(Attribute.HREF, Attribute.MEDIA, Attribute.TYPE,
					Attribute.REL)))
		};

	public LinkElement() {
		super(LinkElement.class, ElementType.LINK, ChildRule.NONE, attributeRules);
		setMedia("screen");
	}

	public final LinkElement setMedia(final String value) {
		setAttribute(Attribute.MEDIA, value);
		return this;
	}

	public static LinkElement css(final String href) {
		return new LinkElement().
			setRel("stylesheet").
			setType(CSS).
			setMedia("screen").
			setHref(href);
	}

	@Override
	public LinkElement clone() throws CloneNotSupportedException {
		return (LinkElement) cloneWithListeners();
	}

	public final String getMedia() {
		return getAttribute(Attribute.MEDIA);
	}

	public final String getRel() {
		return getAttribute(Attribute.REL);
	}

	public final String getType() {
		return getAttribute(Attribute.TYPE);
	}

	@Override
	public void secure() {
		setHref(secureUrl(getHref()));
	}

	public final LinkElement setHref(final String value) {
		setAttribute(Attribute.HREF, value);
		return this;
	}

	public final String getHref() {
		return getAttribute(Attribute.HREF);
	}

	@Override
	public LinkElement setClass(final String value) {
		setAttribute(Attribute.CLASS, value);
		return this;
	}

	@Override
	public LinkElement setClass(final Enum<?>... cssClasses) {
		return setClass(stream(cssClasses).map(StringFun::enumToCamelCase).collect(joining(" ")));
	}

	@Override
	public LinkElement setId(final String value) {
		setAttribute(Attribute.ID, value);
		return this;
	}

	public final LinkElement setRel(final String value) {
		setAttribute(Attribute.REL, value);
		return this;
	}

	@Override
	public LinkElement setText(final String text) {
		setTextContent(text);
		return this;
	}

	public final LinkElement setType(final ContentType value) {
		setAttribute(Attribute.TYPE, value.value);
		return this;
	}

}