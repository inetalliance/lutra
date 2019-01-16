package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;

public class HrElement extends CommonAbstractElement<HrElement>
{

	public HrElement()
	{
		super(HrElement.class, ElementType.HR, ChildRule.NONE, AttributeRule.ANY_COMMON_ATTRIBUTES);
	}

	@Override
	public HrElement copy() {
		return (HrElement) copyWithListeners();
	}
}