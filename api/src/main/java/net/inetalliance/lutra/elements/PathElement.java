package net.inetalliance.lutra.elements;

import static net.inetalliance.lutra.rules.AttributeRule.*;
import static net.inetalliance.lutra.rules.ChildRule.*;

public class PathElement
	extends CommonAbstractElement<PathElement> implements SvgElementChild {

	public PathElement() {
		this(new PathElementChild[]{});
	}

	public PathElement(final PathElementChild... children) {
		super(PathElement.class, ElementType.PATH, ANY_INLINE_OR_TEXT_ELEMENTS, ANY_COMMON_ATTRIBUTES, children);
	}

	@Override
	public PathElement copy() {
		return (PathElement) copyWithListeners();
	}
}