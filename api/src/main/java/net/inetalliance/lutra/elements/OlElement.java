package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;
import net.inetalliance.lutra.rules.MayHaveChild;
import net.inetalliance.lutra.rules.MustHaveAtLeastOneChildOf;

import java.util.EnumSet;

import static net.inetalliance.funky.Funky.stream;

public class OlElement extends CommonAbstractElement<OlElement> implements BlockElement {

	private static final ChildRule[] childRules =
		{
			new MayHaveChild(EnumSet.of(ElementType.LI)),
			new MustHaveAtLeastOneChildOf(ElementType.LI)
		};

	public OlElement(final OlElementChild... children) {
		super(OlElement.class, ElementType.OL, childRules, AttributeRule.ANY_COMMON_ATTRIBUTES, children);
	}

	@Override
	public OlElement clone()
		throws CloneNotSupportedException {
		return (OlElement) cloneWithListeners();
	}

	public Iterable<LiElement> getItems() {
		return () -> stream(getChildren()).filter(e -> e instanceof LiElement).map(e -> (LiElement) e).iterator();

	}

	@Override
	protected boolean isClosed() {
		return false;
	}
}