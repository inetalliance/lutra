package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;
import net.inetalliance.lutra.rules.MustHaveAttribute;

public class BaseElement extends Element implements HeadElementChild {

	private static final AttributeRule[] attributeRules =
		{
			new MustHaveAttribute(Attribute.HREF)
		};

	public BaseElement() {
		super(ElementType.BASE, ChildRule.NONE, attributeRules);
	}

	@Override
	public BaseElement clone() throws CloneNotSupportedException {
		return (BaseElement) cloneWithListeners();
	}

	public final String getHref() {
		return getAttribute(Attribute.HREF);
	}

	@Override
	public BaseElement setClass(final String value) {
		return this;  // do nothing. classes not allowed on this type
	}

	@Override
	public BaseElement setClass(final Enum<?>... cssClasses) {
		return this;  // do nothing. classes not allowed on this type
	}

	public final BaseElement setHref(final String value) {
		setAttribute(Attribute.HREF, value);
		return this;
	}

	@Override
	public BaseElement setId(final String value) {
		setAttribute(Attribute.ID, value);
		return this;
	}

	@Override
	public BaseElement setText(final String text) {
		setTextContent(text);
		return this;
	}
}