
package BUS;

import DAL.DAL_OrderDetail;
import DTO.DTO_InvoiceDetail;
import Utils.Utils;
import java.util.ArrayList;


public class BUS_OrderDetail {
    private DAL_OrderDetail dal = new DAL_OrderDetail();
    
    public ArrayList<DTO_InvoiceDetail> getList() {
        return dal.getList(Utils.selectedOrder.getId());
    }
    
    public int insert(DTO_InvoiceDetail item) {
        return dal.insert(item);
    }
}
