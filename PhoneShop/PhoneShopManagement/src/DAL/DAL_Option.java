
package DAL;

import DTO.DTO_Color;
import DTO.DTO_Option;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetImpl;
import com.mysql.jdbc.Statement;
import java.util.ArrayList;


public class DAL_Option extends DAL_Connection {
    
    public DAL_Option() {
        conn = getConnection();
    }
    
    public ArrayList <DTO_Option> getList(String id) {
        String query = "SELECT `option`.`id`, `productid`, `option`.`color`, `color`.`name`, `product`.`name` as `prname`, `rom`, `ram`, `importprice`, `saleprice`, `remain`, `sold` "
                + "FROM `option`, `color`, `product` WHERE `productid` = '" + id + "' AND `option`.`productid` = `product`.`id` AND `option`.`color` = `color`.`rgb`";
        ArrayList <DTO_Option> list = new ArrayList <DTO_Option> ();
        
        try {
            Statement stmt = (Statement) conn.createStatement();
            ResultSetImpl rs = (ResultSetImpl) stmt.executeQuery(query);
            while(rs.next()) {
                String optionid = rs.getString("id");
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
                list.add(option);
            }  
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public int getTotalSold(String id) {
        String query = "SELECT SUM(`sold`) AS totalsold FROM `option` WHERE `productid` = '" + id + "'";
        int sold = 0;
                
        try {
            Statement stmt = (Statement) conn.createStatement();
            ResultSetImpl rs = (ResultSetImpl) stmt.executeQuery(query);
            while(rs.next()) {
                sold = rs.getInt("totalsold");
            }  
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return sold;
    }
    
    public double getMinImportPrice(String id) {
        String query = "SELECT MIN(`importprice`) AS minimportprice FROM `option` WHERE `productid` = '" + id + "'";
        double minimportprice = 0;
                
        try {
            Statement stmt = (Statement) conn.createStatement();
            ResultSetImpl rs = (ResultSetImpl) stmt.executeQuery(query);
            while(rs.next()) {
                minimportprice = rs.getDouble("minimportprice");
            }  
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return minimportprice;
    }
    
    public double getMinSalePrice(String id) {
        String query = "SELECT MIN(`saleprice`) AS minsaleprice FROM `option` WHERE `productid` = '" + id + "'";
        double minsaleprice = 0;
                
        try {
            Statement stmt = (Statement) conn.createStatement();
            ResultSetImpl rs = (ResultSetImpl) stmt.executeQuery(query);
            while(rs.next()) {
                minsaleprice = rs.getDouble("minsaleprice");
            }  
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return minsaleprice;
    }
    
    public int getTotal() {
        String query = "SELECT * FROM `option`";
        int total = 0;
        
        try {
            Statement stmt = (Statement) conn.createStatement();
            ResultSetImpl rs = (ResultSetImpl) stmt.executeQuery(query);
            while(rs.next()) {
                total++;
            }  
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }
    
    public ArrayList <DTO_Option> getDistinctOption(String id) {
        String query = "SELECT DISTINCT `rom`, `ram`, `importprice`, `saleprice` FROM `option` WHERE `productid` = '" + id + "'";
        ArrayList <DTO_Option> list = new ArrayList <DTO_Option> ();
        
        try {
            Statement stmt = (Statement) conn.createStatement();
            ResultSetImpl rs = (ResultSetImpl) stmt.executeQuery(query);
            while(rs.next()) {
                int rom = rs.getInt("rom");
                int ram = rs.getInt("ram");
                double importprice = rs.getDouble("importprice");
                double saleprice = rs.getDouble("saleprice");
                DTO_Option option = new DTO_Option("OPTION", "PRODUCT", "productName", null, rom, ram, importprice, saleprice, 0, 0, true);
                list.add(option);
            }  
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public int insert(DTO_Option item) {
        String query = "INSERT INTO `option`(`id`, `productid`, `color`, `rom`, `ram`, `importprice`, `saleprice`, `remain`, `sold`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int rs = -1;
        
        try {
            PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(query);
            stmt.setString(1, item.getId());
            stmt.setString(2, item.getProductid());
            stmt.setString(3, item.getColor().rgbToString());
            stmt.setInt(4, item.getRom());
            stmt.setInt(5, item.getRam());
            stmt.setDouble(6, item.getImportprice());
            stmt.setDouble(7, item.getSaleprice());
            stmt.setInt(8, item.getRemain());
            stmt.setInt(9, item.getSold());
            rs = stmt.executeUpdate();
        } 
        
        catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
    
    public int update(DTO_Option item) {
        String query = "UPDATE `option` SET `importprice`= ?, `saleprice`= ? WHERE id = '"+ item.getId() +"'";
        int rs = -1;
        
        try {
            PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(query);
            stmt.setDouble(1, item.getImportprice());
            stmt.setDouble(2, item.getSaleprice());
            rs = stmt.executeUpdate();
        } 
        
        catch (Exception e) {
            e.printStackTrace();
        } 
        return rs;
    }
    
    public int updateRemain(DTO_Option item, int quantity) {
        String query = "UPDATE `option` SET `remain`= `remain` - ? WHERE id = '" + item.getId() + "'";
        int rs = -1;

        try {
            PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(query);
            stmt.setDouble(1, quantity);
            rs = stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
    
    public int updateSold(DTO_Option item, int quantity) {
        String query = "UPDATE `option` SET `sold`= `sold` + ? WHERE id = '" + item.getId() + "'";
        int rs = -1;

        try {
            PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(query);
            stmt.setDouble(1, quantity);
            rs = stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

}
