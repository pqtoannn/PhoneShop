
package DAL;

import BUS.BUS_ImportDetail;
import BUS.BUS_Option;
import DTO.DTO_Date;
import DTO.DTO_DefaultInvoice;
import DTO.DTO_InvoiceDetail;
import DTO.DTO_User;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetImpl;
import com.mysql.jdbc.Statement;
import java.util.ArrayList;


public class DAL_ImportInvoice extends DAL_Connection {
    private BUS_ImportDetail bus = new BUS_ImportDetail();
    private BUS_Option bus_option = new BUS_Option();
    
    public DAL_ImportInvoice() {
        conn = getConnection();
    }
    
    public ArrayList <DTO_DefaultInvoice> getList() {
        String query = "SELECT `importinvoice`.`id`, `userid`, `importinvoice`.`initdate` as invoicedate, `invoiceprice`, "
                + "`username`, `fullname`, `email`, `password`, `user`.`phone`, `user`.`address`, `permission`, `user`.`initdate` as userdate, `status`  FROM `importinvoice`, `user` WHERE `userid` = `user`.`id`";
        ArrayList <DTO_DefaultInvoice> list = new ArrayList <DTO_DefaultInvoice> ();
        
        try {
            Statement stmt = (Statement) conn.createStatement();
            ResultSetImpl rs = (ResultSetImpl) stmt.executeQuery(query);
            while(rs.next()) {
                String id = rs.getString("id");
                String userid = rs.getString("userid");
                String [] initdateString = rs.getString("invoicedate").split(" ")[0].split("-");
                String [] initimeString = rs.getString("invoicedate").split(" ")[1].split(":");
                DTO_Date initdate = new DTO_Date(Integer.parseInt(initdateString[0]), Integer.parseInt(initdateString[1]), Integer.parseInt(initdateString[2]), Integer.parseInt(initimeString[0]), Integer.parseInt(initimeString[1]), (int) Math.round(Double.parseDouble(initimeString[2])));
                double invoiceprice = rs.getDouble("invoiceprice");
                DTO_DefaultInvoice invoice = new DTO_DefaultInvoice(id, userid, initdate, invoiceprice);
                
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

                DTO_User user = new DTO_User(userid, username, fullname, email, password,"", phone, address, permission, status, userDate);
                invoice.setUser(user);
                
                list.add(invoice);
            }  
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public int insert(DTO_DefaultInvoice item) {
        String query = "INSERT INTO `importinvoice`(`id`, `userid`, `initdate`, `invoiceprice`) VALUES (?, ?, ?, ?)";
        int rs = -1;
        
        try {
            PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(query);
            stmt.setString(1, item.getId());
            stmt.setString(2, item.getUserid());
            stmt.setString(3, item.getInitdate().toString());
            stmt.setDouble(4, item.getPrice());
            rs = stmt.executeUpdate();
            
            for (DTO_InvoiceDetail detail : item.getDetails()) {
                bus.insert(detail);
                bus_option.updateRemain(detail.getOption(), detail.getQuantity() * (-1));
            }
        } 
        
        catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
    
    public int getTotal() {
        String query = "SELECT * FROM `importinvoice`";
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
