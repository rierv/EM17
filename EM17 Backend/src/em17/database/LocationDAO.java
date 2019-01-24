package em17.database;

import em17.entities.Location;
import em17.entities.Sector;
import em17.entities.Turnstile;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 
 */
public abstract class LocationDAO {

    /**
     * Default constructor
     */
    public LocationDAO() {
    }


    /**
     * @param partialLocationDescription 
     * @return
     */
    public abstract List<String> getNamesOfLocationsContaining(String partialLocationDescription);

    /**
     * @param description 
     * @return
     */
    public abstract Location getLocation(String description);

    /**
     * @param locationDescription 
     * @return
     */
    public abstract boolean exists(String locationDescription);

    /**
     * @param locationDescription 
     * @param eventId 
     * @return
     */
    public abstract boolean isLocationUsedByAnotherEvent(String locationDescription, String eventId);

    /**
     * @param locationDescription 
     * @return
     */
    public abstract List<Sector> getSectorsOfLocation(String locationDescription);

    /**
     * @param location 
     * @return
     */
    public abstract void add(Location location);

    /**
     * @param locationDescription 
     * @param name 
     * @param capacity 
     * @return
     */
    public abstract void addSectorToLocation(String locationDescription, String name, int capacity);

    /**
     * @param locationDescription 
     * @param sectorName 
     * @return
     */
    public abstract List<Turnstile> getTurnstilesOfSectorOfLocation(String locationDescription, String sectorName);

    /**
     * @param locationDescription 
     * @param oldSectorName 
     * @param newSectorName 
     * @param newCapacity 
     * @return
     */
    public abstract void modifySectorOfLocation(String locationDescription, String oldSectorName, String newSectorName, int newCapacity);

    /**
     * @param locationDescription 
     * @param sectorName 
     * @param turnstileName 
     * @return
     */
    public abstract void addTurnstileToSectorOfLocation(String locationDescription, String sectorName, String turnstileName);

    /**
     * @param locationDescription 
     * @param sectorName 
     * @param oldTurnstileName 
     * @param newTurnstileName 
     * @return
     */
    public abstract void renameTurnstileOfSectorOfLocation(String locationDescription, String sectorName, String oldTurnstileName, String newTurnstileName);

    /**
     * @param locationDescription 
     * @param sectorName 
     * @param turnstileName 
     * @return
     */
    public abstract void removeTurnstileOfSectorOfLocation(String locationDescription, String sectorName, String turnstileName);

    /**
     * @param locationDescription 
     * @param sectorName 
     * @return
     */
    public abstract void removeSectorOfLocation(String locationDescription, String sectorName);

    /**
     * @param locationDescription 
     * @param startDateTime 
     * @param endDateTime 
     * @return
     */
    public abstract boolean isLocationAvailable(String locationDescription, LocalDateTime startDateTime, LocalDateTime endDateTime);

}