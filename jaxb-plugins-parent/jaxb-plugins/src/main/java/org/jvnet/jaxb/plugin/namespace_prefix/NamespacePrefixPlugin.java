package org.jvnet.jaxb.plugin.namespace_prefix;

import jakarta.xml.bind.annotation.XmlNs;
import jakarta.xml.bind.annotation.XmlSchema;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sun.codemodel.JAnnotationArrayMember;
import com.sun.codemodel.JAnnotationUse;
import com.sun.codemodel.JAnnotationValue;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JPackage;
import com.sun.codemodel.JStringLiteral;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.Plugin;
import com.sun.tools.xjc.generator.bean.PackageOutlineImpl;
import com.sun.tools.xjc.model.CCustomizations;
import com.sun.tools.xjc.model.CPluginCustomization;
import com.sun.tools.xjc.model.Model;
import com.sun.tools.xjc.outline.Outline;
import com.sun.tools.xjc.outline.PackageOutline;
import com.sun.tools.xjc.reader.xmlschema.bindinfo.BIDeclaration;
import com.sun.tools.xjc.reader.xmlschema.bindinfo.BIXPluginCustomization;
import com.sun.tools.xjc.reader.xmlschema.bindinfo.BindInfo;
import com.sun.xml.xsom.XSAnnotation;
import com.sun.xml.xsom.XSSchema;
import com.sun.xml.xsom.impl.SchemaImpl;
import org.xml.sax.ErrorHandler;

/**
 * This plugin adds {@link jakarta.xml.bind.annotation.XmlNs} annotations to <i>package-info.java</i> files. Those annotations tells JAXB to generate XML schema's instances with specific namespaces
 * prefixes, instead of the auto-generated (ns1, ns2, ...) prefixes. Definition of thoses prefixes is done in the bindings.xml file.
 *
 * Bindings.xml file example:
 * <pre>
 *  &lt;?xml version=&quot;1.0&quot;?&gt;
 *  &lt;jaxb:bindings version=&quot;3.0&quot;
 *      xmlns:jaxb=&quot;https://jakarta.ee/xml/ns/jaxb&quot;
 *      xmlns:xsi=&quot;http://www.w3.org/2001/XMLSchema-instance&quot;
 *      xmlns:namespace=&quot;http://jaxb2-commons.dev.java.net/basic/namespace-prefix&quot;&gt;
 *
 *      &lt;jaxb:bindings schemaLocation=&quot;unireg-common-1.xsd&quot;&gt;
 *          &lt;jaxb:schemaBindings&gt;
 *              &lt;jaxb:package name=&quot;ch.vd.unireg.xml.common.v1&quot; /&gt;
 *          &lt;/jaxb:schemaBindings&gt;
 *          &lt;jaxb:bindings&gt;
 *              <b>&lt;namespace:prefix name=&quot;common-1&quot; /&gt;</b>
 *          &lt;/jaxb:bindings&gt;
 *      &lt;/jaxb:bindings&gt;
 *
 *  &lt;/jaxb:bindings&gt;
 * </pre>
 *
 * @author Manuel Siggen (c) 2012 Etat-de-Vaud (www.vd.ch)
 */
@SuppressWarnings("UnusedDeclaration")
public class NamespacePrefixPlugin extends Plugin {

    private static final String NAMESPACE_URI = "http://jaxb2-commons.dev.java.net/basic/namespace-prefix";

    @Override
    public String getOptionName() {
        return "Xnamespace-prefix";
    }

    @Override
    public String getUsage() {
        return "-Xnamespace-prefix : activate namespaces prefix customizations";
    }

    @Override
    public List<String> getCustomizationURIs() {
        return Arrays.asList(NAMESPACE_URI);
    }

    @Override
    public boolean isCustomizationTagName(String nsUri, String localName) {
        return NAMESPACE_URI.equals(nsUri) && "prefix".equals(localName);
    }

    @Override
    public boolean run(final Outline outline, final Options options, final ErrorHandler errorHandler) {

        final JClass xmlNsClass = outline.getCodeModel().ref(XmlNs.class);
        final JClass xmlSchemaClass = outline.getCodeModel().ref(XmlSchema.class);

        for (PackageOutline packageOutline : outline.getAllPackageContexts()) {
            final JPackage p = packageOutline._package();

            // get the target namespaces of all schemas that bind to the current package
            final Set<String> packageNamespaces = getPackageNamespace(packageOutline);

            // is there any prefix binding defined for the current package ?
            final Model packageModel = getPackageModel((PackageOutlineImpl) packageOutline);
            final List<Pair> list = getPrefixBinding(packageModel, packageNamespaces);
            acknowledgePrefixAnnotations(packageModel);

            if (list == null || list.isEmpty()) {
                // no prefix binding, nothing to do
                continue;
            }

            // add XML namespace prefix annotations
            final JAnnotationUse xmlSchemaAnnotation = getOrAddXmlSchemaAnnotation(p, xmlSchemaClass);
            if (xmlSchemaAnnotation == null) {
                throw new RuntimeException("Unable to get/add 'XmlSchema' annotation to package [" + p.name() + "]");
            }

            final JAnnotationArrayMember members = xmlSchemaAnnotation.paramArray("xmlns");
            for (Pair pair : list) {
                addNamespacePrefix(xmlNsClass, members, pair.getNamespace(), pair.getPrefix());
            }
        }

        return true;
    }

    private static Set<String> getPackageNamespace(PackageOutline packageOutline) {
        final Map<String, Integer> map = getUriCountMap(packageOutline);
        return map == null ? Collections.<String>emptySet() : map.keySet();
    }

