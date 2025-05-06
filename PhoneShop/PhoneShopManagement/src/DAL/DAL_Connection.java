
package DAL;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;


public class DAL_Connection {
    public Connection conn = null;
    
    public Connection getConnection() {
        try {
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mobileshop", "root", "");
        } 
        
        catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
