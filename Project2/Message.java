public class Message extends Event
{
    private final String message;
    private final int srcAddr;
    private final int destAddr;

    private Host nextHop;
    private int distToNextHop;

    public Message(String message, int srcAddress, int destAddress)
    {
        super();
        this.message = message;
        this.srcAddr = srcAddress;
        this.destAddr = destAddress;
    }

    @Override
    public void setInsertionTime(int currentTime)
    {
        this.insertionTime = currentTime;
        this.arrivalTime = currentTime + distToNextHop;
    }


    @Override
    public void cancel()
    {

    }

    @Override
    public void handle()
    {
        nextHop.receive(this);
    }


    public String getMessage()
    {
        return message;
    }

    public int getSrcAddress()
    {
        return srcAddr;
    }

    public int getDestAddress()
    {
        return destAddr;
    }

    public void setNextHop(Host nextHop, int distance)
    {
        this.nextHop = nextHop;
        this.distToNextHop = distance;

    }
}
