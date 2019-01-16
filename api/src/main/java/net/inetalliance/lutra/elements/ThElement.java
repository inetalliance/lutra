package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;
import net.inetalliance.lutra.rules.MayHaveAttribute;

import java.util.EnumSet;

public class ThElement extends CommonAbstractElement<ThElement> implements TrElementChild
{

	private static final AttributeRule[] attributeRules =
			{
					new MayHaveAttribute(Attribute.union(Attribute.COMMON,
					                                     EnumSet.of(Attribute.ABBR, Attribute.ALIGN, Attribute.VALIGN,
					                                                Attribute.ROWSPAN, Attribute.COLSPAN, Attribute.AXIS, Attribute.CHAR,
					                                                Attribute.CHAROFF, Attribute.SCOPE)))
			};

	public ThElement(final String text)
	{
		this(new TextContent(text));
	}

	public ThElement(final ThElementChild... children)
	{
		super(ThElement.class, ElementType.TH, ChildRule.ANY_BLOCK_OR_INLINE_OR_TEXT_ELEMENTS, attributeRules, children);
	}

	@Override
	public ThElement copy() {
		return (ThElement) copyWithListeners();
	}

	public final String getAbbr()
	{
		return getAttribute(Attribute.ABBR);
	}

	public final String getAlign()
	{
		return getAttribute(Attribute.ALIGN);
	}

	public final String getAxis()
	{
		return getAttribute(Attribute.AXIS);
	}

	public final String getChar()
	{
		return getAttribute(Attribute.CHAR);
	}

	public final String getCharOff()
	{
		return getAttribute(Attribute.CHAROFF);
	}

	public final String getColSpan()
	{
		return getAttribute(Attribute.COLSPAN);
	}

	public final String getRowSpan()
	{
		return getAttribute(Attribute.ROWSPAN);
	}

	public final String getScope()
	{
		return getAttribute(Attribute.SCOPE);
	}

	public final String getVAlign()
	{
		return getAttribute(Attribute.VALIGN);
	}

	@Override
	protected boolean isClosed()
	{
		return false;
	}

	public final ThElement setAbbr(final String value)
	{
		setAttribute(Attribute.ABBR, value);
		return this;
	}

	public final ThElement setAlign(final String value)
	{
		setAttribute(Attribute.ALIGN, value);
		return this;
	}

	public final ThElement setAxis(final String value)
	{
		setAttribute(Attribute.AXIS, value);
		return this;
	}

	public final ThElement setChar(final String value)
	{
		setAttribute(Attribute.CHAR, value);
		return this;
	}

	public final ThElement setCharOff(final String value)
	{
		setAttribute(Attribute.CHAROFF, value);
		return this;
	}

	public final ThElement setColSpan(final Integer value)
	{
		if (value == null)
			removeAttribute(Attribute.COLSPAN);
		else
			setAttribute(Attribute.COLSPAN, value.toString());
		return this;
	}

	public final ThElement setColSpan(final String value)
	{
		setAttribute(Attribute.COLSPAN, value);
		return this;
	}

	public final ThElement setRowSpan(final Integer value)
	{
		if (value == null)
			removeAttribute(Attribute.ROWSPAN);
		else
			setAttribute(Attribute.ROWSPAN, value.toString());
		return this;
	}

	public final ThElement setRowSpan(final String value)
	{
		setAttribute(Attribute.ROWSPAN, value);
		return this;
	}

	public final ThElement setScope(final String value)
	{
		setAttribute(Attribute.SCOPE, value);
		return this;
	}

	public final ThElement setVAlign(final String value)
	{
		setAttribute(Attribute.VALIGN, value);
		return this;
	}
}