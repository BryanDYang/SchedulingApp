package utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/** Utility Class creating a Database Query Statements.
 *
 * @author Bryan Yang
 * */
public class DbQuery {

    /** Prepared SQL Statement. */
    private static PreparedStatement statement;

    /** Getter for Prepared Statement.
     *
     * @return SQL Prepared Statement.
     * */
    public static PreparedStatement getPreparedStatement() {
        return statement;
    }

    /** Setter for Prepared Statement.
     *
     * Create a prepared SQL statement.
     *
     * @param con Database Connection.
     * @param sqlStatement SQL Statement.
     * @throws SQLException from con.prepareStatement().
     * */
    public static void setPreparedStatement(Connection con, String sqlStatement) throws SQLException{

        statement = con.prepareStatement(sqlStatement, PreparedStatement.RETURN_GENERATED_KEYS);

    }


}
