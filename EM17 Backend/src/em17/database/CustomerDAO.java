package em17.database;

import java.util.*;

/**
 * 
 */
public abstract class CustomerDAO {

    /**
     * Default constructor
     */
    public CustomerDAO() {
    }



    /**
     * @param eventId 
     * @return
     */
    public abstract List<String> getEmailAddressesOfAllCustomersOfEvent(String eventId);

}