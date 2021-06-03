package FeedbackFunctionality;

import Functions.colours;
import Styles.fonts;
import javax.swing.BorderFactory;
import javax.swing.border.CompoundBorder;

/**
 *
 * @author ewand
 */
public class InactiveTaskPanel extends javax.swing.JPanel {
    
    fonts fontObject;
    colours coloursObject;
    public String ID;
    public String name; 
    String details;
    String feedback;
    
    public InactiveTaskPanel(colours coloursObject, String ID, String name, String details, String feedback) {
        this.fontObject = new fonts();
        this.coloursObject = coloursObject;
        this.ID = ID;
        this.name = name;
        this.details = details;
        this.feedback = feedback;
        
        //check to see if the feedback var is empty
        //if it is then assign it defualt text informing the user
        if(this.feedback == null || this.feedback.equals("")){//if the feedback var is empty or set to null
            this.feedback = "No Available Feedback";
        }
        
        initComponents();
        setupSuper();//called to setup the most parent panel (the super)
        setupComponents();//called to setup any components that exist in this class
    }

    //method assigns styles to components on the panel
    private void setupComponents(){
        //set the default text for the labels
        nameLabel.setText(name);
        detailsLabel.setText(details);
        feedbackLabel.setText(feedback);
        
        //set the font for the name label
        nameLabel.setFont(fontObject.defaultFont);
        
        //set the font colour for the labels
        nameLabel.setForeground(coloursObject.getTextColour());
        detailsLabel.setForeground(coloursObject.getTextColour());
        feedbackLabel.setForeground(coloursObject.getTextColour());
    }
    
    //method is called to setup the jpanel class styles
    private void setupSuper(){
        setBackground(coloursObject.getMenuPanelColour());
        setBorder(new CompoundBorder(
            BorderFactory.createLineBorder(coloursObject.getContentPanelColour(),10),
            BorderFactory.createMatteBorder(1, 1, 1, 1, coloursObject.getTitlePanelColour())
        ));
    }
    
    public void updateFeedback(String newFeedback){
        feedback = newFeedback;
        feedbackLabel.setText(newFeedback);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nameLabel = new javax.swing.JLabel();
        detailsLabel = new javax.swing.JLabel();
        feedbackLabel = new javax.swing.JLabel();
        selectedRadioButton = new javax.swing.JRadioButton();

        setBackground(coloursObject.getContentPanelColour());

        nameLabel.setText("name");

        detailsLabel.setText("details");

        feedbackLabel.setText("feedback");

        selectedRadioButton.setOpaque(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 307, Short.MAX_VALUE)
                        .addComponent(selectedRadioButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(feedbackLabel)
                            .addComponent(detailsLabel))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(nameLabel)
                    .addComponent(selectedRadioButton))
                .addGap(18, 18, 18)
                .addComponent(detailsLabel)
                .addGap(18, 18, 18)
                .addComponent(feedbackLabel)
                .addContainerGap(197, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel detailsLabel;
    private javax.swing.JLabel feedbackLabel;
    private javax.swing.JLabel nameLabel;
    public javax.swing.JRadioButton selectedRadioButton;
    // End of variables declaration//GEN-END:variables
}
