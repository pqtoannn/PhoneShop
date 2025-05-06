
package GUI;

import static App.Application.notifications;
import BUS.BUS_ExportDetail;
import BUS.BUS_ExportInvoice;
import Custom.InvoiceDetailColorCellRender;
import DTO.DTO_Date;
import DTO.DTO_DefaultInvoice;
import DTO.DTO_InvoiceDetail;
import Utils.Utils;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import Custom.datechooser.DateBetween;
import Custom.datechooser.listener.DateChooserAction;
import Custom.datechooser.listener.DateChooserAdapter;
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
import Custom.datechooser.DateChooser;
import DTO.DocSo;
import com.aspose.words.Document;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.time.LocalDateTime;
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


public class GUI_tableExportInvoice extends javax.swing.JPanel {
    private ImageIcon asc = new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-thick-arrow-pointing-up-30.png"));
    private ImageIcon desc = new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-thick-arrow-pointing-down-30.png"));
    
    private DefaultTableModel mainModel;
    private DefaultTableModel detailModel;
    
    private DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
    private int selectedItem = 0;
    private boolean sortMode = true;
    
    private BUS_ExportInvoice bus_exinvoice = new BUS_ExportInvoice();
    private BUS_ExportDetail bus_exdetail = new BUS_ExportDetail();
    
    private ArrayList <DTO_DefaultInvoice> list = new ArrayList <DTO_DefaultInvoice> ();
    private ArrayList <DTO_InvoiceDetail> listDetail = new ArrayList <DTO_InvoiceDetail> ();
    
    int mouseClick = -1;
    
