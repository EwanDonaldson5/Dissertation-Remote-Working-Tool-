package Features;


import ChatFunctionality.specificUser;
import Functions.generateUniqueID;
import Functions.colours;
import Functions.accessConfigFile;
import Functions.database;
import Styles.buttons;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import static java.util.concurrent.TimeUnit.SECONDS;
import javax.swing.BorderFactory;
import static javax.swing.BorderFactory.createEmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;


public class chat {
    private String url = "jdbc:mysql://localhost:3306/";//the url to give access to the phpmyadmin site
    String loggedInUser;
    String password;
    String databaseName;
    
    ArrayList<String[][]> tables = new ArrayList<String[][]>();
    
    database dbs;//get access to the database object created in the remoteWorkingTool class
    int messagesRecievedDifference;//var holds the number of messages that need to be added to the dbs noOfMessagesRecieved var
    
    accessConfigFile accessConfig = new accessConfigFile();//create an object for accessing the config file
    String colourModeSettingValue = accessConfig.searchForSettingsValue("darkModeEnabled");
    colours coloursObject = new colours(colourModeSettingValue);//create an object for accessing different colours used in the program
    
    buttons buttonStyles = new buttons();//create a copy of the button styles object
    
    public JPanel chatPanel = new JPanel();
    
    String currentlyViewedUser = "";//a global duplicate of the selectedUser string
    int sizeOfChatTable = 0;//integer holds the size of the chat table thats held in the program
    
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    
    public chat(ArrayList<String[][]> tables, String databaseName, String loggedInUser, String password, database dbs) {
        this.tables = tables;
        this.databaseName = databaseName; 
        this.loggedInUser = loggedInUser;
        this.password = password;
        this.dbs = dbs;
                
        createChatPanel(loggedInUser);
        
        chatTableUpdateCheck();//call method to constantly check for chat updates
    }

