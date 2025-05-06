
package BUS;

import DAL.DAL_Brand;
import DTO.DTO_Brand;
import java.util.ArrayList;


public class BUS_Brand {
    private DAL_Brand dal = new DAL_Brand();
    private ArrayList <DTO_Brand> list = dal.getList();

    public ArrayList<DTO_Brand> getList() {
        return list;
    }
}