    public GUI_tableExportInvoice() {
        initComponents();
        
        mainModel = (DefaultTableModel) tbl_Main.getModel();
        detailModel = (DefaultTableModel) tbl_Detail.getModel();
        
        clear();
        
        //CUSTOMCODE ĐỪNG ĐỤNG VÔ!!!
        btn_refresh.setIcon(new FlatSVGIcon("assets/refresh.svg", 0.25f));
        txt_Search.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new FlatSVGIcon("assets/search.svg", 0.35f));
        txt_fromPrice.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new FlatSVGIcon("assets/search.svg", 0.35f));
        txt_toPrice.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new FlatSVGIcon("assets/search.svg", 0.35f));
        txt_Date.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new FlatSVGIcon("assets/search.svg", 0.35f));
        lbl_search.setIcon(new FlatSVGIcon("assets/search.svg", 0.5f));
        
        applyTableStyleMainTable(tbl_Main);
        applyTableStyleDetailTable(tbl_Detail);
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
    }
    
    private void applyTableStyleDetailTable(JTable table) {
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
        table.getTableHeader().setDefaultRenderer(getAlignmentCellRenderDetailTable(table.getTableHeader().getDefaultRenderer(), true));
        table.setDefaultRenderer(Object.class, getAlignmentCellRenderDetailTable(table.getDefaultRenderer(Object.class), false));
    
        table.getColumnModel().getColumn(1).setCellRenderer(new InvoiceDetailColorCellRender());
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
 
    private TableCellRenderer getAlignmentCellRenderDetailTable(TableCellRenderer oldRender, boolean header) {
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
        jLabel1 = new javax.swing.JLabel();
        crazyPanel_MenuBtn = new raven.crazypanel.CrazyPanel();
        lbl_ExportWord = new javax.swing.JLabel();
        btn_refresh = new javax.swing.JButton();
        txt_Date = new javax.swing.JTextField();
        lbl_search = new javax.swing.JLabel();
        crazyPanel_Parent = new raven.crazypanel.CrazyPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_Main = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_Detail = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

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
        crazyPanel_Menu.setPreferredSize(new java.awt.Dimension(1012, 152));

        cbx_Search.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tìm kiếm", "Mã hóa đơn", "Người tạo", "Trị giá", "Ngày tạo" }));
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

        cbx_Sort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sắp xếp", "Ngày tạo", "Trị giá", "Người tạo" }));
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

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Quản lý hóa đơn xuất kho");

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

        btn_refresh.setText("Làm mới");
        btn_refresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshActionPerformed(evt);
            }
        });
        crazyPanel_MenuBtn.add(btn_refresh);

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
                    .addComponent(crazyPanel_MenuBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(26, 26, 26)
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

        tbl_Main.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Mã hóa đơn", "Ngày tạo", "Tổng trị giá(VNĐ)", "Người tạo", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false
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
            tbl_Main.getColumnModel().getColumn(4).setResizable(false);
            tbl_Main.getColumnModel().getColumn(5).setResizable(false);
            tbl_Main.getColumnModel().getColumn(5).setPreferredWidth(30);
        }

        tbl_Detail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sản phẩm", "Màu", "Bộ nhớ trong", "Bộ nhớ đệm", "Số lượng", "Tổng tiền(VNĐ)", "data"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_Detail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_DetailMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbl_DetailMouseReleased(evt);
            }
        });
        jScrollPane4.setViewportView(tbl_Detail);
        if (tbl_Detail.getColumnModel().getColumnCount() > 0) {
            tbl_Detail.getColumnModel().getColumn(6).setMinWidth(0);
            tbl_Detail.getColumnModel().getColumn(6).setPreferredWidth(0);
            tbl_Detail.getColumnModel().getColumn(6).setMaxWidth(0);
        }

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Danh sách hóa đơn");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("Chi tiết hóa đơn");

        javax.swing.GroupLayout crazyPanel_ParentLayout = new javax.swing.GroupLayout(crazyPanel_Parent);
        crazyPanel_Parent.setLayout(crazyPanel_ParentLayout);
        crazyPanel_ParentLayout.setHorizontalGroup(
            crazyPanel_ParentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crazyPanel_ParentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(crazyPanel_ParentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(crazyPanel_ParentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addGroup(crazyPanel_ParentLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        crazyPanel_ParentLayout.setVerticalGroup(
            crazyPanel_ParentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, crazyPanel_ParentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(crazyPanel_ParentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(crazyPanel_ParentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 536, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addGap(13, 13, 13))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(crazyPanel_Menu, javax.swing.GroupLayout.DEFAULT_SIZE, 1181, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(crazyPanel_Parent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
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

    private void cbx_Searchcbx_SearchItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbx_Searchcbx_SearchItemStateChanged
        if(evt.getStateChange() == 1) {
            int choice = cbx_Search.getSelectedIndex();
            switch (choice) {
                case 3:
                    txt_Search.setVisible(false);
                    crazyPanel_SearchByPrice.setVisible(true);
                    txt_Date.setVisible(false);
                    break;
                case 4:
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
            sortASC();
        } else {
            sortMode = false;
            lbl_SortMode.setIcon(desc);
            sortDESC();
        }
    }//GEN-LAST:event_lbl_SortModelbl_SortModeMouseClicked

    private void cbx_Sortcbx_SortItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbx_Sortcbx_SortItemStateChanged
        if(evt.getStateChange() == 1) {
            sortASC();
            lbl_SortMode.setIcon(asc);
        }
    }//GEN-LAST:event_cbx_Sortcbx_SortItemStateChanged

    private void ckb_SelectAllckb_SelectAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckb_SelectAllckb_SelectAllActionPerformed
        setTableCheckbox(mainModel, tbl_Main);
    }//GEN-LAST:event_ckb_SelectAllckb_SelectAllActionPerformed

    private void tbl_MainMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_MainMouseClicked
        onClickTable(tbl_Main, mainModel);
        Utils.selectedExport = list.get(tbl_Main.getSelectedRow());
        Utils.selectedExport.setDetails(bus_exdetail.getList());
        
        renderDetailTable(Utils.selectedExport.getDetails());
        setSelectedAll(mainModel, tbl_Main);
    }//GEN-LAST:event_tbl_MainMouseClicked

    public void onClickTable(JTable table, DefaultTableModel model) {
        int row = table.getSelectedRow();

        if ((boolean) model.getValueAt(row, 0)) {
            model.setValueAt(false, row, 0);
        } else {
            model.setValueAt(true, row, 0);
        }
    }
    private void tbl_MainMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_MainMouseReleased
        int row = tbl_Main.rowAtPoint(evt.getPoint());
        if (mouseClick == row) {
            return;
        }
        if (row != -1 && mouseClick != -1) {
            if (row > mouseClick) {
                for (int i = mouseClick; i <= row; i++) {
                    boolean isChecked = (boolean) tbl_Main.getValueAt(i, 0);
                    tbl_Main.setValueAt(!isChecked, i, 0);
                }

            } else if (row < mouseClick) {
                for (int i = row; i <= mouseClick; i++) {
                    boolean isChecked = (boolean) tbl_Main.getValueAt(i, 0);
                    tbl_Main.setValueAt(!isChecked, i, 0);
                }
            }
        }
        setSelectedAll(mainModel, tbl_Main);
        mouseClick = -1;
    }//GEN-LAST:event_tbl_MainMouseReleased

    private void tbl_DetailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_DetailMouseClicked
        
    }//GEN-LAST:event_tbl_DetailMouseClicked

    private void tbl_DetailMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_DetailMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tbl_DetailMouseReleased

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        clear();
    }//GEN-LAST:event_btn_refreshActionPerformed

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

    private void txt_SearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_SearchKeyReleased
        search();
    }//GEN-LAST:event_txt_SearchKeyReleased

    private void txt_DateFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_DateFocusLost

    }//GEN-LAST:event_txt_DateFocusLost

    private void txt_fromPriceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_fromPriceKeyReleased
        if (txt_fromPrice.getText().toString().equals("") && txt_toPrice.getText().toString().equals("")) {
            return;
        }
        else {
            search();
        }
    }//GEN-LAST:event_txt_fromPriceKeyReleased

    private void txt_toPriceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_toPriceKeyReleased
        if (txt_fromPrice.getText().toString().equals("") && txt_toPrice.getText().toString().equals("")) {
            return;
        }
        else {
            search();
        }
    }//GEN-LAST:event_txt_toPriceKeyReleased

    private void txt_DateFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_DateFocusGained
        if (!txt_Date.getText().toString().contains("to")) {
            return;
        }
        else {
            search();
        }
    }//GEN-LAST:event_txt_DateFocusGained

    private void lbl_searchlbl_SortModeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_searchlbl_SortModeMouseClicked
        search();
    }//GEN-LAST:event_lbl_searchlbl_SortModeMouseClicked

    private void tbl_MainMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_MainMousePressed
        mouseClick = tbl_Main.rowAtPoint(evt.getPoint());
    }//GEN-LAST:event_tbl_MainMousePressed

    private void lbl_ExportWordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_ExportWordMouseClicked
        print();
    }//GEN-LAST:event_lbl_ExportWordMouseClicked

    //Select all
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
        updateItemselected();
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
    
    //Search & Sort
    private void search() {
        /*
        Tìm kiếm
        1/Mã hóa đơn
        2/Người tạo
        3/Trị giá
        4/Ngày tạo
         */
        String keyword = txt_Search.getText().trim();
        switch (cbx_Search.getSelectedIndex()) {
            case 1:
                list = bus_exinvoice.searchByInvoiceID(list, keyword);
                searchNotification(list);
                break;
            case 3:
                list = bus_exinvoice.searchByInvoiceID(list, keyword);
                searchNotification(list);
                break;

            case 4:
                String fromPriceString = txt_fromPrice.getText().trim();
                String toPriceString = txt_toPrice.getText().trim();

                if (!fromPriceString.isEmpty() && !toPriceString.isEmpty()) {
                    double fromPrice = Double.parseDouble(fromPriceString);
                    double toPrice = Double.parseDouble(toPriceString);
                    list = bus_exinvoice.searchByOrderPrice(list, fromPrice, toPrice);
                    searchNotification(list);
                }
                break;
            case 5:
                if (txt_Date.getText().toString().contains("to")) {
                    String[] date = txt_Date.getText().toString().split(" to ");
                    String[] from = date[0].split("/");
                    String[] to = date[1].split("/");

                    DTO_Date fromDate = new DTO_Date(Integer.parseInt(from[2]), Integer.parseInt(from[1]), Integer.parseInt(from[0]), 0, 0, 0);
                    DTO_Date toDate = new DTO_Date(Integer.parseInt(to[2]), Integer.parseInt(to[1]), Integer.parseInt(to[0]), 0, 0, 0);
                    list = bus_exinvoice.searchByInitDate(list, fromDate, toDate);
                    searchNotification(list);
                }
                break;
            default:
                notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, 
                        "Vui lòng chọn chức năng tìm kiếm!!!");
        }
    }

    private void searchNotification(ArrayList<DTO_DefaultInvoice> list) {
        if (list.size() == 0) {
            notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Tìm kiếm thất bại");
        } else {
            notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "TÌm kiếm thành công");
            renderMainTable(list);
        }
    }

    private void sortASC() {
        /*
        Xếp tăng
        1/Ngày tạo
        2/Trị giá
        3/Người tạo
         */
        switch (cbx_Sort.getSelectedIndex()) {
            case 1:
                list = bus_exinvoice.sortByInitDateASC(list);
                renderMainTable(list);
                break;
            case 2:
                list = bus_exinvoice.sortByInvoicePriceASC(list);
                renderMainTable(list);
                break;
            case 3:
                list = bus_exinvoice.sortByInvoiceFullNameASC(list);
                renderMainTable(list);
                break;
            default:
        }
    }

    private void sortDESC() {
        /*
        Xếp giảm
        1/Theo ngày tạo
        2/Trị giá
        3/Người tạo
         */
        switch (cbx_Sort.getSelectedIndex()) {
            case 1:
                list = bus_exinvoice.sortByInitDateDESC(list);
                renderMainTable(list);
                break;
            case 2:
                list = bus_exinvoice.sortByInvoicePriceDESC(list);
                renderMainTable(list);
                break;
            case 3:
                list = bus_exinvoice.sortByInvoiceFullNameDESC(list);
                renderMainTable(list);
                break;
            default:
        }
    }
    //////////
    
    private void renderMainTable(ArrayList <DTO_DefaultInvoice> list) {
        mainModel.setRowCount(0);
        for(DTO_DefaultInvoice invoice : list) {
            Object data [] = new Object[5];
            data[0] = false;
            data[1] = invoice.getId();
            data[2] = invoice.getInitdate().format();
            data[3] = decimalFormat.format(invoice.getPrice());
            data[4] = invoice.getUser().getFullname();
            
            mainModel.addRow(data);
        }
    }
    
    private void renderDetailTable(ArrayList <DTO_InvoiceDetail> list) {
        detailModel.setRowCount(0);
        for(DTO_InvoiceDetail detail : list) {
            Object data [] = new Object[7];
            data[0] = detail.getOption().getProductid();
            data[1] = detail.getOption().getColor();
            String romData = detail.getOption().getRom() + " GB";
            if (detail.getOption().getRom() >= 1000) {
                romData = (detail.getOption().getRom() / 1000) + " T";
            }
            String ramData = detail.getOption().getRam() + " GB";
            if (detail.getOption().getRam() >= 1000) {
                ramData = (detail.getOption().getRam() / 1000) + " T";
            }
            data[2] = romData;
            data[3] = ramData;
            data[4] = detail.getQuantity();
            data[5] = decimalFormat.format(detail.getTotalprice());
            data[6] = detail;
            
            detailModel.addRow(data);
        }
    }
    
    private void clear() {
        list = bus_exinvoice.getList();
        renderMainTable(list);
        //checkbox
        cbx_Search.setSelectedIndex(0);
        cbx_Sort.setSelectedIndex(0);
        ckb_SelectAll.setSelected(false);
        //textfield
        txt_Search.setVisible(true);
        txt_Date.setVisible(false);
        cbx_Search.setSelectedIndex(0);
        //crazypanel
        crazyPanel_SearchByPrice.setVisible(false);
        //label
        lbl_SortMode.setIcon(asc);
        lbl_ItemSelected.setVisible(false);
        //table
        tbl_Main.clearSelection();
        tbl_Detail.clearSelection();
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
        titleRun.setText("HOÁ ĐƠN");
        titleRun.setFontSize(14);
        titleRun.setFontFamily("Times New Roman");
        //Viết "tên cửa hàng"
        titleGraph = document.createParagraph();
        titleRun = titleGraph.createRun();
        titleRun.setBold(true);
        titleRun.setText("Cửa hàng Mobile Shop");
        titleRun.setFontSize(14);
        titleRun.setFontFamily("Times New Roman");
        //Vẽ bản thông tin
        XWPFTable tbl1 = document.createTable();
        tbl1.removeBorders();
        tbl1.setWidth("100%");
        row = tbl1.getRow(0);
        cell = row.getCell(0);
        cell.setWidth("50%");
        paragraph = cell.getParagraphArray(0);
        run = paragraph.createRun();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        run.setFontFamily("Times New Roman");
        run.setFontSize(13);
        run.setText("Địa chỉ:123 phường ABC Quận 5");

        paragraph = cell.addParagraph();
        run = paragraph.createRun();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        run.setFontFamily("Times New Roman");
        run.setFontSize(13);
        run.setText("ĐT:012345678");

        row.createCell();
        cell = row.getCell(1);
        cell.setWidth("50%");
        paragraph = cell.getParagraphArray(0);
        run = paragraph.createRun();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        run.setBold(true);
        run.setFontFamily("Times New Roman");
        run.setFontSize(13);
        run.setText("HÓA ĐƠN XUẤT KHO");

        paragraph = cell.addParagraph();
        run = paragraph.createRun();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        run.setFontFamily("Times New Roman");
        run.setFontSize(13);
        run.setBold(false);
        run.setText("Điện thoại chính hãng");
        paragraph = document.createParagraph();
        //Utils.selectedImport
        
        paragraph = document.createParagraph();
        run = paragraph.createRun();
        run.setFontSize(13);
        run.setText("Tên người tạo:  " + Utils.selectedExport.getUser().getFullname());
        run.setFontFamily("Times New Roman");
        paragraph = document.createParagraph();
        run = paragraph.createRun();
        run.setFontSize(13);
        run.setText("Số điện thoại:  " + Utils.selectedExport.getUser().getPhone());
        run.setFontFamily("Times New Roman");
        paragraph = document.createParagraph();
        run = paragraph.createRun();
        run.setFontSize(13);
        
        run.setText("Hoá đơn Ngày " + Utils.selectedExport.getInitdate().getDd() + " Tháng " + Utils.selectedExport.getInitdate().getMm()+ " Năm " + Utils.selectedExport.getInitdate().getYyyy());

        run.setFontFamily("Times New Roman");
        paragraph = document.createParagraph(); 
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
        
        for (int i = 0; i < Utils.selectedExport.getDetails().size(); i++) {
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
            run.setText(Utils.selectedExport.getDetails().get(i).getOption().getProductname() + ", Rom: " + getUnit(Utils.selectedExport.getDetails().get(i).getOption().getRom()) + ", Ram: " + getUnit(Utils.selectedExport.getDetails().get(i).getOption().getRam()) + ", Màu: " + Utils.selectedExport.getDetails().get(i).getOption().getColor().getName());

            cell = row.getCell(2);
            paragraph = cell.getParagraphArray(0);
            run = paragraph.createRun();
            paragraph.setAlignment(ParagraphAlignment.CENTER);
            run.setFontFamily("Times New Roman");
            run.setFontSize(13);
            run.setText(Utils.selectedExport.getDetails().get(i).getQuantity() + "");

            cell = row.getCell(3);
            paragraph = cell.getParagraphArray(0);
            run = paragraph.createRun();
            paragraph.setAlignment(ParagraphAlignment.CENTER);
            run.setFontFamily("Times New Roman");
            run.setFontSize(13);
            run.setText(decimalFormat.format(Utils.selectedExport.getDetails().get(i).getOption().getImportprice()) + "VNĐ");

            cell = row.getCell(4);
            paragraph = cell.getParagraphArray(0);
            run = paragraph.createRun();
            paragraph.setAlignment(ParagraphAlignment.CENTER);
            run.setFontFamily("Times New Roman");
            run.setFontSize(13);
            run.setText(" " +decimalFormat.format(Utils.selectedExport.getDetails().get(i).getTotalprice()) + "VNĐ");
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
        run.setText(decimalFormat.format(Utils.selectedExport.getPrice()) + "VNĐ");
        
        paragraph = document.createParagraph();
        run = paragraph.createRun();
        String a = decimalFormat.format(Utils.selectedExport.getPrice());
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
        run.setText("Ngày " + today.getDayOfMonth() + " Tháng " + today.getMonthValue()+ " Năm " + today.getYear());

        row = table1.createRow();
        cell = row.getCell(0);
        cell.setWidth("50%");
        paragraph = cell.getParagraphArray(0);
        run = paragraph.createRun();
        run.setBold(true);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        run.setText("");
        run.setFontFamily("Times New Roman");
        run.setFontSize(13);

        cell = row.getCell(1);
        cell.setWidth("50%");
        paragraph = cell.getParagraphArray(0);
        run = paragraph.createRun();
        run.setBold(true);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        run.setText("GIÁM SÁT KHO");
        run.setFontFamily("Times New Roman");
        run.setFontSize(13);
        row = table1.createRow();
        cell = row.getCell(1);
        paragraph = cell.getParagraphArray(0);
        run = paragraph.createRun();
        run.setItalic(true);
        run.setBold(true);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        run.setText("NPTDA");
        run.setFontFamily("Times New Roman");
        run.setFontSize(13);
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lbl_ExportWord;
    private javax.swing.JLabel lbl_ItemSelected;
    private javax.swing.JLabel lbl_SortMode;
    private javax.swing.JLabel lbl_search;
    private javax.swing.JTable tbl_Detail;
    private javax.swing.JTable tbl_Main;
    private javax.swing.JTextField txt_Date;
    private javax.swing.JTextField txt_Search;
    private javax.swing.JTextField txt_fromPrice;
    private javax.swing.JTextField txt_toPrice;
    // End of variables declaration//GEN-END:variables
}
