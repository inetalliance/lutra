package net.inetalliance.lutra.elements;

import static net.inetalliance.lutra.rules.AttributeRule.ANY_COMMON_ATTRIBUTES;
import static net.inetalliance.lutra.rules.ChildRule.ANY_INLINE_OR_TEXT_ELEMENTS;

public class BElement extends CommonAbstractElement<BElement> implements InlineElement {
	public BElement(final String text) {
		this(new TextContent(text));
	}

	public BElement(final BElementChild... children) {
		super(BElement.class, ElementType.B, ANY_INLINE_OR_TEXT_ELEMENTS, ANY_COMMON_ATTRIBUTES, children);
	}

	@Override
	public BElement clone() throws CloneNotSupportedException {
		return (BElement) cloneWithListeners();
	}
}