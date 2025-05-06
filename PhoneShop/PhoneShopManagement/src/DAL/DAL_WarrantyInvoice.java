
package DAL;

import DTO.DTO_Date;
import DTO.DTO_WarrantyInvoice;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetImpl;
import com.mysql.jdbc.Statement;
import java.util.ArrayList;


public class DAL_WarrantyInvoice extends DAL_Connection {
    
    public DAL_WarrantyInvoice() {
        conn = getConnection();
    }
    
    public ArrayList <DTO_WarrantyInvoice> getList(String id) {
        String query = "SELECT `id`, `warrantyid`, `describe`, `initdate`, `expense` FROM `warrantyinvoice` WHERE `warrantyid` = '" + id + "'";
        ArrayList <DTO_WarrantyInvoice> list = new ArrayList <DTO_WarrantyInvoice> ();
        
        try {
            Statement stmt = (Statement) conn.createStatement();
            ResultSetImpl rs = (ResultSetImpl) stmt.executeQuery(query);
            while(rs.next()) {
                String invoiceid = rs.getString("id");
                String warrantyid = rs.getString("warrantyid");
                String describe = rs.getString("describe");
                String [] initdateString = rs.getString("initdate").split(" ")[0].split("-");
                String [] initimeString = rs.getString("initdate").split(" ")[1].split(":");
                DTO_Date initdate = new DTO_Date(Integer.parseInt(initdateString[0]), Integer.parseInt(initdateString[1]), Integer.parseInt(initdateString[2]), Integer.parseInt(initimeString[0]), Integer.parseInt(initimeString[1]), (int) Math.round(Double.parseDouble(initimeString[2])));

                double expense = rs.getDouble("expense");
                DTO_WarrantyInvoice invoice = new DTO_WarrantyInvoice(invoiceid, warrantyid, describe, initdate, expense);
                
                list.add(invoice);
            }  
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public int insert(DTO_WarrantyInvoice item) {
        String query = "INSERT INTO `warrantyinvoice`(`id`, `warrantyid`, `describe`, `initdate`, `expense`) VALUES (?, ?, ?, ?, ?)";
        int rs = -1;
        
        try {
            PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(query);
            stmt.setString(1, item.getId());
            stmt.setString(2, item.getWarrantyid());
            stmt.setString(3, item.getDescribe());
            stmt.setString(4, item.getInitdate().toString());
            stmt.setDouble(5, item.getExpense());
            rs = stmt.executeUpdate();
        } 
        
        catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
    
    public int getTotal() {
        String query = "SELECT * FROM `warrantyinvoice`";
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
