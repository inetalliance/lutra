package net.inetalliance.lutra.elements;

import static net.inetalliance.lutra.rules.AttributeRule.ANY_COMMON_ATTRIBUTES;
import static net.inetalliance.lutra.rules.ChildRule.ANY_INLINE_OR_TEXT_ELEMENTS;

public class PElement extends CommonAbstractElement<PElement> implements BlockElement {

	public PElement(final String text) {
		this(new TextContent(text));
	}

	public PElement(final PElementChild... children) {
		super(PElement.class, ElementType.P, ANY_INLINE_OR_TEXT_ELEMENTS, ANY_COMMON_ATTRIBUTES, children);
	}

	@Override
	public PElement clone() throws CloneNotSupportedException {
		return (PElement) cloneWithListeners();
	}
}