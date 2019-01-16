package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;
import net.inetalliance.lutra.rules.MayHaveAttribute;
import net.inetalliance.lutra.rules.MayHaveChild;

import java.util.EnumSet;

import static net.inetalliance.lutra.elements.Attribute.*;

public class ColgroupElement extends CommonAbstractElement<ColgroupElement> implements TableElementChild {

	private static final ChildRule[] childRules =
		{
			new MayHaveChild(EnumSet.of(ElementType.COL))
		};
	private static final AttributeRule[] attributeRules =
		{
			new MayHaveAttribute(union(COMMON, EnumSet.of(ALIGN, SPAN, VALIGN, WIDTH, CHAR, CHAROFF)))
		};

	public ColgroupElement(final ColgroupElementChild... children) {
		super(ColgroupElement.class, ElementType.COLGROUP, childRules, attributeRules, children);
	}

	@Override
	public ColgroupElement copy() {
		return (ColgroupElement) copyWithListeners();
	}

	public final String getAlign() {
		return getAttribute(ALIGN);
	}

	public final String getChar() {
		return getAttribute(CHAR);
	}

	public final String getCharOff() {
		return getAttribute(CHAROFF);
	}

	public final String getSpan() {
		return getAttribute(SPAN);
	}

	public final String getVAlign() {
		return getAttribute(VALIGN);
	}

	public final String getWidth() {
		return getAttribute(WIDTH);
	}

	public final ColgroupElement setAlign(final String value) {
		setAttribute(ALIGN, value);
		return this;
	}

	public final ColgroupElement setChar(final String value) {
		setAttribute(CHAR, value);
		return this;
	}

	public final ColgroupElement setCharOff(final String value) {
		setAttribute(CHAROFF, value);
		return this;
	}

	public final ColgroupElement setSpan(final String value) {
		setAttribute(SPAN, value);
		return this;
	}

	public final ColgroupElement setVAlign(final String value) {
		setAttribute(VALIGN, value);
		return this;
	}

	public final ColgroupElement setWidth(final String value) {
		setAttribute(WIDTH, value);
		return this;
	}
}