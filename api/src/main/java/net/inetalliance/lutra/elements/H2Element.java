package net.inetalliance.lutra.elements;

import static net.inetalliance.lutra.rules.AttributeRule.ANY_COMMON_ATTRIBUTES;
import static net.inetalliance.lutra.rules.ChildRule.ANY_INLINE_OR_TEXT_ELEMENTS;

public class H2Element extends CommonAbstractElement<H2Element> implements BlockElement {

	public H2Element(final String text) {
		this(new TextContent(text));
	}

	public H2Element(final H2ElementChild... children) {
		super(H2Element.class, ElementType.H2, ANY_INLINE_OR_TEXT_ELEMENTS, ANY_COMMON_ATTRIBUTES, children);
	}

	@Override
	public H2Element copy() {
		return (H2Element) copyWithListeners();
	}
}