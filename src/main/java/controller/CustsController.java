package controller;

import dao.ApptDao;
import dao.CustDao;
import javafx.scene.Node;
import model.Appt;
import model.Cust;
import model.CustDisplay;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/** Controller Class Providing Control Logic for Customer Display. Contains Method with an example of Lambda use.
 *
 * @author Bryan Yang
 * */
public class CustsController implements Initializable {

    /** Customer Object Selected in Customers Table. */
    private static Cust selectedCust;

    /** Address Column for Customers Table. */
    @FXML
    private TableColumn<CustDisplay, String> addressCol;

    /** Clear Search Button. */
    @FXML
    private Button clearSearchBttn;

    /** Customers Table */
    @FXML
    private TableView<CustDisplay> custsTable;

    /** Division Column for Customers Table. */
    @FXML
    private TableColumn<CustDisplay, String> divCol;

    /** Name Column for Customers Table. */
    @FXML
    private TableColumn<CustDisplay, String> nameCol;

    /** Phone Number Column for Customers Table. */
    @FXML
    private TableColumn<CustDisplay, String> phoneCol;

    /** Postal Code Column for Customers Table. */
    @FXML
    private TableColumn<CustDisplay, String> postalCol;

    /** Search Button. */
    @FXML
    private Button searchBttn;

    /** Search Text Field. */
    @FXML
    private TextField searchTxt;

    /** Load ApptsController.
     *
     * @param event Back Button Action.
     * @throws IOException from FXMLLoader.
     * */
    @FXML
    void backBttnAction(ActionEvent event) throws IOException{

        Parent parent = FXMLLoader.load(getClass().getResource("/view/Appts.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    /** Clear Search Text Field.
     *
     * @param event Clear Search Button Action.
     * @throws SQLException from CustDao.selectAll().
     * */
    @FXML
    void clearSearchBttnAction(ActionEvent event) throws SQLException{

        searchTxt.setText("");
        displayAllCusts();

    }

    /** Display Confirmation Message and Delete Selected Customer.
     * Delete All Appointments Associated with Selected Customer.
     *
     * @param event Delete Customer Button Action.
     * @throws SQLException from ApptDao.selectAll(), ApptDao.deleteByID(), CustDao.deleteByID().
     * */
    @FXML
    void deleteCustBttnAction(ActionEvent event) throws SQLException {

        selectedCust = custsTable.getSelectionModel().getSelectedItem();

        if(selectedCust == null){

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("ERROR");
            alert.setHeaderText("Select Customer from List.");
            alert.showAndWait();

        }else{

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Delete Selected Customer?\n" + "Deleting Customer deletes ALL associated appointments.");
            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK){

                try{

                    ObservableList<Appt> dbAppts = ApptDao.selectAll();

                    for(Appt appt : dbAppts){

                        if(appt.getCustID() == selectedCust.getCustID()){

                            ApptDao.deleteByID(appt.getApptID());

                        }

                    }
                    CustDao.deleteByID(selectedCust.getCustID());
                }catch (SQLException e){
                    System.out.println(e.getMessage());
                }

                Alert info = new Alert(Alert.AlertType.INFORMATION);

                info.setTitle("Information");
                info.setHeaderText("Customer" + selectedCust.getCustName() + " and ALL associated appointment have been deleted.");
                info.showAndWait();

                displayAllCusts();

            }

        }

    }

    /** Loads ModifyCustController.
     *
     * @param event Modify Customer Button Action.
     * @throws IOException from FXMLLoader.
     * */
    @FXML
    void modifyCustBttnAction(ActionEvent event) throws IOException {

        selectedCust = custsTable.getSelectionModel().getSelectedItem();

        if(selectedCust == null){

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Error");
            alert.setHeaderText("Select Customer from List");
            alert.showAndWait();

        }else{

            Parent parent = FXMLLoader.load(getClass().getResource("/view/ModifyCusts.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        }

    }

    /** Load NewCustController.
     *
     * @param event New customer Button Action.
     * @throws IOException from FXMLLoader.
     * */
    @FXML
    void newCustBttnAction(ActionEvent event) throws IOException{

        Parent parent = FXMLLoader.load(getClass().getResource("/view/NewCust.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    /** Initiate Customer Search.
     *
     * @param event Search Button Action.
     * @throws SQLException from CustDao.selectAll().
     * */
    @FXML
    void searchBttnAction(ActionEvent event) throws IOException, SQLException {

        displayAllCusts();

    }

    /** Initialize Controller after Root Element has been processed.
     *
     * Lambda Expression: Populate Values for Customers Tableview.
     * Takes advantage of JavaFX tableview cell data object thus more concise than implementation of callback of abstract class.
     *
     * @param url URL Location used to resolve relative paths for Root Object or {@code null} when URL Location is unknown.
     * @param resourceBundle Resource Bundle to localize Root object or {@code null} when Resource Bundle is unknown.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Lambdas: Set Cell Values
        nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustName()));
        addressCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));
        postalCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPostalCode()));
        phoneCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhone()));
        divCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDivName()));

        try{

            displayAllCusts();

        }catch (SQLException e){

            System.out.println(e.getMessage());

        }

    }

    /** Set Customer Table to Display All Customers.
     * Wrapper Class used for Customer Object Display.
     *
     * @throws SQLException from CustDao.selectAll().
     * */
    private void displayAllCusts() throws SQLException{

        try{
            ObservableList<Cust> dbCusts = CustDao.selectAll();
            ObservableList<CustDisplay> custs = FXCollections.observableArrayList();

            for(Cust cust : dbCusts){

                CustDisplay newCust = new CustDisplay(cust);
                custs.add(newCust);

            }

            String searchString = searchTxt.getText();

            if(!searchString.equals("")){
                ObservableList<CustDisplay> filertedCusts = FXCollections.observableArrayList();
                String custName;
                String custAddress;
                String custPostalCode;
                String custPhone;
                String custDivName;

                for(CustDisplay cust : custs){
                    custName = cust.getCustName();
                    custAddress = cust.getAddress();
                    custPostalCode = cust.getPostalCode();
                    custPhone = cust.getPhone();
                    custDivName = cust.getDivName();

                    if(custName.contains(searchString)){
                        filertedCusts.add(cust);
                    }else if(custAddress.contains(searchString)){
                        filertedCusts.add(cust);
                    }else if(custPostalCode.contains(searchString)){
                        filertedCusts.add(cust);
                    }else if(custPhone.contains(searchString)){
                        filertedCusts.add(cust);
                    }else if(custDivName.contains(searchString)){
                        filertedCusts.add(cust);
                    }
                }
                custsTable.setItems(filertedCusts);
            }else{
                custsTable.setItems(custs);
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    /** Pass Selected Customer Object to other controllers.
     *
     * @return Customer Object.
     * */
    public static Cust getSelectedCust(){
        return selectedCust;
    }

}

