package em17.database.mysql;


import em17.database.CustomerDAO;
import em17.database.DBSchema;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a DAO object used to access Customers in the underlying database
 */
public class CustomerDAOMySQL extends CustomerDAO {

    /**
     * Default constructor
     */
    public CustomerDAOMySQL() {
    }
    /**
     * @param eventId 
     * @return
     */
    public List<String> getEmailAddressesOfAllCustomersOfEvent(String eventId) {
        List<String> listOfCustomersEmails = new ArrayList();
        ResultSet resultSet;
        String statement = "SELECT "+ DBSchema.SOLD_TICKET_EMAIL_ADDRESS_COLUMN 
                         +" FROM "+ DBSchema.SOLD_TICKET_TABLE 
                         +" WHERE "+ DBSchema.SOLD_TICKET_EMAIL_ADDRESS_COLUMN +"=\""+ eventId +"\";";
        
        resultSet = DatabaseManagerMySQL.getInstance().query(statement);
        try {
            if (resultSet != null) {
                resultSet.first();
                //while is present a row
                while(resultSet.next()){
                    System.out.println("email "+resultSet.getString(1));
                    listOfCustomersEmails.add(resultSet.getString(1));
                }
                return listOfCustomersEmails;
            }
            else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listOfCustomersEmails;
    }

}