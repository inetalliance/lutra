package net.inetalliance.lutra.rules;

import net.inetalliance.lutra.elements.Element;
import net.inetalliance.lutra.elements.ElementType;

import java.util.Collection;

public class MayNotHaveDescendant extends ChildRule
{
	private final ElementType[] descendantTypes;

	public MayNotHaveDescendant(final ElementType... descendantTypes)
	{
		this.descendantTypes = descendantTypes;
	}

	@Override
	public void validate(final Element parent, final Collection<Element> children, final ValidationErrors errors, final boolean strict)
	{
		final Element descendant = parent.getFirstDescendantOfType(descendantTypes);
		if (descendant != null)
			errors.add(parent, String.format("Element of type %s may not contain an element of type %s at any depth", parent.elementType, descendant.elementType));
	}
}