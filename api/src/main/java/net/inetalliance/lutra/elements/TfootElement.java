package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;
import net.inetalliance.lutra.rules.MayHaveAttribute;
import net.inetalliance.lutra.rules.MayHaveChild;

import java.util.EnumSet;

public class TfootElement extends CommonAbstractElement<TfootElement> implements TableElementChild
{

	private static final ChildRule[] childRules =
			{
					new MayHaveChild(EnumSet.of(ElementType.TR))
			};
	private static final AttributeRule[] attributeRules =
			{
					new MayHaveAttribute(Attribute.union(Attribute.COMMON,
					                                     EnumSet.of(Attribute.ALIGN, Attribute.VALIGN, Attribute.CHAR,
					                                                Attribute.CHAROFF)))
			};

	public TfootElement(final TfootElementChild... children)
	{
		super(TfootElement.class, ElementType.TFOOT, childRules, attributeRules, children);
	}

	@Override
	protected boolean isClosed()
	{
		return false;
	}

	@Override
	public TfootElement clone() throws CloneNotSupportedException
	{
		return (TfootElement) cloneWithListeners();
	}

	public final String getAlign()
	{
		return getAttribute(Attribute.ALIGN);
	}

	public final String getChar()
	{
		return getAttribute(Attribute.CHAR);
	}

	public final String getCharOff()
	{
		return getAttribute(Attribute.CHAROFF);
	}

	public final String getVAlign()
	{
		return getAttribute(Attribute.VALIGN);
	}

	public final TfootElement setAlign(final String value)
	{
		setAttribute(Attribute.ALIGN, value);
		return this;
	}

	public final TfootElement setChar(final String value)
	{
		setAttribute(Attribute.CHAR, value);
		return this;
	}

	public final TfootElement setCharOff(final String value)
	{
		setAttribute(Attribute.CHAROFF, value);
		return this;
	}

	public final TfootElement setVAlign(final String value)
	{
		setAttribute(Attribute.VALIGN, value);
		return this;
	}
}
