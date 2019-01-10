package net.inetalliance.lutra.rules;

import net.inetalliance.lutra.elements.Element;
import net.inetalliance.lutra.elements.ElementType;

import java.util.Collection;
import java.util.EnumSet;

public abstract class ChildRule
{
	public static final ChildRule[] NONE = {NoChildren.INSTANCE};
	public static final ChildRule[] TEXT = {new MayHaveChild(EnumSet.of(ElementType.TEXTCONTENT))};
	public static final ChildRule[] ANY_BLOCK_ELEMENTS = {new MayHaveChild(ElementType.blockElements)};
	public static final ChildRule[] ANY_INLINE_ELEMENTS = {new MayHaveChild(ElementType.inlineElements)};
	public static final ChildRule[] ANY_INLINE_OR_TEXT_ELEMENTS = {new MayHaveChild(ElementType.INLINE_AND_TEXT_ELEMENTS)};
	public static final ChildRule[] ANY_BLOCK_OR_INLINE_ELEMENTS = {new MayHaveChild(ElementType.BLOCK_AND_INLINE_ELEMENTS)};
	public static final ChildRule[] ANY_BLOCK_OR_INLINE_OR_TEXT_ELEMENTS = {new MayHaveChild(ElementType.BLOCK_AND_INLINE_AND_TEXT_ELEMENTS)};

	public abstract void validate(final Element parent, final Collection<Element> children, final ValidationErrors errors, final boolean strict);
}
