package net.inetalliance.lutra.markers;

import net.inetalliance.lutra.elements.Element;

public class CompoundBoundMarker<T,E extends Element> extends BoundMarker<T,E>
{
	private final BoundMarker<T,E>[] markers;

	@SafeVarargs
	@SuppressWarnings("varargs")
	public CompoundBoundMarker(final BoundMarker<T,E>... markers)
	{
		this.markers = markers;
	}

	@Override
	protected <E2 extends E> E2 mark(E2 element, final T object)
	{
		for (final BoundMarker<T, E> marker : markers)
			element = marker.bind(object).mark(element);
		return element;
	}
}