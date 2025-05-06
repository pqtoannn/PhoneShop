
package DTO;


public class DTO_Ram {
    private int ram;
    private String productid;

    public DTO_Ram() {
    }

    public DTO_Ram(int ram, String productid) {
        this.ram = ram;
        this.productid = productid;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }
    
    
}
