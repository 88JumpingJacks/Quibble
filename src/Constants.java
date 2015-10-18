/**
 * Created by jackli on 2015-10-18.
 *
 * Class of String constants for Quibble prompts, commands and messages
 */
public class Constants
{
    // Login and session related Strings
    public static final String WELCOME = "Welcome to Quibble!";
    public static final String LOGIN = "login";
    public static final String SESSION_SELECT = "Enter the type of session: ";
    public static final String SALES = "sales";
    public static final String ADMIN = "admin";

    // Quibble component Strings
    public static final String EVENT_NAME = "Enter event name: ";
    public static final String NUMBER_TICKETS = "Enter number of tickets ";
    public static final String DATE = "Enter event date: ";

    // This error prefix should be used before all error messages
    public static final String ERROR_PREFIX = "Error: ";

    // Error messages
    public static final String ERROR_INVALID_OPTION = ERROR_PREFIX +
            "Invalid option";
    public static final String ERROR_NO_CURRENT_EVENTS_FILE = ERROR_PREFIX +
            "No current event file loaded";
    public static final String ERROR_READ_FILE = ERROR_PREFIX +
            "Problem reading file";

    // Current Events file constant
    public static final String LINE_SEPARATOR = "_";
}
