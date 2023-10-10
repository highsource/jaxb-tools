package org.jvnet.jaxb.plugin.simpletostring;

import com.sun.codemodel.JCodeModel;
import org.apache.commons.lang3.Validate;
import org.jvnet.jaxb.plugin.codegenerator.CodeGenerationAbstraction;

import java.time.format.DateTimeFormatter;

public class ToStringCodeGenerator extends
    CodeGenerationAbstraction<ToStringArguments> {

    public ToStringCodeGenerator(JCodeModel codeModel, String defaultDateFormatterRef, String defaultDateFormatterPattern) {
        super(new ToStringCodeGenerationImplementor(Validate.notNull(codeModel), defaultDateFormatterRef, defaultDateFormatterPattern));
    }

}
