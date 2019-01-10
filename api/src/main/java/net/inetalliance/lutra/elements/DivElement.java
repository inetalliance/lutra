package net.inetalliance.lutra.elements;

import static net.inetalliance.lutra.rules.AttributeRule.ANY_COMMON_ATTRIBUTES;
import static net.inetalliance.lutra.rules.ChildRule.ANY_BLOCK_OR_INLINE_OR_TEXT_ELEMENTS;

public class DivElement extends CommonAbstractElement<DivElement> implements BlockElement {

	private static final DivElement CLEAR =
		new DivElement(new ImgElement()
			.setSrc("/images/clear.gif")
			.setAlt("clear")
			.setWidth(1)
			.setHeight(1)).addClass("clear");

	public DivElement(final String text) {
		this(new TextContent(text));
	}

	public DivElement(final DivElementChild... children) {
		super(DivElement.class, ElementType.DIV,
			ANY_BLOCK_OR_INLINE_OR_TEXT_ELEMENTS, ANY_COMMON_ATTRIBUTES, children);
	}

	public static DivElement clear() throws CloneNotSupportedException {
		return CLEAR.clone();
	}

	@Override
	protected boolean isClosed() {
		return false;
	}

	@Override
	public DivElement clone() throws CloneNotSupportedException {
		return (DivElement) cloneWithListeners();
	}
}