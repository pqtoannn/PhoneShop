
package BUS;

import DAL.DAL_Color;
import DTO.DTO_Color;
import Utils.Utils;
import javafx.collections.ObservableList;


public class BUS_Color {
    private DAL_Color dal = new DAL_Color();

    public ObservableList<DTO_Color> getList() {
        return dal.getList();
    }

    public ObservableList<DTO_Color>getListByProductID() {
        return dal.getListByProductID(Utils.selectedProduct.getId());
    }
    
    public int insert(DTO_Color item) {
        return dal.insert(item);
    }
    
    public int insertColorProduct(DTO_Color item) {
        return dal.insertColorProduct(item);
    }
    
    public int updateImage(DTO_Color item){
        return dal.updateImage(item);
    }
    
    public int deleteColor(DTO_Color item) {
        return dal.deleteColor(item);    
    }
    
    public int deleteColorProduct(DTO_Color item) {
        return dal.deleteColorProduct(item);
    }

    public boolean colorIsExist(String rgb) {
        return dal.colorIsExist(rgb);
    }
    
}
