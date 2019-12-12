package net.inetalliance.lutra.elements;

import static net.inetalliance.lutra.rules.AttributeRule.*;
import static net.inetalliance.lutra.rules.ChildRule.*;

public class ArticleElement
	extends CommonAbstractElement<ArticleElement>
	implements BlockElement {

	public ArticleElement(final String text) {
		this(new TextContent(text));
	}

	public ArticleElement(final ArticleElementChild... children) {
		super(ArticleElement.class, ElementType.ARTICLE,
			ANY_BLOCK_OR_INLINE_OR_TEXT_ELEMENTS, ANY_COMMON_ATTRIBUTES, children);
	}

	@Override
	protected boolean isClosed() {
		return false;
	}

	@Override
	public ArticleElement copy() {
		return (ArticleElement) copyWithListeners();
	}
}