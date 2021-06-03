
import Functions.SystemExit;
import Functions.database;
import Functions.accessConfigFile;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.lang.Thread;

/**
 * Created 29/10/2020
 * Ewan Donaldson
 */
public class remoteWorkingTool{
    
    static ArrayList<String[][]> tables = new ArrayList<String[][]>();
    
    public static void main(String[] args) {
           
        accessConfigFile accessConfig = new accessConfigFile();//create the object to access the config file
        ArrayList<String> settingsList = accessConfig.getSettingsList();//call method to obtain the settins list
        
        String settingValue = "";//string will hold the value obtained from the searchForSetting method
        String usersChoice = "";//string holds the choice of options the user chose from the dashboard choices
        
        if(settingsList.size() == 0){//if the arraylist generated is empty
            System.out.println("main: file read was empty");
        }else{
            String requestedSetting = "dashboardEnabled";//string holds the name for the dashboard setting
            settingValue = accessConfigFile.searchForSetting(requestedSetting, settingsList);//search for value corresponding to the requested setting
            
            if(settingValue.equals("true")){//if the value found for the dashboardEnabled setting is true
                dashboard dash = new dashboard();
                dash.setVisible(true);

                Boolean choiceHasBeenMade = false;//boolean variable is used prove if a match was found in the connect class
                while(choiceHasBeenMade == false){//while the boolean shows no match
                    try {//sleep has been added to stop the program from constantly checking if a login has occured 
                        TimeUnit.SECONDS.sleep(2);//sleep for 2 seconds
                    } catch (InterruptedException ex) {System.out.println(ex);}//print exception if one is caught

                    usersChoice = dash.getUserChoice();//get the users choice from the dashboard class

                    if(usersChoice.equals("create a company")){//if the user has chosen to create a company
                        System.out.println("main: user has chosen to create a company");
                        dash.setVisible(false);//set the dashboard visibility to false

                        choiceHasBeenMade = true;//update the boolean as a choice has been made by the user
                    }

                    if(usersChoice.equals("login or register")){//if the user has chosen to login or register an account
                        System.out.println("main: user has chosen login or register");
                        dash.setVisible(false);//set the dashboard visibility to false

                        choiceHasBeenMade = true;//update the boolean as a choice has been made by the user
                    }
                }
            }else{//if the value found for the dashboardEnables setting is false
                usersChoice = "login or register";//set the usersChoice variable to the login/register option so the program goes stright there
            }
        }
        
        if(usersChoice.equals("create a company")){//if the user has chosen to create a company
            
            createNewCompany create = new createNewCompany();//create a new createCompany jframe
            create.setVisible(true);//set the new jframe to visible
            
            Boolean companyHasBeenCreated = false;//boolean variable is used state if the company has been successfully created
            while(companyHasBeenCreated == false){//while the boolean shows no match
                try {//sleep has been added to stop the program from constantly checking if a login has occured 
                    TimeUnit.SECONDS.sleep(2);//sleep for 2 seconds
                } catch (InterruptedException ex) {System.out.println(ex);}//print exception if one is caught
                
                companyHasBeenCreated = create.checkCreationAttempt();//get the current state of the successfull creation boolean
                
                if(companyHasBeenCreated == true){//if the company database was successfully created
                    usersChoice = "login or register";//set the user choice variable to allow for the user to login once a new company has been created
                    create.setVisible(false);//set the new createCompany jframe visibility to false
                }
            }
        }
        
        
        if(usersChoice.equals("login or register")){//if the user has chosen to login or register an account
            
            loginRegister log = new loginRegister();//create a new login/register form
            log.setVisible(true);//set the visibility of the form to true

            String databaseName = "";
            String userName = "";
            String password = "";

            Boolean companyMatch = false;//boolean variable is used prove if a match was found in the connect class
            while(companyMatch == false){//while the boolean shows no match
                try {//sleep has been added to stop the program from constantly checking if a login has occured 
                    TimeUnit.SECONDS.sleep(2);//sleep for 2 seconds
                } catch (InterruptedException ex) {System.out.println(ex);}//print exception if one is caught

                companyMatch = log.checkLogin();//check the state of the boolean and set the local one to it

                if(companyMatch == true){//if the boolean is now set to true
                    System.out.println("main: user is logged in");
                    log.setVisible(false);
                    databaseName = log.getCompanyID();
                    userName = log.getUsername();
                    password = log.getPassword();
                    break;
                }
            }

            //create the database class to connect to the database
            database dbs = new database(databaseName, userName, password);
            dbs.run();
            dbs.startLoginRecord();
            tables = dbs.getArrayList();
            
            setLookAndFeel();//call the method that sets the look and feel

            UI gui = new UI(tables, databaseName, userName, password, dbs);//load the ui for the main program
            gui.setVisible(true);//set the gui
            
            //create version of the systemExit class
            //this runs code when the application has shutdown
            SystemExit exit = new SystemExit(dbs, databaseName, userName, password);
            
        }
    }
        
