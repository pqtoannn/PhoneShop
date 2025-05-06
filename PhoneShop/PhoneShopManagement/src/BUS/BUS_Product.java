
package BUS;

import DAL.DAL_Product;
import DTO.DTO_Product;
import DTO.DTO_SalesReport;
import java.util.ArrayList;
import java.util.Collections;


public class BUS_Product {
    private DAL_Product dal = new DAL_Product();

    public ArrayList<DTO_Product> getList() {
        return dal.getList();
    }
    
    public ArrayList<DTO_SalesReport> getSalesReport() {
        return dal.getSalesReport();
    }

    public int insert(DTO_Product item) {
        return dal.insert(item);
    }
    
    public int update(DTO_Product item) {
        return dal.update(item);
    }
    
    public int deleteProduct(String id){
        return dal.deleteProduct(id);
    }
    public int getTotal() {
        return dal.getTotal();
    }
    
    public ArrayList<DTO_Product> sortByProductNameASC(ArrayList<DTO_Product> list) {
        Collections.sort(list,(o1, o2) -> o1.getProductname().compareToIgnoreCase(o2.getProductname()));
        
        return list;
    }
    
    public ArrayList<DTO_Product> sortByBrandASC(ArrayList<DTO_Product> list) {
        Collections.sort(list, (o1, o2) -> o1.getBrandname().compareToIgnoreCase(o2.getBrandname()));

        return list;
    }
    
    public ArrayList<DTO_Product> sortByPriceASC(ArrayList<DTO_Product> list) {
        Collections.sort(list, (o1, o2) -> (o1.getMinimportprice()< o2.getMinimportprice()) ? -1 : 1 );

        return list;
    }
    
    public ArrayList<DTO_Product> sortByProductNameDESC(ArrayList<DTO_Product> list) {
        Collections.sort(list, (o1, o2) -> o2.getProductname().compareToIgnoreCase(o1.getProductname()));

        return list;
    }

    public ArrayList<DTO_Product> sortByBrandDESC(ArrayList<DTO_Product> list) {
        Collections.sort(list, (o1, o2) -> o2.getBrandname().compareToIgnoreCase(o1.getBrandname()));

        return list;
    }

    public ArrayList<DTO_Product> sortByPriceDESC(ArrayList<DTO_Product> list) {
        Collections.sort(list, (o1, o2) -> (o2.getMinimportprice() < o1.getMinimportprice()) ? -1 : 1);

        return list;
    }
    
    public ArrayList<DTO_Product> searchByProductID(ArrayList<DTO_Product> list, String keyword) {
        ArrayList<DTO_Product> result = new ArrayList<>();
        for (DTO_Product pr : list) {
            if (pr.getId().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(pr);
            }
        }
        return result;
    }
    
    public ArrayList<DTO_Product> searchByProductName(ArrayList<DTO_Product> list, String keyword) {
        ArrayList<DTO_Product> result = new ArrayList<>();
        for (DTO_Product pr : list) {
            if (pr.getProductname().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(pr);
            }
        }
        return result;
    }
    
    public ArrayList<DTO_Product> searchByBrand(ArrayList<DTO_Product> list, String keyword) {
        ArrayList<DTO_Product> result = new ArrayList<>();
        for (DTO_Product pr : list) {
            if (pr.getBrandname().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(pr);
            }
        }
        return result;
    }
    
    public ArrayList<DTO_Product> searchByPrice(ArrayList<DTO_Product> list, double fromPrice, double toPrice) {
        ArrayList<DTO_Product> result = new ArrayList<>();
        for (DTO_Product pr : list) {
            if (pr.getMinimportprice() >= fromPrice && pr.getMinimportprice() <= toPrice) {
                result.add(pr);
            }
        }
        return result;
    }
    
}
