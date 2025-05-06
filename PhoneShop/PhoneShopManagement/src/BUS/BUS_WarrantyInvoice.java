
package BUS;

import DAL.DAL_WarrantyInvoice;
import DTO.DTO_WarrantyInvoice;
import Utils.Utils;
import java.util.ArrayList;


public class BUS_WarrantyInvoice {
    private DAL_WarrantyInvoice dal = new DAL_WarrantyInvoice();
    
    public ArrayList <DTO_WarrantyInvoice> getList() {
        return dal.getList(Utils.selectedWarranty.getId());
    }
    
    public int insert(DTO_WarrantyInvoice item) {
        return dal.insert(item);
    }
    
    public int getTotal() {
        return dal.getTotal();
    }
}
