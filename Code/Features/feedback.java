package Features;

import FeedbackFunctionality.InactiveTaskPanel;
import Functions.accessConfigFile;
import Functions.colours;
import Styles.buttons;
import Styles.fonts;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import static javax.swing.BorderFactory.createEmptyBorder;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author ewand
 */
public class feedback extends javax.swing.JPanel {
    
    accessConfigFile accessConfig = new accessConfigFile();//create an object for accessing the config file
    String colourModeSettingValue = accessConfig.searchForSettingsValue("darkModeEnabled");
    colours coloursObject = new colours(colourModeSettingValue);//create an object for accessing different colours used in the program
    buttons buttonStyles = new buttons();
    fonts fontStyles = new fonts();
    
    ArrayList<String[][]> tables;
    String[][] taskTable;
    String databaseName;
    String loggedInUser;
    String password;
    String url = "jdbc:mysql://localhost:3306/";
    
    List<InactiveTaskPanel> inactiveTaskList = new ArrayList<InactiveTaskPanel>();//create list to hold the panels made for inactive tasks
    
    String newFeedback;
    
    public feedback(ArrayList<String[][]> tables, String[][] taskTable, String databaseName, String loggedInUser, String password) {
        this.tables = tables;
        this.taskTable = taskTable;
        this.databaseName = databaseName;
        this.loggedInUser = loggedInUser;
        this.password = password;
        
        initComponents();
        contentPanel.setLayout(new GridLayout(0,5));
        
        loadInactiveTasks();
        
        displayInactiveTasks();
        
        applyStyles();
    }

    public void loadInactiveTasks(){
        for(int i = 0; i < taskTable.length; i++){//loop through the task table
            if(taskTable[i][6].equals("0")){//if the indexed task is no longer active
                String name = taskTable[i][4];//get the name of the task record
                String details = taskTable[i][3];//get the details of the task record
                String feedback = taskTable[i][5];//get the feedback field of the indexed task record
                String ID = taskTable[i][0];
                
                InactiveTaskPanel inactiveTask = new InactiveTaskPanel(coloursObject, ID, name, details, feedback);//create a panel for the inactive task
                
                inactiveTaskList.add(inactiveTask);//add the new inactiveTask panel to the list
            }
        }
        System.out.println("total" + inactiveTaskList.size());
    }
    
    public void displayInactiveTasks(){
        for(int i = 0; i < inactiveTaskList.size(); i++){
            contentPanel.add(inactiveTaskList.get(i));//display indexed inactive task panel
            
            contentPanel.revalidate();
            contentPanel.repaint();
        }
    }
    
