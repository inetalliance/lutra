package net.inetalliance.lutra.filters;

import net.inetalliance.lutra.elements.Attribute;

public class AttributeEndsWithPredicate extends AttributePredicate
{
	private final String endsWith;

	public AttributeEndsWithPredicate(final Attribute attribute, final String endsWith)
	{
		super(attribute);
		this.endsWith = endsWith;
	}

	@Override
	public boolean $(final String value)
	{
		return value != null && value.endsWith(endsWith);
	}
}