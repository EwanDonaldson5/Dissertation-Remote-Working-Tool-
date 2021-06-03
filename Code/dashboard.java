
import Functions.colours;
import Functions.accessConfigFile;
import Styles.buttons;
import Styles.titlebar;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * Created 26/10/2020
 * Ewan Donaldson
 */
public class dashboard extends javax.swing.JFrame {
    
    String dashboardReaction = "";//string contains the next step the user wants to take after the dashboard
    ArrayList<String> settingsList = new ArrayList<String>();//arraylist holds the settings from the config file
    
    accessConfigFile accessConfig = new accessConfigFile();//create an object for accessing the config file
    String colourModeSettingValue = accessConfig.searchForSettingsValue("darkModeEnabled");
    colours coloursObject = new colours(colourModeSettingValue);//create an object for accessing different colours used in the program
    
    int jframeWidth = 500;//var holds the frames width
    int jframeHeight = 300;//var holds the frames height
    int jframeXLocation;//var will hold the x location
    int jframeYLocation;//var will hold the y location
            
    public dashboard() {
        initComponents();/* Creates components in method below */
        
        checkAndApplySettings();//method checks for and applies any settings
        
        titlebar tb = new titlebar(dashboard.this, null);//create the titlebar class with the JFrame as the parameter
        tb.applyTitleBarSettings(titlebar);//call to apply styles and settings to the titlebar
        
        setLocationRelativeTo(null);//move to centre of the page
        getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, coloursObject.getTitlePanelColour()));//set the border of the jframe
        
        setLayouts();//assign layouts to jpanels
        applyCommands();//method applys settings and commands on how components should interact with the user
    }
    
    //method is called to check the settings to see if the information panel should be displayed or not
    public void checkAndApplySettings(){
        settingsList = readFile();//call readFile method to obtain a copy of all settings in an array list
        String value = searchForSettingsValue("showDashInfo");//call method to find the corresponding value to the required setting
        if(value.equals("false")){//if the setting for displaying the information panel is false
            informationPanel.setVisible(false);//hide the display panel
        }
    }
    
    //apply action and mouse listener to components to become responsive
    public void applyCommands(){
        //create company button mouse listener
        //code below allows for the text colour to change when a mouse hover is applied
        createCompanyButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {//when hovering over mouse
                createCompanyButton.setForeground(coloursObject.getButtonTextColour());//the colour of the text when hovered over
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {//when cursor stops hovering over the mouse
                createCompanyButton.setForeground(coloursObject.getButtonTextHoverColour());//the colour of the text when the cursor is removed
            }
        });

        //register and login button mouse listener
        //code below allows for the text colour to change when a mouse hover is applied
        registerAndLoginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {//when hovering over mouse
                registerAndLoginButton.setForeground(coloursObject.getButtonTextColour());//the colour of the text when hovered over
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {//when cursor stops hovering over the mouse
                registerAndLoginButton.setForeground(coloursObject.getButtonTextHoverColour());//the colour of the text when the cursor is removed
            }
        });
    }
        
    //method assigns layouts to JPanels
    public void setLayouts(){
        FlowLayout flowLayout = new FlowLayout(FlowLayout.RIGHT);//create a right align flow layout
        titlebar.setLayout(flowLayout);//add the flow layout to the title bar
    }
    
    //method is called ot get the choice of the user
    public String getUserChoice(){
        return dashboardReaction;//return the string
    }
    
    //method is called to read the variables text file
    public static ArrayList<String> readFile(){
        System.out.println();//leave a blank line to aid with visual debugging
        System.out.println("settings:-READING THE CONFIG FILE");
        
        ArrayList<String> listOfSettings = new ArrayList<String>();//create array list to hold the settings
                
        try {
            File myObj = new File("config.txt");//create a file object
            Scanner myReader = new Scanner(myObj);//create a scanner object to read from the text file
            
            ArrayList<String> dataList = new ArrayList<String>();
            while (myReader.hasNextLine()) {//while the scanner has something to read
                String data = myReader.nextLine();//hold the data read in a string variable
                System.out.println("settings: reading line from file");
                System.out.println("settings: found " + data);
                dataList.add(data);//add the string from the text file to the list of strings
            }
            
            listOfSettings = dataList;//set the settings list to the list of retrieved data
            
            myReader.close();//close the scanner object
        } catch (FileNotFoundException e) {
            System.out.println("settings: error occurred while reading file");
            e.printStackTrace();
        }
        
        return listOfSettings;
    }
    
    //method searches for the given setting and then returns the value
    public String searchForSettingsValue(String requestedSetting){
        System.out.println();//leave a blank line to aid with visual debugging
        System.out.println("settings:-SEARCHING FOR A SETTING FROM LIST");
        System.out.println("settings: requesting " + requestedSetting + " from list");
        
        int listSize = settingsList.size();
        String settingValue = "";//string variable holds the value being returned from the searched setting
        
        //loops to read through all the records in the settings array list
        //the record is copied from the arraylist and is then split into two parts at the '=' sign
        //two parts are then put into an array
        //then if the requested setting is found, the corresponding value is then returned
        for(int i = 0; i < listSize; i++){//loop for the number of settings in the array list
            String data = settingsList.get(i);//string holds the individual string from the settings list
            String[] parts = data.split("=");//string array holds the two seperate parts of the setting record from the text file
            
            if(requestedSetting.equals(parts[0])){//if the requested setting is equal to the first part of the read setting
                System.out.println("settings: found the requested setting");
                settingValue = parts[1];//set the setting value variable to the second part of the array
                System.out.println("settings: obtained the value of " + settingValue);
            }
        }
        return settingValue;//return the value from the settings
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
        dashboardLabel = new javax.swing.JLabel();
        createCompanyButton = new javax.swing.JButton();
        registerAndLoginButton = new javax.swing.JButton();
        informationPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        titlebar = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Remote Working Tool");
        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMaximumSize(new java.awt.Dimension(500, 300));
        setMinimumSize(new java.awt.Dimension(500, 300));
        setUndecorated(true);

        contentPanel.setBackground(coloursObject.getMenuPanelColour());

        dashboardLabel.setFont(new java.awt.Font("Microsoft Tai Le", 1, 24)); // NOI18N
        dashboardLabel.setForeground(coloursObject.getTextColour()
        );
        dashboardLabel.setText("DASHBOARD");

        createCompanyButton.setBackground(coloursObject.getContentPanelColour());
        createCompanyButton.setFont(new java.awt.Font("Microsoft Tai Le", 1, 11)); // NOI18N
        createCompanyButton.setForeground(coloursObject.getTextColour()
        );
        createCompanyButton.setText("Create New Company");
        createCompanyButton.setBorder(null);
        createCompanyButton.setContentAreaFilled(false);
        createCompanyButton.setFocusPainted(false);
        createCompanyButton.setFocusable(false);
        createCompanyButton.setOpaque(true);
        createCompanyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createCompanyButtonActionPerformed(evt);
            }
        });

        registerAndLoginButton.setBackground(coloursObject.getContentPanelColour());
        registerAndLoginButton.setFont(new java.awt.Font("Microsoft Tai Le", 1, 11)); // NOI18N
        registerAndLoginButton.setForeground(coloursObject.getTextColour()
        );
        registerAndLoginButton.setText("Login / Register");
        registerAndLoginButton.setBorder(null);
        registerAndLoginButton.setContentAreaFilled(false);
        registerAndLoginButton.setFocusPainted(false);
        registerAndLoginButton.setFocusable(false);
        registerAndLoginButton.setOpaque(true);
        registerAndLoginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerAndLoginButtonActionPerformed(evt);
            }
        });

        informationPanel.setBackground(coloursObject.getContentPanelColour());

        jLabel1.setFont(new java.awt.Font("Microsoft Tai Le", 1, 12)); // NOI18N
        jLabel1.setForeground(coloursObject.getTextColour()
        );
        jLabel1.setText("Information:");

        jLabel2.setFont(new java.awt.Font("Microsoft Tai Le", 0, 12)); // NOI18N
        jLabel2.setForeground(coloursObject.getTextColour()
        );
        jLabel2.setText("When creating a company the name is not case sensitive");

        jLabel3.setFont(new java.awt.Font("Microsoft Tai Le", 0, 12)); // NOI18N
        jLabel3.setForeground(coloursObject.getTextColour()
        );
        jLabel3.setText("Any company name with a space will be replaced with a -");

        jLabel4.setFont(new java.awt.Font("Microsoft Tai Le", 1, 12)); // NOI18N
        jLabel4.setForeground(coloursObject.getTextColour()
        );
        jLabel4.setText("Updates:");

        jLabel5.setFont(new java.awt.Font("Microsoft Tai Le", 0, 12)); // NOI18N
        jLabel5.setForeground(coloursObject.getTextColour()
        );
        jLabel5.setText("When creating a company the name is not case sensitive");

        jLabel6.setFont(new java.awt.Font("Microsoft Tai Le", 0, 12)); // NOI18N
        jLabel6.setForeground(coloursObject.getTextColour()
        );
        jLabel6.setText("Any company name with a space will be replaced with a -");

        jLabel7.setFont(new java.awt.Font("Microsoft Tai Le", 0, 12)); // NOI18N
        jLabel7.setForeground(coloursObject.getTextColour()
        );
        jLabel7.setText("To apply settings you must restart the app");

        javax.swing.GroupLayout informationPanelLayout = new javax.swing.GroupLayout(informationPanel);
        informationPanel.setLayout(informationPanelLayout);
        informationPanelLayout.setHorizontalGroup(
            informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(informationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(11, Short.MAX_VALUE))
        );
        informationPanelLayout.setVerticalGroup(
            informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(informationPanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addGap(42, 42, 42)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout contentPanelLayout = new javax.swing.GroupLayout(contentPanel);
        contentPanel.setLayout(contentPanelLayout);
        contentPanelLayout.setHorizontalGroup(
            contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(createCompanyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(registerAndLoginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dashboardLabel))
                .addGap(18, 18, 18)
                .addComponent(informationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        contentPanelLayout.setVerticalGroup(
            contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(informationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(contentPanelLayout.createSequentialGroup()
                        .addComponent(dashboardLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(registerAndLoginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(createCompanyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 116, Short.MAX_VALUE)))
                .addContainerGap())
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
            .addComponent(contentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(titlebar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void registerAndLoginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerAndLoginButtonActionPerformed
        dashboardReaction = "login or register";//set the user reaction string to login or register
    }//GEN-LAST:event_registerAndLoginButtonActionPerformed

    private void createCompanyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createCompanyButtonActionPerformed
        dashboardReaction = "create a company";//set the user reaction string to create a company
    }//GEN-LAST:event_createCompanyButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel contentPanel;
    private javax.swing.JButton createCompanyButton;
    private javax.swing.JLabel dashboardLabel;
    private javax.swing.JPanel informationPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JButton registerAndLoginButton;
    private javax.swing.JPanel titlebar;
    // End of variables declaration//GEN-END:variables
}
