package controller;

import dao.ApptDao;
import dao.ContDao;
import dao.CustDao;
import dao.UserDao;
import javafx.util.converter.LocalTimeStringConverter;
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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

/** Controller Class providing control logic for Modify Appointment Display.
 *
 * @author Bryan Yang
 * */
public class ModifyApptsController implements Initializable {

    /** Time format for Start and end Time Spinners. */
    private final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

    /** Currently Logged-In User from LogicController. */
    private User currentUser = LoginController.getCurrentUser();

    /** List of Contact Objects for Contact Combo Box. */
    private ObservableList<Cont> contList = FXCollections.observableArrayList();

    /** List of Customer Objects for Customer Combo Box. */
    private ObservableList<Cust> custList = FXCollections.observableArrayList();

    /** List of User Objects for User Combo Box. */
    private ObservableList<User> userList = FXCollections.observableArrayList();

    /** Appointment Object Selected in ApptsController. */
    private Appt selectedAppt;

    /** Contact Object based on selected Appointment Object. */
    private Cont selectedCont;

    /** User Object based on selected Appointment Object. */
    private User selectedUser;

    /** Customer Object based on selected Appointment Object. */
    private Cust selectedCust;

    /** Appointment ID Text Field. */
    @FXML
    private TextField apptIdTxt;

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

    /** Type Text Field.  */
    @FXML
    private TextField typTxt;

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
    void cancelBttnAction(ActionEvent event) throws IOException{

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Cancel changes and return to Appointment Display?");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){

            Parent parent = FXMLLoader.load(getClass().getResource("/view/Appts.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        }

    }

    /** Set Value for Customer ID Text Field on Selected Customer Object.
     *
     * @param event Customer Combo Box Action.
     * */
    @FXML
    void custComboBoxAction(ActionEvent event) {

        selectedCust = custComboBox.getSelectionModel().getSelectedItem();
        custIdTxt.setText(String.valueOf(selectedCust.getCustID()));

    }

    /** Create New Appointment Object and with Selected Appointment Object ID and Update Database.
     * Perform Input Validation from Validation Methods VerifyAppt.
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
            String apptTyp = typTxt.getText();
            LocalDateTime startTime = getStartDateTime();
            LocalDateTime endTime = getEndDateTime();
            String createdBy = selectedAppt.getCreatedBy();
            String lastUpdatedBy = selectedAppt.getCreatedBy();
            int custID = selectedCust.getCustID();
            int userID = selectedUser.getUserID();
            int contID = contComboBox.getSelectionModel().getSelectedItem().getContID();

            Appt newAppt = new Appt(apptTitle, apptDescr, apptLoc, apptTyp, startTime, endTime, createdBy, lastUpdatedBy, custID, userID, contID);

            newAppt.setApptID(selectedAppt.getApptID());

            if(VerifyAppt.overlappingAppt(newAppt)){
                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setTitle("Error");
                alert.setHeaderText("Creating Appointment Overlaps with Existing Appointment.");
                alert.showAndWait();

            }

            if(!VerifyAppt.fieldsEmpty(newAppt) && !VerifyAppt.startAfterEnd(newAppt) && !VerifyAppt.outsideBusinessHours(newAppt) && !VerifyAppt.overlappingAppt(newAppt)) {

                ApptDao.update(newAppt);

                Parent parent = FXMLLoader.load(getClass().getResource("/view/Appts.fxml"));
                Scene scene = new Scene(parent);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();

            }

        }catch(NullPointerException e){

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Error");
            alert.setHeaderText("Field(s) are not selected.");
            alert.showAndWait();

        }catch (SQLException e){

            System.out.println(e.getMessage());

        }

    }

    /** Set value for selectedUser Object based on User Combo Box Selection.
     *
     * @param event User Combo Box Action.
     * */
    @FXML
    void userComboBoxAction(ActionEvent event) {

        selectedUser = userComboBox.getSelectionModel().getSelectedItem();
        userIdTxt.setText(String.valueOf(selectedUser.getUserID()));

    }

    /** Initialize Controller After Root Element has been processed.
     *
     * @param url URL Location used to resolve relative paths for Root Object or {@code null} when Location is unknown.
     * @param resourceBundle Resource Bundle used to localize Root Object or {@code null} when Resource Bundle is unkown.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setValues();

    }


    /** Set Values for Fields in Modify Appointment Display based on Appointment Object Selected in ApptsController. */
    private void setValues() {

        selectedAppt = ApptsController.getSelectedAppt();

        // Set Text Fields.
        apptIdTxt.setText(String.valueOf(selectedAppt.getApptID()));
        titleTxt.setText(selectedAppt.getApptTitle());
        descrTxt.setText(selectedAppt.getApptDescr());
        locTxt.setText(selectedAppt.getApptLoc());
        typTxt.setText(selectedAppt.getApptTyp());

        // Set Start Time Spinner.
        LocalTime startTime = selectedAppt.getStartTime().toLocalTime();
        startSVF.setValue(startTime);
        startTimeSpinner.setValueFactory(startSVF);

        // Set End Time Spinner.
        LocalTime endTime = selectedAppt.getEndTime().toLocalTime();
        endSVF.setValue(endTime);
        endTimeSpinner.setValueFactory(endSVF);

        // Set Start Date Picker.
        LocalDate startDate = selectedAppt.getStartTime().toLocalDate();
        startDatePicker.setValue(startDate);

        // Set End Date Picker.
        LocalDate endDate = selectedAppt.getEndTime().toLocalDate();
        endDatePicker.setValue(endDate);

        try{
            // Set Contact Combo Box
            contList = ContDao.selectAll();
            contComboBox.setItems(contList);
            selectedCont = ContDao.selectByID(selectedAppt.getContID());
            contComboBox.setValue(selectedCont);

            // Set Customer Combo Box
            custList = CustDao.selectAll();
            custComboBox.setItems(custList);
            selectedCust = CustDao.selectByID(selectedAppt.getCustID());
            custComboBox.setValue(selectedCust);
            custIdTxt.setText(String.valueOf(selectedCust.getCustID()));

            // Set User Combo Box
            userList = UserDao.selectAll();
            userComboBox.setItems(userList);
            selectedUser = UserDao.selectByID(selectedAppt.getUserID());
            userComboBox.setValue(selectedUser);
            userIdTxt.setText(String.valueOf(selectedUser.getUserID()));


        }catch (SQLException e){

            System.out.println(e.getMessage());

        }

    }

    /** Create LocalDateTime Object based on values set in Start Date Picker and Start Time Spinner.
     *
     * @return LocalDateTime Object.
     * */
    private LocalDateTime getStartDateTime(){

        LocalDate startDate = startDatePicker.getValue();
        LocalTime startTime = startTimeSpinner.getValue();
        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);

        return startDateTime;

    }

    /** Create LocalDateTime Object based on values set in End Date Picker and End Time Spinner.
     *
     * @return LocalDateTime Object.
     * */
    private LocalDateTime getEndDateTime(){

        LocalDate endDate = endDatePicker.getValue();
        LocalTime endTime = endTimeSpinner.getValue();
        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);

        return endDateTime;
    }

