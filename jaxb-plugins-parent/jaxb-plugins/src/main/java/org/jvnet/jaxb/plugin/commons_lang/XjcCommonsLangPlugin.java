/*
 * Copyright 2006 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jvnet.jaxb.plugin.commons_lang;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.ErrorHandler;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JVar;
import com.sun.tools.xjc.BadCommandLineException;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.Plugin;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.Outline;

import static com.sun.codemodel.JExpr._null;
import static com.sun.codemodel.JExpr.lit;

/**
 * Automatically generates the toString(), hashCode() and equals() methods
 * using org.apache.commons:commons-lang3.
 *
 * Supports the optional ToStringStyle command line parameter to specify
 * the style for use within the toString method.
 *
 * <pre>
 * Example 1:
 *
 *     -Xcommons-lang
 *     -Xcommons-lang:ToStringStyle=SIMPLE_STYLE
 *
 *     to specify the use of
 *
 *     org.apache.commons.lang3.builder.ToStringStyle.SIMPLE_STYLE
 *
 * Example 2:
 *
 *     -Xcommons-lang
 *     -Xcommons-lang:ToStringStyle=my.CustomToStringStyleClass
 *
 *     to specify the use of
 *
 *     my.CustomToStringStyleClass, which must be a subclass of
 *
 *     org.apache.commons.lang3.builder.ToStringStyle, and contains a public no-arg constructor.
 *
 * </pre>
 *
 * The default ToStringStyle adopted by this plugin is MULTI_LINE_STYLE.
 *
 * Equals options:
 * <pre>
 * -Xcommons-lang:equalsTestTransients=TRUE|FALSE (default: FALSE)
 * -Xcommons-lang:equalsTestRecursive=TRUE|FALSE (default: FALSE)
 * </pre>
 * <p>NOTE: <br/>
 * When enabled equals passing when sub objects where last object POJO returns by ref check only but contents is equals
 * </p><p>
 * Performance Cost: Deep reflection is significantly slower than standard equality checks.
 * </p>
 *
 * To disable one of the generated plugins if you wish to use another module use one of the following:
 *
 * <pre>
 *  -Xcommons-lang:addToStringMethod=TRUE|FALSE (default: TRUE)
 *  -Xcommons-lang:addHashCodeMethod=TRUE|FALSE (default: TRUE)
 *  -Xcommons-lang:addEqualsMethod=TRUE|FALSE (default: TRUE)
 * </pre>
 *
 * @author Hanson Char
 * @author William Dutton (disable methods)
 */
public class XjcCommonsLangPlugin extends Plugin
{
    private static final String TOSTRING_STYLE_PARAM = "-Xcommons-lang:ToStringStyle=";
    private static final String TOSTRING_DISABLED_PARAM = "-Xcommons-lang:addToStringMethod=";
    private static final String HASH_CODE_DISABLED_PARAM = "-Xcommons-lang:addHashCodeMethod=";
    private static final String EQUALS_DISABLED_PARAM = "-Xcommons-lang:addEqualsMethod=";
    private static final String EQUALS_TEST_TRANSIENTS_PARAM = "-Xcommons-lang:equalsTestTransients=";
    private static final String EQUALS_TEST_RECURSIVE_PARAM = "-Xcommons-lang:equalsTestRecursive=";

    //Classes
    private static final String TOSTRINGSTYLE_CLASSNAME = "org.apache.commons.lang3.builder.ToStringStyle";
    private static final String EQUALSBUILDER_CLASSNAME = "org.apache.commons.lang3.builder.EqualsBuilder";
    private static final String HASHCODEBUILDER_CLASSNAME = "org.apache.commons.lang3.builder.HashCodeBuilder";
    private static final String TOSTRINGBUILDER_CLASSNAME = "org.apache.commons.lang3.builder.ToStringBuilder";

    protected Log logger = LogFactory.getLog(getClass());

    private String toStringStyle = "MULTI_LINE_STYLE"; //Default Style
    private String toStringClass = null;

    private boolean toStringEnabled = true;
    private boolean equalsEnabled = true;
    private boolean hashCodeEnabled = true;
    private boolean equalsTestTransients = false;
    private boolean equalsTestRecursive = false;

    @Override
    public String getOptionName()
    {
        return "Xcommons-lang";
    }

