ALTER TABLE roles
    ADD code VARCHAR(255);

ALTER TABLE roles
    ADD kk_name VARCHAR(255);

ALTER TABLE roles
    ADD ru_name VARCHAR(255);

ALTER TABLE roles
    ADD CONSTRAINT uc_roles_code UNIQUE (code);

ALTER TABLE roles
    DROP COLUMN name;