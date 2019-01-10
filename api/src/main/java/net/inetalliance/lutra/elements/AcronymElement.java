package net.inetalliance.lutra.elements;

import static net.inetalliance.lutra.rules.AttributeRule.ANY_COMMON_ATTRIBUTES;
import static net.inetalliance.lutra.rules.ChildRule.ANY_INLINE_OR_TEXT_ELEMENTS;

public class AcronymElement extends CommonAbstractElement<AcronymElement> implements InlineElement {

	public AcronymElement(final String text) {
		this(new TextContent(text));
	}

	public AcronymElement(final AcronymElementChild... children) {
		super(AcronymElement.class, ElementType.ACRONYM, ANY_INLINE_OR_TEXT_ELEMENTS, ANY_COMMON_ATTRIBUTES, children);
	}

	@Override
	public AcronymElement clone() throws CloneNotSupportedException {
		return (AcronymElement) cloneWithListeners();
	}
}