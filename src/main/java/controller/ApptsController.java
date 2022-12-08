package controller;

import dao.ApptDao;
import dao.ContDao;
import dao.CustDao;
import model.Appt;
import model.ApptDisplay;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

/** Controller Class Providing Control Logic for Appointments Display.
 *
 * @author Bryan Yang
 * */
public class ApptsController implements Initializable {

    /** Appointment Object Selected in Appointment Table. */
    private static Appt selectedAppt;

    /** All Radio Button */
    @FXML
    private RadioButton allRBttn;

    /** Appointment Table. */
    @FXML
    private TableView<Appt> apptsTable;

    /** Clear Search Button. */
    @FXML
    private Button clearSearchBttn;

    /** Contact Column in Appointments Table. */
    @FXML
    private TableColumn<Appt, String> contCol;

    /** Customer ID Column in Appointments Table. */
    @FXML
    private TableColumn<Appt, Integer> custIdCol;


    /** User ID Column in Appointments Table. */
    @FXML
    private TableColumn<Appt, Integer> userIdCol;

    /** Appointment Description Column in Appointments Table. */
    @FXML
    private TableColumn<Appt, String> descrCol;

    /** End Time Column in Appointments Table. */
    @FXML
    private TableColumn<Appt, String> endCol;

    /** Appointment ID Column in Appointments Table. */
    @FXML
    private TableColumn<Appt, Integer> idCol;

    /** Appointment Location Column in Appointments Table. */
    @FXML
    private TableColumn<Appt, String> locCol;

    /** Month Radio Button. */
    @FXML
    private RadioButton moRBttn;

    /** Search Button. */
    @FXML
    private Button searchBttn;

    /** Search Text Field. */
    @FXML
    private TextField searchTxt;

    /** Start Time Column in Appointments Table. */
    @FXML
    private TableColumn<Appt, String> startCol;

    /** Title Column in Appointments Table. */
    @FXML
    private TableColumn<Appt, String> titleCol;

    /** Toggle Group for Radio Button */
    @FXML
    private ToggleGroup toggleRBttn;

    /** Type Column in Appointments Table. */
    @FXML
    private TableColumn<Appt, String> typCol;

    /** Week Radio Button. */
    @FXML
    private RadioButton weekRBttn;

    /** Constructor for ApptsController Class. */
    public ApptsController(){}

    /** Calls DisplayAllAppts() method.
     *
     * @param event All Raido Button Action.
     * */
    @FXML
    void allRBttnAction(ActionEvent event) {

        try{

            displayAllAppts();

        }catch (SQLException e){

            System.out.println(e.getMessage());

        }

    }

    /** Clears Search Text Box.
     *
     * @param event Clear Search Button Action.
     * @throws SQLException from ApptDao.SelectAll().
     * */
    @FXML
    void clearSearchBttnAction(ActionEvent event) throws SQLException{

        searchTxt.setText("");

        if (allRBttn.isSelected()){
            displayAllAppts();
        } else if(moRBttn.isSelected()){
            displayApptsByMo();
        }else if(weekRBttn.isSelected()){
            displayApptsByWk();
        }

    }

