package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;
import net.inetalliance.lutra.rules.MayHaveAttribute;
import net.inetalliance.lutra.rules.MayHaveChild;

import java.util.EnumSet;

import static net.inetalliance.funky.StringFun.secureUrl;


public class IframeElement extends CommonAbstractElement<IframeElement> implements InlineElement {
	@SuppressWarnings("unchecked")
	private static final ChildRule[] childRules =
		{
			new MayHaveChild(ElementType.union(ElementType.BLOCK_AND_INLINE_AND_TEXT_ELEMENTS,
				EnumSet.of(ElementType.PARAM)))
		};
	@SuppressWarnings("unchecked")
	private static final AttributeRule[] attributeRules =
		{
			new MayHaveAttribute(Attribute.union(Attribute.COMMON,
				EnumSet.of(Attribute.SRC, Attribute.URL, Attribute.SECURITY,
					Attribute.SCROLLING, Attribute.WIDTH, Attribute.HEIGHT,
					Attribute.NAME)))
		};

	public IframeElement(final IframeElementChild... children) {
		super(IframeElement.class, ElementType.IFRAME, childRules, attributeRules, children);
	}

	@Override
	public IframeElement clone() throws CloneNotSupportedException {
		return (IframeElement) cloneWithListeners();
	}

	public final String getData() {
		return getAttribute(Attribute.DATA);
	}

	public final Integer getHeight() {
		final String height = getAttribute(Attribute.HEIGHT);
		try {
			return height == null ? null : Integer.parseInt(height);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public final String getName() {
		return getAttribute(Attribute.NAME);
	}

	public final IframeElement setName(final String value) {
		setAttribute(Attribute.NAME, value);
		return this;
	}

	public final String getScrolling() {
		return getAttribute(Attribute.SCROLLING);
	}

	public final String getSecurity() {
		return getAttribute(Attribute.SECURITY);
	}

	public final String getUrl() {
		return getAttribute(Attribute.URL);
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
		setSrc(secureUrl(getSrc()));
	}

	public final IframeElement setSrc(final String value) {
		setAttribute(Attribute.SRC, value);
		return this;
	}

	public final String getSrc() {
		return getAttribute(Attribute.SRC);
	}

	public final IframeElement setHeight(final int value) {
		return setHeight(Integer.toString(value));
	}

	public final IframeElement setHeight(final String value) {
		setAttribute(Attribute.HEIGHT, value);
		return this;
	}

	public final IframeElement setScrolling(final String value) {
		setAttribute(Attribute.SCROLLING, value);
		return this;
	}

	public final IframeElement setSecurity(final String value) {
		setAttribute(Attribute.SECURITY, value);
		return this;
	}

	public final IframeElement setUrl(final String value) {
		setAttribute(Attribute.URL, value);
		return this;
	}

	public final IframeElement setWidth(final int value) {
		return setWidth(Integer.toString(value));
	}

	public final IframeElement setWidth(final String value) {
		setAttribute(Attribute.WIDTH, value);
		return this;
	}

}