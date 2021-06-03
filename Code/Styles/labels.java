package Styles;

import Functions.accessConfigFile;
import Functions.colours;
import javax.swing.JLabel;

/**
 *
 * @author ewand
 */
public class labels {
    
    accessConfigFile accessConfig = new accessConfigFile();//create an object for accessing the config file
    String colourModeSettingValue = accessConfig.searchForSettingsValue("darkModeEnabled");
    colours coloursObject = new colours(colourModeSettingValue);//create an object for accessing different colours used in the program
    
    //when called it applys the styles to the provided jlabel
    public void applyLabelStyles(JLabel lab){
        lab.setBackground(coloursObject.getContentPanelColour());//set background colour
        lab.setForeground(coloursObject.getButtonTextColour());//set foreground colour
        lab.setFont(new java.awt.Font("Microsoft Tai Le", 0, 11));//set font and size
        
        lab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {//when hovering over mouse
                lab.setForeground(coloursObject.getButtonTextHoverColour());//the colour of the text when hovered over
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {//when cursor stops hovering over the mouse
                lab.setForeground(coloursObject.getButtonTextColour());//the colour of the text when the cursor is removed
            }
        });
    }
}
