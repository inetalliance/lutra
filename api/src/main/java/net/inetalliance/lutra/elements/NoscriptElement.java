package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;

import java.io.IOException;

public class NoscriptElement extends CommonAbstractElement<NoscriptElement> implements BlockElement
{

	public NoscriptElement(final NoscriptElementChild... children)
	{
		super(NoscriptElement.class, ElementType.NOSCRIPT, ChildRule.ANY_BLOCK_ELEMENTS, AttributeRule.ANY_COMMON_ATTRIBUTES, children);
	}

	@Override
	public boolean toString(final Appendable output, final boolean pretty, final int depth,
	                        final ElementType previous, final ElementType next)
			throws IOException
	{
		if (hasClass("close-only"))
		{
			// this is a HORRIBLE HORRIBLE thing Google Site Optimizer is making us do -Erik 2010-08-20
			final boolean tab = pretty && needsTab();
			if (tab)
				tab(output, depth);
			output.append("</").append(elementType.toString()).append(">");
			return tab;
		}
		else
			return super.toString(output, pretty, depth, previous, next);
	}

	@Override
	public NoscriptElement clone() throws CloneNotSupportedException
	{
		return (NoscriptElement) cloneWithListeners();
	}
}