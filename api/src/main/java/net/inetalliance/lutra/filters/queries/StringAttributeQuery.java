package net.inetalliance.lutra.filters.queries;

import net.inetalliance.lutra.elements.Attribute;

public class StringAttributeQuery extends AttributeQuery<String>
{
	public static final StringAttributeQuery INPUT = new StringAttributeQuery(Attribute.ID, "([a-zA-Z_]+)_input");

	public StringAttributeQuery(final Attribute attribute, final String pattern)
	{
		super(attribute, pattern);
	}

	@Override
	protected String fromString(final String value)
	{
		return value;
	}
}