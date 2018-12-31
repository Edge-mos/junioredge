package ru.job4j.siteparser.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.siteparser.config.Config;
import ru.job4j.siteparser.connection.Access;
import ru.job4j.siteparser.connection.DbConnectionImpl;
import ru.job4j.siteparser.controller.ParserController;
import ru.job4j.siteparser.models.Vacancy;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$. Имплементация dao DB.
 * @since 02.12.2018.
 */
public class StorageImpl implements Storage{
    private static final Logger LOGGER = LoggerFactory.getLogger(StorageImpl.class);
    private final Access connection;
    private final Config config;

    public StorageImpl(Config config) {
        this.config = config;
        this.connection = new DbConnectionImpl(this.config);
    }

    /**
     * Инициализация таблицы jobs, если не существует.
     */
    @Override
    public void init() {
        try (Statement st = this.connection.getConnection().createStatement()) {
            st.execute(this.config.getProperty(Config.INITIAL));
        } catch (SQLException e) {
            LOGGER.error("Init method failed!", e);
        }
    }

    /**
     * Удаление таблицы jobs, если существует.
     */
    @Override
    public void dropDb() {
        try (Statement st = this.connection.getConnection().createStatement()) {
            st.execute(this.config.getProperty(Config.DROP));
        } catch (SQLException e) {
            LOGGER.error("Drop Db error!", e);
        }
    }

    /**
     * Добавление списка уникальных вакансий в БД 1 транзакцией.
     * @param vacancies Список уникальных вакансий.
     */
    @Override
    public void insertVacancy(Set<Vacancy> vacancies) {
        try (Connection conn = this.connection.getConnection();
             PreparedStatement ps = conn.prepareStatement(this.config.getProperty(Config.INSERT))) {
            conn.setAutoCommit(false);
            try {
                for (Vacancy vacancy : vacancies) {
                    ps.setString(1, vacancy.getVacancyName());
                    ps.setString(2, vacancy.getVacancyText());
                    ps.setString(3, vacancy.getLink());
                    ps.setTimestamp(4, java.sql.Timestamp.valueOf(vacancy.getDate()));
                    ps.execute();
                }
                conn.commit();
            } catch (SQLException e) {
                LOGGER.error("Transaction failed!", e);
                conn.rollback();
            }
        } catch (SQLException e) {
            LOGGER.error("Insert vacancy error!", e);
        }
    }

    /**
     * Поиск последней даты валидной вакансии для парсинга.
     * @return Последнюю дату, до которой надо парсить, null, если БД пуста.
     */
    public String lastSearch() {
        String date = null;
        try (PreparedStatement ps = this.connection.getConnection().prepareStatement(
                this.config.getProperty(Config.LASTSEARCH),
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE)
        ) {
            ResultSet rs = ps.executeQuery();
            if (rs.first()) {
                date = rs.getString(1);
            }
        } catch (SQLException e) {
            LOGGER.error("Searching date error!", e);
        }
        return date;
    }

    /**
     *
     * @return Возвращает список всех вакансий из БД.
     */
    @Override
    public List<Vacancy> loadAllVacancy() {
        List<Vacancy> result = new LinkedList<>();
        final DateTimeFormatter dtfEn = DateTimeFormatter.ofPattern("yyyy-MM-d HH:mm");
        try (ResultSet rs = this.connection.getConnection().createStatement().executeQuery(this.config.getProperty(Config.GETALL))) {
            while (rs.next()) {
                result.add(new Vacancy(
                        rs.getString("vac_name"),
                        rs.getString("vac_text"),
                        rs.getString("vac_link"),
                        LocalDateTime.parse(rs.getTimestamp("vac_date").toString().replace(":00.0", ""), dtfEn)
                ));
            }
        } catch (SQLException e) {
            LOGGER.error("Load DB data error!", e);
        }
        return result;
    }
}
