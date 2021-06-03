package Features;


import Functions.accessConfigFile;
import Functions.colours;
import Styles.buttons;
import Styles.labels;
import TaskFunctionality.addNewTask;
import TaskFunctionality.specificTask;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 *
 * @author ewand
 */
public class task {
    ArrayList<String[][]> tables;
    String databaseName;
    String loggedInUser;
    String password;
    Boolean showTasks = false;
    
    accessConfigFile accessConfig = new accessConfigFile();//create an object for accessing the config file
    String colourModeSettingValue = accessConfig.searchForSettingsValue("darkModeEnabled");
    colours coloursObject = new colours(colourModeSettingValue);//create an object for accessing different colours used in the program
    
    buttons buttonStyles = new buttons();//create a copy of the button styles object
    labels labelStyles = new labels();//create a copy of the label styles object
    
    public JPanel taskPanel = new JPanel();
    
    
    public task(ArrayList<String[][]> tables, String databaseName, String loggedInUser, String password) {
        this.tables = tables;
        this.databaseName = databaseName; 
        this.loggedInUser = loggedInUser;
        this.password = password;
        
        createTaskPanel();
    }
    
    //method creates the most updated task panel
    public void createTaskPanel(){
        String [][] taskTable = tables.get(0);
        int count = taskTable.length;//sets count to the number of rows in the task table
        int noOfHeaders;
        
        System.out.println("Task Panel Dimensions " + taskPanel.getWidth() + ":" + taskPanel.getHeight());
        
        taskPanel.removeAll();//empties the current task
        taskPanel.setLayout(new BoxLayout(taskPanel,BoxLayout.Y_AXIS));//creates a layout for the task panel
        taskPanel.setBackground(coloursObject.getContentPanelColour());
        
        //fill a list with the headers of the task table fields
        ArrayList<String> headingTitles = new ArrayList<String>(){//create an arraylist to hold the titles on the header of the page
            {
                add("Names");//add the following titles to the arraylist
                add("Start Date");
                add("End Date");
                add("Details");
                add("Importance");
            }
        };
        noOfHeaders = headingTitles.size();//get the size of the headers list
        
        JPanel headers = new JPanel();//create a panel for the headers of the task view panel
        headers.setLayout(new GridLayout(0,noOfHeaders));//set the layout for the panel (grid with no defined rows and a set number of columns)
        headers.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 1, coloursObject.getBorderColour()));//creates border around panel
        headers.setBackground(coloursObject.getMenuPanelColour());
        
        //loop creates the labels for the headers of the task panel
        for(int i = 0;i < headingTitles.size();i++){//loop for the number of headers that exist in the headingTitles arraylist
            JLabel head = new JLabel(headingTitles.get(i));//create new jlabel with the given header
            head.setForeground(coloursObject.getButtonTextHoverColour());//set foreground colour
            head.setFont(new java.awt.Font("Microsoft Tai Le", 0, 16));//set font and size
            headers.add(head);//add label to the panel
        }
          
        taskPanel.add(headers);//add the headers panel to the top of the task panel
        
        //this loop generates the rows to be displayed in the task view
        for(int i = 0;i < count;i++){//loop for the number of records in the table
            if(taskTable[i][6].equals("1")){//if the task is set to active
                int index = i;//integer holds the index of the task in the taskTable 
                
                JPanel panel = new JPanel();//create the panel that the task exists on
                panel.setLayout(new GridLayout(0,noOfHeaders));//creates a layout for the task panel
                panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, coloursObject.getBorderColour()));
                panel.setBackground(coloursObject.getContentPanelColour());
                taskListener(taskTable, index, panel, null);

                //create the label for the each peice of task data and the listener which runs the same event as the parent jpanel and other sibling components
                JLabel taskName = new JLabel(taskTable[i][4]);//create a label for the task name
                taskListener(taskTable, index, null, taskName);//create the listeners that open the specificTask class with matching information
                JLabel startDate = new JLabel(taskTable[i][1]);//create label for the start date
                taskListener(taskTable, index, null, startDate);//create the listeners that open the specificTask class with matching information
                JLabel endDate = new JLabel(taskTable[i][2]);//create label for the end date
                taskListener(taskTable, index, null, endDate);//create the listeners that open the specificTask class with matching information
                JLabel details = new JLabel(taskTable[i][3]);//create label for the details
                taskListener(taskTable, index, null, details);//create the listeners that open the specificTask class with matching information
                JLabel importance = new JLabel(taskTable[i][7]);//create label for the details
                taskListener(taskTable, index, null, details);//create the listeners that open the specificTask class with matching information

                //apply styles to the labels for that specific row/record
                labelStyles.applyLabelStyles(taskName);
                labelStyles.applyLabelStyles(startDate);
                labelStyles.applyLabelStyles(endDate);
                labelStyles.applyLabelStyles(details);
                labelStyles.applyLabelStyles(importance);

                //add content to the panel
                panel.add(taskName);
                panel.add(startDate);
                panel.add(endDate);
                panel.add(details);
                panel.add(importance);

                taskPanel.add(panel);//add the panel to the task panel
            }
        }
        
        //create the panel and components of the row in the task panel that allows the user to create a new task
        JPanel addNewTaskPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));//create jpanel holding the components that take the user to the page to add a new task
        addNewTaskPanel.setBackground(coloursObject.getContentPanelColour());
        JButton addNewTask = new JButton("+ Add New Task");//create a button to take the user to the add task page
        buttonStyles.applyButtonStyles(addNewTask);
        addNewTask.setFont(new java.awt.Font("Microsoft Tai Le", 0, 14));;//overide the font style provided by calling the addButtonStyles method
        addNewTask.addActionListener(new ActionListener() {//create listener for the add new task button
            public void actionPerformed(ActionEvent e) {
                taskPanel.removeAll();//clear the taskPanel
                addNewTask add = new addNewTask(tables, databaseName, loggedInUser, password);//create a new addNewTask JPanel object
                taskPanel.add(add);//add the addNewTask JPanel to the now empty JPanel
                taskPanel.revalidate();
                taskPanel.repaint();//update the JPanel
            } 
        });
        
        addNewTaskPanel.add(addNewTask);//add the button to the jpanel
        taskPanel.add(addNewTaskPanel);//add the jpanel to the task panel
        
        taskPanel.revalidate();//validates the contents of the panel
        taskPanel.repaint();//creates the jpanel
    }
    
    //method is used to create mouse listeners for any parsed component (listener opens the specificTask class)
    public void taskListener(String[][] taskTable, int index, JPanel panel, JLabel label){
        if(panel != null){
            panel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {//when hovering over mouse
                    System.out.println("calendar: user clicked a task");
                    openSpecificTask(taskTable, index);//method opens the page of a specific task to view individual information on it
                }
            });
        }
        if(label != null){
            label.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {//when hovering over mouse
                    System.out.println("calendar: user clicked a task");
                    openSpecificTask(taskTable, index);//method opens the page of a specific task to view individual information on it
                }
            });
        }
    }
    
    //method is called to open all information upon a specific task
    //the task table is parsed with an index to the record
    //the record will be extracted and sent to the specificTask class to be viewed
    public void openSpecificTask(String[][] taskTable, int index){
        String[] record = taskTable[index];//create a copy of the individual record from the task table with the given index
        
        taskPanel.removeAll();//clear the taskPanel
        specificTask displayTask = new specificTask(record, databaseName, loggedInUser, password);//create a version of the specificTask class and feed it the info obtained from the record
        taskPanel.add(displayTask);//add the individual task panel to the main taskPanel
        taskPanel.revalidate();
        taskPanel.repaint();//update the JPanel
        
    }
    
    /**
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 927, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 646, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    **/


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
