package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;
import net.inetalliance.lutra.rules.MayHaveAttribute;
import net.inetalliance.lutra.rules.MayHaveChild;

import java.util.EnumSet;

public class TrElement extends CommonAbstractElement<TrElement>
	implements TableElementChild, TbodyElementChild, TheadElementChild, TfootElementChild {

	private static final ChildRule[] childRules =
		{
			new MayHaveChild(EnumSet.of(ElementType.TH, ElementType.TD))
		};
	private static final AttributeRule[] attributeRules =
		{
			new MayHaveAttribute(Attribute.union(Attribute.COMMON,
				EnumSet.of(Attribute.ALIGN, Attribute.VALIGN, Attribute.CHAR,
					Attribute.CHAROFF)))
		};

	public TrElement(final TrElementChild... children) {
		super(TrElement.class, ElementType.TR, childRules, attributeRules, children);
	}

	@Override
	public TrElement copy() {
		return (TrElement) copyWithListeners();
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

	public final String getVAlign() {
		return getAttribute(Attribute.VALIGN);
	}

	public final TrElement setAlign(final String value) {
		setAttribute(Attribute.ALIGN, value);
		return this;
	}

	public final TrElement setChar(final String value) {
		setAttribute(Attribute.CHAR, value);
		return this;
	}

	public final TrElement setCharOff(final String value) {
		setAttribute(Attribute.CHAROFF, value);
		return this;
	}

	public final TrElement setVAlign(final String value) {
		setAttribute(Attribute.VALIGN, value);
		return this;
	}
}