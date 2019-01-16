package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.MayHaveAttribute;

import java.util.EnumSet;

import static net.inetalliance.lutra.rules.ChildRule.ANY_BLOCK_OR_INLINE_OR_TEXT_ELEMENTS;

public class InsElement extends CommonAbstractElement<InsElement> implements InlineElement, BlockElement {

	private static final AttributeRule[] attributeRules =
		{
			new MayHaveAttribute(Attribute.union(Attribute.COMMON, EnumSet.of(Attribute.CITE, Attribute.DATETIME)))
		};

	public InsElement(final String text) {
		this(new TextContent(text));
	}

	public InsElement(final InsElementChild... children) {
		super(InsElement.class, ElementType.INS, ANY_BLOCK_OR_INLINE_OR_TEXT_ELEMENTS, attributeRules, children);
	}

	@Override
	public InsElement copy() {
		return (InsElement) copyWithListeners();
	}

	public final String getCite() {
		return getAttribute(Attribute.CITE);
	}

	public final String getDatetime() {
		return getAttribute(Attribute.DATETIME);
	}

	public final InsElement setCite(final String value) {
		setAttribute(Attribute.CITE, value);
		return this;
	}

	public final InsElement setDatetime(final String value) {
		setAttribute(Attribute.DATETIME, value);
		return this;
	}
}