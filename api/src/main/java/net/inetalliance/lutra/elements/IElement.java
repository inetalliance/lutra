package net.inetalliance.lutra.elements;

import static net.inetalliance.lutra.rules.AttributeRule.ANY_COMMON_ATTRIBUTES;
import static net.inetalliance.lutra.rules.ChildRule.ANY_INLINE_OR_TEXT_ELEMENTS;

public class IElement extends CommonAbstractElement<IElement> implements InlineElement {
	public IElement(final String text) {
		this(new TextContent(text));
	}

	public IElement(final IElementChild... children) {
		super(IElement.class, ElementType.I, ANY_INLINE_OR_TEXT_ELEMENTS, ANY_COMMON_ATTRIBUTES, children);
	}

	@Override
	public IElement clone() throws CloneNotSupportedException {
		return (IElement) cloneWithListeners();
	}
}