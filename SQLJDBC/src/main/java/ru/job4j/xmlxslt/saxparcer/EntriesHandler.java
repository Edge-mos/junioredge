package ru.job4j.xmlxslt.saxparcer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import ru.job4j.xmlxslt.Controller;

/**
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$. Обработчик для парсера, парсит аттрибуты(только) конвертируемого XML файла.
 * @since 29.11.2018.
 */
public class EntriesHandler extends DefaultHandler {
    private Long summary = 0l;
    private static final Logger logger = LoggerFactory.getLogger(EntriesHandler.class);

    public Long getSummary() {
        return summary;
    }

    public EntriesHandler() {
    }

    @Override
    public void startDocument() throws SAXException {
        logger.info("Start document parsing");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (attributes.getLength() > 0) {
            String currentAttribube = attributes.getValue(0);
            this.summary += Long.parseLong(currentAttribube);
        }
    }

    @Override
    public void endDocument() throws SAXException {
        logger.info("End document parsing");
    }
}