    //method is called to reduce the tables to contain only the data relevant to the user
    public static void reduceTables(String companyID, String username){
        //index for accessing the table from the tables arraylist, and index for accessing the column in the database
        int[] taskIndexes = {0,6};//first table in the arraylist, and seventh column in the database task table is the organisationID
        int[] orgIndexes = {1,0};//second table in arraylist, and first column in table on database
        int[] statsIndexes = {2,0};//third table in the array list, and first column in database table
        int[] userIndexes = {3,4};//fourth table in the tables arraylist, and fifth column in the database table is the userID
        int[] notificationIndexes = {4,1};//fifth table in the tables arraylist, and fifth column in the database table is the userID
        int[] chatIndexes = {5,2,4};//sixth table in the tables arraylist, and third column and fifth column of the chat table on the dbs
        int[] currentIndexes = {};//array is set to one of the above list of indexes within the for loop below
        int tableNumber = 0;//variable represents index to the first slot in the currentIndexes
        int columnNumber = 1;//variable represents index to the second slot in the currentIndexes
        int thirdNumber = 2;//variable represents index to the third slot in the currentIndexes
        
        for(int i = 0; i < tables.size(); i++){//for the number of tables that exist
            switch(i) {//switch statement for the incrementing variable i
                case 0://if i is 0
                    currentIndexes = taskIndexes;//set the currentIndexes to given indexes
                    break;
                case 1://if i is 1
                    currentIndexes = orgIndexes;//set the currentIndexes to given indexes
                    break;
                case 2://if i is 2
                    currentIndexes = statsIndexes;//set the currentIndexes to given indexes
                    break; 
                case 3://if i is 3
                    currentIndexes = userIndexes;//set the currentIndexes to given indexes
                    break;
                case 4://if i is 4
                    currentIndexes = notificationIndexes;//set the currentIndexes to given indexes
                    break;
                default://if i is not one of the values above
                    currentIndexes = chatIndexes;//set the currentIndexes to given indexes
                    break;
            }
        
            //reduce user table to contain only the users with the same company ID
            String[][] oldTable = tables.get(currentIndexes[tableNumber]);

            ArrayList<String[]> tempList = new ArrayList<String[]>();//create a temporary list of the record rows from the database that are kept
            int countOfRows = 0;//variable to count the rows read
            int countOfCols;//variable to count the columns

            for(int c = 0; c < oldTable.length; c++){//for the number of rows in the user table
                if(companyID.equals(oldTable[c][currentIndexes[columnNumber]])){//if the company id provided by the user is equal to the one currently indexed
                    //keep the row
                    tempList.add(oldTable[c]);//add record row to the temporary list
                    countOfRows = countOfRows + 1;//increment the count of rows
                    
                }else if(username.equals(oldTable[c][currentIndexes[columnNumber]])){//if the username provided by the user is equal to the one currently indexed
                    //keep the row
                    tempList.add(oldTable[c]);//add record row to the temporary list
                    countOfRows = countOfRows + 1;//increment the count of rows
                    
                }else if(currentIndexes.length == 3){//if the currentIndexes has three values, eval against the third
                    if(username.equals(oldTable[c][currentIndexes[thirdNumber]])){//if the username is equal to the values indexed
                        //keep the row
                        tempList.add(oldTable[c]);//add record row to the temporary list
                        countOfRows = countOfRows + 1;//increment the count of rows
                    }
                }
            }
            
            countOfCols = tempList.get(0).length;//set the count of columns to the length of an array within the arraylist
            String[][] newTable = new String[countOfRows][countOfCols];//create a new user table

            for(int o = 0; o < countOfRows; o++){//for the number of rows
                newTable[o] = tempList.get(o);//add to the new user table
            }

            tables.set(currentIndexes[tableNumber],newTable);//replaces the old user table with the new one in the tables array list
        }
    }
    
    /* Set the look and feel of the application to windows */
    public static void setLookAndFeel(){
        /* Set to Windows look and feel */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
}
