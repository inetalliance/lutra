package net.inetalliance.lutra.elements;

import static net.inetalliance.lutra.rules.AttributeRule.ANY_COMMON_ATTRIBUTES;
import static net.inetalliance.lutra.rules.ChildRule.ANY_INLINE_OR_TEXT_ELEMENTS;

public class VarElement extends CommonAbstractElement<VarElement> implements InlineElement {

	public VarElement(final String text) {
		this(new TextContent(text));
	}

	public VarElement(final VarElementChild... children) {
		super(VarElement.class, ElementType.VAR, ANY_INLINE_OR_TEXT_ELEMENTS, ANY_COMMON_ATTRIBUTES, children);
	}

	@Override
	public VarElement clone() throws CloneNotSupportedException {
		return (VarElement) cloneWithListeners();
	}
}