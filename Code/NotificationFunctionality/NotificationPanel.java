package NotificationFunctionality;

import Functions.colours;
import Styles.fonts;
import javax.swing.BorderFactory;
import javax.swing.border.CompoundBorder;

/**
 *
 * @author ewand
 */
public class NotificationPanel extends javax.swing.JPanel {

    colours coloursObject;
    fonts fontsObject = new fonts();
    
    String name;
    String content;
    String time;
    
    public NotificationPanel(colours coloursObject, String name, String content, String timeSent) {
        this.coloursObject = coloursObject;
        this.name = name;
        this.content = content;
        this.time = timeSent;
        
        initComponents();
        
        setBackground(coloursObject.getMenuPanelColour());
        setBorder(new CompoundBorder(
            BorderFactory.createLineBorder(coloursObject.getContentPanelColour(),10),
            BorderFactory.createMatteBorder(1, 1, 1, 1, coloursObject.getTitlePanelColour())
        ));
        
        setupComponents();
    }

    public void setupComponents(){
        nameLabel.setFont(fontsObject.defaultFont);
        contentLabel.setFont(fontsObject.defaultFont);
        timeLabel.setFont(fontsObject.defaultFont);
        
        nameLabel.setForeground(coloursObject.getTextColour());
        contentLabel.setForeground(coloursObject.getTextColour());
        timeLabel.setForeground(coloursObject.getTextColour());
        
        nameLabel.setText(name);
        contentLabel.setText(content);
        timeLabel.setText(time);
        
        removeRadioButton.setOpaque(false);
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        timeLabel = new javax.swing.JLabel();
        contentLabel = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        removeRadioButton = new javax.swing.JRadioButton();

        setBackground(coloursObject.getMenuPanelColour());

        jLabel1.setFont(fontsObject.defaultFont);
        jLabel1.setForeground(coloursObject.getTextColour());
        jLabel1.setText("Name:");

        jLabel2.setFont(fontsObject.defaultFont);
        jLabel2.setForeground(coloursObject.getTextColour());
        jLabel2.setText("Content:");

        jLabel3.setFont(fontsObject.defaultFont);
        jLabel3.setForeground(coloursObject.getTextColour());
        jLabel3.setText("Time Sent:");

        timeLabel.setText("time");

        contentLabel.setText("content");

        nameLabel.setText("name");

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
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 125, Short.MAX_VALUE)
                        .addComponent(removeRadioButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(timeLabel)
                            .addComponent(contentLabel))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(nameLabel))
                    .addComponent(removeRadioButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(contentLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(timeLabel)
                    .addComponent(jLabel3))
                .addContainerGap(89, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void removeRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeRadioButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_removeRadioButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel contentLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel nameLabel;
    public javax.swing.JRadioButton removeRadioButton;
    private javax.swing.JLabel timeLabel;
    // End of variables declaration//GEN-END:variables
}
