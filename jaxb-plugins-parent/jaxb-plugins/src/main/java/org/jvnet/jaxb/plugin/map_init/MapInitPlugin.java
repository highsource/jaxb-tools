package org.jvnet.jaxb.plugin.map_init;

import com.sun.codemodel.JClass;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JType;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.CPluginCustomization;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;
import com.sun.tools.xjc.outline.Outline;
import org.jvnet.jaxb.lang.JAXBToStringStrategy;
import org.jvnet.jaxb.plugin.AbstractParameterizablePlugin;
import org.jvnet.jaxb.plugin.ComposedIgnoring;
import org.jvnet.jaxb.plugin.CustomizedIgnoring;
import org.jvnet.jaxb.plugin.Ignoring;
import org.jvnet.jaxb.plugin.inheritance.ExtendsClass;
import org.jvnet.jaxb.plugin.util.FieldOutlineUtils;
import org.jvnet.jaxb.util.CustomizationUtils;
import org.xml.sax.ErrorHandler;

import javax.xml.namespace.QName;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapInitPlugin extends AbstractParameterizablePlugin {

    private static final JType[] ABSENT = new JType[0];

    @Override
    public String getOptionName() {
        return "XMapInit";
    }

    @Override
    public String getUsage() {
        return "Change getter for maps to initialize Maps.";
    }

    private String mapClass = HashMap.class.getName();

    public void setMapClass(String mapClass) {
        this.mapClass = mapClass;
    }

    public String getMapClass() {
        return mapClass;
    }

    public boolean run(Outline outline, Options opt, ErrorHandler errorHandler) {
        for (final ClassOutline classOutline : outline.getClasses()) {
            processClassOutline(classOutline, outline.getCodeModel());
        }
        return true;
    }

    protected void processClassOutline(ClassOutline classOutline, JCodeModel codeModel) {
        final JDefinedClass theClass = classOutline.implClass;
        generateMapInit(classOutline, theClass, codeModel);
    }

    private void generateMapInit(ClassOutline classOutline,
                                 JDefinedClass theClass,
                                 JCodeModel codeModel) {

        final FieldOutline[] declaredFields = FieldOutlineUtils.filter(
            classOutline.getDeclaredFields(), getIgnoring());

        for (final FieldOutline fieldOutline : declaredFields) {

            final String publicName = fieldOutline.getPropertyInfo().getName(true);

            final String getterName = "get" + publicName;

            final JMethod getter = theClass.getMethod(getterName, ABSENT);

            if (getter != null && getter.type().erasure().boxify().isAssignableFrom(codeModel.ref(Map.class))) {
                final JFieldVar field = theClass.fields().get(fieldOutline.getPropertyInfo().getName(false));

                final CPluginCustomization initClassCustomization = CustomizationUtils
                    .findCustomization(fieldOutline, Customizations.INIT_CLASS_ELEMENT_NAME);
                String mapClassName = getMapClass();
                if (initClassCustomization != null) {
                    final InitClass initClass = (InitClass) CustomizationUtils
                        .unmarshall(Customizations.getContext(), initClassCustomization);
                    if (initClass != null && initClass.getClassName() != null && !"".equals(initClass.getClassName())) {
                        mapClassName = initClass.getClassName();
                    }
                }
                if (field != null) {
                    getter.body().pos(0);
                    getter.body()._if(field.eq(JExpr._null()))._then()
                        .assign(field, newCoreMap(codeModel, mapClassName, getter.type().boxify().getTypeParameters()));
                }
            }
        }
    }

    private JExpression newCoreMap(JCodeModel codeModel, String mapClassName, List<JClass> typeParameters) {
        return JExpr._new(codeModel.ref(mapClassName).narrow(typeParameters));
    }

    private Ignoring ignoring = new ComposedIgnoring(
        logger,
        new CustomizedIgnoring(
            Customizations.IGNORED_ELEMENT_NAME));

    public Ignoring getIgnoring() {
        return ignoring;
    }

    public void setIgnoring(Ignoring ignoring) {
        this.ignoring = ignoring;
    }

    @Override
    public Collection<QName> getCustomizationElementNames() {
        return Arrays.asList(
            Customizations.IGNORED_ELEMENT_NAME,
            Customizations.INIT_CLASS_ELEMENT_NAME);
    }
}
