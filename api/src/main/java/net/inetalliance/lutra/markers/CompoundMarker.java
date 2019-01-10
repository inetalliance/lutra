package net.inetalliance.lutra.markers;

import net.inetalliance.lutra.elements.Element;

public class CompoundMarker<E extends Element> extends Marker<E>
{
	private final Marker<E>[] markers;

	@SafeVarargs
	@SuppressWarnings("varargs")
	public CompoundMarker(final Marker<E>... markers)
	{
		this.markers = markers;
	}

	@Override
	public <T extends E> T mark(T element)
	{
		for (final Marker<E> marker : markers)
			element = marker.mark(element);
		return element;
	}
}
