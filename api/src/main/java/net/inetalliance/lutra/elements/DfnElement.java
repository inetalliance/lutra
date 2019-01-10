package net.inetalliance.lutra.elements;

import static net.inetalliance.lutra.rules.AttributeRule.ANY_COMMON_ATTRIBUTES;
import static net.inetalliance.lutra.rules.ChildRule.ANY_INLINE_OR_TEXT_ELEMENTS;

public class DfnElement extends CommonAbstractElement<DfnElement> implements InlineElement {

	public DfnElement(final String text) {
		this(new TextContent(text));
	}

	public DfnElement(final DfnElementChild... children) {
		super(DfnElement.class, ElementType.DFN, ANY_INLINE_OR_TEXT_ELEMENTS, ANY_COMMON_ATTRIBUTES, children);
	}

	@Override
	public DfnElement clone() throws CloneNotSupportedException {
		return (DfnElement) cloneWithListeners();
	}
}