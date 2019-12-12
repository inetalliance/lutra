package net.inetalliance.lutra.elements;

import static net.inetalliance.lutra.rules.AttributeRule.*;
import static net.inetalliance.lutra.rules.ChildRule.*;

public class SectionElement
	extends CommonAbstractElement<SectionElement>
	implements BlockElement {

	public SectionElement(final String text) {
		this(new TextContent(text));
	}

	public SectionElement(final DivElementChild... children) {
		super(SectionElement.class, ElementType.SECTION,
			ANY_BLOCK_OR_INLINE_OR_TEXT_ELEMENTS, ANY_COMMON_ATTRIBUTES, children);
	}

	@Override
	protected boolean isClosed() {
		return false;
	}

	@Override
	public SectionElement copy() {
		return (SectionElement) copyWithListeners();
	}
}