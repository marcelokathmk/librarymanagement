DROP TABLE IF EXISTS book CASCADE;

DROP SEQUENCE IF EXISTS book_seq CASCADE;

CREATE SEQUENCE book_seq start with 1 increment by 1 maxvalue 999999999 cycle;

CREATE TABLE book
(
    id                  int DEFAULT nextval('book_seq'),
    title               character varying NOT NULL,
    author              character varying NOT NULL,
    publication_year    int NOT NULL,
    genre               character varying NOT NULL,
    notes               character varying NOT NULL,
    active              BOOLEAN NOT NULL DEFAULT TRUE,
    creation_date       TIMESTAMP,
    last_modified_date  TIMESTAMP,
    created_by          character varying,
    updated_by          character varying,
    PRIMARY KEY (id)
);

CREATE INDEX book_title_idx on book (title);
CREATE INDEX book_author_idx on book (author);
CREATE INDEX book_genre_idx on book (genre);
CREATE INDEX book_year_idx on book (publication_year);