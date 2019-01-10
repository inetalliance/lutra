package net.inetalliance.lutra.elements;


import static net.inetalliance.lutra.rules.AttributeRule.ANY_COMMON_ATTRIBUTES;
import static net.inetalliance.lutra.rules.ChildRule.ANY_INLINE_OR_TEXT_ELEMENTS;

public class CiteElement extends CommonAbstractElement<CiteElement> implements InlineElement {

	public CiteElement(final String text) {
		this(new TextContent(text));
	}

	public CiteElement(final CiteElementChild... children) {
		super(CiteElement.class, ElementType.CITE, ANY_INLINE_OR_TEXT_ELEMENTS, ANY_COMMON_ATTRIBUTES, children);
	}

	@Override
	public CiteElement clone() throws CloneNotSupportedException {
		return (CiteElement) cloneWithListeners();
	}
}