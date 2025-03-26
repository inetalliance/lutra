package net.inetalliance.lutra.elements;

import static net.inetalliance.lutra.rules.AttributeRule.ANY_COMMON_ATTRIBUTES;
import static net.inetalliance.lutra.rules.ChildRule.ANY_INLINE_OR_TEXT_ELEMENTS;

public class FontElement extends CommonAbstractElement<FontElement> implements InlineElement {

    public FontElement(final String text) {
        this(new TextContent(text));

    }

    public FontElement(final FontElementChild... children) {
        super(FontElement.class, ElementType.FONT, ANY_INLINE_OR_TEXT_ELEMENTS, ANY_COMMON_ATTRIBUTES, children);
    }

    @Override
    public FontElement copy() {
        return (FontElement) copyWithListeners();
    }
}