    //method is called to apply styling to components
    private void applyStyles(){        
        //remove the border from the scroll panes
        contentPanel.setBorder(createEmptyBorder());
        
        //apply styles to the edit feedback button
        buttonStyles.applyButtonStyles(editButton);
        editButton.setBackground(coloursObject.getMenuPanelColour());
        
        //assign a border to the control panel
        controlsPanel.setBorder(BorderFactory.createMatteBorder(2, 1, 0, 0, coloursObject.getTitlePanelColour()));//set the border style for the panel
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        controlsPanel = new javax.swing.JPanel();
        editButton = new javax.swing.JButton();
        contentScrollPane = new javax.swing.JScrollPane();
        contentPanel = new javax.swing.JPanel();

        setBackground(coloursObject.getContentPanelColour());

        controlsPanel.setBackground(coloursObject.getMenuPanelColour());

        editButton.setText("Edit Feedback");
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout controlsPanelLayout = new javax.swing.GroupLayout(controlsPanel);
        controlsPanel.setLayout(controlsPanelLayout);
        controlsPanelLayout.setHorizontalGroup(
            controlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlsPanelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(editButton)
                .addContainerGap(630, Short.MAX_VALUE))
        );
        controlsPanelLayout.setVerticalGroup(
            controlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlsPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(editButton)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        contentScrollPane.setBorder(null);
        contentScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        contentScrollPane.setOpaque(false);
        contentScrollPane.setPreferredSize(super.getPreferredSize());

        contentPanel.setBackground(coloursObject.getContentPanelColour());
        contentPanel.setPreferredSize(super.getPreferredSize());

        javax.swing.GroupLayout contentPanelLayout = new javax.swing.GroupLayout(contentPanel);
        contentPanel.setLayout(contentPanelLayout);
        contentPanelLayout.setHorizontalGroup(
            contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 761, Short.MAX_VALUE)
        );
        contentPanelLayout.setVerticalGroup(
            contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 359, Short.MAX_VALUE)
        );

        contentScrollPane.setViewportView(contentPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(controlsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(contentScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(contentScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(controlsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    //to edit the feedback hit the button and a form will open for the selected task
    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        for(int i = 0; i < inactiveTaskList.size(); i++){
            InactiveTaskPanel panel = inactiveTaskList.get(i);//create local reference to the indexed panel
            
            if(panel.selectedRadioButton.isSelected()){//if the panels radio button is selected
                //create the components for the popup
                JFrame popup = new JFrame();//the frame
                JPanel content = new JPanel();//the panel that contains all components
                JPanel controls = new JPanel();//the panel that holds the two buttons
                JTextField input = new JTextField();//text field for user input
                JButton confirm = new JButton("Confirm");//button to confirm the input
                JButton cancel = new JButton("Cancel");//button to cancel
                
                confirm.addMouseListener(new java.awt.event.MouseAdapter() {//add listener for the label
                    public void mouseClicked(java.awt.event.MouseEvent evt) {//when hovering over mouse
                        System.out.println("popup: confirm button pressed");
                        newFeedback = input.getText();
                        panel.updateFeedback(newFeedback);
                        
                        updateDatabaseFeedback(panel.ID, newFeedback);
                        
                        popup.dispatchEvent(new WindowEvent(popup, WindowEvent.WINDOW_CLOSING));
                    }
                });
                
                cancel.addMouseListener(new java.awt.event.MouseAdapter() {//add listener for the label
                    public void mouseClicked(java.awt.event.MouseEvent evt) {//when hovering over mouse
                        System.out.println("popup: cancel button pressed");
                        popup.dispatchEvent(new WindowEvent(popup, WindowEvent.WINDOW_CLOSING));
                    }
                });
                
                //assign layouts for the panels
                content.setLayout(new GridLayout(2,0));
                controls.setLayout(new GridLayout(0,2));
                
                popup.setLocationRelativeTo(null);//move the popup to centre of the page
                popup.setUndecorated(true);//remove default tilebar
                
                //apply styles to the buttons used in the popup
                buttonStyles.applyButtonStyles(confirm);
                confirm.setBackground(coloursObject.getMenuPanelColour());
                buttonStyles.applyButtonStyles(cancel);
                cancel.setBackground(coloursObject.getMenuPanelColour());
                
                //add the components to eachother
                content.add(input);
                content.add(controls);
                controls.add(confirm);
                controls.add(cancel);
                popup.add(content);
                
                popup.setVisible(true);//set the visibility of the popup to true
                popup.setSize(400,100);//set the dimensions of the popup
            }
        }
    }//GEN-LAST:event_editButtonActionPerformed

    //method called to update the indexed tasks feedback
    private void updateDatabaseFeedback(String ID, String updatedFeedback){
        for(int i = 0; i < taskTable.length; i++){
            if(taskTable[i][0].equals(ID)){
                try{
                    Connection connection = DriverManager.getConnection(url + databaseName, loggedInUser, password);//create connection to database
                    Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);//create statement for use in querying the db

                    //create the start of an insert query by only adding some details. The rest are null by default
                    String query = "update task set Feedback = '"+ updatedFeedback +"' where taskID = '" + ID + "'";//string is used to hold the query that is to be executed

                    statement.executeUpdate(query);//execute the query

                    connection.close();
                    statement.close();
                    
                }catch(Exception e){System.out.println("Error updaing the feedback");}
            }
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel contentPanel;
    private javax.swing.JScrollPane contentScrollPane;
    private javax.swing.JPanel controlsPanel;
    private javax.swing.JButton editButton;
    // End of variables declaration//GEN-END:variables
}
