package em17.database;

import em17.entities.Event;
import em17.entities.EventTypeEnum.EventType;
import em17.entities.SectorPrices;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 
 */
public abstract class EventDAO {

    /**
     * Default constructor
     */
    public EventDAO() {
    }

    /**
     * 
     */
    private int size;


    /**
     * @param event 
     * @return
     */
    public abstract void add(Event event);

    /**
     * @param eventId 
     * @return
     */
    public abstract String getLocationOfEventDescription(String eventId);

    /**
     * @param eventId 
     * @param sectorName 
     * @param price 
     * @return
     */
    public abstract void setTicketFullPriceForSectorForEvent(String eventId, String sectorName, float price);

    /**
     * @param eventId 
     * @param sectorName 
     * @param price 
     * @return
     */
    public abstract void setTicketReducedPriceForSectorForEvent(String eventId, String sectorName, float price);

    /**
     * @param eventId 
     * @return
     */
    public abstract boolean isThereASoldTicketForEvent(String eventId);

    /**
     * @param eventId 
     * @param startDateTime 
     * @param endDateTime 
     * @param eventType 
     * @return
     */
    public abstract void setEventProperties(String eventId, LocalDateTime startDateTime, LocalDateTime endDateTime, EventType eventType);

    /**
     * @param eventId 
     * @return
     */
    public abstract void deleteEventById(String eventId);

    /**
     * @param eventId 
     * @param ageMinInclusive 
     * @param ageMaxExclusive 
     * @return
     */
    public abstract int getCustomersAmountForAgeRangeForEvent(String eventId, int ageMinInclusive, int ageMaxExclusive);

    /**
     * @param eventId 
     * @return
     */
    public abstract Map<String, Number
        > getSoldTicketAmountForEachSectorOfEventLocation(String eventId);

    /**
     * @param eventId 
     * @return
     */
    public abstract float getFullTicketsPercentageOfEvent(String eventId);

    /**
     * @param eventId 
     * @return
     */
    public abstract float getReducedTicketsPercentageOfEvent(String eventId);

    /**
     * @param eventId 
     * @return
     */
    public abstract long getMillisSinceEventCreation(String eventId);

    /**
     * @param eventId 
     * @param millis 
     * @return
     */
    public abstract int getAmountOfTicketsOfEventSoldBefore(String eventId, long millis);

    /**
     * @param eventId 
     * @return
     */
    public abstract int getSoldTicketsAmountForEvent(String eventId);

    /**
     * @param eventId 
     * @return
     */
    public abstract List<SectorPrices> getPricesForEvent(String eventId);

    /**
     * @param eventId 
     * @return
     */
    public abstract Event getEvent(String eventId);

    /**
     * @return
     */
    public abstract List<Event> getAllEvents();

}