package net.inetalliance.lutra.listeners;

import net.inetalliance.lutra.elements.Element;

import java.util.Map;

public class IdMapper implements CloneListener
{
	private final Map<String, Element> map;

	public IdMapper(final Map<String, Element> map)
	{
		this.map = map;
	}

	public void cloned(final Element src, final Element dest)
	{
		final String id = dest.getId();
		if (id != null)
			map.put(id, dest);
	}
}
