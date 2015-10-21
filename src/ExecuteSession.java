import java.util.Scanner;

/**
 * Created by jackli on 2015-10-16.
 * <p>
 * Commence session type that the class is instantiated with
 * TODO - Note: Error checking is minimal in this release as it rapidly built
 * TODO as a proof of concept
 */
public class ExecuteSession
{
    /**
     * Scanner object to accept command line input
     */
    private Scanner sessionScanner = new Scanner(System.in);

    /**
     * Initialize session based on constructor input
     */
    public ExecuteSession()
    {
        executeLogin();

        selectSession();
    }

    /**
     * Accept "login" command, start session selection
     */
    public void executeLogin()
    {
        String lIn;
        boolean lIsLogin = false;

        while (!lIsLogin)
        {
            lIn = sessionScanner.nextLine();

            if (lIn.equals(Constants.LOGIN))
            {
                selectSession();
                lIsLogin = true;
            }
            else
            {
                System.out.println(Constants.ERROR_INVALID_OPTION);
            }
        }
    }

    /**
     * Prompt for session type
     */
    public void selectSession()
    {
        System.out.println(Constants.SESSION_SELECT);
        String lSession = sessionScanner.nextLine();
        boolean lIsSessionSelected = false;

        while (!lIsSessionSelected)
        {
            switch (lSession)
            {
                case Constants.SALES:
                    runSales();
                    lIsSessionSelected = true;
                    break;

                case Constants.ADMIN:
                    runAdmin();
                    lIsSessionSelected = true;
                    break;

                default:
                    System.out.println(Constants.ERROR_INVALID_OPTION);
                    System.out.println(Constants.SESSION_SELECT);
                    lSession = sessionScanner.nextLine();
                    break;
            }
        }
    }

    /**
     * Starts a sales session
     */
    public void runSales()
    {
        // Temporary variable to store event name input
        String lTempEvent;

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
                    if (!SessionTransactions.isKey(lTempEvent))
                    {
                        System.out.println(Constants
                                .ERROR_EVENT_DOES_NOT_EXIST);
                        break;
                    }

                    System.out.println(Constants.PROMPT_NUMBER_TICKETS);
                    lTempNumberTickets = Integer.parseInt(sessionScanner
                            .nextLine());
                    if (lTempNumberTickets > Constants.SALES_MAX_TICKETS)
                    {
                        System.out.println(Constants
                                .ERROR_SALES_EXCEED_MAX_TICKETS);
                        break;
                    }

                    Components.sell(lTempEvent, lTempNumberTickets);
                    break;

                case Constants.RETURN:
                    System.out.println(Constants.PROMPT_EVENT_NAME);
                    lTempEvent = sessionScanner.nextLine();
                    if (!SessionTransactions.isKey(lTempEvent))
                    {
                        System.out.println(Constants
                                .ERROR_EVENT_DOES_NOT_EXIST);
                        break;
                    }

                    System.out.println(Constants.PROMPT_NUMBER_TICKETS);
                    lTempNumberTickets = Integer.parseInt(sessionScanner
                            .nextLine());
                    if (!SessionTransactions.isKey(lTempEvent))
                    {
                        System.out.println(Constants
                                .ERROR_EVENT_DOES_NOT_EXIST);
                        break;
                    }
                    else if (lTempNumberTickets > SessionTransactions
                            .getValue(lTempEvent))
                    {
                        System.out.println(Constants
                                .ERROR_SELL_MORE_TICKETS_THAN_AVAILABLE);
                        break;
                    }
                    else if (lTempNumberTickets > Constants.SALES_MAX_TICKETS)
                    {
                        System.out.println(Constants
                                .ERROR_SALES_EXCEED_MAX_TICKETS);
                        break;
                    }

                    Components.returnTickets(lTempEvent, lTempNumberTickets);
                    break;

                case Constants.LOGOUT:
                    Components.logout();
                    lIsLogout = true;
                    break;

                default:
                    System.out.println(Constants.ERROR_INVALID_OPTION);
                    break;
            }
        }
    }

    /**
     * Starts an admin session
     */
    public void runAdmin()
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
                    if (!SessionTransactions.isKey(lTempEvent))
                    {
                        System.out.println(Constants
                                .ERROR_EVENT_DOES_NOT_EXIST);
                        break;
                    }

                    System.out.println(Constants.PROMPT_NUMBER_TICKETS);
                    lTempNumberTickets = Integer.parseInt(sessionScanner
                            .nextLine());

                    Components.sell(lTempEvent, lTempNumberTickets);
                    break;

                case Constants.RETURN:
                    System.out.println(Constants.PROMPT_EVENT_NAME);
                    lTempEvent = sessionScanner.nextLine();
                    if (!SessionTransactions.isKey(lTempEvent))
                    {
                        System.out.println(Constants
                                .ERROR_EVENT_DOES_NOT_EXIST);
                        break;
                    }

                    System.out.println(Constants.PROMPT_NUMBER_TICKETS);
                    lTempNumberTickets = Integer.parseInt(sessionScanner
                            .nextLine());
                    if (!SessionTransactions.isKey(lTempEvent))
                    {
                        System.out.println(Constants
                                .ERROR_EVENT_DOES_NOT_EXIST);
                        break;
                    }
                    else if (lTempNumberTickets > SessionTransactions
                            .getValue(lTempEvent))
                    {
                        System.out.println(Constants
                                .ERROR_SELL_MORE_TICKETS_THAN_AVAILABLE);
                        break;
                    }

                    Components.returnTickets(lTempEvent, lTempNumberTickets);
                    break;

                case Constants.CREATE:
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
                    // todo check date format

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
                            System.out.println(Constants.ERROR_NEGATIVE_TICKETS);
                            break;
                        }
                        else if (lTempNumberTickets > Constants.MAX_EVENT_TICKETS)
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

                    Components.create(lTempEvent, lTempDate,
                            lTempNumberTickets);
                    break;

                case Constants.ADD:
                    System.out.println(Constants.PROMPT_EVENT_NAME);
                    lTempEvent = sessionScanner.nextLine();
                    if (!SessionTransactions.isKey(lTempEvent))
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

                    Components.add(lTempEvent, lTempNumberTickets);
                    break;

                case Constants.DELETE:
                    System.out.println(Constants.PROMPT_EVENT_NAME);
                    lTempEvent = sessionScanner.nextLine();
                    if (!SessionTransactions.isKey(lTempEvent))
                    {
                        System.out.println(Constants
                                .ERROR_EVENT_DOES_NOT_EXIST);
                        break;
                    }

                    Components.delete(lTempEvent);
                    break;

                case Constants.LOGOUT:
                    Components.logout();
                    lIsLogout = true;
                    break;

                default:
                    System.out.println(Constants.ERROR_INVALID_OPTION);
                    break;
            }
        }
    }
}
