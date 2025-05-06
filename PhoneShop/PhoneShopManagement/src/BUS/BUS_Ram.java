
package BUS;

import DAL.DAL_Ram;
import DTO.DTO_Ram;
import Utils.Utils;
import java.util.ArrayList;


public class BUS_Ram {
    private DAL_Ram dal = new DAL_Ram();

    public ArrayList<DTO_Ram> getList() {
        return dal.getList(Utils.selectedProduct.getId());
    }
    
    public int insert(DTO_Ram item) {
        return dal.insert(item);
    }
    
    public int delete(DTO_Ram item) {
        return dal.delete(item);
    }

}
