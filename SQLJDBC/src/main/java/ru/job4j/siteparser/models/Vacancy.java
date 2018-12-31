package ru.job4j.siteparser.models;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$. Сущность вакансии.
 * @since 02.12.2018.
 */
public class Vacancy {
    private String vacancyName;
    private String vacancyText;
    private String link;
    private LocalDateTime date;

    public Vacancy() {
    }

    public Vacancy(String vacancyName, String vacancyText, String link, LocalDateTime date) {
        this.vacancyName = vacancyName;
        this.vacancyText = vacancyText;
        this.link = link;
        this.date = date;
    }

    public String getVacancyName() {
        return vacancyName;
    }

    public String getVacancyText() {
        return vacancyText;
    }

    public String getLink() {
        return link;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setVacancyName(String vacancyName) {
        this.vacancyName = vacancyName;
    }

    public void setVacancyText(String vacancyText) {
        this.vacancyText = vacancyText;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vacancy vacancy = (Vacancy) o;
        return Objects.equals(vacancyText, vacancy.vacancyText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vacancyText);
    }

    @Override
    public String toString() {
        return "Vacancy_name: "
                .concat(this.vacancyName.concat("\n"))
                .concat("Vacancy_text: ")
                .concat(this.vacancyText.concat("\n"))
                .concat("Link: ")
                .concat(this.link.concat("\n"))
                .concat("Date: ")
                .concat(this.date.toString()).concat("\n");
    }
}
