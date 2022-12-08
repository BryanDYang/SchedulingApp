package controller;

import dao.ApptDao;
import dao.UserDao;
import main.Main;
import model.Appt;
import model.User;
import utilities.UserLog;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.TimeZone;

/** Controller Class providing control logic for Login Display.
 *
 * @author Bryan Yang
 * */
public class LoginController implements Initializable {

    /** User's Locale from System Default. */
    Locale userLocale = Locale.getDefault();

    /** Resource Bundle for Language Translation. */
    ResourceBundle rb = ResourceBundle.getBundle("language/rb", userLocale);

    /** User's Timezone from Class Main. */
    private TimeZone userTimeZone = Main.getUserTimeZone();

    /** User Object currently logged in User. */
    public static User currentUser;

    /** Date Time Format. */
    private final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");

    /** Cancel Button for Login Display. */
    @FXML
    private Button cancelBttn;

    /** Login Button for Login Display. */
    @FXML
    private Button loginBttn;

    /** Password Label for Login Display. */
    @FXML
    private Label passwdLbl;

    /** Password Text Field for Login Display. */
    @FXML
    private TextField passwdTxt;

    /** Timezone Label for Login Display. */
    @FXML
    private Label timezoneLbl;

    /** Username Label for Login Display. */
    @FXML
    private Label userNameLbl;

    /** Username Text Field for Login Display. */
    @FXML
    private TextField userNameTxt;

    /** Timezone ID Label for Login Display. */
    @FXML
    private Label zoneIdLbl;

    /** Close/Terminate Application
     *
     * @param event Cancel Button Action.
     * */
    @FXML
    void cancelBttnAction(ActionEvent event) {

        System.exit(0);

    }

    /** Call ValidUser() and openApptsDisplay() when user is validated.
     *
     * Calls Userlog.writeLog() to update user log with login attempt details.
     * Calls apptReminder() to display reminder message.
     *
     * @param event Login Button Action.
     * @throws IOException from openApptsDisplay().
     * @throws SQLException from apptReminder().
     * */
    @FXML
    void loginBttnAction(ActionEvent event) throws IOException, SQLException{

        if (validUser()) {

            UserLog.writeLog(1);
            openApptsDisplay(event);
            apptReminder();
        } else {

            UserLog.writeLog(0);
            loginError();
        }
    }

    /** Initialize Controller after Root element has been processed.
     *
     * @param url URL Location used to resolve relative paths for Root Object or {@code null} when URL Location is unknown.
     * @param resourceBundle Resource Bundle used to localize Root Object or {@code null} when URL Location is unknown.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setDisplayLang();
        setTimeZoneLbl();

    }

    /** Load ApptsController.
     *
     * @param event Passed from Parent Method.
     * @throws IOException from FXMLLoader.
     * */
    private void openApptsDisplay(ActionEvent event) throws IOException{

        Parent parent = FXMLLoader.load(getClass().getResource("/view/Appts.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    /** Resource Bundle Setting Language Display Elements based on User Locale. */
    private void setDisplayLang(){

        userNameLbl.setText(rb.getString("userNameLbl"));
        passwdLbl.setText(rb.getString("passwdLbl"));
        timezoneLbl.setText(rb.getString("timezoneLbl"));
        cancelBttn.setText(rb.getString("cancelBttn"));
        loginBttn.setText(rb.getString("loginBttn"));

    }

    /** Set Timezone ID Label based on System Default Timezone. */
    private void setTimeZoneLbl(){

        zoneIdLbl.setText(userTimeZone.getID());

    }

    /** Pass User Object Currently Logged-In User to other Controllers.
     *
     * @return Current Logged-In User Object.
     * */
    public static User getCurrentUser(){

        return currentUser;

    }

    public String getWrongUserName() throws SQLException {

        String userInput = userNameTxt.getText();

        return userInput;

    }

    /** Get Username and Password details from Text Fields validating credentials with database.
     *
     * @return Boolean.
     * @throws SQLException from UserDao.selectByName().
     * */
    public Boolean validUser() throws SQLException{

        Boolean isValid = false;

        try{

            String userName = userNameTxt.getText();
            String passwd = passwdTxt.getText();
            currentUser = UserDao.selectByName(userName);

            if(currentUser.getPasswd() == null){
                //No Action - Populate when Username is missing in DB.
            }else if(currentUser.getPasswd().equals(passwd)){
                isValid = true;
            }

        }catch (SQLException e){

            System.out.println(e.getMessage());

        }

        return isValid;

    }

    /** Display Error Message for Incorrect Username or Password. */
    private void loginError(){

        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle(rb.getString("error"));
        alert.setHeaderText(rb.getString("errorTxt"));
        alert.showAndWait();

    }

    /** Validate appointments starting in next 15 minutes for Current User and Display appropriate message.
     *
     * @throws SQLException from ApptDao.selectAll().
     * */
    private void apptReminder() throws SQLException{

        try{
            boolean upcomingAppt = false;
            ObservableList<Appt> dbAppts = ApptDao.selectAll();
            LocalDateTime now = LocalDateTime.now();
            int currentUserID = currentUser.getUserID();

            for(Appt appt : dbAppts){

                LocalDateTime startTime = appt.getStartTime();

                if((appt.getUserID() == currentUserID) && startTime.isAfter(now) && startTime.isBefore(now.plusMinutes(15))){

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);

                    alert.setTitle("Informaton");
                    alert.setHeaderText("Appointment ID " + appt.getApptID() + " is starting at " + startTime.format(dateTimeFormat) + ".");
                    alert.showAndWait();

                    upcomingAppt = true;
                    break;
                }

            }

            if(!upcomingAppt){

                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Information");
                alert.setHeaderText("No Appointment Starting in next 15 minutes.");
                alert.showAndWait();

            }

        }catch (SQLException e){

            System.out.println(e.getMessage());

        }

    }


}

