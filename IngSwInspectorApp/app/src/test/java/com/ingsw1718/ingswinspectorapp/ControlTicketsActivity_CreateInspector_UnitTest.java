package com.ingsw1718.ingswinspectorapp;

import com.ingsw1718.ingswinspectorapp.entity.Document;
import com.ingsw1718.ingswinspectorapp.entity.DocumentTypeEnum;
import com.ingsw1718.ingswinspectorapp.entity.Identificator;
import com.ingsw1718.ingswinspectorapp.exceptions.IncompleteDataException;
import com.ingsw1718.ingswinspectorapp.exceptions.InvalidBirthDateException;
import com.ingsw1718.ingswinspectorapp.logic.ControlTicketsActivity;

import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by Raffolox on 19/02/2018.
 */

public class ControlTicketsActivity_CreateInspector_UnitTest {
    private static ControlTicketsActivity cta;
    private static String mockIdentificatorId;
    private static Method createInspectorMethod;
    private static Date todayDate = new Date();
    private static Date tooEarlyDate = new Date(new Date().getTime() - 315_576_000_000L); //315576000000 = millis in 10 years = 1000*60*60*24*365.25*10
    private static Date tooLateDate = new Date(new Date().getTime() - 2_524_608_000_000L); //2524608000000 = millis in 80 years = 1000*60*60*24*365.25*80
    private static Date futureDate = new Date(new Date().getTime() + 31_557_600_000L); //31557600000 = millis in 1 years = 1000*60*60*24*365.25
    private static Date goodDate = new Date(new Date().getTime() - 946_728_000_000L); //946728000000 = millis in 30 years = 1000*60*60*24*365.25*30
    private static String goodName = "Raffaele";
    private static String goodSurname = "Sorrentino";

