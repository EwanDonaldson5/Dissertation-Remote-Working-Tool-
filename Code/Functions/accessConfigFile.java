package Functions;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author ewand
 */
public class accessConfigFile {
    
    //arraylist contains the default settings strings
    static ArrayList<String> defaultSettings = new ArrayList<String>(); 
    static int defaultSettingsCount = 3;
    //arraylist contains the settings list obtained from the users file
    ArrayList<String> settingsList = new ArrayList<String>();//arraylist holds the settings that have been set by the user
    
    //name of the settings file and the path to access it
    final static String fileName = "config.txt";
    final static Path pathToFile = Paths.get(fileName);
    
    public accessConfigFile(){
        //create the default settings and add them to the default settings arraylist
        final String settingDashEnabled = "dashboardEnabled=true";//create the setting for enabling the dashboard
        defaultSettings.add(settingDashEnabled);//add the setting to the default settings list
        final String settingShowDashInfo = "showDashInfo=true";//create the setting for showing dash infromation
        defaultSettings.add(settingShowDashInfo);//add setting to the default settings list
        final String settingDarkModeEnabled = "darkModeEnabled=true";//create the setting for dark mode
        defaultSettings.add(settingDarkModeEnabled);//add setting the default settings list
//        final String settingShowSocials = "showSocials";//create the setting for showing socials
//        defaultSettings.add(settingShowSocials);//add setting to the default settings list
        
        createConfigFile();//call method. This will either create a file or skip past if one exists
        settingsList = readFile();//set the settings list to the return from the readFile method
    }
    
    //method is called to create a file for holding system variable settings
    public static void createConfigFile(){
        try {
            File myObj = new File(fileName);//create an file object called config.txt
            if (myObj.createNewFile()) {//if a file can be created with that name
                //System.out.println("access: file created: " + myObj.getName());
                PrintStream fileStream = new PrintStream(new File(fileName));//create a print stream for the config file
                
                int settingsCount = defaultSettings.size();//get the number of default settings that exist
                for(int i = 0; i < settingsCount; i++){//loop for the number of settings that exist
                    fileStream.println(defaultSettings.get(i));//add the currently indexed setting to the file
                }
                fileStream.close();
            } else {
                //System.out.println("access: config file already exists");
                ArrayList<String> tempSettingsList = readFile();//get the most recent version of the users config file
                int listLength = tempSettingsList.size();//get the size of the temp settings list
                
                //System.out.println("OUTPUT: " + listLength + "/" + defaultSettingsCount);
                if(listLength != defaultSettingsCount){//if the temp list length is not the same as the default list (i.e the file isn't the corrent length)
                    //System.out.println("access: config file isn't correct length");
                    Files.delete(pathToFile);//delete the config file that exists
                    //System.out.println("access: file has been deleted");
                    createConfigFile();//call create config method to create a file now that one doesn't exist
                }
            }
        } catch (IOException e) {
            //System.out.println("main an error occurred");
            e.printStackTrace();
        }
    }
    
    //method creates a config file or updates the variables field if it already exists
    public void saveFile(){
        //System.out.println();//leave a blank line to aid with visual debugging
        //System.out.println("access:-SAVING CONFIG FILE WITH NEW VALUES");
        try {
            File myObj = new File(fileName);
            if (!myObj.createNewFile()) {        
                
                PrintStream fileStream = new PrintStream(new File(fileName));
                for(int i = 0; i < settingsList.size(); i++){//for the number of strings in the list of settings
                   fileStream.println(settingsList.get(i));//add the record from the list
                   //System.out.println("access: added " + settingsList.get(i) + " to the file");
                }
                fileStream.close();
                
            } else {
                //System.out.println("access: file doesn't exists.");
            }
        } catch (IOException e) {
            //System.out.println("access: an error occurred.");
            e.printStackTrace();
        }
    }
    
    //method flips any String from false to true, and true to false
    //takes in a string that represents a boolean
    public String flipBooleanSetting(String bool){
        if(bool.equals("true") || bool.equals("True")){//if the parsed value is true
            bool = "false";//set the value to false
        }else if(bool.equals("false") || bool.equals("False")){//if the parsed value is false
            bool = "true";//set the value to true
        }else{
            //System.out.println("access: error while flipping String bool");
        }
        return bool;//return the flipped String that represents a boolean
    }
    
