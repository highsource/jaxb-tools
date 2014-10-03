package org.jvnet.jaxb2.maven2.util;

import java.util.Locale;

public class LocaleUtils {
	public static Locale valueOf(String lang) {
		if ((lang == null) || lang.equals("")) {
			return Locale.getDefault();
		}
		String language = "";
		String country = "";
		String variant = "";

		int i1 = lang.indexOf('-');
		if (i1 < 0) {
			language = lang;
		} else {
			language = lang.substring(0, i1);
			++i1;
			int i2 = lang.indexOf('-', i1);
			if (i2 < 0) {
				country = lang.substring(i1);
			} else {
				country = lang.substring(i1, i2);
				variant = lang.substring(i2 + 1);
			}
		}

		if (language.length() == 2) {
			language = language.toLowerCase();
		} else {
			language = "";
		}

		if (country.length() == 2) {
			country = country.toUpperCase();
		} else {
			country = "";
		}

		if ((variant.length() > 0)
				&& ((language.length() == 2) || (country.length() == 2))) {
			variant = variant.toUpperCase();
		} else {
			variant = "";
		}

		return new Locale(language, country, variant);
	}
}
