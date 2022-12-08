package model;

import java.time.LocalDateTime;

/** Model of Customer object class.
 *
 * @author Bryan Yang
 * */
public class Cust {

    /** Customer Database ID. */
    protected int custID;

    /** Customer Name. */
    protected String custName;

    /** Customer Address. */
    protected String address;

    /** Customer Postal Code. */
    protected String postalCode;

    /** Customer Phone Numbers. */
    protected String phone;

    /** Customer Created Date. */
    protected LocalDateTime createdDate;

    /** Customer Created By (User). */
    protected String createdBy;

    /** Customer Information Last Updated Time. */
    protected LocalDateTime lastUpdatedTime;

    /** Customer Information Last Updated By. */
    protected String lastUpdatedBy;

    /** Division Database ID. */
    protected int divID;

    /** No Argument Constructor. */
    public Cust(){

    }

    /** Argument Constructor.
     *
     * @param custID Customer ID.
     * @param custName Customer Name.
     * @param address Customer Address.
     * @param postalCode Customer Postal Code.
     * @param phone Customer Phone Numbers.
     * @param createdDate Customer Created Date.
     * @param createdBy Customer Information Created By.
     * @param lastUpdatedTime Customer Information Last Updated Time.
     * @param lastUpdatedBy Customer Information Last Updated By.
     * @param divID Division Database ID.
     *
     * */
    public Cust(String custName, String address, String postalCode, String phone, String createdBy, String lastUpdatedBy, int divID) {
        this.custName = custName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createdBy = createdBy;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divID = divID;
    }

    /** Getter for Customer Database ID.
     *
     * @return int
     * */
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

    /** Getter for Customer Name.
     *
     * @return String
     * */
    public String getCustName() {
        return custName;
    }

    /** Setter for Customer Name.
     *
     * @param custName Customer Name.
     * */
    public void setCustName(String custName) {
        this.custName = custName;
    }

    /** Getter for Customer Address.
     *
     * @return String
     * */
    public String getAddress() {
        return address;
    }

    /** Setter for Customer Address.
     *
     * @param address Customer Address String.
     * */
    public void setAddress(String address) {
        this.address = address;
    }

    /** Getter for Customer Postal Code.
     *
     * @return Postal Code String.
     * */
    public String getPostalCode() {
        return postalCode;
    }

    /** Setter for Customer Postal Code.
     *
     * @param postalCode Postal Code String.
     * */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /** Getter for Customer Phone Numbers.
     *
     * @return Phone Number String.
     * */
    public String getPhone() {
        return phone;
    }

    /** Setter for Customer Phone Numbers.
     *
     * @param phone Phone Numbers String.
     * */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /** Getter for Customer Created Date.
     *
     * @return Create Data LocalDateTime.
     * */
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    /** Setter for Customer Created Date.
     *
     * @param createdDate Created Date LocalDateTime.
     * */
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    /** Getter for Customer Created By.
     *
     * @return User Created Customer.
     * */
    public String getCreatedBy() {
        return createdBy;
    }

    /** Setter for Customer Created By.
     *
     * @param createdBy User Created Customer.
     * */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /** Getter for Customer Last Updated Time.
     *
     * @return Last Updated Time LocalDateTime.
     * */
    public LocalDateTime getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    /** Setter for Customer Last Updated Time.
     *
     * @param lastUpdatedTime Last Updated LocalDateTime.
     * */
    public void setLastUpdatedTime(LocalDateTime lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    /** Getter for Customer Last Updated By.
     *
     * @return User Last Updated Customer.
     * */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /** Setter for Customer Last Updated By.
     *
     * @param lastUpdatedBy User Last Updated Customer.
     * */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /** Getter for Division Database ID.
     *
     * @return Division Database ID int.
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

    /** toString() Override for Combo Display
     *
     * @return Customer Name.
     * */
    @Override
    public String toString(){
        return custName;
    }

}
