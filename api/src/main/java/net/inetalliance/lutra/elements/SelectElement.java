package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;
import net.inetalliance.lutra.rules.MayHaveAttribute;
import net.inetalliance.lutra.rules.MustHaveAtLeastOneChildOf;
import net.inetalliance.types.EnumGroup;
import net.inetalliance.types.Grouped;
import net.inetalliance.types.Named;
import net.inetalliance.types.localized.Localized;
import net.inetalliance.types.struct.maps.MultivalueMap;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

import static java.util.Comparator.comparing;
import static net.inetalliance.funky.Funky.stream;


public class SelectElement extends CommonFormElement<SelectElement> implements InlineElement {
	private static final ChildRule[] childRules =
		{
			new MustHaveAtLeastOneChildOf(ElementType.OPTION, ElementType.OPTGROUP)
		};
	private static final AttributeRule[] attributeRules =
		{
			new MayHaveAttribute(Attribute.union(Attribute.COMMON,
				EnumSet.of(Attribute.MULTIPLE, Attribute.NAME, Attribute.SIZE,
					Attribute.DISABLED, Attribute.ONFOCUS, Attribute.ONCHANGE,
					Attribute.TABINDEX)))
		};

	public SelectElement(final SelectElementChild... children) {
		super(SelectElement.class, ElementType.SELECT, childRules, attributeRules, children);
	}

	@Override
	public SelectElement clone()
		throws CloneNotSupportedException {
		return (SelectElement) cloneWithListeners();
	}

	public final <E extends Enum<E>> Map<E, OptionElement> addNonLocalizedOptions(final Class<E> type,
	                                                                              final Locale locale, final E selected) {
		final Map<E, OptionElement> map = new EnumMap<E, OptionElement>(type);
		final boolean localized = Localized.class.isAssignableFrom(type);
		for (final E value : EnumSet.allOf(type))
			map.put(value,
				addOption(value.name(), localized ? ((Localized) value).getLocalizedName().get(locale) : value.toString(),
					value == selected));
		return map;
	}

	public final OptionElement addOption(final Object value, final Object label) {
		return addOption(String.valueOf(value), String.valueOf(label));
	}

	public final OptionElement addOption(final String value, final String label) {
		return addOption(value, label, false);
	}

	public final List<OptionElement> addOptions(final int from, final int to) {
		return addOptions(from, to, null);
	}

	public final <E extends Enum<E> & Localized> void addOptions(final Class<E> type, final Locale locale) {
		addOptions(type, locale, null);
	}

	public final <E extends Enum<E> & Localized> void addOptions(final Class<E> type, final Collection<E> values,
	                                                             final Locale locale) {
		addOptions(type, values, locale, null);
	}

	public final List<OptionElement> addOptions(final int from, final int to, final Integer selected) {
		final List<OptionElement> options = new ArrayList<OptionElement>(to - from);
		for (int i = from; i <= to; i++)
			options.add(addOption(i, i, i == selected));
		return options;
	}

	public final OptionElement addOption(final Object value, final Object label, final boolean selected) {
		return addOption(String.valueOf(value), String.valueOf(label), selected);
	}

	public final <E extends Enum<E> & Grouped<E, G> & Localized, G extends Enum<G> & EnumGroup<G, E>>
	Map<G, OptgroupElement> addOptions(final Class<E> type, final Class<G> groups, final Locale locale) {
		return addOptions(type, groups, locale, null);
	}

	public final <E extends Enum<E> & Localized> Map<E, OptionElement> addOptions(final Class<E> type,
	                                                                              final Locale locale, final E selected) {
		return addOptions(type, EnumSet.allOf(type), locale, selected);
	}

	public final <T extends Named>
	List<OptionElement> addOptions(final T selected, final Collection<T> items, final Function<T, ?> valuePlucker) {
		final List<OptionElement> options = new ArrayList<OptionElement>(items.size());
		for (final T item : items)
			options.add(addOption(valuePlucker.apply(item), item.getName(), item.equals(selected)));
		return options;
	}

	public final <G extends Named, T extends Named>
	Map<T, OptionElement> addOptions(final T selected, final Map<G, List<T>> map,
	                                 final Function<T, String> valuePlucker) {
		final Map<T, OptionElement> optionElements = new HashMap<T, OptionElement>(8);
		for (final Map.Entry<G, List<T>> entry : map.entrySet()) {
			final G group = entry.getKey();
			final List<T> items = entry.getValue();
			final OptgroupElement optgroup = new OptgroupElement();
			optgroup.setLabel(group.getName());
			for (final T item : items) {
				final OptionElement option = new OptionElement();
				option.setText(item.getName());
				option.setValue(valuePlucker.apply(item));
				option.setSelected(selected != null && selected.equals(item));
				optionElements.put(item, option);
				optgroup.addChild(option);
			}
			addChild(optgroup);
		}
		return optionElements;
	}

