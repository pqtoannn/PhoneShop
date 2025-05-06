
package GUI;

import BUS.BUS_Color;
import Custom.ColorIcon;
import DTO.DTO_Color;
import Utils.Utils;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JColorChooser;
import raven.toast.Notifications;


public class GUI_formColor extends javax.swing.JFrame {
    private DTO_Color selectedColor;
    private Color color;
    private final BUS_Color bus_color = new BUS_Color();
    private Notifications notifications = new Notifications();
    
    public GUI_formColor() {
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null);
        notifications.getInstance().setJFrame(this);
        btn_Save.setIcon(new FlatSVGIcon("assets/save.svg", 0.3f));
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        crazyPanel_SearchByPrice = new raven.crazypanel.CrazyPanel();
        txt_name = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        btn_chooseColor = new javax.swing.JButton();
        lbl_rgb = new javax.swing.JLabel();
        lbl_showColor = new javax.swing.JLabel();
        btn_Save = new javax.swing.JButton();

        crazyPanel_SearchByPrice.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
            "",
            new String[]{
                "font:bold +10",
                "font:bold +1",
                "",
                "",
                "showClearButton:true;JTextField.placeholderText=first",
                "showClearButton:true;JTextField.placeholderText=last",
                "",
                "showClearButton:true;JTextField.placeholderText=e.g. tesla motors",
                "",
                "showClearButton:true;JTextField.placeholderText=e.g. example@gmail.com",
                "",
                "font:bold +1",
                "",
                "",
                "showClearButton:true;JTextField.placeholderText=street. apt/suite",
                "",
                "showClearButton:true;JTextField.placeholderText=office.room/flat",
                "",
                "showClearButton:true",
                "",
                "",
                "",
                "showClearButton:true",
                "",
                "showClearButton:true",
                "",
                "showClearButton:true;JTextField.placeholderText=for us non-residents only"
            }
        ));
        crazyPanel_SearchByPrice.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
            "wrap 2,fillx,insets 25",
            "[grow 0,trail]15[fill]",
            "",
            new String[]{
                "wrap,al lead",
                "wrap,al lead",
                "wrap,al lead",
                "",
                "split 2",
                "",
                "",
                "",
                "",
                "",
                "span 2,grow 1",
                "wrap,al lead",
                "wrap,al lead",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "span 2,al trail"
            }
        ));

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Tên màu");

        btn_chooseColor.setText("Chọn màu");
        btn_chooseColor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_chooseColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_chooseColorActionPerformed(evt);
            }
        });

        lbl_rgb.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_rgb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout crazyPanel_SearchByPriceLayout = new javax.swing.GroupLayout(crazyPanel_SearchByPrice);
        crazyPanel_SearchByPrice.setLayout(crazyPanel_SearchByPriceLayout);
        crazyPanel_SearchByPriceLayout.setHorizontalGroup(
            crazyPanel_SearchByPriceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, crazyPanel_SearchByPriceLayout.createSequentialGroup()
                .addContainerGap(170, Short.MAX_VALUE)
                .addComponent(btn_chooseColor, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(168, 168, 168))
            .addGroup(crazyPanel_SearchByPriceLayout.createSequentialGroup()
                .addGroup(crazyPanel_SearchByPriceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(crazyPanel_SearchByPriceLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_name, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(crazyPanel_SearchByPriceLayout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(lbl_showColor, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(crazyPanel_SearchByPriceLayout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addComponent(lbl_rgb, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        crazyPanel_SearchByPriceLayout.setVerticalGroup(
            crazyPanel_SearchByPriceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crazyPanel_SearchByPriceLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(crazyPanel_SearchByPriceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_name, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbl_rgb, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_chooseColor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(lbl_showColor, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        btn_Save.setText("Lưu");
        btn_Save.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(crazyPanel_SearchByPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(175, 175, 175)
                .addComponent(btn_Save, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(crazyPanel_SearchByPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_Save, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_chooseColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_chooseColorActionPerformed
        color = Color.BLACK;
        color = JColorChooser.showDialog(this, "Chọn màu", color);
        if (color == null) {
            color = Color.WHITE;
            renderColor(color);
            selectedColor = null;
        }
        renderColor(color);
        
    }//GEN-LAST:event_btn_chooseColorActionPerformed

    private void btn_SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SaveActionPerformed
        if (selectedColor != null && !txt_name.getText().isEmpty()) {
            selectedColor.setName(txt_name.getText().toString());
            if (bus_color.colorIsExist(selectedColor.rgbToString())) {
                notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, 
                        "Đã tồn tại mã màu này !!!");
                return;
            }
            bus_color.insert(selectedColor);
            Utils.defaultColorKeys.add(selectedColor);

            color = Color.BLACK;
            renderColor(color);
            selectedColor = null;
            notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, 
                        "Thêm màu thành công !!!");
            dispose();
        }
        else {
            notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, 
                        "Không để trống tên màu !!!");
            txt_name.requestFocus();
            return;
        }
    }//GEN-LAST:event_btn_SaveActionPerformed

    public void renderColor(Color color) {
        selectedColor = new DTO_Color(new int[] {color.getRed(), color.getGreen(), color.getBlue()}, "", "", new ArrayList<>());
        String[] rgbTemp = selectedColor.rgbToString().split(";");
        int[] rgb = new int[] {Integer.parseInt(rgbTemp[0]), Integer.parseInt(rgbTemp[1]), Integer.parseInt(rgbTemp[2])};
        lbl_showColor.setIcon(new ColorIcon(350, 100, rgb));
        lbl_rgb.setText("R: " + color.getRed() + " G: " + color.getGreen() + " B: " + color.getBlue());
    }
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI_formColor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI_formColor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI_formColor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI_formColor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI_formColor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Save;
    private javax.swing.JButton btn_chooseColor;
    private raven.crazypanel.CrazyPanel crazyPanel_SearchByPrice;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel lbl_rgb;
    private javax.swing.JLabel lbl_showColor;
    private javax.swing.JTextField txt_name;
    // End of variables declaration//GEN-END:variables
}
