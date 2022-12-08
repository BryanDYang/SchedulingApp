package model;

import java.time.LocalDateTime;

/** Model of Appointment object class.
 *
 * @author Bryan Yang
 * */
public class Appt {

    /** Appointment Database ID. */
    protected int apptID;

    /** Appointment title. */
    protected String apptTitle;

    /** Appointment description. */
    protected String apptDescr;

    /** Appointment location. */
    protected String apptLoc;

    /** Appointment type. */
    protected String apptTyp;

    /** Appointment start time. */
    protected LocalDateTime startTime;

    /** Appointment end time. */
    protected LocalDateTime endTime;

    /** Appointment created date. */
    protected LocalDateTime createdDate;

    /** User who created appointment. */
    protected String createdBy;

    /** Last updated time of appointment. */
    protected LocalDateTime lastUpdatedTime;

    /** Last user updated appointment. */
    protected String lastUpdatedBy;

    /** Customer Database ID. */
    protected int custID;

    /** User Database ID. */
    protected int userID;

    /** Contact Database ID. */
    protected int contID;

    /** No Argument Constructor. */
    public Appt(){}

    /** Argument Constructor.
     *
     * @param apptID Appointment Database ID.
     * @param apptTitle Appointment Title.
     * @param apptDescr Appointment Description.
     * @param apptLoc Appointment Location.
     * @param apptTyp Appointment Type.
     * @param startTime Appointment Start Time.
     * @param endTime Appointment End Time.
     * @param createdDate Appointment Created Date.
     * @param createdBy User Created Appointment.
     * @param lastUpdatedTime Appointment Last Updated Time.
     * @param lastUpdatedBy User Last Updated Appointment.
     * @param custID Customer Database ID.
     * @param userID User Database ID.
     * @param contID Contact Database ID.
     *
     * */
    public Appt(String apptTitle, String apptDescr, String apptLoc, String apptTyp, LocalDateTime startTime, LocalDateTime endTime, String createdBy, String lastUpdatedBy, int custID, int userID, int contID) {
        this.apptTitle = apptTitle;
        this.apptDescr = apptDescr;
        this.apptLoc = apptLoc;
        this.apptTyp = apptTyp;
        this.startTime = startTime;
        this.endTime = endTime;
        this.createdBy = createdBy;
        this.lastUpdatedBy = lastUpdatedBy;
        this.custID = custID;
        this.userID = userID;
        this.contID = contID;
    }

    /** Getter for Appointment Database ID.
     *
     * @return int
     * */
    public int getApptID() {
        return apptID;
    }

    /** Setter for Appointment Database ID.
     *
     * @param apptID Appointment Database ID.
     * */
    public void setApptID(int apptID) {
        this.apptID = apptID;
    }

    /** Getter for Appointment Title.
     *
     * @return String
     * */
    public String getApptTitle() {
        return apptTitle;
    }

    /** Setter for Appointment Title.
     *
     * @param apptTitle Appointment title string.
     * */
    public void setApptTitle(String apptTitle) {
        this.apptTitle = apptTitle;
    }

    /** Getter for Appointment Description.
     *
     * @return String
     * */
    public String getApptDescr() {
        return apptDescr;
    }

    /** Setter for Appointment Description.
     *
     * @param apptDescr Appointment Description String.
     * */
    public void setApptDescr(String apptDescr) {
        this.apptDescr = apptDescr;
    }

    /** Getter for Appointment Location.
     *
     * @return String
     * */
    public String getApptLoc() {
        return apptLoc;
    }

    /** Setter for Appointment Location.
     *
     * @param apptLoc Appointment Location String.
     * */
    public void setApptLoc(String apptLoc) {
        this.apptLoc = apptLoc;
    }

    /** Getter for Appointment Type.
     *
     * @return String
     * */
    public String getApptTyp() {
        return apptTyp;
    }

    /** Setter for Appointment Type.
     *
     * @param apptTyp Appointment Type String.
     * */
    public void setApptTyp(String apptTyp) {
        this.apptTyp = apptTyp;
    }

    /** Getter for Appointment Start Time.
     *
     * @return LocalDateTime
     * */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /** Setter for Appointment End Time.
     *
     * @param startTime Appointment Start LocalDateTime.
     * */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /** Getter for Appointment End Time.
     *
     * @return LocalDateTime
     * */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /** Setter for Appointment End Time.
     *
     * @param endTime Appointment End LocalDateTime.
     * */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /** Getter for Appointment Created Date.
     *
     * @return LocalDateTime
     * */
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    /** Setter for Appointment Created Date.
     *
     * @param createdDate Appointment Creation LocalDateTime.
     * */
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    /** Getter for Appointment Created By.
     *
     * @return String
     * */
    public String getCreatedBy() {
        return createdBy;
    }

    /** Setter for Appointment Created By.
     *
     * @param createdBy Appointment Created By String.
     * */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /** Getter for Appointment Last Updated Time.
     *
     * @return LocalDateTime
     * */
    public LocalDateTime getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    /** Setter for Appointment Last Updated Time.
     *
     * @param lastUpdatedTime Appointment Last Updated LocalDateTime.
     * */
    public void setLastUpdatedTime(LocalDateTime lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    /** Getter for Appointment Last Updated By.
     *
     * @return String
     * */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /** Setter for Appointment Last Updated By.
     *
     * @param lastUpdatedBy Appiontment Updated By String.
     * */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Getter for Customer Database ID.
     *
     * @return int
     */
    public int getCustID() {
        return custID;
    }

    /** Setter for Customer Database ID.
     *
     * @param custID Customer Database ID.
     * */
    public void setCustID(int custID) {
        this.custID = custID;
    }

    /** Getter for User Database ID.
     *
     * @return int
     * */
    public int getUserID() {
        return userID;
    }

    /** Setter for User Database ID.
     *
     * @param userID User Database ID.
     * */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /** Getter for Contact Database ID.
     *
     * @return int
     * */
    public int getContID() {
        return contID;
    }

    /** Setter for Contact Database ID.
     *
     * @param contID Contact Database ID.
     * */
    public void setContID(int contID) {
        this.contID = contID;
    }

}
