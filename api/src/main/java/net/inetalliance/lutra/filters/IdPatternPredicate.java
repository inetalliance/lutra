package net.inetalliance.lutra.filters;

import java.util.regex.Pattern;

import static net.inetalliance.lutra.elements.Attribute.*;

public class IdPatternPredicate extends AttributePatternPredicate
{
	public IdPatternPredicate(final String pattern)
	{
		super(ID, pattern);
	}

	public IdPatternPredicate(final Pattern pattern)
	{
		super(ID, pattern);
	}
}