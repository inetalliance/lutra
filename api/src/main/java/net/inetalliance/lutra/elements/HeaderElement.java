package net.inetalliance.lutra.elements;

import static net.inetalliance.lutra.rules.AttributeRule.*;
import static net.inetalliance.lutra.rules.ChildRule.*;

public class HeaderElement
	extends CommonAbstractElement<HeaderElement>
	implements BlockElement {

	public HeaderElement(final String text) {
		this(new TextContent(text));
	}

	public HeaderElement(final HeaderElementChild... children) {
		super(HeaderElement.class, ElementType.HEADER,
			ANY_BLOCK_OR_INLINE_OR_TEXT_ELEMENTS, ANY_COMMON_ATTRIBUTES, children);
	}

	@Override
	protected boolean isClosed() {
		return false;
	}

	@Override
	public HeaderElement copy() {
		return (HeaderElement) copyWithListeners();
	}
}