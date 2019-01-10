package net.inetalliance.lutra.filters.queries;

import net.inetalliance.lutra.elements.Attribute;
import net.inetalliance.lutra.elements.Element;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.inetalliance.funky.Funky.stream;

public abstract class AttributeQuery<T> implements Predicate<Element> {
	public final Pattern pattern;
	public final Attribute attribute;
	public T lastMatch;

	public AttributeQuery(final Attribute attribute, final String pattern) {
		this.attribute = attribute;
		this.pattern = Pattern.compile(pattern);
	}

	public final Predicate<String> getStringPredicate() {
		return pattern.asPredicate();
	}

	@Override
	public boolean test(final Element element) {
		final String value = element.getAttribute(attribute);
		if (value != null) {
			final Matcher matcher = pattern.matcher(value);
			if (matcher.matches()) {
				if (matcher.groupCount() > 0)
					lastMatch = fromString(matcher.group(1));
				return true;
			}
		}
		lastMatch = null;
		return false;
	}

	public Map<Element, T> queryToMap(final Element root) {
		final Map<Element, T> map = new HashMap<Element, T>(0);
		stream(root.getDescendants()).filter(this).forEach(element -> {
			final Matcher matcher = pattern.matcher(element.getAttribute(attribute));
			if (matcher.find())
				map.put(element, fromString(matcher.group(1)));
		});
		return map;
	}

	public T query(final Element root) {
		return stream(root.getTree())
			.filter(this)
			.map(e -> pattern.matcher(e.getAttribute(attribute)))
			.filter(Matcher::find)
			.map(m -> fromString(m.group(1)))
			.findFirst()
			.orElse(null);

	}

	protected abstract T fromString(final String value);
}