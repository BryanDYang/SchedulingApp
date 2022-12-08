package model;

import java.time.LocalDateTime;

/** Model of Appointment User object class.
 *
 * @author Bryan Yang
 * */
public class User {

    /** User Database ID. */
    private int userID;

    /** User Name. */
    private String userName;

    /** User Password. */
    private String passwd;

    /** User Created Date. */
    private LocalDateTime createdDate;

    /** User Created By. */
    private String createdBy;

    /** User Last Updated Time. */
    private LocalDateTime lastUpdatedTime;

    /** User Last Updated By. */
    private String lastUpdatedBy;

    /** No Argument Constructor. */
    public User(){}

    /** With Argument Constructor.
     *
     * @param userID User ID.
     * @param userName User Name.
     * @param passwd User Password.
     * @param createdDate User Created Date.
     * @param createdBy User Created By.
     * @param lastUpdatedTime User Last Updated Time.
     * @param lastUpdatedBy User Last Updated By.
     *
     * */
    public User(int userID, String userName, String passwd, LocalDateTime createdDate, String createdBy, LocalDateTime lastUpdatedTime, String lastUpdatedBy) {
        this.userID = userID;
        this.userName = userName;
        this.passwd = passwd;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdatedTime = lastUpdatedTime;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /** Getter for User Database ID.
     *
     * @return User Database ID.
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

    /** Getter for User Name.
     *
     * @return User Name.
     * */
    public String getUserName() {
        return userName;
    }

    /** Setter for User Name.
     *
     * @param userName User Name.
     * */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** Getter for User Password.
     *
     * @return User Password.
     * */
    public String getPasswd() {
        return passwd;
    }

    /** Setter for User Password.
     *
     * @param passwd User Password.
     * */
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    /** Getter for User Created Date.
     *
     * @return User Created Date.
     * */
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    /** Setter for User Created Date.
     *
     * @param createdDate User Created Date.
     * */
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    /** Getter for User Created By.
     *
     * @return User Created By (User).
     * */
    public String getCreatedBy() {
        return createdBy;
    }

    /** Setter for User Created By.
     *
     * @param createdBy User Created By.
     * */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /** Getter for User Last Updated Time.
     *
     * @return User Last Updated Time.
     * */
    public LocalDateTime getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    /** Setter for User Last Updated Time.
     *
     * @param lastUpdatedTime User Last Updated Time.
     * */
    public void setLastUpdatedTime(LocalDateTime lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    /** Getter for User Last Updated By.
     *
     * @return User Last Updated By.
     * */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /** Setter for User Last Updated By.
     *
     * @param lastUpdatedBy User Last Updated By.
     * */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /** toString Override for Combo Display.
     *
     * @return User Name.
     * */
    @Override
    public String toString(){
        return userName;
    }

}
