package net.inetalliance.lutra.filters;

import net.inetalliance.lutra.elements.Attribute;
import net.inetalliance.lutra.elements.Element;

import java.util.function.Predicate;

public class IdPredicate
	extends AttributeValuePredicate {
	public IdPredicate(final String value) {
		super(Attribute.ID, value);
	}

	public static Predicate<? super Element> any(final String... ids) {
		final IdPredicate[] predicates = new IdPredicate[ids.length];
		for (int i = 0; i < ids.length; i++) {
			predicates[i] = new IdPredicate(ids[i]);
		}
		return e -> {
			for (final IdPredicate predicate : predicates) {
				if (predicate.test(e)) {
					return true;
				}
			}
			return false;
		};
	}
}
