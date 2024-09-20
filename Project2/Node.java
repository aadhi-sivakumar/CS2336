public class Node
{
    private Event event;
    public Node prev;
    public Node next;

    public Node(Event event)
    {
        this.event = event;
        this.prev = null;
        this.next = null;
    }

    public Event getEvent()
    {
        return this.event;
    }
}