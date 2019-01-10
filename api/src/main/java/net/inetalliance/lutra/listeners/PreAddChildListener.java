package net.inetalliance.lutra.listeners;

import net.inetalliance.lutra.elements.Element;

public interface PreAddChildListener
{
	public void preAdd(final Element newChild, final Element destParent);
}