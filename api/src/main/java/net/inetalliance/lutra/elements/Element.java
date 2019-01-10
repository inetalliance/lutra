package net.inetalliance.lutra.elements;

import net.inetalliance.funky.Escaper;
import net.inetalliance.funky.Funky;
import net.inetalliance.funky.StringFun;
import net.inetalliance.funky.iterators.FunctorBreadthFirstIterator;
import net.inetalliance.lutra.DocumentBuilder;
import net.inetalliance.lutra.listeners.CloneListener;
import net.inetalliance.lutra.listeners.InstanceListener;
import net.inetalliance.lutra.listeners.PreAddChildListener;
import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;
import net.inetalliance.lutra.rules.ValidationErrors;
import net.inetalliance.lutra.util.LutraDebugger;
import net.inetalliance.types.localized.LocalizedString;
import net.inetalliance.types.util.ReverseListIterator;
import net.inetalliance.types.util.UnmodifiableIterator;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Arrays.asList;
import static java.util.stream.Stream.of;
import static net.inetalliance.funky.Funky.throwing;
import static net.inetalliance.lutra.elements.Attribute.*;

public abstract class Element implements Cloneable {
	public static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n";
	public static final Pattern SAX_ENTITY_ERROR = Pattern.compile(".*\".+\" was referenced, but not declared.*");
	protected static final String[] NO_CLASSES = new String[0];
	private static final String EDITABLE_IN_PLACE = "editable-in-place";
	private static final Pattern STYLE_DELIMITERS = Pattern.compile("[:;]");
	private static final String TOOLTIPPED = "tooltipped";
	private static final Pattern EDIT_IN_PLACE_CLASS_PATTERN = Pattern.compile("eip_.+_.+");
	private static final String EDIT_IN_PLACE_CLASS_FORMAT = "eip_%s_%s";
	private static Pattern cssPixels = Pattern.compile("([0-9]+)px");
	public final ElementType type;
	private final Map<Attribute, String> attributes;
	private final ChildRule[] childRules;
	private final AttributeRule[] attributeRules;
	private final List<Element> children;
	private Element parent;
	private DocumentLocation location;
	private Map<String, Object> data;
	private HashMap<String, Method> dataCloneMethods;

	protected Element(final ElementType type, final ChildRule[] childRules, final AttributeRule[] attributeRules,
	                  final Child... children) {
		if (type == null) {
			throw new IllegalArgumentException("Element type must not be null");
		}
		this.type = type;
		this.childRules = childRules;
		this.attributeRules = attributeRules;
		attributes = new EnumMap<>(Attribute.class);
		this.children = new ArrayList<>(children.length);
		for (final Child child : children)
			addChild((Element) child);
		if (LutraDebugger.isEnabled()) {
			final DocumentLocation location = DocumentLocation.fromStack();
			if (location != null)
				this.location = location;
		}
	}

	private static Integer getCssPixels(final String style) {
		if (style != null) {
			final Matcher matcher = cssPixels.matcher(style);
			if (matcher.find()) {
				final String value = matcher.group(1);
				try {
					return Integer.parseInt(value);
				} catch (NumberFormatException e) {
					return null;
				}
			}
		}
		return null;
	}

	private static void outputAttribute(final Appendable output, final Attribute key,
	                                    final String attributeValue) throws IOException {
		final String trimmedValue = attributeValue.trim();
		output.append(' ').append(key.toString()).append("=\"");
		final String escaped = Escaper.html40.escape(trimmedValue);
		output.append(escaped.replaceAll("&amp;", "&"));  // stupid google thing [BJX-87]
		output.append('"');
	}

	protected static void tab(final Appendable output, final int depth)
		throws IOException {
		output.append('\n');
		for (int i = 0; i < depth; i++)
			output.append(' ');
	}

	public static boolean containsOfType(final Collection<Element> elements, final ElementType type) {
		for (final Element element : elements) {
			if (element.type == type)
				return true;
		}
		return false;
	}

	public Element addChild(final Element... children) {
		return addChild(null, children);
	}

	public final Map<Attribute, String> getAttributes() {
		return attributes;
	}

	public final DocumentLocation getLocation() {
		return location;
	}

	public void setLocation(final DocumentLocation location) {
		this.location = location;
	}

	public final Element getParent() {
		return parent;
	}

	@Override
	public abstract Element clone()
		throws CloneNotSupportedException;


	public final Element addChild(final PreAddChildListener listener, final Iterable<? extends Element> children) {
		for (final Element child : children)
			addChild(listener, child);
		return this;
	}

