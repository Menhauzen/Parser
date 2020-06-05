import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbConnection {
    public static Connection createConnection() {
        String dbUrl = "jdbc:mysql://localhost:3306/2gis";
        String login = "root";
        String pass = "12345";
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(dbUrl, login, pass);
            System.out.println("database is connected");
        } catch (SQLException ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }catch (ClassNotFoundException cn){
            System.out.println("Драйвер бд не найден");
        }

        return connection;
    }
}
