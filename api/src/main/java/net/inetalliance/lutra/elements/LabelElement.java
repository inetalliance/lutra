package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.*;

import java.util.EnumSet;

public class LabelElement extends CommonAbstractElement<LabelElement> implements InlineElement {

	private static final ChildRule[] childRules =
		{
			new MayHaveChild(ElementType.INLINE_AND_TEXT_ELEMENTS),
			new MayNotHaveDescendant(ElementType.LABEL)
		};
	private static final AttributeRule[] attributeRules =
		{
			new MayHaveAttribute(Attribute.union(Attribute.COMMON, EnumSet.of(Attribute.FOR)))
		};

	public LabelElement(final String text) {
		this(new TextContent(text));
	}

	public LabelElement(final LabelElementChild... children) {
		super(LabelElement.class, ElementType.LABEL, childRules, attributeRules, children);
	}

	@Override
	public LabelElement clone() throws CloneNotSupportedException {
		return (LabelElement) cloneWithListeners();
	}

	public final String getFor() {
		return getAttribute(Attribute.FOR);
	}

	public final LabelElement setFor(final String value) {
		setAttribute(Attribute.FOR, value);
		return this;
	}
}