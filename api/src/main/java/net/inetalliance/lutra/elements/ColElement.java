package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;
import net.inetalliance.lutra.rules.MayHaveAttribute;

import java.util.EnumSet;

public class ColElement extends CommonAbstractElement<ColElement> implements ColgroupElementChild, TableElementChild {

	private static final AttributeRule[] attributeRules =
		{
			new MayHaveAttribute(Attribute.union(Attribute.COMMON,
				EnumSet.of(Attribute.ALIGN, Attribute.SPAN, Attribute.VALIGN,
					Attribute.WIDTH, Attribute.CHAR, Attribute.CHAROFF)))
		};

	public ColElement() {
		super(ColElement.class, ElementType.COL, ChildRule.NONE, attributeRules);
	}

	@Override
	public ColElement copy() {
		return (ColElement) copyWithListeners();
	}

	public final String getAlign() {
		return getAttribute(Attribute.ALIGN);
	}

	public final String getChar() {
		return getAttribute(Attribute.CHAR);
	}

	public final String getCharOff() {
		return getAttribute(Attribute.CHAROFF);
	}

	public final String getSpan() {
		return getAttribute(Attribute.SPAN);
	}

	public final String getVAlign() {
		return getAttribute(Attribute.VALIGN);
	}

	public final String getWidth() {
		return getAttribute(Attribute.WIDTH);
	}

	public final ColElement setAlign(final String value) {
		setAttribute(Attribute.ALIGN, value);
		return this;
	}

	public final ColElement setChar(final String value) {
		setAttribute(Attribute.CHAR, value);
		return this;
	}

	public final ColElement setCharOff(final String value) {
		setAttribute(Attribute.CHAROFF, value);
		return this;
	}

	public final ColElement setSpan(final String value) {
		setAttribute(Attribute.SPAN, value);
		return this;
	}

	public final ColElement setVAlign(final String value) {
		setAttribute(Attribute.VALIGN, value);
		return this;
	}

	public final ColElement setWidth(final String value) {
		setAttribute(Attribute.WIDTH, value);
		return this;
	}
}