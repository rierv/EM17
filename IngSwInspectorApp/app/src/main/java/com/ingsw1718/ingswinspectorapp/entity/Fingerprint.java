package com.ingsw1718.ingswinspectorapp.entity;

import java.util.*;

/**
 * 
 */
public class Fingerprint extends Identificator {

    private String deviceID;

    private String id;

    /**
     * Default constructor
     */
    public Fingerprint(String id, String deviceID) {
        this.id = id;
        this.deviceID = deviceID;
    }

    @Override
    public String getIdentificatorType() {
        return Identificator.TYPE_FINGERPRINT;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public String getId() {
        return id;
    }

    public boolean equals(Object object) {
        return object != null && object instanceof Fingerprint && ((Fingerprint)(object)).deviceID.equals(this.deviceID);
    }
}