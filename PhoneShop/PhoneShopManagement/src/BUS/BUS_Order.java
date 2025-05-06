
package BUS;

import DAL.DAL_Order;
import DTO.DTO_BusinessReport;
import DTO.DTO_Date;
import DTO.DTO_Order;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;


public class BUS_Order {
    private DAL_Order dal = new DAL_Order();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    public ArrayList <DTO_Order> getList(int currStatus) {
        return dal.getList(currStatus);
    }
    
    public ArrayList <DTO_BusinessReport> getBusinessReportByYear(int year) {
        return dal.getBusinessReportByYear(year);
    }
    
    public int insert(DTO_Order item) {
        return dal.insert(item);
    }
    
    public int updateDelidate(DTO_Order item) {
        return dal.updateDelidate(item);
    }
    
    public int updateStatus(DTO_Order item) {
        return dal.updateStatus(item);
    }
    
    public int getTotal() {
        return dal.getTotal();
    }
    
    public ArrayList<DTO_Order> sortByOrderIDASC(ArrayList<DTO_Order> list) {
        Collections.sort(list, (o1, o2) -> o1.getId().compareTo(o2.getId()));
        return list;
    }
    
    public ArrayList<DTO_Order> sortByInitDateASC(ArrayList<DTO_Order> list) {
        Collections.sort(list, (o1, o2) -> (java.time.Duration.between(LocalDateTime.parse(o1.getInitdate().toString().split(" ")[0] + " 00:00:00", formatter), 
                LocalDateTime.parse(o2.getInitdate().toString().split(" ")[0] + " 00:00:00", formatter)).toMinutes() >= 0) ? -1 : 1);
        
        return list;
    }
    
    public ArrayList<DTO_Order> sortByDeliDateASC(ArrayList<DTO_Order> list) {
        Collections.sort(list, (o1, o2) -> (java.time.Duration.between(LocalDateTime.parse(o1.getDelidate().toString().split(" ")[0] + " 00:00:00", formatter), 
                LocalDateTime.parse(o2.getDelidate().toString().split(" ")[0] + " 00:00:00", formatter)).toMinutes() >= 0) ? -1 : 1);
        
        return list;
    }
    
    public ArrayList<DTO_Order> sortByOderPriceASC(ArrayList<DTO_Order> list) {
        Collections.sort(list, (o1, o2) -> (o1.getPrice() < o2.getPrice()) ? -1 : 1 );

        return list;
    }
    
    public ArrayList<DTO_Order> sortByFullNameASC(ArrayList<DTO_Order> list) {
        Collections.sort(list,(o1, o2) -> o1.getUser().getFullname().compareToIgnoreCase(o2.getUser().getFullname()));
        
        return list;
    }
    
    public ArrayList<DTO_Order> sortByEmailASC(ArrayList<DTO_Order> list) {
        Collections.sort(list,(o1, o2) -> o1.getUser().getEmail().compareToIgnoreCase(o2.getUser().getEmail()));
        
        return list;
    }
    
    public ArrayList<DTO_Order> sortByPhoneASC(ArrayList<DTO_Order> list) {
        Collections.sort(list,(o1, o2) -> o1.getPhone().compareTo(o2.getPhone()));
        
        return list;
    }
    
    //DESC
    public ArrayList<DTO_Order> sortByOrderIDDESC(ArrayList<DTO_Order> list) {
        Collections.sort(list, (o1, o2) -> o2.getId().compareTo(o1.getId()));
        return list;
    }
    
    public ArrayList<DTO_Order> sortByInitDateDESC(ArrayList<DTO_Order> list) {
        Collections.sort(list, (o1, o2) -> (java.time.Duration.between(LocalDateTime.parse(o1.getInitdate().toString().split(" ")[0] + " 00:00:00", formatter), 
                LocalDateTime.parse(o2.getInitdate().toString().split(" ")[0] + " 00:00:00", formatter)).toMinutes() <= 0) ? -1 : 1);
        
        return list;
    }
    
