package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Utility Class Creating a Database Connection.
 *
 * @author Bryan Yang
 * */
public class DbCon {

    // URL Parts.
    /** Protocol for Database. */
    private static final String protocol = "jdbc";

    /** Database Vendor. */
    private static final String vendorName = ":mysql:";

    /** Database Server Location. */
    private static final String ipAddress = "//localhost:3306/";

    private static final String databaseName = "client_schedule?connectionTimeZone=SERVER";

    // Main URL.
    /** Concatenated Database URL String. */
    private static final String jdbcURL = protocol + vendorName + ipAddress + databaseName;

    // Reference for Driver Interface.
    /** Database Driver Location. */
    private static final String MYSQLJDBCDriver = "com.mysql.cj.jdbc.Driver";

    /** Database Connection Object. */
    private static Connection con = null;

    /** Username for Database. */
    private static final String username = "sqlUser";

    /** Password for Database. */
    private static final String password = "Passw0rd!";

    /** Starts connection to Database.
     *
     * @return Database Connection.
     * @throws ClassNotFoundException from Class.forName().
     * */
    public static Connection openCon() throws ClassNotFoundException{

        try{
            Class.forName(MYSQLJDBCDriver);
            con = DriverManager.getConnection(jdbcURL, username, password);

        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return con;
    }

}
