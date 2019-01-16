package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;

public class SampElement extends CommonAbstractElement<SampElement> implements InlineElement
{

	public SampElement(final String text)
	{
		this(new TextContent(text));
	}

	public SampElement(final SampElementChild... children)
	{
		super(SampElement.class, ElementType.SAMP, ChildRule.ANY_INLINE_OR_TEXT_ELEMENTS, AttributeRule.ANY_COMMON_ATTRIBUTES, children);
	}

	@Override
	public SampElement copy() {
		return (SampElement) copyWithListeners();
	}
}