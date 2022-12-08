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

/** Controller Class providing Control Logic for New Customer Display.
 *
 * @author Bryan Yang
 * */
public class NewCustController implements Initializable {

    /** List of Countries for Country Combo Box. */
    private ObservableList<Ctry> ctryList = FXCollections.observableArrayList();

    /** User Object for Currently Logged-In User Obtain from LoginController. */
    private User currentUser = LoginController.getCurrentUser();

    /** Customer Address Text Field. */
    @FXML
    private TextField addressTxt;

    /** Country Combo Box. */
    @FXML
    private ComboBox<Ctry> ctryComboBox;

    /** Division Combo Box. */
    @FXML
    private ComboBox<Div> divComboBox;

    /** Customer Name Text Field. */
    @FXML
    private TextField nameTxt;

    /** Customer Phone Number Text Field.  */
    @FXML
    private TextField phoneTxt;

    /** Customer Postal Code Text Field. */
    @FXML
    private TextField postalTxt;

    /** Display Confirmation Message and Load CustsController.
     *
     * @param event Cancel Button Action.
     * @throws IOException from FXMLLoader.
     * */
    @FXML
    void cancelBttnAction(ActionEvent event) throws IOException{

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Cancel changes and return to Customer Display?");
        Optional<ButtonType>result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){

            Parent parent = FXMLLoader.load(getClass().getResource("/view/Custs.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        }

    }

    /** Set Value for selectedCtry and Filter List for Division Combo Box.
     *
     * @param event Country Combo Box Action.
     * */
    @FXML
    void ctryComboBoxAction(ActionEvent event) {

        try{

            Ctry selectCtry = ctryComboBox.getSelectionModel().getSelectedItem();
            ObservableList<Div> dbDiv = DivDao.selectAll();
            ObservableList<Div> divByCtry = FXCollections.observableArrayList();

            for(Div div : dbDiv){

                if(div.getCtryID() == selectCtry.getCtryID()){

                    divByCtry.add(div);

                }

            }

            divComboBox.setItems(divByCtry);

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    /** Create New Customer Object and Insert Object into Datebase.
     *
     * @param event Save Button Action.
     * @throws IOException from FXMLLoader.
     * */
    @FXML
    void saveBttnAction(ActionEvent event) throws IOException{

        try{

            String custName = nameTxt.getText();
            String address = addressTxt.getText();
            String postalCode = postalTxt.getText();
            String phone = phoneTxt.getText();
            String createdBy = currentUser.getUserName();
            String lastUpdatedBy = currentUser.getUserName();
            int divID = divComboBox.getSelectionModel().getSelectedItem().getDivID();

            Cust newCust = new Cust(custName, address, postalCode, phone, createdBy, lastUpdatedBy, divID);

            if(!VerifyCust.fieldsEmpty(newCust)) {

                CustDao.create(newCust);

                Parent parent = FXMLLoader.load(getClass().getResource("/view/Custs.fxml"));
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

    /** Initialize Controller after Root Element has been processed.
     *
     * @param url URL Location used to resolve relative path for Root Object or {@code null} when URL Location is unknown.
     * @param resourceBundle Resource Bundle used to localize Root Object or {@code null} when Resource Bundle is unknown.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setDefaultValues();

    }

    private void setDefaultValues(){

        try{

            ctryList = CtryDao.selectAll();
            ctryComboBox.setItems(ctryList);

        }catch (SQLException e){

            System.out.println(e.getMessage());

        }

    }
}
