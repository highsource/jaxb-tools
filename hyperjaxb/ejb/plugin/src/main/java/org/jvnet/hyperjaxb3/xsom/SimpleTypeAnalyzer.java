package org.jvnet.hyperjaxb3.xsom;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.DatatypeConverter;
import com.sun.xml.xsom.XSAnnotation;
import com.sun.xml.xsom.XSAttGroupDecl;
import com.sun.xml.xsom.XSAttributeDecl;
import com.sun.xml.xsom.XSAttributeUse;
import com.sun.xml.xsom.XSComplexType;
import com.sun.xml.xsom.XSComponent;
import com.sun.xml.xsom.XSContentType;
import com.sun.xml.xsom.XSElementDecl;
import com.sun.xml.xsom.XSFacet;
import com.sun.xml.xsom.XSIdentityConstraint;
import com.sun.xml.xsom.XSListSimpleType;
import com.sun.xml.xsom.XSModelGroup;
import com.sun.xml.xsom.XSModelGroupDecl;
import com.sun.xml.xsom.XSNotation;
import com.sun.xml.xsom.XSParticle;
import com.sun.xml.xsom.XSRestrictionSimpleType;
import com.sun.xml.xsom.XSSchema;
import com.sun.xml.xsom.XSSimpleType;
import com.sun.xml.xsom.XSUnionSimpleType;
import com.sun.xml.xsom.XSWildcard;
import com.sun.xml.xsom.XSXPath;
import com.sun.xml.xsom.XmlString;
import com.sun.xml.xsom.visitor.XSFunction;
import com.sun.xml.xsom.visitor.XSSimpleTypeFunction;

public class SimpleTypeAnalyzer {

	public static Long getMinLength(XSComponent component) {
		if (component == null) {
			return null;
		} else {
			return component.apply(new MinLengthAnalyzer());
		}
	}

	public static Long getMaxLength(XSComponent component) {
		if (component == null) {
			return null;
		} else {
			return component.apply(new MaxLengthAnalyzer());
		}
	}

	public static Long getLength(XSComponent component) {
		if (component == null) {
			return null;
		} else {
			return component.apply(new LengthAnalyzer());
		}
	}

	public static Long getTotalDigits(XSComponent component) {
		if (component == null) {
			return null;
		} else {
			return component.apply(new TotalDigitsAnalyzer());
		}
	}

	public static Long getFractionDigits(XSComponent component) {
		if (component == null) {
			return null;
		} else {
			return component.apply(new FractionDigitsAnalyzer());
		}
	}

	public static abstract class FacetAnalyzer<T> implements XSFunction<T>,
			XSSimpleTypeFunction<T> {

		public T annotation(XSAnnotation arg0) {
			return null;
		}

		public T attGroupDecl(XSAttGroupDecl arg0) {
			return null;
		}

		public T attributeDecl(XSAttributeDecl attributeDecl) {
			return attributeDecl.getType()
					.apply((XSSimpleTypeFunction<T>) this);
		}

		public T attributeUse(XSAttributeUse attributeUse) {
			return attributeUse.getDecl().apply(this);
		}

		public T complexType(XSComplexType arg0) {
			return null;
		}

		public T identityConstraint(XSIdentityConstraint arg0) {
			return null;
		}

		public T notation(XSNotation arg0) {
			return null;
		}

		public T schema(XSSchema arg0) {
			return null;
		}

		public T xpath(XSXPath arg0) {
			return null;
		}

		public T empty(XSContentType arg0) {
			return null;
		}

		public T particle(XSParticle particle) {
			return particle.getTerm().apply(this);
		}

		public T simpleType(XSSimpleType simpleType) {

			return simpleType.apply((XSSimpleTypeFunction<T>) this);
		}

		public T restrictionSimpleType(
				XSRestrictionSimpleType restrictionSimpleType) {

			final List<T> values = new LinkedList<T>();
			final T parentValue;

			if (restrictionSimpleType.getBaseType() != null) {
				parentValue = restrictionSimpleType.getBaseType().apply(this);
			} else {
				parentValue = null;
			}
			values.add(parentValue);
			// for ()
			for (XSFacet facet : restrictionSimpleType.getDeclaredFacets()) {
				final T facetValue = facet.apply(this);
				values.add(facetValue);
			}
			return aggregate(values);
		}

		public T listSimpleType(XSListSimpleType listSimpleType) {
			return listSimpleType.getItemType().apply(
					(XSSimpleTypeFunction<T>) this);
		}

		public T unionSimpleType(XSUnionSimpleType unionSimpleType) {
			final List<T> values = new ArrayList<T>(unionSimpleType
					.getMemberSize());
			for (int index = 0; index < unionSimpleType.getMemberSize(); index++) {
				values.add(unionSimpleType.getMember(index).apply(
						(XSSimpleTypeFunction<T>) this));
			}
			return aggregate(values);
		}

		public T elementDecl(XSElementDecl elementDecl) {
			return elementDecl.getType().apply(this);
		}

		public T modelGroup(XSModelGroup arg0) {
			return null;
		}

		public T modelGroupDecl(XSModelGroupDecl arg0) {
			return null;
		}

		public T wildcard(XSWildcard arg0) {
			return null;
		}

		public T aggregate(Collection<T> values) {
			if (values == null || values.isEmpty()) {
				return null;
			} else {
				T currentValue = null;
				for (final T value : values) {
					currentValue = aggregate(currentValue, value);
				}
				return currentValue;
			}
		}

		protected T aggregate(final T currentValue, final T value) {

			if (value == null) {
				return currentValue;
			} else {
				if (currentValue == null) {
					return value;
				} else {
					return aggregateNonNulls(currentValue, value);
				}
			}
		}

		protected abstract T aggregateNonNulls(final T currentValue,
				final T value);

		public abstract T facet(XSFacet facet);
	}

