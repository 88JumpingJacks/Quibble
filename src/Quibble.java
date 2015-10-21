import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by jackli on 2015-10-16.
 * <p>
 * Class accepts current events file, starts and runs the Quibble Basic Event
 * Ticket Service software
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
     * Start Quibble session, process current events file
     *
     * @param args Current events file
     */
    public static void main(String[] args)
    {
        System.out.println("Welcome to Quibble!");

        // Process current events file in main() because it's only done once
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
            System.exit(1);
        }

        login();
        componentSelection(sessionSelection());
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
     * Logout and end session
     */
    public static void logout()
    {


        System.exit(0);
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
    }

    /**
     * Add more tickets to an event
     *
     * @param aInEventName
     * @param aInNumberTickets
     */
    public static void add(String aInEventName, int aInNumberTickets)
    {
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
    public static void componentSelection(String aInSessionType)
    {
        // Temporary variable to store event name input
        String lTempEvent;

        // Temporary variable to store event date input
        String lTempDate;

        // Temporary variable to store number of tickets input
        int lTempNumberTickets;

        // Command line input
        String lCommandIn;

        boolean lIsLogout = false;

        // Continue to allow user to enter commands until they logout
        while (!lIsLogout)
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
                                .ERROR_SELL_MORE_TICKETS_THAN_AVAILABLE);
                    }
                    else if (aInSessionType.equals(Constants.SALES) &&
                            lTempNumberTickets > Constants.SALES_MAX_TICKETS)
                    {
                        System.out.println(Constants
                                .ERROR_SALES_EXCEED_MAX_TICKETS);
                        break;
                    }

                    sell(lTempEvent, lTempNumberTickets);
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
                                .ERROR_SELL_MORE_TICKETS_THAN_AVAILABLE);
                        break;
                    }
                    else if (aInSessionType.equals(Constants.SALES) &&
                            lTempNumberTickets > Constants.SALES_MAX_TICKETS)
                    {
                        System.out.println(Constants
                                .ERROR_SALES_EXCEED_MAX_TICKETS);
                        break;
                    }

                    returnTickets(lTempEvent, lTempNumberTickets);
                    break;

                case Constants.CREATE:
                    if (!aInSessionType.equals(Constants.ADMIN))
                    {
                        System.out.println(Constants.ERROR_INVALID_OPTION);
                        break;
                    }

                    System.out.println(Constants.PROMPT_EVENT_NAME);
                    lTempEvent = sessionScanner.nextLine();
                    if (lTempEvent.length() > Constants.MAX_EVENT_NAME_LENGTH)
                    {
                        System.out.println(Constants
                                .ERROR_EXCEED_MAX_EVENT_NAME_LENGTH);
                        break;
                    }
                    else if (lTempEvent.isEmpty())
                    {
                        System.out.println(Constants.ERROR_EMPTY_EVENT_NAME);
                        break;
                    }

                    System.out.println(Constants.PROMPT_DATE);
                    lTempDate = sessionScanner.nextLine();

                    System.out.println(Constants.PROMPT_NUMBER_TICKETS);

                    // Take nextLine() instead of nextInt() in case user doesn't
                    // input a number, the input will still be accepted and
                    // the appropriate error message will show
                    try
                    {
                        lTempNumberTickets = Integer.parseInt(sessionScanner
                                .nextLine());
                        if (lTempNumberTickets < 0)
                        {
                            System.out.println(Constants
                                    .ERROR_NEGATIVE_TICKETS);
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
                    break;

                case Constants.LOGOUT:
                    logout();

                default:
                    System.out.println(Constants.ERROR_INVALID_OPTION);
                    break;
            }
        }
    }
}
