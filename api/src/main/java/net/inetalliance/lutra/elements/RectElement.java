package net.inetalliance.lutra.elements;

import static net.inetalliance.lutra.rules.AttributeRule.*;
import static net.inetalliance.lutra.rules.ChildRule.*;

public class RectElement
	extends CommonAbstractElement<RectElement> implements SvgElementChild {

	public RectElement() {
		this(new RectElementChild[]{});
	}

	public RectElement(final RectElementChild... children) {
		super(RectElement.class, ElementType.RECT, ANY_INLINE_OR_TEXT_ELEMENTS, ANY_COMMON_ATTRIBUTES, children);
	}

	@Override
	public RectElement copy() {
		return (RectElement) copyWithListeners();
	}
}