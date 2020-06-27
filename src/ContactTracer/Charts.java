package ContactTracer;

import SourceDS.LinkedList;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.*;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.util.DefaultShadowGenerator;

public class Charts extends javax.swing.JFrame {
    
    public Charts(){
        
    }
    
    public Charts(String date, List infectedList, HashMap infectedIDWithDate, HashMap recoveredNumberWithDate, HashMap deadNumberWithDate, int todayInfected, int todayRecovered, int todayDead, int currentWard, int total) {
        initComponents(date, infectedList, infectedIDWithDate, recoveredNumberWithDate, deadNumberWithDate, todayInfected, todayRecovered, todayDead, currentWard, total);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents(String date, List infectedList, HashMap infectedIDWithDate, HashMap recoveredNumberWithDate, HashMap deadNumberWithDate, int todayInfected, int todayRecovered, int todayDead, int currentWard, int total) {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        pnlReport = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        datelabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        jButton1.setText("Daily Report");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt, todayInfected, todayRecovered, todayDead, currentWard);
            }
        });

        pnlReport.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Daily Report on El-Corona", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N
        pnlReport.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        pnlReport.setLayout(new javax.swing.BoxLayout(pnlReport, javax.swing.BoxLayout.LINE_AXIS));

        jButton2.setText("Monthly Report");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt, infectedList, infectedIDWithDate, recoveredNumberWithDate, deadNumberWithDate);
            }
        });

        datelabel.setText("Date: " + date + "     Currently being cured: " + currentWard + "     Total COVID-19 patients: " + total);
        datelabel.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }

            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                datelabelInputMethodTextChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1)
                                .addGap(72, 72, 72)
                                .addComponent(jButton2)
                                .addGap(219, 219, 219))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(pnlReport, javax.swing.GroupLayout.PREFERRED_SIZE, 1200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 22, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(158, 158, 158)
                                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(datelabel)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(datelabel)
                                .addGap(8, 8, 8)
                                .addComponent(pnlReport, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton1)
                                        .addComponent(jButton2))
                                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt, int todayInfected, int todayRecovered, int todayDead, int currentWard) {//GEN-FIRST:event_jButton1ActionPerformed
        
        DefaultCategoryDataset bar = new DefaultCategoryDataset();

        bar.setValue(todayInfected, "Infected", "Infected");
        bar.setValue(todayRecovered, "Recovered", "Recovered");
        bar.setValue(todayDead, "Dead", "Dead");

        JFreeChart jchart = ChartFactory.createBarChart3D("Report on El-Corona", "Type Of Case", "No Of Cases",
                bar, PlotOrientation.VERTICAL, true, true, false);

        CategoryPlot categoryplot = jchart.getCategoryPlot();
        categoryplot.setRangeGridlinePaint(Color.BLACK);

        ChartPanel chartpanel = new ChartPanel(jchart);
        pnlReport.removeAll();
        pnlReport.add(chartpanel);
        pnlReport.updateUI();

    }
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt, List<String> infectedList, HashMap<String, LinkedList<String>> infectedIDwithDate, HashMap<String, Integer> recoveredNumberWithDate, HashMap<String, Integer> deadNumberWithDate) {//GEN-FIRST:event_jButton2ActionPerformed

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        for(String date : infectedList){
            dataset.addValue(infectedIDwithDate.get(date).length(), "Infected", date.substring(0, 5).replace("-", "/"));
            dataset.addValue(deadNumberWithDate.get(date), "Dead", date.substring(0, 5).replace("-", "/"));
            dataset.addValue(recoveredNumberWithDate.get(date), "Recovered", date.substring(0, 5).replace("-", "/"));
        }

        JFreeChart lineChart = ChartFactory.createLineChart("Report on El-Corona", "Date", "Number of Cases", dataset, PlotOrientation.VERTICAL, true, true, false);
        
        CategoryPlot p = lineChart.getCategoryPlot();
        p.setRangeGridlinePaint(Color.BLACK);
        
        p.setShadowGenerator(new DefaultShadowGenerator());
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) p.getRenderer();
        renderer.setBaseShapesVisible(true);
        renderer.setDrawOutlines(true);
        renderer.setUseFillPaint(true);
        renderer.setBaseFillPaint(Color.white);
        renderer.setSeriesStroke(0, new BasicStroke(3.0f));
        renderer.setSeriesOutlineStroke(0, new BasicStroke(2.0f));
        renderer.setSeriesShape(0, new Ellipse2D.Double(-5.0, -5.0, 10.0, 10.0));
        
        renderer.setSeriesStroke(1, new BasicStroke(3.0f));
        renderer.setSeriesOutlineStroke(1, new BasicStroke(2.0f));
        renderer.setSeriesShape(1, new Ellipse2D.Double(-5.0, -5.0, 10.0, 10.0));
        
        renderer.setSeriesStroke(2, new BasicStroke(3.0f));
        renderer.setSeriesOutlineStroke(2, new BasicStroke(2.0f));
        renderer.setSeriesShape(2, new Ellipse2D.Double(-5.0, -5.0, 10.0, 10.0));
        
        ChartPanel chartpanel = new ChartPanel(lineChart);
        pnlReport.removeAll();
        pnlReport.add(chartpanel);
        pnlReport.updateUI();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void datelabelInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_datelabelInputMethodTextChanged

    }//GEN-LAST:event_datelabelInputMethodTextChanged

    public void start(String date, List infectedList, HashMap infectedIDWithDate, HashMap recoveredNumberWithDate, HashMap deadNumberWithDate, int todayInfected, int todayRecovered, int todayDead, int currentWard, int total) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Charts.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Charts.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Charts.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Charts.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Charts(date, infectedList, infectedIDWithDate, recoveredNumberWithDate, deadNumberWithDate, todayInfected, todayRecovered, todayDead, currentWard, total).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel datelabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel pnlReport;
    // End of variables declaration//GEN-END:variables
}
