package net.inetalliance.lutra.filters;

import net.inetalliance.lutra.elements.Element;

import java.util.function.Predicate;

public class ClassPredicate implements Predicate<Element> {
	private final Predicate<String> predicate;

	public ClassPredicate(final Predicate<String> predicate) {

		this.predicate = predicate;
	}

	@Override
	public boolean test(final Element arg) {
		for (final String s : arg.getClasses()) {
			if (predicate.test(s))
				return true;
		}
		return false;
	}
}