    //method updates a record in the settingsList
    //does this by obtaining the index of the setting in the list
    //then removes the old record and adds the newer one in its place
    public void updateSettingsList(String selectedSetting, String value){
        String updatedSettingsRecord = selectedSetting + "=" + value;//concatenate strings together to create a record that can exist in the config file
        int index = 0;//integer will hold the index for within the settingsList
        switch(selectedSetting){//read and compare the selected setting
            case "dashboardEnabled":
                index = 0;//set index to zero, i.e the first setting from the list
                break;
            case "showDashInfo":
                index = 1;//set index to the second setting in the list
                break;
            case "darkModeEnabled":
                index = 2;//set index to the second setting in the list
                break;
//            case "showSocials":
//                index = 3;//set the index to the third setting in the list
//                break;
            default:
                break;
        }
        settingsList.remove(index);//remove the old version of the setting
        settingsList.add(index, updatedSettingsRecord);//add the updated version of the setting and the provided index
    }
    
    //method searches for the given setting and then returns the value
    public String searchForSettingsValue(String requestedSetting){
        int listSize = settingsList.size();
        String settingValue = "";//string variable holds the value being returned from the searched setting
        
        //loops to read through all the records in the settings array list
        //the record is copied from the arraylist and is then split into two parts at the '=' sign
        //two parts are then put into an array
        //then if the requested setting is found, the corresponding value is then returned
        for(int i = 0; i < listSize; i++){//loop for the number of settings in the array list
            String data = settingsList.get(i);//string holds the individual string from the settings list
            String[] parts = data.split("=");//string array holds the two seperate parts of the setting record from the text file
            
            if(requestedSetting.equals(parts[0])){//if the requested setting is equal to the first part of the read setting
                //System.out.println("access: found the requested setting");
                settingValue = parts[1];//set the setting value variable to the second part of the array
                //System.out.println("access: obtained the value of " + settingValue);
            }
        }
        return settingValue;//return the value from the settings
    }
    
    //method searches for the given setting and then returns the value
    public static String searchForSetting(String requestedSetting, ArrayList<String> listOfSettings){
        //System.out.println();//leave a blank line to aid with visual debugging
        //System.out.println("main:-SEARCHING FOR " + requestedSetting + " FROM CONFIG");
        
        int listSize = listOfSettings.size();
        String settingValue = "";//string variable holds the value being returned from the searched setting
        
        //loops to read through all the records in the settings array list
        //the record is copied from the arraylist and is then split into two parts at the '=' sign
        //two parts are then put into an array
        //then if the requested setting is found, the corresponding value is then returned
        for(int i = 0; i < listSize; i++){//loop for the number of settings in the array list
            String data = listOfSettings.get(i);//string holds the individual string from the settings list
            String[] parts = data.split("=");//string array holds the two seperate parts of the setting record from the text file
            
            if(requestedSetting.equals(parts[0])){//if the requested setting is equal to the first part of the read setting
                //System.out.println("main: found the requested setting");
                settingValue = parts[1];//set the setting value variable to the second part of the array
                //System.out.println("main: obtained the relevant value");
            }
        }
        return settingValue;//return the value from the settings
    }
    
    //method is called to read the config text file
    public static ArrayList<String> readFile(){
        //System.out.println();//leave a blank line to aid with visual debugging
        //System.out.println("access:-READING THE CONFIG FILE");
        
        ArrayList<String> listOfSettings = new ArrayList<String>();
                
        try {
            File myObj = new File(fileName);//create a file object
            Scanner myReader = new Scanner(myObj);//create a scanner object to read from the text file
            
            ArrayList<String> dataList = new ArrayList<String>();
            while (myReader.hasNextLine()) {//while the scanner has something to read
                String data = myReader.nextLine();//hold the data read in a string variable
                //System.out.println("access: reading line from file");
                //System.out.println("access: found " + data);
                dataList.add(data);//add the string from the text file to the list of strings
            }
            
            listOfSettings = dataList;
            
            myReader.close();
        } catch (FileNotFoundException e) {
            //System.out.println("settings: error occurred while reading file");
            e.printStackTrace();
        }
        
        return listOfSettings;
    }
    
    //method returns the list of settings
    public ArrayList<String> getSettingsList(){
        return settingsList;
    }
    
}
