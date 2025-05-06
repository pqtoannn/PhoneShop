
package DTO;


public class DTO_Option {
    private String id, productid,productname;
    private DTO_Color color;
    private int rom, ram, remain, sold;
    private double importprice, saleprice;
    private boolean saved;

    public DTO_Option() {
    }

    public DTO_Option(String id, String productid, String productname, DTO_Color color, int rom, int ram, double importprice, double saleprice, int remain, int sold, boolean saved) {
        this.id = id;
        this.productid = productid;
        this.productname = productname;
        this.color = color;
        this.rom = rom;
        this.ram = ram;
        this.importprice = importprice;
        this.saleprice = saleprice;
        this.remain = remain;
        this.sold = sold;
        this.saved = saved;
    }

    public String getId() {
        return id;
    }

    public void setId(int total) {
        String newId = "OP000" + total;
        switch ((total + "").length()) {
            case 2:
                newId = "OP00" + total;
                break;
            case 3:
                newId = "OP0" + total;
                break;
            case 4:
                newId = "OP" + total;
                break;
            default:
        }
        this.id = newId; 
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    
    
    public DTO_Color getColor() {
        return color;
    }

    public void setColor(DTO_Color color) {
        this.color = color;
    }

    public int getRom() {
        return rom;
    }

    public void setRom(int rom) {
        this.rom = rom;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public int getRemain() {
        return remain;
    }

    public void setRemain(int remain) {
        this.remain = remain;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public double getImportprice() {
        return importprice;
    }

    public void setImportprice(double importprice) {
        this.importprice = importprice;
    }

    public double getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(double saleprice) {
        this.saleprice = saleprice;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }
    
}
