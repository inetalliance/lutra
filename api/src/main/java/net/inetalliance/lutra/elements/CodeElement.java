package net.inetalliance.lutra.elements;

import static net.inetalliance.lutra.rules.AttributeRule.ANY_COMMON_ATTRIBUTES;
import static net.inetalliance.lutra.rules.ChildRule.ANY_INLINE_OR_TEXT_ELEMENTS;

public class CodeElement extends CommonAbstractElement<CodeElement> implements InlineElement {

	public CodeElement(final String text) {
		this(new TextContent(text));
	}

	public CodeElement(final CodeElementChild... children) {
		super(CodeElement.class, ElementType.CODE, ANY_INLINE_OR_TEXT_ELEMENTS, ANY_COMMON_ATTRIBUTES, children);
	}

	@Override
	public CodeElement clone() throws CloneNotSupportedException {
		return (CodeElement) cloneWithListeners();
	}

}