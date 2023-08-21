package org.jvnet.jaxb.annox.xml.bind;

import java.lang.reflect.Member;

import com.sun.xml.bind.v2.model.annotation.Locatable;
import com.sun.xml.bind.v2.runtime.Location;

public class LocatableUtils {

	public static Locatable getLocatable(final Package thePackage) {
		if (thePackage == null) {
			return null;
		} else {
			return new Locatable() {
				public Locatable getUpstream() {
					return null;
				}

				public Location getLocation() {
					return new Location() {
						@Override
						public String toString() {
							return thePackage.getName();
						}
					};
				}
			};
		}
	}

	public static Locatable getLocatable(final Class<?> theClass) {
		if (theClass == null) {
			return null;
		} else {
			return new Locatable() {
				public Locatable getUpstream() {
					return getLocatable(theClass.getPackage());
				}

				public Location getLocation() {
					return new Location() {
						@Override
						public String toString() {
							return theClass.getName();
						}
					};
				}
			};
		}
	}

	public static Locatable getLocatable(final Member theMember) {
		if (theMember == null) {
			return null;
		} else {
			return new Locatable() {
				public Locatable getUpstream() {
					return getLocatable(theMember.getDeclaringClass());
				}

				public Location getLocation() {
					return new Location() {
						@Override
						public String toString() {
							return theMember.getName();
						}
					};
				}
			};
		}
	}

}
