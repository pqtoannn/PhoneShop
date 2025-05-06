package GUI;

import static App.Application.notifications;
import BUS.BUS_Order;
import BUS.BUS_OrderDetail;
import Custom.ShowInfoCellEditor;
import Custom.ShowInfoCellRender;
import Utils.Utils;
import com.formdev.flatlaf.FlatClientProperties;
import Custom.datechooser.DateBetween;
import Custom.datechooser.listener.DateChooserAction;
import Custom.datechooser.listener.DateChooserAdapter;
import java.awt.Component;
import java.text.SimpleDateFormat;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import Custom.datechooser.DateChooser;
import DAL.DAL_Order;
import DTO.DTO_Date;
import DTO.DTO_InvoiceDetail;
import DTO.DTO_Order;
import DTO.DocSo;
import Interface.TableActionEvent;
import com.aspose.words.Document;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import raven.toast.Notifications;

public class GUI_tableOrder extends javax.swing.JPanel {

    private ImageIcon asc = new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-thick-arrow-pointing-up-30.png"));
    private ImageIcon desc = new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-thick-arrow-pointing-down-30.png"));

    private DefaultTableModel mainModel;
    private DefaultTableModel mainModel1;
    private DefaultTableModel mainModel2;
    private DefaultTableModel mainModel3;
    private DefaultTableModel mainModel4;
    private DefaultTableModel mainModel5;
    private DefaultTableModel mainModel6;

    private DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
    private int selectedItem = 0;
    private boolean sortMode = true;
    public static int currMode = Utils.VIEW_MODE;

    private BUS_Order bus_order = new BUS_Order();
    private BUS_OrderDetail bus_orderdetail = new BUS_OrderDetail();
    DAL.DAL_Order dal_order = new DAL_Order();

    private ArrayList<DTO_Order> list = new ArrayList<DTO_Order>();
    private ArrayList<DTO_InvoiceDetail> listDetail = new ArrayList<DTO_InvoiceDetail>();
    public static GUI_formOrder formOrder;
    private int mouseClick = -1;
    private int mouseClick1 = -1;
    private int mouseClick2 = -1;
    private int mouseClick3 = -1;
    private int mouseClick4 = -1;
    private int mouseClick5 = -1;
    private int mouseClick6 = -1;

