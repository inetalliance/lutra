package net.inetalliance.lutra.elements;

import static net.inetalliance.lutra.rules.AttributeRule.ANY_COMMON_ATTRIBUTES;
import static net.inetalliance.lutra.rules.ChildRule.ANY_INLINE_OR_TEXT_ELEMENTS;

public class BdoElement extends CommonAbstractElement<BdoElement> implements InlineElement {
	public BdoElement(final String text) {
		this(new TextContent(text));
	}

	public BdoElement(final BdoElementChild... children) {
		super(BdoElement.class, ElementType.BDO, ANY_INLINE_OR_TEXT_ELEMENTS, ANY_COMMON_ATTRIBUTES, children);
	}

	@Override
	public BdoElement copy() {
		return (BdoElement) copyWithListeners();
	}
}