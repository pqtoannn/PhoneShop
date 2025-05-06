
package DAL;

import DTO.DTO_Ram;
import com.mysql.jdbc.ResultSetImpl;
import com.mysql.jdbc.Statement;
import java.util.ArrayList;


public class DAL_Ram extends DAL_Connection {
    
    public DAL_Ram() {
        conn = getConnection();
    }
    
    public ArrayList <DTO_Ram> getList(String id) {
        String query = "SELECT `ram`, `productid` FROM `ram_product` WHERE `productid` = '" + id + "'";
        ArrayList <DTO_Ram> list = new ArrayList <DTO_Ram> ();
        
        try {
            Statement stmt = (Statement) conn.createStatement();
            ResultSetImpl rs = (ResultSetImpl) stmt.executeQuery(query);
            while(rs.next()) {
                DTO_Ram ram = new DTO_Ram(rs.getInt("ram"), id);
                list.add(ram);
            }  
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public int insert(DTO_Ram item) {
        String query = "INSERT INTO `ram_product`(`ram`, `productid`) VALUES ('" + item.getRam() + "','" + item.getProductid() + "')";
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
    
    public int delete(DTO_Ram item) {
        String query = "DELETE FROM `ram_product` WHERE `ram` = " + item.getRam() + " AND `productid` = '" + item.getProductid() + "'";
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
