package Functions;

import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;


public class Ping {
    
    public ArrayList<String[][]> tables = new ArrayList<String[][]>();
    public String databaseName;
    public String loggedInUser;
    public String password;
    public Icon pingIcon;
    
    public Ping(ArrayList<String[][]> tables, String databaseName, String loggedInUser, String password){
        this.tables = tables;
        this.databaseName = databaseName;
        this.loggedInUser = loggedInUser;
        this.password = password;
        this.pingIcon = new ImageIcon("../Images/bell.png");//create icon using the maxamise.png image
    }
    
    
}
