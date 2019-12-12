package net.inetalliance.lutra.elements;

import static net.inetalliance.lutra.rules.AttributeRule.*;
import static net.inetalliance.lutra.rules.ChildRule.*;

public class TextElement
	extends CommonAbstractElement<TextElement> implements SvgElementChild {

	public TextElement() {
		this(new TextElementChild[]{});
	}

	public TextElement(final TextElementChild... children) {
		super(TextElement.class, ElementType.TEXT, ANY_INLINE_OR_TEXT_ELEMENTS, ANY_COMMON_ATTRIBUTES, children);
	}

	@Override
	public TextElement copy() {
		return (TextElement) copyWithListeners();
	}
}