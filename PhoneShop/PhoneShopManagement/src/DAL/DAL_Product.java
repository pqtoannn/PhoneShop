
package DAL;

import DTO.DTO_Product;
import DTO.DTO_SalesReport;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetImpl;
import com.mysql.jdbc.Statement;
import java.util.ArrayList;


public class DAL_Product extends DAL_Connection {
    
    public DAL_Product() {
        conn = getConnection();
    }
    
    public ArrayList <DTO_Product> getList() {
        String query = "SELECT `product`.`id`, `product`.`name` AS productname, `brandid`, `brand`.`name` AS brandname, `detail`, `minimportprice`, `minsaleprice`, `warranty`, `status`, remain, sold \n" +
"FROM `product`, `brand`, (SELECT `productid`, SUM(`option`.`remain`) AS remain, SUM(`option`.`sold`) AS sold FROM `option` GROUP BY(`productid`)) AS t1\n" +
"WHERE `brand`.`id` = `product`.`brandid` AND `productid` = `product`.`id`";
        ArrayList <DTO_Product> list = new ArrayList <DTO_Product> ();
        String query1 = "SELECT `product`.`id`, `product`.`name` AS productname, `brandid`, `brand`.`name` AS brandname, `detail`, `minimportprice`, `minsaleprice`, `warranty`, `status` FROM `product`, `brand` "
                + "WHERE `brand`.`id` = `product`.`brandid` AND `minimportprice` = 0";
        
        
        try {
            Statement stmt = (Statement) conn.createStatement();
            ResultSetImpl rs = (ResultSetImpl) stmt.executeQuery(query);
            while(rs.next()) {
                String id = rs.getString("id");
                String brandid = rs.getString("brandid");
                String brandname = rs.getString("brandname");
                String productname = rs.getString("productname");
                String detail = rs.getString("detail");
                double minimportprice = rs.getDouble("minimportprice");
                double minsaleprice = rs.getDouble("minsaleprice");
                double warranty = rs.getDouble("warranty");
                int status = rs.getInt("status");
                int remain = rs.getInt("remain");
                int sold = rs.getInt("sold");
                DTO_Product product = new DTO_Product(id, brandid, brandname, productname, minimportprice, minsaleprice, detail, warranty, status, remain, sold);
                list.add(product);
            }  
            Statement stmt1 = (Statement) conn.createStatement();
            ResultSetImpl rs1 = (ResultSetImpl) stmt1.executeQuery(query1);
            while(rs1.next()) {
                String id = rs1.getString("id");
                String brandid = rs1.getString("brandid");
                String brandname = rs1.getString("brandname");
                String productname = rs1.getString("productname");
                String detail = rs1.getString("detail");
                double minimportprice = rs1.getDouble("minimportprice");
                double minsaleprice = rs1.getDouble("minsaleprice");
                double warranty = rs1.getDouble("warranty");
                int status = rs1.getInt("status");
                int remain = 0;
                int sold = 0;
                DTO_Product product = new DTO_Product(id, brandid, brandname, productname, minimportprice, minsaleprice, detail, warranty, status, remain, sold);
                list.add(product);
            }  
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public ArrayList <DTO_SalesReport> getSalesReport() {
        String query = "SELECT t1.`brandname`, SUM(t2.`sold`) AS sold FROM (SELECT `brand`.`name` AS brandname, `product`.`id` AS productid FROM `brand`, `product` WHERE `brand`.`id` = `brandid`) AS t1 , (SELECT `productid`, SUM(`option`.`sold`) AS sold FROM `option` GROUP BY(`productid`)) AS t2 WHERE t1.`productid` = t2.`productid` GROUP BY t1.`brandname`";
        ArrayList <DTO_SalesReport> list = new ArrayList<>();
        
        try {
            Statement stmt = (Statement) conn.createStatement();
            ResultSetImpl rs = (ResultSetImpl) stmt.executeQuery(query);
            while(rs.next()) {
                String brandname = rs.getString("brandname");
                int sold = rs.getInt("sold");
                DTO_SalesReport report = new DTO_SalesReport(brandname, sold);
                list.add(report);
            }  
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public int insert(DTO_Product item) {
        String query = "INSERT INTO `product`(`id`, `brandid`, `name`, `minimportprice`, `minsaleprice`, `detail`, `warranty`, `status`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        int rs = -1;
        
        try {
            PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(query);
            stmt.setString(1, item.getId());
            stmt.setString(2, item.getBrandid());
            stmt.setString(3, item.getProductname());
            stmt.setDouble(4, item.getMinimportprice());
            stmt.setDouble(5, item.getMinsaleprice());
            stmt.setString(6, item.getDetail());
            stmt.setDouble(7, item.getWarranty());
            stmt.setInt(8, item.getStatus());
            rs = stmt.executeUpdate();
        } 
        
        catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
    
    public int update(DTO_Product item) {
        String query = "UPDATE `product` SET `brandid` = ?, `name` = ?, `minimportprice` = ?, `minsaleprice` = ?, `detail` = ?, `warranty` = ?, `status` = ? WHERE `id` = '"+ item.getId() +"'";
        int rs = -1;
        
        try {
            PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(query);
            stmt.setString(1, item.getBrandid());
            stmt.setString(2, item.getProductname());
            stmt.setDouble(3, item.getMinimportprice());
            stmt.setDouble(4, item.getMinsaleprice());
            stmt.setString(5, item.getDetail());
            stmt.setDouble(6, item.getWarranty());
            stmt.setInt(7, item.getStatus());
            rs = stmt.executeUpdate();
        } 
        
        catch (Exception e) {
            e.printStackTrace();
        } 
        return rs;
    }
    public int deleteProduct(String id){
        String query = "UPDATE `product` SET `status` = ? WHERE `id` = '" + id +"'";
        int rs = -1;
        try{
            PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(query);
            stmt.setInt(1, 0);

            rs = stmt.executeUpdate();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return rs;
    }
    public int getTotal() {
        String query = "SELECT * FROM `product`";
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
    
}
