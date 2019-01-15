package net.inetalliance.lutra;

import net.inetalliance.funky.Funky;
import net.inetalliance.lutra.elements.Element;

public interface Elements {
	static void remove(final Element... elements) {
		Funky.nonNull(elements).forEach(Element::remove);
	}
	static void removeId(final Element... elements) {
		Funky.nonNull(elements).forEach(Element::removeId);
	}
	static void hide(final Element... elements) {
		Funky.nonNull(elements).forEach(Element::hide);
	}
	static void show(final Element... elements) {
		Funky.nonNull(elements).forEach(Element::show);
	}
	static void remove(final Iterable<? extends Element> i) {
		Funky.stream(i).forEach(Element::remove);
	}
	static void removeId(final Iterable<? extends Element> i) {
		Funky.stream(i).forEach(Element::removeId);
	}
	static void hide(final Iterable<? extends Element> i) {
		Funky.stream(i).forEach(Element::hide);
	}
	static void show(final Iterable<? extends Element> i) {
		Funky.stream(i).forEach(Element::show);
	}
}
