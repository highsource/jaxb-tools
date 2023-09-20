package org.jvnet.jaxb2_commons.lang.tests;

import java.io.InputStream;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAnyElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import junit.framework.TestCase;

import org.apache.commons.io.IOUtils;
import org.jvnet.jaxb2_commons.lang.CopyStrategy2;
import org.jvnet.jaxb2_commons.lang.CopyTo2;
import org.jvnet.jaxb2_commons.lang.JAXBCopyStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Polygon;

public class CopyStrategyTest extends TestCase {

	public void testPolygon() {
		final GeometryFactory geometryFactory = new GeometryFactory();
		final Polygon polygon = geometryFactory.createPolygon(
				geometryFactory.createLinearRing(new Coordinate[] {
						new Coordinate(0, 0, 0), new Coordinate(1, 1, 0),

						new Coordinate(1, 0, 0), new Coordinate(0, 1, 0),
						new Coordinate(0, 0, 0) }), null);

		polygon.clone();

		new JAXBCopyStrategy().copy(null, polygon);

	}

	public void testAny() throws Exception {
		JAXBContext context = JAXBContext.newInstance(A.class);

		final InputStream is = getClass().getResourceAsStream("Test[0].xml");
		try {
			A a = (A) context.createUnmarshaller().unmarshal(is);

			a.copyTo(a.createNewInstance());
		} finally {
			IOUtils.closeQuietly(is);
		}
	}

	@XmlRootElement(name = "a")
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class A implements CopyTo2 {
		@XmlAnyElement(lax = true)
		private Object any;

		public Object getAny() {
			return any;
		}

		public void setAny(Object any) {
			this.any = any;
		}

		public Object createNewInstance() {
			return new A();
		}

		public Object copyTo(Object target) {
			return copyTo(null, target, JAXBCopyStrategy.getInstance());
		}

		public Object copyTo(ObjectLocator locator, Object target,
				CopyStrategy2 copyStrategy) {
			final A copy = ((target == null) ? ((A) createNewInstance())
					: ((A) target));
			{
				Object sourceAny;
				sourceAny = this.getAny();
				Object copyAny = ((Object) copyStrategy.copy(null, sourceAny, any != null));
				copy.setAny(copyAny);
			}
			return copy;
		}

	}
}
