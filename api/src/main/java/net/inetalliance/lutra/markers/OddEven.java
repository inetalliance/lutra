package net.inetalliance.lutra.markers;

import net.inetalliance.lutra.elements.Element;

public class OddEven extends Marker<Element>
{
	private boolean odd;
	private static final String ODD = "odd";
	private static final String EVEN = "even";

	@Override
	public <T extends Element> T mark(final T element)
	{
		mark(element, odd);
		odd = !odd;
		return element;
	}

	protected static <T extends Element> void mark(final T element, final boolean odd)
	{
		if (odd)
		{
			element.removeClass(EVEN);
			element.addClass(ODD);
		}
		else
		{
			element.removeClass(ODD);
			element.addClass(EVEN);
		}
	}

	public OddEven flip()
	{
		odd = !odd;
		return this;
	}

	public boolean isOdd()
	{
		return odd;
	}

	public OddEven(final boolean odd)
	{
		this.odd = odd;
	}

	public OddEven()
	{
		this(false);
	}
}
