package GUI;

import Utils.EmailValidator;
import BUS.BUS_User;
import DTO.DTO_Date;
import DTO.DTO_Product;
import DTO.DTO_User;
import static GUI.GUI_tableUser.formUser;
import Utils.Utils;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.time.LocalDateTime;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import org.apache.commons.io.FileUtils;
import raven.toast.Notifications;

public class GUI_formUser extends javax.swing.JFrame {
    private final Notifications notifications = new Notifications();
    
    private final BUS_User bus_user = new BUS_User();
    private EmailValidator emailValidator = new EmailValidator();
    private DTO_Date innidate = new DTO_Date();
    private String avatar = "";
    
    public GUI_formUser() {
        initComponents();
        
        setLocationRelativeTo(null);
        setResizable(false);
        notifications.getInstance().setJFrame(this);
        
        //Khởi tạo ban đầu
        
        //CUSTOMCODE ĐỪNG ĐỤNG VÔ!!!
        btn_Refresh.setIcon(new FlatSVGIcon("assets/refresh.svg", 0.25f));
        btn_Save.setIcon(new FlatSVGIcon("assets/save.svg", 0.3f));
        cbx_ChangeMode.setSelectedIndex(GUI_tableUser.currMode);
        txt_Password.putClientProperty(FlatClientProperties.STYLE, ""
                + "showRevealButton:true;"
                + "showCapsLock:true");
       
    }
    /////////////////////////
     
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        crazyPanel_Main = new raven.crazypanel.CrazyPanel();
        lbl_Avatar = new javax.swing.JLabel();
        lbl_UserID = new javax.swing.JLabel();
        lbl_chooseAvatar = new javax.swing.JLabel();
        cbx_ChangeMode = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        crazyPanel_Detail = new raven.crazypanel.CrazyPanel();
        jLabel17 = new javax.swing.JLabel();
        btn_Save = new javax.swing.JButton();
        lbl_InniDate = new javax.swing.JLabel();
        lbl_OrderPrice = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        lbl_deliDate = new javax.swing.JLabel();
        txt_Username = new javax.swing.JTextField();
        txt_Fullname = new javax.swing.JTextField();
        txt_Email = new javax.swing.JTextField();
        txt_Address = new javax.swing.JTextField();
        txt_Password = new javax.swing.JPasswordField();
        txt_Phonenum = new javax.swing.JTextField();
        btn_state = new javax.swing.JToggleButton();
        lbl_deliDate2 = new javax.swing.JLabel();
        cbx_Role = new javax.swing.JComboBox<>();
        lbl_deliDate1 = new javax.swing.JLabel();
        lbl_innidate = new javax.swing.JLabel();
        lbl_status = new javax.swing.JLabel();
        btn_Refresh = new javax.swing.JButton();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        crazyPanel_Main.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
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
        crazyPanel_Main.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
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

