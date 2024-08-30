package org.jvnet.hyperjaxb3.ejb.util.tests;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Id;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jvnet.hyperjaxb3.ejb.util.EntityUtils;

public class EntityUtilsTest {

    @Test
	public void testGetIdOne() throws Exception {
		final One one = new One(10);
		Assertions.assertEquals((long) 10, EntityUtils.getId(one), "Wrong id.");
	}

    @Test
	public void testGetIdTwo() throws Exception {

		final Two two = new Two();
		two.setId(20);
        Assertions.assertEquals((long) 20, EntityUtils.getId(two), "Wrong id.");
	}

    @Test
	public void testGetIdThree() throws Exception {

		final Alpha threeAlpha = new Alpha();
		threeAlpha.p = 30;
		threeAlpha.q = "forty";
		final Three three = new Three(threeAlpha);
		Assertions.assertEquals(threeAlpha, EntityUtils.getId(three), "Wrong id.");
	}

	public void testGetIdFour() throws Exception {
		final Alpha fourAlpha = new Alpha();
		fourAlpha.p = 50;
		fourAlpha.q = "sixty";
		final Four four = new Four();
		four.setId(fourAlpha);
        Assertions.assertEquals(fourAlpha, EntityUtils.getId(four), "Wrong id.");
	}

	public static class One {
		@Id
		public long id;

		public One(long id) {
			super();
			this.id = id;
		}
	}

	public static class Two {
		public long id;

		@Id
		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}
	}

	public static class Three {
		@EmbeddedId
		public Alpha id;

		public Three(Alpha id) {
			super();
			this.id = id;
		}
	}

	public static class Four {
		public Alpha id;

		@EmbeddedId
		public Alpha getId() {
			return id;
		}

		public void setId(Alpha id) {
			this.id = id;
		}
	}

	@Embeddable
	public static class Alpha {
		public long p;

		public String q;

		@Override
		public int hashCode() {
			return HashCodeBuilder.reflectionHashCode(this);
		}

		@Override
		public boolean equals(Object obj) {
			return EqualsBuilder.reflectionEquals(this, obj);
		}

	}

	@Embeddable
	public static class Beta {
		private long r;

		private String s;

		public long getR() {
			return r;
		}

		public void setR(long r) {
			this.r = r;
		}

		public String getS() {
			return s;
		}

		public void setS(String s) {
			this.s = s;
		}

		@Override
		public int hashCode() {
			return HashCodeBuilder.reflectionHashCode(this);
		}

		@Override
		public boolean equals(Object obj) {
			return EqualsBuilder.reflectionEquals(this, obj);
		}
	}
}
