/**
 * Copyright © 2005-2015, Alexey Valikov
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those
 * of the authors and should not be interpreted as representing official policies,
 * either expressed or implied, of the FreeBSD Project.
 */
package org.jvnet.jaxb.plugin.removexmlannotation;

import com.sun.codemodel.JAnnotatable;
import com.sun.codemodel.JAnnotationUse;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JPackage;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.CElementInfo;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.ElementOutline;
import com.sun.tools.xjc.outline.EnumConstantOutline;
import com.sun.tools.xjc.outline.EnumOutline;
import com.sun.tools.xjc.outline.FieldOutline;
import com.sun.tools.xjc.outline.Outline;
import org.jvnet.jaxb.plugin.AbstractPlugin;
import org.jvnet.jaxb.plugin.AnnotationTarget;
import org.xml.sax.ErrorHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class RemoveXmlAnnotationPlugin extends AbstractPlugin {

    private boolean removeObjectFactory = false;
    /**
     * Name of Option to enable this plugin
     */
    static private final String OPTION_NAME = "XremoveXmlAnnotation";
    private static final String OPTION_NAME_REMOVE_OBJECT_FACTORY = "-" + OPTION_NAME + ":removeObjectFactory";

    @Override
    public String getOptionName() {
        return OPTION_NAME;
    }

    @Override
    public String getUsage() {
        return "Remove all jakarta.xml.bind / javax.xml.bind annotation on classes generated";
    }

    @Override
    public boolean run(Outline outline, Options options, ErrorHandler errorHandler) {

        for (final CElementInfo elementInfo : outline.getModel().getAllElements()) {
            final ElementOutline elementOutline = outline.getElement(elementInfo);
            if (elementOutline != null) {
                processElementOutline(elementOutline, options, errorHandler);
            }
        }

        for (final ClassOutline classOutline : outline.getClasses()) {
            processClassOutline(classOutline, options, errorHandler);
        }
        for (final EnumOutline enumOutline : outline.getEnums()) {
            processEnumOutline(enumOutline, options, errorHandler);
        }
        if (removeObjectFactory) {
            outline.getCodeModel().packages().forEachRemaining(new Consumer<JPackage>() {
                @Override
                public void accept(JPackage jPackage) {
                    if (jPackage.hasClasses()) {
                        JDefinedClass jDefinedClass = jPackage._getClass("ObjectFactory");
                        if (jDefinedClass != null) {
                            jPackage.remove(jDefinedClass);
                        }
                    }
                }
            });
        }
        return true;
    }

    protected void processElementOutline(ElementOutline elementOutline, Options options, ErrorHandler errorHandler) {
        try {
            final JAnnotatable annotatable = AnnotationTarget.ELEMENT.getAnnotatable(elementOutline.parent(), elementOutline);
            removeXmlAnnotation(errorHandler, annotatable);
        } catch (IllegalArgumentException iaex) {
            logger.error("Error removing the annotation.", iaex);
        }
    }

    protected void processEnumOutline(EnumOutline enumOutline, Options options, ErrorHandler errorHandler) {
        try {
            final JAnnotatable annotatable = AnnotationTarget.ENUM.getAnnotatable(enumOutline.parent(), enumOutline);
            removeXmlAnnotation(errorHandler, annotatable);
        } catch (IllegalArgumentException iaex) {
            logger.error("Error applying the annotation.", iaex);
        }

        for (final EnumConstantOutline enumConstantOutline : enumOutline.constants) {
            processEnumConstantOutline(enumOutline, enumConstantOutline, options, errorHandler);
        }

    }

    protected void processClassOutline(ClassOutline classOutline, Options options, ErrorHandler errorHandler) {
        try {
            final JAnnotatable annotatable = AnnotationTarget.CLASS.getAnnotatable(classOutline.parent(), classOutline);
            removeXmlAnnotation(errorHandler, annotatable);
        } catch (IllegalArgumentException iaex) {
            logger.error("Error applying the annotation.", iaex);
        }

        for (final FieldOutline fieldOutline : classOutline.getDeclaredFields()) {
            processFieldOutline(classOutline, fieldOutline, options, errorHandler);
        }

    }

    protected void processFieldOutline(ClassOutline classOutline, FieldOutline fieldOutline, Options options, ErrorHandler errorHandler) {
        try {
            final JAnnotatable annotatable = AnnotationTarget.PROPERTY_FIELD.getAnnotatable(fieldOutline.parent().parent(), fieldOutline);
            removeXmlAnnotation(errorHandler, annotatable);
        } catch (IllegalArgumentException iaex) {
            logger.error("Error removing the annotation.", iaex);
        }
    }

    protected void processEnumConstantOutline(EnumOutline enumOutline,
            EnumConstantOutline enumConstantOutline, Options options,
            ErrorHandler errorHandler) {
        try {
            final JAnnotatable annotatable = AnnotationTarget.ENUM_CONSTANT.getAnnotatable(enumOutline.parent(), enumConstantOutline);
            removeXmlAnnotation(errorHandler, annotatable);
        } catch (IllegalArgumentException iaex) {
            logger.error("Error removing the annotation.", iaex);
        }
    }

    private void removeXmlAnnotation(
            ErrorHandler errorHandler,
            final JAnnotatable annotatable) {

        List<JAnnotationUse> annotationsToRemove = new ArrayList<>();
        for (JAnnotationUse annotation : annotatable.annotations()) {
            String packageName = annotation.getAnnotationClass()._package().name();
            if (packageName == null) {
                continue;
            }
            if (packageName.startsWith("jakarta.xml.bind")) {
                annotationsToRemove.add(annotation);
            } else if (packageName.startsWith("javax.xml.bind")) {
                annotationsToRemove.add(annotation);
            }
        }
        for (JAnnotationUse annotation : annotationsToRemove) {
            annotatable.removeAnnotation(annotation);
        }
    }

    @Override
    public int parseArgument(Options opt, String[] args, int i) {
        // eg. -Xdefault-value:all
        String arg = args[i].trim();
        if (arg.startsWith(OPTION_NAME_REMOVE_OBJECT_FACTORY)) {
            removeObjectFactory = true;
            return 1;
        }
        return 0;
    }
}
