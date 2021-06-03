package Functions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author ewand
 */
public class UpdateDatabase {
    
    //variables holding the data used to connect to the database
    String url = "jdbc:mysql://localhost:3306/";
    String username; 
    String databaseName;  
    String loggedInUser;
    String password;
    
    int noOfTables = 5;//6 tables exist
    
    public ArrayList<String[][]> tables;//arraylist for holding the tables
    ArrayList<String> tableNames = new ArrayList<String>();
    
    public UpdateDatabase(String databaseName, String loggedInUser, String password){
        this.databaseName = databaseName;
        this.loggedInUser = loggedInUser;
        this.password = password;
        
        tables = getNewTables();
    }
    
    public ArrayList<String[][]> getNewTables(){
        ArrayList<String[][]> newTables = new ArrayList<String[][]>();
        try{
            noOfTables = getNoOfTables();
            System.out.println("COUNT OF TABLES IS " + noOfTables);
            
            //get a connection to the database
            System.out.println("Connecting to " + databaseName + " with username " + loggedInUser + " with password " + password);
            Connection connection = DriverManager.getConnection(url + databaseName, loggedInUser, password);
            
            for(int i = 1; i <= noOfTables; i++){
                int rows = 0;//variable holds the number of rows in the result set
                int cols = 0;//variable holds the number of cols in the result set
                Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);//create statement for use in querying the db
                String query = "";//string is used to hold the query that is to be executed
                
                switch(i){
                    case 1:
                        //query = "select * from task where active = 1";
                        query = "select * from task";
                        break;
                    case 2:
                        query = "select * from statistics";
                        break;
                    case 3:
                        query = "select * from user";
                        break;
                    case 4:
                        query = "select * from notifications";
                        break;
                    case 5:
                        query = "select * from chat order by timeSent asc";
                        break;
                    case 6:
                        query = "select * from statistics_login_records";
                        break;
                    default://set to default so if the noOfTables variable is incorect an error wont occur
                        System.out.println("database: noOfTables was incorectly returned");
                        break;
                }
                
                ResultSet queryResultSet = statement.executeQuery(query);
                rows = getTableRows(queryResultSet);//get the row count from the result set
                cols = getTableColumns(queryResultSet);//get the column count from the result set
                String[][] table = new String[rows][cols];//create the table array with the given dimensions
                queryResultSet.beforeFirst();//reset the cursor of the result set so it points to its original position (as if unused)

                table = (createTableArray(queryResultSet,table));//create the table by calling the createTableArray method which returns the 2d array table
                newTables.add(table);//add the table to the arraylist containing the entire database
                
                statement.close();//close the statement opened within the for loop
            }
            connection.close();//close the connection
            
            
        }catch(Exception exc){//catch any exceptions
            exc.printStackTrace();//print the exception
        }
        
        return newTables;
    }
    
    
    
    //method returns the number of tables that exist within the database
    public int getNoOfTables(){
        int count = 0;
        try{
            Connection connection = DriverManager.getConnection(url, "registerAccount", null);
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = statement.executeQuery("select count(*) from information_schema.tables WHERE table_schema = '" + databaseName + "'");//count the number of tables in the db
            while(rs.next()){//while the result set has something to read
                count = rs.getInt(1);//set the count to the value within the resultset
            }   
            rs.close();//close result set
            statement.close();//close statement
            connection.close();//close connection
        }catch(Exception exc){//catch any exceptions
            exc.printStackTrace();//print the exception
        }
        return count;
    }
    
    //method returns the number of rows by moving cursor to the last row of the result set and gets the index for that line
    public int getTableRows(ResultSet rs){
        int rows = 0;//create rows variable
        try{
            rs.last();//move cursor to last row
            rows = rs.getRow();//get the row index
        }catch(Exception exc){//catch any exceptions
            exc.printStackTrace();//print the exception
        }
        
        return rows;
    }
    
    //method returns number of columns by
    public int getTableColumns(ResultSet rs){
        int cols = 0;//create columns variable
        try{
            ResultSetMetaData rsmd = rs.getMetaData();//get the meta data from the result set
            cols = rsmd.getColumnCount();//call getColumnCount method to return number of columns
        }catch(Exception exc){//catch any exceptions
            exc.printStackTrace();//print the exception
        }
        
        return cols;
    }
    
    //creates the 2d array for the tables in the database. It uses the frame of the table and reads the result set into it
    public String[][] createTableArray(ResultSet rs, String[][] tableFrame){
        String[][] table = tableFrame;//initialise the table 2d array
        try{
            int count = 0;//counter used for indexing rows in the while loop
            while(rs.next()){//while there is another ROW to be read
                for (int j = 1; j <= table[count].length; j++){//loop for the number of columns that exist
                    table[count][j-1] = rs.getString(j);//set location in taskTable 2d array to the read value from result set
                }   
                count = count + 1;//increment the ocunter variable
            }
            
        }catch(Exception exc){
            exc.printStackTrace();
        }
        return table;//return the filled table
    }
    
}