    @Override
    public String getUsage()
    {
        return "  -Xcommons-lang        :  generate toString(), hashCode() and equals() for generated code using Jakarta's common-lang "
             + "[\n"
             + "\t  -Xcommons-lang:ToStringStyle=MULTI_LINE_STYLE\n"
             + "\t| -Xcommons-lang:ToStringStyle=DEFAULT_STYLE\n"
             + "\t| -Xcommons-lang:ToStringStyle=NO_FIELD_NAMES_STYLE\n"
             + "\t| -Xcommons-lang:ToStringStyle=SHORT_PREFIX_STYLE\n"
             + "\t| -Xcommons-lang:ToStringStyle=SIMPLE_STYLE\n"
             + "\t| -Xcommons-lang:ToStringStyle=NO_CLASS_NAME_STYLE\n"
             + "\t| -Xcommons-lang:ToStringStyle=JSON_STYLE\n"
             + "\t| -Xcommons-lang:ToStringStyle=<any static final variables inside org.apache.commons.lang3.builder.ToStringStyle following regex ^[A-Z0-9_]+>$\n"
             + "\t| -Xcommons-lang:ToStringStyle=<Fully qualified class name of a ToStringStyle subtype>\n"
             + "]\n"
             + " Note: custom ToStringStyle class is needed if you wish to turn off setUseIdentityHashCode on top of MULTI_LINE_STYLE"
             + "\n"
             + " Equals options:\n"
             + "  -Xcommons-lang:equalsTestTransients=TRUE|FALSE (default: FALSE)\n"
             + "  -Xcommons-lang:equalsTestRecursive=TRUE|FALSE (default: FALSE)\n"
             + " NOTE: \n"
             + " Performance Cost: When equalsTestRecursive is TRUE, due to Deep reflection is significantly slower than standard equality checks.\n"
             + "\n"
             + " To disable one of the generated plugins if you wish to use another module use one of the following:\n"
             + "  -Xcommons-lang:addToStringMethod=FALSE\n"
             + "  -Xcommons-lang:addHashCodeMethod=FALSE\n"
             + "  -Xcommons-lang:addEqualsMethod=FALSE\n"
             ;
    }

    @Override
    public boolean run(Outline outline,
        @SuppressWarnings("unused") Options opt,
        @SuppressWarnings("unused") ErrorHandler errorHandler)
    {
        // Process every pojo class generated by jaxb
        for (ClassOutline classOutline : outline.getClasses()) {
            JDefinedClass implClass = classOutline.implClass;
            this.createToStringMethod(implClass);
            this.createEqualsMethod(implClass);
            this.createHashCodeMethod(implClass);
        }
        return true;
    }

    private void createToStringMethod(JDefinedClass implClass) {
        if (!toStringEnabled) {
            return;
        }
        JCodeModel codeModel = implClass.owner();
        JMethod toStringMethod =
            implClass.method(JMod.PUBLIC, codeModel.ref(String.class), "toString");
        // Annotate with @Override
        toStringMethod.annotate(Override.class);

        //Build body of method
        final JExpression toStringStyleExpr;
        if (toStringClass == null) {
            // Call Static Reference i.e. ToStringStyle.JSON_STYLE;
            toStringStyleExpr = codeModel.ref(TOSTRINGSTYLE_CLASSNAME).staticRef(toStringStyle);
        } else {
            //Direct class passed and calls new on it;
            toStringStyleExpr = JExpr._new(codeModel.ref(toStringClass));
        }

        // Invoke ToStringBuilder.reflectionToString(Object,StringStyle)
        toStringMethod.body()
            ._return(
                codeModel.ref(TOSTRINGBUILDER_CLASSNAME)
                    .staticInvoke("reflectionToString")
                    .arg(JExpr._this())
                    .arg(toStringStyleExpr)
            );
    }

    private void createEqualsMethod(JDefinedClass implClass)
    {
        if (!equalsEnabled) {
            return;
        }
        JCodeModel codeModel = implClass.owner();
        JMethod toStringMethod =
            implClass.method(JMod.PUBLIC, codeModel.BOOLEAN, "equals");
        JVar that = toStringMethod.param(Object.class, "that");
        // Annotate with @Override
        toStringMethod.annotate(Override.class);
        if (equalsTestTransients || equalsTestRecursive) {
            //Since: 3.6
            toStringMethod.body()._return(
                codeModel.ref(EQUALSBUILDER_CLASSNAME)
                    .staticInvoke("reflectionEquals") // public static boolean reflectionEquals(
                    .arg(JExpr._this()) //final Object lhs,
                    .arg(that) // final Object rhs,
                    .arg(lit(equalsTestTransients)) // final boolean testTransients,
                    .arg(_null()) // final Class<?> reflectUpToClass,
                    .arg(lit(equalsTestRecursive)) // final boolean testRecursive,
                    .arg(_null()) // final String... excludeFields) {
            );
        } else {
            // Invoke EqualsBuilder.reflectionEquals(Object,Object);
            toStringMethod.body()._return(
                codeModel.ref(EQUALSBUILDER_CLASSNAME)
                    .staticInvoke("reflectionEquals")
                    .arg(JExpr._this())
                    .arg(that)

            );
        }
    }

