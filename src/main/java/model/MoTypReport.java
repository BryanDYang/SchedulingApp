package model;

import java.time.Month;

/** Model infrastructure of Month and Type Report object class of the report tableview.
 *
 * @author Bryan Yang
 * */
public class MoTypReport {

    /** Appointment Year. */
    private int year;

    /** Appointment Month. */
    private Month month;

    /** Appointment Type. */
    private String type;

    /** Appointment Total with same Year, Month and Type. */
    private int total;

    /** No Argument Constructor. */
    public MoTypReport(){}

    /** Argument Constructor.
     *
     * @param yr Appointment Year.
     * @param mo Appointment Month.
     * @param typ Appointment Type.
     * @param total Appointment Total with same Year, Month and Type.
     *
     * */
    public MoTypReport(int year, Month month, String type, int total) {
        this.year = year;
        this.month = month;
        this.type = type;
        this.total = total;
    }

    /** Getter for Appointment Year.
     *
     * @return Appointment Year.
     * */
    public int getYear() {
        return year;
    }

    /** Setter for Appointment Year.
     *
     * @param yr Appointment Year.
     * */
    public void setYear(int year) {
        this.year = year;
    }

    /** Getter for Appointment Month.
     *
     * @return Appointment Month.
     * */
    public Month getMonth() {
        return month;
    }

    /** Setter for Appointment Month.
     *
     * @param mo Appointment Month.
     * */
    public void setMonth(Month month) {
        this.month = month;
    }

    /** Getter for Appointment Type.
     *
     * @return Appointment Type.
     * */
    public String getType() {
        return type;
    }

    /** Setter for Appointment Type.
     *
     * @param typ Appointment Type.
     * */
    public void setType(String type) {
        this.type = type;
    }

    /** Getter for Appointment Total with same Year, Month and Type.
     *
     * @return Appointment Total with same Year, Month and Type.
     * */
    public int getTotal() {
        return total;
    }

    /** Setter for Appointment Total with same Year, Month and Type.
     *
     * @param total Appointment Total with same Year, Month and Type.
     * */
    public void setTotal(int total) {
        this.total = total;
    }


}
