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
        currentEventsMap = new HashMap<>(aInMap);
    }

    /**
     * Return whether the specified event is a current event
     *
     * @param aInKey Event name
     * @return true if event is current, false otherwise
     */
    public static boolean isKey(String aInKey)
    {
        return currentEventsMap.containsKey(aInKey);
    }

    /**
     * Return number of tickets available for specified event
     * User should check that event exists before calling this method
     *
     * @param aInKey Event name
     * @return int - Number of tickets available for event according to the current events file
     */
    public static int getValue(String aInKey)
    {
        return Quibble.getNumberTicketsOriginal(aInKey);
    }

    /**
     * Set number of tickets for an event.
     * The calling class should check that the event exists
     *
     * @param aInKey
     * @param aInValue
     */
    public static void setValue(String aInKey, int aInValue)
    {
        currentEventsMap.put(aInKey, aInValue);
    }

    /**
     * Add key and value to currentEventsMap
     *
     * @param aInKey
     */
    public static void addKeyValue(String aInKey, int aInValue)
    {
        currentEventsMap.put(aInKey, aInValue);
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
