package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }


    public void createUsersTable() {
        final String createUsersTable =
                "CREATE TABLE IF NOT EXISTS users\n" +
                        "(id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,\n" +
                        "name VARCHAR(45) NOT NULL,\n" +
                        "lastName VARCHAR(45) NOT NULL,\n" +
                        "age TINYINT NOT NULL)\n" +
                        "CHARSET = utf8;";
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(createUsersTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        final String dropUsersTable =
                "DROP TABLE IF EXISTS users;";
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(dropUsersTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        final String saveUser =
                "INSERT INTO users(name, lastName, age) " +
                        "VALUES(?, ?, ?);";
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(saveUser)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.execute();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        final String removeUserById =
                "DELETE FROM users " +
                        "WHERE id = ?;";
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(removeUserById)) {
            statement.setLong(1, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        final String getAllUsers = "SELECT * FROM users;";
        List<User> usersList = new ArrayList<>();
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(getAllUsers)) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                usersList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersList;
    }

    public void cleanUsersTable() {
        final String cleanUsersTable = "TRUNCATE TABLE users;";
        try (Connection connection = Util.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(cleanUsersTable);
            statement.execute();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
