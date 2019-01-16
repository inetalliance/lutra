package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;
import net.inetalliance.lutra.rules.MayHaveAttribute;
import net.inetalliance.lutra.rules.MayHaveChild;

import java.util.EnumSet;

public class OptgroupElement extends CommonAbstractElement<OptgroupElement> implements SelectElementChild
{

	private static final ChildRule[] childRules =
			{
					new MayHaveChild(EnumSet.of(ElementType.OPTION))
			};
	private static final AttributeRule[] attributeRules =
			{
					new MayHaveAttribute(Attribute.union(Attribute.COMMON,
					                                     EnumSet.of(Attribute.LABEL, Attribute.DISABLED)))
			};

	public OptgroupElement(final OptgroupElementChild... children)
	{
		super(OptgroupElement.class, ElementType.OPTGROUP, childRules, attributeRules, children);
	}

	@Override
	public OptgroupElement copy() {
		return (OptgroupElement) copyWithListeners();
	}

	public final String getLabel()
	{
		return getAttribute(Attribute.LABEL);
	}

	public final boolean isDisabled()
	{
		return "disabled".equals(getAttribute(Attribute.DISABLED));
	}

	public final OptgroupElement setDisabled(final boolean value)
	{
		setAttribute(Attribute.DISABLED, value ? "disabled" : null);
		return this;
	}

	public final OptgroupElement setLabel(final String value)
	{
		setAttribute(Attribute.LABEL, value);
		return this;
	}
}