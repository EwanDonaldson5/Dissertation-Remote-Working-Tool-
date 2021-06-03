package Functions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author ewand
 */
public class SystemExit {
    
    database dbs;
    String url = "jdbc:mysql://localhost:3306/";
    String username; 
    String databaseName;  
    String loggedInUser;
    String password;
    
    public SystemExit(database dbs, String databaseName, String loggedInUser, String password){
        this.dbs = dbs;
        this.databaseName = databaseName; 
        this.loggedInUser = loggedInUser;
        this.password = password;
        
        Runtime.getRuntime().addShutdownHook(new Thread(){
            public void run(){
                finishLoginRecord();
            }
        });
    }
    
    //method is called to finish the login record on the database
    public void finishLoginRecord(){
        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String recordCode = dbs.currentLoginRecordCode;
        
        try{
            System.out.println("systemExit: closing login record");
            Connection connection = DriverManager.getConnection(url + databaseName, loggedInUser, password);//create connection to database
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);//create statement for use in querying the db
            
            //create the start of an insert query by only adding some details. The rest are null by default
            String query = "update statistics_login_records set logOutTime = '" + currentTime + "', NoOfMessagesSent = " + dbs.noOfMessagesSent + ", NoOfMessagesRecieved = " + dbs.noOfMessagesRecieved + " where StatisticID = '" + recordCode + "'";//string is used to hold the query that is to be executed
            
            statement.executeUpdate(query);//execute the query
            
            connection.close();
            statement.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
