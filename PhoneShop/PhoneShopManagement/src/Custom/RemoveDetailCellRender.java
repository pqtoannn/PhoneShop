
package Custom;

import DTO.DTO_Product;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;


public class RemoveDetailCellRender extends DefaultTableCellRenderer {
    private JTable table;
    private JLabel removeLabel;
    private int row;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        removeLabel = new JLabel();
        removeLabel.setIcon(new ImageIcon(getClass().getResource("/assets/icons8-delete-48.png")));
        removeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        return removeLabel;
    }
    
}
