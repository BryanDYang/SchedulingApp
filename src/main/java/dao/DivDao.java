package dao;

import model.Div;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Main;
import utilities.DbCon;
import utilities.DbQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Data Access Object (DAO) Class Providing Interface for First_Level_Division Database Table.
 *
 * @author Bryan Yang
 * */
public class DivDao {

    /** Database Connection Object Obtained from Main */
    private final static Connection con = Main.con;

    /** Insert New Division Record.
     *
     * @param div Division object to be inserted.
     * @throws SQLException from DbQuery.setPreparedStatement().
     * */
    public static void create(Div div) throws SQLException {

        try {

            String sqlStatement = "INSERT INTO first_level_divisions(Division, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) " + "VALUES(?, NOW(), ?, NOW(), ?, ?_)";

            DbQuery.setPreparedStatement(con, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.setString(1, div.getDivName());
            ps.setString(2, div.getCreatedBy());
            ps.setString(3, div.getLastUpdatedBy());
            ps.setInt(4, div.getCtryID());

            ps.execute();

        }catch(SQLException e) {

            System.out.println(e.getMessage());

        }

    }

    /** Select Division Record by Division ID.
     *
     * @param dbID Division ID.
     * @return Division Object.
     * @throws SQLException from DbQuery.setPreparedStatement().
     * */
    public static Div selectByID(int dbID) throws SQLException {

        Div div = new Div();

        try {

            String sqlStatement = "SELECT * FROM first_level_divisions WHERE Division_ID = ?";

            DbQuery.setPreparedStatement(con, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.setInt(1, dbID);

            ps.execute();

            ResultSet rs = ps.getResultSet();

            rs.next();

            div.setDivID(rs.getInt("Division_ID"));
            div.setDivName(rs.getString("Division"));
            div.setCreatedDate(rs.getTimestamp("Create_Date").toLocalDateTime());
            div.setCreatedBy(rs.getString("Created_By"));
            div.setLastUpdatedTime(rs.getTimestamp("Last_Update").toLocalDateTime());
            div.setLastUpdatedBy(rs.getString("Last_Updated_By"));
            div.setCtryID(rs.getInt("COUNTRY_ID"));

        }catch (SQLException e) {

            System.out.println(e.getMessage());

        }

        return div;

    }

    /** Select All Division Records.
     *
     * @return List of Division Objects.
     * @throws SQLException from DbQuery.setPreparedStatement().
     * */
    public static ObservableList<Div> selectAll() throws SQLException{

        ObservableList<Div> divs = FXCollections.observableArrayList();

        try{

            String sqlStatement = "SELECT * FROM first_level_divisions";

            DbQuery.setPreparedStatement(con, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.execute();

            ResultSet rs = ps.getResultSet();

            while (rs.next()){

                Div div = new Div();

                div.setDivID(rs.getInt("Division_ID"));
                div.setDivName(rs.getString("Division"));
                div.setCreatedDate(rs.getTimestamp("Create_Date").toLocalDateTime());
                div.setCreatedBy(rs.getString("Created_By"));
                div.setLastUpdatedTime(rs.getTimestamp("Last_Update").toLocalDateTime());
                div.setLastUpdatedBy(rs.getString("Last_Updated_By"));
                div.setCtryID(rs.getInt("COUNTRY_ID"));

                divs.add(div);

            }

        }catch (SQLException e){

            System.out.println(e.getMessage());

        }

        return divs;

    }

    /** Update Division Record.
     *
     * @param div Division object to be updated.
     * @throws SQLException from DbQuery.setPreparedStatement().
     * */
    public static void update(Div div) throws SQLException{

        try{

            String sqlStatement = "UPDATE first_level_division SET Division = ?, Last_Update = NOW(), Last_Updated_By = ? COUNTRY_ID = ? WHERE Division_ID = ?";

            DbQuery.setPreparedStatement(con, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.setString(1, div.getDivName());
            ps.setString(2, div.getLastUpdatedBy());
            ps.setInt(3, div.getCtryID());
            ps.setInt(4, div.getDivID());

            ps.execute();

        }catch (SQLException e){

            System.out.println(e.getMessage());

        }

    }

    /** Delete Division Record by Division ID.
     *
     * @param dbID Division ID.
     * @throws SQLException from DbQuery.setPreapredStatement().
     * */
    public static void deleteByID(int dbID) throws SQLException{

        try{

            String sqlStatement = "DELETE FROM first_level_divisions WHERE Division_ID = ?";

            DbQuery.setPreparedStatement(con, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.setInt(1, dbID);

            ps.execute();

        }catch (SQLException e){

            System.out.println(e.getMessage());

        }

    }


}
