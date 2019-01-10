package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;
import net.inetalliance.lutra.rules.MayHaveAttribute;
import net.inetalliance.types.www.ContentType;

import java.util.EnumSet;

/**
 * The <embed> element is not officially part of the XHTML Strick Specification, but its usage is so widespread that it
 * has been included in lutra.
 */
public class EmbedElement extends CommonAbstractElement<EmbedElement> implements InlineElement {

	private static final AttributeRule[] attributeRules =
		{
			new MayHaveAttribute(Attribute.union(Attribute.COMMON,
				EnumSet.of(Attribute.SRC, Attribute.WIDTH, Attribute.HEIGHT,
					Attribute.TYPE)))
		};

	@Override
	protected boolean isClosed() {
		return false;
	}

	public EmbedElement(final String text) {
		this(new TextContent(text));
	}

	public EmbedElement(final EmbedElementChild... children) {
		super(EmbedElement.class, ElementType.EMBED, ChildRule.ANY_BLOCK_OR_INLINE_OR_TEXT_ELEMENTS, attributeRules, children);
	}

	@Override
	public EmbedElement clone() throws CloneNotSupportedException {
		return (EmbedElement) cloneWithListeners();
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

	public final EmbedElement setHeight(final int value) {
		return setHeight(Integer.toString(value));
	}

	public final EmbedElement setWidth(final int value) {
		return setWidth(Integer.toString(value));
	}

	public final EmbedElement setHeight(final String value) {
		setAttribute(Attribute.HEIGHT, value);
		return this;
	}

	public final EmbedElement setSrc(final String value) {
		setAttribute(Attribute.SRC, value);
		return this;
	}

	public final EmbedElement setType(final ContentType value) {
		setAttribute(Attribute.TYPE, value.value);
		return this;
	}

	public final EmbedElement setWidth(final String value) {
		setAttribute(Attribute.WIDTH, value);
		return this;
	}
}