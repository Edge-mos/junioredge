package ru.job4j.siteparser.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.siteparser.models.Vacancy;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ParserControllerTest {
    private ParserController controller;

    @Before
    public void setUp() {
        this.controller = new ParserController("siteparcer.properties");
    }

    @After
    public void dropDb() {
        this.controller.dropDB();
    }

    @Test
    public void whenVacNameIsValidThanTrue() {
        List<String> vacsName = new LinkedList<>(Arrays.asList(
                "Вакансия: Ведущий Java-разработчик",
                "Программист-разработчик java (г. Зеленоград, удаленная работа/частичная занятость)",
                "Senior Java Developer, С-Пб",
                "Full Stack Java Developer ( Relocation to Poland ) от 2 500 до 3 500 USD на руки"));
        vacsName.forEach(s -> {
            assertThat(this.controller.vacNameCheck(s), is(true));
        });
    }

    @Test
    public void whenVacNameIsValidThanFalse() {
        List<String> vacsName = new LinkedList<>(Arrays.asList(
                "Javascript-разработчик (ReactJs), Москва до 200 000 net",
                "Java script #офис#зп до 4500$#Минск (помощь в релокации)",
                "Javascript developer, в г.Томск",
                "Нужен Разработчик Java Script, Москва, 180К (можно удалённо)"));
        vacsName.forEach(s -> {
            assertThat(this.controller.vacNameCheck(s), is(false));
        });
    }

    @Test
    public void whenDbIsEmptyThanLastDateIsFirstDayOfCurrentYear() {
        LocalDateTime currentYearStart = LocalDateTime.of(LocalDateTime.now().getYear(), 1, 1, 0, 0);
        assertThat(this.controller.getLastDateVac().equals(currentYearStart), is(true));
    }

    @Test
    public void whenParsingSuccessThanDbNotEmpty() {
        this.controller.parse();
        this.controller.insert();
        int dataSize = this.controller.loadData().size();
        assertThat(dataSize > 0, is(true));
    }

}