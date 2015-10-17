import java.util.Scanner;

/**
 * Created by jackli on 2015-10-16.
 */
public class Login
{

    /**
     * Scanner object to accept command line input
     */
    Scanner scanner = new Scanner(System.in);

    /**
     * Quibble session type
     */
    public final String sessionType;

    /**
     * Constructor instantiates given session type
     *
     * @param aInSessionType session type
     */
    public Login(String aInSessionType)
    {
        sessionType = aInSessionType;
        executeLogin();
    }

    /**
     * Accept "login" command, start session selection
     */
    public void executeLogin()
    {
        String lIn = scanner.nextLine();

        if (lIn.equals(PromptStrings.LOGIN))
        {
            selectSession();
        }
    }

    public void selectSession()
    {
        System.out.println(PromptStrings.SESSION_SELECT);
        String lSession = scanner.nextLine();
        boolean lIsCorrectType = false;

        while (!lIsCorrectType)
        {
            switch (lSession)
            {
                case PromptStrings.SALES:
                    new ExecuteSession(PromptStrings.SALES);
                    break;
                case PromptStrings.ADMIN:
                    new ExecuteSession(PromptStrings.ADMIN);
                    break;

                default:
                    System.out.println(PromptStrings.INVALID_OPTION);
                    System.out.println(PromptStrings.SESSION_SELECT);
                    lSession = scanner.nextLine();
                    break;
            }
        }
    }
}
