package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;

public abstract class CommonFormElement<T extends CommonFormElement<T>> extends CommonAbstractElement<T>
{
	protected CommonFormElement(final Class<T> concreteType, final ElementType type, final ChildRule[] childRules,
	                            final AttributeRule[] attributeRules, final Child... children)
	{
		super(concreteType, type, childRules, attributeRules, children);
	}

	public abstract void setFormValue(final String value);

	public abstract T setName(final String name);

}

