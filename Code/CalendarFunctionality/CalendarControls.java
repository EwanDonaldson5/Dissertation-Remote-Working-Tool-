package CalendarFunctionality;

import Functions.colours;
import Styles.buttons;

/**
 *
 * @author ewand
 */
public class CalendarControls extends javax.swing.JPanel {

    colours coloursObject;
    buttons buttonObject;
    
    String backForward;
    String monthYear;
    
    public CalendarControls(colours coloursObject) {
        this.coloursObject = coloursObject;
        this.buttonObject = new buttons();
        
        initComponents();
        
        buttonObject.applyButtonStyles(backYearButton);
        buttonObject.applyButtonStyles(forwardYearButton);
        buttonObject.applyButtonStyles(backMonthButton);
        buttonObject.applyButtonStyles(forwardMonthButton);
    }

    public void backMonthButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                
        
    }                                               

    public void forwardMonthButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                   
        
    }  
    
    public void backYearButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                
        
    }                                               

    public void forwardYearButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                   
        
    } 
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        colourSettings = new javax.swing.JPanel();
        currentYearLabel = new javax.swing.JLabel();
        currentYearDisplayLabel = new javax.swing.JLabel();
        currentMonthDisplayLabel = new javax.swing.JLabel();
        currentMonthLabel = new javax.swing.JLabel();
        changeYearLabel2 = new javax.swing.JLabel();
        colourSettings1 = new javax.swing.JPanel();
        changeYearLabel = new javax.swing.JLabel();
        backYearButton = new javax.swing.JButton();
        forwardYearButton = new javax.swing.JButton();
        colourSettings2 = new javax.swing.JPanel();
        forwardMonthButton = new javax.swing.JButton();
        backMonthButton = new javax.swing.JButton();
        changeYearLabel1 = new javax.swing.JLabel();

        setBackground(coloursObject.getContentPanelColour());

        colourSettings.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(42, 45, 53)));
        colourSettings.setOpaque(false);

        currentYearLabel.setFont(new java.awt.Font("Microsoft Tai Le", 1, 12)); // NOI18N
        currentYearLabel.setForeground(coloursObject.getTextColour());
        currentYearLabel.setText("Current Year");

        currentYearDisplayLabel.setFont(new java.awt.Font("Microsoft Tai Le", 1, 12)); // NOI18N
        currentYearDisplayLabel.setForeground(coloursObject.getTextColour());
        currentYearDisplayLabel.setText("Current Year:");

        currentMonthDisplayLabel.setFont(new java.awt.Font("Microsoft Tai Le", 1, 12)); // NOI18N
        currentMonthDisplayLabel.setForeground(coloursObject.getTextColour());
        currentMonthDisplayLabel.setText("Current Month:");

        currentMonthLabel.setFont(new java.awt.Font("Microsoft Tai Le", 1, 12)); // NOI18N
        currentMonthLabel.setForeground(coloursObject.getTextColour());
        currentMonthLabel.setText("Current Month");

        changeYearLabel2.setFont(new java.awt.Font("Microsoft Tai Le", 1, 18)); // NOI18N
        changeYearLabel2.setForeground(coloursObject.getTextColour());
        changeYearLabel2.setText("Current Date");

        javax.swing.GroupLayout colourSettingsLayout = new javax.swing.GroupLayout(colourSettings);
        colourSettings.setLayout(colourSettingsLayout);
        colourSettingsLayout.setHorizontalGroup(
            colourSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(colourSettingsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(colourSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(colourSettingsLayout.createSequentialGroup()
                        .addGroup(colourSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(currentMonthDisplayLabel)
                            .addComponent(currentYearDisplayLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(colourSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(currentYearLabel)
                            .addComponent(currentMonthLabel)))
                    .addComponent(changeYearLabel2))
                .addContainerGap(241, Short.MAX_VALUE))
        );
        colourSettingsLayout.setVerticalGroup(
            colourSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, colourSettingsLayout.createSequentialGroup()
                .addContainerGap(7, Short.MAX_VALUE)
                .addComponent(changeYearLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(colourSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(currentMonthLabel)
                    .addComponent(currentMonthDisplayLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(colourSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(currentYearLabel)
                    .addComponent(currentYearDisplayLabel))
                .addContainerGap())
        );

        colourSettings1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(42, 45, 53)));
        colourSettings1.setOpaque(false);

        changeYearLabel.setFont(new java.awt.Font("Microsoft Tai Le", 1, 18)); // NOI18N
        changeYearLabel.setForeground(coloursObject.getTextColour());
        changeYearLabel.setText("Change Year");

        backYearButton.setText("<<<");
        backYearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backYearButtonActionPerformed(evt);
            }
        });

        forwardYearButton.setText(">>>");
        forwardYearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forwardYearButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout colourSettings1Layout = new javax.swing.GroupLayout(colourSettings1);
        colourSettings1.setLayout(colourSettings1Layout);
        colourSettings1Layout.setHorizontalGroup(
            colourSettings1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(colourSettings1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(colourSettings1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(changeYearLabel)
                    .addGroup(colourSettings1Layout.createSequentialGroup()
                        .addComponent(backYearButton)
                        .addGap(36, 36, 36)
                        .addComponent(forwardYearButton)))
                .addContainerGap(278, Short.MAX_VALUE))
        );
        colourSettings1Layout.setVerticalGroup(
            colourSettings1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(colourSettings1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(changeYearLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(colourSettings1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(backYearButton)
                    .addComponent(forwardYearButton))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        colourSettings2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(42, 45, 53)));
        colourSettings2.setOpaque(false);

        forwardMonthButton.setText(">>>");
        forwardMonthButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forwardMonthButtonActionPerformed(evt);
            }
        });

        backMonthButton.setText("<<<");
        backMonthButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backMonthButtonActionPerformed(evt);
            }
        });

        changeYearLabel1.setFont(new java.awt.Font("Microsoft Tai Le", 1, 18)); // NOI18N
        changeYearLabel1.setForeground(coloursObject.getTextColour());
        changeYearLabel1.setText("Change Month");

        javax.swing.GroupLayout colourSettings2Layout = new javax.swing.GroupLayout(colourSettings2);
        colourSettings2.setLayout(colourSettings2Layout);
        colourSettings2Layout.setHorizontalGroup(
            colourSettings2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(colourSettings2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(colourSettings2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(changeYearLabel1)
                    .addGroup(colourSettings2Layout.createSequentialGroup()
                        .addComponent(backMonthButton)
                        .addGap(37, 37, 37)
                        .addComponent(forwardMonthButton)))
                .addContainerGap(277, Short.MAX_VALUE))
        );
        colourSettings2Layout.setVerticalGroup(
            colourSettings2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(colourSettings2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(changeYearLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(colourSettings2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(backMonthButton)
                    .addComponent(forwardMonthButton))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(colourSettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(colourSettings2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(colourSettings1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(colourSettings, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(colourSettings2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(colourSettings1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

/**
    private void backYearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backYearButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_backYearButtonActionPerformed

    private void forwardYearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forwardYearButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_forwardYearButtonActionPerformed

    private void backMonthButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backMonthButtonActionPerformed
        super.updateCalendarDate("back", "month");
    }//GEN-LAST:event_backMonthButtonActionPerformed

    private void forwardMonthButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forwardMonthButtonActionPerformed
        super.updateCalendarDate("forward", "month");
    }//GEN-LAST:event_forwardMonthButtonActionPerformed
**/

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton backMonthButton;
    public javax.swing.JButton backYearButton;
    private javax.swing.JLabel changeYearLabel;
    private javax.swing.JLabel changeYearLabel1;
    private javax.swing.JLabel changeYearLabel2;
    private javax.swing.JPanel colourSettings;
    private javax.swing.JPanel colourSettings1;
    private javax.swing.JPanel colourSettings2;
    public javax.swing.JLabel currentMonthDisplayLabel;
    public javax.swing.JLabel currentMonthLabel;
    public javax.swing.JLabel currentYearDisplayLabel;
    public javax.swing.JLabel currentYearLabel;
    public javax.swing.JButton forwardMonthButton;
    public javax.swing.JButton forwardYearButton;
    // End of variables declaration//GEN-END:variables
}
