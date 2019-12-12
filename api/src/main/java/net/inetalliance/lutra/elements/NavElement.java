package net.inetalliance.lutra.elements;

import static net.inetalliance.lutra.rules.AttributeRule.*;
import static net.inetalliance.lutra.rules.ChildRule.*;

public class NavElement
	extends CommonAbstractElement<NavElement>
	implements BlockElement {

	public NavElement(final String text) {
		this(new TextContent(text));
	}

	public NavElement(final NavElementChild... children) {
		super(NavElement.class, ElementType.NAV,
			ANY_BLOCK_OR_INLINE_OR_TEXT_ELEMENTS, ANY_COMMON_ATTRIBUTES, children);
	}

	@Override
	protected boolean isClosed() {
		return false;
	}

	@Override
	public NavElement copy() {
		return (NavElement) copyWithListeners();
	}
}