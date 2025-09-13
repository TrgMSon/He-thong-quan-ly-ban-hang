import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class DataConnection {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/?user=root";

    public static Connection setConnect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, "root", "020677MSon@");
            System.out.println("connected");
        } catch(SQLException e) {
            System.out.println("failed");
        }
        return conn;
    }
}
