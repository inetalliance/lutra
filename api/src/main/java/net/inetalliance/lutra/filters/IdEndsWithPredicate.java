package net.inetalliance.lutra.filters;

import net.inetalliance.lutra.elements.Attribute;
import net.inetalliance.lutra.elements.CommonAbstractElement;
import net.inetalliance.lutra.elements.Element;

public class IdEndsWithPredicate extends AttributeEndsWithPredicate
{
	public IdEndsWithPredicate(final String endsWith)
	{
		super(Attribute.ID, endsWith);
	}

	@Override
	public boolean test(final Element element)
	{
		return element instanceof CommonAbstractElement && super.test(element);
	}
}