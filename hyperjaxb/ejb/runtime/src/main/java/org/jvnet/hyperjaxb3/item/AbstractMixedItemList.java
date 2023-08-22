package org.jvnet.hyperjaxb3.item;

import java.util.AbstractList;
import java.util.List;

public abstract class AbstractMixedItemList<EffectiveListType, ListType extends EffectiveListType, ItemType extends MixedItem<ListType>>
		extends AbstractList<EffectiveListType> implements
		MixedItemList<EffectiveListType, ListType, ItemType> {

	protected final List<ItemType> core;

	public AbstractMixedItemList(final List<ItemType> core) {
		super();
		if (core == null) {
			throw new IllegalArgumentException("Core list must be null.");
		}
		this.core = core;
	}

	@Override
	@SuppressWarnings("unchecked")
	public EffectiveListType get(int index) {
		final ItemType item = core.get(index);
		if (item.getText() != null) {
			return (EffectiveListType) item.getText();
		} else {
			return item.getItem();
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public EffectiveListType set(int index, EffectiveListType element) {

		final ItemType oldItem = core.get(index);
		final EffectiveListType oldValue;
		if (oldItem.getText() != null) {
			oldValue = (EffectiveListType) oldItem.getText();
		} else {
			oldValue = oldItem.getItem();
		}
		if (element instanceof String) {
			oldItem.setText((String) element);
		} else {
			oldItem.setItem((ListType) element);
		}
		return oldValue;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void add(int index, EffectiveListType element) {
		final ItemType item;
		if (element instanceof String) {
			item = create((String) element);
		} else {
			item = create((ListType) element);
		}
		core.add(index, item);
	}

	@Override
	public EffectiveListType remove(int index) {
		final ItemType item = core.remove(index);
		return item.getItem();
	}

	@Override
	public int size() {
		return core.size();
	}
}
