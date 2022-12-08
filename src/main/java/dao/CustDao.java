package dao;

import model.Cust;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Main;
import utilities.DbQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Data Access Object (DAO) Class Providing Interface for Customer Database Table.
 *
 * @author Bryan Yang
 * */
public class CustDao {

    /** Database Connection Object from Main. */
    private final static Connection con = Main.con;

    /** Inserts New Customer Record.
     *
     * @param cust Customer Object to be Inserted.
     * @throws SQLException from DbQuery.setPreparedStatement().
     * */
    public static void create(Cust cust) throws SQLException{

        try{

            String sqlStatement = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) " + "VALUES(?, ?, ?, ?, NOW(), ?, NOW(), ?, ?)";

            DbQuery.setPreparedStatement(con, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.setString(1, cust.getCustName());
            ps.setString(2, cust.getAddress());
            ps.setString(3, cust.getPostalCode());
            ps.setString(4, cust.getPhone());
            ps.setString(5, cust.getCreatedBy());
            ps.setString(6, cust.getLastUpdatedBy());
            ps.setInt(7, cust.getDivID());

            ps.execute();

        }catch (SQLException e){

            System.out.println(e.getMessage());

        }

    }

    /** Select Customer Record by Customer ID.
     *
     * @param dbID Customer ID.
     * @return Customer Object.
     * @throws SQLException from DbQuery.setPreparedStatement().
     * */
    public static Cust selectByID(int dbID) throws SQLException{

        Cust cust = new Cust();

        try{

            String sqlStatement = "SELECT * FROM customers WHERE Customer_ID = ?";

            DbQuery.setPreparedStatement(con, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.setInt(1, dbID);

            ps.execute();

            ResultSet rs = ps.getResultSet();

            rs.next();

            cust.setCustID(rs.getInt("Customer_ID"));
            cust.setCustName(rs.getString("Customer_Name"));
            cust.setAddress(rs.getString("Address"));
            cust.setPostalCode(rs.getString("Postal_Code"));
            cust.setPhone(rs.getString("Phone"));
            cust.setCreatedDate(rs.getTimestamp("Create_Date").toLocalDateTime());
            cust.setCreatedBy(rs.getString("Created_By"));
            cust.setLastUpdatedTime(rs.getTimestamp("Last_Update").toLocalDateTime());
            cust.setLastUpdatedBy(rs.getString("Last_Updated_By"));
            cust.setDivID(rs.getInt("Division_ID"));

        }catch (SQLException e){

            System.out.println(e.getMessage());

        }

        return cust;

    }

    /** Select All Customer Records.
     *
     * @return List of Customer Objects.
     * @throws SQLException from DbQuery.setPreparedStatement().
     * */
    public static ObservableList<Cust> selectAll() throws SQLException{

        ObservableList<Cust> custs = FXCollections.observableArrayList();

        try{

            String sqlStatement = "SELECT * FROM customers";

            DbQuery.setPreparedStatement(con, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.execute();

            ResultSet rs = ps.getResultSet();

            while(rs.next()){

                Cust cust = new Cust();

                cust.setCustID(rs.getInt("Customer_ID"));
                cust.setCustName(rs.getString("Customer_Name"));
                cust.setAddress(rs.getString("Address"));
                cust.setPostalCode(rs.getString("Postal_Code"));
                cust.setPhone(rs.getString("Phone"));
                cust.setCreatedDate(rs.getTimestamp("Create_Date").toLocalDateTime());
                cust.setCreatedBy(rs.getString("Created_By"));
                cust.setLastUpdatedTime(rs.getTimestamp("Last_Update").toLocalDateTime());
                cust.setLastUpdatedBy(rs.getString("Last_Updated_By"));
                cust.setDivID(rs.getInt("Division_ID"));

                custs.add(cust);

            }

        }catch (SQLException e){

            System.out.println(e.getMessage());

        }

        return custs;

    }

    /** Updates Customer Record.
     *
     * @param cust Customer Object to be Updated.
     * @throws SQLException from DbQuery.setPreparedStatement().
     * */
    public static void update(Cust cust) throws SQLException{

        try{

            String sqlStatement = "Update customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = Now(), Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";

            DbQuery.setPreparedStatement(con, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.setString(1, cust.getCustName());
            ps.setString(2, cust.getAddress());
            ps.setString(3, cust.getPostalCode());
            ps.setString(4, cust.getPhone());
            ps.setString(5, cust.getLastUpdatedBy());
            ps.setInt(6, cust.getDivID());
            ps.setInt(7, cust.getCustID());

            ps.execute();

        }catch (SQLException e){

            System.out.println(e.getMessage());

        }

    }

    /** Deletes Customer Record by Customer ID.
     *
     * @param dbID Customer ID.
     * @throws SQLException from DbQuery.setPreparedStatement().
     * */
    public static void deleteByID(int dbID) throws SQLException{

        try{

            String sqlStatement = "DELETE FROM customers WHERE Customer_ID = ?";

            DbQuery.setPreparedStatement(con, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.setInt(1, dbID);

            ps.execute();

        }catch (SQLException e){

            System.out.println(e.getMessage());

        }

    }

}
