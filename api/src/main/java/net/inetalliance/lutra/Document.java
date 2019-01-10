package net.inetalliance.lutra;

import net.inetalliance.lutra.elements.BodyElement;
import net.inetalliance.lutra.elements.Element;
import net.inetalliance.lutra.elements.HeadElement;
import net.inetalliance.lutra.elements.ScriptElement;
import net.inetalliance.lutra.elements.TextContent;

import java.io.File;
import java.util.Map;

public interface Document {
	Element getRoot();

	HeadElement getHead();

	BodyElement getBody();

	Element getById(final String id);

	Map<String, Element> getIdMap();

	File getFile();

	void addOnLoad(final String javascript);

	void addOnLoad(final String javascript, final Object... parameters);

	void addJqueryOnLoad(final CharSequence javascript);

	void addJqueryOnLoad(final CharSequence javascript, final Object... parameters);

	public static class Impl {
		private static final String PROTOTYPE_DOM_LOADED = "Element.observe(document,'dom:loaded',function(){%s});";

		public static void addOnLoad(final Document document, final String javascript) {
			document.getHead().addChild(
				new ScriptElement().addChild(new TextContent(String.format(PROTOTYPE_DOM_LOADED, javascript))));
		}

		private static final String JQUERY_DOM_LOADED = "jQuery(document).ready(function(){%s});";

		public static void addJqueryOnLoad(final Document document, final CharSequence javascript) {
			document.getHead().addChild(
				new ScriptElement().addChild(new TextContent(String.format(JQUERY_DOM_LOADED, javascript))));
		}

		public static void addJqueryOnLoad(final Document document, final CharSequence javascript, final Object... parameters) {
			addJqueryOnLoad(document, parameters.length > 0 ? String.format(javascript.toString(), parameters) : javascript);
		}

		public static void addOnLoad(final Document document, final String javascript, final Object... parameters) {
			addOnLoad(document, parameters.length > 0 ? String.format(javascript, parameters) : javascript);
		}
	}


}
