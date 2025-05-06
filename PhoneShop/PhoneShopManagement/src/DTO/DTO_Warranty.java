
package DTO;

import java.util.ArrayList;


public class DTO_Warranty {
    private String id, userid, optionid;
    private DTO_Date initdate;
    private DTO_Option option;
    private DTO_User user;
    private DTO_Product product;
    private ArrayList <DTO_WarrantyInvoice> invoices = new ArrayList <DTO_WarrantyInvoice> ();

    public DTO_Warranty() {
    }

    public DTO_Warranty(String id, String userid, String optionid, DTO_Date initdate) {
        this.id = id;
        this.userid = userid;
        this.optionid = optionid;
        this.initdate = initdate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getOptionid() {
        return optionid;
    }

    public void setOptionid(String optionid) {
        this.optionid = optionid;
    }

    public DTO_Date getInitdate() {
        return initdate;
    }

    public void setInitdate(DTO_Date initdate) {
        this.initdate = initdate;
    }

    public DTO_Option getOption() {
        return option;
    }

    public void setOption(DTO_Option option) {
        this.option = option;
    }

    public DTO_User getUser() {
        return user;
    }

    public void setUser(DTO_User user) {
        this.user = user;
    }

    public ArrayList<DTO_WarrantyInvoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(ArrayList<DTO_WarrantyInvoice> invoices) {
        this.invoices = invoices;
    }

    public DTO_Product getProduct() {
        return product;
    }

    public void setProduct(DTO_Product product) {
        this.product = product;
    }
    
    

}
