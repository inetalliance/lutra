package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;
import net.inetalliance.lutra.rules.MayHaveAttribute;

import java.util.EnumSet;

import static net.inetalliance.lutra.elements.Attribute.*;

public class TextareaElement
	extends CommonFormElement<TextareaElement>
	implements InlineElement {

	private static final AttributeRule[] attributeRules =
		{
			new MayHaveAttribute(union(COMMON,
				EnumSet.of(COLS, ROWS, NAME, ACCESSKEY, DISABLED, ONBLUR, ONCHANGE, ONFOCUS, ONSELECT,
					PLACEHOLDER, READONLY, TABINDEX, AUTOCORRECT, AUTOCAPITALIZE, AUTOCOMPLETE, SPELLCHECK, REQUIRED)))
		};

	public TextareaElement(final String text) {
		this(new TextContent(text));
	}

	public TextareaElement(final TextareaElementChild... children) {
		super(TextareaElement.class, ElementType.TEXTAREA, ChildRule.TEXT, attributeRules, children);
	}

	@SuppressWarnings({"CloneDoesntCallSuperClone"})
	@Override
	public TextareaElement copy() {
		return (TextareaElement) copyWithListeners();
	}

	public final String getAccessKey() {
		return getAttribute(Attribute.ACCESSKEY);
	}

	public final void setValue(final String value) {
		// this method is here just to make TextareaElement seem a little more like InputElement
		setText(value);
	}

	public final String getCols() {
		return getAttribute(Attribute.COLS);
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

	public final String getRows() {
		return getAttribute(Attribute.ROWS);
	}

	public final String getTabIndex() {
		return getAttribute(Attribute.TABINDEX);
	}

	@Override
	protected boolean isClosed() {
		return false;
	}

	public final boolean isDisabled() {
		return "disabled".equals(getAttribute(Attribute.DISABLED));
	}

	public final boolean isReadOnly() {
		return "readonly".equals(getAttribute(Attribute.READONLY));
	}

	public final TextareaElement setAccessKey(final String value) {
		setAttribute(Attribute.ACCESSKEY, value);
		return this;
	}

	public final TextareaElement setCols(final String value) {
		setAttribute(Attribute.COLS, value);
		return this;
	}

	public final TextareaElement setDisabled(final boolean value) {
		setAttribute(Attribute.DISABLED, value ? "disabled" : null);
		return this;
	}

	public final TextareaElement setName(final String value) {
		setAttribute(Attribute.NAME, value);
		return this;
	}

	public final TextareaElement setOnBlur(final String value) {
		setAttribute(Attribute.ONBLUR, value);
		return this;
	}

	public final TextareaElement setOnChange(final String value) {
		setAttribute(Attribute.ONCHANGE, value);
		return this;
	}

	public final TextareaElement setOnFocus(final String value) {
		setAttribute(Attribute.ONFOCUS, value);
		return this;
	}

	public final TextareaElement setOnSelect(final String value) {
		setAttribute(Attribute.ONSELECT, value);
		return this;
	}

	public final TextareaElement setReadOnly(final boolean value) {
		setAttribute(Attribute.READONLY, value ? "readonly" : null);
		return this;
	}

	public final TextareaElement setRows(final String value) {
		setAttribute(Attribute.ROWS, value);
		return this;
	}

	public final TextareaElement setTabIndex(final String value) {
		setAttribute(Attribute.TABINDEX, value);
		return this;
	}

	@Override
	public void setFormValue(final String value) {
		setText(value);
	}
}