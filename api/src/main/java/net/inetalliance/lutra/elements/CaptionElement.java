package net.inetalliance.lutra.elements;

import static net.inetalliance.lutra.rules.AttributeRule.ANY_COMMON_ATTRIBUTES;
import static net.inetalliance.lutra.rules.ChildRule.ANY_INLINE_OR_TEXT_ELEMENTS;

public class CaptionElement extends CommonAbstractElement<CaptionElement> implements TableElementChild {
	@Override
	public CaptionElement clone() throws CloneNotSupportedException {
		return (CaptionElement) cloneWithListeners();
	}

	public CaptionElement(final String text) {
		this(new TextContent(text));
	}

	public CaptionElement(final CaptionElementChild... children) {
		super(CaptionElement.class, ElementType.CAPTION, ANY_INLINE_OR_TEXT_ELEMENTS, ANY_COMMON_ATTRIBUTES, children);
	}
}