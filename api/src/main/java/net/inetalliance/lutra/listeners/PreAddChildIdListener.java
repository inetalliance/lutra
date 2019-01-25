package net.inetalliance.lutra.listeners;

import net.inetalliance.lutra.Document;
import net.inetalliance.lutra.elements.Attribute;
import net.inetalliance.lutra.elements.Element;

import java.util.Map;
import java.util.stream.StreamSupport;

public class PreAddChildIdListener
	implements PreAddChildListener {
	private final Map<String, Element> map;

	public PreAddChildIdListener(final Document destinationDocument) {
		map = destinationDocument.getIdMap();
	}

	@Override
	public void preAdd(final Element newChild, final Element destParent) {
		StreamSupport.stream(newChild.getDescendants().spliterator(), false)
			.filter(Attribute.ID.has).forEach(element -> map.put(element.getId(), element));
	}
}
