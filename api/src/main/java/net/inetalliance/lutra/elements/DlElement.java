package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;
import net.inetalliance.lutra.rules.MayHaveChild;
import net.inetalliance.lutra.rules.MustHaveAtLeastOneChildOf;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class DlElement
	extends CommonAbstractElement<DlElement>
	implements BlockElement {

	private static final ChildRule[] childRules =
		{
			new MayHaveChild(EnumSet.of(ElementType.DT, ElementType.DD)),
			new MustHaveAtLeastOneChildOf(ElementType.DT, ElementType.DD)
		};

	public DlElement(final DlElementChild... children) {
		super(DlElement.class, ElementType.DL, childRules, AttributeRule.ANY_COMMON_ATTRIBUTES, children);
	}

	@Override
	public DlElement copy() {
		return (DlElement) copyWithListeners();
	}

	public static Iterator<DlElement> filter(final Iterator<Element> elements) {
		Iterable<Element> i = () -> elements;
		return StreamSupport.stream(i.spliterator(), false)
			.filter(e -> e instanceof DlElement)
			.map(e -> (DlElement) e).iterator();
	}

	public static Iterable<DlElement> filter(final Iterable<Element> elements) {
		return () -> filter(elements.iterator());
	}
}