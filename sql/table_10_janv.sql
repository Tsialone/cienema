DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

CREATE TABLE utilisateur(
   id_utilisateur SERIAL,
   nom VARCHAR(255) ,
   email VARCHAR(255)  NOT NULL,
   passe VARCHAR(255) ,
   PRIMARY KEY(id_utilisateur),
   UNIQUE(email)
);

CREATE TABLE role(
   id_role SERIAL,
   libelle VARCHAR(255) ,
   PRIMARY KEY(id_role)
);

CREATE TABLE film(
   id_film SERIAL,
   nom VARCHAR(255)  NOT NULL,
   duree TIME NOT NULL,
   PRIMARY KEY(id_film)
);

CREATE TABLE salle(
   id_salle SERIAL,
   nom VARCHAR(50) ,
   PRIMARY KEY(id_salle)
);

CREATE TABLE bloc(
   id_bloc SERIAL,
   libelle VARCHAR(50) ,
   prix NUMERIC(15,2)   NOT NULL,
   PRIMARY KEY(id_bloc)
);

CREATE TABLE seance(
   id_seance INTEGER,
   date_time_debut TIMESTAMP NOT NULL,
   id_salle INTEGER NOT NULL,
   id_film INTEGER NOT NULL,
   PRIMARY KEY(id_seance),
   UNIQUE(id_salle),
   FOREIGN KEY(id_salle) REFERENCES salle(id_salle),
   FOREIGN KEY(id_film) REFERENCES film(id_film)
);

CREATE TABLE caisse(
   id_caisse SERIAL,
   nom VARCHAR(50) ,
   PRIMARY KEY(id_caisse)
);

CREATE TABLE mouvement_caisse(
   id_mc SERIAL,
   debit NUMERIC(15,2)   NOT NULL,
   credit NUMERIC(15,2)   NOT NULL,
   created TIMESTAMP NOT NULL,
   id_caisse INTEGER NOT NULL,
   PRIMARY KEY(id_mc),
   FOREIGN KEY(id_caisse) REFERENCES caisse(id_caisse)
);

CREATE TABLE genre(
   id_genre SERIAL,
   libelle VARCHAR(50) ,
   PRIMARY KEY(id_genre)
);

CREATE TABLE client(
   id_client INTEGER,
   id_genre INTEGER NOT NULL,
   id_utilisateur INTEGER NOT NULL,
   PRIMARY KEY(id_client),
   UNIQUE(id_utilisateur),
   FOREIGN KEY(id_genre) REFERENCES genre(id_genre),
   FOREIGN KEY(id_utilisateur) REFERENCES utilisateur(id_utilisateur)
);

CREATE TABLE place(
   id_place SERIAL,
   numero INTEGER NOT NULL,
   id_bloc INTEGER NOT NULL,
   id_salle INTEGER NOT NULL,
   PRIMARY KEY(id_place),
   UNIQUE(numero),
   FOREIGN KEY(id_bloc) REFERENCES bloc(id_bloc),
   FOREIGN KEY(id_salle) REFERENCES salle(id_salle)
);

CREATE TABLE ticket(
   id_ticket SERIAL,
   date_prevue TIMESTAMP NOT NULL,
   code_groupe VARCHAR(50)  NOT NULL,
   id_place INTEGER NOT NULL,
   id_film INTEGER NOT NULL,
   id_client INTEGER NOT NULL,
   PRIMARY KEY(id_ticket),
   FOREIGN KEY(id_place) REFERENCES place(id_place),
   FOREIGN KEY(id_film) REFERENCES film(id_film),
   FOREIGN KEY(id_client) REFERENCES client(id_client)
);

CREATE TABLE paiement(
   id_paiement SERIAL,
   created TIMESTAMP,
   montant NUMERIC(15,2)   NOT NULL,
   id_ticket INTEGER NOT NULL,
   id_mc INTEGER NOT NULL,
   PRIMARY KEY(id_paiement),
   UNIQUE(id_mc),
   FOREIGN KEY(id_ticket) REFERENCES ticket(id_ticket),
   FOREIGN KEY(id_mc) REFERENCES mouvement_caisse(id_mc)
);

CREATE TABLE utilisateur_role(
   id_utilisateur INTEGER,
   id_role INTEGER,
   PRIMARY KEY(id_utilisateur, id_role),
   FOREIGN KEY(id_utilisateur) REFERENCES utilisateur(id_utilisateur),
   FOREIGN KEY(id_role) REFERENCES role(id_role)
);
CREATE SEQUENCE groupe_seq START WITH 1 INCREMENT BY 1;

-- Minimal seed data

-- Films (2 entries)
INSERT INTO film (nom, duree) VALUES
  ('Inception', '02:28:00'),
  ('Interstellar', '02:49:00');

-- Blocs (2 entries)
INSERT INTO bloc (libelle, prix) VALUES
  ('Orchestre', 5000.00),
  ('Balcon', 3000.00);

-- A required utilisateur for the client FK
INSERT INTO utilisateur (nom, email, passe) VALUES
  ('John Doe', 'john@example.com', 'secret');

-- Genres (2 entries)
INSERT INTO genre (libelle) VALUES
  ('Homme'),
  ('Femme');

-- Clients (1 entry; note id_client is not SERIAL, so we set it)
INSERT INTO client (id_client, id_utilisateur , id_genre) VALUES
  (1, 1,  1);

-- Salle (1 entry; id_salle is not SERIAL, so we set it)
INSERT INTO salle ( nom) VALUES
  ('Salle A');

-- Caisse (1 entry)
INSERT INTO caisse (nom) VALUES
  ('Caisse Principale');

-- Places (4 entries; distributed across blocs and salle)
INSERT INTO place (numero, id_bloc, id_salle) VALUES
  (101, 1, 1),
  (102, 1, 1),
  (201, 2, 1),
  (202, 2, 1);
