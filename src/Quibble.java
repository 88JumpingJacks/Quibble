import java.io.*;
import java.util.*;

/**
 * Created by jackli on 2015-10-16.
 * <p>
 * Class accepts current events file, starts, runs the Quibble Basic Event
 * Ticket Service software and creates an event transactions file detailing
 * the session's transactions upon logout.
 * Quibble component functionality is also defined here.
 */
public class Quibble
{
    /**
     * Scanner object to accept command line input
     */
    private static Scanner sessionScanner = new Scanner(System.in);

    /**
     * Stores current events and number of tickets read from current events file
     * This map is not changed after being populated the first time
     */
    private static Map<String, Integer> eventsMap = new HashMap<>();

    /**
     * Stores transactions from current session that will be written to the
     * output file upon session termination
     */
    private static List<StringBuilder> dailyEventTransactions = new
            ArrayList<>();

    /**
     * Output file that is created upon logout
     */
    private static File outputFile;

    /**
     * Start Quibble session
     * <p>
     * Accepts current events file path as parameter, processes event and
     * number of tickets info, stores this in eventsMap HashMap so the info
     * is easily accessible during session execution. Start Quibble session,
     * process current events file. Calls login(), sessionSelection() and
     * sessionExecute()
     *
     * @param args Current events file path
     *             args[0] is the current events file (input file) path
     *             args[1] is the event transactions file (output file) path
     */
    public static void main(String[] args)
    {
        // Process current events file in main() because it's only done once
        try
        {
            File lCurrentEventsFile = new File(args[0]);
            outputFile = new File(args[1]);

            // Read events and dates from current events file
            // Assume that it is provided (no error checking for this release)
            FileReader lFileReader = new FileReader(lCurrentEventsFile);
            BufferedReader lBufferedReader = new BufferedReader(lFileReader);

            String lCurrentLine;
            String lEventName;
            String lNumberTickets;

            while ((lCurrentLine = lBufferedReader.readLine()) != null)
            {
                if (lCurrentLine.equals("END                  00000"))
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
                        .lastIndexOf(" ")).trim();
                lNumberTickets = lCurrentLine.substring(lCurrentLine.lastIndexOf
                        (" ") + 1);

                eventsMap.put(lEventName,
                        Integer.parseInt(lNumberTickets));
            }

            lFileReader.close();
        }
        catch (IOException e)
        {
            // Show error and terminate program if the file is not well formed
            System.out.println(Constants.ERROR_READ_FILE);
            e.printStackTrace(); // todo remove
            System.exit(1);
        }

