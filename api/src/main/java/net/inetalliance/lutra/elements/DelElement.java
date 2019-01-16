package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.MayHaveAttribute;

import java.util.EnumSet;

import static net.inetalliance.lutra.rules.ChildRule.ANY_BLOCK_OR_INLINE_OR_TEXT_ELEMENTS;

public class DelElement extends CommonAbstractElement<DelElement> implements InlineElement {

	private static final AttributeRule[] attributeRules = {
		new MayHaveAttribute(Attribute.union(Attribute.COMMON, EnumSet.of(Attribute.CITE, Attribute.DATETIME)))
	};

	public DelElement(final String text) {
		this(new TextContent(text));
	}

	public DelElement(final DelElementChild... children) {
		super(DelElement.class, ElementType.DEL, ANY_BLOCK_OR_INLINE_OR_TEXT_ELEMENTS, attributeRules, children);
	}

	@Override
	public DelElement copy() {
		return (DelElement) copyWithListeners();
	}

	public final String getCite() {
		return getAttribute(Attribute.CITE);
	}

	public final String getDatetime() {
		return getAttribute(Attribute.DATETIME);
	}

	public final DelElement setCite(final String value) {
		setAttribute(Attribute.CITE, value);
		return this;
	}

	public final DelElement setDatetime(final String value) {
		setAttribute(Attribute.DATETIME, value);
		return this;
	}
}