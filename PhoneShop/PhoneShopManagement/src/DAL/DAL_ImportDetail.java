
package DAL;

import DTO.DTO_Color;
import DTO.DTO_InvoiceDetail;
import DTO.DTO_Option;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetImpl;
import com.mysql.jdbc.Statement;
import java.util.ArrayList;


public class DAL_ImportDetail extends DAL_Connection {
    
    public DAL_ImportDetail() {
        conn = getConnection();
    }
    
    public ArrayList <DTO_InvoiceDetail> getList(String id) {
        String query = "SELECT `imid`, `optionid`, `quantity`, `totalprice`, `productid`, `option`.`color`, `color`.`name`, `product`.`name` as `prname`, `rom`, `ram`, `importprice`, `saleprice`, `remain`, `sold` FROM `importdetail`, `option`, `color`, `product` WHERE `imid` = '" + id + "' AND `optionid` = `option`.`id` AND `option`.`color` = `color`.`rgb` AND `option`.`productid` = `product`.`id`";
        ArrayList <DTO_InvoiceDetail> list = new ArrayList <DTO_InvoiceDetail> ();
        
        try {
            Statement stmt = (Statement) conn.createStatement();
            ResultSetImpl rs = (ResultSetImpl) stmt.executeQuery(query);
            while(rs.next()) {
                String imid = rs.getString("imid");
                String optionid = rs.getString("optionid");
                int quantity = rs.getInt("quantity");
                double totalprice = rs.getDouble("totalprice");
                DTO_InvoiceDetail detail = new DTO_InvoiceDetail(imid, optionid, quantity, totalprice);
                
                String productid = rs.getString("productid");
                String[] color = rs.getString("color").split(";");
                String colorName = rs.getString("name");
                String prName = rs.getString("prname");
                int rom = rs.getInt("rom");
                int ram = rs.getInt("ram");
                double importprice = rs.getDouble("importprice");
                double saleprice = rs.getDouble("saleprice");
                int remain = rs.getInt("remain");
                int sold = rs.getInt("sold");
                DTO_Option option = new DTO_Option(optionid, productid, prName, new DTO_Color(new int[] {Integer.parseInt(color[0]), Integer.parseInt(color[1]), Integer.parseInt(color[2])}, 
                        colorName, productid, null), rom, ram, importprice, saleprice, remain, sold, true);
                detail.setOption(option);
                
                list.add(detail);
            }  
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public int insert(DTO_InvoiceDetail item) {
        String query = "INSERT INTO `importdetail`(`imid`, `optionid`, `quantity`, `totalprice`) VALUES (?, ?, ?, ?)";
        int rs = -1;
        
        try {
            PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(query);
            stmt.setString(1, item.getInvoiceid());
            stmt.setString(2, item.getOptionid());
            stmt.setInt(3, item.getQuantity());
            stmt.setDouble(4, item.getTotalprice());
            rs = stmt.executeUpdate();
        } 
        
        catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
    
    
    
}
