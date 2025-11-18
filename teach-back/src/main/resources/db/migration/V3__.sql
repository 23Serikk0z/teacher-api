CREATE TABLE roles
(
    id   UUID         NOT NULL,
    name VARCHAR(255) NOT NULL,
    CONSTRAINT pk_roles PRIMARY KEY (id)
);

CREATE TABLE user_roles
(
    role_id UUID NOT NULL,
    user_id UUID NOT NULL,
    CONSTRAINT pk_user_roles PRIMARY KEY (role_id, user_id)
);

CREATE TABLE users
(
    id         UUID NOT NULL,
    username   VARCHAR(255),
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE roles
    ADD CONSTRAINT uc_roles_name UNIQUE (name);

ALTER TABLE users
    ADD CONSTRAINT uc_users_username UNIQUE (username);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_role FOREIGN KEY (role_id) REFERENCES roles (id);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_user FOREIGN KEY (user_id) REFERENCES users (id);

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

ALTER TABLE users
    ADD password VARCHAR(255);

CREATE EXTENSION IF NOT EXISTS "pgcrypto";

ALTER TABLE users
    ALTER COLUMN id SET DATA TYPE UUID USING id::uuid,
ALTER COLUMN id SET DEFAULT gen_random_uuid();