package org.jvnet.jaxb.tests.copyable;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.UnmarshalException;
import jakarta.xml.bind.Unmarshaller;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jvnet.jaxb.maven.test.RunXJCMojo;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class FooElementTest extends RunXJCMojo {

    @Test
    public void testFooElementEmptyAttributes() throws Exception {
        FooElement element = new FooElement();
        element.getBarAttribute().clear();

        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(new StreamSource(new File(getSchemaDirectory(), "Example.xsd")));
        JAXBContext context = JAXBContext.newInstance(FooElement.class);

        Marshaller marshaller = context.createMarshaller();
        marshaller.setSchema(schema);
        // THEN the copied object with values in the attribute is serialized just fine
        StringWriter sw1 = new StringWriter();
        marshaller.marshal(element, sw1);
        System.out.println(sw1);
    }

    @Test
    public void testFooElement() throws Exception {
        Class<?> fooElementClass = Class.forName("org.jvnet.jaxb.tests.copyable.FooElement");
        Assertions.assertNotNull(fooElementClass);

        String elementWithEmptyAttributeXml = "<FooElement BarAttribute=\"\"/>";
        // AND a FooElement with some list elements in the BarAttribute (normal case, should work fine)
        String elementWithValuesInAttributeXml = "<FooElement BarAttribute=\"a b c\"/>";
        // AND an invalid FooElement without BarAttribute
        String elementWithoutListAttributeXml = "<FooElement />";

        // AND a validating unmarshaller for the class
        JAXBContext context = JAXBContext.newInstance(fooElementClass);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(new StreamSource(new File(getSchemaDirectory(), "Example.xsd")));
        unmarshaller.setSchema(schema);

        // EXPECT the invalid FooElement cannot be unmarshalled, showing that XML validation is working
        Assertions.assertThrows(UnmarshalException.class,
            () -> unmarshaller.unmarshal(new StringReader(elementWithoutListAttributeXml)));

        // WHEN the element with values in attribute is unmarshalled
        FooElement elementWithValuesInAttribute = (FooElement) unmarshaller.unmarshal(new StringReader(elementWithValuesInAttributeXml));

        // THEN the list from the attribute is filled as expected
        // (use field directly instead of getter, as the generated getter would convert null values to empty lists)
        Field barAttributeField = fooElementClass.getDeclaredField("barAttribute");
        Assertions.assertEquals(List.of("a", "b", "c"), barAttributeField.get(elementWithValuesInAttribute));

        // WHEN the element with empty attribute is unmarshalled
        FooElement elementWithEmptyAttribute = (FooElement) unmarshaller.unmarshal(new StringReader(elementWithEmptyAttributeXml));
        // THEN the list from the attribute is empty
        Assertions.assertEquals(List.of(), barAttributeField.get(elementWithEmptyAttribute));

        // WHEN both FooElements (the one with values and the one with empty list in attribute) are copied
        Method cloneMethod = fooElementClass.getMethod("clone"); // clone() invokes copyTo(createNewInstance())
        FooElement clonedElementWithEmptyAttribute = (FooElement) cloneMethod.invoke(elementWithEmptyAttribute);
        FooElement clonedElementWithValuesInAttribute = (FooElement) cloneMethod.invoke(elementWithValuesInAttribute);

        // THEN the list from the attribute with values is copied as expected
        Assertions.assertEquals(List.of("a", "b", "c"), barAttributeField.get(clonedElementWithValuesInAttribute));
        // AND the copied list from the empty attribute is not null and empty
        Assertions.assertNotNull(barAttributeField.get(clonedElementWithEmptyAttribute));
        Assertions.assertTrue(((List<String>) barAttributeField.get(clonedElementWithEmptyAttribute)).isEmpty());

        // WHEN the copied objects are serialized again with a validating marshaller
        Marshaller marshaller = context.createMarshaller();
        marshaller.setSchema(schema);
        // THEN the copied object with values in the attribute is serialized just fine
        StringWriter sw1 = new StringWriter();
        marshaller.marshal(clonedElementWithValuesInAttribute, sw1);
        System.out.println(sw1);
        // AND the copied object with originally empty (but now null) attribute is OK too
        StringWriter sw2 = new StringWriter();
        marshaller.marshal(clonedElementWithEmptyAttribute, sw2);
        System.out.println(sw2);
    }
}
