package org.jvnet.jaxb.plugin.nobean;

import com.sun.codemodel.JMethod;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.Plugin;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;
import com.sun.tools.xjc.outline.Outline;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import java.util.Collection;


/**
 * XJC plugin to remove setXX functions.
 * The motivation is to allow for immutable objects or setters generated with Lombok annotations
 *
 * @author Asger Askov Blekinge "asga@kb.dk"
 *
 */
public class NoSettersPlugin extends Plugin {

    protected final String OPTION_NAME = "XnoSetters";

    @Override
    public String getOptionName() {
        return OPTION_NAME;
    }

    @Override
    public String getUsage() {
        return "-" + OPTION_NAME + "    :   Do not generate setter methods for fields\n";
    }

    @Override
    public boolean run(Outline model, Options opts, ErrorHandler errors) throws SAXException {
        for (ClassOutline co : model.getClasses()) {
            for (FieldOutline fo : co.getDeclaredFields()) {
                String setterName = "set" + fo.getPropertyInfo().getName(true);
                co.implClass.methods().removeIf(n -> setterName.equals(n.name()));
            }
        }
        return true;
    }
}
