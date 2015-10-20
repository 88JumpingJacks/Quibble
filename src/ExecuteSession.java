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
                    System.out.println(Constants.EVENT_NAME);
                    lTempEvent = sessionScanner.nextLine();
                    if (!SessionTransactions.isEvent(lTempEvent))
                    {
                        System.out.println(Constants
                                .ERROR_EVENT_DOES_NOT_EXIST);
                        break ;
                    }

                    System.out.println(Constants.NUMBER_TICKETS);
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
                    System.out.println(Constants.EVENT_NAME);
                    lTempEvent = sessionScanner.nextLine();
                    if (!SessionTransactions.isEvent(lTempEvent))
                    {
                        System.out.println(Constants
                                .ERROR_EVENT_DOES_NOT_EXIST);
                        break;
                    }

                    System.out.println(Constants.NUMBER_TICKETS);
                    lTempNumberTickets = Integer.parseInt(sessionScanner
                            .nextLine());
                    if (!SessionTransactions.isEvent(lTempEvent))
                    {
                        System.out.println(Constants
                                .ERROR_EVENT_DOES_NOT_EXIST);
                        break;
                    }
                    else if (lTempNumberTickets > SessionTransactions
                            .getNumberTicketsAvailable(lTempEvent))
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
        boolean lIslogout = false;

        // Temporary variable to store event name input
        String lTempEvent;

        // Temporary variable to store number of tickets input
        int lTempNumberTickets;

        while (!lIslogout)
        {
            String lCommandIn = sessionScanner.nextLine();

            switch (lCommandIn)
            {


            }
        }
    }
}
