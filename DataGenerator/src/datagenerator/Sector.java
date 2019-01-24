/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datagenerator;

import java.util.List;

/**
 *
 * @author Raffolox
 */
public class Sector {
    String name;
    List<String> turnstiles;
    float fullPrice;
    float reducedPrice;
    int availableTickets;

    Sector(String name, List<String> turnstiles, float fullPrice, float reducedPrice, int availableTickets) {
        this.name = name;
        this.turnstiles = turnstiles;
        this.fullPrice = fullPrice;
        this.reducedPrice = reducedPrice;
        this.availableTickets = availableTickets;
    }
}
