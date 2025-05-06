package Custom;

import Interface.EventTextFieldInputChange;
import BUS.BUS_Option;
import BUS.BUS_Product;
import Utils.Utils;
import DTO.DTO_Option;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.JTextField;

public class SalePriceCellEditor extends DefaultCellEditor {

    private JTextField input;
    private EventTextFieldInputChange event;
    private JTable table;
    private int row;
    private DTO_Option item;
    private BUS_Option bus_option = new BUS_Option();
    private BUS_Product bus_product = new BUS_Product();
    private DecimalFormat df = new DecimalFormat("###,###,###");

    public SalePriceCellEditor(EventTextFieldInputChange eChange) {
        super(new JCheckBox());
        this.event = eChange;
        input = new JTextField();
        input.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (input.getText().equals("")) {
                    input.setText("0");
                    return;
                }            
            }
            
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_ENTER) {
                    double price = Double.parseDouble(input.getText().toString().replaceAll(",", ""));
                    if (price != item.getSaleprice()) {
                        item.setSaleprice(price);
                        event.inputChanged(item, price);
                        input.setText(df.format(price));
                    }
                }
            }
            @Override
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
        
                if (Character.isAlphabetic(c)) {
                    evt.consume();
                }
            }
        });
        
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        super.getTableCellEditorComponent(table, value, isSelected, row, column);
        this.table = table;
        this.row = row;
        this.item = (DTO_Option) table.getValueAt(row, 5);
        double price = Double.parseDouble(value.toString().replaceAll(",", ""));
        input.setText(df.format(price));
        input.setEnabled(false);
        enable();
        return input;
    }

    private void enable() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                    input.setEnabled(true);
                } catch (Exception e) {

                }
            }
        }).start();
    }

    @Override
    public Object getCellEditorValue() {
        return input.getText();
    }

}
