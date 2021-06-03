package NotificationFunctionality;


import Functions.accessConfigFile;
import Functions.colours;
import Functions.generateUniqueID;
import Styles.buttons;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JPanel;
import java.sql.Timestamp;


public class addNewNotification extends JPanel{
    ArrayList<String[][]> tables = new ArrayList<String[][]>();
    String databaseName;
    String loggedInUser;
    String password;
    String url = "jdbc:mysql://localhost:3306/";
    
    ArrayList<String[][]> oldTables = new ArrayList<String[][]>();
    String usersCompanyID;
    
    buttons buttonStyles = new buttons();
    generateUniqueID generate = new generateUniqueID();
    accessConfigFile accessConfig = new accessConfigFile();//create an object for accessing the config file
    String colourModeSettingValue = accessConfig.searchForSettingsValue("darkModeEnabled");
    colours coloursObject = new colours(colourModeSettingValue);//create an object for accessing different colours used in the program
    
    public addNewNotification(ArrayList<String[][]> tables, String databaseName, String loggedInUser, String password) {
        initComponents();
        
        this.tables = tables;
        this.databaseName = databaseName;
        this.loggedInUser = loggedInUser;
        this.password = password;
        setVisible(true);
        
        fillComboBox();//call method to assign items to combo boxes
        applyStyles();//method applys styles to components
    }
    
    //method adds a new task to the database with provided parameters
    public void addNotification(){
        final String url = "jdbc:mysql://localhost:3306/";//url of the database
        String[][] userTable = tables.get(2);//create a local user table reference
        String loggedInUserID = "";//var holds the id of the currently logged in user
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String recipientName = recipientComboBox.getSelectedItem().toString();
        String notificationRecipientID = "";
        String notificationName = nameTextField.getText();//get the text typed into the update name text field
        String notificationContent = contentTextField.getText();//get the text typed into the update content field
        
        //loop in user table to attempt to find the id of the user using the program
        //and to find the id of the chosen recipient
        for(int i = 0; i < userTable.length; i++){//loop through the user table records
            if(loggedInUser.equals(userTable[i][1])){//if the logged in username equals one from the user table
                loggedInUserID = userTable[i][0];//update the var holding the ID of the logged in user
            }
            if(recipientName.equals(userTable[i][1])){//if the chosen recipients name is found in the table
                notificationRecipientID = userTable[i][0];//update the var holding the recipients id
            }
        }
        
        //the following if statements are error checking by ensuring no null pointers will occur
        if(!notificationName.equals("")){//if the notification name text field was not left blank
            if(!notificationContent.equals("")){//if the notification content text field was not left blank
                if(!loggedInUserID.equals("")){//if the logged user ID was successfully found
                        try{
                            Connection connection = DriverManager.getConnection(url + databaseName, loggedInUser, password);//get a connection to the database
                            String query = "insert into notifications (NotificationID, SenderID, RecipientID, Content, TimeSent, Active, Name)" + " values (?, ?, ?, ?, ?, ?, ?)";//the mysql insert statement

                            String notificationID = generate.generate(tables, "notificationID");//creates a random ID for the taskID
                            String senderID = loggedInUserID;
                            String recipientID = notificationRecipientID;
                            String content = contentTextField.getText();
                            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                            int active = 1;//represents boolean value (shows whether the task is inactive or not)

                            //if the notification is meant for everyone then set to empty
                            //error checking
                            if(recipientID.equals("everyone")){
                                recipientID = "";
                            }

                            //create the mysql insert preparedstatement
                            PreparedStatement preparedStmt = connection.prepareStatement(query);
                            preparedStmt.setString   (1, notificationID);//the unique ID of the record
                            preparedStmt.setString   (2, senderID);//the id o the user who sent the notification
                            preparedStmt.setString   (3, recipientID);//the recipient of the notification
                            preparedStmt.setString   (4, content);//the details of the notification
                            preparedStmt.setTimestamp(5, timestamp);//the tiem the notification was sent out
                            preparedStmt.setInt      (6, active);//the active value (1 for active, 0 for inactive)
                            preparedStmt.setString   (7, notificationName);//the name of the notification

                            preparedStmt.execute();//execute the preparedstatement

                            connection.close();//close the connection

                            //this code below replaces the task table with the new task table which contains the additional record
                            String[][] notificationTable = tables.get(3);//create a local taskTable from the arraylist
                            String[] newRecord = {notificationID, senderID, recipientID, content, sdf.format(timestamp), Integer.toString(active)};//create an array to hold the new task record being added
                            String[][] newTable = Arrays.copyOf(notificationTable,notificationTable.length+1);//copies the notification table into a new array with space for a new record (arrays are fixed size)
                            newTable[newTable.length-1] = newRecord;//add the new record to the new table
                            tables.set(0,newTable);//replace the old chat tablewith the updated version
                            
                        }catch(Exception exc){//catch any exceptions
                            exc.printStackTrace();//print the exception
                        }
                }else{System.out.println("notifications: unable to find logged in users id while creating note");}
            }else{System.out.println("notifications: error, user left notification name blank");}
        }else{System.out.println("notifications: error, user left notification content blank");}
    }
    
