package TaskFunctionality;


import Functions.accessConfigFile;
import Functions.colours;
import Styles.buttons;
import java.awt.Desktop;
import java.io.File;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileSystemView;


public class addNewTask extends JPanel{
    ArrayList<String[][]> tables = new ArrayList<String[][]>();
    String databaseName;
    String loggedInUser;
    String password;
    
    ArrayList<String[][]> oldTables = new ArrayList<String[][]>();
    String usersCompanyID;
    
    accessConfigFile accessConfig = new accessConfigFile();//create an object for accessing the config file
    String colourModeSettingValue = accessConfig.searchForSettingsValue("darkModeEnabled");
    colours coloursObject = new colours(colourModeSettingValue);//create an object for accessing different colours used in the program
    buttons buttonObject = new buttons();
    
    File profilePicture;//create a file chooser to obtain the profile picture
    
    public addNewTask(ArrayList<String[][]> tables, String databaseName, String loggedInUser, String password) {
        initComponents();
        
        this.tables = tables;
        this.databaseName = databaseName;
        this.loggedInUser = loggedInUser;
        this.password = password;
        setVisible(true);
        
        buttonObject.applyButtonStyles(submitButton);//apply the default button style to the submit button
    }
    
    //method adds a new task to the database with provided parameters
    public void addTask(String[] task){
        final String url = "jdbc:mysql://localhost:3306/";//url of the database
        
        try{
            Connection connection = DriverManager.getConnection(url + databaseName, loggedInUser, password);//get a connection to the database
            String query = "insert into task (taskID, startDate, endDate, details, taskName, feedback, active, importance)" + " values (?, ?, ?, ?, ?, ?, ?, ?)";//the mysql insert statement
            
            String taskID = generateUniqueID("taskID");//creates a random ID for the taskID
            int active = 1;//represents boolean value (shows whether the task is inactive or not)
                        
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");//creates a format for the dates to follow
            Date parsedStart = format.parse(task[1]);//get the start date from the parsed array
            Date parsedEnd = format.parse(task[2]);//get the end date from the parsed array
            java.sql.Date startDate = new java.sql.Date(parsedStart.getTime());//create an sql date holding the start date
            java.sql.Date endDate = new java.sql.Date(parsedEnd.getTime());//create an sql date which holds the end date
            String importance = importanceComboBox.getSelectedItem().toString();
                                    
            //create the mysql insert preparedstatement
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString   (1, taskID);//the unique ID of the task record
            preparedStmt.setDate     (2, startDate);//the start date of the task from the date conversion of the parsed array (conversions above)
            preparedStmt.setDate     (3, endDate);//the end date of the record from the date conversion of the parsed array (conversions above)
            preparedStmt.setString   (4, task[3]);//the details of the record from the fourth slot in the parsed array
            preparedStmt.setString   (5, task[0]);//the name of the task from the first slot in the parsed array
            preparedStmt.setString   (6, "");//the feedback (left empty because feedback is added later)
            preparedStmt.setInt      (7, active);//the int represents a boolean value of 1. This equals true in the database
            preparedStmt.setString   (8, importance);//the importance level of the task from ther task table
            
            preparedStmt.execute();//execute the preparedstatement

            connection.close();//close the connection
            
            //this code below replaces the task table with the new task table which contains the additional record
            String[][] taskTable = tables.get(0);//create a local taskTable from the arraylist
            String[] newRecord = {taskID, startDate.toString(), endDate.toString(), task[3], task[0], "", Integer.toString(active), importance};//create an array to hold the new task record being added
            String[][] newTable = Arrays.copyOf(taskTable,taskTable.length+1);//copies the chat table into a new array with space for a new record (arrays are fixed size)
            newTable[newTable.length-1] = newRecord;//add the new record to the new table
            tables.set(0,newTable);//replace the old chat tablewith the updated version
            
        }catch(Exception exc){//catch any exceptions
            exc.printStackTrace();//print the exception
        }
    }
    
    //generates an unique ID that doesn't already exist in the database. Parameter is the type of ID required
    public String generateUniqueID(String required){
        String uniqueID = "";//create uniqueID variable to hold the ID being generated
        
        ArrayList<String> listOfTakenIDs = new ArrayList<String>();//arraylist will hold the list of IDs that already exist
        String[][] currentTable = tables.get(3);//create currentTable 2d array and set to user table by default
        int index = 0;//create variabel that represents index for column in table which contains the ID
        
        switch(required) {//switch based on the parsed required variable
            case "chatID":
                currentTable = tables.get(4);//create local version of the old chat table
                index = 0;//set index for the column
                break;
            case "companyID":
                currentTable = tables.get(1);//create local version of the old organisation table
                index = 0;//set index for the column
                break;
            case "taskID":
                currentTable = tables.get(0);//create local version of the old task table
                index = 0;//set index for the column
                break;
            default://userID
                currentTable = tables.get(3);//create local version of the old user table
                index = 0;//set index for the column
                break;
          }
        
        Boolean accepted = false;//boolean exists to verify the generated unique ID
                
        //populate the list of taken IDs
        for(int i = 0; i < currentTable.length; i++){//for the number of rows in the chat table in the oldTables arraylist
            listOfTakenIDs.add(currentTable[i][index]);//add the ID from the table to the list of taken IDs
        }
        //while the unique ID is not accepted, set accepted to true and if the ID is already taken then put back to false
        while(!accepted){
            byte[] array = new byte[5];//new array of single bytes
            new Random().nextBytes(array);//new random
            uniqueID = new String(array, Charset.forName("UTF-8"));//set the 5 letter strings to the uniqueID

            accepted = true;//make accepted equal true
            //compare the generated ID against the list of taken ones
            for(int i = 0; i < listOfTakenIDs.size(); i++){//for the number of IDs in the list of taken IDs
                if(uniqueID.equals(listOfTakenIDs.get(i))){//if the generated ID already exists in the provided slot
                    accepted = false;//make accepted equal false
                }
            }
        }
        
        return uniqueID;//return the unique ID
    }
    
