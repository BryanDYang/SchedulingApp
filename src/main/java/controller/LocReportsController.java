package controller;

import dao.ApptDao;
import model.Appt;
import model.ApptDisplay;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/** Controller Class providing control logic for location report display.
 *
 * @author Bryan Yang
 * */
public class LocReportsController implements Initializable {

    /**  */
    private final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("MM-dd-yyy HH:mm:ss");

    /** Contact Column for Report Table. */
    @FXML
    private TableColumn<Appt, String> contCol;

    /** Customer ID Column for Report Table. */
    @FXML
    private TableColumn<Appt, Integer> custIdCol;

    /** End Time Column for Report Table. */
    @FXML
    private TableColumn<Appt, String> endCol;

    /** Appointment ID Column for Report Table. */
    @FXML
    private TableColumn<Appt, Integer> idCol;

    /** Location Column for Report Table. */
    @FXML
    private TableColumn<Appt, String> locCol;

    /** Report Table. */
    @FXML
    private TableView<Appt> reportTable;

    /** Start Time Column for Report Table. */
    @FXML
    private TableColumn<Appt, String> startCol;

    /** Timestamp Label. */
    @FXML
    private Label timestampLbl;

    /** Title Column for Report Table. */
    @FXML
    private TableColumn<Appt, String> titleCol;

    /** Type Column for Report Table. */
    @FXML
    private TableColumn<Appt, String> typCol;

    /** Load ReportsController.
     *
     * @param event Close Button Action.
     * @throws IOException from FXMLLoader.
     * */
    @FXML
    void closeBttnAction(ActionEvent event) throws IOException{

        Parent parent = FXMLLoader.load(getClass().getResource("/view/Report.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();


    }

    /** Initialize Controller after Root Element has been processed.
     *
     * @param url URL Location used to resolve relative paths for Root Object or {@code null} when location is unknown.
     * @param resourceBundle Resource Bundle used to Localize Root Object or {@code null} when locaiton is unknown.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        idCol.setCellValueFactory(new PropertyValueFactory<>("apptID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
        locCol.setCellValueFactory(new PropertyValueFactory<>("apptLoc"));
        contCol.setCellValueFactory(new PropertyValueFactory<>("contName"));
        typCol.setCellValueFactory(new PropertyValueFactory<>("apptTyp"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startTimeString"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endTimeString"));
        custIdCol.setCellValueFactory(new PropertyValueFactory<>("custID"));

        timestampLbl.setText(LocalDateTime.now().format(dateTimeFormat));

        genReport();

    }

    /** Set Reports Table to Show All Appointments Ordered by Location.
     *
     * Usage of Wrapper Class for Display of Appointments.
     * */
    private void genReport(){

        try{
            ObservableList<Appt> dbAppts = ApptDao.selectAllOrderByLocation();
            ObservableList<Appt> appts = FXCollections.observableArrayList();

            for(Appt appt : dbAppts){

                ApptDisplay newAppt = new ApptDisplay(appt);
                appts.add(newAppt);

            }

            reportTable.setItems(appts);

        }catch (SQLException e){

            System.out.println(e.getMessage());

        }

    }

}


