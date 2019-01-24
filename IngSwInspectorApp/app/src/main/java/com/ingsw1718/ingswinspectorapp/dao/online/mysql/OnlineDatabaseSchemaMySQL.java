package com.ingsw1718.ingswinspectorapp.dao.online.mysql;

/**
 * Created by Maurizio on 19/02/2018.
 */

public final class OnlineDatabaseSchemaMySQL {
    public static final String CUSTOMER_TABLE = "customer_table";
    public static final String CUSTOMER_NAME_COLUMN = "name";
    public static final String CUSTOMER_SURNAME_COLUMN = "surname";
    public static final String CUSTOMER_EMAIL_ADDRESS_COLUMN = "emailAddress";
    public static final String CUSTOMER_BIRTH_DATE_COLUMN = "birthDate";

    public static final String LOCATION_TABLE = "location_table";
    public static final String LOCATION_LOCATION_DESCRIPTION_COLUMN = "locationDescription";
    public static final String LOCATION_LATITUDE_COLUMN = "latitude";
    public static final String LOCATION_LONGITUDE_COLUMN = "longitude";

    public static final String SECTOR_TABLE = "sector_table";
    public static final String SECTOR_SECTOR_NAME_COLUMN = "sectorName";
    public static final String SECTOR_CAPACITY_COLUMN = "capacity";
    public static final String SECTOR_LOCATION_DESCRIPTION_COLUMN = LOCATION_LOCATION_DESCRIPTION_COLUMN;

    public static final String TURNSTILE_TABLE = "turnstile_table";
    public static final String TURNSTILE_TURNSTILE_NAME_COLUMN = "turnstileName";
    public static final String TURNSTILE_LOCATION_DESCRIPTION_COLUMN = SECTOR_LOCATION_DESCRIPTION_COLUMN;
    public static final String TURNSTILE_SECTOR_NAME_COLUMN = SECTOR_SECTOR_NAME_COLUMN;

    public static final String EVENT_TABLE = "event_table";
    public static final String EVENT_ID_COLUMN = "eventId";
    public static final String EVENT_NAME_COLUMN = "name";
    public static final String EVENT_DESCRIPTION_COLUMN = "eventDescription";
    public static final String EVENT_START_DATE_TIME_COLUMN = "startDateTime";
    public static final String EVENT_END_DATE_TIME_COLUMN = "endDateTime";
    public static final String EVENT_CREATION_TIME_COLUMN = "creationTime";
    public static final String EVENT_TYPE_COLUMN = "type";
    public static final String EVENT_LOCATION_DESCRIPTION_COLUMN = LOCATION_LOCATION_DESCRIPTION_COLUMN;

    public static final String IDENTIFICATOR_TABLE = "identificator_table";
    public static final String IDENTIFICATOR_ID_COLUMN = "identificatorId";
    public static final String IDENTIFICATOR_TYPE_COLUMN = "identificatorType";
    public static final String IDENTIFICATOR_DOCUMENT_TYPE_COLUMN = "documentType";
    public static final String IDENTIFICATOR_DOCUMENT_NUMBER_COLUMN = "documentNumber";
    public static final String IDENTIFICATOR_DOCUMENT_EXPIRATION_DATE_COLUMN = "documentExpirationDate";
    public static final String IDENTIFICATOR_DEVICE_ID_COLUMN = "deviceId";

    public static final String TICKET_INSPECTOR_TABLE = "ticketInspector_table";
    public static final String TICKET_INSPECTOR_IDENTIFICATOR_ID_COLUMN = IDENTIFICATOR_ID_COLUMN;
    public static final String TICKET_INSPECTOR_NAME_COLUMN = "name";
    public static final String TICKET_INSPECTOR_SURNAME_COLUMN = "surname";
    public static final String TICKET_INSPECTOR_BIRTH_DATE_COLUMN = "birthDate";
    public static final String TICKET_INSPECTOR_ID_COLUMN = "ticketInspectorId";


    public static final String PRICES_TABLE = "prices_table";
    public static final String PRICES_PRICE_COLUMN = "price";
    public static final String PRICES_TYPE_COLUMN = "type";
    public static final String PRICES_SECTOR_NAME_COLUMN = SECTOR_SECTOR_NAME_COLUMN;
    public static final String PRICES_LOCATION_DESCRIPTION_COLUMN = SECTOR_LOCATION_DESCRIPTION_COLUMN;
    public static final String PRICES_EVENT_ID_COLUMN = EVENT_ID_COLUMN;
    public static final String PRICES_TYPE_FULL = "Full";      //value that PRICES_TYPE can has
    public static final String PRICES_TYPE_REDUCED = "Reduced";//value that PRICES_TYPE can has

    public static final String SOLD_TICKET_TABLE = "soldTicket_table";
    public static final String SOLD_TICKET_CODE_COLUMN = "code";
    public static final String SOLD_TICKET_PRICE_COLUMN = "price";
    public static final String SOLD_TICKET_TIME_OF_SALES_MILLIS_COLUMN = "timeOfSalesMillis";
    public static final String SOLD_TICKET_SECTOR_NAME_COLUMN = TURNSTILE_SECTOR_NAME_COLUMN;
    public static final String SOLD_TICKET_TURNSTILE_NAME_COLUMN = TURNSTILE_TURNSTILE_NAME_COLUMN;
    public static final String SOLD_TICKET_LOCATION_DESCRIPTION_COLUMN = TURNSTILE_LOCATION_DESCRIPTION_COLUMN;
    public static final String SOLD_TICKET_EVENT_ID_COLUMN = EVENT_ID_COLUMN;
    public static final String SOLD_TICKET_TICKET_INSPECTOR_ID_COLUMN = TICKET_INSPECTOR_ID_COLUMN;
    public static final String SOLD_TICKET_EMAIL_ADDRESS_COLUMN = CUSTOMER_EMAIL_ADDRESS_COLUMN;

    public static final String USER_TABLE = "user_table";
    public static final String USER_USERNAME_COLUMN = "username";
    public static final String USER_PASSWORD_COLUMN = "password";
    public static final String USER_EMAIL_ADDRESS_COLUMN = "emailAddress";
}

