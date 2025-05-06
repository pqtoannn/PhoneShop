
package DTO;


public class DTO_Order extends DTO_DefaultInvoice {
    private String phone, address;
    private DTO_Date delidate;
    private int pay, status;

    public DTO_Order() {
    }

    public DTO_Order(String id, String userid, String phone, String address, DTO_Date innidate, double price, DTO_Date delidate, int pay, int status) {
        super(id, userid, innidate, price);
        this.phone = phone;
        this.address = address;
        this.delidate = delidate;
        this.pay = pay;
        this.status = status;
    }

    public void setId(int total) {
        String newId = "OD0000" + total;
        switch ((total + "").length()) {
            case 2:
                newId = "OD000" + total;
                break;
            case 3:
                newId = "OD00" + total;
                break;
            case 4:
                newId = "OD0" + total;
                break;
            case 5:
                newId = "OD" + total;
                break;
            default:
        }
        this.id = newId; 
    }
    
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public DTO_Date getDelidate() {
        return delidate;
    }

    public void setDelidate(DTO_Date delidate) {
        this.delidate = delidate;
    }

    public int getPay() {
        return pay;
    }

    public void setPay(int pay) {
        this.pay = pay;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
