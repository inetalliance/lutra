package net.inetalliance.lutra.elements;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public enum Attribute {
	ID,
	CLASS,
	STYLE,
	TITLE,
	ONLOAD,
	ONUNLOAD,
	ONCHANGE,
	ONSUBMIT,
	ONRESET,
	ONSELECT,
	ONBLUR,
	ONFOCUS,
	ONKEYDOWN,
	ONKEYPRESS,
	ONKEYUP,
	ONCLICK,
	ONDBLCLICK,
	ONMOUSEDOWN,
	ONMOUSEMOVE,
	ONMOUSEOUT,
	ONMOUSEOVER,
	ONMOUSEUP,
	XMLNS,
	XMLNS_FB("xmlns:fb"),
	XML_LANG("xml:lang"),
	LANG,
	HREF,
	MEDIA,
	TYPE,
	VALUETYPE,
	DISABLED,
	ALT,
	CHECKED,
	SIZE,
	MAXLENGTH,
	ISMAP,
	LONGDESC,
	USEMAP,
	VALUE,
	REL,
	ABBR,
	ALIGN,
	VALIGN,
	COLSPAN,
	AXIS,
	ROWSPAN,
	CHAR,
	CHAROFF,
	SCOPE,
	SELECTED,
	LABEL,
	SPAN,
	CONTENT,
	NAME,
	MULTIPLE,
	SRC,
	URL,
	SECURITY,
	SCROLLING,
	CITE,
	DATETIME,
	ACTION,
	METHOD,
	ACCEPT,
	ACCEPT_CHARSETS("accept-charsets"),
	NOHREF,
	ENCTYPE,
	BORDER,
	CELLPADDING,
	CELLSPACING,
	SUMMARY,
	FRAME,
	RULES,
	ACCESSKEY,
	CHARSET,
	COORDS,
	SHAPE,
	TABINDEX,
	READONLY,
	FOR,
	CLASSID,
	CODEBASE,
	ARCHIVE,
	CODETYPE,
	DATA,
	DECLARE,
	STANDBY,
	COLS,
	ROWS,
	WIDTH,
	HEIGHT,
	ITEMPROP,
	ITEMTYPE,
	PLACEHOLDER,
	HTTP_EQUIV("http-equiv"),
	// Crazy modern attributes
	AUTOCAPITALIZE,
	AUTOCOMPLETE,
	AUTOCORRECT,
	SPELLCHECK,
	REQUIRED,
	NOVALIDATE,
	PATTERN, CONTROLS, BUFFERED, CROSSORIGIN, CURRENTTIME, MUTED, PRELOAD, PLAYSINLINE, POSTER, LOOP;

	public static final EnumSet<Attribute> COMMON =
		EnumSet.of(ID, CLASS, TITLE, STYLE, ONCLICK, ONDBLCLICK, ONMOUSEDOWN, ONMOUSEUP,
			ONMOUSEOVER, ONMOUSEMOVE, ONMOUSEOUT, ONKEYPRESS, ONKEYDOWN, ONKEYUP, ITEMPROP, ITEMTYPE);
	public final String name;
	public final Function<Element, String> from;
	public final Predicate<Element> has;
	public final Predicate<Element> doesntHave;

	Attribute() {
		this(null);
	}

	Attribute(final String name) {
		this.name = name == null ? name().toLowerCase() : name;
		from = element -> element.getAttribute(Attribute.this);
		doesntHave = e -> {
			final String attribute = from.apply(e);
			return attribute == null || attribute.isEmpty();
		};
		has = doesntHave.negate();
	}

	private static final Map<String, Attribute> FROM_NAME;

	static {
		FROM_NAME = new HashMap<>(values().length);
		for (final Attribute attribute : values()) {
			FROM_NAME.put(attribute.name, attribute);
		}
	}

	public Predicate<Element> is(final String value) {
		return is(value::equals);
	}

	public Predicate<Element> is(final Predicate<String> predicate) {
		return element -> predicate.test(element.getAttribute(Attribute.this));
	}

	public static Attribute fromName(final String name) {
		return FROM_NAME.get(name);
	}

	@Override
	public String toString() {
		return name;
	}

	@SafeVarargs
	public static EnumSet<Attribute> union(final EnumSet<Attribute> first, final EnumSet<Attribute>... rest) {
		final EnumSet<Attribute> union = EnumSet.copyOf(first);
		for (final EnumSet<Attribute> set : rest) {
			union.addAll(set);
		}
		return union;
	}
}
