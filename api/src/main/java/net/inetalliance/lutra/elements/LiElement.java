package net.inetalliance.lutra.elements;

import static net.inetalliance.lutra.rules.AttributeRule.ANY_COMMON_ATTRIBUTES;
import static net.inetalliance.lutra.rules.ChildRule.ANY_BLOCK_OR_INLINE_OR_TEXT_ELEMENTS;

public class LiElement extends CommonAbstractElement<LiElement> implements UlElementChild, OlElementChild
{

	public LiElement(final String text)
	{
		this(new TextContent(text));
	}

	public LiElement(final LiElementChild... children)
	{
		super(LiElement.class, ElementType.LI, ANY_BLOCK_OR_INLINE_OR_TEXT_ELEMENTS, ANY_COMMON_ATTRIBUTES, children);
	}

	@Override
	public LiElement copy() {
		return (LiElement) copyWithListeners();
	}
}