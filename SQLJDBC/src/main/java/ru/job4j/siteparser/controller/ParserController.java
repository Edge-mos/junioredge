package ru.job4j.siteparser.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.siteparser.config.Config;
import ru.job4j.siteparser.dao.Storage;
import ru.job4j.siteparser.dao.StorageImpl;
import ru.job4j.siteparser.models.Vacancy;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$. Контроллер программы.
 * @since 02.12.2018.
 */
public class ParserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParserController.class);
    private final Storage storage;
    /**
     * Кэш уникальных вакансий.
     */
    private Set<Vacancy> cache = new LinkedHashSet<>();
    /**
     * URL сайта, находится в properties.
     */
    private final String URL;
    /**
     * Паттерн поиска и отсева заголовков вакансий, находится в properties.
     */
    private final Pattern pattern;
    private final String dateFormat = "d MMM yy";
    private final String timeFormat = ", HH:mm";
    /**
     * Полный форматтер даты с русской локалью для парсинга даты вакансии.
     */
    private final DateTimeFormatter dtfRu = DateTimeFormatter.ofPattern(dateFormat.concat(timeFormat))
            .withLocale(new Locale("ru"));
    /**
     * Форматтер день, месяц, год для исправления значений "вчера", "сегодня", "май"
     */
    private final DateTimeFormatter dfRu = DateTimeFormatter.ofPattern(dateFormat)
            .withLocale(new Locale("ru"));
    /**
     * Полный форматтер даты с анг локалью, для парсинга даты из TimeStamp SQL.
     */
    private final DateTimeFormatter dtfEn = DateTimeFormatter.ofPattern(dateFormat.concat(timeFormat));

    public ParserController(String pathToConfig) {
        Config config = new Config(pathToConfig);
        this.storage = new StorageImpl(config);
        this.storage.init();
        this.URL = config.getProperty(Config.SITE_URL);
        this.pattern = Pattern.compile(config.getProperty(Config.PATTERN));
    }

    List<Vacancy> loadData() {
        return this.storage.loadAllVacancy();
    }

    public void dropDB() {
        this.storage.dropDb();
    }

    /**
     * Добавление вакансий из кэша в БД.
     */
    public void insert() {
        this.storage.insertVacancy(this.cache);
    }

    /**
     * Парсинг вакансий с сайта и добавления их в кэш.
     */
    public void parse() {
        int count = 1;
        LocalDateTime lastParsedDate = this.getLastDateVac();
        final boolean[] proceed = {true};

        while (proceed[0]) {
            try {
                String tmpURL = this.URL.concat(String.valueOf(count++));
                Document doc = Jsoup.connect(tmpURL).get();
                Element table = doc.select("table.forumTable").first();
                Elements rows = table.select("tr");
                rows.forEach(row -> row.select("td")
                        .stream().filter(filtered -> filtered.hasClass("postslisttopic"))
                        .forEach(col -> {
                            String vacName = col.child(0).text();
                            if (this.vacNameCheck(vacName)) {
                                LocalDateTime vacDate = this.dateParse(row.select("td.altCol").get(1).text());
                                if (vacDate.isAfter(lastParsedDate)) {
                                    String vacRef = col.child(0).attr("href");
                                    String vacBio = this.parseVacBio(vacRef);
                                    this.cache.add(new Vacancy(vacName, vacBio, vacRef, vacDate));
                                } else {
                                    proceed[0] = false;
                                }
                            }
                        }));
            } catch (IOException e) {
                LOGGER.error("Parsing problem!", e);
            }
        }
    }

    /**
     * Проверяет текст заголовка вакансии регулярным выражением.
     * @param vacName Заголовок вакансии.
     * @return Если совпала группа, где присутствует script - возвращает false.
     */
    boolean vacNameCheck(String vacName) {
        Matcher m = this.pattern.matcher(vacName);
        if (m.find()) {
            return m.group(1) == null;
        }
        return false;
    }

    /**
     * Получает текст вакансии.
     * @param vacRef Ссылка на вакансию.
     * @return текст вакансии.
     */
    private String parseVacBio(String vacRef) {
        Document vacBio = null;
        try {
            vacBio = Jsoup.connect(vacRef).get();
        } catch (IOException e) {
            LOGGER.error("Parsing Vacancy Bio Error!", e);
        }
        return vacBio != null ? (vacBio.getElementsByAttributeValue("class", "msgBody").get(1).text()) : (null);
    }

    /**
     * Приводит дату публикации вакансии к единому виду.
     * @param dateString строка для исправления, если нужно.
     * @return Объект даты вакансии.
     */
    private LocalDateTime dateParse(String dateString) {
        if (dateString.contains("сегодня")) {
            dateString = dateString.replace("сегодня", LocalDate.now().format(this.dfRu));
        }
        if (dateString.contains("вчера")) {
            dateString = dateString.replace("вчера", LocalDate.now().minusDays(1).format(this.dfRu));
        }
        if (dateString.contains("май")) {
            dateString = dateString.replace("май", "мая");
        }
        return LocalDateTime.parse(dateString, this.dtfRu);
    }

    /**
     * Получает дату последней вакансии, до которой нужно парсить, если БД пуста, то дата берётся как 1-й день текущего года.
     * @return Объект даты.
     */
    LocalDateTime getLastDateVac() {
        String date = this.storage.lastSearch();
        return date != null ? (LocalDateTime.parse(date, this.dtfEn)) :
                (LocalDateTime.of(LocalDateTime.now().getYear(), 1, 1, 0, 0));
    }
}