    //reset the components that the user enters into
    public void emptyUserInput(){
        taskNameTextField.setText("");
        startDatePicker.getFormattedTextField().setText("");
        endDatePicker.getFormattedTextField().setText("");
        detailsTextField.setText("");
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dashboardSettings = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        taskNameTextField = new javax.swing.JTextField();
        detailsTextField = new javax.swing.JTextField();
        endDatePicker = new org.jdatepicker.JDatePicker();
        startDatePicker = new org.jdatepicker.JDatePicker();
        jLabel6 = new javax.swing.JLabel();
        importanceComboBox = new javax.swing.JComboBox<>();
        submitButton = new javax.swing.JButton();

        setBackground(coloursObject.getContentPanelColour());

        dashboardSettings.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(42, 45, 53)));
        dashboardSettings.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Microsoft Tai Le", 0, 14)); // NOI18N
        jLabel1.setForeground(coloursObject.getTextColour()
        );
        jLabel1.setText("Task Name:");

        jLabel2.setFont(new java.awt.Font("Microsoft Tai Le", 1, 18)); // NOI18N
        jLabel2.setForeground(coloursObject.getTextColour()
        );
        jLabel2.setText("Add New Task");

        jLabel3.setFont(new java.awt.Font("Microsoft Tai Le", 0, 14)); // NOI18N
        jLabel3.setForeground(coloursObject.getTextColour()
        );
        jLabel3.setText("Start Date:");

        jLabel4.setFont(new java.awt.Font("Microsoft Tai Le", 0, 14)); // NOI18N
        jLabel4.setForeground(coloursObject.getTextColour()
        );
        jLabel4.setText("End Date:");

        jLabel5.setFont(new java.awt.Font("Microsoft Tai Le", 0, 14)); // NOI18N
        jLabel5.setForeground(coloursObject.getTextColour()
        );
        jLabel5.setText("Details:");

        jLabel6.setFont(new java.awt.Font("Microsoft Tai Le", 0, 14)); // NOI18N
        jLabel6.setForeground(coloursObject.getTextColour()
        );
        jLabel6.setText("Importance:");

        importanceComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "High", "Medium", "Low" }));
        importanceComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importanceComboBoxActionPerformed(evt);
            }
        });

        submitButton.setText("Submit New Task");
        submitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dashboardSettingsLayout = new javax.swing.GroupLayout(dashboardSettings);
        dashboardSettings.setLayout(dashboardSettingsLayout);
        dashboardSettingsLayout.setHorizontalGroup(
            dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardSettingsLayout.createSequentialGroup()
                .addGroup(dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dashboardSettingsLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel2))
                    .addGroup(dashboardSettingsLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(submitButton)
                            .addGroup(dashboardSettingsLayout.createSequentialGroup()
                                .addGroup(dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(startDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(taskNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(detailsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(endDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(importanceComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        dashboardSettingsLayout.setVerticalGroup(
            dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardSettingsLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(taskNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dashboardSettingsLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addGap(52, 52, 52)
                        .addComponent(jLabel5))
                    .addGroup(dashboardSettingsLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addGroup(dashboardSettingsLayout.createSequentialGroup()
                                .addComponent(startDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(endDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(detailsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(13, 13, 13)
                .addGroup(dashboardSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(importanceComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(submitButton)
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
                .addGap(0, 62, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButtonActionPerformed
        String taskName = taskNameTextField.getText();//get the task name from the users input in the textfield
        
        //convert the date picker dates into a string which can then be parsed and used
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd"); //create a date format
            //get the date chosen by the user and convert it to text
            GregorianCalendar newGregCal = (GregorianCalendar) startDatePicker.getModel().getValue();//return the gregorianCalendar Date from the JDatePicker
            Date newStartDate = (Date)(newGregCal.getTime());//convert the gregorian calendar date to a new date object
            java.sql.Date sDate = new java.sql.Date(newStartDate.getTime());//convert date to an sql date object
        String startDate = dateFormat.format(sDate);//create the string which holds the final parsable date
            //get the date chosen by the user and convert it to text
            GregorianCalendar newGregCal2 = (GregorianCalendar) endDatePicker.getModel().getValue();//return the gregorianCalendar Date from the JDatePicker
            Date newEndDate = (Date)(newGregCal2.getTime());//convert the gregorian calendar date to a new date object
            java.sql.Date eDate = new java.sql.Date(newEndDate.getTime());//convert date to an sql date object
        String endDate = dateFormat.format(eDate);//create the string which holds the final parsable date
        
        String details = detailsTextField.getText();//get the details from the user input in the text field
        
        String[] task = {taskName, startDate, endDate, details};//create a string array holding all user entered data for the new task
        
        addTask(task);//call the add task method with the string array to create the new task
        
        emptyUserInput();//call method to empty out the user inputs
    }//GEN-LAST:event_submitButtonActionPerformed

    private void importanceComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importanceComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_importanceComboBoxActionPerformed

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel dashboardSettings;
    private javax.swing.JTextField detailsTextField;
    private org.jdatepicker.JDatePicker endDatePicker;
    private javax.swing.JComboBox<String> importanceComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private org.jdatepicker.JDatePicker startDatePicker;
    private javax.swing.JButton submitButton;
    private javax.swing.JTextField taskNameTextField;
    // End of variables declaration//GEN-END:variables
}
