package ru.job4j.xmlxslt.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;
import ru.job4j.xmlxslt.Controller;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

/**
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$. Валидатор для XML, требует XSD схему документа.
 * @since 29.11.2018.
 */
public class ValidatorXSD {
    private static final Logger logger = LoggerFactory.getLogger(ValidatorXSD.class);

    public static boolean validate(String xmlLocation, String xsdLocation) {
        boolean result = true;
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
            Schema schema = factory.newSchema(new File(xsdLocation));
            Validator validator = schema.newValidator();
            Source source = new StreamSource(xmlLocation);
            validator.validate(source);
        } catch (SAXException ex) {
            result = false;
        } catch (IOException e) {
            logger.error("IO trouble", e);
            e.printStackTrace();
        }
        return result;
    }
}
