package ru.job4j.trackerjdbc.interfaces.implemented;

import ru.job4j.trackerjdbc.model.Item;
import ru.job4j.trackerjdbc.interfaces.Connect;
import ru.job4j.trackerjdbc.interfaces.InitConfig;
import ru.job4j.trackerjdbc.interfaces.Process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$.
 * @since 01.10.2018.
 */
public class Tracker implements Process {
    private final InitConfig config = new Config();
    private final Connect connection = new DbConnection(this.config);

    public Tracker() {
        this.initial();
    }

    private void initial() {
        try(Statement statement = this.connection.getConnection().createStatement()) {
            statement.execute(this.config.getProperty("db.initial"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean add(Item obj) {
        boolean result = false;
        try (PreparedStatement ps = this.connection.getConnection().prepareStatement(this.config.getProperty("db.insert"))) {
            ps.setString(1, obj.getName());
            ps.setString(2, obj.getDescription());
            ps.setDate(3, obj.getCreated());
            ps.setString(4, obj.getComment());
            result = ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void replace(int id, Item obj) {
        try (PreparedStatement ps = this.connection.getConnection().prepareStatement(
                this.config.getProperty("db.findById"),
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE)
        ) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.first();
            rs.updateString("name", obj.getName());
            rs.updateString("description", obj.getDescription());
            rs.updateDate("created", obj.getCreated());
            rs.updateString("comment", obj.getComment());
            rs.updateRow();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Item findById(int id) {
        Item result = null;
        try (PreparedStatement ps = this.connection.getConnection().prepareStatement(
                this.config.getProperty("db.findById"),
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE)
        ) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.first();
            result = this.resSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
       return result;
    }

    @Override
    public boolean delete(int id) {
        boolean result = false;
        try (PreparedStatement ps = this.connection.getConnection().prepareStatement(this.config.getProperty("db.delete"))){
            ps.setInt(1, id);
            result = ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Item> getAll() {
        List<Item> result = new LinkedList<>();
        try (ResultSet rs = this.connection.getConnection().createStatement().executeQuery(this.config.getProperty("db.getAll"))){
            while (rs.next()) {
                result.add(this.resSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Item> findByName(String objName) {
        List<Item> result = new LinkedList<>();
        try (PreparedStatement ps = this.connection.getConnection().prepareStatement(this.config.getProperty("db.findByName"))) {
            ps.setString(1, objName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(this.resSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void dropTable() {
        try (Statement statement = this.connection.getConnection().createStatement()) {
            statement.execute(this.config.getProperty("db.drop"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Item resSet(ResultSet rs) throws SQLException {
       return new Item(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getDate("created"),
                rs.getString("comment")
        );
    }

    @Override
    public void close() throws Exception {
        if (this.connection.getConnection() != null) {
            this.connection.getConnection().close();
        }
    }
}
