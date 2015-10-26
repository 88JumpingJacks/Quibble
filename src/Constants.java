/**
 * Created by jackli on 2015-10-18.
 * <p>
 * Class of String constants for Quibble prompts, commands, messages, errors
 */
public class Constants
{
    // Login and session related Strings
    public static final String LOGIN = "login";
    public static final String SESSION_SELECT = "Enter the type of session: ";
    public static final String SALES = "sales";
    public static final String ADMIN = "admin";
    public static final String LOGOUT = "logout";

    // Quibble component names
    public static final String CREATE = "create";
    public static final String ADD = "add";
    public static final String DELETE = "delete";
    public static final String SELL = "sell";
    public static final String RETURN = "return";

    // Quibble component prompt Strings
    public static final String PROMPT_EVENT_NAME = "Enter event name: ";
    public static final String PROMPT_NUMBER_TICKETS = "Enter number of " +
            "tickets: ";
    public static final String PROMPT_DATE = "Enter event date: ";

    // Components constraints constants
    public static final int SALES_MAX_TICKETS = 8;
    public static final int MAX_EVENT_NAME_LENGTH = 20;
    public static final int MAX_EVENT_TICKETS = 99999;


    // This error prefix should be used before all error messages
    public static final String ERROR_PREFIX = "Error: ";

    // Error messages
    public static final String ERROR_INVALID_OPTION = ERROR_PREFIX +
            "Invalid option";
    public static final String ERROR_READ_FILE = ERROR_PREFIX +
            "Problem reading file";
    public static final String ERROR_SALES_EXCEED_MAX_TICKETS = ERROR_PREFIX +
            "Cannot sell more than " + SALES_MAX_TICKETS + " tickets in one " +
            "transaction";
    public static final String ERROR_EXCEED_MAX_EVENT_NAME_LENGTH =
            ERROR_PREFIX + "Event name cannot exceed " +
                    MAX_EVENT_NAME_LENGTH + " characters";
    public static final String ERROR_EMPTY_EVENT_NAME = ERROR_PREFIX +
            "Event name cannot be empty";
    public static final String ERROR_EXCEED_MAX_EVENT_TICKETS = ERROR_PREFIX +
            "Event cannot have more than " + MAX_EVENT_TICKETS;
    public static final String ERROR_EVENT_DOES_NOT_EXIST = ERROR_PREFIX +
            "Event does not exist";
    public static final String ERROR_SELL_MORE_TICKETS_THAN_AVAILABLE =
            ERROR_PREFIX + "Cannot sell more tickets than are currently " +
                    "available";
    public static final String ERROR_NEGATIVE_TICKETS = ERROR_PREFIX +
            "Cannot have negative number of tickets";
    public static final String ERROR_INVALID_INPUT = ERROR_PREFIX +
            "Invalid input";
    public static final String ERROR_OUTPUT_FILE_ERROR = ERROR_PREFIX +
            "Output file error";
}

