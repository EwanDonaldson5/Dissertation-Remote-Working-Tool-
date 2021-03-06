package Features;


import Functions.colours;
import Functions.accessConfigFile;
import Styles.buttons;
import java.util.ArrayList;
import javax.swing.JButton;
/**
 *
 * @author ewand
 */
public class settings extends javax.swing.JPanel {
       
    ArrayList<String> settingsList = new ArrayList<String>();//arraylist holds the settings that have been set by the user
    
    accessConfigFile accessConfig = new accessConfigFile();//create an object for accessing the config file
    String colourModeSettingValue = accessConfig.searchForSettingsValue("darkModeEnabled");
    colours coloursObject = new colours(colourModeSettingValue);//create an object for accessing different colours used in the program
    
    buttons buttonStyles = new buttons();//create a copy of the button styles object
    
    public settings() {
        initComponents();
        applyComponentStyles();
        
        settingsList = accessConfig.readFile();//obtain the settings from the config file
        
        String dashboardEnabledValue = accessConfig.searchForSettingsValue("dashboardEnabled");//get the value for the dashboardEmabled setting
        dashboardEnabledValue = dashboardEnabledValue.substring(0, 1).toUpperCase() + dashboardEnabledValue.substring(1);//convert the string to start with uppercase
        dashboardEnabledButton.setText(dashboardEnabledValue);//set the JButton for displaying the users option to the choice that currently exists
        
        String showDashInfoValue = accessConfig.searchForSettingsValue("showDashInfo");
        showDashInfoValue = showDashInfoValue.substring(0, 1).toUpperCase() + showDashInfoValue.substring(1);//convert the string to start with uppercase
        showDashInfoButton.setText(showDashInfoValue);//set the JButton for displaying the users option to the choice that currently exists
        
    }
    
