package net.inetalliance.lutra.filters;

import net.inetalliance.lutra.elements.Attribute;
import net.inetalliance.lutra.elements.Element;

import java.util.function.Predicate;

public abstract class AttributePredicate implements Predicate<Element>
{
	private final Attribute attribute;

	public AttributePredicate(final Attribute attribute)
	{
		this.attribute = attribute;
	}

	@Override
	public boolean test(final Element element)
	{
		return $(element.getAttribute(attribute));
	}

	protected abstract boolean $(final String value);
}