package em17.database.mysql;

import em17.database.DBSchema;
import em17.database.EventDAO;
import em17.entities.SectorPrices;
import em17.entities.Event;
import em17.entities.EventTypeEnum;
import em17.entities.EventTypeEnum.EventType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Represents a DAO object used to access Events in the underlying database
 */
public class EventDAOMySQL extends EventDAO {

    /**
     * @param event 
     * 
     */
    public void add(Event event) {
        System.out.println("startDate: "+event.getStartDate()+" startTime: "+event.getStartTime() +" endDate: "+event.getEndDate()+" endTime: "+event.getEndTime());
        String formatStartDateTime = event.getStartDate().toString() +" "+ event.getStartTime().toString(),
               formatEndDateTime = event.getEndDate().toString() +" "+ event.getEndTime().toString(),
               statement = "INSERT INTO "+ DBSchema.EVENT_TABLE +" ("+ DBSchema.EVENT_ID_COLUMN +","+ DBSchema.EVENT_NAME_COLUMN +","+ DBSchema.EVENT_DESCRIPTION_COLUMN +","+ DBSchema.EVENT_START_DATE_TIME_COLUMN +","+ DBSchema.EVENT_END_DATE_TIME_COLUMN +","+ DBSchema.EVENT_CREATION_TIME_COLUMN +","+ DBSchema.EVENT_LOCATION_DESCRIPTION_COLUMN +","+ DBSchema.EVENT_TYPE_COLUMN +")"
                            +"VALUES (\""+ event.getId() +"\", \""+ event.getName() +"\", \""+ event.getDescription() +"\", \""+ formatStartDateTime +"\", \""+ formatEndDateTime +"\", \""+ event.getCreationTime() +"\", \""+ event.getLocationDescription() +"\", \""+ event.getEventType() +"\");";
        System.out.println("startDateTime riformattato: "+formatStartDateTime+" endDateTime riformattato: "+formatEndDateTime);
        DatabaseManagerMySQL.getInstance().update(statement);
    }   
    
