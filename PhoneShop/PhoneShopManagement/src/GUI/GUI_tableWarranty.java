package GUI;

import static App.Application.notifications;
import BUS.BUS_Warranty;
import BUS.BUS_WarrantyInvoice;
import Custom.ShowInfoCellEditor;
import Custom.ShowInfoCellRender;
import Custom.WarrantyColorCellRender;
import Utils.Utils;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.Component;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import Custom.datechooser.DateBetween;
import Custom.datechooser.DateChooser;
import Custom.datechooser.listener.DateChooserAction;
import Custom.datechooser.listener.DateChooserAdapter;
import DAL.DAL_Option;
import DAL.DAL_Order;
import DAL.DAL_OrderDetail;
import DAL.DAL_Product;
import DTO.DTO_Date;
import DTO.DTO_InvoiceDetail;
import DTO.DTO_Order;
import DTO.DTO_Warranty;
import DTO.DTO_WarrantyInvoice;
import Interface.TableActionEvent;
import java.awt.event.MouseEvent;
import raven.toast.Notifications;

public class GUI_tableWarranty extends javax.swing.JPanel {

    private ImageIcon asc = new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-thick-arrow-pointing-up-30.png"));
    private ImageIcon desc = new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-thick-arrow-pointing-down-30.png"));

    private DefaultTableModel mainModel;
    private DefaultTableModel outofdateModel;
    private DefaultTableModel invoiceModel;

    private DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
    private int selectedItem = 0;
    private boolean sortMode = true;
    public static int currMode = Utils.VIEW_MODE;

    private BUS_Warranty bus_warranty = new BUS_Warranty();
    private BUS_WarrantyInvoice bus_warrantyinvoice = new BUS_WarrantyInvoice();
    DAL_Order dal_order = new DAL_Order();
    DAL_OrderDetail dal_orderDetail = new DAL_OrderDetail();
    DAL_Option dal_option = new DAL_Option();
    DAL_Product dal_product = new DAL_Product();

    private ArrayList<DTO_Warranty> list = new ArrayList<DTO_Warranty>();
    private ArrayList<DTO_WarrantyInvoice> listInvoice = new ArrayList<DTO_WarrantyInvoice>();
    public static GUI_formWarrInvoice formWarrInvoice;
    
    private ArrayList<DTO_Order> listOD = new ArrayList<DTO_Order>();
    private ArrayList<DTO_InvoiceDetail> listODDetail = new ArrayList<DTO_InvoiceDetail>();
    
    private int mouseClick = -1;
    private int mouseClick1 = -1;

    public GUI_tableWarranty() {
        initComponents();

        mainModel = (DefaultTableModel) tbl_Main.getModel();
        outofdateModel = (DefaultTableModel) tbl_OutOfDate.getModel();
        invoiceModel = (DefaultTableModel) tbl_Invoice.getModel();

        clear();

        //CUSTOMCODE ĐỪNG ĐỤNG VÔ!!!
        btn_AddInvoice.setIcon(new FlatSVGIcon("assets/add.svg", 0.35f));
        btn_refresh.setIcon(new FlatSVGIcon("assets/refresh.svg", 0.25f));
        txt_Search.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new FlatSVGIcon("assets/search.svg", 0.35f));

        lbl_search.setIcon(new FlatSVGIcon("assets/search.svg", 0.5f));
        
