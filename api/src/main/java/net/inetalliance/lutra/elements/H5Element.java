package net.inetalliance.lutra.elements;

import static net.inetalliance.lutra.rules.AttributeRule.ANY_COMMON_ATTRIBUTES;
import static net.inetalliance.lutra.rules.ChildRule.ANY_INLINE_OR_TEXT_ELEMENTS;

public class H5Element extends CommonAbstractElement<H5Element> implements BlockElement {

	public H5Element(final String text) {
		this(new TextContent(text));
	}

	public H5Element(final H5ElementChild... children) {
		super(H5Element.class, ElementType.H5, ANY_INLINE_OR_TEXT_ELEMENTS, ANY_COMMON_ATTRIBUTES, children);
	}

	@Override
	public H5Element copy() {
		return (H5Element) copyWithListeners();
	}
}