package Features;

import Functions.accessConfigFile;
import Functions.colours;
import ProductivityFunctionality.messagesBarChart;
import ProductivityFunctionality.productivityControls;
import ProductivityFunctionality.timeWorkingPieChart;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author ewand
 */
public class productivity{
    String currentGraphSelection = "";//variable holds the currently chosen chart selection
    String oldGraphSelection = "";//variable holds the old current selection, from before the page was updated
    String currentTimePeriodSelection = "";
    String oldTimePeriodSelection = "";
    
    public ArrayList<String[][]> tables = new ArrayList<String[][]>();//the arraylist containing the tables from the database
    
    accessConfigFile accessConfig = new accessConfigFile();//create an object for accessing the config file
    String colourModeSettingValue = accessConfig.searchForSettingsValue("darkModeEnabled");
    colours coloursObject = new colours(colourModeSettingValue);//create an object for accessing different colours used in the program
    
    productivityControls controls;
    
    messagesBarChart msgChart;
    timeWorkingPieChart twpc;
    
    ClickListener cl = new ClickListener();//create a click listener class for the controls in the jpanel
    
    public JPanel productivityPanel = new JPanel();
    
    public productivity(ArrayList<String[][]> tables){
        this.tables = tables;
        
        productivityPanel.setBackground(coloursObject.getContentPanelColour());//set the background colour of the root JPanel to the content panel colour
        productivityPanel.setLayout(new GridLayout(0,2));//set the horizontal layout of the jpanel
        
        controls = new productivityControls();//create a productivityControls object
        addActionListeners();//adds action listeners to the control components
        
        productivityPanel.add(controls);//add the controls panel to the productivity panel
        //add(twpc);
        
        //set the default selections for graph type and time period
        //then update the display labels to by default show the default selection
        controls.messagesRadioButton.setSelected(true);
        controls.monthlyRadioButton.setSelected(true);
        //setCurrentSelection("Messages", "Monthly");
        controls.chosenGraphLabel.setText(currentGraphSelection);
        controls.currentTimePeriodLabel.setText(currentTimePeriodSelection);
        
        
    }
    
    //method adds action listeners to components of the controls jpanels
    public void addActionListeners(){
        controls.timeWorkingRadioButton.addActionListener(cl);//add the action listener to the time spent working radio button
        controls.messagesRadioButton.addActionListener(cl);//add the action listener to the time messages radio button
        
        //add action listener to the radio buttons which control the time frame
        controls.dailyRadioButton.addActionListener(cl);
        controls.weeklyRadioButton.addActionListener(cl);
        controls.monthlyRadioButton.addActionListener(cl);
    }
    
    public void setCurrentSelection(String graphSelection, String timePeriodSelection){
        currentGraphSelection = graphSelection;
        currentTimePeriodSelection = timePeriodSelection;
        
        //msgChart.timePeriod = currentTimePeriodSelection;//update the timePeriod var in the messages bar chart
        //twpc.chosenTimePeriod = currentTimePeriodSelection;//update the timePeriod var in the time working pie chart
        
        //System.out.println("productivity: current selection is " + currentTimePeriodSelection + " " + currentGraphSelection);
        updateDisplayedGraph();
    }
    
    //method changes the displayed graph depending on the currentSelection string
    //first the old graph displayed is removed
    //second the new graph is displayed and the public variable for the chosen in the graph classes is updated
    public void updateDisplayedGraph(){
        //remove the old graph from the panel
        if(oldGraphSelection.equals("TimeWorking")){
            productivityPanel.remove(twpc);//remove the current chart
        }
        if(oldGraphSelection.equals("Messages")){
            productivityPanel.remove(msgChart);//remove the messages chart
        }
        
        //add the user chosen graph to the panel
        if(currentGraphSelection.equals("TimeWorking")){//if the currentSelection is on TimeWorking
            twpc = new timeWorkingPieChart(tables, currentTimePeriodSelection);
            //twpc.setTimePeriod(currentTimePeriodSelection);//call method in the time working graph class to update the users choice of time period
            productivityPanel.add(twpc);//add the time working chart
            productivityPanel.revalidate();
            productivityPanel.repaint();
        }
        if(currentGraphSelection.equals("Messages")){//if the currentSelection is on Messages
            msgChart = new messagesBarChart(tables, currentTimePeriodSelection);
            //msgChart.setTimePeriod(currentTimePeriodSelection);//call method in the messeges graph class to update the users choice of time period
            productivityPanel.add(msgChart);//add the messages chart
            productivityPanel.revalidate();
            productivityPanel.repaint();
        }
    }
    
    //class is a click listener for the jpanel components
    private class ClickListener implements ActionListener {
        String graph = "Time Working";//string holds the selection
        String timePeriod = "Daily";//string hold the selected time period
                
        public void actionPerformed(ActionEvent e) {//when an action is performed
            
            //before the currentSelection variable is changed create a copy of what was previously displayed
            oldGraphSelection = currentGraphSelection;//create a copy of old graph selection 
            oldTimePeriodSelection = currentTimePeriodSelection;//create a copy of the old time period selection
            
            if (e.getSource() == controls.timeWorkingRadioButton){//if the source of the action was the time radio button
                graph = "TimeWorking";//set selection to time working
                controls.chosenGraphLabel.setText("Time Working");
            }
            if (e.getSource() == controls.messagesRadioButton){//if the source of the action was the time radio button
                graph = "Messages";//set selection to time working
                controls.chosenGraphLabel.setText("Messages");
            }
            if (e.getSource() == controls.dailyRadioButton){//if the source of the action was the time radio button
                timePeriod = "Daily";//set selection to time working
                controls.currentTimePeriodLabel.setText("Daily");
            }
            if (e.getSource() == controls.weeklyRadioButton){//if the source of the action was the time radio button
                timePeriod = "Weekly";//set selection to time working
                controls.currentTimePeriodLabel.setText("Weekly");
            }
            if (e.getSource() == controls.monthlyRadioButton){//if the source of the action was the time radio button
                timePeriod = "Monthly";//set selection to time working
                controls.currentTimePeriodLabel.setText("Monthly");
            }
            updateSelection();//call the clickListeners internal method
            
        }
        public void updateSelection(){//method calls the public update method       
            setCurrentSelection(graph, timePeriod);//call method to update the current selection
        }
    }
}
    