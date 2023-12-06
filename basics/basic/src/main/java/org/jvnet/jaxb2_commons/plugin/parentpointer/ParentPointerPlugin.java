package org.jvnet.jaxb2_commons.plugin.parentpointer;

import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JVar;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.Plugin;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.Outline;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlTransient;
import org.jvnet.jaxb2_commons.lang.Child;
import org.xml.sax.ErrorHandler;

import java.util.logging.Logger;

/**
 * @author gk5885
 * This plugin came from here : org.andromda.thirdparty.jaxb2_commons:parent-pointer-plugin:1.0
 */
public class ParentPointerPlugin extends Plugin {

    protected final String OPTION_NAME = "Xparent-pointer";

    private static final String className = Plugin.class.getName();

    private static final Logger logger = Logger.getLogger(className);

    private static final String parentFieldName = "parent";

    private static final String parentGetterName = "getParent";

    private static final String parentSetterName = "setParent";

    private static final String unmarshalCallbackName = "afterUnmarshal";

    @Override
    public String getOptionName() {
        return OPTION_NAME;
    }

    @Override
    public String getUsage() {
        return "-" + OPTION_NAME + " : Add a field that points to the parent object.";
    }

    @Override
    public boolean run(Outline outline, Options options, ErrorHandler errorHandler) {
        logger.entering(className, "run");
        for (ClassOutline classOutline : outline.getClasses()) {
            implementChild(outline, classOutline.implClass);
            addUnmarshalCallback(outline, classOutline.implClass);
        }
        logger.exiting(className, "run", true);
        return true;
    }

    private void addParentField(JDefinedClass definedClass) {
        logger.entering(className, "addParentField");
        JFieldVar parentField = definedClass.field(JMod.PRIVATE, Object.class, parentFieldName);
        parentField.annotate(XmlTransient.class);
        logger.exiting(className, "addParentField");
    }

    private void addParentGetter(Outline outline, JDefinedClass definedClass) {
        logger.entering(className, "addParentGetter");
        JMethod getParentMethod =
                definedClass.method(JMod.PUBLIC, Object.class, parentGetterName);
        getParentMethod.body()._return(JExpr.ref(JExpr._this(), parentFieldName));
        getParentMethod
                .javadoc()
                .append(
                        "Gets the parent object in the object tree representing the unmarshalled xml document.");
        getParentMethod.javadoc().addReturn().append("The parent object.");
        logger.exiting(className, "addParentGetter");
    }

    private void addParentSetter(Outline outline, JDefinedClass definedClass) {
        logger.entering(className, "addParentSetter");
        JMethod setParentMethod =
                definedClass.method(JMod.PUBLIC, outline.getCodeModel().VOID,
                        parentSetterName);
        JVar parentSetterVar =
                setParentMethod.param(Object.class, parentFieldName);
        setParentMethod.body().assign(JExpr.ref(JExpr._this(), parentFieldName), parentSetterVar);
        logger.exiting(className, "addParentSetter");
    }

    private void addUnmarshalCallback(Outline outline, JDefinedClass definedClass) {
        logger.entering(className, "addUnmarshalCallback");
        JMethod afterUnmarshalMethod =
                definedClass.method(JMod.PUBLIC, outline.getCodeModel().VOID,
                        unmarshalCallbackName);
        JVar unmarshallerVar =
                afterUnmarshalMethod.param(Unmarshaller.class, "unmarshaller");
        JVar parentVar =
                afterUnmarshalMethod.param(Object.class, parentFieldName);
        afterUnmarshalMethod.body().invoke(parentSetterName).arg(parentVar);
        afterUnmarshalMethod
                .javadoc()
                .append(
                        "This method is invoked by the JAXB implementation on each instance when unmarshalling completes.");
        afterUnmarshalMethod.javadoc().addParam(unmarshallerVar).append(
                "The unmarshaller that generated the instance.");
        afterUnmarshalMethod.javadoc().addParam(parentVar).append(
                "The parent object in the object tree.");
        logger.exiting(className, "addUnmarshalCallback");
    }

    private void implementChild(Outline outline, JDefinedClass definedClass) {
        logger.entering(className, "implementChild");
        definedClass._implements(Child.class);
        addParentField(definedClass);
        addParentGetter(outline, definedClass);
        addParentSetter(outline, definedClass);
        logger.exiting(className, "implementChild");
    }
}