    private void createHashCodeMethod(JDefinedClass implClass)
    {
        if (!hashCodeEnabled) {
            return;
        }
        JCodeModel codeModel = implClass.owner();
        JMethod toStringMethod =
            implClass.method(JMod.PUBLIC, codeModel.INT, "hashCode");
        // Annotate with @Override
        toStringMethod.annotate(Override.class);
        // Invoke EqualsBuilder.reflectionHashCode(Object);
        toStringMethod.body()._return(
            codeModel.ref(HASHCODEBUILDER_CLASSNAME)
                     .staticInvoke("reflectionHashCode")
                     .arg(JExpr._this())
        );
        return;
    }

    @Override
    public int parseArgument(Options opt, String[] args, int i)
        throws BadCommandLineException
    {
        // eg.
        //   -Xcommons-lang:ToStringStyle=SIMPLE_STYLE
        // or
        //   -Xcommons-lang:ToStringStyle=<Fully qualified class name of a ToStringStyle subtype>
        String arg = args[i].trim();

        if (arg.startsWith(TOSTRING_STYLE_PARAM))
        {
            String value = arg.substring(TOSTRING_STYLE_PARAM.length());

            if (value.matches("^[A-Z0-9_]+$")) {
                try {
                    Class.forName(TOSTRINGSTYLE_CLASSNAME).getField(value);
                } catch (NoSuchFieldException | ClassNotFoundException | SecurityException e) {
                    logger.error("Could not verify : '" + TOSTRINGSTYLE_CLASSNAME + "." + value + "'. Assume it's not visible to generator.", e);
                }
                toStringStyle = value;
                return 1;
            }

            try {
                Class.forName(value);
            } catch (ClassNotFoundException e) {
                logger.warn("Could not find class: '" + value + "'. Assume it's not visible to generator.");
            }
            toStringClass = value;
            return 1;
        }

        // eg. -Xcommons-lang:addToStringMethod=TRUE
        if (arg.startsWith(TOSTRING_DISABLED_PARAM))
        {
            String toStringBoolean = arg.substring(TOSTRING_DISABLED_PARAM.length());
            toStringEnabled = Boolean.parseBoolean(toStringBoolean);
            return 1;
        }

        // eg. -Xcommons-lang:addEqualsMethod=TRUE
        if (arg.startsWith(EQUALS_DISABLED_PARAM))
        {
            String toStringBoolean = arg.substring(EQUALS_DISABLED_PARAM.length());
            equalsEnabled = Boolean.parseBoolean(toStringBoolean);
            return 1;
        }

        // eg. -Xcommons-lang:addHashCodeMethod=TRUE
        if (arg.startsWith(HASH_CODE_DISABLED_PARAM))
        {
            String toStringBoolean = arg.substring(HASH_CODE_DISABLED_PARAM.length());
            hashCodeEnabled = Boolean.parseBoolean(toStringBoolean);
            return 1;
        }

        // eg. -Xcommons-lang:equalsTestTransients=TRUE
        if (arg.startsWith(EQUALS_TEST_TRANSIENTS_PARAM))
        {
            String toStringBoolean = arg.substring(EQUALS_TEST_TRANSIENTS_PARAM.length());
            equalsTestTransients = Boolean.parseBoolean(toStringBoolean);
            return 1;
        }

        // eg. -Xcommons-lang:equalsTestRecursive=TRUE
        if (arg.startsWith(EQUALS_TEST_RECURSIVE_PARAM))
        {
            String toStringBoolean = arg.substring(EQUALS_TEST_RECURSIVE_PARAM.length());
            equalsTestRecursive = Boolean.parseBoolean(toStringBoolean);
            return 1;
        }

        return 0;
    }
}
