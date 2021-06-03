package ProductivityFunctionality;

import Functions.accessConfigFile;
import Functions.colours;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import java.util.Date;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 *
 * @author ewand
 */
public class timeWorkingPieChart extends JPanel{
    
    ArrayList<String[][]> tables = new ArrayList<String[][]>();
    
    accessConfigFile accessConfig = new accessConfigFile();//create an object for accessing the config file
    String colourModeSettingValue = accessConfig.searchForSettingsValue("darkModeEnabled");
    colours coloursObject = new colours(colourModeSettingValue);//create an object for accessing different colours used in the program
        
    public String timePeriod;//string variable holds the time period that is chosen by the user
    
    public timeWorkingPieChart(ArrayList<String[][]> tables, String timePeriod){
        this.tables = tables;
        this.timePeriod = timePeriod;
        System.out.println("TIME PERIOD IS " + timePeriod);
        //PieDataset dataSet = fillDataset();//create the dataset
        PieDataset dataSet2 = fillDataset2();//create the dataset
        JFreeChart chart = createChart(dataSet2);//create the pie chart
        
        ChartPanel chartPanel = new ChartPanel(chart);
        add(chartPanel);
        setBackground(coloursObject.getContentPanelColour());
    }
    
    public JFreeChart createChart(PieDataset dataSet){
        JFreeChart chart = ChartFactory.createPieChart("Sample Pie Chart", dataSet, true, // legend?
            true, // tooltips?
            false // URLs?
        );
        chart.setBackgroundPaint(coloursObject.getContentPanelColour());//set the background colour of the area around the bar chart
        chart.getTitle().setPaint(coloursObject.getTextColour());//set the colour of the bar chart title to the text colour
        
        PiePlot plot = (PiePlot)chart.getPlot();
        plot.setSectionPaint(0, coloursObject.getTitlePanelColour());
        plot.setBackgroundPaint(coloursObject.getContentPanelColour());//set the background of the bar chart to the content panel colour
        plot.setOutlinePaint(null);
        
        return chart;
    }
    
        //DefaultPieDataset dataset = new DefaultPieDataset();
        //dataset.setValue("Time Not Working", minutesNotWorking);//create value in dataset for time not worked (make sure this is the first dataset for style purposes)
        //dataset.setValue("Before Start", timeDifference.get(0));//set value to the time before user started working
        //dataset.setValue("Time Working", timeDifference.get(0));
        //dataset.setValue("After End", timeDifference.get(0));//set value to the time after the user ended working
        
    private DefaultPieDataset fillDataset2(){
        DefaultPieDataset dataset = new DefaultPieDataset();//create dataset to hold the data being inserted into the graphs  
        String[][] slrTable = tables.get(5);//get the statistics_login_records table
        
        //main list holds the number of groups
        //sub list contains records that exist in the group
        ArrayList<ArrayList> mainList = new ArrayList<ArrayList>();
        ArrayList<String> subList = new ArrayList<String>();
        
        //create calendar object to obtain the the current day
        Calendar calendar = Calendar.getInstance();//create the calendar instance for this moment
        long currentTime = calendar.getTimeInMillis();//get the current time in milliseconds
                       
        switch(timePeriod){
            case "Monthly"://if time period is set to monthly then set the calendar object to 1 year back (allows for 12 groups [12 months])
                calendar.set(YEAR, calendar.get(calendar.YEAR) - 1);
                break;
                
            case "Weekly"://if the time period is set to weekly then set the calendar object to 1 month back (allows for 4 groups [4 weeks])
                calendar.set(MONTH, calendar.get(calendar.MONTH) - 1);
                break;
                
            case "Daily"://if the time period is set to daily then set the calendar to 1 week back (allows for 7 groups [7 days])
                calendar.set(DAY_OF_WEEK, calendar.get(calendar.DAY_OF_WEEK) - 1);
                break;
        }
        
        ArrayList<Long> timesList = new ArrayList<Long>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//create a date format object
        try{
            
            for(int i = 0; i < slrTable.length; i++){//loop through all the reduced table records
                //obtain the login records start and end time
                Date recordStartDate = sdf.parse(slrTable[i][1]);//create date object from the start date of the login record
                Date recordEndDate = sdf.parse(slrTable[i][2]);//create date object from the end date of the login record
                
                //if the login record exists within the time period
                Date parameter = calendar.getTime();//get the parameter date object from the calendar
                if(recordStartDate.getTime() > parameter.getTime()){//if the record start time is within the parameter
                    //calc the time the login record was active
                    long timeDifference = recordEndDate.getTime() - recordStartDate.getTime();//calculate the difference in time
                    timesList.add(timeDifference);
                }
            }  
        }catch(Exception exc){}
        
        //add up the times in the list of times
        //these times are within the parameter
        long totalTime = 0;//var holds the total time worked
        for(int i = 0; i < timesList.size(); i++){
            totalTime = totalTime + timesList.get(i);//add the indexed record to the total time
        }
        
        //now retrieve the total time elapsed from the parameter to now
        long fullTime = currentTime - calendar.getTimeInMillis();//var equals the current time in milliseconds minus the new (parameter) calendar time in milli
        
        long notWorking = fullTime - totalTime;
        
        dataset.setValue("Time Working (mins)", totalTime/60000);//add the grouped data to the datset (divide by 60000 to turn milliseconds to minutes)
        dataset.setValue("Time Not Working (mins)", notWorking/60000);//add the grouped data to the datset
        
        return dataset;
    }
    
