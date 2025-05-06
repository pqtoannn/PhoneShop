
package BUS;

import DAL.DAL_ExportDetail;
import DTO.DTO_InvoiceDetail;
import Utils.Utils;
import java.util.ArrayList;


public class BUS_ExportDetail {
    private DAL_ExportDetail dal = new DAL_ExportDetail();
    
    public ArrayList<DTO_InvoiceDetail> getList() {
        return dal.getList(Utils.selectedExport.getId());
    }

    public int insert(DTO_InvoiceDetail item) {
        return dal.insert(item);
    }
    
}