    /** Value Factory for Start Time Spinner based on 15 minutes Intervals. */
    SpinnerValueFactory startSVF = new SpinnerValueFactory<LocalTime>() {
        {
            setConverter(new LocalTimeStringConverter(timeFormat, null));
        }

        @Override
        public void decrement(int steps) {
            LocalTime localTime = (LocalTime) getValue();
            setValue(localTime.plusHours(steps));
            setValue(localTime.plusMinutes(steps + 14));
        }

        @Override
        public void increment(int steps) {
            LocalTime localTime = (LocalTime) getValue();
            setValue(localTime.minusHours(steps));
            setValue(localTime.minusMinutes(16 - steps));

        }
    };

    /** Value Factory for End Time Spinner based on 15 minutes Intervals. */
    SpinnerValueFactory endSVF = new SpinnerValueFactory<LocalTime>() {
        {
            setConverter(new LocalTimeStringConverter(timeFormat, null));
        }
        @Override
        public void decrement(int steps) {
            LocalTime localTime = (LocalTime) getValue();
            setValue(localTime.plusHours(steps));
            setValue(localTime.plusMinutes(steps + 14));
        }

        @Override
        public void increment(int steps) {
            LocalTime localTime = (LocalTime) getValue();
            setValue(localTime.minusHours(steps));
            setValue(localTime.minusMinutes(16 - steps));

        }
    };


}


