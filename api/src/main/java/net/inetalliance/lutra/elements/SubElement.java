package net.inetalliance.lutra.elements;

import static net.inetalliance.lutra.rules.AttributeRule.ANY_COMMON_ATTRIBUTES;
import static net.inetalliance.lutra.rules.ChildRule.ANY_INLINE_OR_TEXT_ELEMENTS;

public class SubElement extends CommonAbstractElement<SubElement> implements InlineElement {

	public SubElement(final String text) {
		this(new TextContent(text));
	}

	public SubElement(final SubElementChild... children) {
		super(SubElement.class, ElementType.SUB, ANY_INLINE_OR_TEXT_ELEMENTS, ANY_COMMON_ATTRIBUTES, children);
	}

	@Override
	public SubElement clone() throws CloneNotSupportedException {
		return (SubElement) cloneWithListeners();
	}
}