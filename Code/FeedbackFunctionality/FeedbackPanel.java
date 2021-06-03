package FeedbackFunctionality;

/**
 *
 * @author ewand
 */
public class FeedbackPanel extends javax.swing.JPanel {

    String name; 
    String feedback;
    
    public FeedbackPanel(String name, String feedback) {
        this.name = name;
        this.feedback = feedback;
        
        initComponents();
        setupComponents();
    }
    
    private void setupComponents(){
        nameLabel.setText(name);
        feedbackLabel.setText(feedback);
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nameLabel = new javax.swing.JLabel();
        feedbackLabel = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 204, 51));

        nameLabel.setText("name");

        feedbackLabel.setText("feedback");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(feedbackLabel)
                    .addComponent(nameLabel))
                .addContainerGap(300, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(nameLabel)
                .addGap(39, 39, 39)
                .addComponent(feedbackLabel)
                .addContainerGap(185, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel feedbackLabel;
    private javax.swing.JLabel nameLabel;
    // End of variables declaration//GEN-END:variables
}