    public ArrayList<DTO_Order> sortByDeliDateDESC(ArrayList<DTO_Order> list) {
        Collections.sort(list, (o1, o2) -> (java.time.Duration.between(LocalDateTime.parse(o1.getDelidate().toString().split(" ")[0] + " 00:00:00", formatter), 
                LocalDateTime.parse(o2.getDelidate().toString().split(" ")[0] + " 00:00:00", formatter)).toMinutes() <= 0) ? -1 : 1);
        
        return list;
    }
    
    public ArrayList<DTO_Order> sortByOrderPriceDESC(ArrayList<DTO_Order> list) {
        Collections.sort(list, ((o1, o2) -> (o2.getPrice() < o1.getPrice()) ? -1 : 1 ));

        return list;
    }
    
    public ArrayList<DTO_Order> sortByFullNameDESC(ArrayList<DTO_Order> list) {
        Collections.sort(list,(o1, o2) -> o2.getUser().getFullname().compareToIgnoreCase(o1.getUser().getFullname()));
        
        return list;
    }
    
    public ArrayList<DTO_Order> sortByEmailDESC(ArrayList<DTO_Order> list) {
        Collections.sort(list,(o1, o2) -> o2.getUser().getEmail().compareToIgnoreCase(o1.getUser().getEmail()));
        
        return list;
    }
    
    public ArrayList<DTO_Order> sortByPhoneDESC(ArrayList<DTO_Order> list) {
        Collections.sort(list,(o1, o2) -> o2.getPhone().compareTo(o1.getPhone()));
        
        return list;
    }
    
    public ArrayList<DTO_Order> searchByOrderID(ArrayList<DTO_Order> list, String keyword) {
        ArrayList<DTO_Order> result = new ArrayList<>();
        for (DTO_Order od : list) {
            if (od.getId().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(od);
            }
        }
        return result;
    }
    
    public ArrayList<DTO_Order> searchByFullName(ArrayList<DTO_Order> list, String keyword) {
        ArrayList<DTO_Order> result = new ArrayList<>();
        String normalizedKeyWord = removeDiacritics(keyword.toLowerCase());
        for (DTO_Order od : list) {
            String normalizedFullname = removeDiacritics(od.getUser().getFullname().toLowerCase());
            if (normalizedFullname.contains(normalizedKeyWord)) {
                result.add(od);
            }
        }
        return result;
    }
    
    public ArrayList<DTO_Order> searchByEmail(ArrayList<DTO_Order> list, String keyword) {
        ArrayList<DTO_Order> result = new ArrayList<>();
        for (DTO_Order od : list) {
            if (od.getUser().getEmail().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(od);
            }
        }
        return result;
    }
    
    public ArrayList<DTO_Order> searchByPhone(ArrayList<DTO_Order> list, String keyword) {
        ArrayList<DTO_Order> result = new ArrayList<>();
        for (DTO_Order od : list) {
            if (keyword.equals(od.getUser().getPhone())) {
                result.add(od);
            }
        }
        return result;
    }
    
    public ArrayList<DTO_Order> searchByInitDate(ArrayList<DTO_Order> list, DTO_Date fromDate, DTO_Date toDate) {
        ArrayList<DTO_Order> result = new ArrayList<>();
        for (DTO_Order od : list) {
            DTO_Date ODDate = od.getInitdate();

            LocalDateTime date1 = LocalDateTime.parse(fromDate.toString(), formatter);
            LocalDateTime date_a = LocalDateTime.parse(ODDate.toString().split(" ")[0] + " 00:00:00", formatter);
            LocalDateTime date2 = LocalDateTime.parse(toDate.toString(), formatter);

            long diffInMinutes1 = java.time.Duration.between(date1, date_a).toMinutes();
            long diffInMinutes2 = java.time.Duration.between(date_a, date2).toMinutes();

            if ((diffInMinutes1 >= 0) && (diffInMinutes2 >= 0)) {
                result.add(od);
            }

        }
        return result;
    }
    
    public ArrayList<DTO_Order> searchByOrderPrice(ArrayList<DTO_Order> list, double fromPrice, double toPrice) {
        ArrayList<DTO_Order> result = new ArrayList<>();
        for (DTO_Order od : list) {
            if (od.getPrice() >= fromPrice && od.getPrice() <= toPrice) {
                result.add(od);
            }
        }
        return result;
    }
    
    private String removeDiacritics(String inputString) {
        return Normalizer.normalize(inputString, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
    
}
