DROP TABLE IF EXISTS fee CASCADE;

DROP SEQUENCE IF EXISTS fee_seq CASCADE;

CREATE SEQUENCE fee_seq start with 1 increment by 1 maxvalue 999999999 cycle;

CREATE TABLE fee
(
    id                  int DEFAULT nextval('fee_seq'),
    fee_value           NUMERIC(10,2) NOT NULL,
    creation_date       TIMESTAMP,
    last_modified_date  TIMESTAMP,
    created_by          character varying,
    updated_by          character varying,
    PRIMARY KEY (id)
);