package net.inetalliance.lutra.elements;

import static net.inetalliance.lutra.rules.AttributeRule.ANY_COMMON_ATTRIBUTES;
import static net.inetalliance.lutra.rules.ChildRule.ANY_INLINE_OR_TEXT_ELEMENTS;

public class H4Element extends CommonAbstractElement<H4Element> implements BlockElement {

	public H4Element(final String text) {
		this(new TextContent(text));
	}

	public H4Element(final H4ElementChild... children) {
		super(H4Element.class, ElementType.H4, ANY_INLINE_OR_TEXT_ELEMENTS, ANY_COMMON_ATTRIBUTES, children);
	}

	@Override
	public H4Element clone() throws CloneNotSupportedException {
		return (H4Element) cloneWithListeners();
	}
}