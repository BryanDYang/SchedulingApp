package controller;

import dao.ApptDao;
import dao.ContDao;
import dao.CustDao;
import dao.UserDao;
import model.Appt;
import model.Cont;
import model.Cust;
import model.User;
import utilities.VerifyAppt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.converter.LocalTimeStringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

/** Controller Class providing control logic for New Appointment Display.
 *
 * @author Bryan Yang
 * */
public class NewApptController implements Initializable {

    /** Date Time Format. */
    private final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

    /** Currently Logged-In User Object from LoginController. */
    private User currentUser = LoginController.getCurrentUser();

    /** List of Contact Objects for Contact Combo Box. */
    private ObservableList<Cont> contList = FXCollections.observableArrayList();

    /** List of Customer Objects for Customer Combo Box. */
    private ObservableList<Cust> custList = FXCollections.observableArrayList();

    /** List of User Objects for User Combo Box. */
    private ObservableList<User> userList = FXCollections.observableArrayList();

    /** Contact Object Selected in Contact Combo Box. */
    private Cont selectedCont;

    /** Customer Object Selected in Customer Combo Box. */
    private Cust selectedCust;

    /** User Object Selected in User Combo Box. */
    private User selectedUser;

    /** Contact Combo Box. */
    @FXML
    private ComboBox<Cont> contComboBox;

    /** Customer Combo Box. */
    @FXML
    private ComboBox<Cust> custComboBox;

    /** Customer ID Text Field. */
    @FXML
    private TextField custIdTxt;

    /** Description Text Field. */
    @FXML
    private TextField descrTxt;

    /** End Date Picker Object. */
    @FXML
    private DatePicker endDatePicker;

    /** End Time Spinner Object. */
    @FXML
    private Spinner<LocalTime> endTimeSpinner;

    /** Location Text Field. */
    @FXML
    private TextField locTxt;

    /** Start Date Picker Object. */
    @FXML
    private DatePicker startDatePicker;

    /** Start Time Spinner Object. */
    @FXML
    private Spinner<LocalTime> startTimeSpinner;

    /** Title Text Field. */
    @FXML
    private TextField titleTxt;

    /** Type Text Field. */
    @FXML
    private TextField typeText;

    /** User Combo Box. */
    @FXML
    private ComboBox<User> userComboBox;

    /** User ID Text Field. */
    @FXML
    private TextField userIdTxt;

