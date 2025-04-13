DROP TABLE IF EXISTS usr CASCADE;

DROP SEQUENCE IF EXISTS usr_seq CASCADE;

CREATE SEQUENCE usr_seq start with 1 increment by 1 maxvalue 999999999 cycle;

CREATE TABLE usr
(
    id                  int DEFAULT nextval('usr_seq'),
    login               character varying NOT NULL,
    password            character varying NOT NULL,
    name                character varying NOT NULL,
    role                character varying,
    creation_date       TIMESTAMP,
    last_modified_date  TIMESTAMP,
    active              BOOLEAN NOT NULL DEFAULT TRUE,
    UNIQUE (login),
    PRIMARY KEY (id)
);

CREATE INDEX usr_idx on usr (login);