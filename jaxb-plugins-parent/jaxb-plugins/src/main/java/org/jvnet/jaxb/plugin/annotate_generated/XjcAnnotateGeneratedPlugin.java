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
package org.jvnet.jaxb.plugin.annotate_generated;

import com.sun.codemodel.*;
import com.sun.tools.xjc.BadCommandLineException;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.Plugin;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.Outline;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jvnet.jaxb.xml.bind.Generated;
import org.xml.sax.ErrorHandler;

/**
 * <p>
 * XannotateGenerated when being embedded into strict PMD/SPOTBUGS/CodeCoverage environments
 * and not all interfaces will be covered.
 * </p>
 *
 * <p>
 * To add @org.jvnet.jaxb.xml.bind.Generated annotations to all generated nodes where possible;
 * useful for JaCoCo (which has built in support), or other style checkers and code coverage tools:
 *</p>
 * <pre>
 *     Example 1:
 *     -XannotateGenerated
 *     -XannotateGenerated:addGeneratedAnnotation=TRUE
 *
 *     Example 2:
 *     -XannotateGenerated
 *     -XannotateGenerated:customGeneratedAnnotationClass=com.example.MyGeneratedClass
 *
 *     to specify the use of
 *
 *     com.example.MyGeneratedClass, which must at least contain
 *
 *     @Documented
 *     @Retention(RetentionPolicy.RUNTIME)
 *     @Target({ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR})
 *     public @interface ${PREFIX}Generated${SUFFIX} {
 *     }
 *
 *     So that Jacoco and other classes can see it post compile time and match the REGEX/lookup.
 * </pr>
 *
 * <p>
 * To add the @SuppressFBWarnings annotation which is useful if you want to run SpotBugs/FindBugs on your class files.
 * To enable this feature, make sure com.github.spotbugs:spotbugs-annotations is on the classpath when you compile, and add the following config key:
 *</p>
 *
 * <pre>
 *     Example 2:
 *     -XannotateGenerated
 *     -XannotateGenerated:addSuppressFBWarnings=TRUE
 * </pre>
 *
 * <p>
 * To add the @SuppressWarnings("all") annotation to all generated nodes by default.
 * This can be turned off which is useful if you want to use static code analyzers like Checker Framework.
 *</p>
 *
 * <pre>
 *     Example 2:
 *     -XannotateGenerated
 *     -XannotateGenerated:addSuppressWarnings=FALSE
 * </pre>
 *
 * @author William Dutton
 */
public class XjcAnnotateGeneratedPlugin extends Plugin
{
    private static final String ADD_GENERATED_ANNOTATION_PARAM = "-XannotateGenerated:addGeneratedAnnotation=";
    private static final String CUSTOM_GENERATED_ANNOTATION_CLASS_PARAM = "-XannotateGenerated:customGeneratedAnnotationClass=";
    private static final String ADD_GENERATED_SUPPRESS_FB_WARNINGS_PARAM = "-XannotateGenerated:addSuppressFBWarnings=";
    private static final String ADD_GENERATED_SUPPRESS_WARNINGS_PARAM = "-XannotateGenerated:addSuppressWarnings=";
    // Will be a JDirectClass since we don't wish to have spotbugs-annotations in our plugins list.
    public static final String SUPPRESS_FBWARNINGS_FQ_CLASS = "edu.umd.cs.findbugs.annotations.SuppressFBWarnings";

    protected Log logger = LogFactory.getLog(getClass());

    private boolean addGeneratedAnnotation = true;
    private boolean suppresFBWarnings = false;
    private boolean suppressWarnings = true;
    private String customeGeneratedClass = null;

    @Override
    public String getOptionName()
    {
        return "XannotateGenerated";
    }