    //method fills the data set for the chart
//    public DefaultPieDataset fillDataset(){
//        DefaultPieDataset dataset = new DefaultPieDataset();//create dataset to hold the data being inserted into the graphs  
//        
//        String[][] slrTable = tables.get(5);//get the statistics_login_records table
//        ArrayList<String[]> foreignTable = restrictTableWithTimePeriod(slrTable);
//        int rows = foreignTable.size();//get the number of rows in the foreign table
//        int cols = foreignTable.get(0).length;//get the number of columns in the foreign table
//        
//        //using the array list called foreign table, create a new multi-dimensional string array to hold the included records
//        String[][] reducedTable = new String[rows][cols];//create new table which will hold the reduced amount of login records
//        for(int i = 0; i < rows; i++){//loop for the number of rows
//            reducedTable[i] = foreignTable.get(i);//add the login record to the table at the indexed slot
//        }
//        
//        try {
//            //create an array list contains dates, these dates are the start time for the login records that still exist in reducedTable
//            //loop through the reduced table to convert the timestamps to dates and add them to the list
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//create a date format
//            List<Date> dates = new ArrayList<Date>();//create a list to hold the dates
//            for(int i = 0; i < reducedTable.length; i++){//loop through all the reduced table records
//                Date recordStartDate = sdf.parse(reducedTable[i][1]);//create date object from the reduced tables records
//                dates.add(recordStartDate);//add this date to the list of dats
//            }
//            
//            //calculate the time each record was active for
//            //then add those times together to get the total time working in the time period
//            long timeWorkingCounter = 0;//create variable for holding the total time working for that given group
//            for(int i = 0; i < reducedTable.length; i++){
//                String[] record = reducedTable[i];//create a reference to the indexed record in the reducedTable
//                
//                Date start = sdf.parse(record[1]);//convert the login time from the record to a date
//                long recordStartTime = start.getTime();//convert the login date variable to a long
//                Date end = sdf.parse(record[2]);//convert the login out from the record to a date
//                long recordEndTime = end.getTime();//convert the logout date variable to a long
//
//                long loginRecordLength = recordEndTime - recordStartTime;//calculate the length of time the given record was active for
//
//                timeWorkingCounter = timeWorkingCounter + loginRecordLength;//add the calculated record time length to the groups time working counter
//            }
//            
//            long periodLength = 0;//holds the timePeriod in milliseconds
//            switch(timePeriod){
//                case "Daily":
//                    periodLength = 86400000L;//1 day in milliseconds
//                    
//                    break;
//                    
//                case "Weekly":
//                    periodLength =  604800016L;//1 week in milliseconds
//                    break;
//                    
//                case "Monthly":
//                    periodLength = 2629800000L;//1 month in milliseconds
//                    break;
//            }            
//            dataset.setValue("Time Working", timeWorkingCounter);//add the grouped data to the datset
//            dataset.setValue("Time Not Working", periodLength - timeWorkingCounter);//add the grouped data to the datset
//            
//            
//
//        } catch (ParseException exp) {
//            exp.printStackTrace();
//        }
//        
//        
////        dataset.addValue( 1.0 , fiat , speed );
////        dataset.addValue( 5.0 , audi , speed );
////        dataset.addValue( 4.0 , ford , speed );
//        
//        return dataset;
//    }
    
