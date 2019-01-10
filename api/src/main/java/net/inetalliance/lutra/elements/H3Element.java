package net.inetalliance.lutra.elements;

import static net.inetalliance.lutra.rules.AttributeRule.ANY_COMMON_ATTRIBUTES;
import static net.inetalliance.lutra.rules.ChildRule.ANY_INLINE_OR_TEXT_ELEMENTS;

public class H3Element extends CommonAbstractElement<H3Element> implements BlockElement {

	public H3Element(final String text) {
		this(new TextContent(text));
	}

	public H3Element(final H3ElementChild... children) {
		super(H3Element.class, ElementType.H3, ANY_INLINE_OR_TEXT_ELEMENTS, ANY_COMMON_ATTRIBUTES, children);
	}

	@Override
	public H3Element clone() throws CloneNotSupportedException {
		return (H3Element) cloneWithListeners();
	}
}