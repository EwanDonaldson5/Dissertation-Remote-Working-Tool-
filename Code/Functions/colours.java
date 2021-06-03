package Functions;


import java.awt.Color;

/**
 *
 * @author ewand
 */
public class colours {
    
    //create colours to be used by specific components and their children
    Color borderColour;//variable that represents the colour of the borders
    Color contentPanelColour;//the colour of the content panel
    Color menuPanelColour;//the colour of the menu panel
    Color titlebarPanelColour;//the colour of the titlebar panel
    //create colours to be used by the buttons
    Color buttonTextColour;//the colour of the button text when cursor doesn't hover
    Color buttonTextHoverColour;//the colour of the button text when the cursor is hovering
    Color textColour;//the colour of text in the application
    Color barChartBarColour;
    
    public colours(String colourMode){//colourMode can be 'true' or 'false'
        //System.out.println("colours: dark mode is " + colourMode);
        
        if(colourMode.equals("true")){//if the setting for dark mode is true
            borderColour = new Color(42,45,53);//variable that represents the colour of the borders
            contentPanelColour = new Color(46,48,57);//the colour of the content panel
            menuPanelColour = new Color(42,45,53);//the colour of the menu panel
            titlebarPanelColour = new Color(36,38,45);//the colour of the titlebar panel
            buttonTextColour = new Color(112,118,127);//the colour of the button text when cursor doesn't hover
            buttonTextHoverColour = new Color(255,255,255);//the colour of the button text when the cursor is hovering
            textColour = new Color(250,250,250);//the colour of the text used
            barChartBarColour = new Color(250,250,250);//the colour used by the barChart bars
        }else{//if the setting for dark mode isn't true
            borderColour = new Color(190,190,190);//variable that represents the colour of the borders
            contentPanelColour = new Color(250,250,250);//the colour of the content panel
            menuPanelColour = new Color(190,190,190);//the colour of the menu panel
            titlebarPanelColour = new Color(150,150,150);//the colour of the titlebar panel
            buttonTextColour = new Color(20,20,20);//the colour of the button text when cursor doesn't hover
            buttonTextHoverColour = new Color(70,70,70);//the colour of the button text when the cursor is hovering
            textColour = new Color(15,15,15);//the colour of the text used
            barChartBarColour = new Color(15,15,15);//the colour used by the barChart bars
        }
    }
    
    public Color getBorderColour(){return borderColour;}//return the colour used for the borders
    
    public Color getContentPanelColour(){return contentPanelColour;}//return the colour used for the content panel
    
    public Color getMenuPanelColour(){return menuPanelColour;}//return the colour used for the menu panel
    
    public Color getTitlePanelColour(){return titlebarPanelColour;}//return the colour used for the title bar
    
    public Color getButtonTextColour(){return buttonTextColour;}//return the colour used for any button text
    
    public Color getButtonTextHoverColour(){return buttonTextHoverColour;}//return the colour used for any button hover text
    
    public Color getTextColour(){return textColour;}//return the colour used for any text
    
    public Color getBarChartBarColour(){return barChartBarColour;}//return the colour used for any text 
}
