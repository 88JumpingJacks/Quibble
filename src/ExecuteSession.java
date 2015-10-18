/**
 * Created by jackli on 2015-10-16.
 *
 * Commence session type that the class is instantiated with
 */
public class ExecuteSession
{
    /**
     * Quibble session type
     */
    private final String session;

    /**
     * Initialize session based on constructor input
     *
     * @param aInSessionType session type
     */
    public ExecuteSession(String aInSessionType)
    {
        session = aInSessionType;

        switch (session)
        {
            //todo
            case Constants.SALES:

                break;
            case Constants.ADMIN:

                break;
            default:

                break;
        }
    }

    public void runSales()
    {

    }

    public void runAdmin()
    {

    }
}
