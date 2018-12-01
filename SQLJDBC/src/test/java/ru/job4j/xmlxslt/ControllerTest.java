package ru.job4j.xmlxslt;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.xmlxslt.models.Entries;
import ru.job4j.xmlxslt.models.Entry;
import ru.job4j.xmlxslt.validator.ValidatorXSD;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ControllerTest {
    private Controller controller = new Controller();

    @Before
    public void setup() {
        this.controller.init();
    }

    @After
    public void drop() {
        this.controller.dropDb();
    }

    @Test
    public void whenAddingValuesToDB() {
        this.controller.insertValuesToDb(100);
        List<Entry> entries= this.controller.loadFromDb();
        assertThat(entries.size(), is(100));
    }

    @Test
    public void whenGetXMLFromDbValuesThanFileExosts() {
        this.controller.insertValuesToDb(5);
        this.controller.createXMLFile("../SQLJDBC/src/test/java/ru/job4j/xmlxslt/OUT.xml", this.controller.loadFromDb());
        File file = new File("../SQLJDBC/src/test/java/ru/job4j/xmlxslt/OUT.xml");
        assertThat(file.exists(), is(true));
    }

    @Test
    public void whenValidateAndConvertTrueAndConvertedFileExists() {
        this.controller.insertValuesToDb(100);
        this.controller.createXMLFile("../SQLJDBC/src/test/java/ru/job4j/xmlxslt/OUT.xml", this.controller.loadFromDb());

        this.controller.validateAndConvertXML("../SQLJDBC/src/test/java/ru/job4j/xmlxslt/OUT.xml",
                "../SQLJDBC/src/test/java/ru/job4j/xmlxslt/entries.xsd",
                "../SQLJDBC/src/test/java/ru/job4j/xmlxslt/convertion.xsl",
                "../SQLJDBC/src/test/java/ru/job4j/xmlxslt/CONVERTED.xml");
        File file = new File("../SQLJDBC/src/test/java/ru/job4j/xmlxslt/CONVERTED.xml");
        assertThat(file.exists(), is(true));
    }

    @Test
    public void whenFileNotValidateThanFalse() {
        // Вносим правки в файл вручную.
        boolean res = ValidatorXSD.validate("../SQLJDBC/src/test/java/ru/job4j/xmlxslt/DAMAGE.xml",
                "../SQLJDBC/src/test/java/ru/job4j/xmlxslt/entries.xsd");
        assertThat(res, is(false));
    }

    @Test
    public void whenSummaryResultIsTrue() {
        this.controller.insertValuesToDb(100);
        this.controller.createXMLFile("../SQLJDBC/src/test/java/ru/job4j/xmlxslt/OUT.xml", this.controller.loadFromDb());

        this.controller.validateAndConvertXML("../SQLJDBC/src/test/java/ru/job4j/xmlxslt/OUT.xml",
                "../SQLJDBC/src/test/java/ru/job4j/xmlxslt/entries.xsd",
                "../SQLJDBC/src/test/java/ru/job4j/xmlxslt/convertion.xsl",
                "../SQLJDBC/src/test/java/ru/job4j/xmlxslt/CONVERTED.xml");
        long res = this.controller.parseResult("../SQLJDBC/src/test/java/ru/job4j/xmlxslt/CONVERTED.xml");
        assertThat(res, is(5050L));
    }

    @Test
    public void whenOverMillionValuesAddedThanProgrammRunsLessThan5Minutes() {
        long start = System.currentTimeMillis();
        this.controller.insertValuesToDb(1500000);
        this.controller.createXMLFile("../SQLJDBC/src/test/java/ru/job4j/xmlxslt/MILLION.xml", this.controller.loadFromDb());
        File fileMill = new File("../SQLJDBC/src/test/java/ru/job4j/xmlxslt/MILLION.xml");

        this.controller.validateAndConvertXML("../SQLJDBC/src/test/java/ru/job4j/xmlxslt/MILLION.xml",
                "../SQLJDBC/src/test/java/ru/job4j/xmlxslt/entries.xsd",
                "../SQLJDBC/src/test/java/ru/job4j/xmlxslt/convertion.xsl",
                "../SQLJDBC/src/test/java/ru/job4j/xmlxslt/CONVMill.xml");
        File fileMillConv = new File("../SQLJDBC/src/test/java/ru/job4j/xmlxslt/CONVMill.xml");

        long res = this.controller.parseResult("../SQLJDBC/src/test/java/ru/job4j/xmlxslt/CONVMill.xml");
        assertThat(res, is(1125000750000L));
        long finish = System.currentTimeMillis();
        long timeResult = finish -start;
        assertThat((timeResult < 300_000L), is(true));

        fileMill.delete();
        fileMillConv.delete();
    }

}