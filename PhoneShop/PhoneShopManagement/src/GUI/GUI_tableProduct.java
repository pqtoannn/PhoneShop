package GUI;

import static App.Application.notifications;
import Custom.QueueColorCellRender;
import Custom.ProductQuantityCellEditor;
import Interface.EventSpinnerInputChange;
import BUS.BUS_Color;
import BUS.BUS_ImportInvoice;
import BUS.BUS_Option;
import BUS.BUS_Product;
import BUS.BUS_Ram;
import BUS.BUS_Rom;
import Custom.QueuePriceCellEditor;
import Custom.QueueQuantityCellEditor;
import Custom.ShowInfoCellEditor;
import Custom.ShowInfoCellRender;
import DTO.DTO_Color;
import DTO.DTO_Date;
import DTO.DTO_DefaultInvoice;
import DTO.DTO_InvoiceDetail;
import DTO.DTO_Option;
import DTO.DTO_Product;
import Interface.EventTextFieldInputChange;
import Interface.TableActionEvent;
import Utils.Utils;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import raven.toast.Notifications;


public class GUI_tableProduct extends javax.swing.JPanel {
    private ImageIcon asc = new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-thick-arrow-pointing-up-30.png"));
    private ImageIcon desc = new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-thick-arrow-pointing-down-30.png"));
    
    private DefaultTableModel mainModel;
    private DefaultTableModel queueModel;
    
    private DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
    private int selectedItem = 0;
    private boolean sortMode = true;
    public static int currMode = Utils.VIEW_MODE;
    
    private BUS_Product bus_product = new BUS_Product();
    private BUS_Color bus_color = new BUS_Color();
    private BUS_Rom bus_rom = new BUS_Rom();
    private BUS_Ram bus_ram = new BUS_Ram();
    private BUS_Option bus_option = new BUS_Option();
    private BUS_ImportInvoice bus_iminvoice = new BUS_ImportInvoice();
    
    private ArrayList <DTO_Product> list = new ArrayList <DTO_Product> ();
    private ArrayList <DTO_Option> optionList = new ArrayList<>();
    public static GUI_formProduct formProduct;
    
    private File pathDirectory = new File("D:\\");
    private int mouseClick = -1;
    private int mouseClick1 = -1;
    
    public GUI_tableProduct() {
        initComponents();

        mainModel = (DefaultTableModel) tbl_Main.getModel();
        queueModel = (DefaultTableModel) tbl_Queue.getModel();
        
        clear();
        btn_Add.setIcon(new FlatSVGIcon("assets/add.svg", 0.35f));
        btn_Delete.setIcon(new FlatSVGIcon("assets/delete.svg", 0.35f));
        btn_refresh.setIcon(new FlatSVGIcon("assets/refresh.svg", 0.25f));
        txt_Search.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new FlatSVGIcon("assets/search.svg", 0.35f));
        lbl_search.setIcon(new FlatSVGIcon("assets/search.svg", 0.5f));
        
        //CUSTOMCODE ĐỪNG ĐỤNG VÔ!!!
        applyTableStyleMainTable(tbl_Main);
        applyTableStyleQueueTable(tbl_Queue);
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
        
