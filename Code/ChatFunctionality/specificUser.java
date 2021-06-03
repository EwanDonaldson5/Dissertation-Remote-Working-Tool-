package ChatFunctionality;

import Functions.accessConfigFile;
import Functions.colours;

/**
 *
 * @author ewand
 */
public class specificUser extends javax.swing.JPanel {
    //these variables hold the infromation of the task from the database
    String userName = "";
    String socials = "";
    
    accessConfigFile accessConfig = new accessConfigFile();//create an object for accessing the config file
    String colourModeSettingValue = accessConfig.searchForSettingsValue("darkModeEnabled");
    colours coloursObject = new colours(colourModeSettingValue);//create an object for accessing different colours used in the program

    public specificUser(String[] userRecord) {
        initComponents();
        
        //create local copies of the information from the record parsed
        this.userName = userRecord[1];
        this.socials = userRecord[3];
        
        setComponentText();//call method to set the text of the displayed components
    }
    
    //method is called to assign the relevant information to each label on the jpanel
    public void setComponentText(){
        nameLabel.setText(userName);
        socialsLabel.setText(socials);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dashboardSettings = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        taskNameLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        socialsLabel = new javax.swing.JLabel();

        setBackground(coloursObject.getContentPanelColour());

        dashboardSettings.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(42, 45, 53)));
        dashboardSettings.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Microsoft Tai Le", 0, 14)); // NOI18N
        jLabel1.setForeground(coloursObject.getTextColour()
        );
        jLabel1.setText("Name:");

        taskNameLabel.setFont(new java.awt.Font("Microsoft Tai Le", 1, 18)); // NOI18N
        taskNameLabel.setForeground(coloursObject.getTextColour()
        );
        taskNameLabel.setText("Task Name");

        jLabel3.setFont(new java.awt.Font("Microsoft Tai Le", 0, 14)); // NOI18N
        jLabel3.setForeground(coloursObject.getTextColour()
        );
        jLabel3.setText("Socials:");

        nameLabel.setFont(new java.awt.Font("Microsoft Tai Le", 0, 14)); // NOI18N
        nameLabel.setForeground(coloursObject.getTextColour()
        );
        nameLabel.setText("Name");

        socialsLabel.setFont(new java.awt.Font("Microsoft Tai Le", 0, 14)); // NOI18N
        socialsLabel.setForeground(coloursObject.getTextColour()
        );
        socialsLabel.setText("Socials");

        javax.swing.GroupLayout dashboardSettingsLayout = new javax.swing.GroupLayout(dashboardSettings);
        dashboardSettings.setLayout(dashboardSettingsLayout);
        dashboardSettingsLayout.setHorizontalGroup(
            dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardSettingsLayout.createSequentialGroup()
                .addGroup(dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dashboardSettingsLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(taskNameLabel))
                    .addGroup(dashboardSettingsLayout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addGroup(dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nameLabel)
                            .addComponent(socialsLabel))))
                .addContainerGap(539, Short.MAX_VALUE))
        );
        dashboardSettingsLayout.setVerticalGroup(
            dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dashboardSettingsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(taskNameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(nameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(socialsLabel))
                .addContainerGap(154, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dashboardSettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(dashboardSettings, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(167, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel dashboardSettings;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JLabel socialsLabel;
    private javax.swing.JLabel taskNameLabel;
    // End of variables declaration//GEN-END:variables
}
