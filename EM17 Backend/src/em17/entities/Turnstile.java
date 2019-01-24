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
public class Turnstile {
    /**
     * The turnstile's name
     */
    private final String turnstileName;
    
    /**
     * Creates a turnstile with a given name
     * @param turnstileName
     */
    public Turnstile(String turnstileName) {
        this.turnstileName = turnstileName;
    }

    /**
     * @return the turnstileName
     */
    public String getTurnstileName() {
        return turnstileName;
    }
    
    public boolean equals(Object o) {
        return o != null && o instanceof Turnstile && ((Turnstile)(o)).getTurnstileName().equals(this.getTurnstileName());
    }
}
