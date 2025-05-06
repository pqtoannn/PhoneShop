
package DAL;

import DTO.DTO_Date;
import DTO.DTO_User;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetImpl;
import com.mysql.jdbc.Statement;
import java.util.ArrayList;


public class DAL_User extends DAL_Connection {
    
    public DAL_User() {
        conn = getConnection();
    }
    
    public ArrayList <DTO_User> getList(int permision) {
        String query = "SELECT `id`, `username`, `fullname`, `email`, `password`, `avatar`, `phone`, `address`, `permission`, `status`, `initdate`  FROM `user` where `permission` = " + permision ;
        ArrayList <DTO_User> list = new ArrayList <DTO_User> ();
        
        try {
            Statement stmt = (Statement) conn.createStatement();
            ResultSetImpl rs = (ResultSetImpl) stmt.executeQuery(query);
            while(rs.next()) {
                String id = rs.getString("id");
                String username = rs.getString("username");
                String fullname = rs.getString("fullname");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String avatar = rs.getString("avatar");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                int permission = rs.getInt("permission");
                int status = rs.getInt("status");
                String [] initdateString = rs.getString("initdate").split(" ")[0].split("-");
                DTO_Date initdate = new DTO_Date(Integer.parseInt(initdateString[0]), Integer.parseInt(initdateString[1]), Integer.parseInt(initdateString[2]), 0, 0, 0);

                DTO_User user = new DTO_User(id, username, fullname, email, password, avatar, phone, address, permission, status, initdate);
                
                list.add(user);
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public DTO_User getUser(String inemail, String inpassword){
        String query = "SELECT * FROM `user` WHERE `email` = '" + inemail + "'";
        DTO_User newUser = new DTO_User();
        try {
            Statement stmt = (Statement) conn.createStatement();
            ResultSetImpl rs = (ResultSetImpl) stmt.executeQuery(query);
            while(rs.next()) {
                String id = rs.getString("id");
                String username = rs.getString("username");
                String fullname = rs.getString("fullname");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String avatar = rs.getString("avatar");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                int permission = rs.getInt("permission");
                int status = rs.getInt("status");
                
                String [] innidateString = rs.getString("initdate").split("-");
                DTO_Date innidate = new DTO_Date(Integer.parseInt(innidateString[2]), Integer.parseInt(innidateString[1]), Integer.parseInt(innidateString[0]), 0, 0, 0);

                newUser = new DTO_User(id, username, fullname, email, password, avatar, phone, address, permission, status, innidate);
                if (!inpassword.equals(password)) {
                    newUser.setPassword("");
                }
            } 
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return newUser;
    }
    
    public DTO_User getUserByID(String userid) {
        String query = "SELECT * FROM `user` WHERE `id` = '" + userid + "'";
        DTO_User newUser = new DTO_User();
        try {
            Statement stmt = (Statement) conn.createStatement();
            ResultSetImpl rs = (ResultSetImpl) stmt.executeQuery(query);
            while (rs.next()) {
                String id = rs.getString("id");
                String username = rs.getString("username");
                String fullname = rs.getString("fullname");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String avatar = rs.getString("avatar");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                int permission = rs.getInt("permission");
                int status = rs.getInt("status");

                String[] initdateString = rs.getString("initdate").split("-");
                DTO_Date initdate = new DTO_Date(Integer.parseInt(initdateString[2]), Integer.parseInt(initdateString[1]), Integer.parseInt(initdateString[0]), 0, 0, 0);

                newUser = new DTO_User(id, username, fullname, email, password, avatar, phone, address, permission, status, initdate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newUser;
    }
    
    public boolean phoneIsExist(String userid, String phone) {
        boolean check = false;
        String query = "";
        int option = 0;
        int cnt = 0;
        if (userid.equals("")) {
            query = "SELECT * FROM `user` WHERE `phone` = '" + phone + "'";
        }
        else {
            option = 1;
            query = "SELECT * FROM `user` WHERE `id` != '" + userid + "' AND `phone` = '" + phone + "'";
        }
        try {
            Statement stmt = (Statement) conn.createStatement();
            ResultSetImpl rs = (ResultSetImpl) stmt.executeQuery(query);
            while (rs.next()) {
                cnt++;
            }
            if (option == 0 && cnt > 0) {
                check = true;
            }
            else if (option == 1 && cnt > 1) {
                check = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }
    
    public boolean userIsExist(String email) {
        boolean check = false;
        String query = "";
        query = "SELECT * FROM `user` WHERE `email` = '" + email + "'";
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
        
    public int insert(DTO_User item) {
        String query = "INSERT INTO `user`(`id`, `username`, `fullname`, `email`, `password`, `avatar`, `phone`, `address`, `permission`, `status`, `initdate`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int rs = -1;
        
        try {
            PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(query);
            stmt.setString(1, item.getId());
            stmt.setString(2, item.getUsername());
            stmt.setString(3, item.getFullname());
            stmt.setString(4, item.getEmail());
            stmt.setString(5, item.getPassword());
            stmt.setString(6, item.getAvatar());
            stmt.setString(7, item.getPhone());
            stmt.setString(8, item.getAddress());
            stmt.setInt(9, item.getPermission());
            stmt.setInt(10, item.getStatus());
            stmt.setString(11, item.getInitdate().toString().split(" ")[0]);
            rs = stmt.executeUpdate();
        } 
        
        catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
    
    public int update(DTO_User item) {
        String query = "UPDATE `user` SET `username` = ?, `fullname` = ?, `email` = ?, `password` = ?, `avatar` = ?, `phone` = ?, `address` = ?, `permission` = ?, `status` = ? WHERE `id` = '" + item.getId() + "'";
        int rs = -1;
        
        try {
            PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(query);
            stmt.setString(1, item.getUsername());
            stmt.setString(2, item.getFullname());
            stmt.setString(3, item.getEmail());
            stmt.setString(4, item.getPassword());
            stmt.setString(5, item.getAvatar());
            stmt.setString(6, item.getPhone());
            stmt.setString(7, item.getAddress());
            stmt.setInt(8, item.getPermission());
            stmt.setInt(9, item.getStatus());
            rs = stmt.executeUpdate();
        } 
        
        catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
    
    public int changeStatus(String id, int status) {
        String query = "UPDATE `user` SET `status` = ? WHERE `id` = '" + id + "'";
        int rs = -1;
        
        try {
            PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(query);
            stmt.setInt(1, status);
            rs = stmt.executeUpdate();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
    
    public int getTotal() {
        String query = "SELECT * FROM `user`";
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