    /**
     * @param eventId 
     * @return the specified event's location's description
     */
    public String getLocationOfEventDescription(String eventId) {
        ResultSet resultSet;
        String statementRetrieveLocationDescription = "SELECT "+ DBSchema.EVENT_LOCATION_DESCRIPTION_COLUMN 
                                                    +" FROM "+ DBSchema.EVENT_TABLE 
                                                    +" WHERE "+ DBSchema.EVENT_ID_COLUMN +"=\""+ eventId +"\";";
        try{
            resultSet = DatabaseManagerMySQL.getInstance().query(statementRetrieveLocationDescription);
            if (resultSet != null) {
                resultSet.first();
                return resultSet.getString(1);
            }
            else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    
    /**
     * Inserts a price of the specified type for the specified sector of the specified event
     */
    private void setTicketPrice(String eventId, String sectorName, float price, String priceType) {
        if (!isThereASoldTicketForEvent(eventId)) {
            String locationDescription = getLocationOfEventDescription(eventId);
            String deleteStatement = "DELETE FROM " + DBSchema.PRICES_TABLE +
                    " WHERE " + DBSchema.PRICES_EVENT_ID_COLUMN + " = \"" + eventId + "\" AND " + DBSchema.PRICES_SECTOR_NAME_COLUMN + " = \"" + sectorName + "\" AND " + DBSchema.PRICES_TYPE_COLUMN + " = \"" + priceType + "\";";
            String insertStatement = "INSERT INTO "+ DBSchema.PRICES_TABLE + 
                    "("+ DBSchema.PRICES_LOCATION_DESCRIPTION_COLUMN +","+ DBSchema.PRICES_SECTOR_NAME_COLUMN +","+ DBSchema.PRICES_EVENT_ID_COLUMN +","+ DBSchema.PRICES_PRICE_COLUMN +","+ DBSchema.PRICES_TYPE_COLUMN +") "
                    +"VALUES (\""+ locationDescription +"\", \""+ sectorName +"\", \""+ eventId +"\", \""+ price +"\", \""+ priceType +"\");";

            DatabaseManagerMySQL.getInstance().update(deleteStatement);
            DatabaseManagerMySQL.getInstance().update(insertStatement);
        }
    }
    
    /**
     * @param eventId 
     * @param sectorName 
     * @param price 
     * 
     */
    public void setTicketFullPriceForSectorForEvent(String eventId, String sectorName, float price) {
        setTicketPrice(eventId, sectorName, price, DBSchema.PRICES_TYPE_FULL);
    }

    /**
     * @param eventId 
     * @param sectorName 
     * @param price 
     * 
     */
    public void setTicketReducedPriceForSectorForEvent(String eventId, String sectorName, float price) {
        setTicketPrice(eventId, sectorName, price, DBSchema.PRICES_TYPE_REDUCED);
    }
    
    /**
     * @param eventId 
     * 
     * @return
     */
    public boolean isThereASoldTicketForEvent(String eventId) {
        ResultSet resultSet;
        String statement = " SELECT COUNT(*)"
                          +" FROM "+ DBSchema.SOLD_TICKET_TABLE 
                          +" WHERE "+ DBSchema.SOLD_TICKET_EVENT_ID_COLUMN +"=\""+ eventId +"\";";
        resultSet = DatabaseManagerMySQL.getInstance().query(statement);
        
        try {
            if (resultSet != null) {
                resultSet.first();
                System.out.println("count "+resultSet.getInt(1));
                return resultSet.getInt(1)!=0;
            }
            else {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }


    /**
     * @param eventId 
     * @param startDateTime 
     * @param endDateTime 
     * @param eventType 
     */
    public void setEventProperties(String eventId, LocalDateTime startDateTime, LocalDateTime endDateTime, EventType eventType) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss");
        
        String formatStartDateTime = startDateTime.format(formatter);
        String formatEndDateTime = endDateTime.format(formatter);
        String statementForUpdateTheOthersAttributes = "UPDATE "+ DBSchema.EVENT_TABLE 
                                                     +" SET "+ DBSchema.EVENT_START_DATE_TIME_COLUMN +"=\""+ formatStartDateTime +"\", "+ DBSchema.EVENT_END_DATE_TIME_COLUMN +"=\""+ formatEndDateTime +"\", "+ DBSchema.EVENT_TYPE_COLUMN +"=\""+ eventType +"\""
                                                     +" WHERE "+ DBSchema.EVENT_ID_COLUMN +"=\""+ eventId +"\";";
        DatabaseManagerMySQL.getInstance().update(statementForUpdateTheOthersAttributes);
    }

    /**
     * @param eventId 
     */
    public void deleteEventById(String eventId) {
        String statement = "DELETE FROM "+ DBSchema.EVENT_TABLE 
                         +" WHERE "+ DBSchema.EVENT_ID_COLUMN +"=\""+ eventId +"\";";
        DatabaseManagerMySQL.getInstance().update(statement);
    }

    /**
     * @param eventId 
     * @param ageMinInclusive 
     * @param ageMaxExclusive 
     * @return
     */
    public int getCustomersAmountForAgeRangeForEvent(String eventId, int ageMinInclusive, int ageMaxExclusive) {
        String tabellaEta = "(SELECT (DATEDIFF(now(),"+ DBSchema.CUSTOMER_BIRTH_DATE_COLUMN +") / 365.25) AS age"
                           +" FROM "+ DBSchema.CUSTOMER_TABLE +" INNER JOIN "+ DBSchema.SOLD_TICKET_TABLE 
                                +" ON "+DBSchema.CUSTOMER_TABLE +"."+ DBSchema.CUSTOMER_EMAIL_ADDRESS_COLUMN +"="+ DBSchema.SOLD_TICKET_TABLE +"."+ DBSchema.SOLD_TICKET_EMAIL_ADDRESS_COLUMN 
                           +" WHERE "+ DBSchema.SOLD_TICKET_EVENT_ID_COLUMN +"=\""+ eventId +"\") AS tabellaEta",
               statementAmount = "SELECT COUNT(*)"
                               +" FROM "+ tabellaEta 
                               +" WHERE tabellaEta.age >=\" "+ ageMinInclusive +"\" AND tabellaEta.age <\" "+ ageMaxExclusive +"\";";
        
        ResultSet resultSet = DatabaseManagerMySQL.getInstance().query(statementAmount);
        
        try {
            if (resultSet != null) {
                resultSet.first();
                System.out.println("counter "+resultSet.getInt(1));
                return resultSet.getInt(1);
            }
            else {
                return 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    /**
     * @param eventId 
     * @return
     */
    public Map<String, Number> getSoldTicketAmountForEachSectorOfEventLocation(String eventId) {
        ResultSet resultSet;
        TreeMap<String, Number> map = new TreeMap<>();
        
        String statement ="SELECT " + DBSchema.SOLD_TICKET_SECTOR_NAME_COLUMN + ", COUNT(*)"
                         +" FROM "+ DBSchema.SOLD_TICKET_TABLE 
                         +" WHERE "+ DBSchema.SOLD_TICKET_EVENT_ID_COLUMN +" = \""+ eventId +"\""
                         +" GROUP BY " + DBSchema.SOLD_TICKET_SECTOR_NAME_COLUMN + ";";
                 
        resultSet = DatabaseManagerMySQL.getInstance().query(statement);
        try {
            if (resultSet != null) {
                resultSet.beforeFirst();
                while(resultSet.next()) {
                    String sectorName = resultSet.getString(1);
                    System.out.println("sectorName "+sectorName);
                    int count = resultSet.getInt(2);
                    map.put(sectorName, count);
                }
            }
            return map;
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private float getTicketsPercentageOfEventByTicketType(String eventId, String ticketType) {
        int totalTicketsNumberForEventId, ticketsOfThisTypeAmount;
        ResultSet resultSet;
        String statement = "SELECT COUNT(*) FROM " + DBSchema.SOLD_TICKET_TABLE + " AS S JOIN (SELECT * FROM " + DBSchema.PRICES_TABLE + " WHERE " + DBSchema.PRICES_TYPE_COLUMN + " = \"" + ticketType + "\") AS P ON S." + DBSchema.SOLD_TICKET_SECTOR_NAME_COLUMN + " = P." + DBSchema.PRICES_SECTOR_NAME_COLUMN + " AND S." + DBSchema.SOLD_TICKET_EVENT_ID_COLUMN + " = P." + DBSchema.PRICES_EVENT_ID_COLUMN + " AND S." + DBSchema.SOLD_TICKET_PRICE_COLUMN + " = P." + DBSchema.PRICES_PRICE_COLUMN + ";";
        totalTicketsNumberForEventId = getSoldTicketsAmountForEvent(eventId);
        resultSet=DatabaseManagerMySQL.getInstance().query(statement);
        if (resultSet != null) {
            try {
                resultSet.first();
                ticketsOfThisTypeAmount = resultSet.getInt(1);
                System.out.println("counter "+ticketsOfThisTypeAmount);
                return ((float)(ticketsOfThisTypeAmount))/totalTicketsNumberForEventId*100;
            } catch (SQLException ex) {
                Logger.getLogger(EventDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
                return 0.0f;
            }
        }
        else {
            return 0.0f;
        }
    }
    
    /**
     * @param eventId 
     * @return
     */
    public float getFullTicketsPercentageOfEvent(String eventId) {
        return this.getTicketsPercentageOfEventByTicketType(eventId, DBSchema.PRICES_TYPE_FULL);
    }

    /**
     *
     * @param eventId
     * @return
     */
    public float getReducedTicketsPercentageOfEvent(String eventId) {
        return this.getTicketsPercentageOfEventByTicketType(eventId, DBSchema.PRICES_TYPE_REDUCED);
    }

    /**
     * @param eventId 
     * @return
     */
    public long getMillisSinceEventCreation(String eventId){
        long creationMillis, sinceEventCreationMillis;
        long todayInMillis =  new Date().getTime();
        ResultSet resultSet;
        String statement = "SELECT "+ DBSchema.EVENT_CREATION_TIME_COLUMN 
                         +" FROM "+ DBSchema.EVENT_TABLE
                         +" WHERE "+ DBSchema.EVENT_ID_COLUMN +"=\""+ eventId +"\";";
        
        resultSet = DatabaseManagerMySQL.getInstance().query(statement);
        try {
            if (resultSet!=null) {
                resultSet.first();
                creationMillis = resultSet.getLong(1);
                System.out.println("creationMillis "+creationMillis);
                sinceEventCreationMillis = todayInMillis - creationMillis;
                return sinceEventCreationMillis;
            }
            else {
                return 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    /**
     * @param millis 
     * @return
     */
    public int getAmountOfTicketsOfEventSoldBefore(String eventId, long millis) {
        int amount;
        ResultSet resultSet;
        String statement = "SELECT COUNT(*)" 
                         +" FROM "+ DBSchema.SOLD_TICKET_TABLE
                         +" WHERE "+ DBSchema.SOLD_TICKET_TIME_OF_SALE_MILLIS_COLUMN +" < \""+ millis +"\" AND " + DBSchema.SOLD_TICKET_EVENT_ID_COLUMN + " = \"" + eventId + "\";";
        
        resultSet = DatabaseManagerMySQL.getInstance().query(statement);
        try {
            if (resultSet != null) {
                resultSet.first();
                amount = resultSet.getInt(1);
                System.out.println("amount "+amount);
                return amount;
            }
            else {
                return 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    /**
     * @param eventId 
     * @return
     */
    public int getSoldTicketsAmountForEvent(String eventId) {
        ResultSet resultSet;
        int totalTicketsNumberForEventId;
        String statementForRetrieveTotalTicketsNumberForEventId = "SELECT COUNT(*)"
                                                                +" FROM "+ DBSchema.SOLD_TICKET_TABLE 
                                                                +" WHERE "+DBSchema.SOLD_TICKET_EVENT_ID_COLUMN +"="+ "\""+ eventId +"\";";
        
        resultSet = DatabaseManagerMySQL.getInstance().query(statementForRetrieveTotalTicketsNumberForEventId);
        try {
            if (resultSet != null) {
                resultSet.first();
                totalTicketsNumberForEventId = resultSet.getInt(1);
                System.out.println("counter "+totalTicketsNumberForEventId);
                return totalTicketsNumberForEventId;
            }
            else {
                return 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
    
    /**
     * @param eventId
     * @return a list of SectorPrices objects, each of them containing a sector's name and a price for each available price type
     */
    public List<SectorPrices> getPricesForEvent(String eventId) {
        System.out.println("GET PRICES FOR EVENT HERE");
        ResultSet resultSetFullPrices, resultSetReducedPrices;
        String statementSectorPricesFull = "SELECT "+ DBSchema.PRICES_SECTOR_NAME_COLUMN +", "+ DBSchema.PRICES_PRICE_COLUMN 
                                         +" FROM "+ DBSchema.PRICES_TABLE 
                                         +" WHERE "+ DBSchema.PRICES_TYPE_COLUMN +"=\""+ DBSchema.PRICES_TYPE_FULL + "\" AND "+ DBSchema.PRICES_EVENT_ID_COLUMN +"=\""+ eventId +"\";";
        String statementSectorPricesReduced;
        String sectorName;
        float fullPrice, reducedPrice;
        List<SectorPrices> list = new ArrayList<>();
        SectorPrices sectorPrices;
        
        resultSetFullPrices = DatabaseManagerMySQL.getInstance().query(statementSectorPricesFull); //retrieve sectorName and fullPrice by eventId
        try {
            if (resultSetFullPrices != null) {
                resultSetFullPrices.beforeFirst();
                while(resultSetFullPrices.next()){
                    sectorName = resultSetFullPrices.getString(1);
                    fullPrice = resultSetFullPrices.getFloat(2);

                    statementSectorPricesReduced = "SELECT "+ DBSchema.PRICES_PRICE_COLUMN
                                                 +" FROM "+ DBSchema.PRICES_TABLE
                                                 +" WHERE "+ DBSchema.PRICES_TYPE_COLUMN +" = \""+ DBSchema.PRICES_TYPE_REDUCED + "\" AND "+ DBSchema.PRICES_EVENT_ID_COLUMN +"=\""+ eventId +"\" AND "+ DBSchema.PRICES_SECTOR_NAME_COLUMN +"=\""+ sectorName +"\";";

                    resultSetReducedPrices = DatabaseManagerMySQL.getInstance().query(statementSectorPricesReduced); //retrieve reducedPrice by sectorName and eventId
                    if (resultSetReducedPrices != null) {
                        resultSetReducedPrices.first();
                        reducedPrice = resultSetReducedPrices.getFloat(1);
                        System.out.println("sectorName "+sectorName+" fullPrice "+fullPrice+" prezzo ridotto "+reducedPrice);
                        sectorPrices = new SectorPrices(sectorName, fullPrice, reducedPrice);
                        list.add(sectorPrices);
                    }
                    else {
                        return null;
                    }
                }
                return list;
            }
            else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    /**
     * @param eventId
     * @return the Event object whose id is the specified one
     */
    public Event getEvent(String eventId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.S");
        LocalDateTime startDateTime, endDateTime;
        EventType eventType;
        long creationTime;
        Event event;
        ResultSet resultSet;
        String name, description, startDateTimeString, endDateTimeString, locationDescription, eventTypeString,
               statement = "SELECT *"+
                          " FROM "+ DBSchema.EVENT_TABLE 
                         +" WHERE "+ DBSchema.EVENT_ID_COLUMN +" = \""+ eventId +"\";";
 
        resultSet = DatabaseManagerMySQL.getInstance().query(statement);
        try {
            if (resultSet != null) {
                resultSet.first();
                name = resultSet.getString(2);
                description = resultSet.getString(3);
                startDateTimeString = resultSet.getString(4);
                endDateTimeString = resultSet.getString(5);

                startDateTimeString = startDateTimeString.replace ( " " , "T" );
                startDateTime = LocalDateTime.parse(startDateTimeString, formatter);

                endDateTimeString = endDateTimeString.replace ( " " , "T" );
                endDateTime = LocalDateTime.parse(endDateTimeString);


                creationTime = resultSet.getLong(6);
                locationDescription = resultSet.getString(7);
                eventTypeString = resultSet.getString(8);
                eventType = EventTypeEnum.getEventTypeFromString(eventTypeString);
                System.out.println("name :"+name+" description :"+description+" startDateTime :"+startDateTime +" endDateTime :"+endDateTime+" creationTime :"+creationTime+" locationDescription :"+locationDescription+" eventType :"+eventType);
                event = new Event(eventId, name, description, locationDescription, startDateTime, endDateTime, eventType, creationTime);
                return event;
            }
            else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } 
    }
    
    /**
     * @return all the existing Events
     */
    public List<Event> getAllEvents() {
        List<Event> list = new ArrayList<>();
        ResultSet resultSet;
        String statement = "SELECT "+ DBSchema.EVENT_ID_COLUMN 
                         +" FROM "+ DBSchema.EVENT_TABLE +";";
        Event event;
        
        resultSet = DatabaseManagerMySQL.getInstance().query(statement);
        try {
            if (resultSet != null) {
                resultSet.beforeFirst();
                while(resultSet.next()){
                    System.out.println("eventId ottenuto "+resultSet.getString(1));
                    event = getEvent(resultSet.getString(1));
                    list.add(event);
                }
                return list;
            }
            else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}