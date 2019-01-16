package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;
import net.inetalliance.lutra.rules.MayHaveAttribute;

import java.util.EnumSet;

public class ParamElement extends Element implements ObjectElementChild, IframeElementChild {

	private static final AttributeRule[] attributeRules =
		{
			new MayHaveAttribute(EnumSet.of(Attribute.NAME, Attribute.VALUE, Attribute.ID, Attribute.TYPE,
				Attribute.VALUETYPE))
		};

	public ParamElement() {
		super(ElementType.PARAM, ChildRule.NONE, attributeRules);
	}

	@Override
	public ParamElement copy() {
		return (ParamElement) copyWithListeners();
	}

	public final String getName() {
		return getAttribute(Attribute.NAME);
	}

	public final String getType() {
		return getAttribute(Attribute.TYPE);
	}

	public final String getValue() {
		return getAttribute(Attribute.VALUE);
	}

	public final String getValueType() {
		return getAttribute(Attribute.VALUETYPE);
	}

	@Override
	public ParamElement setClass(final String value) {
		return this;  // do nothing. classes not allowed on this type
	}

	@Override
	public ParamElement setClass(final Enum<?>... cssClasses) {
		return this;  // do nothing. classes not allowed on this type
	}

	@Override
	public ParamElement setId(final String value) {
		setAttribute(Attribute.ID, value);
		return this;
	}

	public final ParamElement setName(final String value) {
		setAttribute(Attribute.NAME, value);
		return this;
	}

	@Override
	public ParamElement setText(final String text) {
		setTextContent(text);
		return this;
	}

	public final ParamElement setType(final String value) {
		setAttribute(Attribute.TYPE, value);
		return this;
	}

	public final ParamElement setValue(final String value) {
		setAttribute(Attribute.VALUE, value);
		return this;
	}

	public final ParamElement setValueType(final String value) {
		setAttribute(Attribute.VALUETYPE, value);
		return this;
	}
}