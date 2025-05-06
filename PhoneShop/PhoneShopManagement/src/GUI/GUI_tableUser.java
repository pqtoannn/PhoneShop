package GUI;

import static App.Application.notifications;
import BUS.BUS_User;
import Custom.ShowInfoCellEditor;
import Custom.ShowInfoCellRender;
import Custom.datechooser.DateBetween;
import Custom.datechooser.DateChooser;
import Custom.datechooser.listener.DateChooserAction;
import Custom.datechooser.listener.DateChooserAdapter;
import DTO.DTO_Date;
import DTO.DTO_User;
import Interface.TableActionEvent;
import Utils.Utils;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import raven.toast.Notifications;


public class GUI_tableUser extends javax.swing.JPanel {
    private ImageIcon asc = new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-thick-arrow-pointing-up-30.png"));
    private ImageIcon desc = new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-thick-arrow-pointing-down-30.png"));
    
    private DefaultTableModel adminModel;
    private DefaultTableModel customerModel;
    
    private int selectedItem = 0;
    private boolean sortMode = true;
    public static int currMode = Utils.VIEW_MODE;
    
    private BUS_User bus_user = new BUS_User();
    
    private ArrayList <DTO_User> list = new ArrayList <DTO_User> ();
    public static GUI_formUser formUser;
    
    private int mouseClick = -1;
    private int mouseClick1 = -1;
    
    public GUI_tableUser() {
        initComponents();

        adminModel = (DefaultTableModel) tbl_Admin.getModel();
        customerModel = (DefaultTableModel) tbl_Customer.getModel();
        
        clear();
        btn_Add.setIcon(new FlatSVGIcon("assets/add.svg", 0.35f));
        btn_refresh.setIcon(new FlatSVGIcon("assets/refresh.svg", 0.25f));
        btn_Block.setIcon(new FlatSVGIcon("assets/lock.svg", 0.3f));
        txt_Search.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new FlatSVGIcon("assets/search.svg", 0.35f));
        lbl_search.setIcon(new FlatSVGIcon("assets/search.svg", 0.5f));
        
