package net.inetalliance.lutra.elements;

import static net.inetalliance.lutra.rules.AttributeRule.ANY_COMMON_ATTRIBUTES;
import static net.inetalliance.lutra.rules.ChildRule.ANY_INLINE_OR_TEXT_ELEMENTS;

public class EmElement extends CommonAbstractElement<EmElement> implements InlineElement {

	public EmElement(final String text) {
		this(new TextContent(text));
	}

	public EmElement(final EmElementChild... children) {
		super(EmElement.class, ElementType.EM, ANY_INLINE_OR_TEXT_ELEMENTS, ANY_COMMON_ATTRIBUTES, children);
	}

	@Override
	public EmElement copy() {
		return (EmElement) copyWithListeners();
	}
}