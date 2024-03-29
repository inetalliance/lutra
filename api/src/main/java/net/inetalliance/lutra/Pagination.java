package net.inetalliance.lutra;

import net.inetalliance.lutra.elements.*;
import net.inetalliance.lutra.filters.IdStartsWithPredicate;
import net.inetalliance.lutra.filters.queries.IntegerClassQuery;
import net.inetalliance.lutra.markers.OddEven;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.*;

public class Pagination {
	public static final Predicate<Element> paginate = new IdStartsWithPredicate("paginate");
	public static final Predicate<Element> paginateWrapper = new IdStartsWithPredicate("paginationWrapper");
	public static final IntegerClassQuery paginationPerPage = new IntegerClassQuery("per-page-([0-9]+)");
	public static final IntegerClassQuery paginationMaxPages = new IntegerClassQuery("max-pages-([0-9]+)");

	private static final String PAGINATION_START_PARAMETER = "start";

	public static int paginate(final int size, final LazyDocument page,
	                           final Locale locale, final String originalRequestUrlWithQuery) {
		return paginate(size, page, locale, () -> page.byId.values().stream().filter(paginate).iterator(),
				originalRequestUrlWithQuery);
	}

	public static int paginate(final int size, final LazyDocument page,
	                           final Locale locale, final Iterable<Element> paginationElements,
	                           final String originalRequestUrlWithQuery) {
		if (!paginationElements.iterator().hasNext()) {
			return 0;
		}
		for (final Element paginationElement : paginationElements) {
			final Integer perPage = paginationPerPage.query(paginationElement);
			if (perPage != null) {
				return paginate(size, page, locale, paginationElements, originalRequestUrlWithQuery, perPage);
			}
		}
		throw new IllegalStateException(
				String.format("Expecting element with css class to match \"%s\"", paginationPerPage.pattern.pattern()));
	}

	public static <T> Collection<T> paginate(final Collection<T> collection, final LazyDocument page,
	                                         final Locale locale, final String originalRequestUrlWithQuery) {
		return paginate(collection, page, locale, () -> page.byId.values().stream().filter(paginate).iterator(),
				originalRequestUrlWithQuery);
	}

	public static <T> Collection<T> paginate(final Collection<T> collection, final LazyDocument page,
	                                         final Locale locale, final Iterable<Element> paginationElements,
	                                         final String originalRequestUrlWithQuery) {
		if (!paginationElements.iterator().hasNext()) {
			return collection;
		}
		for (final Element paginationElement : paginationElements) {
			final Integer perPage = paginationPerPage.query(paginationElement);
			if (perPage != null) {
				return paginate(collection, page, locale, paginationElements, originalRequestUrlWithQuery, perPage);
			}
		}
		throw new IllegalStateException(
				String.format("Expecting element with css class to match \"%s\"", paginationPerPage.pattern.pattern()));
	}

	public static int paginate(final int size, final LazyDocument page,
	                           final Locale locale, final String originalRequestUrlWithQuery,
	                           final int perPage) {
		return paginate(size, page, locale, () -> page.byId.values().stream().filter(paginate).iterator(),
				originalRequestUrlWithQuery, perPage);
	}

	public static <T> Collection<T> paginate(final Collection<T> collection, final LazyDocument page,
	                                         final Locale locale, final String originalRequestUrlWithQuery,
	                                         final int perPage) {
		return paginate(collection, page, locale, () -> page.byId.values().stream().filter(paginate).iterator(),
				originalRequestUrlWithQuery, perPage);
	}

	private static final int PAGINATION_DEFAULT_MAX_PAGES = 10;

	public static <T> Collection<T> paginate(final Collection<T> collection, final LazyDocument page,
	                                         final Locale locale, final Iterable<Element> paginationElements,
	                                         final String originalRequestUrlWithQuery, final int perPage) {
		if (!paginationElements.iterator().hasNext()) {
			return collection.stream().limit(perPage).collect(toList());
		} else {
			final int size = collection.size();
			final int start = paginate(size, page, locale, paginationElements, originalRequestUrlWithQuery, perPage);
			if (size <= perPage) {
				return collection;
			}
			return new PaginatedCollection<>(collection, start, start + perPage);
		}

	}

	private static Integer parseParam(final String param) {
		if(param != null && !param.isEmpty()) {
			try {
				return Integer.parseInt(param);
			} catch (NumberFormatException e) {
				// this is here to stop problems when bots pass weird values
			}
		}
		return 0;
	}

