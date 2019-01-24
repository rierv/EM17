{\rtf1\ansi\ansicpg1252\deff0\nouicompat\deflang1040{\fonttbl{\f0\fnil\fcharset0 Calibri;}}
{\*\generator Riched20 10.0.16299}\viewkind4\uc1 
\pard\sa200\sl276\slmult1\f0\fs22\lang16\par
# USER\ul\par
\ulnone CREATE TABLE user_table(\par
    username varchar(20) NOT NULL,\par
    password varchar(20) NOT NULL,\par
    emailAddress varchar(20) NOT NULL,\par
    constraint pk_user primary key(emailAddress)\par
);\par
# LOCATION\par
CREATE TABLE location_table (\par
  locationDescription varchar(200) NOT NULL,\par
  latitude double NOT NULL,\par
  longitude double NOT NULL,\par
  PRIMARY KEY (locationDescription)\par
);\par
# SECTOR\par
CREATE TABLE sector_table (\par
  sectorName varchar(200) NOT NULL,\par
  capacity integer NOT NULL,\par
  locationDescription varchar(200) NOT NULL,\par
  PRIMARY KEY (locationDescription,sectorName),\par
  CONSTRAINT fk_locationSector FOREIGN KEY (locationDescription) REFERENCES location_table (locationDescription)\par
);\par
# TURNSTILE\par
CREATE TABLE turnstile_table (\par
  locationDescription varchar(200) NOT NULL,\par
  sectorName varchar(200) NOT NULL,\par
  turnstileName varchar(200) NOT NULL,\par
  PRIMARY KEY (locationDescription,sectorName,turnstileName),\par
  CONSTRAINT fk_sectorTurnstile FOREIGN KEY (locationDescription, sectorName) REFERENCES sector_table (locationDescription, sectorName)\par
);\par
# EVENT\par
CREATE TABLE event_table (\par
  eventId varchar(200) NOT NULL,\par
  name varchar(200) NOT NULL,\par
  eventDescription varchar(200) NOT NULL,\par
  startDateTime dateTime NOT NULL,\par
  endDateTime dateTime NOT NULL,\par
  creationTime long NOT NULL,\par
  locationDescription varchar(200) NOT NULL,\par
  type enum('Concerto','Evento sportivo','Manifestazione', 'Non categorizzato') NOT NULL,\par
  PRIMARY KEY (eventId),\par
  CONSTRAINT fk_eventLocation FOREIGN KEY (locationDescription) REFERENCES location_table (locationDescription)\par
);\par
# PRICES\par
CREATE TABLE prices_table (\par
  locationDescription varchar(200) NOT NULL,\par
  sectorName varchar(200) NOT NULL,\par
  eventId varchar(200) NOT NULL,\par
  price float NOT NULL,\par
  type enum('Full','Reduced') NOT NULL,\par
  CONSTRAINT fk_EventPrices FOREIGN KEY (eventId) REFERENCES event_table (eventId) ON DELETE CASCADE,\par
  CONSTRAINT fk_SectorPrices FOREIGN KEY (locationDescription, sectorName) REFERENCES sector_table (locationDescription, sectorName)\par
);\par
# CUSTOMER\par
CREATE TABLE customer_table (\par
  name varchar(200) NOT NULL,\par
  surname varchar(200) NOT NULL,\par
  birthDate date NOT NULL,\par
  emailAddress varchar(200) NOT NULL,\par
  PRIMARY KEY (emailAddress)\par
);\par
# IDENTIFICATOR\par
CREATE TABLE identificator_table(\par
    identificatorId int not null auto_increment,\par
    identificatorType enum('DOCUMENT','FINGERPRINT') not null,\par
    documentType enum('ID Card', 'Passport', 'Driving License'),\par
    documentNumber varchar(20),\par
    documentExpirationDate date,\par
    deviceId varchar(20),\par
    primary key(identificatorId),\par
    constraint ck_identificator check ((identificatorType = 'DOCUMENT' and deviceId is null) or (identificatorType = 'FINGERPRINT' AND documentType is null and  documentExpirationDate is null))\par
);\par
# TICKET INSPECTOR\par
CREATE TABLE ticketInspector_table(\par
    ticketInspectorId varchar(12) NOT NULL,\par
    name varchar(20) NOT NULL,\par
    surname varchar(20) NOT NULL,\par
    birthDate date NOT NULL,\par
    identificatorId int NOT NULL,\par
    PRIMARY KEY (ticketInspectorId),\par
    CONSTRAINT fk_identificatorTicketInspector FOREIGN KEY(identificatorId) REFERENCES identificator_table (identificatorId)\par
);\par
# SOLD TICKET\par
CREATE TABLE soldTicket_table (\par
  code varchar(200) NOT NULL,\par
  price varchar(200) NOT NULL,\par
  timeOfSaleMillis long NOT NULL,\par
  emailAddress varchar(200) NOT NULL,\par
  locationDescription varchar(200) NOT NULL,\par
  sectorName varchar(200) NOT NULL,\par
  turnstileName varchar(200) NOT NULL,\par
  eventId varchar(200) NOT NULL,\par
  ticketInspectorId varchar(12),\par
  PRIMARY KEY (code),\par
  CONSTRAINT fk_customerSoldTicket FOREIGN KEY (emailAddress) REFERENCES customer_table (emailAddress),\par
  CONSTRAINT fk_eventSoldTicket FOREIGN KEY (eventId) REFERENCES event_table (eventId) ON DELETE CASCADE,\par
  CONSTRAINT fk_turnstileSoldTicket FOREIGN KEY (locationDescription, sectorName, turnstileName) REFERENCES turnstile_table (locationDescription, sectorName, turnstileName),\par
CONSTRAINT fk_ticketInspectorSoldTicket FOREIGN KEY (ticketInspectorId) REFERENCES ticketInspector_table(ticketInspectorId)\par
);\par
}
 