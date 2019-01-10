package net.inetalliance.lutra.elements;

import static net.inetalliance.lutra.elements.ElementType.ADDRESS;
import static net.inetalliance.lutra.rules.AttributeRule.ANY_COMMON_ATTRIBUTES;
import static net.inetalliance.lutra.rules.ChildRule.ANY_INLINE_OR_TEXT_ELEMENTS;

public class AddressElement extends CommonAbstractElement<AddressElement> implements BlockElement {

	public AddressElement(final String text) {
		this(new TextContent(text));
	}

	public AddressElement(final AddressElementChild... children) {
		super(AddressElement.class, ADDRESS, ANY_INLINE_OR_TEXT_ELEMENTS, ANY_COMMON_ATTRIBUTES, children);
	}

	@Override
	public AddressElement clone() throws CloneNotSupportedException {
		return (AddressElement) cloneWithListeners();
	}
}