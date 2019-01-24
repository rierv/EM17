package com.ingsw1718.ingswinspectorapp.entity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Raffolox
 */
public class DocumentTypeEnum {

    public static class DocumentType {
        private final String documentType;

        private DocumentType(String documentType) {
            this.documentType = documentType;
        }

        @Override
        public String toString() {
            return documentType;
        }
    }

    public static final DocumentType
            ID_CARD = new DocumentType("ID Card"),
            PASSPORT = new DocumentType("Passport"),
            DRIVING_LICENSE = new DocumentType("Driving License");

    public static String[] values() {
        return new String[] {ID_CARD.toString(), PASSPORT.toString(), DRIVING_LICENSE.toString()};
    }

    public static DocumentType getDocumentTypeFromString(String documentType) {
        if (documentType.equals(ID_CARD.toString())) {
            return ID_CARD;
        }
        else if (documentType.equals(PASSPORT.toString())) {
            return PASSPORT;
        }
        else if (documentType.equals(DRIVING_LICENSE.toString())) {
            return DRIVING_LICENSE;
        }
        else {
            return null;
        }
    }
}
