package com.ingsw1718.ingswinspectorapp.entity;

import java.util.*;

/**
 * 
 */
public abstract class Identificator {
    public static final String TYPE_DOCUMENT = "DOCUMENT";
    public static final String TYPE_FINGERPRINT = "FINGERPRINT";

    public abstract String getIdentificatorType();

    public abstract String getId();

    public abstract boolean equals(Object object);
}