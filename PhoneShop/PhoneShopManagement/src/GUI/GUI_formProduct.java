package GUI;

import Custom.SalePriceCellEditor;
import Custom.ImportPriceCellEditor;
import Interface.EventTextFieldInputChange;
import BUS.BUS_Brand;
import BUS.BUS_Color;
import BUS.BUS_Option;
import BUS.BUS_Product;
import BUS.BUS_Ram;
import BUS.BUS_Rom;
import Custom.ColorIcon;
import DTO.DTO_Brand;
import DTO.DTO_Color;
import DTO.DTO_InvoiceDetail;
import DTO.DTO_Option;
import DTO.DTO_Product;
import DTO.DTO_Ram;
import DTO.DTO_Rom;
import Utils.Utils;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import org.apache.commons.io.FileUtils;
import raven.toast.Notifications;


public class GUI_formProduct extends javax.swing.JFrame {
    private boolean isAddOption = false;
    private boolean alreadyUpdatePrice = false;
    private final DefaultTableModel optionModel;
    private final DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
    private final Notifications notifications = new Notifications();
    private DTO_Color selectedColor;
    private DTO_Rom selectedRom;
    private DTO_Ram selectedRam;
    
    private ButtonGroup menuColorGroup = new ButtonGroup();
    private ButtonGroup colorGroup = new ButtonGroup();
    private ButtonGroup romGroup = new ButtonGroup();
    private ButtonGroup ramGroup = new ButtonGroup();
    
    private ObservableList <DTO_Color> colorList = FXCollections.observableArrayList();
    private ArrayList <DTO_Rom> romList = new ArrayList<DTO_Rom>();
    private ArrayList <DTO_Ram> ramList = new ArrayList<DTO_Ram>();
    private ArrayList <DTO_Option> optionList = new ArrayList<DTO_Option>();
    private DTO_Option selectedOption = new DTO_Option();
    
