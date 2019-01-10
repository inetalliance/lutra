package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.*;

import java.util.EnumSet;

public class FormElement extends CommonAbstractElement<FormElement> implements BlockElement
{

	private static final ChildRule[] childRules =
			{
					new MayHaveChild(ElementType.blockElements),
					new MayNotHaveDescendant(ElementType.FORM)
			};
	private static final AttributeRule[] attributeRules =
			{
					new MayHaveAttribute(Attribute.union(Attribute.COMMON,
					                                     EnumSet.of(Attribute.ACTION, Attribute.METHOD, Attribute.ACCEPT, Attribute.ACCEPT_CHARSETS,
					                                                Attribute.ENCTYPE, Attribute.ONRESET, Attribute.ONSUBMIT)))
			};

	public FormElement(final FormElementChild... children)
	{
		super(FormElement.class, ElementType.FORM, childRules, attributeRules, children);
	}

	@Override
	public FormElement clone() throws CloneNotSupportedException
	{
		return (FormElement) cloneWithListeners();
	}

	public final String getAccept()
	{
		return getAttribute(Attribute.ACCEPT);
	}

	public final String getAcceptCharsets()
	{
		return getAttribute(Attribute.ACCEPT_CHARSETS);
	}

	public final String getAction()
	{
		return getAttribute(Attribute.ACTION);
	}

	public final String getEncType()
	{
		return getAttribute(Attribute.ENCTYPE);
	}

	public final String getMethod()
	{
		return getAttribute(Attribute.METHOD);
	}

	public final String getOnReset()
	{
		return getAttribute(Attribute.ONRESET);
	}

	public final String getOnSubmit()
	{
		return getAttribute(Attribute.ONSUBMIT);
	}

	public final FormElement setAccept(final String value)
	{
		setAttribute(Attribute.ACCEPT, value);
		return this;
	}

	public final FormElement setAcceptCharsets(final String value)
	{
		setAttribute(Attribute.ACCEPT_CHARSETS, value);
		return this;
	}

	public final FormElement setAction(final String value)
	{
		setAttribute(Attribute.ACTION, value);
		return this;
	}

	public final FormElement setEncType(final String value)
	{
		setAttribute(Attribute.ENCTYPE, value);
		return this;
	}

	public final FormElement setMethod(final String value)
	{
		setAttribute(Attribute.METHOD, value);
		return this;
	}

	public final FormElement setOnReset(final String value)
	{
		setAttribute(Attribute.ONRESET, value);
		return this;
	}

	public final FormElement setOnSubmit(final String value)
	{
		setAttribute(Attribute.ONSUBMIT, value);
		return this;
	}

}