package dao;

import javafx.scene.chart.ScatterChart;
import model.Ctry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Main;
import utilities.DbCon;
import utilities.DbQuery;

import java.sql.*;

/** Data Access Object (DAO) Class Providing Interface for Country Database Table.
 *
 * @author Bryan Yang
 * */
public class CtryDao {

    /**
     * Database Connection Object from Main
     */
    private final static Connection con = Main.con;

    /**
     * Insert New Recorded into Country Table.
     *
     * @param ctry Country Object to be Inserted.
     * @throws SQLException from DbQuery.setPreparedStatement().
     */
    public static void create(Ctry ctry) throws SQLException {

        try {

            String sqlStatement = "INSERT INTO countries(Country, Create_Date, Created_By, Last_Update, Last_Updated_By)" + "VALUES(?, NOW(), ?, NOW(), ?)";

            DbQuery.setPreparedStatement(con, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.setString(1, ctry.getCtryName());
            ps.setString(2, ctry.getCreatedBy());
            ps.setString(3, ctry.getLastUpdatedBy());

            ps.execute();

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }

    }

    /**
     * Select Country Record by Country ID.
     *
     * @param dbID Country ID.
     * @return Country Object.
     * @throws SQLException from DbQuery.setPreparedStatement().
     */
    public static Ctry selectByID(int dbID) throws SQLException {

        Ctry ctry = new Ctry();

        try {

            String sqlStatement = "SELECT * FROM countries WHERE Country_ID = ?";

            DbQuery.setPreparedStatement(con, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.setInt(1, dbID);

            ps.execute();

            ResultSet rs = ps.getResultSet();

            rs.next();

            ctry.setCtryID(rs.getInt("Country_ID"));
            ctry.setCtryName(rs.getString("Country"));
            ctry.setCreatedDate(rs.getTimestamp("Create_Date").toLocalDateTime());
            ctry.setCreatedBy(rs.getString("Created_By"));
            ctry.setLastUpdatedTime(rs.getTimestamp("Last_Update").toLocalDateTime());
            ctry.setLastUpdatedBy(rs.getString("Last_Updated_By"));

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }

        return ctry;

    }

    /**
     * Select All Country Records.
     *
     * @return List of Country Objects.
     * @throws SQLException from DbQuery.setPreparedStatement().
     */
    public static ObservableList<Ctry> selectAll() throws SQLException {

        ObservableList<Ctry> ctries = FXCollections.observableArrayList();

        try {

            String sqlStatement = "SELECT * FROM countries";

            DbQuery.setPreparedStatement(con, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.execute();

            ResultSet rs = ps.getResultSet();

            while (rs.next()) {

                Ctry ctry = new Ctry();

                ctry.setCtryID(rs.getInt("Country_ID"));
                ctry.setCtryName(rs.getString("Country"));
                ctry.setCreatedDate(rs.getTimestamp("Create_Date").toLocalDateTime());
                ctry.setCreatedBy(rs.getString("Created_By"));
                ctry.setLastUpdatedTime(rs.getTimestamp("Last_Update").toLocalDateTime());
                ctry.setLastUpdatedBy(rs.getString("Last_Updated_By"));

                ctries.add(ctry);

            }


        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }

        return ctries;

    }

    /** Updates Country Record.
     *
     * @param ctry Country Object to be Updated.
     * @throws SQLException from DbQuery.setPreparedStatement().
     * */
    public static void update(Ctry ctry) throws SQLException{

        try{

            String sqlStatement = "UPDATE countries SET Country = ?, Last_Update = NOW(), Last_Updated_By = ?, WHERE Country_ID = ?";

            DbQuery.setPreparedStatement(con, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.setString(1, ctry.getCtryName());
            ps.setString(2, ctry.getLastUpdatedBy());
            ps.setInt(3, ctry.getCtryID());

            ps.execute();

        }catch (SQLException e){

            System.out.println(e.getMessage());

        }

    }

    /** Delete Country Record by Country ID.
     *
     * @param dbID Country ID.
     * @throws SQLException from DbQuery.setPreparedStatement().
     * */
    public static void deleteByID(int dbID) throws SQLException{

        try{

            String sqlStatement = "DELETE FROM countries WHERE Country_ID = ?";

            DbQuery.setPreparedStatement(con, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.setInt(1, dbID);

            ps.execute();

        }catch (SQLException e){

            System.out.println(e.getMessage());

        }

    }

}
