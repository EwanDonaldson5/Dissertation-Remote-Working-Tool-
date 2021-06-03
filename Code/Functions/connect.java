package Functions;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author ewand
 */
public class connect extends JFrame{
    
    private String url = "jdbc:mysql://localhost:3306/";
    private String username = "registerAccount";   
    private String password = null;
    String databaseName;//variable holds the name of the database being accessed
    Boolean valid = false;//boolean is used to validate the users input
    
    JPanel display = new JPanel();
    JTextField dbNameTextField = new JTextField("Enter Company Name");//create a textfield for users input
    JButton submit = new JButton("Submit");//create a submit button for the form
    
    public connect(){
        setLocationRelativeTo(null);//set the location of the jpanbel to the centre of the screen
        setPreferredSize(new Dimension(200,200));//set the dimension of the jframe
        setMinimumSize(new Dimension(200,200));
        setButtonListeners();
        
        add(display);//adds display jpanel to the jframe
        display.add(dbNameTextField);//add the textfield to the jpanel
        display.add(submit);//add the submit button tot he jpanel
        
    }
    
    public void verifyAttempt(){//varify the users entry against the localhost list of databases
        
        try{
            Connection conn = DriverManager.getConnection(url,username,password);//create a connection to the phpmyadmin page on the local host
                    
            Statement state = conn.createStatement();//create a statement
            //select all the databases with the chosen name (should be either 0 or 1)
            ResultSet nameCheck = state.executeQuery("SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = '" + databaseName + "'"); 
            
            String nameFound = "";//create a variable to hold the name found in the returned result set
            while(nameCheck.next()){//read the data in the result set
                nameFound = nameCheck.getString(1);//set variable to contain the data in the result set
            }
            if(databaseName.equals(nameFound)){//if the users entered db name is equal to an existing one
                System.out.println("ITS A MATCH");
                valid = true;//set the valid entry boolean to true
            }else{
                System.out.println("ITS NOT A MATCH");
            }
            
            state.close();//close the statement
            
        }catch(Exception exc){//catch any exceptions
            exc.printStackTrace();//print the exception
        }
        
        
    }
    
    public void setButtonListeners(){
        submit.addActionListener(new ActionListener() {//create listener for the submit button
            public void actionPerformed(ActionEvent e) {
                databaseName = dbNameTextField.getText();//get the text from the user input
                verifyAttempt();//call the method to verify the users attempt to connect
            }
        });
    }
    public Boolean checkEntry(){//check the valid entry boolean and return it
        return valid;
    }
    
    public String getEntry(){
        return databaseName;
    }
    
}
