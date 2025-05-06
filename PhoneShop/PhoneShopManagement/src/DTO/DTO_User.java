
package DTO;


public class DTO_User {
    private String id, username, fullname, email, password, avatar, phone, address;
    private int permission, status;
    private DTO_Date initdate;

    public DTO_User() {
    }

    public DTO_User(String id, String username, String fullname, String email, String password, String avatar, String phone, String address, int permission, int status, DTO_Date initdate) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.phone = phone;
        this.address = address;
        this.permission = permission;
        this.status = status;
        this.initdate = initdate;
    }

    public String getId() {
        return id;
    }

    public void setId(int total) {
        String newId = "US0000" + total;
        switch ((total + "").length()) {
            case 2:
                newId = "US000" + total;
                break;
            case 3:
                newId = "US00" + total;
                break;
            case 4:
                newId = "US0" + total;
                break;
            case 5:
                newId = "US" + total;
                break;
            default:
        }
        this.id = newId; 
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DTO_Date getInitdate() {
        return initdate;
    }

    public void setInitdate(DTO_Date initdate) {
        this.initdate = initdate;
    }

    public Object toLowerCase() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
 
}
