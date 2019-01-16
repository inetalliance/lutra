package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;
import net.inetalliance.lutra.rules.MayHaveAttribute;

import java.util.EnumSet;

/**
 * The <embed> element is not officially part of the XHTML Strick Specification, but its usage is so widespread that it
 * has been included in lutra.
 */
public class AudioElement extends CommonAbstractElement<AudioElement> implements InlineElement {

	private static final AttributeRule[] attributeRules =
		{
			new MayHaveAttribute(Attribute.union(Attribute.COMMON,
				EnumSet.of(Attribute.SRC)))
		};

	@Override
	protected boolean isClosed() {
		return false;
	}

	public AudioElement(final String text) {
		this(new TextContent(text));
	}

	public AudioElement(final EmbedElementChild... children) {
		super(AudioElement.class, ElementType.AUDIO, ChildRule.ANY_BLOCK_OR_INLINE_OR_TEXT_ELEMENTS, attributeRules, children);
	}

	@Override
	public AudioElement copy() {
		return (AudioElement) copyWithListeners();
	}

	public final String getSrc() {
		return getAttribute(Attribute.SRC);
	}

	public final AudioElement setSrc(final String value) {
		setAttribute(Attribute.SRC, value);
		return this;
	}
}