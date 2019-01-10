package net.inetalliance.lutra.elements;

import net.inetalliance.funky.Escaper;
import net.inetalliance.funky.StringFun;
import net.inetalliance.lutra.listeners.CloneListener;
import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;

import java.io.IOException;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

public class TextContent extends Element
	implements
	// child interfaces of types that take text content:
	AbbrElementChild,
	AcronymElementChild,
	AddressElementChild,
	AElementChild,
	BdoElementChild,
	BElementChild,
	BigElementChild,
	ButtonElementChild,
	CaptionElementChild,
	CiteElementChild,
	CodeElementChild,
	DdElementChild,
	DelElementChild,
	DfnElementChild,
	DivElementChild,
	DtElementChild,
	EmElementChild,
	EmbedElementChild,
	FieldsetElementChild,
	H1ElementChild,
	H2ElementChild,
	H3ElementChild,
	H4ElementChild,
	H5ElementChild,
	H6ElementChild,
	IElementChild,
	InsElementChild,
	KbdElementChild,
	LabelElementChild,
	LegendElementChild,
	LiElementChild,
	ObjectElementChild,
	OptionElementChild,
	PElementChild,
	PreElementChild,
	SampElementChild,
	ScriptElementChild,
	SmallElementChild,
	SpanElementChild,
	StrongElementChild,
	StyleElementChild,
	SubElementChild,
	SupElementChild,
	TdElementChild,
	ThElementChild,
	TextareaElementChild,
	TitleElementChild,
	TtElementChild,
	VarElementChild {

	private final String content;

	public TextContent(final String content) {
		super(ElementType.TEXTCONTENT, ChildRule.NONE, AttributeRule.NONE);
		this.content = content;
	}

	@SuppressWarnings({"CloneDoesntCallSuperClone"})
	@Override
	public TextContent clone()
		throws CloneNotSupportedException {
		return new TextContent(content);
	}

	@Override
	public Element cloneWithListeners(final Iterable<? extends CloneListener> listeners) {
		return new TextContent(content);
	}

	@Override
	public String toString() {
		return getParent().type == ElementType.SCRIPT ? content : Escaper.html40.escape(content);
	}

	@Override
	public TextContent setClass(final String value) {
		setAttribute(Attribute.CLASS, value);
		return this;
	}

	@Override
	public TextContent setClass(final Enum<?>... cssClasses) {
		return setClass(stream(cssClasses).map(StringFun::enumToCamelCase).collect(joining(" ")));
	}

	@Override
	public TextContent setId(final String value) {
		setAttribute(Attribute.ID, value);
		return this;
	}

	@Override
	protected boolean needsTab() {
		return false;
	}

	@Override
	public String getText() {
		return content;
	}

	@Override
	public TextContent setText(final String text) {
		setTextContent(text);
		return this;
	}

	@Override
	public boolean toString(final Appendable output, final boolean pretty, final int depth,
	                        final ElementType previous, final ElementType next) throws IOException {
		output.append(toString());
		return false;
	}
}
