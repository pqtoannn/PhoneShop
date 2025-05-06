
package DTO;


public class DTO_WarrantyInvoice {
    private String id, warrantyid, describe;
    private DTO_Date initdate;
    private double expense;

    public DTO_WarrantyInvoice() {
    }

    public DTO_WarrantyInvoice(String id, String warrantyid, String describe, DTO_Date initdate, double expense) {
        this.id = id;
        this.warrantyid = warrantyid;
        this.describe = describe;
        this.initdate = initdate;
        this.expense = expense;
    }

    public String getId() {
        return id;
    }

    public void setId(int total) {
        String newId = "WA0000" + total;
        switch ((total + "").length()) {
            case 2:
                newId = "WA000" + total;
                break;
            case 3:
                newId = "WA00" + total;
                break;
            case 4:
                newId = "WA0" + total;
                break;
            case 5:
                newId = "WA" + total;
                break;
            default:
        }
        this.id = newId; 
    }

    public String getWarrantyid() {
        return warrantyid;
    }

    public void setWarrantyid(String warrantyid) {
        this.warrantyid = warrantyid;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public DTO_Date getInitdate() {
        return initdate;
    }

    public void setInitdate(DTO_Date initdate) {
        this.initdate = initdate;
    }

    public double getExpense() {
        return expense;
    }

    public void setExpense(double expense) {
        this.expense = expense;
    }
    
    
}
