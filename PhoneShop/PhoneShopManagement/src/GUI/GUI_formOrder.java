package GUI;

import BUS.BUS_Option;
import BUS.BUS_Order;
import BUS.BUS_User;
import Custom.OrderDetailColorCellRender;
import Custom.OrderOptionColorCellRender;
import Custom.OrderQuantityCellEditor;
import Custom.RemoveDetailCellEditor;
import Custom.RemoveDetailCellRender;
import DAL.DAL_Option;
import DAL.DAL_OrderDetail;
import DAL.DAL_Product;
import DAL.DAL_User;
import DTO.DTO_Date;
import DTO.DTO_InvoiceDetail;
import DTO.DTO_Option;
import DTO.DTO_Order;
import DTO.DTO_Product;
import DTO.DTO_User;
import static GUI.GUI_tableUser.currMode;
import static GUI.GUI_tableUser.formUser;
import Interface.EventSpinnerInputChange;
import Interface.TableActionEvent;
import Utils.Utils;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import raven.toast.Notifications;

public class GUI_formOrder extends javax.swing.JFrame {

    private DefaultTableModel detailModel;
    private DefaultTableModel optionModel;

    private Notifications notifications = new Notifications();
    private DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
    private int selectedItem = 0;
    private boolean sortMode = true;

    private BUS_Option bus_option = new BUS_Option();
    private DAL_Product dal_ProDuct = new DAL_Product();
    private DAL_Option dal_Option = new DAL_Option();
    private DAL_OrderDetail dal_OrderDetail = new DAL_OrderDetail();
    private ArrayList<DTO_InvoiceDetail> detailList = new ArrayList<DTO_InvoiceDetail>();
    private ArrayList<DTO_Option> optionList = new ArrayList<DTO_Option>();

    private final BUS_Order bus_order = new BUS_Order();
    private final BUS_User bus_user = new BUS_User();
    
    private ArrayList<String> cbx_data = new ArrayList<String>();
    private double orderPrice;
    private DTO_Date innidate = new DTO_Date();

