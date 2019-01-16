package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.*;

import java.util.EnumSet;

public class AElement extends CommonAbstractElement<AElement> implements InlineElement {
	private static final ChildRule[] childRules =
		{
			new MayHaveChild(ElementType.INLINE_AND_TEXT_ELEMENTS),
			new MayNotHaveDescendant(ElementType.A)
		};
	private static final AttributeRule[] attributeRules =
		{
			new MayHaveAttribute(Attribute.union(Attribute.COMMON,
				EnumSet.of(Attribute.HREF, Attribute.ACCESSKEY, Attribute.CHARSET,
					Attribute.ONBLUR, Attribute.ONFOCUS, Attribute.REL,
					Attribute.SHAPE, Attribute.TABINDEX, Attribute.TYPE)))
			// leaving out hreflang and rev
		};

	public AElement(final String text) {
		this(new TextContent(text));
	}

	public AElement(final AElementChild... children) {
		super(AElement.class, ElementType.A, childRules, attributeRules, children);
	}

	@Override
	public AElement copy() {
		return (AElement) copyWithListeners();
	}

	public final void disable() {
		removeAttribute(Attribute.HREF);
		removeAttribute(Attribute.ONCLICK);
	}

	public final String getAccessKey() {
		return getAttribute(Attribute.ACCESSKEY);
	}

	public final String getCharSet() {
		return getAttribute(Attribute.CHARSET);
	}

	public final String getHref() {
		return getAttribute(Attribute.HREF);
	}

	public final String getOnBlur() {
		return getAttribute(Attribute.ONBLUR);
	}

	public final String getOnFocus() {
		return getAttribute(Attribute.ONFOCUS);
	}

	public final String getRel() {
		return getAttribute(Attribute.REL);
	}

	public final String getShape() {
		return getAttribute(Attribute.SHAPE);
	}

	public final String getTabIndex() {
		return getAttribute(Attribute.TABINDEX);
	}

	public final String getType() {
		return getAttribute(Attribute.TYPE);
	}

	@Override
	protected boolean isClosed() {
		return false;
	}

	public final AElement setAccessKey(final String value) {
		setAttribute(Attribute.ACCESSKEY, value);
		return this;
	}

	public final AElement setCharSet(final String value) {
		setAttribute(Attribute.CHARSET, value);
		return this;
	}

	public final AElement setHref(final String value) {
		setAttribute(Attribute.HREF, value);
		return this;
	}

	public final AElement setHref(final String value, final Object... parameters) {
		return setHref(parameters.length > 0 ? String.format(value, parameters) : value);
	}

	public final AElement setOnBlur(final String value) {
		setAttribute(Attribute.ONBLUR, value);
		return this;
	}

	public final AElement setOnFocus(final String value) {
		setAttribute(Attribute.ONFOCUS, value);
		return this;
	}

	public final AElement setRel(final String value) {
		setAttribute(Attribute.REL, value);
		return this;
	}

	public final AElement setShape(final String value) {
		setAttribute(Attribute.SHAPE, value);
		return this;
	}

	public final AElement setTabIndex(final String value) {
		setAttribute(Attribute.TABINDEX, value);
		return this;
	}

	public final AElement setTitle(final String value, final Object... parameters) {
		return setTitle(parameters.length > 0 ? String.format(value, parameters) : value);
	}

	public final AElement setType(final String value) {
		setAttribute(Attribute.TYPE, value);
		return this;
	}

}