package dao;

import model.Cont;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Main;
import utilities.DbQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Data Access Object (DAO) class providing interface of the Contact Database Table.
 *
 * @author Bryan Yang
 * */
public class ContDao {

    /** Database Connection Object Obtained from Main */
    private final static Connection con = Main.con;

    /** Insert New Record into Contact Table with Values from Contact Object.
     *
     * @param cont Contact Object to be inserted.
     * @throws SQLException from DbQuery.setPreparedStatement.
     * */
    public static void create(Cont cont) throws SQLException {

        try{

            String sqlStatement = "INSERT INTO contact(Contact_Name, Email) VALUES(?, ?)";

            DbQuery.setPreparedStatement(con, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.setString(1, cont.getContName());
            ps.setString(2, cont.getContEmail());

            ps.execute();

        }catch (SQLException e){

            System.out.println(e.getMessage());

        }

    }

    /** Selects Record from Contacts Table by Contact ID.
     *
     * @param dbID Contact ID.
     * @return Contact Object.
     * @throws SQLException from DbQuery.setPreparedStatement.
     * */
    public static Cont selectByID(int dbID) throws SQLException {

        Cont cont = new Cont();

        try{

            String sqlStatement = "SELECT * FROM contacts WHERE Contact_ID = ?";

            DbQuery.setPreparedStatement(con, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.setInt(1, dbID);

            ps.execute();

            ResultSet rs = ps.getResultSet();

            rs.next();

            cont.setContID(rs.getInt("Contact_ID"));
            cont.setContName(rs.getString("Contact_Name"));
            cont.setContEmail(rs.getString("Email"));

        }catch (SQLException e){

            System.out.println(e.getMessage());

        }

        return cont;

    }

    /** Select All Records from Contacts Table.
     *
     * @return List of Contact Objects.
     * @throws SQLException from DbQuery.setPreparedStatement.
     * */
    public static ObservableList<Cont> selectAll() throws SQLException{

        ObservableList<Cont> conts = FXCollections.observableArrayList();

        try{

            String sqlStatement = "SELECT * FROM contacts";

            DbQuery.setPreparedStatement(con, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.execute();

            ResultSet rs = ps.getResultSet();

            while(rs.next()){

                Cont cont = new Cont();

                cont.setContID(rs.getInt("Contact_ID"));
                cont.setContName(rs.getString("Contact_Name"));
                cont.setContEmail(rs.getString("Email"));

                conts.add(cont);

            }

        }catch (SQLException e){

            System.out.println(e.getMessage());

        }

        return conts;

    }

    /** Updates Record in Contact Table.
     *
     * @param cont Contact Object to be Updated.
     * @throws SQLException from DbQuery.setPreparedStatement.
     * */
    public static void update(Cont cont) throws SQLException{

        try{

            String sqlStatement = "UPDATE contacts SET Contact_Name = ?, Email = ?" + "WHERE Contact_ID = ?";

            DbQuery.setPreparedStatement(con, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.setString(1, cont.getContName());
            ps.setString(2, cont.getContEmail());
            ps.setInt(3, cont.getContID());

            ps.execute();

        }catch (SQLException e){

            System.out.println(e.getMessage());

        }

    }

    /** Deletes Record from Contacts Table by Contact ID.
     *
     * @param dbID Contact ID to be Deleted.
     * @throws SQLException from DbQuery.setPreparedStatement.
     * */
    public static void deleteByID(int dbID) throws SQLException{

        try{

            String sqlStatement = "DELETE FROM contacts WHERE Contact_ID = ?";

            DbQuery.setPreparedStatement(con, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.setInt(1, dbID);

            ps.execute();

        }catch (SQLException e){

            System.out.println(e.getMessage());

        }

    }

}
