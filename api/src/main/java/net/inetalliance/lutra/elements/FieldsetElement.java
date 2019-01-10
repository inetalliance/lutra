package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;
import net.inetalliance.lutra.rules.MayHaveChild;

import java.util.EnumSet;

import static net.inetalliance.lutra.elements.ElementType.BLOCK_AND_INLINE_AND_TEXT_ELEMENTS;

public class FieldsetElement extends CommonAbstractElement<FieldsetElement> implements BlockElement
{
	private static final ChildRule[] childRules =
			{
					new MayHaveChild(ElementType.union(BLOCK_AND_INLINE_AND_TEXT_ELEMENTS, EnumSet.of(ElementType.LEGEND)))
			};

	public FieldsetElement(final String text)
	{
		this(new TextContent(text));
	}

	public FieldsetElement(final FieldsetElementChild... children)
	{
		super(FieldsetElement.class, ElementType.FIELDSET, childRules, AttributeRule.ANY_COMMON_ATTRIBUTES, children);
	}

	@Override
	public FieldsetElement clone() throws CloneNotSupportedException
	{
		return (FieldsetElement) cloneWithListeners();
	}
}