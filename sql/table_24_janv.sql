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
   prix_pub NUMERIC(15,2)   NOT NULL,
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

CREATE TABLE categorie(
   id_categorie SERIAL,
   libelle VARCHAR(50) ,
   PRIMARY KEY(id_categorie)
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

CREATE TABLE remise(
   id_remise SERIAL,
   montant NUMERIC(15,2)   NOT NULL,
   id_categorie INTEGER NOT NULL,
   id_cp INTEGER NOT NULL,
   PRIMARY KEY(id_remise),
   FOREIGN KEY(id_categorie) REFERENCES categorie(id_categorie),
   FOREIGN KEY(id_cp) REFERENCES categorie_p(id_cp)
);

CREATE TABLE categ_herit(
   id_ch SERIAL,
   pourcentage NUMERIC(15,2)   NOT NULL,
   id_categorie INTEGER NOT NULL,
   id_categorie_1 INTEGER,
   PRIMARY KEY(id_ch),
   FOREIGN KEY(id_categorie) REFERENCES categorie(id_categorie),
   FOREIGN KEY(id_categorie_1) REFERENCES categorie(id_categorie)
);

CREATE TABLE societe(
   id_societe SERIAL,
   nom VARCHAR(50) ,
   PRIMARY KEY(id_societe)
);

CREATE TABLE pub(
   id_pub SERIAL,
   desc_ VARCHAR(50) ,
   created TIMESTAMP NOT NULL,
   id_societe INTEGER NOT NULL,
   PRIMARY KEY(id_pub),
   FOREIGN KEY(id_societe) REFERENCES societe(id_societe)
);

CREATE TABLE commande(
   id_commande SERIAL,
   creted TIMESTAMP NOT NULL,
   id_societe INTEGER NOT NULL,
   PRIMARY KEY(id_commande),
   FOREIGN KEY(id_societe) REFERENCES societe(id_societe)
);

CREATE TABLE client(
   id_client SERIAL,
   nom VARCHAR(255)  NOT NULL,
   id_categorie INTEGER NOT NULL,
   PRIMARY KEY(id_client),
   FOREIGN KEY(id_categorie) REFERENCES categorie(id_categorie)
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

CREATE TABLE diffusion(
   id_diffusion SERIAL,
   id_seance INTEGER NOT NULL,
   id_pub INTEGER NOT NULL,
   PRIMARY KEY(id_diffusion),
   FOREIGN KEY(id_seance) REFERENCES seance(id_seance),
   FOREIGN KEY(id_pub) REFERENCES pub(id_pub)
);

CREATE TABLE commande_details(
   id_cd SERIAL,
   id_diffusion INTEGER NOT NULL,
   id_commande INTEGER NOT NULL,
   PRIMARY KEY(id_cd),
   UNIQUE(id_diffusion),
   FOREIGN KEY(id_diffusion) REFERENCES diffusion(id_diffusion),
   FOREIGN KEY(id_commande) REFERENCES commande(id_commande)
);


CREATE TABLE ticket(
   id_ticket SERIAL,
   created TIMESTAMP NOT NULL,
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

CREATE TABLE paiement_commande(
   id_pc SERIAL,
   montant NUMERIC(15,2)  ,
   id_cd INTEGER NOT NULL,
   PRIMARY KEY(id_pc),
   FOREIGN KEY(id_cd) REFERENCES commande_details(id_cd)
);


-- Insert data
INSERT INTO film(nom, duree) VALUES 
('Titanic', '02:00:00');

INSERT INTO salle(nom, capacite) VALUES 
('Salle A', 4),
('Salle B', 4);



INSERT INTO categorie_p(libelle, prix_defaut) VALUES 
('Standard', 30000.00),
('Premium', 30000.00),  
('VIP', 45000.00);

INSERT INTO prix_c(created, prix, id_cp) VALUES 
('2000-01-13 14:00:00', 30000.00, 1),
('2000-01-13 14:00:00', 30000.00, 2),
('2000-01-13 14:00:00', 30000.00, 3);

-- INSERT INTO categorie_prix(id_cp, id_pc, prix, created) VALUES 
-- (1, 1, 20000.00, NOW()),
-- (1, 2, 120.00, '2026-01-13 14:00:00'),
-- (2, 1, 50000.00, NOW()),
-- (3, 1, 90000.00, NOW());


-- INSERT INTO place(numero, id_cp, id_salle) VALUES 
-- (1, 1, 1), 
-- (2, 1, 1), 
-- (3, 2, 1), 
-- (4, 3, 1),
-- (1, 1, 2),

-- (2, 1, 2),
-- (3, 2, 2),
-- (4, 3, 2);

INSERT INTO seance(date_time_debut, id_salle, id_film , prix_pub) VALUES 
('2026-01-20 10:00:00', 1, 1  , 200000),
('2026-01-21 10:00:00', 1, 1 , 200000),
('2026-01-21 15:00:00', 1, 1 , 200000);

INSERT INTO caisse(nom) VALUES 
('Caisse Principale'),
('Caisse Secondaire');

INSERT INTO societe(nom) VALUES 
('Vaniala'),
('Lewis'),
('Socobis');

INSERT INTO pub(desc_, created, id_societe) VALUES 
('Promo Vaniala', NOW(), 1),
('Promo Lewis', NOW(), 2),
('Promo Socobis', NOW(), 3);

-- INSERT INTO mouvement_caisse(debit, credit, created, id_caisse) VALUES 
-- (0.00, 500.00, NOW(), 1),
-- (200.00, 0.00, NOW(), 1),
-- (0.00, 300.00, NOW(), 2);

INSERT INTO categorie(libelle) VALUES 
('Adulte'),
('Adolescent'),
('Enfant');
   
INSERT INTO client(nom, id_categorie) VALUES 
('Jean Dupont - Adulte', 1),
('Marie Martin - Adolescent', 2),
('Pierre Durand - Enfant', 3);

-- INSERT INTO categ_herit(pourcentage, id_categorie, id_categorie_1) VALUES 
-- (50.00, 3, 1);

-- INSERT INTO remise(montant, id_categorie, id_cp , created) VALUES 
-- (30000.00, 1, 1 , '2000-01-13 14:00:00'),
-- (40000.00, 1, 2 , '2000-01-13 14:00:00'),
-- (80000.00, 1, 3 , '2000-01-13 14:00:00'),

-- (20000.00, 2, 1 , '2000-01-13 14:00:00'),
-- (30000.00, 2, 2 , '2000-01-13 14:00:00'),
-- (45000.00, 2, 3 , '2000-01-13 14:00:00');












-- 50000






-- Remise pour Enfant (50% - héritage de Adulte)
-- (50.00, 3, 1 , NOW ()),
-- (50.00, 3, 2 , NOW ()),
-- (50.00, 3, 3 , NOW ());

-- INSERT INTO ticket(id_seance, id_place, id_film, id_client , created) VALUES 
-- (1, 1, 1, 1, NOW()),
-- (1, 2, 1, 2, NOW()),
-- (2, 3, 2, 3, NOW());


-- manova prix billet vip adult