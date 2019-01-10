package net.inetalliance.lutra.elements;

import static net.inetalliance.lutra.rules.AttributeRule.ANY_COMMON_ATTRIBUTES;
import static net.inetalliance.lutra.rules.ChildRule.ANY_INLINE_OR_TEXT_ELEMENTS;

public class StrongElement extends CommonAbstractElement<StrongElement> implements InlineElement {

	public StrongElement(final String text) {
		this(new TextContent(text));
	}

	public StrongElement(final StrongElementChild... children) {
		super(StrongElement.class, ElementType.STRONG, ANY_INLINE_OR_TEXT_ELEMENTS, ANY_COMMON_ATTRIBUTES, children);
	}

	@Override
	public StrongElement clone() throws CloneNotSupportedException {
		return (StrongElement) cloneWithListeners();
	}
}