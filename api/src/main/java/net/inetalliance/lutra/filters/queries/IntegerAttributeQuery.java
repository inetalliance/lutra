package net.inetalliance.lutra.filters.queries;

import net.inetalliance.lutra.elements.Attribute;

public class IntegerAttributeQuery extends AttributeQuery<Integer>
{
	public IntegerAttributeQuery(final Attribute attribute, final String pattern)
	{
		super(attribute, pattern);
	}

	@Override
	protected Integer fromString(final String value)
	{
		return Integer.parseInt(value);
	}
}