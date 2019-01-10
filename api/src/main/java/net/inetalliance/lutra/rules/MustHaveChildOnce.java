package net.inetalliance.lutra.rules;

import net.inetalliance.lutra.elements.Element;
import net.inetalliance.lutra.elements.ElementType;

import java.util.Collection;

public class MustHaveChildOnce extends ChildRule
{
	private final ElementType childType;

	public MustHaveChildOnce(final ElementType childType)
	{
		this.childType = childType;
	}

	@Override
	public void validate(final Element parent, final Collection<Element> children, final ValidationErrors errors, final boolean strict)
	{
		boolean has = false;
		final String message = "Element of type %s must contain exactly one %s";
		for (final Element child : children)
		{
			if (child.type == childType)
			{
				if (has)
					errors.add(parent, String.format(message, parent.type, childType));
				else
					has = true;
			}
		}
		if (!has)
			errors.add(parent, String.format(message, parent.type, childType));
	}
}