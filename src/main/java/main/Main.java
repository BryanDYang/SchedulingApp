package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utilities.DbCon;
import model.ApptDisplay;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.TimeZone;

/** Appointment Scheduling Application implementing program for managing User's Appointments with Customers.
 *
 * @author Bryan Yang
 * */
public class Main extends Application {

    /** Database Connection Object. */
    public static Connection con = null;

    /** User's Time Zone. */
    public static TimeZone userTimeZone = TimeZone.getDefault();

    /** Start Method Creating FXML Stage and Loads Initial Scene.
     *
     * @param primaryStage FXML Stage.
     * @throws Exception from FXMLLoader.
     * */
    @Override
    public void start(Stage primaryStage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        primaryStage.setTitle("Appointment Manager Application");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    /** Getter for User's Time Zone.
     *
     * @return TimeZone Object.
     * */
    public static TimeZone getUserTimeZone(){

        return userTimeZone;

    }

    /** Application Entry.
     *
     * @param args Command Line Arguments.
     * @throws ClassNotFoundException from DbCon.startCon().
     * @throws SQLException from DbCon.startCon().
     * */
    public static void main(String[] args) throws ClassNotFoundException, SQLException{

        con = DbCon.openCon();

        launch(args);

    }

}