    public ArrayList<String[]> restrictTableWithTimePeriod(String[][] table){
        int recordCount = 0;//count of the number of records read
        int successCount = 0;//count of the number of useable records
        ArrayList<String[]> restrictedTable = new ArrayList<String[]>();
        int increase = 0;//hold the time that is added to create the startParameter (600 = 10 mins [10*60secs])
        int split = 0;//var contains the split value which indicates how many sections to seperate the records into
        
        
        if(timePeriod.equals("Daily")){//if the parsed time period is daily
            split = 86400;//this is one day in seconds
            increase = 2592000;//represents one month in seconds
        
        }else if(timePeriod.equals("Weekly")){//if the parsed time period is weekly
            split = 604800;//this is one week in seconds
            increase = 31104000;//represents one year in seconds
        
        }else if(timePeriod.equals("Monthly")){//if the parsed time period is monthly
            split = 2592000;//this is one month in seconds
            increase = 31104000;//represents one year in seconds
        
        }else{System.out.println("out: error with parsed time period");}
        System.out.println("TimePeriod is " + timePeriod);
        
        long endParameter = System.currentTimeMillis();//endParameter is the current time. This is at the end of the time period
        long startParameter = getCalculatedParameter(endParameter, increase);//calculate the startParameter by calling method
        
        //for loop limits the records that are included by comparing the records login and logout time against the parameters
        //if the record falls between both parameters then it is included
        for(int i = 0; i < table.length; i++){//loop for the number of records in the parsed table
            recordCount = recordCount + 1;//increment the record count
            
            if(table[i][1] == null){//if the record in the login records table contains a null login time value
                //System.out.println("out: records loginTime contains null value");//output error message
                
            }else if(table[i][2] == null){//if the record in the login record table contains a null logout time value
                //System.out.println("out: records logoutTime contains null value");//output error message
            
            }else{    
                String recordLogin = table[i][1];//get the login time of the first record
                Timestamp start = Timestamp.valueOf(recordLogin);
                long loginTime = start.getTime();
                
                String recordLogout = table[i][2];//get the logout time of the first record
                Timestamp end = Timestamp.valueOf(recordLogout);
                long logoutTime = end.getTime();
                
                if((loginTime > startParameter) && (logoutTime < endParameter)){//if the login record is within the time period parameters
                    //System.out.println("Parameters are: " + new Timestamp(startParameter) + " and " + new Timestamp(endParameter));
                    //System.out.println("Login and Out : " + new Timestamp(loginTime) + " and " + new Timestamp(logoutTime));
                    
                    restrictedTable.add(table[i]);
                    successCount = successCount + 1;
                    
                }else{//System.out.println("out: record is not within parameters");
                    
                }
            }
        }
        System.out.println("Out of " + recordCount + " there are " + successCount + " useable records");
        
        return restrictedTable;
    }
    
    //method is called to get the startParameter for the timePeriod
    public long getCalculatedParameter(long currentTime, int increase) {
        Timestamp original = new Timestamp(currentTime);//get the timestamp version of the current time
        
        Calendar cal = Calendar.getInstance();//create a calendar instance
        cal.setTimeInMillis(original.getTime());//set the calendar time to the timestamp
        cal.add(Calendar.SECOND, (-1 * increase));//add time to the date
        
        Timestamp later = new Timestamp(cal.getTime().getTime());//convert the updated calendar date to a timestamp
        long parameter = later.getTime();//convert the timestamp back to a long
        
        return parameter;
    }
    
    //method is called to return the difference in between two parsed times
    public ArrayList<Long> getTimeDifference(String dateStart, String dateStop){
        ArrayList<Long> times = new ArrayList<Long>();//create an arrayList to hold the time difference
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//create a format which copies the timestamp structure
        Date d1 = null;//hold the first date
        Date d2 = null;//hold the second date
        
        try{
            d1 = format.parse(dateStart);//format the date
            d2 = format.parse(dateStop);//format the date
            
            //in milliseconds
            long diff = d2.getTime() - d1.getTime();//get the difference between times
            long diffSeconds = diff / 1000;//get how many seconds are in the difference
            long diffMinutes = diffSeconds / 60;//get how many minutes are in the difference
            long remainder = diffSeconds % 60;//get the remainder of seconds form the previous calculation
            
            //System.out.println("There are " + diffMinutes + " minutes");
            //System.out.println("With a remainder of " + remainder + " seconds");
            times.add(diffMinutes);
            times.add(remainder);
            
        }catch(Exception e){
            System.out.println("chart: error occured getting time difference" + e);
        }
        
        return times;
    }
    
    //method is called from the parent class to update the users chosen time period, and to update the graph
    public void setTimePeriod(String timePeriod){
        timePeriod = timePeriod;//update the global variable for the users time period input
        
        
    }
    
}
