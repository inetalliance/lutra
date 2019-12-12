package net.inetalliance.lutra.elements;

import static net.inetalliance.lutra.rules.AttributeRule.*;
import static net.inetalliance.lutra.rules.ChildRule.*;

public class MainElement
	extends CommonAbstractElement<MainElement>
	implements BlockElement {

	public MainElement(final String text) {
		this(new TextContent(text));
	}

	public MainElement(final MainElementChild... children) {
		super(MainElement.class, ElementType.MAIN,
			ANY_BLOCK_OR_INLINE_OR_TEXT_ELEMENTS, ANY_COMMON_ATTRIBUTES, children);
	}

	@Override
	protected boolean isClosed() {
		return false;
	}

	@Override
	public MainElement copy() {
		return (MainElement) copyWithListeners();
	}
}