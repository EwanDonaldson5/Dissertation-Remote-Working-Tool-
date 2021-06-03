package TaskFunctionality;

import Functions.accessConfigFile;
import Functions.colours;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author ewand
 */
public class specificTask extends javax.swing.JPanel {
    String databaseName;
    String loggedInUser;
    String password;
    String url = "jdbc:mysql://localhost:3306/";
    
    //these variables hold the infromation of the task from the database
    String taskName = "";
    String taskID = "";
    String startDate = "";
    String endDate = "";
    String details = "";
    String feedback = "";
    String importance = "";
    String active = "";
    
    accessConfigFile accessConfig = new accessConfigFile();//create an object for accessing the config file
    String colourModeSettingValue = accessConfig.searchForSettingsValue("darkModeEnabled");
    colours coloursObject = new colours(colourModeSettingValue);//create an object for accessing different colours used in the program

    public specificTask(String[] taskRecord, String databaseName, String loggedInUser, String password) {
        initComponents();
        
        this.databaseName = databaseName;
        this.loggedInUser = loggedInUser;
        this.password = password;
        
        //create local copies of the information from the record parsed
        this.taskName = taskRecord[4];
        this.taskID = taskRecord[0];
        this.startDate = taskRecord[1];
        this.endDate = taskRecord[2];
        this.details = taskRecord[3];
        this.feedback = taskRecord[5];
        this.importance = taskRecord[7];
        this.active = taskRecord[6];
        
        if(active.equals("1")){//if the active value is set to 1 (true)
            activeCheckBox.setSelected(true);//the update the checkbox to be selected
        }else{activeCheckBox.setSelected(false);}//else set the checkbox to false
        
        setComponentText();//call method to set the text of the displayed components
    }
    
