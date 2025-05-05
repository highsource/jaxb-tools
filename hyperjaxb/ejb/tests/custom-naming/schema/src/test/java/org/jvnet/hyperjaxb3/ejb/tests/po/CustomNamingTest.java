package org.jvnet.hyperjaxb3.ejb.tests.po;

import org.junit.Assert;
import org.junit.Test;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Method;

public class CustomNamingTest {

    @Test
    public void testItemFields() throws NoSuchMethodException {
        Class<Items.Item> itemClass = Items.Item.class;
        Table table = itemClass.getAnnotation(Table.class);
        Assert.assertEquals("FOO_ITEM", table.name());

        Method getShipDateItemMethod = itemClass.getMethod("getShipDateItem");
        Column getShipDateItemColumn = getShipDateItemMethod.getAnnotation(Column.class);
        Assert.assertEquals("FOO_SHIP_DATE_ITEM", getShipDateItemColumn.name());

    }

}
