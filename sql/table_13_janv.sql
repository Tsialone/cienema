DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

CREATE TABLE film(
   id_film SERIAL,
   nom VARCHAR(255)  NOT NULL,
   duree TIME NOT NULL,
   PRIMARY KEY(id_film)
);

CREATE TABLE salle(
   id_salle SERIAL,
   nom VARCHAR(50) ,
   capacite INTEGER NOT NULL,
   PRIMARY KEY(id_salle)
);

CREATE TABLE seance(
   id_seance SERIAL,
   date_time_debut TIMESTAMP NOT NULL,
   id_salle INTEGER NOT NULL,
   id_film INTEGER NOT NULL,
   PRIMARY KEY(id_seance),
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

CREATE TABLE reservation(
   id_reservation SERIAL,
   date_reservation TIMESTAMP NOT NULL,
   PRIMARY KEY(id_reservation)
);

CREATE TABLE categorie_p(
   id_cp SERIAL,
   libelle VARCHAR(50) ,
   prix NUMERIC(15,2)   NOT NULL,
   PRIMARY KEY(id_cp)
);

CREATE TABLE client(
   id_client SERIAL,
   nom VARCHAR(255)  NOT NULL,
   id_genre INTEGER NOT NULL,
   PRIMARY KEY(id_client),
   FOREIGN KEY(id_genre) REFERENCES genre(id_genre)
);

CREATE TABLE place(
   id_place SERIAL,
   numero INTEGER NOT NULL,
   id_cp INTEGER NOT NULL,
   id_salle INTEGER NOT NULL,
   PRIMARY KEY(id_place),
   FOREIGN KEY(id_cp) REFERENCES categorie_p(id_cp),
   FOREIGN KEY(id_salle) REFERENCES salle(id_salle)
);

CREATE TABLE ticket(
   id_ticket SERIAL,
   id_seance INTEGER NOT NULL,
   id_reservation INTEGER,
   id_place INTEGER NOT NULL,
   id_film INTEGER NOT NULL,
   id_client INTEGER NOT NULL,
   PRIMARY KEY(id_ticket),
   FOREIGN KEY(id_seance) REFERENCES seance(id_seance),
   FOREIGN KEY(id_reservation) REFERENCES reservation(id_reservation),
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



-- Insert data
INSERT INTO film(nom, duree) VALUES 
('Avatar', '02:42:00'),
('Inception', '02:28:00'),
('Interstellar', '02:49:00'),
('The Dark Knight', '02:32:00');

INSERT INTO salle(nom, capacite) VALUES 
('Salle A', 100),
('Salle B', 120),
('Salle C', 80);

INSERT INTO categorie_p(libelle, prix) VALUES 
('Standard', 20000.00),
('Premium', 50000.00);

INSERT INTO place(numero, id_cp, id_salle) VALUES 
(1, 1, 1), (2, 1, 1), (3, 1, 1), (4, 2, 1), (5, 2, 1),
(1, 1, 2), (2, 1, 2), (3, 2, 2), (4, 2, 2), (5, 2, 2),
(1, 1, 3), (2, 1, 3), (3, 1, 3), (4, 1, 3), (5, 2, 3);

INSERT INTO seance(date_time_debut, id_salle, id_film) VALUES 
('2026-01-13 14:00:00', 1, 1),
('2026-01-13 17:00:00', 1, 2),
('2026-01-13 20:00:00', 2, 3),
('2026-01-14 14:00:00', 2, 4),
('2026-01-14 18:00:00', 3, 1);

INSERT INTO caisse(nom) VALUES 
('Caisse Principale'),
('Caisse Secondaire');

INSERT INTO mouvement_caisse(debit, credit, created, id_caisse) VALUES 
(0.00, 500.00, NOW(), 1),
(200.00, 0.00, NOW(), 1),
(0.00, 300.00, NOW(), 2);

INSERT INTO genre(libelle) VALUES 
('Homme'),
('Femme'),
('Autre');

INSERT INTO client(nom, id_genre) VALUES 
('Jean Dupont', 1),
('Marie Martin', 2),
('Pierre Durand', 1),
('Sophie Leclerc', 2);

INSERT INTO ticket(id_seance, id_place, id_film, id_client) VALUES 
(1, 1, 1, 1),
(1, 2, 1, 2),
(2, 6, 2, 3),
(2, 7, 2, 4),
(3, 11, 3, 1);
