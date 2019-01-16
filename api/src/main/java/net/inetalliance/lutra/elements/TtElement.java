package net.inetalliance.lutra.elements;

import static net.inetalliance.lutra.rules.AttributeRule.ANY_COMMON_ATTRIBUTES;
import static net.inetalliance.lutra.rules.ChildRule.ANY_INLINE_OR_TEXT_ELEMENTS;

public class TtElement extends CommonAbstractElement<TtElement> implements InlineElement {

	public TtElement(final String text) {
		this(new TextContent(text));
	}

	public TtElement(final TtElementChild... children) {
		super(TtElement.class, ElementType.TT, ANY_INLINE_OR_TEXT_ELEMENTS, ANY_COMMON_ATTRIBUTES, children);
	}

	@Override
	public TtElement copy() {
		return (TtElement) copyWithListeners();
	}
}