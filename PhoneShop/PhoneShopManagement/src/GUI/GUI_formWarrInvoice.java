package GUI;

import BUS.BUS_Color;
import BUS.BUS_Option;
import BUS.BUS_Ram;
import BUS.BUS_Rom;
import BUS.BUS_WarrantyInvoice;
import Custom.ColorIcon;
import DTO.DTO_Date;
import DTO.DTO_WarrantyInvoice;
import static GUI.GUI_tableProduct.formProduct;
import static GUI.GUI_tableUser.formUser;
import Utils.Utils;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;
import javax.swing.UIManager;
import raven.toast.Notifications;


public class GUI_formWarrInvoice extends javax.swing.JFrame {
    private Notifications notifications = new Notifications();
    private DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
    
    private BUS_WarrantyInvoice bus = new BUS_WarrantyInvoice();
    private BUS_Color bus_color = new BUS_Color();
    private BUS_Rom bus_rom = new BUS_Rom();
    private BUS_Ram bus_ram = new BUS_Ram();
    private BUS_Option bus_option = new BUS_Option();

    public GUI_formWarrInvoice() {
        initComponents();
        notifications.getInstance().setJFrame(this);
        //Khởi tạo ban đầu
        setResizable(false);
        setLocationRelativeTo(null);
        
        switch (GUI_tableWarranty.currMode) {
            case Utils.ADD_MODE:
                setAddMode();
                break;
            case Utils.VIEW_MODE:
                setViewMode();
                break;
        }
        btn_refresh.setIcon(new FlatSVGIcon("assets/refresh.svg", 0.25f));
        btn_Save.setIcon(new FlatSVGIcon("assets/save.svg", 0.3f));
    }
     
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        crazyPanel_Main = new raven.crazypanel.CrazyPanel();
        btn_refresh = new javax.swing.JButton();
        crazyPanel_Detail = new raven.crazypanel.CrazyPanel();
        lbl_OrderID = new javax.swing.JLabel();
        btn_Save = new javax.swing.JButton();
        lbl_InvoiceID_data = new javax.swing.JLabel();
        lbl_InniDate_data = new javax.swing.JLabel();
        lbl_InniDate = new javax.swing.JLabel();
        lbl_ProductID_data = new javax.swing.JLabel();
        lbl_OrderPrice = new javax.swing.JLabel();
        lbl_Color_data = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        lbl_FullName_data = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        lbl_Rom_data = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        lbl_Email_data = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        lbl_Phone_data = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txt_Expense = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_Describe = new javax.swing.JTextArea();
        jLabel26 = new javax.swing.JLabel();
        lbl_showUser = new javax.swing.JLabel();
        lbl_showProduct = new javax.swing.JLabel();
        lbl_Ram_data = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        lbl_remainTime = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        addWindowListener(new java.awt.event.WindowAdapter() {
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

        btn_refresh.setText("Làm mới");
        btn_refresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshActionPerformed(evt);
            }
        });

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

        lbl_OrderID.setText("Mã hóa đơn");

        btn_Save.setText("Lưu");
        btn_Save.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SaveActionPerformed(evt);
            }
        });

        lbl_InvoiceID_data.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        lbl_InniDate_data.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        lbl_InniDate.setText("Ngày tạo");

        lbl_ProductID_data.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        lbl_OrderPrice.setText("Mã sản phẩm");

        lbl_Color_data.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbl_Color_data.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel19.setText("Màu");

        lbl_FullName_data.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel20.setText("Họ tên");

        lbl_Rom_data.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel24.setText("Bộ nhớ trong");

        lbl_Email_data.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel25.setText("Email");

        lbl_Phone_data.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel21.setText("Số điện thoại");

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel33.setText("VNĐ");

        jLabel23.setText("Chi phí");

        txt_Expense.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ExpenseActionPerformed(evt);
            }
        });
        txt_Expense.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_ExpenseKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_ExpenseKeyTyped(evt);
            }
        });

        txt_Describe.setColumns(20);
        txt_Describe.setRows(5);
        txt_Describe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_DescribeKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(txt_Describe);

        jLabel26.setText("Mô tả lỗi");

        lbl_showUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-eye-40.png"))); // NOI18N
        lbl_showUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_showUserMouseClicked(evt);
            }
        });

        lbl_showProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-eye-40.png"))); // NOI18N
        lbl_showProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_showProductMouseClicked(evt);
            }
        });

        lbl_Ram_data.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel27.setText("Bộ nhớ đệm");

        lbl_remainTime.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        javax.swing.GroupLayout crazyPanel_DetailLayout = new javax.swing.GroupLayout(crazyPanel_Detail);
        crazyPanel_Detail.setLayout(crazyPanel_DetailLayout);
        crazyPanel_DetailLayout.setHorizontalGroup(
            crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crazyPanel_DetailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(crazyPanel_DetailLayout.createSequentialGroup()
                        .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(crazyPanel_DetailLayout.createSequentialGroup()
                                    .addComponent(lbl_OrderPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(lbl_ProductID_data, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(crazyPanel_DetailLayout.createSequentialGroup()
                                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(lbl_Color_data, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(crazyPanel_DetailLayout.createSequentialGroup()
                                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl_Rom_data, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_showProduct))
                    .addGroup(crazyPanel_DetailLayout.createSequentialGroup()
                        .addComponent(lbl_OrderID, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_InvoiceID_data, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(crazyPanel_DetailLayout.createSequentialGroup()
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_Ram_data, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(crazyPanel_DetailLayout.createSequentialGroup()
                        .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(crazyPanel_DetailLayout.createSequentialGroup()
                                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl_Email_data, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(crazyPanel_DetailLayout.createSequentialGroup()
                                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl_FullName_data, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(lbl_showUser))
                    .addGroup(crazyPanel_DetailLayout.createSequentialGroup()
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_Phone_data, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(crazyPanel_DetailLayout.createSequentialGroup()
                        .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(crazyPanel_DetailLayout.createSequentialGroup()
                                .addComponent(txt_Expense, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel33))))
                    .addGroup(crazyPanel_DetailLayout.createSequentialGroup()
                        .addComponent(lbl_InniDate, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_InniDate_data, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_remainTime, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, crazyPanel_DetailLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_Save, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(130, 130, 130))
        );
        crazyPanel_DetailLayout.setVerticalGroup(
            crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crazyPanel_DetailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_OrderID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_InvoiceID_data, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_InniDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_InniDate_data, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_remainTime, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_OrderPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_ProductID_data, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_showProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(lbl_Color_data, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_Rom_data, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_Ram_data, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_Email_data, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_FullName_data, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_showUser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_Phone_data, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_Expense, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btn_Save, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Thông tin hóa đơn bảo hành");

        javax.swing.GroupLayout crazyPanel_MainLayout = new javax.swing.GroupLayout(crazyPanel_Main);
        crazyPanel_Main.setLayout(crazyPanel_MainLayout);
        crazyPanel_MainLayout.setHorizontalGroup(
            crazyPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crazyPanel_MainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(crazyPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(crazyPanel_MainLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(crazyPanel_MainLayout.createSequentialGroup()
                        .addGroup(crazyPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(crazyPanel_Detail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(crazyPanel_MainLayout.createSequentialGroup()
                                .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        crazyPanel_MainLayout.setVerticalGroup(
            crazyPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crazyPanel_MainLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(crazyPanel_Detail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(crazyPanel_Main, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(crazyPanel_Main, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SaveActionPerformed
        insertInvoice();
        showInvoice();
        GUI_tableProduct.currMode = Utils.VIEW_MODE;
        setViewMode();
    }//GEN-LAST:event_btn_SaveActionPerformed

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        clear();
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void txt_ExpenseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ExpenseActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_ExpenseActionPerformed

    private void txt_ExpenseKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_ExpenseKeyReleased
        //createID();
    }//GEN-LAST:event_txt_ExpenseKeyReleased

    private void txt_ExpenseKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_ExpenseKeyTyped
        char c = evt.getKeyChar();

        if (Character.isAlphabetic(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_txt_ExpenseKeyTyped

    private void txt_DescribeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_DescribeKeyReleased
        //createID();
    }//GEN-LAST:event_txt_DescribeKeyReleased

    private void lbl_showUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_showUserMouseClicked
        if (formUser != null) {
            notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Bạn đã mở cửa sổ này rồi!!!");
            return;
        }
        Utils.selectedUser = Utils.selectedWarranty.getUser();
        GUI_tableUser.currMode = Utils.VIEW_MODE;
        formUser = new GUI_formUser();
        formUser.setVisible(true);
    }//GEN-LAST:event_lbl_showUserMouseClicked

    private void lbl_showProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_showProductMouseClicked
        if (formProduct != null) {
            notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Bạn đã mở cửa sổ này rồi!!!");
            return;
        }
        Utils.selectedProduct = Utils.selectedWarranty.getProduct();
        Utils.selectedProduct.setColors(bus_color.getListByProductID());
        Utils.selectedProduct.setRoms(bus_rom.getList());
        Utils.selectedProduct.setRams(bus_ram.getList());
        Utils.selectedProduct.setOptions(bus_option.getList());
        GUI_tableProduct.currMode = Utils.VIEW_MODE;
        formProduct = new GUI_formProduct();
        formProduct.setVisible(true);
    }//GEN-LAST:event_lbl_showProductMouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        GUI_tableWarranty.formWarrInvoice = null;
    }//GEN-LAST:event_formWindowClosing
    
    //Mode
    private void setViewMode() {
        GUI_tableProduct.currMode = Utils.VIEW_MODE;
        //TextField
        txt_Expense.setEditable(false);
        txt_Describe.setEditable(false);
        //Button
        btn_refresh.setEnabled(false);
        btn_Save.setEnabled(false);
        
        showInvoice();
    }
    
    private void setAddMode() {
        GUI_tableProduct.currMode = Utils.ADD_MODE;
        clear();
        //TextField
        txt_Expense.setEditable(true);
        txt_Describe.setEditable(true);
        //Button
        btn_refresh.setEnabled(true);
        btn_Save.setEnabled(true);
        
        showInvoice();
    }
    
    //Insert
    private void createID() {
        if(txt_Expense.getText().toString().equals("") || txt_Describe.getText().toString().equals("") 
                || GUI_tableWarranty.currMode != Utils.ADD_MODE) {
            return;
        }
        DTO_WarrantyInvoice newInvoice = new DTO_WarrantyInvoice();
        newInvoice.setId(bus.getTotal() + 1);
        lbl_InvoiceID_data.setText(newInvoice.getId());
    }
    
    private void insertInvoice() {
        if (lbl_InvoiceID_data.getText().isEmpty()) {
            notifications.getInstance().show(Notifications.Type.INFO,
                    Notifications.Location.TOP_CENTER, "Không được để trống thông tin !!!");
            return;
        }
        String id = lbl_InvoiceID_data.getText().toString();
        String [] initdateString = lbl_InniDate_data.getText().toString().split(" ")[0].split("/");
        String [] initimeString = lbl_InniDate_data.getText().toString().split(" ")[1].split(":");
        DTO_Date innidate = new DTO_Date(Integer.parseInt(initdateString[2]), Integer.parseInt(initdateString[1]), Integer.parseInt(initdateString[0]), Integer.parseInt(initimeString[0]), Integer.parseInt(initimeString[1]), Integer.parseInt(initimeString[2]));
        
        double expense = 0;
        if (!txt_Expense.getText().toString().equals("")) {
            expense = Double.parseDouble(txt_Expense.getText().toString());
        }
        String describe = "";
        if (!txt_Describe.getText().toString().equals("")) {
            describe = txt_Describe.getText().toString();
        }
        
        DTO_WarrantyInvoice newInvoice = new DTO_WarrantyInvoice(id, Utils.selectedWarranty.getId(), describe, innidate, expense);
        
        int check = bus.insert(newInvoice);
        if (check != -1) {
            notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Thêm hóa đơn bảo hành thành công!!!");
            Utils.selectedWarrantyInvoice = newInvoice;
            clear();
        }
        else {
            notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Thêm hóa đơn bảo hành thất bại!!!");
        }
    }
    
    private void showInvoice() {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime warrInitdate = LocalDateTime.parse(Utils.selectedWarranty.getInitdate().toString()
                , DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        double remaintime = Utils.selectedWarranty.getProduct().getWarranty() - 
                ChronoUnit.MONTHS.between(warrInitdate, today);
        if (GUI_tableWarranty.currMode == Utils.ADD_MODE) {
            DTO_WarrantyInvoice newInvoice = new DTO_WarrantyInvoice();
            newInvoice.setId(bus.getTotal() + 1);
            lbl_InvoiceID_data.setText(newInvoice.getId());
            DTO_Date initdate = new DTO_Date(today.getYear(), today.getMonthValue(), today.getDayOfMonth()
                    , today.getHour(), today.getMinute(), today.getSecond());
            lbl_InniDate_data.setText(initdate.format());
        }
        else {
            lbl_InvoiceID_data.setText(Utils.selectedWarrantyInvoice.getId());
            lbl_InniDate_data.setText(Utils.selectedWarrantyInvoice.getInitdate().format());
        }
        if (remaintime >= 0) {
            lbl_remainTime.setText("Còn lại: " + remaintime + " tháng");
            lbl_remainTime.setForeground(new Color(17, 182, 60));
        }
        else {
            lbl_remainTime.setText("Hết bảo hành");
            lbl_remainTime.setForeground(new Color(202, 48, 48));
        }
        lbl_ProductID_data.setText(Utils.selectedWarranty.getOption().getProductid());
        int[] rgbTemp = Utils.selectedWarranty.getOption().getColor().getRgb();
        int[] rgb = new int[] {rgbTemp[0], rgbTemp[1], rgbTemp[2]};
        lbl_Color_data.setIcon(new ColorIcon(60, 30, rgb));
        String romData = Utils.selectedWarranty.getOption().getRom() + " GB";
        if (Utils.selectedWarranty.getOption().getRom() >= 1000) {
            romData = (Utils.selectedWarranty.getOption().getRom() / 1000) + " T";
        }
        String ramData = Utils.selectedWarranty.getOption().getRam() + " GB";
        if (Utils.selectedWarranty.getOption().getRam() >= 1000) {
            ramData = (Utils.selectedWarranty.getOption().getRam() / 1000) + " T";
        }
        lbl_Rom_data.setText(romData);
        lbl_Ram_data.setText(ramData);
        lbl_Email_data.setText(Utils.selectedWarranty.getUser().getEmail());
        lbl_FullName_data.setText(Utils.selectedWarranty.getUser().getFullname());
        lbl_Phone_data.setText(Utils.selectedWarranty.getUser().getPhone());
        
        if (GUI_tableWarranty.currMode == Utils.VIEW_MODE) {
            txt_Expense.setText(decimalFormat.format(Utils.selectedWarrantyInvoice.getExpense()) + "");
            txt_Describe.setText(Utils.selectedWarrantyInvoice.getDescribe());  
        }
    }
    /////////
    
    //Clear
    private void clear() {
        lbl_InvoiceID_data.setText("");
        txt_Expense.setText("");
        txt_Describe.setText("");
    }
    
    public static void main(String args[]) {
        FlatRobotoFont.install();
        FlatLaf.registerCustomDefaultsSource("crazypanel");
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
        //FlatMacLightLaf.setup();
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI_formWarrInvoice().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Save;
    private javax.swing.JButton btn_refresh;
    private raven.crazypanel.CrazyPanel crazyPanel_Detail;
    private raven.crazypanel.CrazyPanel crazyPanel_Main;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_Color_data;
    private javax.swing.JLabel lbl_Email_data;
    private javax.swing.JLabel lbl_FullName_data;
    private javax.swing.JLabel lbl_InniDate;
    private javax.swing.JLabel lbl_InniDate_data;
    private javax.swing.JLabel lbl_InvoiceID_data;
    private javax.swing.JLabel lbl_OrderID;
    private javax.swing.JLabel lbl_OrderPrice;
    private javax.swing.JLabel lbl_Phone_data;
    private javax.swing.JLabel lbl_ProductID_data;
    private javax.swing.JLabel lbl_Ram_data;
    private javax.swing.JLabel lbl_Rom_data;
    private javax.swing.JLabel lbl_remainTime;
    private javax.swing.JLabel lbl_showProduct;
    private javax.swing.JLabel lbl_showUser;
    private javax.swing.JTextArea txt_Describe;
    private javax.swing.JTextField txt_Expense;
    // End of variables declaration//GEN-END:variables
}
