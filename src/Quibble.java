import java.util.HashMap;
import java.util.Map;

/**
 * Created by jackli on 2015-10-16.
 * <p>
 * Class starts the Quibble Basic Event Ticket Service software
 * Class accepts current events file
 */
public class Quibble
{
    /**
     * Map to store current event name and number of tickets
     * This info is read from the current events file
     */
    private static Map<String, Integer> currentEventsMap = new HashMap<>();

    public static void main(String[] args)
    {
        System.out.println(Constants.WELCOME);

        currentEventsMap = FileUtils.readCurrentEvents(args[0]);

        // Begin session login procedure
        new Login();
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

}
