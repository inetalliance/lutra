package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;
import net.inetalliance.lutra.rules.MayHaveAttribute;
import net.inetalliance.lutra.rules.MayHaveChild;

import java.util.EnumSet;

public class TheadElement extends CommonAbstractElement<TheadElement> implements TableElementChild {
	@Override
	public TheadElement copy() {
		return (TheadElement) copyWithListeners();
	}

	private static final ChildRule[] childRules =
		{
			new MayHaveChild(EnumSet.of(ElementType.TR))
		};
	private static final AttributeRule[] attributeRules =
		{
			new MayHaveAttribute(Attribute.union(Attribute.COMMON,
				EnumSet.of(Attribute.ALIGN, Attribute.VALIGN, Attribute.CHAR,
					Attribute.CHAROFF)))
		};

	public TheadElement(final TheadElementChild... children) {
		super(TheadElement.class, ElementType.THEAD, childRules, attributeRules, children);
	}

	@Override
	protected boolean isClosed() {
		return false;
	}

	public final TheadElement setAlign(final String value) {
		setAttribute(Attribute.ALIGN, value);
		return this;
	}

	public final String getAlign() {
		return getAttribute(Attribute.ALIGN);
	}

	public final TheadElement setVAlign(final String value) {
		setAttribute(Attribute.VALIGN, value);
		return this;
	}

	public final String getVAlign() {
		return getAttribute(Attribute.VALIGN);
	}

	public final TheadElement setChar(final String value) {
		setAttribute(Attribute.CHAR, value);
		return this;
	}

	public final String getChar() {
		return getAttribute(Attribute.CHAR);
	}

	public final TheadElement setCharOff(final String value) {
		setAttribute(Attribute.CHAROFF, value);
		return this;
	}

	public final String getCharOff() {
		return getAttribute(Attribute.CHAROFF);
	}
}