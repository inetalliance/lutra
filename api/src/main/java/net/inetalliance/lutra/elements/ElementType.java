package net.inetalliance.lutra.elements;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public enum ElementType {
	A {
		@Override
		public Element create() {
			return new AElement();
		}
	},
	ABBR {
		@Override
		public Element create() {
			return new AbbrElement();
		}
	},
	ACRONYM {
		@Override
		public Element create() {
			return new AcronymElement();
		}
	},
	ADDRESS {
		@Override
		public Element create() {
			return new AddressElement();
		}
	},
	AREA {
		@Override
		public Element create() {
			return new AreaElement();
		}
	},
	B {
		@Override
		public Element create() {
			return new BElement();
		}
	},
	BASE {
		@Override
		public Element create() {
			return new BaseElement();
		}
	},
	BDO {
		@Override
		public Element create() {
			return new BdoElement();
		}
	},
	BIG {
		@Override
		public Element create() {
			return new BigElement();
		}
	},
	BLOCKQUOTE {
		@Override
		public Element create() {
			return new BlockquoteElement();
		}
	},
	BODY {
		@Override
		public Element create() {
			return new BodyElement();
		}
	},
	BR {
		@Override
		public Element create() {
			return new BrElement();
		}
	},
	BUTTON {
		@Override
		public Element create() {
			return new ButtonElement();
		}
	},
	CAPTION {
		@Override
		public Element create() {
			return new CaptionElement();
		}
	},
	CITE {
		@Override
		public Element create() {
			return new CiteElement();
		}
	},
	CODE {
		@Override
		public Element create() {
			return new CodeElement();
		}
	},
	COL {
		@Override
		public Element create() {
			return new ColElement();
		}
	},
	COLGROUP {
		@Override
		public Element create() {
			return new ColgroupElement();
		}
	},
	DD {
		@Override
		public Element create() {
			return new DdElement();
		}
	},
	DEL {
		@Override
		public Element create() {
			return new DelElement();
		}
	},
	DFN {
		@Override
		public Element create() {
			return new DfnElement();
		}
	},
	DIV {
		@Override
		public Element create() {
			return new DivElement();
		}
	},
	DL {
		@Override
		public Element create() {
			return new DlElement();
		}
	},
	DT {
		@Override
		public Element create() {
			return new DtElement();
		}
	},
	EM {
		@Override
		public Element create() {
			return new EmElement();
		}
	},
	EMBED {
		@Override
		public Element create() {
			return new EmbedElement();
		}
	},
	FIELDSET {
		@Override
		public Element create() {
			return new FieldsetElement();
		}
	},
	FORM {
		@Override
		public Element create() {
			return new FormElement();
		}
	},
	H1 {
		@Override
		public Element create() {
			return new H1Element();
		}
	},
	H2 {
		@Override
		public Element create() {
			return new H2Element();
		}
	},
	H3 {
		@Override
		public Element create() {
			return new H3Element();
		}
	},
	H4 {
		@Override
		public Element create() {
			return new H4Element();
		}
	},
	H5 {
		@Override
		public Element create() {
			return new H5Element();
		}
	},
	H6 {
		@Override
		public Element create() {
			return new H6Element();
		}
	},
	HEAD {
		@Override
		public Element create() {
			return new HeadElement();
		}
	},
	HR {
		@Override
		public Element create() {
			return new HrElement();
		}
	},
	HTML {
		@Override
		public Element create() {
			return new HtmlElement();
		}
	},
	I {
		@Override
		public Element create() {
			return new IElement();
		}
	},
	IFRAME {
		@Override
		public Element create() {
			return new IframeElement();
		}
	},
	IMG {
		@Override
		public Element create() {
			return new ImgElement();
		}
	},
	INPUT {
		@Override
		public Element create() {
			return new InputElement();
		}
	},
	INS {
		@Override
		public Element create() {
			return new InsElement();
		}
	},
	LABEL {
		@Override
		public Element create() {
			return new LabelElement();
		}
	},
	LEGEND {
		@Override
		public Element create() {
			return new LegendElement();
		}
	},
	LI {
		@Override
		public Element create() {
			return new LiElement();
		}
	},
	LINK {
		@Override
		public Element create() {
			return new LinkElement();
		}
	},
	KBD {
		@Override
		public Element create() {
			return new KbdElement();
		}
	},
	MAP {
		@Override
		public Element create() {
			return new MapElement();
		}
	},
	META {
		@Override
		public Element create() {
			return new MetaElement();
		}
	},
	NOSCRIPT {
		@Override
		public Element create() {
			return new NoscriptElement();
		}
	},
	OBJECT {
		@Override
		public Element create() {
			return new ObjectElement();
		}
	},
	OL {
		@Override
		public Element create() {
			return new OlElement();
		}
	},
	OPTGROUP {
		@Override
		public Element create() {
			return new OptgroupElement();
		}
	},
	OPTION {
		@Override
		public Element create() {
			return new OptionElement();
		}
	},
	P {
		@Override
		public Element create() {
			return new PElement();
		}
	},
	PARAM {
		@Override
		public Element create() {
			return new ParamElement();
		}
	},
	PRE {
		@Override
		public Element create() {
			return new PreElement();
		}
	},
	Q {
		@Override
		public Element create() {
			return new QElement();
		}
	},
	SAMP {
		@Override
		public Element create() {
			return new SampElement();
		}
	},
	SCRIPT {
		@Override
		public Element create() {
			return new ScriptElement();
		}
	},
	SELECT {
		@Override
		public Element create() {
			return new SelectElement();
		}
	},
	SMALL {
		@Override
		public Element create() {
			return new SmallElement();
		}
	},
	SPAN {
		@Override
		public Element create() {
			return new SpanElement();
		}
	},
	STRONG {
		@Override
		public Element create() {
			return new StrongElement();
		}
	},
	STYLE {
		@Override
		public Element create() {
			return new StyleElement();
		}
	},
	SUB {
		@Override
		public Element create() {
			return new SubElement();
		}
	},
	SUP {
		@Override
		public Element create() {
			return new SupElement();
		}
	},
	TABLE {
		@Override
		public Element create() {
			return new TableElement();
		}
	},
	TBODY {
		@Override
		public Element create() {
			return new TbodyElement();
		}
	},
	TD {
		@Override
		public Element create() {
			return new TdElement();
		}
	},
	TEXTAREA {
		@Override
		public Element create() {
			return new TextareaElement();
		}
	},
	TEXTCONTENT {
		@Override
		public Element create() {
			return new TextContent("");
		}
	},
	TFOOT {
		@Override
		public Element create() {
			return new TfootElement();
		}
	},
	TH {
		@Override
		public Element create() {
			return new ThElement();
		}
	},
	THEAD {
		@Override
		public Element create() {
			return new TheadElement();
		}
	},
	TITLE {
		@Override
		public Element create() {
			return new TitleElement();
		}
	},
	TR {
		@Override
		public Element create() {
			return new TrElement();
		}
	},
	TT {
		@Override
		public Element create() {
			return new TtElement();
		}
	},
	UL {
		@Override
		public Element create() {
			return new UlElement();
		}
	},
	VAR {
		@Override
		public Element create() {
			return new VarElement();
		}
	},
	AUDIO {
		@Override
		public Element create() {
			return new AudioElement();
		}
	};

	public static final EnumSet<ElementType> blockElements =
		EnumSet.of(ADDRESS, BLOCKQUOTE, DEL, DIV, DL, FIELDSET, FORM, H1, H2, H3, H4, H5, H6,
			HR, INS, NOSCRIPT, OL, P, PRE, SCRIPT, TABLE, UL);
	public static final EnumSet<ElementType> inlineElements =
		EnumSet.of(A, ABBR, ACRONYM, B, BDO, BIG, BR, BUTTON, CITE, CODE, DEL, DFN,
			EM, I, IMG, INS, INPUT, LABEL, MAP, OBJECT, KBD, Q, SAMP, SCRIPT,
			SELECT, SMALL, SPAN, STRONG, SUB, SUP, TEXTAREA, TT, VAR);
	public static final EnumSet<ElementType> BLOCK_AND_INLINE_ELEMENTS = union(blockElements, inlineElements);
	public static final EnumSet<ElementType> BLOCK_AND_INLINE_AND_TEXT_ELEMENTS = union(blockElements, inlineElements, EnumSet.of(TEXTCONTENT));
	public static final EnumSet<ElementType> INLINE_AND_TEXT_ELEMENTS = union(inlineElements, EnumSet.of(TEXTCONTENT));

	private static final Map<String, ElementType> fromName;

	public final String name;
	public final Predicate<Element> predicate;

	ElementType() {
		name = name().toLowerCase();
		predicate = object -> object.type == ElementType.this;
	}

	public final boolean isInline() {
		return inlineElements.contains(this);
	}

	public final boolean isBlock() {
		return blockElements.contains(this);
	}

	@Override
	public String toString() {
		return name;
	}

	public abstract Element create();

	@SafeVarargs
	public static EnumSet<ElementType> union(final EnumSet<ElementType> first, final EnumSet<ElementType>... rest) {
		final EnumSet<ElementType> union = EnumSet.copyOf(first);
		for (final EnumSet<ElementType> set : rest)
			union.addAll(set);
		return union;
	}

	static {
		fromName = new HashMap<>(values().length);
		for (final ElementType type : values())
			fromName.put(type.name, type);
	}

	public static ElementType fromName(final String name) {
		return fromName.get(name);
	}

	public static CharSequence toCommaDelimitedList(final ElementType... types) {
		final StringBuilder list = new StringBuilder(256);
		for (final ElementType type : types) {
			if (list.length() > 0)
				list.append(", ");
			list.append(type);
		}
		return list;
	}
}
