package em17.entities;

import em17.entities.EventTypeEnum.EventType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * Represents a single Event
 */
public class Event {
    /**
     * The event unique identifier
     */
    private final String id;

    /**
     * The event name
     */
    private final String name;

    /**
     * The event description
     */
    private final String description;
    
    /**
     * The event's location description
     */
    private final String locationDescription;

    /**
     * The event's start date
     */
    private final LocalDate startDate;
    
    /**
     * The event's start time
     */
    private final LocalTime startTime;

    /**
     * The event's end date
     */
    private final LocalDate endDate;

    /**
     * The event's end time
     */
    private final LocalTime endTime;

    /**
     * The event's type
     */
    private final EventType eventType;

    /**
     * The event's creation date and time
     */
    private final long creationTime;
    
    /**
     * Creates a new event with a specified creation time
     * @param id
     * @param name 
     * @param description 
     * @param locationDescription 
     * @param startDateTime 
     * @param endDateTime 
     * @param eventType 
     * @param creationTime 
     */
    public Event(String id, String name, String description, String locationDescription, LocalDateTime startDateTime, LocalDateTime endDateTime, EventType eventType, long creationTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.locationDescription = locationDescription;
        this.startDate = startDateTime.toLocalDate();
        this.startTime = startDateTime.toLocalTime();
        this.endDate = endDateTime.toLocalDate();
        this.endTime = endDateTime.toLocalTime();
        this.eventType = eventType;
        this.creationTime = creationTime;
    }


    /**
     * @return the id
     */
    public String getId() {
        return this.id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the locationDescription
     */
    public String getLocationDescription() {
        return locationDescription;
    }

    /**
     * @return the startDate
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * @return the startTime
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * @return the startDateTime
     */
    public LocalDateTime getStartDateTime() {
        return LocalDateTime.of(getStartDate(), getStartTime());
    }

    /**
     * @return the endDate
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * @return the endTime
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * @return the endDateTime
     */
    public LocalDateTime getEndDateTime() {
        return LocalDateTime.of(getEndDate(), getEndTime());
    }

    /**
     * @return the eventType
     */
    public EventType getEventType() {
        return eventType;
    }

    /**
     * @return the creationTime
     */
    public long getCreationTime() {
        return creationTime;
    }
    
    @Override
    public String toString() {
        return this.getName();
    }
    
    public String toExpandedString() {
        return "Event name: " + getName() + "\nEvent location: " + getLocationDescription() + "\nEvent description: " + getDescription() + "\nStarts at: " + getStartDateTime().toString() + "\nEnds at: " + getEndDateTime().toString() + "\nEvent type: " + getEventType().toString();
    }
    
}