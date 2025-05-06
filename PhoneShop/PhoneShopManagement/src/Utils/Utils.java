
package Utils;

import DTO.DTO_Brand;
import DTO.DTO_Color;
import DTO.DTO_Date;
import DTO.DTO_DefaultInvoice;
import DTO.DTO_InvoiceDetail;
import DTO.DTO_Order;
import DTO.DTO_Product;
import DTO.DTO_User;
import DTO.DTO_Warranty;
import DTO.DTO_WarrantyInvoice;
import java.io.File;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Utils {
    public static final int VIEW_MODE = 1;
    public static final int ADD_MODE = 2;
    public static final int UPDATE_MODE = 3;
    
    public static ArrayList <DTO_InvoiceDetail> queueList = new ArrayList <DTO_InvoiceDetail> ();
    public static ArrayList <DTO_Brand> brandList = new ArrayList <DTO_Brand> ();
    public static ObservableList <DTO_Color> defaultColorKeys = FXCollections.observableArrayList();
      
    public static DTO_Product selectedProduct = new DTO_Product();
    public static DTO_User selectedUser = new DTO_User();
    public static DTO_User loginedUser = new DTO_User();
    public static DTO_Order selectedOrder = new DTO_Order();
    public static DTO_DefaultInvoice selectedImport = new DTO_DefaultInvoice();
    public static DTO_DefaultInvoice selectedExport = new DTO_DefaultInvoice();
    public static DTO_Warranty selectedWarranty = new DTO_Warranty();
    public static DTO_WarrantyInvoice selectedWarrantyInvoice = new DTO_WarrantyInvoice();
    public static DTO_Date selectedDelidate = new DTO_Date();
    
    public static int imageIndex;
    public static File currentPath = new File("D:\\");
}