    //method is called to assign the relevant information to each label on the jpanel
    public void setComponentText(){
        taskNameLabel.setText(taskName);
        taskIDLabel.setText(taskID);
        startDateLabel.setText(startDate);
        endDateLabel.setText(endDate);
        detailsLabel.setText(details);
        feedbackLabel.setText(feedback);
        importanceLabel.setText(importance);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dashboardSettings = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        taskNameLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        taskIDLabel = new javax.swing.JLabel();
        startDateLabel = new javax.swing.JLabel();
        endDateLabel = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        detailsLabel = new javax.swing.JLabel();
        feedbackLabel = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        importanceLabel = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        activeCheckBox = new javax.swing.JCheckBox();

        setBackground(coloursObject.getContentPanelColour());

        dashboardSettings.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(42, 45, 53)));
        dashboardSettings.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Microsoft Tai Le", 0, 14)); // NOI18N
        jLabel1.setForeground(coloursObject.getTextColour()
        );
        jLabel1.setText("Task ID:");

        taskNameLabel.setFont(new java.awt.Font("Microsoft Tai Le", 1, 18)); // NOI18N
        taskNameLabel.setForeground(coloursObject.getTextColour()
        );
        taskNameLabel.setText("Task Name");

        jLabel3.setFont(new java.awt.Font("Microsoft Tai Le", 0, 14)); // NOI18N
        jLabel3.setForeground(coloursObject.getTextColour()
        );
        jLabel3.setText("Start Date:");

        taskIDLabel.setFont(new java.awt.Font("Microsoft Tai Le", 0, 14)); // NOI18N
        taskIDLabel.setForeground(coloursObject.getTextColour()
        );
        taskIDLabel.setText("task ID");

        startDateLabel.setFont(new java.awt.Font("Microsoft Tai Le", 0, 14)); // NOI18N
        startDateLabel.setForeground(coloursObject.getTextColour()
        );
        startDateLabel.setText("Start Date");

        endDateLabel.setFont(new java.awt.Font("Microsoft Tai Le", 0, 14)); // NOI18N
        endDateLabel.setForeground(coloursObject.getTextColour()
        );
        endDateLabel.setText("End Date");

        jLabel4.setFont(new java.awt.Font("Microsoft Tai Le", 0, 14)); // NOI18N
        jLabel4.setForeground(coloursObject.getTextColour()
        );
        jLabel4.setText("End Date:");

        jLabel5.setFont(new java.awt.Font("Microsoft Tai Le", 0, 14)); // NOI18N
        jLabel5.setForeground(coloursObject.getTextColour()
        );
        jLabel5.setText("Details:");

        detailsLabel.setFont(new java.awt.Font("Microsoft Tai Le", 0, 14)); // NOI18N
        detailsLabel.setForeground(coloursObject.getTextColour()
        );
        detailsLabel.setText("Details");

        feedbackLabel.setFont(new java.awt.Font("Microsoft Tai Le", 0, 14)); // NOI18N
        feedbackLabel.setForeground(coloursObject.getTextColour()
        );
        feedbackLabel.setText("Feedback");

        jLabel6.setFont(new java.awt.Font("Microsoft Tai Le", 0, 14)); // NOI18N
        jLabel6.setForeground(coloursObject.getTextColour()
        );
        jLabel6.setText("Feedback:");

        importanceLabel.setFont(new java.awt.Font("Microsoft Tai Le", 0, 14)); // NOI18N
        importanceLabel.setForeground(coloursObject.getTextColour()
        );
        importanceLabel.setText("Importance");

        jLabel7.setFont(new java.awt.Font("Microsoft Tai Le", 0, 14)); // NOI18N
        jLabel7.setForeground(coloursObject.getTextColour()
        );
        jLabel7.setText("Importance:");

        jLabel8.setFont(new java.awt.Font("Microsoft Tai Le", 0, 14)); // NOI18N
        jLabel8.setForeground(coloursObject.getTextColour()
        );
        jLabel8.setText("Active:");

        activeCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                activeCheckBoxActionPerformed(evt);
            }
        });

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
                        .addGroup(dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(dashboardSettingsLayout.createSequentialGroup()
                                .addGroup(dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(taskIDLabel)
                                    .addComponent(startDateLabel)))
                            .addGroup(dashboardSettingsLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(dashboardSettingsLayout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(feedbackLabel))
                                    .addGroup(dashboardSettingsLayout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(endDateLabel))))))
                    .addGroup(dashboardSettingsLayout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(detailsLabel))
                    .addGroup(dashboardSettingsLayout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(importanceLabel))
                    .addGroup(dashboardSettingsLayout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(activeCheckBox)))
                .addContainerGap(487, Short.MAX_VALUE))
        );
        dashboardSettingsLayout.setVerticalGroup(
            dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardSettingsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(taskNameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(taskIDLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(startDateLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(endDateLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(detailsLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(feedbackLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(importanceLabel)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(activeCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel8))
                .addContainerGap(14, Short.MAX_VALUE))
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
                .addGap(0, 157, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void activeCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_activeCheckBoxActionPerformed
        //flip the active variable
        if(active.equals("1")){//if active checkbox is selected
            active = "0";//then set it to zero
        }else{active = "1";}//if active is 0 then set it to 1
        
        try{
            System.out.println("specificTask: updating active val of task:" + taskName);
            Connection connection = DriverManager.getConnection(url + databaseName, loggedInUser, password);//create connection to database
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);//create statement for use in querying the db

            //create the start of an insert query by only adding some details. The rest are null by default
            String query = "update task set Active = '"+ active +"' where taskID = '" + taskID + "'";//string is used to hold the query that is to be executed

            statement.executeUpdate(query);//execute the query

            connection.close();
            statement.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }//GEN-LAST:event_activeCheckBoxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JCheckBox activeCheckBox;
    private javax.swing.JPanel dashboardSettings;
    private javax.swing.JLabel detailsLabel;
    private javax.swing.JLabel endDateLabel;
    private javax.swing.JLabel feedbackLabel;
    private javax.swing.JLabel importanceLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel startDateLabel;
    private javax.swing.JLabel taskIDLabel;
    private javax.swing.JLabel taskNameLabel;
    // End of variables declaration//GEN-END:variables
}
