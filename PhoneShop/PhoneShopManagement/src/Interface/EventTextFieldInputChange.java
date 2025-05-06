
package Interface;

import DTO.DTO_InvoiceDetail;
import DTO.DTO_Option;


public interface EventTextFieldInputChange {
    public void inputChanged(DTO_Option item, double newPrice);
    public void inputChanged(DTO_InvoiceDetail item, double newPrice);
}
