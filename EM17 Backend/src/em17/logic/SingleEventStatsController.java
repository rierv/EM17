package em17.logic;


import common.MathUtils;
import em17.database.DAOFactory;
import em17.database.EventDAO;
import java.util.*;
import javafx.event.ActionEvent;

/**
 * Manages the "view single event's stats" function
 */
public class SingleEventStatsController {
    
    /**
     * The DAO object used to access the sold tickets data
     */
    private final EventDAO eventDAO = DAOFactory.getInstance().getEventDAO();
    
    /**
     * The event of which the stats are being requested
     */
    private String eventId = null;
    
    /**
     * @param eventId
     */
    public SingleEventStatsController(String eventId) {
        this.eventId = eventId;
    }

    /**
     * @return a Map object with each entry containing an age range and the amount of customers whose age is within the specified range
     */
    public Map<String, Number> getCustomersByAgeData() {
        Map<String, Number> data = new TreeMap<>();
        String ageRanges[] = new String[] {"00", "18", "24", "30", "40", "50", "60", "70", "80", "99"};
        for (int i=0; i<ageRanges.length-1; i++) {
            int amount = eventDAO.getCustomersAmountForAgeRangeForEvent(eventId, Integer.parseInt(ageRanges[i]), Integer.parseInt(ageRanges[i+1]));
            data.put(ageRanges[i] + "-" + ageRanges[i+1], amount);
        }
        return data;
    }

    /**
     * @return a Map object with each entry containing a sector name and the amount of sold tickets for the event with id eventId in that sector
     */
    public Map<String, Number> getSoldTicketsBySectorData() {
        return eventDAO.getSoldTicketAmountForEachSectorOfEventLocation(eventId);
    }

    /**
     * @return a Map object with two entries, each containing a ticket type (full or reduced) and the relative sold tickets percentage
     */
    public Map<String, Number> getFullAndReducedTicketsPriceData() {
        Map<String, Number> data = new TreeMap<>();
        float fullTicketsAmount = eventDAO.getFullTicketsPercentageOfEvent(eventId);
        data.put("Full tickets", fullTicketsAmount);
        float reducedTicketsAmount = eventDAO.getReducedTicketsPercentageOfEvent(eventId);
        data.put("Reduced tickets", reducedTicketsAmount);
        return data;
    }

    /**
     * @return a Map object with each entry containing a time from the event creation and the amount of tickets sold before that time for that event
     */
    public Map<String, Number> getSoldTicketsOverTimeData() {
        Map<String, Number> data = new TreeMap<>();
        long millisSinceCreation = eventDAO.getMillisSinceEventCreation(eventId);
        int INTERVALS = 20;
        for (int i=0; i<INTERVALS; i++) {
            int amount = eventDAO.getAmountOfTicketsOfEventSoldBefore(eventId, millisSinceCreation/INTERVALS*i);
            String label = MathUtils.toRoundedString(((float)(i))/INTERVALS);
            data.put(label, amount);
        }
        return data;
    }
    
    /**
     * Shows the main screen (OuterView with EventManagementScreen)
     * @param event
     */
    public void showMainScreen(ActionEvent event) {
        EventManagementController.getInstance().showMainScreen(event);
    }

}