    /** Display Confirmation Message and Load ApptsController.
     *
     * @param event Cancel Button Action.
     * @throws IOException from FXMLLoader.
     * */
    @FXML
    void cancelBttnnAction(ActionEvent event) throws IOException{

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Cancel changes and return to Appointment Display?");
        Optional<ButtonType>result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){

            Parent parent = FXMLLoader.load(getClass().getResource("/view/Appts.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        }

    }

    /** Set Value for selectedCust Object and Value for Customer ID Text Field.
     *
     * @param event Customer Combo Box Action.
     * */
    @FXML
    void custComboBoxAction(ActionEvent event) {

        selectedCust = custComboBox.getSelectionModel().getSelectedItem();
        custIdTxt.setText(String.valueOf(selectedCust.getCustID()));

    }

    /** Create New Appointment Object and Save to Database.
     * Perform Input Validation with Methods from VerifyAppt.
     *
     * @param event Save Button Action.
     * @throws IOException from FXMLLoader.
     * */
    @FXML
    void saveBttnAction(ActionEvent event) throws IOException{

        try{

            String apptTitle = titleTxt.getText();
            String apptDescr = descrTxt.getText();
            String apptLoc = locTxt.getText();
            String apptTyp = typeText.getText();
            LocalDateTime startTime = getStartDateTime();
            LocalDateTime endTime = getEndDateTime();
            String createdBy = currentUser.getUserName();
            String lastUpdatedBy = currentUser.getUserName();
            int custID = selectedCust.getCustID();
            int userID = selectedUser.getUserID();
            int contID = contComboBox.getSelectionModel().getSelectedItem().getContID();

            Appt newAppt = new Appt(apptTitle, apptDescr, apptLoc, apptTyp, startTime, endTime, createdBy, lastUpdatedBy, custID, userID, contID);

            if(VerifyAppt.overlappingAppt(newAppt)){

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("New Appointment Overlaps with Existing Appointment.");
                alert.showAndWait();

            }

            if(!VerifyAppt.fieldsEmpty(newAppt) && !VerifyAppt.startAfterEnd(newAppt) && !VerifyAppt.outsideBusinessHours(newAppt) && !VerifyAppt.overlappingAppt(newAppt)){

                ApptDao.create(newAppt);

                Parent parent = FXMLLoader.load(getClass().getResource("/view/Appts.fxml"));
                Scene scene = new Scene(parent);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();

            }

        }catch (NullPointerException e){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Field(s) not selected.");
            alert.showAndWait();

        }catch (SQLException e){

            System.out.println(e.getMessage());

        }

    }

    /** Set Value for SelectedUser Object and Value of User ID Text Field.
     *
     * @param event User Combo Box Action.
     * */
    @FXML
    void userComboBoxAction(ActionEvent event) {

        selectedUser = userComboBox.getSelectionModel().getSelectedItem();
        userIdTxt.setText(String.valueOf(selectedUser.getUserID()));

    }

    /** Initialize Controller after Root Element has been processed.
     *
     * @param url URL Location used to resolve relative paths for Root Object or {@code null} when URL Location is unknown.
     * @param resourceBundle Resource Bundle used to localize Root Object or {@code null} when Resource Bundle is unknown.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setDefaultValues();

    }

    /** Set Default Value for Timer Spinner and Combo Boxes. */
    private void setDefaultValues(){

        startSVF.setValue(LocalTime.of(8,00));
        startTimeSpinner.setValueFactory(startSVF);
        endSVF.setValue(LocalTime.of(22, 00));
        endTimeSpinner.setValueFactory(endSVF);

        try{

            contList = ContDao.selectAll();
            contComboBox.setItems(contList);

            custList = CustDao.selectAll();
            custComboBox.setItems(custList);

            userList = UserDao.selectAll();
            userComboBox.setItems(userList);

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    /** Create LocalDateTime Object based on Values of Start Date Picker and Start Time Spinner.
     *
     * @return LocalDateTime Object.
     * */
    private LocalDateTime getStartDateTime(){

        LocalDate startDate = startDatePicker.getValue();
        LocalTime startTime = startTimeSpinner.getValue();
        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);

        return startDateTime;
    }

    /** Create LocalDateTime Object based on Values of End Date Picker and End Time Spinner.
     *
     * @return LocalDateTime Object.
     * */
    private LocalDateTime getEndDateTime(){

        LocalDate endDate = endDatePicker.getValue();
        LocalTime endTime = endTimeSpinner.getValue();
        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);

        return  endDateTime;
    }

    /** Value Factory for Start Time Spinner based on 15 minutes Intervals. */
    SpinnerValueFactory startSVF = new SpinnerValueFactory<LocalTime>() {
        {
            setConverter(new LocalTimeStringConverter(timeFormat, null));
        }

        @Override
        public void increment(int steps) {
            LocalTime localTime = (LocalTime) getValue();
            setValue(localTime.minusHours(steps));
            setValue(localTime.minusMinutes(16 - steps));

        }

        @Override
        public void decrement(int steps) {
            LocalTime localTime = (LocalTime) getValue();
            setValue(localTime.plusHours(steps));
            setValue(localTime.plusMinutes(steps + 14));
        }

    };

    /** Value Factory for End Time Spinner based on 15 minutes Intervals. */
    SpinnerValueFactory endSVF = new SpinnerValueFactory<LocalTime>() {
        {
            setConverter(new LocalTimeStringConverter(timeFormat, null));

        }
            @Override
            public void increment ( int steps){
            LocalTime localTime = (LocalTime) getValue();
            setValue(localTime.minusHours(steps));
            setValue(localTime.minusMinutes(16 - steps));

            }

            @Override
            public void decrement ( int steps){
            LocalTime localTime = (LocalTime) getValue();
            setValue(localTime.plusHours(steps));
            setValue(localTime.plusMinutes(steps + 14));
            }


    };

}
