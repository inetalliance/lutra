package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;
import net.inetalliance.lutra.rules.MayHaveChild;
import net.inetalliance.lutra.rules.MayNotHaveDescendant;

public class PreElement extends CommonAbstractElement<PreElement> implements BlockElement
{

	private static final ChildRule[] childRules =
			{
					new MayHaveChild(ElementType.INLINE_AND_TEXT_ELEMENTS),
					new MayNotHaveDescendant(ElementType.IMG, ElementType.BIG, ElementType.SMALL, ElementType.SUB, ElementType.SUP)
			};

	public PreElement(final String text)
	{
		this(new TextContent(text));
	}

	public PreElement(final PreElementChild... children)
	{
		super(PreElement.class, ElementType.PRE, childRules, AttributeRule.ANY_COMMON_ATTRIBUTES, children);
	}

	@Override
	public PreElement copy() {
		return (PreElement) copyWithListeners();
	}
}