package utilities;

import controller.LoginController;
import model.User;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/** Utility Class creating User Log.
 *
 * @author Bryan Yang
 * */
public class UserLog {

    /** User currently Logged In. */
    static User currentUser;

    /** UTC Timezone ID. */
    static ZoneId utcZoneID = ZoneId.of("UTC");

    /** Date Time Format. */
    static DateTimeFormatter globalFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy 'at' HH:mm:ssa z");

    /**  */
    private static String userInput;

    /** Record User Login Attempts Timestamp on Log File.
     *
     * @param entryTyp int for successful/Unsuccessful log entry type.
     * @throws IOException from FileWriter().
     * */
    public static void writeLog(int entryTyp) throws IOException{

        LocalDateTime currentDateTimeUTC = LocalDateTime.now(utcZoneID);
        ZonedDateTime zonedDateTimeUTC = currentDateTimeUTC.atZone(utcZoneID);
        String dateTimeString = globalFormat.format(zonedDateTimeUTC);

        try{

            FileWriter fileWriter = new FileWriter("login_activity.txt", true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            currentUser = LoginController.getCurrentUser();
            String userName = currentUser.getUserName();

            if(userName == null){

                userName = "Unknown User";

            }

            if(entryTyp == 0){

                printWriter.println(userName + " was denied access at " + dateTimeString);
                printWriter.close();

            }

            if(entryTyp == 1){

                printWriter.println(userName + " was granted access at " + dateTimeString);
                printWriter.close();

            }

        }catch (IOException e){

            System.out.println(e.getMessage());

        }

    }

}
