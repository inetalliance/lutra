package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;

public class BigElement extends CommonAbstractElement<BigElement> implements InlineElement {

	public BigElement(final String text) {
		this(new TextContent(text));
	}

	public BigElement(final BigElementChild... children) {
		super(BigElement.class, ElementType.BIG, ChildRule.ANY_INLINE_OR_TEXT_ELEMENTS, AttributeRule.ANY_COMMON_ATTRIBUTES, children);
	}

	@Override
	public BigElement copy() {
		return (BigElement) copyWithListeners();
	}
}