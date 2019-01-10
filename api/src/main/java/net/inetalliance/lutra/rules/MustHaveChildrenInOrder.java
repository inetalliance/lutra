package net.inetalliance.lutra.rules;

import net.inetalliance.lutra.elements.Element;
import net.inetalliance.lutra.elements.ElementType;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class MustHaveChildrenInOrder extends ChildRule
{
	private final ElementType[] childTypes;

	public MustHaveChildrenInOrder(final ElementType... childTypes)
	{
		this.childTypes = childTypes;
	}

	@Override
	public void validate(final Element parent, final Collection<Element> children, final ValidationErrors errors, final boolean strict)
	{
		final Iterator<Element> childIterator = children.iterator();
		for (final ElementType type : childTypes)
		{
			if (!childIterator.hasNext() || childIterator.next().type != type)
			{
				errors.add(parent, String.format("Element of type %s must contain the following children, in order: %s", parent.type, Arrays.toString(childTypes)));
				break;
			}
		}
	}
}