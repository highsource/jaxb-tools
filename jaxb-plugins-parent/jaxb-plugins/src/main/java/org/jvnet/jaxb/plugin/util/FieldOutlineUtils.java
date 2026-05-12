package org.jvnet.jaxb.plugin.util;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JType;
import org.jvnet.jaxb.plugin.Ignoring;

import com.sun.tools.xjc.outline.FieldOutline;

import javax.xml.namespace.QName;
import java.util.Map;

public class FieldOutlineUtils {

	private FieldOutlineUtils() {

	}

    public static String OTHER_ATTRIBUTES_PUBLIC_NAME = "OtherAttributes";
    public static String OTHER_ATTRIBUTES_PRIVATE_NAME = "otherAttributes";

    public static JType getOtherAttributesType(JCodeModel model) {
        return model.ref(Map.class).narrow(QName.class, String.class);
    }

	public static FieldOutline[] filter(final FieldOutline[] fieldOutlines,
			final Ignoring ignoring) {
        return ArrayUtils.filter(fieldOutlines, new Predicate<FieldOutline>() {
            public boolean evaluate(FieldOutline fieldOutline) {
                return !ignoring.isIgnored(fieldOutline);

            }
        }, FieldOutline.class);
	}
}
