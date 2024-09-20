public class Timer implements Event
{
    private static int uniqueIDCounter = 0;
    private int timerID;
    private int arrivalTime;
    private int insertionTime = 0;

    public Timer(int duration)
    {
        this.timerID = uniqueIDCounter++;
        this.arrivalTime = duration;
    }

    @Override
    public void setInsertionTime(int currentTime)
    {
        this.insertionTime = currentTime;
    }

    @Override
    public int getInsertionTime()
    {
        return insertionTime;
    }

    @Override
    public int getArrivalTime()
    {
        return arrivalTime;
    }
    @Override
    public void cancel(int currentTime)
    {
        System.out.println("Timer " + timerID + " canceled at time: " + currentTime);
    }

    @Override
    public void handle()
    {
        System.out.println("Timer " + timerID + " handled (arrival time: " + arrivalTime + ")");
    }

    @Override
    public String toString()
    {
        return "Timer " + timerID + " (insertion time: " + insertionTime + ", arrival time: " + arrivalTime + ")";
    }

    public static int getIDCounter()
    {
        return uniqueIDCounter;
    }

    public int getID()
    {
        return timerID;
    }
}
