package net.inetalliance.lutra.markers;

import net.inetalliance.lutra.elements.Element;

public abstract class Marker<E extends Element>
{
	public abstract <T extends E> T mark(final T element);
}