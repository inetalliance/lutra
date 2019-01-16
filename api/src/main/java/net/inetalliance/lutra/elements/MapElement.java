package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.*;

import java.util.EnumSet;

public class MapElement extends CommonAbstractElement<MapElement> implements InlineElement
{

	private static final EnumSet<Attribute> allowed;

	static
	{
		allowed = EnumSet.of(Attribute.NAME);
		allowed.addAll(Attribute.COMMON);
	}

	private static final ChildRule[] childRules =
			{
					new MayHaveChild(ElementType.union(ElementType.blockElements,
					                                   EnumSet.of(ElementType.AREA)))
			};
	private static final AttributeRule[] attributeRules =
			{
					new MayHaveAttribute(allowed),
					new MustHaveAttribute(Attribute.ID),
			};

	public MapElement(final MapElementChild... children)
	{
		super(MapElement.class, ElementType.MAP, childRules, attributeRules, children);
	}

	@Override
	public MapElement copy() {
		return (MapElement) copyWithListeners();
	}
}