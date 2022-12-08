package model;

import dao.DivDao;
import java.sql.SQLException;

/** Model of Display Customer wrapper object class in the customer tableview.
 *
 * @author Bryan Yang
 * */
public class CustDisplay extends  Cust{

    /** Division Name. */
    String divName;

    /** Argument Constructor.
     *
     * @param cust Customer object to be wrapped.
     * @throws SQLException from DivDao.selectByID().
     *
     * */
    public CustDisplay(Cust cust) throws SQLException{

        this.custID = cust.custID;
        this.custName = cust.custName;
        this.address = cust.address;
        this.postalCode = cust.postalCode;
        this.phone = cust.phone;
        this.createdDate = cust.createdDate;
        this.createdBy = cust.createdBy;
        this.lastUpdatedTime = cust.lastUpdatedTime;
        this.lastUpdatedBy = cust.lastUpdatedBy;
        this.divID = cust.divID;

        try{

            this.divName = DivDao.selectByID(cust.divID).getDivName();

        }catch (SQLException e){

            System.out.println(e.getMessage());

        }

    }

    /** Getter for Division Name.
     *
     * @return Division Name String
     * */
    public String getDivName() {
        return divName;
    }

    /** Setter for the Division Name.
     *
     * @param divName Division Name String
     * */
    public void setDivName(String divName) {
        this.divName = divName;
    }
}
