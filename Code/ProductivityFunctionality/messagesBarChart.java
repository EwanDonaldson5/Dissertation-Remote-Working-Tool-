package ProductivityFunctionality;

import Functions.accessConfigFile;
import Functions.colours;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
/**
 *
 * @author ewand
 */
public class messagesBarChart extends JPanel {

    ArrayList<String[][]> tables = new ArrayList<String[][]>();//the arraylist containing the tables from the database
    public String timePeriod = "Monthly";//hold the user chosen time period for the graph
    
    accessConfigFile accessConfig = new accessConfigFile();//create an object for accessing the config file
    String colourModeSettingValue = accessConfig.searchForSettingsValue("darkModeEnabled");
    colours coloursObject = new colours(colourModeSettingValue);//create an object for accessing different colours used in the program
    
    public messagesBarChart(ArrayList<String[][]> tables, String timePeriod) {
        this.tables = tables;
        this.timePeriod = timePeriod;
        
        JFreeChart barChart = createBarChart();//call method to create a bar chart
        
        ChartPanel chartPanel = new ChartPanel( barChart );//create chart panel with bar chart inside
        chartPanel.setBackground(coloursObject.getContentPanelColour());//set background of the chart panel
        chartPanel.setBorder(new EmptyBorder(10, 10, 10, 10));//set an inset border for the chartPanel
        add(chartPanel);//add chartPanel to the root panel
        
        setBackground(coloursObject.getContentPanelColour());//sets the background colour fo the root panel
    }

    //method returns a fully created bar chart
    public JFreeChart createBarChart(){
        JFreeChart barChart = ChartFactory.createBarChart(
            "Number of Messages Sent and Recieved",//title of the chart         
            timePeriod,//x-axis of the chart
            "Number of Messages",//y-axis of the chart 
            fillDataset(),//call method to fill the data set        
            PlotOrientation.HORIZONTAL,
            true, true, false);
        
        
        barChart.setBackgroundPaint(coloursObject.getContentPanelColour());//set the background colour of the area around the bar chart
        barChart.getTitle().setPaint(coloursObject.getTextColour());//set the colour of the bar chart title to the text colour
        
        CategoryPlot plot = barChart.getCategoryPlot();
        plot.setBackgroundPaint(coloursObject.getContentPanelColour());//set the background of the bar chart to the content panel colour
        plot.setOutlinePaint(null);//hide the outline of the graph
        plot.setRangeGridlinesVisible(false);//hide the grid-lines
        plot.getRenderer().setSeriesPaint(0, coloursObject.getBarChartBarColour());//set the colour of the bars in the bar chart
        
        CategoryAxis xAxis = plot.getDomainAxis();//create access to the x-axis
        xAxis.setLabelPaint(coloursObject.getTextColour());//set the colour of the x-axis label
        xAxis.setTickLabelPaint(coloursObject.getTextColour());//set the colour of the a-axis numbers
        
        ValueAxis yAxis = plot.getRangeAxis();//create access to the y-axis
        yAxis.setLabelPaint(coloursObject.getTextColour());//set the colour of the y-axis label
        yAxis.setTickLabelPaint(coloursObject.getTextColour());//set the colour of the y-axis numbers
        
        return barChart;
    }
    
