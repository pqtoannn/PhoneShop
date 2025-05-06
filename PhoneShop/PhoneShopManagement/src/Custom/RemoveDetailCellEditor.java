package Custom;

import Interface.TableActionEvent;
import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;

public class RemoveDetailCellEditor extends DefaultCellEditor {

    private TableActionEvent event;

    public RemoveDetailCellEditor(TableActionEvent event) {
        super(new JCheckBox());
        this.event = event;
    }

    @Override
    public Component getTableCellEditorComponent(JTable jtable, Object o, boolean bln, int row, int column) {
        JLabel removeLabel = new JLabel();
        removeLabel.setIcon(new ImageIcon(getClass().getResource("/assets/icons8-delete-48.png")));
        removeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        removeLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                event.onView(row);
            }
        });
        return removeLabel;
    }

}
