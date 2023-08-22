package org.jvnet.hyperjaxb3.item;

import java.util.List;

public interface ItemList<ListType, ItemType extends Item<ListType>> extends
		List<ListType> {

	public ItemType create(ListType item);

}
