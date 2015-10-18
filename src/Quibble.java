import java.io.*;
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

        try
        {
            File lCurrentEventsFile = new File(args[0]);

            // Read events and dates from current events file
            // Assume that it is provided (no error checking for this release)
            FileReader lFileReader = new FileReader(lCurrentEventsFile);
            BufferedReader lBufferedReader = new BufferedReader(lFileReader);

            String lCurrentLine;
            String lEventName;
            String lNumberTickets;
            String[] lLineArray;

            while ((lCurrentLine = lBufferedReader.readLine()) != null)
            {
                if (lCurrentLine.equals(Constants.END_EVENT))
                {
                    break;
                }

                // The file's format for each line is AAAAAAAAAAAAAAAAAAAA_NNNNN
                // where "AAAAAAAAAAAAAAAAAAAA" is the event name, "_" is a
                // space and "NNNNN" is the number of tickets available for the
                // event.
                // Assign the event name as key and the number of tickets as
                // value to the currentEventsMap
                lEventName = lCurrentLine.substring(0, lCurrentLine
                        .lastIndexOf(Constants.LINE_SEPARATOR));
                lNumberTickets = lCurrentLine.substring(lCurrentLine.lastIndexOf
                        (Constants.LINE_SEPARATOR) + 1);

                currentEventsMap.put(lEventName,
                        Integer.parseInt(lNumberTickets));
            }

            lFileReader.close();
        }
        catch (IOException e)
        {
            // Show error and terminate program if the file is not well formed
            System.out.println(Constants.ERROR_READ_FILE);
            System.exit(1);
        }

        // Begin session login procedure
        new Login();
    }
}
