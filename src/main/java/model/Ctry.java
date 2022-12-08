package model;

import java.time.LocalDateTime;

/** Model of Country object class.
 *
 * @author Bryan Yang
 * */
public class Ctry {

    /** Country Database ID. */
    private int ctryID;

    /** Country Name. */
    private String ctryName;

    /** Country Created Date. */
    private LocalDateTime createdDate;

    /** Country Created By. */
    private String createdBy;

    /** Last Updated Time of Country. */
    private LocalDateTime lastUpdatedTime;

    /** User Last Updated Country. */
    private String lastUpdatedBy;

    /** No Argument Constructor. */
    public Ctry(){}

    /** Argument Constructor.
     *
     * @param ctryID Country ID String.
     * @param ctryName Country Name String.
     * @param createdDate Country Created Date.
     * @param createdBy Country Created By (User).
     * */
    public Ctry(int ctryID, String ctryName, LocalDateTime createdDate, String createdBy, LocalDateTime lastUpdatedTime, String lastUpdatedBy) {
        this.ctryID = ctryID;
        this.ctryName = ctryName;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdatedTime = lastUpdatedTime;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Getter for Country Database ID.
     *
     * @return int
     */
    public int getCtryID() {
        return ctryID;
    }

    /** Setter for Country Database ID.
     *
     * @param ctryID Country Database ID.
     * */
    public void setCtryID(int ctryID) {
        this.ctryID = ctryID;
    }

    /** Getter for Country Name.
     *
     * @return String
     * */
    public String getCtryName() {
        return ctryName;
    }

    /** Setter for Country Name.
     *
     * @param ctryName Country Name.
     * */
    public void setCtryName(String ctryName) {
        this.ctryName = ctryName;
    }

    /** Getter for Country Created Date.
     *
     * @return LocalDateTime
     * */
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    /** Setter for Country Created Date.
     *
     * @param createdDate Country Created Date.
     * */
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    /** Getter for Country Created By.
     *
     * @return String
     * */
    public String getCreatedBy() {
        return createdBy;
    }

    /** Setter for Country Created By.
     *
     * @param createdBy User Created Country.
     * */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /** Getter for Last Updated Time.
     *
     * @return LocalDateTime
     * */
    public LocalDateTime getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    /** Setter for Last Updated Time.
     *
     * @param lastUpdatedTime Country Last Updated Time.
     * */
    public void setLastUpdatedTime(LocalDateTime lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    /** Getter for Last Updated By.
     *
     * @return String
     * */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /** Setter for Last Updated By.
     *
     * @param lastUpdatedBy Country Last Updated By.
     * */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /** toString() Override for Combo Display.
     *
     * @return Country Name
     * */
    @Override
    public String toString(){
        return ctryName;
    }

}
