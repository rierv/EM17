/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datagenerator;

import com.github.javafaker.Faker;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Raffolox
 */
public class DataGenerator {    
    //NEEDED FOR THE CLASS--------------------------------------
    private Sector lastGeneratedSector = null;
    private List<Customer> customers = null;
    
    //----------------------------------------------------------
    
    private float fullTicketsPercentage = 0.67f;
    private int timeOfSaleMinRand = 1_000_000, timeOfSaleMaxRand = 1_000_000_000, soldTicketsNeeded;
    private String locationDescription, eventID;
    private List<Sector> sectors;
    
    public DataGenerator(String LOCATION_DESCRIPTION, List<Sector> SECTORS, String EVENT_ID, int SOLD_TICKETS_NEEDED) {
        this.locationDescription = LOCATION_DESCRIPTION;
        this.sectors = SECTORS;
        this.eventID = EVENT_ID;
        this.soldTicketsNeeded = SOLD_TICKETS_NEEDED;  
        customers = new ArrayList<>(SOLD_TICKETS_NEEDED);
    }
    
    public void generate() {
        System.out.println("INSERT INTO customer_table(name, surname, birthDate, emailAddress) VALUES ");
        for (int i=0; i<soldTicketsNeeded; i++) {
            Customer tmp = new Customer();
            customers.add(tmp);
            System.out.println("(\"" + tmp.name + "\", \"" + tmp.surname + "\", \"" + tmp.getBirthDate() + "\", \"" + tmp.emailAddress + "\"), ");
        }
        
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nINSERT INTO soldTicket_table(code, price, timeOfSaleMillis, emailAddress, locationDescription, sectorName, turnstileName, eventId, ticketInspectorId) VALUES");
        for (int i=0; i<soldTicketsNeeded; i++) {
            generateSector();
            System.out.println(
            "(\"" + generateTicketCode() + "\","
            + " " + generatePrice() + ","
            + " " + generateTimeOfSaleMillis() + ","
            + " \"" + customers.get(i).emailAddress + "\","
            + " \"" + locationDescription + "\","
            + " \"" + generateSectorName() + "\","
            + " \"" + generateTurnstileName() + "\","
            + " \"" + eventID + "\","
            + " null),");
        }
    }
    
    private void generateSector() {
        Random r = new Random();
        lastGeneratedSector = sectors.get(r.nextInt(sectors.size()));
        while (lastGeneratedSector == null || lastGeneratedSector.availableTickets == 0) {
            lastGeneratedSector = sectors.get(r.nextInt(sectors.size()));
        }
        lastGeneratedSector.availableTickets--;
    }
    
    private String generateTicketCode() {
        String allowedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random r = new Random();
        StringBuilder code = new StringBuilder();
        for (int i=0; i<12; i++) {
            code.append(allowedChars.charAt(r.nextInt(allowedChars.length())));
        }
        return code.toString();
    }

    private float generatePrice() {
        Random r = new Random();
        if (r.nextFloat() <= fullTicketsPercentage) {
            return lastGeneratedSector.fullPrice;
        }
        else {
            return lastGeneratedSector.reducedPrice;
        }
    }

    private long generateTimeOfSaleMillis() {
        Random r = new Random();
        return r.nextInt(timeOfSaleMaxRand) + timeOfSaleMinRand;
    }

    private String generateSectorName() {
        return lastGeneratedSector.name;
    }

    private String generateTurnstileName() {
        Random r = new Random();
        return lastGeneratedSector.turnstiles.get(r.nextInt(lastGeneratedSector.turnstiles.size()));
    }
    
}
