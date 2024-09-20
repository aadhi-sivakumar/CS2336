import java.util.NoSuchElementException;

public class ArrayEventList implements FutureEventList
{
    private int currentSimulationTime = 0;
    protected int size = 0;
    private int capacity = 5;
    public Event[] events = new Event[capacity];

    @Override
    public Event removeFirst()
    {
        if (size == 0)
        {
            throw new NoSuchElementException("List is empty");
        }

        // Update currentSimulationTime to the arrival time of the first event
        currentSimulationTime = events[0].getArrivalTime();

        Event removedEvent = events[0];

        // Copy the elements from mainArray to newArray, excluding the first element
        System.arraycopy(events, 1, events, 0, capacity - 1);

        size--;

        return removedEvent;
    }

    @Override
    public boolean remove(Event e)
    {
        int left = 0;
        int right = size - 1;
        currentSimulationTime = e.getArrivalTime();

        return recursiveBinarySearch(e, left, right);
    }

    private boolean recursiveBinarySearch(Event e, int left, int right)
    {
        // Check if the right pointer is greater than or equal to the left pointer
        if (right >= left)
        {
            // Calculate the middle index
            int mid = left + (right - left) / 2;

            // If the element is found at the middle index
            if (events[mid] == e)
            {
                // Remove the element by shifting elements to the left
                System.arraycopy(events, mid + 1, events, mid, size - mid - 1);
                size--;
                return true;
            }

            // If the element is smaller than the element at mid index, search in the left subarray
            if (events[mid].getArrivalTime() > e.getArrivalTime())
            {
                return recursiveBinarySearch(e, left, mid - 1);
            }

            // Otherwise, search in the right subarray
            return recursiveBinarySearch(e, mid + 1, right);
        }

        // Element not found in the array
        return false;
    }

    @Override
    public void insert(Event e)
    {
        size++;

        // If the list is full, double the capacity
        if (size >= capacity)
        {
            capacity *= 2;
            Event[] newEvents = new Event[capacity];
            System.arraycopy(events, 0, newEvents, 0, events.length);
            events = newEvents;
        }

        // Find the correct position for insertion using insertion sort
        int i = size - 1;
        while (i > 0 && events[i - 1].getArrivalTime() > e.getArrivalTime())
        {
            events[i] = events[i - 1];
            i--;
        }
        events[i] = e;
    }

    @Override
    public int size()
    {
        return size;
    }

    @Override
    public int capacity()
    {
        return capacity;
    }

    @Override
    public int getSimulationTime()
    {
        return currentSimulationTime;
    }
}


