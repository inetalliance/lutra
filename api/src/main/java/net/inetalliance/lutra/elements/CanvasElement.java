package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;
import net.inetalliance.lutra.rules.MayHaveAttribute;
import net.inetalliance.lutra.rules.MayHaveChild;

import java.util.EnumSet;

import static net.inetalliance.lutra.elements.LinkElement.secureUrl;

public class CanvasElement
	extends CommonAbstractElement<CanvasElement>
	implements InlineElement {
	private static final ChildRule[] childRules =
		{
			new MayHaveChild(ElementType.union(ElementType.BLOCK_AND_INLINE_AND_TEXT_ELEMENTS,
				EnumSet.of(ElementType.PARAM)))
		};
	private static final AttributeRule[] attributeRules =
		{
			new MayHaveAttribute(Attribute.union(Attribute.COMMON,
				EnumSet.of( Attribute.DATA, Attribute.HEIGHT, Attribute.WIDTH )))
		};

	public CanvasElement(final String text) {
		this(new TextContent(text));
	}

	public CanvasElement(final CanvasElementChild... children) {
		super(CanvasElement.class, ElementType.CANVAS, childRules, attributeRules, children);
	}

	@Override
	public CanvasElement copy() {
		return (CanvasElement) copyWithListeners();
	}


	public final Integer getHeight() {
		final String height = getAttribute(Attribute.HEIGHT);
		try {
			return height == null ? null : Integer.parseInt(height);
		} catch (NumberFormatException e) {
			return null;
		}
	}


	public final Integer getWidth() {
		final String width = getAttribute(Attribute.WIDTH);
		try {
			return width == null ? null : Integer.parseInt(width);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	@Override
	protected boolean isClosed() {
		return false;
	}

	@Override
	public void secure() {
		setData(secureUrl(getData()));
	}

	public final CanvasElement setData(final String value) {
		setAttribute(Attribute.DATA, value);
		return this;
	}

	public final String getData() {
		return getAttribute(Attribute.DATA);
	}

	public final CanvasElement setHeight(final int value) {
		return setHeight(Integer.toString(value));
	}

	public final CanvasElement setHeight(final String value) {
		setAttribute(Attribute.HEIGHT, value);
		return this;
	}



	public final CanvasElement setWidth(final int value) {
		return setWidth(Integer.toString(value));
	}

	public final CanvasElement setWidth(final String value) {
		setAttribute(Attribute.WIDTH, value);
		return this;
	}

}