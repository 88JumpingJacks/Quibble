import java.util.HashMap;
import java.util.Map;

/**
 * Created by jackli on 2015-10-20.
 *
 * This class stores updates from transactions to events
 */
public class SessionTransactions
{
    /**
     * Map to store current event name and number of tickets
     * This info is copied from the current events file content but updated with
     * info from the current session. This is written to the backend in future
     * releases
     */
    private static Map<String, Integer> currentEventsMap = new HashMap<>();

    /**
     * Initialize with events map read from the current events file
     *
     * @param aInMap Map read from the current events file when session starts
     */
    public SessionTransactions(Map<String, Integer> aInMap)
    {
        currentEventsMap = new HashMap<String, Integer>(aInMap);
    }

    /**
     * Return whether the specified event is a current event
     *
     * @param aInEvent Event name
     * @return true if event is current, false otherwise
     */
    public static boolean isEvent(String aInEvent)
    {
        return currentEventsMap.containsKey(aInEvent.replace(" ", Constants
                .SPACE));
    }

    /**
     * Return number of tickets available for specified event
     * User should check that event exists before calling this method
     *
     * @param aInEvent Event name
     * @return int - Number of tickets available for event as
     */
    public static int getNumberTicketsAvailable(String aInEvent)
    {
        return currentEventsMap.get(aInEvent);
    }

    /**
     * Set number of tickets for an event.
     * The calling class should check that the event exists
     *
     * @param aInEvent
     * @param aInNumberTickets
     */
    public static void setNumberOfTickets(String aInEvent, int aInNumberTickets)
    {
        currentEventsMap.put(aInEvent, aInNumberTickets);
    }

    /**
     * Remove event and associate number of tickets from currentEventsMap
     *
     * @param aInEvent
     */
    public static void remove(String aInEvent)
    {
        currentEventsMap.remove(aInEvent);
    }

}
