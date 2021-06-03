
import Functions.accessConfigFile;
import Functions.colours;
import Styles.titlebar;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.swing.BorderFactory;

/**
 *
 * @author ewand
 */
public class createNewCompany extends javax.swing.JFrame {

    Boolean companyCreated = false;//boolean holds the state of whether the new company has been created
    
    private String url = "jdbc:mysql://localhost:3306/";//url for accessing the database
    
    //variables hold the dimensions of the frame
    //also will hold the location of the frame
    int jframeWidth = 300;//define the width
    int jframeHeight = 450;//define the height
    int jframeXLocation;//create var for holding the x location
    int jframeYLocation;//create var for holding the y location
    
    accessConfigFile accessConfig = new accessConfigFile();//create an object for accessing the config file
    String colourModeSettingValue = accessConfig.searchForSettingsValue("darkModeEnabled");
    colours coloursObject = new colours(colourModeSettingValue);//create an object for accessing different colours used in the program
    
    public createNewCompany() {//constructor
        initComponents();//initialise the components
        
        titlebar tb = new titlebar(createNewCompany.this, null);//create the titlebar class with the JFrame as the parameter
        tb.applyTitleBarSettings(titlebar);//call to apply styles and settings to the titlebar
        
        createComponents();//call method to create components (apply additional code to existing features)
        setLayouts();//call method to assign layouts to panels
        
        setLocationRelativeTo(null);//move the frame to centre of the page
        getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, coloursObject.getTitlePanelColour()));//set the border of the jframe
    }
    
    //call method to create and assign components values
    public void createComponents(){
        //register company text field mouse listener
        registerCompanyTextField.addMouseListener(new MouseAdapter(){//add mouse listener to the register company text field
            public void mouseClicked(MouseEvent e){//when the mouse is clicked on the register company text field
                registerCompanyTextField.setText("");//set the text of the field to blank
            }
        });
        //create button action listener
        createButton.addActionListener(new ActionListener() {//create listener for the add new task button
            public void actionPerformed(ActionEvent e) {//wehn an action is performed
                String databaseName = registerCompanyTextField.getText();//create local version of the text in the new database textfield
                
                try{
                    //attempt to register account
                    //create connection to web server using the register account that already exists
                    Connection conn = DriverManager.getConnection(url, "registerAccount", null);//create a connection to the phpmyadmin using the register account
                    
                    Statement state = conn.createStatement();//create a statement
                    System.out.println("createCompany: creating new company database");
                    state.executeUpdate("CREATE DATABASE " + databaseName);//execute update query to create database with the assigned name
                    System.out.println("createCompany: company database created");
                    state.close();//close the statement
                    
                    conn.close();//close the connection for creating the database
                    
                    //create connection to company database using the register account
                    Connection dbconn = DriverManager.getConnection(url + databaseName, "registerAccount", null);//create a connection to the newly created database
                    System.out.println("createCompany: connected to new database");
                    
                    //create the user table in the new database
                    Statement userState = dbconn.createStatement();//create a statement
                    System.out.println("createCompany: creating user table");
                    userState.executeUpdate("CREATE TABLE user(UserID char(5), Name varchar(100), DateOfBirth date, Socials varchar(1000), PRIMARY KEY(UserID))");//execute update to database
                    System.out.println("createCompany: user table created");
                    userState.close();//close statement
                    
                    //create the task table in the new database
                    Statement taskState = dbconn.createStatement();//create a statement
                    System.out.println("createCompany: creating task table");
                    taskState.executeUpdate("CREATE TABLE task(TaskID char(5), StartDate date, EndDate date, Details varchar(5000), TaskName char(100), Feedback varchar(1000), OrganisationID char(5), Active int(1), PRIMARY KEY(TaskID))");
                    System.out.println("createCompany: task table created");
                    taskState.close();//close statement
                    
                    //create the chat table in the new database
                    Statement chatState = dbconn.createStatement();//create a statement
                    System.out.println("createCompany: creating chat table");
                    chatState.executeUpdate("CREATE TABLE chat(ChatID char(5), SenderID char(5), Message longtext, RecipientID char(5), TimeSent timestamp, PRIMARY KEY(ChatID))");
                    System.out.println("createCompany: chat table created");
                    chatState.close();//close statement
                    
                    //create the notifications table in the new database
                    Statement notificationState = dbconn.createStatement();//create a statement
                    System.out.println("createCompany: creating notifications table");                //SenderName should be ID
                    notificationState.executeUpdate("CREATE TABLE notifications(NotificationID char(5), SenderName char(5), RecipientID char(5), Content longtext, TimeSent timestamp, PRIMARY KEY(NotificationID))");
                    System.out.println("createCompany: notifications table created");
                    notificationState.close();//close statement
                    
                    //create the statistics table in the new database
                    Statement statisticsState = dbconn.createStatement();//create a statement
                    System.out.println("createCompany: creating statistics table");
                    statisticsState.executeUpdate("CREATE TABLE statistics(UserID char(5), LastLoggedInTime time, LastLoggedInDate date, PRIMARY KEY(UserID))");
                    System.out.println("createCompany: statistics table created");
                    statisticsState.close();//close statement
                    
                    dbconn.close();//close connection to the database

                    companyCreated = true;//set the successfull creation table to true
                    
                }catch(Exception exc){//catch any exceptions
                    exc.printStackTrace();//print the exception
                }
            } 
        });
    }
    
    
    //method is called to check if the attempted creation of the company
    public Boolean checkCreationAttempt(){
        return companyCreated;
    }
    
    //method assigns layouts to JPanels
    public void setLayouts(){
        FlowLayout flowLayout = new FlowLayout(FlowLayout.RIGHT);//create a right align flow layout
        titlebar.setLayout(flowLayout);//add the flow layout to the title bar
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        contentPanel = new javax.swing.JPanel();
        createCompanyLabel = new javax.swing.JLabel();
        registerCompanyTextField = new javax.swing.JTextField();
        createButton = new javax.swing.JButton();
        titlebar = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(300, 450));
        setMinimumSize(new java.awt.Dimension(300, 450));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(300, 450));

        contentPanel.setBackground(coloursObject.getMenuPanelColour()
        );

        createCompanyLabel.setFont(new java.awt.Font("Microsoft Tai Le", 1, 24)); // NOI18N
        createCompanyLabel.setForeground(new java.awt.Color(255, 255, 255));
        createCompanyLabel.setText("CREATE COMPANY");

        registerCompanyTextField.setBackground(coloursObject.getContentPanelColour()
        );
        registerCompanyTextField.setFont(new java.awt.Font("Microsoft Tai Le", 0, 11)); // NOI18N
        registerCompanyTextField.setForeground(new java.awt.Color(255, 255, 255));
        registerCompanyTextField.setText("Company Name");
        registerCompanyTextField.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(46, 48, 57), 3, true));
        registerCompanyTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerCompanyTextFieldActionPerformed(evt);
            }
        });

        createButton.setBackground(coloursObject.getContentPanelColour()
        );
        createButton.setFont(new java.awt.Font("Microsoft Tai Le", 1, 11)); // NOI18N
        createButton.setForeground(new java.awt.Color(255, 255, 255));
        createButton.setText("Create");
        createButton.setBorder(null);
        createButton.setContentAreaFilled(false);
        createButton.setFocusPainted(false);
        createButton.setFocusable(false);
        createButton.setOpaque(true);
        createButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout contentPanelLayout = new javax.swing.GroupLayout(contentPanel);
        contentPanel.setLayout(contentPanelLayout);
        contentPanelLayout.setHorizontalGroup(
            contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentPanelLayout.createSequentialGroup()
                .addGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(contentPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(createCompanyLabel)
                            .addGroup(contentPanelLayout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(registerCompanyTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(contentPanelLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(createButton, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        contentPanelLayout.setVerticalGroup(
            contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(createCompanyLabel)
                .addGap(46, 46, 46)
                .addComponent(registerCompanyTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(createButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(246, Short.MAX_VALUE))
        );

        titlebar.setBackground(coloursObject.getTitlePanelColour()
        );
        titlebar.setMaximumSize(new java.awt.Dimension(32767, 34));
        titlebar.setMinimumSize(new java.awt.Dimension(100, 34));
        titlebar.setPreferredSize(new java.awt.Dimension(0, 34));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titlebar, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
            .addComponent(contentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(titlebar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(contentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void registerCompanyTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerCompanyTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_registerCompanyTextFieldActionPerformed

    private void createButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createButtonActionPerformed
        
    }//GEN-LAST:event_createButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(createNewCompany.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(createNewCompany.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(createNewCompany.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(createNewCompany.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new createNewCompany().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel contentPanel;
    private javax.swing.JButton createButton;
    private javax.swing.JLabel createCompanyLabel;
    private javax.swing.JTextField registerCompanyTextField;
    private javax.swing.JPanel titlebar;
    // End of variables declaration//GEN-END:variables
}
