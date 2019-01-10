package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;
import net.inetalliance.lutra.rules.MayHaveChild;
import net.inetalliance.lutra.rules.MustHaveAtLeastOneChildOf;

import java.util.EnumSet;
import java.util.Iterator;

import static net.inetalliance.funky.Funky.stream;

public class DlElement extends CommonAbstractElement<DlElement> implements BlockElement {

	private static final ChildRule[] childRules =
		{
			new MayHaveChild(EnumSet.of(ElementType.DT, ElementType.DD)),
			new MustHaveAtLeastOneChildOf(ElementType.DT, ElementType.DD)
		};

	public DlElement(final DlElementChild... children) {
		super(DlElement.class, ElementType.DL, childRules, AttributeRule.ANY_COMMON_ATTRIBUTES, children);
	}

	@Override
	public DlElement clone()
		throws CloneNotSupportedException {
		return (DlElement) cloneWithListeners();
	}

	public static Iterator<DlElement> filter(final Iterator<Element> elements) {
		return stream(() -> elements)
			.filter(e -> e instanceof DlElement)
			.map(e -> (DlElement) e).iterator();
	}

	public static Iterable<DlElement> filter(final Iterable<Element> elements) {
		return () -> filter(elements.iterator());
	}
}