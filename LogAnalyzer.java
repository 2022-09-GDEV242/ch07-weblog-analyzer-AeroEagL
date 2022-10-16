/**
 * Read web server data and analyse hourly access patterns.
 * 
 * @author David J. Barnes and Michael Kölling.
 * @version    2016.02.29
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts; 
    // Where to calculate the access counts by day.
    private int[] dayCounts;
    // Where to calculate the access count by month.
    private int[] monthCounts;
    // Use a LogfileReader to access the data.
    LogfileReader reader;
    LogfileCreator creator = new LogfileCreator();
    /**
     * Create an object to analyze hourly, daily, and monthly web accesses.
     */
    public LogAnalyzer()
    { 
        // Create the array objects that hold each of the hourly, daily, and monthly counts of logs.
        hourCounts = new int[24];
        dayCounts = new int[28];
        monthCounts = new int[12];
        // Create the reader to obtain the data.
        reader = new LogfileReader("demo.log");
    }
    
    /**
     * Create an object to analyze hourly, daily, and monthly web accesses by using a specific file name,
     * and creates a file of that filename.
     * @param logFileName The name of the log file to create/read
     */
    public LogAnalyzer(String logFileName)
    {
        // Create the array objects that hold each of the hourly, daily, and monthly counts of logs.
        hourCounts = new int[24];
        dayCounts = new int[28];
        monthCounts = new int[12];
        int numEntries = 200;
        creator.createFile(logFileName, numEntries);
        reader = new LogfileReader(logFileName);
    }
    
    /**
     * Analyze the hourly, daily, and monthly access data from the log file.
     */
    public void analyzeAllData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int month = entry.getMonth();
            int hour = entry.getHour();
            int day = entry.getDay();
            monthCounts[month-1]++;
            dayCounts[day-1]++;
            hourCounts[hour]++;
        }
    }
    
    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }
    }

    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        for(int hour = 0; hour < hourCounts.length; hour++) {
            System.out.println(hour + ": " + hourCounts[hour]);
        }
    }
    
    /**
     * Print the monthly counts(added up from all years with that month).
     * These should have been set with a prior call to analyzeAllData.
     */
    public void printTotalAccessesPerMonth()
    {
        for(int i = 1; i <= monthCounts.length; i++)
            System.out.println("" + i + ": " + monthCounts[i - 1]);
    }
    
    /**
     * This method returns the array of the monthly counts of logs.
     */
    public int[] totalAccessesPerMonth()
    {
        return monthCounts;
    }
    
    /**
     * This method returns the average number of log accesses per month(divided by the amount of years)
     */
    public double[] averageAccessesPerMonth()
    {
        double[] averageMonthCount = new double[12];
        
        for(int i = 0; i < monthCounts.length; i++)
            averageMonthCount[i] = Double.valueOf(monthCounts[i])/5;
            
        return averageMonthCount;
    }
    
    /**
     * This method prints the average number of log accesses per month(total of 
     * each month divided by years)
     */
    public void printAverageAccessesPerMonth()
    {
        double[] temp = averageAccessesPerMonth();
        
        for(int i = 1; i <= monthCounts.length; i++)
        {
            System.out.println("Average for month " + i + ": " + temp[i - 1]);
        }
    }
    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
    
    /**
     * Return the number of accesses recorded in the log file
     */
    public int numberOfAccesses()
    {
        int total = 0;
        // Add the value in each element of hourCounts to total
        for(int i : hourCounts)
            total += i;
            
        return total;
    }
    
    /**
     * This method returns the busiest month(1-12) in terms of logs
     * @return the busiest month in terms of logs
     */
    public int busiestMonth()
    {
        int busiestMonth = 1;
        
        for(int i = 0; i < monthCounts.length; i++)
            if(monthCounts[i] > monthCounts[busiestMonth - 1])
            {
                busiestMonth = i + 1;
            }
            
            return busiestMonth;
    }
    
    /**
     * This method returns the quietest month(1-12) in terms of logs
     * @return quietest month in terms of logs
     */
    public int quietestMonth()
    {
        int quietestMonth = 1;
        
        for(int i = 0; i < monthCounts.length; i++)
            if(monthCounts[i] < monthCounts[quietestMonth - 1])
            {
                quietestMonth = i + 1;
            }
            
            return quietestMonth;
    }
    
    /**
     * This method returns the busiest day(1-28) in terms of logs
     * @return the busiest day of the month over the course of log file in terms of logs
     */
    public int busiestDay()
    {
        int busiestDay = 1;
        
        for(int i = 0; i < dayCounts.length; i++)
            if(dayCounts[i] > dayCounts[busiestDay - 1])
            {
                busiestDay = i + 1;
            }
            
        return busiestDay;
    }

    /**
     * This method returns the quietest day(1-28) in terms of logs
     * @return the quietest day of the month over the course of log file in terms of logs
     */
    public int quietestDay()
    {
        int quietestDay = 1;
        
        for(int i = 0; i < dayCounts.length; i++)
            if(dayCounts[i] < dayCounts[quietestDay - 1])
            {
                quietestDay = i + 1;
            }
            
            return quietestDay;
    }
    
    /**
     * This method returns the busiest hour(0-23) in terms of logs
     * @return the busiest hour of the day over the course of log file in terms of logs
     */
    public int busiestHour()
    {
        int busiestHour = 0;

        for(int i = 0; i < hourCounts.length; i++)
            if(hourCounts[i] > hourCounts[busiestHour])
            {
                busiestHour = i;
            }
            
        return busiestHour;
    }
    
    /**
     * This method returns the quietest hour(0-23) in terms of logs
     * @return the quietest hour of the day over the course of log file in terms of logs
     */
    public int quietestHour()
    {
        int quietestHour = 0;
        
        for(int i = 0; i < hourCounts.length; i++)
            if(hourCounts[i] < hourCounts[quietestHour])
                quietestHour = i;

        return quietestHour;
    }
    
    /**
     * This method returns the start of the 2-hour window in which the most amount of logs are entered
     */
    public int startOfBusiestTwoHoursPeriod()
    {
        int busiestHourStart = 0;
        
        for(int i = 0; i < hourCounts.length; i++)
            if(hourCounts[i] + hourCounts[(i + 1) % 24] > 
                hourCounts[busiestHourStart] + hourCounts[(busiestHourStart + 1) % 24])
                busiestHourStart = i;
                
        return busiestHourStart;
    }
}
