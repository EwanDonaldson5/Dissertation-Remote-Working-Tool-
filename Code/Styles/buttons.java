package Styles;

import Functions.accessConfigFile;
import Functions.colours;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;

/**
 *
 * @author ewand
 */
public class buttons {
    
    accessConfigFile accessConfig = new accessConfigFile();//create an object for accessing the config file
    String colourModeSettingValue = accessConfig.searchForSettingsValue("darkModeEnabled");
    colours coloursObject = new colours(colourModeSettingValue);//create an object for accessing different colours used in the program
    
    
    //when called it applys the styles to the provided jbutton
    //this version of the method exists for the titlebar button styles
    public void applyButtonStyles(JButton button, String type){
        button.setBackground(coloursObject.getTitlePanelColour());//set background colour
        button.setForeground(coloursObject.getButtonTextColour());//set foreground colour
        button.setFont(new java.awt.Font("Microsoft Tai Le", 0, 16));//set font and size
        button.setBorder(null);//remove the border//remove any border
        button.setFocusPainted(false);//make not focusable (custom hover effect overides this)
        button.setPreferredSize(new Dimension(50,25));//set preferred dimensions for the close application button
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {//when hovering over mouse
                button.setForeground(coloursObject.getButtonTextHoverColour());//the colour of the text when hovered over
                if(type == "close"){//if the close button is being edited
                    button.setBackground(new Color(237,64,64));//colour chosen for the 
                }
                if(type == "scale"){//if the fullscreen button is being edited
                    button.setBackground(coloursObject.getMenuPanelColour());
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {//when cursor stops hovering over the mouse
                button.setForeground(coloursObject.getButtonTextColour());//the colour of the text when the cursor is removed
                button.setBackground(coloursObject.getTitlePanelColour());
            }
        });
    }
    
    //when called it applys the styles to the provided jbutton
    //this version of the method is called for the generic styling of button
    public void applyButtonStyles(JButton button){
        button.setBackground(coloursObject.getContentPanelColour());//set background colour
        button.setForeground(coloursObject.getButtonTextColour());//set foreground colour
        button.setFont(new java.awt.Font("Microsoft Tai Le", 0, 14));//set font and size
        button.setBorder(null);//remove the border//remove any border
        button.setFocusPainted(false);//make not focusable (custom hover effect overides this)
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {//when hovering over mouse
                button.setForeground(coloursObject.getButtonTextHoverColour());//the colour of the text when hovered over
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {//when cursor stops hovering over the mouse
                button.setForeground(coloursObject.getButtonTextColour());//the colour of the text when the cursor is removed
            }
        });
    }
}
