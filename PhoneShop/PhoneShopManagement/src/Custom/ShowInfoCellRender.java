
package Custom;

import DTO.DTO_Product;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;


public class ShowInfoCellRender extends DefaultTableCellRenderer {
    private JTable table;
    private JLabel eyeLabel;
    private int row;
    private DTO_Product item;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        //this.item = (DTO_Product) table.getValueAt(row, 10);
        eyeLabel = new JLabel();
        eyeLabel.setIcon(new ImageIcon(getClass().getResource("/assets/icons8-eye-40.png")));
        eyeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        return eyeLabel;
    }
    
}
