package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;
import net.inetalliance.lutra.rules.MayHaveAttribute;

import java.util.EnumSet;

public class OptionElement extends CommonAbstractElement<OptionElement> implements OptgroupElementChild, SelectElementChild {


	private static final AttributeRule[] attributeRules =
		{
			new MayHaveAttribute(Attribute.union(Attribute.COMMON,
				EnumSet.of(Attribute.SELECTED, Attribute.VALUE, Attribute.DISABLED, Attribute.LABEL)))
		};

	public OptionElement(final String text) {
		this(new TextContent(text));
	}

	public OptionElement(final OptionElementChild... children) {
		super(OptionElement.class, ElementType.OPTION, ChildRule.TEXT, attributeRules, children);
	}

	@Override
	public OptionElement clone() throws CloneNotSupportedException {
		return (OptionElement) cloneWithListeners();
	}

	public final String getLabel() {
		return getAttribute(Attribute.LABEL);
	}

	public final String getValue() {
		return getAttribute(Attribute.VALUE);
	}

	public final boolean isDisabled() {
		return "disabled".equals(getAttribute(Attribute.DISABLED));
	}

	public final boolean isSelected() {
		return "selected".equals(getAttribute(Attribute.SELECTED));
	}

	public final OptionElement setDisabled(final boolean value) {
		setAttribute(Attribute.DISABLED, value ? "disabled" : null);
		return this;
	}

	public final OptionElement setLabel(final String value) {
		setAttribute(Attribute.LABEL, value);
		return this;
	}

	public final OptionElement setSelected(final boolean value) {
		setAttribute(Attribute.SELECTED, value ? "selected" : null);
		return this;
	}

	public final OptionElement setValue(final String value) {
		setAttribute(Attribute.VALUE, value);
		return this;
	}

	@Override
	protected boolean isClosed() {
		return false;
	}
}