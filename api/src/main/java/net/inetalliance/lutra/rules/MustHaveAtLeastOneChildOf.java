package net.inetalliance.lutra.rules;

import net.inetalliance.lutra.elements.Element;
import net.inetalliance.lutra.elements.ElementType;

import java.util.Collection;

public class MustHaveAtLeastOneChildOf extends ChildRule
{
	private final ElementType[] childTypes;

	public MustHaveAtLeastOneChildOf(final ElementType... childTypes)
	{
		this.childTypes = childTypes;
	}

	@Override
	public void validate(final Element parent, final Collection<Element> children, final ValidationErrors errors, final boolean strict)
	{
		for (final Element child : children)
		{
			for (final ElementType type : childTypes)
			{
				if (child.type == type)
				{
					return;
				}
			}
		}
		errors.add(parent, String.format("Element of type %s must contain at least one of: %s", parent.type, childTypesToString()));
	}

	private String childTypesToString()
	{
		final StringBuilder string = new StringBuilder(128);
		for (final ElementType type : childTypes)
		{
			if (string.length() > 0)
				string.append(", ");
			string.append(type);
		}
		return string.toString();
	}
}