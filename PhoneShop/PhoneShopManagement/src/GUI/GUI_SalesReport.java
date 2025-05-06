package GUI;

import BUS.BUS_Product;
import DTO.DTO_SalesReport;
import java.awt.Color;
import java.util.ArrayList;
import javaswingdev.chart.ModelPieChart;
import javaswingdev.chart.PieChart;


public class GUI_SalesReport extends javax.swing.JPanel {
    private BUS_Product bus_product = new BUS_Product();
    private ArrayList<Color> colors = new ArrayList<>();
    
    public GUI_SalesReport() {
        initComponents();
        setColorsData();
        pieChart.setChartType(PieChart.PeiChartType.DONUT_CHART);
        for (int i = 0; i < bus_product.getSalesReport().size(); i++) {
            DTO_SalesReport report = bus_product.getSalesReport().get(i);
            pieChart.addData(new ModelPieChart(report.getBrandname(), report.getSold(), colors.get(i)));
        }
    }
    
    private void setColorsData() {
        colors.add(Color.red);
        colors.add(Color.blue);
        colors.add(Color.yellow);
        colors.add(Color.green);
        colors.add(Color.pink);
        colors.add(Color.orange);
        colors.add(Color.cyan);
        colors.add(Color.gray);
        colors.add(Color.MAGENTA);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        crazyPanel1 = new raven.crazypanel.CrazyPanel();
        jLabel1 = new javax.swing.JLabel();
        pieChart = new javaswingdev.chart.PieChart();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Báo cáo doanh số theo thương hiệu");

        javax.swing.GroupLayout crazyPanel1Layout = new javax.swing.GroupLayout(crazyPanel1);
        crazyPanel1.setLayout(crazyPanel1Layout);
        crazyPanel1Layout.setHorizontalGroup(
            crazyPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, crazyPanel1Layout.createSequentialGroup()
                .addGap(160, 160, 160)
                .addComponent(pieChart, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                .addGap(151, 151, 151))
            .addGroup(crazyPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        crazyPanel1Layout.setVerticalGroup(
            crazyPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crazyPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(pieChart, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                .addGap(14, 14, 14))
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private raven.crazypanel.CrazyPanel crazyPanel1;
    private javax.swing.JLabel jLabel1;
    private javaswingdev.chart.PieChart pieChart;
    // End of variables declaration//GEN-END:variables
}
