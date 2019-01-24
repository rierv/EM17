package em17.logic;


import common.EmailSender;
import em17.database.CustomerDAO;
import em17.database.DAOFactory;
import em17.database.EventDAO;
import em17.database.LocationDAO;
import em17.entities.Event;
import em17.entities.EventTypeEnum.EventType;
import em17.entities.Location;
import em17.entities.Sector;
import em17.entities.SectorPrices;
import em17.entities.Turnstile;
import em17.gui.SceneManager;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javafx.event.ActionEvent;

/**
 * Manages all the "Manage events" function
 * @author Raffolox
 */
public class EventManagementController {

    // <editor-fold defaultstate="collapsed" desc=" Variables ">
    
    private static EventManagementController instance = null;
    
    /**
     * DAO Factory
     */
    DAOFactory daoFactory = DAOFactory.getInstance();
    
    /**
     * The DAO object used to access the locations informations
     */
    LocationDAO locationDAO = daoFactory.getLocationDAO();
    
    /**
     * The DAO object used to access the events informations
     */
    EventDAO eventDAO = daoFactory.getEventDAO();
    
    /**
     * Whether the user is modifying an existing event or creating a new one
     */
    private boolean modifyingEvent;

    /**
     * The active event's id
     */
    private String activeEventId;

    /**
     * The active event's location's description
     */
    private String activeLocationDescription;
    
        // <editor-fold defaultstate="collapsed" desc=" Event ID ">

        private static final int EVENT_ID_LENGTH = 12;
        private static final String EVENT_ID_ALLOWED_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        // </editor-fold>
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Constructor ">
        
