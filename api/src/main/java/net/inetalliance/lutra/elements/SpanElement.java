package net.inetalliance.lutra.elements;

import static net.inetalliance.lutra.rules.AttributeRule.ANY_COMMON_ATTRIBUTES;
import static net.inetalliance.lutra.rules.ChildRule.ANY_INLINE_OR_TEXT_ELEMENTS;

public class SpanElement extends CommonAbstractElement<SpanElement> implements InlineElement {

	public SpanElement(final String text) {
		this(new TextContent(text));
	}

	@Override
	protected boolean isClosed() {
		return false;
	}

	public SpanElement(final SpanElementChild... children) {
		super(SpanElement.class, ElementType.SPAN, ANY_INLINE_OR_TEXT_ELEMENTS, ANY_COMMON_ATTRIBUTES, children);
	}

	@Override
	public SpanElement copy() {
		return (SpanElement) copyWithListeners();
	}
}