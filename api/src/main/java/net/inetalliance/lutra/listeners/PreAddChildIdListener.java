package net.inetalliance.lutra.listeners;

import net.inetalliance.lutra.Document;
import net.inetalliance.lutra.elements.Attribute;
import net.inetalliance.lutra.elements.Element;

import java.util.Map;

import static net.inetalliance.funky.Funky.stream;

public class PreAddChildIdListener implements PreAddChildListener {
	private final Map<String, Element> map;

	public PreAddChildIdListener(final Document destinationDocument) {
		map = destinationDocument.getIdMap();
	}

	@Override
	public void preAdd(final Element newChild, final Element destParent) {
		stream(newChild.getDescendants()).filter(Attribute.ID.has).forEach(element -> {
			map.put(element.getId(), element);
		});
	}
}
