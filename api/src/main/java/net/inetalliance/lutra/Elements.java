package net.inetalliance.lutra;

import net.inetalliance.lutra.elements.Element;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public interface Elements {

	static Stream<Element> nonNull(final Element... elements) {
		return Arrays.stream(elements).filter(Objects::nonNull);
	}

	static void remove(final Element... elements) {
		nonNull(elements).forEach(Element::remove);
	}

	static void removeId(final Element... elements) {
		nonNull(elements).forEach(Element::removeId);
	}

	static void hide(final Element... elements) {
		nonNull(elements).forEach(Element::hide);
	}

	static void show(final Element... elements) {
		nonNull(elements).forEach(Element::show);
	}

	static void remove(final Iterable<? extends Element> i) {
		i.forEach(Element::remove);
	}

	static void removeId(final Iterable<? extends Element> i) {
		i.forEach(Element::removeId);
	}

	static void hide(final Iterable<? extends Element> i) {
		i.forEach(Element::hide);
	}

	static void show(final Iterable<? extends Element> i) {
		i.forEach(Element::show);
	}

	static void setVisible(final boolean visible, final Element... elements) {
		nonNull(elements).forEach(e -> e.setVisible(visible));
	}

	static void setVisible(final boolean visible, final Iterable<? extends Element> i) {
		i.forEach(e -> e.setVisible(visible));
	}

	static void removeChildren(final Element... elements) {
		nonNull(elements).forEach(Element::removeChildren);
	}

	static void removeChildren(final Iterable<? extends Element> elements) {
		elements.forEach(Element::removeChildren);
	}
}
