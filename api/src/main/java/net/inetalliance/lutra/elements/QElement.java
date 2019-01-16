package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;
import net.inetalliance.lutra.rules.MayHaveAttribute;

import java.util.EnumSet;

public class QElement extends CommonAbstractElement<QElement> implements InlineElement {

	private static final AttributeRule[] attributeRules =
		{
			new MayHaveAttribute(Attribute.union(Attribute.COMMON, EnumSet.of(Attribute.CITE)))
		};

	public QElement(final QElementChild... children) {
		super(QElement.class, ElementType.Q, ChildRule.ANY_INLINE_ELEMENTS, attributeRules, children);
	}

	@Override
	public QElement copy() {
		return (QElement) copyWithListeners();
	}

	public final String getCite() {
		return getAttribute(Attribute.CITE);
	}

	public final QElement setCite(final String value) {
		setAttribute(Attribute.CITE, value);
		return this;
	}
}