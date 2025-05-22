package org.jvnet.jaxb2_commons.plugin.propertylistenerinjector;

import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JInvocation;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.Plugin;
import com.sun.tools.xjc.model.CPluginCustomization;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;
import com.sun.tools.xjc.outline.Outline;
import com.sun.tools.xjc.util.DOMUtils;
import javax.xml.bind.annotation.XmlTransient;
import org.xml.sax.ErrorHandler;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.List;

/**
 * This Plugin will generate property change events on each setXXX.
 *
 * See the javadoc of {@link com.sun.tools.xjc.Plugin} for what those methods mean.
 *
 * @author Jerome Dochez
 * This plugin came from here : org.andromda.thirdparty.jaxb2_commons:property-listener-injector:1.1
 */
public class PropertyListenerInjectorPlugin extends Plugin {

    protected final String OPTION_NAME = "Xinject-listener-code";

    public String getOptionName() {
        return OPTION_NAME;
    }

    public List<String> getCustomizationURIs() {
        return Collections.singletonList(Customizations.NAMESPACE_URI);
    }

    public boolean isCustomizationTagName(String nsUri, String localName) {
        return nsUri.equals(Customizations.LISTENER_ELEMENT_NAME.getNamespaceURI())
            && localName.equals(Customizations.LISTENER_ELEMENT_NAME.getLocalPart());
    }

    public String getUsage() {
        return "  -X" + OPTION_NAME + "\t:  inject property change event support to setter methods";
    }

    // meat of the processing
    public boolean run(Outline model, Options opt, ErrorHandler errorHandler) {
        CPluginCustomization listener = model.getModel().getCustomizations().find(Customizations.NAMESPACE_URI, Customizations.LISTENER_ELEMENT_NAME.getLocalPart());
        String globalInterfaceSetting = VetoableChangeListener.class.getName();
        if (listener != null) {
            listener.markAsAcknowledged();
            globalInterfaceSetting = DOMUtils.getElementText(listener.element);
        }

        for (ClassOutline co : model.getClasses()) {
            CPluginCustomization c = co.target.getCustomizations().find(Customizations.NAMESPACE_URI, Customizations.LISTENER_ELEMENT_NAME.getLocalPart());

            String interfaceName;
            if (c != null) {
                c.markAsAcknowledged();
                interfaceName = DOMUtils.getElementText(c.element);
            } else {
                interfaceName = globalInterfaceSetting;
            }
            if (VetoableChangeListener.class.getName().equals(interfaceName)) {
                addSupport(VetoableChangeListener.class, VetoableChangeSupport.class, co);
            }
            if (PropertyChangeListener.class.getName().equals(interfaceName)) {
                addSupport(PropertyChangeListener.class, PropertyChangeSupport.class, co);
            }

        }

        return true;
    }

    private void addSupport(Class listener, Class support, ClassOutline classOutline) {

        JDefinedClass target = classOutline.implClass;
        // add the support field.
        // JFieldVar field = target.field(JMod.PRIVATE|JMod.TRANSIENT, support, "support");
        JFieldVar fieldSupport = target.field(JMod.PRIVATE, support, "support");

        fieldSupport.annotate(XmlTransient.class);

        // and initialize it....
        fieldSupport.init(JExpr.direct("new " + support.getSimpleName() + "(this)"));

        // we need to add
        for (Method method : support.getMethods()) {
            if (Modifier.isPublic(method.getModifiers())) {
                if (method.getName().startsWith("add")) {
                    // look if one of the parameters in the listnener class...
                    for (Class param : method.getParameterTypes()) {
                        if (param.getName().equals(listener.getName())) {
                            addMethod(method, target);
                            break;
                        }
                    }
                }
                if (method.getName().startsWith("remove")) {
                    // look if one of the parameters in the listnener class...
                    for (Class param : method.getParameterTypes()) {
                        if (param.getName().equals(listener.getName())) {
                            addMethod(method, target);
                            break;
                        }
                    }
                }
            }
        }

        final FieldOutline[] declaredFields = classOutline.getDeclaredFields();
        for (final FieldOutline fieldOutline : declaredFields) {

            final String publicName = fieldOutline.getPropertyInfo().getName(true);

            final String setterName = "set" + publicName;

            final JType rawType = fieldOutline.getRawType();
            final JMethod boxifiedSetter = target.getMethod(setterName, new JType[] { rawType.boxify() });
            final JMethod unboxifiedSetter = target.getMethod(setterName, new JType[] { rawType.unboxify() });
            final JMethod setter = boxifiedSetter != null ? boxifiedSetter : unboxifiedSetter;

            if (setter != null) {
                final String privateName = fieldOutline.getPropertyInfo().getName(false);
                final JFieldVar field = target.fields().get(privateName);
                if (field != null) {
                    /*
                     *         String oldValue = this.value;
                     *         this.value = newValue;
                     *         this.pcs.firePropertyChange("value", oldValue, newValue);
                     */
                    setter.body().pos(0);

                    final JVar oldPropertyValue = setter.body().decl(JMod.FINAL,
                        rawType, "old" + publicName,
                        field);
                    setter.body().pos(setter.body().getContents().size());
                    setter.body().add(fieldSupport.invoke("firePropertyChange").arg(privateName).arg(oldPropertyValue).arg(field));
                }
            }
        }
    }

    private void addMethod(Method method, JDefinedClass target) {
        JMethod addListener = target.method(JMod.PUBLIC, method.getReturnType(), method.getName());
        Class params[] = method.getParameterTypes();

        JInvocation inv = addListener.body().invoke(JExpr.ref("support"), method.getName());

        for (int i=0; i<params.length; i++) {
            inv.arg(addListener.param(params[i], "param" + i));
        }
    }
}
