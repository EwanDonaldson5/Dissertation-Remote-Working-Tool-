package DropBoxFunctionality;

import Functions.accessConfigFile;
import Functions.colours;
import Styles.buttons;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.Metadata;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 *
 * @author ewand
 */
public class DropBoxGUI extends javax.swing.JPanel {
    
    static DbxClientV2 client;
    
    accessConfigFile accessConfig = new accessConfigFile();//create an object for accessing the config file
    String colourModeSettingValue = accessConfig.searchForSettingsValue("darkModeEnabled");
    colours coloursObject = new colours(colourModeSettingValue);//create an object for accessing different colours used in the program
    buttons buttonStyles = new buttons();
    
    List<Metadata> metaList;
    List<FileandFolderPanel> filesFoldersList;//create a list to hold the file and folder jpanels in
    
    public DropBoxGUI(List<Metadata> metaList, DbxClientV2 client) {
        this.metaList = metaList;
        this.client = client;
        filesFoldersList  = getListOfDocs();//fill list by calling method
        
        removeAll();//clear the parent panel
        
        initComponents();//initialize components
        
        //display the panel on the user interface
        displayPanel.setLayout(new GridLayout(0,4));
        for(int i = 0; i < filesFoldersList.size(); i++){
            displayPanel.add(filesFoldersList.get(i));
        }
        
        //apply styles to components
        applyStylesToComponents();
    }
    
    //method is called to return a list that contains folders and files
    public List<FileandFolderPanel> getListOfDocs(){
        List<FileandFolderPanel> map = new ArrayList<>();
        
        for(int i = 0; i < metaList.size(); i++){
            //get the name of the indexed folder or file
            String name = metaList.get(i).getName();
            
            //check if a full stop is included (indicating a file type, so not a folder)
            Boolean fileOrFolder = name.contains(".");//returns false if not
            
            //if there was a full stop then create a file panel
            //else if there was no full stop, then create a folder panel
            if(fileOrFolder){
                FileandFolderPanel filePanel = createFileFolderPanel("File", name);//create panel for the file
                map.add(filePanel);//add the new file panel to the list
            
            }else{
                FileandFolderPanel folderPanel = createFileFolderPanel("Folder", name);//create panel for the folder
                map.add(folderPanel);//add the new panel to the list
            }
            
        }
        return map;//return the filled list
    }
    
