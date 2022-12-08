package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** Controller Class providing Control Logic for Reports Display.
 *
 * @author Bryan Yang
 * */
public class ReportsController implements Initializable {

    /** Appointment based on Location Report Radio Button. */
    @FXML
    private RadioButton locRBttn;

    /** Appointment based on Month Report Radio Button. */
    @FXML
    private RadioButton moRBttn;

    /** Radio Button Toggle Group. */
    @FXML
    private ToggleGroup radioButtons;

    /** Contact Schedule Report Radio Button. */
    @FXML
    private RadioButton schedRadioButton;

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

    /** Load Controller based on Selected Radio Button.
     *
     * @param event Generate Button Action.
     * @throws IOException from FXMLLoader.
     * */
    @FXML
    void genBttnAction(ActionEvent event) throws IOException{

        if(moRBttn.isSelected()) {

            Parent parent = FXMLLoader.load(getClass().getResource("/view/MoTypReport.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        }else if(schedRadioButton.isSelected()) {

            Parent parent = FXMLLoader.load(getClass().getResource("/view/SchedReport.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        }else if(locRBttn.isSelected()){

            Parent parent = FXMLLoader.load(getClass().getResource("/view/LocReport.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        }

    }

    /** Initialize Controller after Root Element has been processed.
     *
     * @param url URL Location used to resolve relative paths for Root Object or {@code null} when URL Location is unknown.
     * @param resourceBundle Resource Bundle used to Localize Root Object or {@code null} when Resource Bundle is unknown.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
