package DTO;


public class DTO_SalesReport {
    private String brandname;
    private int sold;

    public DTO_SalesReport() {
    }

    public DTO_SalesReport(String brandname, int sold) {
        this.brandname = brandname;
        this.sold = sold;
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

}
