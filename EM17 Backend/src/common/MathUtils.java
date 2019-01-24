/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 *
 * @author Raffolox
 */
public class MathUtils {
    
    private MathUtils() {}
    
    /**
     * @param value the float to be rounded
     * @return a float containing a float value equal to the given one rounded to two decimal places
     */
    public static float toRoundedFloat(float value) {
        return Float.parseFloat(toRoundedString(value));
    }
    
    /**
     * @param value the float to be rounded
     * @return a String containing a float value equal to the given one rounded to two decimal places
     */
    public static String toRoundedString(float value) {
        NumberFormat nf= NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        nf.setRoundingMode(RoundingMode.HALF_UP);
        String number = nf.format(value);
        number = number.replace(',', '.');
        return number;
    }
}