    //apply styling and instructions to any components
    public void applyComponentStyles(){
        //apply the default button style to the buttons
        buttonStyles.applyButtonStyles(dashboardEnabledButton);
        buttonStyles.applyButtonStyles(showDashInfoButton);
        buttonStyles.applyButtonStyles(darkModeButton);
        buttonStyles.applyButtonStyles(socialsButton);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dashboardSettings = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        dashboardEnabledButton = new javax.swing.JButton();
        showDashInfoButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        colourSettings = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        darkModeButton = new javax.swing.JButton();
        colourSettings1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        socialsButton = new javax.swing.JButton();

        setBackground(coloursObject.getContentPanelColour()
        );

        dashboardSettings.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(42, 45, 53)));
        dashboardSettings.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Microsoft Tai Le", 0, 14)); // NOI18N
        jLabel1.setForeground(coloursObject.getTextColour()
        );
        jLabel1.setText("Show Dashboard:");

        jLabel2.setFont(new java.awt.Font("Microsoft Tai Le", 1, 18)); // NOI18N
        jLabel2.setForeground(coloursObject.getTextColour()
        );
        jLabel2.setText("Dashboard");

        dashboardEnabledButton.setText("True");
        dashboardEnabledButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dashboardEnabledButtonActionPerformed(evt);
            }
        });

        showDashInfoButton.setText("True");
        showDashInfoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showDashInfoButtonActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Microsoft Tai Le", 0, 14)); // NOI18N
        jLabel3.setForeground(coloursObject.getTextColour()
        );
        jLabel3.setText("Show Dashboard Info:");

        javax.swing.GroupLayout dashboardSettingsLayout = new javax.swing.GroupLayout(dashboardSettings);
        dashboardSettings.setLayout(dashboardSettingsLayout);
        dashboardSettingsLayout.setHorizontalGroup(
            dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardSettingsLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dashboardSettingsLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(showDashInfoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dashboardEnabledButton, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel2))
                .addContainerGap(375, Short.MAX_VALUE))
        );
        dashboardSettingsLayout.setVerticalGroup(
            dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dashboardSettingsLayout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(dashboardEnabledButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, Short.MAX_VALUE))
                .addGap(12, 12, 12)
                .addGroup(dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(showDashInfoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, Short.MAX_VALUE))
                .addContainerGap())
        );

        colourSettings.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(42, 45, 53)));
        colourSettings.setOpaque(false);

        jLabel4.setFont(new java.awt.Font("Microsoft Tai Le", 0, 14)); // NOI18N
        jLabel4.setForeground(coloursObject.getTextColour()
        );
        jLabel4.setText("Dark Mode:");

        jLabel5.setFont(new java.awt.Font("Microsoft Tai Le", 1, 18)); // NOI18N
        jLabel5.setForeground(coloursObject.getTextColour()
        );
        jLabel5.setText("Colours");

        darkModeButton.setText("True");
        darkModeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                darkModeButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout colourSettingsLayout = new javax.swing.GroupLayout(colourSettings);
        colourSettings.setLayout(colourSettingsLayout);
        colourSettingsLayout.setHorizontalGroup(
            colourSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(colourSettingsLayout.createSequentialGroup()
                .addGroup(colourSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(colourSettingsLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5))
                    .addGroup(colourSettingsLayout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(darkModeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        colourSettingsLayout.setVerticalGroup(
            colourSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, colourSettingsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(colourSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(darkModeButton))
                .addGap(43, 43, 43))
        );

        colourSettings1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(42, 45, 53)));
        colourSettings1.setOpaque(false);

        jLabel6.setFont(new java.awt.Font("Microsoft Tai Le", 0, 14)); // NOI18N
        jLabel6.setForeground(coloursObject.getTextColour()
        );
        jLabel6.setText("Show Socials:");

        jLabel7.setFont(new java.awt.Font("Microsoft Tai Le", 1, 18)); // NOI18N
        jLabel7.setForeground(coloursObject.getTextColour()
        );
        jLabel7.setText("Profiles");

        socialsButton.setText("True");
        socialsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                socialsButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout colourSettings1Layout = new javax.swing.GroupLayout(colourSettings1);
        colourSettings1.setLayout(colourSettings1Layout);
        colourSettings1Layout.setHorizontalGroup(
            colourSettings1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(colourSettings1Layout.createSequentialGroup()
                .addGroup(colourSettings1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(colourSettings1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7))
                    .addGroup(colourSettings1Layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(socialsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        colourSettings1Layout.setVerticalGroup(
            colourSettings1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, colourSettings1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(colourSettings1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(socialsButton))
                .addGap(43, 43, 43))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dashboardSettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(colourSettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(colourSettings1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(dashboardSettings, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(colourSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(colourSettings1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(135, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void dashboardEnabledButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dashboardEnabledButtonActionPerformed
        String dashEnabledChoice = dashboardEnabledButton.getText();//get the option currently set on the true or false jbutton
        String edited = dashEnabledChoice.substring(0, 1).toUpperCase() + dashEnabledChoice.substring(1);//convert above string to a version with an uppercase first letter
        
        if(edited.equals("True")){//if the string is equals to true
            dashboardEnabledButton.setText("False");//set the button text to false
        }else if(edited.equals("False")){//if the string is equals to false
            dashboardEnabledButton.setText("True");//set the button text to true
        }
        
        String value = accessConfig.searchForSettingsValue("dashboardEnabled");//get the current value for the dashboard enabled setting
        value = accessConfig.flipBooleanSetting(value);//flip the value held in the string
        accessConfig.updateSettingsList("dashboardEnabled", value);//update the settings list with the new inverted value
        accessConfig.saveFile();//save the file
    }//GEN-LAST:event_dashboardEnabledButtonActionPerformed

    private void showDashInfoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showDashInfoButtonActionPerformed
        String dashInfoChoice = showDashInfoButton.getText();//get the option currently set on the true or false jbutton
        String edited = dashInfoChoice.substring(0, 1).toUpperCase() + dashInfoChoice.substring(1);//convert above string to a version with an uppercase first letter
        
        if(edited.equals("True")){//if the string is equals to true
            showDashInfoButton.setText("False");//set the button text to false
        }else if(edited.equals("False")){//if the string is equals to false
            showDashInfoButton.setText("True");//set the button text to true
        }
        
        String value = accessConfig.searchForSettingsValue("showDashInfo");//get the current value for the show dashboard info setting
        value = accessConfig.flipBooleanSetting(value);//flip the value held in the string
        accessConfig.updateSettingsList("showDashInfo", value);//update the settings list with the new inverted value
        accessConfig.saveFile();//save the file
    }//GEN-LAST:event_showDashInfoButtonActionPerformed

    private void darkModeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_darkModeButtonActionPerformed
        String darkModeChoice = darkModeButton.getText();//get the option currently set on the true or false jbutton
        String edited = darkModeChoice.substring(0, 1).toUpperCase() + darkModeChoice.substring(1);//convert above string to a version with an uppercase first letter
        
        if(edited.equals("True")){//if the string is equals to true
            darkModeButton.setText("False");//set the button text to false
        }else if(edited.equals("False")){//if the string is equals to false
            darkModeButton.setText("True");//set the button text to true
        }
        
        String value = accessConfig.searchForSettingsValue("darkModeEnabled");//get the current value for the show dashboard info setting
        value = accessConfig.flipBooleanSetting(value);//flip the value held in the string
        accessConfig.updateSettingsList("darkModeEnabled", value);//update the settings list with the new inverted value
        accessConfig.saveFile();//save the file
    }//GEN-LAST:event_darkModeButtonActionPerformed

    private void socialsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_socialsButtonActionPerformed
        String socialsChoice = socialsButton.getText();//get the current option from the buttons text
        String edited = socialsChoice.substring(0, 1).toUpperCase() + socialsChoice.substring(1);//convert above string to a version with an uppercase first letter
        
        if(edited.equals("True")){//if the string is equals to true
            socialsButton.setText("False");//set the button text to false
        }else if(edited.equals("False")){//if the string is equals to false
            socialsButton.setText("True");//set the button text to true
        }
        
        String value = accessConfig.searchForSettingsValue("showSocials");//get the current value for the show dashboard info setting
        value = accessConfig.flipBooleanSetting(value);//flip the value held in the string
        accessConfig.updateSettingsList("showSocials", value);//update the settings list with the new inverted value
        accessConfig.saveFile();//save the file
    }//GEN-LAST:event_socialsButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel colourSettings;
    private javax.swing.JPanel colourSettings1;
    private javax.swing.JButton darkModeButton;
    private javax.swing.JButton dashboardEnabledButton;
    private javax.swing.JPanel dashboardSettings;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JButton showDashInfoButton;
    private javax.swing.JButton socialsButton;
    // End of variables declaration//GEN-END:variables
}
