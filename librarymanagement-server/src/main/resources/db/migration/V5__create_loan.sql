DROP TABLE IF EXISTS loan CASCADE;

DROP SEQUENCE IF EXISTS loan_seq CASCADE;

CREATE SEQUENCE loan_seq start with 1 increment by 1 maxvalue 999999999 cycle;

CREATE TABLE loan
(
    id                  int DEFAULT nextval('loan_seq'),
    user_id             int NOT NULL,
    book_id             int NOT NULL,
    loan_date           TIMESTAMP NOT NULL,
    due_date            TIMESTAMP NOT NULL,
    return_date         TIMESTAMP,
    returned            BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (id),
    CONSTRAINT loan_user_id_fk FOREIGN KEY (user_id) REFERENCES usr (id),
    CONSTRAINT loan_book_id_fk FOREIGN KEY (book_id) REFERENCES book (id)
);