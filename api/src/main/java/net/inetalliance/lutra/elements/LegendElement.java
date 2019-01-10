package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.MayHaveAttribute;

import java.util.EnumSet;

import static net.inetalliance.lutra.rules.ChildRule.ANY_INLINE_OR_TEXT_ELEMENTS;

public class LegendElement extends CommonAbstractElement<LegendElement> implements FieldsetElementChild {

	private static final AttributeRule[] attributeRules =
		{
			new MayHaveAttribute(Attribute.union(Attribute.COMMON, EnumSet.of(Attribute.ACCESSKEY)))
		};

	public LegendElement(final String text) {
		this(new TextContent(text));
	}

	public LegendElement(final LegendElementChild... children) {
		super(LegendElement.class, ElementType.LEGEND, ANY_INLINE_OR_TEXT_ELEMENTS, attributeRules, children);
	}

	@Override
	public LegendElement clone() throws CloneNotSupportedException {
		return (LegendElement) cloneWithListeners();
	}

	public final String getAccessKey() {
		return getAttribute(Attribute.ACCESSKEY);
	}

	public final LegendElement setAccessKey(final String value) {
		setAttribute(Attribute.ACCESSKEY, value);
		return this;
	}
}