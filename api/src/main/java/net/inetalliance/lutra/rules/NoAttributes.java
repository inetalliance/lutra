package net.inetalliance.lutra.rules;

import net.inetalliance.lutra.elements.Attribute;
import net.inetalliance.lutra.elements.Element;

import java.util.Map;

public class NoAttributes extends AttributeRule
{
	public static final NoAttributes INSTANCE = new NoAttributes();

	private NoAttributes()
	{
	}

	@Override
	public void validate(final Element parent, final Map<Attribute, String> attributes, final ValidationErrors errors, final boolean strict)
	{
		if (attributes.size() > 1 ||
		    (strict && !attributes.isEmpty()) ||
		    (!strict && attributes.size() == 1 && attributes.keySet().iterator().next() != Attribute.ID))
		{
			errors.add(parent, String.format("Element of type %s may not have any attributes", parent.type));
		}
	}
}