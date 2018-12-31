package ru.job4j.siteparser.dao;

import ru.job4j.siteparser.models.Vacancy;

import java.util.List;
import java.util.Set;

/**
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$. Интерфейс dao слоя.
 * @since 02.12.2018.
 */
public interface Storage {
    void init();
    void dropDb();
    void insertVacancy(Set<Vacancy> vacancies);
    String lastSearch();
    List<Vacancy> loadAllVacancy();
}
