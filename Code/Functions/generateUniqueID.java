package Functions;


import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author ewand
 */
public class generateUniqueID {
    public String generate(ArrayList<String[][]> tables, String required){
        String uniqueID = "";//create uniqueID variable to hold the ID being generated
        
        ArrayList<String> listOfTakenIDs = new ArrayList<String>();//arraylist will hold the list of IDs that already exist
        String[][] currentTable;//create currentTable 2d array and set to user table by default
        int index = 0;//create variabel that represents index for column in table which contains the ID
        
        switch(required) {//switch based on the parsed required variable
            case "chatID":
                currentTable = tables.get(4);//create local version of the old chat table
                index = 0;//set index for the column
                break;
            case "statisticID":
                currentTable = tables.get(1);//create local version of the statistics table
                index = 0;//set index for the column
                break;
            case "taskID":
                currentTable = tables.get(0);//create local version of the old task table
                index = 0;//set index for the column
                break;
            case "notificationID":
                currentTable = tables.get(3);//create local version of the old task table
                index = 0;//set index for the column
                break;
            default://userID
                currentTable = tables.get(2);//create local version of the old user table
                index = 0;//set index for the column
                break;
          }
        
        Boolean accepted = false;//boolean exists to verify the generated unique ID
                
        //populate the list of taken IDs
        for(int i = 0; i < currentTable.length; i++){//for the number of rows in the chat table in the oldTables arraylist
            listOfTakenIDs.add(currentTable[i][index]);//add the ID from the table to the list of taken IDs
        }
        //while the unique ID is not accepted, set accepted to true and if the ID is already taken then put back to false
        while(!accepted){
            byte[] array = new byte[5];//new array of single bytes
            new Random().nextBytes(array);//new random
            uniqueID = new String(array, Charset.forName("UTF-8"));//set the 5 letter strings to the uniqueID
            
            accepted = true;//make accepted equal true
            //compare the generated ID against the list of taken ones
            for(int i = 0; i < listOfTakenIDs.size(); i++){//for the number of IDs in the list of taken IDs
                if(uniqueID.equals(listOfTakenIDs.get(i))){//if the generated ID already exists in the provided slot
                    accepted = false;//make accepted equal false
                }
            }
        }
        
        return uniqueID;//return the unique ID
    }
}
