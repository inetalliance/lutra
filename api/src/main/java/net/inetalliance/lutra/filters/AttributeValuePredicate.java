package net.inetalliance.lutra.filters;

import net.inetalliance.lutra.elements.Attribute;

public class AttributeValuePredicate extends AttributePredicate
{
	private final String value;

	public AttributeValuePredicate(final Attribute attribute, final String value)
	{
		super(attribute);
		this.value = value;
	}

	@Override
	public boolean $(final String value)
	{
		return value != null && value.equals(this.value);
	}
}