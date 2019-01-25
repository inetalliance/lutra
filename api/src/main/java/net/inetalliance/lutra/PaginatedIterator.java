package net.inetalliance.lutra;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class PaginatedIterator<T> implements Iterator<T> {

	private final Iterator<T> iterator;
	private final int end;
	private int index;

	PaginatedIterator(final Iterator<T> iterator, final int start, final int end) {
		this.iterator = iterator;
		this.end = end;
		for (int i = 0; i < start && iterator.hasNext(); i++) {
			iterator.next();
		}
		index = start;
	}

	public boolean hasNext() {
		return iterator.hasNext() && index < end;
	}

	public T next() {
		if (index >= end) {
			throw new NoSuchElementException(String.format("current index is %s, and end of this page is %s", index, end));
		}
		final T next = iterator.next();
		index++;
		return next;
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}
}
