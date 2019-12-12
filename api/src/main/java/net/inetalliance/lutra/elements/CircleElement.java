package net.inetalliance.lutra.elements;

import static net.inetalliance.lutra.rules.AttributeRule.*;
import static net.inetalliance.lutra.rules.ChildRule.*;

public class CircleElement
	extends CommonAbstractElement<CircleElement> implements SvgElementChild {

	public CircleElement() {
		this(new CircleElementChild[]{});
	}

	public CircleElement(final CircleElementChild... children) {
		super(CircleElement.class, ElementType.CIRCLE, ANY_INLINE_OR_TEXT_ELEMENTS, ANY_COMMON_ATTRIBUTES, children);
	}

	@Override
	public CircleElement copy() {
		return (CircleElement) copyWithListeners();
	}
}