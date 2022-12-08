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

/** Controller Class providing Control Logic for Contact Schedule Report Display.
 *
 * @author Bryan Yang
 * */
public class SchedReportController implements Initializable {

    /** Date Time Format. */
    private final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("MM-dd-yyy HH:mm:ss");

    /** Contact Column for Report Table. */
    @FXML
    private TableColumn<Appt, String> contCol;

    /** Customer ID Column for Report Table. */
    @FXML
    private TableColumn<Appt, Integer> custIdCol;

    /** Description Column for Report Table. */
    @FXML
    private TableColumn<Appt, String> descrCol;

    /** End Time Column for Report Table. */
    @FXML
    private TableColumn<Appt, String> endCol;

    /** Appointment ID Column for Report Table. */
    @FXML
    private TableColumn<Appt, Integer> idCol;

    /** Report Table. */
    @FXML
    private TableView<Appt> reportTable;

    /** Start Time Column for Report Table. */
    @FXML
    private TableColumn<Appt, String> startCol;

    /** Report Timestamp Label */
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
     * @param url URL Location used to resolve relative paths for Root Object or {@code null} when URL Location is unknown.
     * @param resourceBundle Resource Bundle used to localize Root Object or {@code null} when Resource Bundle is unknown.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        idCol.setCellValueFactory(new PropertyValueFactory<>("apptID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
        descrCol.setCellValueFactory(new PropertyValueFactory<>("apptDescr"));
        contCol.setCellValueFactory(new PropertyValueFactory<>("contName"));
        typCol.setCellValueFactory(new PropertyValueFactory<>("apptTyp"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startTimeString"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endTimeString"));
        custIdCol.setCellValueFactory(new PropertyValueFactory<>("custID"));

        timestampLbl.setText(LocalDateTime.now().format(dateTimeFormat));

        genReport();

    }

    /** Set Report Table to Show List of Appointments input by Contact. */
    private void genReport(){

        try{

            ObservableList<Appt> dbAppts = ApptDao.selectAllOrderByContact();
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
