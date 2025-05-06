
package BUS;

import DAL.DAL_Warranty;
import DTO.DTO_Date;
import DTO.DTO_Warranty;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;


public class BUS_Warranty {
    private DAL_Warranty dal = new DAL_Warranty();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    public ArrayList <DTO_Warranty> getList() {
        return dal.getList();
    }
    
    public ArrayList <DTO_Warranty> getListOutOfDate() {
        return dal.getListOutOfDate();
    }
    
    public int insert(DTO_Warranty item) {
        return dal.insert(item);
    }
    
    public ArrayList<DTO_Warranty> sortByInitDateASC(ArrayList<DTO_Warranty> list) {
        Collections.sort(list, (o1, o2) -> (java.time.Duration.between(LocalDateTime.parse(o1.getInitdate().toString().split(" ")[0] + " 00:00:00", formatter), 
                LocalDateTime.parse(o2.getInitdate().toString().split(" ")[0] + " 00:00:00", formatter)).toMinutes() >= 0) ? -1 : 1);
        
        return list;
    }
    
    public ArrayList<DTO_Warranty> sortByFullNameASC(ArrayList<DTO_Warranty> list) {
        Collections.sort(list,(o1, o2) -> o1.getUser().getFullname().compareToIgnoreCase(o2.getUser().getFullname()));
        
        return list;
    }
    
    public ArrayList<DTO_Warranty> sortByProductNameASC(ArrayList<DTO_Warranty> list) {
        Collections.sort(list,(o1, o2) -> o1.getProduct().getProductname().compareToIgnoreCase(o2.getProduct().getProductname()));
        
        return list;
    }
    
    public ArrayList<DTO_Warranty> sortByInitDateDESC(ArrayList<DTO_Warranty> list) {
        Collections.sort(list, (o1, o2) -> (java.time.Duration.between(LocalDateTime.parse(o1.getInitdate().toString().split(" ")[0] + " 00:00:00", formatter), 
                LocalDateTime.parse(o2.getInitdate().toString().split(" ")[0] + " 00:00:00", formatter)).toMinutes() <= 0) ? -1 : 1);
        
        return list;
    }
    
    public ArrayList<DTO_Warranty> sortByFullNameDESC(ArrayList<DTO_Warranty> list) {
        Collections.sort(list,(o1, o2) -> o2.getUser().getFullname().compareToIgnoreCase(o1.getUser().getFullname()));
        
        return list;
    }
    
    public ArrayList<DTO_Warranty> sortByProductNameDESC(ArrayList<DTO_Warranty> list) {
        Collections.sort(list,(o1, o2) -> o2.getProduct().getProductname().compareToIgnoreCase(o1.getProduct().getProductname()));
        
        return list;
    }
    
    public ArrayList<DTO_Warranty> searchByWarrantyID(ArrayList<DTO_Warranty> list, String keyword) {
        ArrayList<DTO_Warranty> result = new ArrayList<>();
        for (DTO_Warranty wa : list) {
            if (wa.getId().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(wa);
            }
        }
        return result;
    }

    public ArrayList<DTO_Warranty> searchByFullName(ArrayList<DTO_Warranty> list, String keyword) {
        ArrayList<DTO_Warranty> result = new ArrayList<>();
        String normalizedKeyWord = removeDiacritics(keyword.toLowerCase());
        for (DTO_Warranty in : list) {
            String normalizedFullname = removeDiacritics(in.getUser().getFullname().toLowerCase());
            if (normalizedFullname.contains(normalizedKeyWord)) {
                result.add(in);
            }
        }
        return result;
    }
    
    public ArrayList<DTO_Warranty> searchByProductName(ArrayList<DTO_Warranty> list, String keyword) {
        ArrayList<DTO_Warranty> result = new ArrayList<>();
        for (DTO_Warranty wa : list) {
            if (wa.getProduct().getProductname().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(wa);
            }
        }
        return result;
    }
    
    public ArrayList<DTO_Warranty> searchByInitDate(ArrayList<DTO_Warranty> list, DTO_Date fromDate, DTO_Date toDate) {
        ArrayList<DTO_Warranty> result = new ArrayList<>();
        for (DTO_Warranty in : list) {
            DTO_Date INDate = in.getInitdate();

            LocalDateTime date1 = LocalDateTime.parse(fromDate.toString(), formatter);
            LocalDateTime date_a = LocalDateTime.parse(INDate.toString().split(" ")[0] + " 00:00:00", formatter);
            LocalDateTime date2 = LocalDateTime.parse(toDate.toString(), formatter);

            long diffInMinutes1 = java.time.Duration.between(date1, date_a).toMinutes();
            long diffInMinutes2 = java.time.Duration.between(date_a, date2).toMinutes();

            if ((diffInMinutes1 >= 0) && (diffInMinutes2 >= 0)) {
                result.add(in);
            }
        }
        return result;
    }
    
    private String removeDiacritics(String inputString) {
        return Normalizer.normalize(inputString, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

}