        // User cannot end a Quibble instance unless there is an error
        // They can only log out of their session and this will bring them
        // back to the initial login state again
        while (true)
        {
            login();
            sessionExecute(sessionSelection());
        }

    }

    /**
     * Handle login command
     */
    public static void login()
    {
        String lIn;
        boolean lIsLogin = false;

        while (!lIsLogin)
        {
            try
            {
                // Handle login command
                lIn = sessionScanner.nextLine();

                if (lIn.equals(Constants.LOGIN))
                {
                    lIsLogin = true;
                }
                else
                {
                    System.out.println(Constants.ERROR_INVALID_OPTION);
                }
            }
            // This will happen when an automated test reaches the test input
            // file's EOF
            // Just exit the Quibble program
            catch (NoSuchElementException e)
            {
                System.exit(1);
            }
        }
    }

    /**
     * Handle session selection
     *
     * @return String - session type
     */
    public static String sessionSelection()
    {
        System.out.println(Constants.SESSION_SELECT);
        String lSession = sessionScanner.nextLine();

        while (true)
        {
            switch (lSession)
            {
                case Constants.SALES:
                    return Constants.SALES;

                case Constants.ADMIN:
                    return Constants.ADMIN;

                default:
                    System.out.println(Constants.ERROR_INVALID_OPTION);
                    System.out.println(Constants.SESSION_SELECT);
                    lSession = sessionScanner.nextLine();
                    break;
            }
        }
    }

    /**
     * Logout and end session, return user to Quibble login
     * Write the session's transactions to the output file
     *
     * @return true if successful logout
     */
    public static boolean logout()
    {
        FileWriter lFW;

        try
        {
            lFW = new FileWriter(outputFile, true);

            for (int lCounter = 0; lCounter < dailyEventTransactions.size();
                 lCounter++)
            {
                lFW.write(dailyEventTransactions.get(lCounter).toString());
                lFW.write(String.format("%n"));
            }

            // Write logout transaction
            lFW.write("00                       000000 00000");
            lFW.write(String.format("%n"));

            lFW.close();

            // todo might need to create directories if users specifies path
            // todo with new directories
            // todo to create output file
            outputFile.createNewFile();
        }
        catch (IOException e)
        {
            System.out.println(Constants.ERROR_OUTPUT_FILE_ERROR);
            System.exit(1);
        }

        return true;
    }

    /**
     * Sell tickets for an event
     *
     * @param aInEventName     Event name
     * @param aInNumberTickets Number of tickets
     */
    public static void sell(String aInEventName, int aInNumberTickets)
    {
        eventsMap.put(aInEventName, (eventsMap.get(aInEventName) -
                aInNumberTickets));
    }

    /**
     * Return previously sold tickets to an event
     *
     * @param aInEventName
     * @param aInNumberTickets
     */
    public static void returnTickets(String aInEventName, int
            aInNumberTickets)
    {
        eventsMap.put(aInEventName, (eventsMap.get(aInEventName) +
                aInNumberTickets));
    }

    /**
     * Create and allocate tickets for a new event.
     *
     * @param aInEventName     Event name
     * @param aInDate          Date in format YYMMDD
     * @param aInNumberTickets Number of tickets
     */
    public static void create(String aInEventName, String aInDate, int
            aInNumberTickets)
    {
        // todo cannot implement because there is no backend in this phase
    }

    /**
     * Add more tickets to an event
     *
     * @param aInEventName
     * @param aInNumberTickets
     */
    public static void add(String aInEventName, int aInNumberTickets)
    {
        // Call returnTickets() because the login is the same
        returnTickets(aInEventName, aInNumberTickets);
    }

    /**
     * Delete an event and its tickets
     *
     * @param aInEventName
     */
    public static void delete(String aInEventName)
    {
        eventsMap.remove(aInEventName);
    }

    /**
     * Run a Quibble session
     *
     * @param aInSessionType Session type
     */
    public static void sessionExecute(String aInSessionType)
    {
        // Temporary variable to store event name input
        String lTempEvent;

        // Temporary variable to store event date input
        String lTempDate;

        // Temporary variable to store number of tickets input
        int lTempNumberTickets;

        // Stores individual transactions to be added to
        // dailyEventTransactions List
        StringBuilder lTransaction = new StringBuilder();

        // Command line input
        String lCommandIn;

        boolean lIsLogout = false;

        // Continue to allow user to enter commands until they logout
        while (!lIsLogout)
        {
            try
            {
                lCommandIn = ""; // Reassign lCommandIn to empty String in case
                // previous loop value is still stored
                lCommandIn = sessionScanner.nextLine();

                switch (lCommandIn)
                {
                    case Constants.SELL:
                        System.out.println(Constants.PROMPT_EVENT_NAME);
                        lTempEvent = sessionScanner.nextLine();
                        if (!eventsMap.containsKey(lTempEvent))
                        {
                            System.out.println(Constants
                                    .ERROR_EVENT_DOES_NOT_EXIST);
                            break;
                        }

                        System.out.println(Constants.PROMPT_NUMBER_TICKETS);
                        lTempNumberTickets = Integer.parseInt(sessionScanner
                                .nextLine());
                        if (lTempNumberTickets > eventsMap.get(lTempEvent))
                        {
                            System.out.println(Constants
                                    .ERROR_INSUFFICIENT_TICKETS);
                        }
                        else if (aInSessionType.equals(Constants.SALES) &&
                                lTempNumberTickets > Constants
                                        .SALES_MAX_TICKETS)
                        {
                            System.out.println(Constants
                                    .ERROR_SALES_EXCEED_MAX_TICKETS);
                            break;
                        }

                        sell(lTempEvent, lTempNumberTickets);
                        dailyEventTransactions.add(buildTransaction("01",
                                lTempEvent, "000000", lTempNumberTickets));
                        break;

                    case Constants.RETURN:
                        System.out.println(Constants.PROMPT_EVENT_NAME);
                        lTempEvent = sessionScanner.nextLine();
                        if (!eventsMap.containsKey(lTempEvent))
                        {
                            System.out.println(Constants
                                    .ERROR_EVENT_DOES_NOT_EXIST);
                            break;
                        }

                        System.out.println(Constants.PROMPT_NUMBER_TICKETS);
                        lTempNumberTickets = Integer.parseInt(sessionScanner
                                .nextLine());
                        if (!eventsMap.containsKey(lTempEvent))
                        {
                            System.out.println(Constants
                                    .ERROR_EVENT_DOES_NOT_EXIST);
                            break;
                        }
                        else if (lTempNumberTickets > eventsMap.get(lTempEvent))
                        {
                            System.out.println(Constants
                                    .ERROR_INSUFFICIENT_TICKETS);
                            break;
                        }
                        else if (aInSessionType.equals(Constants.SALES) &&
                                lTempNumberTickets > Constants
                                        .SALES_MAX_TICKETS)
                        {
                            System.out.println(Constants
                                    .ERROR_SALES_EXCEED_MAX_TICKETS);
                            break;
                        }

                        returnTickets(lTempEvent, lTempNumberTickets);
                        dailyEventTransactions.add(buildTransaction("02",
                                lTempEvent, "000000", lTempNumberTickets));
                        break;

                    case Constants.CREATE:
                        if (!aInSessionType.equals(Constants.ADMIN))
                        {
                            System.out.println(Constants.ERROR_INVALID_OPTION);
                            break;
                        }

                        System.out.println(Constants.PROMPT_EVENT_NAME);
                        lTempEvent = sessionScanner.nextLine();
                        if (lTempEvent.length() > Constants
                                .MAX_EVENT_NAME_LENGTH)
                        {
                            System.out.println(Constants
                                    .ERROR_EXCEED_MAX_EVENT_NAME_LENGTH);
                            break;
                        }
                        else if (lTempEvent.isEmpty())
                        {
                            System.out.println(Constants
                                    .ERROR_EMPTY_EVENT_NAME);
                            break;
                        }

                        System.out.println(Constants.PROMPT_DATE);
                        lTempDate = sessionScanner.nextLine();

                        System.out.println(Constants.PROMPT_NUMBER_TICKETS);

                        // Take nextLine() instead of nextInt() in case user
                        // doesn't
                        // input a number, the input will still be accepted and
                        // the appropriate error message will show
                        try
                        {
                            lTempNumberTickets = Integer.parseInt(sessionScanner
                                    .nextLine());
                            if (lTempNumberTickets < 0)
                            {
                                System.out.println(Constants
                                        .ERROR_INVALID_INPUT);
                                break;
                            }
                            else if (lTempNumberTickets > Constants
                                    .MAX_EVENT_TICKETS)
                            {
                                System.out.println(Constants
                                        .ERROR_EXCEED_MAX_EVENT_TICKETS);
                                break;
                            }
                        }
                        catch (NumberFormatException e)
                        {
                            System.out.println(Constants.ERROR_INVALID_INPUT);
                            break;
                        }

                        create(lTempEvent, lTempDate,
                                lTempNumberTickets);
                        dailyEventTransactions.add(buildTransaction("03",
                                lTempEvent, lTempDate, lTempNumberTickets));
                        break;

                    case Constants.ADD:
                        if (!aInSessionType.equals(Constants.ADMIN))
                        {
                            System.out.println(Constants.ERROR_INVALID_OPTION);
                            break;
                        }

                        System.out.println(Constants.PROMPT_EVENT_NAME);
                        lTempEvent = sessionScanner.nextLine();
                        if (!eventsMap.containsKey(lTempEvent))
                        {
                            System.out.println(Constants
                                    .ERROR_EVENT_DOES_NOT_EXIST);
                            break;
                        }

                        System.out.println(Constants.PROMPT_NUMBER_TICKETS);
                        lTempNumberTickets = Integer.parseInt(sessionScanner
                                .nextLine());
                        if (lTempNumberTickets > Constants.MAX_EVENT_TICKETS)
                        {
                            System.out.println(Constants
                                    .ERROR_EXCEED_MAX_EVENT_TICKETS);
                            break;
                        }

                        add(lTempEvent, lTempNumberTickets);
                        dailyEventTransactions.add(buildTransaction("04",
                                lTempEvent, "000000", lTempNumberTickets));
                        break;

                    case Constants.DELETE:
                        if (!aInSessionType.equals(Constants.ADMIN))
                        {
                            System.out.println(Constants.ERROR_INVALID_OPTION);
                            break;
                        }

                        System.out.println(Constants.PROMPT_EVENT_NAME);
                        lTempEvent = sessionScanner.nextLine();
                        if (!eventsMap.containsKey(lTempEvent))
                        {
                            System.out.println(Constants
                                    .ERROR_EVENT_DOES_NOT_EXIST);
                            break;
                        }

                        delete(lTempEvent);
                        dailyEventTransactions.add(buildTransaction("05",
                                lTempEvent, "000000", 0));
                        break;

                    case Constants.LOGOUT:
                        lIsLogout = logout();
                        break;

                    default:
                        System.out.println(Constants.ERROR_INVALID_OPTION);
                        break;
                }
            }
            // This will happen when an automated test reaches the test input
            // file's EOF
            // Just exit the Quibble program
            catch (NoSuchElementException e)
            {
                System.exit(1);
            }

            // Clear the StringBuilder
            lTransaction.setLength(0);
        }
    }

    /**
     * Format parameter values and return a StringBuilder representing the
     * transaction
     *
     * @param aInTransactionCode
     * @param aInEventName
     * @param aInNumberTickets
     * @return StringBuilder representing the transaction
     */
    public static StringBuilder buildTransaction(String aInTransactionCode,
                                                 String aInEventName, String
                                                         aInEventDate, int
                                                         aInNumberTickets)
    {
        StringBuilder aOutTransaction = new StringBuilder();

        // Append transaction code
        aOutTransaction.append(aInTransactionCode + " ");

        // Append event name
        aOutTransaction.append(aInEventName);

        int lNumSpaces = Constants.MAX_EVENT_NAME_LENGTH - aInEventName
                .length();

        for (int lCounter = 0; lCounter < lNumSpaces; lCounter++)
        {
            aOutTransaction.append(" ");
        }
        aOutTransaction.append(" ");

        // Append event date
        aOutTransaction.append(" ");
        aOutTransaction.append(aInEventDate);
        aOutTransaction.append(" ");

        // Append number of tickets
        int lNumZeros = 5 - String.valueOf(aInNumberTickets).length();

        for (int lCounter = 0; lCounter < lNumZeros; lCounter++)
        {
            aOutTransaction.append(0);
        }

        aOutTransaction.append(aInNumberTickets);

        return aOutTransaction;
    }
}
