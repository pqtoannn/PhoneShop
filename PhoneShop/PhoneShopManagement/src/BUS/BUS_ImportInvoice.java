
package BUS;

import DAL.DAL_ImportInvoice;
import DTO.DTO_Date;
import DTO.DTO_DefaultInvoice;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;


public class BUS_ImportInvoice {
    private DAL_ImportInvoice dal = new DAL_ImportInvoice();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ArrayList <DTO_DefaultInvoice> getList() {
        return dal.getList();
    }
    
    public int insert(DTO_DefaultInvoice item) {
        return dal.insert(item);
    }
    
    public int getTotal() {
        return dal.getTotal();
    }
    
    public ArrayList<DTO_DefaultInvoice> sortByInvoicePriceASC(ArrayList<DTO_DefaultInvoice> list) {
        Collections.sort(list, ((o1, o2) -> (o1.getPrice() < o2.getPrice()) ? -1 : 1 ));

        return list;
    }
    
    public ArrayList<DTO_DefaultInvoice> sortByInitDateASC(ArrayList<DTO_DefaultInvoice> list) {
        Collections.sort(list, (o1, o2) -> (java.time.Duration.between(LocalDateTime.parse(o1.getInitdate().toString().split(" ")[0] + " 00:00:00", formatter), 
                LocalDateTime.parse(o2.getInitdate().toString().split(" ")[0] + " 00:00:00", formatter)).toMinutes() >= 0) ? -1 : 1);
        
        return list;
    }
    
    public ArrayList<DTO_DefaultInvoice> sortByInvoiceFullNameASC(ArrayList<DTO_DefaultInvoice> list) {
        Collections.sort(list,(o1, o2) -> o1.getUser().getFullname().compareToIgnoreCase(o2.getUser().getFullname()));
        
        return list;
    }
    
    public ArrayList<DTO_DefaultInvoice> sortByInvoicePriceDESC(ArrayList<DTO_DefaultInvoice> list) {
        Collections.sort(list, ((o1, o2) -> (o2.getPrice() < o1.getPrice()) ? -1 : 1 ));

        return list;
    }
    
    public ArrayList<DTO_DefaultInvoice> sortByInitDateDESC(ArrayList<DTO_DefaultInvoice> list) {
        Collections.sort(list, (o1, o2) -> (java.time.Duration.between(LocalDateTime.parse(o1.getInitdate().toString().split(" ")[0] + " 00:00:00", formatter), 
                LocalDateTime.parse(o2.getInitdate().toString().split(" ")[0] + " 00:00:00", formatter)).toMinutes() <= 0) ? -1 : 1);
        
        return list;
    }
    
    public ArrayList<DTO_DefaultInvoice> sortByInvoiceFullNameDESC(ArrayList<DTO_DefaultInvoice> list) {
        Collections.sort(list,(o1, o2) -> o2.getUser().getFullname().compareToIgnoreCase(o1.getUser().getFullname()));
        
        return list;
    }
    
    public ArrayList<DTO_DefaultInvoice> searchByInvoiceID(ArrayList<DTO_DefaultInvoice> list, String keyword) {
        ArrayList<DTO_DefaultInvoice> result = new ArrayList<>();
        for (DTO_DefaultInvoice in : list) {
            if (in.getId().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(in);
            }
        }
        return result;
    }
    
    public ArrayList<DTO_DefaultInvoice> searchByFullName(ArrayList<DTO_DefaultInvoice> list, String keyword) {
        ArrayList<DTO_DefaultInvoice> result = new ArrayList<>();
        String normalizedKeyWord = removeDiacritics(keyword.toLowerCase());
        for (DTO_DefaultInvoice in : list) {
            String normalizedFullname = removeDiacritics(in.getUser().getFullname().toLowerCase());
            if (normalizedFullname.contains(normalizedKeyWord)) {
                result.add(in);
            }
        }
        return result;
    }
    
    public ArrayList<DTO_DefaultInvoice> searchByInitDate(ArrayList<DTO_DefaultInvoice> list, DTO_Date fromDate, DTO_Date toDate) {
        ArrayList<DTO_DefaultInvoice> result = new ArrayList<>();
        for (DTO_DefaultInvoice in : list) {
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

    public ArrayList<DTO_DefaultInvoice> searchByOrderPrice(ArrayList<DTO_DefaultInvoice> list, double fromPrice, double toPrice) {
        ArrayList<DTO_DefaultInvoice> result = new ArrayList<>();
        for (DTO_DefaultInvoice in : list) {
            if (in.getPrice() >= fromPrice && in.getPrice() <= toPrice) {
                result.add(in);
            }
        }
        return result;
    }
    
    private String removeDiacritics(String inputString) {
        return Normalizer.normalize(inputString, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
}