	public Element addChild(final PreAddChildListener listener, final Element... children) {
		for (final Element child : children) {
			if (child != null) {
				if (listener != null)
					listener.preAdd(child, this);
				this.children.add(child);
				child.parent = this;
			}
		}
		return this;
	}

	public final Element addClass(final Enum<?>... cssClasses) {
		addClass(Arrays.stream(cssClasses).map(StringFun::enumToCamelCase).toArray(String[]::new));
		return this;
	}

	public void addHelp(final String help) {
		if (help == null || help.trim().length() == 0) {
			removeTooltip();
			hide();
		} else {
			setTooltip(help);
			show();
		}
	}

	public void addHelp(final LocalizedString localizedHelp, final Locale locale) {
		addHelp(localizedHelp == null ? null : localizedHelp.get(locale));
	}

	public void removeTooltip() {
		removeAttribute(TITLE);
		removeClass(TOOLTIPPED);
	}

	protected void removeAttribute(final Attribute attribute) {
		attributes.remove(attribute);
	}

	public Element removeClass(final String... cssClasses) {
		// only worth implementing in CommonAbstractElement subclass
		return this;
	}

	public Element hide() {
		setStyle("display", "none");
		return this;
	}

	public Element setStyle(final String style, final String value) {
		final String styleValue = getAttribute(STYLE);
		if (styleValue == null || styleValue.length() == 0)
			setAttribute(STYLE, String.format("%s:%s;", style, value));
		else {
			final StringBuilder buffer = new StringBuilder(0);
			final String[] styleTokens = STYLE_DELIMITERS.split(styleValue);
			for (int i = 0; i < styleTokens.length; i += 2) {
				final String token = styleTokens[i];
				if (!token.equals(style) && i + 1 < styleTokens.length)
					buffer.append(token).append(':').append(styleTokens[i + 1]).append(';');
			}
			buffer.append(style).append(':').append(value).append(';');
			setAttribute(STYLE, buffer.toString());
		}
		return this;
	}

	public String getAttribute(final Attribute attribute) {
		return attributes.get(attribute);
	}

	public Element setAttribute(final Attribute attribute, final String value) {
		if (value == null)
			attributes.remove(attribute);
		else
			attributes.put(attribute, value);
		return this;
	}

	public Element setTooltip(final String tooltip) {
		if (tooltip == null || tooltip.trim().length() == 0)
			removeTooltip();
		else {
			setTitle(tooltip);
			addClass(TOOLTIPPED);
		}
		return this;
	}

	public Element setTitle(final String title) {
		throw new UnsupportedOperationException(
			String.format("Element of type %s cannot have attribute %s", type, TITLE));
	}

	public Element addClass(final String... cssClasses) {
		throw new UnsupportedOperationException(
			String.format("Elements of type %s cannot have a %s attribute", type, CLASS));
	}

	public Element show() {
		removeStyle("display");
		return this;
	}

	public Element removeStyle(final String style) {
		final String styleValue = getAttribute(STYLE);
		if (styleValue != null) {
			if (styleValue.length() > 0) {
				final StringBuilder buffer = new StringBuilder(0);
				final String[] styleTokens = STYLE_DELIMITERS.split(styleValue);
				for (int i = 0; i < styleTokens.length; i += 2) {
					final String token = styleTokens[i];
					if (!token.equals(style) && i + 1 < styleTokens.length)
						buffer.append(token).append(':').append(styleTokens[i + 1]).append(';');
				}
				if (buffer.length() == 0)
					removeAttribute(STYLE);
				else
					setAttribute(STYLE, buffer.toString());
			} else
				removeAttribute(STYLE);
		}
		return this;
	}

	public Element addRemoveClass(final boolean add, final String... cssClasses) {
		for (final String cssClass : cssClasses) {
			if (add)
				addClass(cssClass);
			else
				removeClass(cssClass);
		}
		return this;
	}

	public void addToTop(final Element child) {
		if (child != null) {
			children.add(0, child);
			child.parent = parent;
		}
	}

	public Element clone(final Consumer<Element> functor, final Predicate<? super Element> predicate,
	                     final Element... elementsToRemove) {
		final Element clone;
		if (elementsToRemove.length == 0) {
			// if none specified, check all descendants
			clone = cloneWithListeners(List.of());
			Funky.stream(clone.getTree()).filter(predicate).forEach(functor);
		} else {
			final Collection<InstanceListener> listeners = new ArrayList<>(elementsToRemove.length);
			of(elementsToRemove).filter(predicate).map(InstanceListener::new).forEach(listeners::add);
			clone = cloneWithListeners(listeners);
			listeners.stream().map(InstanceListener::getClone).forEach(functor);
			return clone;
		}
		return clone;
	}

