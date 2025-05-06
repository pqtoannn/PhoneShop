
package DAL;

import DTO.DTO_Rom;
import com.mysql.jdbc.ResultSetImpl;
import com.mysql.jdbc.Statement;
import java.util.ArrayList;


public class DAL_Rom extends DAL_Connection {
    
    public DAL_Rom() {
        conn = getConnection();
    }
    
    public ArrayList <DTO_Rom> getList(String id) {
        String query = "SELECT `rom`, `productid` FROM `rom_product` WHERE `productid` = '" + id + "'";
        ArrayList <DTO_Rom> list = new ArrayList <DTO_Rom> ();
        
        try {
            Statement stmt = (Statement) conn.createStatement();
            ResultSetImpl rs = (ResultSetImpl) stmt.executeQuery(query);
            while(rs.next()) {
                DTO_Rom rom = new DTO_Rom(rs.getInt("rom"), id);
                list.add(rom);
            }  
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public int insert(DTO_Rom item) {
        String query = "INSERT INTO `rom_product`(`rom`, `productid`) VALUES ('" + item.getRom() + "','" + item.getProductid() + "')";
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
    
    public int delete(DTO_Rom item) {
        String query = "DELETE FROM `rom_product` WHERE `rom` = " + item.getRom() + " AND `productid` = '" + item.getProductid() + "'";
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
