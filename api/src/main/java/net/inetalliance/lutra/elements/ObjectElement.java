package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;
import net.inetalliance.lutra.rules.MayHaveAttribute;
import net.inetalliance.lutra.rules.MayHaveChild;
import net.inetalliance.types.www.ContentType;

import java.util.EnumSet;

import static net.inetalliance.funky.StringFun.secureUrl;


public class ObjectElement extends CommonAbstractElement<ObjectElement> implements InlineElement {
	@SuppressWarnings("unchecked")
	private static final ChildRule[] childRules =
		{
			new MayHaveChild(ElementType.union(ElementType.BLOCK_AND_INLINE_AND_TEXT_ELEMENTS,
				EnumSet.of(ElementType.PARAM)))
		};
	@SuppressWarnings("unchecked")
	private static final AttributeRule[] attributeRules =
		{
			new MayHaveAttribute(Attribute.union(Attribute.COMMON,
				EnumSet.of(Attribute.CLASSID, Attribute.CODEBASE, Attribute.HEIGHT,
					Attribute.NAME, Attribute.TYPE, Attribute.WIDTH,
					Attribute.ARCHIVE, Attribute.CODETYPE, Attribute.DATA,
					Attribute.DECLARE, Attribute.STANDBY, Attribute.TABINDEX,
					Attribute.USEMAP)))
		};

	public ObjectElement(final String text) {
		this(new TextContent(text));
	}

	public ObjectElement(final ObjectElementChild... children) {
		super(ObjectElement.class, ElementType.OBJECT, childRules, attributeRules, children);
	}

	@Override
	public ObjectElement copy() {
		return (ObjectElement) copyWithListeners();
	}

	public final String getArchive() {
		return getAttribute(Attribute.ARCHIVE);
	}

	public final String getClassId() {
		return getAttribute(Attribute.CLASSID);
	}

	public final String getCodeBase() {
		return getAttribute(Attribute.CODEBASE);
	}

	public final String getCodeType() {
		return getAttribute(Attribute.CODETYPE);
	}

	public final String getDeclare() {
		return getAttribute(Attribute.DECLARE);
	}

	public final Integer getHeight() {
		final String height = getAttribute(Attribute.HEIGHT);
		try {
			return height == null ? null : Integer.parseInt(height);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public final String getName() {
		return getAttribute(Attribute.NAME);
	}

	public final String getStandBy() {
		return getAttribute(Attribute.STANDBY);
	}

	public final String getTabIndex() {
		return getAttribute(Attribute.TABINDEX);
	}

	public final String getType() {
		return getAttribute(Attribute.TYPE);
	}

	public final Integer getWidth() {
		final String width = getAttribute(Attribute.WIDTH);
		try {
			return width == null ? null : Integer.parseInt(width);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	@Override
	protected boolean isClosed() {
		return false;
	}

	public final boolean isUseMap() {
		return "usemap".equals(getAttribute(Attribute.USEMAP));
	}

	@Override
	public void secure() {
		setData(secureUrl(getData()));
	}

	public final ObjectElement setData(final String value) {
		setAttribute(Attribute.DATA, value);
		return this;
	}

	public final String getData() {
		return getAttribute(Attribute.DATA);
	}

	public final ObjectElement setArchive(final String value) {
		setAttribute(Attribute.ARCHIVE, value);
		return this;
	}

	public final ObjectElement setClassId(final String value) {
		setAttribute(Attribute.CLASSID, value);
		return this;
	}

	public final ObjectElement setCodeBase(final String value) {
		setAttribute(Attribute.CODEBASE, value);
		return this;
	}

	public final ObjectElement setCodeType(final String value) {
		setAttribute(Attribute.CODETYPE, value);
		return this;
	}

	public final ObjectElement setDeclare(final String value) {
		setAttribute(Attribute.DECLARE, value);
		return this;
	}

	public final ObjectElement setHeight(final int value) {
		return setHeight(Integer.toString(value));
	}

	public final ObjectElement setHeight(final String value) {
		setAttribute(Attribute.HEIGHT, value);
		return this;
	}

	public final ObjectElement setName(final String value) {
		setAttribute(Attribute.NAME, value);
		return this;
	}

	public final ObjectElement setStandBy(final String value) {
		setAttribute(Attribute.STANDBY, value);
		return this;
	}

	public final ObjectElement setTabIndex(final String value) {
		setAttribute(Attribute.TABINDEX, value);
		return this;
	}

	public final ObjectElement setType(final ContentType value) {
		return setType(value.value);
	}

	public final ObjectElement setType(final String value) {
		setAttribute(Attribute.TYPE, value);
		return this;
	}

	public final ObjectElement setUseMap(final boolean value) {
		setAttribute(Attribute.USEMAP, value ? "usemap" : null);
		return this;
	}

	public final ObjectElement setWidth(final int value) {
		return setWidth(Integer.toString(value));
	}

	public final ObjectElement setWidth(final String value) {
		setAttribute(Attribute.WIDTH, value);
		return this;
	}

}