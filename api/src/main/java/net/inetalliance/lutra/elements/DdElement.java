package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;

public class DdElement extends CommonAbstractElement<DdElement> implements DlElementChild {

	public DdElement(final String text) {
		this(new TextContent(text));
	}

	public DdElement(final DdElementChild... children) {
		super(DdElement.class, ElementType.DD, ChildRule.ANY_BLOCK_OR_INLINE_OR_TEXT_ELEMENTS, AttributeRule.ANY_COMMON_ATTRIBUTES, children);
	}

	@Override
	public DdElement clone() throws CloneNotSupportedException {
		return (DdElement) cloneWithListeners();
	}
}