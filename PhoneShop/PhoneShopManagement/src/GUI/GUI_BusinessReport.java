package GUI;

import BUS.BUS_Order;
import DTO.DTO_BusinessReport;
import java.awt.Color;
import java.time.LocalDate;
import java.util.ArrayList;
import Custom.chart.ModelChart;


public class GUI_BusinessReport extends javax.swing.JPanel {
    private BUS_Order bus_order = new BUS_Order();
    private LocalDate today = LocalDate.now();
    
    public GUI_BusinessReport() {
        initComponents();
        setYearData();
        chart.setTitle("Tổng trị giá(VNĐ)");
        chart.addLegend("Doanh thu", Color.decode("#7b4397"), Color.decode("#dc2430"));
        chart.addLegend("Chi phí", Color.decode("#e65c00"), Color.decode("#F9D423"));
        chart.addLegend("Lợi nhuận", Color.decode("#0099F7"), Color.decode("#F11712"));
        cbx_year.setSelectedItem(today.getYear() + "");
        
    }

    private void setYearData() {
        ArrayList <Object> yyyy = new ArrayList<>();
        for(int i = today.getYear() - 10; i <= today.getYear(); i++) {
            yyyy.add(i);
        }
        for(Object a : yyyy) {
            cbx_year.addItem(a + "");
        }
    }
    
    private void setData(int year) {
        chart.clear();
        for (DTO_BusinessReport report : bus_order.getBusinessReportByYear(year)) {
            chart.addData(new ModelChart("Tháng " + report.getMonth(), new double[]{report.getRevenue(), report.getExpense(), report.getProfit()}));
        }
        chart.start();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        crazyPanel1 = new raven.crazypanel.CrazyPanel();
        jLabel1 = new javax.swing.JLabel();
        chart = new Custom.chart.CurveLineChart();
        cbx_year = new javax.swing.JComboBox<>();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Báo cáo tình hình kinh doanh theo năm");

        chart.setForeground(new java.awt.Color(153, 153, 153));

        cbx_year.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        cbx_year.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbx_yearItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout crazyPanel1Layout = new javax.swing.GroupLayout(crazyPanel1);
        crazyPanel1.setLayout(crazyPanel1Layout);
        crazyPanel1Layout.setHorizontalGroup(
            crazyPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crazyPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(crazyPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chart, javax.swing.GroupLayout.DEFAULT_SIZE, 621, Short.MAX_VALUE)
                    .addGroup(crazyPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbx_year, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(21, 21, 21))
        );
        crazyPanel1Layout.setVerticalGroup(
            crazyPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crazyPanel1Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(crazyPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbx_year, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(chart, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(crazyPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(crazyPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbx_yearItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbx_yearItemStateChanged
        if (evt.getStateChange() == 1) {
            setData(Integer.parseInt(cbx_year.getSelectedItem() + ""));
            System.out.println(cbx_year.getSelectedItem() + "");
        }
    }//GEN-LAST:event_cbx_yearItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbx_year;
    private Custom.chart.CurveLineChart chart;
    private raven.crazypanel.CrazyPanel crazyPanel1;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
