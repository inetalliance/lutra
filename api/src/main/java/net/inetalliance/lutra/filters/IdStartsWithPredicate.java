package net.inetalliance.lutra.filters;

import net.inetalliance.lutra.elements.Attribute;

public class IdStartsWithPredicate extends AttributeStartsWithPredicate
{
	public IdStartsWithPredicate(final String startsWith)
	{
		super(Attribute.ID, startsWith);
	}
}