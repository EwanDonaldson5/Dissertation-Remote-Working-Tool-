package Features;

import CalendarFunctionality.CalendarControls;
import Functions.colours;
import Functions.accessConfigFile;
import Styles.buttons;
import TaskFunctionality.specificTask;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 *
 * @author ewand
 */
public class calendar extends JPanel{
    ArrayList<String[][]> tables = new ArrayList<String[][]>();
    
    private String url = "jdbc:mysql://localhost:3306/";
    String databaseName;
    String loggedInUser;
    String password;
    Boolean showTasks = false;
    
    accessConfigFile accessConfig = new accessConfigFile();//create an object for accessing the config file
    String colourModeSettingValue = accessConfig.searchForSettingsValue("darkModeEnabled");
    colours coloursObject = new colours(colourModeSettingValue);//create an object for accessing different colours used in the program
    
    buttons buttonStyles = new buttons();//create a copy of the button styles object
    
    String usersCompanyID;
    
    public JPanel calendarPanel = new JPanel();
    CalendarControls cControls = new CalendarControls(coloursObject);//create an object for accessing different colours used in the program);
    
    calendar.ClickListener cl = new calendar.ClickListener();//create a click listener class for the controls in the jpanel
    
    Calendar cal;
    int month;
    int year;
    
    public calendar(ArrayList<String[][]> tables, String databaseName, String loggedInUser, String password) {
        this.tables = tables;
        this.databaseName = databaseName; 
        this.loggedInUser = loggedInUser;
        this.password = password;
        
        //initComponents();
        
        cal = Calendar.getInstance();//create the calendar instance
        createCalendarPanel(cal.get(cal.MONTH), cal.get(cal.YEAR));//call method and parse the current real-world month
        
        addActionListeners();//call method to add actionListeners to the controls buttons
        
    }
    
