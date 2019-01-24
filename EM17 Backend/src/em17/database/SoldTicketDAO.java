package em17.database;

import java.util.*;

/**
 * 
 */
public abstract class SoldTicketDAO {

    /**
     * Default constructor
     */
    public SoldTicketDAO() {
    }


    /**
     * @param minInclusive 
     * @param maxExclusive 
     * @return
     */
    public abstract int getCustomersAmountForAgeRangeForAllEvents(int minInclusive, int maxExclusive);

    /**
     * @param minInclusive 
     * @param maxExclusive 
     * @return
     */
    public abstract int getSoldTicketsAmountForPriceRangeForAllEvents(int minInclusive, int maxExclusive);

    /**
     * @param eventType 
     * @return
     */
    public abstract int getSoldTicketsAmountForEventType(String eventType);

    /**
     * @return
     */
    public abstract int getTotalSoldTicketsAmount();

}