    /**
     * Make sure the prefix annotations have been acknowledged.
     *
     * @param packageModel the package model
     */
    private void acknowledgePrefixAnnotations(Model packageModel) {
        final CCustomizations customizations = packageModel.getCustomizations();
        if (customizations != null) {
            for (CPluginCustomization customization : customizations) {
                if (customization.element.getNamespaceURI().equals(NAMESPACE_URI)) {
                    if (!customization.element.getLocalName().equals("prefix")) {
                        throw new RuntimeException("Unrecognized element [" + customization.element.getLocalName() + "]");
                    }
                    customization.markAsAcknowledged();
                }
            }
        }
    }

    /**
     * This method detects prefixes for a given package as specified in the bindings file. Usually, there is only one namespace per package, but there may be more.
     *
     * @param packageModel     the package model
     * @param packageNamespace the target namespace for the package
     * @return the prefix annotations
     */
    private static List<Pair> getPrefixBinding(Model packageModel, Set<String> packageNamespace) {

        final List<Pair> list = new ArrayList<Pair>();

        // loop on existing schemas (XSD files)
        for (XSSchema schema : packageModel.schemaComponent.getSchemas()) {

            final SchemaImpl s = (SchemaImpl) schema;
            final XSAnnotation annotation = s.getAnnotation();
            if (annotation == null) {
                continue;
            }

            final Object anno = annotation.getAnnotation();
            if (anno == null || !(anno instanceof BindInfo)) {
                continue;
            }

            final BindInfo b = (BindInfo) anno;
            final String targetNS = b.getOwner().getOwnerSchema().getTargetNamespace();

            if (!packageNamespace.contains(targetNS)) { // only consider schemas that bind the current package
                continue;
            }

            // get the prefix's name
            String prefix = "";
            for (BIDeclaration declaration : b.getDecls()) {
                if (declaration instanceof BIXPluginCustomization) {
                    final BIXPluginCustomization customization = (BIXPluginCustomization) declaration;
                    if (customization.element.getNamespaceURI().equals(NAMESPACE_URI)) {
                        if (!customization.element.getLocalName().equals("prefix")) {
                            throw new RuntimeException("Unrecognized element [" + customization.element.getLocalName() + "]");
                        }
                        prefix = customization.element.getAttribute("name");
                        customization.markAsAcknowledged();
                        break;
                    }
                }
            }

            list.add(new Pair(targetNS, prefix));
        }

        return list;
    }

    private static void addNamespacePrefix(JClass xmlNsClass, JAnnotationArrayMember members, String namespace, String prefix) {
        final JAnnotationUse ns = members.annotate(xmlNsClass);
        ns.param("namespaceURI", namespace);
        ns.param("prefix", prefix);
    }

    private static JAnnotationUse getOrAddXmlSchemaAnnotation(JPackage p, JClass xmlSchemaClass) {

        JAnnotationUse xmlAnn = null;

        final Collection<JAnnotationUse> annotations = getAnnotations(p);
        if (annotations != null) {
            for (JAnnotationUse annotation : annotations) {
                final JClass clazz = getAnnotationJClass(annotation);
                if (clazz == xmlSchemaClass) {
                    xmlAnn = annotation;
                    break;
                }
            }
        }

        if (xmlAnn == null) {
            // XmlSchema annotation not found, let's add one
            xmlAnn = p.annotate(xmlSchemaClass);
        }

        return xmlAnn;
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Integer> getUriCountMap(PackageOutline packageOutline) {
        try {
            final Field field = PackageOutlineImpl.class.getDeclaredField("uriCountMap");
            field.setAccessible(true);
            return (Map<String, Integer>) field.get(packageOutline);
        }
        catch (NoSuchFieldException e) {
            throw new RuntimeException("Unable to access 'uriCountMap' field for package outline [" + packageOutline._package().name() + "] : " + e.getMessage(), e);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to find 'uriCountMap' field for package outline [" + packageOutline._package().name() + "] : " + e.getMessage(), e);
        }
    }

    private static Model getPackageModel(PackageOutlineImpl packageOutline) {
        try {
            final Field field = PackageOutlineImpl.class.getDeclaredField("_model");
            field.setAccessible(true);
            return (Model) field.get(packageOutline);
        }
        catch (NoSuchFieldException e) {
            throw new RuntimeException("Unable to access '_model' field for package outline [" + packageOutline._package().name() + "] : " + e.getMessage(), e);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to find '_model' field for package outline [" + packageOutline._package().name() + "] : " + e.getMessage(), e);
        }
    }

    private static Collection<JAnnotationUse> getAnnotations(JPackage p) {
        return p.annotations();
    }

    private static JClass getAnnotationJClass(JAnnotationUse annotation) {
        return annotation.getAnnotationClass();
    }

    private static Map<String, JAnnotationValue> getAnnotationMemberValues(JAnnotationUse annotation) {
        return annotation.getAnnotationMembers();
    }


    private static String getStringAnnotationValue(JAnnotationValue val) {
        try {
            final Field clazzField = val.getClass().getDeclaredField("value");
            clazzField.setAccessible(true);
            final JStringLiteral j = (JStringLiteral) clazzField.get(val);
            return j == null ? null : j.str;
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to access 'value' field for class [" + val.getClass() + "] : " + e.getMessage(), e);
        }
        catch (NoSuchFieldException e) {
            throw new RuntimeException("Unable to find 'value' field for class [" + val.getClass() + "] : " + e.getMessage(), e);
        }
    }

    private static class Pair {
        private final String namespace;
        private final String prefix;

        private Pair(String namespace, String prefix) {
            this.namespace = namespace;
            this.prefix = prefix;
        }

        public String getNamespace() {
            return namespace;
        }

        public String getPrefix() {
            return prefix;
        }
    }
}