	/**
	 * Returns an iterable including this element and all its descendants.
	 *
	 * @return an iterable including this element and all its descendants.
	 */
	public Iterable<Element> getTree() {
		return () -> new FunctorBreadthFirstIterator<>(this, e -> e.getChildren().iterator());
	}

	@SuppressWarnings({"unchecked"})
	public Element cloneWithListeners(final Iterable<? extends CloneListener> listeners) {
		final Element clone = type.create();
		clone.attributes.putAll(attributes);
		for (final Element child : children)
			clone.addChild(child.cloneWithListeners(listeners));
		clone.location = location;
		if (listeners != null) {
			for (final CloneListener listener : listeners)
				listener.cloned(this, clone);
		}
		// clone data map
		if (data != null) {
			clone.data = new HashMap<>(data.size());
			clone.dataCloneMethods = (HashMap<String, Method>) dataCloneMethods.clone();
			try {
				for (final Map.Entry<String, Object> entry : data.entrySet()) {
					final String key = entry.getKey();
					final Object value = entry.getValue();
					if (value instanceof String)
						clone.data.put(key, value);
					else {
						final Method cloneMethod = dataCloneMethods.get(key);
						final Object clonedValue = cloneMethod.invoke(value);
						clone.data.put(key, clonedValue);
					}
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return clone;
	}

	public Element cloneWithListeners() {
		return cloneWithListeners(null);
	}

	public void disableEditInPlace() {
		removeClass(EDIT_IN_PLACE_CLASS_PATTERN);
		removeClass(EDITABLE_IN_PLACE);
		removeClass("localized");
	}

	public Collection<String> removeClass(final Pattern pattern) {
		// only worth implementing in CommonAbstractElement subclass
		return Collections.emptyList();
	}

	public void enableEditInPlace(final Object chain, final String property) {
		enableEditInPlace(chain, property, false);
	}

	public void enableEditInPlace(final Object chain, final String property,
	                              final boolean localized) {
		removeClass(EDIT_IN_PLACE_CLASS_PATTERN);
		addClass(EDITABLE_IN_PLACE);
		addClass(String.format(EDIT_IN_PLACE_CLASS_FORMAT, chain, property));
		if (localized)
			addClass("localized");
	}

	public String escapeAbbreviated() {
		return Escaper.html40.escape(toStringAbbreviated());
	}

	public String toStringAbbreviated() {
		final StringBuilder output = new StringBuilder(32);
		output.append('<').append(type);
		for (final Map.Entry<Attribute, String> entry : attributes.entrySet()) {
			final String attributeValue = entry.getValue();
			final String trimmedValue = attributeValue.trim();
			if (trimmedValue != null) {
				output.append(' ').append(entry.getKey()).append("=\"");
				output.append(Escaper.html40.escape(trimmedValue));
				output.append('"');
			}
		}
		if (isClosed())
			output.append('/');
		else
			output.append(">...</").append(type);
		output.append('>');
		return output.toString();
	}

	protected boolean isClosed() {
		return children.isEmpty();
	}

	public String escapeChildrenForJavascript() {
		return Escaper.js.escape(childrenToString());
	}

	public String childrenToString() {
		return childrenToString(false);
	}

	public String childrenToString(final boolean pretty) {
		final StringBuilder result = new StringBuilder(1024);
		try {
			for (final Element child : children)
				outputChild(result, child, pretty, 0, null, null);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return result.toString();
	}

	public String escapeForJavascript() {
		return Escaper.js.escape(toString());
	}

	@Override
	public String toString() {
		try {
			return toString(false);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public Object get(final String key) {
		return data == null ? null : data.get(key);
	}

	public Element up(final Predicate<Element> predicate) {
		return Funky.stream(getAncestors()).filter(predicate).findFirst().orElse(null);
	}

	public Element down(final Predicate<Element> predicate) {
		return Funky.stream(getDescendants()).filter(predicate).findFirst().orElse(null);
	}

	public Iterable<Element> getAncestors() {
		return Funky.recurse(Element::getParent, parent);
	}

	public Element getFirstChild() {
		return children.isEmpty() ? null : children.get(0);
	}

	public Element getFirstDescendantOfType(final ElementType... types) {
		for (final Element descendant : getDescendants()) {
			for (final ElementType type : types) {
				if (descendant.type == type)
					return descendant;
			}
		}
		return null;
	}

	/**
	 * Returns an iterable including this element's descendants.
	 *
	 * @return an iterable including this element's descendants.
	 */
	public Iterable<Element> getDescendants() {
		return ()-> new FunctorBreadthFirstIterator<>(children, Element::getChildren);
	}

	public Integer getHeightStyle() {
		return getCssPixels(getStyle("height"));
	}

	public String getStyle(final String style) {
		final String styleValue = getAttribute(STYLE);
		if (styleValue != null && styleValue.length() > 0) {
			final String[] styleTokens = STYLE_DELIMITERS.split(styleValue);
			for (int i = 0; i < styleTokens.length; i++) {
				final String token = styleTokens[i];
				if (token.equals(style) && i + 1 < styleTokens.length)
					return styleTokens[i + 1];
			}
		}
		return null;
	}

	public final Element getLastChild() {
		return children.isEmpty() ? null : children.get(children.size() - 1);
	}

	public Element getNextSibling() {
		if (parent == null)
			return null;
		boolean foundThis = false;
		for (final Element child : parent.children) {
			if (foundThis)
				return child;
			else if (child == this)
				foundThis = true;
		}
		return null;
	}

	public Iterable<Element> getSiblingsAfter() {
		return () -> {
			final ChildIterator iterator = new ChildIterator(parent);
			while (iterator.hasNext()) {
				if (Element.this.equals(iterator.next()))
					break;
			}
			return iterator;
		};
	}

	public Iterable<Element> getSiblingsBefore() {
		return () -> new UnmodifiableIterator<>(new ReverseListIterator<>(parent.children, Element.this));
	}

	public Map<String, String> getStyles() {
		final String styleValue = getAttribute(STYLE);
		final String[] styleTokens = STYLE_DELIMITERS.split(styleValue);
		final Map<String, String> map = new HashMap<>(styleTokens.length >> 1);
		for (int i = 0; i < styleTokens.length; i += 2)
			map.put(styleTokens[i].trim(), styleTokens[i + 1]);
		return map;
	}

	public String getText() {
		final TextContent textContent = getFirstChildOfType(TextContent.class);
		return textContent == null ? null : textContent.getText();
	}

	public abstract Element setText(final String text);

	public <E extends Element> E getFirstChildOfType(final Class<E> type) {
		for (final Element child : children) {
			if (type.isInstance(child))
				return type.cast(child);
		}
		return null;
	}

	public Integer getWidthStyle() {
		return getCssPixels(getStyle("width"));
	}

	public final boolean hasChildren() {
		return !children.isEmpty();
	}

	public <E extends Enum<E>> boolean hasClass(final E cssClass) {
		return hasClass(StringFun.enumToCamelCase(cssClass));
	}

	public boolean hasClass(final Pattern pattern) {
		for (final String c : getClasses()) {
			if (pattern.matcher(c).matches())
				return true;
		}
		return false;
	}

	public String[] getClasses() {
		return NO_CLASSES;
	}

	public void insertAfter(final Element newSibling) {
		final int index = parent.children.indexOf(this);
		parent.children.add(index + 1, newSibling);
		newSibling.parent = parent;
	}

	public void insertBefore(final Element newSibling) {
		final int index = parent.children.indexOf(this);
		parent.children.add(index, newSibling);
		newSibling.parent = parent;
	}

	public boolean isEditableInPlace() {
		return hasClass(EDITABLE_IN_PLACE);
	}

	public boolean hasClass(final String cssClass) {

		return asList(getClasses()).contains(cssClass);
	}

	public boolean isHidden() {
		return "none".equals(getStyle("display"));
	}

	public void put(final String key, final Object value) {
		if (data == null) {
			data = new HashMap<>(1);
			dataCloneMethods = new HashMap<>(1);
		}
		if (value == null) {
			data.remove(key);
			dataCloneMethods.remove(key);
		} else {
			data.put(key, value);
			try {
				if (!String.class.equals(value.getClass()))
					dataCloneMethods.put(key, value.getClass().getDeclaredMethod("clone"));
			} catch (NoSuchMethodException e) {
				throw new RuntimeException("Any values in element metadata must have a clone() method.");
			}
		}
	}

	public void remove() {
		if (parent != null)
			parent.removeChild(this);
	}

	public void removeChild(final Element child) {
		children.remove(child);
		child.parent = null;
		child.location = null;
	}

	public Element removeAttribute(final Attribute... attributes) {
		for (final Attribute attribute : attributes)
			this.attributes.remove(attribute);
		return this;
	}

	public final void removeId() {
		removeAttribute(ID);
	}

	public boolean replaceChild(final Element oldChild, final Element newChild) {
		for (int i = 0, childrenMax = children.size(); i < childrenMax; i++) {
			final Element child = children.get(i);
			if (child.equals(oldChild)) {
				children.set(i, newChild);
				newChild.parent = this;
				return true;
			}
		}
		return false;
	}

	public boolean replaceWith(final Element replacement) {
		return parent != null && parent.replaceChild(this, replacement);
	}

	/**
	 * Converts any url attributes using "http" to use "https". Useful to avoid "this page contains insecure elements"
	 * browser warnings.
	 */
	public void secure() {
		// overridden in classes with url attributes
	}

	public abstract Element setClass(final String value);

	public abstract Element setClass(final Enum<?>... cssClasses);

	public final Element setId(final String value, final Object... parameters) {
		return setId(parameters.length > 0 ? String.format(value, parameters) : value);
	}

	public Element setText(final String text, final Object... params) {
		return setText(String.format(text, params));
	}

	protected void setTextContent(String text) {
		removeChildren();
		if (text != null) {
			int i = 0;
			while (i < 3) {
				i++;
				try {
					addXhtmlToNode(text);
					break;
				} catch (IOException e) {
					throw new RuntimeException(e);
				} catch (SAXException e) {
					//todo this doesn't really fix cases where you have valid entity references with stray ampersands
					if (e.getMessage().contains("'&' in the entity reference"))
						text = text.replaceAll("&", "&amp;");
					else if (SAX_ENTITY_ERROR.matcher(e.getMessage()).matches()) {
						text = Escaper.html40.replaceWithUnicode(text);
					} else {
						// parse failed, add as text
						System.err.println(String.format("Unable to parse XHTML: %s", e.getMessage()));
						appendChild(new TextContent(text));
						break;
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	public final void removeChildren() {
		children.clear();
	}

	private void addXhtmlToNode(final String src)
		throws Exception {
		final StringBuilder xml = new StringBuilder(XML_HEADER);
		xml.append("<span>");
		xml.append(src);
		xml.append("</span>");
		final DocumentBuilder builder = new DocumentBuilder(null);
		builder.load(new ByteArrayInputStream(xml.toString().getBytes()));
		final List<Element> children = builder.getRoot().getChildren();
		if (LutraDebugger.isEnabled()) {
			final DocumentLocation location = DocumentLocation.fromStack();
			if (location != null) {
				for (final Element child : children)
					child.location = location;
			}
		}
		addChild(children);
	}

	public List<Element> getChildren() {
		return new AbstractList<Element>() {
			@Override
			public Iterator<Element> iterator() {
				return new ChildIterator(Element.this);
			}

			@Override
			public Element get(final int index) {
				return children.get(index);
			}

			@Override
			public int size() {
				return children.size();
			}
		};
	}

	public final Element addChild(final Iterable<? extends Element> children) {
		return addChild(null, children);
	}

	public final Element appendChild(final Element... children) {
		addChild(children);
		return this;
	}

	public Element setTooltip(final String tooltip, final Object... params) {
		setTooltip(String.format(tooltip, params));
		return this;
	}

	public Element setVisible(final boolean visible) {
		if (visible)
			show();
		else
			hide();
		return this;
	}

	public void toJavascriptFunction(final StringBuilder javascript) {
		javascript.append("function() {\n");
		javascript.append("var node = document.createElement('").append(type.name).append("');\n");
		for (final Map.Entry<Attribute, String> entry : attributes.entrySet())
			javascript.append("node.setAttribute('").append(entry.getKey().name).append("','").append(
				entry.getValue()).append("');\n");
		for (final Element child : children) {
			javascript.append("node.appendChild(");
			child.toJavascriptFunction(javascript);
			javascript.append("());\n");
		}
		javascript.append("return node;\n");
		javascript.append('}');
	}

	public String toString(final boolean pretty)
		throws IOException {
		final StringBuilder output = new StringBuilder(1024);
		toString(output, pretty, 0, null, null);
		return output.toString();
	}

	public boolean toString(final Appendable output, final boolean pretty, final int depth,
	                        final ElementType previous,
	                        final ElementType next)
		throws IOException {
		final boolean tab = pretty && needsTab();
		if (tab)
			tab(output, depth);
		output.append('<').append(type.toString());
		Predicate<? super Map.Entry<Attribute, ?>> filter = i -> true;
		if (attributes.containsKey(ITEMTYPE)) {
			if (attributes.containsKey(ITEMPROP)) {
				outputAttribute(output, ITEMPROP, attributes.get(ITEMPROP));
				filter = e -> !e.getKey().equals(ITEMPROP);
			}
			output.append(" itemscope");
		}
		attributes.entrySet().stream().filter(filter).forEach(
			throwing( entry -> outputAttribute(output, entry.getKey(), entry.getValue())));
		if (data != null) {
			for (Map.Entry<String, Object> entry : data.entrySet()) {
				if (entry.getKey().startsWith("data-"))
					output.append(' ').append(entry.getKey()).append('=').append('"').append(entry.getValue().toString()).append('"');
			}
		}
		if (isClosed())
			output.append('/');
		else {
			output.append('>');
			boolean ourChildrenTabbed = false;
			for (int i = 0; i < children.size(); i++) {
				final Element child = children.get(i);
				ourChildrenTabbed |= outputChild(output, child, pretty, depth,
					i > 0 ? children.get(i - 1).type : null,
					i + 1 < children.size() ? children.get(i + 1).type : null);
			}
			if (ourChildrenTabbed)
				tab(output, depth);
			output.append("</").append(type.toString());
		}
		output.append('>');
		return tab;
	}

	protected boolean needsTab() {
		return !type.isInline();
	}

	protected boolean outputChild(final Appendable output, final Element child, final boolean pretty,
	                              final int depth, final ElementType previous, final ElementType next)
		throws IOException {
		return child.toString(output, pretty, depth + 1, previous, next);
	}

	public Element toggleClass(final boolean mode, final String a, final String b) {
		addClass(mode ? a : b);
		removeClass(mode ? b : a);
		return this;
	}

	public ValidationErrors validate(final boolean strict) {
		final ValidationErrors errors = new ValidationErrors();
		validate(errors, strict);
		return errors;
	}

	public void validate(final ValidationErrors errors, final boolean strict) {
		for (final ChildRule rule : childRules)
			rule.validate(this, children, errors, strict);
		for (final AttributeRule rule : attributeRules)
			rule.validate(this, attributes, errors, strict);
		for (final Element child : children)
			child.validate(errors, strict);
	}

	public void prefixIdAttribute(final String prefix) {
		final String id = getId();
		setId(prefix + Character.toUpperCase(id.charAt(0)) + id.substring(1));
	}

	public final String getId() {
		return getAttribute(ID);
	}

	public Element setId(final String value) {
		throw new UnsupportedOperationException(
			String.format("Element of type %s cannot have attribute %s", type, TITLE));
	}


	private static class ChildIterator implements Iterator<Element> {
		private final Iterator<Element> iterator;
		private Element lastGiven;

		private ChildIterator(final Element parent) {
			iterator = parent.children.iterator();
		}

		@Override
		public boolean hasNext() {
			return iterator.hasNext();
		}

		@Override
		public Element next() {
			lastGiven = iterator.next();
			return lastGiven;
		}

		@Override
		public void remove() {
			iterator.remove();// will throw exception if lastGiven is null
			lastGiven.parent = null;
			lastGiven.location = null;
		}
	}

	public static class DocumentLocation {
		public final String filename;
		public final Integer line;
		public final Integer column;

		public DocumentLocation(final String filename) {
			this(filename, null, null);
		}

		public DocumentLocation(final String filename, final int line) {
			this(filename, line, null);
		}

		public DocumentLocation(final String filename, final Integer line, final Integer column) {
			this.filename = filename;
			this.line = line;
			this.column = column;
		}

		@SuppressWarnings({"ThrowableInstanceNeverThrown"})
		public static DocumentLocation fromStack() {
			for (final StackTraceElement traceElement : new Exception().getStackTrace()) {
				final String className = traceElement.getClassName();
				if (Element.class.getName().equals(className) && "cloneWithListeners".equals(traceElement.getMethodName()))
					return null;
				if (!className.startsWith("net.mage.lutra") && !className.startsWith("org.apache.xerces"))
					return new DocumentLocation(className, traceElement.getLineNumber());
			}
			return null;
		}

		@Override
		public String toString() {
			final StringBuilder builder = new StringBuilder(16);
			builder.append(filename);
			if (line != null)
				builder.append(':').append(line);
			if (column != null)
				builder.append(" [").append(column).append(']');
			return builder.toString();
		}
	}
}
