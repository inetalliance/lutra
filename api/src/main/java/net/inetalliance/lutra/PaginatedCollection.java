package net.inetalliance.lutra;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

public class PaginatedCollection<T>
	extends AbstractCollection<T> {
	private final Collection<T> wrapped;
	private final int start;
	private final int end;

	PaginatedCollection(final Collection<T> wrapped, final int start, final int end) {
		this.wrapped = wrapped;
		this.start = start;
		this.end = end;
	}

	@Override
	public int size() {
		return wrapped.size() > end ? end - start : wrapped.size() - start;
	}

	@Override
	public Iterator<T> iterator() {
		return new PaginatedIterator<>(wrapped.iterator(), start, end);
	}

}
