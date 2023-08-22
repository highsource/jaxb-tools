package org.jvnet.hyperjaxb3.item;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.List;

public abstract class AbstractItemList<ListType, ItemType extends Item<ListType>>
		extends AbstractList<ListType> implements ItemList<ListType, ItemType>, Serializable {

	private static final long serialVersionUID = -6512320214488719797L;

	protected final List<ItemType> core;

	public AbstractItemList(final List<ItemType> core) {
		super();
		if (core == null) {
			throw new IllegalArgumentException("Core list must be null.");
		}
		this.core = core;
	}

	@Override
	public ListType get(int index) {
		final ItemType item = core.get(index);
		return item.getItem();
	}

	@Override
	public ListType set(int index, ListType element) {

		final ItemType oldItem = core.get(index);
		final ListType oldValue = oldItem.getItem();
		oldItem.setItem(element);
		return oldValue;
	}

	@Override
	public void add(int index, ListType element) {
		final ItemType item = create(element);
		core.add(index, item);
	}

	@Override
	public ListType remove(int index) {
		final ItemType item = core.remove(index);
		return item.getItem();
	}

	@Override
	public int size() {
		return core.size();
	}
}