    //creates the chat panel by generating the list of users then calling another method to update the messages that are listed
    public void createChatPanel(String selectedUser){
        chatPanel.removeAll();//reset the panel
        
        String[][] userTable = tables.get(2);//create a local copy of the user table
        
        String selectedUsersName = "Name Not Found";
        for(int i = 0; i < userTable.length; i++){
            if(selectedUser.equals(userTable[i][0])){
                selectedUsersName = userTable[i][1];
            }
        }
        System.out.println("chat: currently viewing " + selectedUsersName);
        currentlyViewedUser = selectedUser;//assign a name to the global copy of the selectedUser variable
        
        GridBagLayout chatLayout = new GridBagLayout();//create the layout for the chat page (col 1 for user list / col 2 for messages)
        chatPanel.setLayout(chatLayout);//sets jpanel to a grid with two columns
        chatPanel.setBackground(coloursObject.getContentPanelColour());
        
        GridBagConstraints userListConstraints = new GridBagConstraints();//create a grid bag constraint for the list of users
        userListConstraints.fill = GridBagConstraints.BOTH;//set the constaint to stretch acros both axis
        userListConstraints.gridx = 0;//set the top left most corner to the first column
        userListConstraints.gridy = 0;//set the top left most corner to the first row
        userListConstraints.weightx = 0;//this line sticks  the user list to the side of the panel (fixes an issue from before)
        userListConstraints.weighty = 1;
        
        JPanel userList = new JPanel();//create a panel for the list of users you can message
        userList.setLayout(new GridLayout(0,1));//set the layout to a 1 column list
        userList.setBackground(coloursObject.getContentPanelColour());
        chatPanel.add(userList,userListConstraints);
        
        //display the list of users that can be messaged
        for(int i = 0; i < userTable.length; i++){//loop for the number of users that exist in the table
            int index = i;//creates a copy of the loop counter as an index for the user table
            
            if(!userTable[i][1].equals(loggedInUser)){//if the currently searched username is the currently logged in one
                JPanel panel = new JPanel();//create a new panel
                panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, coloursObject.getBorderColour()));//set the border style for the panel
                panel.setBackground(coloursObject.getContentPanelColour());
                
                JLabel userName = new JLabel(userTable[i][1]);//create a label that contains the users name
                userName.setForeground(coloursObject.getButtonTextHoverColour());
                userName.addMouseListener(new java.awt.event.MouseAdapter() {//add listener for the label
                    public void mouseClicked(java.awt.event.MouseEvent evt) {//when hovering over mouse
                        System.out.println("chat: user profile selected");
                        openSpecificUser(userTable, index);//method opens the page of a specific task to view individual information on it
                    }
                    public void mouseEntered(java.awt.event.MouseEvent evt) {//when hovering over mouse
                        userName.setForeground(coloursObject.getButtonTextColour());//the colour of the text when the cursor is removed
                    }
                    public void mouseExited(java.awt.event.MouseEvent evt) {//when cursor stops hovering over the mouse
                        userName.setForeground(coloursObject.getButtonTextHoverColour());//the colour of the text when hovered over
                    }
                });
                
                String thisUserID = userTable[i][0];//grabs the userID of the the user that a panel is being made for
                
                JButton change = new JButton("View Messages");//creates a new button which will change the currently displayed 
                buttonStyles.applyButtonStyles(change);
                change.addActionListener(new ActionListener() {//create listener for the view change button
                    public void actionPerformed(ActionEvent e) {
                        chatPanel.removeAll();//clear the chat panel
                        createChatPanel(thisUserID);//recreate chat panel with the new selected userID
                        chatPanel.revalidate();//revalidate the contents of the panel
                        chatPanel.repaint();//repaint the components
                    } 
                });
                
                panel.add(userName);//add the users name in the form of a label to the panel
                panel.add(change);//add the change button to the panel
                
                userList.add(panel);//add the panel to the list of users
                
                if(selectedUser == ""){selectedUser = userTable[i][1];}//if the page has been created without a selected user then set to last one generated
            }
        }
        if(selectedUser == loggedInUser){//if the selected user equals the logged in user
            updateMessages("blank");//call the method with the string blank which makes the method generate a blank messages panel
        }else{
            updateMessages(selectedUser);//call the method to update the messages being displayed, pass the selcted user as a parameter
        }
        
        chatPanel.repaint();
        chatPanel.revalidate();
    }
    
    public void openSpecificUser(String[][] userTable, int index){
        String[] record = userTable[index];//create a copy of the individual record from the task table with the given index
        
        chatPanel.removeAll();//clear the taskPanel
        chatPanel.setLayout(new GridLayout(0,1));//update the layout to allow this to be fullscreen
        specificUser displayUser = new specificUser(record);//create a version of the specificTask class and feed it the info obtained from the record
        chatPanel.add(displayUser);//add the individual task panel to the main taskPanel
        chatPanel.revalidate();
        chatPanel.repaint();//update the JPanel
    }
    
    //updates the right panel of the chat page so only the selected users conversation appears
    public void updateMessages(String selectedUser){
        
        String[][] chatTable = getUpdatedChatTable();
        //String[][] chatTable = tables.get(4);
        
        
        FlowLayout left = new FlowLayout(FlowLayout.LEFT);//creates a layout that moves the message to the left of the page
        FlowLayout right = new FlowLayout(FlowLayout.RIGHT);//creates layout that moves the message to the right of the page
        
        GridBagConstraints chatConstraints = new GridBagConstraints();//create a grid bag constraint for the chat jpanel
        chatConstraints.fill = GridBagConstraints.BOTH;//set the constaint to stretch acros both axis
        chatConstraints.gridx = 1;//set the top left most corner to the first column
        chatConstraints.gridy = 0;//set the top left most corner to the first row
        chatConstraints.gridwidth = 5;//set the column span of the constraint to 6
        chatConstraints.gridheight = 1;
        chatConstraints.weightx = 1;
        chatConstraints.weighty = 1;
                
        if(selectedUser.equals("blank")){//if the parsed user is blank
            JPanel content = new JPanel();//create a jpanel for the messages
            content.setBackground(coloursObject.getContentPanelColour());//set the background colour of the jpanel
            chatPanel.add(content, chatConstraints);//add the messages jpanel to the chat panel
        }else{
            JPanel messagePanel = new JPanel();//create panel for the messages and controls to be added to
            messagePanel.setLayout(new GridBagLayout());
            messagePanel.setBackground(coloursObject.getContentPanelColour());
            
            //create a grid bag constraint for the messages section of the messagePanel
            GridBagConstraints mes = new GridBagConstraints();
            mes.fill = GridBagConstraints.BOTH;//set the constaint to stretch acros both axis
            mes.gridx = 0;//set the top left most corner to the first column
            mes.gridy = 0;//set the top left most corner to the first row
            mes.weightx = 1;
            mes.weighty = 1;
            
            //create a panel to hold the list of messages
            JPanel content = new JPanel();//create panel to hold the chat messages
            content.setBackground(coloursObject.getContentPanelColour());//set the background of the jpanel
            content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));//set the layout to a 1 column layout

            //create scroll panel to hold the list of messages
            //this allows messages to be scrolled through
            JScrollPane scroll = new JScrollPane(content);
            scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scroll.setBorder(createEmptyBorder());
            scroll.getVerticalScrollBar().setOpaque(false);
            
            messagePanel.add(scroll, mes);
            chatPanel.add(messagePanel, chatConstraints);//add the messages to the jpanel

            //two counters are used to count the number of messages sent by each individual
            int userSentMessageCounter = 0;//count of messages sent by the user
            int viewedSentMessageCounter = 0;//count of messages sent by the person being viewed
            
            String loggedInUserID = getLoggedInUserID();
            
            //for loop displays the chat messages on the right side or left of the chat panel
            for(int i = 0; i < chatTable.length; i++){
                if(chatTable[i][1].equals(selectedUser) || chatTable[i][3].equals(selectedUser)){//if the currently selected user is equal to the userID
                    JPanel panel = null;//create a jpanel
                    
                    if(chatTable[i][1].equals(loggedInUserID)){//if the message was sent by this user
                        
                        //System.out.println("chat: message sent by user was found");
                        userSentMessageCounter++;//increment counter of how many messages sent by the user have been found
                        
                        panel = new JPanel(left);//create the panel to hold the message
                        panel.setBackground(coloursObject.getContentPanelColour());
                        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, coloursObject.getBorderColour()));

                        JLabel message = new JLabel(chatTable[i][2]);//create the label that holds the message
                        message.setFont(new java.awt.Font("Microsoft Tai Le", 0, 11));//set the text of the jlabel
                        message.setForeground(coloursObject.getButtonTextHoverColour());//set the text colour of the jlabel
                        panel.add(message);//add the message to the panel

                        content.add(panel);//add the chat to the chat panel
                        
                    }else if(chatTable[i][3].equals(loggedInUserID)){//if user was sent by the other user
                        
                        //System.out.println("chat: message found from viewed user");
                        viewedSentMessageCounter++;//increment counter of how many messages sent by the viewed person have been found
                        
                        panel = new JPanel(right);//create the panel to hold the message
                        panel.setBackground(coloursObject.getContentPanelColour());
                        
                        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, coloursObject.getBorderColour()));

                        JLabel message = new JLabel(chatTable[i][2]);//create the label that holds the message
                        message.setFont(new java.awt.Font("Microsoft Tai Le", 0, 11));//set the text of the jlabel
                        message.setForeground(coloursObject.getButtonTextHoverColour());//set the text colour of the jlabel
                        panel.add(message);//add the message to the panel

                        content.add(panel);//add the chat to the chat panel
                    }
                }
            }
            int totalFoundMessages = userSentMessageCounter + viewedSentMessageCounter;
            System.out.println("chat: found " + totalFoundMessages + " total messages");
            System.out.println("chat:       " + userSentMessageCounter + " user sent messages");
            System.out.println("chat:       " + viewedSentMessageCounter + " viewed persons messages");
            
            //create the jpanel that contains the components to send a new message
            JPanel sendPanel = new JPanel(new GridLayout(0,2));//create a panel to hold the textfield and send button
            sendPanel.setBackground(coloursObject.getMenuPanelColour());
            sendPanel.setBorder(new EmptyBorder(20, 20, 10, 20));

            //create the text field the user can type into
            JTextField typeChat = new JTextField();//create textfield so user can type message
            typeChat.setBackground(coloursObject.getMenuPanelColour());
            typeChat.setBorder(new LineBorder(coloursObject.getContentPanelColour(), 2, true));
            typeChat.setForeground(coloursObject.getButtonTextHoverColour());
            
            //create the button that sends the message
            JButton sendMessage = new JButton("Send");//create send button so the user can send the message to the database
            buttonStyles.applyButtonStyles(sendMessage);//call method to apply button styles
            sendMessage.addActionListener(new ActionListener() {//create listener for the view change button
                public void actionPerformed(ActionEvent e) {
                    if(!typeChat.getText().equals("")){//if typechat is not empty
                        chatPanel.removeAll();//clear the chatPanel

                        sendMessage(typeChat, selectedUser, chatTable);//send the message across to the database by calling the send message method
                        createChatPanel(selectedUser);//recreate chat panel with the new selected userID

                        dbs.incrementMessagesSent();//call method to increment count of messages sent by 1
                        
                        chatPanel.revalidate();//revalidate the contents of the panel
                        chatPanel.repaint();//repaint the components
                    }
                } 
              } );
            
            //create a grid bag constraint for the controls section of the messagesPanel
            GridBagConstraints con = new GridBagConstraints();
            con.fill = GridBagConstraints.BOTH;//set the constaint to stretch acros both axis
            con.gridx = 0;//set the top left most corner to the first column
            con.gridy = 1;//set the top left most corner to the first row
            con.weightx = 1;
            con.weighty = 0;//pins this to the bottom of the jpanel
            
            //add the components to the sendPanel
            sendPanel.add(typeChat);
            sendPanel.add(sendMessage);
            messagePanel.add(sendPanel, con);//add the controlls to the jpanel containing the messages
        }
    }
    
    
    
    //sends typed message to the database to be saved
    public void sendMessage(JTextField textField, String selectedUser, String[][]chatTable){
        
        String[][] userTable = tables.get(2);//create a local copy of the user table
        
        String loggedInUserID = getLoggedInUserID();
        
        try{
            //System.out.println("chat: selected user's unique id is " + selectedUser);
            //get a connection to the database
            Connection connection = DriverManager.getConnection(url + databaseName, loggedInUser, password);

            //create a timestamp as its required for saving the message to the database
            java.util.Date date = new java.util.Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            java.sql.Timestamp sqlTime = new java.sql.Timestamp(date.getTime());
            
            //generate an unique ID by running method
            generateUniqueID generate = new generateUniqueID();
            String chatID = generate.generate(tables, "chatID");
                        
            //the mysql insert statement
            String query = " insert into chat (chatID, senderID, message, recipientID, timeSent)" + " values (?, ?, ?, ?, ?)";

            //create the mysql insert preparedstatement
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString   (1, chatID);
            preparedStmt.setString   (2, loggedInUserID);
            preparedStmt.setString   (3, textField.getText());
            preparedStmt.setString   (4, selectedUser);
            preparedStmt.setTimestamp(5, sqlTime);

            preparedStmt.execute();//execute the preparedstatement

            connection.close();//close the connection
            
            String[] newRecord = {chatID, loggedInUser, textField.getText(), selectedUser, sqlTime.toString()};//create an array to hold the new chat record being added
            String[][] newTable = Arrays.copyOf(chatTable,chatTable.length+1);//copies the chat table into a new array with space for a new record (arrays are fixed size)
            newTable[newTable.length-1] = newRecord;//add the new record to the new table
            tables.set(4,newTable);//replace the old chat table with the updated version
        
        }catch(Exception exc){//catch any exceptions
            exc.printStackTrace();//print the exception
        }
    }
    
    //method constantly checks for updates in the chat page and updates the display for the user
    public void chatTableUpdateCheck(){
        final Runnable beeper = new Runnable() {
            public void run() { 
                Boolean tableComparison = compareTables();//set the boolean to the output from compareTables method
                if(tableComparison){//if the table comparison boolean contains true value
                    System.out.println("chat: online table is different to local table");
                    dbs.incrementMessagesRecieved(messagesRecievedDifference);//call database method to increase noOfMessagesRecieved var by the calculated value
                    createChatPanel(currentlyViewedUser);//run method to update the chatPanel with the currently displayed user
                }
            }
        };
        final ScheduledFuture<?> beeperHandle = scheduler.scheduleAtFixedRate(beeper, 10, 10, SECONDS);
        scheduler.schedule(new Runnable() {
            public void run() {
                beeperHandle.cancel(true);
            }
        }, 60 * 60, SECONDS);
    }
    
    //method compares the table contained within this class against the table that exists on the db
    //if the tables are differant then it returns true
    public Boolean compareTables(){
        String[][] localChatTable = tables.get(4);
        int rows = 0;
        int cols = 0;
        
        try{
            Connection conn = DriverManager.getConnection(url + databaseName, loggedInUser, password);//create a connection to the phpmyadmin page on the local host
            Statement chatStatement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);//create a statement
            ResultSet chatResultSet = chatStatement.executeQuery("select * from chat order by timeSent asc");//create a result set from the db table
            
            rows = getTableRows(chatResultSet);//get the row count from the result set
            cols = getTableColumns(chatResultSet);//get the column count from the result set
            String[][] dbChatTable = new String[rows][cols];//create the table array with the given dimensions
            chatResultSet.beforeFirst();//reset the cursor of the result set so it points to its original position (as if unused)

            dbChatTable = (createTableArray(chatResultSet,dbChatTable));//create the table by calling the createTableArray method which returns the 2d array table
            
            //Now compare the two obtained tables
            System.out.println("chat: comparing table lengths " + localChatTable.length + " : " + dbChatTable.length);
            if(localChatTable.length != dbChatTable.length){//if the table the program has is not the same length as the table online
                int difference = dbChatTable.length - localChatTable.length;//calculate the number of messages in difference
                
                return true;//return a true boolean value
            }
            
        }catch(Exception exc){//catch any exceptions
            exc.printStackTrace();//print the exception
        }
        
        return false;//return a false boolean
    }
    
    //method returns the ID of the currently logged in user
    public String getLoggedInUserID(){
        String[][] userTable = tables.get(2);
        
        //below code uses the name found in the loggedInUser string to find the ID of that user in the same record
        String loggedInUserID = "Name Not Found";//string holds the logged in users ID, it by default holds an error message
        for(int i = 0; i < userTable.length; i++){
            if(loggedInUser.equals(userTable[i][1])){
                loggedInUserID = userTable[i][0];
            }
        }
        System.out.println("chat: logged in user ID is " + loggedInUserID);
        return loggedInUserID;
    }
    
    //method returns the most up to date version of the chat table in the form of a 3d string array
    public String[][] getUpdatedChatTable(){
        
        try{
            //System.out.println("");
            Connection conn = DriverManager.getConnection(url + databaseName, loggedInUser, password);//create a connection to the phpmyadmin page on the local host
            Statement chatStatement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);//create a statement
            ResultSet chatResultSet = chatStatement.executeQuery("select * from chat order by timeSent asc");//create a result set from the db table
            
            int rows = getTableRows(chatResultSet);//get the row count from the result set
            int cols = getTableColumns(chatResultSet);//get the column count from the result set
            String[][] dbChatTable = new String[rows][cols];//create the table array with the given dimensions
            chatResultSet.beforeFirst();//reset the cursor of the result set so it points to its original position (as if unused)

            dbChatTable = (createTableArray(chatResultSet,dbChatTable));//create the table by calling the createTableArray method which returns the 2d array table
            
            return dbChatTable;
            
        }catch(Exception e){
            System.out.println("chat: error fetching updated chat table");
        }
        
        return null;
    }
    
    //method returns the number of rows by moving cursor to the last row of the result set and gets the index for that line
    public int getTableRows(ResultSet rs){
        int rows = 0;//create rows variable
        try{
            rs.last();//move cursor to last row
            rows = rs.getRow();//get the row index
        }catch(Exception exc){//catch any exceptions
            exc.printStackTrace();//print the exception
        }
        
        return rows;
    }
    
    //method returns number of columns by
    public int getTableColumns(ResultSet rs){
        int cols = 0;//create columns variable
        try{
            ResultSetMetaData rsmd = rs.getMetaData();//get the meta data from the result set
            cols = rsmd.getColumnCount();//call getColumnCount method to return number of columns
        }catch(Exception exc){//catch any exceptions
            exc.printStackTrace();//print the exception
        }
        
        return cols;
    }
    
    //creates the 2d array for the tables in the database. It uses the frame of the table and reads the result set into it
    public String[][] createTableArray(ResultSet rs, String[][] tableFrame){
        String[][] table = tableFrame;//initialise the table 2d array
        try{
            int count = 0;//counter used for indexing rows in the while loop
            while(rs.next()){//while there is another ROW to be read
                for (int j = 1; j <= table[count].length; j++){//loop for the number of columns that exist
                    table[count][j-1] = rs.getString(j);//set location in taskTable 2d array to the read value from result set
                }   
                count = count + 1;//increment the ocunter variable
            }
            
        }catch(Exception exc){
            exc.printStackTrace();
        }
        return table;//return the filled table
    }
    
    
    /**
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 942, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    **/

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
