package net.inetalliance.lutra.rules;

import net.inetalliance.lutra.elements.Element;
import net.inetalliance.lutra.elements.ElementType;

import java.util.Collection;
import java.util.EnumSet;

public class MayHaveChild extends ChildRule
{
	private final EnumSet<ElementType> childTypes;

	public MayHaveChild(final EnumSet<ElementType> childTypes)
	{
		this.childTypes = childTypes;
	}

	@Override
	public void validate(final Element parent, final Collection<Element> children, final ValidationErrors errors, final boolean strict)
	{
		for (final Element child : children)
		{
			if (!childTypes.contains(child.type))
				errors.add(child, String.format("Element of type %s may not contain a child of type %s", parent.type, child.type));
		}
	}
}