        //CUSTOMCODE ĐỪNG ĐỤNG VÔ!!!
        applyTableStyleTable(tbl_Customer);
        applyTableStyleTable(tbl_Admin);
        /////////
    }
    
    //CUSTOMCODE ĐỪNG ĐỤNG VÔ!!!
    private void applyTableStyleTable(JTable table) {
        //  Change scroll style
        JScrollPane scroll = (JScrollPane) table.getParent().getParent();
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Table.background;"
                + "track:$Table.background;"
                + "trackArc:999");

        table.getTableHeader().putClientProperty(FlatClientProperties.STYLE_CLASS, "table_style");
        table.putClientProperty(FlatClientProperties.STYLE_CLASS, "table_style");

        //To Create table alignment
        table.getTableHeader().setDefaultRenderer(getAlignmentCellRenderTable(table.getTableHeader().getDefaultRenderer(), true));
        table.setDefaultRenderer(Object.class, getAlignmentCellRenderTable(table.getDefaultRenderer(Object.class), false));
        
        table.getColumnModel().getColumn(9).setCellEditor(new ShowInfoCellEditor(new TableActionEvent() {
            @Override
            public void onView(int row) {
                if(formUser != null){
                    notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Bạn đã mở cửa sổ này rồi!!!");
                    return;
                }
                switch (jtab_main.getSelectedIndex()) {
                    case 0:
                        if (tbl_Customer.getSelectedRowCount() == 0) {
                            return;
                        }
                        list = bus_user.getList(0);
                        Utils.selectedUser = list.get(tbl_Customer.getSelectedRow());
                        break;
                    case 1:
                        if (tbl_Admin.getSelectedRowCount() == 0) {
                            return;
                        }
                        list = bus_user.getList(1);
                        Utils.selectedUser = list.get(tbl_Admin.getSelectedRow());
                        break;
                }
                currMode = Utils.VIEW_MODE;
                formUser = new GUI_formUser();
                formUser.setVisible(true);
            }
                
        }));
        table.getColumnModel().getColumn(9).setCellRenderer(new ShowInfoCellRender());
    }
    
    private TableCellRenderer getAlignmentCellRenderTable(TableCellRenderer oldRender, boolean header) {
        return new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component com = oldRender.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (com instanceof JLabel) {
                    JLabel label = (JLabel) com;
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    
                    if (header == false) {
                        switch (column) {
                            case 8:
                                if (value.toString().equals("Đang hoạt động")) {
                                    com.setForeground(new Color(17, 182, 60));
                                }
                                else {
                                    com.setForeground(new Color(202, 48, 48));
                                }
                                break;
                            default:
                                if (isSelected) {
                                    com.setForeground(table.getSelectionForeground());
                                } 
                                else {
                                    com.setForeground(table.getForeground());
                                }
                        }
                        
                    }
                }
                return com;
            }
        };
    }
 
    //////////////////////////////////
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        crazyPanel_Parent = new raven.crazypanel.CrazyPanel();
        jtab_main = new javax.swing.JTabbedPane();
        crazyPanel_Main = new raven.crazypanel.CrazyPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_Customer = new javax.swing.JTable();
        crazyPanel_Queue = new raven.crazypanel.CrazyPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_Admin = new javax.swing.JTable();
        crazyPanel_Menu = new raven.crazypanel.CrazyPanel();
        cbx_Search = new javax.swing.JComboBox<>();
        txt_Search = new javax.swing.JTextField();
        crazyPanel_SearchByPrice = new raven.crazypanel.CrazyPanel();
        txt_fromPrice = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txt_toPrice = new javax.swing.JTextField();
        lbl_SortMode = new javax.swing.JLabel();
        cbx_Sort = new javax.swing.JComboBox<>();
        crazyPanel_SelectAll = new raven.crazypanel.CrazyPanel();
        ckb_SelectAll = new javax.swing.JCheckBox();
        lbl_ItemSelected = new javax.swing.JLabel();
        crazyPanel_MenuBtn = new raven.crazypanel.CrazyPanel();
        btn_Add = new javax.swing.JButton();
        btn_Block = new javax.swing.JButton();
        btn_refresh = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lbl_search = new javax.swing.JLabel();
        txt_Date = new javax.swing.JTextField();

        crazyPanel_Parent.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
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

        jtab_main.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jtab_main.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jtab_mainStateChanged(evt);
            }
        });
        jtab_main.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtab_mainMouseClicked(evt);
            }
        });

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

        tbl_Customer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Mã người dùng", "Tên người dùng", "Họ tên", "Email", "Số điện thoại", "Địa chỉ", "Ngày tạo", "Trạng thái", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_Customer.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                tbl_CustomerMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                tbl_CustomerMouseMoved(evt);
            }
        });
        tbl_Customer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_CustomerMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tbl_CustomerMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                tbl_CustomerMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbl_CustomerMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbl_CustomerMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_Customer);
        if (tbl_Customer.getColumnModel().getColumnCount() > 0) {
            tbl_Customer.getColumnModel().getColumn(0).setPreferredWidth(20);
            tbl_Customer.getColumnModel().getColumn(9).setResizable(false);
            tbl_Customer.getColumnModel().getColumn(9).setPreferredWidth(30);
        }

        javax.swing.GroupLayout crazyPanel_MainLayout = new javax.swing.GroupLayout(crazyPanel_Main);
        crazyPanel_Main.setLayout(crazyPanel_MainLayout);
        crazyPanel_MainLayout.setHorizontalGroup(
            crazyPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crazyPanel_MainLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1106, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
        crazyPanel_MainLayout.setVerticalGroup(
            crazyPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, crazyPanel_MainLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE)
                .addGap(16, 16, 16))
        );

        jtab_main.addTab("Khách hàng", crazyPanel_Main);

        crazyPanel_Queue.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
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

        tbl_Admin.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Mã người dùng", "Tên người dùng", "Họ tên", "Email", "Số điện thoại", "Địa chỉ", "Ngày tạo", "Trạng thái", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_Admin.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                tbl_AdminMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                tbl_AdminMouseMoved(evt);
            }
        });
        tbl_Admin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_AdminMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tbl_AdminMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                tbl_AdminMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbl_AdminMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbl_AdminMouseReleased(evt);
            }
        });
        jScrollPane4.setViewportView(tbl_Admin);
        if (tbl_Admin.getColumnModel().getColumnCount() > 0) {
            tbl_Admin.getColumnModel().getColumn(0).setPreferredWidth(20);
            tbl_Admin.getColumnModel().getColumn(9).setResizable(false);
            tbl_Admin.getColumnModel().getColumn(9).setPreferredWidth(30);
        }

        javax.swing.GroupLayout crazyPanel_QueueLayout = new javax.swing.GroupLayout(crazyPanel_Queue);
        crazyPanel_Queue.setLayout(crazyPanel_QueueLayout);
        crazyPanel_QueueLayout.setHorizontalGroup(
            crazyPanel_QueueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crazyPanel_QueueLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1106, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
        crazyPanel_QueueLayout.setVerticalGroup(
            crazyPanel_QueueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, crazyPanel_QueueLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE)
                .addGap(16, 16, 16))
        );

        jtab_main.addTab("Admin", crazyPanel_Queue);

        javax.swing.GroupLayout crazyPanel_ParentLayout = new javax.swing.GroupLayout(crazyPanel_Parent);
        crazyPanel_Parent.setLayout(crazyPanel_ParentLayout);
        crazyPanel_ParentLayout.setHorizontalGroup(
            crazyPanel_ParentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crazyPanel_ParentLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jtab_main)
                .addGap(14, 14, 14))
        );
        crazyPanel_ParentLayout.setVerticalGroup(
            crazyPanel_ParentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jtab_main)
        );

        crazyPanel_Menu.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
            "",
            new String[]{
                "",
                "showClearButton:true;JTextField.placeholderText=Tìm kiếm",
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
        crazyPanel_Menu.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
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

        cbx_Search.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tìm kiếm", "Mã người dùng", "Tên người dùng", "Họ tên", "Email", "Số điện thoại", "Ngày tạo" }));
        cbx_Search.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbx_SearchItemStateChanged(evt);
            }
        });

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
        crazyPanel_SearchByPrice.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        crazyPanel_SearchByPrice.add(txt_fromPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 120, 30));

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("To");
        crazyPanel_SearchByPrice.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 20, 30));
        crazyPanel_SearchByPrice.add(txt_toPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 120, 30));

        lbl_SortMode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-thick-arrow-pointing-up-30.png"))); // NOI18N
        lbl_SortMode.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_SortMode.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_SortModeMouseClicked(evt);
            }
        });

        cbx_Sort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sắp xếp", "Mã người dùng", "Tên người dùng", "Họ tên", "Email", "Số điện thoại", "Ngày tạo" }));
        cbx_Sort.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbx_SortItemStateChanged(evt);
            }
        });

        crazyPanel_SelectAll.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
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
        crazyPanel_SelectAll.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
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
        crazyPanel_SelectAll.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ckb_SelectAll.setText("Chọn tất cả");
        ckb_SelectAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckb_SelectAllActionPerformed(evt);
            }
        });
        crazyPanel_SelectAll.add(ckb_SelectAll, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 10, 90, -1));

        lbl_ItemSelected.setForeground(new java.awt.Color(255, 0, 51));
        lbl_ItemSelected.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        crazyPanel_SelectAll.add(lbl_ItemSelected, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 100, 26));

        crazyPanel_MenuBtn.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
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
        crazyPanel_MenuBtn.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
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
        crazyPanel_MenuBtn.setLayout(new java.awt.FlowLayout());

        btn_Add.setText("Thêm");
        btn_Add.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AddActionPerformed(evt);
            }
        });
        crazyPanel_MenuBtn.add(btn_Add);

        btn_Block.setText("Khóa tài khoản");
        btn_Block.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_Block.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_BlockActionPerformed(evt);
            }
        });
        crazyPanel_MenuBtn.add(btn_Block);

        btn_refresh.setText("Làm mới");
        btn_refresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshActionPerformed(evt);
            }
        });
        crazyPanel_MenuBtn.add(btn_refresh);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Quản lý tài khoản");

        lbl_search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-thick-arrow-pointing-up-30.png"))); // NOI18N
        lbl_search.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_search.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_searchlbl_SortModeMouseClicked(evt);
            }
        });

        txt_Date.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_DateFocusLost(evt);
            }
        });

        javax.swing.GroupLayout crazyPanel_MenuLayout = new javax.swing.GroupLayout(crazyPanel_Menu);
        crazyPanel_Menu.setLayout(crazyPanel_MenuLayout);
        crazyPanel_MenuLayout.setHorizontalGroup(
            crazyPanel_MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crazyPanel_MenuLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(crazyPanel_MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(crazyPanel_MenuLayout.createSequentialGroup()
                        .addComponent(cbx_Search, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_Search, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_Date, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(crazyPanel_SearchByPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_search)
                        .addGap(12, 12, 12)
                        .addComponent(cbx_Sort, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbl_SortMode)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(crazyPanel_SelectAll, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(crazyPanel_MenuLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(crazyPanel_MenuBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        crazyPanel_MenuLayout.setVerticalGroup(
            crazyPanel_MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, crazyPanel_MenuLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(crazyPanel_MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(crazyPanel_MenuBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(28, 28, 28)
                .addGroup(crazyPanel_MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(crazyPanel_SelectAll, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(crazyPanel_MenuLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(crazyPanel_MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_SortMode)
                            .addComponent(cbx_Sort, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_search)))
                    .addGroup(crazyPanel_MenuLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(crazyPanel_SearchByPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(crazyPanel_MenuLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(crazyPanel_MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_Search, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbx_Search, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_Date, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(crazyPanel_Parent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(crazyPanel_Menu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(crazyPanel_Menu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(crazyPanel_Parent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tbl_CustomerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_CustomerMouseClicked
        onClickTable(tbl_Customer, customerModel);
        setSelectedAll(customerModel, tbl_Customer);
    }//GEN-LAST:event_tbl_CustomerMouseClicked

    private void jtab_mainMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtab_mainMouseClicked
        if (jtab_main.getSelectedIndex() == 1) {
//            selectedItem = countRow(adminModel, tbl_Admin);
//            if (adminModel.getRowCount() != 0 && selectedItem == adminModel.getRowCount()) {
//                ckb_SelectAll.setSelected(true);
//            } else {
//                ckb_SelectAll.setSelected(false);
//            }
            list = bus_user.getList(1);
            renderTable(list, adminModel);
        } else {
//            selectedItem = countRow(customerModel, tbl_Customer);
//            if (customerModel.getRowCount() != 0 && selectedItem == customerModel.getRowCount()) {
//                ckb_SelectAll.setSelected(true);
//            } else {
//                ckb_SelectAll.setSelected(false);
//            }
            list = bus_user.getList(0);
            renderTable(list, customerModel);
        }
        ckb_SelectAll.setSelected(false);
        updateItemSelected();
    }//GEN-LAST:event_jtab_mainMouseClicked

    private void cbx_SearchItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbx_SearchItemStateChanged
        if(evt.getStateChange() == 1) {
            int choice = cbx_Search.getSelectedIndex();
            switch (choice) {
                case 6:
                    txt_Search.setVisible(false);
                    crazyPanel_SearchByPrice.setVisible(false);
                    txt_Date.setVisible(true);

                    DateChooser chooser = new DateChooser();
                    chooser.setTextField(txt_Date);
                    chooser.setDateSelectionMode(DateChooser.DateSelectionMode.BETWEEN_DATE_SELECTED);
                    chooser.setLabelCurrentDayVisible(false);
                    chooser.setDateFormat(new SimpleDateFormat("dd/MM/YYYY"));
                    chooser.addActionDateChooserListener(new DateChooserAdapter() {
                        @Override
                        public void dateBetweenChanged(DateBetween date, DateChooserAction action) {
                            SimpleDateFormat df = new SimpleDateFormat("dd/MM/YYYY");
                            String dateFrom = df.format(date.getFromDate());
                            String toDate = df.format(date.getToDate());
                        }
                    });
                    break;
                default:
                    txt_Search.setVisible(true);
                    crazyPanel_SearchByPrice.setVisible(false);
                    txt_Date.setVisible(false);
            }
        }
    }//GEN-LAST:event_cbx_SearchItemStateChanged

    private void lbl_SortModeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_SortModeMouseClicked
        if (!sortMode) {
            sortMode = true;
            lbl_SortMode.setIcon(asc);
            selectModelSortASC();
        } else {
            sortMode = false;
            lbl_SortMode.setIcon(desc);
            selectModelSortDESC();
        }
    }//GEN-LAST:event_lbl_SortModeMouseClicked

    private void cbx_SortItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbx_SortItemStateChanged
        if(evt.getStateChange() == 1) {
            selectModelSortASC();
            lbl_SortMode.setIcon(asc);
        }
    }//GEN-LAST:event_cbx_SortItemStateChanged

    private void ckb_SelectAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckb_SelectAllActionPerformed
        switch (jtab_main.getSelectedIndex()) {
            case 0:
            setTableCheckbox(adminModel, tbl_Customer);
            break;
            case 1:
            setTableCheckbox(customerModel, tbl_Admin);
            break;
            default:
            throw new AssertionError();
        }
    }//GEN-LAST:event_ckb_SelectAllActionPerformed

    private void btn_AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AddActionPerformed
        currMode = Utils.ADD_MODE;
        new GUI_formUser().setVisible(true);
    }//GEN-LAST:event_btn_AddActionPerformed

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        clear();
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void tbl_CustomerMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_CustomerMouseReleased
        onReleaseTable(tbl_Customer, evt, mouseClick);
        setSelectedAll(customerModel, tbl_Customer);
    }//GEN-LAST:event_tbl_CustomerMouseReleased

    private void jtab_mainStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jtab_mainStateChanged

    }//GEN-LAST:event_jtab_mainStateChanged

    private void btn_BlockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_BlockActionPerformed
        if (selectedItem <= 0) {
            return;
        } 
        else {
            if (JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn đổi quyền cho các user này????") == JOptionPane.YES_OPTION) {
                if (jtab_main.getSelectedIndex() == 0) {
                    int total = tbl_Customer.getRowCount();
                    for (int i = 0; i < total; i++) {
                        if ((boolean) tbl_Customer.getValueAt(i, 0)) {
                            if (tbl_Customer.getValueAt(i, 7).toString().equals("Đang hoạt động")) {
                                bus_user.changeStatus(tbl_Customer.getValueAt(i, 1).toString(), 0);
                            } else {
                                bus_user.changeStatus(tbl_Customer.getValueAt(i, 1).toString(), 1);
                            }
                        }
                    }
                    clear();
                } 
                else {
                    if (Utils.loginedUser.getPermission() != 2) {
                        notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Bạn không có quyền này!!!");
                        return;
                    } else {
                        int total = tbl_Admin.getRowCount();
                        for (int i = 0; i < total; i++) {
                            if ((boolean) tbl_Admin.getValueAt(i, 0)) {
                                if (tbl_Admin.getValueAt(i, 7).toString().equals("Đang hoạt động")) {
                                    bus_user.changeStatus(tbl_Admin.getValueAt(i, 1).toString(), 0);
                                } else {
                                    bus_user.changeStatus(tbl_Admin.getValueAt(i, 1).toString(), 1);
                                }
                            }
                        }
                    }
                    list = bus_user.getList(1);
                    renderTable(list, adminModel);
                }
            } 
            else {
                return;
            }
        }
        
    }//GEN-LAST:event_btn_BlockActionPerformed

    private void tbl_CustomerMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_CustomerMouseDragged

    }//GEN-LAST:event_tbl_CustomerMouseDragged

    private void tbl_CustomerMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_CustomerMouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_tbl_CustomerMouseMoved

    private void tbl_CustomerMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_CustomerMouseExited

    }//GEN-LAST:event_tbl_CustomerMouseExited

    private void tbl_CustomerMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_CustomerMouseEntered

        
    }//GEN-LAST:event_tbl_CustomerMouseEntered

    private void tbl_CustomerMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_CustomerMousePressed
        mouseClick = tbl_Customer.rowAtPoint(evt.getPoint());
    }//GEN-LAST:event_tbl_CustomerMousePressed

    private void tbl_AdminMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_AdminMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_tbl_AdminMouseDragged

    private void tbl_AdminMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_AdminMouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_tbl_AdminMouseMoved

    private void tbl_AdminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_AdminMouseClicked
        onClickTable(tbl_Admin, adminModel);
        setSelectedAll(adminModel, tbl_Admin);
    }//GEN-LAST:event_tbl_AdminMouseClicked

    private void tbl_AdminMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_AdminMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tbl_AdminMouseEntered

    private void tbl_AdminMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_AdminMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_tbl_AdminMouseExited

    private void tbl_AdminMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_AdminMousePressed
        mouseClick = tbl_Admin.rowAtPoint(evt.getPoint());
    }//GEN-LAST:event_tbl_AdminMousePressed

    private void tbl_AdminMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_AdminMouseReleased
        onReleaseTable(tbl_Admin, evt, mouseClick1);
        setSelectedAll(adminModel, tbl_Admin);
    }//GEN-LAST:event_tbl_AdminMouseReleased

    private void lbl_searchlbl_SortModeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_searchlbl_SortModeMouseClicked
        int selected = jtab_main.getSelectedIndex();
        switch (selected) {
            case 0:
                search(customerModel);
                break;
            case 1:
                search(adminModel);
        }
    }//GEN-LAST:event_lbl_searchlbl_SortModeMouseClicked

    private void txt_DateFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_DateFocusLost

    }//GEN-LAST:event_txt_DateFocusLost

    //Select all
    private int countRow(DefaultTableModel model, JTable table){
        int count = 0;
        for(int i = 0; i < model.getRowCount(); i++) {
            if((boolean) model.getValueAt(i, 0)){
                count++;
            }
        }
        return count;
    }
    
    private void onReleaseTable(JTable table, MouseEvent evt, int mouseClick) {
        int row = table.rowAtPoint(evt.getPoint());
        if (mouseClick == row) {
            return;
        }
        if (row != -1 && mouseClick != -1) {
            if (row > mouseClick) {
                for (int i = mouseClick; i <= row; i++) {
                    boolean isChecked = (boolean) table.getValueAt(i, 0);
                    table.setValueAt(!isChecked, i, 0);
                }

            } else if (row < mouseClick) {
                for (int i = row; i <= mouseClick; i++) {
                    boolean isChecked = (boolean) table.getValueAt(i, 0);
                    table.setValueAt(!isChecked, i, 0);
                }
            }
        }
        mouseClick = -1;
    }
    
    public void onClickTable(JTable table, DefaultTableModel model) {
        int row = table.getSelectedRow();

        if ((boolean) model.getValueAt(row, 0)) {
            model.setValueAt(false, row, 0);
        } else {
            model.setValueAt(true, row, 0);
        }
    }
    
    private void setTableCheckbox(DefaultTableModel model, JTable table) {
        for(int i = 0; i < model.getRowCount(); i++) {
            table.setValueAt(ckb_SelectAll.isSelected(), i, 0);
        }
        if (ckb_SelectAll.isSelected()) {
            selectedItem = model.getRowCount();
        }
        else if (!ckb_SelectAll.isSelected()) {
            selectedItem = 0;
        }
        updateItemSelected();
    }
    
    private void setSelectedAll(DefaultTableModel model, JTable table) {
        int total = model.getRowCount();
        selectedItem = 0;
        for(int i = 0; i < total; i++) {
            if((Boolean) table.getValueAt(i, 0)) {
                selectedItem++;
            }
        }

        if(selectedItem == total) {
            ckb_SelectAll.setSelected(true);
        }
        else if(selectedItem < total) {
            ckb_SelectAll.setSelected(false);
        }
        updateItemSelected();
    }
    
    private void updateItemSelected() {
        if (selectedItem > 0) {
            lbl_ItemSelected.setVisible(true);
            lbl_ItemSelected.setText("Đã chọn " + selectedItem + " SP");
            btn_Block.setEnabled(true);
        }
        else if (selectedItem == 0) {
            lbl_ItemSelected.setVisible(false); 
            btn_Block.setEnabled(false);
        }
    }
    ///////
    
    //Search & Sort
    private void search(DefaultTableModel model) throws NumberFormatException {
        /*
        Tìm kiếm
        1/Mã người dùng
        2/Tên người dùng
        3/Họ tên
        4/Email
        5/Số điện thoại
        6/Ngày tạo
         */
        String keyword = txt_Search.getText();
        switch (cbx_Search.getSelectedIndex()) {
            case 1:
                list = bus_user.searchByUserID(list, keyword);
                searchNotification(list, model);
                break;
            case 2:
                list = bus_user.searchByUserName(list, keyword);
                searchNotification(list, model);
                break;
            case 3:
                list = bus_user.searchByFullName(list, keyword);
                searchNotification(list, model);
                break;
            case 4:
                list = bus_user.searchByEmail(list, keyword);
                searchNotification(list, model);
                break;
            case 5:
                list = bus_user.searchByPhone(list, keyword);
                searchNotification(list, model);
                break;
            case 6:
                if (txt_Date.getText().toString().contains("to")) {
                    String[] date = txt_Date.getText().toString().split(" to ");
                    String[] from = date[0].split("/");
                    String[] to = date[1].split("/");

                    DTO_Date fromDate = new DTO_Date(Integer.parseInt(from[2]), Integer.parseInt(from[1]), Integer.parseInt(from[0]), 0, 0, 0);
                    DTO_Date toDate = new DTO_Date(Integer.parseInt(to[2]), Integer.parseInt(to[1]), Integer.parseInt(to[0]), 0, 0, 0);
                    list = bus_user.searchByInitDate(list, fromDate, toDate);
                    searchNotification(list, model);
                }
                break;
            default:
                notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, 
                        "Vui lòng chọn chức năng tìm kiếm!!!");
        }
    }
    
    private void searchNotification(ArrayList<DTO_User> list, DefaultTableModel model) {
        if (list.isEmpty()) {
            notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Tìm kiếm thất bại");
        } else {
            notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "TÌm kiếm thành công");
            renderTable(list, model);
        }
    }
    
    private void selectModelSortASC() {
        switch (jtab_main.getSelectedIndex()) {
            case 0:
                sortASC(customerModel);
                break;
            case 1:
                sortASC(adminModel);
                break;
        }
    }

    private void selectModelSortDESC() {
        switch (jtab_main.getSelectedIndex()) {
            case 0:
                sortDESC(customerModel);
                break;
            case 1:
                sortDESC(adminModel);
                break;
        }
    }
    
    private void sortASC(DefaultTableModel model) {
        /*
        1/Mã người dùng
        2/Tên người dùng
        3/Họ tên
        4/Email
        5/Số điện thoại
        6/Ngày tạo
        */
        switch (cbx_Sort.getSelectedIndex()) {
            case 1:
                list = bus_user.sortByUserIDASC(list);
                renderTable(list, model);
                break;
            case 2:
                list = bus_user.sortByUserNameASC(list);
                renderTable(list, model);
                break;
            case 3:
                list = bus_user.sortByFullNameASC(list);
                renderTable(list, model);
                break;
            case 4:
                list = bus_user.sortByEmailASC(list);
                renderTable(list, model);
                break;
            case 5:
                list = bus_user.sortByPhoneASC(list);
                renderTable(list, model);
                break;
            case 6:
                list = bus_user.sortByInitDateASC(list);
                renderTable(list, model);
                break;
        }
    }
    
    private void sortDESC(DefaultTableModel model) {
        /*
        1/Mã người dùng
        2/Tên người dùng
        3/Họ tên
        4/Email
        5/Số điện thoại
        6/Ngày tạo
        */
        switch (cbx_Sort.getSelectedIndex()) {
            case 1:
                list = bus_user.sortByUserIDDESC(list);
                renderTable(list, model);
                break;
            case 2:
                list = bus_user.sortByUserNameDESC(list);
                renderTable(list, model);
                break;
            case 3:
                list = bus_user.sortByFullNameDESC(list);
                renderTable(list, model);
                break;
            case 4:
                list = bus_user.sortByEmailDESC(list);
                renderTable(list, model);
                break;
            case 5:
                list = bus_user.sortByPhoneDESC(list);
                renderTable(list, model);
                break;
            case 6:
                list = bus_user.sortByInitDateDESC(list);
                renderTable(list, model);
                break;
        }
    }
    
    private void renderTable(ArrayList <DTO_User> list, DefaultTableModel model) {
        model.setRowCount(0);
        for(DTO_User user : list) {
            Object data [] = new Object[9];
            data[0] = false;
            data[1] = user.getId();
            data[2] = user.getUsername();
            data[3] = user.getFullname();
            data[4] = user.getEmail();
            data[5] = user.getPhone();
            data[6] = user.getAddress();
            data[7] = user.getInitdate().format();
            String status;
            switch (user.getStatus()) {
                case 0:
                    status = "Ngừng hoạt động";
                    break;
                default:
                    status = "Đang hoạt động";
            }
            data[8] = status;
            model.addRow(data);
        }
    }
    
    private void clear() {
        list = bus_user.getList(0);
        renderTable(list, customerModel);
        //checkbox
        cbx_Search.setSelectedIndex(0);
        cbx_Sort.setSelectedIndex(0);
        ckb_SelectAll.setSelected(false);
        //textfield
        txt_Search.setVisible(true);
        txt_Date.setVisible(false);
        //crazypanel
        crazyPanel_SearchByPrice.setVisible(false);
        //label
        lbl_SortMode.setIcon(asc);
        lbl_ItemSelected.setVisible(false);
        //tabbedpane
        jtab_main.setSelectedIndex(0);
        //table
        tbl_Customer.clearSelection();
        //button
        btn_Block.setEnabled(false);
        sortMode = true;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Add;
    private javax.swing.JButton btn_Block;
    private javax.swing.JButton btn_refresh;
    private javax.swing.JComboBox<String> cbx_Search;
    private javax.swing.JComboBox<String> cbx_Sort;
    private javax.swing.JCheckBox ckb_SelectAll;
    private raven.crazypanel.CrazyPanel crazyPanel_Main;
    private raven.crazypanel.CrazyPanel crazyPanel_Menu;
    private raven.crazypanel.CrazyPanel crazyPanel_MenuBtn;
    private raven.crazypanel.CrazyPanel crazyPanel_Parent;
    private raven.crazypanel.CrazyPanel crazyPanel_Queue;
    private raven.crazypanel.CrazyPanel crazyPanel_SearchByPrice;
    private raven.crazypanel.CrazyPanel crazyPanel_SelectAll;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jtab_main;
    private javax.swing.JLabel lbl_ItemSelected;
    private javax.swing.JLabel lbl_SortMode;
    private javax.swing.JLabel lbl_search;
    private javax.swing.JTable tbl_Admin;
    private javax.swing.JTable tbl_Customer;
    private javax.swing.JTextField txt_Date;
    private javax.swing.JTextField txt_Search;
    private javax.swing.JTextField txt_fromPrice;
    private javax.swing.JTextField txt_toPrice;
    // End of variables declaration//GEN-END:variables
}