	public static abstract class AbstractLongFacetAnalyzer extends
			FacetAnalyzer<Long> {
		public Long getValue(XSFacet facet) {
			final XmlString value = facet.getValue();

			if (value == null) {
				return null;
			} else {
				final String v = value.value;

				if (v == null) {
					return null;
				} else {
					final BigInteger integerValue = DatatypeConverter.parseInteger(v);
					if (integerValue.compareTo(BigInteger
							.valueOf(Long.MAX_VALUE)) > 0) {
						return Long.MAX_VALUE;
					} else {
						return integerValue.longValue();
					}
				}
			}
		}

	}

	public static class MinLengthAnalyzer extends AbstractLongFacetAnalyzer {
		public Long facet(XSFacet facet) {
			if (XSFacet.FACET_MINLENGTH.equals(facet.getName())) {
				return getValue(facet);
			} else {
				return null;
			}
		}

		@Override
		protected Long aggregateNonNulls(final Long currentValue,
				final Long value) {
			return currentValue > value ? currentValue : value;
		}

	}

	public static class MaxLengthAnalyzer extends AbstractLongFacetAnalyzer {
		public Long facet(XSFacet facet) {
			if (XSFacet.FACET_MAXLENGTH.equals(facet.getName())

			) {
				return getValue(facet);
			} else {
				return null;
			}
		}

		@Override
		protected Long aggregateNonNulls(final Long currentValue,
				final Long value) {
			return currentValue < value ? currentValue : value;
		}

	}

	public static class LengthAnalyzer extends AbstractLongFacetAnalyzer {
		public Long facet(XSFacet facet) {
			if (XSFacet.FACET_LENGTH.equals(facet.getName())

			) {
				return getValue(facet);
			} else {
				return null;
			}
		}

		@Override
		protected Long aggregateNonNulls(final Long currentValue,
				final Long value) {
			return currentValue > value ? currentValue : value;
		}

	}

	public static class TotalDigitsAnalyzer extends AbstractLongFacetAnalyzer {
		@Override
		protected Long aggregateNonNulls(Long currentValue, Long value) {
			return currentValue < value ? currentValue : value;
		}

		@Override
		public Long facet(XSFacet facet) {
			if (XSFacet.FACET_TOTALDIGITS.equals(facet.getName())) {
				return getValue(facet);
			} else {
				return null;
			}
		}
	}

	public static class FractionDigitsAnalyzer extends
			AbstractLongFacetAnalyzer {
		@Override
		protected Long aggregateNonNulls(Long currentValue, Long value) {
			return currentValue < value ? currentValue : value;
		}

		@Override
		public Long facet(XSFacet facet) {
			if (XSFacet.FACET_FRACTIONDIGITS.equals(facet.getName())) {
				return getValue(facet);
			} else {
				return null;
			}
		}
	}

}
