package DropBoxFunctionality;

import Functions.colours;
import Styles.fonts;
import com.dropbox.core.v2.DbxClientV2;
import javax.swing.BorderFactory;
import javax.swing.border.CompoundBorder;

/**
 *
 * @author ewand
 */
public class FileandFolderPanel extends javax.swing.JPanel {

    String type;
    String name;
    colours coloursObject;
    fonts fontObject;
    static DbxClientV2 client;
    
    public FileandFolderPanel(String type, String name, colours coloursObject, DbxClientV2 client) {
        this.type = type;
        this.name = name;
        this.coloursObject = coloursObject;
        this.fontObject = new fonts();
        this.client = client;
        
        initComponents();
        
        setBackground(coloursObject.getMenuPanelColour());
        setBorder(new CompoundBorder(
            BorderFactory.createLineBorder(coloursObject.getContentPanelColour(),10),
            BorderFactory.createMatteBorder(1, 1, 1, 1, coloursObject.getTitlePanelColour())
        ));
        
        updatePanel();
        
        //add a listener for when the panel is clicked
//        addMouseListener(new java.awt.event.MouseAdapter() {//add listener for the label
//            public void mouseClicked(java.awt.event.MouseEvent evt) {//when hovering over mouse
//                
//                File file = new File(name);
//                try {
//                    OutputStream outputStream = new FileOutputStream(file);
//                    FileMetadata metadata = client.files()
//                        .downloadBuilder("TESTdl.txt")
//                        .download(outputStream);
//                    
//                } catch (Exception ex) {System.out.println("dropboxFile: error opening file: " + ex);}
//                
//                
//            }
//        });
    }

    public void updatePanel(){
        //set the text colour for the labels
        typeLabel.setForeground(coloursObject.getTextColour());
        nameLabel.setForeground(coloursObject.getTextColour());
        
        //set the default text for the labels
        typeLabel.setText(type);
        nameLabel.setText(name);
        
        //set the font for the name label
        nameLabel.setFont(fontObject.defaultFont);
        
        //removeRadioButton.setBackground(coloursObject.getMenuPanelColour());
        removeRadioButton.setOpaque(false);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        typeLabel = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        removeRadioButton = new javax.swing.JRadioButton();

        typeLabel.setText("Type");

        nameLabel.setText("Name");

        removeRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeRadioButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nameLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(typeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 237, Short.MAX_VALUE)
                        .addComponent(removeRadioButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(removeRadioButton)
                    .addComponent(typeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nameLabel)
                .addContainerGap(229, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void removeRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeRadioButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_removeRadioButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel nameLabel;
    public javax.swing.JRadioButton removeRadioButton;
    private javax.swing.JLabel typeLabel;
    // End of variables declaration//GEN-END:variables
}
