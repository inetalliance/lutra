package net.inetalliance.lutra.elements;

import net.inetalliance.funky.StringFun;
import net.inetalliance.lutra.MicrodataType;
import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Pattern;

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

public abstract class CommonAbstractElement<T extends CommonAbstractElement<T>> extends Element {
	private static final Pattern SPACE = Pattern.compile(" +");

	private final Class<T> concreteType;

	protected CommonAbstractElement(final Class<T> concreteType, final ElementType type, final ChildRule[] childRules,
	                                final AttributeRule[] attributeRules, final Child... children) {
		super(type, childRules, attributeRules, children);
		this.concreteType = concreteType;
	}

	@Override
	public abstract T clone()
		throws CloneNotSupportedException;

	@Override
	public T addClass(final String... cssClasses) {
		return addClass(concreteType.cast(this), cssClasses);
	}

	@Override
	public final String[] getClasses() {
		return getClasses(this);
	}

	public final String getItemProp() {
		return getAttribute(Attribute.ITEMPROP);
	}

	public final String getItemType() {
		return getAttribute(Attribute.ITEMTYPE);
	}

	public final String getOnClick() {
		return getAttribute(Attribute.ONCLICK);
	}

	public final String getOnDblClick() {
		return getAttribute(Attribute.ONDBLCLICK);
	}

	public final String getOnKeyDown() {
		return getAttribute(Attribute.ONKEYDOWN);
	}

	public final String getOnKeyPress() {
		return getAttribute(Attribute.ONKEYPRESS);
	}

	public final String getOnKeyUp() {
		return getAttribute(Attribute.ONKEYUP);
	}

	public final String getOnMouseDown() {
		return getAttribute(Attribute.ONMOUSEDOWN);
	}

	public final String getOnMouseMove() {
		return getAttribute(Attribute.ONMOUSEMOVE);
	}

	public final String getOnMouseOut() {
		return getAttribute(Attribute.ONMOUSEOUT);
	}

	public final String getOnMouseOver() {
		return getAttribute(Attribute.ONMOUSEOVER);
	}

	public final String getOnMouseUp() {
		return getAttribute(Attribute.ONMOUSEUP);
	}

	public final String getTitle() {
		return getAttribute(Attribute.TITLE);
	}

	@Override
	public Element removeClass(final String... cssClasses) {
		return removeClass(this, cssClasses);
	}

	@Override
	public Collection<String> removeClass(final Pattern pattern) {
		return removeClass(this, pattern);
	}

	@Override
	public T setClass(final String value) {
		return setClass(concreteType.cast(this), value);
	}

	@Override
	public T setClass(final Enum<?>... cssClasses) {
		return setClass(concreteType.cast(this), cssClasses);
	}

	@Override
	public T setId(final String value) {
		setAttribute(Attribute.ID, value);
		return concreteType.cast(this);
	}

	public final T setItemProp(final String value) {
		setAttribute(Attribute.ITEMPROP, value);
		return concreteType.cast(this);
	}

	public final T setItemType(final MicrodataType value) {
		setAttribute(Attribute.ITEMTYPE, value.toString());
		return concreteType.cast(this);
	}

	public final T setOnClick(final String value) {
		setAttribute(Attribute.ONCLICK, value);
		return concreteType.cast(this);
	}

	public final T setOnClick(final String value, final Object... params) {
		setAttribute(Attribute.ONCLICK, String.format(value, params));
		return concreteType.cast(this);
	}

	public final T setOnDblClick(final String value) {
		setAttribute(Attribute.ONDBLCLICK, value);
		return concreteType.cast(this);
	}

	public final T setOnDblClick(final String value, final Object... params) {
		setAttribute(Attribute.ONDBLCLICK, String.format(value, params));
		return concreteType.cast(this);
	}

	public final T setOnKeyDown(final String value) {
		setAttribute(Attribute.ONKEYDOWN, value);
		return concreteType.cast(this);
	}

	public final T setOnKeyDown(final String value, final Object... params) {
		setAttribute(Attribute.ONKEYDOWN, String.format(value, params));
		return concreteType.cast(this);
	}

	public final T setOnKeyPress(final String value) {
		setAttribute(Attribute.ONKEYPRESS, value);
		return concreteType.cast(this);
	}

	public final T setOnKeyPress(final String value, final Object... params) {
		setAttribute(Attribute.ONKEYPRESS, String.format(value, params));
		return concreteType.cast(this);
	}

	public final T setOnKeyUp(final String value) {
		setAttribute(Attribute.ONKEYUP, value);
		return concreteType.cast(this);
	}

