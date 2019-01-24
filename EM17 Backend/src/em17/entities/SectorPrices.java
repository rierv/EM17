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
public class SectorPrices {
    /**
     * The sector name this price refers to
     */
    private final String sectorName;
    
    /**
     * The full price
     */
    private float fullPrice;
    
    /**
     * The reduced price
     */
    private float reducedPrice;

    /**
     * Constructor
     * @param sectorName
     * @param fullPrice
     * @param reducedPrice 
     */
    public SectorPrices(String sectorName, float fullPrice, float reducedPrice) {
        this.sectorName = sectorName;
        this.fullPrice = fullPrice;
        this.reducedPrice = reducedPrice;
    }
    
    /**
     * @return the sectorName
     */
    public String getSectorName() {
        return sectorName;
    }

    /**
     * @return the fullPrice
     */
    public float getFullPrice() {
        return fullPrice;
    }

    /**
     * @return the reducedPrice
     */
    public float getReducedPrice() {
        return reducedPrice;
    }

    /**
     * @param fullPrice the fullPrice to set
     */
    public void setFullPrice(float fullPrice) {
        this.fullPrice = fullPrice;
    }

    /**
     * @param reducedPrice the reducedPrice to set
     */
    public void setReducedPrice(float reducedPrice) {
        this.reducedPrice = reducedPrice;
    }
}
