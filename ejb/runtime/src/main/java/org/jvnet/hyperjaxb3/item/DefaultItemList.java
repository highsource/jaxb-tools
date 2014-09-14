package org.jvnet.hyperjaxb3.item;

import java.io.Serializable;
import java.util.List;

public class DefaultItemList<ListType, ItemType extends Item<ListType>> extends
		AbstractItemList<ListType, ItemType> implements Serializable {

	private static final long serialVersionUID = -1941259574287915806L;
	private final Class<? extends ItemType> itemClass;

	public DefaultItemList(List<ItemType> core,
			final Class<? extends ItemType> itemClass) {
		super(core);
		this.itemClass = itemClass;
	}

	public Class<? extends ItemType> getItemClass() {
		return itemClass;
	}

	public ItemType create(ListType item) {
		return ItemUtils.create(getItemClass(), item);
	}

}
