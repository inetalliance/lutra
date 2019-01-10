package net.inetalliance.lutra.filters;

import net.inetalliance.lutra.elements.Element;

import java.util.function.Predicate;

public class HasParentMatchingPredicate implements Predicate<Element>
{
	private final Predicate<Element> parentPredicate;

	public HasParentMatchingPredicate(final Predicate<Element> parentPredicate)
	{
		this.parentPredicate = parentPredicate;
	}

	@Override
	public boolean test(final Element element)
	{
		final Element parent = element.getParent();
		return parent != null && parentPredicate.test(element);
	}
}