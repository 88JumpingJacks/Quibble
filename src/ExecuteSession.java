import java.util.Scanner;

/**
 * Created by jackli on 2015-10-16.
 * <p>
 * Commence session type that the class is instantiated with
 * TODO - Note: Error checking is minimal in this release
 */
public class ExecuteSession
{
    /**
     * Scanner object to accept command line input
     */
    private Scanner sessionScanner = new Scanner(System.in);

    /**
     * Initialize session based on constructor input
     *
     * @param aInSessionType session type
     */
    public ExecuteSession(String aInSessionType)
    {
        switch (aInSessionType)
        {
            case Constants.SALES:
                runSales();
                break;

            case Constants.ADMIN:
                runAdmin();
                break;

            default:
                //Should not reach, valid input already checked in Login class
                break;
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

        // Continue to allow user to enter commands until they logout
        while (true)
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

        // Continue to allow user to enter commands until they logout
        while (true)
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
                    lTempNumberTickets = sessionScanner.nextInt();
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

                    Components.create(lTempEvent, lTempDate,
                            lTempNumberTickets);
                    System.out.println("YO");
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
                    break;

                default:
                    System.out.println(Constants.ERROR_INVALID_OPTION);
                    break;
            }
        }
    }
}
