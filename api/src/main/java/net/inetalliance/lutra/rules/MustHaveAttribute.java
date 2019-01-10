package net.inetalliance.lutra.rules;

import net.inetalliance.lutra.elements.Attribute;
import net.inetalliance.lutra.elements.Element;

import java.util.Map;

public class MustHaveAttribute extends AttributeRule
{
	private final Attribute attribute;

	public MustHaveAttribute(final Attribute attribute)
	{
		this.attribute = attribute;
	}

	@Override
	public void validate(final Element parent, final Map<Attribute, String> attributes, final ValidationErrors errors, final boolean strict)
	{
		if (!attributes.containsKey(attribute))
			errors.add(parent, String.format("Element of type %s must have attribute: %s", parent.type, attribute));
	}
}