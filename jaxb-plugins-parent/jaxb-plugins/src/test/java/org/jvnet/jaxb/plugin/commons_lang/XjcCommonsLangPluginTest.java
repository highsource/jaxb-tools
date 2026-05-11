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

import com.sun.codemodel.*;
import com.sun.tools.xjc.BadCommandLineException;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.Outline;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.xml.sax.ErrorHandler;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class XjcCommonsLangPluginTest {

    @Mock
    private Outline outline;
    @Mock
    private Options options;
    @Mock
    private ErrorHandler errorHandler;
    @Mock
    private CClassInfo cClassInfo;

    private JDefinedClass implClass;
    private XjcCommonsLangPlugin plugin;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        JCodeModel codeModel = new JCodeModel();
        implClass = codeModel._class("TestGeneratedClass");

        ClassOutline classOutline = new TestableClassOutline(cClassInfo, implClass);
        when(outline.getClasses()).thenReturn((Collection) Collections.singletonList(classOutline));

        plugin = new XjcCommonsLangPlugin();
    }

    @Test
    void testGetOptionName() {
        assertEquals("Xcommons-lang", plugin.getOptionName());
    }

    @Test
    void testGetUsage() {
        String usage = plugin.getUsage();
        assertNotNull(usage);
        assertEquals("  -Xcommons-lang        :  generate toString(), hashCode() and equals() for generated code using Jakarta's common-lang [\n" +
            "\t  -Xcommons-lang:ToStringStyle=MULTI_LINE_STYLE\n" +
            "\t| -Xcommons-lang:ToStringStyle=DEFAULT_STYLE\n" +
            "\t| -Xcommons-lang:ToStringStyle=NO_FIELD_NAMES_STYLE\n" +
            "\t| -Xcommons-lang:ToStringStyle=SHORT_PREFIX_STYLE\n" +
            "\t| -Xcommons-lang:ToStringStyle=SIMPLE_STYLE\n" +
            "\t| -Xcommons-lang:ToStringStyle=NO_CLASS_NAME_STYLE\n" +
            "\t| -Xcommons-lang:ToStringStyle=JSON_STYLE\n" +
            "\t| -Xcommons-lang:ToStringStyle=<any static final variables inside org.apache.commons.lang3.builder.ToStringStyle following regex ^[A-Z0-9_]+>$\n" +
            "\t| -Xcommons-lang:ToStringStyle=<Fully qualified class name of a ToStringStyle subtype>\n" +
            "]\n" +
            " Note: custom ToStringStyle class is needed if you wish to turn off setUseIdentityHashCode on top of MULTI_LINE_STYLE\n" +
            " Equals options:\n" +
            "  -Xcommons-lang:equalsTestTransients=TRUE|FALSE (default: FALSE)\n" +
            "  -Xcommons-lang:equalsTestRecursive=TRUE|FALSE (default: FALSE)\n" +
            " NOTE: \n" +
            " Performance Cost: When equalsTestRecursive is TRUE, due to Deep reflection is significantly slower than standard equality checks.\n" +
            "\n" +
            " To disable one of the generated plugins if you wish to use another module use one of the following:\n" +
            "  -Xcommons-lang:addToStringMethod=FALSE\n" +
            "  -Xcommons-lang:addHashCodeMethod=FALSE\n" +
            "  -Xcommons-lang:addEqualsMethod=FALSE\n", usage);
    }

    @Test
    void testRunGeneratesAllMethods() throws Exception {
        plugin.run(outline, options, errorHandler);

        assertTrue(hasMethod(implClass, "toString"), "toString() should be generated");
        assertTrue(hasMethod(implClass, "equals"), "equals() should be generated");
        assertTrue(hasMethod(implClass, "hashCode"), "hashCode() should be generated");
    }

    @Test
    void testRunSkipsMethodsWhenDisabled() throws Exception {
        // Set flags to disable generation
        plugin.parseArgument(options, new String[]{"-Xcommons-lang:addToStringMethod=FALSE"}, 0);
        plugin.parseArgument(options, new String[]{"-Xcommons-lang:addEqualsMethod=FALSE"}, 0);
        plugin.parseArgument(options, new String[]{"-Xcommons-lang:addHashCodeMethod=FALSE"}, 0);

        plugin.run(outline, options, errorHandler);

        // Verify that the methods were NOT added to the real object
        assertFalse(hasMethod(implClass, "toString"), "toString() should not be generated");
        assertFalse(hasMethod(implClass, "equals"), "equals() should not be generated");
        assertFalse(hasMethod(implClass, "hashCode"), "hashCode() should not be generated");
    }


    @Test
    void testOnlyToStringGenerated() throws Exception {
        assertEquals(1, plugin.parseArgument(options, new String[]{"-Xcommons-lang:addToStringMethod=TRUE"}, 0));
        assertEquals(1, plugin.parseArgument(options, new String[]{"-Xcommons-lang:addEqualsMethod=FALSE"}, 0));
        assertEquals(1, plugin.parseArgument(options, new String[]{"-Xcommons-lang:addHashCodeMethod=FALSE"}, 0));

        plugin.run(outline, options, errorHandler);

        assertTrue(hasMethod(implClass, "toString"), "toString() should be generated");
        assertFalse(hasMethod(implClass, "equals"), "equals() should be generated");
        assertFalse(hasMethod(implClass, "hashCode"), "hashCode() should be generated");
    }

    @Test
    void testOnlyEqualsGenerated() throws Exception {
        assertEquals(1, plugin.parseArgument(options, new String[]{"-Xcommons-lang:addToStringMethod=FALSE"}, 0));
        assertEquals(1, plugin.parseArgument(options, new String[]{"-Xcommons-lang:addEqualsMethod=TRUE"}, 0));
        assertEquals(1, plugin.parseArgument(options, new String[]{"-Xcommons-lang:addHashCodeMethod=FALSE"}, 0));

        plugin.run(outline, options, errorHandler);

        assertFalse(hasMethod(implClass, "toString"), "toString() should be generated");
        assertTrue(hasMethod(implClass, "equals"), "equals() should be generated");
        JMethod method = getMethod(implClass, "equals").get();
        assertEquals(1, method.listParams().length);
        assertEquals("that", Arrays.stream(method.listParams()).findFirst().get().name());
        assertEquals("java.lang.Object", Arrays.stream(method.listParams()).findFirst().get().type().fullName());
        assertFalse(hasMethod(implClass, "hashCode"), "hashCode() should be generated");
    }

    @Test
    void testOnlyEqualsGeneratedWithTestTransients() throws Exception {
        assertEquals(1, plugin.parseArgument(options, new String[]{"-Xcommons-lang:addToStringMethod=FALSE"}, 0));
        assertEquals(1, plugin.parseArgument(options, new String[]{"-Xcommons-lang:addEqualsMethod=TRUE"}, 0));
        assertEquals(1, plugin.parseArgument(options, new String[]{"-Xcommons-lang:addHashCodeMethod=FALSE"}, 0));
        assertEquals(1, plugin.parseArgument(options, new String[]{"-Xcommons-lang:equalsTestTransients=TRUE"}, 0));

        plugin.run(outline, options, errorHandler);

        assertFalse(hasMethod(implClass, "toString"), "toString() should be generated");
        assertTrue(hasMethod(implClass, "equals"), "equals() should be generated");
        assertFalse(hasMethod(implClass, "hashCode"), "hashCode() should be generated");
    }

    @Test
    void testOnlyEqualsGeneratedWithRecursive() throws Exception {
        assertEquals(1, plugin.parseArgument(options, new String[]{"-Xcommons-lang:addToStringMethod=FALSE"}, 0));
        assertEquals(1, plugin.parseArgument(options, new String[]{"-Xcommons-lang:addEqualsMethod=TRUE"}, 0));
        assertEquals(1, plugin.parseArgument(options, new String[]{"-Xcommons-lang:addHashCodeMethod=FALSE"}, 0));
        assertEquals(1, plugin.parseArgument(options, new String[]{"-Xcommons-lang:equalsTestRecursive=TRUE"}, 0));

        plugin.run(outline, options, errorHandler);

        assertFalse(hasMethod(implClass, "toString"), "toString() should be generated");
        assertTrue(hasMethod(implClass, "equals"), "equals() should be generated");
        assertFalse(hasMethod(implClass, "hashCode"), "hashCode() should be generated");
    }

    @Test
    void testOnlyEqualsGeneratedWithTransiantAndRecursive() throws Exception {
        assertEquals(1, plugin.parseArgument(options, new String[]{"-Xcommons-lang:addToStringMethod=FALSE"}, 0));
        assertEquals(1, plugin.parseArgument(options, new String[]{"-Xcommons-lang:addEqualsMethod=TRUE"}, 0));
        assertEquals(1, plugin.parseArgument(options, new String[]{"-Xcommons-lang:addHashCodeMethod=FALSE"}, 0));
        assertEquals(1, plugin.parseArgument(options, new String[]{"-Xcommons-lang:equalsTestTransients=TRUE"}, 0));
        assertEquals(1, plugin.parseArgument(options, new String[]{"-Xcommons-lang:equalsTestRecursive=TRUE"}, 0));

        plugin.run(outline, options, errorHandler);

        assertFalse(hasMethod(implClass, "toString"), "toString() should be generated");
        assertTrue(hasMethod(implClass, "equals"), "equals() should be generated");
        JMethod method = getMethod(implClass, "equals").get();
        assertEquals(1, method.listParams().length);
        assertEquals("that", Arrays.stream(method.listParams()).findFirst().get().name());
        assertEquals("java.lang.Object", Arrays.stream(method.listParams()).findFirst().get().type().fullName());
        //Unsure how to verify args inside since post jdk8 its now in a class that does hides its classes externally.
        assertFalse(hasMethod(implClass, "hashCode"), "hashCode() should be generated");
    }

    @Test
    void testOnlyHashCodeGenerated() throws Exception {
        assertEquals(1, plugin.parseArgument(options, new String[]{"-Xcommons-lang:addToStringMethod=FALSE"}, 0));
        assertEquals(1, plugin.parseArgument(options, new String[]{"-Xcommons-lang:addEqualsMethod=FALSE"}, 0));
        assertEquals(1, plugin.parseArgument(options, new String[]{"-Xcommons-lang:addHashCodeMethod=TRUE"}, 0));

        plugin.run(outline, options, errorHandler);

        assertFalse(hasMethod(implClass, "toString"), "toString() should not be generated");
        assertFalse(hasMethod(implClass, "equals"), "equals() should not be generated");
        assertTrue(hasMethod(implClass, "hashCode"), "hashCode() should be generated");
    }


    @Test
    void testParseArgumentToStringStyleStandard() throws BadCommandLineException {
        String arg = "-Xcommons-lang:ToStringStyle=SIMPLE_STYLE";
        assertEquals(1, plugin.parseArgument(options, new String[]{arg}, 0));

        plugin.run(outline, options, errorHandler);

        assertTrue(hasMethod(implClass, "toString"), "toString() should be generated");
    }

    @Test
    void testParseArgumentToStringStyleCustom() throws BadCommandLineException {
        String arg = "-Xcommons-lang:ToStringStyle=java.lang.String";
        assertEquals(1, plugin.parseArgument(options, new String[]{arg}, 0));

        plugin.run(outline, options, errorHandler);

        assertTrue(hasMethod(implClass, "toString"), "toString() should be generated");
    }

    @Test
    void testParseArgumentToStringStyleToClassGeneratorCantSeet() throws BadCommandLineException {
        String arg = "-Xcommons-lang:ToStringStyle=com.non.existent.Style";
        assertEquals(1, plugin.parseArgument(options, new String[]{arg}, 0));

        plugin.run(outline, options, errorHandler);

        assertTrue(hasMethod(implClass, "toString"), "toString() should be generated");
    }

    @Test
    void testParseArgumentDisablingFlags() throws BadCommandLineException {
        assertEquals(1, plugin.parseArgument(options, new String[]{"-Xcommons-lang:addToStringMethod=FALSE"}, 0));
        assertEquals(1, plugin.parseArgument(options, new String[]{"-Xcommons-lang:addEqualsMethod=FALSE"}, 0));
        assertEquals(1, plugin.parseArgument(options, new String[]{"-Xcommons-lang:addHashCodeMethod=FALSE"}, 0));

        plugin.run(outline, options, errorHandler);

        assertFalse(hasMethod(implClass, "toString"), "toString() should not be generated");
        assertFalse(hasMethod(implClass, "equals"), "equals() should not be generated");
        assertFalse(hasMethod(implClass, "hashCode"), "hashCode() should not be generated");
    }

    @Test
    void testParseArgumentUnknown() throws BadCommandLineException {
        assertEquals(0, plugin.parseArgument(options, new String[]{"-Xunknown-param"}, 0));

        plugin.run(outline, options, errorHandler);

        assertTrue(hasMethod(implClass, "toString"), "toString() should be generated");
        assertTrue(hasMethod(implClass, "equals"), "equals() should be generated");
        assertTrue(hasMethod(implClass, "hashCode"), "hashCode() should be generated");
    }

    @Test
    void testParseArgumentToStringStyleInvalidClassError() throws BadCommandLineException {
        String arg = "-Xcommons-lang:ToStringStyle=NO_FINAL_STATIC_EXISTS_FOR_THIS";
        assertEquals(1, plugin.parseArgument(options, new String[]{arg}, 0));

        plugin.run(outline, options, errorHandler);

        assertTrue(hasMethod(implClass, "toString"), "toString() should be generated");
    }


    @Test
    void testCreateToStringMethodWithCustomClassBranch() throws Exception {
        String arg = "-Xcommons-lang:ToStringStyle=java.lang.String";
        plugin.parseArgument(options, new String[]{arg}, 0);

        assertTrue(plugin.run(outline, options, errorHandler));

        assertTrue(hasMethod(implClass, "toString"), "toString() should be generated using custom class branch");
    }

    private boolean hasMethod(JDefinedClass clazz, String methodName) {
        for (JMethod m : clazz.methods()) {
            if (m.name().equals(methodName)) {
                return true;
            }
        }
        return false;
    }

    private Optional<JMethod> getMethod(JDefinedClass clazz, String methodName) {
        return clazz.methods().stream()
            .filter(anno -> anno.name().equals(methodName))
            .findFirst();
    }

    public static class TestableClassOutline extends ClassOutline {
        public TestableClassOutline(CClassInfo target, JDefinedClass implClass) {
            super(target, null, null, implClass);
        }

        @Override
        public Outline parent() {
            return null;
        }
    }
}