    public GUI_tableOrder() {
        initComponents();

        mainModel = (DefaultTableModel) tbl_Main.getModel();
        mainModel1 = (DefaultTableModel) tbl_Main1.getModel();
        mainModel2 = (DefaultTableModel) tbl_Main2.getModel();
        mainModel3 = (DefaultTableModel) tbl_Main3.getModel();
        mainModel4 = (DefaultTableModel) tbl_Main4.getModel();
        mainModel5 = (DefaultTableModel) tbl_Main5.getModel();
        mainModel6 = (DefaultTableModel) tbl_Main6.getModel();

        clear();

        //CUSTOMCODE ĐỪNG ĐỤNG VÔ!!!
        btn_Add.setIcon(new FlatSVGIcon("assets/add.svg", 0.35f));
        btn_refresh.setIcon(new FlatSVGIcon("assets/refresh.svg", 0.25f));
        txt_Search.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new FlatSVGIcon("assets/search.svg", 0.35f));
        txt_fromPrice.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new FlatSVGIcon("assets/search.svg", 0.35f));
        txt_toPrice.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new FlatSVGIcon("assets/search.svg", 0.35f));
        txt_Date.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new FlatSVGIcon("assets/search.svg", 0.35f));
        lbl_search.setIcon(new FlatSVGIcon("assets/search.svg", 0.5f));

        applyTableStyleMainTable(tbl_Main);
        applyTableStyleMainTable(tbl_Main1);
        applyTableStyleMainTable(tbl_Main2);
        applyTableStyleMainTable(tbl_Main3);
        applyTableStyleMainTable(tbl_Main4);
        applyTableStyleMainTable(tbl_Main5);
        applyTableStyleMainTable(tbl_Main6);
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

        table.getColumnModel().getColumn(9).setCellEditor(new ShowInfoCellEditor(new TableActionEvent() {
            @Override
            public void onView(int row) {
                if (formOrder != null) {
                    notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Bạn đã mở cửa sổ này rồi!!!");
                    return;
                }
                if (tbl_Main.getSelectedRowCount() == 1 || tbl_Main1.getSelectedRowCount() == 1 || tbl_Main2.getSelectedRowCount() == 1
                        || tbl_Main3.getSelectedRowCount() == 1 || tbl_Main4.getSelectedRowCount() == 1
                        || tbl_Main5.getSelectedRowCount() == 1 || tbl_Main6.getSelectedRowCount() == 1) {
                    getSelectedOrder();
                    currMode = Utils.VIEW_MODE;
                    formOrder = new GUI_formOrder();
                    formOrder.setVisible(true);

                }
            }

        }));
        table.getColumnModel().getColumn(9).setCellRenderer(new ShowInfoCellRender());
    }

    private TableCellRenderer getAlignmentCellRenderMainTable(TableCellRenderer oldRender, boolean header) {
        return new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component com = oldRender.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (com instanceof JLabel) {
                    JLabel label = (JLabel) com;
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    value = "";

                    if (header == false) {
                        switch (column) {
//                            case 9:
//                                if (value.toString().equals("Chờ xử lý") || value.toString().equals("Bị hủy") || value.toString().equals("Giao thất bại")) {
//                                    com.setForeground(new Color(202, 48, 48));
//                                } else if (value.toString().equals("Đã xác nhận") || value.toString().equals("Đang vận chuyển")) {
//                                    com.setForeground(new Color(255, 255, 51));
//                                } else if (value.toString().equals("Giao thành công") || value.toString().equals("Tại shop")) {
//                                    com.setForeground(new Color(17, 182, 60));
//                                }
//                                break;
                            case 8:
                                com.setForeground(new Color(17, 182, 60));
                                break;
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

        crazyPanel_Menu = new raven.crazypanel.CrazyPanel();
        cbx_Search = new javax.swing.JComboBox<>();
        txt_Search = new javax.swing.JTextField();
        crazyPanel_SearchByPrice = new raven.crazypanel.CrazyPanel();
        txt_fromPrice = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        txt_toPrice = new javax.swing.JTextField();
        lbl_SortMode = new javax.swing.JLabel();
        cbx_Sort = new javax.swing.JComboBox<>();
        crazyPanel_SelectAll = new raven.crazypanel.CrazyPanel();
        ckb_SelectAll = new javax.swing.JCheckBox();
        lbl_ItemSelected = new javax.swing.JLabel();
        crazyPanel_MenuBtn = new raven.crazypanel.CrazyPanel();
        lbl_ExportWord = new javax.swing.JLabel();
        btn_Add = new javax.swing.JButton();
        btn_refresh = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txt_Date = new javax.swing.JTextField();
        lbl_search = new javax.swing.JLabel();
        crazyPanel_Parent = new raven.crazypanel.CrazyPanel();
        jTabbedPane = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_Main = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_Main1 = new javax.swing.JTable();
        jScrollPane10 = new javax.swing.JScrollPane();
        tbl_Main2 = new javax.swing.JTable();
        jScrollPane11 = new javax.swing.JScrollPane();
        tbl_Main3 = new javax.swing.JTable();
        jScrollPane12 = new javax.swing.JScrollPane();
        tbl_Main4 = new javax.swing.JTable();
        jScrollPane13 = new javax.swing.JScrollPane();
        tbl_Main5 = new javax.swing.JTable();
        jScrollPane14 = new javax.swing.JScrollPane();
        tbl_Main6 = new javax.swing.JTable();

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

        cbx_Search.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tìm kiếm", "Mã hóa đơn", "Người đặt", "Email", "Điện thoại", "Trị giá", "Ngày tạo" }));
        cbx_Search.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbx_Searchcbx_SearchItemStateChanged(evt);
            }
        });

        txt_Search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_SearchKeyReleased(evt);
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

        txt_fromPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_fromPriceKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_fromPriceKeyTyped(evt);
            }
        });
        crazyPanel_SearchByPrice.add(txt_fromPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 120, 30));

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("To");
        crazyPanel_SearchByPrice.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 20, 30));

        txt_toPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_toPriceKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_toPriceKeyTyped(evt);
            }
        });
        crazyPanel_SearchByPrice.add(txt_toPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 120, 30));

        lbl_SortMode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-thick-arrow-pointing-up-30.png"))); // NOI18N
        lbl_SortMode.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_SortMode.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_SortModelbl_SortModeMouseClicked(evt);
            }
        });

        cbx_Sort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sắp xếp", "Ngày tạo", "Ngày giao", "Trị giá", "Người đặt", "Email", "Số điện thoại" }));
        cbx_Sort.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbx_Sortcbx_SortItemStateChanged(evt);
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
                ckb_SelectAllckb_SelectAllActionPerformed(evt);
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

        lbl_ExportWord.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/printer.png"))); // NOI18N
        lbl_ExportWord.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_ExportWord.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_ExportWordMouseClicked(evt);
            }
        });
        crazyPanel_MenuBtn.add(lbl_ExportWord);

        btn_Add.setText("Thêm");
        btn_Add.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AddActionPerformed(evt);
            }
        });
        crazyPanel_MenuBtn.add(btn_Add);

        btn_refresh.setText("Làm mới");
        btn_refresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshActionPerformed(evt);
            }
        });
        crazyPanel_MenuBtn.add(btn_refresh);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Quản lý đơn đặt hàng");

        txt_Date.addFocusListener(new java.awt.event.FocusAdapter() {
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_Search, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txt_Date, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(crazyPanel_MenuBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGroup(crazyPanel_MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, crazyPanel_MenuLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(crazyPanel_MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_Search, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbx_Search, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_Date, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33))
                    .addGroup(crazyPanel_MenuLayout.createSequentialGroup()
                        .addGroup(crazyPanel_MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(crazyPanel_MenuLayout.createSequentialGroup()
                                .addGap(51, 51, 51)
                                .addGroup(crazyPanel_MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_SortMode)
                                    .addComponent(cbx_Sort, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_search)))
                            .addGroup(crazyPanel_MenuLayout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addComponent(crazyPanel_SearchByPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(crazyPanel_MenuLayout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(crazyPanel_SelectAll, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );

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

        tbl_Main.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Mã hóa đơn", "Ngày tạo", "Tổng trị giá(VNĐ)", "Người đặt", "Số điện thoại", "Email", "Địa chỉ", "Thanh toán", ""
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
            tbl_Main.getColumnModel().getColumn(9).setResizable(false);
            tbl_Main.getColumnModel().getColumn(9).setPreferredWidth(30);
        }

        jTabbedPane.addTab("Chờ xử lý", jScrollPane2);

        tbl_Main1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Mã hóa đơn", "Ngày tạo", "Tổng trị giá(VNĐ)", "Người đặt", "Số điện thoại", "Email", "Địa chỉ", "Thanh toán", ""
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
        tbl_Main1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_Main1MouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbl_Main1MouseReleased(evt);
            }
        });
        jScrollPane4.setViewportView(tbl_Main1);
        if (tbl_Main1.getColumnModel().getColumnCount() > 0) {
            tbl_Main1.getColumnModel().getColumn(0).setPreferredWidth(20);
            tbl_Main1.getColumnModel().getColumn(9).setResizable(false);
            tbl_Main1.getColumnModel().getColumn(9).setPreferredWidth(30);
        }

        jTabbedPane.addTab("Đã xác nhận", jScrollPane4);

        tbl_Main2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Mã hóa đơn", "Ngày tạo", "Tổng trị giá(VNĐ)", "Người đặt", "Số điện thoại", "Email", "Địa chỉ", "Thanh toán", ""
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
        tbl_Main2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_Main2MouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbl_Main2MouseReleased(evt);
            }
        });
        jScrollPane10.setViewportView(tbl_Main2);
        if (tbl_Main2.getColumnModel().getColumnCount() > 0) {
            tbl_Main2.getColumnModel().getColumn(0).setPreferredWidth(20);
            tbl_Main2.getColumnModel().getColumn(9).setResizable(false);
            tbl_Main2.getColumnModel().getColumn(9).setPreferredWidth(30);
        }

        jTabbedPane.addTab("Đang vận chuyển", jScrollPane10);

        tbl_Main3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Mã hóa đơn", "Ngày tạo", "Tổng trị giá(VNĐ)", "Người đặt", "Số điện thoại", "Email", "Địa chỉ", "Thanh toán", ""
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
        tbl_Main3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_Main3MouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbl_Main3MouseReleased(evt);
            }
        });
        jScrollPane11.setViewportView(tbl_Main3);
        if (tbl_Main3.getColumnModel().getColumnCount() > 0) {
            tbl_Main3.getColumnModel().getColumn(0).setPreferredWidth(20);
            tbl_Main3.getColumnModel().getColumn(9).setResizable(false);
            tbl_Main3.getColumnModel().getColumn(9).setPreferredWidth(30);
        }

        jTabbedPane.addTab("Bị hủy", jScrollPane11);

        tbl_Main4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Mã hóa đơn", "Ngày tạo", "Tổng trị giá(VNĐ)", "Người đặt", "Số điện thoại", "Email", "Địa chỉ", "Thanh toán", ""
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
        tbl_Main4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_Main4MouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbl_Main4MouseReleased(evt);
            }
        });
        jScrollPane12.setViewportView(tbl_Main4);
        if (tbl_Main4.getColumnModel().getColumnCount() > 0) {
            tbl_Main4.getColumnModel().getColumn(0).setPreferredWidth(20);
            tbl_Main4.getColumnModel().getColumn(9).setResizable(false);
            tbl_Main4.getColumnModel().getColumn(9).setPreferredWidth(30);
        }

        jTabbedPane.addTab("Giao thành công", jScrollPane12);

        tbl_Main5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Mã hóa đơn", "Ngày tạo", "Tổng trị giá(VNĐ)", "Người đặt", "Số điện thoại", "Email", "Địa chỉ", "Thanh toán", ""
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
        tbl_Main5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_Main5MouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbl_Main5MouseReleased(evt);
            }
        });
        jScrollPane13.setViewportView(tbl_Main5);
        if (tbl_Main5.getColumnModel().getColumnCount() > 0) {
            tbl_Main5.getColumnModel().getColumn(0).setPreferredWidth(20);
            tbl_Main5.getColumnModel().getColumn(9).setResizable(false);
            tbl_Main5.getColumnModel().getColumn(9).setPreferredWidth(30);
        }

        jTabbedPane.addTab("Giao thất bại", jScrollPane13);

        tbl_Main6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Mã hóa đơn", "Ngày tạo", "Tổng trị giá(VNĐ)", "Người đặt", "Số điện thoại", "Email", "Địa chỉ", "Thanh toán", ""
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
        tbl_Main6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_Main6MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbl_Main6MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbl_Main6MouseReleased(evt);
            }
        });
        jScrollPane14.setViewportView(tbl_Main6);
        if (tbl_Main6.getColumnModel().getColumnCount() > 0) {
            tbl_Main6.getColumnModel().getColumn(0).setPreferredWidth(20);
            tbl_Main6.getColumnModel().getColumn(9).setResizable(false);
            tbl_Main6.getColumnModel().getColumn(9).setPreferredWidth(30);
        }

        jTabbedPane.addTab("Tại shop", jScrollPane14);

        javax.swing.GroupLayout crazyPanel_ParentLayout = new javax.swing.GroupLayout(crazyPanel_Parent);
        crazyPanel_Parent.setLayout(crazyPanel_ParentLayout);
        crazyPanel_ParentLayout.setHorizontalGroup(
            crazyPanel_ParentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crazyPanel_ParentLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane)
                .addContainerGap())
        );
        crazyPanel_ParentLayout.setVerticalGroup(
            crazyPanel_ParentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, crazyPanel_ParentLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(crazyPanel_Parent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(crazyPanel_Menu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(crazyPanel_Menu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(crazyPanel_Parent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(12, 12, 12))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AddActionPerformed
        currMode = Utils.ADD_MODE;
        new GUI_formOrder().setVisible(true);
    }//GEN-LAST:event_btn_AddActionPerformed

    private void cbx_Searchcbx_SearchItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbx_Searchcbx_SearchItemStateChanged
        if (evt.getStateChange() == 1) {
            int choice = cbx_Search.getSelectedIndex();
            switch (choice) {
                case 5:
                    txt_Search.setVisible(false);
                    crazyPanel_SearchByPrice.setVisible(true);
                    txt_Date.setVisible(false);
                    break;
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
    }//GEN-LAST:event_cbx_Searchcbx_SearchItemStateChanged

    private void lbl_SortModelbl_SortModeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_SortModelbl_SortModeMouseClicked
        if (!sortMode) {
            sortMode = true;
            lbl_SortMode.setIcon(asc);
            selectModelSortASC();
        } else {
            sortMode = false;
            lbl_SortMode.setIcon(desc);
            selectModelSortDESC();
        }
    }//GEN-LAST:event_lbl_SortModelbl_SortModeMouseClicked

    private void cbx_Sortcbx_SortItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbx_Sortcbx_SortItemStateChanged
        if (evt.getStateChange() == 1) {
            selectModelSortASC();
            lbl_SortMode.setIcon(asc);
        }
    }//GEN-LAST:event_cbx_Sortcbx_SortItemStateChanged

    private void ckb_SelectAllckb_SelectAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckb_SelectAllckb_SelectAllActionPerformed
        switch (jTabbedPane.getSelectedIndex()) {
            case 0:
                setTableCheckbox(mainModel, tbl_Main);
                break;
            case 1:
                setTableCheckbox(mainModel1, tbl_Main1);
                break;
            case 2:
                setTableCheckbox(mainModel2, tbl_Main2);
                break;
            case 3:
                setTableCheckbox(mainModel3, tbl_Main3);
                break;
            case 4:
                setTableCheckbox(mainModel4, tbl_Main4);
                break;
            case 5:
                setTableCheckbox(mainModel5, tbl_Main5);
                break;
            case 6:
                setTableCheckbox(mainModel6, tbl_Main6);
                break;
        }
    }//GEN-LAST:event_ckb_SelectAllckb_SelectAllActionPerformed

    private void tbl_MainMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_MainMouseClicked
        onClickTable(tbl_Main, mainModel);
        Utils.selectedOrder = list.get(tbl_Main.getSelectedRow());
        listDetail = bus_orderdetail.getList();
        Utils.selectedOrder.setDetails(listDetail);
        setSelectedAll(mainModel, tbl_Main);
    }//GEN-LAST:event_tbl_MainMouseClicked

    private void tbl_MainMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_MainMouseReleased
        onReleaseTable(tbl_Main, evt, mouseClick);
        setSelectedAll(mainModel, tbl_Main);
    }//GEN-LAST:event_tbl_MainMouseReleased

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        clear();
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void tbl_Main1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_Main1MouseClicked
        onClickTable(tbl_Main1, mainModel);
        Utils.selectedOrder = list.get(tbl_Main1.getSelectedRow());
        listDetail = bus_orderdetail.getList();
        Utils.selectedOrder.setDetails(listDetail);
        setSelectedAll(mainModel1, tbl_Main1);
    }//GEN-LAST:event_tbl_Main1MouseClicked

    private void tbl_Main1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_Main1MouseReleased
        onReleaseTable(tbl_Main1, evt, mouseClick1);
        setSelectedAll(mainModel1, tbl_Main1);
    }//GEN-LAST:event_tbl_Main1MouseReleased

    private void tbl_Main2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_Main2MouseClicked
        onClickTable(tbl_Main2, mainModel2);
        Utils.selectedOrder = list.get(tbl_Main2.getSelectedRow());
        listDetail = bus_orderdetail.getList();
        Utils.selectedOrder.setDetails(listDetail);
        setSelectedAll(mainModel2, tbl_Main2);
    }//GEN-LAST:event_tbl_Main2MouseClicked

    private void tbl_Main2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_Main2MouseReleased
        onReleaseTable(tbl_Main2, evt, mouseClick2);
        setSelectedAll(mainModel2, tbl_Main2);
    }//GEN-LAST:event_tbl_Main2MouseReleased

    private void tbl_Main3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_Main3MouseClicked
        onClickTable(tbl_Main3, mainModel3);
        Utils.selectedOrder = list.get(tbl_Main3.getSelectedRow());
        listDetail = bus_orderdetail.getList();
        Utils.selectedOrder.setDetails(listDetail);
        setSelectedAll(mainModel3, tbl_Main3);
    }//GEN-LAST:event_tbl_Main3MouseClicked

    private void tbl_Main3MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_Main3MouseReleased
        onReleaseTable(tbl_Main3, evt, mouseClick3);
        setSelectedAll(mainModel3, tbl_Main3);
    }//GEN-LAST:event_tbl_Main3MouseReleased

    private void tbl_Main4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_Main4MouseClicked
        onClickTable(tbl_Main4, mainModel4);
        Utils.selectedOrder = list.get(tbl_Main4.getSelectedRow());
        listDetail = bus_orderdetail.getList();
        Utils.selectedOrder.setDetails(listDetail);
        setSelectedAll(mainModel4, tbl_Main4);
    }//GEN-LAST:event_tbl_Main4MouseClicked

    private void tbl_Main4MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_Main4MouseReleased
        onReleaseTable(tbl_Main4, evt, mouseClick4);
        setSelectedAll(mainModel4, tbl_Main4);
    }//GEN-LAST:event_tbl_Main4MouseReleased

    private void tbl_Main5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_Main5MouseClicked
        onClickTable(tbl_Main5, mainModel5);
        Utils.selectedOrder = list.get(tbl_Main5.getSelectedRow());
        listDetail = bus_orderdetail.getList();
        Utils.selectedOrder.setDetails(listDetail);
        setSelectedAll(mainModel5, tbl_Main5);
    }//GEN-LAST:event_tbl_Main5MouseClicked

    private void tbl_Main5MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_Main5MouseReleased
        onReleaseTable(tbl_Main5, evt, mouseClick5);
        setSelectedAll(mainModel5, tbl_Main5);
    }//GEN-LAST:event_tbl_Main5MouseReleased

    private void tbl_Main6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_Main6MouseClicked
        onClickTable(tbl_Main6, mainModel6);
        Utils.selectedOrder = list.get(tbl_Main6.getSelectedRow());
        listDetail = bus_orderdetail.getList();
        Utils.selectedOrder.setDetails(listDetail);
        setSelectedAll(mainModel6, tbl_Main6);
    }//GEN-LAST:event_tbl_Main6MouseClicked

    private void tbl_Main6MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_Main6MouseReleased
        onReleaseTable(tbl_Main6, evt, mouseClick6);
        setSelectedAll(mainModel6, tbl_Main6);
    }//GEN-LAST:event_tbl_Main6MouseReleased

    private void txt_fromPriceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_fromPriceKeyReleased

    }//GEN-LAST:event_txt_fromPriceKeyReleased

    private void txt_toPriceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_toPriceKeyReleased

    }//GEN-LAST:event_txt_toPriceKeyReleased

    private void txt_DateFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_DateFocusLost

    }//GEN-LAST:event_txt_DateFocusLost

    private void txt_SearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_SearchKeyReleased

    }//GEN-LAST:event_txt_SearchKeyReleased

    private void txt_fromPriceKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_fromPriceKeyTyped
        char c = evt.getKeyChar();

        if (Character.isAlphabetic(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_txt_fromPriceKeyTyped

    private void txt_toPriceKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_toPriceKeyTyped
        char c = evt.getKeyChar();

        if (Character.isAlphabetic(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_txt_toPriceKeyTyped

    private void jTabbedPaneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPaneMouseClicked
        switch (jTabbedPane.getSelectedIndex()) {
            case 0:
//                selectedItem = countRow(mainModel, tbl_Main);
//                if (mainModel.getRowCount() != 0 && selectedItem == mainModel.getRowCount()) {
//                    ckb_SelectAll.setSelected(true);
//                } else {
//                    ckb_SelectAll.setSelected(false);
//                }
                list = bus_order.getList(0);
                renderMainTable(list, mainModel);
                break;
            case 1:
//                selectedItem = countRow(mainModel1, tbl_Main1);
//                if (mainModel1.getRowCount() != 0 && selectedItem == mainModel1.getRowCount()) {
//                    ckb_SelectAll.setSelected(true);
//                } else {
//                    ckb_SelectAll.setSelected(false);
//                }
                list = bus_order.getList(1);
                renderMainTable(list, mainModel1);
                break;
            case 2:
//                selectedItem = countRow(mainModel2, tbl_Main2);
//                if (mainModel2.getRowCount() != 0 && selectedItem == mainModel2.getRowCount()) {
//                    ckb_SelectAll.setSelected(true);
//                } else {
//                    ckb_SelectAll.setSelected(false);
//                }
                list = bus_order.getList(2);
                renderMainTable(list, mainModel2);
                break;
            case 3:
//                selectedItem = countRow(mainModel3, tbl_Main3);
//                if (mainModel3.getRowCount() != 0 && selectedItem == mainModel3.getRowCount()) {
//                    ckb_SelectAll.setSelected(true);
//                } else {
//                    ckb_SelectAll.setSelected(false);
//                }
                list = bus_order.getList(3);
                renderMainTable(list, mainModel3);
                break;
            case 4:
//                selectedItem = countRow(mainModel4, tbl_Main4);
//                if (mainModel4.getRowCount() != 0 && selectedItem == mainModel4.getRowCount()) {
//                    ckb_SelectAll.setSelected(true);
//                } else {
//                    ckb_SelectAll.setSelected(false);
//                }
                list = bus_order.getList(4);
                renderMainTable(list, mainModel4);
                break;
            case 5:
//                selectedItem = countRow(mainModel5, tbl_Main5);
//                if (mainModel5.getRowCount() != 0 && selectedItem == mainModel5.getRowCount()) {
//                    ckb_SelectAll.setSelected(true);
//                } else {
//                    ckb_SelectAll.setSelected(false);
//                }
                list = bus_order.getList(5);
                renderMainTable(list, mainModel5);
                break;
            case 6:
//                selectedItem = countRow(mainModel6, tbl_Main6);
//                if (mainModel6.getRowCount() != 0 && selectedItem == mainModel6.getRowCount()) {
//                    ckb_SelectAll.setSelected(true);
//                } else {
//                    ckb_SelectAll.setSelected(false);
//                }
                list = bus_order.getList(6);
                renderMainTable(list, mainModel6);
                break;
        }
        ckb_SelectAll.setSelected(false);
        updateItemselected();
    }//GEN-LAST:event_jTabbedPaneMouseClicked

    private void lbl_searchlbl_SortModeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_searchlbl_SortModeMouseClicked
        int selected = jTabbedPane.getSelectedIndex();
        switch (selected) {
            case 0:
                search(mainModel);
                break;
            case 1:
                search(mainModel1);
                break;
            case 2:
                search(mainModel2);
                break;
            case 3:
                search(mainModel3);
                break;
            case 4:
                search(mainModel4);
                break;
            case 5:
                search(mainModel5);
                break;
            case 6:
                search(mainModel6);
                break;
        }
    }//GEN-LAST:event_lbl_searchlbl_SortModeMouseClicked

    private void tbl_Main6MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_Main6MousePressed
        mouseClick6 = tbl_Main6.rowAtPoint(evt.getPoint());
    }//GEN-LAST:event_tbl_Main6MousePressed

    private void tbl_MainMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_MainMousePressed
        mouseClick = tbl_Main.rowAtPoint(evt.getPoint());
    }//GEN-LAST:event_tbl_MainMousePressed

    private void lbl_ExportWordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_ExportWordMouseClicked
        print();
    }//GEN-LAST:event_lbl_ExportWordMouseClicked

    private void getSelectedOrder() {
        switch (jTabbedPane.getSelectedIndex()) {
            case 0:
                list = bus_order.getList(0);
                Utils.selectedOrder = list.get(tbl_Main.getSelectedRow());
                break;
            case 1:
                list = bus_order.getList(1);
                Utils.selectedOrder = list.get(tbl_Main1.getSelectedRow());
                break;
            case 2:
                list = bus_order.getList(2);
                Utils.selectedOrder = list.get(tbl_Main2.getSelectedRow());
                break;
            case 3:
                list = bus_order.getList(3);
                Utils.selectedOrder = list.get(tbl_Main3.getSelectedRow());
                break;
            case 4:
                list = bus_order.getList(4);
                Utils.selectedOrder = list.get(tbl_Main4.getSelectedRow());
                break;
            case 5:
                list = bus_order.getList(5);
                Utils.selectedOrder = list.get(tbl_Main5.getSelectedRow());
                break;
            case 6:
                list = bus_order.getList(6);
                Utils.selectedOrder = list.get(tbl_Main6.getSelectedRow());
                break;
        }
        Utils.selectedOrder.setDetails(bus_orderdetail.getList());
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
        updateItemselected();
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
        updateItemselected();
    }

    private void updateItemselected() {
        if(selectedItem == 1){
            lbl_ItemSelected.setVisible(true);
            lbl_ItemSelected.setText("Đã chọn " + selectedItem + " dòng");
            lbl_ExportWord.setEnabled(true);
        }
        else if (selectedItem > 1) {
            lbl_ItemSelected.setVisible(true);
            lbl_ItemSelected.setText("Đã chọn " + selectedItem + " dòng");
            lbl_ExportWord.setEnabled(false);
        }
        else if (selectedItem == 0) {
            lbl_ItemSelected.setVisible(false); 
            lbl_ExportWord.setEnabled(false);
        }
    }
    ///////////
    
    //Search && Sort
    private void search(DefaultTableModel model) throws NumberFormatException {
        //Tìm kiếm
        //Mã hóa đơn
        //Người đặt
        //Email
        //Điện thoại
        //Trị giá
        //Ngày tạo

        String keyword = txt_Search.getText().trim();
        switch (cbx_Search.getSelectedIndex()) {
            case 0:
                notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Vui lòng chọn chức năng tìm kiếm!!!");
                break;
            case 1:
                list = bus_order.searchByOrderID(list, keyword);
                searchNotification(list, model);
                break;
            case 2:
                list = bus_order.searchByFullName(list, keyword);
                searchNotification(list, model);
                break;
            case 3:
                list = bus_order.searchByEmail(list, keyword);
                searchNotification(list, model);
                break;
            case 4:
                list = bus_order.searchByPhone(list, keyword);
                searchNotification(list, model);
                break;
            case 5:
                String fromPriceString = txt_fromPrice.getText().trim();
                String toPriceString = txt_toPrice.getText().trim();

                if (!fromPriceString.isEmpty() && !toPriceString.isEmpty()) {
                    double fromPrice = Double.parseDouble(fromPriceString);
                    double toPrice = Double.parseDouble(toPriceString);
                    list = bus_order.searchByOrderPrice(list, fromPrice, toPrice);
                    searchNotification(list, model);
                }
                break;
            case 6:
                if (txt_Date.getText().toString().contains("to")) {
                    String[] date = txt_Date.getText().toString().split(" to ");
                    String[] from = date[0].split("/");
                    String[] to = date[1].split("/");

                    DTO_Date fromDate = new DTO_Date(Integer.parseInt(from[2]), Integer.parseInt(from[1]), Integer.parseInt(from[0]), 0, 0, 0);
                    DTO_Date toDate = new DTO_Date(Integer.parseInt(to[2]), Integer.parseInt(to[1]), Integer.parseInt(to[0]), 0, 0, 0);
                    list = bus_order.searchByInitDate(list, fromDate, toDate);
                    searchNotification(list, model);
                }
                break;
        }
    }

    private void searchNotification(ArrayList<DTO_Order> listod, DefaultTableModel model) {
        if (listod.isEmpty()) {
            notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Tìm kiếm thất bại");
        } else {
            notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "TÌm kiếm thành công");
            renderMainTable(listod, model);
        }
    }
    
    private void selectModelSortASC() {
        switch (jTabbedPane.getSelectedIndex()) {
            case 0:
                sortASC(mainModel);
                break;
            case 1:
                sortASC(mainModel1);
                break;
            case 2:
                sortASC(mainModel2);
                break;
            case 3:
                sortASC(mainModel3);
                break;
            case 4:
                sortASC(mainModel4);
                break;
            case 5:
                sortASC(mainModel5);
                break;
            case 6:
                sortASC(mainModel6);
                break;
        }
    }

    private void selectModelSortDESC() {
        switch (jTabbedPane.getSelectedIndex()) {
            case 0:
                sortDESC(mainModel);
                break;
            case 1:
                sortDESC(mainModel1);
                break;
            case 2:
                sortDESC(mainModel2);
                break;
            case 3:
                sortDESC(mainModel3);
                break;
            case 4:
                sortDESC(mainModel4);
                break;
            case 5:
                sortDESC(mainModel5);
                break;
            case 6:
                sortDESC(mainModel6);
                break;
        }
    }
    
    private void sortASC(DefaultTableModel model) {
        //Sắp xếp
        //Ngày tạo
        //Ngày giao
        //Trị giá
        //Người đặt
        //Email
        //Thanh toán
        //Số điện thoại
        switch (cbx_Sort.getSelectedIndex()) {
            case 1:
                list = bus_order.sortByInitDateASC(list);
                renderMainTable(list, model);
                break;
            case 2:
                list = bus_order.sortByDeliDateASC(list);
                renderMainTable(list, model);
                break;
            case 3:
                list = bus_order.sortByOderPriceASC(list);
                renderMainTable(list, model);
                break;
            case 4:
                list = bus_order.sortByFullNameASC(list);
                renderMainTable(list, model);
                break;
            case 5:
                list = bus_order.sortByEmailASC(list);
                renderMainTable(list, model);
                break;
            case 6:
                list = bus_order.sortByPhoneASC(list);
                renderMainTable(list, model);
                break;
        }
    }

    private void sortDESC(DefaultTableModel model) {
        switch (cbx_Sort.getSelectedIndex()) {
            case 1:
                list = bus_order.sortByInitDateDESC(list);
                renderMainTable(list, model);
                break;
            case 2:
                list = bus_order.sortByDeliDateDESC(list);
                renderMainTable(list, model);
                break;
            case 3:
                list = bus_order.sortByOrderPriceDESC(list);
                renderMainTable(list, model);
                break;
            case 4:
                list = bus_order.sortByFullNameDESC(list);
                renderMainTable(list, model);
                break;
            case 5:
                list = bus_order.sortByEmailDESC(list);
                renderMainTable(list, model);
                break;
            case 6:
                list = bus_order.sortByPhoneDESC(list);
                renderMainTable(list, model);
                break;
        }
    }
    //////////

    private void renderMainTable(ArrayList<DTO_Order> list, DefaultTableModel model) {
        model.setRowCount(0);
        for (DTO_Order order : list) {
            Object data[] = new Object[10];
            data[0] = false;
            data[1] = order.getId();
            data[2] = order.getInitdate().format();
            data[3] = decimalFormat.format(order.getPrice());
            data[4] = order.getUser().getFullname();
            data[5] = order.getPhone();
            data[6] = order.getUser().getEmail();
            data[7] = order.getAddress();
            String pay = "";
            if (order.getPay() == 1) {
                pay = "Chuyển khoản";
            } else {
                pay = "Tiền mặt";
            }
            data[8] = pay;

//            String status = "Chưa xử lý";
//            switch (order.getStatus()) {
//                case 1:
//                    status = "Đã xác nhận";
//                    break;
//                case 2:
//                    status = "Đang vận chuyển";
//                    break;
//                case 3:
//                    status = "Bị hủy";
//                    break;
//                case 4:
//                    status = "Giao thành công";
//                    break;
//                case 5:
//                    status = "Giao thất bại";
//                    break;
//                case 6:
//                    status = "Tại shop";
//                    break;
//            }
//            data[9] = status;
            model.addRow(data);
        }
    }

    private void clear() {
        jTabbedPane.setSelectedIndex(0);
        list = bus_order.getList(0);
        renderMainTable(list, mainModel);
        //checkbox
        cbx_Search.setSelectedIndex(0);
        cbx_Sort.setSelectedIndex(0);
        cbx_Search.setSelectedIndex(0);
        ckb_SelectAll.setSelected(false);
        //textfield
        txt_Search.setVisible(true);
        txt_Date.setVisible(false);
        //crazypanel
        crazyPanel_SearchByPrice.setVisible(false);
        //label
        lbl_SortMode.setIcon(asc);
        lbl_ItemSelected.setVisible(false);
        //table
        tbl_Main.clearSelection();
        //button
        lbl_ExportWord.setEnabled(false);
        sortMode = true;
    }

    private String getUnit(int value){
        String result = value + " GB";
            if (value >= 1000) {
                result = (value / 1000) + " T";
            }
        return result;
    }
    private void printWord(XWPFDocument document) {
        XWPFParagraph titleGraph = document.createParagraph();
        XWPFTableRow row = null;
        XWPFTableCell cell = null;
        XWPFParagraph paragraph = null;
        XWPFRun run = null;
        titleGraph.setAlignment(ParagraphAlignment.CENTER);
        //Viết "Hoá đơn"
        XWPFRun titleRun = titleGraph.createRun();
        
        titleRun.setBold(true);
        titleRun.setText("HOÁ ĐƠN BÁN HÀNG");
        titleRun.setFontSize(14);
        titleRun.setFontFamily("Times New Roman");
        
        paragraph = document.createParagraph();
        run = paragraph.createRun();
        run.setFontFamily("Times New Roman");
        run = paragraph.createRun();
        run.setFontSize(13);
        run.setText("Hoá đơn được tạo Ngày " + Utils.selectedOrder.getInitdate().getDd() + " Tháng " + Utils.selectedOrder.getInitdate().getMm()+ " Năm " + Utils.selectedOrder.getInitdate().getYyyy());
        run.setFontFamily("Times New Roman");
        //Vẽ bản thông tin
        XWPFTable tbl1 = document.createTable();
        tbl1.setWidth("100%");
        row = tbl1.getRow(0);
        cell = row.getCell(0);
        cell.setWidth("50%");
        paragraph = cell.getParagraphArray(0);
        run = paragraph.createRun();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        run.setFontFamily("Times New Roman");
        run.setFontSize(13);
        run.setText("TỪ: Cửa hàng Mobile Shop");
        
        cell.addParagraph();
        paragraph = cell.getParagraphArray(1);
        run = paragraph.createRun();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        run.setFontFamily("Times New Roman");
        run.setFontSize(13);
        run.setText("Địa chỉ: 123 phường ABC Quận 5");
        
        cell.addParagraph();
        paragraph = cell.getParagraphArray(2);
        run = paragraph.createRun();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        run.setFontFamily("Times New Roman");
        run.setFontSize(13);
        run.setText("SĐT: 012345678");
        
        row.createCell();
        cell = row.getCell(1);
        cell.setWidth("50%");
        paragraph = cell.getParagraphArray(0);
        run = paragraph.createRun();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        run.setBold(false);
        run.setFontFamily("Times New Roman");
        run.setFontSize(13);
        run.setText("ĐẾN: Anh/Chị: " + Utils.selectedOrder.getUser().getFullname());
        
        cell.addParagraph();
        paragraph = cell.getParagraphArray(1);
        run = paragraph.createRun();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        run.setBold(false);
        run.setFontFamily("Times New Roman");
        run.setFontSize(13);
        run.setText("Địa chỉ: " + Utils.selectedOrder.getUser().getAddress());
        
        cell.addParagraph();
        paragraph = cell.getParagraphArray(2);
        run = paragraph.createRun();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        run.setBold(false);
        run.setFontFamily("Times New Roman");
        run.setFontSize(13);
        run.setText("SĐT: " + Utils.selectedOrder.getUser().getPhone());

        paragraph = document.createParagraph();
        paragraph = document.createParagraph();
        run.setFontFamily("Times New Roman");
        run = paragraph.createRun();
        run.setFontSize(13);
        run.setText("Thông tin sản phẩm");
        run.setFontFamily("Times New Roman");
        
        //Tạo chi tiết hoá đơn
        XWPFTable table = document.createTable();
        table.setWidth("100%");
        row = table.getRow(0);
        cell = row.getCell(0);
        cell.setWidth("7%");
        paragraph = cell.getParagraphArray(0);
        run = paragraph.createRun();
        run.setBold(true);
        run.setFontSize(13);
        run.setFontFamily("Times New Roman");
        run.setText("STT");
        paragraph.setAlignment(ParagraphAlignment.CENTER);

        cell = row.createCell();

        paragraph = cell.getParagraphArray(0);
        run = paragraph.createRun();
        run.setBold(true);
        run.setFontSize(13);
        run.setFontFamily("Times New Roman");
        run.setText("Tên Hàng");
        paragraph.setAlignment(ParagraphAlignment.CENTER);

        cell = row.createCell();

        paragraph = cell.getParagraphArray(0);
        run = paragraph.createRun();
        run.setBold(true);
        run.setFontSize(13);
        run.setFontFamily("Times New Roman");
        run.setText("Số lượng");
        paragraph.setAlignment(ParagraphAlignment.CENTER);

        cell = row.createCell();

        paragraph = cell.getParagraphArray(0);
        run = paragraph.createRun();
        run.setBold(true);
        run.setFontSize(13);
        run.setFontFamily("Times New Roman");
        run.setText("Đơn giá");
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        cell = row.createCell();

        paragraph = cell.getParagraphArray(0);
        run = paragraph.createRun();
        run.setBold(true);
        run.setFontSize(13);
        run.setFontFamily("Times New Roman");
        run.setText("Thành tiền");
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        
        for (int i = 0; i < Utils.selectedOrder.getDetails().size(); i++) {
            row = table.createRow();
            cell = row.getCell(0);
            paragraph = cell.getParagraphArray(0);
            run = paragraph.createRun();
            paragraph.setAlignment(ParagraphAlignment.CENTER);
            run.setFontFamily("Times New Roman");
            run.setFontSize(13);
            run.setText(i + 1 + "");

            cell = row.getCell(1);
            paragraph = cell.getParagraphArray(0);
            run = paragraph.createRun();
            paragraph.setAlignment(ParagraphAlignment.CENTER);
            run.setFontFamily("Times New Roman");
            run.setFontSize(13);
            run.setText(Utils.selectedOrder.getDetails().get(i).getOption().getProductname() + ", Rom: " + getUnit(Utils.selectedOrder.getDetails().get(i).getOption().getRom()) + ", Ram: " + getUnit(Utils.selectedOrder.getDetails().get(i).getOption().getRam()) + ", Màu: " + Utils.selectedOrder.getDetails().get(i).getOption().getColor().getName());

            cell = row.getCell(2);
            paragraph = cell.getParagraphArray(0);
            run = paragraph.createRun();
            paragraph.setAlignment(ParagraphAlignment.CENTER);
            run.setFontFamily("Times New Roman");
            run.setFontSize(13);
            run.setText(Utils.selectedOrder.getDetails().get(i).getQuantity() + "");

            cell = row.getCell(3);
            paragraph = cell.getParagraphArray(0);
            run = paragraph.createRun();
            paragraph.setAlignment(ParagraphAlignment.CENTER);
            run.setFontFamily("Times New Roman");
            run.setFontSize(13);
            run.setText(" " + decimalFormat.format(Utils.selectedOrder.getDetails().get(i).getOption().getImportprice()) + "VNĐ");

            cell = row.getCell(4);
            paragraph = cell.getParagraphArray(0);
            run = paragraph.createRun();
            paragraph.setAlignment(ParagraphAlignment.CENTER);
            run.setFontFamily("Times New Roman");
            run.setFontSize(13);
            run.setText(" " +decimalFormat.format(Utils.selectedOrder.getDetails().get(i).getTotalprice()) + "VNĐ");
        }
        row = table.createRow();
        row.getCell(0).getCTTc().addNewTcPr();
        row.getCell(0).getCTTc().getTcPr().addNewGridSpan();
        row.getCell(0).getCTTc().getTcPr().getGridSpan().setVal(BigInteger.valueOf(2L));
        cell = row.getCell(0);

        paragraph = cell.getParagraphArray(0);
        run = paragraph.createRun();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        run.setFontFamily("Times New Roman");
        run.setFontSize(13);
        run.setBold(true);
        run.setText("Tổng cộng: ");
        cell = row.getCell(1);
        paragraph = cell.getParagraphArray(0);
        run = paragraph.createRun();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        run.setFontFamily("Times New Roman");
        run.setFontSize(13);
        run.setBold(true);
        run.setText(decimalFormat.format(Utils.selectedOrder.getPrice()) + "VNĐ");
        
        paragraph = document.createParagraph();
        run = paragraph.createRun();
        String a = decimalFormat.format(Utils.selectedOrder.getPrice());
        DocSo docso = new DocSo();
        run.setText("Thành chữ: "+docso.In(a.replaceAll(",", "")) + "Việt Nam đồng");
        run.setFontFamily("Times New Roman");
        run.setFontSize(13);
        XWPFTable table1 = document.createTable();
        table1.removeBorders();
        table1.setWidth("100%");
        row = table1.getRow(0);
        row.createCell();
        cell = row.getCell(1);
        paragraph = cell.getParagraphArray(0);
        run = paragraph.createRun();
        run.setItalic(true);
        
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        run.setFontFamily("Times New Roman");
        run.setFontSize(13);
        LocalDateTime today = LocalDateTime.now();
        run.setText("In Ngày " + today.getDayOfMonth() + " Tháng " + today.getMonthValue()+ " Năm " + today.getYear());
        
        row = table1.createRow();
        cell = row.getCell(1);
        cell.setWidth("50%");
        paragraph = cell.getParagraphArray(0);
        run = paragraph.createRun();
        run.setBold(true);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        run.setText("Khách hàng ký nhận");
        run.setFontFamily("Times New Roman");
        run.setFontSize(13);
        row = table1.createRow();
    }

    private void printPdf(File a){
        System.out.println(a);
        try {
            Document docx = new Document(a+"");
            String b = (a+"").replace(getExtensionFile(a), "pdf");
            docx.save(b);
        } catch (Exception ex) {
            ex.printStackTrace();
        }        
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
    
    private void print() {
        if (JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xuất ra file word!") == JOptionPane.YES_OPTION) {
            JFileChooser filechooser = new JFileChooser();
            filechooser.setCurrentDirectory(new File("D:\\"));
            int response = filechooser.showSaveDialog(null);
            if (response == filechooser.APPROVE_OPTION) {
                File file = new File(filechooser.getSelectedFile().getAbsolutePath());
                System.out.println(file);
                if (getExtensionFile(file).equals("docx")) {
                    if (file.exists() == false) {
                        try {
                            //Khởi tạo document
                            XWPFDocument document = new XWPFDocument();
                            printWord(document);
                            try {
                                FileOutputStream fop = new FileOutputStream(file);
                                document.write(fop);
                                fop.close();
                                notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Bạn đã xuất hoá đơn thành công!!!");
                                if (JOptionPane.showConfirmDialog(this, "Bạn có muốn xuất ra file pdf luôn k???") == JOptionPane.YES_OPTION) {
                                    printPdf(file);
                                    notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Bạn xuất pdf thành công!!!");
                                }
                                Desktop desktop = Desktop.getDesktop();
                                desktop.open(file);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            JOptionPane.showMessageDialog(this, "File đang được mở");
                            return;
                        }
                    } else {
                        if (JOptionPane.showConfirmDialog(this, "Tên file bạn đặt đã bị trùng.\nBạn có chắc muốn ghi đè lên không???") == JOptionPane.YES_OPTION) {

                            try {
                                //Khởi tạo document
                                XWPFDocument document = new XWPFDocument();
                                printWord(document);
                                try {
                                    file.delete();
                                    FileOutputStream fop = new FileOutputStream(file);
                                    document.write(fop);
                                    fop.close();
                                    notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Bạn đã xuất hoá đơn thành công!!!");
                                    if (JOptionPane.showConfirmDialog(this, "Bạn có muốn xuất ra file pdf luôn k???") == JOptionPane.YES_OPTION) {
                                        printPdf(file);
                                        notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Bạn xuất pdf thành công!!!");
                                    }
                                    Desktop desktop = Desktop.getDesktop();
                                    desktop.open(file);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                JOptionPane.showMessageDialog(this, "File đang được mở");
                                return;
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Bạn phải đặt tên file với extention là .docx");
                }
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Add;
    private javax.swing.JButton btn_refresh;
    private javax.swing.JComboBox<String> cbx_Search;
    private javax.swing.JComboBox<String> cbx_Sort;
    private javax.swing.JCheckBox ckb_SelectAll;
    private raven.crazypanel.CrazyPanel crazyPanel_Menu;
    private raven.crazypanel.CrazyPanel crazyPanel_MenuBtn;
    private raven.crazypanel.CrazyPanel crazyPanel_Parent;
    private raven.crazypanel.CrazyPanel crazyPanel_SearchByPrice;
    private raven.crazypanel.CrazyPanel crazyPanel_SelectAll;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JLabel lbl_ExportWord;
    private javax.swing.JLabel lbl_ItemSelected;
    private javax.swing.JLabel lbl_SortMode;
    private javax.swing.JLabel lbl_search;
    private javax.swing.JTable tbl_Main;
    private javax.swing.JTable tbl_Main1;
    private javax.swing.JTable tbl_Main2;
    private javax.swing.JTable tbl_Main3;
    private javax.swing.JTable tbl_Main4;
    private javax.swing.JTable tbl_Main5;
    private javax.swing.JTable tbl_Main6;
    private javax.swing.JTextField txt_Date;
    private javax.swing.JTextField txt_Search;
    private javax.swing.JTextField txt_fromPrice;
    private javax.swing.JTextField txt_toPrice;
    // End of variables declaration//GEN-END:variables
}
