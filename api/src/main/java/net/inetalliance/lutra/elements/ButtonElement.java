package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;
import net.inetalliance.lutra.rules.MayHaveAttribute;
import net.inetalliance.lutra.rules.MayHaveChild;
import net.inetalliance.lutra.rules.MayNotHaveDescendant;

import java.util.EnumSet;

import static net.inetalliance.lutra.elements.Attribute.*;

public class ButtonElement extends CommonAbstractElement<ButtonElement> implements InlineElement {

	private static final ChildRule[] childRules =
		{
			new MayHaveChild(ElementType.BLOCK_AND_INLINE_AND_TEXT_ELEMENTS),
			new MayNotHaveDescendant(ElementType.FORM, ElementType.FIELDSET, ElementType.BUTTON, ElementType.INPUT,
				ElementType.LABEL, ElementType.SELECT, ElementType.TEXTAREA)
		};
	private static final AttributeRule[] attributeRules =
		{
			new MayHaveAttribute(union(COMMON,
				EnumSet.of(NAME, TYPE, VALUE, ACCESSKEY, DISABLED, ONBLUR, ONFOCUS, TABINDEX)))};

	public ButtonElement(final String text) {
		this(new TextContent(text));
	}

	public ButtonElement(final ButtonElementChild... children) {
		super(ButtonElement.class, ElementType.BUTTON, childRules, attributeRules, children);
	}

	@Override
	public ButtonElement clone() throws CloneNotSupportedException {
		return (ButtonElement) cloneWithListeners();
	}

	public final String getAccessKey() {
		return getAttribute(ACCESSKEY);
	}

	public final String getDisabled() {
		return getAttribute(DISABLED);
	}

	public final String getName() {
		return getAttribute(NAME);
	}

	public final String getOnBlur() {
		return getAttribute(ONBLUR);
	}

	public final String getOnFocus() {
		return getAttribute(ONFOCUS);
	}

	public final String getTabIndex() {
		return getAttribute(TABINDEX);
	}

	public final String getType() {
		return getAttribute(TYPE);
	}

	public final String getValue() {
		return getAttribute(VALUE);
	}

	public final ButtonElement setAccessKey(final String value) {
		setAttribute(ACCESSKEY, value);
		return this;
	}

	public final ButtonElement setDisabled(final String value) {
		setAttribute(DISABLED, value);
		return this;
	}

	public final ButtonElement setName(final String value) {
		setAttribute(NAME, value);
		return this;
	}

	public final ButtonElement setOnBlur(final String value) {
		setAttribute(ONBLUR, value);
		return this;
	}

	public final ButtonElement setOnFocus(final String value) {
		setAttribute(ONFOCUS, value);
		return this;
	}

	public final ButtonElement setTabIndex(final String value) {
		setAttribute(TABINDEX, value);
		return this;
	}

	public final ButtonElement setType(final String value) {
		setAttribute(TYPE, value);
		return this;
	}

	public final ButtonElement setValue(final String value) {
		setAttribute(VALUE, value);
		return this;
	}
}