import java.io.*;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by jackli on 2015-10-16.
 * <p>
 * Class accepts current events file, starts, runs the Quibble Basic Event
 * Ticket Service software and creates an event transactions file detailing
 * the session's transactions upon logout.
 * Quibble component functionality is also defined here.
 * <p>
 * Quibble is a command line based software product and is assumed to be run
 * from a shell script in the /Shell_Scripts folder of this Java project.
 * Hence the directory names are not compatible with running the java program
 * from another folder
 */
public class Quibble
{
    /**
     * Map for reference to store months and the number of days they have
     */
    private final static Map<Integer, Integer> monthsAndNumberDays = new
            HashMap<>();
    /**
     * Map of daily event transaction file where
     * key - transaction name
     * value - transaction code
     */
    public static Map<String, String> transactionCodeMap = new HashMap<>();
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
    private static List<StringBuilder> dailyEventTransactionsList = new
            ArrayList<>();
    /**
     * Output file that is created upon logout of the transactions that
     */
    private static File outputFile_daily_Event_Transactions;

    /**
     * Populate transactionCodeMap with transaction codes
     */
    static
    {
        transactionCodeMap.put("sell", "01");
        transactionCodeMap.put("return", "02");
        transactionCodeMap.put("create", "03");
        transactionCodeMap.put("add", "04");
        transactionCodeMap.put("delete", "05");
    }

    /**
     * Start Quibble session
     * <p>
     * Start Quibble session,process current events file. Calls login() and
     * sessionExecute()
     */
    public static void main(String[] args)
    {
        // Populate monthsAndNumberDays Map
        monthsAndNumberDays.put(1, 31);
        monthsAndNumberDays.put(2, 28);
        monthsAndNumberDays.put(3, 31);
        monthsAndNumberDays.put(4, 30);
        monthsAndNumberDays.put(5, 31);
        monthsAndNumberDays.put(6, 30);
        monthsAndNumberDays.put(7, 31);
        monthsAndNumberDays.put(8, 31);
        monthsAndNumberDays.put(9, 30);
        monthsAndNumberDays.put(10, 31);
        monthsAndNumberDays.put(11, 30);
        monthsAndNumberDays.put(12, 31);

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
     * Handle login command.
     * Read Current Events File, initialize daily event transaction file
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

        // Process current events file in main() because it's only done once
        try
        {
            // Quibble files are located in this directory path
            String lFilesDir = "../../Quibble_Files_Assignment6/";

            File lCurrentEventsFile = new File(lFilesDir + 
                    "Current_Events_File");
            outputFile_daily_Event_Transactions = new File
                    (lFilesDir + "Daily_Event_Transaction_File");

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
            e.printStackTrace();
            System.exit(1);
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
     * Write the session's transactions to the daily event transactions
     * output file
     *
     * @return true if successful logout
     */
    public static boolean logout()
    {
        FileWriter lFW;

        try
        {
            lFW = new FileWriter(outputFile_daily_Event_Transactions);

            for (int lCounter = 0; lCounter < dailyEventTransactionsList.size();
                 lCounter++)
            {
                lFW.write(dailyEventTransactionsList.get(lCounter).toString());

                if (lCounter < dailyEventTransactionsList.size())
                {
                    lFW.write(String.format("%n"));
                }
            }

            // Write logout transaction
            lFW.write("00                      000000 00000");

            lFW.close();

            outputFile_daily_Event_Transactions.createNewFile();
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
        // dailyEventTransactionsList List
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
                            else if (lTempNumberTickets > eventsMap.get
                                    (lTempEvent))
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

                        }
                        catch (NumberFormatException e)
                        {
                            System.out.println(Constants.ERROR_INVALID_INPUT);
                            break;
                        }

                        sell(lTempEvent, lTempNumberTickets);
                        dailyEventTransactionsList.add(buildTransaction
                                (transactionCodeMap.get("sell"),
                                        lTempEvent, "000000",
                                        lTempNumberTickets));
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
                            else if (!eventsMap.containsKey(lTempEvent))
                            {
                                System.out.println(Constants
                                        .ERROR_EVENT_DOES_NOT_EXIST);
                                break;
                            }

                            if (lTempNumberTickets > eventsMap.get(lTempEvent))
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
                        }
                        catch (NumberFormatException e)
                        {
                            System.out.println(Constants.ERROR_INVALID_INPUT);
                            break;
                        }

                        returnTickets(lTempEvent, lTempNumberTickets);
                        dailyEventTransactionsList.add(buildTransaction
                                (transactionCodeMap.get("return"),
                                        lTempEvent, "000000",
                                        lTempNumberTickets));
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
                        try
                        {
                            // ***Check that date is in format YYMMDD
                            // Check that entered date is of length 6
                            if (lTempDate.length() != 6)
                            {
                                throw new NumberFormatException();
                            }

                            // Check that all indexes of the input are int
                            int lYear = Integer.parseInt(lTempDate.substring
                                    (0, 2));
                            int lMonth = Integer.parseInt(lTempDate.substring
                                    (2, 4));
                            int lDay = Integer.parseInt(lTempDate.substring
                                    (4, 6));

                            // Check that month is valid
                            // i.e. The user cannot specify 32 days because
                            // no month has 32 days
                            if (!monthsAndNumberDays.containsKey(lMonth))
                            {
                                throw new NumberFormatException();
                            }

                            // Check that date does not exceed the maximum
                            // number of days for the given month
                            if (lDay > monthsAndNumberDays.get(lMonth) ||
                                    lDay < 1)
                            {
                                throw new NumberFormatException();
                            }

                            // Check that event date is within 2 years from
                            // current date
                            LocalDate lToday = LocalDate.now();
                            LocalDate lEventDate = LocalDate.of(2000 + lYear,
                                    lMonth, lDay);
                            if (lEventDate.isBefore(lToday))
                            {
                                throw new Exception();
                            }
                            else if (lEventDate.isAfter(lToday.plusYears(2)))
                            {
                                throw new Exception();
                            }
                        }
                        catch (NumberFormatException e)
                        {
                            System.out.println(Constants
                                    .ERROR_INVALID_DATE_FORMAT);
                            break;
                        }
                        catch (Exception e)
                        {
                            System.out.println(Constants
                                    .ERROR_EXCEED_DATE_RANGE);
                            break;
                        }

                        System.out.println(Constants.PROMPT_NUMBER_TICKETS);
                        // Take nextLine() instead of nextInt() in case user
                        // doesn't input a number, the input will still be
                        // accepted and the appropriate error message will show
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
                        dailyEventTransactionsList.add(buildTransaction
                                (transactionCodeMap.get("create"),
                                        lTempEvent, lTempDate,
                                        lTempNumberTickets));
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
                            else if (lTempNumberTickets + eventsMap.get
                                    (lTempEvent) > Constants.MAX_EVENT_TICKETS)
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

                        add(lTempEvent, lTempNumberTickets);
                        dailyEventTransactionsList.add(buildTransaction
                                (transactionCodeMap.get("add"),
                                        lTempEvent, "000000",
                                        lTempNumberTickets));
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
                        dailyEventTransactionsList.add(buildTransaction
                                (transactionCodeMap.get("delete"),
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