    public void updateNotification(){
        String[][] notificationTable = tables.get(3);//create local notifications table
        String notificationID = "";
        String notificationChoice = selectNotificationComboBox.getSelectedItem().toString();//get the selected name from the combo box
        String notificationName = updateNameTextField.getText();//get the text typed into the update name text field
        String notificationContent = updateContentTextField.getText();//get the text typed into the update content field
        String oldName = "";
        String oldContent = "";
        String active = "";
        
        //for loop will discover the id that exists to the corresponding user chosen name
        //also discovers the original name and content for the chosen notification
        for(int i = 0; i < notificationTable.length; i++){//loop through the notifications table
            if(notificationChoice.equals(notificationTable[i][6])){
                notificationID = notificationTable[i][0];//retrieve the id
                oldName = notificationTable[i][6];//retrieve the name
                oldContent = notificationTable[i][3];//retrieve the content
                active = notificationTable[i][5];
            }
        }
        if(!notificationID.equals("")){//if the notification ID was successfully found
            if(notificationName.equals("")){//if the name text field was untouched
                notificationName = oldName;//set the chosen name to the old name (update with the name the notification already had)
            }
            if(notificationContent.equals("")){//if the content text field was untouched (update with the content the notification already had)
                notificationContent = oldContent;
            }
            if(activeCheckBox.isSelected()){//if the checkbox is selected then assign the active value a 1
                active = "1";
            }else{//else: if the check box is not checked then assign active the value of 0
                active = "0";
            }
            
            try{
                System.out.println("notifications: " + notificationID);
                Connection connection = DriverManager.getConnection(url + databaseName, loggedInUser, password);//create connection to database
                Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);//create statement for use in querying the db

                //create the start of an insert query by only adding some details. The rest are null by default
                String query = "update notifications set Name = '"+ notificationName +"', Content = '" + notificationContent + "', Active = '" + active + "' where NotificationID = '" + notificationID + "'";//string is used to hold the query that is to be executed

                statement.executeUpdate(query);//execute the query

                connection.close();
                statement.close();
            }catch(Exception e){
                System.out.println(e);
            }
        }else{//if the notification ID was not successfuly found
            System.out.println("notifications: ID not found during update");
        }
    }

    public void applyStyles(){
        buttonStyles.applyButtonStyles(submitButton);
        buttonStyles.applyButtonStyles(updateButton);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dashboardSettings = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        contentTextField = new javax.swing.JTextField();
        recipientComboBox = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        selectNotificationComboBox = new javax.swing.JComboBox<>();
        updateContentTextField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        submitButton = new javax.swing.JButton();
        updateButton = new javax.swing.JButton();
        nameTextField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        updateNameTextField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        activeCheckBox = new javax.swing.JCheckBox();
        jLabel10 = new javax.swing.JLabel();

        setBackground(coloursObject.getContentPanelColour());

        dashboardSettings.setBackground(coloursObject.getContentPanelColour());
        dashboardSettings.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(42, 45, 53)));
        dashboardSettings.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Microsoft Tai Le", 0, 14)); // NOI18N
        jLabel1.setForeground(coloursObject.getTextColour()
        );
        jLabel1.setText("Recipient:");

        jLabel2.setFont(new java.awt.Font("Microsoft Tai Le", 1, 18)); // NOI18N
        jLabel2.setForeground(coloursObject.getTextColour()
        );
        jLabel2.setText("Update Notification");

        jLabel5.setFont(new java.awt.Font("Microsoft Tai Le", 0, 14)); // NOI18N
        jLabel5.setForeground(coloursObject.getTextColour()
        );
        jLabel5.setText("Content:");

        recipientComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Everyone" }));
        recipientComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recipientComboBoxActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Microsoft Tai Le", 0, 14)); // NOI18N
        jLabel6.setForeground(coloursObject.getTextColour()
        );
        jLabel6.setText("Select Notification:");

        jLabel3.setFont(new java.awt.Font("Microsoft Tai Le", 1, 18)); // NOI18N
        jLabel3.setForeground(coloursObject.getTextColour()
        );
        jLabel3.setText("Add New Notification");

        selectNotificationComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Option:" }));
        selectNotificationComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectNotificationComboBoxActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Microsoft Tai Le", 0, 14)); // NOI18N
        jLabel7.setForeground(coloursObject.getTextColour()
        );
        jLabel7.setText("Content:");

        submitButton.setText("Submit Notification");
        submitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitButtonActionPerformed(evt);
            }
        });

        updateButton.setText("Update Notification");
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Microsoft Tai Le", 0, 14)); // NOI18N
        jLabel8.setForeground(coloursObject.getTextColour()
        );
        jLabel8.setText("Name:");

        jLabel9.setFont(new java.awt.Font("Microsoft Tai Le", 0, 14)); // NOI18N
        jLabel9.setForeground(coloursObject.getTextColour()
        );
        jLabel9.setText("Name:");

        activeCheckBox.setSelected(true);

        jLabel10.setFont(new java.awt.Font("Microsoft Tai Le", 0, 14)); // NOI18N
        jLabel10.setForeground(coloursObject.getTextColour()
        );
        jLabel10.setText("Active:");

        javax.swing.GroupLayout dashboardSettingsLayout = new javax.swing.GroupLayout(dashboardSettings);
        dashboardSettings.setLayout(dashboardSettingsLayout);
        dashboardSettingsLayout.setHorizontalGroup(
            dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardSettingsLayout.createSequentialGroup()
                .addGroup(dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dashboardSettingsLayout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(contentTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 330, Short.MAX_VALUE)
                        .addComponent(submitButton))
                    .addGroup(dashboardSettingsLayout.createSequentialGroup()
                        .addGroup(dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(dashboardSettingsLayout.createSequentialGroup()
                                .addGap(92, 92, 92)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(dashboardSettingsLayout.createSequentialGroup()
                                .addGap(73, 73, 73)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(recipientComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(dashboardSettingsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(dashboardSettingsLayout.createSequentialGroup()
                                .addGap(60, 60, 60)
                                .addGroup(dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(dashboardSettingsLayout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(updateNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(dashboardSettingsLayout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(updateContentTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(updateButton))
                            .addGroup(dashboardSettingsLayout.createSequentialGroup()
                                .addGroup(dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addGroup(dashboardSettingsLayout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel2))
                                    .addGroup(dashboardSettingsLayout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(selectNotificationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
            .addGroup(dashboardSettingsLayout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(activeCheckBox)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dashboardSettingsLayout.setVerticalGroup(
            dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardSettingsLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(recipientComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(contentTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(submitButton))
                .addGap(107, 107, 107)
                .addComponent(jLabel2)
                .addGroup(dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(selectNotificationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(updateNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(updateContentTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(updateButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10)
                    .addComponent(activeCheckBox))
                .addContainerGap(123, Short.MAX_VALUE))
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
                .addGap(0, 75, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButtonActionPerformed
        addNotification();//run the method to add a new notification
        recipientComboBox.setSelectedIndex(0);//rest combo box index to 0
        nameTextField.setText("");//reset the name text field
        contentTextField.setText("");//reset the text field for the content
    }//GEN-LAST:event_submitButtonActionPerformed

    //method fills the combo box with the list of users from the user table
    public void fillComboBox(){
        String[][] userTable = tables.get(2);//create a local user table
        String[][] notificationTable = tables.get(3);//create local notifications table
        
        //fill the combo box used to create a new notification
        for(int i = 0; i < userTable.length; i++){//loop through the user table records
            recipientComboBox.addItem(userTable[i][1]);//add the users name to the combo box
        }
        
        //fill the combo box used to update a notification
        for(int i = 0; i < notificationTable.length; i++){
            if(notificationTable[i][5].equals("1")){//if the notification is active and not set to unactive
                selectNotificationComboBox.addItem(notificationTable[i][6]);//fill the combo box with the indexed notification names
            }
        }
    }
    
    
    
    private void recipientComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recipientComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_recipientComboBoxActionPerformed

    private void selectNotificationComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectNotificationComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_selectNotificationComboBoxActionPerformed

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        updateNotification();//run the method to update a notification
        selectNotificationComboBox.setSelectedIndex(0);//rest combo box index to 0
        updateNameTextField.setText("");//reste the name text field
        updateContentTextField.setText("");//reset the text field for the content
    }//GEN-LAST:event_updateButtonActionPerformed

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox activeCheckBox;
    private javax.swing.JTextField contentTextField;
    private javax.swing.JPanel dashboardSettings;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JComboBox<String> recipientComboBox;
    private javax.swing.JComboBox<String> selectNotificationComboBox;
    private javax.swing.JButton submitButton;
    private javax.swing.JButton updateButton;
    private javax.swing.JTextField updateContentTextField;
    private javax.swing.JTextField updateNameTextField;
    // End of variables declaration//GEN-END:variables
}
