package Features;


import Functions.colours;
import Functions.accessConfigFile;
import NotificationFunctionality.NotificationPanel;
import NotificationFunctionality.addNewNotification;
import Styles.buttons;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import static javax.swing.BorderFactory.createEmptyBorder;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


/**
 *
 * @author ewand
 */
public class notifications extends JPanel{
    ArrayList<String[][]> tables = new ArrayList<String[][]>();
    String databaseName;
    String loggedInUser;
    String password;
    Boolean showTasks = false;
    
    accessConfigFile accessConfig = new accessConfigFile();//create an object for accessing the config file
    String colourModeSettingValue = accessConfig.searchForSettingsValue("darkModeEnabled");
    colours coloursObject = new colours(colourModeSettingValue);//create an object for accessing different colours used in the program
    buttons buttonStyles = new buttons();
    
    List<NotificationPanel> notificationList;
    
    public notifications(ArrayList<String[][]> tables, String databaseName, String loggedInUser, String password) {
        initComponents();
        
        this.tables = tables;
        this.databaseName = databaseName; 
        this.loggedInUser = loggedInUser;
        this.password = password;
        notificationList = new ArrayList<>();
        
        displayPanel.setLayout(new GridLayout(0,1));
        
        createNotificationsPanel();
        
        applyStyles();//method applys styles to components on the page
        
    }
    
    public void applyStyles(){
        buttonStyles.applyButtonStyles(editNotificationsButton);//apply styles from the colours object to the edit notfications button
        editNotificationsButton.setBackground(coloursObject.getMenuPanelColour());//override the background colour set in the above method
        
        buttonStyles.applyButtonStyles(deleteButton);//apply styles from the colours object to the delete notfications button
        deleteButton.setBackground(coloursObject.getMenuPanelColour());//override the background colour set in the above method
        
        controlsPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, coloursObject.getTitlePanelColour()));//set the border style for the panel
        
        displayScrollPanel.setBorder(createEmptyBorder());//remove the border around the scroll panel
    }

    //method creates and displays all the notifications that are relevent ot the user. 
    //Loads the table and displays records with the senders name, or with no name (means its for everybody)
    public void createNotificationsPanel(){
        String[][] notificationsTable = tables.get(3);//create a local version of the notifications table
        //String[][] userTable = tables.get(2);//create a local version of the notifications table
        
        for(int i = 0; i < notificationsTable.length; i++){//loop for the number of notifications that exist
            if(Integer.parseInt(notificationsTable[i][5]) == 1){//if the active field in the record is 1 (if the notification is set to active)
                String noteContent = notificationsTable[i][3];
                String time = notificationsTable[i][4];
                String name = notificationsTable[i][6];              
                
                NotificationPanel notePanel = new NotificationPanel(coloursObject, name, noteContent, time);
                notificationList.add(notePanel);
                displayPanel.add(notePanel);
            }
        }
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        controlsPanel = new javax.swing.JPanel();
        editNotificationsButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        displayScrollPanel = new javax.swing.JScrollPane();
        displayPanel = new javax.swing.JPanel();

        setBackground(coloursObject.getContentPanelColour());

        controlsPanel.setBackground(coloursObject.getMenuPanelColour());

        editNotificationsButton.setText("Create or Update");
        editNotificationsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editNotificationsButtonActionPerformed(evt);
            }
        });

        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout controlsPanelLayout = new javax.swing.GroupLayout(controlsPanel);
        controlsPanel.setLayout(controlsPanelLayout);
        controlsPanelLayout.setHorizontalGroup(
            controlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlsPanelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(controlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(deleteButton)
                    .addComponent(editNotificationsButton))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        controlsPanelLayout.setVerticalGroup(
            controlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlsPanelLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(editNotificationsButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(deleteButton)
                .addContainerGap(585, Short.MAX_VALUE))
        );

        displayScrollPanel.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        displayPanel.setBackground(coloursObject.getContentPanelColour());

        javax.swing.GroupLayout displayPanelLayout = new javax.swing.GroupLayout(displayPanel);
        displayPanel.setLayout(displayPanelLayout);
        displayPanelLayout.setHorizontalGroup(
            displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 652, Short.MAX_VALUE)
        );
        displayPanelLayout.setVerticalGroup(
            displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        displayScrollPanel.setViewportView(displayPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(controlsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(displayScrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 655, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(controlsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(displayScrollPanel)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void editNotificationsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editNotificationsButtonActionPerformed
        removeAll();
        setLayout(new GridLayout(0,1));
        addNewNotification newNote = new addNewNotification(tables, databaseName, loggedInUser, password);
        add(newNote);
        revalidate();
        repaint();
    }//GEN-LAST:event_editNotificationsButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        try{
            for(int i = 0; i < notificationList.size(); i++){
                NotificationPanel panel = notificationList.get(i);
                if(panel.removeRadioButton.isSelected()){
                    removeNotification(panel.getName());//call method to remove notification from database
                    displayPanel.remove(panel);
                    displayPanel.revalidate();
                    displayPanel.repaint();
                }
            }
        }catch(Exception exc){}
    }//GEN-LAST:event_deleteButtonActionPerformed
        
    private void removeNotification(String name){
        
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel controlsPanel;
    private javax.swing.JButton deleteButton;
    private javax.swing.JPanel displayPanel;
    private javax.swing.JScrollPane displayScrollPanel;
    private javax.swing.JButton editNotificationsButton;
    // End of variables declaration//GEN-END:variables
}
