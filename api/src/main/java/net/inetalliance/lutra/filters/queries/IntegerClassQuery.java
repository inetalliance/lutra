package net.inetalliance.lutra.filters.queries;

public class IntegerClassQuery extends ClassQuery<Integer>
{
	public IntegerClassQuery(final String pattern)
	{
		super(pattern);
	}

	@Override
	protected Integer fromString(final String value)
	{
		return value == null ? null : Integer.parseInt(value);
	}
}