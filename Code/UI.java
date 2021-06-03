import Functions.colours;
import Functions.accessConfigFile;
import Features.calendar;
import Features.notifications;
import Features.settings;
import Features.chat;
import Features.dropBox;
import Features.feedback;
import Features.productivity;
import Features.task;
import Functions.Ping;
import Functions.UpdateDatabase;
import Functions.database;
import Styles.titlebar;
import TaskFunctionality.addNewTask;
import TaskFunctionality.specificTask;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class UI extends javax.swing.JFrame {
    ArrayList<String[][]> tables = new ArrayList<String[][]>();
    
    String loggedInUser;
    Boolean showTasks = false;
    Boolean isFullScreen = false;
    
    String activeCard;//string contains the text of the currently shown jpanel
    
    String databaseName;
    String userName;
    String password;
    
    database dbs;//database object created in the remoteWorkingTool class
    accessConfigFile accessConfig = new accessConfigFile();//create an object for accessing the config file
    String colourModeSettingValue = accessConfig.searchForSettingsValue("darkModeEnabled");
    colours coloursObject = new colours(colourModeSettingValue);//create an object for accessing different colours used in the program
    
    database databaseObject;//create a database object
    
    //create JPanels for each window of content that gets displayed in the card layout of the content jpanel
    JPanel taskPanel = new JPanel();;
    JPanel calendarPanel = new JPanel();
    JPanel notificationPanel = new JPanel();
    JPanel productivityPanel = new JPanel();
    JPanel chatPanel = new JPanel();
    JPanel dropboxPanel = new JPanel();
    JPanel feedbackPanel = new JPanel();
    JPanel callingPanel = new JPanel();
    settings externalSettingsPanel = new settings();
        JPanel settingsPanel = externalSettingsPanel;
    JPanel emptyPanel = new JPanel();
        
    int countOfFeatures = 9;//this variable contains the number of features in the menu panel
    
    public UI(ArrayList<String[][]> tables, String databaseName, String loggedInUser, String password, database dbs) {
        initComponents();
        setLocationRelativeTo(null);//moves the jframe to the centre of the screen
        
        //set the border surrounding the jframe by calling the get root pane method
        getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, coloursObject.getTitlePanelColour()));//set the border of the jframe
        
        //create local versions of the parsed parameters
        this.tables = tables;//the tables from the database  
        this.databaseName = databaseName;
        this.loggedInUser = loggedInUser;//the user that is currently logged in
        this.password = password;
        this.dbs = dbs;
               
        Ping pingObject = new Ping(tables, databaseName, loggedInUser, password);
        titlebar tb = new titlebar(UI.this, pingObject);//create the titlebar class with the JFrame as the parameter
        tb.applyTitleBarSettings(titlebar);//call to apply styles and settings to the titlebar
        
        setPanelLayouts();//calls method to assign layouts to the jpanels on the ui
        assignPanelColours();//call method to assign background colours to the content panels
        assignCards();//call the assignCards method to assign names to each jpanel that gets displayed in the content panel
        
        //set the task panel to be the default card shown
        task externalTaskPanel = new task(tables, databaseName, loggedInUser, password);
        changeCard("task card");
        taskPanel.add(externalTaskPanel.taskPanel);
        
        updateMenuPanel();//call the method to update the menu panel
    }
    
    //method loads the default components of the menu panel
    public void updateMenuPanel(){
        clearMenuPanel();//clear the menu for it's updated values
        
        String[][] taskTable = tables.get(0);//create a local version of the task table
        String card;
        
        menu.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, coloursObject.getTitlePanelColour()));//creates border an thr ight of the menu panel
        
        for(int i = 0; i < countOfFeatures; i++){//loops for the number of features that exist
            
            switch(i){
                case 0:
                    JPanel taskWithDropdown = new JPanel();
                    taskWithDropdown.setOpaque(false);
                    taskWithDropdown.setLayout(new BoxLayout(taskWithDropdown, BoxLayout.X_AXIS));
                    
                    JButton  tasks = new JButton("Tasks");//create button to display the tasks
                    card = "task card";
                    addButtonValues(tasks, "default", card);//call method to add styling values to the button
                    tasks.addActionListener(new ActionListener() {//add a listener for the tasks button
                        public void actionPerformed(ActionEvent e) {//when the button is pressed                   
                            UpdateDatabase updateTables = new UpdateDatabase(databaseName, loggedInUser, password);
                            tables = updateTables.tables;
                            
                            changeCard("task card");
                            taskPanel.removeAll();//clear the contents of the task JPanel
                            updateMenuPanel();//update the menu panel

                            task externalTaskPanel = new task(tables, databaseName, loggedInUser, password);

                            taskPanel.add(externalTaskPanel.taskPanel);//add the JPanel from the external class
                    }});
                    
                    Icon dropDownIcon;//initialize the dropdown buttons icon
                    JButton dropdown = new JButton();//create a button for the dropdown of tasks
                    if(showTasks == false){//if the tasks list isnt being shown
                        dropDownIcon = new ImageIcon("../Images/dropDown.png");//create icon using the drop down image
                    }else{
                        dropDownIcon = new ImageIcon("../Images/dropUp.png");//create icon using the drop down inverted image
                    }
                    dropdown.setIcon(dropDownIcon);//set the icon of the button to the defined icon from above
                    dropdown.setPreferredSize(tasks.getPreferredSize());//set the preferred size of the dropdown button
                    dropdown.setBackground(coloursObject.getMenuPanelColour());//set the background colour to the colour of the menu jpanel
                    dropdown.setBorder(null);//remove the border
                    dropdown.setFocusPainted(false);
                    dropdown.addActionListener(new ActionListener() {//add a listener for the dropdown button
                        public void actionPerformed(ActionEvent e) {//when the button is pressed
                            if(showTasks == false){
                                showTasks = true;//set the show tasks boolean to true
                                updateMenuPanel();//update the menu panel
                            }else{
                                showTasks = false;//set the show tasks boolean to true
                                updateMenuPanel();//update the menu panel
                            }
                            
                    }});
                    
                    Dimension dim = new Dimension(tasks.getWidth() + dropdown.getWidth(), tasks.getHeight());
                    taskWithDropdown.setPreferredSize(dim);
                    
                    taskWithDropdown.add(tasks);
                    taskWithDropdown.add(dropdown);
                    
                    menu.add(taskWithDropdown);//add the button for displaying tasks to the task panel
                    if(showTasks == true){//if the show tasks boolean is true (if the tasks button was pressed)
                        addTasksToMenu(taskTable);
                    }
                    break;
                case 1:
                    JButton calendar = new JButton("Calendar");//create button to display the calendar
                    card = "calendar card";
                    addButtonValues(calendar, "default", card);//call method to add styling values to the button
                    menu.add(calendar);//add the calendar panel to the menu
                    calendar.addActionListener(new ActionListener() {//button action listener
                        public void actionPerformed(ActionEvent e) {
                            UpdateDatabase updateTables = new UpdateDatabase(databaseName, loggedInUser, password);//create update tables object
                            tables = updateTables.tables;//update the tables parsed from the database
                            
                            changeCard("calendar card");//change card on the content panel card layout
                            calendarPanel.removeAll();//clear the contents of the task JPanel
                            updateMenuPanel();//call method to update the menu panel

                            calendar externalCalendarPanel = new calendar(tables, databaseName, loggedInUser, password);//create the calendar object
                            calendarPanel.add(externalCalendarPanel.calendarPanel);//add the JPanel from the external class
                    }});
                    break;
                case 2:
                    JButton notifications = new JButton("Notifications");//create button to display the notifications
                    card = "notifications card";
                    addButtonValues(notifications, "default", card);//call method to add styling values to the button
                    menu.add(notifications);//add the notifications panel to the menu
                    notifications.addActionListener(new ActionListener() {//button action listener
                        public void actionPerformed(ActionEvent e) {
                            UpdateDatabase updateTables = new UpdateDatabase(databaseName, loggedInUser, password);
                            tables = updateTables.tables;
                            
                            changeCard("notifications card");
                            notificationPanel.removeAll();
                            updateMenuPanel();

                            notifications notificationsPage = new notifications(tables, databaseName, loggedInUser, password);

                            notificationPanel.add(notificationsPage);
                    }});
                    break;
                case 3:
                    JButton productivity = new JButton("Productivity");//create button to display the productivity
                    card = "productivity card";
                    addButtonValues(productivity, "default", card);//call method to add styling values to the button
                    menu.add(productivity);//add the productivity panel to the menu
                    productivity.addActionListener(new ActionListener() {//button action listener
                        public void actionPerformed(ActionEvent e) {
                            UpdateDatabase updateTables = new UpdateDatabase(databaseName, loggedInUser, password);
                            tables = updateTables.tables;
                            
                            changeCard("productivity card");                            
                            updateMenuPanel();
                            
                            productivityPanel.removeAll();
                            
                            productivity pro = new productivity(tables);
                            //int width = productivityPanel.getWidth();//get the width of the productivity panel
                            //int height = productivityPanel.getHeight();//get the height of the productivity panel
                            //pro.setPreferredSize(new Dimension(width,height));//set the preffered size to the size of the productivity panel
                            //pro.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, coloursObject.getTitlePanelColour()));
                            
                            productivityPanel.add(pro.productivityPanel);
                    }});
                    break;
                case 4:
                    JButton chat = new JButton("Chat");//create button to display the chat
                    card = "chat card";
                    addButtonValues(chat, "default", card);//call method to add styling values to the button
                    menu.add(chat);//add the chat panel to the menu
                    chat.addActionListener(new ActionListener() {//button action listener
                        public void actionPerformed(ActionEvent e) {
                            UpdateDatabase updateTables = new UpdateDatabase(databaseName, loggedInUser, password);
                            tables = updateTables.tables;
                            
                            changeCard("chat card");
                            updateMenuPanel();

                            chatPanel.removeAll();//clear the contents of the task JPanel

                            chat externalChatPanel = new chat(tables, databaseName, loggedInUser, password, dbs);

                            chatPanel.add(externalChatPanel.chatPanel);//add the JPanel from the external class
                    }});
                    break;
                case 5:
                    JButton dropbox = new JButton("Drop Box");//create button to display the dropbox
                    card = "dropbox card";
                    addButtonValues(dropbox, "default", card);//call method to add styling values to the button
                    menu.add(dropbox);//add the dropbox panel to the menu
                    dropbox.addActionListener(new ActionListener() {//button action listener
                        public void actionPerformed(ActionEvent e) {
                            UpdateDatabase updateTables = new UpdateDatabase(databaseName, loggedInUser, password);
                            tables = updateTables.tables;
                            
                            changeCard("dropbox card");
                            updateMenuPanel();
                            dropboxPanel.removeAll();
                            try {
                                dropBox drop = new dropBox();//create drop box object
                                dropboxPanel.add(drop.dropBoxUI);//add the gui made within the drop box object to the dropbox panel
                            } catch (IOException ex) {
                                System.out.println("ui exception: " + ex);
                            }
                    }});
                    break;
                case 6:
                    JButton feedback = new JButton("Feedback");//create button to display the feedback
                    card = "feedback card";
                    addButtonValues(feedback, "default", card);//call method to add styling values to the button
                    menu.add(feedback);//add the feedback panel to the menu
                    feedback.addActionListener(new ActionListener() {//button action listener
                        public void actionPerformed(ActionEvent e) {
                            UpdateDatabase updateTables = new UpdateDatabase(databaseName, loggedInUser, password);
                            tables = updateTables.tables;
                            
                            changeCard("feedback card");
                            updateMenuPanel();
                            feedbackPanel.removeAll();
                            try{
                                feedback feedback = new feedback(tables, tables.get(0), databaseName, loggedInUser, password);//create the feedback jpanel class
                                feedbackPanel.add(feedback);//add the feedback jpanel class to the jpanel
                                
                            }catch(Exception exc){System.out.println("ui exception: " + exc);}
                            
                    }});
                    break;
                case 7:
                    JButton calling = new JButton("Calling");//create button to display the calling
                    card = "calling card";
                    addButtonValues(calling, "default", card);//call method to add styling values to the button
                    menu.add(calling);//add the calling panel to the menu
                    calling.addActionListener(new ActionListener() {//button action listener
                        public void actionPerformed(ActionEvent e) {
                            changeCard("calling card");
                            updateMenuPanel();
                            
                    }});
                    break;
                default:
                    JButton settings = new javax.swing.JButton("Settings");//create button to display the settings
                    card = "settings card";
                    addButtonValues(settings, "default", card);//call method to add styling values to the button
                    menu.add(settings);//add the settings panel to the menu
                    settings.addActionListener(new ActionListener() {//button action listener
                        public void actionPerformed(ActionEvent e) {
                            changeCard("settings card");
                            updateMenuPanel();

                    }});
                    break;
            }
            menu.add(Box.createRigidArea(new Dimension(10,10)));
        }
        
        repaintMenuPanel();
    }
    
    public void addTasksToMenu(String[][] taskTable){
        for(int i = 0; i < taskTable.length; i++){//loop for the number of available tasks
            int index = i;//create a local version of the loop counter which acts as an index for the task table
            String taskName = taskTable[i][4];//get the name of the current task
            JButton task = new JButton(taskName);//create a button for that task with its name
            addButtonValues(task, "small", "");//call method to add styling values to the button
            
            //add an action listener to the individual task button to allow for details on that task to be displayed
            task.addActionListener(new ActionListener() {//create listener for the the individual task button
                public void actionPerformed(ActionEvent e) {
                    openSpecificTask(taskTable, index);//call method to open a specific task with parsed data
                } 
            });
            
            menu.add(task);//add the button to the panel holding tasks
        }
    }
    
    //method is called to open all information upon a specific task
    //the task table is parsed with an index to the record
    //the record will be extracted and sent to the specificTask class to be viewed
    public void openSpecificTask(String[][] taskTable, int index){
        String[] record = taskTable[index];//create a copy of the individual record from the task table with the given index
        
        emptyPanel.removeAll();//clear the taskPanel
        specificTask displayTask = new specificTask(record, databaseName, loggedInUser, password);//create a version of the specificTask class and feed it the info obtained from the record
        emptyPanel.add(displayTask);//add the individual task panel to the main taskPanel
        emptyPanel.revalidate();
        emptyPanel.repaint();//update the JPanel
        
        changeCard("empty card");//update content panel to display the empty panel which contains this specific tasks information
    }
    
    
    //adds the styling to the buttons
    public void addButtonValues(JButton button, String size, String card){
        button.setPreferredSize(new Dimension(40, 40));
        button.setBackground(coloursObject.getMenuPanelColour());//set the default background
        
        if(card.equals(activeCard)){//if this button is related to the currently viewed card
            button.setForeground(coloursObject.getButtonTextHoverColour());//set the text to the hovered colour
        }else{
            button.setForeground(coloursObject.getButtonTextColour());//set text to the default foreground
        }
        
        if(size == "small"){//if the text is to be made smaller
            button.setFont(new java.awt.Font("Microsoft Tai Le", 0, 11));
            button.setHorizontalAlignment(SwingConstants.RIGHT);
        }else{
            button.setFont(new java.awt.Font("Microsoft Tai Le", 0, 16));
            button.setHorizontalAlignment(SwingConstants.LEFT);
        }
        
        button.setBorder(null);//remove the border
        button.setFocusPainted(false);
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {//when hovering over mouse
                button.setBackground(coloursObject.getMenuPanelColour());//the colour of the button when hovered over
                button.setForeground(coloursObject.getButtonTextHoverColour());//the colour of the text when hovered over
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {//when cursor stops hovering over the mouse
                button.setBackground(coloursObject.getMenuPanelColour());//the colour of the button when the cursor is removed
                if(card.equals(activeCard)){
                    button.setForeground(coloursObject.getButtonTextHoverColour());//the colour of the text when the cursor is removed
                }else{
                    button.setForeground(coloursObject.getButtonTextColour());//the colour of the text when the cursor is removed
                }
            }
        });
    }
    
    //sets the layouts for the jpanels
    public void setPanelLayouts(){
        FlowLayout flowLayout = new FlowLayout(FlowLayout.RIGHT);//create a right align flow layout
        titlebar.setLayout(flowLayout);//add the flow layout to the title bar
        
        GridLayout gridLayout = new GridLayout(0,1);//create vertical 1 column layout for the menu panel
        menu.setLayout(gridLayout);//set the layout of the menu to the grid layout
        
        CardLayout cardLayout = new CardLayout();//create card layout for the content panel
        content.setLayout(cardLayout);//add the layout to the content panel
        
        taskPanel.setLayout(new GridLayout(0,1));//set the layout of the task Panel so it fits the external JPane 100% on both axis
        chatPanel.setLayout(new GridLayout(0,1));//sets the layout of the chat Panel so it fits the external JPane 100% on both axis
        calendarPanel.setLayout(new GridLayout(0,1));
        productivityPanel.setLayout(new GridLayout(0,1));
        notificationPanel.setLayout(new GridLayout(0,1));
        dropboxPanel.setLayout(new GridLayout(0,1));
        feedbackPanel.setLayout(new GridLayout(0,1));
        callingPanel.setLayout(new GridLayout(0,1));
        emptyPanel.setLayout(new GridLayout(0,1));
    }
    
    //set the background colour of the jpanels displayed in the content panel
    public void assignPanelColours(){
        Color contentPanelColour = coloursObject.getContentPanelColour();
        taskPanel.setBackground(contentPanelColour);
        calendarPanel.setBackground(contentPanelColour);
        notificationPanel.setBackground(contentPanelColour);
        productivityPanel.setBackground(contentPanelColour);
        chatPanel.setBackground(contentPanelColour);
        dropboxPanel.setBackground(contentPanelColour);
        feedbackPanel.setBackground(contentPanelColour);
        callingPanel.setBackground(contentPanelColour);
        settingsPanel.setBackground(contentPanelColour);
    }
            
    //assings the jpanels for each menu option to the card layout in the content jpanel
    public void assignCards(){
        content.add(taskPanel, "task card");
        content.add(calendarPanel, "calendar card");
        content.add(notificationPanel, "notifications card");
        content.add(productivityPanel, "productivity card");
        content.add(chatPanel, "chat card");
        content.add(dropboxPanel, "dropbox card");
        content.add(feedbackPanel, "feedback card");
        content.add(callingPanel, "calling card");
        content.add(settingsPanel, "settings card");
        content.add(emptyPanel, "empty card");
    }
    
    //used to change the card on the cardLayout contained within the content panel
    private void changeCard(String str){
        CardLayout card = (CardLayout)(content.getLayout());//get the card layout from the content panel
        card.show(content, str);//show the card with the matching string i.e panel
        activeCard = str;
    }
        
    public void repaintTitlePanel(){titlebar.revalidate(); titlebar.repaint();}//repaint the components of titlebar
    public void repaintMenuPanel(){menu.revalidate(); menu.repaint();}//repaint the components of menu
    public void repaintContentPanel(){content.revalidate(); content.repaint();}//repaint the components of content
    
    public void clearTitlePanel(){titlebar.removeAll();}//clears the components of titlebar
    public void clearMenuPanel(){menu.removeAll();}//clears the components of menu
    public void clearContentPanel(){content.removeAll();}//clears the components of content
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menu = new javax.swing.JPanel();
        titlebar = new javax.swing.JPanel();
        content = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Remotely");
        setBackground(new java.awt.Color(0, 0, 0));
        setMaximumSize(new java.awt.Dimension(1440, 810));
        setMinimumSize(new java.awt.Dimension(1440, 810));
        setUndecorated(true);

        menu.setBackground(coloursObject.getMenuPanelColour()
        );
        menu.setMaximumSize(new java.awt.Dimension(160, 32767));
        menu.setMinimumSize(new java.awt.Dimension(160, 100));
        menu.setPreferredSize(new java.awt.Dimension(160, 480));

        javax.swing.GroupLayout menuLayout = new javax.swing.GroupLayout(menu);
        menu.setLayout(menuLayout);
        menuLayout.setHorizontalGroup(
            menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 160, Short.MAX_VALUE)
        );
        menuLayout.setVerticalGroup(
            menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 310, Short.MAX_VALUE)
        );

        titlebar.setBackground(coloursObject.getTitlePanelColour()
        );
        titlebar.setMaximumSize(new java.awt.Dimension(32767, 34));
        titlebar.setMinimumSize(new java.awt.Dimension(100, 34));
        titlebar.setPreferredSize(new java.awt.Dimension(0, 34));

        javax.swing.GroupLayout titlebarLayout = new javax.swing.GroupLayout(titlebar);
        titlebar.setLayout(titlebarLayout);
        titlebarLayout.setHorizontalGroup(
            titlebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        titlebarLayout.setVerticalGroup(
            titlebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 34, Short.MAX_VALUE)
        );

        content.setBackground(coloursObject.getContentPanelColour()
        );

        javax.swing.GroupLayout contentLayout = new javax.swing.GroupLayout(content);
        content.setLayout(contentLayout);
        contentLayout.setHorizontalGroup(
            contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 511, Short.MAX_VALUE)
        );
        contentLayout.setVerticalGroup(
            contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 310, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titlebar, javax.swing.GroupLayout.DEFAULT_SIZE, 671, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(menu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(content, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(titlebar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(content, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(menu, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel content;
    private javax.swing.JPanel menu;
    private javax.swing.JPanel titlebar;
    // End of variables declaration//GEN-END:variables
}
