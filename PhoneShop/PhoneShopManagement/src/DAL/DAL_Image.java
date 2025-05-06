
package DAL;

import com.mysql.jdbc.ResultSetImpl;
import com.mysql.jdbc.Statement;
import java.util.ArrayList;


public class DAL_Image extends DAL_Connection {
    
    public DAL_Image() {
        conn = getConnection();
    }
    
    public ArrayList <String> getList(String id, int color) {
        String query = "SELECT `image` FROM `image_product_color` WHERE `productid` = '" + id + "' AND `color` = " + color + "";
        ArrayList <String> list = new ArrayList <String> ();
        
        try {
            Statement stmt = (Statement) conn.createStatement();
            ResultSetImpl rs = (ResultSetImpl) stmt.executeQuery(query);
            while(rs.next()) {
                list.add(rs.getString(query));
            }  
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public int insert(String image, String id, int color) {
        String query = "INSERT INTO `image_product_color`(`image`, `productid`, `color`) VALUES ('" + image + "', '" + id + "', '" + color + "')";
        int rs = -1;
        
        try {
            Statement stmt = (Statement) conn.createStatement();
            rs = stmt.executeUpdate(query);
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
}
