package net.inetalliance.lutra;

import net.inetalliance.lutra.elements.*;

import java.util.Map;
import java.util.TreeMap;

import static net.inetalliance.funky.Funky.stream;

public abstract class CompiledDocument extends HtmlElement {
	protected final Map<String, Element> byId;

	protected CompiledDocument() {
		byId = new TreeMap<>();
	}

	@Override
	public abstract HeadElement getHead();

	@Override
	public abstract BodyElement getBody();

	@Override
	public final Element getById(final String id) {
		return byId.get(id);
	}

	@Override
	public Map<String, Element> getIdMap() {
		return byId;
	}

	public final void reindex() {
		stream(getBody().getDescendants()).filter(Attribute.ID.has).forEach(element -> {
			byId.put(element.getId(), element);
		});
	}

	public static interface Factory {
		CompiledDocument $(final String path);
	}
}