package net.inetalliance.lutra.elements;

import static net.inetalliance.lutra.rules.AttributeRule.*;
import static net.inetalliance.lutra.rules.ChildRule.*;

public class FooterElement
	extends CommonAbstractElement<FooterElement>
	implements BlockElement {

	public FooterElement(final String text) {
		this(new TextContent(text));
	}

	public FooterElement(final FooterElementChild... children) {
		super(FooterElement.class, ElementType.FOOTER,
			ANY_BLOCK_OR_INLINE_OR_TEXT_ELEMENTS, ANY_COMMON_ATTRIBUTES, children);
	}

	@Override
	protected boolean isClosed() {
		return false;
	}

	@Override
	public FooterElement copy() {
		return (FooterElement) copyWithListeners();
	}
}