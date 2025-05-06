
package DTO;

import java.util.ArrayList;


public class DTO_Color {
    private int[] rgb = new int[3];
    private String name, productid;
    ArrayList<String> images = new ArrayList<>();
    
    public DTO_Color() {
        
    }

    public DTO_Color(int[] rgb, String name, String productid, ArrayList<String> images) {
        this.name = name;
        this.rgb = rgb;
        this.productid = productid;
        this.images = images;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }
    
    
    public int[] getRgb() {
        return rgb;
    }

    public void setRgb(int[] rgb) {
        this.rgb = rgb;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }
    
    public String rgbToString() {
        return getRgb()[0] + ";" + getRgb()[1] + ";" + getRgb()[2];
    }
  
    public String imageToString(){
        String a = "";
        if(images == null || images.size() == 0)
            return a;
        for(int  i = 0; i < images.size()-1; i++){
            a += images.get(i) + "@";
        }
        return a + images.get(images.size()-1);
    }
  
}
