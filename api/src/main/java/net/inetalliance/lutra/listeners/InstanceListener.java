package net.inetalliance.lutra.listeners;

import net.inetalliance.lutra.elements.Element;

public class InstanceListener implements CloneListener
{
	private final Element original;
	private Element clone;

	public InstanceListener(final Element original)
	{
		this.original = original;
	}

	public void cloned(final Element src, final Element dest)
	{
		if (this.original == src)
			clone = dest;
	}

	public final Element getClone()
	{
		return clone;
	}
}