        lbl_Avatar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_Avatar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lbl_UserID.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbl_UserID.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lbl_chooseAvatar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-add-image-40.png"))); // NOI18N
        lbl_chooseAvatar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_chooseAvatar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_chooseAvatarMouseClicked(evt);
            }
        });

        cbx_ChangeMode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chế độ", "Chỉ xem", "Chỉ thêm", "Chỉ sửa" }));
        cbx_ChangeMode.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbx_ChangeModeItemStateChanged(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Thông tin tài khoản");

        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setText("Mã người dùng");

        crazyPanel_Detail.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
            "",
            new String[]{
                "showClearButton:true;JTextField.placeholderText=first",
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
                ""
            }
        ));
        crazyPanel_Detail.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
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

        jLabel17.setText("Mật khẩu");

        btn_Save.setBackground(new java.awt.Color(153, 255, 153));
        btn_Save.setText("Lưu");
        btn_Save.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SaveActionPerformed(evt);
            }
        });

        lbl_InniDate.setText("Tên đăng nhập");

        lbl_OrderPrice.setText("Họ tên");

        jLabel19.setText("Email");

        jLabel20.setText("Địa chỉ");

        jLabel24.setText("Số điện thoại");

        lbl_deliDate.setText("Trạng thái");

        txt_Username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_UsernameActionPerformed(evt);
            }
        });
        txt_Username.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_UsernameKeyReleased(evt);
            }
        });

        txt_Fullname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_FullnameKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_FullnameKeyReleased(evt);
            }
        });

        txt_Email.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_EmailKeyReleased(evt);
            }
        });

        txt_Address.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_AddressKeyReleased(evt);
            }
        });

        txt_Password.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_PasswordKeyReleased(evt);
            }
        });

        txt_Phonenum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_PhonenumKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_PhonenumKeyTyped(evt);
            }
        });

        btn_state.setText("State");
        btn_state.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                btn_stateItemStateChanged(evt);
            }
        });
        btn_state.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_stateActionPerformed(evt);
            }
        });

        lbl_deliDate2.setText("Quyền truy cập");

        cbx_Role.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Khách hàng", "Admin", "Root" }));
        cbx_Role.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbx_RoleItemStateChanged(evt);
            }
        });
        cbx_Role.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                cbx_RoleCaretPositionChanged(evt);
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
            }
        });

        lbl_deliDate1.setText("Ngày tạo");

        lbl_innidate.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbl_innidate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lbl_status.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbl_status.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout crazyPanel_DetailLayout = new javax.swing.GroupLayout(crazyPanel_Detail);
        crazyPanel_Detail.setLayout(crazyPanel_DetailLayout);
        crazyPanel_DetailLayout.setHorizontalGroup(
            crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crazyPanel_DetailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(crazyPanel_DetailLayout.createSequentialGroup()
                        .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, crazyPanel_DetailLayout.createSequentialGroup()
                                .addComponent(lbl_deliDate2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18))
                            .addGroup(crazyPanel_DetailLayout.createSequentialGroup()
                                .addComponent(lbl_deliDate, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)))
                        .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lbl_status, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbx_Role, 0, 181, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_Save, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_state, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(crazyPanel_DetailLayout.createSequentialGroup()
                            .addComponent(lbl_OrderPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(txt_Fullname, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(crazyPanel_DetailLayout.createSequentialGroup()
                            .addComponent(lbl_InniDate, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(txt_Username, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(crazyPanel_DetailLayout.createSequentialGroup()
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(txt_Password))
                        .addGroup(crazyPanel_DetailLayout.createSequentialGroup()
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(txt_Email, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(crazyPanel_DetailLayout.createSequentialGroup()
                            .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_deliDate1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(18, 18, Short.MAX_VALUE)
                            .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txt_Address, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                                .addComponent(txt_Phonenum)
                                .addComponent(lbl_innidate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        crazyPanel_DetailLayout.setVerticalGroup(
            crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crazyPanel_DetailLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_Username, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_InniDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_Fullname, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_OrderPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_Email, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_Password, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_Phonenum, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_Address, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(crazyPanel_DetailLayout.createSequentialGroup()
                        .addComponent(lbl_deliDate1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 5, Short.MAX_VALUE))
                    .addComponent(lbl_innidate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbl_deliDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_state, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbl_status, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(23, 23, 23)
                .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbx_Role, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_deliDate2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Save, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        btn_Refresh.setText("Làm mới");
        btn_Refresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_Refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_RefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout crazyPanel_MainLayout = new javax.swing.GroupLayout(crazyPanel_Main);
        crazyPanel_Main.setLayout(crazyPanel_MainLayout);
        crazyPanel_MainLayout.setHorizontalGroup(
            crazyPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crazyPanel_MainLayout.createSequentialGroup()
                .addGroup(crazyPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, crazyPanel_MainLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(69, 69, 69))
                    .addGroup(crazyPanel_MainLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(crazyPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lbl_chooseAvatar, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_Avatar, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_UserID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(crazyPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(crazyPanel_MainLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_Refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, crazyPanel_MainLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                        .addGroup(crazyPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, crazyPanel_MainLayout.createSequentialGroup()
                                .addComponent(cbx_ChangeMode, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(267, 267, 267))
                            .addComponent(crazyPanel_Detail, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );
        crazyPanel_MainLayout.setVerticalGroup(
            crazyPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crazyPanel_MainLayout.createSequentialGroup()
                .addGroup(crazyPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(crazyPanel_MainLayout.createSequentialGroup()
                        .addGroup(crazyPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(btn_Refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, crazyPanel_MainLayout.createSequentialGroup()
                        .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(crazyPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(crazyPanel_MainLayout.createSequentialGroup()
                        .addComponent(cbx_ChangeMode, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(crazyPanel_Detail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(crazyPanel_MainLayout.createSequentialGroup()
                        .addComponent(lbl_UserID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_Avatar, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_chooseAvatar)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(crazyPanel_Main, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(crazyPanel_Main, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 4, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cbx_ChangeModeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbx_ChangeModeItemStateChanged
        if(evt.getStateChange() == 1) {
            switch (cbx_ChangeMode.getSelectedIndex()) {
                case 1:
                    setViewMode();
                    break;
                case 2:
                    setAddMode();
                    break;
                case 3:
                    setUpdateMode();
                    break;
                default:
            }
        }
    }//GEN-LAST:event_cbx_ChangeModeItemStateChanged

    private void lbl_chooseAvatarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_chooseAvatarMouseClicked
        insertImages();
    }//GEN-LAST:event_lbl_chooseAvatarMouseClicked

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        formUser = null;
    }//GEN-LAST:event_formWindowClosing

    private void btn_SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SaveActionPerformed
        if (GUI_tableUser.currMode == Utils.ADD_MODE) {
            if (txt_Fullname.getText().equals("")) {
                notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Chưa nhập tên!!!");
                txt_Fullname.requestFocus();
                return;
            }
            if (txt_Email.getText().equals("")) {
                notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Chưa nhập Email!!!");
                txt_Email.requestFocus();
                return;
            }
            if (txt_Password.getText().equals("")) {
                notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Chưa nhập Password!!!");
                txt_Password.requestFocus();
                return;
            }
            if (bus_user.phoneIsExist("", txt_Phonenum.getText().toString())) {
                notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Số điện thoại này đã tồn tại !!!");
                return;
            }
            if(! emailValidator.validate(txt_Email.getText().trim())){
                JOptionPane.showMessageDialog(this, "Email sai định dạng vd: abc123@gmail.com");
                txt_Email.requestFocus();
                return;
            }
            DTO_User newUser = new DTO_User(lbl_UserID.getText().toString().trim(), txt_Username.getText(), txt_Fullname.getText(),txt_Email.getText(), 
                    txt_Password.getText(), "", txt_Phonenum.getText(), txt_Address.getText(), cbx_Role.getSelectedIndex(), 1, innidate);
            newUser.setAvatar(reloadImage(newUser, avatar));
            if(bus_user.insert(newUser) != -1){
                JOptionPane.showMessageDialog(this, "Tạo tài khoản mới thành công");
            }
            else{
                notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Thêm tài khoản không thành công!!!");                
            }
            Utils.selectedUser = newUser;
            GUI_tableUser.currMode = Utils.UPDATE_MODE;
            cbx_ChangeMode.setSelectedIndex(GUI_tableUser.currMode);
        }
        
        else if (GUI_tableUser.currMode == Utils.UPDATE_MODE) {
            if (txt_Fullname.getText().equals("")) {
                notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Chưa nhập tên!!!");
                txt_Fullname.requestFocus();
                return;
            }
            if (txt_Email.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Email chưa nhập");
                txt_Email.requestFocus();
                return;
            }    
            if (txt_Password.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Password chưa nhập");
                txt_Password.requestFocus();
                return;
            }
            if (bus_user.phoneIsExist(Utils.selectedUser.getId(), txt_Phonenum.getText().toString())) {
                notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Số điện thoại này đã tồn tại !!!");
                return;
            }
            if(! emailValidator.validate(txt_Email.getText().trim())){
                JOptionPane.showMessageDialog(this, "Email sai định dạng vd: abc123@gmail.com");
                txt_Email.requestFocus();
                return;
            }
            Utils.selectedUser.setAddress(txt_Address.getText());
            Utils.selectedUser.setEmail(txt_Email.getText());
            Utils.selectedUser.setFullname(txt_Fullname.getText());
            Utils.selectedUser.setPassword(txt_Password.getText());
            Utils.selectedUser.setPermission(cbx_Role.getSelectedIndex());
            Utils.selectedUser.setPhone(txt_Phonenum.getText());
            Utils.selectedUser.setUsername(txt_Username.getText());
            if(lbl_status.getText().equals("Đang hoạt động")){
                Utils.selectedUser.setStatus(1);
            }
            else{
                Utils.selectedUser.setStatus(1);
            }
            Utils.selectedUser.setAvatar(reloadImage(Utils.selectedUser, avatar));
            if(bus_user.update(Utils.selectedUser) != -1){
                JOptionPane.showMessageDialog(this, "Sửa thông tin tài khoản thành công");
            }
            else{
                notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Sửa thông tin tài khoản thất bại!!!");                
            }
        }
    }//GEN-LAST:event_btn_SaveActionPerformed

    private void txt_UsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_UsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_UsernameActionPerformed

    private void txt_PhonenumKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_PhonenumKeyTyped
        char c = evt.getKeyChar();
        if(!Character.isDigit(c)){
            evt.consume();
        }
    }//GEN-LAST:event_txt_PhonenumKeyTyped

    private void btn_stateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_stateActionPerformed
        if(lbl_status.getText().equals("Đang hoạt động")){
            lbl_status.setText("Ngừng hoạt động");
            lbl_status.setForeground(Color.red);
            btn_state.setBackground(Color.GREEN);
        }
        else{
            lbl_status.setText("Đang hoạt động");
            lbl_status.setForeground(Color.green);
            btn_state.setBackground(Color.RED);
        }
    }//GEN-LAST:event_btn_stateActionPerformed

    private void btn_stateItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_btn_stateItemStateChanged

    }//GEN-LAST:event_btn_stateItemStateChanged

    private void cbx_RoleItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbx_RoleItemStateChanged
        if(cbx_ChangeMode.getSelectedIndex() == Utils.ADD_MODE ){
            if(cbx_Role.getSelectedIndex() == 2 && Utils.loginedUser.getPermission() != 2){
                cbx_Role.setSelectedIndex(1);
                notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Bạn không có quyền để thêm tài khoản root!!!");
            }
            else{
                return;
            }
        }
    }//GEN-LAST:event_cbx_RoleItemStateChanged

    private void cbx_RoleCaretPositionChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_cbx_RoleCaretPositionChanged

    }//GEN-LAST:event_cbx_RoleCaretPositionChanged

    private void txt_UsernameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_UsernameKeyReleased
        createID();
    }//GEN-LAST:event_txt_UsernameKeyReleased

    private void txt_FullnameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_FullnameKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_FullnameKeyPressed

    private void txt_FullnameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_FullnameKeyReleased
        createID();
    }//GEN-LAST:event_txt_FullnameKeyReleased

    private void txt_EmailKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_EmailKeyReleased
        createID();
    }//GEN-LAST:event_txt_EmailKeyReleased

    private void txt_PasswordKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_PasswordKeyReleased
        createID();
    }//GEN-LAST:event_txt_PasswordKeyReleased

    private void txt_PhonenumKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_PhonenumKeyReleased
        createID();
    }//GEN-LAST:event_txt_PhonenumKeyReleased

    private void txt_AddressKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_AddressKeyReleased
        createID();
    }//GEN-LAST:event_txt_AddressKeyReleased

    private void btn_RefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_RefreshActionPerformed
        clear();
    }//GEN-LAST:event_btn_RefreshActionPerformed
    
    //Mode
    private void setViewMode() {
        GUI_tableUser.currMode = Utils.VIEW_MODE;
        //Combobox
        cbx_ChangeMode.setEnabled(true);
        cbx_Role.setSelectedIndex(Utils.selectedUser.getPermission());
        cbx_Role.setEnabled(false);
        //Label
        lbl_Avatar.setVisible(true);
        lbl_chooseAvatar.setVisible(true);
        //Button
        btn_Refresh.setEnabled(false);
        btn_Save.setEnabled(false);
        btn_state.setEnabled(false);
        //Textfield
        txt_Address.setEditable(false);
        txt_Email.setEditable(false);
        txt_Fullname.setEditable(false);
        txt_Password.setEditable(false);
        txt_Phonenum.setEditable(false);
        txt_Username.setEditable(false);
        
        showUser();
    }
    
    private void setAddMode() {
        GUI_tableUser.currMode = Utils.ADD_MODE;        
        clear();
        //Combobox
        cbx_ChangeMode.setEnabled(false);
        cbx_Role.setEnabled(true);
        //Label
        lbl_Avatar.setVisible(true);
        lbl_chooseAvatar.setVisible(true);
        //Button
        btn_Refresh.setEnabled(true);
        btn_Save.setEnabled(true);
        btn_state.setEnabled(false);
        btn_state.setBackground(Color.RED);
        //Textfield
        txt_Address.setEditable(true);
        txt_Email.setEditable(true);
        txt_Fullname.setEditable(true);
        txt_Password.setEditable(true);
        txt_Phonenum.setEditable(true);
        txt_Username.setEditable(true);
    }
    
    private void setUpdateMode() {
        GUI_tableUser.currMode = Utils.UPDATE_MODE;
        //Combobox
        cbx_ChangeMode.setEnabled(true);
        cbx_Role.setSelectedIndex(Utils.selectedUser.getPermission());
        cbx_Role.setEnabled(false);
        //Label
        lbl_Avatar.setVisible(true);
        lbl_chooseAvatar.setVisible(true);
        //Button
        btn_Refresh.setEnabled(false);
        btn_Save.setEnabled(true);
        btn_state.setEnabled(true);
        //Textfield
        txt_Address.setEditable(true);
        txt_Email.setEditable(true);
        txt_Fullname.setEditable(true);
        txt_Password.setEditable(true);
        txt_Phonenum.setEditable(true);
        txt_Username.setEditable(true);
        
        showUser();
    }
    
    private void showUser() {
        lbl_UserID.setText(Utils.selectedProduct.getId());
        txt_Username.setText(Utils.selectedUser.getUsername());
        txt_Address.setText(Utils.selectedUser.getAddress());
        txt_Email.setText(Utils.selectedUser.getEmail() + "");
        txt_Fullname.setText(Utils.selectedUser.getFullname());
        lbl_innidate.setText(Utils.selectedUser.getInitdate().format().split(" ")[0]);
        txt_Password.setText(Utils.selectedUser.getPassword());
        txt_Phonenum.setText(Utils.selectedUser.getPhone());
        avatar = Utils.selectedUser.getAvatar();
        lbl_Avatar.setIcon(resizeImg(avatar));
        
        if(Utils.selectedUser.getStatus() == 1){
            lbl_status.setText("Đang hoạt động");
            lbl_status.setForeground(Color.green);
            btn_state.setBackground(Color.red);
        }
        else{
            lbl_status.setText("Ngừng hoạt động");
            lbl_status.setForeground(Color.red);
            btn_state.setBackground(Color.green);
        }  
    }
    
    //Clear
    private void clear() {
        lbl_UserID.setText("");
        lbl_UserID.setText("");
        txt_Username.setText("");
        txt_Address.setText("");
        txt_Email.setText("");
        txt_Fullname.setText("");
        lbl_innidate.setText("");
        txt_Password.setText("");
        txt_Phonenum.setText("");
        avatar = "C:\\xampp\\htdocs\\API_PhoneShop\\avatar\\default.png";
        lbl_Avatar.setIcon(resizeImg(avatar));
        lbl_status.setText("Đang hoạt động");
        lbl_status.setForeground(Color.green);
    }
    
    private String getExtensionFile(File file){
        String fileName = file.getName();
        int index = fileName.lastIndexOf('.');
        if (index > 0) {
          String extension = fileName.substring(index+1);
          return extension;
        }
        return "";
    }
    
    private void insertImages() {
        if (cbx_ChangeMode.getSelectedIndex() != 1) {
            if (JOptionPane.showConfirmDialog(this, "Chọn file ảnh để làm avatar") == JOptionPane.YES_OPTION) {
                JFileChooser filechooser = new JFileChooser();
                filechooser.setCurrentDirectory(Utils.currentPath);
//                    filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int option = filechooser.showOpenDialog(this);
                if (option == filechooser.APPROVE_OPTION) {
                    File newFile = filechooser.getSelectedFile();
                    avatar = newFile + "";
                    lbl_Avatar.setIcon(resizeImg(avatar));
                }
            }
        }  
    }
    
    public ImageIcon resizeImg(String imgpath) {
        ImageIcon imgicon = new ImageIcon(imgpath);
        Image img = imgicon.getImage();
        Image newimg = img.getScaledInstance(lbl_Avatar.getWidth(), lbl_Avatar.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon reimg = new ImageIcon(newimg);
        return reimg;
    }

    private String reloadImage(DTO_User img, String path) {
        File newFile = new File(path);
        try {
            FileUtils.copyFile(newFile, new File("C:\\xampp\\htdocs\\API_PhoneShop\\avatar\\" + img.getId() + "." + getExtensionFile(newFile)));
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return "C:\\xampp\\htdocs\\API_PhoneShop\\avatar\\" + img.getId() + "." + getExtensionFile(newFile);
    }
    ///////////////
    
    //Insert
    private void createID() {
        if(txt_Username.getText().toString().equals("") || txt_Fullname.getText().toString().equals("") || txt_Email.getText().toString().equals("") || txt_Password.getText().toString().equals("") 
                || txt_Phonenum.getText().toString().equals("") || txt_Address.getText().toString().equals("") || GUI_tableUser.currMode != Utils.ADD_MODE) {
            return;
        }
        DTO_User newUser = new DTO_User();
        newUser.setId(bus_user.getTotal() + 1);
        lbl_UserID.setText(newUser.getId());
        
        LocalDateTime today = LocalDateTime.now();
        innidate = new DTO_Date(today.getYear(), today.getMonthValue(), today.getDayOfMonth(), today.getHour(), today.getMinute(), today.getSecond());
        lbl_innidate.setText(innidate.format().split(" ")[0]);
    }
    /////////////////////
    
    public static void main(String args[]) {
        FlatRobotoFont.install();
        FlatLaf.registerCustomDefaultsSource("crazypanel");
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
        //FlatMacLightLaf.setup();
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI_formUser().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Refresh;
    private javax.swing.JButton btn_Save;
    private javax.swing.JToggleButton btn_state;
    private javax.swing.JComboBox<String> cbx_ChangeMode;
    private javax.swing.JComboBox<String> cbx_Role;
    private raven.crazypanel.CrazyPanel crazyPanel_Detail;
    private raven.crazypanel.CrazyPanel crazyPanel_Main;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel lbl_Avatar;
    private javax.swing.JLabel lbl_InniDate;
    private javax.swing.JLabel lbl_OrderPrice;
    private javax.swing.JLabel lbl_UserID;
    private javax.swing.JLabel lbl_chooseAvatar;
    private javax.swing.JLabel lbl_deliDate;
    private javax.swing.JLabel lbl_deliDate1;
    private javax.swing.JLabel lbl_deliDate2;
    private javax.swing.JLabel lbl_innidate;
    private javax.swing.JLabel lbl_status;
    private javax.swing.JTextField txt_Address;
    private javax.swing.JTextField txt_Email;
    private javax.swing.JTextField txt_Fullname;
    private javax.swing.JPasswordField txt_Password;
    private javax.swing.JTextField txt_Phonenum;
    private javax.swing.JTextField txt_Username;
    // End of variables declaration//GEN-END:variables
}
