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
     * Stores current events and number of tickets read from current events file
     * This map is not changed after being populated the first time
     */
    private static Map<String, Integer> originalEventsMap = new HashMap<>();


    public static void main(String[] args)
    {
        System.out.println(Constants.WELCOME);

        originalEventsMap = FileUtils.readCurrentEvents(args[0]);

        // Copy originalEventsMap to currentEventsMap
        new SessionTransactions(originalEventsMap);

        // Begin session login procedure
        Components.login();
    }
}