    /**
     * Default constructor
     */
    private EventManagementController() {
        
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" Singleton method ">
    
    /**
     * @return The unique instance of EventManagementController 
     */
    public static EventManagementController getInstance() {
        if (instance==null) {
            instance = new EventManagementController();
        }
        return instance;
    }
    
    // </editor-fold>
    
    /**
     * Resets the controller to its initial state
     */
    public void reset() {
        this.activeEventId = null;
        this.activeLocationDescription = null;
        this.modifyingEvent = false;
        daoFactory.getDatabaseManager().rollbackToSafeState();
    }
    
    // <editor-fold defaultstate="collapsed" desc=" Changing the active screen ">

    /**
     * Shows the Event Informations Inserter Screen
     * @param event
     */
    private void showEventInformationInserterScreen(ActionEvent event) {
        SceneManager.changeScene(event, "/em17/gui/eventmanagement/addevent/EventInformationsInserterScreen.fxml");
        daoFactory.getDatabaseManager().startTransaction();
    }
    
    /**
     * Shows the Event Informations Inserter Screen for a new event
     * @param event
     */
    public void showEventInformationInserterScreenForNewEvent(ActionEvent event) {
        this.modifyingEvent = false;
        showEventInformationInserterScreen(event);
    }

    /**
     * Shows the price chooser screen
     * @param event
     */
    public void showPriceChooserScreen(ActionEvent event) {
        if (!eventDAO.isThereASoldTicketForEvent(activeEventId)) {
            while (true) {
                try {
                    int availablePrices = eventDAO.getPricesForEvent(activeEventId).size();
                    int sectorsNumber = locationDAO.getSectorsOfLocation(activeLocationDescription).size();
                    if (availablePrices == sectorsNumber) {
                        break;
                    }
                    else {
                        System.out.println("Prices: " + availablePrices + " sectors " + sectorsNumber);
                        Thread.sleep(1000);
                    }
                }
                catch (InterruptedException ignored) {
                }
            }
            SceneManager.changeScene(event, "/em17/gui/eventmanagement/addevent/PriceChooserScreen.fxml");
        }
        else { //Skip: the prices cannot be changed
            this.finalizeEvent(event);
        }
    }
    
    /**
     * Shows the event location's details inserter screen
     * @param event
     */
    public void showEventLocationDetailsInserterScreen(ActionEvent event) {
        SceneManager.changeScene(event, "/em17/gui/eventmanagement/addevent/EventLocationDetailsInserterScreen.fxml");
    }
    
    /**
     * Shows the statistics relative to the specified event
     * @param eventId 
     * @param event 
     */
    public void showEventStatistics(String eventId, ActionEvent event) {
        this.activeEventId = eventId;
        SceneManager.changeScene(event, "/em17/gui/eventmanagement/singleeventstats/SingleEventStatsScreen.fxml");
    }
    
    /**
     * Shows the main screen
     * @param event
     */
    public void showMainScreen(ActionEvent event) {
        SceneManager.changeScene(event, "/em17/gui/OuterView.fxml");
    }

    /**
     * Shows the Event Informations Inserter Screen filling it with some data from the event with id eventId
     * @param event
     * @param eventId 
     */
    public void showEventInformationInserterScreenForExistingEvent(String eventId, ActionEvent event) {
        this.modifyingEvent = true;
        this.activeEventId = eventId;
        showEventInformationInserterScreen(event);
    }

    /**
     * Shows the Event Informations Inserter Screen filling it with some data from the active event
     * @param event
     */
    public void showEventInformationInserterScreenForActiveEvent(ActionEvent event) {
        showEventInformationInserterScreen(event);
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" Reading the active events' list ">
    
    /**
     * @return all the events
     */
    public List<Event> getAllEvents() {
        return eventDAO.getAllEvents();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Inserting an event's basic informations ">
    
    /**
     * @return whether the active event is being modified or not
     */
    public boolean isModifyingEvent() {
        return modifyingEvent;
    }

    /**
     * Given the partial location description text, searches for all the locations with a
     * description starting with that partial description and returns them
     * @param partialLocationDescription 
     * @return the list of locations descriptions starting with the given partial description
     */
    public List<String> getNamesOfLocationsStartingWith(String partialLocationDescription) {
        return locationDAO.getNamesOfLocationsContaining(partialLocationDescription);
    }
    
        // <editor-fold defaultstate="collapsed" desc=" Creating an event ">
    
        
        public static final int EVENT_CREATION_STATUS_SUCCESSFUL = 0;
        public static final int EVENT_CREATION_STATUS_LOCATION_NOT_AVAILABLE = 1;
        public static final int EVENT_CREATION_STATUS_LOCATION_NOT_FOUND = 2;
    
        /**
         * Creates a new event and show the next screen
         * 
         * @param name 
         * @param description 
         * @param locationDescription 
         * @param startDateTime 
         * @param endDateTime 
         * @param eventType 
         * @param event 
         * @return one of three event statuses: the creation has been successful or the location is not existing or the location is not available
         */
        public int createEvent(String name, String description, String locationDescription, LocalDateTime startDateTime, LocalDateTime endDateTime, EventType eventType, ActionEvent event) {
            if (! locationDAO.exists(locationDescription)) {
                GoogleMapsAPI maps = new GoogleMapsAPI();
                if (maps.physicallyExists(locationDescription)) {
                    Location newLocation = maps.createNewLocationFromDescription(locationDescription);
                    System.out.println(newLocation);
                    locationDAO.add(newLocation);
                }
                else {
                    return EVENT_CREATION_STATUS_LOCATION_NOT_FOUND;
                }
            }
            if (! locationDAO.isLocationAvailable(locationDescription, startDateTime, endDateTime)) {
                return EVENT_CREATION_STATUS_LOCATION_NOT_AVAILABLE;
            }
            activeLocationDescription = locationDescription;

            Event e = getNewEvent(name, description, locationDescription, startDateTime, endDateTime, eventType);
            activeEventId = e.getId();
            System.out.println("activeEventId: "+activeEventId);
            eventDAO.add(e);

            if (locationDAO.isLocationUsedByAnotherEvent(locationDescription, activeEventId)) {
                for (Sector sector: locationDAO.getSectorsOfLocation(locationDescription)) {
                    eventDAO.setTicketFullPriceForSectorForEvent(activeEventId, sector.getSectorName(), 0.0f);
                    eventDAO.setTicketReducedPriceForSectorForEvent(activeEventId, sector.getSectorName(), 0.0f);
                }
                showPriceChooserScreen(event);
            }
            else {
                showEventLocationDetailsInserterScreen(event);
            }
            return EVENT_CREATION_STATUS_SUCCESSFUL;
        }

        /**
         * Creates a new Event object and returns it
         * @param name
         * @param description
         * @param locationDescription
         * @param startDateTime
         * @param endDateTime
         * @return The event object created
         */
        private Event getNewEvent(String name, String description, String locationDescription, LocalDateTime startDateTime, LocalDateTime endDateTime, EventType eventType) {
            String id = generateNewEventId();
            System.out.println("Sto creando l'evento con id: "+ id +" name: "+ name +" description: "+description+" location: "+ locationDescription +" startDateTime: "+ startDateTime +" endDateTime: "+ endDateTime +" tipo: "+ eventType.toString());
            Event event = new Event(id, name, description, locationDescription, startDateTime, endDateTime, eventType, new Date().getTime());
            System.out.println("Restituisco l'evento creato");
            return event;
        }

        /**
         * @return a new id used to create a new event
         */
        private String generateNewEventId() {
            Random r = new Random();
            StringBuilder eventId = new StringBuilder();
            for (int i=0; i<EVENT_ID_LENGTH; i++) {
                eventId.append(EVENT_ID_ALLOWED_CHARACTERS.charAt(r.nextInt(EVENT_ID_ALLOWED_CHARACTERS.length())));
            }
            return eventId.toString();
        }

        // </editor-fold>
    
        // <editor-fold defaultstate="collapsed" desc=" Modifying an event ">
    
        /**
         * @return the active event's name
         */
        public Event getActiveEvent() {
            return eventDAO.getEvent(activeEventId);
        }

        /**
         * Modifies the active event's properties
         * @param startDateTime 
         * @param endDateTime 
         * @param eventType 
         * @param event 
         */
        public void modifyEvent(LocalDateTime startDateTime, LocalDateTime endDateTime, EventType eventType, ActionEvent event) {
            eventDAO.setEventProperties(activeEventId, startDateTime, endDateTime, eventType);
            activeLocationDescription = eventDAO.getLocationOfEventDescription(activeEventId);
            showPriceChooserScreen(event);
        }

        // </editor-fold>
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" Adding a location's details ">

    /**
     * @return A list of Sector elements, each of them representing a sector of the active location
     */
    public List<Sector> getSectorsOfActiveLocation() {
        return locationDAO.getSectorsOfLocation(activeLocationDescription);
    }
    
    /**
     * @param name 
     * @param capacity 
     */
    public void addSector(String name, int capacity) {
        locationDAO.addSectorToLocation(activeLocationDescription, name, capacity);
        this.setTicketFullPriceForSector(name, 0.0f);
        this.setTicketReducedPriceForSector(name, 0.0f);
    }

    /**
     * @param sectorName
     * @return A list of Turnstile elements, each of them representing a turnstile of the specified sector of the active location
     */
    public List<Turnstile> getTurnstilesOfSectorOfActiveLocation(String sectorName) {
        return locationDAO.getTurnstilesOfSectorOfLocation(activeLocationDescription, sectorName);
    }

    /**
     * @param oldSectorName 
     * @param newSectorName 
     * @param newCapacity 
     */
    public void modifySector(String oldSectorName, String newSectorName, int newCapacity) {
        locationDAO.modifySectorOfLocation(activeLocationDescription, oldSectorName, newSectorName, newCapacity);
    }

    /**
     * @param sectorName 
     */
    public void removeSector(String sectorName) {
        locationDAO.removeSectorOfLocation(activeLocationDescription, sectorName);
    }

    /**
     * @param sectorName the sector the turnstile is included in
     * @param turnstileName 
     */
    public void addTurnstile(String sectorName, String turnstileName) {
        locationDAO.addTurnstileToSectorOfLocation(activeLocationDescription, sectorName, turnstileName);
    }

    /**
     * @param sectorName the sector the turnstile is included in
     * @param oldTurnstileName 
     * @param newTurnstileName 
     */
    public void renameTurnstile(String sectorName, String oldTurnstileName, String newTurnstileName) {
        locationDAO.renameTurnstileOfSectorOfLocation(activeLocationDescription, sectorName, oldTurnstileName, newTurnstileName);
    }

    /**
     * @param sectorName the sector the turnstile is included in
     * @param turnstileName 
     */
    public void removeTurnstile(String sectorName, String turnstileName) {
        locationDAO.removeTurnstileOfSectorOfLocation(activeLocationDescription, sectorName, turnstileName);
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Changing an event's price ">

    /**
     * @return A list of Price elements, each of them representing a price for a sector of the active location for the active event
     */
    public List<SectorPrices> getPricesOfActiveEvent() {
        return eventDAO.getPricesForEvent(activeEventId);
    }
    
    /**
     * Changes the full price for the given sector of the active event
     * @param sectorName 
     * @param price 
     */
    public void setTicketFullPriceForSector(String sectorName, float price) {
        eventDAO.setTicketFullPriceForSectorForEvent(activeEventId, sectorName, price);
    }

    /**
     * Changes the reduced price for the given sector of the active event
     * @param sectorName 
     * @param price 
     */
    public void setTicketReducedPriceForSector(String sectorName, float price) {
        eventDAO.setTicketReducedPriceForSectorForEvent(activeEventId, sectorName, price);
    }

    // </editor-fold>
        
    // <editor-fold defaultstate="collapsed" desc=" Creation/modification finalizing ">
    
    /**
     * Terminates the event creation or the event editing
     * @param event
     */
    public void finalizeEvent(ActionEvent event) {
        System.out.println("FINALIZING EVENT");
        if (isModifyingEvent()) {
            sendEmailToTicketHolders(activeEventId, "The event " + getActiveEvent().toString() + " has been modified", getActiveEvent().toExpandedString());
        }
        daoFactory.getDatabaseManager().commitTransaction();
        showMainScreen(event);
    }

    /**
     * Sends an email to each customer who owns a ticket for the event with the specified id
     * @param eventId 
     * @param subject 
     * @param body 
     */
    public void sendEmailToTicketHolders(String eventId, String subject, String body) {
        CustomerDAO customerDAO = daoFactory.getCustomerDAO();
        List<String> emailAddresses = customerDAO.getEmailAddressesOfAllCustomersOfEvent(eventId);
        emailAddresses.forEach((emailAddress) -> {
            sendEmail(emailAddress, subject, body);
        });
    }

    /**
     * Sends an email to the specified email address
     * @param emailAddress 
     * @param subject 
     * @param body 
     */
    private void sendEmail(String emailAddress, String subject, String body) {
        EmailSender.sendEmail(emailAddress, subject, body);
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" Deletion ">
    
    /**
     * Deletes the event with the given ID
     * @param eventId 
     */
    public void deleteEventWithId(String eventId) {
        sendEmailToTicketHolders(eventId, "The event " + eventDAO.getEvent(eventId).toString() + " has been deleted!", eventDAO.getEvent(eventId).toExpandedString());
        eventDAO.deleteEventById(eventId);
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" Utils ">
    
    /**
     * @return the active event's id, sort of a global variable
     */
    public String getActiveEventId() {
        return activeEventId;
    }
    
    // </editor-fold>
    
}