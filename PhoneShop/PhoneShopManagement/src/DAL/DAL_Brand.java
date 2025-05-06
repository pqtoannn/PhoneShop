
package DAL;

import DTO.DTO_Brand;
import com.mysql.jdbc.ResultSetImpl;
import com.mysql.jdbc.Statement;
import java.util.ArrayList;


public class DAL_Brand extends DAL_Connection {
    public DAL_Brand() {
        conn = getConnection();
    }
    
    public ArrayList <DTO_Brand> getList() {
        String query = "SELECT `id`, `name`, `image` FROM `brand`";
        ArrayList <DTO_Brand> list = new ArrayList <DTO_Brand> ();
        
        try {
            Statement stmt = (Statement) conn.createStatement();
            ResultSetImpl rs = (ResultSetImpl) stmt.executeQuery(query);
            while(rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String image = rs.getString("image");
                DTO_Brand brand = new DTO_Brand(id, name, image);
                list.add(brand);
            }  
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
