
package DAL;

import DTO.DTO_Color;
import DTO.DTO_Date;
import DTO.DTO_Option;
import DTO.DTO_Product;
import DTO.DTO_User;
import DTO.DTO_Warranty;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetImpl;
import com.mysql.jdbc.Statement;
import java.util.ArrayList;


public class DAL_Warranty extends DAL_Connection {
    
    public DAL_Warranty() {
        conn = getConnection();
    }
    
    public ArrayList <DTO_Warranty> getList() {
        String query = "SELECT `warranty`.`id`, `userid`, `optionid`, `warranty`.`initdate` as warrantydate, `productid`, `product`.`name` AS productname, `product`.`warranty` AS remaintime, `option`.`color`, `color`.`name` AS colorname, `rom`, `ram`, `importprice`, `saleprice`, `remain`, `sold`, "
                + "`username`, `fullname`, `email`, `password`, `phone`, `address`, `permission`, `user`.`initdate` as userdate, `user`.`status`  FROM `warranty`, `option`, `user`, `product`, `color` "
                + "WHERE `userid` = `user`.`id` AND `optionid` = `option`.`id` AND `option`.`color` = `color`.`rgb` AND `productid` = `product`.`id` AND (DATEDIFF(CURDATE(),`warranty`.`initdate`) <= (`product`.`warranty` * 30))";
        ArrayList <DTO_Warranty> list = new ArrayList <DTO_Warranty> ();
        
        try {
            Statement stmt = (Statement) conn.createStatement();
            ResultSetImpl rs = (ResultSetImpl) stmt.executeQuery(query);
            while(rs.next()) {
                String id = rs.getString("id");
                String userid = rs.getString("userid");
                String optionid = rs.getString("optionid");
                String [] initdateString = rs.getString("warrantydate").split(" ")[0].split("-");
                String [] initimeString = rs.getString("warrantydate").split(" ")[1].split(":");
                DTO_Date innidate = new DTO_Date(Integer.parseInt(initdateString[0]), Integer.parseInt(initdateString[1]), Integer.parseInt(initdateString[2]), Integer.parseInt(initimeString[0]), Integer.parseInt(initimeString[1]), (int) Math.round(Double.parseDouble(initimeString[2])));

                DTO_Warranty warranty = new DTO_Warranty(id, userid, optionid, innidate);
                
                String productid = rs.getString("productid");
                String productname = rs.getString("productname");
                double remaintime = rs.getDouble("remaintime");
                String[] color = rs.getString("color").split(";");
                String colorName = rs.getString("colorname");
                int rom = rs.getInt("rom");
                int ram = rs.getInt("ram");
                double importprice = rs.getDouble("importprice");
                double saleprice = rs.getDouble("saleprice");
                int remain = rs.getInt("remain");
                int sold = rs.getInt("sold");
                DTO_Option option = new DTO_Option(optionid, productid, productname, new DTO_Color(new int[] {Integer.parseInt(color[0]), Integer.parseInt(color[1]), Integer.parseInt(color[2])}, 
                        colorName, productid, null), rom, ram, importprice, saleprice, remain, sold, true);
                warranty.setOption(option);
                DTO_Product product = new DTO_Product(productid, "", "", productname, 0, 0, "", remaintime, 1, 0, 0);
                warranty.setProduct(product);
                
                String username = rs.getString("username");
                String fullname = rs.getString("fullname");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                int permission = rs.getInt("permission");
                int status = rs.getInt("status");
                
                String[] userdateString = rs.getString("userdate").split(" ")[0].split("-");
                DTO_Date userDate = new DTO_Date(Integer.parseInt(userdateString[0]), Integer.parseInt(userdateString[1]), Integer.parseInt(userdateString[2]), 0, 0, 0);

                DTO_User user = new DTO_User(userid, username, fullname, email, password, "", phone, address, permission, status, userDate);
                warranty.setUser(user);
                
                list.add(warranty);
            }  
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public ArrayList<DTO_Warranty> getListOutOfDate() {
        String query = "SELECT `warranty`.`id`, `userid`, `optionid`, `warranty`.`initdate` as warrantydate, `productid`, `product`.`name` AS productname, `option`.`color`, `color`.`name` AS colorname, `rom`, `ram`, `importprice`, `saleprice`, `remain`, `sold`, "
                + "`username`, `fullname`, `email`, `password`, `phone`, `address`, `permission`, `user`.`initdate` as userdate, `user`.`status`  FROM `warranty`, `option`, `user`, `product`, `color` "
                + "WHERE `userid` = `user`.`id` AND `optionid` = `option`.`id` AND `option`.`color` = `color`.`rgb` AND `productid` = `product`.`id` AND (DATEDIFF(CURDATE(),`warranty`.`initdate`) > (`product`.`warranty` * 30))";
        ArrayList <DTO_Warranty> list = new ArrayList <DTO_Warranty> ();
        
        try {
            Statement stmt = (Statement) conn.createStatement();
            ResultSetImpl rs = (ResultSetImpl) stmt.executeQuery(query);
            while(rs.next()) {
                String id = rs.getString("id");
                String userid = rs.getString("userid");
                String optionid = rs.getString("optionid");
                String [] initdateString = rs.getString("warrantydate").split(" ")[0].split("-");
                String [] initimeString = rs.getString("warrantydate").split(" ")[1].split(":");
                DTO_Date innidate = new DTO_Date(Integer.parseInt(initdateString[0]), Integer.parseInt(initdateString[1]), Integer.parseInt(initdateString[2]), Integer.parseInt(initimeString[0]), Integer.parseInt(initimeString[1]), (int) Math.round(Double.parseDouble(initimeString[2])));

                DTO_Warranty warranty = new DTO_Warranty(id, userid, optionid, innidate);
                
                String productid = rs.getString("productid");
                String productname = rs.getString("productname");
                String[] color = rs.getString("color").split(";");
                String colorName = rs.getString("colorname");
                int rom = rs.getInt("rom");
                int ram = rs.getInt("ram");
                double importprice = rs.getDouble("importprice");
                double saleprice = rs.getDouble("saleprice");
                int remain = rs.getInt("remain");
                int sold = rs.getInt("sold");
                DTO_Option option = new DTO_Option(optionid, productid, productname, new DTO_Color(new int[] {Integer.parseInt(color[0]), Integer.parseInt(color[1]), Integer.parseInt(color[2])}, 
                        colorName, productid, null), rom, ram, importprice, saleprice, remain, sold, true);
                warranty.setOption(option);
                DTO_Product product = new DTO_Product(productid, "", "", productname, 0, 0, "", 0, 1, 0, 0);
                warranty.setProduct(product);
                
                String username = rs.getString("username");
                String fullname = rs.getString("fullname");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                int permission = rs.getInt("permission");
                int status = rs.getInt("status");
                
                String[] userdateString = rs.getString("userdate").split(" ")[0].split("-");
                DTO_Date userDate = new DTO_Date(Integer.parseInt(userdateString[0]), Integer.parseInt(userdateString[1]), Integer.parseInt(userdateString[2]), 0, 0, 0);

                DTO_User user = new DTO_User(userid, username, fullname, email, password, "", phone, address, permission, status, userDate);
                warranty.setUser(user);
                
                list.add(warranty);
            }  
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public int insert(DTO_Warranty item) {
        String query = "INSERT INTO `warranty`(`id`, `userid`, `optionid`, `initdate`) VALUES (?, ?, ?, ?)";
        int rs = -1;
        
        try {
            PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(query);
            stmt.setString(1, item.getId());
            stmt.setString(2, item.getUserid());
            stmt.setString(3, item.getOptionid());
            stmt.setString(4, item.getInitdate().toString());
            rs = stmt.executeUpdate();
        } 
        
        catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

}
