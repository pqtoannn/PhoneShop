
package Custom;

import DTO.DTO_InvoiceDetail;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;


public class InvoiceDetailColorCellRender extends DefaultTableCellRenderer {
    private JTable table;
    private JLabel colorLabel;
    private int row;
    private DTO_InvoiceDetail item;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        this.item = (DTO_InvoiceDetail) table.getValueAt(row, 6);
        String color = item.getOption().getColor().rgbToString();
        String[] rgbTemp = color.split(";");
        int[] rgb = new int[] {Integer.parseInt(rgbTemp[0]), Integer.parseInt(rgbTemp[1]), Integer.parseInt(rgbTemp[2])};
        colorLabel = new JLabel();
        colorLabel.setIcon(new ColorIcon(30, 30, rgb));
        colorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        return colorLabel;
    }
    
}
