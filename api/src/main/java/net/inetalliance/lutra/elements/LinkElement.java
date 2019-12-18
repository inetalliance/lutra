package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;
import net.inetalliance.lutra.rules.MayHaveAttribute;

import java.util.EnumSet;
import java.util.Optional;

import static java.util.Arrays.*;
import static java.util.stream.Collectors.*;

public class LinkElement
	extends CommonAbstractElement<LinkElement>
	implements HeadElementChild {
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
			setType("text/css").
			setMedia("screen").
			setHref(href);
	}

	@Override
	public LinkElement copy() {
		return (LinkElement) copyWithListeners();
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

	static String secureUrl(final String url) {
		return Optional.ofNullable(url)
			.filter(u -> u.startsWith("http://"))
			.map(u -> "https" + u.substring(4))
			.orElse(url);
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
		return setClass(stream(cssClasses).map(Element::enumToCamelCase).collect(joining(" ")));
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

	public final LinkElement setType(final String value) {
		setAttribute(Attribute.TYPE, value);
		return this;
	}

}