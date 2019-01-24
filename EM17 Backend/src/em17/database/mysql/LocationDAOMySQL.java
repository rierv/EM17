package em17.database.mysql;


import em17.database.DBSchema;
import em17.database.LocationDAO;
import em17.entities.Location;
import em17.entities.Turnstile;
import em17.entities.Sector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
    * Represents a DAO object used to access Locations in the underlying database
 */
public class LocationDAOMySQL extends LocationDAO {

    /**
     * Default constructor
     */
    public LocationDAOMySQL() {
    }


    /**
     * @param partialLocationDescription 
     * @return a list of location names containing the specified description
     */
    public List<String> getNamesOfLocationsContaining(String partialLocationDescription) {
        List<String> list = new ArrayList<>();
        ResultSet resultSet;
        String statement = "SELECT "+ DBSchema.LOCATION_LOCATION_DESCRIPTION_COLUMN
                         +" FROM "+ DBSchema.LOCATION_TABLE
                         +" WHERE "+DBSchema.LOCATION_LOCATION_DESCRIPTION_COLUMN +" LIKE \"%"+partialLocationDescription+"%\";"; 
        
        resultSet = DatabaseManagerMySQL.getInstance().query(statement);
        try {
            if (resultSet != null) {
                resultSet.beforeFirst();
                while(resultSet.next()){
                    System.out.println("Lista di locationDescription ottenuta "+resultSet.getString(1));
                    list.add(resultSet.getString(1));
                }
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(LocationDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * @param description
     * @return a Location object whose description is the specified one
     */
    public Location getLocation(String description) {
        Location location;
        double latitude, longitude;
        ResultSet resultSet;
        String statement = "SELECT *"
                         +" FROM "+ DBSchema.LOCATION_TABLE
                         +" WHERE "+ DBSchema.LOCATION_LOCATION_DESCRIPTION_COLUMN +"=\""+ description +"\";"; 
       
        resultSet = DatabaseManagerMySQL.getInstance().query(statement);
        try {
            if (resultSet != null) {
                resultSet.first();
                latitude = resultSet.getDouble(2);
                longitude = resultSet.getDouble(3);
                System.out.println("latitude "+latitude);
                System.out.println("longitude "+longitude);
                location = new Location(description, latitude, longitude);
                return location;
            }
            else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LocationDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    /**
     * @param locationDescription 
     * @return whether a location with the specified description exists or not
     */
    public boolean exists(String locationDescription) {
        ResultSet resultSet;
        int counter;
        String statement ="SELECT COUNT(*) "
                        +" FROM "+ DBSchema.LOCATION_TABLE
                        +" WHERE "+DBSchema.LOCATION_LOCATION_DESCRIPTION_COLUMN +"=\""+ locationDescription +"\";";
        resultSet = DatabaseManagerMySQL.getInstance().query(statement);
        try {
            if (resultSet != null) {
                resultSet.first();
                counter = resultSet.getInt(1);
                System.out.println("Counter ottenuto: "+counter);
                return counter!=0;
            }
            else {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LocationDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * @param locationDescription 
     * @param eventId 
     * @return whether or not an event hosted in the specified description exists
     */
    public boolean isLocationUsedByAnotherEvent(String locationDescription, String eventId) {
        ResultSet resultSet;
        int counter;
        String statement = "SELECT COUNT(*)"
                         +" FROM "+ DBSchema.EVENT_TABLE
                         +" WHERE "+ DBSchema.EVENT_LOCATION_DESCRIPTION_COLUMN +"=\""+ locationDescription +"\" AND " + DBSchema.EVENT_ID_COLUMN + " <> \"" + eventId + "\";";
        
        resultSet = DatabaseManagerMySQL.getInstance().query(statement);
        try {
            if (resultSet != null) {
                resultSet.first();
                counter = resultSet.getInt(1);
                System.out.println("Counter ottenuto "+counter);
                return counter!=0;
            }
            else {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LocationDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * @param locationDescription 
     * @return a list of Sector objects, each of them representing a sector of the location whose description is the given one
     */
    public List<Sector> getSectorsOfLocation(String locationDescription) {
        Sector sector;
        List<Sector> sectorList = new ArrayList<>();
        int capacity;
        ResultSet resultSet;
        String sectorName,
               statement = "SELECT "+ DBSchema.SECTOR_SECTOR_NAME_COLUMN +","+ DBSchema.SECTOR_CAPACITY_COLUMN 
                         +" FROM "+ DBSchema.SECTOR_TABLE
                         +" WHERE "+ DBSchema.SECTOR_LOCATION_DESCRIPTION_COLUMN +"=\""+ locationDescription +"\";";
                             
        resultSet = DatabaseManagerMySQL.getInstance().query(statement);
        try {
            if (resultSet != null) {
                resultSet.beforeFirst();
                while(resultSet.next()){
                    sectorName = resultSet.getString(1);
                    capacity = resultSet.getInt(2);
                    System.out.println("sectorName ottenuto "+sectorName+" capacity ottenuta "+ capacity);
                    sector = new Sector(sectorName, capacity);
                    sectorList.add(sector);
                }
            }
            return sectorList;
        } catch (SQLException ex) {
            Logger.getLogger(LocationDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Adds a new location to the database
     * @param location 
     */
    public void add(Location location) {
        String statement = "INSERT INTO "+ DBSchema.LOCATION_TABLE +"("+ DBSchema.LOCATION_LOCATION_DESCRIPTION_COLUMN +", "+ DBSchema.LOCATION_LATITUDE_COLUMN +","+ DBSchema.LOCATION_LONGITUDE_COLUMN +")"
                          +" VALUES (\""+ location.getDescription() +"\", \""+ location.getLatitude() +"\" ,\""+ location.getLongitude() +"\");";
        
        DatabaseManagerMySQL.getInstance().update(statement);
    }

    /**
     * Adds a new sector to the location
     * @param locationDescription 
     * @param name 
     * @param capacity 
     */
    public void addSectorToLocation(String locationDescription, String name, int capacity) {
        String statement ="INSERT INTO "+ DBSchema.SECTOR_TABLE +" ("+ DBSchema.SECTOR_SECTOR_NAME_COLUMN +","+ DBSchema.SECTOR_CAPACITY_COLUMN +","+ DBSchema.SECTOR_LOCATION_DESCRIPTION_COLUMN +")"
                        +" VALUES (\""+ name +"\", \""+ capacity +"\", \""+ locationDescription +"\");";
        DatabaseManagerMySQL.getInstance().update(statement);
    }

    /**
     * @param activeLocationDescription
     * @param sectorName
     * @return a list of turnstile objects, each of them representing a turnstile of the sector whose name is the given one and whose location is the given one
     */
    public List<Turnstile> getTurnstilesOfSectorOfLocation(String activeLocationDescription, String sectorName) {
        List<Turnstile> turnstileList = new ArrayList<>();
        Turnstile turnstile;
        ResultSet resultSet;
        String turnstileName,
               statement = "SELECT "+ DBSchema.TURNSTILE_TURNSTILE_NAME_COLUMN
                         +" FROM "+ DBSchema.TURNSTILE_TABLE
                         +" WHERE "+DBSchema.TURNSTILE_LOCATION_DESCRIPTION_COLUMN +"=\""+ activeLocationDescription +"\" AND "+ DBSchema.TURNSTILE_SECTOR_NAME_COLUMN +"=\""+ sectorName +"\";";
        
        resultSet = DatabaseManagerMySQL.getInstance().query(statement);
        try {
            if (resultSet != null) {
                resultSet.beforeFirst();
                while(resultSet.next()){
                    turnstileName = resultSet.getString(1);
                    System.out.println("nome tornello "+ turnstileName);
                    turnstile = new Turnstile(turnstileName);
                    turnstileList.add(turnstile);
                }
            }
            return turnstileList;
        } catch (SQLException ex) {
            Logger.getLogger(LocationDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Modifies a sector of a location
     * @param locationDescription 
     * @param oldSectorName 
     * @param newSectorName 
     * @param newCapacity 
     */
    public void modifySectorOfLocation(String locationDescription, String oldSectorName, String newSectorName, int newCapacity) {
        String statement = "UPDATE "+ DBSchema.SECTOR_TABLE
                         +" SET "+ DBSchema.SECTOR_SECTOR_NAME_COLUMN +"=\""+ newSectorName +"\", "+ DBSchema.SECTOR_CAPACITY_COLUMN +"=\""+ newCapacity +"\""
                         +" WHERE "+ DBSchema.SECTOR_SECTOR_NAME_COLUMN +"=\""+ oldSectorName +"\";";
        
        DatabaseManagerMySQL.getInstance().update(statement);
    }

    /**
     * Adds a turnstile to a sector of a location
     * @param locationDescription 
     * @param sectorName 
     * @param turnstileName 
     */
    public void addTurnstileToSectorOfLocation(String locationDescription, String sectorName, String turnstileName) {
        String statement ="INSERT INTO "+ DBSchema.TURNSTILE_TABLE +"("+ DBSchema.TURNSTILE_TURNSTILE_NAME_COLUMN +","+ DBSchema.TURNSTILE_LOCATION_DESCRIPTION_COLUMN +","+ DBSchema.TURNSTILE_SECTOR_NAME_COLUMN +")"
                        +" VALUES (\""+ turnstileName +"\", \""+ locationDescription +"\", \""+ sectorName +"\");";
        
        DatabaseManagerMySQL.getInstance().update(statement);
    }

    /**
     * Renames a turnstile of a sector of a location
     * @param locationDescription 
     * @param sectorName 
     * @param oldTurnstileName 
     * @param newTurnstileName 
     */
    public void renameTurnstileOfSectorOfLocation(String locationDescription, String sectorName, String oldTurnstileName, String newTurnstileName) {
        String statement = "UPDATE "+ DBSchema.TURNSTILE_TABLE
                         +" SET "+ DBSchema.TURNSTILE_TURNSTILE_NAME_COLUMN +"=\""+ newTurnstileName +"\""
                         +" WHERE "+ DBSchema.TURNSTILE_SECTOR_NAME_COLUMN +"=\""+ sectorName +"\" AND "+ DBSchema.TURNSTILE_LOCATION_DESCRIPTION_COLUMN +"=\""+ locationDescription +"\" AND "+ DBSchema.TURNSTILE_TURNSTILE_NAME_COLUMN +"=\""+ oldTurnstileName +"\";";
        
        DatabaseManagerMySQL.getInstance().update(statement);
    }

    /**
     * Removes a turnstile from a sector of a location
     * @param locationDescription 
     * @param sectorName 
     * @param turnstileName 
     */
    public void removeTurnstileOfSectorOfLocation(String locationDescription, String sectorName, String turnstileName) {
        String statement = "DELETE FROM "+ DBSchema.TURNSTILE_TABLE
                         +" WHERE "+ DBSchema.TURNSTILE_LOCATION_DESCRIPTION_COLUMN +"=\""+ locationDescription +"\" AND "+ DBSchema.TURNSTILE_TURNSTILE_NAME_COLUMN +"=\""+ turnstileName +"\";";
    
        DatabaseManagerMySQL.getInstance().update(statement);
    }

    /**
     * Removes a sector of a location
     * @param locationDescription 
     * @param sectorName 
     */
    public void removeSectorOfLocation(String locationDescription, String sectorName) {
        String statement = "DELETE FROM "+ DBSchema.SECTOR_TABLE
                         +" WHERE "+ DBSchema.SECTOR_LOCATION_DESCRIPTION_COLUMN +"=\""+ locationDescription +"\" AND "+ DBSchema.SECTOR_SECTOR_NAME_COLUMN +"=\""+ sectorName +"\";";
    
        DatabaseManagerMySQL.getInstance().update(statement);
    }

    /**
     * @param locationDescription
     * @param startDateTime
     * @param endDateTime
     * @return whether or not the specified location is available in the time frame between startDateTime and endDateTime
     */
    public boolean isLocationAvailable(String locationDescription, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        int counter;
        ResultSet resultSet;
        String statement = "SELECT COUNT(*)"
                         +" FROM "+ DBSchema.EVENT_TABLE
                         +" WHERE "+DBSchema.EVENT_LOCATION_DESCRIPTION_COLUMN +"=\""+ locationDescription +"\" AND ("+ DBSchema.EVENT_START_DATE_TIME_COLUMN +"<=\""+ startDateTime +"\" OR "+ DBSchema.EVENT_START_DATE_TIME_COLUMN +"<=\""+ endDateTime +"\") AND ("+ DBSchema.EVENT_END_DATE_TIME_COLUMN +">=\""+ startDateTime +"\" OR "+ DBSchema.EVENT_END_DATE_TIME_COLUMN +">=\""+ endDateTime +"\");";   
        resultSet = DatabaseManagerMySQL.getInstance().query(statement);
        try {
            if(resultSet != null){
                resultSet.first();
                counter = resultSet.getInt(1);
                return counter == 0;
            }
            else{
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LocationDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

}