        table.getColumnModel().getColumn(10).setCellEditor(new ProductQuantityCellEditor(new EventSpinnerInputChange() {
            @Override
            public void inputChanged() {
            }
        }));
        table.getColumnModel().getColumn(10).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(SwingConstants.CENTER);
                return this;
            }
        });
        
        table.getColumnModel().getColumn(11).setCellEditor(new ShowInfoCellEditor(new TableActionEvent() {
            @Override
            public void onView(int row) {
                if(formProduct != null){
                    notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Bạn đã mở cửa sổ này rồi!!!");
                    return;
                }
                if (tbl_Main.getSelectedRowCount() == 1) {
                    Utils.selectedProduct = list.get(tbl_Main.getSelectedRow());
                    Utils.selectedProduct.setColors(bus_color.getListByProductID());
                    Utils.selectedProduct.setRoms(bus_rom.getList());
                    Utils.selectedProduct.setRams(bus_ram.getList());
                    Utils.selectedProduct.setOptions(bus_option.getList());
                    currMode = Utils.VIEW_MODE;
                    formProduct = new GUI_formProduct();
                    formProduct.setVisible(true);
                }
            }
                
        }));
        table.getColumnModel().getColumn(11).setCellRenderer(new ShowInfoCellRender());
    }
    
    private void applyTableStyleQueueTable(JTable table) {
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
        table.getTableHeader().setDefaultRenderer(getAlignmentCellRenderQueueTable(table.getTableHeader().getDefaultRenderer(), true));
        table.setDefaultRenderer(Object.class, getAlignmentCellRenderQueueTable(table.getDefaultRenderer(Object.class), false));
        
//        table.getColumnModel().getColumn(6).setCellEditor(new QueuePriceCellEditor(new EventTextFieldInputChange() {
//            @Override
//            public void inputChanged(DTO_Option item, double newPrice) {
//                
//            }
//
//            @Override
//            public void inputChanged(DTO_InvoiceDetail item, double newPrice) {
//                updateQueuePrice(item, newPrice);
//                sumAmount();
//            }
//        }));
//        table.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
//            @Override
//            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//                setHorizontalAlignment(SwingConstants.CENTER);
//                return this;
//            }
//        });
        
        table.getColumnModel().getColumn(8).setCellEditor(new QueueQuantityCellEditor(new EventSpinnerInputChange() {
            @Override
            public void inputChanged() {
                sumAmount();
            }
        }));
        
        table.getColumnModel().getColumn(8).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(SwingConstants.CENTER);
                return this;
            }
        });
        
        table.getColumnModel().getColumn(3).setCellRenderer(new QueueColorCellRender());
    }
    
    private TableCellRenderer getAlignmentCellRenderMainTable(TableCellRenderer oldRender, boolean header) {
        return new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component com = oldRender.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (com instanceof JLabel) {
                    JLabel label = (JLabel) com;
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    if (header) {
                        switch (column) {
                            case 10:
                                com.setForeground(new Color(202, 48, 48));
                                break;
                        }
                    }
                    else {
                        switch (column) {
                            case 5:
                                com.setForeground(new Color(17, 182, 60));
                                break;
                            case 7:
                                if (value.toString().equals("Đang kinh doanh")) {
                                    com.setForeground(new Color(17, 182, 60));
                                }
                                else {
                                    com.setForeground(new Color(202, 48, 48));
                                }
                                break;
                            case 8:
                                com.setForeground(new Color(202, 48, 48));
                                break;
                            case 10:
                                com.setForeground(new Color(202, 48, 48));
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
 
    private TableCellRenderer getAlignmentCellRenderQueueTable(TableCellRenderer oldRender, boolean header) {
        return new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component com = oldRender.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (com instanceof JLabel) {
                    JLabel label = (JLabel) com;
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    
                    if (header == false) {
                        switch (column) {
                            case 7:
                                com.setForeground(new Color(202, 48, 48));
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
        tbl_Main = new javax.swing.JTable();
        crazyPanel_Queue = new raven.crazypanel.CrazyPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_Queue = new javax.swing.JTable();
        lbl_totalprice = new javax.swing.JLabel();
        btn_insertImport = new javax.swing.JButton();
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
        jLabel2 = new javax.swing.JLabel();
        lbl_ExportExcel = new javax.swing.JLabel();
        btn_AddQueue = new javax.swing.JButton();
        btn_Add = new javax.swing.JButton();
        btn_Delete = new javax.swing.JButton();
        btn_refresh = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
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

        tbl_Main.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Mã sản phẩm", "Tên sản phẩm", "Thương hiệu", "Giá nhập(VNĐ)", "Giá bán(VNĐ)", "Bảo hành(tháng)", "Trạng thái", "Tồn kho", "Đã bán", "Số lượng chờ nhập", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false, false, false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_Main.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                tbl_MainMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                tbl_MainMouseMoved(evt);
            }
        });
        tbl_Main.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_MainMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tbl_MainMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                tbl_MainMouseExited(evt);
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
            tbl_Main.getColumnModel().getColumn(11).setResizable(false);
            tbl_Main.getColumnModel().getColumn(11).setPreferredWidth(30);
        }

        javax.swing.GroupLayout crazyPanel_MainLayout = new javax.swing.GroupLayout(crazyPanel_Main);
        crazyPanel_Main.setLayout(crazyPanel_MainLayout);
        crazyPanel_MainLayout.setHorizontalGroup(
            crazyPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crazyPanel_MainLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 951, Short.MAX_VALUE)
                .addContainerGap())
        );
        crazyPanel_MainLayout.setVerticalGroup(
            crazyPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, crazyPanel_MainLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
                .addGap(16, 16, 16))
        );

        jtab_main.addTab("Kho sản phẩm", crazyPanel_Main);

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

        tbl_Queue.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Mã sản phẩm", "Mã tùy chọn", "Màu sắc", "Bộ nhớ trong", "Bộ nhớ ngoài", "Giá nhập(VNĐ)", "Còn lại", "Số lượng", "Tổng tiền(VNĐ)", "data"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false, false, true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_Queue.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                tbl_QueueMouseDragged(evt);
            }
        });
        tbl_Queue.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_QueueMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbl_QueueMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbl_QueueMouseReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tbl_Queue);
        if (tbl_Queue.getColumnModel().getColumnCount() > 0) {
            tbl_Queue.getColumnModel().getColumn(0).setPreferredWidth(10);
            tbl_Queue.getColumnModel().getColumn(3).setPreferredWidth(30);
            tbl_Queue.getColumnModel().getColumn(4).setPreferredWidth(50);
            tbl_Queue.getColumnModel().getColumn(5).setPreferredWidth(50);
            tbl_Queue.getColumnModel().getColumn(8).setPreferredWidth(50);
            tbl_Queue.getColumnModel().getColumn(10).setMinWidth(0);
            tbl_Queue.getColumnModel().getColumn(10).setPreferredWidth(0);
            tbl_Queue.getColumnModel().getColumn(10).setMaxWidth(0);
        }

        lbl_totalprice.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_totalprice.setText("Tổng trị giá: ");

        btn_insertImport.setText("Xác nhận nhập kho");
        btn_insertImport.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_insertImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_insertImportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout crazyPanel_QueueLayout = new javax.swing.GroupLayout(crazyPanel_Queue);
        crazyPanel_Queue.setLayout(crazyPanel_QueueLayout);
        crazyPanel_QueueLayout.setHorizontalGroup(
            crazyPanel_QueueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crazyPanel_QueueLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(crazyPanel_QueueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(crazyPanel_QueueLayout.createSequentialGroup()
                        .addComponent(lbl_totalprice, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_insertImport))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 947, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );
        crazyPanel_QueueLayout.setVerticalGroup(
            crazyPanel_QueueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crazyPanel_QueueLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(crazyPanel_QueueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(crazyPanel_QueueLayout.createSequentialGroup()
                        .addComponent(lbl_totalprice, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(btn_insertImport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jtab_main.addTab("Hàng đợi", crazyPanel_Queue);

        javax.swing.GroupLayout crazyPanel_ParentLayout = new javax.swing.GroupLayout(crazyPanel_Parent);
        crazyPanel_Parent.setLayout(crazyPanel_ParentLayout);
        crazyPanel_ParentLayout.setHorizontalGroup(
            crazyPanel_ParentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crazyPanel_ParentLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jtab_main)
                .addGap(18, 18, 18))
        );
        crazyPanel_ParentLayout.setVerticalGroup(
            crazyPanel_ParentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, crazyPanel_ParentLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jtab_main)
                .addGap(14, 14, 14))
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

        cbx_Search.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tìm kiếm", "Mã sản phẩm", "Tên sản phẩm", "Thương hiệu", "Khoảng giá" }));
        cbx_Search.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbx_SearchItemStateChanged(evt);
            }
        });
        cbx_Search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbx_SearchActionPerformed(evt);
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

        cbx_Sort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sắp xếp", "Mã sản phẩm", "Thương hiệu", "Giá nhập", "Giá bán", "Bán chạy", "Tồn kho" }));
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

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/import.png"))); // NOI18N
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        crazyPanel_MenuBtn.add(jLabel2);

        lbl_ExportExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/printer.png"))); // NOI18N
        lbl_ExportExcel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_ExportExcel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_ExportExcelMouseClicked(evt);
            }
        });
        crazyPanel_MenuBtn.add(lbl_ExportExcel);

        btn_AddQueue.setText("Thêm hàng đợi");
        btn_AddQueue.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_AddQueue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AddQueueActionPerformed(evt);
            }
        });
        crazyPanel_MenuBtn.add(btn_AddQueue);

        btn_Add.setText("Thêm");
        btn_Add.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AddActionPerformed(evt);
            }
        });
        crazyPanel_MenuBtn.add(btn_Add);

        btn_Delete.setText("Xóa");
        btn_Delete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_DeleteActionPerformed(evt);
            }
        });
        crazyPanel_MenuBtn.add(btn_Delete);

        btn_refresh.setText("Làm mới");
        btn_refresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshActionPerformed(evt);
            }
        });
        crazyPanel_MenuBtn.add(btn_refresh);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Quản lý sản phẩm");

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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_Search, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(crazyPanel_SearchByPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_search)
                        .addGap(18, 18, 18)
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
                            .addComponent(cbx_Search, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
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

    private void tbl_MainMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_MainMouseClicked
        onClickTable(tbl_Main, mainModel);
        setSelectedAll(mainModel, tbl_Main);
    }//GEN-LAST:event_tbl_MainMouseClicked

    private void tbl_QueueMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_QueueMouseClicked
        onClickTable(tbl_Queue, queueModel);
        sumAmount();
        setSelectedAll(queueModel, tbl_Queue);
    }//GEN-LAST:event_tbl_QueueMouseClicked

    private void jtab_mainMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtab_mainMouseClicked
        if (jtab_main.getSelectedIndex() == 1) {
            selectedItem = countRow(queueModel, tbl_Queue);
            if (queueModel.getRowCount() != 0 && selectedItem == queueModel.getRowCount()) {
                ckb_SelectAll.setSelected(true);
            } else {
                ckb_SelectAll.setSelected(false);
            }
        } else {
            selectedItem = countRow(mainModel, tbl_Main);
            if (mainModel.getRowCount() != 0 && selectedItem == mainModel.getRowCount()) {
                ckb_SelectAll.setSelected(true);
            } else {
                ckb_SelectAll.setSelected(false);
            }
        }
        updateItemSelected();
    }//GEN-LAST:event_jtab_mainMouseClicked

    private void cbx_SearchItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbx_SearchItemStateChanged
        if(evt.getStateChange() == 1) {
            int choice = cbx_Search.getSelectedIndex();
            switch (choice) {
                case 4:
                crazyPanel_SearchByPrice.setVisible(true);
                txt_Search.setVisible(false);
                break;
                default:
                crazyPanel_SearchByPrice.setVisible(false);
                txt_Search.setVisible(true);
            }
        }
    }//GEN-LAST:event_cbx_SearchItemStateChanged

    private void lbl_SortModeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_SortModeMouseClicked
        if (!sortMode) {
            sortMode = true;
            lbl_SortMode.setIcon(asc);
            sortASC();
        } else {
            sortMode = false;
            lbl_SortMode.setIcon(desc);
            sortDESC();
        }
    }//GEN-LAST:event_lbl_SortModeMouseClicked

    private void cbx_SortItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbx_SortItemStateChanged
        if(evt.getStateChange() == 1) {
            sortASC();
            lbl_SortMode.setIcon(asc);
        }
    }//GEN-LAST:event_cbx_SortItemStateChanged

    private void ckb_SelectAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckb_SelectAllActionPerformed
        switch (jtab_main.getSelectedIndex()) {
            case 0:
            setTableCheckbox(mainModel, tbl_Main);
            break;
            case 1:
            setTableCheckbox(queueModel, tbl_Queue);
            break;
            default:
            throw new AssertionError();
        }
    }//GEN-LAST:event_ckb_SelectAllActionPerformed

    private void btn_AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AddActionPerformed
        currMode = Utils.ADD_MODE;
        new GUI_formProduct().setVisible(true);
    }//GEN-LAST:event_btn_AddActionPerformed

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        clear();
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void tbl_MainMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_MainMouseReleased
        onReleaseTable(tbl_Main, evt, mouseClick);
        setSelectedAll(mainModel, tbl_Main);
    }//GEN-LAST:event_tbl_MainMouseReleased

    private void jtab_mainStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jtab_mainStateChanged
        
    }//GEN-LAST:event_jtab_mainStateChanged
    
    private boolean queueContainsID(String id){
        for(int i = 0; i < Utils.queueList.size(); i++){
            if(Utils.queueList.get(i).getOption().getProductid().equals(id)){
                return true;
            }
        }
        return false;
    }
    private void btn_AddQueueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AddQueueActionPerformed
        if(jtab_main.getSelectedIndex() == 0){
            int total = mainModel.getRowCount();
            for (int i = 0; i < total; i++) {
                if ((boolean) mainModel.getValueAt(i, 0)) {
                    if(mainModel.getValueAt(i, 7).equals("Ngừng kinh doanh")){
                        continue;
                    }
                    else{
                        if(queueContainsID(mainModel.getValueAt(i, 1).toString())) {
                            for (int g = 0; g < Utils.queueList.size(); g++) {
                                if (Utils.queueList.get(g).getOption().getProductid()
                                        .equals(mainModel.getValueAt(i, 1).toString())) {
                                    Utils.queueList.get(g).setQuantity(Utils.queueList.get(g).getQuantity() 
                                            + Integer.parseInt(mainModel.getValueAt(i, 10).toString()));
                                }
                            }
                        }
                        else {
                            optionList = bus_option.getByProductID(mainModel.getValueAt(i, 1).toString());
                            for (DTO_Option j : optionList) {
                                DTO_InvoiceDetail newQueueItem = new DTO_InvoiceDetail("", j.getId()
                                        , Integer.parseInt(mainModel.getValueAt(i, 10).toString())
                                        , j.getImportprice() * Integer.parseInt(mainModel.getValueAt(i, 10).toString()));
                                newQueueItem.setOption(j);
                                Utils.queueList.add(newQueueItem);
                            }
                        }
                    }
                    renderQueueTable(Utils.queueList);
                }
            }
            notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Thêm hàng đợi thành công!!!");
            jtab_main.setSelectedIndex(1);
            selectedItem = countRow(queueModel, tbl_Queue);
            if (queueModel.getRowCount() != 0 && selectedItem == queueModel.getRowCount()) {
                ckb_SelectAll.setSelected(true);
            } else {
                ckb_SelectAll.setSelected(false);
            }
            updateItemSelected();
        }
        else{
            notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Bạn chưa chọn dòng\nThêm hàng đợi thất bại!!!");
            return;
        }
    }//GEN-LAST:event_btn_AddQueueActionPerformed

    private void btn_DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DeleteActionPerformed
        if (jtab_main.getSelectedIndex() == 0) {
            if (JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn xoá các sản phẩm đã được chọn?") == JOptionPane.YES_OPTION) {
                int total = mainModel.getRowCount();
                for (int i = 0; i < total; i++) {
                    if ((boolean) mainModel.getValueAt(i, 0)) {
                        bus_product.deleteProduct(mainModel.getValueAt(i, 1).toString());
                    }
                }
                clear();
            }
        }
    }//GEN-LAST:event_btn_DeleteActionPerformed

    private void tbl_MainMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_MainMouseDragged
