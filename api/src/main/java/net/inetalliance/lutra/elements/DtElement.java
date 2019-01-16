package net.inetalliance.lutra.elements;

import static net.inetalliance.lutra.rules.AttributeRule.ANY_COMMON_ATTRIBUTES;
import static net.inetalliance.lutra.rules.ChildRule.ANY_INLINE_OR_TEXT_ELEMENTS;

public class DtElement extends CommonAbstractElement<DtElement> implements DlElementChild {

	public DtElement(final String text) {
		this(new TextContent(text));
	}

	public DtElement(final DtElementChild... children) {
		super(DtElement.class, ElementType.DT, ANY_INLINE_OR_TEXT_ELEMENTS, ANY_COMMON_ATTRIBUTES, children);
	}

	@Override
	public DtElement copy() {
		return (DtElement) copyWithListeners();
	}
}