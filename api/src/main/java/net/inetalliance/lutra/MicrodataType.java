package net.inetalliance.lutra;

public enum MicrodataType
{
	AGGREGATE_RATING("http://schema.org/AggregateRating"),
	BREADCRUMB("http://data-vocabulary.org/Breadcrumb"),
	OFFER("http://schema.org/Offer"),
	PERSON("http://schema.org/Person"),
	PRODUCT("http://schema.org/Product"),
	RATING("http://schema.org/Rating"),
	REVIEW("http://schema.org/Review"),
	WEBPAGE("http://schema.org/WebPage");

	public final String url;

	MicrodataType(final String url)
	{
		this.url = url;
	}

	@Override public String toString()
	{
		return url;
	}
}