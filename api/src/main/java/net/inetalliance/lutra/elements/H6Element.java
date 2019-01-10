package net.inetalliance.lutra.elements;

import static net.inetalliance.lutra.rules.AttributeRule.ANY_COMMON_ATTRIBUTES;
import static net.inetalliance.lutra.rules.ChildRule.ANY_INLINE_OR_TEXT_ELEMENTS;

public class H6Element extends CommonAbstractElement<H6Element> implements BlockElement {

	public H6Element(final String text) {
		this(new TextContent(text));
	}

	public H6Element(final H6ElementChild... children) {
		super(H6Element.class, ElementType.H6, ANY_INLINE_OR_TEXT_ELEMENTS, ANY_COMMON_ATTRIBUTES, children);
	}

	@Override
	public H6Element clone() throws CloneNotSupportedException {
		return (H6Element) cloneWithListeners();
	}
}