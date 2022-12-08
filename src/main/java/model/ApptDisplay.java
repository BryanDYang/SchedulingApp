package model;

import dao.ContDao;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

/** Model of Display Appointment wrapper class extends Appointment Class in the Appointments Tableview.
 *
 * @author Bryan Yang
 * */
public class ApptDisplay extends Appt{

    /** Contact Name Associated w/ Appointment. */
    private String contName;

    /** Appointment Start Time as a String. */
    private String startTimeString;

    /** Appointment End Time as a String. */
    private String endTimeString;

    /** Argument Based Contructor.
     *
     * @param appt Appointment to be Wrapped.
     * @throws SQLException from ContDao.selectBYID().
     *
     * */
    public ApptDisplay(Appt appt) throws SQLException{

        super();
        this.apptID = appt.apptID;
        this.apptTitle = appt.apptTitle;
        this.apptDescr = appt.apptDescr;
        this.apptLoc = appt.apptLoc;
        this.apptTyp = appt.apptTyp;
        this.startTime = appt.startTime;
        this.endTime = appt.endTime;
        this.createdDate = appt.createdDate;
        this.createdBy = appt.createdBy;
        this.lastUpdatedTime = appt.lastUpdatedTime;
        this.lastUpdatedBy = appt.lastUpdatedBy;
        this.custID = appt.custID;
        this.userID = appt.userID;
        this.contID = appt.contID;

        try{

            this.contName = ContDao.selectByID(appt.getContID()).getContName();

        }catch(SQLException e){

            System.out.println(e.getMessage());

        }

        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");

        this.startTimeString = appt.getStartTime().format(dateTimeFormat);
        this.endTimeString = appt.getEndTime().format(dateTimeFormat);

    }

    /** Getter for Contact Name.
     *
     * @return String
     * */
    public String getContName() {
        return contName;
    }

    /** Setter for Contact Name.
     *
     * @param contName Contact Name String
     * */
    public void setContName(String contName) {
        this.contName = contName;
    }

    /** Getter for Appointment Start Time String.
     *
     * @return String
     * */
    public String getStartTimeString() {
        return startTimeString;
    }

    /** Setter for Appointment Start Time String.
     *
     * @param startTimeString Start Time String
     * */
    public void setStartTimeString(String startTimeString) {
        this.startTimeString = startTimeString;
    }

    /** Getter for Appointment End Time String.
     *
     * @return String
     * */
    public String getEndTimeString() {
        return endTimeString;
    }

    /** Setter for Appointment End Time String.
     *
     * @param endTimeString End Time String
     * */
    public void setEndTimeString(String endTimeString) {
        this.endTimeString = endTimeString;
    }

}