        applyTableStyleMainTable(tbl_Main);
        applyTableStyleMainTable(tbl_OutOfDate);
        applyTableStyleInvoiceTable(tbl_Invoice);
        /////////
    }

    //CUSTOMCODE ĐỪNG ĐỤNG VÔ!!!
    private void applyTableStyleMainTable(JTable table) {
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
        table.getTableHeader().setDefaultRenderer(getAlignmentCellRenderMainTable(table.getTableHeader().getDefaultRenderer(), true));
        table.setDefaultRenderer(Object.class, getAlignmentCellRenderMainTable(table.getDefaultRenderer(Object.class), false));
    
        table.getColumnModel().getColumn(4).setCellRenderer(new WarrantyColorCellRender());
    }

    private void applyTableStyleInvoiceTable(JTable table) {
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
        table.getTableHeader().setDefaultRenderer(getAlignmentCellRenderInvoiceTable(table.getTableHeader().getDefaultRenderer(), true));
        table.setDefaultRenderer(Object.class, getAlignmentCellRenderInvoiceTable(table.getDefaultRenderer(Object.class), false));

        table.getColumnModel().getColumn(3).setCellEditor(new ShowInfoCellEditor(new TableActionEvent() {
            @Override
            public void onView(int row) {
                if (formWarrInvoice != null) {
                    notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Bạn đã mở cửa sổ này rồi!!!");
                    return;
                }
                if (tbl_Main.getSelectedRowCount() == 1) {
                    currMode = Utils.VIEW_MODE;
                    Utils.selectedWarrantyInvoice = listInvoice.get(tbl_Invoice.getSelectedRow());
                    formWarrInvoice = new GUI_formWarrInvoice();
                    formWarrInvoice.setVisible(true);
                }
            }

        }));
        table.getColumnModel().getColumn(3).setCellRenderer(new ShowInfoCellRender());
    }

    private TableCellRenderer getAlignmentCellRenderMainTable(TableCellRenderer oldRender, boolean header) {
        return new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component com = oldRender.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (com instanceof JLabel) {
                    JLabel label = (JLabel) com;
                    label.setHorizontalAlignment(SwingConstants.CENTER);

                    if (header == false) {
                        switch (column) {
                            default:
                                if (isSelected) {
                                    com.setForeground(table.getSelectionForeground());
                                } else {
                                    com.setForeground(table.getForeground());
                                }
                        }

                    }
                }
                return com;
            }
        };
    }

    private TableCellRenderer getAlignmentCellRenderInvoiceTable(TableCellRenderer oldRender, boolean header) {
        return new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component com = oldRender.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (com instanceof JLabel) {
                    JLabel label = (JLabel) com;
                    label.setHorizontalAlignment(SwingConstants.CENTER);

                    if (header == false) {
                        switch (column) {
                            default:
                                if (isSelected) {
                                    com.setForeground(table.getSelectionForeground());
                                } else {
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
        jTabbedPane = new javax.swing.JTabbedPane();
        crazyPanel_Main = new raven.crazypanel.CrazyPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_Main = new javax.swing.JTable();
        crazyPanel_Queue = new raven.crazypanel.CrazyPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_OutOfDate = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_Invoice = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        crazyPanel_Menu = new raven.crazypanel.CrazyPanel();
        cbx_Search = new javax.swing.JComboBox<>();
        txt_Search = new javax.swing.JTextField();
        lbl_SortMode = new javax.swing.JLabel();
        cbx_Sort = new javax.swing.JComboBox<>();
        crazyPanel_SelectAll = new raven.crazypanel.CrazyPanel();
        ckb_SelectAll = new javax.swing.JCheckBox();
        lbl_ItemSelected = new javax.swing.JLabel();
        crazyPanel_MenuBtn = new raven.crazypanel.CrazyPanel();
        btn_AddInvoice = new javax.swing.JButton();
        btn_refresh = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txt_Date = new javax.swing.JTextField();
        lbl_search = new javax.swing.JLabel();

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

        jTabbedPane.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTabbedPane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPaneMouseClicked(evt);
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

        tbl_Main.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Mã bảo hành", "Ngày tạo", "Tên sản phẩm", "Màu", "Bộ nhớ trong", "Bộ nhớ đệm", "Họ tên", "Số điện thoại", "data"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_Main.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_MainMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbl_MainMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbl_MainMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_Main);
        if (tbl_Main.getColumnModel().getColumnCount() > 0) {
            tbl_Main.getColumnModel().getColumn(0).setPreferredWidth(20);
            tbl_Main.getColumnModel().getColumn(9).setMinWidth(0);
            tbl_Main.getColumnModel().getColumn(9).setPreferredWidth(0);
            tbl_Main.getColumnModel().getColumn(9).setMaxWidth(0);
        }

        javax.swing.GroupLayout crazyPanel_MainLayout = new javax.swing.GroupLayout(crazyPanel_Main);
        crazyPanel_Main.setLayout(crazyPanel_MainLayout);
        crazyPanel_MainLayout.setHorizontalGroup(
            crazyPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crazyPanel_MainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 748, Short.MAX_VALUE)
                .addContainerGap())
        );
        crazyPanel_MainLayout.setVerticalGroup(
            crazyPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crazyPanel_MainLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );

        jTabbedPane.addTab("Còn hạn", crazyPanel_Main);

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

        tbl_OutOfDate.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Mã bảo hành", "Ngày tạo", "Tên sản phẩm", "Màu", "Bộ nhớ trong", "Bộ nhớ đệm", "Họ tên", "Số điện thoại", "data"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_OutOfDate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_OutOfDateMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbl_OutOfDateMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbl_OutOfDateMouseReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tbl_OutOfDate);
        if (tbl_OutOfDate.getColumnModel().getColumnCount() > 0) {
            tbl_OutOfDate.getColumnModel().getColumn(0).setPreferredWidth(20);
            tbl_OutOfDate.getColumnModel().getColumn(9).setMinWidth(0);
            tbl_OutOfDate.getColumnModel().getColumn(9).setPreferredWidth(0);
            tbl_OutOfDate.getColumnModel().getColumn(9).setMaxWidth(0);
        }

        javax.swing.GroupLayout crazyPanel_QueueLayout = new javax.swing.GroupLayout(crazyPanel_Queue);
        crazyPanel_Queue.setLayout(crazyPanel_QueueLayout);
        crazyPanel_QueueLayout.setHorizontalGroup(
            crazyPanel_QueueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crazyPanel_QueueLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 748, Short.MAX_VALUE)
                .addContainerGap())
        );
        crazyPanel_QueueLayout.setVerticalGroup(
            crazyPanel_QueueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crazyPanel_QueueLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE)
                .addGap(22, 22, 22))
        );

        jTabbedPane.addTab("Hết hạn", crazyPanel_Queue);

        tbl_Invoice.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã hóa đơn", "Ngày tạo", "Chi phí(VNĐ)", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_Invoice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_InvoiceMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbl_InvoiceMouseReleased(evt);
            }
        });
        jScrollPane4.setViewportView(tbl_Invoice);
        if (tbl_Invoice.getColumnModel().getColumnCount() > 0) {
            tbl_Invoice.getColumnModel().getColumn(3).setResizable(false);
            tbl_Invoice.getColumnModel().getColumn(3).setPreferredWidth(30);
        }

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Danh sách phiếu bảo hành");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("Danh sách hóa đơn");

        javax.swing.GroupLayout crazyPanel_ParentLayout = new javax.swing.GroupLayout(crazyPanel_Parent);
        crazyPanel_Parent.setLayout(crazyPanel_ParentLayout);
        crazyPanel_ParentLayout.setHorizontalGroup(
            crazyPanel_ParentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crazyPanel_ParentLayout.createSequentialGroup()
                .addGroup(crazyPanel_ParentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(crazyPanel_ParentLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel2))
                    .addGroup(crazyPanel_ParentLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTabbedPane)))
                .addGap(25, 25, 25)
                .addGroup(crazyPanel_ParentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, crazyPanel_ParentLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(215, 215, 215))))
        );
        crazyPanel_ParentLayout.setVerticalGroup(
            crazyPanel_ParentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crazyPanel_ParentLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jLabel2)
                .addGroup(crazyPanel_ParentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(crazyPanel_ParentLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane4)
                        .addGap(22, 22, 22))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, crazyPanel_ParentLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jTabbedPane))))
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

        cbx_Search.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tìm kiếm", "Mã bảo hành", "Tên sản phẩm", "Họ tên", "Ngày tạo bảo hành" }));
        cbx_Search.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbx_SearchItemStateChanged(evt);
            }
        });

        txt_Search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_SearchKeyReleased(evt);
            }
        });

        lbl_SortMode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-thick-arrow-pointing-up-30.png"))); // NOI18N
        lbl_SortMode.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_SortMode.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_SortModeMouseClicked(evt);
            }
        });

        cbx_Sort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sắp xếp", "Theo ngày tạo", "Họ tên", "Tên sản phẩm" }));
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

        btn_AddInvoice.setText("Thêm hóa đơn");
        btn_AddInvoice.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_AddInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AddInvoiceActionPerformed(evt);
            }
        });
        crazyPanel_MenuBtn.add(btn_AddInvoice);

        btn_refresh.setText("Làm mới");
        btn_refresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshActionPerformed(evt);
            }
        });
        crazyPanel_MenuBtn.add(btn_refresh);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Quản lý bảo hành");

        txt_Date.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_DateFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_DateFocusLost(evt);
            }
        });

        lbl_search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-thick-arrow-pointing-up-30.png"))); // NOI18N
        lbl_search.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_search.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_searchlbl_SortModeMouseClicked(evt);
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_Search, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_Date, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_search)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbx_Sort, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                    .addComponent(crazyPanel_MenuBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(26, 26, 26)
                .addGroup(crazyPanel_MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(crazyPanel_SelectAll, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(crazyPanel_MenuLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(crazyPanel_MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_SortMode)
                            .addGroup(crazyPanel_MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txt_Search, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbx_Search, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txt_Date, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lbl_search)
                            .addComponent(cbx_Sort, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(crazyPanel_Menu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(crazyPanel_Parent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(crazyPanel_Menu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(crazyPanel_Parent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(12, 12, 12))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tbl_MainMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_MainMouseClicked
        Utils.selectedWarranty = list.get(tbl_Main.getSelectedRow());
        Utils.selectedWarranty.setInvoices(bus_warrantyinvoice.getList());

        listInvoice = Utils.selectedWarranty.getInvoices();
        renderInvoiceTable(Utils.selectedWarranty.getInvoices());
        onClickTable(tbl_Main, mainModel);
        setSelectedAll(mainModel, tbl_Main);
    }//GEN-LAST:event_tbl_MainMouseClicked

    private void jTabbedPaneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPaneMouseClicked
        if (jTabbedPane.getSelectedIndex() == 1) {
            list = bus_warranty.getListOutOfDate();
            renderMainTable(list, outofdateModel);
//            selectedItem = countRow(outofdateModel, tbl_OutOfDate);
//            if (outofdateModel.getRowCount() != 0 && selectedItem == outofdateModel.getRowCount()) {
//                ckb_SelectAll.setSelected(true);
//            } else {
//                ckb_SelectAll.setSelected(false);
//            }
        } else {
            list = bus_warranty.getList();
            renderMainTable(list, mainModel);
//            selectedItem = countRow(mainModel, tbl_Main);
//            if (mainModel.getRowCount() != 0 && selectedItem == mainModel.getRowCount()) {
//                ckb_SelectAll.setSelected(true);
//            } else {
//                ckb_SelectAll.setSelected(false);
//            }
        }
        ckb_SelectAll.setSelected(false);
        updateItemSelected();
    }//GEN-LAST:event_jTabbedPaneMouseClicked

    private void cbx_SearchItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbx_SearchItemStateChanged
        if (evt.getStateChange() == 1) {
            int choice = cbx_Search.getSelectedIndex();
            switch (choice) {
                case 4:
                    txt_Search.setVisible(false);
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
        if (evt.getStateChange() == 1) {
            selectModelSortASC();
            lbl_SortMode.setIcon(asc);
        }
    }//GEN-LAST:event_cbx_SortItemStateChanged

    private void ckb_SelectAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckb_SelectAllActionPerformed
        switch (jTabbedPane.getSelectedIndex()) {
            case 0:
                setTableCheckbox(mainModel, tbl_Main);
                break;
            case 1:
                setTableCheckbox(outofdateModel, tbl_OutOfDate);
                break;
        }
    }//GEN-LAST:event_ckb_SelectAllActionPerformed

    private void btn_AddInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AddInvoiceActionPerformed
        currMode = Utils.ADD_MODE;
        new GUI_formWarrInvoice().setVisible(true);
    }//GEN-LAST:event_btn_AddInvoiceActionPerformed

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        clear();
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void tbl_MainMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_MainMouseReleased
        onReleaseTable(tbl_Main, evt, mouseClick);
        setSelectedAll(mainModel, tbl_Main);
    }//GEN-LAST:event_tbl_MainMouseReleased

    private void tbl_OutOfDateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_OutOfDateMouseClicked
        Utils.selectedWarranty = list.get(tbl_OutOfDate.getSelectedRow());
        Utils.selectedWarranty.setInvoices(bus_warrantyinvoice.getList());

        listInvoice = Utils.selectedWarranty.getInvoices();
        renderInvoiceTable(Utils.selectedWarranty.getInvoices());
        onClickTable(tbl_OutOfDate, outofdateModel);
        setSelectedAll(outofdateModel, tbl_OutOfDate);
    }//GEN-LAST:event_tbl_OutOfDateMouseClicked

    private void tbl_OutOfDateMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_OutOfDateMouseReleased
        onReleaseTable(tbl_OutOfDate, evt, mouseClick1);
        setSelectedAll(outofdateModel, tbl_OutOfDate);
    }//GEN-LAST:event_tbl_OutOfDateMouseReleased

    private void tbl_InvoiceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_InvoiceMouseClicked

    }//GEN-LAST:event_tbl_InvoiceMouseClicked

    private void tbl_InvoiceMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_InvoiceMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tbl_InvoiceMouseReleased

    private void txt_SearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_SearchKeyReleased

    }//GEN-LAST:event_txt_SearchKeyReleased

    private void txt_DateFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_DateFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_DateFocusLost

    private void txt_DateFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_DateFocusGained

    }//GEN-LAST:event_txt_DateFocusGained

    private void lbl_searchlbl_SortModeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_searchlbl_SortModeMouseClicked
        int selected = jTabbedPane.getSelectedIndex();
        switch (selected) {
            case 0:
                search(mainModel);
                break;
            case 1:
                search(outofdateModel);
        }
    }//GEN-LAST:event_lbl_searchlbl_SortModeMouseClicked

    private void tbl_MainMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_MainMousePressed
        mouseClick = tbl_Main.rowAtPoint(evt.getPoint());
    }//GEN-LAST:event_tbl_MainMousePressed

    private void tbl_OutOfDateMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_OutOfDateMousePressed
        mouseClick1 = tbl_OutOfDate.rowAtPoint(evt.getPoint());
    }//GEN-LAST:event_tbl_OutOfDateMousePressed

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
    
    private void setTableCheckbox(DefaultTableModel model, JTable table) {
        for (int i = 0; i < model.getRowCount(); i++) {
            table.setValueAt(ckb_SelectAll.isSelected(), i, 0);
        }
        if (ckb_SelectAll.isSelected()) {
            selectedItem = model.getRowCount();
        } else if (!ckb_SelectAll.isSelected()) {
            selectedItem = 0;
        }
        updateItemSelected();
    }

    private void setSelectedAll(DefaultTableModel model, JTable table) {
        int total = model.getRowCount();
        selectedItem = 0;
        for (int i = 0; i < total; i++) {
            if ((Boolean) table.getValueAt(i, 0)) {
                selectedItem++;
            }
        }

        if (selectedItem == total) {
            ckb_SelectAll.setSelected(true);
        } else if (selectedItem < total) {
            ckb_SelectAll.setSelected(false);
        }
        updateItemSelected();
    }

    private void updateItemSelected() {
        if (selectedItem > 0) {
            lbl_ItemSelected.setVisible(true);
            lbl_ItemSelected.setText("Đã chọn " + selectedItem + " Phiếu");
            if (selectedItem == 1) {
//                btn_PrintWarranty.setEnabled(true);
//                btn_PrintInvoice.setEnabled(true);
                btn_AddInvoice.setEnabled(true);
            }
            else {
//                btn_PrintWarranty.setEnabled(true);
//                btn_PrintInvoice.setEnabled(true);
                btn_AddInvoice.setEnabled(false);
            }
        } else if (selectedItem == 0) {
            lbl_ItemSelected.setVisible(false);
//            btn_PrintWarranty.setEnabled(false);
//            btn_PrintInvoice.setEnabled(false);
            btn_AddInvoice.setEnabled(false);
        }
    }
    ///////

    //Search & Sort
    private void search(DefaultTableModel model) throws NumberFormatException {
        /*
        Tìm kiếm
        1/Mã bảo hành
        2/Tên sản phẩm
        3/Họ tên
        4/Ngày tạo bảo hành
         */
        String keyword = txt_Search.getText();
        switch (cbx_Search.getSelectedIndex()) {
            case 1:
                list = bus_warranty.searchByWarrantyID(list, keyword);
                searchNotification(list, model);
                break;
            case 2:
                list = bus_warranty.searchByProductName(list, keyword);
                searchNotification(list, model);
                break;
            case 3:
                list = bus_warranty.searchByFullName(list, keyword);
                searchNotification(list, model);
                break;
            case 4:
                if (txt_Date.getText().toString().contains("to")) {
                    String[] date = txt_Date.getText().toString().split(" to ");
                    String[] from = date[0].split("/");
                    String[] to = date[1].split("/");

                    DTO_Date fromDate = new DTO_Date(Integer.parseInt(from[2]), Integer.parseInt(from[1]), Integer.parseInt(from[0]), 0, 0, 0);
                    DTO_Date toDate = new DTO_Date(Integer.parseInt(to[2]), Integer.parseInt(to[1]), Integer.parseInt(to[0]), 0, 0, 0);
                    list = bus_warranty.searchByInitDate(list, fromDate, toDate);
                    searchNotification(list, model);
                }
                break;
            default:
                notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, 
                        "Vui lòng chọn chức năng tìm kiếm!!!");
        }
    }
    
    private void searchNotification(ArrayList<DTO_Warranty> list, DefaultTableModel model) {
        if (list.isEmpty()) {
            notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Tìm kiếm thất bại");
        } else {
            notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "TÌm kiếm thành công");
            renderMainTable(list, model);
        }
    }

    private void selectModelSortASC() {
        switch (jTabbedPane.getSelectedIndex()) {
            case 0:
                sortASC(mainModel);
                break;
            case 1:
                sortASC(outofdateModel);
                break;
        }
    }

    private void selectModelSortDESC() {
        switch (jTabbedPane.getSelectedIndex()) {
            case 0:
                sortDESC(mainModel);
                break;
            case 1:
                sortDESC(outofdateModel);
                break;
        }
    }
    
    private void sortASC(DefaultTableModel model) {
        /*
        Xếp tăng
        1/Ngày tạo
        2/Họ tên
        3/Tên sản phẩm
         */
        switch (cbx_Sort.getSelectedIndex()) {
            case 1:
                list = bus_warranty.sortByInitDateASC(list);
                renderMainTable(list, model);
                break;
            case 2:
                list = bus_warranty.sortByFullNameASC(list);
                renderMainTable(list, model);
                break;
            case 3:
                list = bus_warranty.sortByProductNameASC(list);
                renderMainTable(list, model);
                break;
        }
    }

    private void sortDESC(DefaultTableModel model) {
        /*
        Xếp giảm
        1/Ngày tạo
        2/Họ tên
        3/Tên sản phẩm
         */
        switch (cbx_Sort.getSelectedIndex()) {
            case 1:
                list = bus_warranty.sortByInitDateDESC(list);
                renderMainTable(list, model);
                break;
            case 2:
                list = bus_warranty.sortByFullNameDESC(list);
                renderMainTable(list, model);
                break;
            case 3:
                list = bus_warranty.sortByProductNameDESC(list);
                renderMainTable(list, model);
                break;
        }
    }
    //////////

    private void renderMainTable(ArrayList<DTO_Warranty> list, DefaultTableModel model) {
        model.setRowCount(0);
        for (DTO_Warranty warranty : list) {
            Object data[] = new Object[10];
            data[0] = false;
            data[1] = warranty.getId();
            data[2] = warranty.getInitdate().format();
            data[3] = warranty.getProduct().getProductname();
            data[4] = warranty.getOption().getColor();
            String romData = warranty.getOption().getRom() + " GB";
            if (warranty.getOption().getRom() >= 1000) {
                romData = (warranty.getOption().getRom() / 1000) + " T";
            }
            String ramData = warranty.getOption().getRam() + " GB";
            if (warranty.getOption().getRam() >= 1000) {
                ramData = (warranty.getOption().getRam() / 1000) + " T";
            }
            data[5] = romData;
            data[6] = ramData;
            data[7] = warranty.getUser().getFullname();
            data[8] = warranty.getUser().getPhone();
            data[9] = warranty;

            model.addRow(data);
        }
    }

    private void renderInvoiceTable(ArrayList<DTO_WarrantyInvoice> list) {
        invoiceModel.setRowCount(0);
        for (DTO_WarrantyInvoice invoice : list) {
            Object data[] = new Object[6];
            data[0] = invoice.getId();
            data[1] = invoice.getInitdate().format();
            data[2] = invoice.getExpense();
            invoiceModel.addRow(data);
        }
    }

    private void clear() {
        list = bus_warranty.getList();
        renderMainTable(list, mainModel);
        //checkbox
        cbx_Search.setSelectedIndex(0);
        cbx_Sort.setSelectedIndex(0);
        ckb_SelectAll.setSelected(false);
        //textfield
        txt_Search.setVisible(true);
        txt_Date.setVisible(false);
        //label
        lbl_SortMode.setIcon(asc);
        lbl_ItemSelected.setVisible(false);
        //tabbedpane
        jTabbedPane.setSelectedIndex(0);
        //table
        tbl_Main.clearSelection();
        tbl_OutOfDate.clearSelection();
        invoiceModel.setRowCount(0);
        tbl_Invoice.clearSelection();
        //button
//        btn_PrintInvoice.setEnabled(false);
        btn_AddInvoice.setEnabled(false);
        
        sortMode = true;

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_AddInvoice;
    private javax.swing.JButton btn_refresh;
    private javax.swing.JComboBox<String> cbx_Search;
    private javax.swing.JComboBox<String> cbx_Sort;
    private javax.swing.JCheckBox ckb_SelectAll;
    private raven.crazypanel.CrazyPanel crazyPanel_Main;
    private raven.crazypanel.CrazyPanel crazyPanel_Menu;
    private raven.crazypanel.CrazyPanel crazyPanel_MenuBtn;
    private raven.crazypanel.CrazyPanel crazyPanel_Parent;
    private raven.crazypanel.CrazyPanel crazyPanel_Queue;
    private raven.crazypanel.CrazyPanel crazyPanel_SelectAll;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JLabel lbl_ItemSelected;
    private javax.swing.JLabel lbl_SortMode;
    private javax.swing.JLabel lbl_search;
    private javax.swing.JTable tbl_Invoice;
    private javax.swing.JTable tbl_Main;
    private javax.swing.JTable tbl_OutOfDate;
    private javax.swing.JTextField txt_Date;
    private javax.swing.JTextField txt_Search;
    // End of variables declaration//GEN-END:variables
}
