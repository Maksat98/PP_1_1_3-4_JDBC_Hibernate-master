package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getUtil().getConnection();

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Statement statement = Util.getUtil().getStatement()) {
            try {

                connection.setAutoCommit(false);
                String TABLE = "CREATE TABLE IF NOT EXISTS users "
                        + "(Id INT AUTO_INCREMENT PRIMARY KEY, "
                        + " name VARCHAR(45) NOT NULL, "
                        + " lastName VARCHAR(45) NOT NULL, "
                        + " age INTEGER NOT NULL)";
                statement.executeUpdate(TABLE);

                connection.commit();
            }catch (SQLException ex)  {
                connection.rollback();
                throw ex;
            } finally {
                connection.setAutoCommit(true);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void dropUsersTable() {
        try (Statement statement = Util.getUtil().getStatement()) {
            try {
                connection.setAutoCommit(false);
                String DROP = "DROP TABLE IF EXISTS users";
                statement.executeUpdate(DROP);

                connection.commit();
            } catch (SQLException ex) {
                connection.rollback();
                throw ex;
            } finally {
                connection.setAutoCommit(true);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String INSERT = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?);";
        try (Statement statement = Util.getUtil().getStatement()) {
            connection.setAutoCommit(false);
            try {
                PreparedStatement preparedStatement = statement.
                        getConnection().
                        prepareStatement(INSERT);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, lastName);
                preparedStatement.setByte(3, age);
                preparedStatement.executeUpdate();

                connection.commit();
            } catch (SQLException ex) {
                connection.rollback();
                throw ex;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void removeUserById(long id) {
        String REMOVE = "DELETE FROM users WHERE id =" + id;

        try (Statement statement = Util.getStatement()) {
            try {
                connection.setAutoCommit(false);
                statement.executeUpdate(REMOVE);

                connection.commit();
            } catch (SQLException ex) {
                connection.rollback();
                throw ex;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        String ALL = "SELECT * FROM users";
        List<User> users = new ArrayList<>();

        try (Statement statement = Util.getUtil().getStatement()) {
            ResultSet resultSet = statement.executeQuery(ALL);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("Id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        String CLEAN = "TRUNCATE TABLE users";
        try (Statement statement = Util.getUtil().getStatement()) {
            try {
                connection.setAutoCommit(false);
                statement.executeUpdate(CLEAN);

                connection.commit();
            } catch (SQLException ex) {
                connection.rollback();
                throw ex;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
