package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;
import net.inetalliance.lutra.rules.MayHaveAttribute;

import java.util.EnumSet;

public class BrElement extends Element implements InlineElement {

	private static final AttributeRule[] attributeRules =
		{
			new MayHaveAttribute(EnumSet.of(Attribute.CLASS, Attribute.ID, Attribute.TITLE, Attribute.STYLE))
		};

	public BrElement() {
		super(ElementType.BR, ChildRule.NONE, attributeRules);
	}

	@Override
	public BrElement clone() throws CloneNotSupportedException {
		return (BrElement) cloneWithListeners();
	}

	public final String getCssClass() {
		return getAttribute(Attribute.CLASS);
	}

	public final String getStyle() {
		return getAttribute(Attribute.STYLE);
	}

	public final String getTitle() {
		return getAttribute(Attribute.TITLE);
	}

	@Override
	public BrElement setClass(final String value) {
		return this;  // do nothing. classes not allowed on this type
	}

	@Override
	public BrElement setClass(final Enum<?>... cssClasses) {
		return this;  // do nothing. classes not allowed on this type
	}

	@Override
	public BrElement setId(final String value) {
		setAttribute(Attribute.ID, value);
		return this;
	}

	public final BrElement setStyle(final String value) {
		setAttribute(Attribute.STYLE, value);
		return this;
	}

	@Override
	public BrElement setText(final String text) {
		setTextContent(text);
		return this;
	}

	@Override
	public final BrElement setTitle(final String value) {
		setAttribute(Attribute.TITLE, value);
		return this;
	}
}