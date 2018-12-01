package ru.job4j.xmlxslt.saxparcer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

/**
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$.
 * @since 29.11.2018.
 */
public class Parser {
    private EntriesHandler handler;
    private static final Logger logger = LoggerFactory.getLogger(Parser.class);

    public EntriesHandler getHandler() {
        return this.handler;
    }

    public Parser(EntriesHandler handler) {
        this.handler = handler;

    }

    public void parse(String pathToFile) {
        File file = new File(pathToFile);
        if (file.exists()) {
            try {
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser parser = factory.newSAXParser();
                parser.parse(file, this.handler);
            } catch (ParserConfigurationException e) {
                logger.error("Parser Configuration error", e);
            } catch (IOException e) {
                logger.error("IOE parser", e);
            } catch (SAXException e) {
                logger.error("SAX error parser", e);
            }
        }
    }
}
