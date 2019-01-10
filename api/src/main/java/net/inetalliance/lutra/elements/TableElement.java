package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.*;

import java.util.Collection;
import java.util.EnumSet;

public class TableElement extends CommonAbstractElement<TableElement> implements BlockElement
{

	private static final ChildRule[] childRules =
			{
					new MayHaveChild(EnumSet.of(ElementType.CAPTION, ElementType.COL, ElementType.COLGROUP, ElementType.THEAD,
					                            ElementType.TFOOT, ElementType.TBODY, ElementType.TR)),
					new FirstChildOrNotAppear(ElementType.CAPTION),
					new ChildRule() // table children logic is complicated so it's hardcoded here
					{
						@SuppressWarnings({"EnumSwitchStatementWhichMissesCases"})
						@Override
						public void validate(final Element parent, final Collection<Element> children, final ValidationErrors errors, final boolean strict)
						{
							final EnumSet<ElementType> capColColgroup = EnumSet.of(ElementType.CAPTION, ElementType.COL, ElementType.COLGROUP);
							boolean seenNonCaption = false;
							boolean foundThead = false;
							boolean foundTfoot = false;
							boolean foundTbody = false;
							boolean foundTr = false;
							for (final Element child : children)
							{
								final boolean inCapColColgroup = capColColgroup.contains(child.type);
								if (!seenNonCaption && !inCapColColgroup)
									seenNonCaption = true;
								else if (seenNonCaption && inCapColColgroup)
									errors.add(child, String.format("%s elements of must appear at the top of a %s element", capColColgroup, ElementType.TABLE));
								final String onlyOnce = "%s may only have one child of type %s";
								final String needAnother = "%s may only have a %s if it also has a %s";
								final String excludes = "%s may only have a %s if it does not also have a %s";
								switch (child.type)
								{
									case THEAD:
										if (foundThead)
											errors.add(child, String.format(onlyOnce, ElementType.TABLE, ElementType.THEAD));
										foundThead = true;
										if (!containsOfType(children, ElementType.TBODY))
											errors.add(child, String.format(needAnother, ElementType.TABLE, ElementType.THEAD, ElementType.TBODY));
										break;
									case TFOOT:
										if (foundTfoot)
											errors.add(child, String.format(onlyOnce, ElementType.TABLE, ElementType.TFOOT));
										foundTfoot = true;
										if (!containsOfType(children, ElementType.TBODY))
											errors.add(child, String.format(needAnother, ElementType.TABLE, ElementType.TFOOT, ElementType.TBODY));
										break;
									case TBODY:
										if (foundTbody)
											errors.add(child, String.format(onlyOnce, ElementType.TABLE, ElementType.TBODY));
										foundTbody = true;
										if (containsOfType(children, ElementType.TR))
											errors.add(child, String.format(excludes, ElementType.TABLE, ElementType.TBODY, ElementType.TR));
										break;
									case TR:
										foundTr = true;
										if (containsOfType(children, ElementType.TBODY))
											errors.add(child, String.format(excludes, ElementType.TABLE, ElementType.TR, ElementType.TBODY));
										break;
								}
							}
							if (!foundTbody && !foundTr)
								errors.add(parent, String.format("%s must include one of the following elements: %s, %s", ElementType.TABLE, ElementType.TR, ElementType.TBODY));
						}
					}
			};
	private static final AttributeRule[] attributeRules =
			{
					new MayHaveAttribute(Attribute.union(Attribute.COMMON,
					                                     EnumSet.of(Attribute.BORDER, Attribute.CELLPADDING,
					                                                Attribute.CELLSPACING, Attribute.SUMMARY,
					                                                Attribute.WIDTH, Attribute.FRAME, Attribute.RULES)))
			};

	public TableElement(final TableElementChild... children)
	{
		super(TableElement.class, ElementType.TABLE, childRules, attributeRules, children);
	}

	@Override
	public TableElement clone() throws CloneNotSupportedException
	{
		return (TableElement) cloneWithListeners();
	}
}