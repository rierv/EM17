/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package em17.entities;

/**
 *
 * @author Raffolox
 */
public class Sector {
    /**
     * The sector's name
     */
    private final String sectorName;
    
    /**
     * The sector's capacity
     */
    private final int capacity;
        
    /**
     * Creates a sector with a given name, capacity and turnstiles number
     * @param sectorName
     * @param capacity
     * @param turnstiles
     */
    public Sector(String sectorName, int capacity) {
        this.sectorName = sectorName;
        this.capacity = capacity;
    }
    
    /**
     * @return The sector's name
     */
    public String getSectorName() {
        return sectorName;
    }
    
    /**
     * @return The sector's capacity
     */
    public int getCapacity() {
        return capacity;
    }
    
    public boolean equals(Object o) {
        return o != null && o instanceof Sector && ((Sector)(o)).getSectorName().equals(this.getSectorName()) && ((Sector)(o)).getCapacity() == this.getCapacity();
    }
    
}
