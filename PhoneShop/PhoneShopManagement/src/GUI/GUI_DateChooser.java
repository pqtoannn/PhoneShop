package GUI;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLaf;
import java.awt.event.MouseEvent;
import Custom.calendar.model.ModelDate;
import Custom.calendar.utils.CalendarSelectedListener;
import Utils.Utils;
import javax.swing.JLabel;


public class GUI_DateChooser extends javax.swing.JFrame {
    private JLabel txt_delidate;
    
    public GUI_DateChooser(JLabel txt_delidate) {
        initComponents();
        this.txt_delidate = txt_delidate;
        calendar2.addCalendarSelectedListener(new CalendarSelectedListener() {
            @Override
            public void selected(MouseEvent evt, ModelDate date) {
                date.toDate();
                txt_delidate.setText(Utils.selectedDelidate.format().split(" ")[0]);
            }
        });
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        calendar2 = new Custom.calendar.Calendar();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(calendar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(calendar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        FlatLaf.registerCustomDefaultsSource("test");
        FlatDarculaLaf.setup();
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new GUI_DateChooser().setVisible(true);
//            }
//        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Custom.calendar.Calendar calendar2;
    // End of variables declaration//GEN-END:variables

}
