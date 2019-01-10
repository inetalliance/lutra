package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;

public class TitleElement extends Element implements HeadElementChild
{

	public TitleElement(final String text)
	{
		this(new TextContent(text));
	}

	public TitleElement(final TitleElementChild... children)
	{
		super(ElementType.TITLE, ChildRule.TEXT, AttributeRule.NONE, children);
	}

	@Override
	public TitleElement clone() throws CloneNotSupportedException
	{
		return (TitleElement) cloneWithListeners();
	}

	@Override
	public TitleElement setClass(final String value)
	{
		return this;  // do nothing. classes not allowed on this type
	}

	@Override
	public TitleElement setClass(final Enum<?>... cssClasses)
	{
		return this;  // do nothing. classes not allowed on this type
	}

	@Override
	public TitleElement setId(final String value)
	{
		setAttribute(Attribute.ID, value);
		return this;
	}

	@Override protected boolean isClosed()
	{
		return false;
	}

	@Override
	public TitleElement setText(final String text)
	{
		setTextContent(text);
		return this;
	}
}