    public GUI_formOrder() {
        initComponents();

        setLocationRelativeTo(null);
        notifications.getInstance().setJFrame(this);

        //Khởi tạo ban đầu
        detailModel = (DefaultTableModel) tbl_Detail.getModel();
        optionModel = (DefaultTableModel) tbl_Option.getModel();

        cbx_ChangeMode.setSelectedIndex(GUI_tableOrder.currMode);

        //CUSTOMCODE ĐỪNG ĐỤNG VÔ!!!
        btn_refresh.setIcon(new FlatSVGIcon("assets/refresh.svg", 0.25f));
        btn_Save.setIcon(new FlatSVGIcon("assets/save.svg", 0.3f));
        lbl_search.setIcon(new FlatSVGIcon("assets/search.svg", 0.5f));
        applyTableStyleDetailTable(tbl_Detail);
        applyTableStyleOptionTable(tbl_Option);

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
    
        table.getColumnModel().getColumn(5).setCellEditor(new OrderQuantityCellEditor(new EventSpinnerInputChange() {
            @Override
            public void inputChanged() {
                sumAmount();
            }
        }));
        table.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(SwingConstants.CENTER);
                return this;
            }
        });
        
        table.getColumnModel().getColumn(1).setCellRenderer(new OrderDetailColorCellRender());
        
        table.getColumnModel().getColumn(7).setCellEditor(new RemoveDetailCellEditor(new TableActionEvent() {
            @Override
            public void onView(int row) {
                if (GUI_tableOrder.currMode != Utils.ADD_MODE) {
                    return;
                }
                if (JOptionPane.showConfirmDialog(GUI_formOrder.this, "Bạn muốn xóa chi tiết đơn này ???") == JOptionPane.YES_OPTION) {
                    for (int i = 0; i < optionModel.getRowCount(); i++) {
                        if (tbl_Option.getValueAt(i, 2).toString().equals(detailList.get(row).getOptionid())) {
                            tbl_Option.setValueAt(false, i, 0);
                            break;
                        }
                    }
                    setSelectedAll(optionModel, tbl_Option);
                    detailList.remove(row);
                    renderDetailTable(detailList);
                    sumAmount();
                }
            }

        }));
        table.getColumnModel().getColumn(7).setCellRenderer(new RemoveDetailCellRender());
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
       
        table.getColumnModel().getColumn(3).setCellRenderer(new OrderOptionColorCellRender());
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
                            case 4:
                                com.setForeground(new Color(202, 48, 48));
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
                            case 7:
                                com.setForeground(new Color(202, 48, 48));
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
    /////////////////////////

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        crazyPanel_Main = new raven.crazypanel.CrazyPanel();
        btn_refresh = new javax.swing.JButton();
        crazyPanel_Detail = new raven.crazypanel.CrazyPanel();
        lbl_OrderID = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        cbx_Status = new javax.swing.JComboBox<>();
        btn_Save = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        lbl_InniDate = new javax.swing.JLabel();
        lbl_OrderPrice = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        lbl_deliDate = new javax.swing.JLabel();
        txt_Address = new javax.swing.JTextField();
        txt_OrderID = new javax.swing.JLabel();
        txt_initDate = new javax.swing.JLabel();
        txt_OrderPrice = new javax.swing.JLabel();
        txt_deliDate = new javax.swing.JLabel();
        txt_Phone = new javax.swing.JTextField();
        txt_user = new javax.swing.JTextField();
        btn_check = new javax.swing.JLabel();
        lbl_setDelidate = new javax.swing.JLabel();
        cbx_ChangeMode = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        crazyPanel_DetailTable = new raven.crazypanel.CrazyPanel();
        jTabbedPane = new javax.swing.JTabbedPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_Detail = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbl_Option = new javax.swing.JTable();
        crazyPanel_AddMode = new raven.crazypanel.CrazyPanel();
        txt_Search = new javax.swing.JTextField();
        cbx_Search = new javax.swing.JComboBox<>();
        cbx_Rom = new javax.swing.JComboBox<>();
        cbx_Ram = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        crazyPanel_SelectAll = new raven.crazypanel.CrazyPanel();
        ckb_SelectAll = new javax.swing.JCheckBox();
        lbl_ItemSelected = new javax.swing.JLabel();
        lbl_search = new javax.swing.JLabel();
        lbl_totalprice = new javax.swing.JLabel();

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

        lbl_OrderID.setText("Mã đơn hàng");

        jLabel17.setText("Trạng thái");

        cbx_Status.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbx_StatusItemStateChanged(evt);
            }
        });

        btn_Save.setText("Lưu");
        btn_Save.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SaveActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("Thông tin chung");

        lbl_InniDate.setText("Ngày tạo");

        lbl_OrderPrice.setText("Tổng trị giá");

        jLabel19.setText("Người đặt");

        jLabel20.setText("Địa chỉ");

        jLabel24.setText("Số điện thoại");

        lbl_deliDate.setText("Ngày giao");

        txt_Address.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txt_Address.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_AddressKeyReleased(evt);
            }
        });

        txt_OrderID.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        txt_initDate.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        txt_OrderPrice.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        txt_deliDate.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        txt_Phone.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txt_Phone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_PhoneKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_PhoneKeyTyped(evt);
            }
        });

        txt_user.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txt_user.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_userKeyTyped(evt);
            }
        });

        btn_check.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-eye-40.png"))); // NOI18N
        btn_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_checkMouseClicked(evt);
            }
        });

        lbl_setDelidate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/calendar.png"))); // NOI18N
        lbl_setDelidate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_setDelidate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_setDelidateMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout crazyPanel_DetailLayout = new javax.swing.GroupLayout(crazyPanel_Detail);
        crazyPanel_Detail.setLayout(crazyPanel_DetailLayout);
        crazyPanel_DetailLayout.setHorizontalGroup(
            crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crazyPanel_DetailLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23)
                    .addGroup(crazyPanel_DetailLayout.createSequentialGroup()
                        .addComponent(lbl_OrderID, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txt_OrderID, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(crazyPanel_DetailLayout.createSequentialGroup()
                        .addComponent(lbl_InniDate, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txt_initDate, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(crazyPanel_DetailLayout.createSequentialGroup()
                        .addComponent(lbl_OrderPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txt_OrderPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(crazyPanel_DetailLayout.createSequentialGroup()
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txt_user, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_check))
                    .addGroup(crazyPanel_DetailLayout.createSequentialGroup()
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_Phone, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(crazyPanel_DetailLayout.createSequentialGroup()
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_Address, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(crazyPanel_DetailLayout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbx_Status, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(crazyPanel_DetailLayout.createSequentialGroup()
                        .addComponent(lbl_deliDate, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_deliDate, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbl_setDelidate)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, crazyPanel_DetailLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btn_Save, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(128, 128, 128))
        );
        crazyPanel_DetailLayout.setVerticalGroup(
            crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crazyPanel_DetailLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel23)
                .addGap(12, 12, 12)
                .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_OrderID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_OrderID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_InniDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_initDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_OrderPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_OrderPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(crazyPanel_DetailLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_check, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_user, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_Phone, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_Address, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_setDelidate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_deliDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_deliDate, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(crazyPanel_DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbx_Status, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_Save, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        cbx_ChangeMode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chế độ", "Chỉ xem", "Chỉ thêm", "Chỉ sửa" }));
        cbx_ChangeMode.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbx_ChangeModeItemStateChanged(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Thông tin đơn đặt hàng");

        crazyPanel_DetailTable.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
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
        crazyPanel_DetailTable.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
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

        jTabbedPane.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTabbedPane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPaneMouseClicked(evt);
            }
        });
        jTabbedPane.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTabbedPaneKeyTyped(evt);
            }
        });

        tbl_Detail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sản phẩm", "Màu", "Bộ nhớ trong", "Bộ nhớ đệm", "Còn lại", "Số lượng", "Tổng tiền(VNĐ)", "", "data"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, false, true, false
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
            tbl_Detail.getColumnModel().getColumn(7).setResizable(false);
            tbl_Detail.getColumnModel().getColumn(7).setPreferredWidth(30);
            tbl_Detail.getColumnModel().getColumn(8).setMinWidth(0);
            tbl_Detail.getColumnModel().getColumn(8).setPreferredWidth(0);
            tbl_Detail.getColumnModel().getColumn(8).setMaxWidth(0);
        }

        jTabbedPane.addTab("Chi tiết đơn hàng", jScrollPane4);

        tbl_Option.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Mã sản phẩm", "Mã tùy chọn", "Màu", "Bộ nhớ trong", "Bộ nhớ đệm", "Giá bán(VNĐ)", "Còn lại", "data"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_Option.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_OptionMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbl_OptionMouseReleased(evt);
            }
        });
        jScrollPane5.setViewportView(tbl_Option);
        if (tbl_Option.getColumnModel().getColumnCount() > 0) {
            tbl_Option.getColumnModel().getColumn(0).setResizable(false);
            tbl_Option.getColumnModel().getColumn(0).setPreferredWidth(20);
            tbl_Option.getColumnModel().getColumn(8).setMinWidth(0);
            tbl_Option.getColumnModel().getColumn(8).setPreferredWidth(0);
            tbl_Option.getColumnModel().getColumn(8).setMaxWidth(0);
        }

        jTabbedPane.addTab("Bảng giá tùy chọn", jScrollPane5);

        crazyPanel_AddMode.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
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
        crazyPanel_AddMode.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
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

        txt_Search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_SearchKeyReleased(evt);
            }
        });

        cbx_Search.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tìm kiếm", "Mã sản phẩm", "Tên sản phẩm" }));
        cbx_Search.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbx_Searchcbx_SearchItemStateChanged(evt);
            }
        });

        cbx_Rom.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn", "16 GB", "32 GB", "64 GB", "128 GB", "256 GB", "512 GB", "1 T", "2 T" }));
        cbx_Rom.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbx_RomItemStateChanged(evt);
            }
        });

        cbx_Ram.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn", "1 GB", "2 GB", "3 GB", "4 GB", "5 GB", "6 GB", "7 GB", "8 GB", " " }));
        cbx_Ram.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbx_RamItemStateChanged(evt);
            }
        });

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel27.setText("Bộ nhớ đệm");

        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel28.setText("Bộ nhớ trong");

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

        lbl_search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-thick-arrow-pointing-up-30.png"))); // NOI18N
        lbl_search.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_search.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_searchlbl_SortModeMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout crazyPanel_AddModeLayout = new javax.swing.GroupLayout(crazyPanel_AddMode);
        crazyPanel_AddMode.setLayout(crazyPanel_AddModeLayout);
        crazyPanel_AddModeLayout.setHorizontalGroup(
            crazyPanel_AddModeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crazyPanel_AddModeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(crazyPanel_AddModeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(crazyPanel_AddModeLayout.createSequentialGroup()
                        .addComponent(cbx_Search, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_Search, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_search))
                    .addGroup(crazyPanel_AddModeLayout.createSequentialGroup()
                        .addGroup(crazyPanel_AddModeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(crazyPanel_AddModeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbx_Ram, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbx_Rom, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(crazyPanel_SelectAll, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        crazyPanel_AddModeLayout.setVerticalGroup(
            crazyPanel_AddModeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, crazyPanel_AddModeLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(crazyPanel_AddModeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txt_Search, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_search, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbx_Search))
                .addGap(4, 4, 4)
                .addGroup(crazyPanel_AddModeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(crazyPanel_AddModeLayout.createSequentialGroup()
                        .addGroup(crazyPanel_AddModeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(crazyPanel_AddModeLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(cbx_Rom)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(crazyPanel_AddModeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbx_Ram, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(crazyPanel_AddModeLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(crazyPanel_SelectAll, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(364, 364, 364))
        );

        lbl_totalprice.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbl_totalprice.setText("Tổng trị giá: ");

        javax.swing.GroupLayout crazyPanel_DetailTableLayout = new javax.swing.GroupLayout(crazyPanel_DetailTable);
        crazyPanel_DetailTable.setLayout(crazyPanel_DetailTableLayout);
        crazyPanel_DetailTableLayout.setHorizontalGroup(
            crazyPanel_DetailTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, crazyPanel_DetailTableLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(crazyPanel_DetailTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTabbedPane)
                    .addGroup(crazyPanel_DetailTableLayout.createSequentialGroup()
                        .addComponent(crazyPanel_AddMode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_totalprice, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)))
                .addContainerGap())
        );
        crazyPanel_DetailTableLayout.setVerticalGroup(
            crazyPanel_DetailTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, crazyPanel_DetailTableLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(crazyPanel_DetailTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(crazyPanel_AddMode, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(crazyPanel_DetailTableLayout.createSequentialGroup()
                        .addComponent(lbl_totalprice, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout crazyPanel_MainLayout = new javax.swing.GroupLayout(crazyPanel_Main);
        crazyPanel_Main.setLayout(crazyPanel_MainLayout);
        crazyPanel_MainLayout.setHorizontalGroup(
            crazyPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crazyPanel_MainLayout.createSequentialGroup()
                .addGap(384, 384, 384)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(crazyPanel_MainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(crazyPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(crazyPanel_MainLayout.createSequentialGroup()
                        .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbx_ChangeMode, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(crazyPanel_MainLayout.createSequentialGroup()
                        .addComponent(crazyPanel_Detail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(crazyPanel_DetailTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        crazyPanel_MainLayout.setVerticalGroup(
            crazyPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crazyPanel_MainLayout.createSequentialGroup()
                .addGroup(crazyPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(crazyPanel_MainLayout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addGroup(crazyPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbx_ChangeMode, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(crazyPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(crazyPanel_Detail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(crazyPanel_DetailTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
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

    private void cbx_ChangeModeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbx_ChangeModeItemStateChanged
        if (evt.getStateChange() == 1) {
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
            }
        }
    }//GEN-LAST:event_cbx_ChangeModeItemStateChanged

    private void btn_SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SaveActionPerformed
        if (GUI_tableProduct.currMode == Utils.ADD_MODE) {
            insertOrder();
            GUI_tableProduct.currMode = Utils.UPDATE_MODE;
            cbx_ChangeMode.setSelectedIndex(GUI_tableProduct.currMode);
        } 
        else if (GUI_tableProduct.currMode == Utils.UPDATE_MODE) {
            updateOrder();
        }
    }//GEN-LAST:event_btn_SaveActionPerformed

    private void cbx_StatusItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbx_StatusItemStateChanged

    }//GEN-LAST:event_cbx_StatusItemStateChanged

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        clear();
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void ckb_SelectAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckb_SelectAllActionPerformed
        setTableCheckbox(optionModel, tbl_Option);
    }//GEN-LAST:event_ckb_SelectAllActionPerformed

    private void cbx_RamItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbx_RamItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cbx_RamItemStateChanged

    private void cbx_RomItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbx_RomItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cbx_RomItemStateChanged

    private void cbx_Searchcbx_SearchItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbx_Searchcbx_SearchItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cbx_Searchcbx_SearchItemStateChanged

    private void txt_SearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_SearchKeyReleased
        //        search();
    }//GEN-LAST:event_txt_SearchKeyReleased

    private void jTabbedPaneKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabbedPaneKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTabbedPaneKeyTyped

    private void jTabbedPaneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPaneMouseClicked
        switch (jTabbedPane.getSelectedIndex()) {
            case 1:
            crazyPanel_SelectAll.setVisible(true);
            break;
            default:
            crazyPanel_SelectAll.setVisible(false);
        }
    }//GEN-LAST:event_jTabbedPaneMouseClicked

    private void tbl_OptionMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_OptionMouseReleased

    }//GEN-LAST:event_tbl_OptionMouseReleased

    private void tbl_OptionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_OptionMouseClicked
        int row = tbl_Option.getSelectedRow();
        if ((boolean) optionModel.getValueAt(row, 0)) {
            optionModel.setValueAt(false, row, 0);
        } 
        else {
            optionModel.setValueAt(true, row, 0);
        }
        setSelectedAll(optionModel, tbl_Option);
        if (tbl_Option.getSelectedRowCount() == 1) {
            DTO_Option option = (DTO_Option) tbl_Option.getValueAt(tbl_Option.getSelectedRow(), 8);
            for (int j = 0; j < detailModel.getRowCount(); j++) {
                DTO_InvoiceDetail detail = (DTO_InvoiceDetail) tbl_Detail.getValueAt(j, 8);
                if (option.getId().equals(detail.getOptionid())) {
                    notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Đã chọn rồi !!!");
                    tbl_Option.setValueAt(true, tbl_Option.getSelectedRow(), 0);
                    return;
                }
            }
            DTO_InvoiceDetail newDetail = new DTO_InvoiceDetail(txt_OrderID.getText().toString(), option.getId(),
                     1, Double.parseDouble((tbl_Option.getValueAt(tbl_Option.getSelectedRow(), 6) + "").replace(",", "")));
            newDetail.setOption(option);
            detailList.add(newDetail);
            renderDetailTable(detailList);
            sumAmount();
            createID();
            return;
        }
        
    }//GEN-LAST:event_tbl_OptionMouseClicked

    private void tbl_DetailMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_DetailMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tbl_DetailMouseReleased

    private void tbl_DetailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_DetailMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbl_DetailMouseClicked

    private void lbl_searchlbl_SortModeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_searchlbl_SortModeMouseClicked
        optionList.clear();
        String input = txt_Search.getText();
        ArrayList<DTO_Product> listPD = dal_ProDuct.getList();

        for (DTO_Product pd : listPD) {
            String productID = pd.getId();
            ArrayList<DTO_Option> listOP = dal_Option.getList(productID);
            int inputRom = cbx_Rom.getSelectedIndex();
            int inputRam = cbx_Ram.getSelectedIndex(); 
            
            switch (cbx_Search.getSelectedIndex()) {
                case 1:
                    if (input.equalsIgnoreCase(productID)) {
                        if (inputRom == 0 && inputRam == 0) {
                            addData(optionList, listOP);
                        } else if (inputRom == 0 && inputRam != 0) {
                            searchWithRam(listOP);
                        } else if (inputRom != 0 && inputRam == 0) {
                            searchWithRom(listOP);
                        } else {
                            searchWithRamAndRom(listOP);
                        }
                    }
                    break;
                    
                case 2:
                    if (input.equalsIgnoreCase(pd.getProductname())) {
                        if (inputRom == 0 && inputRam == 0) {
                            addData(optionList, listOP);
                        } else if (inputRom == 0 && inputRam != 0) {
                            searchWithRam(listOP);
                        } else if (inputRom != 0 && inputRam == 0) {
                            searchWithRom(listOP);
                        } else {
                            searchWithRamAndRom(listOP);
                        }

                    }
                    break;
            }
        }
        if (cbx_Search.getSelectedIndex() == 0) {
            notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Vui lòng chọn chức năng tìm kiếm !!!");
        }
        if (optionList.size() != 0) {
//            for (int i = 0; i < detailModel.getRowCount(); i++) {
//                for (int j = 0; j < optionModel.getRowCount(); j++) {
//                    if (detailList.get(i).getOptionid().equals(optionList.get(j).getId())) {
//                        tbl_Option.setValueAt(true, j, 0);
//                    }
//                }
//            }
            renderOptionTable(optionList);
            notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Tìm kiếm thành công !!!");
        } 
        else {
            notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Tìm kiếm thất bại !!!");
        }
    }//GEN-LAST:event_lbl_searchlbl_SortModeMouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        GUI_tableOrder.formOrder = null;
    }//GEN-LAST:event_formWindowClosing

    private void txt_PhoneKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_PhoneKeyTyped
        char c = evt.getKeyChar();
        
        if (Character.isAlphabetic(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_txt_PhoneKeyTyped

    private void txt_userKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_userKeyTyped
        char c = evt.getKeyChar();
        
        if (Character.isAlphabetic(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_txt_userKeyTyped

    private void btn_checkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_checkMouseClicked
        if (GUI_tableOrder.currMode != Utils.ADD_MODE) {
            if (formUser != null) {
                notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Bạn đã mở cửa sổ này rồi!!!");
                return;
            }
            Utils.selectedUser = Utils.selectedOrder.getUser();
            currMode = Utils.VIEW_MODE;
            formUser = new GUI_formUser();
            formUser.setVisible(true);
        } 
        else {
            DAL_User dal_User = new DAL_User();
            ArrayList<DTO_User> listUser = dal_User.getList(0);

            // Phone & Address
            for (DTO_User us : listUser) {
                if (txt_user.getText().equals(us.getPhone())) {
                    txt_Address.setText(us.getAddress());
                    txt_Phone.setText(us.getPhone());
                    txt_user.setText(us.getFullname());
                    break;
                }
            }
            if (txt_Phone.getText().equals("")) {
                notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Kiểm tra thất bại !!!");

            } else {
                createID();
                notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Kiểm tra thành công !!!");
            }
        }
    }//GEN-LAST:event_btn_checkMouseClicked

    private void txt_PhoneKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_PhoneKeyReleased
        createID();
    }//GEN-LAST:event_txt_PhoneKeyReleased

    private void txt_AddressKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_AddressKeyReleased
        createID();
    }//GEN-LAST:event_txt_AddressKeyReleased

    private void lbl_setDelidateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_setDelidateMouseClicked
        new GUI_DateChooser(txt_deliDate).setVisible(true);
    }//GEN-LAST:event_lbl_setDelidateMouseClicked

    private void searchWithRamAndRom(ArrayList<DTO_Option> listOP) {
        int ram;
        ArrayList<DTO_Option> listRam = new ArrayList<DTO_Option>();
        switch (cbx_Ram.getSelectedIndex()) {
            case 1:
                ram = 1;
                addDataWithRam(listRam, listOP, ram);
                break;
            case 2:
                ram = 2;
                addDataWithRam(listRam, listOP, ram);
                break;
            case 3:
                ram = 3;
                addDataWithRam(listRam, listOP, ram);
                break;
            case 4:
                ram = 4;
                addDataWithRam(listRam, listOP, ram);
                break;
            case 5:
                ram = 5;
                addDataWithRam(listRam, listOP, ram);
                break;
            case 6:
                ram = 6;
                addDataWithRam(listRam, listOP, ram);
                break;
            case 7:
                ram = 7;
                addDataWithRam(listRam, listOP, ram);
                break;
            case 8:
                ram = 8;
                addDataWithRam(listRam, listOP, ram);
                break;
        }

        if (listRam.size() != 0) {
            int rom;
            switch (cbx_Rom.getSelectedIndex()) {
                case 1:
                    rom = 16;
                    addDataWithRom(optionList, listRam, rom);
                    break;
                case 2:
                    rom = 32;
                    addDataWithRom(optionList, listRam, rom);
                    break;
                case 3:
                    rom = 64;
                    addDataWithRom(optionList, listRam, rom);
                    break;
                case 4:
                    rom = 128;
                    addDataWithRom(optionList, listRam, rom);
                    break;
                case 5:
                    rom = 256;
                    addDataWithRom(optionList, listRam, rom);
                    break;
                case 6:
                    rom = 512;
                    addDataWithRom(optionList, listRam, rom);
                    break;
                case 7:
                    rom = 1000;
                    addDataWithRom(optionList, listRam, rom);
                    break;
                case 8:
                    rom = 2000;
                    addDataWithRom(optionList, listRam, rom);
                    break;
            }
        }
        //renderOptionTable(optionList);
    }

    private void searchWithRam(ArrayList<DTO_Option> listOP) {
        int ram;
        switch (cbx_Ram.getSelectedIndex()) {
            case 0:
                addData(optionList, listOP);
                break;
            case 1:
                ram = 1;
                addDataWithRam(optionList, listOP, ram);
                break;
            case 2:
                ram = 2;
                addDataWithRam(optionList, listOP, ram);
                break;
            case 3:
                ram = 3;
                addDataWithRam(optionList, listOP, ram);
                break;
            case 4:
                ram = 4;
                addDataWithRam(optionList, listOP, ram);
                break;
            case 5:
                ram = 5;
                addDataWithRam(optionList, listOP, ram);
                break;
            case 6:
                ram = 6;
                addDataWithRam(optionList, listOP, ram);
                break;
            case 7:
                ram = 7;
                addDataWithRam(optionList, listOP, ram);
                break;
            case 8:
                ram = 8;
                addDataWithRam(optionList, listOP, ram);
                break;
        }
        renderOptionTable(optionList);
    }

    private void searchWithRom(ArrayList<DTO_Option> listOP) {
        int rom;
        switch (cbx_Rom.getSelectedIndex()) {
            case 0:
                addData(optionList, listOP);
                break;
            case 1:
                rom = 16;
                addDataWithRom(optionList, listOP, rom);
                break;
            case 2:
                rom = 32;
                addDataWithRom(optionList, listOP, rom);
                break;
            case 3:
                rom = 64;
                addDataWithRom(optionList, listOP, rom);
                break;
            case 4:
                rom = 128;
                addDataWithRom(optionList, listOP, rom);
                break;
            case 5:
                rom = 256;
                addDataWithRom(optionList, listOP, rom);
                break;
            case 6:
                rom = 512;
                addDataWithRom(optionList, listOP, rom);
                break;
            case 7:
                rom = 1000;
                addDataWithRom(optionList, listOP, rom);
                break;
            case 8:
                rom = 2000;
                addDataWithRom(optionList, listOP, rom);
                break;
        }
        renderOptionTable(optionList);
    }

    private void addDataWithRom(ArrayList list, ArrayList<DTO_Option> listop, int rom) {

        for (DTO_Option op : listop) {
            if (op.getRom() == rom && op.getRam() > 0) {
                list.add(op);
            }
        }
    }

    private void addDataWithRam(ArrayList list, ArrayList<DTO_Option> listop, int ram) {
        for (DTO_Option op : listop) {
            if (op.getRam() == ram && op.getRemain() > 0) {
                list.add(op);
            }
        }
    }

    private void addData(ArrayList list, ArrayList<DTO_Option> listop) {
        for (DTO_Option op : listop) {
            if (op.getRemain() > 0) {
                list.add(op);
            }
        }
    }

    private int getStatusValue(String status) {
        switch (status) {
            case "Chờ xử lý":
                return 0;
            case "Đã xác nhận":
                return 1;
            case "Đang vận chuyển":
                return 2;
            case "Bị hủy":
                return 3;
            case "Giao thành công":
                return 4;
            case "Giao thất bại":
                return 5;
            default:
                return 6;
        }
    }
    
    private String getStatusString(int status) {
        String result = "Chờ xử lý";
        switch (status) {
            case 1:
                result = "Đã xác nhận";
                break;
            case 2:
                result = "Đang vận chuyển";
                break;
            case 3:
                result = "Bị hủy";
                break;
            case 4:
                result = "Giao thành công";
                break;
            case 5:
                result = "Giao thất bại";
                break;
            case 6:
                result = "Tại shop";
                break;
            default:
        }
        return result;
    }
    
    //Select all
    private void setTableCheckbox(DefaultTableModel model, JTable table) {
        if (jTabbedPane.getSelectedIndex() == 1) {
            for (int i = 0; i < model.getRowCount(); i++) {
                table.setValueAt(ckb_SelectAll.isSelected(), i, 0);
            }
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
            lbl_ItemSelected.setText("Đã chọn " + selectedItem + " SP");
        } else if (selectedItem == 0) {
            lbl_ItemSelected.setVisible(false);
        }
        //System.out.println(ItemSelected);
    }
    ///////

    //Mode
    private void setViewMode() {
        GUI_tableProduct.currMode = Utils.VIEW_MODE;
        showOrder();
        //TextField
        txt_user.setEditable(false);
        txt_Phone.setEditable(false);
        txt_Address.setEditable(false);
        
        //Button
        btn_refresh.setEnabled(false);
        lbl_setDelidate.setEnabled(false);
        btn_Save.setEnabled(false);

        //Combobox
        cbx_ChangeMode.setEnabled(true);
        cbx_Status.setEnabled(false);
        cbx_data.clear();
        cbx_data.add(getStatusString(Utils.selectedOrder.getStatus()));
        for (String a : cbx_data) {
            cbx_Status.addItem(a);
        }
        cbx_Status.setSelectedIndex(0);
        
        //Panel
        crazyPanel_DetailTable.setVisible(true);
        crazyPanel_AddMode.setVisible(false);

        //JTabPane
        jTabbedPane.setSelectedIndex(0);
        jTabbedPane.setEnabled(false);
        
        tbl_Detail.setEnabled(false);
    }

    private void setAddMode() {
        GUI_tableProduct.currMode = Utils.ADD_MODE;

        clear();
        //TextField
        txt_user.setEditable(true);
        txt_Phone.setEditable(true);
        txt_Address.setEditable(true);

        //Button
        btn_refresh.setEnabled(true);
        lbl_setDelidate.setEnabled(false);
        btn_Save.setEnabled(true);
        cbx_data.add(getStatusString(6));
        for (String a : cbx_data) {
            cbx_Status.addItem(a);
        }
        cbx_Status.setSelectedIndex(0);

        //Combobox
        cbx_ChangeMode.setEnabled(true);

        //Panel
        crazyPanel_DetailTable.setVisible(true);
        crazyPanel_AddMode.setVisible(true);

        //JTabPane
        jTabbedPane.setSelectedIndex(1);
        jTabbedPane.setEnabled(true);
        
        tbl_Detail.setEnabled(true);

    }

    private void setUpdateMode() {
        GUI_tableProduct.currMode = Utils.UPDATE_MODE;
        showOrder();
        //TextField
        txt_user.setEditable(false);
        txt_Phone.setEditable(false);
        txt_Address.setEditable(false);
        
        //Button
        btn_refresh.setEnabled(false);
        btn_Save.setEnabled(true);

        //Combobox
        cbx_ChangeMode.setEnabled(true);
        cbx_Status.setEnabled(true);
        cbx_data.clear();
        switch (Utils.selectedOrder.getStatus()) {
            case 0:
                cbx_data.add("Chờ xử lý");
                cbx_data.add("Đã xác nhận");
                cbx_data.add("Bị hủy");
                lbl_setDelidate.setEnabled(false);
                break;
            case 1:
                cbx_data.add("Đã xác nhận");
                cbx_data.add("Đang vận chuyển");
                cbx_data.add("Bị hủy");
                lbl_setDelidate.setEnabled(true);
                break;
            case 2:
                cbx_data.add("Đang vận chuyển");
                cbx_data.add("Giao thành công");
                cbx_data.add("Giao thất bại");
                lbl_setDelidate.setEnabled(true);
                break;
            case 3:
                cbx_data.add("Bị hủy");
                lbl_setDelidate.setEnabled(false);
                break;
            case 4:
                cbx_data.add("Giao thành công");
                lbl_setDelidate.setEnabled(false);
                break;
            case 5:
                cbx_data.add("Giao thất bại");
                lbl_setDelidate.setEnabled(false);
                break;
            case 6:
                cbx_data.add("Tại shop");
                lbl_setDelidate.setEnabled(false);
                break;
        }
        for (String a : cbx_data) {
            cbx_Status.addItem(a);
        }
        //cbx_Status.setSelectedIndex(0);

        //Panel
        crazyPanel_DetailTable.setVisible(true);
        crazyPanel_AddMode.setVisible(false);

        //JTabPane
        jTabbedPane.setSelectedIndex(0);
        jTabbedPane.setEnabled(false);
        
        tbl_Detail.setEnabled(false);
    }

    //Insert
    private void createID() {
        if (detailList.size() == 0 || txt_user.getText().toString().equals("") || txt_Phone.getText().toString().equals("") 
                || txt_Address.getText().toString().equals("") || GUI_tableProduct.currMode != Utils.ADD_MODE) {
            return;
        }
        DTO_Order newOrder = new DTO_Order();
        newOrder.setId(bus_order.getTotal() + 1);
        txt_OrderID.setText(newOrder.getId());
        
        for (int i = 0; i< detailList.size(); i++) {
            detailList.get(i).setInvoiceid(newOrder.getId());
        }
        
        LocalDateTime today = LocalDateTime.now();
        innidate = new DTO_Date(today.getYear(), today.getMonthValue(), today.getDayOfMonth(), today.getHour(), today.getMinute(), today.getSecond());
        txt_initDate.setText(innidate.format());
    }

    private void insertOrder() {
        if (txt_OrderID.getText().isEmpty()) {
            notifications.getInstance().show(Notifications.Type.INFO
                    , Notifications.Location.TOP_CENTER, "Không được để trống thông tin !!!");
            return;
        }
        else if (bus_user.phoneIsExist(lbl_OrderID.getText(), txt_Phone.getText().toString())) {
            notifications.getInstance().show(Notifications.Type.INFO
                    , Notifications.Location.TOP_CENTER, "Số điện thoại này đã tồn tại !!!");
            return;
        }
        String id = txt_OrderID.getText().toString();
        String phone = txt_Phone.getText().toString();
        String address = txt_Address.getText().toString();
        
        DTO_Order newOrder = new DTO_Order(id, Utils.loginedUser.getId(), phone, address, innidate, orderPrice, null, 0, 6);
        newOrder.setDetails(detailList);

        int check = bus_order.insert(newOrder);
        if (check != -1) {
            notifications.getInstance().show(Notifications.Type.INFO
                    , Notifications.Location.TOP_CENTER, "Thêm đơn hàng thành công!!!");
            Utils.selectedOrder = newOrder;
            clear();
            this.dispose();
        } else {
            notifications.getInstance().show(Notifications.Type.INFO
                    , Notifications.Location.TOP_CENTER, "Thêm đơn hàng thất bại!!!");
        }
    }
    /////////

    //Update 
    private void updateOrder() {
        int check = -2;
        if (!txt_deliDate.getText().toString().equals("")) {
            String[] delidateString = txt_deliDate.getText().toString().split("/");
            DTO_Date delidate = new DTO_Date(Integer.parseInt(delidateString[2]), Integer.parseInt(delidateString[1]), Integer.parseInt(delidateString[0]), 0, 0, 0);
            Utils.selectedOrder.setDelidate(delidate);
            check = bus_order.updateDelidate(Utils.selectedOrder);
        }
        if (cbx_Status.getSelectedIndex() != 0) {
            if (Utils.selectedOrder.getStatus() == getStatusValue("Đã xác nhận") 
                    && getStatusValue(cbx_Status.getSelectedItem().toString()) == getStatusValue("Đang vận chuyển")) {
                if (!checkOptionRemain()) {
                    notifications.getInstance().show(Notifications.Type.INFO
                            , Notifications.Location.TOP_CENTER, "Tồn kho không đủ đáp ứng !!!");
                    return;
                }
            }
            Utils.selectedOrder.setStatus(getStatusValue(cbx_Status.getSelectedItem().toString()));
            check = bus_order.updateStatus(Utils.selectedOrder);
        }

        if (check == -1) {
            notifications.getInstance().show(Notifications.Type.INFO
                    , Notifications.Location.TOP_CENTER, "Cập nhật thông tin thất bại !!!");
        } 
        else if (check == -2) {
            notifications.getInstance().show(Notifications.Type.INFO
                    , Notifications.Location.TOP_CENTER, "Không có thay đổi !!!");
        } 
        else {
            notifications.getInstance().show(Notifications.Type.INFO
                    , Notifications.Location.TOP_CENTER, "Cập nhật thông tin thành công !!!");
            showOrder();
        }
    }

    private boolean checkOptionRemain() {
        boolean check = true;
        for (int i = 0; i < tbl_Detail.getRowCount(); i++) {
            DTO_InvoiceDetail detail = (DTO_InvoiceDetail) tbl_Detail.getValueAt(i, 8);
            if (detail.getOption().getRemain() < detail.getQuantity()) {
                check = false;
                break;
            }
        }
        return check;
    }
    
    private void showOrder() {
        txt_OrderID.setText(Utils.selectedOrder.getId());
        txt_initDate.setText(Utils.selectedOrder.getInitdate().format());
        txt_OrderPrice.setText(decimalFormat.format(Utils.selectedOrder.getPrice()) + " VNĐ");
        lbl_totalprice.setText("Tổng trị giá: " + decimalFormat.format(Utils.selectedOrder.getPrice()) + " VNĐ");
        txt_user.setText(Utils.selectedOrder.getUser().getFullname());
        txt_Phone.setText(Utils.selectedOrder.getPhone());
        txt_Address.setText(Utils.selectedOrder.getAddress());
        if (Utils.selectedOrder.getDelidate() == null) {
            txt_deliDate.setText("");
        }
        else {
            txt_deliDate.setText(Utils.selectedOrder.getDelidate().format().split(" ")[0]);
        }
        renderDetailTable(Utils.selectedOrder.getDetails());
    }

    private void sumAmount() {
        orderPrice = 0;
        for (int i = 0; i < tbl_Detail.getRowCount(); i++) {
            DTO_InvoiceDetail item = (DTO_InvoiceDetail) tbl_Detail.getValueAt(i, 8);
            orderPrice += item.getTotalprice();
        }
        lbl_totalprice.setText("Tổng trị giá: " + decimalFormat.format(orderPrice) + "VNĐ");
        txt_OrderPrice.setText(decimalFormat.format(orderPrice) + " VNĐ");
    }

    private void renderDetailTable(ArrayList<DTO_InvoiceDetail> list) {
        detailModel.setRowCount(0);
        for (DTO_InvoiceDetail detail : list) {
            Object data[] = new Object[9];
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
            data[4] = detail.getOption().getRemain();
            data[5] = detail.getQuantity();
            data[6] = decimalFormat.format(detail.getTotalprice());
            data[8] = detail;

            detailModel.addRow(data);
        }
    }

    private void renderOptionTable(ArrayList<DTO_Option> list) {
        optionModel.setRowCount(0);
        for (DTO_Option option : list) {
            Object data[] = new Object[9];
            data[0] = false;
            data[1] = option.getProductid();
            data[2] = option.getId();
            data[3] = option.getColor();
            String romData = option.getRom() + " GB";
            if (option.getRom() >= 1000) {
                romData = (option.getRom() / 1000) + " T";
            }
            String ramData = option.getRam() + " GB";
            if (option.getRam() >= 1000) {
                ramData = (option.getRam() / 1000) + " T";
            }
            data[4] = romData;
            data[5] = ramData;
            data[6] = decimalFormat.format(option.getSaleprice());
            data[7] = option.getRemain();
            data[8] = option;

            optionModel.addRow(data);
        }
    }

    //Clear
    private void clear() {
        //Label
        txt_OrderID.setText("");
        txt_initDate.setText("");
        txt_OrderPrice.setText("");
        txt_Address.setText("");
        txt_Phone.setText("");
        txt_Address.setText("");
        txt_deliDate.setText("");

        //Combobox
        cbx_Search.setSelectedIndex(0);
        cbx_Rom.setSelectedIndex(0);
        cbx_Ram.setSelectedIndex(0);

        //Table
        detailModel.setRowCount(0);
        optionModel.setRowCount(0);
    }

    public static void main(String args[]) {
        FlatRobotoFont.install();
        FlatLaf.registerCustomDefaultsSource("crazypanel");
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
        //FlatMacLightLaf.setup();
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI_formOrder().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Save;
    private javax.swing.JLabel btn_check;
    private javax.swing.JButton btn_refresh;
    private javax.swing.JComboBox<String> cbx_ChangeMode;
    private javax.swing.JComboBox<String> cbx_Ram;
    private javax.swing.JComboBox<String> cbx_Rom;
    private javax.swing.JComboBox<String> cbx_Search;
    private javax.swing.JComboBox<String> cbx_Status;
    private javax.swing.JCheckBox ckb_SelectAll;
    private raven.crazypanel.CrazyPanel crazyPanel_AddMode;
    private raven.crazypanel.CrazyPanel crazyPanel_Detail;
    private raven.crazypanel.CrazyPanel crazyPanel_DetailTable;
    private raven.crazypanel.CrazyPanel crazyPanel_Main;
    private raven.crazypanel.CrazyPanel crazyPanel_SelectAll;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JLabel lbl_InniDate;
    private javax.swing.JLabel lbl_ItemSelected;
    private javax.swing.JLabel lbl_OrderID;
    private javax.swing.JLabel lbl_OrderPrice;
    private javax.swing.JLabel lbl_deliDate;
    private javax.swing.JLabel lbl_search;
    private javax.swing.JLabel lbl_setDelidate;
    private javax.swing.JLabel lbl_totalprice;
    private javax.swing.JTable tbl_Detail;
    private javax.swing.JTable tbl_Option;
    private javax.swing.JTextField txt_Address;
    private javax.swing.JLabel txt_OrderID;
    private javax.swing.JLabel txt_OrderPrice;
    private javax.swing.JTextField txt_Phone;
    private javax.swing.JTextField txt_Search;
    private javax.swing.JLabel txt_deliDate;
    private javax.swing.JLabel txt_initDate;
    private javax.swing.JTextField txt_user;
    // End of variables declaration//GEN-END:variables
}
