package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;
import net.inetalliance.lutra.rules.MayHaveAttribute;

import java.util.EnumSet;

public class FigureElement
        extends CommonAbstractElement<FigureElement>
        implements InlineElement {

    private static final AttributeRule[] attributeRules =
            {
                    new MayHaveAttribute(Attribute.COMMON)
            };

    @Override
    protected boolean isClosed() {
        return false;
    }

    public FigureElement(final String text) {
        this(new TextContent(text));
    }

    public FigureElement(final EmbedElementChild... children) {
        super(FigureElement.class, ElementType.FIGURE, ChildRule.ANY_BLOCK_OR_INLINE_OR_TEXT_ELEMENTS,
                attributeRules,
                children);
    }

    @Override
    public FigureElement copy() {
        return (FigureElement) copyWithListeners();
    }

}