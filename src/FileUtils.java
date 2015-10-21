import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jackli on 2015-10-19.
 *  <p>
 *  Class of File utility methods
 * This class is also intended to contain methods for backend file processing
 * that will be implemented in future releases
 */
public class FileUtils
{
    /**
     * Reads events and number of tickets from file that is denoted by its path
     * and stores contents in a Map<String, Integer>
     *
     * @param aInFilePath File Path
     * @return Map<String, Integer> where key is event name and value is
     * number of tickets
     */
    public static Map<String, Integer> readCurrentEvents(String aInFilePath)
    {
        Map<String, Integer> aOutMap = new HashMap<>();

        try
        {
            File lCurrentEventsFile = new File(aInFilePath);

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
                        .lastIndexOf(Constants.SPACE)).trim();
                lNumberTickets = lCurrentLine.substring(lCurrentLine.lastIndexOf
                        (Constants.SPACE) + 1);

                aOutMap.put(lEventName,
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

        return aOutMap;
    }
}
