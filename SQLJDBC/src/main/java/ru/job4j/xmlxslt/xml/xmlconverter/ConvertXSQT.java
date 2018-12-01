package ru.job4j.xmlxslt.xml.xmlconverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.xmlxslt.Controller;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.File;

import static javax.xml.transform.OutputKeys.DOCTYPE_PUBLIC;
import static javax.xml.transform.OutputKeys.INDENT;

public interface ConvertXSQT {
    Logger logger = LoggerFactory.getLogger(ConvertXSQT.class);

    static void xslConvert(String xslPath, String inputXmlPath, String output) {
        StreamSource xslFile = new StreamSource(new File(xslPath));
        StreamSource inputFile = new StreamSource(new File(inputXmlPath));
        StreamResult outputFile = new StreamResult(new File(output));

        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer trans = tf.newTransformer(xslFile);
            trans.setOutputProperty(OutputKeys.INDENT, "yes");
            trans.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes");
            trans.transform(inputFile, outputFile);
        } catch (TransformerException ex) {
            logger.error("Transforming failure", ex);
        }
    }
}