    @BeforeClass
    public static void init() {
        cta = new ControlTicketsActivity();
        mockIdentificatorId = "ABCD1234ab56";
        try {
            createInspectorMethod = cta.getClass().getDeclaredMethod("createInspector", String.class, String.class, Date.class, String.class);
            createInspectorMethod.setAccessible(true);
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private void createInspector(String name, String surname, Date dateOfBirth, String identificatorId) throws IncompleteDataException, InvalidBirthDateException {
        try {
            createInspectorMethod.invoke(cta, name, surname, dateOfBirth, identificatorId);
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (InvocationTargetException e) {
            try {
                throw e.getCause();
            }
            catch (IncompleteDataException ide) {
                throw ide;
            }
            catch (InvalidBirthDateException ibde) {
                throw ibde;
            }
            catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    @Test
    public void createInspector_nullName_nullSurname_nullBirthDate_nullIdentificator() {
        try {
            createInspector(null, null, null, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_nullName_nullSurname_nullBirthDate_goodIdentificator() {
        try {
            createInspector(null, null, null, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_nullName_nullSurname_todayBirthDate_nullIdentificator() {
        try {
            createInspector(null, null, todayDate, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_nullName_nullSurname_todayBirthDate_goodIdentificator() {
        try {
            createInspector(null, null, todayDate, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_nullName_nullSurname_tooEarlyBirthDate_nullIdentificator() {
        try {
            createInspector(null, null, tooEarlyDate, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_nullName_nullSurname_tooEarlyBirthDate_goodIdentificator() {
        try {
            createInspector(null, null, tooEarlyDate, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_nullName_nullSurname_tooLateBirthDate_nullIdentificator() {
        try {
            createInspector(null, null, tooLateDate, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_nullName_nullSurname_tooLateBirthDate_goodIdentificator() {
        try {
            createInspector(null, null, tooLateDate, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_nullName_nullSurname_futureBirthDate_nullIdentificator() {
        try {
            createInspector(null, null, futureDate, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_nullName_nullSurname_futureBirthDate_goodIdentificator() {
        try {
            createInspector(null, null, futureDate, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_nullName_nullSurname_goodBirthDate_nullIdentificator() {
        try {
            createInspector(null, null, goodDate, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_nullName_nullSurname_goodBirthDate_goodIdentificator() {
        try {
            createInspector(null, null, goodDate, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_nullName_emptySurname_nullBirthDate_nullIdentificator() {
        try {
            createInspector(null, "", null, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_nullName_emptySurname_nullBirthDate_goodIdentificator() {
        try {
            createInspector(null, "", null, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_nullName_emptySurname_todayBirthDate_nullIdentificator() {
        try {
            createInspector(null, "", todayDate, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_nullName_emptySurname_todayBirthDate_goodIdentificator() {
        try {
            createInspector(null, "", todayDate, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_nullName_emptySurname_tooEarlyBirthDate_nullIdentificator() {
        try {
            createInspector(null, "", tooEarlyDate, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_nullName_emptySurname_tooEarlyBirthDate_goodIdentificator() {
        try {
            createInspector(null, "", tooEarlyDate, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_nullName_emptySurname_tooLateBirthDate_nullIdentificator() {
        try {
            createInspector(null, "", tooLateDate, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_nullName_emptySurname_tooLateBirthDate_goodIdentificator() {
        try {
            createInspector(null, "", tooLateDate, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_nullName_emptySurname_futureBirthDate_nullIdentificator() {
        try {
            createInspector(null, "", futureDate, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_nullName_emptySurname_futureBirthDate_goodIdentificator() {
        try {
            createInspector(null, "", futureDate, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_nullName_emptySurname_goodBirthDate_nullIdentificator() {
        try {
            createInspector(null, "", goodDate, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_nullName_emptySurname_goodBirthDate_goodIdentificator() {
        try {
            createInspector(null, "", goodDate, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_nullName_goodSurname_nullBirthDate_nullIdentificator() {
        try {
            createInspector(null, goodSurname, null, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_nullName_goodSurname_nullBirthDate_goodIdentificator() {
        try {
            createInspector(null, goodSurname, null, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_nullName_goodSurname_todayBirthDate_nullIdentificator() {
        try {
            createInspector(null, goodSurname, todayDate, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_nullName_goodSurname_todayBirthDate_goodIdentificator() {
        try {
            createInspector(null, goodSurname, todayDate, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_nullName_goodSurname_tooEarlyBirthDate_nullIdentificator() {
        try {
            createInspector(null, goodSurname, tooEarlyDate, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_nullName_goodSurname_tooEarlyBirthDate_goodIdentificator() {
        try {
            createInspector(null, goodSurname, tooEarlyDate, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_nullName_goodSurname_tooLateBirthDate_nullIdentificator() {
        try {
            createInspector(null, goodSurname, tooLateDate, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_nullName_goodSurname_tooLateBirthDate_goodIdentificator() {
        try {
            createInspector(null, goodSurname, tooLateDate, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_nullName_goodSurname_futureBirthDate_nullIdentificator() {
        try {
            createInspector(null, goodSurname, futureDate, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_nullName_goodSurname_futureBirthDate_goodIdentificator() {
        try {
            createInspector(null, goodSurname, futureDate, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_nullName_goodSurname_goodBirthDate_nullIdentificator() {
        try {
            createInspector(null, goodSurname, goodDate, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_nullName_goodSurname_goodBirthDate_goodIdentificator() {
        try {
            createInspector(null, goodSurname, goodDate, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_emptyName_nullSurname_nullBirthDate_nullIdentificator() {
        try {
            createInspector("", null, null, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_emptyName_nullSurname_nullBirthDate_goodIdentificator() {
        try {
            createInspector("", null, null, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_emptyName_nullSurname_todayBirthDate_nullIdentificator() {
        try {
            createInspector("", null, todayDate, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_emptyName_nullSurname_todayBirthDate_goodIdentificator() {
        try {
            createInspector("", null, todayDate, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_emptyName_nullSurname_tooEarlyBirthDate_nullIdentificator() {
        try {
            createInspector("", null, tooEarlyDate, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_emptyName_nullSurname_tooEarlyBirthDate_goodIdentificator() {
        try {
            createInspector("", null, tooEarlyDate, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_emptyName_nullSurname_tooLateBirthDate_nullIdentificator() {
        try {
            createInspector("", null, tooLateDate, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_emptyName_nullSurname_tooLateBirthDate_goodIdentificator() {
        try {
            createInspector("", null, tooLateDate, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_emptyName_nullSurname_futureBirthDate_nullIdentificator() {
        try {
            createInspector("", null, futureDate, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_emptyName_nullSurname_futureBirthDate_goodIdentificator() {
        try {
            createInspector("", null, futureDate, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_emptyName_nullSurname_goodBirthDate_nullIdentificator() {
        try {
            createInspector("", null, goodDate, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_emptyName_nullSurname_goodBirthDate_goodIdentificator() {
        try {
            createInspector("", null, goodDate, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_emptyName_emptySurname_nullBirthDate_nullIdentificator() {
        try {
            createInspector("", "", null, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_emptyName_emptySurname_nullBirthDate_goodIdentificator() {
        try {
            createInspector("", "", null, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_emptyName_emptySurname_todayBirthDate_nullIdentificator() {
        try {
            createInspector("", "", todayDate, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_emptyName_emptySurname_todayBirthDate_goodIdentificator() {
        try {
            createInspector("", "", todayDate, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_emptyName_emptySurname_tooEarlyBirthDate_nullIdentificator() {
        try {
            createInspector("", "", tooEarlyDate, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_emptyName_emptySurname_tooEarlyBirthDate_goodIdentificator() {
        try {
            createInspector("", "", tooEarlyDate, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_emptyName_emptySurname_tooLateBirthDate_nullIdentificator() {
        try {
            createInspector("", "", tooLateDate, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_emptyName_emptySurname_tooLateBirthDate_goodIdentificator() {
        try {
            createInspector("", "", tooLateDate, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_emptyName_emptySurname_futureBirthDate_nullIdentificator() {
        try {
            createInspector("", "", futureDate, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_emptyName_emptySurname_futureBirthDate_goodIdentificator() {
        try {
            createInspector("", "", futureDate, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_emptyName_emptySurname_goodBirthDate_nullIdentificator() {
        try {
            createInspector("", "", goodDate, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_emptyName_emptySurname_goodBirthDate_goodIdentificator() {
        try {
            createInspector("", "", goodDate, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_emptyName_goodSurname_nullBirthDate_nullIdentificator() {
        try {
            createInspector("", goodSurname, null, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_emptyName_goodSurname_nullBirthDate_goodIdentificator() {
        try {
            createInspector("", goodSurname, null, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_emptyName_goodSurname_todayBirthDate_nullIdentificator() {
        try {
            createInspector("", goodSurname, todayDate, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_emptyName_goodSurname_todayBirthDate_goodIdentificator() {
        try {
            createInspector("", goodSurname, todayDate, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_emptyName_goodSurname_tooEarlyBirthDate_nullIdentificator() {
        try {
            createInspector("", goodSurname, tooEarlyDate, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_emptyName_goodSurname_tooEarlyBirthDate_goodIdentificator() {
        try {
            createInspector("", goodSurname, tooEarlyDate, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_emptyName_goodSurname_tooLateBirthDate_nullIdentificator() {
        try {
            createInspector("", goodSurname, tooLateDate, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_emptyName_goodSurname_tooLateBirthDate_goodIdentificator() {
        try {
            createInspector("", goodSurname, tooLateDate, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_emptyName_goodSurname_futureBirthDate_nullIdentificator() {
        try {
            createInspector("", goodSurname, futureDate, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_emptyName_goodSurname_futureBirthDate_goodIdentificator() {
        try {
            createInspector("", goodSurname, futureDate, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_emptyName_goodSurname_goodBirthDate_nullIdentificator() {
        try {
            createInspector("", goodSurname, goodDate, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_emptyName_goodSurname_goodBirthDate_goodIdentificator() {
        try {
            createInspector("", goodSurname, goodDate, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_goodName_nullSurname_nullBirthDate_nullIdentificator() {
        try {
            createInspector(goodName, null, null, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_goodName_nullSurname_nullBirthDate_goodIdentificator() {
        try {
            createInspector(goodName, null, null, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_goodName_nullSurname_todayBirthDate_nullIdentificator() {
        try {
            createInspector(goodName, null, todayDate, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_goodName_nullSurname_todayBirthDate_goodIdentificator() {
        try {
            createInspector(goodName, null, todayDate, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_goodName_nullSurname_tooEarlyBirthDate_nullIdentificator() {
        try {
            createInspector(goodName, null, tooEarlyDate, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_goodName_nullSurname_tooEarlyBirthDate_goodIdentificator() {
        try {
            createInspector(goodName, null, tooEarlyDate, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_goodName_nullSurname_tooLateBirthDate_nullIdentificator() {
        try {
            createInspector(goodName, null, tooLateDate, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_goodName_nullSurname_tooLateBirthDate_goodIdentificator() {
        try {
            createInspector(goodName, null, tooLateDate, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_goodName_nullSurname_futureBirthDate_nullIdentificator() {
        try {
            createInspector(goodName, null, futureDate, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_goodName_nullSurname_futureBirthDate_goodIdentificator() {
        try {
            createInspector(goodName, null, futureDate, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_goodName_nullSurname_goodBirthDate_nullIdentificator() {
        try {
            createInspector(goodName, null, goodDate, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_goodName_nullSurname_goodBirthDate_goodIdentificator() {
        try {
            createInspector(goodName, null, goodDate, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_goodName_emptySurname_nullBirthDate_nullIdentificator() {
        try {
            createInspector(goodName, "", null, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_goodName_emptySurname_nullBirthDate_goodIdentificator() {
        try {
            createInspector(goodName, "", null, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_goodName_emptySurname_todayBirthDate_nullIdentificator() {
        try {
            createInspector(goodName, "", todayDate, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_goodName_emptySurname_todayBirthDate_goodIdentificator() {
        try {
            createInspector(goodName, "", todayDate, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_goodName_emptySurname_tooEarlyBirthDate_nullIdentificator() {
        try {
            createInspector(goodName, "", tooEarlyDate, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_goodName_emptySurname_tooEarlyBirthDate_goodIdentificator() {
        try {
            createInspector(goodName, "", tooEarlyDate, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_goodName_emptySurname_tooLateBirthDate_nullIdentificator() {
        try {
            createInspector(goodName, "", tooLateDate, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_goodName_emptySurname_tooLateBirthDate_goodIdentificator() {
        try {
            createInspector(goodName, "", tooLateDate, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_goodName_emptySurname_futureBirthDate_nullIdentificator() {
        try {
            createInspector(goodName, "", futureDate, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_goodName_emptySurname_futureBirthDate_goodIdentificator() {
        try {
            createInspector(goodName, "", futureDate, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_goodName_emptySurname_goodBirthDate_nullIdentificator() {
        try {
            createInspector(goodName, "", goodDate, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_goodName_emptySurname_goodBirthDate_goodIdentificator() {
        try {
            createInspector(goodName, "", goodDate, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_goodName_goodSurname_nullBirthDate_nullIdentificator() {
        try {
            createInspector(goodName, goodSurname, null, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_goodName_goodSurname_nullBirthDate_goodIdentificator() {
        try {
            createInspector(goodName, goodSurname, null, mockIdentificatorId);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_goodName_goodSurname_todayBirthDate_nullIdentificator() {
        try {
            createInspector(goodName, goodSurname, todayDate, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_goodName_goodSurname_todayBirthDate_goodIdentificator() {
        try {
            createInspector(goodName, goodSurname, todayDate, mockIdentificatorId);
            fail();
        }
        catch (InvalidBirthDateException ibde) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_goodName_goodSurname_tooEarlyBirthDate_nullIdentificator() {
        try {
            createInspector(goodName, goodSurname, tooEarlyDate, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_goodName_goodSurname_tooEarlyBirthDate_goodIdentificator() {
        try {
            createInspector(goodName, goodSurname, tooEarlyDate, mockIdentificatorId);
            fail();
        }
        catch (InvalidBirthDateException ibde) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_goodName_goodSurname_tooLateBirthDate_nullIdentificator() {
        try {
            createInspector(goodName, goodSurname, tooLateDate, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_goodName_goodSurname_tooLateBirthDate_goodIdentificator() {
        try {
            createInspector(goodName, goodSurname, tooLateDate, mockIdentificatorId);
            fail();
        }
        catch (InvalidBirthDateException ibde) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_goodName_goodSurname_futureBirthDate_nullIdentificator() {
        try {
            createInspector(goodName, goodSurname, futureDate, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_goodName_goodSurname_futureBirthDate_goodIdentificator() {
        try {
            createInspector(goodName, goodSurname, futureDate, mockIdentificatorId);
            fail();
        }
        catch (InvalidBirthDateException ibde) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }

    @Test
    public void createInspector_goodName_goodSurname_goodBirthDate_nullIdentificator() {
        try {
            createInspector(goodName, goodSurname, goodDate, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Throwable t) { t.printStackTrace();
            fail();
        }
    }
}
