import java.util.Scanner;

/**
 * Created by jackli on 2015-10-16.
 *
 * Commence session type that the class is instantiated with
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

    public void runSales()
    {
        boolean lIslogout = false;

        //Temporary variable to store event name input
        String lTempEvent;

        //Temporary variable to store number of tickets input
        int lTempNumberTickets;

        while (!lIslogout)
        {
            String lCommandIn = sessionScanner.nextLine();

            switch (lCommandIn)
            {
                case Constants.SELL:
                    System.out.println(Constants.EVENT_NAME);
                    lTempEvent = sessionScanner.nextLine();
                    //todo check if it is a current event;

                    System.out.println(Constants.NUMBER_TICKETS);
                    lTempNumberTickets = Integer.parseInt(sessionScanner.nextLine());
                    if (lTempNumberTickets > Constants.SALES_MAX_TICKETS)
                    {
                        System.out.println(Constants.ERROR_SALES_EXCEED_MAX_TICKETS);
                    }
                    break;

                case Constants.RETURN:
                    System.out.println(Constants.EVENT_NAME);
                    lTempEvent = sessionScanner.nextLine();

                    System.out.println(Constants.NUMBER_TICKETS);
                    lTempNumberTickets = Integer.parseInt(sessionScanner.nextLine());
                    break;

                case Constants.LOGOUT:
                    lIslogout = true;
                    //todo in the future implement writing to the daily event transaction file
                    break;

                default:
                    System.out.println(Constants.ERROR_INVALID_OPTION);
                    break;
            }
        }
    }

    public void runAdmin()
    {
        boolean lIslogout = false;

        //Temporary variable to store event name input
        String lTempEvent;

        //Temporary variable to store number of tickets input
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
