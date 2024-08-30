package org.jvnet.jaxb.lang.tests;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Polygon;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jvnet.jaxb.lang.DefaultCopyStrategy;
import org.jvnet.jaxb.lang.tests.pojo.CloneableNoClone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class DefaultCopyStrategyTest {

    @Test
    public void testPolygonOK() {
        final GeometryFactory geometryFactory = new GeometryFactory();
        final Polygon polygon = geometryFactory.createPolygon(
                geometryFactory.createLinearRing(new Coordinate[] {
                        new Coordinate(0, 0, 0), new Coordinate(1, 1, 0),

                        new Coordinate(1, 0, 0), new Coordinate(0, 1, 0),
                        new Coordinate(0, 0, 0) }), null);

        polygon.clone();

        new DefaultCopyStrategy().copy(null, polygon);
    }

    @Test
    public void testXMLGregorianCalendarOK() throws DatatypeConfigurationException {
        final XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar();

        new DefaultCopyStrategy().copy(null, calendar);
    }
    @Test
    public void testCloneableNoCloneKO() {
        final CloneableNoClone object = new CloneableNoClone("no-clone-method-in-it");

        Assertions.assertThrows(
            UnsupportedOperationException.class,
            () -> new DefaultCopyStrategy().copy(null, object),
            "Exception UnsupportedOperationException not thrown");
    }
}
