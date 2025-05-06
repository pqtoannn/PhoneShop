
package BUS;

import DAL.DAL_ImportDetail;
import DTO.DTO_InvoiceDetail;
import Utils.Utils;
import java.util.ArrayList;


public class BUS_ImportDetail {
    private DAL_ImportDetail dal = new DAL_ImportDetail();
    
    public ArrayList<DTO_InvoiceDetail> getList() {
        return dal.getList(Utils.selectedImport.getId());
    }

    public int insert(DTO_InvoiceDetail item) {
        return dal.insert(item);
    }
}
