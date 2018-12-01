package ru.job4j.xmlxslt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.xmlxslt.dao.DbDaoImpl;
import ru.job4j.xmlxslt.dao.StorageDao;
import ru.job4j.xmlxslt.exeptions.DaoExeption;
import ru.job4j.xmlxslt.models.Entry;
import ru.job4j.xmlxslt.saxparcer.EntriesHandler;
import ru.job4j.xmlxslt.saxparcer.Parser;
import ru.job4j.xmlxslt.validator.ValidatorXSD;
import ru.job4j.xmlxslt.xml.XmlUsage;
import ru.job4j.xmlxslt.xml.xmlconverter.ConvertXSQT;

import java.util.List;

/**
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$. Контроллер программы.
 * @since 29.11.2018.
 */
public class Controller {
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    private StorageDao storage = new DbDaoImpl();
    private XmlUsage xmlUsage = new XmlUsage();
    private EntriesHandler handler = new EntriesHandler();
    private Parser saxParser = new Parser(this.handler);


    /**
     * Создание базы данных, если не созданна.
     */
    public void init() {
        try {
            this.storage.init();
        } catch (DaoExeption daoExeption) {
            logger.error(daoExeption.getMessage(), daoExeption);
        }
    }

    /**
     *  Удаление базы данных, если присутствует.
     */
    public void dropDb() {
        try {
            this.storage.drop();
        } catch (DaoExeption daoExeption) {
            logger.error(daoExeption.getMessage(), daoExeption);
        }
    }

    /**
     * Ввод в базу данных числовых значений начиная с 1.
     * @param to до какого значения.
     */
    public void insertValuesToDb(int to) {
        try {
            this.storage.insertValue(to);
        } catch (DaoExeption daoExeption) {
            logger.error(daoExeption.getMessage(), daoExeption);
        }
    }

    /**
     * Получение данных из БД.
     * @return либо данные, либо null.
     */
    public List<Entry> loadFromDb() {
        try {
            return this.storage.loadValues();
        } catch (DaoExeption daoExeption) {
            logger.error(daoExeption.getMessage(), daoExeption);
        }
        return null;
    }

    /**
     * Формирование XML файла из данных БД.
     * @param out Выходной файл(путь).
     * @param values Данные из БД.
     */
    public void createXMLFile(String out, List<Entry> values) {
        this.xmlUsage.storeXML(out);
        this.xmlUsage.save(values);
    }

    /**
     * Производит валидацию выходного XML файла по схеме, если верно, трансформирует по XSL и выдаёт трансформируемый файл.
     * @param xmlLocation Путь до файла XML, который нужно валидировать и трансформировать.
     * @param xsdSchemeLocation Схема валидации.
     * @param xslFileLocation Схема трансформации для входного XML.
     * @param convertedOutFile На выходе трансформированный XML.
     */
    public void validateAndConvertXML(String xmlLocation, String xsdSchemeLocation, String xslFileLocation, String convertedOutFile) {
        boolean validationRes = ValidatorXSD.validate(xmlLocation, xsdSchemeLocation);
        if(validationRes) {
            ConvertXSQT.xslConvert(xslFileLocation, xmlLocation, convertedOutFile);
        }
    }

    /**
     * Парсинг выходного файла и получение суммы всех его значений.
     * @param convertedXMLPath Путь до конвертированного файла XML.
     * @return Сумма всех атрибутов.
     */
    public long parseResult(String convertedXMLPath) {
        this.saxParser.parse(convertedXMLPath);
        return this.handler.getSummary();
    }
}
