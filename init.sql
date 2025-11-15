-- Keycloak DB
CREATE DATABASE keycloak;
CREATE USER keycloak WITH ENCRYPTED PASSWORD 'keycloak';
GRANT ALL PRIVILEGES ON DATABASE keycloak TO keycloak;

\connect keycloak
GRANT ALL ON SCHEMA public TO keycloak;
ALTER SCHEMA public OWNER TO keycloak;

-- Animal DB
CREATE DATABASE animal;
CREATE USER animal WITH ENCRYPTED PASSWORD 'animal';
GRANT ALL PRIVILEGES ON DATABASE animal TO animal;

\connect animal
GRANT ALL ON SCHEMA public TO animal;
ALTER SCHEMA public OWNER TO animal;
