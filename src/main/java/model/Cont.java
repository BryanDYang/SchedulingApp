package model;

/** Model of Contact object class.
 *
 * @author Bryan Yang
 * */
public class Cont extends User {

    /** Contact Database ID. */
    private int contID;

    /** Contact Name. */
    private String contName;

    /** Contact Email Address. */
    private String contEmail;

    /** No Argument Constructor. */
    public Cont(){}

    /** Argument Constructor.
     *
     * @param contName Contact Name String.
     * @param contEmail Contact Email String.
     * */
    public Cont(int contID, String contName, String contEmail) {
        this.contID = contID;
        this.contName = contName;
        this.contEmail = contEmail;
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

    /** Getter for Contact Name.
     *
     * @return String
     * */
    public String getContName() {
        return contName;
    }

    /** Setter for Contact Name.
     *
     * @param contName Contact Name String.
     * */
    public void setContName(String contName) {
        this.contName = contName;
    }

    /** Getter for Contact Email.
     *
     * @return
     * */
    public String getContEmail() {
        return contEmail;
    }

    /** Setter for Contact Email.
     *
     * @param contEmail Contact Email String.
     *  */
    public void setContEmail(String contEmail) {
        this.contEmail = contEmail;
    }

    @Override
    public String toString(){
        return contName;
    }

}
