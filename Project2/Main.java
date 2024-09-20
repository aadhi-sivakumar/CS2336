import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner userInputScanner = new Scanner(System.in);
        // Prompts user for filename
        System.out.print("Enter the filename: ");
        String fileName = userInputScanner.nextLine();

        try
        {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            Scanner fileScanner = new Scanner(fileInputStream);
            // Creates a map to store SimpleHost objects with their key addresses
            HashMap<Integer, SimpleHost> hostMap = new HashMap<>();

            // Reads the center host's address
            int centerHostAddress = fileScanner.nextInt();
            fileScanner.nextLine();

            // Initializes future event list
            FutureEventList eventList = new LinkedEventList();
            SimpleHost centerHost = new SimpleHost(centerHostAddress, eventList);
            hostMap.put(centerHostAddress, centerHost);

            int neighborAddress;
            // Reads the neighbor's addresses and distances
            while ((neighborAddress = fileScanner.nextInt()) != -1)
            {
                int distance = fileScanner.nextInt();
                fileScanner.nextLine();
                SimpleHost neighborHost = new SimpleHost(neighborAddress, eventList);
                hostMap.put(neighborAddress, neighborHost);
                centerHost.addNeighbor(neighborHost, distance);
                neighborHost.addNeighbor(centerHost, distance);
            }

            // Reads the events and simulates sending pings
            while (fileScanner.hasNextLine())
            {
                int sourceAddr = fileScanner.nextInt();
                int destinationAddr = fileScanner.nextInt();
                int interval = fileScanner.nextInt();
                int duration = fileScanner.nextInt();
                SimpleHost sourceHost = hostMap.get(sourceAddr);
                sourceHost.sendPings(destinationAddr, interval, duration);
            }

            // Processes events in the event list using looping
            while (eventList.size() > 0)
            {
                Event nextEvent = eventList.removeFirst();
                nextEvent.handle();
            }
        }
        // Handles file not found exception
        catch (FileNotFoundException e)
        {
            System.err.println("Error: Cannot find simulation file: " + fileName);
        }
    }
}
