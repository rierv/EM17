package em17.logic;

import em17.database.DAOFactory;
import em17.database.SoldTicketDAO;
import em17.entities.EventTypeEnum;
import em17.gui.OuterViewAdapter;
import java.util.*;

/**
 * The controller for the "view all events stats" function
 */
public class AllEventsStatsController {
    
    /**
     * Default constructor
     */
    public AllEventsStatsController() {
        
    }
    
    /**
     * The DAO object used to access the sold tickets data
     */
    private final SoldTicketDAO soldTicketDAO = DAOFactory.getInstance().getSoldTicketDAO();

    /**
     * @return a Map object with each entry containing an age range and the amount of customers whose age is within the specified range
     */
    public Map<String, Number> getCustomersByAgeData() {
        Map<String, Number> data = new TreeMap<>();
        String ageRanges[] = new String[] {"00", "18", "24", "30", "40", "50", "60", "70", "80", "99"};
        for (int i=0; i<ageRanges.length-1; i++) {
            int amount = soldTicketDAO.getCustomersAmountForAgeRangeForAllEvents(Integer.parseInt(ageRanges[i]), Integer.parseInt(ageRanges[i+1]));
            data.put(ageRanges[i] + "-" + ageRanges[i+1], amount);
        }
        return data;
    }

    /**
     * @return a Map object with each entry containing a price range and the amount of sold tickets whose price is within the specified range
     */
    public Map<String, Number> getSoldTicketsByPriceData() {
        Map<String, Number> data = new TreeMap<>();
        String priceRanges[] = new String[] {"000", "010", "025", "050", "075", "100", "150", "200", "300", "500", "999"};
        for (int i=0; i<priceRanges.length-1; i++) {
            int amount = soldTicketDAO.getSoldTicketsAmountForPriceRangeForAllEvents(Integer.parseInt(priceRanges[i]), Integer.parseInt(priceRanges[i+1]));
            data.put(priceRanges[i] + "-" + priceRanges[i+1], amount);
        }
        return data;
    }

    /**
     * @return a Map object with each entry containing a event type and the amount of sold tickets for events whose type is the specified one
     */
    public Map<String, Number> getSoldTicketsByEventTypeData() {
        Map<String, Number> data = new TreeMap<>();
        EventTypeEnum.values().forEach((eventType) -> {
            int amount = soldTicketDAO.getSoldTicketsAmountForEventType(eventType);
            data.put(eventType, amount);
        });
        return data;
    }

    /**
     * @return the total sold tickets amount
     */
    public int getTotalSoldTickets() {
        return soldTicketDAO.getTotalSoldTicketsAmount();
    }

    public void sendIsReadySignal() {
        OuterViewAdapter.receiveAllStatsReadySignal();
    }

}