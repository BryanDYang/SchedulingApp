package model;

import java.time.LocalDateTime;

/** Model of Division object class.
 *
 * @author Bryan Yang
 * */
public class Div {

    /** Division Database ID. */
    private int divID;

    /** Division Name. */
    private String divName;

    /** Division Created Date. */
    private LocalDateTime createdDate;

    /** Division Created BY (User). */
    private String createdBy;

    /** Division Last Updated Time. */
    private LocalDateTime lastUpdatedTime;

    /** Division Last Updated By (User). */
    private String lastUpdatedBy;

    /** Country Database ID. */
    private int ctryID;

    /** No Argument Constructor. */
    public Div(){}

    /** Argument Constructor.
     *
     * @param divID Division ID.
     * @param divName Division Name.
     * @param createdBy Division Created By (User).
     * @param lastUpdatedTime Division Last Updated Time.
     * @param lastUpdatedBy Division Last Updated By (User).
     * @param ctryID Country Database ID.
     *
     * */
    public Div(int divID, String divName, LocalDateTime createdDate, String createdBy, LocalDateTime lastUpdatedTime, String lastUpdatedBy, int ctryID) {
        this.divID = divID;
        this.divName = divName;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdatedTime = lastUpdatedTime;
        this.lastUpdatedBy = lastUpdatedBy;
        this.ctryID = ctryID;
    }

    /** Getter for Division Database ID.
     *
     * @return Division Database ID.
     * */
    public int getDivID() {
        return divID;
    }

    /** Setter for Division Database ID.
     *
     * @param divID Division Database ID.
     * */
    public void setDivID(int divID) {
        this.divID = divID;
    }

    /** Getter for Division Name.
     *
     * @return Division Name.
     * */
    public String getDivName() {
        return divName;
    }

    /** Setter for Division Name.
     *
     * @param divName Division Name.
     * */
    public void setDivName(String divName) {
        this.divName = divName;
    }

    /** Getter for Division Created Date.
     *
     * @return Division Created Date.
     * */
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    /** Setter for Division Created Date.
     *
     * @param createdDate Division Created Date.
     * */
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    /** Getter for Division Created By.
     *
     * @return Division Created By (User).
     * */
    public String getCreatedBy() {
        return createdBy;
    }

    /** Setter for Division Created By.
     *
     * @param createdBy Division Created By (User).
     * */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /** Getter for Division Last Updated Time.
     *
     * @return Division Last Updated Time.
     * */
    public LocalDateTime getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    /** Setter for Division Last Updated Time.
     *
     * @param lastUpdatedTime Division Last Updated Time.
     * */
    public void setLastUpdatedTime(LocalDateTime lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    /** Getter for Division Last Updated By (User).
     *
     * @return Division Last Updated By (User).
     * */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /** Setter for Division Last Updated By (User).
     *
     * @param lastUpdatedBy Division Last Updated By (User).
     * */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /** Getter for Country Database ID.
     *
     * @return Country Database ID.
     * */
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

    /** toString() override for combo display.
     *
     * @return Division Name.
     * */
    @Override
    public String toString(){
        return divName;
    }

}
