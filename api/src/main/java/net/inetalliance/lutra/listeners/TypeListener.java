package net.inetalliance.lutra.listeners;

import net.inetalliance.lutra.elements.Element;
import net.inetalliance.lutra.elements.ElementType;

public class TypeListener implements CloneListener
{
	private final ElementType type;
	private Element element;

	public TypeListener(final ElementType type)
	{
		this.type = type;
	}

	public void cloned(final Element src, final Element dest)
	{
		if (dest.type == type)
			element = dest;
	}

	public final Element getElement()
	{
		return element;
	}
}