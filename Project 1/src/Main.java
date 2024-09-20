import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        ArrayEventList eventList = new ArrayEventList();
        try
        {
            File file = new File("Events.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                if (!line.isEmpty())
                {
                    // Extract the command character from the line
                    char command = line.charAt(0);

                    switch (command)
                    {
                        //Insert a timer
                        case 'I':
                            insertTimerEvent(line, eventList);
                            break;

                        //Remove a timer
                        case 'R':
                            removeFirstEvent(eventList);
                            break;

                        //Cancel a specific timer event
                        case 'C':
                            cancelTimerEvent(line, eventList);
                            break;

                        //Occurs if the command I, R, or C is not typed. Case sensitive.
                        default:
                            System.out.println("Invalid command: " + command);
                    }
                }
            }
            scanner.close();

            System.out.println("Future event list size: " + eventList.size());
            System.out.println("Future event list capacity: " + eventList.capacity());
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found. ");
        }
    }

    private static void insertTimerEvent(String line, ArrayEventList eventList)
    {
        // Extract the duration from the line
        int duration = Integer.parseInt(line.substring(2));

        //Duration cannot be negative
        if (duration <= 0)
        {
            System.out.println("Duration cannot be negative.");
            return;
        }

        // Create a new timer with the given duration
        Timer timer = new Timer(duration + eventList.getSimulationTime());
        timer.setInsertionTime(eventList.getSimulationTime());
        eventList.insert(timer);
        System.out.println(timer.toString());
    }


    private static void removeFirstEvent(ArrayEventList eventList)
    {
        //Accounts for empty list
        if (eventList.size() == 0)
        {
            System.out.println("Event list is empty.");
            return;
        }

        // Remove the first event from the list
        Event removedEvent = eventList.removeFirst();
        removedEvent.setInsertionTime(removedEvent.getArrivalTime());
        removedEvent.handle();
    }

    private static void cancelTimerEvent(String line, ArrayEventList eventList) {
        // Extract the event ID from the line
        int eventID = Integer.parseInt(line.substring(2));

        // Search for the event with the given ID
        boolean eventFound = false;
        for (int i = 0; i < eventList.size(); i++)
        {
            Event event = eventList.events[i];
            if (event instanceof Timer && event.getArrivalTime() != -1)
            {
                // Cast event to Timer to access getID() method
                Timer timerEvent = (Timer) event;
                // Check if the ID of the timer event matches the specified eventID
                if (timerEvent.getID() == eventID)
                {
                    // Cancel the event
                    timerEvent.cancel(eventList.getSimulationTime());
                    // Remove the event from the array
                    removeEventAtIndex(eventList.events, i, eventList.size());
                    eventList.size--; // Decrement the size
                    eventFound = true;
                    break;
                }
            }
        }

        if (!eventFound)
        {
            System.out.println("Event with ID " + eventID + " not found.");
        }
    }

    //reordering the array
    private static void removeEventAtIndex(Event[] arr, int index, int size)
    {
        for (int i = index; i < size - 1; i++)
        {
            arr[i] = arr[i + 1];
        }
        arr[size - 1] = null;
    }
}


