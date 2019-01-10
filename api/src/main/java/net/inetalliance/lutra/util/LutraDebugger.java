package net.inetalliance.lutra.util;

import net.inetalliance.lutra.LazyDocument;
import net.inetalliance.lutra.elements.AElement;
import net.inetalliance.lutra.elements.DivElement;
import net.inetalliance.lutra.elements.Element;
import net.inetalliance.lutra.elements.LiElement;
import net.inetalliance.lutra.elements.UlElement;
import net.inetalliance.lutra.rules.ValidationErrors;

import java.util.HashSet;
import java.util.Set;

public class LutraDebugger
{
	private static final ThreadLocal<LutraDebugger> instance = new ThreadLocal<LutraDebugger>();
	private final LinkAuthorizer authorizer;
	private final Set<String> messages;

	public static boolean enable;

	private LutraDebugger(final LinkAuthorizer authorizer)
	{
		this.authorizer = authorizer;
		messages = new HashSet<String>(0);
	}

	public boolean isAuthorized(final String href)
	{
		return authorizer.isAuthorized(href);
	}

	public static boolean isEnabled()
	{
		return get() != null;
	}

	public static void enable(final LinkAuthorizer authorizer)
	{
		if (enable)
			instance.set(new LutraDebugger(authorizer));
	}

	public static void validate(final LazyDocument document)
	{
		final LutraDebugger debugger = get();
		if (debugger != null)
		{
			final ValidationErrors validationErrors = document.validate(true);
			final UlElement ul = new UlElement();
			if (!validationErrors.isEmpty())
				ul.addChild(validationErrors.toUl().getChildren());
			for (final String message : debugger.messages)
				ul.addChild(new LiElement().setText(message));
			if (ul.hasChildren())
			{
				document.body.addChild(new DivElement(
								new AElement("X")
												.setHref("#")
												.setOnClick("Element.remove($('lutraDebugger'));return false;").setClass("close"),
								ul).setId("lutraDebugger"));
			}
			instance.set(null);
		}
	}

	public static void deprecated(final String solution, final Element... elements)
	{
		// disabled until veil is reworked (29-may-2008, erik)
		//		final LutraDebugger debugger = get();
		//		if (debugger != null)
		//		{
		//			for (final Element element : elements)
		//			{
		//				if (element != null)
		//					debugger.messages.add(String.format("%s is deprecated. %s", element.getId(), solution));
		//			}
		//		}
	}

	public static LutraDebugger get()
	{
		return instance.get();
	}

	public static interface LinkAuthorizer
	{
		public boolean isAuthorized(final String href);
	}
}
