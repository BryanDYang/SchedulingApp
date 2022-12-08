package dao;

import model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Main;
import utilities.DbCon;
import utilities.DbQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Data Access Object (DAO) Clas providing Interface for User Database Table.
 *
 * @author Bryan Yang
 * */
public class UserDao {

    /** Database Connection Object Obtained from Main */
    private final static Connection con = Main.con;

    /** Inserts New User Record.
     *
     * @param user User to be Inserted.
     * @throws SQLException from DbQuery.setPreparedStatement().
     * */
    public static void create(User user) throws SQLException{

        try{

            String sqlStatement = "INSERT INTO users(User_Name, Password, Create_Date, Created_By, Last_Update, Last_Updated_By) VALUES(?, ?, NOW(), ?, NOW(), ?)";

            DbQuery.setPreparedStatement(con, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPasswd());
            ps.setString(3, user.getCreatedBy());
            ps.setString(4, user.getLastUpdatedBy());

            ps.execute();

        }catch (SQLException e){

            System.out.println(e.getMessage());

        }

    }

    /** Select User Record by User ID.
     *
     * @param dbID User ID.
     * @return User Object.
     * @throws SQLException from DbQuery.setPreparedStatement().
     * */
    public static User selectByID(int dbID) throws SQLException{

        User user = new User();

        try{

            String sqlStatement = "SELECT * FROM users WHERE User_ID = ?";

            DbQuery.setPreparedStatement(con, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.setInt(1, dbID);

            ps.execute();

            ResultSet rs = ps.getResultSet();

            rs.next();

            user.setUserID(rs.getInt("User_ID"));
            user.setUserName(rs.getString("User_Name"));
            user.setPasswd(rs.getString("Password"));
            user.setCreatedDate(rs.getTimestamp("Create_Date").toLocalDateTime());
            user.setCreatedBy(rs.getString("Created_By"));
            user.setLastUpdatedTime(rs.getTimestamp("Last_Update").toLocalDateTime());
            user.setLastUpdatedBy(rs.getString("Last_Updated_By"));

        }catch (SQLException e){

            System.out.println(e.getMessage());

        }

        return user;

    }

    /** Select User Record by Username.
     *
     * @param userName User Name String.
     * @return User Object.
     * @throws SQLException from DbQuery.setPreparedStatement().
     * */
    public static User selectByName(String userName) throws SQLException{

        User user = new User();

        try{

            String sqlStatement = "SELECT * FROM users WHERE User_Name = ?";

            DbQuery.setPreparedStatement(con, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.setString(1, userName);

            ps.execute();

            ResultSet rs = ps.getResultSet();

            if (rs.next()){

                user.setUserID(rs.getInt("User_ID"));
                user.setUserName(rs.getString("User_Name"));
                user.setPasswd(rs.getString("Password"));
                user.setCreatedDate(rs.getTimestamp("Create_Date").toLocalDateTime());
                user.setCreatedBy(rs.getString("Created_By"));
                user.setLastUpdatedTime(rs.getTimestamp("Last_Update").toLocalDateTime());
                user.setLastUpdatedBy(rs.getString("Last_Updated_By"));

            }

        }catch (SQLException e){

            System.out.println(e.getMessage());

        }

        return user;

    }

    /** Selects All User Records.
     *
     * @return List of User Objects.
     * @throws SQLException from DbQuery.setPreparedStatement().
     * */
    public static ObservableList<User> selectAll() throws SQLException{

        ObservableList<User> users = FXCollections.observableArrayList();

        try{
            String sqlstatement = "SELECT * FROM users";

            DbQuery.setPreparedStatement(con, sqlstatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.execute();

            ResultSet rs = ps.getResultSet();

            while(rs.next()){

                User user = new User();

                user.setUserID(rs.getInt("User_ID"));
                user.setUserName(rs.getString("User_Name"));
                user.setPasswd(rs.getString("Password"));
                user.setCreatedDate(rs.getTimestamp("Create_Date").toLocalDateTime());
                user.setCreatedBy(rs.getString("Created_By"));
                user.setLastUpdatedTime(rs.getTimestamp("Last_Update").toLocalDateTime());
                user.setLastUpdatedBy(rs.getString("Last_Updated_By"));

                users.add(user);

            }

        }catch (SQLException e){

            System.out.println(e.getMessage());

        }

        return users;

    }

    /** Update User Record.
     *
     * @param user User Object to be updated.
     * @throws SQLException from DbQuery.setPreparedStatement().
     * */
    public static void update(User user) throws SQLException{

        try{
            String sqlStatement = "UPDATE users SET User_Name = ?, Password = ?, Last_Update = NOW(), Last_Updated_By = ?, WHERE User_ID = ?";

            DbQuery.setPreparedStatement(con, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPasswd());
            ps.setString(3, user.getLastUpdatedBy());
            ps.setInt(4, user.getUserID());

            ps.execute();

        }catch (SQLException e){

            System.out.println(e.getMessage());

        }

    }

    /** Delete User Record by User ID.
     *
     * @param dbID User ID.
     * @throws SQLException from DbQuery.setPreapredStatement().
     * */
    public static void deleteByID(int dbID) throws SQLException{

        try{

            String sqlStatement = "DELETE FROM users WHERE User_ID = ?";

            DbQuery.setPreparedStatement(con, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.setInt(1, dbID);

            ps.execute();

        }catch (SQLException e){

            System.out.println(e.getMessage());

        }

    }

}
