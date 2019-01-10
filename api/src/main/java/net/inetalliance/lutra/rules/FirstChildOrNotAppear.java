package net.inetalliance.lutra.rules;

import net.inetalliance.lutra.elements.Element;
import net.inetalliance.lutra.elements.ElementType;

import java.util.Collection;
import java.util.Iterator;

public class FirstChildOrNotAppear extends ChildRule
{
	private final ElementType type;

	public FirstChildOrNotAppear(final ElementType type)
	{
		this.type = type;
	}

	@Override
	public void validate(final Element parent, final Collection<Element> children, final ValidationErrors errors, final boolean strict)
	{
		if (!children.isEmpty())
		{
			final Iterator<Element> iterator = children.iterator();
			if (iterator.next().type == type)
				return;
			while (iterator.hasNext())
			{
				if (iterator.next().type == type)
				{
					errors.add(parent, String.format("Element of type %s may only contain one element of type %s, and it must be the first child.", parent.type, type));
					return;
				}
			}
		}
	}
}
