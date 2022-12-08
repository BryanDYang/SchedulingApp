package dao;

import javafx.collections.FXCollections;
import model.Appt;
import utilities.DbCon;
import javafx.collections.ObservableList;
import utilities.DbQuery;

import java.sql.*;

/** Data Access Object (DAO) Class providing interface of the Appointments Database Table.
 *
 *
 * @author Bryan Yang
 * */
public class ApptDao {

    // private final static Connection Con = Main.con;
    /** Database Connection Object Obtained from Main. */
    private static Connection con = null;

    static{
        try{
            con = DbCon.openCon();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

    }

    /** Insert Values from Appointment Object into Appointment Table.
     *
     * @param appt Appointment object to be inserted.
     * @throws SQLException from DbQuery.setPreparedStatement.
     * */
    public static void create(Appt appt) throws SQLException{

        try{
            String sqlStatement = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_by, Customer_ID, User_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, NOW(), ?, NOW(), ?, ?, ?, ?)";

            DbQuery.setPreparedStatement(con, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.setString(1, appt.getApptTitle());
            ps.setString(2, appt.getApptDescr());
            ps.setString(3, appt.getApptLoc());
            ps.setString(4,appt.getApptTyp());
            ps.setTimestamp(5,Timestamp.valueOf(appt.getStartTime()));
            ps.setTimestamp(6,Timestamp.valueOf(appt.getEndTime()));
            ps.setString(7,appt.getCreatedBy());
            ps.setString(8,appt.getLastUpdatedBy());
            ps.setInt(9,appt.getCustID());
            ps.setInt(10,appt.getUserID());
            ps.setInt(11,appt.getContID());

            ps.execute();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    /** Select All Values from Appointments Table by Appointment ID.
     *
     * @param dbID Appintment ID to select.
     * @return Appointment Object.
     * @throws SQLException from DbQuery.setPreparedStatement.
     * */
    public static Appt selectByID(int dbID) throws SQLException{

        Appt appt = new Appt();

        try{

            String sqlStatement = "SELECT * FROM appointments WHERE Appointment_ID = ?";

            DbQuery.setPreparedStatement(con, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.setInt(1, dbID);

            ps.execute();

            ResultSet rs = ps.getResultSet();

            rs.next();

            appt.setApptID(rs.getInt("Appointment_ID"));
            appt.setApptTitle(rs.getString("Title"));
            appt.setApptDescr(rs.getString("Description"));
            appt.setApptLoc(rs.getString("Location"));
            appt.setApptTyp(rs.getString("Type"));
            appt.setStartTime(rs.getTimestamp("Start").toLocalDateTime());
            appt.setEndTime(rs.getTimestamp("End").toLocalDateTime());
            appt.setCreatedDate(rs.getTimestamp("Create_Date").toLocalDateTime());
            appt.setLastUpdatedBy(rs.getString("Created_By"));
            appt.setLastUpdatedTime(rs.getTimestamp("Last_Update").toLocalDateTime());
            appt.setCustID(rs.getInt("Customer_ID"));
            appt.setUserID(rs.getInt("User_ID"));
            appt.setContID(rs.getInt("Contact_ID"));

        }catch (SQLException e){

            System.out.println(e.getMessage());

        }

        return appt;

    }

    /** Selet All Values from Appointments Table Ordered via Start Time.
     *
     * @return List of Appointment Objects.
     * @throws SQLException from DbQuery.setPreparedStatement.
     * */
    public static ObservableList<Appt> selectAll() throws SQLException{

        ObservableList<Appt> appts = FXCollections.observableArrayList();

        try{

            String sqlStatement = "SELECT * FROM appointments ORDER BY Start";

            DbQuery.setPreparedStatement(con, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.execute();

            ResultSet rs = ps.getResultSet();

            while(rs.next()){

                Appt appt = new Appt();

                appt.setApptID(rs.getInt("Appointment_ID"));
                appt.setApptTitle(rs.getString("Title"));
                appt.setApptDescr(rs.getString("Description"));
                appt.setApptLoc(rs.getString("Location"));
                appt.setApptTyp(rs.getString("Type"));
                appt.setStartTime(rs.getTimestamp("Start").toLocalDateTime());
                appt.setEndTime(rs.getTimestamp("End").toLocalDateTime());
                appt.setCreatedDate(rs.getTimestamp("Create_Date").toLocalDateTime());
                appt.setCreatedBy(rs.getString("Created_By"));
                appt.setLastUpdatedTime(rs.getTimestamp("Last_Update").toLocalDateTime());
                appt.setCustID(rs.getInt("Customer_ID"));
                appt.setUserID(rs.getInt("User_ID"));
                appt.setContID(rs.getInt("Contact_ID"));

                appts.add(appt);


            }

        }catch (SQLException e){

            System.out.println(e.getMessage());

        }

        return appts;

    }


    /** Select All Values from Appointments Table Orderd By Contact.
     *
     * @return List of Appointment Objects.
     * @throws SQLException from DbQuery.setPreparedStatement.
     * */
    public static ObservableList<Appt> selectAllOrderByContact() throws SQLException{

        ObservableList<Appt> appts = FXCollections.observableArrayList();

        try{

            String sqlStatement = "SELECT * FROM appointments ORDER BY Contact_ID, Start";

            DbQuery.setPreparedStatement(con, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.execute();

            ResultSet rs = ps.getResultSet();

            while (rs.next()){

                Appt appt = new Appt();

                appt.setApptID(rs.getInt("Appointment_ID"));
                appt.setApptTitle(rs.getString("Title"));
                appt.setApptDescr(rs.getString("Description"));
                appt.setApptLoc(rs.getString("Location"));
                appt.setApptTyp(rs.getString("Type"));
                appt.setStartTime(rs.getTimestamp("Start").toLocalDateTime());
                appt.setEndTime(rs.getTimestamp("End").toLocalDateTime());
                appt.setCreatedDate(rs.getTimestamp("Create_Date").toLocalDateTime());
                appt.setCreatedBy(rs.getString("Created_By"));
                appt.setLastUpdatedTime(rs.getTimestamp("Last_Update").toLocalDateTime());
                appt.setCustID(rs.getInt("Customer_ID"));
                appt.setUserID(rs.getInt("User_ID"));
                appt.setContID(rs.getInt("Contact_ID"));

                appts.add(appt);

            }

        }catch (SQLException e){

            System.out.println(e.getMessage());

        }

        return appts;

    }

    /**  Select All Values from Appointments Table Ordered By Location.
     *
     * @return List of Appointment Objects.
     * @throws SQLException from DbQuery.setPreparedStatement.
     * */
    public static ObservableList<Appt> selectAllOrderByLocation() throws SQLException{

        ObservableList<Appt> appts = FXCollections.observableArrayList();

        try{

            String sqlStatement = "SELECT * FROM appointments ORDER BY Location, Start";

            DbQuery.setPreparedStatement(con, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.execute();

            ResultSet rs = ps.getResultSet();

            while(rs.next()){

                Appt appt = new Appt();

                appt.setApptID(rs.getInt("Appointment_ID"));
                appt.setApptTitle(rs.getString("Title"));
                appt.setApptDescr(rs.getString("Description"));
                appt.setApptLoc(rs.getString("Location"));
                appt.setApptTyp(rs.getString("Type"));
                appt.setStartTime(rs.getTimestamp("Start").toLocalDateTime());
                appt.setEndTime(rs.getTimestamp("End").toLocalDateTime());
                appt.setCreatedDate(rs.getTimestamp("Create_Date").toLocalDateTime());
                appt.setCreatedBy(rs.getString("Created_By"));
                appt.setLastUpdatedTime(rs.getTimestamp("Last_Update").toLocalDateTime());
                appt.setCustID(rs.getInt("Customer_ID"));
                appt.setUserID(rs.getInt("User_ID"));
                appt.setContID(rs.getInt("Contact_ID"));

                appts.add(appt);

            }

        }catch (SQLException e){

            System.out.println(e.getMessage());

        }

        return appts;

    }

    /** Update Appointments Table with Values from Appointment Object.
     *
     * @param appt Appointment Object
     * @throws SQLException from DbQuery.setPreparedStatement.
     * */
    public static void update(Appt appt) throws SQLException{

        try{

            String sqlStatement = "Update appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = Now(), Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";

            DbQuery.setPreparedStatement(con, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.setString(1, appt.getApptTitle());
            ps.setString(2,appt.getApptDescr());
            ps.setString(3,appt.getApptLoc());
            ps.setString(4, appt.getApptTyp());
            ps.setTimestamp(5, Timestamp.valueOf(appt.getStartTime()));
            ps.setTimestamp(6, Timestamp.valueOf(appt.getEndTime()));
            ps.setString(7, appt.getLastUpdatedBy());
            ps.setInt(8, appt.getCustID());
            ps.setInt(9, appt.getUserID());
            ps.setInt(10, appt.getContID());
            ps.setInt(11, appt.getApptID());

            ps.execute();

        }catch (SQLException e){

            System.out.println(e.getMessage());

        }

    }

    /** Delete Appointment from Appointments Table by Appointment ID.
     *
     * @param dbID Appointment ID to be Deleted.
     * @throws SQLException from DbQuery.setPreparedStatement.
     * */
    public static void deleteByID(int dbID) throws SQLException{

        try{

            String sqlStatement = "DELETE FROM appointments WHERE Appointment_ID = ?";

            DbQuery.setPreparedStatement(con, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.setInt(1, dbID);

            ps.execute();

        }catch (SQLException e){

            System.out.println(e.getMessage());

        }

    }


}
