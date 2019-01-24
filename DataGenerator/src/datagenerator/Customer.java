/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datagenerator;

import java.time.LocalDate;
import java.util.Random;

/**
 *
 * @author Raffolox
 */
class Customer {
    private static int emailAddressesGenerated = 0;
    String name;
    String surname;
    LocalDate birthDate;
    String emailAddress;
    static LocalDate startBirthDate = LocalDate.parse("1950-01-01");
    static LocalDate endBirthDate = LocalDate.parse("2010-12-31");

    Customer() {
        this.name = generateNameComponent();
        this.surname = generateNameComponent();
        int minDay = (int) startBirthDate.toEpochDay();
        int maxDay = (int) endBirthDate.toEpochDay();
        long randomDay = minDay + new Random().nextInt(maxDay - minDay);
        this.birthDate = LocalDate.ofEpochDay(randomDay);
        this.emailAddress = generateEmailAddress();
    }

    String getBirthDate() {
        return this.birthDate.getYear() + "-" + this.birthDate.getMonthValue() + "-" + this.birthDate.getDayOfMonth();
    }

    private String generateNameComponent() {
        String allowedChars = "abcdefghijklmnopqrstuvwxyz";
        Random r = new Random();
        StringBuilder nameComponent = new StringBuilder();
        nameComponent.append(allowedChars.toUpperCase().charAt(r.nextInt(allowedChars.length())));
        for (int i=0; i<r.nextInt(8)+4; i++) {
            nameComponent.append(allowedChars.charAt(r.nextInt(allowedChars.length())));
        }
        return nameComponent.toString();
    }

    private static String generateEmailAddress() {
        String allowedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random r = new Random();
        StringBuilder email = new StringBuilder();
        for (int i=0; i<6; i++) {
            email.append(allowedChars.charAt(r.nextInt(allowedChars.length())));
        }
        String finalCharacters = ("0000"+emailAddressesGenerated);
        finalCharacters = finalCharacters.substring(finalCharacters.length()-5);
        email.append(finalCharacters);
        email.append("@");
        for (int i=0; i<4; i++) {
            email.append(allowedChars.charAt(r.nextInt(allowedChars.length())));
        }
        email.append(".");
        for (int i=0; i<2; i++) {
            email.append(allowedChars.charAt(r.nextInt(allowedChars.length())));
        }
        emailAddressesGenerated++;
        return email.toString();
    }
}