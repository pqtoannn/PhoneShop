
package BUS;

import DAL.DAL_Option;
import DTO.DTO_Option;
import Utils.Utils;
import java.util.ArrayList;


public class BUS_Option {
    private DAL_Option dal = new DAL_Option();

    public ArrayList<DTO_Option> getList() {
        return dal.getList(Utils.selectedProduct.getId());
    }
    
    public ArrayList<DTO_Option> getByProductID(String id) {
        return dal.getList(id);
    }
    
    public int getTotalSold(String id) {
        return dal.getTotalSold(id);
    }
    
    public double getMinImportPrice(String id) {
        return dal.getMinImportPrice(id);
    }
    
    public double getMinSalePrice(String id) {
        return dal.getMinSalePrice(id);
    }
    
    public ArrayList<DTO_Option> getDistinctOption() {
        return dal.getDistinctOption(Utils.selectedProduct.getId());
    }
 
    public int insert(DTO_Option item) {
        return dal.insert(item);
    }
    
    public int update(DTO_Option item) {
        return dal.update(item);
    }
    
    public int updateRemain(DTO_Option item, int quantity) {
        return dal.updateRemain(item, quantity);
    }
    
    public int updateSold(DTO_Option item, int quantity) {
        return dal.updateSold(item,quantity);
    }
    
    public int getTotal() {
        return dal.getTotal();
    }
    
}
