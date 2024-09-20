public class LinkedEventList implements FutureEventList
{
    private Node head;
    private static int currSimulationTime = 0;
    private int size;

    public LinkedEventList()
    {
        this.head = null;
        this.size = 0;
    }

    @Override
    public Event removeFirst()
    {
        // Checks if list is empty
        if (head == null)
        {
            // Empty List
            System.out.println("Head is null");
            return null;
        }
        Event removedEvent = head.getEvent();
        // Updates the simulation time to the arrival time of the removed event
        currSimulationTime = removedEvent.getArrivalTime();
        // Moves the head pointer to the next node
        head = head.next;

        // Updates the previous pointer of the new head to null
        if (head != null)
        {
            head.prev = null;
        }
        //Decrements the size of the list
        size--;
        return removedEvent;
    }

    @Override
    public boolean remove(Event e)
    {
        Node curr = head;
        // Loops through the list until it reaches the end of the list or the event to be removed
        while (curr != null && !curr.getEvent().equals(e))
        {
            curr = curr.next;
        }
        // Checks if curr reached the end of the list and the event is not found
        if (curr == null)
        {
            System.out.println("Event is not found");
            //Event is mot found
            return false;
        }
        // If curr is the first node, it updates the head to point to the next node
        if (curr.prev == null)
        {
            head = curr.next;
        }
        // If curr is not the first node, it updates the next node of the previous pointer to skip curr
        else
        {
            curr.prev.next = curr.next;
        }
        // If curr is not the last node, it updates the previous pointer of the next node to skip curr
        if (curr.next != null)
        {
            curr.next.prev = curr.prev;
        }
        // Decrements the size of the list
        size--;
        // Indicates successful removal of the event from the list
        return true;
    }

    @Override
    public void insert(Event e)
    {
        Node newNode = new Node(e);
        // Sets the insertion time of the event to the curr simulation time
        e.setInsertionTime(getSimulationTime());
        int arrivalTime = e.getArrivalTime();

        // Inserts the event into the list while maintaining sorted order by arrival time
        if (head == null || arrivalTime < head.getEvent().getArrivalTime())
        {
            // If the list is empty or the new event has an earlier arrival time than the head, insert at the beginning
            newNode.next = head;

            // If the list is not empty, it sets the previous pointer of the curr head node to newNode.
            if (head != null)
            {
                head.prev = newNode;
            }
            // Sets newNode as the new head node of the list.
            head = newNode;
        }
        else
        {
            Node curr = head;
            // Traverses the list until it reaches the end or when the arrival time of the next event is greater than the arrival time of the new event
            while (curr.next != null && curr.next.getEvent().getArrivalTime() <= arrivalTime)
            {
                curr = curr.next;
            }
            // Inserts the new node between curr and curr.next
            newNode.next = curr.next;
            newNode.prev = curr;
            if (curr.next != null)
            {
                curr.next.prev = newNode;
            }
            curr.next = newNode;
        }
        // Increments the size of the list
        size++;
    }

    @Override
    public int size()
    {
        return size;
    }

    @Override
    public int capacity()
    {
        // Capacity of the list is the same as its size
        return size();
    }

    @Override
    public int getSimulationTime()
    {
        return currSimulationTime;
    }
}
