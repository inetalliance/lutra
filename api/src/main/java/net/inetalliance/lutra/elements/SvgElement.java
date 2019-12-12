package net.inetalliance.lutra.elements;

import static net.inetalliance.lutra.rules.AttributeRule.*;
import static net.inetalliance.lutra.rules.ChildRule.*;

public class SvgElement
	extends CommonAbstractElement<SvgElement> implements InlineElement {

	public SvgElement(final String text) {
		this(new TextContent(text));
	}

	public SvgElement(final SvgElementChild... children) {
		super(SvgElement.class, ElementType.SVG, ANY_INLINE_OR_TEXT_ELEMENTS, ANY_COMMON_ATTRIBUTES, children);
	}

	@Override
	public SvgElement copy() {
		return (SvgElement) copyWithListeners();
	}
}