	public static int paginate(final int size, final LazyDocument page,
	                           final Locale locale, final Iterable<Element> paginationElements,
	                           final String originalRequestUrlWithQuery, final int perPage) {
		if (size <= perPage) {
			// don't need pagination
			paginationElements.forEach(Element::remove);
			page.byId.values().stream().filter(paginateWrapper).forEach(Element::remove);
			return 0;
		}
		Integer maxPages = null;
		for (final Element element : paginationElements) {
			maxPages = paginationMaxPages.query(element);
			if (maxPages != null) {
				break;
			}
		}
		if (maxPages == null) {
			maxPages = PAGINATION_DEFAULT_MAX_PAGES;
		}
		final String baseUrl = stripParameters(originalRequestUrlWithQuery);
		final Map<String, List<String>> parameters = parseParameters(
				getQueryString(originalRequestUrlWithQuery));
		final String startParameter = parameters.getOrDefault(PAGINATION_START_PARAMETER, List.of())
				.stream().findFirst().orElse(null);
		final int start = parseParam(startParameter);
		final int totalPages = (int) Math.ceil(size / (double) perPage);
		final NavElement nav = new NavElement();
		final UlElement ul = new UlElement();
		ul.setClass("pagination");
		nav.appendChild(ul);
		if (start > 0) {
			final Element previousLi = makePaginationLi(baseUrl, parameters, start - perPage,
					 String.format("Previous %d", perPage));
			previousLi.addClass("previous");
			ul.appendChild(previousLi);
		}
		final int firstPage;
		final int lastPage;
		if (totalPages > maxPages) {
			final int currentPage = start / perPage;
			firstPage = Math.max(0, currentPage - maxPages / 2);
			lastPage = Math.min(totalPages, currentPage + maxPages / 2 + 1);
		} else {
			firstPage = 0;
			lastPage = totalPages;
		}
		final OddEven oddEven = new OddEven();
		for (int i = firstPage; i < lastPage; i++) {
			final int linkStartParam = i * perPage;
			final Element li = makePaginationLi(baseUrl, parameters, linkStartParam != start, linkStartParam,
					Integer.toString(i + 1), linkStartParam == start ? "Current Page" : "Page " + (i + 1));
			oddEven.mark(li);
			ul.appendChild(li);
		}
		final int afterPage = size - (start + perPage);
		final int onNextPage;
		if (afterPage <= 0) {
			onNextPage = 0;
		} else {
			onNextPage = Math.min(afterPage, perPage);
		}
		if (onNextPage > 0) {
			final Element nextLi = makePaginationLi(baseUrl, parameters, start + perPage,
					 String.format("Next %d", onNextPage));
			nextLi.addClass("next");
			ul.appendChild(nextLi);
		}
		for (final Element element : paginationElements) {
			element.removeChildren();
			nav.put("aria-label", null);
			var label = (String) element.get("aria-label");
			if (label != null && !label.isEmpty()) {
				nav.put("aria-label", label);
				element.put("aria-label", null);
			}
			element.appendChild(nav.copy());
		}
		return start;
	}

	public static Map<String, List<String>> parseParameters(final String queryString) {
		final Map<String, List<String>> params = new HashMap<>();
		if (queryString == null || queryString.isEmpty()) {
			return params;
		}
		int index = 0;
		while (index < queryString.length()) {
			final int equalsIndex = queryString.indexOf('=', index);
			if (equalsIndex < 0) {
				break;
			}
			final String key = URLDecoder.decode(queryString.substring(index, equalsIndex), StandardCharsets.UTF_8);
			final int ampersandIndex = queryString.indexOf('&', equalsIndex);
			final String value;
			if (ampersandIndex < 0) {
				value = URLDecoder.decode(queryString.substring(equalsIndex + 1), StandardCharsets.UTF_8);
				index = equalsIndex + 1;
			} else {
				value = URLDecoder.decode(queryString.substring(equalsIndex + 1, ampersandIndex), StandardCharsets.UTF_8);
				index = ampersandIndex + 1;
			}
			if (value != null) {
				params.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
			}
		}
		return params;
	}

	private static final Pattern QUESTION_MARK = Pattern.compile("\\?");

	private static String getQueryString(final String url) {
		final String[] tokens = QUESTION_MARK.split(url, 2);
		return tokens.length > 1 ? tokens[1] : "";
	}

	private static String stripParameters(final String url) {
		return QUESTION_MARK.split(url, 2)[0];
	}

	private static Element makePaginationLi(final String baseUrl, final Map<String, List<String>> parameters,
	                                        final int linkStartParam, final String label) {
		return makePaginationLi(baseUrl,parameters, true,linkStartParam,label, null);
	}
	private static Element makePaginationLi(final String baseUrl, final Map<String, List<String>> parameters,
	                                        final boolean addLink, final int linkStartParam, final String label,
	                                        final String srOnly) {
		if (linkStartParam == 0) {
			parameters.remove(PAGINATION_START_PARAMETER);
		} else {
			parameters.put(PAGINATION_START_PARAMETER, Collections.singletonList(Integer.toString(linkStartParam)));
		}
		var li = new LiElement().setClass("page-item");
		var labelSpan = new SpanElement(label);
		var srSpan = srOnly == null ? null : new SpanElement(srOnly).setClass("sr-only");
		if(addLink) {
			var a = new AElement(labelSpan);
			li.appendChild(a.setHref(createUrl(baseUrl,parameters))) ;
			if(srOnly != null) {
				a.appendChild(srSpan);
			}
		} else {
			li.addClass("active");
			li.appendChild(labelSpan);
			if(srOnly != null) {
				li.appendChild(srSpan);
			}
		}
		return li;
	}

	private static String createUrl(final String fullUrl, final Map<String, List<String>> parameters) {
		if (parameters == null || parameters.isEmpty()) {
			return fullUrl;
		}
		final StringBuilder buffer = new StringBuilder(fullUrl);
		boolean needAmpersand = false;
		final int questionIndex = buffer.indexOf("?");
		if (questionIndex < 0) {
			buffer.append('?');
		} else {
			needAmpersand = questionIndex < buffer.length() - 1;
		}
		// copy parameters (Note: POST parameters will be converted to GET parameters)
		toQueryString(buffer, parameters, needAmpersand);
		return buffer.toString();
	}

	private static void toQueryString(final StringBuilder buffer, final Map<String, List<String>> parameters,
	                                  boolean needAmpersand) {
		for (final Map.Entry<String, List<String>> entry : parameters.entrySet()) {
			final String param = entry.getKey();
			if (entry.getValue() != null) {
				for (final String value : entry.getValue()) {
					if (value != null) {
						if (needAmpersand) {
							buffer.append('&');
						}
						buffer.append(param);
						buffer.append('=');
						buffer.append(URLEncoder.encode(value, StandardCharsets.UTF_8));
						needAmpersand = true;
					}
				}
			}
		}
	}
}
