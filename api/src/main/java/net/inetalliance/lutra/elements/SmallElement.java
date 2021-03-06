package net.inetalliance.lutra.elements;

import static net.inetalliance.lutra.rules.AttributeRule.ANY_COMMON_ATTRIBUTES;
import static net.inetalliance.lutra.rules.ChildRule.ANY_INLINE_OR_TEXT_ELEMENTS;

public class SmallElement extends CommonAbstractElement<SmallElement> implements InlineElement {

	public SmallElement(final String text) {
		this(new TextContent(text));
	}

	public SmallElement(final SmallElementChild... children) {
		super(SmallElement.class, ElementType.SMALL, ANY_INLINE_OR_TEXT_ELEMENTS, ANY_COMMON_ATTRIBUTES, children);
	}

	@Override
	public SmallElement copy() {
		return (SmallElement) copyWithListeners();
	}
}