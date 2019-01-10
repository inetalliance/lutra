package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;
import net.inetalliance.lutra.rules.MayHaveAttribute;

import java.util.EnumSet;

public class BodyElement extends CommonAbstractElement<BodyElement> implements HtmlElementChild {

	private static final AttributeRule[] attributeRules =
		{
			new MayHaveAttribute(Attribute.union(Attribute.COMMON, EnumSet.of(Attribute.ONLOAD, Attribute.ONUNLOAD)))
		};

	public BodyElement(final BodyElementChild... children) {
		super(BodyElement.class, ElementType.BODY, ChildRule.ANY_BLOCK_ELEMENTS, attributeRules, children);
	}

	@Override
	public BodyElement clone() throws CloneNotSupportedException {
		return (BodyElement) cloneWithListeners();
	}

	public final String getOnload() {
		return getAttribute(Attribute.ONLOAD);
	}

	public final BodyElement setOnload(final String value) {
		setAttribute(Attribute.ONLOAD, value);
		return this;
	}

	public final String getOnunload() {
		return getAttribute(Attribute.ONUNLOAD);
	}

	public final BodyElement setOnunload(final String value) {
		setAttribute(Attribute.ONUNLOAD, value);
		return this;
	}
}