    //creates the calendar page. It creates two sections, one with the controls and information, the second with the calendar itself
    public void createCalendarPanel(int parsedMonth, int parsedYear){
        month = parsedMonth;//update the global versions of these variables
        year = parsedYear;
          
        calendarPanel.removeAll();//clear the calendar panel of its contents
        calendarPanel.add(cControls);//add the controls panel to the calendar panel
        
        String[][] taskTable = tables.get(0);//get local version of the task table
        int taskNameIndex = 4;//index of the column in the table that contains task name
        int endDateIndex = 2;//index of the column in the table that contains end date
        final String[] monthNames = { "January", "February", "March", "April","May", "June", //list of all the months within a year
            "July", "August", "September", "October", "November", "December" };
        
        //create the calendar object and collect some data on it
        cal.set(parsedYear, parsedMonth, 1);//sets the month to the value that was parsed upon the start of the method
        int lastday = cal.getActualMaximum(cal.DATE);//variable set to the last day of the month in integer value
        
        calendarPanel.setLayout(new BoxLayout(calendarPanel,BoxLayout.Y_AXIS));//set the layout for the calendar page
        calendarPanel.setBackground(coloursObject.getContentPanelColour());
        
        cControls.currentMonthLabel.setText(monthNames[month]);//set the text of the current month label in the controls panel
        cControls.currentYearLabel.setText(Integer.toString(year));//set the text of the current year label in the controls panel
        
        //create the section which displays the calendar
        JPanel calendarContent = new JPanel();//create panel for the calendar to be displayed
        calendarContent.setLayout(new GridLayout(0, 7));//set layout to have 7 columns, one for each day of the week
        calendarContent.setBackground(coloursObject.getContentPanelColour());
        
        for(int i = 1; i <= lastday; i++){//for the number of days that exist in this given month
            JPanel panel = new JPanel();//panel represents and contains the components of the individual calendar day
            panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));//set vertical box layout for the panel
            panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, coloursObject.getBorderColour()));
            panel.setBackground(coloursObject.getContentPanelColour());
            
            JLabel dayLabel = new JLabel(Integer.toString(i));//create label with the date as the title
            dayLabel.setFont(new java.awt.Font("Microsoft Tai Le", 0, 11));//set the text of the jlabel
            dayLabel.setForeground(coloursObject.getButtonTextHoverColour());//set the text colour of the jlabel
            
            panel.add(dayLabel);//add the label to the panel
            
            for(int c = 0; c < taskTable.length; c++){//loop for the number of rows that exist in the task table
                int index = c;//get a copy of the loop counter. This acts as an index in the task table
                
                try{
                    int year = cal.get(cal.YEAR);//get the currently displayed year of the calendar
                    int month = cal.get(cal.MONTH)+1;//get the currently displayed month of the calendar
                    
                    String taskDate = taskTable[c][endDateIndex];//get the end date of the task being viewed in the table
                    String calendarDate = "";//create variable to hold the currently created calendar date
                    
                    //if the day being read has only one digit then add the missing 0 in the concatenation below (2020/12/06 vs 2020/12/6)
                    if(i <= 9){//if less than or equal to 9
                        calendarDate = year + "-" + month + "-0" + i;//concatenate the year, month and day from the currently created calendar (with the missing zero)
                    }else{
                        calendarDate = year + "-" + month + "-" + i;//concatenate the year, month and day from the currently created calendar
                    }
                    
                    //if the task end date matches the date currently being indexed from the calendar
                    if(taskDate.equals(calendarDate)){//if the date in the task table record is equal to the date of the calendar panel being created
                        JButton taskName = new JButton(taskTable[c][taskNameIndex]);//create a button with the task name
                        buttonStyles.applyButtonStyles(taskName);//apply button styles to this button
                        taskName.setOpaque(false);//overide the applyButtonStyles method by hiding the background
                        panel.add(taskName);//add the button to the panel
                        
                        taskName.addMouseListener(new java.awt.event.MouseAdapter() {//add action listener to the button that displays the task name
                            public void mouseClicked(java.awt.event.MouseEvent evt) {//when this button has been clicked
                                openSpecificTask(taskTable, index);//method opens the page of a specific task to view individual information on it
                            }
                            //copy the action listener events below which affects the panel colour
                            public void mouseEntered(java.awt.event.MouseEvent evt) {//when hovering over mouse
                                panel.setBackground(coloursObject.getTitlePanelColour());
                            }
                            public void mouseExited(java.awt.event.MouseEvent evt) {//when cursor stops hovering over the mouse
                                panel.setBackground(coloursObject.getContentPanelColour());
                            }
                        });
                    }
                    
                }catch(Exception exc){//catch any exceptions
                    exc.printStackTrace();//print the exception
                }
            }
            
            //adds actionlistener to the jpanel that is the individual day
            //when hovered over the panel should change colour
            panel.addMouseListener(new MouseAdapter() {//create mouse motion listener for the titlebar
                public void mouseEntered(java.awt.event.MouseEvent evt) {//when hovering over mouse
                    panel.setBackground(coloursObject.getTitlePanelColour());
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {//when cursor stops hovering over the mouse
                    panel.setBackground(coloursObject.getContentPanelColour());
                }
            });
            
            calendarContent.add(panel);//add label to the panel
        }

        calendarPanel.add(calendarContent, BorderLayout.CENTER);//add the calendar to the calendar page
        
        calendarPanel.revalidate();//revalidate the calendar panel
        calendarPanel.repaint();//repaint the components of the calendar panel
        
    }
    
    //method is called from the controls panel to update the calendar
    //the backForward var contains whether to go back or forward
    //the timeFrame var contains whether to change in increments of month or year
    public void updateCalendarDate(String backForward, String timeFrame){
        if(timeFrame.equals("month")){//if the time frame is in months
            if(backForward.equals("back")){//if the back button was pressed
                if(month == 0){//if the month is already on january
                    createCalendarPanel(11, year - 1);//set the month to december and the year to this year minus 1
                }else{
                    createCalendarPanel(month - 1, year);//set the month to 1 less and keep the year the same
                }
            }
            if(backForward.equals("forward")){//if the forward button is pressed
                if(month == 11){//if the month is already decemeber
                    createCalendarPanel(0, year + 1);//set the month to january and the year to the next year
                }else{
                    createCalendarPanel(month + 1, year);//set the month to the next month and keep the year
                }
            }
        }else if(timeFrame.equals("year")){//if the time frame is in years
            if(backForward.equals("back")){//if the back button was pressed
                createCalendarPanel(month, year - 1);//decrease the year by 1
            }
            if(backForward.equals("forward")){//if the forward button is pressed
                createCalendarPanel(month, year + 1);//increase the year by 1
            }
        }
        
    }
    
    //class is a click listener for the jpanel components
    private class ClickListener implements ActionListener {
                
        public void actionPerformed(ActionEvent e) {//when an action is performed
            if (e.getSource() == cControls.backMonthButton){//if the source of the action is back a month button in the controls panel
                updateCalendarDate("back", "month");
            }
            if (e.getSource() == cControls.forwardMonthButton){//if the source of the action is forward a month button in the controls panel
                updateCalendarDate("forward", "month");
            }
            if (e.getSource() == cControls.backYearButton){//if the source of the action is back a year button in the controls panel
                updateCalendarDate("back", "year");
            }
            if (e.getSource() == cControls.forwardYearButton){//if the source of the action is forward a year button in the controls panel
                updateCalendarDate("forward", "year");
            }
        }
    }
    
    //method adds acion listeners to buttons from the controls panel
    public void addActionListeners(){
        cControls.backMonthButton.addActionListener(cl);//add the click listener to the backMonth button from the controls
        cControls.forwardMonthButton.addActionListener(cl);//add the click listener to the forwardMonth button from the controls
        cControls.backYearButton.addActionListener(cl);//add the click listener to the backYear button from the controls
        cControls.forwardYearButton.addActionListener(cl);//add the click listener to the forwardYear button from the controls
    }
    
    //method is called to open all information upon a specific task
    //the task table is parsed with an index to the record
    //the record will be extracted and sent to the specificTask class to be viewed
    public void openSpecificTask(String[][] taskTable, int index){
        String[] record = taskTable[index];//create a copy of the individual record from the task table with the given index
        
        calendarPanel.removeAll();//clear the taskPanel
        specificTask displayTask = new specificTask(record, databaseName, loggedInUser, password);//create a version of the specificTask class and feed it the info obtained from the record
        calendarPanel.add(displayTask);//add the individual task panel to the main taskPanel
        calendarPanel.revalidate();
        calendarPanel.repaint();//update the JPanel
        
    }
    
    
    
    /**
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        controlsPanel = new javax.swing.JPanel();
        calendarDisplayPanel = new javax.swing.JPanel();

        controlsPanel.setBackground(new java.awt.Color(51, 255, 0));

        javax.swing.GroupLayout controlsPanelLayout = new javax.swing.GroupLayout(controlsPanel);
        controlsPanel.setLayout(controlsPanelLayout);
        controlsPanelLayout.setHorizontalGroup(
            controlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 835, Short.MAX_VALUE)
        );
        controlsPanelLayout.setVerticalGroup(
            controlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 89, Short.MAX_VALUE)
        );

        calendarDisplayPanel.setBackground(new java.awt.Color(204, 0, 0));

        javax.swing.GroupLayout calendarDisplayPanelLayout = new javax.swing.GroupLayout(calendarDisplayPanel);
        calendarDisplayPanel.setLayout(calendarDisplayPanelLayout);
        calendarDisplayPanelLayout.setHorizontalGroup(
            calendarDisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        calendarDisplayPanelLayout.setVerticalGroup(
            calendarDisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 446, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(controlsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(calendarDisplayPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(controlsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(calendarDisplayPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    **/

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JPanel calendarDisplayPanel;
    private javax.swing.JPanel controlsPanel;
    // End of variables declaration//GEN-END:variables
}
