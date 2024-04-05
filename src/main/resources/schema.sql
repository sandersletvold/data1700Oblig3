CREATE TABLE billett
(
    billettNr SMALLINT NOT NULL AUTO_INCREMENT,
    film VARCHAR(30),
    antall SMALLINT,
    fornavn VARCHAR(20),
    etternavn VARCHAR(25),
    telefonnr CHAR(8),
    epost VARCHAR(35),
    PRIMARY KEY (billettNr)
);