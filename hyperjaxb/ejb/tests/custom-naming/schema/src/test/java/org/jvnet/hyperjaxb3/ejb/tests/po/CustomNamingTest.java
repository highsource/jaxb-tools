package org.jvnet.hyperjaxb3.ejb.tests.po;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

public class CustomNamingTest {

    @Test
    public void testItemFields() throws NoSuchMethodException {
        Class<Items.Item> itemClass = Items.Item.class;
        Table table = itemClass.getAnnotation(Table.class);
        Assertions.assertEquals("FOO_ITEM", table.name());

        Method getShipDateItemMethod = itemClass.getMethod("getShipDateItem");
        Column getShipDateItemColumn = getShipDateItemMethod.getAnnotation(Column.class);
        Assertions.assertEquals("FOO_SHIP_DATE_ITEM", getShipDateItemColumn.name());

    }

}
