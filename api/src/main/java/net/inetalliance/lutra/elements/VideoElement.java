package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;
import net.inetalliance.lutra.rules.MayHaveAttribute;

import java.util.EnumSet;

/**
 * The embed element is not officially part of the XHTML Strict Specification, but its usage is so widespread
 * that it
 * has been included in lutra.
 */
public class VideoElement
        extends CommonAbstractElement<VideoElement>
        implements InlineElement {

    private static final AttributeRule[] attributeRules =
            {
                    new MayHaveAttribute(Attribute.union(Attribute.COMMON,
                            EnumSet.of(Attribute.SRC, Attribute.WIDTH, Attribute.HEIGHT,
                                    Attribute.CONTROLS, Attribute.BUFFERED, Attribute.CROSSORIGIN, Attribute.CURRENTTIME,
                                    Attribute.LOOP, Attribute.POSTER, Attribute.PLAYSINLINE, Attribute.MUTED, Attribute.PRELOAD,
                                    Attribute.TYPE)))
            };

    @Override
    protected boolean isClosed() {
        return false;
    }

    public VideoElement(final String text) {
        this(new TextContent(text));
    }

    public VideoElement(final EmbedElementChild... children) {
        super(VideoElement.class, ElementType.VIDEO, ChildRule.ANY_BLOCK_OR_INLINE_OR_TEXT_ELEMENTS, attributeRules,
                children);
    }

    @Override
    public VideoElement copy() {
        return (VideoElement) copyWithListeners();
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

    public final VideoElement setHeight(final int value) {
        return setHeight(Integer.toString(value));
    }

    public final VideoElement setWidth(final int value) {
        return setWidth(Integer.toString(value));
    }

    public final VideoElement setHeight(final String value) {
        setAttribute(Attribute.HEIGHT, value);
        return this;
    }

    public final VideoElement setSrc(final String value) {
        setAttribute(Attribute.SRC, value);
        return this;
    }

    public final VideoElement setType(final String value) {
        setAttribute(Attribute.TYPE, value);
        return this;
    }

    public final VideoElement setWidth(final String value) {
        setAttribute(Attribute.WIDTH, value);
        return this;
    }
}