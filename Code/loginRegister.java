
import Functions.colours;
import Functions.accessConfigFile;
import Styles.buttons;
import Styles.labels;
import Styles.titlebar;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author ewand
 */
public class loginRegister extends javax.swing.JFrame {

    Boolean loggedIn = false;
    Boolean isFullScreen = false;
    
    String activeCard;//string contains the text of the currently shown jpanel
    
    private String url = "jdbc:mysql://localhost:3306/";
    String companyName;
    String username;
    String password;
    
    //dimensions and location variables for the frame
    int jframeWidth = 300;
    int jframeHeight = 450;
    int jframeXLocation;
    int jframeYLocation;

    accessConfigFile accessConfig = new accessConfigFile();//create an object for accessing the config file
    String colourModeSettingValue = accessConfig.searchForSettingsValue("darkModeEnabled");
    colours coloursObject = new colours(colourModeSettingValue);//create an object for accessing different colours used in the program
    
    buttons buttonStyles = new buttons();//create a copy of the button styles object
    labels labelStyles = new labels();//create a copy of the label styles object 
    
    File profilePicture;//holds the profile picture selected by the user
    
    public loginRegister() {
        initComponents();/* Creates components in method below */
        
        setLocationRelativeTo(null);//move to centre of the page
        getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, coloursObject.getTitlePanelColour()));//set the border of the jframe
        
        titlebar tb = new titlebar(loginRegister.this, null);//create the titlebar class with the JFrame as the parameter
        tb.applyTitleBarSettings(titlebar);//call to apply styles and settings to the titlebar
        
        applySettings();//apply any settings such as button reactions
        applyLoginCommands();//apply component commands for the login jpanel
        applyRegisterCommands();//apply components for the register jpanel
        
        FlowLayout flowLayout = new FlowLayout(FlowLayout.RIGHT);//create a right align flow layout
        titlebar.setLayout(flowLayout);//add the flow layout to the title bar
        
        //set the login panel to be the default card shown
        changeCard("loginCard");
    }
    
    //method is called to check the users input information against the database table that has been retrieved
    public void verifyLoginAttempt(){
        
        try{
            //attempt connection to the database using the provided information
            System.out.println("companyName:" + companyName + " username:" + username + " password:" + password);
            Connection conn = DriverManager.getConnection(url + companyName, username, password);
            Statement state = conn.createStatement();//create a statement
            ResultSet nameCheck = state.executeQuery("SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = '" + companyName + "'"); 
            
            String nameFound = "";//create a variable to hold the name found in the returned result set
            while(nameCheck.next()){//read the data in the result set
                nameFound = nameCheck.getString(1);//set variable to contain the data in the result set
            }
            if(companyName.equals(nameFound)){//if the users entered db name is equal to an existing one
                System.out.println("ITS A MATCH");
                loggedIn = true;//set the logged in boolean to true
            }else{
                System.out.println("ITS NOT A MATCH");
            }
            
            state.close();//close the statement
            conn.close();
            
        }catch(Exception exc){//catch an incorrect loggin attempt
            System.out.println("ERROR WHEN LOGGING IN: " + exc);
            loginErrorLabel.setText("There was an error when logging in");
            //exc.printStackTrace();//print the exception
        }
    }
    
    //method is called to register an account with a company that already exists
    public void verifyRegisterAttempt(String company, String user, String password, String retypedPassword){
        try{
            Boolean match = checkPasswordMatch();//create a boolean holding the verification of the passwords matching
            if(!match){//if the match boolean returns a false value (so passwords didnt match)
                registerErrorLabel.setText("Passwords didn't match");
            }else{
                Connection conn = DriverManager.getConnection(url, "registerAccount", null);//connect using the register account which can only view database names
                
                //check that the database requested exists
                System.out.println("register: attempting to connect to the database");
                Statement state = conn.createStatement();//create a statement
                ResultSet nameCheck = state.executeQuery("SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = '" + company + "'"); 

                String nameFound = "";//create a variable to hold the name found in the returned result set
                while(nameCheck.next()){//read the data in the result set
                    nameFound = nameCheck.getString(1);//set variable to contain the data in the result set
                }
                if(company.equals(nameFound)){//if the users entered db name is equal to an existing one
                    System.out.println("register: DB was found");
                    
                    //check to see if the username entered already exists in the database
                    Statement checkForDuplicateUserstatement = conn.createStatement();//create a statement for checking is the enterd username already exists
                    ResultSet rs = checkForDuplicateUserstatement.executeQuery("SELECT User FROM mysql.user");//show all the users in the mysql user accounts
                    Boolean userAlreadyExists = false;//create boolean to hold whether or not the entered username already exists
                    while(rs.next()){//while there is something to read in the result set
                        String readUsername = rs.getString("User");//get the username being read
                        if(user.equals(readUsername)){//if the username entered by the user alreayd exists in the result set
                            userAlreadyExists = true;//set the boolean to true
                        }
                    }
                    if(userAlreadyExists){//if the boolean which states the username already existing is true then stop the register process
                        System.out.println("register: username entered already exists");
                        registerErrorLabel.setText("user already exists");
                    }else{
                        System.out.println("register: username doesn't already exist");
                        
                        //create the user in the mysql user accounts list
                        Statement createUserStatement = conn.createStatement();//create a statement
                        createUserStatement.executeUpdate("CREATE USER '" + user + "'@localhost IDENTIFIED BY'" + password +"'");//create a new user in the mysql user list
                        System.out.println("register: created user account with a password");

                        //assign mysql priviledges to the newly created user so they have basic access to their companys database
                        Statement grantPriviledgesStatement = conn.createStatement();//create a statement
                        grantPriviledgesStatement.executeUpdate("GRANT INSERT,SELECT,UPDATE ON " + company + ".* TO '" + user + "'@localhost");//grant priviledges to the newly created user
                        System.out.println("register: graned priviledges to new user");
                        
                        //get a list of User IDs that exist in the companys user table, do this so a unique one can be generated
                        System.out.println("register: generating unique user id");
                        Statement getListOfUserIDs = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);//create a statement to obtain the list of pre-exisiting user IDs
                        ResultSet userListRS = getListOfUserIDs.executeQuery("SELECT UserID from " + company + ".user");
                        String generatedID = "";
                        if(userListRS != null){
                            userListRS.last();//move cursor to the last row
                            
                            int resultSetSize = userListRS.getRow();//get the id of the last row (ie the number of rows)
                            ArrayList<String> arrayListofUsers = new ArrayList<String>();//create arraylist for holding the list of users
                            
                            userListRS.beforeFirst();//move the curor to the first position
                            while(userListRS.next()){//while there is more from the result set to read
                                String readUsername = userListRS.getString("UserID");//get the UserID currently being read
                                arrayListofUsers.add(readUsername);//add the currently read UserID to the arraylist of users
                            }
                            
                            Boolean idIsUnique = false;//boolean represents if the generated ID is unique
                            while(!idIsUnique){//while the unique ID boolean is false
                                idIsUnique = true;//set variable to true because right now it is unique
                                generatedID = generateUserID();//generate an unique ID by calling the method to do so
                                
                                for(int i = 0; i < arrayListofUsers.size(); i++){//loops for the number of userIDs that have been added to the arraylist
                                    String currentUserID = arrayListofUsers.get(i);//string variable holds the currently viewed UserID in the arraylist
                                    
                                    if(generatedID.equals(currentUserID)){//if the username entered by the user already exists in the result set
                                        idIsUnique = false;//set the boolean to false
                                    }
                                }
                                userListRS.beforeFirst();//reset the result set cursor for the next attempt at an unique ID
                            }
                        }
                        System.out.println("register: unique id successfully generated, it is " + generatedID);
                        
                        //add the user account to the companys database users table
                        System.out.println("register: adding user details into company database");
                        Statement addUserToTableStatement = conn.createStatement();//create a statement
                        addUserToTableStatement.executeUpdate("INSERT INTO " + company + ".user(UserID, Name) VALUES('" + generatedID + "', '" + user + "')");//insert the new user into the database user table
                        System.out.println("register: user has been added to the user table");

                        createUserStatement.close();//close the statement
                        grantPriviledgesStatement.close();//close the statement
                        addUserToTableStatement.close();//close the statement

                        //change to login jpanel due to the successful registration
                        containerJPanel.removeAll();
                        containerJPanel.add(loginJPanel);
                        containerJPanel.repaint();
                        containerJPanel.revalidate();
                    }
                    checkForDuplicateUserstatement.close();//close the statement
                    
                    System.out.println("register: the user has been fully registered into the system");
                    
                }else{
                    System.out.println("DB wasn't found");
                }
                state.close();//close the statement
                conn.close();//close the statement
            }
            
        }catch(Exception exc){//catch an incorrect loggin attempt
            System.out.println("register: caught an error " + exc);
            registerErrorLabel.setText("There was an error when registering");
            //exc.printStackTrace();//print the exception
        }
    }
    
    //method generates a unique 5 letter ID
    public String generateUserID(){
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";//create a string holding all the letters that the unique ID could contain
        StringBuilder sb = new StringBuilder(5);//create a new string builder with length of 5
        for (int i = 0; i < 5; i++) {//loop for 5 times
  
            // generate a random number between 
            // 0 to AlphaNumericString variable length 
            int index = (int)(AlphaNumericString.length() * Math.random()); 
  
            // add Character one by one in end of sb 
            sb.append(AlphaNumericString.charAt(index)); 
        } 
  
        return sb.toString(); 
    }
    
    public void applySettings(){
        containerJPanel.add(loginJPanel, "loginCard");//add the login jpanel to the card layout in the container jpanel
        containerJPanel.add(registerJPanel, "registerCard");//add the register jpanel to the card layout in the container jpanel
    }
    
    //apply commands on how components from login JPanel should react to user input
    public void applyLoginCommands(){
        //if the user has moved the mouse while attempting to type in the textfields (if the user left the textfield empty) then reapply the temp text
        loginJPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {//when hovering over mouse
                if(companyIDTextField.getText().equals("")){//if the company tf is empty
                    companyIDTextField.setText("Company Name");//assign the original text
                }
                if(usernameTextField.getText().equals("")){//if the username tf is empty
                    usernameTextField.setText("Username");//assign the original text
                }
                if(passwordTextField.getText().equals("")){//if the password tf is empty
                    passwordTextField.setText("Password");//assign the original text
                }
            }
        });
        //code below allows for the text colour to change when a mouse hover is applied
        buttonStyles.applyButtonStyles(submitButton);
        
        //code below allows for the text colour to change when a mouse hover is applied
        registerHereButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {//when hovering over mouse
                registerHereButton.setForeground(coloursObject.getButtonTextHoverColour());//the colour of the text when hovered over
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {//when cursor stops hovering over the mouse
                registerHereButton.setForeground(coloursObject.getButtonTextColour());//the colour of the text when the cursor is removed
            }
        });
        //code below sets the text to blank in textfield when the user clicks on it
        companyIDTextField.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                companyIDTextField.setText("");//set the text to blank
                loginErrorLabel.setText("");//set the error message label to blank
            }
        });
        //code below sets the text to blank in textfield when the user clicks on it
        usernameTextField.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                usernameTextField.setText("");//set the text to blank
                loginErrorLabel.setText("");//set the error message label to blank
            }
        });
        //code below sets the text to blank in textfield when the user clicks on it
        passwordTextField.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                passwordTextField.setText("");//set the text to blank
                loginErrorLabel.setText("");//set the error message label to blank
            }
        });
    }
    
    //apply commands on how components from register JPanel should react to user input
    public void applyRegisterCommands(){
        //if the user has moved the mouse while attempting to type in the textfields (if the user left the textfield empty) then reapply the temp text
        registerJPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {//when hovering over with the mouse
                if(registerCompanyIDTextField.getText().equals("")){//if the company tf is empty
                    registerCompanyIDTextField.setText("Company Name");//assign the original text
                }
                if(registerUsernameTextField.getText().equals("")){//if the username tf is empty
                    registerUsernameTextField.setText("Username");//assign the original text
                }
                if(registerPasswordTextField.getText().equals("")){//if the password tf is empty
                    registerPasswordTextField.setText("Password");//assign the original text
                }
                if(retypePasswordTextField.getText().equals("")){//if the password tf is empty
                    retypePasswordTextField.setText("Retype Password");//assign the original text
                }
            }
        });
        //code below allows for the text colour to change when a mouse hover is applied
        buttonStyles.applyButtonStyles(registerSubmitButton);
        
        //code below allows for the text colour to change when a mouse hover is applied
        loginHereButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {//when hovering over mouse
                loginHereButton.setForeground(coloursObject.getButtonTextHoverColour());//the colour of the text when hovered over
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {//when cursor stops hovering over the mouse
                loginHereButton.setForeground(coloursObject.getButtonTextColour());//the colour of the text when the cursor is removed
            }
        });
        //code below sets the text to blank in textfield when the user clicks on it
        registerCompanyIDTextField.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                registerCompanyIDTextField.setText("");//set the text to blank
                registerErrorLabel.setText("");//set the error message label to blank
            }
        });
        //code below sets the text to blank in textfield when the user clicks on it
        registerUsernameTextField.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                registerUsernameTextField.setText("");//set the text to blank
                registerErrorLabel.setText("");//set the error message label to blank
            }
        });
        //code below sets the text to blank in textfield when the user clicks on it
        registerPasswordTextField.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                registerPasswordTextField.setText("");//set the text to blank
                registerErrorLabel.setText("");//set the error message label to blank
            }
        });
        //code below sets the text to blank in textfield when the user clicks on it
        retypePasswordTextField.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                retypePasswordTextField.setText("");//set the text to blank
                registerErrorLabel.setText("");//set the error message label to blank
            }
        });
    }
    
    //method is called to check the passwords match
    public Boolean checkPasswordMatch(){
        String pass1 = registerPasswordTextField.getText();//get the password in the first textfield
        String pass2 = retypePasswordTextField.getText();//get the retyped password in the second textfield
        
        if(pass1.equals(pass2)){//if the two entries are equal then return true
            return true;//return treu boolean value
        }else{
            return false;//return false boolean value
        }
    }
    
    //used to change the card on the cardLayout contained within the content panel
    private void changeCard(String str){
        CardLayout card = (CardLayout)(containerJPanel.getLayout());//get the card layout from the content panel
        card.show(containerJPanel, str);//show the card with the matching string i.e panel
        activeCard = str;
    }
      
    public String getUsername(){
        return username;
    }
        
    public Boolean checkLogin(){
        return loggedIn;
    }
    
    public String getCompanyID(){
        return companyName;
    }
    
    public String getPassword(){
        return password;
    }  

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        registerJPanel = new javax.swing.JPanel();
        registerCompanyIDTextField = new javax.swing.JTextField();
        registerSubmitButton = new javax.swing.JButton();
        registerUsernameTextField = new javax.swing.JTextField();
        registerPasswordTextField = new javax.swing.JTextField();
        userImageLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        loginHereButton = new javax.swing.JButton();
        retypePasswordTextField = new javax.swing.JTextField();
        registerErrorLabel = new javax.swing.JLabel();
        loginJPanel = new javax.swing.JPanel();
        companyIDTextField = new javax.swing.JTextField();
        submitButton = new javax.swing.JButton();
        usernameTextField = new javax.swing.JTextField();
        passwordTextField = new javax.swing.JTextField();
        userImageLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        registerHereButton = new javax.swing.JButton();
        loginErrorLabel = new javax.swing.JLabel();
        getUserImageButton = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        containerJPanel = new javax.swing.JPanel();
        titlebar = new javax.swing.JPanel();

        registerJPanel.setBackground(coloursObject.getMenuPanelColour()
        );
        registerJPanel.setMaximumSize(new java.awt.Dimension(300, 416));
        registerJPanel.setMinimumSize(new java.awt.Dimension(300, 416));
        registerJPanel.setPreferredSize(new java.awt.Dimension(300, 416));

        registerCompanyIDTextField.setBackground(coloursObject.getContentPanelColour()
        );
        registerCompanyIDTextField.setFont(new java.awt.Font("Microsoft Tai Le", 0, 11)); // NOI18N
        registerCompanyIDTextField.setForeground(coloursObject.getTextColour()
        );
        registerCompanyIDTextField.setText("Company Name");
        registerCompanyIDTextField.setBorder(new javax.swing.border.LineBorder(coloursObject.getContentPanelColour(), 3, true));
        registerCompanyIDTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerCompanyIDTextFieldActionPerformed(evt);
            }
        });

        registerSubmitButton.setBackground(coloursObject.getContentPanelColour()
        );
        registerSubmitButton.setFont(new java.awt.Font("Microsoft Tai Le", 1, 11)); // NOI18N
        registerSubmitButton.setForeground(coloursObject.getButtonTextColour()
        );
        registerSubmitButton.setText("Submit");
        registerSubmitButton.setBorder(null);
        registerSubmitButton.setContentAreaFilled(false);
        registerSubmitButton.setFocusPainted(false);
        registerSubmitButton.setFocusable(false);
        registerSubmitButton.setOpaque(true);
        registerSubmitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerSubmitButtonActionPerformed(evt);
            }
        });

        registerUsernameTextField.setBackground(coloursObject.getContentPanelColour()
        );
        registerUsernameTextField.setFont(new java.awt.Font("Microsoft Tai Le", 0, 11)); // NOI18N
        registerUsernameTextField.setForeground(coloursObject.getTextColour()
        );
        registerUsernameTextField.setText("Username");
        registerUsernameTextField.setBorder(new javax.swing.border.LineBorder(coloursObject.getContentPanelColour(), 3, true));

        registerPasswordTextField.setBackground(coloursObject.getContentPanelColour()
        );
        registerPasswordTextField.setFont(new java.awt.Font("Microsoft Tai Le", 0, 11)); // NOI18N
        registerPasswordTextField.setForeground(coloursObject.getTextColour()
        );
        registerPasswordTextField.setText("Password");
        registerPasswordTextField.setBorder(new javax.swing.border.LineBorder(coloursObject.getContentPanelColour(), 3, true));
        registerPasswordTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerPasswordTextFieldActionPerformed(evt);
            }
        });

        userImageLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\ewand\\OneDrive\\Dissertation\\Development\\Images\\userIconSmall.png")); // NOI18N

        jLabel2.setFont(new java.awt.Font("Microsoft Tai Le", 1, 24)); // NOI18N
        jLabel2.setForeground(coloursObject.getTextColour()
        );
        jLabel2.setText("REGISTER");

        loginHereButton.setBackground(new java.awt.Color(42, 45, 53));
        loginHereButton.setFont(new java.awt.Font("Microsoft Tai Le", 1, 10)); // NOI18N
        loginHereButton.setForeground(new java.awt.Color(112, 118, 127));
        loginHereButton.setText("Already have an account? Login Here");
        loginHereButton.setBorder(null);
        loginHereButton.setBorderPainted(false);
        loginHereButton.setContentAreaFilled(false);
        loginHereButton.setFocusPainted(false);
        loginHereButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginHereButtonActionPerformed(evt);
            }
        });

        retypePasswordTextField.setBackground(coloursObject.getContentPanelColour()
        );
        retypePasswordTextField.setFont(new java.awt.Font("Microsoft Tai Le", 0, 11)); // NOI18N
        retypePasswordTextField.setForeground(coloursObject.getTextColour()
        );
        retypePasswordTextField.setText("Retype Password");
        retypePasswordTextField.setBorder(new javax.swing.border.LineBorder(coloursObject.getContentPanelColour(), 3, true));
        retypePasswordTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                retypePasswordTextFieldActionPerformed(evt);
            }
        });

        registerErrorLabel.setFont(new java.awt.Font("Microsoft Tai Le", 1, 10)); // NOI18N
        registerErrorLabel.setForeground(new java.awt.Color(197, 55, 55));

        javax.swing.GroupLayout registerJPanelLayout = new javax.swing.GroupLayout(registerJPanel);
        registerJPanel.setLayout(registerJPanelLayout);
        registerJPanelLayout.setHorizontalGroup(
            registerJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, registerJPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(loginHereButton)
                .addGap(60, 60, 60))
            .addGroup(registerJPanelLayout.createSequentialGroup()
                .addGroup(registerJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(registerJPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2))
                    .addGroup(registerJPanelLayout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(userImageLabel1))
                    .addGroup(registerJPanelLayout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(registerJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(registerPasswordTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(registerCompanyIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(registerUsernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(retypePasswordTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(registerErrorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(registerJPanelLayout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addComponent(registerSubmitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        registerJPanelLayout.setVerticalGroup(
            registerJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(registerJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(userImageLabel1)
                .addGap(18, 18, 18)
                .addComponent(registerCompanyIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(registerUsernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(registerPasswordTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(retypePasswordTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(loginHereButton, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(registerSubmitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(registerErrorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        loginJPanel.setBackground(coloursObject.getMenuPanelColour()
        );
        loginJPanel.setMaximumSize(new java.awt.Dimension(300, 450));
        loginJPanel.setMinimumSize(new java.awt.Dimension(300, 450));

        companyIDTextField.setBackground(coloursObject.getContentPanelColour()
        );
        companyIDTextField.setFont(new java.awt.Font("Microsoft Tai Le", 0, 11)); // NOI18N
        companyIDTextField.setForeground(coloursObject.getTextColour()
        );
        companyIDTextField.setText("Company Name");
        companyIDTextField.setBorder(new javax.swing.border.LineBorder(coloursObject.getContentPanelColour(), 3, true));
        companyIDTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                companyIDTextFieldActionPerformed(evt);
            }
        });

        submitButton.setBackground(coloursObject.getContentPanelColour()
        );
        submitButton.setFont(new java.awt.Font("Microsoft Tai Le", 1, 11)); // NOI18N
        submitButton.setForeground(coloursObject.getButtonTextColour());
        submitButton.setText("Submit");
        submitButton.setBorder(null);
        submitButton.setFocusPainted(false);
        submitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitButtonActionPerformed(evt);
            }
        });

        usernameTextField.setBackground(coloursObject.getContentPanelColour()
        );
        usernameTextField.setFont(new java.awt.Font("Microsoft Tai Le", 0, 11)); // NOI18N
        usernameTextField.setForeground(coloursObject.getTextColour()
        );
        usernameTextField.setText("Username");
        usernameTextField.setBorder(new javax.swing.border.LineBorder(coloursObject.getContentPanelColour(), 3, true));

        passwordTextField.setBackground(coloursObject.getContentPanelColour()
        );
        passwordTextField.setFont(new java.awt.Font("Microsoft Tai Le", 0, 11)); // NOI18N
        passwordTextField.setForeground(coloursObject.getTextColour()
        );
        passwordTextField.setText("Password");
        passwordTextField.setBorder(new javax.swing.border.LineBorder(coloursObject.getContentPanelColour(), 3, true));
        passwordTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordTextFieldActionPerformed(evt);
            }
        });

        userImageLabel.setIcon(new javax.swing.ImageIcon("C:\\Users\\ewand\\OneDrive\\Dissertation\\Development\\Images\\userIconSmall.png")); // NOI18N

        jLabel1.setFont(new java.awt.Font("Microsoft Tai Le", 1, 24)); // NOI18N
        jLabel1.setForeground(coloursObject.getTextColour()
        );
        jLabel1.setText("LOGIN");

        registerHereButton.setBackground(new java.awt.Color(42, 45, 53));
        registerHereButton.setFont(new java.awt.Font("Microsoft Tai Le", 1, 10)); // NOI18N
        registerHereButton.setForeground(new java.awt.Color(112, 118, 127));
        registerHereButton.setText("Havent got an account yet? Register Here");
        registerHereButton.setBorder(null);
        registerHereButton.setBorderPainted(false);
        registerHereButton.setContentAreaFilled(false);
        registerHereButton.setFocusPainted(false);
        registerHereButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerHereButtonActionPerformed(evt);
            }
        });

        loginErrorLabel.setFont(new java.awt.Font("Microsoft Tai Le", 1, 10)); // NOI18N
        loginErrorLabel.setForeground(new java.awt.Color(197, 55, 55));

        javax.swing.GroupLayout loginJPanelLayout = new javax.swing.GroupLayout(loginJPanel);
        loginJPanel.setLayout(loginJPanelLayout);
        loginJPanelLayout.setHorizontalGroup(
            loginJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginJPanelLayout.createSequentialGroup()
                .addGroup(loginJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(loginJPanelLayout.createSequentialGroup()
                        .addGroup(loginJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(loginJPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1))
                            .addGroup(loginJPanelLayout.createSequentialGroup()
                                .addGap(77, 77, 77)
                                .addComponent(userImageLabel))
                            .addGroup(loginJPanelLayout.createSequentialGroup()
                                .addGap(46, 46, 46)
                                .addGroup(loginJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(loginJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(passwordTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(companyIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(usernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(loginJPanelLayout.createSequentialGroup()
                                        .addGap(59, 59, 59)
                                        .addComponent(submitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(loginJPanelLayout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(registerHereButton)))))
                        .addGap(0, 39, Short.MAX_VALUE))
                    .addGroup(loginJPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(loginErrorLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        loginJPanelLayout.setVerticalGroup(
            loginJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(userImageLabel)
                .addGap(18, 18, 18)
                .addComponent(companyIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(usernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(passwordTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(registerHereButton, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(submitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(loginErrorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(59, Short.MAX_VALUE))
        );

        getUserImageButton.setText("Search");
        getUserImageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getUserImageButtonActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Microsoft Tai Le", 0, 14)); // NOI18N
        jLabel7.setForeground(coloursObject.getTextColour()
        );
        jLabel7.setText("Profile Picture:");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login/Register");
        setMaximumSize(new java.awt.Dimension(300, 450));
        setMinimumSize(new java.awt.Dimension(300, 450));
        setUndecorated(true);
        setResizable(false);

        containerJPanel.setLayout(new java.awt.CardLayout());

        titlebar.setBackground(coloursObject.getTitlePanelColour()
        );
        titlebar.setMaximumSize(new java.awt.Dimension(32767, 34));
        titlebar.setMinimumSize(new java.awt.Dimension(100, 34));
        titlebar.setPreferredSize(new java.awt.Dimension(0, 34));

        javax.swing.GroupLayout titlebarLayout = new javax.swing.GroupLayout(titlebar);
        titlebar.setLayout(titlebarLayout);
        titlebarLayout.setHorizontalGroup(
            titlebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        titlebarLayout.setVerticalGroup(
            titlebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 34, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titlebar, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
            .addComponent(containerJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(titlebar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(containerJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void passwordTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordTextFieldActionPerformed

    private void companyIDTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_companyIDTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_companyIDTextFieldActionPerformed

    private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButtonActionPerformed
        companyName = companyIDTextField.getText().toLowerCase();
        username = usernameTextField.getText();
        password = passwordTextField.getText();
        verifyLoginAttempt();
    }//GEN-LAST:event_submitButtonActionPerformed

    private void registerHereButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerHereButtonActionPerformed
        containerJPanel.removeAll();
        containerJPanel.add(registerJPanel);        
        containerJPanel.repaint();
        containerJPanel.revalidate();
    }//GEN-LAST:event_registerHereButtonActionPerformed

    private void registerCompanyIDTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerCompanyIDTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_registerCompanyIDTextFieldActionPerformed

    private void registerSubmitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerSubmitButtonActionPerformed
        String company = registerCompanyIDTextField.getText().toLowerCase();
        String user = registerUsernameTextField.getText();
        String password = registerPasswordTextField.getText();
        String retypedPassword = retypePasswordTextField.getText();
        
        verifyRegisterAttempt(company, user, password, retypedPassword);
    }//GEN-LAST:event_registerSubmitButtonActionPerformed

    private void registerPasswordTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerPasswordTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_registerPasswordTextFieldActionPerformed

    private void loginHereButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginHereButtonActionPerformed
        containerJPanel.removeAll();
        containerJPanel.add(loginJPanel);
        containerJPanel.repaint();
        containerJPanel.revalidate();
    }//GEN-LAST:event_loginHereButtonActionPerformed

    private void retypePasswordTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_retypePasswordTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_retypePasswordTextFieldActionPerformed

    private void getUserImageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getUserImageButtonActionPerformed
        JFileChooser profilePictureFileChooser = new JFileChooser("d:", FileSystemView.getFileSystemView());//create a JFileChooser to open an image file
        profilePictureFileChooser.showSaveDialog(null);// Open the save dialog

        File picture = profilePictureFileChooser.getSelectedFile();//get the selected image
        profilePicture = picture;//update the global var holding the file
    }//GEN-LAST:event_getUserImageButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField companyIDTextField;
    private javax.swing.JPanel containerJPanel;
    private javax.swing.JButton getUserImageButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel loginErrorLabel;
    private javax.swing.JButton loginHereButton;
    private javax.swing.JPanel loginJPanel;
    private javax.swing.JTextField passwordTextField;
    private javax.swing.JTextField registerCompanyIDTextField;
    private javax.swing.JLabel registerErrorLabel;
    private javax.swing.JButton registerHereButton;
    private javax.swing.JPanel registerJPanel;
    private javax.swing.JTextField registerPasswordTextField;
    private javax.swing.JButton registerSubmitButton;
    private javax.swing.JTextField registerUsernameTextField;
    private javax.swing.JTextField retypePasswordTextField;
    private javax.swing.JButton submitButton;
    private javax.swing.JPanel titlebar;
    private javax.swing.JLabel userImageLabel;
    private javax.swing.JLabel userImageLabel1;
    private javax.swing.JTextField usernameTextField;
    // End of variables declaration//GEN-END:variables
}
