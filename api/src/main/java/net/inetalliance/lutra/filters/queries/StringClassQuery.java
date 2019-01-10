package net.inetalliance.lutra.filters.queries;

public class StringClassQuery extends ClassQuery<String>
{
	public StringClassQuery(final String pattern)
	{
		super(pattern);
	}

	@Override
	protected String fromString(final String value)
	{
		return value;
	}
}