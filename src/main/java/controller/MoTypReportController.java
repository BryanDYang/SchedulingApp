package controller;

import dao.ApptDao;
import model.Appt;
import model.MoTypReport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
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

/** Controller Class providing control logic for Month Type Report Display.
 * Contains Method with Example of Lambda use.
 *
 * @author Bryan Yang
 * */
public class MoTypReportController implements Initializable {

    /** Date Time Format */
    private final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");

    /** Month Column for Report Table. */
    @FXML
    private TableColumn<MoTypReport, Month> monthCol;

    /** Report Table. */
    @FXML
    private TableView<MoTypReport> reportTable;

    /** Report TimeStamp Label. */
    @FXML
    private Label timestampLbl;

    /** Total Column for Report Table. */
    @FXML
    private TableColumn<MoTypReport, Integer> totalCol;

    /** Type Column for Report Table. */
    @FXML
    private TableColumn<MoTypReport, String> typeCol;

    /** Year Column for Report Table. */
    @FXML
    private TableColumn<MoTypReport, Integer> yearCol;

    /** Load Report Controller.
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


    /** Set Reports Table to Display Count of Appointments based on by count of year, month and type.
     *
     * Lambda Expression: Method iterating through a collection of forEach and much less error-prone than trying to achieve same result with for loop iterator.
     * */
    private void genReport(){

        try{
            ObservableList<Integer> years = FXCollections.observableArrayList();
            ObservableList<Month> months = FXCollections.observableArrayList();
            ObservableList<String> types = FXCollections.observableArrayList();
            ObservableList<Appt> dbAppts = ApptDao.selectAll();
            ObservableList<MoTypReport> reportList = FXCollections.observableArrayList();

            // Get unique years - example of Lambda Expression.
            dbAppts.forEach(appt -> {
                Integer year = appt.getStartTime().getYear();
                if(!years.contains(year)) {years.add(year);}
            });

            // Get unique month - example of Lambda Expression.
            dbAppts.forEach(appt -> {
                Month month = appt.getStartTime().getMonth();
                if(!months.contains(month)){months.add(month);}
            });

            // Get unique types - example of Lambda Expression.
            dbAppts.forEach(appt -> {
                String type = appt.getApptTyp();
                if(!types.contains(type)){types.add(type);}
            });

            Integer year;
            Month month;
            String type;
            Appt appt;
            int typeCount;

            // Loop thru Years.
            for (int i = 0; i < years.size(); i++){
                year = years.get(i);

                // Loop thru Months.
                for(int k = 0; k < months.size(); k++){
                    month = months.get(k);

                    // Loop thru Types.
                    for(int j = 0; j < types.size(); j++){
                        type = types.get(j);
                        typeCount = 0;

                        // Loop thru Appointments.
                        for(int p = 0; p < dbAppts.size(); p++){
                            appt = dbAppts.get(p);

                            // If Appointment is Same Year, Month, and Type - Increment Count.
                            if(appt.getStartTime().getYear() == year && appt.getStartTime().getMonth().equals(month) && appt.getApptTyp().equals(type)){

                                typeCount++;
                            }
                        }

                        // If Count > 0: Build Report Display Object then Add to List to be Displayed.
                        if(typeCount > 0){
                            MoTypReport reportObject = new MoTypReport(year, month, type, typeCount);
                            reportList.add(reportObject);
                        }
                    }
                }
            }

            // Set Table List
            reportTable.setItems(reportList);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    /** Initialize Controller after Root Element has been Processed.
     *
     * @param url URL Location used to resolve relative paths for Root Object or {@code null} when URL Location is unknown.
     * @param resourceBundle Resource Bundle used to localize Root Object or {@code null} when Resource Bundle is unknown.
     * * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));
        monthCol.setCellValueFactory(new PropertyValueFactory<>("month"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        totalCol.setCellValueFactory(new PropertyValueFactory<>("total"));

        timestampLbl.setText(LocalDateTime.now().format(dateTimeFormat));

        genReport();

    }


}

