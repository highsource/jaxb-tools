package org.jvnet.jaxb.plugin.defaultvalueplugin;

import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.xml.sax.ErrorHandler;

import com.sun.codemodel.ClassType;
import com.sun.codemodel.JBlock;
import com.sun.codemodel.JCatchBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JEnumConstant;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JTryBlock;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.Plugin;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.EnumConstantOutline;
import com.sun.tools.xjc.outline.EnumOutline;
import com.sun.tools.xjc.outline.FieldOutline;
import com.sun.tools.xjc.outline.Outline;
import com.sun.xml.xsom.XmlString;
import com.sun.xml.xsom.XSAttributeUse;
import com.sun.xml.xsom.XSElementDecl;
import com.sun.xml.xsom.XSParticle;
import com.sun.xml.xsom.XSTerm;

/**
 * Modifies the JAXB code model to set default values to the schema "default" attribute.
 * Currently, the following field types can be initialized:
 * <ul>
 * <li>Enumerations (see {@link Enum})</li>
 * <li>{@link String}</li>
 * <li>Descendants of {@link java.lang.Number}
 * <ul>
 * <li>{@link Byte}</li>
 * <li>{@link Double}</li>
 * <li>{@link Float}</li>
 * <li>{@link Integer}</li>
 * <li>{@link Long}</li>
 * <li>{@link Short}</li>
 * </ul>
 * </li>
 * <li>{@link Boolean}</li>
 * </ul>
 *
 * Created: Mon Apr 24 22:04:25 2006
 *
 * @author <a href="mailto:hari@dcis.net">Hari Selvarajan</a>
 * @author <a href="mailto:Juergen.Lukasczyk@web.de">J�rgen Lukasczyk</a>
 * @version 1.1
 */
