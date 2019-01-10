package net.inetalliance.lutra.rules;

import net.inetalliance.lutra.elements.Element;

import java.util.Collection;

public class NoChildren extends ChildRule
{
	public static final NoChildren INSTANCE = new NoChildren();

	private NoChildren()
	{
	}

	@Override
	public void validate(final Element parent, final Collection<Element> children, final ValidationErrors errors, final boolean strict)
	{
		if (!children.isEmpty())
			errors.add(parent, String.format("Element of type %s may not have any children", parent.type));
	}
}