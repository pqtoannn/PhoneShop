
package DTO;


public class DTO_Rom {
    private int rom;
    private String productid;

    public DTO_Rom() {
    }

    public DTO_Rom(int rom, String productid) {
        this.rom = rom;
        this.productid = productid;
    }

    public int getRom() {
        return rom;
    }

    public void setRom(int rom) {
        this.rom = rom;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

}
