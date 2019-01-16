package net.inetalliance.lutra.elements;

import net.inetalliance.funky.StringFun;
import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;
import net.inetalliance.lutra.rules.MayHaveAttribute;
import net.inetalliance.lutra.rules.MustHaveAttribute;

import java.util.EnumSet;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

public class AreaElement extends Element implements MapElementChild {

	private static final AttributeRule[] attributeRules =
		{
			new MayHaveAttribute(Attribute.union(Attribute.COMMON,
				EnumSet.of(Attribute.ALT, Attribute.COORDS, Attribute.HREF,
					Attribute.SHAPE, Attribute.ACCESSKEY, Attribute.ONBLUR,
					Attribute.ONFOCUS, Attribute.NOHREF, Attribute.TABINDEX))),
			new MustHaveAttribute(Attribute.ALT)
		};

	public AreaElement() {
		super(ElementType.AREA, ChildRule.NONE, attributeRules);
	}

	@Override
	public AreaElement copy() {
		return (AreaElement) copyWithListeners();
	}

	public final String getAccessKey() {
		return getAttribute(Attribute.ACCESSKEY);
	}

	public final String getAlt() {
		return getAttribute(Attribute.ALT);
	}

	public final String getCoords() {
		return getAttribute(Attribute.COORDS);
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

	public final String getShape() {
		return getAttribute(Attribute.SHAPE);
	}

	public final String getTabIndex() {
		return getAttribute(Attribute.TABINDEX);
	}

	public final boolean isNoHref() {
		return "nohref".equals(getAttribute(Attribute.NOHREF));
	}

	public final AreaElement setAccessKey(final String value) {
		setAttribute(Attribute.ACCESSKEY, value);
		return this;
	}

	public final AreaElement setAlt(final String value) {
		setAttribute(Attribute.ALT, value);
		return this;
	}

	@Override
	public AreaElement setClass(final String value) {
		setAttribute(Attribute.CLASS, value);
		return this;
	}

	@Override
	public AreaElement setClass(final Enum<?>... cssClasses) {
		return setClass(stream(cssClasses).map(StringFun::enumToCamelCase).collect(joining(" ")));
	}

	public final AreaElement setCoords(final String value) {
		setAttribute(Attribute.COORDS, value);
		return this;
	}

	public final AreaElement setHref(final String value) {
		setAttribute(Attribute.HREF, value);
		return this;
	}

	@Override
	public AreaElement setId(final String value) {
		setAttribute(Attribute.ID, value);
		return this;
	}

	public final AreaElement setNoHref(final boolean value) {
		setAttribute(Attribute.NOHREF, value ? "nohref" : null);
		return this;
	}

	public final AreaElement setOnBlur(final String value) {
		setAttribute(Attribute.ONBLUR, value);
		return this;
	}

	public final AreaElement setOnFocus(final String value) {
		setAttribute(Attribute.ONFOCUS, value);
		return this;
	}

	public final AreaElement setShape(final String value) {
		setAttribute(Attribute.SHAPE, value);
		return this;
	}

	public final AreaElement setTabIndex(final String value) {
		setAttribute(Attribute.TABINDEX, value);
		return this;
	}

	@Override
	public AreaElement setText(final String text) {
		setTextContent(text);
		return this;
	}
}