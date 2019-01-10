package net.inetalliance.lutra.filters;

import net.inetalliance.lutra.elements.Element;

import java.util.function.Predicate;

public class HasClassPredicate implements Predicate<Element> {
	private final String cssClass;

	public HasClassPredicate(final String cssClass) {
		this.cssClass = cssClass;
	}

	@Override
	public boolean test(final Element element) {
		return element.hasClass(cssClass);
	}
}