package ProductivityFunctionality;

import Features.productivity;
import Functions.accessConfigFile;
import Functions.colours;
import Styles.fonts;
import Styles.labels;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author ewand
 */
public class productivityControls extends JPanel {

    accessConfigFile accessConfig = new accessConfigFile();//create an object for accessing the config file
    String colourModeSettingValue = accessConfig.searchForSettingsValue("darkModeEnabled");
    colours coloursObject = new colours(colourModeSettingValue);//create an object for accessing different colours used in the program
    labels labelsObject = new labels();
    fonts fontsObject = new fonts();
    
    String currentSelection = "";//variable holds the currently chosen chart selection
    
    
    public productivityControls() {
        initComponents();
        setupComponents();
    }

    public void setupComponents(){
        Font defaultFont = fontsObject.getDefaultFont();
        
        //add graph radio buttons to a button group
        //a button group allows the radio buttons to only have one selected
        graphButtonGroup.add(timeWorkingRadioButton);
        graphButtonGroup.add(messagesRadioButton);
        
        //add time period radio buttons to a group
        timePeriodButtonGroup.add(dailyRadioButton);
        timePeriodButtonGroup.add(weeklyRadioButton);
        timePeriodButtonGroup.add(monthlyRadioButton);
        
//        chosenGraphLabel.setForeground(coloursObject.getTextColour());
//        chosenGraphLabel.setFont(defaultFont);
//        
//        currentSelectionLabel.setForeground(coloursObject.getTextColour());
//        currentSelectionLabel.setFont(defaultFont);
//        
//        timeWorkingRadioButton.setForeground(coloursObject.getTextColour());
//        timeWorkingRadioButton.setFont(defaultFont);
//        
//        messagesRadioButton.setForeground(coloursObject.getTextColour());
//        messagesRadioButton.setFont(defaultFont);
    }
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        graphButtonGroup = new javax.swing.ButtonGroup();
        timePeriodButtonGroup = new javax.swing.ButtonGroup();
        graphControlsPanel = new javax.swing.JPanel();
        messagesRadioButton = new javax.swing.JRadioButton();
        timeWorkingRadioButton = new javax.swing.JRadioButton();
        CSLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        currentGraphSelectionLabel = new javax.swing.JLabel();
        chosenGraphLabel = new javax.swing.JLabel();
        timePeriodControlsPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        CSLabel2 = new javax.swing.JLabel();
        currentTimePeriodLabel = new javax.swing.JLabel();
        dailyRadioButton = new javax.swing.JRadioButton();
        weeklyRadioButton = new javax.swing.JRadioButton();
        monthlyRadioButton = new javax.swing.JRadioButton();
        colourSettings4 = new javax.swing.JPanel();

        setBackground(coloursObject.getContentPanelColour());

        graphControlsPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(42, 45, 53)));
        graphControlsPanel.setOpaque(false);

        messagesRadioButton.setFont(new java.awt.Font("Microsoft Tai Le", 1, 12)); // NOI18N
        messagesRadioButton.setForeground(coloursObject.getTextColour());
        messagesRadioButton.setText("Messages");
        messagesRadioButton.setOpaque(false);
        messagesRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                messagesRadioButtonActionPerformed(evt);
            }
        });

        timeWorkingRadioButton.setFont(new java.awt.Font("Microsoft Tai Le", 1, 12)); // NOI18N
        timeWorkingRadioButton.setForeground(coloursObject.getTextColour());
        timeWorkingRadioButton.setText("Time Working");
        timeWorkingRadioButton.setOpaque(false);
        timeWorkingRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timeWorkingRadioButtonActionPerformed(evt);
            }
        });

        CSLabel.setFont(new java.awt.Font("Microsoft Tai Le", 1, 12)); // NOI18N
        CSLabel.setForeground(coloursObject.getTextColour());
        CSLabel.setText("Current Selection:");

        jLabel2.setFont(new java.awt.Font("Microsoft Tai Le", 1, 18)); // NOI18N
        jLabel2.setForeground(coloursObject.getTextColour()
        );
        jLabel2.setText("Graph");

        currentGraphSelectionLabel.setFont(new java.awt.Font("Microsoft Tai Le", 1, 12)); // NOI18N
        currentGraphSelectionLabel.setForeground(coloursObject.getTextColour());

        chosenGraphLabel.setFont(new java.awt.Font("Microsoft Tai Le", 1, 12)); // NOI18N
        chosenGraphLabel.setForeground(coloursObject.getTextColour());

        javax.swing.GroupLayout graphControlsPanelLayout = new javax.swing.GroupLayout(graphControlsPanel);
        graphControlsPanel.setLayout(graphControlsPanelLayout);
        graphControlsPanelLayout.setHorizontalGroup(
            graphControlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(graphControlsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(graphControlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(graphControlsPanelLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(graphControlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(timeWorkingRadioButton)
                            .addComponent(messagesRadioButton)))
                    .addGroup(graphControlsPanelLayout.createSequentialGroup()
                        .addComponent(CSLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(chosenGraphLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(currentGraphSelectionLabel))
                    .addComponent(jLabel2))
                .addContainerGap(79, Short.MAX_VALUE))
        );
        graphControlsPanelLayout.setVerticalGroup(
            graphControlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(graphControlsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(graphControlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(graphControlsPanelLayout.createSequentialGroup()
                        .addGroup(graphControlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CSLabel)
                            .addComponent(currentGraphSelectionLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                        .addComponent(timeWorkingRadioButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(messagesRadioButton)
                        .addContainerGap(84, Short.MAX_VALUE))
                    .addGroup(graphControlsPanelLayout.createSequentialGroup()
                        .addComponent(chosenGraphLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        timePeriodControlsPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(42, 45, 53)));
        timePeriodControlsPanel.setOpaque(false);

        jLabel3.setFont(new java.awt.Font("Microsoft Tai Le", 1, 18)); // NOI18N
        jLabel3.setForeground(coloursObject.getTextColour()
        );
        jLabel3.setText("Time Period");

        CSLabel2.setFont(new java.awt.Font("Microsoft Tai Le", 1, 12)); // NOI18N
        CSLabel2.setForeground(coloursObject.getTextColour());
        CSLabel2.setText("Current Selection:");

        currentTimePeriodLabel.setFont(new java.awt.Font("Microsoft Tai Le", 1, 12)); // NOI18N
        currentTimePeriodLabel.setForeground(coloursObject.getTextColour());

        dailyRadioButton.setFont(new java.awt.Font("Microsoft Tai Le", 1, 12)); // NOI18N
        dailyRadioButton.setForeground(coloursObject.getTextColour());
        dailyRadioButton.setText("Daily");
        dailyRadioButton.setOpaque(false);
        dailyRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dailyRadioButtonActionPerformed(evt);
            }
        });

        weeklyRadioButton.setFont(new java.awt.Font("Microsoft Tai Le", 1, 12)); // NOI18N
        weeklyRadioButton.setForeground(coloursObject.getTextColour());
        weeklyRadioButton.setText("Weekly");
        weeklyRadioButton.setOpaque(false);

        monthlyRadioButton.setFont(new java.awt.Font("Microsoft Tai Le", 1, 12)); // NOI18N
        monthlyRadioButton.setForeground(coloursObject.getTextColour());
        monthlyRadioButton.setText("Monthly");
        monthlyRadioButton.setOpaque(false);

        javax.swing.GroupLayout timePeriodControlsPanelLayout = new javax.swing.GroupLayout(timePeriodControlsPanel);
        timePeriodControlsPanel.setLayout(timePeriodControlsPanelLayout);
        timePeriodControlsPanelLayout.setHorizontalGroup(
            timePeriodControlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(timePeriodControlsPanelLayout.createSequentialGroup()
                .addGroup(timePeriodControlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(timePeriodControlsPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(timePeriodControlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(timePeriodControlsPanelLayout.createSequentialGroup()
                                .addComponent(CSLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(currentTimePeriodLabel))))
                    .addGroup(timePeriodControlsPanelLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(timePeriodControlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(weeklyRadioButton)
                            .addComponent(dailyRadioButton)
                            .addComponent(monthlyRadioButton))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        timePeriodControlsPanelLayout.setVerticalGroup(
            timePeriodControlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(timePeriodControlsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(timePeriodControlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CSLabel2)
                    .addComponent(currentTimePeriodLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dailyRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(weeklyRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(monthlyRadioButton)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        colourSettings4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(42, 45, 53)));
        colourSettings4.setOpaque(false);

        javax.swing.GroupLayout colourSettings4Layout = new javax.swing.GroupLayout(colourSettings4);
        colourSettings4.setLayout(colourSettings4Layout);
        colourSettings4Layout.setHorizontalGroup(
            colourSettings4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        colourSettings4Layout.setVerticalGroup(
            colourSettings4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(graphControlsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(timePeriodControlsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(colourSettings4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(graphControlsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(timePeriodControlsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(colourSettings4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(222, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void timeWorkingRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timeWorkingRadioButtonActionPerformed
        //currentSelection = "TimeWorking";
        //chosenGraphLabel.setText("Time Working");c
        //super.setCurrentSelection(currentSelection);
    }//GEN-LAST:event_timeWorkingRadioButtonActionPerformed

    private void messagesRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_messagesRadioButtonActionPerformed
        //currentSelection = "Messages";
        //chosenGraphLabel.setText("Messages");
        //super.setCurrentSelection(currentSelection);
    }//GEN-LAST:event_messagesRadioButtonActionPerformed

    private void dailyRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dailyRadioButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dailyRadioButtonActionPerformed

    public String getCurrentSelection(){
        return currentSelection;
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel CSLabel;
    private javax.swing.JLabel CSLabel2;
    public javax.swing.JLabel chosenGraphLabel;
    private javax.swing.JPanel colourSettings4;
    private javax.swing.JLabel currentGraphSelectionLabel;
    public javax.swing.JLabel currentTimePeriodLabel;
    public javax.swing.JRadioButton dailyRadioButton;
    private javax.swing.ButtonGroup graphButtonGroup;
    private javax.swing.JPanel graphControlsPanel;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    public javax.swing.JRadioButton messagesRadioButton;
    public javax.swing.JRadioButton monthlyRadioButton;
    private javax.swing.ButtonGroup timePeriodButtonGroup;
    private javax.swing.JPanel timePeriodControlsPanel;
    public javax.swing.JRadioButton timeWorkingRadioButton;
    public javax.swing.JRadioButton weeklyRadioButton;
    // End of variables declaration//GEN-END:variables
    }
//}