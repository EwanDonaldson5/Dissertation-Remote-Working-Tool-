package Styles;

import java.awt.Font;

/**
 *
 * @author ewand
 */
public class fonts {
    public Font defaultFont;
    
    public fonts(){
        defaultFont = new java.awt.Font("Microsoft Tai Le", 0, 16);
    }
    
    public Font getDefaultFont(){return defaultFont;}
}
