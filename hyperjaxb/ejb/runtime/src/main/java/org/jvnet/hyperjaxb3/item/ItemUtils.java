package org.jvnet.hyperjaxb3.item;

import java.util.List;

public class ItemUtils {

	public static <T, ItemType extends Item<T>> ItemType create(
			Class<? extends ItemType> itemClass, T value) {
		try {
			final ItemType item = itemClass.newInstance();
			item.setItem(value);
			return item;
		} catch (InstantiationException ex) {
			throw new IllegalArgumentException("Error in default constructor.",
					ex);
		} catch (IllegalAccessException ex) {
			throw new IllegalArgumentException("Error in default constructor.",
					ex);
		}
	}

	public static <T> boolean shouldBeWrapped(List<T> core) {
		if (core == null || !(core instanceof ItemList)) {
			return true;
		} else {
			return false;
		}
	}

	public static <T, ItemType extends Item<T>> List<T> wrap(List<T> core,
			List<ItemType> items, Class<? extends ItemType> itemClass) {
		if (core == null || !(core instanceof ItemList)) {
			final List<T> newCore = new DefaultItemList<T, ItemType>(items,
					itemClass);
			if (core != null) {
				newCore.addAll(core);
			}
			return newCore;
		} else {
			return core;
		}
	}
}
