package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;
import net.inetalliance.lutra.rules.MayHaveAttribute;

import java.util.EnumSet;

import static net.inetalliance.lutra.elements.Attribute.*;

public class InputElement
	extends CommonFormElement<InputElement>
	implements InlineElement {

	private static final AttributeRule[] attributeRules =
		{
			new MayHaveAttribute(union(COMMON,
				EnumSet.of(ALT, CHECKED, MAXLENGTH, NAME, SIZE, TYPE, VALUE, ACCESSKEY, DISABLED, ISMAP, ONBLUR, ONCHANGE,
					ONFOCUS, ONSELECT, READONLY, SRC, TABINDEX, USEMAP, PLACEHOLDER, AUTOCAPITALIZE, AUTOCOMPLETE,
					AUTOCORRECT, SPELLCHECK, REQUIRED, PATTERN)))
		};

	public InputElement() {
		super(InputElement.class, ElementType.INPUT, ChildRule.NONE, attributeRules);
	}

	@Override
	public InputElement copy() {
		return (InputElement) copyWithListeners();
	}

	public boolean isRadio() {
		return "radio".equalsIgnoreCase(getType());
	}

	public boolean isCheckbox() {
		return "checkbox".equalsIgnoreCase(getType());
	}

	public boolean isText() {
		return "text".equalsIgnoreCase(getType());
	}

	public final String getAccept() {
		return getAttribute(Attribute.ACCEPT);
	}

	public final String getPlaceHolder() {
		return getAttribute(PLACEHOLDER);
	}

	public final String getAccessKey() {
		return getAttribute(Attribute.ACCESSKEY);
	}

	public final String getAlt() {
		return getAttribute(Attribute.ALT);
	}

	public final String getMaxLength() {
		return getAttribute(Attribute.MAXLENGTH);
	}

	public final String getName() {
		return getAttribute(Attribute.NAME);
	}

	public final String getOnBlur() {
		return getAttribute(Attribute.ONBLUR);
	}

	public final String getOnChange() {
		return getAttribute(Attribute.ONCHANGE);
	}

	public final String getOnFocus() {
		return getAttribute(Attribute.ONFOCUS);
	}

	public final String getOnSelect() {
		return getAttribute(Attribute.ONSELECT);
	}

	public final String getSize() {
		return getAttribute(Attribute.SIZE);
	}

	public final String getSrc() {
		return getAttribute(SRC);
	}

	public final String getTabIndex() {
		return getAttribute(Attribute.TABINDEX);
	}

	public final String getType() {
		return getAttribute(Attribute.TYPE);
	}

	public final String getUseMap() {
		return getAttribute(Attribute.USEMAP);
	}

	public final String getValue() {
		return getAttribute(Attribute.VALUE);
	}

	public final boolean isChecked() {
		return "checked".equals(getAttribute(Attribute.CHECKED));
	}

	public final boolean isDisabled() {
		return "disabled".equals(getAttribute(Attribute.DISABLED));
	}

	public final boolean isMap() {
		return "ismap".equals(getAttribute(Attribute.ISMAP));
	}

	public final boolean isReadOnly() {
		return "readonly".equals(getAttribute(Attribute.READONLY));
	}

	public final InputElement setAccept(final String value) {
		setAttribute(Attribute.ACCEPT, value);
		return this;
	}

	public final InputElement setAccessKey(final String value) {
		setAttribute(Attribute.ACCESSKEY, value);
		return this;
	}

	public final InputElement setAlt(final String value) {
		setAttribute(Attribute.ALT, value);
		return this;
	}

	public final InputElement setPlaceHolder(final String value) {
		setAttribute(PLACEHOLDER, value);
		return this;
	}

	public final InputElement setChecked(final boolean value) {
		setAttribute(Attribute.CHECKED, value ? "checked" : null);
		return this;
	}

	public final InputElement setDisabled(final boolean value) {
		setAttribute(Attribute.DISABLED, value ? "disabled" : null);
		return this;
	}

	public final InputElement setIsMap(final boolean value) {
		setAttribute(Attribute.ISMAP, value ? "ismap" : null);
		return this;
	}

	public final InputElement setMaxLength(final String value) {
		setAttribute(Attribute.MAXLENGTH, value);
		return this;
	}

	public final InputElement setName(final String value) {
		setAttribute(Attribute.NAME, value);
		return this;
	}

	public final InputElement setOnBlur(final String value) {
		setAttribute(Attribute.ONBLUR, value);
		return this;
	}

	public final InputElement setOnChange(final String value) {
		setAttribute(Attribute.ONCHANGE, value);
		return this;
	}

	public final InputElement setOnFocus(final String value) {
		setAttribute(Attribute.ONFOCUS, value);
		return this;
	}

	public final InputElement setOnSelect(final String value) {
		setAttribute(Attribute.ONSELECT, value);
		return this;
	}

	public final InputElement setReadOnly(final boolean value) {
		setAttribute(Attribute.READONLY, value ? "readonly" : null);
		return this;
	}

	public final InputElement setSize(final String value) {
		setAttribute(Attribute.SIZE, value);
		return this;
	}

	public final InputElement setSrc(final String value) {
		setAttribute(SRC, value);
		return this;
	}

	public final InputElement setTabIndex(final String value) {
		setAttribute(Attribute.TABINDEX, value);
		return this;
	}

	public final InputElement setType(final String value) {
		setAttribute(Attribute.TYPE, value);
		return this;
	}

	public final InputElement setUseMap(final String value) {
		setAttribute(Attribute.USEMAP, value);
		return this;
	}

	public final InputElement setValue(final String value, final Object... params) {
		return setValue(String.format(value, params));
	}

	public final InputElement setValue(final String value) {
		setAttribute(Attribute.VALUE, value);
		return this;
	}

	@Override
	public void setFormValue(final String value) {
		if (isCheckbox()) {
			setChecked(value != null);
		} else {
			setValue(value);
		}
	}

}