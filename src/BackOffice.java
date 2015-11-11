import java.io.*;

/**
 * Created by jackli on 2015-11-11.
 * <p>
 * Class represents Quibble Back Office operations such as merging events
 * transaction files, creating new master events file, creating new current
 * events files, etc.
 */
public class BackOffice
{
    private final static File MERGED_EVENT_TRANSACTION_FILE = new File
            ("Merged_Event_Transaction_File");

    /**
     * Merge the Event Transaction Files
     *
     * @param aInETFiles File objects for event transaction files
     * @return File - Merged file created from individual event transaction
     * files
     */
    public static File mergeEventTransactionFiles(File... aInETFiles)
    {
        try
        {
            FileReader lFileReader;
            BufferedReader lBufferedReader;

            // Writers for merged event transaction file
            FileWriter lFileWriter = new FileWriter
                    (MERGED_EVENT_TRANSACTION_FILE);

            // Read each line from each event transaction file and write the
            // lines to the merged file
            for (File lCurrentFile : aInETFiles)
            {
                // Readers for event transaction file
                lFileReader = new FileReader(lCurrentFile);
                lBufferedReader = new BufferedReader(lFileReader);

                // Current line
                String lLine;

                while ((lLine = lBufferedReader.readLine()) != null)
                {
                    if (lLine.equals("00                      000000 00000"))
                    {
                        break;
                    }

                    lFileWriter.write(lLine);
                    lFileWriter.write(String.format("%n"));
                }

                // Write "00                      000000 00000" to end of
                // merged file
                lFileWriter.write("00                      000000 00000");

                lBufferedReader.close();
                lFileReader.close();

                lFileWriter.close();
            }
        }
        catch (FileNotFoundException e)
        {
            // Show error and terminate program if the file is not well formed
            System.out.println(Constants.ERROR_READ_FILE);
            e.printStackTrace();
            System.exit(1);
        }
        catch (IOException e)
        {
            // Show error and terminate program if the file is not well formed
            System.out.println(Constants.ERROR_READ_FILE);
            e.printStackTrace();
            System.exit(1);
        }
        catch (Exception e)
        {
            // Show error and terminate program if the file is not well formed
            System.out.println(Constants.ERROR_READ_FILE);
            e.printStackTrace();
            System.exit(1);
        }

        return MERGED_EVENT_TRANSACTION_FILE;
    }
}