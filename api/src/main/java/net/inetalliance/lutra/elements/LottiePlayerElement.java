package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;
import net.inetalliance.lutra.rules.MayHaveAttribute;

import java.util.EnumSet;

/**
 * The lottie player element is not officially part of the XHTML Strict Specification
 */
public class LottiePlayerElement
        extends CommonAbstractElement<LottiePlayerElement>
        implements InlineElement {

    private static final AttributeRule[] attributeRules =
            {
                    new MayHaveAttribute(Attribute.union(Attribute.COMMON,
                            EnumSet.of(Attribute.SRC, Attribute.CONTROLS,
                                    Attribute.AUTOPLAY, Attribute.LOOP,
                                    Attribute.MODE,  Attribute.TYPE)))
            };

    @Override
    protected boolean isClosed() {
        return false;
    }

    public LottiePlayerElement(final String text) {
        this(new TextContent(text));
    }

    public LottiePlayerElement(final EmbedElementChild... children) {
        super(LottiePlayerElement.class, ElementType.LOTTIE_PLAYER, ChildRule.NONE, attributeRules,
                children);
    }

    @Override
    public LottiePlayerElement copy() {
        return (LottiePlayerElement) copyWithListeners();
    }

    public final String getSrc() {
        return getAttribute(Attribute.SRC);
    }

    public final String getType() {
        return getAttribute(Attribute.TYPE);
    }

    public final Integer getWidth() {
        final String width = getAttribute(Attribute.WIDTH);
        try {
            return width == null ? null : Integer.parseInt(width);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public final Integer getHeight() {
        final String height = getAttribute(Attribute.HEIGHT);
        try {
            return height == null ? null : Integer.parseInt(height);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public final LottiePlayerElement setHeight(final int value) {
        return setHeight(Integer.toString(value));
    }

    public final LottiePlayerElement setWidth(final int value) {
        return setWidth(Integer.toString(value));
    }

    public final LottiePlayerElement setHeight(final String value) {
        setAttribute(Attribute.HEIGHT, value);
        return this;
    }

    public final LottiePlayerElement setSrc(final String value) {
        setAttribute(Attribute.SRC, value);
        return this;
    }

    public final LottiePlayerElement setType(final String value) {
        setAttribute(Attribute.TYPE, value);
        return this;
    }

    public final LottiePlayerElement setWidth(final String value) {
        setAttribute(Attribute.WIDTH, value);
        return this;
    }
}
