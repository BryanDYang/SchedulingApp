package controller;

import dao.CtryDao;
import dao.CustDao;
import dao.DivDao;
import model.Ctry;
import model.Cust;
import model.Div;
import model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utilities.VerifyCust;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/** Controller Class providing control logic for Modify Customer Display.
 *
 * @author Bryan Yang
 * */
public class ModifyCustsController implements Initializable {

    /** List of Country Objects for Country Combo Box. */
    private ObservableList<Ctry> ctryList = FXCollections.observableArrayList();

    /** Currently Logged-In User from LogicController. */
    private User currentUser = LoginController.getCurrentUser();

    /** Customer Object Selected in CustController. */
    private Cust selectedCust;

    /** Customer Address Text Field. */
    @FXML
    private TextField addressTxt;

    /** Country Combo Box. */
    @FXML
    private ComboBox<Ctry> ctryComboBox;

    /** Customer ID Text Field. */
    @FXML
    private TextField custIdTxt;

    /** Division Combo Box. */
    @FXML
    private ComboBox<Div> divComboBox;

    /** Customer Name Text Field. */
    @FXML
    private TextField nameTxt;

    /** Customer Phone Number Text Field. */
    @FXML
    private TextField phoneTxt;

    /** Customer Postal Code Text Field. */
    @FXML
    private TextField postalTxt;

    /** Display Confirmation Message and Load CustController.
     *
     * @param event Cancel Button Action.
     * @throws IOException from FXMLLoader.
     * */
    @FXML
    void cancelBttnAction(ActionEvent event) throws IOException{

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Cancel changes and return to Customer Display?");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){

            Parent parent = FXMLLoader.load(getClass().getResource("/view/Custs.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        }

    }

    /** Set Value for Country Combo Box and Filter Division Combo Box based on Country Value.
     *
     * @param event Country Combo Box Action.
     * */
    @FXML
    void ctryComboBoxAction(ActionEvent event) {

        try{
            Ctry selectedCtry = ctryComboBox.getSelectionModel().getSelectedItem();
            ObservableList<Div> dbDiv = DivDao.selectAll();
            ObservableList<Div> divByCtry = FXCollections.observableArrayList();

            for(Div div : dbDiv){

                if(div.getCtryID() == selectedCtry.getCtryID()) {

                    divByCtry.add(div);

                }

            }

            divComboBox.setItems(divByCtry);
            divComboBox.setValue(null);

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    /** Create New Customer Object with Database ID from Selected Customer and Update Database.
     *
     * @param event Save Button Action.
     * @throws IOException from FXMLLoader.
     * */
    @FXML
    void saveBttnAction(ActionEvent event) throws IOException {

        try{

            String custName = nameTxt.getText();
            String address = addressTxt.getText();
            String postalCode = postalTxt.getText();
            String phone = phoneTxt.getText();
            String createdBy = selectedCust.getCreatedBy();
            String lastUpdatedBy = currentUser.getUserName();
            int divID = divComboBox.getSelectionModel().getSelectedItem().getDivID();

            Cust newCust = new Cust (custName, address, postalCode, phone, createdBy, lastUpdatedBy, divID);

            newCust.setCustID(selectedCust.getCustID());

            if(!VerifyCust.fieldsEmpty(newCust)){

                CustDao.update(newCust);

                Parent parent = FXMLLoader.load(getClass().getResource("/view/Custs.fxml"));
                Scene scene = new Scene(parent);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();

            }

        }catch (NullPointerException e){

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Error");
            alert.setHeaderText("Field(s) not selected.");
            alert.showAndWait();

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    /** Initialize Controller after Root Element has been processed.
     *
     * @param url URL Location used to resolve relative paths for Root Object or {@code null} when URL Location is unknown.
     * @param resourceBundle Resource Bundle used to localize Root Object or {@code null} when Resource Bundle is unknown.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setValues();

    }

    /** Set Fields Values based on Customer Object selected in CustController. */
    private void setValues(){

        selectedCust = CustsController.getSelectedCust();

        // Set Text Fields.
        custIdTxt.setText(String.valueOf(selectedCust.getCustID()));
        nameTxt.setText(selectedCust.getCustName());
        addressTxt.setText(selectedCust.getAddress());
        postalTxt.setText(selectedCust.getPostalCode());
        phoneTxt.setText(selectedCust.getPhone());

        try{
            // Get Selected Division then Country.
            Div selectedDiv = DivDao.selectByID(selectedCust.getDivID());
            Ctry ctry = CtryDao.selectByID(selectedDiv.getCtryID());

            // Set Country List.
            ctryList = CtryDao.selectAll();
            ctryComboBox.setItems(ctryList);
            ctryComboBox.setValue(ctry);

            // Set Division List
            ObservableList<Div> dbDivs = DivDao.selectAll();
            ObservableList<Div> divByCtry = FXCollections.observableArrayList();

            for (Div div : dbDivs){

                if(div.getCtryID() == ctry.getCtryID()){

                    divByCtry.add(div);

                }

            }

            divComboBox.setItems(divByCtry);
            divComboBox.setValue(selectedDiv);

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }


    }

}
