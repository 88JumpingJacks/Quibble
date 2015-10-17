import java.io.BufferedReader;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jackli on 2015-10-16.
 * <p>
 * Class starts the Quibble Basic Event Ticket Service software
 * Class accepts
 */
public class Quibble
{
    Map<String, Integer> currentEventsMap = new HashMap<>();

    public static void main(String[] args)
    {
        System.out.println(PromptStrings.WELCOME);

        File lCurrentEventsFile = new File(args[0]);

        if (lCurrentEventsFile != null)
        {
//            Charset lCharSet = Charset.forName("US-ASCII");
//            try (BufferedReader lReader = Files.newBufferedReader
//                    (lCurrentEventsFile, lCharSet))
//            {
//
//            }
        }
        else
        {
            // todo check current events file - see profs response on how to
            // handle if missing
        }

        Login lLogin = new Login();
    }
}
