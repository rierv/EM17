package em17.entities;


/**
 * Represents a single location in which events are hosted
 */
public class Location {



    /**
     * The location's textual description
     */
    private final String description;

    /**
     * The location's latitude
     */
    private final double latitude;

    /**
     * The location's longitude
     */
    private final double longitude;
    
    /**
     * @param description
     * @param latitude
     * @param longitude
     */
    public Location(String description, double latitude, double longitude) {
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }
    
    @Override
    public String toString() {
        return this.getDescription() + " at (" + this.getLatitude() + ", " + this.getLongitude() + ")";
    }

}