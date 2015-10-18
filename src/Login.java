import java.util.Scanner;

/**
 * Created by jackli on 2015-10-16.
 *
 * Login and session selection
 */

public class Login
{
    /**
     * Scanner object to accept command line input
     */
    private Scanner scanner = new Scanner(System.in);

    /**
     * Constructor instantiates given session type
     */
    public Login()
    {
        executeLogin();
    }

    /**
     * Accept "login" command, start session selection
     */
    public void executeLogin()
    {
        String lIn = scanner.nextLine();

        if (lIn.equals(Constants.LOGIN))
        {
            selectSession();
        }
    }

    /**
     * Prompt for session type
     */
    public void selectSession()
    {
        System.out.println(Constants.SESSION_SELECT);
        String lSession = scanner.nextLine();
        while (true)
        {
            switch (lSession)
            {
                case Constants.SALES:
                    new ExecuteSession(Constants.SALES);
                    break;

                case Constants.ADMIN:
                    new ExecuteSession(Constants.ADMIN);
                    break;

                default:
                    System.out.println(Constants.ERROR_INVALID_OPTION);
                    System.out.println(Constants.SESSION_SELECT);
                    lSession = scanner.nextLine();
                    break;
            }
        }
    }
}