//        int row = tbl_Main.rowAtPoint(evt.getPoint());
//        int column = tbl_Main.columnAtPoint(evt.getPoint());
//        System.out.println(row);
//        if(row != -1)
//        if (tbl_Main.getColumnClass(0) == Boolean.class) {
//            // Get the value of the checkbox.
//            
//            boolean isChecked = (boolean) tbl_Main.getValueAt(row, 0);
//
//            // Set the value of the checkbox to the opposite of its current value.
//            tbl_Main.setValueAt(!isChecked, row, 0);
//        }
    }//GEN-LAST:event_tbl_MainMouseDragged

    private void tbl_MainMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_MainMouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_tbl_MainMouseMoved

    private void tbl_QueueMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_QueueMouseDragged

    }//GEN-LAST:event_tbl_QueueMouseDragged

    private void tbl_MainMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_MainMouseExited

    }//GEN-LAST:event_tbl_MainMouseExited

    private void tbl_MainMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_MainMouseEntered

        
    }//GEN-LAST:event_tbl_MainMouseEntered

    private void tbl_MainMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_MainMousePressed
        mouseClick = tbl_Main.rowAtPoint(evt.getPoint());
    }//GEN-LAST:event_tbl_MainMousePressed

    private void tbl_QueueMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_QueueMouseReleased
        onReleaseTable(tbl_Queue, evt, mouseClick1);
        setSelectedAll(queueModel, tbl_Queue);
        sumAmount();
    }//GEN-LAST:event_tbl_QueueMouseReleased

    private void tbl_QueueMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_QueueMousePressed
        mouseClick1 = tbl_Queue.rowAtPoint(evt.getPoint());
        sumAmount();
    }//GEN-LAST:event_tbl_QueueMousePressed

    private void btn_insertImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_insertImportActionPerformed
        LocalDateTime today = LocalDateTime.now();
        DTO_Date innidate = new DTO_Date(today.getYear(), today.getMonthValue(), today.getDayOfMonth(), today.getHour(), today.getMinute(), today.getSecond());
        DTO_DefaultInvoice newIminvoice = new DTO_DefaultInvoice("", Utils.loginedUser.getId(), innidate, sumAmount());
        newIminvoice.setImportId(bus_iminvoice.getTotal() + 1);
        
        ArrayList<DTO_InvoiceDetail> detailList = new ArrayList<DTO_InvoiceDetail>();
        for (int i = 0; i < tbl_Queue.getRowCount(); i++) {
            if ((boolean)tbl_Queue.getValueAt(i, 0)) {
                DTO_InvoiceDetail detail = (DTO_InvoiceDetail) tbl_Queue.getValueAt(i, 10);
                detail.setInvoiceid(newIminvoice.getId());
                detailList.add(detail);
            }
        }
        newIminvoice.setDetails(detailList);
        Utils.queueList = new ArrayList<>();
        renderQueueTable(Utils.queueList);
        bus_iminvoice.insert(newIminvoice);
    }//GEN-LAST:event_btn_insertImportActionPerformed

    private void cbx_SearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbx_SearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbx_SearchActionPerformed

    private void lbl_searchlbl_SortModeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_searchlbl_SortModeMouseClicked
        search();
    }//GEN-LAST:event_lbl_searchlbl_SortModeMouseClicked

    private String getExtensionFile(File file){
        String fileName = file.getName();
        int index = fileName.lastIndexOf('.');
        if (index > 0) {
          String extension = fileName.substring(index+1);
          return extension;
        }
        return "";
    }
    
    private void lbl_ExportExcelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_ExportExcelMouseClicked
        if (JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xuất ra file excel!") == JOptionPane.YES_OPTION) {
            JFileChooser filechooser = new JFileChooser();            
            filechooser.setCurrentDirectory(pathDirectory);
            int response = filechooser.showSaveDialog(null);
            if (response == filechooser.APPROVE_OPTION) {
                File file = new File(filechooser.getSelectedFile().getAbsolutePath());
                pathDirectory = new File(file.getParent());
                System.out.println(file);
                if(getExtensionFile(file).equals("xlsx")) {
                    if (file.exists() == false) {
                        try {
                            XSSFWorkbook workbook = new XSSFWorkbook();
                            XSSFSheet sheet = (XSSFSheet) workbook.createSheet("Sheet0");
                            XSSFRow row = null;
                            Cell cell = null;
                            row = sheet.createRow(1);
                            String title[] = {"Mã sản phẩm", "Tên sản phẩm", "Thương hiệu", "Giá nhập", "Giá bán", "Bảo hành", "Trạng thái", "Số lượng tồn", "Đã bán"};
                            for (int i = 0; i < title.length; i++) {
                                cell = row.createCell(i, CellType.STRING);
                                cell.setCellValue(title[i]);
                            }
                            for (int i = 0; i < list.size(); i++) {
                                row = sheet.createRow(2 + i);
                                cell = row.createCell(0, CellType.STRING);
                                cell.setCellValue(list.get(i).getId());

                                cell = row.createCell(1, CellType.STRING);
                                cell.setCellValue(list.get(i).getProductname());

                                cell = row.createCell(2, CellType.STRING);
                                cell.setCellValue(list.get(i).getBrandname());

                                cell = row.createCell(3, CellType.STRING);
                                cell.setCellValue(list.get(i).getMinimportprice());

                                cell = row.createCell(4, CellType.NUMERIC);
                                cell.setCellValue(list.get(i).getMinsaleprice());
                                
                                cell = row.createCell(5, CellType.NUMERIC);
                                cell.setCellValue(list.get(i).getWarranty());

                                cell = row.createCell(6, CellType.NUMERIC);
                                cell.setCellValue(list.get(i).getStatus());

                                cell = row.createCell(7, CellType.NUMERIC);
                                cell.setCellValue(list.get(i).getRemain());
                                
                                cell = row.createCell(8, CellType.NUMERIC);
                                cell.setCellValue(list.get(i).getSold());
                            }
                            try {
                                FileOutputStream fop = new FileOutputStream(file);
                                workbook.write(fop);
                                fop.close();
                                JOptionPane.showMessageDialog(this, "Bạn đã xuất danh sách sản phẩm thành công!!");
                            } catch (Exception e) {
                                JOptionPane.showMessageDialog(this, "File đang được mở");
                                return;
                            }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra");
                            return;
                        }
                    } 
                    else {
                        if (JOptionPane.showConfirmDialog(this, "Tên file bạn đặt đã bị trùng.\nBạn có chắc muốn ghi đè lên không???") == JOptionPane.YES_OPTION) {
                            file.delete();
                            try {
                            XSSFWorkbook workbook = new XSSFWorkbook();
                            XSSFSheet sheet = (XSSFSheet) workbook.createSheet("Sheet0");
                            XSSFRow row = null;
                            Cell cell = null;
                            row = sheet.createRow(1);
                            String title[] = {"Mã sản phẩm", "Tên sản phẩm", "Thương hiệu", "Giá nhập", "Giá bán", "Bảo hành", "Trạng thái", "Số lượng tồn", "Đã bán"};
                            for (int i = 0; i < title.length; i++) {
                                cell = row.createCell(i, CellType.STRING);
                                cell.setCellValue(title[i]);
                            }
                            for (int i = 0; i < list.size(); i++) {
                                row = sheet.createRow(2 + i);
                                cell = row.createCell(0, CellType.STRING);
                                cell.setCellValue(list.get(i).getId());

                                cell = row.createCell(1, CellType.STRING);
                                cell.setCellValue(list.get(i).getProductname());

                                cell = row.createCell(2, CellType.STRING);
                                cell.setCellValue(list.get(i).getBrandname());

                                cell = row.createCell(3, CellType.STRING);
                                cell.setCellValue(list.get(i).getMinimportprice());

                                cell = row.createCell(4, CellType.NUMERIC);
                                cell.setCellValue(list.get(i).getMinsaleprice());
                                
                                cell = row.createCell(5, CellType.NUMERIC);
                                cell.setCellValue(list.get(i).getWarranty());

                                cell = row.createCell(6, CellType.NUMERIC);
                                cell.setCellValue(list.get(i).getStatus());

                                cell = row.createCell(7, CellType.NUMERIC);
                                cell.setCellValue(list.get(i).getRemain());
                                
                                cell = row.createCell(8, CellType.NUMERIC);
                                cell.setCellValue(list.get(i).getSold());
                            }
                            try {
                                FileOutputStream fop = new FileOutputStream(file);
                                workbook.write(fop);
                                fop.close();
                                JOptionPane.showMessageDialog(this, "Bạn đã xuất danh sách sản phẩm thành công!!");
                            } catch (Exception e) {
                                JOptionPane.showMessageDialog(this, "File đang được mở");
                                return;
                            }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra");
                            return;
                        }
                        }
                    }
                }
                else{
                    JOptionPane.showMessageDialog(this, "Bạn phải đặt tên file với extention là .xlsx");
                }
            }
        }
    }//GEN-LAST:event_lbl_ExportExcelMouseClicked

    private void importExcel(){
        if(JOptionPane.showConfirmDialog(this, "Chọn một file excel để nhập các thông tin sản phẩm?") == JOptionPane.YES_OPTION){
            JFileChooser filechooser = new JFileChooser();            
            filechooser.setCurrentDirectory(pathDirectory);
            int response = filechooser.showDialog(null, null);
            if (response == filechooser.APPROVE_OPTION) {
                File file = new File(filechooser.getSelectedFile().getAbsolutePath());
                pathDirectory = new File(file.getParent());
                System.out.println(file);
                if (getExtensionFile(file).equals("xlsx")) {
                    try {
                        XSSFWorkbook workbook = new XSSFWorkbook(file);
                        XSSFSheet sheet = workbook.getSheetAt(0);
                        Iterator<Row> rowIterator = sheet.iterator();
                        if(!rowIterator.hasNext()){
                            notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER,
                                      "Thêm không thành công !!!\nKhông có nội dung.");
                            return;
                        }
                        while (rowIterator.hasNext()) {
                            Row row = rowIterator.next();
                            
                            // For each row, iterate through all the columns
                            if (row.getRowNum() == 0) {
                                // Ignore header
                                continue;
                            }
                            Iterator<Cell> cellIterator = row.cellIterator();
                            ArrayList<String> string = new ArrayList();
                            while (cellIterator.hasNext()) {
                                Cell cell = cellIterator.next();
                                if(cell.getColumnIndex() >3)
                                    break;
                                string.add(cell.getStringCellValue());
                                System.out.println(string.get(0));
                            }
                            if(string.size() == 4){
                                DTO_Product newProduct = new DTO_Product("", string.get(0), "", string.get(1), 0, 0, string.get(2), Double.parseDouble(string.get(3)), 1, 0, 0);
                                newProduct.setId(bus_product.getTotal()  + 1);
                                if(bus_product.insert(newProduct) == -1){
                                    notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER,
                                      "Thêm sản phẩm " + newProduct.getProductname()  +" không thành công.");
                                }
                            }
                            else{
                                notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER,
                                      "hàng " + row.getRowNum() +"Không đủ điều kiện để thêm sản phẩm");
                            }
                        }
                    }
//                    catch(IndexOutOfBoundsException e){
//                        notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER,
//                                      "Thêm sản phẩm từ file " + file.getName()  +"thành công.");
//                    clear();
//                        return;
//                    }
                    catch(NumberFormatException e){
                        notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER,
                                      "Thêm không thành công !!!\nthời gian bảo hành không được rỗng.");
                    }
                    catch (Exception e) {
                        notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER,
                                      "Thêm không thành công !!!\nCó lỗi xảy ra.");
                        e.printStackTrace();
                        return;
                    }
                    
                    notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER,
                                      "Thêm sản phẩm từ file " + file.getName()  +" hoàn tất.");
                    clear();
                }
                else{
                    notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, 
                        "file không hợp lệ!!!!");
                }
            }
        }
    }
    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        importExcel();
    }//GEN-LAST:event_jLabel2MouseClicked

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
            if (selectedItem == 1) {
                btn_AddQueue.setEnabled(true);
                btn_Delete.setEnabled(true);
                btn_insertImport.setBackground(Color.GREEN);
                btn_insertImport.setEnabled(true);
            }
            else {
                btn_AddQueue.setEnabled(true);
                btn_Delete.setEnabled(true);
                btn_insertImport.setBackground(Color.GREEN);
                btn_insertImport.setEnabled(true);
            } 
        }
        else if (selectedItem == 0) {
            lbl_ItemSelected.setVisible(false); 
            btn_AddQueue.setEnabled(false);
            btn_Delete.setEnabled(false);
            btn_insertImport.setBackground(Color.RED);
            btn_insertImport.setEnabled(false);
        }
        //System.out.println(ItemSelected);
    }
    ///////
    
    //Search & Sort
    private void search() {
        /*
        Tìm kiếm
        1/Mã sản phẩm
        2/Tên sản phẩm
        3/Thương hiệu
        4/Khoảng giá
        5/Số điện thoại
        6/Ngày tạo
         */
        String keyword = txt_Search.getText();
        switch (cbx_Search.getSelectedIndex()) {
            case 1:
                list = bus_product.searchByProductID(list, keyword);
                searchNotification(list);
                break;
            case 2:
                list = bus_product.searchByProductName(list, keyword);
                searchNotification(list);
                break;
            case 3:
                list = bus_product.searchByBrand(list, keyword);
                searchNotification(list);
                break;
            case 4:
                String fromPriceString = txt_fromPrice.getText().trim();
                String toPriceString = txt_toPrice.getText().trim();

                if (!fromPriceString.isEmpty() && !toPriceString.isEmpty()) {
                    double fromPrice = Double.parseDouble(fromPriceString);
                    double toPrice = Double.parseDouble(toPriceString);
                    list = bus_product.searchByPrice(list, fromPrice, toPrice);
                    searchNotification(list);
                }
                break;
            default:
                notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, 
                        "Vui lòng chọn chức năng tìm kiếm!!!");
        }
    }
    
    private void searchNotification(ArrayList<DTO_Product> list) {
        if (list.isEmpty()) {
            notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Tìm kiếm thất bại");
        } else {
            notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "TÌm kiếm thành công");
            renderMainTable(list);
        }
    }
    
    private void sortASC() {
        /*
        1/Mã SP
        2/Thương hiệu
        3/Giá
        4/Bán chạy
        5/Tồn kho
        */
        switch (cbx_Sort.getSelectedIndex()) {
            case 1:
                list = bus_product.sortByProductNameASC(list);
                renderMainTable(list);
                break;
            case 2:
                list = bus_product.sortByBrandASC(list);
                renderMainTable(list);
                break;
            case 3:
                list = bus_product.sortByPriceASC(list);
                renderMainTable(list);
                break;
        }
    }
    
    private void sortDESC() {
        /*
        1/Mã SP
        2/Thương hiệu
        3/Giá
        4/Bán chạy
        5/Tồn kho
        */
        switch (cbx_Sort.getSelectedIndex()) {
            case 1:
                list = bus_product.sortByProductNameDESC(list);
                renderMainTable(list);
                break;
            case 2:
                list = bus_product.sortByBrandDESC(list);
                renderMainTable(list);
                break;
            case 3:
                list = bus_product.sortByPriceDESC(list);
                renderMainTable(list);
                break;
        }
    }
    
    private void renderMainTable(ArrayList <DTO_Product> list) {
        mainModel.setRowCount(0);
        for(DTO_Product product : list) {
            Object data [] = new Object[11];
            data[0] = false;
            data[1] = product.getId();
            data[2] = product.getProductname();
            data[3] = product.getBrandname();
            data[4] = decimalFormat.format(product.getMinimportprice());
            data[5] = decimalFormat.format(product.getMinsaleprice());
            data[6] = product.getWarranty();
            String status = "Đang kinh doanh";
            switch (product.getStatus()) {
                case 0:
                    status = "Ngừng kinh doanh";
                    break;
                case 2:
                    status = "Sắp ra mắt";
                    break;
                default:
            }
            data[7] = status;
            data[8] = product.getRemain();
            data[9] = product.getSold();
            data[10] = 1;
            mainModel.addRow(data);
        }
    }
    
    private void renderQueueTable(ArrayList <DTO_InvoiceDetail> list) {
        queueModel.setRowCount(0);
        for(DTO_InvoiceDetail queueItem : list) {
            Object data [] = new Object[11];
            data[0] = false;
            data[1] = queueItem.getOption().getProductid();
            data[2] = queueItem.getOptionid();
            data[3] = queueItem.getOption().getColor();
            String romData = queueItem.getOption().getRom() + " GB";
            if (queueItem.getOption().getRom() >= 1000) {
                romData = (queueItem.getOption().getRom() / 1000) + " T";
            }
            String ramData = queueItem.getOption().getRam() + " GB";
            if (queueItem.getOption().getRam() >= 1000) {
                ramData = (queueItem.getOption().getRam() / 1000) + " T";
            }
            data[4] = romData;
            data[5] = ramData;
            data[6] = decimalFormat.format(queueItem.getOption().getImportprice());
            data[7] = queueItem.getOption().getRemain();
            data[8] = queueItem.getQuantity();
            data[9] = decimalFormat.format(queueItem.getTotalprice());
            data[10] = queueItem;
            queueModel.addRow(data);
        }
        sumAmount();
    }
    
    private double sumAmount() {
        int total = 0;
        for (int i = 0; i < tbl_Queue.getRowCount(); i++) {
            if((boolean)queueModel.getValueAt(i, 0)){
                DTO_InvoiceDetail item = (DTO_InvoiceDetail) tbl_Queue.getValueAt(i, 10);
                total += item.getTotalprice();
            }
        }
        lbl_totalprice.setText("Tổng trị giá: " + decimalFormat.format(total));
        return total;
    }
    
    private void updateQueuePrice(DTO_InvoiceDetail item, double newPrice) {
            int rom = item.getOption().getRom();
            int ram = item.getOption().getRam();
            DTO_Color color = item.getOption().getColor();
            for (DTO_InvoiceDetail invoiceDetail : Utils.queueList) {
                if (invoiceDetail.getOption().getRom() == rom && invoiceDetail.getOption().getRam() == ram && invoiceDetail.getOption().getColor() == color) {
                    invoiceDetail.getOption().setImportprice(newPrice);
                    bus_option.update(invoiceDetail.getOption());
                    notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Sửa giá nhập thành công!!!");
                    optionList = bus_option.getDistinctOption();
                    return;
                }
            }        
        notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Sửa thất bại!!!");
    }
    
    private void clear() {
        list = bus_product.getList();
        renderMainTable(list);
        renderQueueTable(Utils.queueList);
        //checkbox
        cbx_Search.setSelectedIndex(0);
        cbx_Sort.setSelectedIndex(0);
        ckb_SelectAll.setSelected(false);
        //textfield
        txt_Search.setVisible(true);
        //crazypanel
        crazyPanel_SearchByPrice.setVisible(false);
        //label
        lbl_SortMode.setIcon(asc);
        lbl_ItemSelected.setVisible(false);
        //tabbedpane
        jtab_main.setSelectedIndex(0);
        //table
        tbl_Main.clearSelection();
        tbl_Queue.clearSelection();
        //button
        btn_AddQueue.setEnabled(false);
        btn_Delete.setEnabled(false);
        
        sortMode = true;
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Add;
    private javax.swing.JButton btn_AddQueue;
    private javax.swing.JButton btn_Delete;
    private javax.swing.JButton btn_insertImport;
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jtab_main;
    private javax.swing.JLabel lbl_ExportExcel;
    private javax.swing.JLabel lbl_ItemSelected;
    private javax.swing.JLabel lbl_SortMode;
    private javax.swing.JLabel lbl_search;
    private javax.swing.JLabel lbl_totalprice;
    private javax.swing.JTable tbl_Main;
    private javax.swing.JTable tbl_Queue;
    private javax.swing.JTextField txt_Search;
    private javax.swing.JTextField txt_fromPrice;
    private javax.swing.JTextField txt_toPrice;
    // End of variables declaration//GEN-END:variables
}
