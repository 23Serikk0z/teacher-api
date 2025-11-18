-- Animal DB
CREATE DATABASE animal;
CREATE USER animal WITH ENCRYPTED PASSWORD 'animal';
GRANT ALL PRIVILEGES ON DATABASE animal TO animal;

\connect animal
GRANT ALL ON SCHEMA public TO animal;
ALTER SCHEMA public OWNER TO animal;
