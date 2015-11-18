import java.io.*;
import java.util.*;

/**
 * Created by jackli on 2015-11-11.
 * <p>
 * Class represents Quibble Back Office operations such as merging events
 * transaction files, creating new master events file, creating new current
 * events files, etc.
 */
public class BackOffice
{
    /**
     * Process the previous day's transactions against the master events
     * file, updates current events file and master events file with the newly
     * processed info
     *
     * @param aInMasterEventFile
     * @param aInMergedEventTransactionFiles
     */
    public static void processTransactions(File aInMasterEventFile, File
            aInCurrentEventsFile, File aInMergedEventTransactionFiles)
    {
        try
        {
            FileReader lFileReader = new FileReader
                    (aInMasterEventFile);
            BufferedReader lBufferedReader = new BufferedReader(lFileReader);

            // Store info read from master events file
            Map<String, MasterEventTransactionsInfo> lMasterMap = new
                    HashMap<>();

            // Store current line
            String lLine;

            MasterEventTransactionsInfo lMasterLineInfo;

            // Read master events file line by line, store info in a HashMap
            while ((lLine = lBufferedReader.readLine()) != null)
            {
                // Split lLine by white space twice
                String[] lLineArray = lLine.split("\\s+", 3);

                String lDate = lLineArray[0];
                String lNumTickets = lLineArray[1];
                String lMasterEventName = lLineArray[2];

                lMasterLineInfo = new MasterEventTransactionsInfo(lDate,
                        lNumTickets,
                        lMasterEventName);

                lMasterMap.put(lMasterEventName, lMasterLineInfo);
            }

            lFileReader = new FileReader
                    (aInMergedEventTransactionFiles);
            lBufferedReader = new BufferedReader(lFileReader);

            // Read aInMergedEventTransactionFiles line-by-line
            while ((lLine = lBufferedReader.readLine()) != null)
            {
                // Parse the line to get the transaction code (read first two
                // characters)
                String lTransactionCode = lLine.substring(0, 2);
                String lTransactionEventName = lLine.substring(3, 24).trim();

                // Date (This is only used if the transaction is "create")
                String lDate = lLine.substring(26, 33);

                // Number of tickets in the transaction
                String lNumTicketsTransaction = lLine.substring(35);

                int lNewNumTickets = 0;

                // Process the transaction against the master events file
                switch (lTransactionCode)
                {
                    // todo need to do error checking for all of these
                    // todo it's a rapid prototype afterall ;-)
                    // sell
                    case "01":
                        lNewNumTickets = Integer.parseInt(lMasterMap.get
                                (lTransactionEventName).numberTickets) - Integer
                                .parseInt(lNumTicketsTransaction);

                        lMasterLineInfo = new MasterEventTransactionsInfo
                                (lDate, Integer.toString(lNewNumTickets),
                                        lTransactionEventName);

                        // Update lMasterMap with new info
                        lMasterMap.put(lTransactionEventName, lMasterLineInfo);
                        break;

                    // return
                    case "02":
                        lNewNumTickets = Integer.parseInt(lMasterMap.get
                                (lTransactionEventName).numberTickets) + Integer
                                .parseInt(lNumTicketsTransaction);

                        lMasterLineInfo = new MasterEventTransactionsInfo
                                (lDate, Integer.toString(lNewNumTickets),
                                        lTransactionEventName);

                        // Update lMasterMap with new info
                        lMasterMap.put(lTransactionEventName, lMasterLineInfo);
                        break;

                    // create
                    case "03":
                        lMasterLineInfo = new MasterEventTransactionsInfo
                                (lDate, lNumTicketsTransaction,
                                        lTransactionEventName);

                        lMasterMap.put(lTransactionEventName, lMasterLineInfo);
                        break;

                    // add
                    case "04":
                        lNewNumTickets = Integer.parseInt(lMasterMap.get
                                (lTransactionEventName).numberTickets) + Integer
                                .parseInt(lNumTicketsTransaction);

                        lMasterLineInfo = new MasterEventTransactionsInfo
                                (lDate, Integer.toString(lNewNumTickets),
                                        lTransactionEventName);

                        // Update lMasterMap with new info
                        lMasterMap.put(lTransactionEventName, lMasterLineInfo);
                        break;

                    // delete
                    case "05":
                        lMasterMap.remove(lTransactionEventName);
                        break;

                    default:
                        // todo error
                        break;
                }
            }

            // Clear current content from master events file
            FileWriter lFW_Master = new FileWriter(aInMasterEventFile);
            lFW_Master.write("");
            lFW_Master.close();

            // Clear current content from current events file
            lFW_Master = new FileWriter(aInCurrentEventsFile);
            lFW_Master.write("");
            lFW_Master.close();

            // Instantiate new FileWriter that appends instead of of overwrite
            // for both master events file and current events file
            lFW_Master = new FileWriter(aInMasterEventFile, true);
            FileWriter lFW_Current_Events = new FileWriter
                    (aInCurrentEventsFile, true);

            // Write lMasterMap line by line to master events file and
            // todo - probably need to change data structure from map to
            // todo something else because Maps don't guarantee ordering of
            // content
            for (String lKey : lMasterMap.keySet())
            {
                // lKey is the event name
                lFW_Master.write(lMasterMap.get(lKey).date);// + " ");
                lFW_Master.write(lMasterMap.get(lKey).numberTickets + " ");
                lFW_Master.write(lKey + "\n");

                lFW_Current_Events.write(lKey + " ");
                lFW_Current_Events.write(lMasterMap.get(lKey).numberTickets +
                        "\n");
            }

            // todo Delete new line at end of master event file and current
            // todo events file

            lFW_Master.close();
            lFW_Current_Events.close();
        }
        catch (FileNotFoundException e)
        {
            // Failed constraint log (just print error message to console)
            System.out.println(Constants.ERROR_READ_FILE);
            e.printStackTrace();
            System.exit(1);
        }
        catch (IOException e)
        {
            // Failed constraint log (just print error message to console)
            System.out.println(Constants.ERROR_READ_FILE);
            e.printStackTrace();
            System.exit(1);
        }
    }
}