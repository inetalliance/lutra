package net.inetalliance.lutra.filters;

import net.inetalliance.funky.Funky;
import net.inetalliance.lutra.elements.Attribute;
import net.inetalliance.lutra.elements.Element;

import java.util.Arrays;
import java.util.function.Predicate;

public class IdPredicate extends AttributeValuePredicate {
	public IdPredicate(final String value) {
		super(Attribute.ID, value);
	}

	public static Predicate<? super Element> any(final String... ids) {
		final IdPredicate[] predicates = new IdPredicate[ids.length];
		for (int i = 0; i < ids.length; i++)
			predicates[i] = new IdPredicate(ids[i]);
		return Funky.any(Arrays.asList(predicates));
	}
}
