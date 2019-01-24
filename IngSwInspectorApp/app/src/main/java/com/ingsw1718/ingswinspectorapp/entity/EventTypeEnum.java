/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingsw1718.ingswinspectorapp.entity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Raffolox
 */
public class EventTypeEnum {
    
    public static class EventType {
        private final String eventType;
        
        private EventType(String eventType) {
            this.eventType = eventType;
        }
        
        @Override
        public String toString() {
            return eventType;
        }

        @Override
        public boolean equals(Object object) {
            return object != null && object instanceof EventType && ((EventType)(object)).toString().equals(this.toString());
        }
    }
    
    public static final EventType
            CONCERTO = new EventType("Concerto"),
            EVENTO_SPORTIVO = new EventType("Evento sportivo"),
            MANIFESTAZIONE = new EventType("Manifestazione"),
            NON_CATEGORIZZATO = new EventType("Non categorizzato");
    
    public static List<String> values() {
        ArrayList values = new ArrayList<>();
        values.add(CONCERTO.toString());
        values.add(EVENTO_SPORTIVO.toString());
        values.add(MANIFESTAZIONE.toString());
        values.add(NON_CATEGORIZZATO.toString());
        return values;
    }
    
    public static EventType getEventTypeFromString(String eventType) {
        if (eventType == null || eventType.isEmpty() || eventType.equals(NON_CATEGORIZZATO.toString())) {
            return NON_CATEGORIZZATO;
        }
        else if (eventType.equals(CONCERTO.toString())) {
            return CONCERTO;
        }
        else if (eventType.equals(EVENTO_SPORTIVO.toString())) {
            return EVENTO_SPORTIVO;
        }
        else if (eventType.equals(MANIFESTAZIONE.toString())) {
            return MANIFESTAZIONE;
        }
        else {
            return null;
        }
    }
}