    //method is called to create a small panel for the file
    //this is then put into the grid of files/folders by adding them to the arraylist
    public FileandFolderPanel createFileFolderPanel(String type, String name){
        FileandFolderPanel panel = new FileandFolderPanel(type, name, coloursObject, client);
        
        //add action listener to the panel to add hover effect
        panel.addMouseListener(new java.awt.event.MouseAdapter() {//add action listener to the button that displays the task name
            //copy the action listener events below which affects the panel colour
            public void mouseEntered(java.awt.event.MouseEvent evt) {//when hovering over mouse
                panel.setBackground(coloursObject.getTitlePanelColour());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {//when cursor stops hovering over the mouse
                panel.setBackground(coloursObject.getMenuPanelColour());
            }
        });
        
        return panel;
    }
    
    //method is called to apply styles to components
    //uses the button styles object is used bu utilizing the apply button styles method
    public void applyStylesToComponents(){
        buttonStyles.applyButtonStyles(uploadButton);//apply styles from the colours object to the add new file button
        uploadButton.setBackground(coloursObject.getMenuPanelColour());
        
        buttonStyles.applyButtonStyles(deleteButton);
        deleteButton.setBackground(coloursObject.getMenuPanelColour());
        
        buttonStyles.applyButtonStyles(downloadButton);
        downloadButton.setBackground(coloursObject.getMenuPanelColour());
                
        controlsPanel.setBorder(BorderFactory.createMatteBorder(2, 1, 0, 0, coloursObject.getTitlePanelColour()));//set the border style for the panel
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        controlsPanel = new javax.swing.JPanel();
        uploadButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        downloadButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        displayPanel = new javax.swing.JPanel();

        setBackground(coloursObject.getContentPanelColour());

        controlsPanel.setBackground(coloursObject.getMenuPanelColour());

        uploadButton.setText("Upload File");
        uploadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadButtonActionPerformed(evt);
            }
        });

        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        downloadButton.setText("Download");
        downloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout controlsPanelLayout = new javax.swing.GroupLayout(controlsPanel);
        controlsPanel.setLayout(controlsPanelLayout);
        controlsPanelLayout.setHorizontalGroup(
            controlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlsPanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(deleteButton)
                .addGap(30, 30, 30)
                .addComponent(uploadButton)
                .addGap(48, 48, 48)
                .addComponent(downloadButton)
                .addContainerGap(443, Short.MAX_VALUE))
        );
        controlsPanelLayout.setVerticalGroup(
            controlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlsPanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(controlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(uploadButton)
                    .addComponent(deleteButton)
                    .addComponent(downloadButton))
                .addContainerGap(43, Short.MAX_VALUE))
        );

        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setOpaque(false);

        displayPanel.setBackground(coloursObject.getContentPanelColour());
        displayPanel.setPreferredSize(super.getPreferredSize());

        javax.swing.GroupLayout displayPanelLayout = new javax.swing.GroupLayout(displayPanel);
        displayPanel.setLayout(displayPanelLayout);
        displayPanelLayout.setHorizontalGroup(
            displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 783, Short.MAX_VALUE)
        );
        displayPanelLayout.setVerticalGroup(
            displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 345, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(displayPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(controlsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(controlsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void uploadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uploadButtonActionPerformed
        //select the file then upload it
        try{
            File file = getSelectedFile();
            uploadFile(file);
            
        }catch(Exception exc){System.out.println("dropbox: uploading error " + exc);}
    }//GEN-LAST:event_uploadButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        try{
            for(int i = 0; i < filesFoldersList.size(); i++){
                FileandFolderPanel panel = filesFoldersList.get(i);
                if(panel.removeRadioButton.isSelected()){
                    removeFile(panel.name);
                    displayPanel.remove(panel);
                    displayPanel.revalidate();
                    displayPanel.repaint();
                }
            }
        }catch(Exception exc){}
    }//GEN-LAST:event_deleteButtonActionPerformed

    //method is called upon pressing of the download button
    //any selected files are saved as a file explorer window opens with the ability to set a location and name
    private void downloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downloadButtonActionPerformed
        try{
            for(int i = 0; i < filesFoldersList.size(); i++){//loop through the list of selected panels
                FileandFolderPanel panel = filesFoldersList.get(i);//obtain a reference to the indexed panel
                if(panel.removeRadioButton.isSelected()){//if the panels radio button is selected
                    System.out.println("dropbox: downloading: " + panel.name);//print that a file is being downloaded
                    
                    //obtain access to the drop box file
                    String filename = panel.name;//obtain the name of the file via the panel object
                    URL download = new URL("https://www.dropbox.com/s/jo5nx5risfa6vte/" + filename);//create url for the dropbox file(if filename has a spcae, error will occur)
                    ReadableByteChannel rbc = Channels.newChannel(download.openStream());//create a channel to read
                    
                    //open a file explorer
                    JFrame parentFrame = new JFrame();//create a jframe
                    JFileChooser fileChooser = new JFileChooser();//create a file chooser object
                    fileChooser.setDialogTitle("Specify a location to save");//set the dialog for the top of the page
                    
                    //get verification of users choice
                    String savePath = "";//create variable for holding the save path ste by the user
                    int userSelection = fileChooser.showSaveDialog(parentFrame);//obtain  the users selection
                    if (userSelection == JFileChooser.APPROVE_OPTION) {//if the selection is the approve option
                        File fileToSave = fileChooser.getSelectedFile();//then create a reference to the file
                        savePath = fileToSave.getAbsolutePath(); //set the save path var to the path from the file
                    }
                            
                    //save the file
                    FileOutputStream fileOut = new FileOutputStream(savePath + filename);//save the file in an output stream
                    fileOut.getChannel().transferFrom(rbc, 0, 1 << 24);
                    fileOut.flush();
                    fileOut.close();
                    rbc.close();
                }
            }
            
        }catch(Exception e){System.out.println("dropbox: error downloading: " + e);}
    }//GEN-LAST:event_downloadButtonActionPerformed
    
    //method is called to remove a file from drop box
    public void removeFile(String name) throws IOException{
        try{
            System.out.println("dropBox: begin file deletion");
            
            
            File file = new File(name);//create the file object
            System.out.println("deleting " + file.getName());
            //FileInputStream in = new FileInputStream("/"+file);
            
            client.files().deleteV2("/" + file.getName());//remove the indicated file
                        
        } catch (Exception ex) {
            System.out.println(ex);
        }
        
        //refillDisplayPanel();//call method to repaint the display panel
    }
    
    //method is called to obtain the selected file using a file chooser
    //this open the file explorer to the user
    public File getSelectedFile(){
        File selectedFile = null;
        JFileChooser chooser = new JFileChooser(); 
        int returnVal = chooser.showOpenDialog(DropBoxGUI.this);
        
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            selectedFile = chooser.getSelectedFile();
            //This is where a real application would open the file.
            //log.append("Opening: " + file.getName() + "." + newline);
        
        }
        System.out.println("Uploading file: " + selectedFile);
        return selectedFile;
    }
    
    //method is called to upload the file
    public void uploadFile(File file) throws IOException{
        //Upload "test.txt" to Dropbox
        try{
            System.out.println("dropBox: begin file upload");
            
            FileInputStream in = new FileInputStream(file);
            
            client.files().uploadBuilder("/" + file.getName()).uploadAndFinish(in);
            
            FileandFolderPanel panel = createFileFolderPanel("File", file.getName());
            filesFoldersList.add(panel);
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex);
        }
        
        refillDisplayPanel();//call method to repaint the display panel
    }
    
    //method is called to refill the display panel with the updated list of panels
    public void refillDisplayPanel(){
        displayPanel.removeAll();
        for(int i = 0; i < filesFoldersList.size(); i++){
            displayPanel.add(filesFoldersList.get(i));
        }
        displayPanel.repaint();
        displayPanel.revalidate();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel controlsPanel;
    private javax.swing.JButton deleteButton;
    private javax.swing.JPanel displayPanel;
    private javax.swing.JButton downloadButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton uploadButton;
    // End of variables declaration//GEN-END:variables
}
