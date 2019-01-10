package net.inetalliance.lutra.elements;

import static net.inetalliance.lutra.rules.AttributeRule.ANY_COMMON_ATTRIBUTES;
import static net.inetalliance.lutra.rules.ChildRule.ANY_INLINE_OR_TEXT_ELEMENTS;

public class SupElement extends CommonAbstractElement<SupElement> implements InlineElement {

	public SupElement(final String text) {
		this(new TextContent(text));
	}

	public SupElement(final SupElementChild... children) {
		super(SupElement.class, ElementType.SUP, ANY_INLINE_OR_TEXT_ELEMENTS, ANY_COMMON_ATTRIBUTES, children);
	}

	@Override
	public SupElement clone() throws CloneNotSupportedException {
		return (SupElement) cloneWithListeners();
	}
}