    //method fills the data set for the chart
    public DefaultCategoryDataset fillDataset(){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();//create dataset to hold the data being inserted into the graphs  
        
        String[][] slrTable = tables.get(5);//get the statistics_login_records table
        ArrayList<String[]> foreignTable = restrictTableWithTimePeriod(slrTable);
        int rows = foreignTable.size();//get the number of rows in the foreign table
        int cols = foreignTable.get(0).length;//get the number of columns in the foreign table
        
        //using the array list called foreign table, create a new multi-dimensional string array to hold the included records
        String[][] reducedTable = new String[rows][cols];//create new table which will hold the reduced amount of login records
        for(int i = 0; i < rows; i++){//loop for the number of rows
            reducedTable[i] = foreignTable.get(i);//add the login record to the table at the indexed slot
        }
        
        try {
            //create an array list contains dates, these dates are the start time for the login records that still exist in reducedTable
            //loop through the reduced table to convert the timestamps to dates and add them to the list
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//create a date format
            List<Date> dates = new ArrayList<Date>();//create a list to hold the dates
            for(int i = 0; i < reducedTable.length; i++){//loop through all the reduced table records
                Date recordStartDate = sdf.parse(reducedTable[i][1]);//create date object from the reduced tables records
                dates.add(recordStartDate);//add this date to the list of dats
            }
            
            ArrayList<ArrayList<String[]>> nestedRecordsList = new ArrayList<ArrayList<String[]>>();//aray list holds lists of records
            ArrayList<ArrayList<Date>> nestedDatesList = new ArrayList<>();//nested list holds lists of dates
            ArrayList<Date> group = new ArrayList<Date>();//list holds the temporary group that gets inserted into the nested dates list
            ArrayList<String[]> groupedRecords = new ArrayList<String[]>();//list holds the temporary group of records that ecist in the same time frame
            int counter = 0;
            
            //switch statement runs off the timePeriod variable
            //from here methods are called to group the reducedTable records into groups
            switch(timePeriod){
                case "Daily":
                    int day = 0;
                    int dayOfYear = -1;
                    Collections.sort(dates, new WeekComparator());
                    for (Date date : dates) {

                        if (dayOfYear != getDayOfYear(date)) {//if the next week has come
                            dayOfYear = getDayOfYear(date);
                            day++;
                            //System.out.println("Week " + week + ":");
                            nestedDatesList.add(group);//add the currently filled group list to the nested list of dates
                            group = new ArrayList<Date>();//reset the group list to collect the next set of dates in the new week
                            nestedRecordsList.add(groupedRecords);
                            groupedRecords = new ArrayList<String[]>();
                        }
                        group.add(date);//add the date to the group list
                        groupedRecords.add(reducedTable[counter]);
                        //System.out.println(date);
                        counter++;
                    }
                    break;
                    
                case "Weekly":
                    int week = 0;
                    int weekOfYear = -1;
                    Collections.sort(dates, new WeekComparator());
                    for (Date date : dates) {

                        if (weekOfYear != getWeekOfYear(date)) {//if the next week has come
                            weekOfYear = getWeekOfYear(date);
                            week++;
                            //System.out.println("Week " + week + ":");
                            nestedDatesList.add(group);//add the currently filled group list to the nested list of dates
                            group = new ArrayList<Date>();//reset the group list to collect the next set of dates in the new week
                            nestedRecordsList.add(groupedRecords);
                            groupedRecords = new ArrayList<String[]>();
                        }
                        group.add(date);//add the date to the group list
                        groupedRecords.add(reducedTable[counter]);
                        //System.out.println(date);
                        counter++;
                    }
                    break;
                    
                case "Monthly":
                    int month = 0;
                    int monthOfYear = -1;
                    Collections.sort(dates, new WeekComparator());
                    for (Date date : dates) {

                        if (monthOfYear != getMonthOfYear(date)) {//if the next week has come
                            monthOfYear = getMonthOfYear(date);
                            month++;
                            
                            nestedDatesList.add(group);//add the currently filled group list to the nested list of dates
                            group = new ArrayList<Date>();//reset the group list to collect the next set of dates in the new week
                            nestedRecordsList.add(groupedRecords);
                            groupedRecords = new ArrayList<String[]>();
                        }
                        group.add(date);//add the date to the group list
                        groupedRecords.add(reducedTable[counter]);
                        counter++;
                    }
                    break;
            }
            
            
            //this section of code loops through the nested lists
            //it fills an arraylist with the totals of the number of messages sent and recieved within each group
            for(int i = 0; i < nestedDatesList.size(); i++){//loop through the nested list
                ArrayList<Date> groupList = nestedDatesList.get(i);//create a reference to the currently idnexed list within the nested list
                ArrayList<String[]> recordList = nestedRecordsList.get(i);
                int messagesSentCounter = 0;//create variable for holding the group total of messages sent
                int messagesRecievedCounter = 0;//create var holding the group total of recieved messages
                
                for(int c = 0; c < groupList.size(); c++){//loop though the dates in this group list
                    String[] record = recordList.get(c);
                    
                    messagesSentCounter = messagesSentCounter + Integer.parseInt(record[3]);//add the number of messages sent in this record to the gorup total
                    messagesRecievedCounter = messagesRecievedCounter + Integer.parseInt(record[4]);//add th enumber of recieved messages in this record to the group total
                }
                System.out.println("WEEK: " + i + " has " + messagesSentCounter + "/" + messagesRecievedCounter);
                
                dataset.addValue(messagesSentCounter, "Sent Messages", "Week " + Integer.toString(i));//add the grouped data to the datset
                dataset.addValue(messagesRecievedCounter, "Recieved Messages", "Week " + Integer.toString(i));//add the grouped data to the data set
            }

        } catch (ParseException exp) {
            exp.printStackTrace();
        }
        
        
//        dataset.addValue( 1.0 , fiat , speed );
//        dataset.addValue( 5.0 , audi , speed );
//        dataset.addValue( 4.0 , ford , speed );
        
        return dataset;
    }
    
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
        
        Date date = new Date(startParameter);//get a date object with the start parameter that was calculated
        Calendar calendar = Calendar.getInstance();//create a calendar instance
        calendar.setTime(date);//set the cursor of the calendar to the start parameter

        calendar.get(Calendar.YEAR);//get the year from the start parameter
        calendar.get(Calendar.MONTH);//get the month from the start parameter
        calendar.get(Calendar.DAY_OF_MONTH);//get the day of the month from the start parameter
        
        
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
    
    public void calcDiffInTimestamps(String[][] table){
        List<Long> loginRecords = new ArrayList<Long>();//create a list for holding the records (in terms of length of record)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//create a date formater
        
        //code below works out the difference between the login and logout time
        try {
            for(int i = 0; i < table.length; i++){//loop for length of the statistics login records table
                String loginTime = table[i][1];//get the login time from the record
                String logoutTime = table[i][2];//get the logout time from the record
                
                Timestamp loginTimestamp = Timestamp.valueOf(loginTime);
                Timestamp logoutTimestamp = Timestamp.valueOf(logoutTime);
                
                long diffInTime = logoutTimestamp.getTime() - loginTimestamp.getTime();
                loginRecords.add(diffInTime);
            }
        } catch(Exception e) {System.out.println("messagesChart: error while reducing " + e);}
    }
    
    public static class MonthComparator implements Comparator<Date> {

        @Override
        public int compare(Date o1, Date o2) {
            int result = getMonthOfYear(o1) - getMonthOfYear(o2);
            if (result == 0) {
                result = o1.compareTo(o2);
            }
            return result;
        }
    }
    
    public static class WeekComparator implements Comparator<Date> {

        @Override
        public int compare(Date o1, Date o2) {
            int result = getWeekOfYear(o1) - getWeekOfYear(o2);
            if (result == 0) {
                result = o1.compareTo(o2);
            }
            return result;
        }
    }
    
    public static class DayComparator implements Comparator<Date> {

        @Override
        public int compare(Date o1, Date o2) {
            int result = getDayOfYear(o1) - getDayOfYear(o2);
            if (result == 0) {
                result = o1.compareTo(o2);
            }
            return result;
        }
    }
    
    protected static int getMonthOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }

    protected static int getWeekOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }
    
    protected static int getDayOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_YEAR);
    }
}
