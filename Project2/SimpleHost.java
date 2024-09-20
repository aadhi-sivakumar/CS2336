public class SimpleHost extends Host
{
    private int destAddr;
    private int durationTimerId;
    private int interval;
    private int duration;

    public SimpleHost(int address, FutureEventList fel)
    {
        super();
        setHostParameters(address, fel);
    }
    @Override
    protected void receive(Message msg)
    {
        switch (msg.getMessage())
        {
            // Handles ping request
            case "ping request":
                System.out.println("[" + msg.getArrivalTime() + "ts] Host " + this.getHostAddress() + ": Ping request from host " + msg.getSrcAddress());
                // Creates ping response message
                Message pingResponse = new Message("ping response", this.getHostAddress(), msg.getSrcAddress());
                pingResponse.setInsertionTime(this.getCurrentTime());
                this.sendToNeighbor(pingResponse);
                break;
            // Handles ping response
            case "ping response":
                int rtt = (msg.getArrivalTime() - msg.getInsertionTime()) * 2; // Calculate RTT
                System.out.println("[" + msg.getArrivalTime() + "ts] Host " + this.getHostAddress() + ": Ping response from host " + msg.getSrcAddress() + " (RTT = " + rtt + "ts)");
                break;

            default:
                // Unknown message type
                System.out.println("Unknown message type");
                break;
        }
    }

    @Override
    protected void timerExpired(int eventId)
    {
        // Updates current time
        int currTime = getCurrentTime();

        if (eventId == durationTimerId)
        {
            System.out.println("[" + duration + "ts] Host " + this.getHostAddress() + ": Stopped sending pings");
        }
        else
        {
            // Interval timer expired
            System.out.println("[" + currTime + "ts] Host " + this.getHostAddress() + ": Sent ping to host " + destAddr);
            Message pingRequest = new Message("ping request", this.getHostAddress(), destAddr);

            if (destAddr == this.getHostAddress())
            {
                // Handles ping request locally
                receive(pingRequest);
            }
            else
            {
                // Sends ping request to neighbor
                sendToNeighbor(pingRequest);
            }

            if (currTime + interval < duration)
            {
                newTimer(interval);
            }
        }
    }

    @Override
    protected void timerCancelled(int eventId)
    {
        System.out.println("Timer " + eventId + " cancelled");
    }

    public void sendPings(int destAddr, int interval, int duration)
    {
        this.destAddr = destAddr;
        this.interval = interval;
        this.duration = duration;
        newTimer(interval);
        durationTimerId = newTimer(duration);
    }
}
