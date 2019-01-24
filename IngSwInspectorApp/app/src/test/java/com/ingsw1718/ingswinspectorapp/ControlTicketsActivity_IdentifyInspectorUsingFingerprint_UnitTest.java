package com.ingsw1718.ingswinspectorapp;

import com.ingsw1718.ingswinspectorapp.exceptions.FingerprintProblemException;
import com.ingsw1718.ingswinspectorapp.logic.ControlTicketsActivity;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Raffolox on 19/02/2018.
 */

public class ControlTicketsActivity_IdentifyInspectorUsingFingerprint_UnitTest {
    private static ControlTicketsActivity cta;

    @BeforeClass
    public static void init() {
        cta = new ControlTicketsActivity();
    }

    @Test
    public void identifyInspector_null() {
        try {
            cta.identifyInspector(null);
            fail();
        }
        catch (FingerprintProblemException fpe) {
            assertTrue(true);
        }
        catch (Exception e) {
            fail();
        }
    }

    @Test
    public void identifyInspector_empty() {
        try {
            cta.identifyInspector("");
            fail();
        }
        catch (FingerprintProblemException fpe) {
            assertTrue(true);
        }
        catch (Exception e) {
            fail();
        }
    }

    @Test
    public void identifyInspector_tooShortDeviceID() {
        try {
            cta.identifyInspector("ABC123");
            fail();
        }
        catch (FingerprintProblemException fpe) {
            assertTrue(true);
        }
        catch (Exception e) {
            fail();
        }
    }

    @Test
    public void identifyInspector_tooLongDeviceID() {
        try {
            cta.identifyInspector("ABC123def456ABC123def456");
            fail();
        }
        catch (FingerprintProblemException fpe) {
            assertTrue(true);
        }
        catch (Exception e) {
            fail();
        }
    }

    @Test
    public void identifyInspector_nonHexDeviceID() {
        try {
            cta.identifyInspector("123abc123abc123m");
            fail();
        }
        catch (FingerprintProblemException fpe) {
            assertTrue(true);
        }
        catch (Exception e) {
            fail();
        }
    }

    @Test
    public void identifyInspector_specialCharactersDeviceID() {
        try {
            cta.identifyInspector("12@3!A-z");
            fail();
        }
        catch (FingerprintProblemException fpe) {
            assertTrue(true);
        }
        catch (Exception e) {
            fail();
        }
    }
}
