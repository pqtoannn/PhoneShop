
package DAL;

import DTO.DTO_Color;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetImpl;
import com.mysql.jdbc.Statement;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class DAL_Color extends DAL_Connection {
    
    public DAL_Color() {
        conn = getConnection();
    }
    
    public ObservableList <DTO_Color> getList() {
        String query = "SELECT `rgb`, `name` FROM `color`";
        ObservableList <DTO_Color> list = FXCollections.observableArrayList();
        
        try {
            Statement stmt = (Statement) conn.createStatement();
            ResultSetImpl rs = (ResultSetImpl) stmt.executeQuery(query);
            while(rs.next()) {
                String[] rgbTemp = rs.getString("rgb").split(";");
                int[] rgb = new int[] {Integer.parseInt(rgbTemp[0]), Integer.parseInt(rgbTemp[1]), Integer.parseInt(rgbTemp[2])};
                DTO_Color color = new DTO_Color(rgb, rs.getString("name"), "", new ArrayList<String>());
                list.add(color);
            }  
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public ObservableList <DTO_Color> getListByProductID(String id) {
        String query = "SELECT `color`, `name`, `images` FROM `color`, `color_product` WHERE `productid` = '" + id + "' AND `rgb` = `color`";
        ObservableList <DTO_Color> list = FXCollections.observableArrayList(); 
        try {
            Statement stmt = (Statement) conn.createStatement();
            ResultSetImpl rs = (ResultSetImpl) stmt.executeQuery(query);
            while(rs.next()) {
                String t = rs.getString("images");                 
                ArrayList<String> hinhanh = new ArrayList<String>();
                if(!t.equals("")){   
                String b[]=t.split("@");                
                for(String i : b){
                        hinhanh.add(i);
                }
                }
                String[] rgbTemp = rs.getString("color").split(";");
                int[] rgb = new int[] {Integer.parseInt(rgbTemp[0]), Integer.parseInt(rgbTemp[1]), Integer.parseInt(rgbTemp[2])};
                DTO_Color color = new DTO_Color(rgb, rs.getString("name"), id, hinhanh);
                list.add(color);
            }  
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public int insert(DTO_Color item) {
        String query = "INSERT INTO `color`(`rgb`, `name`) VALUES ('" + item.rgbToString() + "','" + item.getName() + "')";
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
    
    public int insertColorProduct(DTO_Color item) {
        String query = "INSERT INTO `color_product`(`color`, `productid`, `images`) VALUES ('" + item.rgbToString() + "','" + item.getProductid() + "','" + item.imageToString() + "')";
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
    
    public int updateImage(DTO_Color item){
        String query = "UPDATE `color_product` SET `images` = ? WHERE `color` = '" +item.rgbToString() + "'AND `productid` = '" + item.getProductid() +"'";
        int rs = -1;
        try { 
            
            PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(query);
            stmt.setString(1, item.imageToString());
            rs = stmt.executeUpdate();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return rs;
    }
    
    public int deleteColor(DTO_Color item){
        String query = "DELETE FROM `color` WHERE `rgb` = '" + item.rgbToString() +"'";
        int rs = -1;
        try{
            Statement stmt = (Statement) conn.createStatement();
            rs = stmt.executeUpdate(query);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return rs;
    }
    public int deleteColorProduct(DTO_Color item) {
        
        String query = "DELETE FROM `color_product` WHERE `color` = '" + item.rgbToString() + "' AND `productid` = '" + item.getProductid() + "'";
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
    
    public boolean colorIsExist(String rgb) {
        boolean check = false;
        String query = "SELECT * FROM `color` WHERE `rgb` = '" + rgb + "'";
        try {
            Statement stmt = (Statement) conn.createStatement();
            ResultSetImpl rs = (ResultSetImpl) stmt.executeQuery(query);
            while (rs.next()) {
                check = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }
    
}
