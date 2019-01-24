package em17.database.mysql;


import em17.database.DBSchema;
import em17.database.SoldTicketDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a DAO object used to access SoldTickets in the underlying database
 */
public class SoldTicketDAOMySQL extends SoldTicketDAO {

    /**
     * Default constructor
     */
    public SoldTicketDAOMySQL() {
    }

    /**
     * @param minInclusive 
     * @param maxExclusive 
     * @return the sold tickets amount whose holder's age is x, where minInclusive <= x < maxExclusive
     */
    public int getCustomersAmountForAgeRangeForAllEvents(int minInclusive, int maxExclusive) {
        int count;
        ResultSet resultSet;
        String tabellaEta = "(SELECT (DATEDIFF(now(),"+ DBSchema.CUSTOMER_BIRTH_DATE_COLUMN +") / 365.25) AS age"
                           +" FROM "+ DBSchema.CUSTOMER_TABLE +" INNER JOIN "+ DBSchema.SOLD_TICKET_TABLE 
                                +" ON "+DBSchema.CUSTOMER_TABLE +"."+ DBSchema.CUSTOMER_EMAIL_ADDRESS_COLUMN +"="+ DBSchema.SOLD_TICKET_TABLE +"."+ DBSchema.SOLD_TICKET_EMAIL_ADDRESS_COLUMN +")"
                           +" AS tabellaEta",
               statementAmount = "SELECT COUNT(*)"
                               +" FROM "+ tabellaEta 
                               +" WHERE tabellaEta.age >=\""+ minInclusive +"\" AND tabellaEta.age <\""+ maxExclusive +"\";";
        
        resultSet = DatabaseManagerMySQL.getInstance().query(statementAmount);
        try {  
            if (resultSet != null) {
                resultSet.first();
                count = resultSet.getInt(1);
                System.out.println("count ottenuto "+count);
                return count;
            }
            else {
                return 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SoldTicketDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    /**
     * @param minInclusive 
     * @param maxExclusive 
     * @return the sold tickets amount whose price is x, where minInclusive <= x < maxExclusive
     */
    public int getSoldTicketsAmountForPriceRangeForAllEvents(int minInclusive, int maxExclusive) {
        int count;
        ResultSet resultSet;
        String statementAmount = "SELECT COUNT(*)"
                               +" FROM "+ DBSchema.SOLD_TICKET_TABLE 
                               +" WHERE "+ DBSchema.SOLD_TICKET_PRICE_COLUMN +" >=\""+ minInclusive +"\" AND "+ DBSchema.SOLD_TICKET_PRICE_COLUMN +" <\""+ maxExclusive +"\";";
        
        resultSet = DatabaseManagerMySQL.getInstance().query(statementAmount);
        
        try {
            if (resultSet != null) {
                resultSet.first();
                count = resultSet.getInt(1);
                System.out.println("count ottenuto"+count);
                return count;
            }
            else {
                return 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SoldTicketDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    /**
     * @param eventType 
     * @return the sold tickets amount for the events whose type is the given one
     */
    public int getSoldTicketsAmountForEventType(String eventType) {
        int count;
        ResultSet resultSet;
        String statementAmount = "SELECT COUNT(*)"
                               +" FROM "+ DBSchema.SOLD_TICKET_TABLE +" INNER JOIN "+ DBSchema.EVENT_TABLE
                                    +" ON "+ DBSchema.SOLD_TICKET_TABLE +"."+ DBSchema.SOLD_TICKET_EVENT_ID_COLUMN +"="+ DBSchema.EVENT_TABLE +"."+ DBSchema.EVENT_ID_COLUMN
                               +" WHERE "+ DBSchema.EVENT_TYPE_COLUMN +" =\""+ eventType +"\";";
        
        resultSet = DatabaseManagerMySQL.getInstance().query(statementAmount);
        
        try {
            if (resultSet != null) {
                resultSet.first();
                count = resultSet.getInt(1);
                System.out.println("count ottenuto "+count);
                return count;
            }
            else {
                return 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SoldTicketDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    /**
     * @return the total sold tickets amount
     */
    public int getTotalSoldTicketsAmount() {
        int count;
        ResultSet resultSet;
        String statementAmount = "SELECT COUNT(*)"
                               +" FROM "+ DBSchema.SOLD_TICKET_TABLE;
        
        resultSet = DatabaseManagerMySQL.getInstance().query(statementAmount);
        
        try {
            if (resultSet != null) {
                resultSet.first();
                count = resultSet.getInt(1);
                System.out.println("counter ottenuto "+ count);
                return count;
            }
            else {
                return 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SoldTicketDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

}