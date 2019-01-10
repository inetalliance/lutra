package net.inetalliance.lutra.filters;

import net.inetalliance.lutra.elements.Attribute;

public class AttributeStartsWithPredicate extends AttributePredicate
{
	private final String startsWith;

	public AttributeStartsWithPredicate(final Attribute attribute, final String startsWith)
	{
		super(attribute);
		this.startsWith = startsWith;
	}

	@Override
	public boolean $(final String value)
	{
		return value != null && value.startsWith(startsWith);
	}
}
