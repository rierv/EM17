/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datagenerator;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Raffolox
 */
public class Interface {
    
    //PARAMETERS------------------------------------------------
    private static final String LOCATION_DESCRIPTION = "Stadio San Paolo, Napoli";
    private static final List<Sector> SECTORS = new ArrayList<>();
    private static final String EVENT_ID = "ciPRsGLq1dJZ";
    private static final int SOLD_TICKETS_NEEDED = 25000;
        
    static {
        List<String> turnstiles1 = new ArrayList<>();
        turnstiles1.add("Tornello 1");
        turnstiles1.add("Tornello 2");
        turnstiles1.add("Tornello 3");
        SECTORS.add(new Sector("Curva A", turnstiles1, 33.33f, 20.25f, 10000));
        List<String> turnstiles2 = new ArrayList<>();
        turnstiles2.add("Tornello 1");
        turnstiles2.add("Tornello 2");
        SECTORS.add(new Sector("Curva B", turnstiles2, 236.54f, 150.06f, 15000));
        List<String> turnstiles3 = new ArrayList<>();
        turnstiles3.add("Tornello 1");
        turnstiles3.add("Tornello 2");
        turnstiles3.add("Tornello 3");
        SECTORS.add(new Sector("Distinti", turnstiles3, 154.64f, 84.77f, 20000));
        List<String> turnstiles4 = new ArrayList<>();
        turnstiles4.add("Tornello 1");
        SECTORS.add(new Sector("Ospiti", turnstiles4, 196.45f, 52.68f, 3000));
    }
    //-----------------------------------------------------------
    
    public static void main(String...s) {
        new DataGenerator(LOCATION_DESCRIPTION, SECTORS, EVENT_ID, SOLD_TICKETS_NEEDED).generate();
    }
}