	public final <E extends Enum<E> & Localized> Map<E, OptionElement> addOptions(final Class<E> type,
	                                                                              final Collection<E> values,
	                                                                              final Locale locale, final E selected) {
		final Map<E, OptionElement> map = new EnumMap<E, OptionElement>(type);
		for (final E value : values)
			map.put(value, addOption(value.name(), value.getLocalizedName().get(locale), value == selected));
		return map;
	}

	public final OptionElement addOption(final String value, final String label, final boolean selected) {
		final OptionElement option = new OptionElement(label)
			.setValue(value)
			.setSelected(selected);
		addChild(option);
		return option;
	}

	public final <G extends Named, T extends Named>
	Map<T, OptionElement> addOptions(final T selected, final Iterable<T> items, final Function<T, G> groupPlucker,
	                                 final Function<T, String> valuePlucker) {
		final MultivalueMap<G, T> map = new MultivalueMap<G, T>(8);
		for (final T item : items)
			map.add(groupPlucker.apply(item), item);
		return addOptions(selected, map, valuePlucker);
	}

	public final <E extends Enum<E> & Grouped<E, G> & Localized, G extends Enum<G> & EnumGroup<G, E>>
	Map<G, OptgroupElement> addOptions(final Class<E> type, final Class<G> groups, final Locale locale, final E selected) {
		final EnumSet<E> values = EnumSet.allOf(type);
		final Map<G, OptgroupElement> map = new EnumMap<G, OptgroupElement>(groups);
		for (final G group : EnumSet.allOf(groups)) {
			final OptgroupElement optGroup = new OptgroupElement().setLabel(group.getLocalizedName().get(locale));
			appendChild(optGroup);
			map.put(group, optGroup);
			values.stream().filter(group.getPredicate()).forEach(value -> {
				optGroup.appendChild(new OptionElement(value.getLocalizedName().get(locale))
					.setValue(value.name())
					.setSelected(value == selected));
			});
		}
		return map;
	}

	public final String getName() {
		return getAttribute(Attribute.NAME);
	}

	public final String getOnBlur() {
		return getAttribute(Attribute.ONBLUR);
	}

	public final String getOnChange() {
		return getAttribute(Attribute.ONCHANGE);
	}

	public final String getOnFocus() {
		return getAttribute(Attribute.ONFOCUS);
	}

	public final OptionElement getOptionWithValue(final String value) {

		return stream(getDescendants())
			.filter(e -> e instanceof OptionElement)
			.map(e -> (OptionElement) e)
			.filter(e -> value.equals(e.getValue()))
			.findFirst()
			.orElse(null);

	}

	public final String getSize() {
		return getAttribute(Attribute.SIZE);
	}

	public final String getTabIndex() {
		return getAttribute(Attribute.TABINDEX);
	}

	@Override
	protected boolean isClosed() {
		return false;
	}

	public final boolean isDisabled() {
		return "disabled".equals(getAttribute(Attribute.DISABLED));
	}

	public final boolean isMultiple() {
		return "multiple".equals(getAttribute(Attribute.MULTIPLE));
	}

	public final SelectElement setDisabled(final boolean value) {
		setAttribute(Attribute.DISABLED, value ? "disabled" : null);
		return this;
	}

	@Override
	public void setFormValue(final String value) {
		setValue(value);
	}

	public final void setValue(final String value) {
		final AtomicBoolean foundOne = new AtomicBoolean(false);
		stream(getDescendants()).filter(e -> e instanceof OptionElement).map(o -> (OptionElement) o).forEach(option -> {
			if (!foundOne.get() && Objects.equals(value, option.getValue())) {
				option.setSelected(true);
				foundOne.set(true);  // can't break because we need to set the rest to false
			} else
				option.setSelected(false);
		});
	}

	public final SelectElement setMultiple(final boolean value) {
		setAttribute(Attribute.MULTIPLE, value ? "multiple" : null);
		return this;
	}

	@Override
	public final SelectElement setName(final String value) {
		setAttribute(Attribute.NAME, value);
		return this;
	}

	public final SelectElement setOnBlur(final String value) {
		setAttribute(Attribute.ONBLUR, value);
		return this;
	}

	public final SelectElement setOnChange(final String value) {
		setAttribute(Attribute.ONCHANGE, value);
		return this;
	}

	public final SelectElement setOnFocus(final String value) {
		setAttribute(Attribute.ONFOCUS, value);
		return this;
	}

	public final SelectElement setSize(final String value) {
		setAttribute(Attribute.SIZE, value);
		return this;
	}

	public final SelectElement setTabIndex(final String value) {
		setAttribute(Attribute.TABINDEX, value);
		return this;
	}

	public void sort() {
		final SortedSet<OptionElement> sorted = new TreeSet<>(comparing(OptionElement::getValue));
		getChildren().stream().map(e -> (OptionElement) e).forEach(sorted::add);
		removeChildren();
		addChild(sorted);
	}

}