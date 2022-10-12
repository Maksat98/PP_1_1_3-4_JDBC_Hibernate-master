package jm.task.core.jdbc.util;
import org.hibernate.Session;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Util {
    static private Connection connection;
    static private Session session;
    private static Util util;
    private static final String URL = "jdbc:mysql://localhost:3306/jaja";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "12345678";

        public static Util getUtil() {
            if (util == null) {
                util = new Util();
            }
            return util;
        }
    public Util () {
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return connection;
    }
    public static Statement getStatement() {
        try {
            return getConnection().createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
