package ru.job4j.xmlxslt.xml;

import ru.job4j.xmlxslt.models.Entries;
import ru.job4j.xmlxslt.models.Entry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.List;

/**
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$. Утилитарный класс, принимает путь сохранения XML файла(storeXML) и создаёт XML файл по JAXB.
 * @since 29.11.2018.
 */
public class XmlUsage {
    private File file;

    public void storeXML(String pathToFile) {
        try {
            if (pathToFile.length() == 0) {
                throw new IllegalArgumentException();
            }
            this.file = new File(pathToFile);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
    }

    public void save(List<Entry> entries) {
        try {
            if(entries.size() == 0) {
                throw new IllegalArgumentException();
            }
            JAXBContext jaxbContext = JAXBContext.newInstance(Entries.class);
            Marshaller mar = jaxbContext.createMarshaller();
            mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            Entries root = new Entries(entries);
            mar.marshal(root, this.file);
        } catch (JAXBException | IllegalArgumentException ex ) {
            ex.printStackTrace();
        }
    }

}