public class DefaultValuePlugin
    extends Plugin
{

    private static final String OPTION_NAME_ALL = "-Xdefault-value:all";
    private boolean all = false;
    /**
     * Name of Option to enable this plugin
     */
    static private final String OPTION_NAME = "Xdefault-value";


    /**
     * Creates a new <code>DefaultValuePlugin</code> instance.
     *
     */
    public DefaultValuePlugin() {
    }


    /**
     * DefaultValuePlugin uses "-Xdefault-value" as the command-line
     * argument
     *
     */
    public String getOptionName() {
        return OPTION_NAME;
    }


    /**
     * Return usage information for plugin
     *
     */
    public String getUsage()
    {
        return "  -"+OPTION_NAME+"    : enable rewriting of classes to set default values for fields as specified in XML schema\n"
            + " [-"+OPTION_NAME+":all : enable rewriting of classes for default values of all fields and attributes]"
            ;
    }

    /**
     * Run the plugin. We perform the following steps:
     *
     * <ul>
     * <li>Look for fields that:
     * <ul>
     * <li>Were generated from XSD description</li>
     * <li>The XSD description is of type xsd:element (or xsd:attribute if all is set)</li>
     * <li>A default value is specified</li>
     * <li>Map to one of the supported types</li>
     * </ul>
     * </li>
     * <li>Add a new initializsation expression to every field found</li>
     * </ul>
     *
     */
    public boolean run(Outline outline, Options opt, ErrorHandler errorHandler)
    {
        // For all Classes generated
        for (ClassOutline co : outline.getClasses()) {

            // Some conversions may have to add class level code
            JFieldVar dtf = null;        // Helper code: DatatypeFactory

            // check all Fields in Class
            for (FieldOutline f : co.getDeclaredFields()) {
                CPropertyInfo fieldInfo = f.getPropertyInfo();
                XmlString xmlDefaultValue = null;
                if (fieldInfo.getSchemaComponent() instanceof XSParticle) {
                    XSTerm term = ((XSParticle) fieldInfo.getSchemaComponent()).getTerm();// Default values only necessary for fields derived from an xsd:element
                    if (!term.isElementDecl()) {
                        continue;
                    }
                    XSElementDecl element = term.asElementDecl();
                    xmlDefaultValue = element.getDefaultValue();
                } else if (all && fieldInfo.getSchemaComponent() instanceof XSAttributeUse) {
                    XSAttributeUse attribute = ((XSAttributeUse) fieldInfo.getSchemaComponent());
                    xmlDefaultValue = attribute.getDefaultValue();
                } else {
                    // Do nothing if Field is not created from an xsd particle
                    continue;
                }

                // Do nothing if no default value
                if (xmlDefaultValue == null) {
                    continue;
                }
                String defaultValue = xmlDefaultValue.value;

                // Get handle to JModel representing the field
                Map<String, JFieldVar> fields = co.implClass.fields();
                JFieldVar var = fields.get(fieldInfo.getName(false));

                // Handle primitive types via boxed representation (treat boolean as java.lang.Boolean)
                JType type = f.getRawType();
                if (type.isPrimitive())
                    type = type.boxify();
                String typeFullName = type.fullName();

                // Create an appropriate default expression depending on type
                if ("java.lang.String".equals(typeFullName)) {
                    var.init(JExpr.lit(defaultValue));
                    if (opt.verbose)
                        System.out.println("[INFO] Initializing String variable "
                            +fieldInfo.displayName()
                            +" to \""+defaultValue+"\"");
                }

                else if ("java.lang.Boolean".equals(typeFullName)) {
                    var.init(JExpr.lit(Boolean.valueOf(defaultValue)));
                    if (opt.verbose)
                        System.out.println("[INFO] Initializing Boolean variable "
                            +fieldInfo.displayName()
                            +" to "+defaultValue+"");
                }

                else if ( ("java.lang.Byte".equals(typeFullName))
                    || ("java.lang.Short".equals(typeFullName))
                    || ("java.lang.Integer".equals(typeFullName))
                ) {
                    // CodeModel does not distinguish between Byte, Short and Integer literals
                    var.init(JExpr.lit(Integer.valueOf(defaultValue)));
                    if (opt.verbose)
                        System.out.println("[INFO] Initializing Integer variable "
                            +fieldInfo.displayName()
                            +" to "+defaultValue+"");
                }

                else if ("java.lang.Long".equals(typeFullName)) {
                    var.init(JExpr.lit(Long.valueOf(defaultValue)));
                    if (opt.verbose)
                        System.out.println("[INFO] Initializing Long variable "
                            +fieldInfo.displayName()
                            +" to "+defaultValue+"");
                }

                else if ("java.lang.Float".equals(typeFullName)) {
                    var.init(JExpr.lit(Float.valueOf(defaultValue)));
                    if (opt.verbose)
                        System.out.println("[INFO] Initializing Float variable "
                            +fieldInfo.displayName()
                            +" to "+defaultValue+"");
                }

                else if ( ("java.lang.Single".equals(typeFullName))
                    || ("java.lang.Double".equals(typeFullName))
                ) {
                    // CodeModel does not distinguish between Single and Double literals
                    var.init(JExpr.lit(Double.valueOf(defaultValue)));
                    if (opt.verbose)
                        System.out.println("[INFO] Initializing Double variable "
                            +fieldInfo.displayName()
                            +" to "+defaultValue+"");
                }

                else if ("javax.xml.datatype.XMLGregorianCalendar".equals(typeFullName)) {
                    // XMLGregorianCalender is constructed by DatatypeFactory, so we have to have
                    // an instance of that once per class
                    if (dtf == null) {
                        dtf = installDtF(co.implClass);
                        if (dtf == null)  continue;
                    }
                    // Use our DtF instance to generate the initialization expression
                    var.init(JExpr.invoke(dtf, "newXMLGregorianCalendar")
                        .arg(defaultValue));
                    if (opt.verbose)
                        System.out.println("[INFO] Initializing XMLGregorianCalendar variable "
                            +fieldInfo.displayName()
                            +" with value of "+defaultValue);
                }

                else if ( (type instanceof JDefinedClass)
                    && (((JDefinedClass) type).getClassType() == ClassType.ENUM) ) {
                    // Find Enum constant
                    JEnumConstant constant = findEnumConstant(type, defaultValue, outline);
                    if (constant != null) {
                        var.init(constant);
                        if (opt.verbose)
                            System.out.println("[INFO] Initializing enum variable "
                                + fieldInfo.displayName() + " with constant " + constant.getName());
                    }
                }

                // Don't know how to create default for this type
                else {
                    System.out.println("[WARN] Did not create default value for field "
                        + fieldInfo.displayName()
                        + ". Don't know how to create default value expression for fields of type "
                        + typeFullName
                        + ". Default value of \""+defaultValue+"\" specified in schema"
                        );
                }

            } // for FieldOutline

        } // for ClassOutline

        return true;
    }


    /**
     * Retrieve the enum constant that correlates to the string value.
     * @param enumType    Type identifying an Enum in the code model
     * @param enumStringValue    Lexical value of the constant to search
     * @param outline    Outline of the code model
     * @return The matching Constant from the enum type or NULL if not found
     */
    private JEnumConstant findEnumConstant(JType enumType, String enumStringValue, Outline outline)
    {
        // Search all Enums generated
        for (EnumOutline eo : outline.getEnums()) {
            // Is it the type of my variable?
            if (eo.clazz == enumType) {
                // Search all Constants of that enum
                for (EnumConstantOutline eco : eo.constants) {
                    // Is the enum generated from the XML defaut value string?
                    if (eco.target.getLexicalValue().equals(enumStringValue)) {
                        return eco.constRef;
                    }
                }  // for Constants
                // Did not find the constant???
                System.out.println("[WARN] Could not find EnumConstant for value: "+enumStringValue);
                return null;
            }
        }
        // Did not find the type??
        System.out.println("[WARN] Could not find Enum class for type: "+enumType.fullName());
        return null;
    }


    /**
     * Enhance the CodeModel of a Class to include a {@link DatatypeFactory} as a static private field.
     * The factory is needed to construct {@link XMLGregorianCalendar} from String representation.
     * @param parentClass    Class where the DatatypeFactory will be created
     * @return    Reference to the created static field
     */
    private JFieldVar installDtF(final JDefinedClass parentClass)
    {
        try {
            JCodeModel cm = parentClass.owner();
            // Create a static variable of type DatatypeFactory
            JClass dtfClass = cm.ref(DatatypeFactory.class);
            JFieldVar dtf = parentClass.field(JMod.STATIC | JMod.FINAL | JMod.PRIVATE,
                dtfClass, "DATATYPE_FACTORY");
            // Initialize variable in static block
            JBlock si = parentClass.init();
            JTryBlock tryBlock = si._try();
            tryBlock.body().assign(dtf, dtfClass.staticInvoke("newInstance"));
            // Catch exception & rethrow as unchecked Exception
            JCatchBlock catchBlock = tryBlock._catch(cm.ref(DatatypeConfigurationException.class));
            JVar ex = catchBlock.param("ex");
            JClass runtimeException = cm.ref(RuntimeException.class);
            catchBlock.body()._throw(JExpr._new(runtimeException)
                .arg("Unable to initialize DatatypeFactory")
                .arg(ex));
            // Return reference to initialized static field
            return dtf;
        } catch (Exception e) {
            // We don't want JAXB to break of any plugin error
            System.out.println("[ERROR] Failed to create code");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int parseArgument(Options opt, String[] args, int i) {
        // eg. -Xdefault-value:all
        String arg = args[i].trim();
        if (arg.startsWith(OPTION_NAME_ALL)) {
            all = true;
            return 1;
        }
        return 0;
    }


}
