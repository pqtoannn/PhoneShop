
package BUS;

import DAL.DAL_Rom;
import DTO.DTO_Rom;
import Utils.Utils;
import java.util.ArrayList;


public class BUS_Rom {
    private DAL_Rom dal = new DAL_Rom();

    public ArrayList<DTO_Rom> getList() {
        return dal.getList(Utils.selectedProduct.getId());
    }
    
    public int insert(DTO_Rom item) {
        return dal.insert(item);
    }
    
    public int delete(DTO_Rom item) {
        return dal.delete(item);
    }
    
}
