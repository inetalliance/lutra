package net.inetalliance.lutra.elements;

import static net.inetalliance.lutra.rules.AttributeRule.*;
import static net.inetalliance.lutra.rules.ChildRule.*;

public class AsideElement
	extends CommonAbstractElement<AsideElement>
	implements BlockElement {

	public AsideElement(final String text) {
		this(new TextContent(text));
	}

	public AsideElement(final AsideElementChild... children) {
		super(AsideElement.class, ElementType.ASIDE,
			ANY_BLOCK_OR_INLINE_OR_TEXT_ELEMENTS, ANY_COMMON_ATTRIBUTES, children);
	}

	@Override
	protected boolean isClosed() {
		return false;
	}

	@Override
	public AsideElement copy() {
		return (AsideElement) copyWithListeners();
	}
}