    /** Loads CustController.
     *
     * @param event Cancel Button Action.
     * @throws IOException from FXMLLoader.
     * */
    @FXML
    void custBttnAction(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("/view/Custs.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    /** Display Confirmation Message End Deletes Selected Appointment.
     *
     * Appointment List Refreshed based on Radio Button Selected.
     *
     * @param event Delete Appointment Button Action.
     * */
    @FXML
    void deleteApptBttnAction(ActionEvent event) throws IOException {

        selectedAppt = apptsTable.getSelectionModel().getSelectedItem();

        if(selectedAppt == null){

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Error");
            alert.setHeaderText("Select Appointment from List");
            alert.showAndWait();

        }else{

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Delete Selected Appointment?");
            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK){

                try{
                    ApptDao.deleteByID(selectedAppt.getApptID());
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }

                Alert info = new Alert(Alert.AlertType.INFORMATION);

                info.setTitle("Information");
                info.setHeaderText("Appointment ID " + selectedAppt.getApptID() + " of Type " + selectedAppt.getApptTyp() + " has been cancelled.");
                info.showAndWait();

                if(allRBttn.isSelected()){

                    try{
                        displayAllAppts();
                    }catch (SQLException e){
                        System.out.println(e.getMessage());
                    }

                }else if(weekRBttn.isSelected()){
                    try{
                        displayApptsByWk();
                    }catch (SQLException e){
                        System.out.println(e.getMessage());
                    }
                }else if(moRBttn.isSelected()){
                    try{
                        displayApptsByMo();
                    }catch (SQLException e){
                        System.out.println(e.getMessage());
                    }
                }

            }

        }

    }

    /** Close/Terminate Application.
     *
     * @param event Exit Button Action.
     * */
    @FXML
    void exitBttnAction(ActionEvent event) {

        System.exit(0);

    }

    /** Call displayApptsByMo method.
     *
     * @param event Month Radio Button Action.
     * */
    @FXML
    void moRBttnAction(ActionEvent event) {

        try{

            displayApptsByMo();

        }catch (SQLException e){

            System.out.println(e.getMessage());

        }

    }

    /** Load ModifyApptsController.
     *
     * @paarm event Modify Appointments Button Action.
     * @throws IOException from FXMLLoader.
     * */
    @FXML
    void modifyApptBttnAction(ActionEvent event) throws IOException {

        selectedAppt = apptsTable.getSelectionModel().getSelectedItem();

        if (selectedAppt == null){

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Error");
            alert.setHeaderText("Select Appointment from List.");
            alert.showAndWait();

        }else {

            Parent parent = FXMLLoader.load(getClass().getResource("/view/ModifyAppts.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        }

    }

    /** Load NewApptController.
     *
     * @param event New Appointment Button Action.
     * @throws IOException from FXMLLoader.
     * */
    @FXML
    void newApptBttnAction(ActionEvent event) throws IOException{

        Parent parent = FXMLLoader.load(getClass().getResource("/view/NewAppt.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    /** Load ReportsController.
     *
     * @param event Reports Button Action.
     * @throws IOException from FXMLLoader.
     * */
    @FXML
    void reportsBttnAction(ActionEvent event) throws IOException{

        Parent parent = FXMLLoader.load(getClass().getResource("/view/Report.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    /** Initiate Appointment Search.
     *
     * @param event Search Button Action.
     * @throws SQLException from ApptDao.selectAll().
     * */
    @FXML
    void searchBttnAction(ActionEvent event) throws SQLException{

        if(allRBttn.isSelected()){

            displayAllAppts();

        }else if(moRBttn.isSelected()){

            displayApptsByMo();

        }else if(weekRBttn.isSelected()){

            displayApptsByWk();

        }

    }

    /** Calls displayApptsByWk() method.
     *
     * @param event Week Radio Button Action.
     * */
    @FXML
    void weekRBttnAction(ActionEvent event) {

        try{

            displayApptsByWk();

        }catch (SQLException e){

            System.out.println(e.getMessage());

        }

    }

    /** Called Initialize Controller after Root Element has been processed.
     *
     * @param url URL Location used to resolve relative paths for Root Object or {@code null} when URL Location is unknown.
     * @param resourceBundle Resource Bundle used to Localize Root Object or {@Code null} when Resource Bundle is unknown.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        idCol.setCellValueFactory(new PropertyValueFactory<>("apptID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
        descrCol.setCellValueFactory(new PropertyValueFactory<>("apptDescr"));
        locCol.setCellValueFactory(new PropertyValueFactory<>("apptLoc"));
        contCol.setCellValueFactory(new PropertyValueFactory<>("contName"));
        typCol.setCellValueFactory(new PropertyValueFactory<>("apptTyp"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startTimeString"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endTimeString"));
        custIdCol.setCellValueFactory(new PropertyValueFactory<>("custID"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userID"));

        try{

            displayAllAppts();

        }catch (SQLException e){

            System.out.println(e.getMessage());

        }

        allRBttn.setSelected(true);

    }

    /** Sets Appointments Table to Show All Appointments.
     *
     * @throws SQLException from ApptDao.selectAll().
     * */
    private void displayAllAppts() throws SQLException{

        try{

            ObservableList<Appt> dbAppts = ApptDao.selectAll();
            ObservableList<Appt> appts = FXCollections.observableArrayList();

            for(Appt appt : dbAppts){

                ApptDisplay newAppt = new ApptDisplay(appt);
                appts.add(newAppt);

            }

            String searchString = searchTxt.getText();

            if(!searchString.equals("")){
                ObservableList<Appt> filteredAppts = FXCollections.observableArrayList();
                String apptTitle;
                String apptDescr;
                String apptLoc;
                String apptCont;
                String apptTyp;

                for(Appt appt : appts){
                    apptTitle = appt.getApptTitle();
                    apptDescr = appt.getApptDescr();
                    apptLoc = appt.getApptLoc();
                    apptCont = ContDao.selectByID(appt.getContID()).getContName();
                    apptTyp = appt.getApptTyp();

                    if(apptTitle.contains(searchString)){
                        filteredAppts.add(appt);
                    }else if(apptDescr.contains(searchString)){
                        filteredAppts.add(appt);
                    }else if(apptLoc.contains(searchString)){
                        filteredAppts.add(appt);
                    }else if(apptCont.contains(searchString)){
                        filteredAppts.add(appt);
                    }else if(apptTyp.contains(searchString)){
                        filteredAppts.add(appt);
                    }


                }
                apptsTable.setItems(filteredAppts);
            }else {
                apptsTable.setItems(appts);
            }
        }catch (SQLException e){

            System.out.println(e.getMessage());

        }

    }

    /** Set Appointments Table to Show Appointments with Start Time in seven days.
     *
     * @throws SQLException from ApptDao.selectAll().
     * */
    private void displayApptsByWk() throws SQLException{

        try{
            ObservableList<Appt> dbAppts = ApptDao.selectAll();
            ObservableList<Appt> appts = FXCollections.observableArrayList();

            LocalDate todayDate = LocalDate.now();
            LocalTime midnight = LocalTime.MIDNIGHT;
            LocalDateTime todayMidnight = LocalDateTime.of(todayDate, midnight);
            LocalDateTime oneWeek = todayMidnight.plusWeeks(1);

            for(Appt appt : dbAppts){

                if(appt.getStartTime().isAfter(todayMidnight) && appt.getStartTime().isBefore(oneWeek)){
                    ApptDisplay newAppt = new ApptDisplay(appt);
                    appts.add(newAppt);
                }

            }

            String searchString = searchTxt.getText();

            if(!searchString.equals("")){
                ObservableList<Appt> filteredAppts = FXCollections.observableArrayList();
                String apptTitle;
                String apptDescr;
                String apptLoc;
                String apptCont;
                String apptTyp;

                for(Appt appt : appts){
                    apptTitle = appt.getApptTitle();
                    apptDescr = appt.getApptDescr();
                    apptLoc = appt.getApptLoc();
                    apptCont = ContDao.selectByID(appt.getContID()).getContName();
                    apptTyp = appt.getApptTyp();

                    if(apptTitle.contains(searchString)){
                        filteredAppts.add(appt);
                    }else if(apptDescr.contains(searchString)){
                        filteredAppts.add(appt);
                    }else if(apptLoc.contains(searchString)){
                        filteredAppts.add(appt);
                    }else if(apptCont.contains(searchString)){
                        filteredAppts.add(appt);
                    }else if(apptTyp.contains(searchString)){
                        filteredAppts.add(appt);
                    }
                }
                apptsTable.setItems(filteredAppts);
            }else{
                apptsTable.setItems(appts);
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    /** Sets Appointments Table to Show Appointments with Start Time in Thirty Days.
     *
     * @throws SQLException from ApptDao.selectAll().
     * */
    private void displayApptsByMo() throws SQLException{

        try{
            ObservableList<Appt> dbAppts = ApptDao.selectAll();
            ObservableList<Appt> appts = FXCollections.observableArrayList();

            LocalDate todayDate = LocalDate.now();
            LocalTime midnight = LocalTime.MIDNIGHT;
            LocalDateTime todayMidnight = LocalDateTime.of(todayDate, midnight);
            LocalDateTime oneMonth = todayMidnight.plusMonths(1);

            for(Appt appt : dbAppts){
                if (appt.getStartTime().isAfter(todayMidnight) && appt.getStartTime().isBefore(oneMonth)) {
                    ApptDisplay newAppt = new ApptDisplay(appt);
                    appts.add(newAppt);
                }
            }

            String searchString = searchTxt.getText();

            if(!searchString.equals("")) {
                ObservableList<Appt> filteredAppts = FXCollections.observableArrayList();
                String apptTitle;
                String apptDescr;
                String apptLoc;
                String apptCont;
                String apptTyp;

                for (Appt appt : appts) {
                    apptTitle = appt.getApptTitle();
                    apptDescr = appt.getApptDescr();
                    apptLoc = appt.getApptLoc();
                    apptCont = ContDao.selectByID(appt.getContID()).getContName();
                    apptTyp = appt.getApptTyp();

                    if (apptTitle.contains(searchString)) {
                        filteredAppts.add(appt);
                    } else if (apptDescr.contains(searchString)) {
                        filteredAppts.add(appt);
                    } else if (apptLoc.contains(searchString)) {
                        filteredAppts.add(appt);
                    } else if (apptCont.contains(searchString)) {
                        filteredAppts.add(appt);
                    } else if (apptTyp.contains(searchString)) {
                        filteredAppts.add(appt);
                    }
                }
                apptsTable.setItems(filteredAppts);
            }else {
                apptsTable.setItems(appts);
            }

        }catch (SQLException e){

            System.out.println(e.getMessage());

        }

    }

    /** Pass Selected Appointment Object to other controllers.
     *
     * @return selectedAppt Object.
     * */
    public static Appt getSelectedAppt(){
        return selectedAppt;
    }

}
