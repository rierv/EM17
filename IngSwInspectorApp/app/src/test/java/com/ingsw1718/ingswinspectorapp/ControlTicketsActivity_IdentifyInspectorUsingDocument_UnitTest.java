package com.ingsw1718.ingswinspectorapp;

import com.ingsw1718.ingswinspectorapp.entity.DocumentTypeEnum;
import com.ingsw1718.ingswinspectorapp.exceptions.DocumentExpiredException;
import com.ingsw1718.ingswinspectorapp.exceptions.IncompleteDataException;
import com.ingsw1718.ingswinspectorapp.logic.ControlTicketsActivity;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by Raffolox on 19/02/2018.
 */

public class ControlTicketsActivity_IdentifyInspectorUsingDocument_UnitTest {
    private static ControlTicketsActivity cta;

    @BeforeClass
    public static void init() {
        cta = new ControlTicketsActivity();
    }

    @Test
    public void identifyInspector_nullType_nullNumber_nullExpiration() {
        try {
            cta.identifyInspector(null, null, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Exception e) {
            fail();
        }
    }

    @Test
    public void identifyInspector_nullType_nullNumber_expiresInFuture() {
        try {
            Date tomorrow = new Date();
            tomorrow.setTime(tomorrow.getTime() + 86400000); //86400000 = millis in a day = 1000*60*60*24
            cta.identifyInspector(null, null, tomorrow);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Exception e) {
            fail();
        }
    }

    @Test
    public void identifyInspector_nullType_nullNumber_expiresToday() {
        try {
            cta.identifyInspector(null, null, new Date());
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Exception e) {
            fail();
        }
    }

    @Test
    public void identifyInspector_nullType_nullNumber_alreadyExpired() {
        try {
            Date yesterday = new Date();
            yesterday.setTime(yesterday.getTime() - 86400000); //86400000 = millis in a day = 1000*60*60*24
            cta.identifyInspector(null, null, yesterday);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Exception e) {
            fail();
        }
    }

    @Test
    public void identifyInspector_nullType_emptyNumber_nullExpiration() {
        try {
            cta.identifyInspector(null, "", null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Exception e) {
            fail();
        }
    }

    @Test
    public void identifyInspector_nullType_emptyNumber_expiresInFuture() {
        try {
            Date tomorrow = new Date();
            tomorrow.setTime(tomorrow.getTime() + 86400000); //86400000 = millis in a day = 1000*60*60*24
            cta.identifyInspector(null, "", tomorrow);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Exception e) {
            fail();
        }
    }

    @Test
    public void identifyInspector_nullType_emptyNumber_expiresToday() {
        try {
            cta.identifyInspector(null, "", new Date());
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Exception e) {
            fail();
        }
    }

    @Test
    public void identifyInspector_nullType_emptyNumber_alreadyExpired() {
        try {
            Date yesterday = new Date();
            yesterday.setTime(yesterday.getTime() - 86400000); //86400000 = millis in a day = 1000*60*60*24
            cta.identifyInspector(null, "", yesterday);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Exception e) {
            fail();
        }
    }

    @Test
    public void identifyInspector_nullType_filledNumber_nullExpiration() {
        try {
            cta.identifyInspector(null, "ABC", null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Exception e) {
            fail();
        }
    }

    @Test
    public void identifyInspector_nullType_filledNumber_expiresInFuture() {
        try {
            Date tomorrow = new Date();
            tomorrow.setTime(tomorrow.getTime() + 86400000); //86400000 = millis in a day = 1000*60*60*24
            cta.identifyInspector(null, "ABC", tomorrow);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Exception e) {
            fail();
        }
    }

    @Test
    public void identifyInspector_nullType_filledNumber_expiresToday() {
        try {
            cta.identifyInspector(null, "ABC", new Date());
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Exception e) {
            fail();
        }
    }

    @Test
    public void identifyInspector_nullType_filledNumber_alreadyExpired() {
        try {
            Date yesterday = new Date();
            yesterday.setTime(yesterday.getTime() - 86400000); //86400000 = millis in a day = 1000*60*60*24
            cta.identifyInspector(null, "ABC", yesterday);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Exception e) {
            fail();
        }
    }

    @Test
    public void identifyInspector_existingType_nullNumber_nullExpiration() {
        try {
            cta.identifyInspector(DocumentTypeEnum.ID_CARD, null, null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Exception e) {
            fail();
        }
    }

    @Test
    public void identifyInspector_existingType_nullNumber_expiresInFuture() {
        try {
            Date tomorrow = new Date();
            tomorrow.setTime(tomorrow.getTime() + 86400000); //86400000 = millis in a day = 1000*60*60*24
            cta.identifyInspector(DocumentTypeEnum.ID_CARD, null, tomorrow);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Exception e) {
            fail();
        }
    }

    @Test
    public void identifyInspector_existingType_nullNumber_expiresToday() {
        try {
            cta.identifyInspector(DocumentTypeEnum.ID_CARD, null, new Date());
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Exception e) {
            fail();
        }
    }

    @Test
    public void identifyInspector_existingType_nullNumber_alreadyExpired() {
        try {
            Date yesterday = new Date();
            yesterday.setTime(yesterday.getTime() - 86400000); //86400000 = millis in a day = 1000*60*60*24
            cta.identifyInspector(DocumentTypeEnum.ID_CARD, null, yesterday);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Exception e) {
            fail();
        }
    }

    @Test
    public void identifyInspector_existingType_emptyNumber_nullExpiration() {
        try {
            cta.identifyInspector(DocumentTypeEnum.ID_CARD, "", null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Exception e) {
            fail();
        }
    }

    @Test
    public void identifyInspector_existingType_emptyNumber_expiresInFuture() {
        try {
            Date tomorrow = new Date();
            tomorrow.setTime(tomorrow.getTime() + 86400000); //86400000 = millis in a day = 1000*60*60*24
            cta.identifyInspector(DocumentTypeEnum.ID_CARD, "", tomorrow);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Exception e) {
            fail();
        }
    }

    @Test
    public void identifyInspector_existingType_emptyNumber_expiresToday() {
        try {
            cta.identifyInspector(DocumentTypeEnum.ID_CARD, "", new Date());
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Exception e) {
            fail();
        }
    }

    @Test
    public void identifyInspector_existingType_emptyNumber_alreadyExpired() {
        try {
            Date yesterday = new Date();
            yesterday.setTime(yesterday.getTime() - 86400000); //86400000 = millis in a day = 1000*60*60*24
            cta.identifyInspector(DocumentTypeEnum.ID_CARD, "", yesterday);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Exception e) {
            fail();
        }
    }

    @Test
    public void identifyInspector_existingType_filledNumber_nullExpiration() {
        try {
            cta.identifyInspector(DocumentTypeEnum.ID_CARD, "ABC", null);
            fail();
        }
        catch (IncompleteDataException ide) {
            assertTrue(true);
        }
        catch (Exception e) {
            fail();
        }
    }

    @Test
    public void identifyInspector_existingType_filledNumber_alreadyExpired() {
        try {
            Date yesterday = new Date();
            yesterday.setTime(yesterday.getTime() - 86400000); //86400000 = millis in a day = 1000*60*60*24
            cta.identifyInspector(DocumentTypeEnum.ID_CARD, "ABC", yesterday);
            fail();
        }
        catch (DocumentExpiredException dee) {
            assertTrue(true);
        }
        catch (Exception e) {
            fail();
        }
    }
}
