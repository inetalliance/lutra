package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.MicrodataType;
import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.MayHaveAttribute;
import net.inetalliance.lutra.rules.MustHaveAttribute;

import java.util.EnumSet;

import static net.inetalliance.lutra.elements.Attribute.*;
import static net.inetalliance.lutra.elements.ElementType.META;
import static net.inetalliance.lutra.rules.ChildRule.NONE;

public class MetaElement extends Element implements HeadElementChild {
	private static final AttributeRule[] attributeRules =
		new AttributeRule[]{
			new MayHaveAttribute(EnumSet.of(CONTENT, NAME, HTTP_EQUIV, ITEMPROP, ITEMTYPE)),
			new MustHaveAttribute(NAME)
		};

	public MetaElement() {
		super(META, NONE, attributeRules);
	}

	@Override
	public MetaElement clone() throws CloneNotSupportedException {
		return (MetaElement) cloneWithListeners();
	}

	public final String getContent() {
		return getAttribute(CONTENT);
	}

	public final String getName() {
		return getAttribute(NAME);
	}

	@Override
	public MetaElement setClass(final String value) {
		return this;  // do nothing. classes not allowed on this type
	}

	@Override
	public MetaElement setClass(final Enum<?>... cssClasses) {
		return this;  // do nothing. classes not allowed on this type
	}

	public final MetaElement setContent(final String value) {
		setAttribute(CONTENT, value);
		return this;
	}

	public final MetaElement setHttpEquiv(final String value) {
		setAttribute(HTTP_EQUIV, value);
		return this;
	}

	@Override
	public MetaElement setId(final String value) {
		setAttribute(Attribute.ID, value);
		return this;
	}

	public final MetaElement setItemType(final MicrodataType value) {
		setAttribute(Attribute.ITEMTYPE, value.toString());
		return this;
	}

	public final MetaElement setItemProp(final String value) {
		setAttribute(Attribute.ITEMPROP, value);
		return this;
	}

	public final MetaElement setName(final String value) {
		setAttribute(NAME, value);
		return this;
	}

	@Override
	public MetaElement setText(final String text) {
		setTextContent(text);
		return this;
	}

}
