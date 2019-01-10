package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;
import net.inetalliance.lutra.rules.MayHaveAttribute;
import net.inetalliance.lutra.rules.MustHaveAttribute;

import java.util.EnumSet;

import static net.inetalliance.funky.StringFun.secureUrl;


public class ImgElement extends CommonAbstractElement<ImgElement> implements InlineElement
{
	private static final AttributeRule[] attributeRules =
			{
					new MayHaveAttribute(Attribute.union(Attribute.COMMON,
					                                     EnumSet.of(Attribute.ALT, Attribute.HEIGHT, Attribute.WIDTH,
					                                                Attribute.SRC, Attribute.ISMAP, Attribute.LONGDESC,
					                                                Attribute.USEMAP))),
					new MustHaveAttribute(Attribute.SRC),
					new MustHaveAttribute(Attribute.ALT)
			};

	public ImgElement()
	{
		super(ImgElement.class, ElementType.IMG, ChildRule.NONE, attributeRules);
	}

	@Override
	public ImgElement clone() throws CloneNotSupportedException
	{
		return (ImgElement) cloneWithListeners();
	}

	public final String getAlt()
	{
		return getAttribute(Attribute.ALT);
	}

	public final String getHeight()
	{
		return getAttribute(Attribute.HEIGHT);
	}

	public final String getIsMap()
	{
		return getAttribute(Attribute.ISMAP);
	}

	public final String getLongDesc()
	{
		return getAttribute(Attribute.LONGDESC);
	}

	public final String getUseMap()
	{
		return getAttribute(Attribute.USEMAP);
	}

	public final String getWidth()
	{
		return getAttribute(Attribute.WIDTH);
	}

	@Override public void secure()
	{
		setSrc(secureUrl(getSrc()));
	}

	public final ImgElement setSrc(final String value)
	{
		setAttribute(Attribute.SRC, value);
		return this;
	}

	public final String getSrc()
	{
		return getAttribute(Attribute.SRC);
	}

	public final ImgElement setAlt(final String value)
	{
		setAttribute(Attribute.ALT, value);
		return this;
	}

	public final ImgElement setHeight(final String value)
	{
		setAttribute(Attribute.HEIGHT, value);
		return this;
	}

	public final ImgElement setHeight(final int value)
	{
		return setHeight(Integer.toString(value));
	}

	public final ImgElement setIsMap(final String value)
	{
		setAttribute(Attribute.ISMAP, value);
		return this;
	}

	public final ImgElement setLongDesc(final String value)
	{
		setAttribute(Attribute.LONGDESC, value);
		return this;
	}

	public final ImgElement setSrc(final String value, final Object... parameters)
	{
		return setSrc(String.format(value, parameters));
	}

	public final ImgElement setUseMap(final String value)
	{
		setAttribute(Attribute.USEMAP, value);
		return this;
	}

	public final ImgElement setWidth(final int value)
	{
		return setWidth(Integer.toString(value));
	}

	public final ImgElement setWidth(final String value)
	{
		setAttribute(Attribute.WIDTH, value);
		return this;
	}

}