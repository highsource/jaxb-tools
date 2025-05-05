package org.jvnet.hyperjaxb3.ejb.tests.po;

import jakarta.persistence.Column;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

public class ItemMaxIdentifierLengthTest {

    @Test
    public void testItemFields() throws NoSuchMethodException {
        Class<Items.Item> itemClass = Items.Item.class;
        Method getShipDateItemMethod = itemClass.getMethod("getShipDateItem");
        Column getShipDateItemColumn = getShipDateItemMethod.getAnnotation(Column.class);
        Assertions.assertEquals("SHIP_DATE_ITEM", getShipDateItemColumn.name());

        Method getLongMethod = itemClass.getMethod("getAvailableDateByWhenItemCanBeShippedToLaterThanShipDateItem");
        Column getLongColumn = getLongMethod.getAnnotation(Column.class);
        // long but not so long enough since configuration of plugin
        Assertions.assertEquals("AVAILABLE_DATE_BY_WHEN_ITEM_CAN_BE_SHIPPED_TO_LATER_THAN_SHIP_DATE_ITEM", getLongColumn.name());

        Method getTooLongMethod = itemClass.getMethod("getNotAvailableDateByWhenItemCanNoLongerBeShippedToLaterThanShipDateItem");
        Column getTooLongColumn = getTooLongMethod.getAnnotation(Column.class);
        // long but too long so identifier = max
        Assertions.assertEquals(80, getTooLongColumn.name().length());
        Assertions.assertTrue(getTooLongColumn.name().startsWith("NOT_AVAILABLE_DATE_BY_WHEN_ITEM_CAN_NO_LONGER_BE_SHIPPED_TO_LATER_THAN_SHIP_DA"));
    }

}
