package org.jvnet.jaxb.plugin.nobean;

import com.sun.codemodel.JMethod;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.Plugin;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;
import com.sun.tools.xjc.outline.Outline;
import org.jvnet.jaxb.plugin.util.FieldOutlineUtils;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import java.util.Collection;


/**
 * XJC plugin to remove getXX functions.
 * The motivation is to allow for bean methods from annotations
 *
 * @author Asger Askov Blekinge "asga@kb.dk"
 *
 */
public class NoGettersPlugin extends Plugin {

    protected final String OPTION_NAME = "XnoGetters";

    @Override
    public String getOptionName() {
        return OPTION_NAME;
    }

    @Override
    public String getUsage() {
        return "-" + OPTION_NAME + "    :   Do not generate getter methods for fields\n";
    }

    @Override
    public boolean run(Outline model, Options opts, ErrorHandler errors) throws SAXException {
        for (ClassOutline co : model.getClasses()) {
            for (FieldOutline fo : co.getDeclaredFields()) {
                String getterSuffix = fo.getPropertyInfo().getName(true);
                String getterName = "get" + getterSuffix;
                String isGetterName = "is" + getterSuffix;
                co.implClass.methods().removeIf(n -> getterName.equals(n.name()) || isGetterName.equals(n.name()));
            }
        }
        return true;
    }
}
