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
   prix_defaut NUMERIC(15,2)   NOT NULL,
   PRIMARY KEY(id_cp)
);

CREATE TABLE prix_c(
   id_pc SERIAL,
   created TIMESTAMP NOT NULL,
   prix NUMERIC(15,2)   NOT NULL,
   id_cp INTEGER NOT NULL,
   PRIMARY KEY(id_pc),
   FOREIGN KEY(id_cp) REFERENCES categorie_p(id_cp)
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



INSERT INTO categorie_p(libelle, prix_defaut) VALUES 
('Standard', 20000.00),
('Premium', 50000.00),  
('VIP', 100000.00);

INSERT INTO prix_c(created, prix, id_cp) VALUES 
(NOW(), 20000.00, 1),
(NOW(), 50000.00, 2),
(NOW(), 100000.00, 3);

-- INSERT INTO categorie_prix(id_cp, id_pc, prix, created) VALUES 
-- (1, 1, 20000.00, NOW()),
-- (1, 2, 120.00, '2026-01-13 14:00:00'),
-- (2, 1, 50000.00, NOW()),
-- (3, 1, 90000.00, NOW());

INSERT INTO place(numero, id_cp, id_salle) VALUES 
-- Salle A (100 places): 70 Standard + 20 Premium + 10 VIP
-- Standard: places 1-70
(1, 1, 1), (2, 1, 1), (3, 1, 1), (4, 1, 1), (5, 1, 1), (6, 1, 1), (7, 1, 1), (8, 1, 1), (9, 1, 1), (10, 1, 1),
(11, 1, 1), (12, 1, 1), (13, 1, 1), (14, 1, 1), (15, 1, 1), (16, 1, 1), (17, 1, 1), (18, 1, 1), (19, 1, 1), (20, 1, 1),
(21, 1, 1), (22, 1, 1), (23, 1, 1), (24, 1, 1), (25, 1, 1), (26, 1, 1), (27, 1, 1), (28, 1, 1), (29, 1, 1), (30, 1, 1),
(31, 1, 1), (32, 1, 1), (33, 1, 1), (34, 1, 1), (35, 1, 1), (36, 1, 1), (37, 1, 1), (38, 1, 1), (39, 1, 1), (40, 1, 1),
(41, 1, 1), (42, 1, 1), (43, 1, 1), (44, 1, 1), (45, 1, 1), (46, 1, 1), (47, 1, 1), (48, 1, 1), (49, 1, 1), (50, 1, 1),
(51, 1, 1), (52, 1, 1), (53, 1, 1), (54, 1, 1), (55, 1, 1), (56, 1, 1), (57, 1, 1), (58, 1, 1), (59, 1, 1), (60, 1, 1),
(61, 1, 1), (62, 1, 1), (63, 1, 1), (64, 1, 1), (65, 1, 1), (66, 1, 1), (67, 1, 1), (68, 1, 1), (69, 1, 1), (70, 1, 1),
-- Premium: places 71-90
(71, 2, 1), (72, 2, 1), (73, 2, 1), (74, 2, 1), (75, 2, 1), (76, 2, 1), (77, 2, 1), (78, 2, 1), (79, 2, 1), (80, 2, 1),
(81, 2, 1), (82, 2, 1), (83, 2, 1), (84, 2, 1), (85, 2, 1), (86, 2, 1), (87, 2, 1), (88, 2, 1), (89, 2, 1), (90, 2, 1),
-- VIP: places 91-100
(91, 3, 1), (92, 3, 1), (93, 3, 1), (94, 3, 1), (95, 3, 1), (96, 3, 1), (97, 3, 1), (98, 3, 1), (99, 3, 1), (100, 3, 1);

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
