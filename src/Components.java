/**
 * Created by jackli on 2015-10-16.
 * <p>
 * This class provides base SessionTransactions component functions.
 * <p>
 * NOTE: Privilege checking is not done here and logic for checking session
 * privileges for each component should be done in the calling class.
 * Additionally, constraint checking including on the limits of what a
 * particular session can do for a given component should also be done in the
 * calling class.
 */
public class Components
{
     /**
     * Begin SessionTransactions session
     */
    public static void login()
    {
        new ExecuteSession();
    }

    /**
     * Logout of SessionTransactions and end session
     */
    public static void logout()
    {
        // todo in future releases, implement writing to the daily
        // todo event transaction file
        // todo For now, just terminate the program
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
        // todo do not allow sales for an event created in the current session
        SessionTransactions.setValue(aInEventName,
                (SessionTransactions
                        .getValue(aInEventName) -
                        aInNumberTickets));
    }

    /**
     * Return sold tickets for an event
     *
     * @param aInEventName
     * @param aInNumberTickets
     */
    public static void returnTickets(String aInEventName, int
            aInNumberTickets)
    {
        // todo Check that there are enough tickets to return

        SessionTransactions.setValue(aInEventName,
                (SessionTransactions.getValue(aInEventName)
                        + aInNumberTickets));
    }

    /**
     * Create and allocate tickets for a new event.
     * todo This is implemented in a future release when the backend is
     * todo implemented
     *
     * @param aInEventName     Event name
     * @param aInDate          Date in format YYMMDD
     * @param aInNumberTickets Number of tickets
     */
    public static void create(String aInEventName, String aInDate, int
            aInNumberTickets)
    {
        // todo check that date is correctly formatted

        // todo check that date is between tomorrow and two years from
        // todo today

        // todo check that aInNumberTickets does not exceed maximum limit

        // todo In future releases, should save this info to the event
        // todo transaction file

    }

    /**
     * Add more tickets to an event
     *
     * @param aInEventName
     * @param aInNumberTickets
     */
    public static void add(String aInEventName, int aInNumberTickets)
    {
        SessionTransactions.setValue(aInEventName,
                (SessionTransactions.getValue(aInEventName) +
                        aInNumberTickets));
    }

    /**
     * Delete an event and its tickets
     *
     * @param aInEventName
     */
    public static void delete(String aInEventName)
    {
        SessionTransactions.remove(aInEventName);
    }
}
