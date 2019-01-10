package net.inetalliance.lutra.rules;

import net.inetalliance.lutra.elements.Attribute;
import net.inetalliance.lutra.elements.Element;

import java.util.Map;

public abstract class AttributeRule
{
	public static final AttributeRule[] NONE = new AttributeRule[]{NoAttributes.INSTANCE};
	public static final AttributeRule[] ANY_COMMON_ATTRIBUTES = {new MayHaveAttribute(Attribute.COMMON)};

	public abstract void validate(final Element parent, final Map<Attribute, String> attributes, final ValidationErrors errors, final boolean strict);
}