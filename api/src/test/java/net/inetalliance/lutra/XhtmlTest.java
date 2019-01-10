package net.inetalliance.lutra;

import junit.framework.TestCase;
import net.inetalliance.lutra.elements.Element;
import net.inetalliance.lutra.elements.ElementType;

public class XhtmlTest extends TestCase
{
	public void testConstructors()
	{
		for (final ElementType type : ElementType.values())
		{
			if (type != ElementType.TEXTCONTENT)
			{
				final Element element = type.create();
				assertEquals("Constructor type problem: " + element.getClass().getSimpleName(), type, element.type);
			}
		}
	}
}