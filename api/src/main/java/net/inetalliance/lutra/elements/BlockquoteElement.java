package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.MayHaveAttribute;

import java.util.EnumSet;

import static net.inetalliance.lutra.rules.ChildRule.ANY_BLOCK_ELEMENTS;

public class BlockquoteElement extends CommonAbstractElement<BlockquoteElement> implements BlockElement {

	private static final AttributeRule[] attributeRules = {
		new MayHaveAttribute(Attribute.union(Attribute.COMMON, EnumSet.of(Attribute.CITE)))
	};

	public BlockquoteElement(final BlockquoteElementChild... children) {
		super(BlockquoteElement.class, ElementType.BLOCKQUOTE, ANY_BLOCK_ELEMENTS, attributeRules, children);
	}

	@Override
	public BlockquoteElement copy() {
		return (BlockquoteElement) copyWithListeners();
	}

	public final String getCite() {
		return getAttribute(Attribute.CITE);
	}

	public final BlockquoteElement setCite(final String value) {
		setAttribute(Attribute.CITE, value);
		return this;
	}
}