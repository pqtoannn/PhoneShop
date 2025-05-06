
package DTO;

import java.util.ArrayList;


public class DTO_DefaultInvoice {
    protected String id, userid;
    protected DTO_Date initdate;
    protected double price;
    protected DTO_User user;
    protected ArrayList <DTO_InvoiceDetail> details = new ArrayList <DTO_InvoiceDetail> ();

    public DTO_DefaultInvoice() {
    }

    public DTO_DefaultInvoice(String id, String userid, DTO_Date initdate, double price) {
        this.id = id;
        this.userid = userid;
        this.initdate = initdate;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setImportId(int total) {
        String newId = "IM0000" + total;
        switch ((total + "").length()) {
            case 2:
                newId = "IM000" + total;
                break;
            case 3:
                newId = "IM00" + total;
                break;
            case 4:
                newId = "IM0" + total;
                break;
            case 5:
                newId = "IM" + total;
                break;
            default:
        }
        this.id = newId; 
    }

    public void setExportId(int total) {
        String newId = "EX0000" + total;
        switch ((total + "").length()) {
            case 2:
                newId = "EX000" + total;
                break;
            case 3:
                newId = "EX00" + total;
                break;
            case 4:
                newId = "EX0" + total;
                break;
            case 5:
                newId = "EX" + total;
                break;
            default:
        }
        this.id = newId; 
    }
    
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public DTO_Date getInitdate() {
        return initdate;
    }

    public void setInitdate(DTO_Date initdate) {
        this.initdate = initdate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public DTO_User getUser() {
        return user;
    }

    public void setUser(DTO_User user) {
        this.user = user;
    }
    
    public ArrayList<DTO_InvoiceDetail> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<DTO_InvoiceDetail> details) {
        this.details = details;
    }
    
}