    private final BUS_Product bus_product = new BUS_Product();
    private final BUS_Brand bus_brand = new BUS_Brand();
    private final BUS_Color bus_color = new BUS_Color();
    private final BUS_Rom bus_rom = new BUS_Rom();
    private final BUS_Ram bus_ram = new BUS_Ram();
    private final BUS_Option bus_option = new BUS_Option();
    
    
    public GUI_formProduct() {
        initComponents();
        
        setLocationRelativeTo(null);
        notifications.getInstance().setJFrame(this);
        
        //Khởi tạo ban đầu
        optionModel = (DefaultTableModel) tbl_optionprice.getModel();
        lbl_PrevImg.setEnabled(false);
        lbl_NextImg.setEnabled(false);
        lbl_Del.setEnabled(false);
        
        
        Utils.brandList = bus_brand.getList();
        for (DTO_Brand brand : Utils.brandList) {
            cbx_Brand.addItem(brand.getName());
        }
        
        Utils.defaultColorKeys = bus_color.getList();
        if (Utils.selectedProduct != null) {
            colorList = bus_color.getListByProductID();
        }
        
        //CUSTOMCODE ĐỪNG ĐỤNG VÔ!!!
        btn_Refresh.setIcon(new FlatSVGIcon("assets/refresh.svg", 0.25f));
        btn_Save.setIcon(new FlatSVGIcon("assets/save.svg", 0.3f));
        btn_SaveOption.setIcon(new FlatSVGIcon("assets/save.svg", 0.3f));
        applyTableStyleOptionTable(tbl_optionprice);
        toolbar_ColorList.setLayout(new GridLayout(2, 4, 10, 0));
        toolbar_AddColorList.setLayout(new GridLayout(5, 4));
        crazyPanel_RamList.setLayout(new GridLayout(3, 4, 15, 20));
        crazyPanel_RomList.setLayout(new GridLayout(3, 4, 15, 20));
        cbx_ChangeMode.setSelectedIndex(GUI_tableProduct.currMode);
        /////////
        
        for (DTO_Color color : Utils.defaultColorKeys) {
            ColorIcon colorIcon = new ColorIcon(30, 30, color.getRgb());
            JToggleButton colorBtn = new JToggleButton(colorIcon);
            colorBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == 1) {
                        addColorItem(color);
                    } 
                    else if (e.getButton() == MouseEvent.BUTTON3) {
//                        bus_color.deleteColor(color);
//                        Utils.defaultColorKeys.remove(color);
                    }

                }
            });

            colorGroup.add(colorBtn);
            toolbar_AddColorList.add(colorBtn);
        }
        
        for (int i = 0; i < (20 - Utils.defaultColorKeys.size()); i++) {
            JLabel label = new JLabel();
            toolbar_AddColorList.add(label);
        }
        //////////////////////////////////////////////
        eventListOnChanged();
    }
    
    private void applyTableStyleOptionTable(JTable table) {
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
        table.getTableHeader().setDefaultRenderer(getAlignmentCellRenderOptionTable(table.getTableHeader().getDefaultRenderer(), true));
        table.setDefaultRenderer(Object.class, getAlignmentCellRenderOptionTable(table.getDefaultRenderer(Object.class), false));
        
        table.getColumnModel().getColumn(3).setCellEditor(new ImportPriceCellEditor(new EventTextFieldInputChange() {
            @Override
            public void inputChanged(DTO_Option item, double newPrice) {
                updateImportPrice(item, newPrice);
            }

            @Override
            public void inputChanged(DTO_InvoiceDetail item, double newPrice) {
            }
        }));
        table.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(SwingConstants.CENTER);
                return this;
            }
        });
        
        table.getColumnModel().getColumn(4).setCellEditor(new SalePriceCellEditor(new EventTextFieldInputChange() {
            @Override
            public void inputChanged(DTO_Option item, double newPrice) {
                updateSalePrice(item, newPrice);
            }

            @Override
            public void inputChanged(DTO_InvoiceDetail item, double newPrice) {
            }
        }));
        table.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(SwingConstants.CENTER);
                return this;
            }
        });
    }
    
    private TableCellRenderer getAlignmentCellRenderOptionTable(TableCellRenderer oldRender, boolean header) {
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
    /////////////////////////
     
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        crazyPanel_Main = new raven.crazypanel.CrazyPanel();
        lbl_ProductImg = new javax.swing.JLabel();
        lbl_ProductID = new javax.swing.JLabel();
        lbl_option = new javax.swing.JLabel();
        lbl_AddImg = new javax.swing.JLabel();
        btn_Refresh = new javax.swing.JButton();
        crazyPanel_Detail = new raven.crazypanel.CrazyPanel();
        txt_ProductName = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        cbx_Brand = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_ProductDetail = new javax.swing.JTextArea();
        jLabel21 = new javax.swing.JLabel();
        btn_Save = new javax.swing.JButton();
        jLabel34 = new javax.swing.JLabel();
        txt_Warranty = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        cbx_ChangeMode = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        lbl_PrevImg = new javax.swing.JLabel();
        lbl_NextImg = new javax.swing.JLabel();
        crazyPanel_AddOption = new raven.crazypanel.CrazyPanel();
        cbx_Rom = new javax.swing.JComboBox<>();
        cbx_Ram = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        toolbar_AddColorList = new javax.swing.JToolBar();
        btn_addColor = new javax.swing.JButton();
        crazyPanel_Options = new raven.crazypanel.CrazyPanel();
        crazyPanel_RomList = new raven.crazypanel.CrazyPanel();
        crazyPanel_RamList = new raven.crazypanel.CrazyPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        lbl_Remain = new javax.swing.JLabel();
        toolbar_ColorList = new javax.swing.JToolBar();
        btn_AddOption = new javax.swing.JToggleButton();
        lbl_Remain1 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        crazyPanel_PriceTable = new raven.crazypanel.CrazyPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_optionprice = new javax.swing.JTable();
        jLabel22 = new javax.swing.JLabel();
        btn_SaveOption = new javax.swing.JButton();
        lbl_numberImg = new javax.swing.JLabel();
        lbl_Del = new javax.swing.JLabel();

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

        lbl_ProductImg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_ProductImg.setText("img");
        lbl_ProductImg.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lbl_ProductID.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbl_ProductID.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lbl_option.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_option.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_option.setText("Các tùy chọn cho sản phẩm");

        lbl_AddImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-add-image-40.png"))); // NOI18N
        lbl_AddImg.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_AddImg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_AddImgMouseClicked(evt);
            }
        });

        btn_Refresh.setText("Làm mới");
        btn_Refresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_Refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_RefreshActionPerformed(evt);
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

        txt_ProductName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ProductNameActionPerformed(evt);
            }
        });
        txt_ProductName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_ProductNameKeyReleased(evt);
            }
        });

        jLabel15.setText("Tên sản phẩm");

        jLabel17.setText("Thương hiệu");

        cbx_Brand.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn" }));
        cbx_Brand.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbx_BrandItemStateChanged(evt);
            }
        });

        txt_ProductDetail.setColumns(20);
        txt_ProductDetail.setRows(5);
        txt_ProductDetail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_ProductDetailKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(txt_ProductDetail);

        jLabel21.setText("Mô tả");

        btn_Save.setText("Lưu");
        btn_Save.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SaveActionPerformed(evt);
            }
        });

        jLabel34.setText("Bảo hành");

        txt_Warranty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_WarrantyActionPerformed(evt);
            }
        });
        txt_Warranty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_WarrantyKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_WarrantyKeyTyped(evt);
            }
        });

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel33.setText("tháng");

        javax.swing.GroupLayout crazyPanel_DetailLayout = new javax.swing.GroupLayout(crazyPanel_Detail);
        crazyPanel_Detail.setLayout(crazyPanel_DetailLayout);
        crazyPanel_DetailLayout.setHorizontalGroup(
            crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crazyPanel_DetailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(crazyPanel_DetailLayout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_ProductName)
                        .addGap(20, 20, 20))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, crazyPanel_DetailLayout.createSequentialGroup()
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(crazyPanel_DetailLayout.createSequentialGroup()
                        .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(crazyPanel_DetailLayout.createSequentialGroup()
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cbx_Brand, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(crazyPanel_DetailLayout.createSequentialGroup()
                                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
                                    .addGroup(crazyPanel_DetailLayout.createSequentialGroup()
                                        .addComponent(txt_Warranty, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, crazyPanel_DetailLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btn_Save, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(14, 14, 14))))
        );
        crazyPanel_DetailLayout.setVerticalGroup(
            crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crazyPanel_DetailLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_ProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbx_Brand, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_Warranty, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(19, 19, 19)
                .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btn_Save, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cbx_ChangeMode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chế độ", "Chỉ xem", "Chỉ thêm", "Chỉ sửa" }));
        cbx_ChangeMode.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbx_ChangeModeItemStateChanged(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Thông tin sản phẩm");

        lbl_PrevImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-back-to-30.png"))); // NOI18N
        lbl_PrevImg.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_PrevImg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_PrevImgMouseClicked(evt);
            }
        });

        lbl_NextImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-next-page-30.png"))); // NOI18N
        lbl_NextImg.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_NextImg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_NextImgMouseClicked(evt);
            }
        });

        crazyPanel_AddOption.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
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
        crazyPanel_AddOption.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
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

        cbx_Rom.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn", "16 GB", "32 GB", "64 GB", "128 GB", "256 GB", "512 GB", "1 T", "2 T" }));
        cbx_Rom.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbx_RomItemStateChanged(evt);
            }
        });

        cbx_Ram.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn", "1 GB", "2 GB", "3 GB", "4 GB", "5 GB", "6 GB", "7 GB", "8 GB" }));
        cbx_Ram.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbx_RamItemStateChanged(evt);
            }
        });
        cbx_Ram.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbx_RamActionPerformed(evt);
            }
        });

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("Chọn màu");

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("Bộ nhớ đệm");

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("Bộ nhớ trong");

        toolbar_AddColorList.setRollover(true);

        btn_addColor.setText("Thêm màu");
        btn_addColor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_addColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addColorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout crazyPanel_AddOptionLayout = new javax.swing.GroupLayout(crazyPanel_AddOption);
        crazyPanel_AddOption.setLayout(crazyPanel_AddOptionLayout);
        crazyPanel_AddOptionLayout.setHorizontalGroup(
            crazyPanel_AddOptionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, crazyPanel_AddOptionLayout.createSequentialGroup()
                .addGroup(crazyPanel_AddOptionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(crazyPanel_AddOptionLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(crazyPanel_AddOptionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbx_Rom, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(crazyPanel_AddOptionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(crazyPanel_AddOptionLayout.createSequentialGroup()
                                .addComponent(cbx_Ram, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_addColor, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, crazyPanel_AddOptionLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, crazyPanel_AddOptionLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(toolbar_AddColorList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(8, 8, 8))
        );
        crazyPanel_AddOptionLayout.setVerticalGroup(
            crazyPanel_AddOptionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crazyPanel_AddOptionLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(crazyPanel_AddOptionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(crazyPanel_AddOptionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbx_Rom, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbx_Ram, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_addColor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(toolbar_AddColorList, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                .addContainerGap())
        );

        crazyPanel_Options.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
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
        crazyPanel_Options.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
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

        crazyPanel_RomList.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
            "[light]border:0,0,0,0,shade(@background,5%),,20;[dark]border:0,0,0,0,tint(@background,5%),,20;[light]background:shade(@background,2%);[dark]background:tint(@background,2%)",
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
        crazyPanel_RomList.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
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

        crazyPanel_RamList.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
            "[light]border:0,0,0,0,shade(@background,5%),,20;[dark]border:0,0,0,0,tint(@background,5%),,20;[light]background:shade(@background,2%);[dark]background:tint(@background,2%)",
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
        crazyPanel_RamList.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
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

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel19.setText("Bộ nhớ trong");

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel26.setText("Bộ nhớ đệm");

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel27.setText("Màu sắc");

        lbl_Remain.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_Remain.setText("Tồn kho: ");

        toolbar_ColorList.setRollover(true);

        btn_AddOption.setText("Thêm tùy chọn mới");
        btn_AddOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AddOptionActionPerformed(evt);
            }
        });

        lbl_Remain1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_Remain1.setText("Đã bán:");

        javax.swing.GroupLayout crazyPanel_OptionsLayout = new javax.swing.GroupLayout(crazyPanel_Options);
        crazyPanel_Options.setLayout(crazyPanel_OptionsLayout);
        crazyPanel_OptionsLayout.setHorizontalGroup(
            crazyPanel_OptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(crazyPanel_OptionsLayout.createSequentialGroup()
                .addGroup(crazyPanel_OptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(crazyPanel_OptionsLayout.createSequentialGroup()
                        .addGroup(crazyPanel_OptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(crazyPanel_OptionsLayout.createSequentialGroup()
                                .addGap(62, 62, 62)
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(crazyPanel_OptionsLayout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(crazyPanel_OptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(crazyPanel_RomList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(crazyPanel_OptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(lbl_Remain1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lbl_Remain, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)))))
                        .addGroup(crazyPanel_OptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(crazyPanel_OptionsLayout.createSequentialGroup()
                                .addGap(87, 87, 87)
                                .addGroup(crazyPanel_OptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(crazyPanel_OptionsLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(crazyPanel_OptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(toolbar_ColorList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(crazyPanel_RamList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, crazyPanel_OptionsLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_AddOption)))
                .addContainerGap())
        );
        crazyPanel_OptionsLayout.setVerticalGroup(
            crazyPanel_OptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crazyPanel_OptionsLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(crazyPanel_OptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_Remain, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(crazyPanel_OptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(crazyPanel_OptionsLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(toolbar_ColorList, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                        .addGap(4, 4, 4))
                    .addGroup(crazyPanel_OptionsLayout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(lbl_Remain1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(crazyPanel_OptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(crazyPanel_OptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(crazyPanel_RomList, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                    .addComponent(crazyPanel_RamList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_AddOption, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9))
        );

        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setText("Mã sản phẩm");

        crazyPanel_PriceTable.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
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
        crazyPanel_PriceTable.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
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

        tbl_optionprice.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Bộ nhớ trong", "Bộ nhớ đệm", "Giá nhập(VNĐ)", "Giá bán(VNĐ)", "data"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, true, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_optionprice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_optionpriceMouseClicked(evt);
            }
        });
        tbl_optionprice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbl_optionpriceKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tbl_optionpriceKeyTyped(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_optionprice);
        if (tbl_optionprice.getColumnModel().getColumnCount() > 0) {
            tbl_optionprice.getColumnModel().getColumn(0).setPreferredWidth(20);
            tbl_optionprice.getColumnModel().getColumn(5).setMinWidth(0);
            tbl_optionprice.getColumnModel().getColumn(5).setPreferredWidth(0);
            tbl_optionprice.getColumnModel().getColumn(5).setMaxWidth(0);
        }

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel22.setText("Bảng giá cho tùy chọn");

        btn_SaveOption.setText("Lưu tùy chọn");
        btn_SaveOption.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_SaveOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SaveOptionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout crazyPanel_PriceTableLayout = new javax.swing.GroupLayout(crazyPanel_PriceTable);
        crazyPanel_PriceTable.setLayout(crazyPanel_PriceTableLayout);
        crazyPanel_PriceTableLayout.setHorizontalGroup(
            crazyPanel_PriceTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crazyPanel_PriceTableLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(crazyPanel_PriceTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(crazyPanel_PriceTableLayout.createSequentialGroup()
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_SaveOption, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE))
                .addContainerGap())
        );
        crazyPanel_PriceTableLayout.setVerticalGroup(
            crazyPanel_PriceTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crazyPanel_PriceTableLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(crazyPanel_PriceTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_SaveOption, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        lbl_numberImg.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_numberImg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lbl_Del.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-delete-48.png"))); // NOI18N
        lbl_Del.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_Del.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_DelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout crazyPanel_MainLayout = new javax.swing.GroupLayout(crazyPanel_Main);
        crazyPanel_Main.setLayout(crazyPanel_MainLayout);
        crazyPanel_MainLayout.setHorizontalGroup(
            crazyPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crazyPanel_MainLayout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(238, 238, 238)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(crazyPanel_MainLayout.createSequentialGroup()
                .addGroup(crazyPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(crazyPanel_MainLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(crazyPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(crazyPanel_MainLayout.createSequentialGroup()
                                .addGroup(crazyPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, crazyPanel_MainLayout.createSequentialGroup()
                                        .addComponent(lbl_numberImg, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lbl_Del)
                                        .addGap(32, 32, 32))
                                    .addGroup(crazyPanel_MainLayout.createSequentialGroup()
                                        .addGroup(crazyPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(crazyPanel_MainLayout.createSequentialGroup()
                                                .addGap(37, 37, 37)
                                                .addComponent(lbl_PrevImg)
                                                .addGap(10, 10, 10)
                                                .addComponent(lbl_AddImg)
                                                .addGap(10, 10, 10)
                                                .addComponent(lbl_NextImg))
                                            .addComponent(lbl_option, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(14, 14, 14)))
                                .addComponent(crazyPanel_Detail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(crazyPanel_Options, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(crazyPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(crazyPanel_PriceTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(crazyPanel_AddOption, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(crazyPanel_MainLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(crazyPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(crazyPanel_MainLayout.createSequentialGroup()
                                .addComponent(lbl_ProductImg, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(580, 810, Short.MAX_VALUE))
                            .addGroup(crazyPanel_MainLayout.createSequentialGroup()
                                .addComponent(lbl_ProductID, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(btn_Refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbx_ChangeMode, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        crazyPanel_MainLayout.setVerticalGroup(
            crazyPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crazyPanel_MainLayout.createSequentialGroup()
                .addGroup(crazyPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(crazyPanel_MainLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1))
                .addGroup(crazyPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(crazyPanel_MainLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(lbl_ProductID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addGroup(crazyPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(crazyPanel_MainLayout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(crazyPanel_PriceTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, crazyPanel_MainLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(crazyPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(crazyPanel_MainLayout.createSequentialGroup()
                                        .addComponent(lbl_ProductImg, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(crazyPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(lbl_numberImg, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lbl_Del))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(crazyPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lbl_AddImg)
                                            .addGroup(crazyPanel_MainLayout.createSequentialGroup()
                                                .addGap(10, 10, 10)
                                                .addGroup(crazyPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(lbl_PrevImg)
                                                    .addComponent(lbl_NextImg))))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lbl_option))
                                    .addComponent(crazyPanel_Detail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(crazyPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(crazyPanel_Options, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(crazyPanel_AddOption, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(crazyPanel_MainLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(crazyPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_Refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbx_ChangeMode, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(crazyPanel_Main, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(crazyPanel_Main, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_ProductNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ProductNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_ProductNameActionPerformed

    private void btn_RefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_RefreshActionPerformed
        clear();
    }//GEN-LAST:event_btn_RefreshActionPerformed

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

    private void btn_SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SaveActionPerformed
        if (GUI_tableProduct.currMode == Utils.ADD_MODE) {
            insertProduct();
            
        } 
        else if (GUI_tableProduct.currMode == Utils.UPDATE_MODE) {
            updateProduct();
            showProduct();
            renderOptionTable();
        }
        
    }//GEN-LAST:event_btn_SaveActionPerformed

    private void txt_WarrantyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_WarrantyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_WarrantyActionPerformed

    private void lbl_AddImgMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_AddImgMouseClicked
        insertImages();
    }//GEN-LAST:event_lbl_AddImgMouseClicked

    private void cbx_RomItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbx_RomItemStateChanged
        int value = 0;
        DTO_Rom rom = new DTO_Rom();
        if(evt.getStateChange() == 1) {
            if (cbx_Rom.getSelectedIndex() != 0) {
                String t = cbx_Rom.getSelectedItem().toString();
                switch (t.length()) {
                    case 3:
                        value = Integer.parseInt(cbx_Rom.getSelectedItem().toString().substring(0, 1)) * 1000;
                        break;
                    default:
                        value = Integer.parseInt(t.substring(0, t.length() - 3));
                }
                rom.setRom(value);
                rom.setProductid(Utils.selectedProduct.getId());
                addRomItem(rom);
            } 
            cbx_Rom.setSelectedIndex(0);
        }
    }//GEN-LAST:event_cbx_RomItemStateChanged

    private void cbx_RamItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbx_RamItemStateChanged
        int value = 0;
        DTO_Ram ram = new DTO_Ram();
        if(evt.getStateChange() == 1) {
            if (cbx_Ram.getSelectedIndex() != 0) {
                String t = cbx_Ram.getSelectedItem().toString();
                switch (t.length()) {
                    case 3:
                        value = Integer.parseInt(cbx_Ram.getSelectedItem().toString().substring(0, 1)) * 1000;
                        break;
                    default:
                        value = Integer.parseInt(t.substring(0, t.length() - 3));
                }
                ram.setRam(value);
                ram.setProductid(Utils.selectedProduct.getId());
                addRamItem(ram);
            }  
            cbx_Ram.setSelectedIndex(0);
        }
    }//GEN-LAST:event_cbx_RamItemStateChanged

    private void txt_ProductNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_ProductNameKeyReleased
        createID();
    }//GEN-LAST:event_txt_ProductNameKeyReleased

    private void cbx_BrandItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbx_BrandItemStateChanged
        if(evt.getStateChange() == 1) {
            createID();
//            if (GUI_tableProduct.currMode != Utils.ADD_MODE) {
//                cbx_Brand.setSelectedIndex(0);
//            }
        }
    }//GEN-LAST:event_cbx_BrandItemStateChanged

    private void txt_WarrantyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_WarrantyKeyReleased
        createID();
    }//GEN-LAST:event_txt_WarrantyKeyReleased

    private void txt_ProductDetailKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_ProductDetailKeyReleased
        createID();
    }//GEN-LAST:event_txt_ProductDetailKeyReleased

    private void btn_SaveOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SaveOptionActionPerformed
        insertOptions();
    }//GEN-LAST:event_btn_SaveOptionActionPerformed

    private void tbl_optionpriceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_optionpriceMouseClicked
        checkIsSavedOption();
    }//GEN-LAST:event_tbl_optionpriceMouseClicked

    private void tbl_optionpriceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_optionpriceKeyReleased
//        updateOptionPrice();
//        alreadyUpdatePrice = true;
//        notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Sửa giá thành công!!!");
//        alreadyUpdatePrice = false;
    }//GEN-LAST:event_tbl_optionpriceKeyReleased

    private void btn_AddOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AddOptionActionPerformed
        changeAddOptionMode();
        clearOptionPanel();
    }//GEN-LAST:event_btn_AddOptionActionPerformed

    private void tbl_optionpriceKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_optionpriceKeyTyped
        char c = evt.getKeyChar();
        
        if (!Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_tbl_optionpriceKeyTyped

    private void txt_WarrantyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_WarrantyKeyTyped
        char c = evt.getKeyChar();
        
        if (Character.isAlphabetic(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_txt_WarrantyKeyTyped

    private void cbx_RamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbx_RamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbx_RamActionPerformed

    private void btn_addColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addColorActionPerformed
        new GUI_formColor().setVisible(true);
    }//GEN-LAST:event_btn_addColorActionPerformed

    private void lbl_DelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_DelMouseClicked
        if ((GUI_tableProduct.currMode != Utils.UPDATE_MODE) || (selectedColor == null || selectedColor.getImages().size() == 0)) {
            return;
        }
        else if (selectedColor.getImages().size() != 0 && selectedColor != null ) {
            if(JOptionPane.showConfirmDialog(this, "Bạn chắc muốn xoá ảnh này???") == JOptionPane.YES_OPTION){
                File del = new File(selectedColor.getImages().get(Utils.imageIndex));
                del.delete();
                selectedColor.setImages(reloadImage(selectedColor));
                Utils.imageIndex = 0;
                showImage(selectedColor.getImages());
                bus_color.updateImage(selectedColor);
            }
        }
    }//GEN-LAST:event_lbl_DelMouseClicked

    private void lbl_NextImgMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_NextImgMouseClicked
        nextImage();
    }//GEN-LAST:event_lbl_NextImgMouseClicked

    private void lbl_PrevImgMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_PrevImgMouseClicked
        prevImage();
    }//GEN-LAST:event_lbl_PrevImgMouseClicked

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        GUI_tableProduct.formProduct = null;
    }//GEN-LAST:event_formWindowClosing
    
    //Mode
    private void setViewMode() {
        GUI_tableProduct.currMode = Utils.VIEW_MODE;
        isAddOption = false;
        showProduct();
        showOptions();
        //
        btn_Refresh.setEnabled(false);
        cbx_ChangeMode.setEnabled(true);
        //
        lbl_Del.setVisible(false);
        lbl_ProductImg.setVisible(true);
        lbl_AddImg.setVisible(true);
        lbl_NextImg.setVisible(true);
        lbl_PrevImg.setVisible(true);
        //
        txt_ProductName.setEnabled(false);
        cbx_Brand.setEnabled(false);
        txt_Warranty.setEnabled(false);
        txt_ProductDetail.setEnabled(false);
        btn_Save.setEnabled(false);
        //
        //btn_SaveOption.setEnabled(false);
        crazyPanel_PriceTable.setVisible(true);
        tbl_optionprice.setEnabled(false);
        //
        crazyPanel_AddOption.setVisible(isAddOption); 
        //
        lbl_option.setVisible(true);
        crazyPanel_Options.setVisible(true);
        btn_AddOption.setText("Thêm tùy chọn mới");
        btn_AddOption.setEnabled(false);    
    }
    
    private void setAddMode() {
        GUI_tableProduct.currMode = Utils.ADD_MODE;
        isAddOption = false;
        clear();
        //
        btn_Refresh.setEnabled(true);
        cbx_ChangeMode.setEnabled(false);
        //
        lbl_Del.setVisible(false);
        lbl_ProductImg.setVisible(false);
        lbl_AddImg.setVisible(false);
        lbl_NextImg.setVisible(false);
        lbl_PrevImg.setVisible(false);
        //
        txt_ProductName.setEnabled(true);
        cbx_Brand.setEnabled(true);
        txt_Warranty.setEnabled(true);
        txt_ProductDetail.setEnabled(true);
        btn_Save.setEnabled(true);
        //
        crazyPanel_PriceTable.setVisible(false);
        //
        crazyPanel_AddOption.setVisible(isAddOption);
        //
        lbl_option.setVisible(false);
        crazyPanel_Options.setVisible(false);
        btn_AddOption.setText("Thêm tùy chọn mới");
        btn_AddOption.setEnabled(false);  
    }
    
    private void setUpdateMode() {
        GUI_tableProduct.currMode = Utils.UPDATE_MODE;
        isAddOption = false;
        showProduct();
        showOptions();
        //
        btn_Refresh.setEnabled(false);
        cbx_ChangeMode.setEnabled(true);
        //
        lbl_Del.setVisible(true);
        lbl_ProductImg.setVisible(true);
        lbl_AddImg.setVisible(true);
        lbl_NextImg.setVisible(true);
        lbl_PrevImg.setVisible(true);
        //
        txt_ProductName.setEnabled(true);
        cbx_Brand.setEnabled(true);
        txt_Warranty.setEnabled(true);
        txt_ProductDetail.setEnabled(true);
        btn_Save.setEnabled(true);
        //
        crazyPanel_PriceTable.setVisible(true);
        tbl_optionprice.setEnabled(true);
        //
        crazyPanel_AddOption.setVisible(false);
        //
        lbl_option.setVisible(true);
        crazyPanel_Options.setVisible(true);
        btn_AddOption.setText("Thêm tùy chọn mới");
        btn_AddOption.setEnabled(true); 
        
    }
    
    private void changeAddOptionMode() {
        if (!isAddOption) {
            isAddOption = true;
            //
            crazyPanel_PriceTable.setEnabled(false);
            crazyPanel_AddOption.setVisible(isAddOption);
            //
            btn_AddOption.setText("Xong");
            btn_SaveOption.setEnabled(false);
        }
        else {
            isAddOption = false;
            //
            crazyPanel_PriceTable.setEnabled(true);
            crazyPanel_AddOption.setVisible(isAddOption);
            //
            insertColor();
            insertRom();
            insertRam();
            //
            btn_AddOption.setText("Thêm tùy chọn mới");
            btn_SaveOption.setEnabled(true);
        }
    }
    ////////
    
    //Clear
    private void clear() {
        lbl_ProductID.setText("");
        txt_ProductName.setText("");
        txt_ProductDetail.setText("");
        txt_Warranty.setText("");
        cbx_Brand.setSelectedIndex(0);
        
        optionModel.setRowCount(0);
    }
    
    private void clearOptionPanel() {
        cbx_Ram.setSelectedIndex(0);
        cbx_Rom.setSelectedIndex(0);
        colorGroup.clearSelection();
    }
    ////////
    
    //Option
    private void updateFElibs() {
        EventQueue.invokeLater(() -> {
            FlatLaf.updateUI();
            FlatAnimatedLafChange.hideSnapshotWithAnimation();
        });
    }
    
    private void addColorItem(DTO_Color selectedColor) {
        for (DTO_Color color : colorList) {
            if (color.rgbToString().equals(selectedColor.rgbToString())) {
                return;
            }
        }
        colorList.add(selectedColor);
        updateColorToolbar();
    }
    
    private void addRomItem(DTO_Rom selectedRom) {
        for (DTO_Rom rom : romList) {
            if (rom.getRom() == selectedRom.getRom()) {
                return;
            }
        }
        romList.add(selectedRom);
        updateRomPanel(); 
    }
    
    private void addRamItem(DTO_Ram selectedRam) {
        for (DTO_Ram ram : ramList) {
            if (ram.getRam() == selectedRam.getRam()) {
                return;
            }
        }
        ramList.add(selectedRam);
        updateRamPanel();
    }
    
    private void updateColorToolbar() {
        toolbar_ColorList.removeAll();
        //System.out.println(colorList.size());
        for (DTO_Color color : colorList) {
            ColorIcon colorIcon = new ColorIcon(30, 30, color.getRgb());
            JToggleButton colorBtn = new JToggleButton(colorIcon);
            colorBtn.addActionListener((ActionEvent e) -> {
                if (isAddOption) {
                    removeColor(color);
                }
                else {
                    selectedColor = color;
                    showSelectedOption();
                    Utils.imageIndex = 0;
                    showImage(selectedColor.getImages());
                }
            });
            menuColorGroup.add(colorBtn);
            toolbar_ColorList.add(colorBtn);
        }
        
        for (int i = 0; i < (8 - colorList.size()); i++) {
            JLabel label = new JLabel();
            toolbar_ColorList.add(label);
        }
        
        updateFElibs();
    }
    
    private void eventListOnChanged() {
        Utils.defaultColorKeys.addListener(new ListChangeListener<DTO_Color>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends DTO_Color> change) {
                toolbar_AddColorList.removeAll();
                for (DTO_Color color : Utils.defaultColorKeys) {
                    ColorIcon colorIcon = new ColorIcon(30, 30, color.getRgb());
                    JToggleButton colorBtn = new JToggleButton(colorIcon);
                    colorBtn.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            if (e.getButton() == 1) {
                                addColorItem(color);
                                updateColorToolbar();
                                selectedColor = null;
                            } 
                            else if (e.getButton() == MouseEvent.BUTTON3 && cbx_ChangeMode.getSelectedIndex() != 1) {
//                                bus_color.deleteColor(color);
//                                Utils.defaultColorKeys.remove(color);
//                                selectedColor = null;
                            }
                        }
                    });
                    colorGroup.add(colorBtn);
                    toolbar_AddColorList.add(colorBtn);
                }
                for (int i = 0; i < (20 - Utils.defaultColorKeys.size()); i++) {
                    JLabel label = new JLabel();
                    toolbar_AddColorList.add(label);
                }
                updateFElibs();
            }
        });
        colorList.addListener(new ListChangeListener<DTO_Color>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends DTO_Color> change) {
                updateColorToolbar();
            }
        });
    }
    
    private void updateRomPanel() {
        crazyPanel_RomList.removeAll();
        Collections.sort(romList, (o1, o2) -> o1.getRom() - o2.getRom());
        for (DTO_Rom rom : romList) {
            String romData = rom.getRom() + " GB";
            if (rom.getRom() >= 1000) {
                romData = (rom.getRom() / 1000) + " T";
            }
            
            JToggleButton romButton = new JToggleButton(romData);
            romButton.setCursor(Cursor.getPredefinedCursor(HAND_CURSOR));
            romButton.addActionListener((ActionEvent e) -> {
                String t = romButton.getText().toString();
                if (isAddOption) {
                    if (t.length() == 3) {
                        removeRom(new DTO_Rom(Integer.parseInt(t.substring(0, t.length() - 2)) * 1000, Utils.selectedProduct.getId()));
                    }
                    else {
                        removeRom(new DTO_Rom(Integer.parseInt(t.substring(0, t.length() - 3)), Utils.selectedProduct.getId()));
                    }  
                }
                else {
                    if (GUI_tableProduct.currMode == Utils.VIEW_MODE) {
                        selectedRom = rom;
                        showSelectedOption();
                    }
                }
            });
            romGroup.add(romButton);
            crazyPanel_RomList.add(romButton);
        }
        
        for (int i = 0; i < (12 - romList.size()); i++) {
            JLabel label = new JLabel();
            crazyPanel_RomList.add(label);
        }
        updateFElibs();
    }
    
    private void nextImage() {
        if (selectedColor.getImages() == null || selectedColor.getImages().size() == 0) {
            return;
        }
        if (selectedColor.getImages().size() - 1 <= Utils.imageIndex) {
            return;
        }
        Utils.imageIndex++;
        showImage(selectedColor.getImages());
    }
    
    private void prevImage() {
        if (selectedColor.getImages() == null || selectedColor.getImages().size() == 0) {
            return;
        }
        if (Utils.imageIndex == 0) {
            return;
        }
        Utils.imageIndex--;
        showImage(selectedColor.getImages());
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
        if (cbx_ChangeMode.getSelectedIndex() == 3) {
            if (selectedColor == null) {
                notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Bạn chưa chọn màu để tuỳ chỉnh hình ảnh!!!");
            } 
            else {
                if (JOptionPane.showConfirmDialog(this, "Bạn phải chọn file ảnh cho màu này?\n Nếu bạn sửa lại tất cả file ảnh sẽ được ghi đè Ok?") == JOptionPane.YES_OPTION) {
                    JFileChooser filechooser = new JFileChooser();

                    filechooser.setCurrentDirectory(Utils.currentPath);
                    filechooser.setMultiSelectionEnabled(true);
//                    filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    int option = filechooser.showOpenDialog(this);
                    if (option == filechooser.APPROVE_OPTION) {

                        File[] files = filechooser.getSelectedFiles();
                        Utils.currentPath = files[0].getParentFile();
                        ArrayList<String> imgPaths = reloadImage(selectedColor);
                        try {

                            int g = imgPaths.size();
                            for (int i = 0; i < files.length; i++) {
                                FileUtils.copyFile(files[i], new File("C:\\xampp\\htdocs\\API_PhoneShop\\images\\" + Utils.selectedProduct.getId() + "\\" + Utils.selectedProduct.getId() + "_" + selectedColor.rgbToString() + "__" + g + "." + getExtensionFile(files[i])));
                                imgPaths.add("C:\\xampp\\htdocs\\API_PhoneShop\\images\\" + Utils.selectedProduct.getId() + "\\" + Utils.selectedProduct.getId() + "_" + selectedColor.rgbToString() + "__" + g + "." + getExtensionFile(files[i]));
                                g++;
                            }
                            
                            selectedColor.setImages(imgPaths);
                            bus_color.updateImage(selectedColor);
                        } 
                        catch (IOException ex) {
                            Logger.getLogger(GUI_formProduct.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    showImage(selectedColor.getImages());
                    lbl_AddImg.setEnabled(true);
                }
            }
        } 
        else {
            notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Bạn đang ở chế độ xem không thể tuỳ chỉnh ảnh!!!");
            return;
        }
    }
    
    public ImageIcon resizeImg(String imgpath) {
        ImageIcon imgicon = new ImageIcon(imgpath);
        Image img = imgicon.getImage();
        Image newimg = img.getScaledInstance(lbl_ProductImg.getWidth(), lbl_ProductImg.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon reimg = new ImageIcon(newimg);
        return reimg;
    }

    private ArrayList<String> reloadImage(DTO_Color img) {
        File file = new File("C:\\xampp\\htdocs\\API_PhoneShop\\images\\" + Utils.selectedProduct.getId());
        File[] paths;

        ArrayList<String> imgPaths = new ArrayList<>();
        if (file.isDirectory() && file.exists()) {
            try {
                FileFilter filter = new FileFilter() {
                    @Override
                    public boolean accept(File pathname) {
                        return pathname.isFile();
                    }
                };
                paths = file.listFiles(filter);

                for (File a : paths) {
                    String temp = a + "";
                    if (!temp.contains(img.rgbToString())) {
                        continue;
                    } 
                    else {
                        imgPaths.add(a + "");
                    }
                }
                if (!imgPaths.isEmpty()) {
                    int k = 0;
                    for (int i = 0; i < imgPaths.size(); i++) {
                        File temp = new File(imgPaths.get(i));
                        File newFile = new File(imgPaths.get(i).substring(0, imgPaths.get(i).indexOf("__")) + "__" + k + "." + getExtensionFile(temp));
                        temp.renameTo(newFile);
                        imgPaths.set(i, newFile+"");
                        k++;
                    }
                    return imgPaths;
                }
            } 
            catch (Exception e) {
                e.printStackTrace();
            }

        }
        return imgPaths;
    }
    
    private void showImage(ArrayList<String> list) {
        lbl_ProductImg.setText("");
        if (list == null || list.isEmpty()) {
            lbl_Del.setEnabled(false);
            lbl_PrevImg.setEnabled(false);
            lbl_NextImg.setEnabled(false);
            ImageIcon imgicon = new ImageIcon(getClass().getResource("/assets/noimage.png"));
            Image img = imgicon.getImage();
            Image newimg = img.getScaledInstance(lbl_ProductImg.getWidth(), lbl_ProductImg.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon reimg = new ImageIcon(newimg);
            lbl_ProductImg.setIcon(reimg);
            lbl_numberImg.setText(Utils.imageIndex + "/0");
            return;
        }

        if (Utils.imageIndex == 0) {
            lbl_Del.setEnabled(true);
            lbl_PrevImg.setEnabled(false);
            lbl_NextImg.setEnabled(true);
            lbl_ProductImg.setIcon(resizeImg(String.valueOf(list.get(Utils.imageIndex))));
        } 
        else if (Utils.imageIndex == list.size() - 1) {
            lbl_Del.setEnabled(true);
            lbl_NextImg.setEnabled(false);
            lbl_PrevImg.setEnabled(true);
            lbl_ProductImg.setIcon(resizeImg(String.valueOf(list.get(Utils.imageIndex))));
        } 
        else if (Utils.imageIndex > 0 && Utils.imageIndex < list.size() - 1) {
            lbl_Del.setEnabled(true);
            lbl_PrevImg.setEnabled(true);
            lbl_NextImg.setEnabled(true);
            lbl_ProductImg.setIcon(resizeImg(String.valueOf(list.get(Utils.imageIndex))));
        }
        lbl_numberImg.setText(Utils.imageIndex + 1 + "/" + list.size());
    }
    
    private void updateRamPanel() {
        crazyPanel_RamList.removeAll();
        Collections.sort(ramList, (o1, o2) -> o1.getRam() - o2.getRam());
        for (DTO_Ram ram : ramList) {
            String ramData = ram.getRam() + " GB";
            if (ram.getRam() >= 1000) {
                ramData = (ram.getRam() / 1000) + " T";
            }
            
            JToggleButton ramButton= new JToggleButton(ramData);
            ramButton.setCursor(Cursor.getPredefinedCursor(HAND_CURSOR));
            ramButton.addActionListener((ActionEvent e) -> {
                String t = ramButton.getText().toString();
                if (isAddOption) {
                    if (t.length() == 3) {
                        removeRam(new DTO_Ram(Integer.parseInt(t.substring(0, t.length() - 2)) * 1000, Utils.selectedProduct.getId()));
                    }
                    else {
                        removeRam(new DTO_Ram(Integer.parseInt(t.substring(0, t.length() - 3)), Utils.selectedProduct.getId()));
                    }  
                }
                else {
                    if (GUI_tableProduct.currMode == Utils.VIEW_MODE) {
                        selectedRam = ram;
                        showSelectedOption();
                    }
                }
            });
            ramGroup.add(ramButton);
            crazyPanel_RamList.add(ramButton);
        }
        
        for (int i = 0; i < (12 - ramList.size()); i++) {
            JLabel label = new JLabel();
            crazyPanel_RamList.add(label);
        }
        updateFElibs();
    }
    ///////////////
    
    //Insert
    private void createID() {
        if(cbx_Brand.getSelectedIndex() == 0 || txt_ProductName.getText().toString().equals("") || txt_ProductDetail.getText().toString().equals("") || txt_Warranty.getText().toString().equals("") || GUI_tableProduct.currMode != Utils.ADD_MODE) {
            return;
        }
        DTO_Product newPro = new DTO_Product();
        newPro.setId(bus_product.getTotal() + 1);
        lbl_ProductID.setText(newPro.getId());
    }
    
    private void insertProduct() {
        if (lbl_ProductID.getText().isEmpty()) {
            notifications.getInstance().show(Notifications.Type.INFO,
                    Notifications.Location.TOP_CENTER, "Không được để trống thông tin !!!");
            return;
        }
        String id = lbl_ProductID.getText().toString();
        String brandid = Utils.brandList.get(cbx_Brand.getSelectedIndex() - 1).getId();
        String brandname = Utils.brandList.get(cbx_Brand.getSelectedIndex() - 1).getName();
        String productname = txt_ProductName.getText().toString();
        String detail = txt_ProductDetail.getText().toString();
        double minimportprice = 0;
        double minsaleprice = 0;
        double warranty = Double.parseDouble(txt_Warranty.getText().toString());
        int status = 1;
        DTO_Product newProduct = new DTO_Product(id, brandid, brandname, productname, minimportprice, minsaleprice, detail, warranty, status, 0, 0);
        int check = bus_product.insert(newProduct);
        if (check != -1) {
            notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Thêm mặt hàng thành công!!!");
            Utils.selectedProduct = newProduct;
            clear();
            showProduct();
            GUI_tableProduct.currMode = Utils.UPDATE_MODE;
            cbx_ChangeMode.setSelectedIndex(GUI_tableProduct.currMode);
            new File("C:\\xampp\\htdocs\\API_PhoneShop\\images\\" + Utils.selectedProduct.getId()).mkdir();
        }
        else {
            notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Thêm mặt hàng thất bại!!!");
        }
    }
    
    private void insertColor() {
        String id = Utils.selectedProduct.getId();
        boolean check1 = false;
        for (DTO_Color color : colorList) {
            boolean check = true;
            for (DTO_Color color1 : Utils.selectedProduct.getColors()) {
                if (color1.rgbToString().equals(color.rgbToString())) {
                    check = false;
                    break;
                }
            }
            if (check) {
                check1 = true;
                color.setProductid(id);
                bus_color.insertColorProduct(color);
                if (!Utils.selectedProduct.getOptions().isEmpty()) {
                    for (DTO_Option option : optionList) {
                        if (option.isSaved()) {
                            int rom = option.getRom();
                            int ram = option.getRam();
                            DTO_Option newId = new DTO_Option();
                            newId.setId(bus_option.getTotal() + 1);
                            DTO_Option newOption = new DTO_Option(newId.getId(), Utils.selectedProduct.getId(), Utils.selectedProduct.getProductname(), color
                                    , rom, ram, 0, 0, 0, 0, true);
                            bus_option.insert(newOption);
                        }
                    }
                }
            }
        }
        if (check1) {
            Utils.selectedProduct.setColors(bus_color.getListByProductID());
            colorList = bus_color.getListByProductID();
        } 
    }
    
    private void insertRom() {
        boolean check1 = false;
        for (int i = 0; i < romList.size(); i++) {
            boolean check = true;
            for (int j = 0; j < Utils.selectedProduct.getRoms().size(); j++) {
                if (romList.get(i).getRom() == Utils.selectedProduct.getRoms().get(j).getRom()) {
                    check = false;
                    break;
                }
            }
            if (check) {
                check1 = true;
                bus_rom.insert(romList.get(i));
            }
        }
        if (check1) {
            Utils.selectedProduct.setRoms(bus_rom.getList());
            romList = bus_rom.getList();
            renderOptionTable();
        }
    }
    
    private void insertRam() {
        boolean check1 = false;
        for (int i = 0; i < ramList.size(); i++) {
            boolean check = true;
            for (int j = 0; j < Utils.selectedProduct.getRams().size(); j++) {
                if (ramList.get(i).getRam() == Utils.selectedProduct.getRams().get(j).getRam()) {
                    check = false;
                    break;
                }
            }
            if (check) {
                check1 = true;
                bus_ram.insert(ramList.get(i));
            }
        }
        if (check1) {
            Utils.selectedProduct.setRams(bus_ram.getList());
            ramList = bus_ram.getList();
            renderOptionTable();
        }
    }
    
    private void renderOptionTable() {
        optionModel.setRowCount(0);
        String id = Utils.selectedProduct.getId();
        optionList = new ArrayList<>();
        for (DTO_Ram ram : ramList) {
            for (DTO_Rom rom : romList) {
                DTO_Option newOption = new DTO_Option("", id, "", null, rom.getRom(), ram.getRam(), 0, 0, 0, 0, false);
                if (containOption(newOption) != null) {
                    newOption = containOption(newOption);
                }
                optionList.add(newOption);
            }
        }
        Collections.sort(optionList, ((o1, o2) -> (o1.getRom() != o2.getRom()) ? (o1.getRom() - o2.getRom()) : (o1.getRam() - o2.getRam()) ));
        
        for(DTO_Option option : optionList) {
            Object data [] = new Object[6];
            data[0] = option.isSaved();
            String romData = option.getRom() + " GB";
            if (option.getRom() >= 1000) {
                romData = (option.getRom() / 1000) + " T";
            }
            String ramData = option.getRam() + " GB";
            if (option.getRam() >= 1000) {
                ramData = (option.getRam() / 1000) + " T";
            }
            data[1] = romData;
            data[2] = ramData;
            data[3] = decimalFormat.format(option.getImportprice());
            data[4] = decimalFormat.format(option.getSaleprice());
            data[5] = option;
            optionModel.addRow(data);
        }
    }
    
    private DTO_Option containOption(DTO_Option selectedOption) {
        for (DTO_Option option : Utils.selectedProduct.getOptions()) {
            if (option.getRom() == selectedOption.getRom() && option.getRam() == selectedOption.getRam()) {
                return option;
            }
        }
        return null;
    }
    
    private void insertOptions() {
        boolean check = false;
        for (int i = 0; i < optionList.size(); i++) {
            if ((boolean) tbl_optionprice.getValueAt(i, 0)) {
                if (!optionList.get(i).isSaved()) {
                    check = true;
                    int rom = optionList.get(i).getRom();
                    int ram = optionList.get(i).getRam();
                    double importprice = optionList.get(i).getImportprice();
                    double saleprice = optionList.get(i).getSaleprice();
                    for (DTO_Color color : Utils.selectedProduct.getColors()) {
                        DTO_Option newId = new DTO_Option();
                        newId.setId(bus_option.getTotal() + 1);
                        DTO_Option newOption = new DTO_Option(newId.getId(), Utils.selectedProduct.getId(), "", color, rom, ram, importprice, saleprice, 0, 0, true);
                        bus_option.insert(newOption);
                        System.out.println(newOption.getProductid());
                    }
                }
            }
        }
        if (check) {
            notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Thêm tùy chọn thành công!!!");
            Utils.selectedProduct.setOptions(bus_option.getList());
            optionList = bus_option.getDistinctOption();
            Utils.selectedProduct.setMinimportprice(bus_option.getMinImportPrice(Utils.selectedProduct.getId()));
            Utils.selectedProduct.setMinsaleprice(bus_option.getMinSalePrice(Utils.selectedProduct.getId()));
            renderOptionTable();
        }
        else {
            notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Thêm tùy chọn thất bại!!!");
        }
    }

    private void updateImportPrice(DTO_Option item, double newPrice) {
        if (item.isSaved()) {
            int rom = item.getRom();
            int ram = item.getRam();
            for (DTO_Option option : Utils.selectedProduct.getOptions()) {
                if (option.getRom() == rom && option.getRam() == ram) {
                    option.setImportprice(newPrice);
                    bus_option.update(option);
                }
            }
            notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Sửa giá nhập thành công!!!");
            Utils.selectedProduct.setOptions(bus_option.getList());
            optionList = bus_option.getDistinctOption();

            Utils.selectedProduct.setMinimportprice(bus_option.getMinImportPrice(Utils.selectedProduct.getId()));
            System.out.println(Utils.selectedProduct.getMinimportprice());
            bus_product.update(Utils.selectedProduct);
        }
        alreadyUpdatePrice = true;
        notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Sửa thành công!!!");
        alreadyUpdatePrice = false;
    }
    
    private void updateSalePrice(DTO_Option item, double newPrice) {
        if (item.isSaved()) {
            int rom = item.getRom();
            int ram = item.getRam();
            for (DTO_Option option : Utils.selectedProduct.getOptions()) {
                if (option.getRom() == rom && option.getRam() == ram) {
                    option.setSaleprice(newPrice);
                    bus_option.update(option);
                }
            }
            notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Sửa giá bán thành công!!!");
            Utils.selectedProduct.setOptions(bus_option.getList());
            optionList = bus_option.getDistinctOption();
            Utils.selectedProduct.setMinsaleprice(bus_option.getMinSalePrice(Utils.selectedProduct.getId()));
            bus_product.update(Utils.selectedProduct);
        }
        alreadyUpdatePrice = true;
        notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Sửa thành công!!!");
        alreadyUpdatePrice = false;
    }
    
    private void checkIsSavedOption() {
        if (GUI_tableProduct.currMode == Utils.UPDATE_MODE) {
            if (optionList.get(tbl_optionprice.getSelectedRow()).isSaved()) {
                tbl_optionprice.setValueAt(true, tbl_optionprice.getSelectedRow(), 0);
                notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Có option này rồi!!!");
            }
        } 
    }
    
    private void showProduct() {
        lbl_ProductID.setText(Utils.selectedProduct.getId());
        cbx_Brand.setSelectedItem(Utils.selectedProduct.getBrandname());
        txt_ProductName.setText(Utils.selectedProduct.getProductname());
        txt_Warranty.setText(Utils.selectedProduct.getWarranty() + "");
        txt_ProductDetail.setText(Utils.selectedProduct.getDetail());
    }
    
    private void showOptions() {
        colorList = bus_color.getListByProductID();
        romList = bus_rom.getList();
        ramList = bus_ram.getList();
        optionList = bus_option.getDistinctOption();
        updateColorToolbar();
        updateRomPanel();
        updateRamPanel();
        renderOptionTable();
    }
    
    private void showSelectedOption() {
        //System.out.println(selectedColor + ";" + selectedRom + ";" + selectedRam);
        if (selectedColor != null && selectedRom != null && selectedRam != null) {
            for (DTO_Option option : Utils.selectedProduct.getOptions()) {
                if (option.getColor().rgbToString().equals(selectedColor.rgbToString()) 
                        && option.getRom() == selectedRom.getRom() 
                        && option.getRam() == selectedRam.getRam()) {
                    selectedOption = option;
                    break;
                }
            }
            lbl_Remain.setText("Tồn kho: " + selectedOption.getRemain());
            lbl_Remain1.setText("Đã bán: " + selectedOption.getSold());
        }
        else if (selectedRom != null && selectedRam != null) {
            //System.out.println(selectedRom.getRom() + ";" + selectedRam.getRam());
        }
        else {
        }
    }
    /////////////////////
    
    //Update 
    private void updateProduct() {
        String brandid = Utils.brandList.get(cbx_Brand.getSelectedIndex() - 1).getId();
        String brandname = Utils.brandList.get(cbx_Brand.getSelectedIndex() - 1).getName();
        String productname = txt_ProductName.getText().toString();
        String detail = txt_ProductDetail.getText().toString();
        double warranty = Double.parseDouble(txt_Warranty.getText().toString());
        
        Utils.selectedProduct.setBrandid(brandid);
        Utils.selectedProduct.setBrandname(brandname);
        Utils.selectedProduct.setProductname(productname);
        Utils.selectedProduct.setDetail(detail);
        Utils.selectedProduct.setWarranty(warranty);
        
        int check = bus_product.update(Utils.selectedProduct);
        if (check != -1) {
            notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Cập nhật thông tin thành công!!!");
            showProduct();
        }
        else {
            notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Cập nhật thông tin thất bại!!!");
        }
    }
    /////////////////////
    
    //Remove
    private boolean containColor(ObservableList<DTO_Color> list, DTO_Color item) {
        for (DTO_Color color : list) {
            if (color.rgbToString().equals(item.rgbToString())) {
                return true;
            }
        }
        return false;
    }
    
    private boolean containRom(ArrayList<DTO_Rom> list, DTO_Rom item) {
        for (DTO_Rom rom : list) {
            if (rom.getRom() == item.getRom()) {
                return true;
            }
        }
        return false;
    }
    
    private boolean containRam(ArrayList<DTO_Ram> list, DTO_Ram item) {
        for (DTO_Ram ram : list) {
            if (ram.getRam() == item.getRam()) {
                return true;
            }
        }
        return false;
    }
    
    private void removeColor(DTO_Color selectedColor) {
        if (containColor(Utils.selectedProduct.getColors(), selectedColor)) {
            if (Utils.selectedProduct.getOptions().size() == 0) { 
                int check = bus_color.deleteColor(selectedColor);
                if (check != -1) {
                    notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Xóa màu thành công!!!");
                    Utils.selectedProduct.setColors(bus_color.getListByProductID());
                    colorList.remove(selectedColor);
                    updateColorToolbar();
                }
                else {
                    notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Xóa màu thất bại!!!");
                }
            }
            else {
                notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Tùy chọn này đã lưu vào CSDL!!!");
            }
        }
        else {
            colorList.remove(selectedColor);
            updateColorToolbar();
        }
    }
    
    private void removeRom(DTO_Rom selectedRom) {   
        if (containRom(Utils.selectedProduct.getRoms(), selectedRom)) {
            if (!containRom(selectedRom)) {
                int check = bus_rom.delete(selectedRom);
                if (check != -1) {
                    notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Xóa rom thành công!!!");
                    Utils.selectedProduct.setRoms(bus_rom.getList());
                    romList = bus_rom.getList();
                    updateRomPanel();
                }
                else {
                    notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Xóa rom thất bại!!!");
                }
            }
            else {
                notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Tùy chọn này đã lưu vào CSDL!!!");
            }
        }  
        else {
            for (int i = 0; i < romList.size(); i++) {
                if (romList.get(i).getRom() == selectedRom.getRom()) {
                    romList.remove(i);
                    break;
                }
            }
            updateRomPanel();
        } 
    }
    
    private void removeRam(DTO_Ram selectedRam) {
        if (containRam(Utils.selectedProduct.getRams(), selectedRam)) {
            if (!containRam(selectedRam)) {
                int check = bus_ram.delete(selectedRam);
                if (check != -1) {
                    notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Xóa ram thành công!!!");
                    Utils.selectedProduct.setRams(bus_ram.getList());
                    ramList = bus_ram.getList();
                    updateRamPanel();
                }
                else {
                    notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Xóa ram thất bại!!!");
                }
            }
            else {
                notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Tùy chọn này đã lưu vào CSDL!!!");
            }
        }
        else {
            for (int i = 0; i < ramList.size(); i++) {
                if (ramList.get(i).getRam() == selectedRam.getRam()) {
                    ramList.remove(i);
                    break;
                }
            }
            updateRamPanel();
        }
    }
    
    private boolean containRom(DTO_Rom rom) {
        for (DTO_Option option : optionList) {
            if (option.isSaved()) {
                if (option.getRom() == rom.getRom()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean containRam(DTO_Ram ram) {
        for (DTO_Option option : optionList) {
            if (option.isSaved()) {
                if (option.getRam() == ram.getRam()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static void main(String args[]) {
        FlatRobotoFont.install();
        FlatLaf.registerCustomDefaultsSource("crazypanel");
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
        //FlatMacLightLaf.setup();
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI_formProduct().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btn_AddOption;
    private javax.swing.JButton btn_Refresh;
    private javax.swing.JButton btn_Save;
    private javax.swing.JButton btn_SaveOption;
    private javax.swing.JButton btn_addColor;
    private javax.swing.JComboBox<String> cbx_Brand;
    private javax.swing.JComboBox<String> cbx_ChangeMode;
    private javax.swing.JComboBox<String> cbx_Ram;
    private javax.swing.JComboBox<String> cbx_Rom;
    private raven.crazypanel.CrazyPanel crazyPanel_AddOption;
    private raven.crazypanel.CrazyPanel crazyPanel_Detail;
    private raven.crazypanel.CrazyPanel crazyPanel_Main;
    private raven.crazypanel.CrazyPanel crazyPanel_Options;
    private raven.crazypanel.CrazyPanel crazyPanel_PriceTable;
    private raven.crazypanel.CrazyPanel crazyPanel_RamList;
    private raven.crazypanel.CrazyPanel crazyPanel_RomList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbl_AddImg;
    private javax.swing.JLabel lbl_Del;
    private javax.swing.JLabel lbl_NextImg;
    private javax.swing.JLabel lbl_PrevImg;
    private javax.swing.JLabel lbl_ProductID;
    private javax.swing.JLabel lbl_ProductImg;
    private javax.swing.JLabel lbl_Remain;
    private javax.swing.JLabel lbl_Remain1;
    private javax.swing.JLabel lbl_numberImg;
    private javax.swing.JLabel lbl_option;
    private javax.swing.JTable tbl_optionprice;
    private javax.swing.JToolBar toolbar_AddColorList;
    private javax.swing.JToolBar toolbar_ColorList;
    private javax.swing.JTextArea txt_ProductDetail;
    private javax.swing.JTextField txt_ProductName;
    private javax.swing.JTextField txt_Warranty;
    // End of variables declaration//GEN-END:variables
}
