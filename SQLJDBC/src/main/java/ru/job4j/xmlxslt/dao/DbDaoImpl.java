package ru.job4j.xmlxslt.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.xmlxslt.config.Config;
import ru.job4j.xmlxslt.connection.Access;
import ru.job4j.xmlxslt.connection.DbConnectionImpl;
import ru.job4j.xmlxslt.exeptions.DaoExeption;
import ru.job4j.xmlxslt.models.Entry;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$. Слой dao данных. Connection получаем из статического метода интерфейса Access!!
 * @since 29.11.2018.
 */
public class DbDaoImpl implements StorageDao{
    private static final Logger logger = LoggerFactory.getLogger(DbDaoImpl.class);

    @Override
    public void init() throws DaoExeption {
        try(Statement statement = Access.getConnectionDB().createStatement()) {
            statement.execute(Config.getProperty(Config.DB_INITIAL));
        } catch (SQLException ex) {
            throw new DaoExeption("init-db problem", ex);
        }
    }

    @Override
    public void drop() throws DaoExeption {
        try(Statement statement = Access.getConnectionDB().createStatement()) {
            statement.execute(Config.getProperty(Config.DB_DROP));
        } catch (SQLException ex) {
            throw new DaoExeption("drop-db problem", ex);
        }
    }

    @Override
    public void insertValue(int toValue) throws DaoExeption {
        try(Connection connection = Access.getConnectionDB();
            PreparedStatement ps = connection.prepareStatement(Config.getProperty(Config.DB_INSERT))) {
            connection.setAutoCommit(false);
            try {
                for (int i = 1; i <= toValue; i++) {
                    ps.setInt(1, i);
                    ps.execute();
                }
                connection.commit();
            } catch (SQLException ex) {
                logger.info("transaction failed", ex);
                connection.rollback();
                throw ex;
            }
        } catch (SQLException ex) {
            throw new DaoExeption("insert value-db problem", ex);
        }
    }

    @Override
    public List<Entry> loadValues() throws DaoExeption {
        List<Entry> entries = new LinkedList<>();
        try(ResultSet rs = Access.getConnectionDB().createStatement().executeQuery(Config.getProperty(Config.DB_LOAD_VALS))) {
            while (rs.next()) {
                entries.add(new Entry(rs.getInt("field")) );
            }
        } catch (SQLException ex) {
            throw new DaoExeption("loading-db problem");
        }
        return entries;
    }
}
