-- Sample Data for Cinema Database

-- Insert Genres
INSERT INTO genre (libelle) VALUES
('Action'),
('Comédie'),
('Drame'),
('Science-Fiction'),
('Horreur');

-- Insert Films
INSERT INTO film (nom, duree) VALUES
('Avatar 2', '03:12:00'),
('Le Seigneur des Anneaux', '03:28:00'),
('Rire sans fin', '01:30:00'),
('Inception', '02:28:00'),
('Conjuring', '02:12:00');

-- Insert Salles
INSERT INTO salle (nom) VALUES
('Salle 1 - Premium'),
('Salle 2 - Standard'),
('Salle 3 - VIP'),
('Salle 4 - Family');

-- Insert Places
INSERT INTO place (numero, id_salle) VALUES
(1, 1), (2, 1), (3, 1), (4, 1), (5, 1),
(6, 1), (7, 1), (8, 1), (9, 1), (10, 1),
(11, 2), (12, 2), (13, 2), (14, 2), (15, 2),
(16, 2), (17, 2), (18, 2), (19, 2), (20, 2),
(21, 3), (22, 3), (23, 3), (24, 3), (25, 3),
(26, 4), (27, 4), (28, 4), (29, 4), (30, 4);

-- Insert Seances
INSERT INTO seance (id_seance, date_time_debut, id_salle, id_film) VALUES
(1, '2026-01-13 14:00:00', 1, 1),
(2, '2026-01-13 17:00:00', 1, 2),
(3, '2026-01-13 15:30:00', 2, 3),
(4, '2026-01-13 18:30:00', 3, 4),
(5, '2026-01-13 20:00:00', 4, 5),
(6, '2026-01-14 14:00:00', 1, 1),
(7, '2026-01-14 17:00:00', 2, 2);

-- Insert Caisses
INSERT INTO caisse (nom) VALUES
('Caisse 1'),
('Caisse 2'),
('Caisse 3');

-- Insert Mouvements Caisse
INSERT INTO mouvement_caisse (debit, credit, created, id_caisse) VALUES
(0, 100.00, '2026-01-13 10:00:00', 1),
(0, 150.00, '2026-01-13 11:00:00', 2),
(50.00, 0, '2026-01-13 12:00:00', 1),
(0, 200.00, '2026-01-13 13:00:00', 3);

-- Insert Reservations
INSERT INTO reservation (id_reservation, date_reservation) VALUES
(1, '2026-01-12 10:30:00'),
(2, '2026-01-12 14:15:00'),
(3, '2026-01-13 09:00:00');

-- Insert Clients
INSERT INTO client (id_client, nom, id_genre) VALUES
(1, 'Jean Dupont', 1),
(2, 'Marie Martin', 2),
(3, 'Pierre Bernard', 3),
(4, 'Sophie Lefevre', 1),
(5, 'Luc Moreau', 2);

-- Insert Tickets
INSERT INTO ticket (id_reservation, id_place, id_film, id_client) VALUES
(1, 1, 1, 1),
(1, 2, 1, 2),
(2, 11, 3, 3),
(2, 12, 3, 4),
(3, 21, 4, 5);

-- Insert Paiements
INSERT INTO paiement (created, montant, id_ticket, id_mc) VALUES
('2026-01-12 10:35:00', 15.50, 1, 1),
('2026-01-12 10:36:00', 15.50, 2, 1),
('2026-01-12 14:20:00', 12.00, 3, 2),
('2026-01-12 14:21:00', 12.00, 4, 2),
('2026-01-13 09:05:00', 18.00, 5, 4);
