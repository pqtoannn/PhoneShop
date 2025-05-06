
package DAL;

import BUS.BUS_ExportInvoice;
import BUS.BUS_Option;
import BUS.BUS_OrderDetail;
import BUS.BUS_Warranty;
import DTO.DTO_Date;
import DTO.DTO_DefaultInvoice;
import DTO.DTO_InvoiceDetail;
import DTO.DTO_Order;
import DTO.DTO_BusinessReport;
import DTO.DTO_User;
import DTO.DTO_Warranty;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetImpl;
import com.mysql.jdbc.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class DAL_Order extends DAL_Connection {
    private BUS_OrderDetail bus_orderdetail = new BUS_OrderDetail();
    private BUS_ExportInvoice bus_exportinvoice = new BUS_ExportInvoice();
    private BUS_Warranty bus_warranty = new BUS_Warranty();
    private BUS_Option bus_option = new BUS_Option();
    
    public DAL_Order() {
        conn = getConnection();
    }
    
    public ArrayList <DTO_Order> getList(int currStatus) {
        String query = "SELECT `order`.`id`, `userid`, `order`.`phone` AS orderphone, `order`.`address` AS orderaddress, `order`.`initdate` as orderdate, `orderprice`, `delidate`, `pay`, `order`.`status` AS orderstatus, "
                + "`username`, `fullname`, `email`, `password`, `user`.`phone`, `user`.`address`, `permission`, `user`.`initdate` as userdate, `user`.`status` as userstatus  FROM `order`, `user` WHERE `order`.`userid` = `user`.`id` AND `order`.`status` = " + currStatus;
        ArrayList <DTO_Order> list = new ArrayList <DTO_Order> ();
        
        try {
            Statement stmt = (Statement) conn.createStatement();
            ResultSetImpl rs = (ResultSetImpl) stmt.executeQuery(query);
            while(rs.next()) {
                String id = rs.getString("id");
                String userid = rs.getString("userid");
                String orderphone = rs.getString("orderphone");
                String orderaddress = rs.getString("orderaddress");
                
                String [] initdateString = rs.getString("orderdate").split(" ")[0].split("-");
                String [] initimeString = rs.getString("orderdate").split(" ")[1].split(":");
                DTO_Date innidate = new DTO_Date(Integer.parseInt(initdateString[0]), Integer.parseInt(initdateString[1]), Integer.parseInt(initdateString[2]), Integer.parseInt(initimeString[0]), Integer.parseInt(initimeString[1]), (int) Math.round(Double.parseDouble(initimeString[2])));

                double orderprice = rs.getDouble("orderprice");
                
                DTO_Date delidate = null;
                if (rs.getString("delidate") != null) {
                    String [] delidateString = rs.getString("delidate").split("-");
                    delidate = new DTO_Date(Integer.parseInt(delidateString[0]), Integer.parseInt(delidateString[1]), Integer.parseInt(delidateString[2]), 0, 0, 0);
                }
                
                int pay = rs.getInt("pay");
                int orderstatus = rs.getInt("orderstatus");
                DTO_Order order = new DTO_Order(id, userid, orderphone, orderaddress, innidate, orderprice, delidate, pay, orderstatus);
                
                String username = rs.getString("username");
                String fullname = rs.getString("fullname");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                int permission = rs.getInt("permission");
                int status = rs.getInt("userstatus");
                String[] userdateString = rs.getString("userdate").split(" ")[0].split("-");
                DTO_Date userDate = new DTO_Date(Integer.parseInt(userdateString[0]), Integer.parseInt(userdateString[1]), Integer.parseInt(userdateString[2]), 0, 0, 0);

                DTO_User user = new DTO_User(userid, username, fullname, email, password, "", phone, address, permission, status, userDate);
                order.setUser(user);
                
                list.add(order);
            }  
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public ArrayList <DTO_BusinessReport> getBusinessReportByYear(int year) {
        String query = "SELECT `t1`.`month` AS month, `revenue`, `expense` FROM \n" +
"(SELECT MONTH(`initdate`) AS month, SUM(`orderprice`) as revenue FROM `order` WHERE YEAR(`initdate`) = "+ year +" GROUP BY MONTH(`initdate`)) AS t1,\n" +
"(SELECT MONTH(`initdate`) AS month, SUM(`invoiceprice`) as expense FROM `importinvoice` WHERE YEAR(`initdate`) = "+ year +" GROUP BY MONTH(`initdate`)) AS t2\n" +
"WHERE `t1`.`month` = `t2`.`month`";
        ArrayList <DTO_BusinessReport> list = new ArrayList<>();
        
        try {
            Statement stmt = (Statement) conn.createStatement();
            ResultSetImpl rs = (ResultSetImpl) stmt.executeQuery(query);
            while(rs.next()) {
                int month = rs.getInt("month");
                double revenue = rs.getDouble("revenue");
                double expense = rs.getDouble("expense");
                DTO_BusinessReport report = new DTO_BusinessReport(month, revenue, expense);
                list.add(report);
            }  
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public int insert(DTO_Order item) {
        String query = "INSERT INTO `order`(`id`, `userid`, `phone`, `address`, `initdate`, `orderprice`, `status`, `pay`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        int rs = -1;
        
        try {
            PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(query);
            stmt.setString(1, item.getId());
            stmt.setString(2, item.getUserid());
            stmt.setString(3, item.getPhone());
            stmt.setString(4, item.getAddress());
            stmt.setString(5, item.getInitdate().toString());
            stmt.setDouble(6, item.getPrice());
            stmt.setInt(7, item.getStatus());
            stmt.setInt(8, item.getPay());
            rs = stmt.executeUpdate();
            
            for (DTO_InvoiceDetail detail : item.getDetails()) {
                bus_orderdetail.insert(detail);
            }
            
            String userid = item.getUserid();
            LocalDateTime today = LocalDateTime.now();
            DTO_Date initdate = new DTO_Date(today.getYear(), today.getMonthValue(), today.getDayOfMonth()
                    , today.getHour(), today.getMinute(), today.getSecond());
            double invoiceprice = 0;
            for (DTO_InvoiceDetail detail : item.getDetails()) {
                invoiceprice = invoiceprice + detail.getTotalprice();
            }
            DTO_DefaultInvoice exportinvoice = new DTO_DefaultInvoice("", userid, initdate, invoiceprice);
            exportinvoice.setExportId(bus_exportinvoice.getTotal() + 1);
            for (int i = 0; i < item.getDetails().size(); i++) {
                item.getDetails().get(i).setInvoiceid(exportinvoice.getId());
                bus_option.updateRemain(item.getDetails().get(i).getOption(), item.getDetails().get(i).getQuantity());
            }
            exportinvoice.setDetails(item.getDetails());
            bus_exportinvoice.insert(exportinvoice);

            for (DTO_InvoiceDetail detail : item.getDetails()) {
                String productid = detail.getOption().getProductid();
                int sold = bus_option.getTotalSold(productid);
                bus_option.updateSold(detail.getOption(), detail.getQuantity());
                for (int i = 1; i <= detail.getQuantity(); i++) {
                    DTO_Warranty warranty = new DTO_Warranty(productid + "_" + (sold + i), userid, detail.getOptionid(), initdate);
                    bus_warranty.insert(warranty);
                }
            }
            
        } 
        
        catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
    
    public int updateDelidate(DTO_Order item) {
        String query = "UPDATE `order` SET `delidate` = ? WHERE `id` = '"+ item.getId() +"'";
        int rs = -1;
        
        try {
            PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(query);
            stmt.setString(1, item.getDelidate().toString().split(" ")[0]);
            rs = stmt.executeUpdate();
        } 
        
        catch (Exception e) {
            e.printStackTrace();
        } 
        return rs;
    }
    
    public int updateStatus(DTO_Order item) {
        String query = "UPDATE `order` SET `status` = ? WHERE `id` = '"+ item.getId() +"'";
        int rs = -1;
        
        try {
            PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(query);
            stmt.setInt(1, item.getStatus());
            rs = stmt.executeUpdate();
            String userid = item.getUserid();
            LocalDateTime today = LocalDateTime.now();
            DTO_Date initdate = new DTO_Date(today.getYear(), today.getMonthValue(), today.getDayOfMonth()
                    , today.getHour(), today.getMinute(), today.getSecond());
            switch (item.getStatus()) {
                case 2:
                    double invoiceprice = 0;
                    for (DTO_InvoiceDetail detail : item.getDetails()) {
                        invoiceprice = invoiceprice + detail.getTotalprice();
                    }
                    DTO_DefaultInvoice exportinvoice = new DTO_DefaultInvoice("", userid, initdate, invoiceprice);
                    exportinvoice.setExportId(bus_exportinvoice.getTotal() + 1);
                    for (int i = 0; i < item.getDetails().size(); i++) {
                        item.getDetails().get(i).setInvoiceid(exportinvoice.getId());
                        bus_option.updateRemain(item.getDetails().get(i).getOption(), item.getDetails().get(i).getQuantity());
                    }
                    exportinvoice.setDetails(item.getDetails());
                    bus_exportinvoice.insert(exportinvoice);
                    break;
                case 4:
                    for (DTO_InvoiceDetail detail : item.getDetails()) {
                        String productid = detail.getOption().getProductid();
                        int sold = bus_option.getTotalSold(productid);
                        bus_option.updateSold(detail.getOption(), detail.getQuantity());
                        for (int i = 1; i <= detail.getQuantity(); i++) {
                            DTO_Warranty warranty = new DTO_Warranty(productid + "_" + (sold + i), userid, detail.getOptionid(), initdate);
                            bus_warranty.insert(warranty);
                        }
                    }
                    break;
            }
        } 
        
        catch (Exception e) {
            e.printStackTrace();
        } 
        return rs;
    }
    
    public int getTotal() {
        String query = "SELECT * FROM `order`";
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
