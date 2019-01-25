package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;
import net.inetalliance.lutra.rules.MayHaveChild;
import net.inetalliance.lutra.rules.MustHaveChildOnce;

import java.util.EnumSet;

public class HeadElement
	extends Element
	implements HtmlElementChild {
	private static final ChildRule[] childRules =
		{
			new MayHaveChild(EnumSet.of(ElementType.TITLE, ElementType.BASE, ElementType.LINK, ElementType.META,
				ElementType.SCRIPT, ElementType.STYLE)),
			new MustHaveChildOnce(ElementType.TITLE)
		};

	public HeadElement(final HeadElementChild... children) {
		super(ElementType.HEAD, childRules, AttributeRule.NONE, children);
	}

	@Override
	public HeadElement copy() {
		return (HeadElement) copyWithListeners();
	}

	public void addRss(final String title, final String href) {
		appendChild(new LinkElement()
			.setHref(href)
			.setType("application/rss+xml")
			.setRel("alternate")
			.setTitle(title));
	}

	public ScriptElement addScript(final CharSequence javascript) {
		return addScript(javascript.toString());
	}

	public ScriptElement addScript(final String javascript) {
		final ScriptElement child = new ScriptElement();
		child.addChild(new TextContent(javascript));
		addChild(child);
		return child;
	}

	public ScriptElement addScriptToTop(final CharSequence javascript) {
		return addScriptToTop(javascript.toString());
	}

	public ScriptElement addScriptToTop(final String javascript) {
		final ScriptElement child = new ScriptElement();
		child.addChild(new TextContent(javascript));
		final Element firstChild = getFirstChild();
		if (firstChild == null) {
			addChild(child);
		} else {
			firstChild.insertBefore(child);
		}
		return child;
	}

	public void addStylesheet(final String... cssFiles) {
		for (final String cssFile : cssFiles) {
			addChild(LinkElement.css(cssFile));
		}
	}

	public ScriptElement importScript(final String jsFile) {
		final ScriptElement element = new ScriptElement().setSrc(jsFile);
		addChild(element);
		return element;
	}

	public void importScript(final String... jsFiles) {
		for (final String jsFile : jsFiles) {
			addChild(new ScriptElement().setSrc(jsFile));
		}
	}

	@Override
	public HeadElement setClass(final String value) {
		return this;  // do nothing. classes not allowed on this type
	}

	@Override
	public HeadElement setClass(final Enum<?>... cssClasses) {
		return this;  // do nothing. classes not allowed on this type
	}

	@Override
	public HeadElement setId(final String value) {
		setAttribute(Attribute.ID, value);
		return this;
	}

	@Override
	public HeadElement setText(final String text) {
		setTextContent(text);
		return this;
	}

}