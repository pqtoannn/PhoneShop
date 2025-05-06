
package GUI;

import App.Application;
import BUS.BUS_User;
import DTO.DTO_Date;
import DTO.DTO_User;
import Utils.EmailValidator;
import Utils.Utils;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.util.UIScale;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.time.LocalDateTime;
import javax.swing.JOptionPane;
import raven.toast.Notifications;


public class GUI_formRegister extends javax.swing.JFrame {
    private BUS_User bus_user = new BUS_User();
    private EmailValidator emailValidator = new EmailValidator();
    private Notifications notifications = new Notifications();
    public GUI_formRegister() {
        initComponents();
        init();
        setLocationRelativeTo(null);
        setResizable(false);
        notifications.getInstance().setJFrame(this);
    }
    
    private void init() {
        setLayout(new RegisterFormLayout());
        register.setLayout(new registerLayout());
        lbTitle.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:$h1.font");
        register.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Login.background;"
                + "arc:20;"
                + "border:30,40,50,30");

        txtPass.putClientProperty(FlatClientProperties.STYLE, ""
                + "showRevealButton:true;"
                + "showCapsLock:true");
        txtPassAg.putClientProperty(FlatClientProperties.STYLE, ""
                + "showRevealButton:true;"
                + "showCapsLock:true");
        cmdRegister.putClientProperty(FlatClientProperties.STYLE, ""
                + "borderWidth:0;"
                + "focusWidth:0");
        txtUsername.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tên người dùng");
        txtEmail.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Email");
        txtEmail.setEnabled(false);
        txtPass.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Mật khẩu");
        txtPassAg.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập lại mật khẩu");
    }

    private class RegisterFormLayout implements LayoutManager {

        @Override
        public void addLayoutComponent(String name, Component comp) {
        }

        @Override
        public void removeLayoutComponent(Component comp) {
        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            synchronized (parent.getTreeLock()) {
                return new Dimension(0, 0);
            }
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            synchronized (parent.getTreeLock()) {
                return new Dimension(0, 0);
            }
        }

        @Override
        public void layoutContainer(Container parent) {
            synchronized (parent.getTreeLock()) {
                int width = parent.getWidth();
                int height = parent.getHeight();
                int loginWidth = UIScale.scale(360);
                int loginHeight = register.getPreferredSize().height;
                int x = (width - loginWidth) / 2;
                int y = (height - loginHeight) / 2;
                register.setBounds(x, y, loginWidth, loginHeight);
            }
        }
    }
    
    private class registerLayout implements LayoutManager {
        private final int titleGap = 10;
        private final int textGap = 10;
        private final int labelGap = 5;
        private final int buttonGap = 50;

        @Override
        public void addLayoutComponent(String name, Component comp) {
        }

        @Override
        public void removeLayoutComponent(Component comp) {
        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            synchronized (parent.getTreeLock()) {
                Insets insets = parent.getInsets();
                int height = insets.top + insets.bottom;

                height += lbTitle.getPreferredSize().height;
                height += UIScale.scale(titleGap);
                height += lbUsername.getPreferredSize().height;
                height += UIScale.scale(labelGap);
                height += txtUsername.getPreferredSize().height;
                height += UIScale.scale(textGap);
                height += lbUser.getPreferredSize().height;
                height += UIScale.scale(labelGap);
                height += txtEmail.getPreferredSize().height;
                height += UIScale.scale(textGap);

                height += lbPass.getPreferredSize().height;
                height += UIScale.scale(labelGap);
                height += txtPass.getPreferredSize().height;
                height += UIScale.scale(textGap);
                height += lbPassAg.getPreferredSize().height;
                height += UIScale.scale(labelGap);
                height += txtPassAg.getPreferredSize().height;
                height += UIScale.scale(buttonGap);
                height += cmdRegister.getPreferredSize().height;
                height += lbl_login.getPreferredSize().height;
                return new Dimension(0, height);
            }
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            synchronized (parent.getTreeLock()) {
                return new Dimension(0, 0);
            }
        }

        @Override
        public void layoutContainer(Container parent) {
            synchronized (parent.getTreeLock()) {
                Insets insets = parent.getInsets();
                int x = insets.left;
                int y = insets.top;
                int width = parent.getWidth() - (insets.left + insets.right);

                lbTitle.setBounds(x, y, width, lbTitle.getPreferredSize().height);
                y += lbTitle.getPreferredSize().height + UIScale.scale(titleGap);

                lbUsername.setBounds(x, y, width, lbUsername.getPreferredSize().height);
                y += lbUsername.getPreferredSize().height + UIScale.scale(labelGap);
                txtUsername.setBounds(x, y, width, txtUsername.getPreferredSize().height);
                y += txtUsername.getPreferredSize().height + UIScale.scale(textGap);
                
                lbUser.setBounds(x, y, width, lbUser.getPreferredSize().height);
                y += lbUser.getPreferredSize().height + UIScale.scale(labelGap);
                txtEmail.setBounds(x, y, width, txtEmail.getPreferredSize().height);
                y += txtEmail.getPreferredSize().height + UIScale.scale(textGap);

                lbPass.setBounds(x, y, width, lbPass.getPreferredSize().height);
                y += lbPass.getPreferredSize().height + UIScale.scale(labelGap);
                txtPass.setBounds(x, y, width, txtPass.getPreferredSize().height);
                y += txtPass.getPreferredSize().height + UIScale.scale(textGap);
                
                lbPassAg.setBounds(x, y, width, lbPassAg.getPreferredSize().height);
                y += lbPassAg.getPreferredSize().height + UIScale.scale(labelGap);
                txtPassAg.setBounds(x, y, width, txtPassAg.getPreferredSize().height);
                y += txtPassAg.getPreferredSize().height + UIScale.scale(buttonGap);

                cmdRegister.setBounds(x + 70, y, 150, cmdRegister.getPreferredSize().height);
                lbl_login.setBounds(x + 70, y + 50, 250, lbl_login.getPreferredSize().height);
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        register = new javax.swing.JPanel();
        lbTitle = new javax.swing.JLabel();
        lbUsername = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        lbPass = new javax.swing.JLabel();
        txtPass = new javax.swing.JPasswordField();
        txtPassAg = new javax.swing.JPasswordField();
        lbPassAg = new javax.swing.JLabel();
        lbUser = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        cmdRegister = new javax.swing.JButton();
        lbl_login = new javax.swing.JLabel();

        lbTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTitle.setText("Đăng ký");

        lbUsername.setText("Tên người dùng");

        lbPass.setText("Mật khẩu");

        lbPassAg.setText("Nhập lại mật khẩu");

        lbUser.setText("Email");

        cmdRegister.setText("Đăng ký");
        cmdRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdRegisterActionPerformed(evt);
            }
        });

        lbl_login.setForeground(new java.awt.Color(0, 102, 255));
        lbl_login.setText("Đã có tài khoản ? đăng nhập");
        lbl_login.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_loginMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout registerLayout = new javax.swing.GroupLayout(register);
        register.setLayout(registerLayout);
        registerLayout.setHorizontalGroup(
            registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(registerLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(registerLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(lbUser))
            .addGroup(registerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtPass, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPassAg, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbPassAg)))
            .addGroup(registerLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(registerLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(lbUsername))
                    .addComponent(lbTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(registerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(registerLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(lbPass))
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, registerLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(cmdRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(88, 88, 88))
            .addGroup(registerLayout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(lbl_login, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        registerLayout.setVerticalGroup(
            registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(registerLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(lbTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbUsername)
                .addGap(9, 9, 9)
                .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(lbUser)
                .addGap(6, 6, 6)
                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(lbPass)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbPassAg)
                .addGap(4, 4, 4)
                .addComponent(txtPassAg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmdRegister)
                .addGap(18, 18, 18)
                .addComponent(lbl_login)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(register, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(49, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(register, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(52, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmdRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdRegisterActionPerformed
        if (txtEmail.getText().isEmpty() || txtPass.getText().isEmpty() || txtUsername.getText().isEmpty() || txtPassAg.getText().isEmpty()) {
            notifications.getInstance().show(Notifications.Type.INFO
                    , Notifications.Location.TOP_CENTER, "Không được để trống thông tin !!!");
            return;
        }
        
        if(! emailValidator.validate(txtEmail.getText().trim())){
                JOptionPane.showMessageDialog(this, "Email sai định dạng vd: abc123@gmail.com");
                txtEmail.requestFocus();
                return;
        }
        
        if(bus_user.userIsExits(txtEmail.getText().toString())){
            notifications.getInstance().show(Notifications.Type.INFO
                    , Notifications.Location.TOP_CENTER, "Tài khoản đã tồn tại !!!");
            return;
        }
        
        if(!txtPass.getText().equals(txtPassAg.getText())){
            notifications.getInstance().show(Notifications.Type.INFO
                    , Notifications.Location.TOP_CENTER, "Lặp lại sai mật khẩu !!!");
            return;
        }
        
        LocalDateTime today = LocalDateTime.now();
        DTO_Date innidate = new DTO_Date(today.getYear(), today.getMonthValue(), today.getDayOfMonth(), today.getHour(), today.getMinute(), today.getSecond());
        DTO_User newUser = new DTO_User("", txtUsername.getText(), txtUsername.getText(), txtEmail.getText(), txtPass.getText(), "", "", "", 1, 1, innidate);
        newUser.setId(bus_user.getTotal() + 1);

        if (bus_user.insert(newUser) != -1) {
            JOptionPane.showMessageDialog(this, "Tạo tài khoản mới thành công");
            this.dispose();
        } else {
            notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Thêm tài khoản không thành công!!!");
        }
    }//GEN-LAST:event_cmdRegisterActionPerformed
    
    private void lbl_loginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_loginMouseClicked
        this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_lbl_loginMouseClicked

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
            java.util.logging.Logger.getLogger(GUI_formRegister.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI_formRegister.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI_formRegister.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI_formRegister.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI_formRegister().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdRegister;
    private javax.swing.JLabel lbPass;
    private javax.swing.JLabel lbPassAg;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JLabel lbUser;
    private javax.swing.JLabel lbUsername;
    private javax.swing.JLabel lbl_login;
    private javax.swing.JPanel register;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JPasswordField txtPassAg;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
