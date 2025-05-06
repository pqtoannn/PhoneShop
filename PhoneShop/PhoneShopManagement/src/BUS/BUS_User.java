
package BUS;

import DAL.DAL_User;
import DTO.DTO_Date;
import DTO.DTO_User;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;


public class BUS_User {
    private DAL_User dal = new DAL_User();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    public ArrayList<DTO_User> getList(int permisstion) {
        return dal.getList(permisstion);
    }

    public int insert(DTO_User item) {
        return dal.insert(item);
    }
    
    public int update(DTO_User item) {
        return dal.update(item);
    }
    
    public int getTotal() {
        return dal.getTotal();
    }
    
    public int changeStatus(String email, int status){
        return dal.changeStatus(email, status);
    }
    
    public DTO_User getUser(String email, String password){
        return dal.getUser(email, password);
    }
    
    public DTO_User getUserByID(String userid){
        return dal.getUserByID(userid);
    }
    
    public boolean phoneIsExist(String userid, String phone) {
        return dal.phoneIsExist(userid, phone);
    }
    
    public boolean userIsExits(String email){
        return dal.userIsExist(email);
    }
    
    public ArrayList<DTO_User> sortByUserIDASC(ArrayList<DTO_User> list) {
        Collections.sort(list, (o1, o2) -> o1.getId().compareTo(o2.getId()));
        return list;
    }
    
    public ArrayList<DTO_User> sortByUserNameASC(ArrayList<DTO_User> list) {
        Collections.sort(list, (o1, o2) -> o1.getUsername().compareToIgnoreCase(o2.getUsername()));
        return list;
    }
    
    public ArrayList<DTO_User> sortByFullNameASC(ArrayList<DTO_User> list) {
        Collections.sort(list, (o1, o2) -> o1.getFullname().compareToIgnoreCase(o2.getFullname()));
        return list;
    }
    
    public ArrayList<DTO_User> sortByEmailASC(ArrayList<DTO_User> list) {
        Collections.sort(list, (o1, o2) -> o1.getEmail().compareToIgnoreCase(o2.getEmail()));
        return list;
    }
    
    public ArrayList<DTO_User> sortByPhoneASC(ArrayList<DTO_User> list) {
        Collections.sort(list, (o1, o2) -> o1.getPhone().compareTo(o2.getPhone()));
        return list;
    }
    
    public ArrayList<DTO_User> sortByInitDateASC(ArrayList<DTO_User> list) {
        Collections.sort(list, (o1, o2) -> (java.time.Duration.between(LocalDateTime.parse(o1.getInitdate().toString().split(" ")[0] + " 00:00:00", formatter), 
                LocalDateTime.parse(o2.getInitdate().toString().split(" ")[0] + " 00:00:00", formatter)).toMinutes() >= 0) ? -1 : 1);
        
        return list;
    }
    
    public ArrayList<DTO_User> sortByUserIDDESC(ArrayList<DTO_User> list) {
        Collections.sort(list, (o1, o2) -> o2.getId().compareTo(o1.getId()));
        return list;
    }

    public ArrayList<DTO_User> sortByUserNameDESC(ArrayList<DTO_User> list) {
        Collections.sort(list, (o1, o2) -> o2.getUsername().compareToIgnoreCase(o1.getUsername()));
        return list;
    }

    public ArrayList<DTO_User> sortByFullNameDESC(ArrayList<DTO_User> list) {
        Collections.sort(list, (o1, o2) -> o2.getFullname().compareToIgnoreCase(o1.getFullname()));
        return list;
    }

    public ArrayList<DTO_User> sortByEmailDESC(ArrayList<DTO_User> list) {
        Collections.sort(list, (o1, o2) -> o2.getEmail().compareToIgnoreCase(o1.getEmail()));
        return list;
    }

    public ArrayList<DTO_User> sortByPhoneDESC(ArrayList<DTO_User> list) {
        Collections.sort(list, (o1, o2) -> o2.getPhone().compareTo(o1.getPhone()));
        return list;
    }

    public ArrayList<DTO_User> sortByInitDateDESC(ArrayList<DTO_User> list) {
        Collections.sort(list, (o1, o2) -> (java.time.Duration.between(LocalDateTime.parse(o1.getInitdate().toString().split(" ")[0] + " 00:00:00", formatter), 
                LocalDateTime.parse(o2.getInitdate().toString().split(" ")[0] + " 00:00:00", formatter)).toMinutes() <= 0) ? -1 : 1);
        
        return list;
    }
    
    public ArrayList<DTO_User> searchByUserID(ArrayList<DTO_User> list, String keyword) {
        ArrayList<DTO_User> result = new ArrayList<>();
        for (DTO_User us : list) {
            if (us.getId().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(us);
            }
        }
        return result;
    }
    
    public ArrayList<DTO_User> searchByUserName(ArrayList<DTO_User> list, String keyword) {
        ArrayList<DTO_User> result = new ArrayList<>();
        for (DTO_User us : list) {
            if (us.getUsername().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(us);
            }
        }
        return result;
    }

    public ArrayList<DTO_User> searchByFullName(ArrayList<DTO_User> list, String keyword) {
        ArrayList<DTO_User> result = new ArrayList<>();
        String normalizedKeyWord = removeDiacritics(keyword.toLowerCase());
        for (DTO_User us : list) {
            String normalizedFullname = removeDiacritics(us.getFullname().toLowerCase());
            if (normalizedFullname.contains(normalizedKeyWord)) {
                result.add(us);
            }
        }
        return result;
    }
    
    public ArrayList<DTO_User> searchByEmail(ArrayList<DTO_User> list, String keyword) {
        ArrayList<DTO_User> result = new ArrayList<>();
        for (DTO_User us : list) {
            if (us.getEmail().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(us);
            }
        }
        return result;
    }
    
    public ArrayList<DTO_User> searchByPhone(ArrayList<DTO_User> list, String keyword) {
        ArrayList<DTO_User> result = new ArrayList<>();
        for (DTO_User us : list) {
            if (us.getPhone().contains(keyword.toLowerCase())) {
                result.add(us);
            }
        }
        return result;
    }
    
    public ArrayList<DTO_User> searchByInitDate(ArrayList<DTO_User> list, DTO_Date fromDate, DTO_Date toDate) {
        ArrayList<DTO_User> result = new ArrayList<>();
        for (DTO_User us : list) {
            DTO_Date INDate = us.getInitdate();

            LocalDateTime date1 = LocalDateTime.parse(fromDate.toString(), formatter);
            LocalDateTime date_a = LocalDateTime.parse(INDate.toString().split(" ")[0] + " 00:00:00", formatter);
            LocalDateTime date2 = LocalDateTime.parse(toDate.toString(), formatter);

            long diffInMinutes1 = java.time.Duration.between(date1, date_a).toMinutes();
            long diffInMinutes2 = java.time.Duration.between(date_a, date2).toMinutes();

            if ((diffInMinutes1 >= 0) && (diffInMinutes2 >= 0)) {
                result.add(us);
            }
        }
        return result;
    }
    
    private String removeDiacritics(String inputString) {
        return Normalizer.normalize(inputString, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
    
}
