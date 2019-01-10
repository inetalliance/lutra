package net.inetalliance.lutra.filters;

import net.inetalliance.lutra.elements.Attribute;

import java.util.regex.Pattern;

public class AttributePatternPredicate extends AttributePredicate
{
	protected final Pattern pattern;

	public AttributePatternPredicate(final Attribute attribute, final String pattern)
	{
		this(attribute, Pattern.compile(pattern));
	}

	public AttributePatternPredicate(final Attribute attribute, final Pattern pattern)
	{
		super(attribute);
		this.pattern = pattern;
	}

	@Override
	public boolean $(final String value)
	{
		return value != null && pattern.matcher(value).matches();
	}

	public Pattern getPattern()
	{
		return pattern;
	}
}
