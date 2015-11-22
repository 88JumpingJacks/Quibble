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

            System.out.println("ENTERING WHILE");
            // Read master events file line by line, store info in a HashMap
            while ((lLine = lBufferedReader.readLine()) != null)
            {
                System.out.println("IN WHILE");

                System.out.println("lLine: " + lLine);
                // Split lLine by white space twice
//                String[] lLineArray = lLine.split("\\s+", 3);
                String[] lLineArray = new String[3];
                lLineArray[0] = lLine.substring(0, 6);
                lLineArray[1] = lLine.substring(7, 12);
                lLineArray[2] = lLine.substring(13, 33);

                System.out.println("lLineArray length: " + lLineArray.length);
                for (String yo : lLineArray)
                {
                    System.out.println("yo: " + yo);
                }

                String lDate = lLineArray[0];
                String lNumTickets = lLineArray[1];
                String lMasterEventName = lLineArray[2];
                System.out.println("while event name: " + lMasterEventName);

                lMasterLineInfo = new MasterEventTransactionsInfo(lDate,
                        lNumTickets,
                        lMasterEventName);

                lMasterMap.put(lMasterEventName, lMasterLineInfo);
                System.out.println("JUST PUT IN MAP: " + lMasterMap.get(lMasterEventName));
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
                String lTransactionEventName = lLine.substring(3, 23);
                if (lTransactionEventName == null)
                {
                    // If the event name is null, this means that we are
                    // processing an end of file line
                    // (i.e. an "00                      000000 00000") line
                    // We do not need to process this line so skip it
                    continue;
                }

                // Date (This is only used if the transaction is "create")
                String lDate = lLine.substring(24, 30);

                // Number of tickets in the transaction
                String lNumTicketsTransaction = lLine.substring(31);

                // todo remove
                System.out.println("lTransactionCode: " + lTransactionCode +
                        " length: " + lTransactionCode.length());
                System.out.println("lTransactionEventName: " +
                        lTransactionEventName + " length: " +
                        lTransactionEventName.length());
                System.out.println("lDate: " + lDate + " length: " + lDate
                        .length());
                System.out.println("lNumTicketsTransaction: " +
                        lNumTicketsTransaction + " length: " +
                        lNumTicketsTransaction.length());

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
                        System.out.println("lMasterMap: ");
                        System.out.println(lMasterMap.isEmpty());
                        for (String lName : lMasterMap.keySet())
                        {
                            System.out.print("lName: " + lName);
                            System.out.println(lName.length() + " get(): " +
                                    lMasterMap.get(lName).numberTickets);
                        }

                        System.out.println("lNumTicketsTransaction: " + lNumTicketsTransaction);

                        System.out.println("******: " + lTransactionEventName + "LENGTH: " + lTransactionEventName.length());
//                        System.out.println("NUMBER TICKETS: " + lMasterMap.get
//                                (lTransactionEventName).numberTickets);
                        int loldTickets = Integer.parseInt(lMasterMap.get
                                (lTransactionEventName).numberTickets);
                        int lMoreTickets = Integer
                                .parseInt(lNumTicketsTransaction);
//                        lNewNumTickets = Integer.parseInt(lMasterMap.get
//                                (lTransactionEventName).numberTickets) + Integer
//                                .parseInt(lNumTicketsTransaction);

                        lNewNumTickets = loldTickets + lMoreTickets;

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

            lBufferedReader.close();

            // Clear current content from master events file
            FileWriter lFW_Master = new FileWriter(aInMasterEventFile);
            lFW_Master.write("");
            lFW_Master.close();

            // Clear current content from current events file
            FileWriter lFW_CurrentEvents = new FileWriter(aInCurrentEventsFile);
            lFW_CurrentEvents.write("");
            lFW_CurrentEvents.close();

            // Instantiate new FileWriter that appends instead of of overwrite
            // for both master events file and current events file
            FileWriter lFW_MasterEvent = new FileWriter(aInMasterEventFile, true);
            FileWriter lFW_Current_Events = new FileWriter
                    (aInCurrentEventsFile, true);

            // Write lMasterMap line by line to master events file and content
            
            int lCount = 0;
            int lEnd = lMasterMap.size();
            for (String lKey : lMasterMap.keySet())
            {
                lCount++;
                lFW_MasterEvent.write(lMasterMap.get(lKey).date + " ");
                lFW_MasterEvent.write(lMasterMap.get(lKey).numberTickets + " ");

                // lKey is the event name
                lFW_MasterEvent.write(lKey + "\n");

                lFW_Current_Events.write(lKey + " ");
                lFW_Current_Events.write(lMasterMap.get(lKey).numberTickets);
                
                if (lCount < lEnd)
                {
                    lFW_Current_Events.write("\n");
                }
            }

            lFW_MasterEvent.close();
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