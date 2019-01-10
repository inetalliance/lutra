package net.inetalliance.lutra.markers;

import net.inetalliance.lutra.elements.Element;

public abstract class BoundMarker<T, E extends Element>
{
	public Marker<E> bind(final T object)
	{
		return new Marker<E>()
		{
			@Override
			public <E2 extends E> E2 mark(final E2 element)
			{
				return BoundMarker.this.mark(element, object);
			}
		};
	}

	protected abstract <E2 extends E> E2 mark(final E2 element, final T object);
}
