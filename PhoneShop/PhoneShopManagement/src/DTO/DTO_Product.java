
package DTO;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class DTO_Product {
    private String id, brandid, brandname, productname, detail;
    private double minimportprice, minsaleprice, warranty;
    private int status, remain, sold;
    private ArrayList <DTO_Option> options = new ArrayList <DTO_Option> ();
    private ArrayList <String> images = new ArrayList <String> ();
    private ObservableList <DTO_Color> colors = FXCollections.observableArrayList();
    private ArrayList <DTO_Rom> roms = new ArrayList <DTO_Rom> ();
    private ArrayList <DTO_Ram> rams = new ArrayList <DTO_Ram> ();

    public DTO_Product() {
    }

    public DTO_Product(String id, String brandid, String brandname, String productname, double minimportprice, double minsaleprice, String detail, double warranty, int status, int remain, int sold) {
        this.id = id;
        this.brandid = brandid;
        this.brandname = brandname;
        this.productname = productname;
        this.minimportprice = minimportprice;
        this.minsaleprice = minsaleprice;
        this.detail = detail;
        this.warranty = warranty;
        this.status = status;
        this.remain = remain;
        this.sold = sold;
    }

    public String getId() {
        return id;
    }

    public void setId(int total) {
        String newId = "PR0000" + total;
        switch ((total + "").length()) {
            case 2:
                newId = "PR000" + total;
                break;
            case 3:
                newId = "PR00" + total;
                break;
            case 4:
                newId = "PR0" + total;
                break;
            case 5:
                newId = "PR" + total;
                break;
            default:
        }
        this.id = newId; 
    }

    public String getBrandid() {
        return brandid;
    }

    public void setBrandid(String brandid) {
        this.brandid = brandid;
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public double getMinimportprice() {
        return minimportprice;
    }

    public void setMinimportprice(double minimportprice) {
        this.minimportprice = minimportprice;
    }

    public double getMinsaleprice() {
        return minsaleprice;
    }

    public void setMinsaleprice(double minsaleprice) {
        this.minsaleprice = minsaleprice;
    }

    public double getWarranty() {
        return warranty;
    }

    public void setWarranty(double warranty) {
        this.warranty = warranty;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public ArrayList<DTO_Option> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<DTO_Option> options) {
        this.options = options;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public ObservableList<DTO_Color> getColors() {
        return colors;
    }

    public void setColors(ObservableList<DTO_Color> colors) {
        this.colors = colors;
    }

    public ArrayList<DTO_Rom> getRoms() {
        return roms;
    }

    public void setRoms(ArrayList<DTO_Rom> roms) {
        this.roms = roms;
    }

    public ArrayList<DTO_Ram> getRams() {
        return rams;
    }

    public void setRams(ArrayList<DTO_Ram> rams) {
        this.rams = rams;
    }

    
    
}
