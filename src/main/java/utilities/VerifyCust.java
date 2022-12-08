package utilities;

import model.Appt;
import model.Cust;
import javafx.scene.control.Alert;

/** Utility class validating Customer.
 *
 * @author Bryan Yang
 * */
public class VerifyCust {

    /** Validate Empty Fields in Customer.
     *
     * @param cust Customer to be checked.
     * @return Boolean.
     * */
    public static boolean fieldsEmpty(Cust cust){

        boolean fieldsEmpty = false;

        if(cust.getCustName().equals("")){
            fieldsEmpty = true;
        }
        else if(cust.getAddress().equals("")){
            fieldsEmpty = true;
        }
        else if(cust.getPostalCode().equals("")){
            fieldsEmpty = true;
        }
        else if(cust.getPhone().equals("")){
            fieldsEmpty = true;
        }

        if(fieldsEmpty){

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Error");
            alert.setHeaderText("Text Field(s) Empty.");
            alert.showAndWait();

        }

        return  fieldsEmpty;

    }

}
