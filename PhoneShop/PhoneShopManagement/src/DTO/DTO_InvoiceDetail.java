
package DTO;



public class DTO_InvoiceDetail {
    private String invoiceid, optionid;
    private int quantity;
    private double totalprice;
    private DTO_Option option;

    public DTO_InvoiceDetail() {
    }

    public DTO_InvoiceDetail(String invoiceid, String optionid, int quantity, double totalprice) {
        this.invoiceid = invoiceid;
        this.optionid = optionid;
        this.quantity = quantity;
        this.totalprice = totalprice;
    }

    public String getInvoiceid() {
        return invoiceid;
    }

    public void setInvoiceid(String invoiceid) {
        this.invoiceid = invoiceid;
    }

    public String getOptionid() {
        return optionid;
    }

    public void setOptionid(String optionid) {
        this.optionid = optionid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(double totalprice) {
        this.totalprice = totalprice;
    }

    public DTO_Option getOption() {
        return option;
    }

    public void setOption(DTO_Option option) {
        this.option = option;
    }
    
}