    @Override
    public String getUsage() {
        return "  -XannotateGenerated        :  To add @org.jvnet.jaxb.xml.bind.Generated annotations to all generated nodes where possible; "
            + "useful for JaCoCo (which has built in support), or other style checkers and code coverage tools:\n"
            + " Options value is TRUE or FALSE: \n"
            + " To Disable @Generated addition\n"
            + "\n"
            + "\t-XannotateGenerated\n"
            + "\t-XannotateGenerated:addGeneratedAnnotation=FALSE\n"
            + "\n"
            + "To use your own class for @GeneratedAnnotations, use the following variable\n"
            + "\t-XannotateGenerated\n"
            + "\t-XannotateGenerated:customGeneratedAnnotationClass=com.example.MyGeneratedClass\n"
            + "\n"
            + " To add the @SuppressFBWarnings annotation which is useful if you want to run SpotBugs/FindBugs on your class files."
            + " To enable this feature, make sure com.github.spotbugs:spotbugs-annotations is on the classpath when you compile, and add the following config key:\n"
            + "\n"
            + "\t-XannotateGenerated\n"
            + "\t-XannotateGenerated:addSuppressFBWarnings=TRUE\n"
            + "\n"
            + "\n"
            + " To add the @SuppressWarnings(\"all\") annotation to all generated nodes by default. "
            + " This can be turned off which is useful if you want to use static code analyzers like Checker Framework.\n"
            + "\n"
            + "\t-XannotateGenerated\n"
            + "\t-XannotateGenerated:addSuppressWarnings=FALSE\n"
            + "\n"
            + "\nYou can set multiple variables. i.e. to only have FB Warnings you can do:"
            + "\t-XannotateGenerated\n"
            + "\t-XannotateGenerated:addSuppressWarnings=FALSE\n"
            + "\t-XannotateGenerated:addSuppressFBWarnings=TRUE\n"
            + "\t-XannotateGenerated:addGeneratedAnnotation=FALSE\n";
    }

    @Override
    public boolean run(Outline outline,
        @SuppressWarnings("unused") Options opt,
        @SuppressWarnings("unused") ErrorHandler errorHandler)
    {
        // Process every pojo class generated by jaxb
        for (ClassOutline classOutline : outline.getClasses()) {
            JDefinedClass implClass = classOutline.implClass;
            this.addGeneratedAnnotationToClass(implClass);
            this.addSuppressFBWarnings(implClass);
            this.suppressWarnings(implClass);
        }
        return true;
    }

    private void addGeneratedAnnotationToClass(JDefinedClass implClass) {
        if (addGeneratedAnnotation) {
            if (customeGeneratedClass != null){
                implClass.annotate(implClass.owner().ref(customeGeneratedClass));
            } else {
                implClass.annotate(Generated.class);
            }
        }
    }

    private void addSuppressFBWarnings(JDefinedClass implClass) {
        if (suppresFBWarnings) {
            implClass.annotate(implClass.owner().ref(SUPPRESS_FBWARNINGS_FQ_CLASS));
        }
    }

    private void suppressWarnings(JDefinedClass implClass) {
        if (suppressWarnings) {
            JCodeModel codeModel = implClass.owner();
            JAnnotationUse annotation = implClass.annotate(SuppressWarnings.class);
            annotation.param("value", "all");
        }
    }

    @Override
    public int parseArgument(Options opt, String[] args, int i)
        throws BadCommandLineException
    {
        String arg = args[i].trim();

        // eg. -XannotateGenerated:addGeneratedAnnotation=FALSE
        // eg. -XannotateGenerated:addGeneratedAnnotation=TRUE
        if (arg.startsWith(ADD_GENERATED_ANNOTATION_PARAM))
        {
            String toStringEnabled = arg.substring(ADD_GENERATED_ANNOTATION_PARAM.length());
            addGeneratedAnnotation = Boolean.parseBoolean(toStringEnabled);
            return 1;
        }

        // eg. -XannotateGenerated:customGeneratedAnnotationClass=com.example.MyGeneratedClass
        if (arg.startsWith(CUSTOM_GENERATED_ANNOTATION_CLASS_PARAM))
        {
            String value = arg.substring(CUSTOM_GENERATED_ANNOTATION_CLASS_PARAM.length());
            try {
                Class.forName(value);
            } catch (ClassNotFoundException e) {
                logger.warn("Could not find class: '" + value + "'. Assume it's not visible to generator.");
            }
            customeGeneratedClass = value;
            return 1;
        }

        // eg. -XannotateGenerated:addSuppressFBWarnings=FALSE
        // eg. -XannotateGenerated:addSuppressFBWarnings=TRUE
        if (arg.startsWith(ADD_GENERATED_SUPPRESS_FB_WARNINGS_PARAM))
        {
            String toStringEnabled = arg.substring(ADD_GENERATED_SUPPRESS_FB_WARNINGS_PARAM.length());
            suppresFBWarnings = Boolean.parseBoolean(toStringEnabled);
            return 1;
        }

        // eg. -XannotateGenerated:addSuppressWarnings=FALSE
        // eg. -XannotateGenerated:addSuppressWarnings=TRUE
        if (arg.startsWith(ADD_GENERATED_SUPPRESS_WARNINGS_PARAM))
        {
            String toStringEnabled = arg.substring(ADD_GENERATED_SUPPRESS_WARNINGS_PARAM.length());
            suppressWarnings = Boolean.parseBoolean(toStringEnabled);
            return 1;
        }

        return 0;
    }
}
