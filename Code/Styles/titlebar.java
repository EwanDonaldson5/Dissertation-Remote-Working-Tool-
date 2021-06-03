package Styles;

import Features.notifications;
import Functions.Ping;
import Functions.accessConfigFile;
import Functions.colours;
import Functions.generateUniqueID;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author ewand
 */
public class titlebar {
    
    JFrame rootJframe;
    Boolean isFullScreen = false;
    
    int jframeWidth = 1440;
    int jframeHeight = 810;
    int jframeXLocation;
    int jframeYLocation;
    
    generateUniqueID generate = new generateUniqueID();
    accessConfigFile accessConfig = new accessConfigFile();//create an object for accessing the config file
    String colourModeSettingValue = accessConfig.searchForSettingsValue("darkModeEnabled");
    colours coloursObject = new colours(colourModeSettingValue);//create an object for accessing different colours used in the program
    
    Ping pingObject;
    
    public titlebar(JFrame jframe, Ping pingObject){
        this.rootJframe = jframe;
        this.pingObject = pingObject;
    }
    
    //applys the settings and styles for the titlebar (adds controls to close the page and to move the jframe around the screen)
    public void applyTitleBarSettings(JPanel panel){
        //apply settings to the jpanel to allow for it to be dragged around the screen
        panel.addMouseListener(new MouseAdapter() {//create mouse listener for the titlebar
            public void mousePressed(MouseEvent e){//when the mouse is pressed
                jframeXLocation = e.getX();//get the x coordinate relative to the window
                jframeYLocation = e.getY();//get the y coordinate relative to the window
            }
        });
        panel.addMouseMotionListener(new MouseAdapter() {//create mouse motion listener for the titlebar
            public void mouseDragged(MouseEvent e) {//when the mouse is dragged
                rootJframe.setLocation(//set the jframe location to the following calculated x and y values
                    rootJframe.getLocation().x + e.getX() - jframeXLocation,
                    rootJframe.getLocation().y + e.getY() - jframeYLocation 
                );
            } 
        });
        
        if(pingObject != null){//if the passed ping object isn't null
            Icon pingIcon = pingObject.pingIcon;//create icon using the bell.png image
            JButton pingButton = new JButton(pingIcon);//create the fullscreen button with the default maximise icon
            applyButtonStyles(pingButton, "scale");//apply button styles to this button
            pingButton.addActionListener(new ActionListener() {//create listener for close application button
                public void actionPerformed(ActionEvent e) {
                    System.out.println("titlebar: ping button pressed");
                    
                    final String url = "jdbc:mysql://localhost:3306/";//url of the database
                    ArrayList<String[][]> tables = pingObject.tables;
                    String[][] userTable = tables.get(2);
                    String databaseName = pingObject.databaseName;
                    String loggedInUser = pingObject.loggedInUser;
                    String loggedInUserID = "";
                    String password = pingObject.password;
                    
                    //loop in user table to attempt to find the id of the user using the program
                    //and to find the id of the chosen recipient
                    for(int i = 0; i < userTable.length; i++){//loop through the user table records
                        if(loggedInUser.equals(userTable[i][1])){//if the logged in username equals one from the user table
                            loggedInUserID = userTable[i][0];//update the var holding the ID of the logged in user
                        }
                    }
                    
                    //create a notification to get assistance
                    try{
                        Connection connection = DriverManager.getConnection(url + databaseName, loggedInUser, password);//get a connection to the database
                        String query = "insert into notifications (NotificationID, SenderID, RecipientID, Content, TimeSent, Active, Name)" + " values (?, ?, ?, ?, ?, ?, ?)";//the mysql insert statement

                        String notificationID = generate.generate(tables, "notificationID");//creates a random ID for the taskID
                        String senderID = loggedInUserID;
                        String recipientID = "";
                        String content = loggedInUser + " requires assistance!";
                        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                        int active = 1;//represents boolean value (shows whether the task is inactive or not)
                        String notificationName = "Assistance Notification";
                        
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
                        
                        System.out.println("titlebar: ping notification created successfully");
                    }catch(Exception exc){System.out.println("titlebar: error creating ping button");}
                } 
            });
            panel.add(pingButton);//add the close to taskbar button to the titlebar
        }
        
        //create a button that minimises the program to the taskbar
        Icon taskbarClose = new ImageIcon("../Images/closeToTaskbar.png");//create icon using the maxamise.png image
        JButton closeToTaskbar = new JButton(taskbarClose);//create the fullscreen button with the default maximise icon
        applyButtonStyles(closeToTaskbar, "scale");//apply button styles to this button
        closeToTaskbar.addActionListener(new ActionListener() {//create listener for close application button
            public void actionPerformed(ActionEvent e) {
                rootJframe.setState(JFrame.ICONIFIED);
            } 
        });
        panel.add(closeToTaskbar);//add the close to taskbar button to the titlebar
        
        //create a fullscreen button that allows the user to switch between fullscreen and windowed
        Icon maximiseIcon = new ImageIcon("../Images/maximise.png");//create icon using the maxamise.png image
        JButton fullScreen = new JButton(maximiseIcon);//create the fullscreen button with the default maximise icon
        applyButtonStyles(fullScreen, "scale");//apply button styles to this button
        fullScreen.addActionListener(new ActionListener() {//create listener for close application button
            public void actionPerformed(ActionEvent e) {
                if(!isFullScreen){//if the program is not in fullscreen mode
                    isFullScreen = true;//set the fullscreen boolean to true
                    Icon minimiseIcon = new ImageIcon("../Images/minimise.png");//create a minimise icon
                    fullScreen.setIcon(minimiseIcon);//update the icon of the button
                    rootJframe.setExtendedState(JFrame.MAXIMIZED_BOTH);//make the window fullscreen
                }else{
                    isFullScreen = false;//set boolean to false
                    Icon maximiseIcon = new ImageIcon("../Images/maximise.png");//create a maximise icon
                    fullScreen.setIcon(maximiseIcon);//update the buttons icon to display maximise
                    rootJframe.setSize(new Dimension(jframeWidth, jframeHeight));//set the size of the window to the default size
                    rootJframe.setLocationRelativeTo(null);//moves the jframe to the centre of the screen
                }
            } 
        });
        panel.add(fullScreen);//add the fullscreen button to the titlebar
        
        //create a close application button that resides in the top right corner
        Icon crossIcon = new ImageIcon("../Images/cross.png");
        JButton exitApplication = new JButton(crossIcon);
        applyButtonStyles(exitApplication, "close");//apply styling to the button
        exitApplication.addActionListener(new ActionListener() {//create listener for close application button
            public void actionPerformed(ActionEvent e) {
                System.exit(0);//shut down the program
            } 
        });
        panel.add(exitApplication);//add the close button to the titlebar
        
    }
    
    
    //when called it applys the styles to the provided jbutton
    public void applyButtonStyles(JButton button, String type){
        button.setBackground(coloursObject.getTitlePanelColour());//set background colour
        button.setForeground(coloursObject.getButtonTextColour());//set foreground colour
        button.setFont(new java.awt.Font("Microsoft Tai Le", 0, 16));//set font and size
        button.setBorder(null);//remove the border//remove any border
        button.setFocusPainted(false);//make not focusable (custom hover effect overides this)
        button.setPreferredSize(new Dimension(50,25));//set preferred dimensions for the close application button
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {//when hovering over mouse
                button.setForeground(coloursObject.getButtonTextHoverColour());//the colour of the text when hovered over
                if(type == "close"){//if the close button is being edited
                    button.setBackground(new Color(237,64,64));
                }
                if(type == "scale"){//if the fullscreen button is being edited
                    button.setBackground(coloursObject.getMenuPanelColour());
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {//when cursor stops hovering over the mouse
                button.setForeground(coloursObject.getButtonTextColour());//the colour of the text when the cursor is removed
                button.setBackground(coloursObject.getTitlePanelColour());
            }
        });
    }
    
}
