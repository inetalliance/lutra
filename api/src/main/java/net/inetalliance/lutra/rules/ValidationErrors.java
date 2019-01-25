package net.inetalliance.lutra.rules;

import net.inetalliance.lutra.elements.Element;
import net.inetalliance.lutra.elements.LiElement;
import net.inetalliance.lutra.elements.UlElement;
import net.inetalliance.lutra.markers.FirstLast;
import net.inetalliance.lutra.markers.OddEven;

import java.util.*;

public class ValidationErrors {

	private final Map<Element, List<String>> errors;

	public ValidationErrors() {
		errors = new HashMap<>();
	}

	public void add(final Element element, final String error) {
		errors.computeIfAbsent(element, e -> new ArrayList<>()).add(error);
	}

	public boolean isEmpty() {
		return errors.isEmpty();
	}

	public int size() {
		return errors.size();
	}

	public Set<Map.Entry<Element, List<String>>> entrySet() {
		return errors.entrySet();
	}

	public void toString(final Collection<String> out) {
		for (final Map.Entry<Element, List<String>> entry : errors.entrySet()) {
			final Element.DocumentLocation location = entry.getKey().getLocation();
			for (final String message : entry.getValue()) {
				final String formattedMessage = location == null ? message : String.format("%s - %s", location, message);
				if (!out.contains(formattedMessage)) {
					out.add(formattedMessage);
				}
			}
		}
	}

	public UlElement toUl() {
		if (isEmpty()) {
			return null;
		}
		final Collection<String> errors = new ArrayList<>(this.errors.size());
		toString(errors);
		final UlElement list = new UlElement();
		final OddEven oddEven = new OddEven();
		final FirstLast firstLast = new FirstLast();
		for (final String error : firstLast.listen(errors)) {
			list.addChild(firstLast.mark(oddEven.mark(new LiElement().setText(error))));
		}
		return list;
	}

	@Override
	public String toString() {
		if (errors.isEmpty()) {
			return "No errors.";
		} else {
			final Collection<String> out = new ArrayList<>(errors.size());
			toString(out);
			final StringBuilder string = new StringBuilder(1024);
			for (final String error : out) {
				if (string.length() > 0) {
					string.append('\n');
				}
				string.append(error);
			}
			return string.toString();
		}
	}
}