	public final T setOnKeyUp(final String value, final Object... params) {
		setAttribute(Attribute.ONKEYUP, String.format(value, params));
		return concreteType.cast(this);
	}

	public final T setOnMouseDown(final String value) {
		setAttribute(Attribute.ONMOUSEDOWN, value);
		return concreteType.cast(this);
	}

	public final T setOnMouseDown(final String value, final Object... params) {
		setAttribute(Attribute.ONMOUSEDOWN, String.format(value, params));
		return concreteType.cast(this);
	}

	public final T setOnMouseMove(final String value) {
		setAttribute(Attribute.ONMOUSEMOVE, value);
		return concreteType.cast(this);
	}

	public final T setOnMouseMove(final String value, final Object... params) {
		setAttribute(Attribute.ONMOUSEMOVE, String.format(value, params));
		return concreteType.cast(this);
	}

	public final T setOnMouseOut(final String value) {
		setAttribute(Attribute.ONMOUSEOUT, value);
		return concreteType.cast(this);
	}

	public final T setOnMouseOut(final String value, final Object... params) {
		setAttribute(Attribute.ONMOUSEOUT, String.format(value, params));
		return concreteType.cast(this);
	}

	public final T setOnMouseOver(final String value) {
		setAttribute(Attribute.ONMOUSEOVER, value);
		return concreteType.cast(this);
	}

	public final T setOnMouseOver(final String value, final Object... params) {
		setAttribute(Attribute.ONMOUSEOVER, String.format(value, params));
		return concreteType.cast(this);
	}

	public final T setOnMouseUp(final String value) {
		setAttribute(Attribute.ONMOUSEUP, value);
		return concreteType.cast(this);
	}

	public final T setOnMouseUp(final String value, final Object... params) {
		setAttribute(Attribute.ONMOUSEUP, String.format(value, params));
		return concreteType.cast(this);
	}

	@Override
	public T setText(final String text) {
		setTextContent(text);
		return concreteType.cast(this);
	}

	@Override
	public final T setTitle(final String value) {
		setAttribute(Attribute.TITLE, value);
		return concreteType.cast(this);
	}

	public static <T extends Element> T addClass(final T element, final String... cssClasses) {
		final String[] previousClasses = getClasses(element);
		final StringBuilder buffer = new StringBuilder(0);
		for (final String previousClass : previousClasses) {
			if (asList(cssClasses).contains(previousClass))
				continue;
			if (buffer.length() > 0)
				buffer.append(' ');
			buffer.append(previousClass);
		}
		for (final String cssClass : cssClasses) {
			if (buffer.length() > 0)
				buffer.append(' ');
			buffer.append(cssClass);
		}
		if (buffer.length() == 0)
			element.removeAttribute(Attribute.CLASS);
		else
			element.setAttribute(Attribute.CLASS, buffer.toString());
		return element;
	}

	public static String[] getClasses(final Element element) {
		final String attribute = element.getAttribute(Attribute.CLASS);
		return attribute == null ? NO_CLASSES : SPACE.split(attribute);
	}

	public static <T extends Element> T removeClass(final T element, final String... cssClasses) {
		final StringBuilder buffer = new StringBuilder(0);
		for (final String token : element.getClasses()) {
			if (!Arrays.asList(cssClasses).contains(token)) {
				if (buffer.length() > 0)
					buffer.append(' ');
				buffer.append(token);
			}
		}
		if (buffer.length() == 0)
			element.removeAttribute(Attribute.CLASS);
		else
			element.setAttribute(Attribute.CLASS, buffer.toString());
		return element;
	}

	public static Collection<String> removeClass(final Element element, final Pattern pattern) {
		final StringBuilder buffer = new StringBuilder(0);
		final String[] tokens = element.getClasses();
		final Collection<String> removed = new ArrayList<String>(tokens.length);
		for (final String token : tokens) {
			if (pattern.matcher(token).matches())
				removed.add(token);
			else {
				if (buffer.length() > 0)
					buffer.append(' ');
				buffer.append(token);
			}
		}
		if (buffer.length() == 0)
			element.removeAttribute(Attribute.CLASS);
		else
			element.setAttribute(Attribute.CLASS, buffer.toString());
		return removed;
	}

	public static <T extends Element> T setClass(final T element, final String value) {
		element.setAttribute(Attribute.CLASS, value);
		return element;
	}

	public static <T extends Element> T setClass(final T element, final Enum<?>... cssClasses) {
		return setClass(element, stream(cssClasses).map(StringFun::enumToCamelCase).collect(joining(" ")));
	}
}