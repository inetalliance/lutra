package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;
import net.inetalliance.lutra.rules.MayHaveAttribute;
import net.inetalliance.lutra.rules.MustHaveAttribute;

import java.util.EnumSet;

public class StyleElement extends Element implements HeadElementChild {

	private static final AttributeRule[] attributeRules =
		{
			new MayHaveAttribute(EnumSet.of(Attribute.MEDIA, Attribute.TITLE, Attribute.TYPE)),
			new MustHaveAttribute(Attribute.TYPE)
		};

	public StyleElement(final String text) {
		this(new TextContent(text));
	}

	public StyleElement(final StyleElementChild... children) {
		super(ElementType.STYLE, ChildRule.TEXT, attributeRules, children);
		setMedia("screen");
		setType("text/css");
	}

	@Override
	protected boolean isClosed() {
		return false;
	}

	public final StyleElement setMedia(final String value) {
		setAttribute(Attribute.MEDIA, value);
		return this;
	}

	public final StyleElement setType(final String value) {
		setAttribute(Attribute.TYPE, value);
		return this;
	}

	@Override
	public StyleElement copy() {
		return (StyleElement) copyWithListeners();
	}

	public final String getMedia() {
		return getAttribute(Attribute.MEDIA);
	}

	public final String getTitle() {
		return getAttribute(Attribute.TITLE);
	}

	public final String getType() {
		return getAttribute(Attribute.TYPE);
	}

	@Override
	public StyleElement setClass(final String value) {
		return this;  // do nothing. classes not allowed on this type
	}

	@Override
	public StyleElement setClass(final Enum<?>... cssClasses) {
		return this;  // do nothing. classes not allowed on this type
	}

	@Override
	public StyleElement setId(final String value) {
		setAttribute(Attribute.ID, value);
		return this;
	}

	@Override
	public StyleElement setText(final String text) {
		setTextContent(text);
		return this;
	}

	@Override
	public final StyleElement setTitle(final String value) {
		setAttribute(Attribute.TITLE, value);
		return this;
	}
}