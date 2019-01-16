package net.inetalliance.lutra.elements;

import static net.inetalliance.lutra.rules.AttributeRule.ANY_COMMON_ATTRIBUTES;
import static net.inetalliance.lutra.rules.ChildRule.ANY_INLINE_OR_TEXT_ELEMENTS;

public class KbdElement extends CommonAbstractElement<KbdElement> {

	public KbdElement(final String text) {
		this(new TextContent(text));
	}

	public KbdElement(final KbdElementChild... children) {
		super(KbdElement.class, ElementType.KBD, ANY_INLINE_OR_TEXT_ELEMENTS, ANY_COMMON_ATTRIBUTES, children);
	}

	@Override
	public KbdElement copy() {
		return (KbdElement) copyWithListeners();
	}
}