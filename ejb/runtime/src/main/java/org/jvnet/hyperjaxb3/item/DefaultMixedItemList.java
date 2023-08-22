package org.jvnet.hyperjaxb3.item;

import java.io.Serializable;
import java.util.List;

public class DefaultMixedItemList<EffectiveListType, ListType extends EffectiveListType, ItemType extends MixedItem<ListType>>
		extends AbstractMixedItemList<EffectiveListType, ListType, ItemType>
		implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Class<? extends ItemType> itemClass;
	
	public DefaultMixedItemList(List<ItemType> core,
			final Class<? extends ItemType> itemClass) {
		super(core);
		this.itemClass = itemClass;
	}
	
	public Class<? extends ItemType> getItemClass() {
		return itemClass;
	}
	
	public ItemType create(ListType item) {
		return MixedItemUtils.create(getItemClass(), item);
	}

	public ItemType create(String item) {
		return MixedItemUtils.create(getItemClass(), item);
	}

}
