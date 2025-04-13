INSERT INTO usr(id, login, "password", "name", "role", creation_date, last_modified_date, active)
VALUES(nextval('usr_seq'), 'user_owner', 'bXlvd25lcnBhc3N3b3JkZW5jcnlwdGVk', 'User Owner', 'ROLE_OWNER', now(), now(), true);

INSERT INTO usr(id, login, "password", "name", "role", creation_date, last_modified_date, active)
VALUES(nextval('usr_seq'), 'user_client', 'bXljbGllbnRwYXNzd29yZGVuY3J5cHRlZA==', 'User Client', 'ROLE_CLIENT', now(), now(), true);