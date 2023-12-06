package org.jvnet.jaxb.plugin.booleangetter;

import com.sun.codemodel.JMethod;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.Plugin;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.Outline;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import java.util.Collection;

/**
 * XJC plugin to generate getXX functions instead of isXX functions for boolean values.
 * The motivation is to make using XJC generated classes easier to use with Spring, since
 * Spring expects accessors to be called getXX.
 *
 * @author Adam Burnett
 *
 * This plugin came from here : org.andromda.thirdparty.jaxb2_commons:booleangetter:1.0
 */
public class BooleanGetter extends Plugin {

    protected final String OPTION_NAME = "Xboolean-getter";

    @Override
    public String getOptionName() {
        return OPTION_NAME;
    }

    @Override
    public String getUsage() {
        return "-" + OPTION_NAME + "    :   Generate getXX instead of isXX functions for boolean values\n";
    }

    @Override
    public boolean run(Outline model, Options opts, ErrorHandler errors) throws SAXException {
        for (ClassOutline co : model.getClasses()) {
            Collection<JMethod> methods = co.implClass.methods();
            // pretty simple, just look at all our generated methods and rename isXX to getXX
            for (JMethod m : methods) {
                if (m.name().startsWith("is")) {
                    m.name("get" + m.name().substring("is".length()));
                }
            }
        }
        return true;
    }
}
