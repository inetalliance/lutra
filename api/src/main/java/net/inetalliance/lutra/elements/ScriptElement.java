package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;
import net.inetalliance.lutra.rules.MayHaveAttribute;
import net.inetalliance.lutra.rules.MustHaveAttribute;

import java.io.IOException;
import java.util.EnumSet;

import static net.inetalliance.funky.StringFun.secureUrl;


public class ScriptElement extends Element implements HeadElementChild, BlockElement {
	private static final AttributeRule[] attributeRules =
		{
			new MayHaveAttribute(EnumSet.of(Attribute.SRC, Attribute.TYPE, Attribute.CHARSET, Attribute.ID)),
			new MustHaveAttribute(Attribute.TYPE)
		};

	private final boolean useCdata;

	public ScriptElement(final CharSequence text) {
		this(true, text.toString());
	}

	public ScriptElement(final String text) {
		this(true, new TextContent(text));
	}

	public ScriptElement(final ScriptElementChild... children) {
		this(true, children);
	}

	public ScriptElement(final boolean useCdata, final CharSequence text) {
		this(useCdata, text.toString());
	}

	public ScriptElement(final boolean useCdata, final String text) {
		this(useCdata, new TextContent(text));
	}

	public ScriptElement(final boolean useCdata, final ScriptElementChild... children) {
		super(ElementType.SCRIPT, ChildRule.TEXT, attributeRules, children);
		this.useCdata = useCdata;
		setType("text/javascript");
	}

	public final ScriptElement setType(final String value) {
		setAttribute(Attribute.TYPE, value);
		return this;
	}

	@Override
	public final String[] getClasses() {
		return CommonAbstractElement.getClasses(this);
	}

	@Override
	public ScriptElement clone()
		throws CloneNotSupportedException {
		return (ScriptElement) cloneWithListeners();
	}

	public final String getType() {
		return getAttribute(Attribute.TYPE);
	}

	@Override
	protected boolean isClosed() {
		return false;
	}

	@Override
	protected boolean outputChild(final Appendable output, final Element child, final boolean pretty,
	                              final int depth, final ElementType previous, final ElementType next)
		throws IOException {
		final boolean hasSrc = getSrc() != null;
		if (!hasSrc) {
			if (useCdata)
				output.append("/*<![CDATA[*/");
			if (pretty)
				output.append('\n');
			child.toString(output, false, depth, previous, next);
			if (pretty)
				output.append('\n');
			if (useCdata)
				output.append("/*]]>*/");
		}
		return false;
	}

	public final String getSrc() {
		return getAttribute(Attribute.SRC);
	}

	@Override
	public void secure() {
		setSrc(secureUrl(getSrc()));
	}

	public final ScriptElement setSrc(final String value) {
		setAttribute(Attribute.SRC, value);
		return this;
	}

	@Override
	public Element setClass(final String value) {
		return this;  // do nothing. classes not allowed on this type
	}

	@Override
	public Element setClass(final Enum<?>... cssClasses) {
		return this;  // do nothing. classes not allowed on this type
	}

	@Override
	public Element setId(final String value) {
		return this;  // do nothing. classes not allowed on this type
	}

	public final ScriptElement setSrc(final String value, final Object... parameters) {
		return setSrc(String.format(value, parameters));
	}

	@Override
	public ScriptElement setText(final String text) {
		setTextContent(text);
		return this;
	}

}