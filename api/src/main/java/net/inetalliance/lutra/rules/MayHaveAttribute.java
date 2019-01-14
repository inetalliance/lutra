package net.inetalliance.lutra.rules;

import net.inetalliance.lutra.elements.Attribute;
import net.inetalliance.lutra.elements.Element;

import java.util.EnumSet;
import java.util.Map;

public class MayHaveAttribute extends AttributeRule
{
	private final EnumSet<Attribute> attributes;

	public MayHaveAttribute(final EnumSet<Attribute> attributes)
	{
		this.attributes = attributes;
	}

	@Override
	public void validate(final Element parent, final Map<Attribute, String> attributes, final ValidationErrors errors, final boolean strict)
	{
		for (final Attribute attribute : attributes.keySet())
		{
			if (!this.attributes.contains(attribute))
				errors.add(parent, String.format("Element of type %s may not contain attribute: %s", parent.elementType, attribute));
		}
	}
}