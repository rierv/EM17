package em17.logic;

import em17.entities.Location;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * Google Maps API
 */
public class GoogleMapsAPI {

    /**
     * Google Maps API Key
     */
    private static final String GOOGLE_MAPS_API_KEY = "AIzaSyA-jiouy5H5CUzJ9CAbmE3iI9ezih1TfAI";
    
    /**
     * Default constructor
     */
    public GoogleMapsAPI() {

    }
    
    /**
     * Creates the URL for the geocoding request
     * @param location The location's description
     * @return The URL of the request wrapped in an URL object. Null if location is null
     */
    private URL getGeocodingRequestURL(String location) {
        if (location != null) {
            location = location.replace(" ", "%20");
            URL geocodingURL = null;
            try {
                geocodingURL = new URL("https://maps.googleapis.com/maps/api/geocode/xml?address=" + location + "&key=" + GOOGLE_MAPS_API_KEY);
            }
            catch (MalformedURLException murle) {
                murle.printStackTrace();
            }
            finally {
                return geocodingURL;
            }
        }
        else {
            return null;
        }
    }
    
    /**
     * Creates an InputStream from a given url
     * @param url
     * @return The InputStream object. Null if url is null.
     */
    private InputStream getInputStreamFromURL(URL url) {
        if (url != null) {
            InputStream stream = null;
            try {
                stream = url.openStream();
            }
            catch(IOException ioe) {
                ioe.printStackTrace();
            }
            finally {
                return stream;
            }
        }
        else {
            return null;
        }
    }
    
    /**
     * @return a DocumentBuilder object
     */
    private DocumentBuilder getDocumentBuilder() {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;
        try {
            docBuilder = dbFactory.newDocumentBuilder();
        }
        catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
        finally {
            return docBuilder;
        }
    }
    
    /**
     * Creates a Document object from an input stream
     * @param docBuilder The document builder object
     * @param stream The stream
     * @return The Document object. Null if one between the document builder and the stream is null
     */
    private Document createDocument(DocumentBuilder docBuilder, InputStream stream) {
        Document doc = null;
        if (stream != null && docBuilder != null) {
            try {
                doc = docBuilder.parse(stream);
            }
            catch (SAXException | IOException e) {
                e.printStackTrace();
            }
            finally {
                return doc;
            }
        }
        else {
            return null;
        }
    }
    
    /**
     * Creates a Document object to read all known data about a geocoded location
     * @param locationDescription
     * @return the Document object
     */
    private Document getDocumentFromLocationDescription(String locationDescription) {
        URL geocodingURL = getGeocodingRequestURL(locationDescription);
        InputStream stream = getInputStreamFromURL(geocodingURL);
        DocumentBuilder docBuilder = getDocumentBuilder();
        Document locationDocument = createDocument(docBuilder, stream);
        return locationDocument;
    }
    
    /**
     * @param locationDescription 
     * @return
     */
    public boolean physicallyExists(String locationDescription) {
        Document locationDocument = getDocumentFromLocationDescription(locationDescription);
        if (locationDocument != null) {
            Node location = locationDocument.getElementsByTagName("location").item(0);
            if (location != null) { //Geocoding was successful
                return true;
            }
            else { //Geocoding failed
                return false;
            }
        }
        else {
            return false;
        }
    }

    /**
     * Given the location description, searches for it on Google Maps and creates a new Location object
     * whose attributes are the location description and its latitude and longitude
     * @param locationDescription 
     * @return
     */
    public Location createNewLocationFromDescription(String locationDescription) {
        Document locationDocument = getDocumentFromLocationDescription(locationDescription);
        if (locationDocument != null) {
            Node location = locationDocument.getElementsByTagName("location").item(0);
            if (location != null) { //Geocoding was successful
                double latitude = Double.parseDouble(location.getChildNodes().item(1).getTextContent());
                double longitude = Double.parseDouble(location.getChildNodes().item(3).getTextContent());
                return new Location(locationDescription, latitude, longitude);
            }
            else { //Geocoding failed
                return null;
            }
        }
        else {
            return null;
        }
    }

}