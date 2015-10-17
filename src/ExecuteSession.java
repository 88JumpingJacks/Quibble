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

        if (session.equals(PromptStrings.SALES))
        {

        }
        else if (session.equals(PromptStrings.ADMIN))
        {

        }
        else
        {
            // todo error invalid option
        }
    }

    public void runSales()
    {

    }

    public void runAdmin()
    {

    }
}
