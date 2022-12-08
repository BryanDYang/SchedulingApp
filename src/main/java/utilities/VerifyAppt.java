package utilities;

import dao.ApptDao;
import model.Appt;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;

/** Utility Class Validating Appointment.
 *
 * @author Bryan Yang
 * */
public class VerifyAppt {

    /** Date Time Format. */
    private static final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");

    /** Validate Empty Text Fields for Appointment.
     *
     * @param appt Appointment to be checked.
     * @return Boolean.
     * */
    public static boolean fieldsEmpty(Appt appt){

        boolean fieldsEmpty = false;

        if(appt.getApptTitle().equals("")){
            fieldsEmpty = true;
        }
        else if(appt.getApptDescr().equals("")){
            fieldsEmpty = true;
        }
        else if(appt.getApptLoc().equals("")){
            fieldsEmpty = true;
        }
        else if(appt.getApptTyp().equals("")){
            fieldsEmpty = true;
        }

        if(fieldsEmpty){

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Error");
            alert.setHeaderText("Text field(s) are empty.");
            alert.showAndWait();

        }

        return fieldsEmpty;

    }

    /** Validate Appointment Start Time is After End Time.
     *
     * @param appt Appointment to be checked.
     * @return Boolean.
     * */
    public static boolean startAfterEnd(Appt appt){

        boolean startAfterEnd = false;
        LocalDateTime startTime = appt.getStartTime();
        LocalDateTime endTime = appt.getEndTime();

        if(startTime.isAfter(endTime)){

            startAfterEnd = true;

        }

        if(startAfterEnd){

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Error");
            alert.setHeaderText("Start Time should occur BEFORE End Time.");
            alert.showAndWait();

        }

        return startAfterEnd;

    }

    /** Validate Appointment is Outside Business Hours.
     *
     * @param appt Appointment to be checked.
     * @return Boolean.
     * */
    public static boolean outsideBusinessHours(Appt appt){

        //Rezone Appointment Start Time Zone to Eastern Time.
        LocalDateTime startDateTime = appt.getStartTime();
        LocalDate startDate = startDateTime.toLocalDate();
        ZonedDateTime startTimeZoned = startDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime startTimeAsEST = startTimeZoned.withZoneSameInstant(ZoneId.of("America/New_York"));

        //Rezone Business Start Time Zone to Eastern Time.
        LocalTime businessStartTime = LocalTime.parse("08:00:00");
        LocalDateTime businessStartDateTime = LocalDateTime.of(startDate, businessStartTime);
        ZonedDateTime businessStartTimeZoned = businessStartDateTime.atZone(ZoneId.of("America/New_York"));

        //Rezone Appointment End Time Zone to Eastern Time.
        LocalDateTime endDateTime = appt.getEndTime();
        ZonedDateTime endTimeZoned = endDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime endTimeAsEST = endTimeZoned.withZoneSameInstant(ZoneId.of("America/New_York"));

        //Rezone Business End Time Zone to Eastern Time.
        LocalTime businessEndTime = LocalTime.parse("22:00:00");
        LocalDateTime businessEndDateTime = LocalDateTime.of(startDate, businessEndTime);
        ZonedDateTime businessEndTimeZoned = businessEndDateTime.atZone(ZoneId.of("America/New_York"));

        boolean outsideBusinessHours = false;

        if(startTimeAsEST.isBefore(businessStartTimeZoned) || endTimeAsEST.isAfter(businessEndTimeZoned)){

            outsideBusinessHours = true;

        }

        if(outsideBusinessHours){

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("ERROR");
            alert.setHeaderText("Subject Appointment is outside of business hours.\n" + "Business hours: 8:00 - 22:00 EST.");
            alert.showAndWait();

        }

        return outsideBusinessHours;

    }

    /** Validate Overlapping Appointments.
     *
     * @param newAppt Appointment to be checked.
     * @return Boolean.
     * @throws SQLException from ApptDao.selectAll().
     * */
    public static boolean overlappingAppt(Appt newAppt) throws SQLException{

        ObservableList<Appt> allAppts = ApptDao.selectAll();

        boolean overlapping = false;

        Appt overlappingAppt = new Appt();

        for(Appt appt : allAppts) {

            /*
            Don't check New Appointment against itself.
            Needed during modified appointment check.
            */
            if (appt.getApptID() == newAppt.getApptID()) {
                continue;
            }

            int newApptCustID = newAppt.getCustID();
            LocalDateTime newApptStart = newAppt.getStartTime();
            LocalDateTime newApptEnd = newAppt.getEndTime();

            int apptCustID = appt.getContID();
            LocalDateTime apptStart = appt.getStartTime();
            LocalDateTime apptEnd = appt.getEndTime();

            overlappingAppt = appt;

            // If New Appointment Starts in the middle of other appointment.
            if ((newApptCustID == apptCustID) && newApptStart.isAfter(apptStart) && newApptStart.isBefore(apptEnd)) {
                overlapping = true;
                break;
            }
            // If New Appointment Ends in the middle of other appointment.
            else if ((newApptCustID == apptCustID) && newApptEnd.isAfter(apptStart) && newApptEnd.isBefore(apptEnd)) {
                overlapping = true;
                break;
            }
            // If New Appointment Spans other Appointment.
            else if ((newApptCustID == apptCustID) && newApptStart.isBefore(apptStart) && newApptEnd.isAfter(apptEnd)) {
                overlapping = true;
                break;
            }
            //If New Appointment Starts at the same time and ends after other appointment.
            else if ((newApptCustID == apptCustID) && newApptStart.isEqual(apptStart) && newApptEnd.isAfter(apptEnd)) {
                overlapping = true;
                break;
            } else if ((newApptCustID == apptCustID) && newApptStart.isBefore(apptStart) && newApptEnd.isEqual(apptEnd)) {
                overlapping = true;
                break;
            } else if ((newApptCustID == apptCustID) && newApptStart.isEqual(apptStart) && newApptEnd.isEqual(apptEnd)) {
                overlapping = true;
                break;
            }
        }

        return overlapping;

    }

}
