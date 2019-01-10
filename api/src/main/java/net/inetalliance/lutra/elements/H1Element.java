package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;

public class H1Element extends CommonAbstractElement<H1Element> implements BlockElement {

	public H1Element(final String text) {
		this(new TextContent(text));
	}

	public H1Element(final H1ElementChild... children) {
		super(H1Element.class, ElementType.H1, ChildRule.ANY_INLINE_OR_TEXT_ELEMENTS, AttributeRule.ANY_COMMON_ATTRIBUTES, children);
	}

	@Override
	public H1Element clone() throws CloneNotSupportedException {
		return (H1Element) cloneWithListeners();
	}
}