package net.inetalliance.lutra.filters.queries;

import net.inetalliance.lutra.elements.Element;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.inetalliance.funky.Funky.stream;
import static net.inetalliance.lutra.elements.DlElement.filter;

public abstract class ClassQuery<T> implements Predicate<Element> {

	public final Pattern pattern;

	public ClassQuery(final String pattern)
	{
		this.pattern = Pattern.compile(pattern);
	}

	@Override
	public boolean test(final Element object)
	{
		for (final String cssClass : object.getClasses())
		{
			final Matcher matcher = pattern.matcher(cssClass);
			if (matcher.matches())
				return true;
		}
		return false;
	}

	public Map<Element, T> queryToMap(final Element root)
	{
		final Map<Element, T> map = new HashMap<Element, T>(0);
		stream(getIterable(root)).filter(this).forEach(element -> {
				for (final String cssClass : element.getClasses()) {
					final Matcher matcher = pattern.matcher(cssClass);
					if (matcher.find())
						map.put(element, fromString(matcher.group(1)));
				}
		});
		return map;
	}

	public void removeClass(final Element element)
	{
		element.removeClass(pattern);
	}

	public T query(final Element root)
	{
		for (final Element element : filter(getIterable(root)))
		{
			for (final String cssClass : element.getClasses())
			{
				final Matcher matcher = pattern.matcher(cssClass);
				if (matcher.find())
					return fromString(matcher.group(1));
			}
		}
		return null;
	}

	private static Iterable<Element> getIterable(final Element root)
	{
		return root == null ? Set.of() : root.getTree();
	}

	protected abstract T fromString(final String value);
}
