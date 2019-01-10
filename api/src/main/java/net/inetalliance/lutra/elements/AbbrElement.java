package net.inetalliance.lutra.elements;

import static net.inetalliance.lutra.elements.ElementType.ABBR;
import static net.inetalliance.lutra.rules.AttributeRule.ANY_COMMON_ATTRIBUTES;
import static net.inetalliance.lutra.rules.ChildRule.ANY_INLINE_OR_TEXT_ELEMENTS;

public class AbbrElement extends CommonAbstractElement<AbbrElement> implements InlineElement {
	public AbbrElement(final String text) {
		this(new TextContent(text));
	}

	public AbbrElement(final AbbrElementChild... children) {
		super(AbbrElement.class, ABBR, ANY_INLINE_OR_TEXT_ELEMENTS, ANY_COMMON_ATTRIBUTES, children);
	}

	@Override
	public AbbrElement clone() throws CloneNotSupportedException {
		return (AbbrElement) cloneWithListeners();
	}
}