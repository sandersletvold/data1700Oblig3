CREATE TABLE billett
(
    billettNr SMALLINT NOT NULL AUTO_INCREMENT,
    film VARCHAR(30),
    antall SMALLINT,
    fornavn VARCHAR(20),
    etternavn VARCHAR(25),
    telefonnr VARCHAR(8),
    epost VARCHAR(35),
    PRIMARY KEY (billettNr)
);

CREATE TABLE bruker
(
    id INTEGER AUTO_INCREMENT NOT NULL,
    brukernavn VARCHAR(30) NOT NULL,
    passord VARCHAR(30) NOT NULL,
    PRIMARY KEY (id)
);