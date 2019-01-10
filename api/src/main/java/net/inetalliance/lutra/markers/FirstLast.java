package net.inetalliance.lutra.markers;

import net.inetalliance.lutra.elements.Element;

import java.util.Iterator;

public class FirstLast extends Marker<Element>
{
	private boolean first;
	private boolean last;
	private static final String FIRST = "first";
	private static final String LAST = "last";

	public <T> Iterable<T> listen(final Iterable<T> iterable)
	{
		return new Iterable<T>()
		{
			public Iterator<T> iterator()
			{
				return listen(iterable.iterator());
			}
		};
	}

	public <T> Iterator<T> listen(final Iterator<T> iterator)
	{
		return new FirstLastIterator<T>(iterator);
	}

	private class FirstLastIterator<T> implements Iterator<T>
	{
		private final Iterator<T> iterator;
		private boolean firstForIterator;

		private FirstLastIterator(final Iterator<T> iterator)
		{
			this.iterator = iterator;
			firstForIterator = true;
		}

		public boolean hasNext()
		{
			return iterator.hasNext();
		}

		public T next()
		{
			final T next = iterator.next();
			first = firstForIterator;
			if (firstForIterator)
				firstForIterator = false;
			last = !hasNext();
			return next;
		}

		public void remove()
		{
			// removal could screw things up if it's done after marking the clone
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public <T extends Element> T mark(final T element)
	{
		if (first)
			element.addClass(FIRST);
		if (last)
			element.addClass(LAST);
		return element;
	}

	public FirstLast()
	{
	}
}