-- USE rooftop_immobiliare;

-- Utenti staff (amministratori, agenti, valutatori)
INSERT INTO utenti (nome, cognome, email, password, ruolo, telefono, stato) VALUES
('Marco', 'Rossi', 'marco.rossi@rooftop.it', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'AMMINISTRATORE', '+39 335 1234567', 'ATTIVO'),
('Laura', 'Bianchi', 'laura.bianchi@rooftop.it', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'AGENTE', '+39 348 2345678', 'ATTIVO'),
('Giuseppe', 'Verdi', 'giuseppe.verdi@rooftop.it', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'AGENTE', '+39 340 3456789', 'ATTIVO'),
('Francesca', 'Neri', 'francesca.neri@rooftop.it', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'VALUTATORE', '+39 333 4567890', 'ATTIVO'),
('Alessandro', 'Colombo', 'alessandro.colombo@rooftop.it', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'AGENTE', '+39 347 5678901', 'ATTIVO'),
('Martina', 'Ferrari', 'martina.ferrari@rooftop.it', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'VALUTATORE', '+39 331 6789012', 'ATTIVO'),
('Roberto', 'Moretti', 'roberto.moretti@rooftop.it', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'AGENTE', '+39 338 7890123', 'DISABILITATO'),

('Giovanni', 'Mancini', 'giovanni.mancini@email.it', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'PROPRIETARIO', '+39 340 1111111', 'ATTIVO'),
('Elena', 'Rizzo', 'elena.rizzo@email.it', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'PROPRIETARIO', '+39 347 2222222', 'ATTIVO'),
('Paolo', 'Gallo', 'paolo.gallo@email.it', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'PROPRIETARIO', '+39 335 3333333', 'ATTIVO'),
('Silvia', 'Conti', 'silvia.conti@email.it', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'PROPRIETARIO', '+39 333 4444444', 'ATTIVO'),
('Andrea', 'Marchetti', 'andrea.marchetti@email.it', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'PROPRIETARIO', '+39 348 5555555', 'ATTIVO'),
('Chiara', 'De Luca', 'chiara.deluca@email.it', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'PROPRIETARIO', '+39 331 6666666', 'ATTIVO'),
('Stefano', 'Barbieri', 'stefano.barbieri@email.it', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'PROPRIETARIO', '+39 340 7777777', 'ATTIVO'),
('Valentina', 'Santoro', 'valentina.santoro@email.it', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'PROPRIETARIO', '+39 347 8888888', 'ATTIVO'),
('Luca', 'Fontana', 'luca.fontana@email.it', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'PROPRIETARIO', '+39 335 9999999', 'ATTIVO'),
('Federica', 'Marini', 'federica.marini@email.it', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'PROPRIETARIO', '+39 333 1010101', 'ATTIVO'),
('Marco', 'Giordano', 'marco.giordano@email.it', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'PROPRIETARIO', '+39 338 1111222', 'ATTIVO'),
('Alessia', 'Ferrero', 'alessia.ferrero@email.it', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'PROPRIETARIO', '+39 340 3333444', 'ATTIVO');

INSERT INTO clienti (nome, cognome, email, telefono, consenso_gdpr, consenso_marketing) VALUES
('Davide', 'Greco', 'davide.greco@email.com', '+39 340 2020202', TRUE, TRUE),
('Sara', 'Romano', 'sara.romano@email.com', '+39 347 3030303', TRUE, FALSE),
('Matteo', 'Caruso', 'matteo.caruso@email.com', '+39 335 4040404', TRUE, TRUE),
('Giulia', 'Martino', 'giulia.martino@email.com', '+39 333 5050505', TRUE, TRUE),
('Simone', 'Leone', 'simone.leone@email.com', '+39 348 6060606', TRUE, FALSE),
('Alice', 'Lombardi', 'alice.lombardi@email.com', '+39 331 7070707', TRUE, TRUE),
('Lorenzo', 'Esposito', 'lorenzo.esposito@email.com', '+39 340 8080808', TRUE, TRUE),
('Beatrice', 'Vitale', 'beatrice.vitale@email.com', '+39 347 9090909', TRUE, FALSE),
('Riccardo', 'Benedetti', 'riccardo.benedetti@email.com', '+39 335 1212121', TRUE, TRUE),
('Elisa', 'Pellegrini', 'elisa.pellegrini@email.com', '+39 333 1313131', TRUE, TRUE),
('Antonio', 'Serra', 'antonio.serra@email.com', '+39 348 1414141', TRUE, FALSE),
('Sofia', 'Costa', 'sofia.costa@email.com', '+39 331 1515151', TRUE, TRUE);

INSERT INTO immobili (id_immobile, id_proprietario, id_agente, titolo, descrizione, indirizzo, civico, citta, provincia, cap, quartiere, tipologia, metri_quadri, piano, anno_costruzione, prezzo_richiesto, stato_immobile, stato_annuncio, creato_il) VALUES
(1, 8, 2, 'Luminoso Appartamento in Centro Storico', 'Splendido appartamento ristrutturato con finiture di pregio, sito nel cuore del centro storico. Doppia esposizione, molto luminoso, composto da ampio soggiorno, cucina abitabile, 2 camere matrimoniali e 2 bagni.', 'Via Giuseppe Garibaldi', '45', 'Milano', 'MI', '20121', 'Centro Storico', 'APPARTAMENTO', 95.00, '3', 1920, 485000.00, 'BUONO', 'PUBBLICATO', '2025-09-15 10:30:00'),
(2, 9, 3, 'Villa con Giardino Privato', 'Elegante villa indipendente su due livelli con ampio giardino privato. Piano terra: salone doppio, cucina abitabile, bagno servizio. Piano primo: 3 camere, 2 bagni, terrazzo. Cantina e garage doppio.', 'Via dei Colli', '12', 'Roma', 'RM', '00153', 'Testaccio', 'VILLA', 250.00, 'Villa', 2005, 890000.00, 'BUONO', 'PUBBLICATO', '2025-09-20 14:15:00'),
(3, 10, 2, 'Attico con Vista Panoramica', 'Prestigioso attico di recente costruzione con terrazzo panoramico di 80 mq. Soggiorno open space con cucina a vista, 3 camere da letto, 2 bagni. Predisposizione domotica e aria condizionata.', 'Corso Vittorio Emanuele', '88', 'Torino', 'TO', '10121', 'San Salvario', 'ATTICO', 140.00, '7', 2018, 620000.00, 'NUOVO', 'PUBBLICATO', '2025-10-01 09:00:00'),
(4, 11, 5, 'Monolocale Zona Universitaria', 'Grazioso monolocale appena ristrutturato, ideale per studenti o giovani professionisti. Zona giorno con angolo cottura, bagno con doccia. Arredato, pronto per essere abitato.', 'Via San Francesco', '23/A', 'Bologna', 'BO', '40121', 'Zona Universitaria', 'MONOLOCALE', 35.00, '2', 1970, 145000.00, 'NUOVO', 'PUBBLICATO', '2025-10-05 11:20:00'),
(5, 12, 3, 'Appartamento Moderno con Parcheggio', 'Appartamento in zona residenziale tranquilla, ben collegata. Ingresso, soggiorno con angolo cottura, 2 camere, bagno, ripostiglio. Balcone e posto auto coperto. Ottime condizioni.', 'Via delle Rose', '67', 'Firenze', 'FI', '50100', 'Campo di Marte', 'APPARTAMENTO', 75.00, '1', 1985, 295000.00, 'BUONO', 'PUBBLICATO', '2025-10-10 16:45:00'),
(6, 13, 2, 'Terreno Edificabile Vista Mare', 'Terreno edificabile di circa 1000 mq in posizione panoramica con vista mare. Possibilità di costruire villa bifamiliare. Tutti i servizi disponibili, strada asfaltata.', 'Località Belvedere', 'snc', 'Napoli', 'NA', '80126', 'Posillipo', 'TERRENO', 1000.00, NULL, NULL, 380000.00, 'BUONO', 'PUBBLICATO', '2025-10-12 10:00:00'),
(7, 14, 5, 'Trilocale Ristrutturato con Balcone', 'Appartamento completamente ristrutturato a nuovo, secondo piano con ascensore. Soggiorno, cucina abitabile, 2 camere, bagno finestrato, balcone. Riscaldamento autonomo, basse spese condominiali.', 'Via Torino', '34', 'Genova', 'GE', '16121', 'Centro', 'APPARTAMENTO', 68.00, '2', 1960, 235000.00, 'NUOVO', 'PUBBLICATO', '2025-10-15 13:30:00'),
(8, 15, 3, 'Villa Bifamiliare da Ristrutturare', 'Villa bifamiliare su tre livelli da ristrutturare completamente. Ampi spazi, giardino privato, garage. Ottima opportunità per creare la casa dei propri sogni.', 'Via dei Pini', '5', 'Verona', 'VR', '37121', 'Borgo Trento', 'VILLA', 180.00, 'Villa', 1975, 320000.00, 'DA_RISTRUTTURARE', 'TRATTATIVA', '2025-10-18 08:45:00'),
(9, 16, 2, 'Bilocale Moderno Zona Stazione', 'Bilocale completamente ristrutturato vicino alla stazione, ideale per investimento o prima casa. Soggiorno con angolo cottura, camera matrimoniale, bagno. Quarto piano con ascensore.', 'Via Roma', '78', 'Brescia', 'BS', '25121', 'Centro', 'APPARTAMENTO', 55.00, '4', 1980, 195000.00, 'BUONO', 'VALUTAZIONE', '2025-10-25 09:00:00'),
(10, 17, 5, 'Attico di Lusso in Costruzione', 'Prestigioso attico di nuova costruzione in fase di completamento. Finiture di altissimo livello, domotica completa, terrazzo di 150 mq con jacuzzi e vista panoramica. Due posti auto e cantina.', 'Viale Europa', '150', 'Milano', 'MI', '20139', 'Porta Romana', 'ATTICO', 220.00, '8', 2024, 1200000.00, 'NUOVO', 'PUBBLICATO', '2025-10-28 15:00:00'),
(11, 8, 3, 'Bilocale Arredato Centro Storico', 'Grazioso bilocale completamente arredato in pieno centro storico. Ideale per investimento o pied-à-terre. Soggiorno con angolo cottura, camera, bagno. Terzo piano con ascensore.', 'Via Dante', '92', 'Brescia', 'BS', '25121', 'Centro Storico', 'APPARTAMENTO', 48.00, '3', 1975, 165000.00, 'BUONO', 'PUBBLICATO', '2025-10-30 11:00:00'),
(12, 9, 2, 'Locale Commerciale Corso Principale', 'Ampio locale commerciale su strada principale ad alto passaggio. Attualmente libero, pronto per nuova attività. Vetrine ampie, bagno, ripostiglio. Ideale ufficio, showroom o negozio.', 'Corso Italia', '45', 'Parma', 'PR', '43121', 'Centro', 'ALTRO', 85.00, 'PT', 1990, 275000.00, 'BUONO', 'PUBBLICATO', '2025-11-01 10:00:00');

INSERT INTO caratteristiche_immobile (id_immobile, ascensore, parcheggio, posti_auto, garage, balcone, balcone_mq, terrazzo, terrazzo_mq, giardino, giardino_mq, cantina, arredato, aria_condizionata, allarme, riscaldamento, classe_energetica, orientamento) VALUES
(1, TRUE, FALSE, 0, FALSE, TRUE, 8.00, FALSE, NULL, FALSE, NULL, TRUE, FALSE, TRUE, FALSE, 'CENTRALIZZATO', 'C', 'SUD_EST'),
(2, FALSE, TRUE, 2, TRUE, FALSE, NULL, TRUE, 40.00, TRUE, 350.00, TRUE, FALSE, TRUE, TRUE, 'AUTONOMO', 'A2', 'SUD'),
(3, TRUE, TRUE, 1, TRUE, FALSE, NULL, TRUE, 80.00, FALSE, NULL, TRUE, FALSE, TRUE, TRUE, 'AUTONOMO', 'A3', 'OVEST'),
(4, TRUE, FALSE, 0, FALSE, FALSE, NULL, FALSE, NULL, FALSE, NULL, FALSE, TRUE, FALSE, FALSE, 'CENTRALIZZATO', 'E', 'NORD'),
(5, TRUE, TRUE, 1, FALSE, TRUE, 12.00, FALSE, NULL, FALSE, NULL, TRUE, FALSE, FALSE, FALSE, 'AUTONOMO', 'D', 'EST'),
(6, FALSE, FALSE, 0, FALSE, FALSE, NULL, FALSE, NULL, FALSE, NULL, FALSE, FALSE, FALSE, FALSE, NULL, 'NC', 'SUD'),
(7, TRUE, FALSE, 0, FALSE, TRUE, 6.00, FALSE, NULL, FALSE, NULL, FALSE, FALSE, TRUE, FALSE, 'AUTONOMO', 'B', 'SUD_OVEST'),
(8, FALSE, FALSE, 0, TRUE, TRUE, 15.00, FALSE, NULL, TRUE, 200.00, TRUE, FALSE, FALSE, FALSE, 'CENTRALIZZATO', 'G', 'NORD_EST'),
(9, FALSE, FALSE, 0, FALSE, TRUE, 10.00, FALSE, NULL, FALSE, NULL, TRUE, FALSE, FALSE, FALSE, 'CENTRALIZZATO', 'D', 'SUD'),
(10, TRUE, TRUE, 2, TRUE, FALSE, NULL, TRUE, 120.00, FALSE, NULL, TRUE, FALSE, TRUE, TRUE, 'AUTONOMO', 'A4', 'SUD'),
(11, TRUE, FALSE, 0, FALSE, TRUE, 5.00, FALSE, NULL, FALSE, NULL, FALSE, TRUE, TRUE, FALSE, 'AUTONOMO', 'C', 'EST'),
(12, FALSE, TRUE, 1, FALSE, FALSE, NULL, FALSE, NULL, FALSE, NULL, FALSE, FALSE, FALSE, FALSE, 'CENTRALIZZATO', 'D', 'NORD');

INSERT INTO valutazioni (id_immobile, id_valutatore, valore_stimato, valore_min, valore_max, stato, metodo, data_valutazione, note) VALUES
(1, 3, 485000.00, 465000.00, 505000.00, 'COMPLETATA', 'MANUALE', '2025-09-16 14:30:00', 'Valutazione basata su comparabili della zona. Immobile in ottime condizioni, prezzo allineato al mercato.'),
(2, 3, 890000.00, 850000.00, 930000.00, 'COMPLETATA', 'MANUALE', '2025-09-21 10:00:00', 'Villa di pregio, giardino ben curato. Prezzo giustificato dalla posizione e dalle caratteristiche.'),
(3, 3, 620000.00, 590000.00, 650000.00, 'COMPLETATA', 'MANUALE', '2025-10-02 11:15:00', 'Attico di nuova costruzione, terrazzo panoramico molto apprezzabile. Fascia alta del mercato.'),
(4, 3, 145000.00, 138000.00, 152000.00, 'COMPLETATA', 'AUTOMATICO', '2025-10-05 15:00:00', 'Monolocale zona universitaria, valutazione automatica confermata. Buon investimento.'),
(5, 3, 295000.00, 280000.00, 310000.00, 'COMPLETATA', 'MANUALE', '2025-10-11 09:30:00', 'Appartamento standard per la zona, posto auto rappresenta un plus.'),
(6, 3, 380000.00, 350000.00, 420000.00, 'COMPLETATA', 'MANUALE', '2025-10-13 16:00:00', 'Terreno edificabile, vista mare eccellente. Prezzo competitivo per la zona.'),
(7, 3, 235000.00, 225000.00, 245000.00, 'COMPLETATA', 'AUTOMATICO', '2025-10-16 10:00:00', 'Ristrutturazione completa recente, valore allineato.'),
(8, 3, 320000.00, 280000.00, 350000.00, 'COMPLETATA', 'MANUALE', '2025-10-19 14:00:00', 'Immobile da ristrutturare, prezzo tiene conto dei lavori necessari. Potenziale interessante.'),
(9, 3, 195000.00, 185000.00, 205000.00, 'IN_LAVORAZIONE', 'MANUALE', '2025-10-26 08:00:00', 'Valutazione in corso, necessario sopralluogo approfondito.'),
(10, 3, 1200000.00, 1150000.00, 1280000.00, 'COMPLETATA', 'MANUALE', '2025-10-29 11:30:00', 'Immobile di lusso, segmento premium. Prezzo giustificato dalle finiture e dalla posizione strategica.'),
(11, 3, 165000.00, 158000.00, 172000.00, 'COMPLETATA', 'AUTOMATICO', '2025-10-31 09:00:00', 'Bilocale in buone condizioni, arredato. Prezzo di mercato.'),
(12, 3, 275000.00, 260000.00, 290000.00, 'COMPLETATA', 'MANUALE', '2025-11-02 10:00:00', 'Locale commerciale, posizione strategica su corso principale. Buona opportunità.');

INSERT INTO contratti (id_immobile, id_proprietario, numero_contratto, tipo_contratto, durata_mesi, percentuale_provvigione, prezzo_concordato, data_proposta, data_accettazione, data_scadenza, stato, file_contratto) VALUES
(1, 4, 'CTR-2025-001', 'ESCLUSIVA', 6, 3.00, 485000.00, '2025-09-15 15:00:00', '2025-09-17 10:30:00', '2026-03-17', 'ACCETTATO', '/documenti/contratti/CTR-2025-001.pdf'),
(2, 5, 'CTR-2025-002', 'ESCLUSIVA', 12, 3.50, 890000.00, '2025-09-20 16:00:00', '2025-09-22 14:00:00', '2026-09-22', 'ACCETTATO', '/documenti/contratti/CTR-2025-002.pdf'),
(3, 6, 'CTR-2025-003', 'ESCLUSIVA', 6, 3.00, 620000.00, '2025-10-01 10:00:00', '2025-10-03 11:00:00', '2026-04-03', 'ACCETTATO', '/documenti/contratti/CTR-2025-003.pdf'),
(4, 7, 'CTR-2025-004', 'NON_ESCLUSIVA', 6, 2.50, 145000.00, '2025-10-05 12:00:00', '2025-10-06 09:00:00', '2026-04-06', 'ACCETTATO', '/documenti/contratti/CTR-2025-004.pdf'),
(5, 4, 'CTR-2025-005', 'ESCLUSIVA', 6, 3.00, 295000.00, '2025-10-10 17:00:00', '2025-10-12 15:30:00', '2026-04-12', 'ACCETTATO', '/documenti/contratti/CTR-2025-005.pdf'),
(6, 5, 'CTR-2025-006', 'ESCLUSIVA', 12, 4.00, 380000.00, '2025-10-12 11:00:00', '2025-10-14 10:00:00', '2026-10-14', 'ACCETTATO', '/documenti/contratti/CTR-2025-006.pdf'),
(7, 6, 'CTR-2025-007', 'NON_ESCLUSIVA', 6, 2.50, 235000.00, '2025-10-15 14:00:00', '2025-10-16 16:00:00', '2026-04-16', 'ACCETTATO', '/documenti/contratti/CTR-2025-007.pdf'),
(8, 7, 'CTR-2025-008', 'ESCLUSIVA', 9, 3.00, 320000.00, '2025-10-18 09:00:00', '2025-10-20 11:30:00', '2026-07-20', 'ACCETTATO', '/documenti/contratti/CTR-2025-008.pdf'),
(9, 4, 'CTR-2025-009', 'ESCLUSIVA', 6, 3.00, 195000.00, '2025-10-25 10:00:00', NULL, '2026-04-25', 'PROPOSTO', NULL),
(10, 5, 'CTR-2025-010', 'ESCLUSIVA', 12, 3.50, 1200000.00, '2025-10-28 16:00:00', '2025-10-30 14:00:00', '2026-10-30', 'ACCETTATO', '/documenti/contratti/CTR-2025-010.pdf'),
(11, 6, 'CTR-2025-011', 'NON_ESCLUSIVA', 3, 2.00, 165000.00, '2025-10-30 13:00:00', '2025-10-31 10:00:00', '2026-01-31', 'ACCETTATO', '/documenti/contratti/CTR-2025-011.pdf'),
(12, 7, 'CTR-2025-012', 'ESCLUSIVA', 6, 3.00, 275000.00, '2025-11-01 11:00:00', '2025-11-02 15:00:00', '2026-05-02', 'ACCETTATO', '/documenti/contratti/CTR-2025-012.pdf');

INSERT INTO appuntamenti (id_immobile, id_cliente, id_agente, tipo, data_ora_inizio, data_ora_fine, luogo, stato, note) VALUES
(1, 1, 2, 'VISITA', '2025-11-05 10:00:00', '2025-11-05 11:00:00', 'Via Giuseppe Garibaldi 45, Milano', 'PROGRAMMATO', 'Cliente interessato, prima visita.'),
(1, 4, 2, 'VISITA', '2025-11-07 15:00:00', '2025-11-07 16:00:00', 'Via Giuseppe Garibaldi 45, Milano', 'PROGRAMMATO', 'Coppia giovane, cerca prima casa.'),
(2, 2, 3, 'VISITA', '2025-11-04 14:00:00', '2025-11-04 15:30:00', 'Via dei Colli 12, Roma', 'CONFERMATO', 'Famiglia con due figli, molto interessati alla villa.'),
(3, 3, 2, 'VISITA', '2025-10-28 11:00:00', '2025-10-28 12:00:00', 'Corso Vittorio Emanuele 88, Torino', 'COMPLETATO', 'Visita effettuata, cliente impressionato dal terrazzo.'),
(3, 5, 2, 'TELEFONATA', '2025-11-06 09:30:00', '2025-11-06 09:45:00', NULL, 'PROGRAMMATO', 'Follow-up per fissare seconda visita.'),
(4, 6, 5, 'VISITA', '2025-10-20 16:00:00', '2025-10-20 16:30:00', 'Via San Francesco 23/A, Bologna', 'COMPLETATO', 'Studentessa, necessita risposta rapida.'),
(5, 7, 3, 'VISITA', '2025-11-08 10:30:00', '2025-11-08 11:30:00', 'Via delle Rose 67, Firenze', 'PROGRAMMATO', NULL),
(6, NULL, 2, 'SOPRALLUOGO', '2025-10-13 09:00:00', '2025-10-13 10:30:00', 'Località Belvedere, Napoli', 'COMPLETATO', 'Sopralluogo con geometra per verifica conformità urbanistica.'),
(7, 8, 5, 'VISITA', '2025-10-25 17:00:00', '2025-10-25 17:45:00', 'Via Torino 34, Genova', 'COMPLETATO', 'Cliente soddisfatto della ristrutturazione.'),
(8, NULL, 3, 'SOPRALLUOGO', '2025-10-19 10:00:00', '2025-10-19 12:00:00', 'Via dei Pini 5, Verona', 'COMPLETATO', 'Sopralluogo per valutazione lavori di ristrutturazione necessari.'),
(10, 9, 5, 'TELEFONATA', '2025-11-01 14:00:00', '2025-11-01 14:20:00', NULL, 'COMPLETATO', 'Presentazione immobile di lusso, fissato appuntamento per visita.'),
(10, 9, 5, 'VISITA', '2025-11-10 11:00:00', '2025-11-10 13:00:00', 'Viale Europa 150, Milano', 'PROGRAMMATO', 'Visita cantiere con imprenditore interessato.'),
(11, 10, 3, 'VISITA', '2025-11-03 18:00:00', '2025-11-03 18:30:00', 'Via Dante 92, Brescia', 'PROGRAMMATO', 'Giovane professionista, cerca bilocale arredato.'),
(12, 11, 2, 'TELEFONATA', '2025-11-02 15:30:00', '2025-11-02 15:45:00', NULL, 'COMPLETATO', 'Informazioni preliminari su locale commerciale.'),
(12, 11, 2, 'VISITA', '2025-11-09 16:00:00', '2025-11-09 17:00:00', 'Corso Italia 45, Parma', 'PROGRAMMATO', 'Imprenditore cerca locale per nuovo ufficio.');

INSERT INTO documenti (id_immobile, tipo, nome_file, path_file, created_at) VALUES
(1, 'PLANIMETRIA', 'planimetria_garibaldi_45.pdf', '/uploads/immobili/1/planimetria_garibaldi_45.pdf', '2025-09-15 11:00:00'),
(1, 'APE', 'ape_garibaldi_45.pdf', '/uploads/immobili/1/ape_garibaldi_45.pdf', '2025-09-15 11:05:00'),
(1, 'FOTO', 'soggiorno_01.jpg', '/uploads/immobili/1/soggiorno_01.jpg', '2025-09-15 11:10:00'),
(1, 'FOTO', 'cucina_01.jpg', '/uploads/immobili/1/cucina_01.jpg', '2025-09-15 11:12:00'),
(1, 'FOTO', 'camera_01.jpg', '/uploads/immobili/1/camera_01.jpg', '2025-09-15 11:15:00'),
(2, 'PLANIMETRIA', 'planimetria_villa_colli.pdf', '/uploads/immobili/2/planimetria_villa_colli.pdf', '2025-09-20 15:00:00'),
(2, 'APE', 'ape_villa_colli.pdf', '/uploads/immobili/2/ape_villa_colli.pdf', '2025-09-20 15:05:00'),
(2, 'FOTO', 'esterno_villa.jpg', '/uploads/immobili/2/esterno_villa.jpg', '2025-09-20 15:10:00'),
(2, 'FOTO', 'giardino_01.jpg', '/uploads/immobili/2/giardino_01.jpg', '2025-09-20 15:15:00'),
(2, 'FOTO', 'salone_villa.jpg', '/uploads/immobili/2/salone_villa.jpg', '2025-09-20 15:20:00'),
(2, 'CATASTALE', 'visura_catastale_villa.pdf', '/uploads/immobili/2/visura_catastale_villa.pdf', '2025-09-20 15:25:00'),
(3, 'PLANIMETRIA', 'planimetria_attico_torino.pdf', '/uploads/immobili/3/planimetria_attico_torino.pdf', '2025-10-01 10:00:00'),
(3, 'APE', 'ape_attico_torino.pdf', '/uploads/immobili/3/ape_attico_torino.pdf', '2025-10-01 10:05:00'),
(3, 'FOTO', 'terrazzo_panoramico.jpg', '/uploads/immobili/3/terrazzo_panoramico.jpg', '2025-10-01 10:10:00'),
(3, 'FOTO', 'living_open_space.jpg', '/uploads/immobili/3/living_open_space.jpg', '2025-10-01 10:15:00'),
(4, 'PLANIMETRIA', 'planimetria_monolocale_bologna.pdf', '/uploads/immobili/4/planimetria_monolocale_bologna.pdf', '2025-10-05 12:00:00'),
(4, 'APE', 'ape_monolocale.pdf', '/uploads/immobili/4/ape_monolocale.pdf', '2025-10-05 12:05:00'),
(4, 'FOTO', 'monolocale_generale.jpg', '/uploads/immobili/4/monolocale_generale.jpg', '2025-10-05 12:10:00'),
(5, 'PLANIMETRIA', 'planimetria_firenze.pdf', '/uploads/immobili/5/planimetria_firenze.pdf', '2025-10-10 17:30:00'),
(5, 'FOTO', 'soggiorno_firenze.jpg', '/uploads/immobili/5/soggiorno_firenze.jpg', '2025-10-10 17:35:00'),
(6, 'CATASTALE', 'visura_terreno_napoli.pdf', '/uploads/immobili/6/visura_terreno_napoli.pdf', '2025-10-12 11:00:00'),
(6, 'FOTO', 'vista_terreno.jpg', '/uploads/immobili/6/vista_terreno.jpg', '2025-10-12 11:10:00'),
(7, 'PLANIMETRIA', 'planimetria_genova.pdf', '/uploads/immobili/7/planimetria_genova.pdf', '2025-10-15 14:30:00'),
(7, 'APE', 'ape_genova.pdf', '/uploads/immobili/7/ape_genova.pdf', '2025-10-15 14:35:00'),
(10, 'PLANIMETRIA', 'planimetria_attico_lusso_milano.pdf', '/uploads/immobili/10/planimetria_attico_lusso_milano.pdf', '2025-10-28 16:00:00'),
(10, 'FOTO', 'render_attico_01.jpg', '/uploads/immobili/10/render_attico_01.jpg', '2025-10-28 16:10:00'),
(10, 'FOTO', 'render_terrazzo.jpg', '/uploads/immobili/10/render_terrazzo.jpg', '2025-10-28 16:15:00');

INSERT INTO recensioni (id_agente, id_immobile, nome_cliente, rating, titolo, commento, verificata, created_at) VALUES
(2, 1, 'Marco S.', 5, 'Professionalità e competenza', 'Laura è stata eccezionale! Mi ha seguito passo passo nella ricerca del mio appartamento ideale. Sempre disponibile e molto preparata. Consigliatissima!', TRUE, '2025-09-25 14:30:00'),
(3, 2, 'Famiglia Benedetti', 5, 'Esperienza fantastica', 'Giuseppe ci ha aiutato a trovare la villa perfetta per la nostra famiglia. Professionale, paziente e sempre pronto a rispondere alle nostre domande. Servizio impeccabile!', TRUE, '2025-10-15 10:00:00'),
(2, 3, 'Andrea L.', 4, 'Molto soddisfatto', 'Ottima esperienza, Laura ha compreso subito le mie esigenze e mi ha proposto immobili perfetti. Unico neo: tempi un po\' lunghi per alcune pratiche burocratiche.', TRUE, '2025-10-22 16:45:00'),
(5, 4, 'Chiara M.', 5, 'Agente top!', 'Alessandro è stato davvero in gamba. Mi ha trovato il monolocale perfetto per iniziare l\'università. Veloce, efficiente e simpatico. Lo consiglio!', TRUE, '2025-10-18 09:30:00'),
(3, 5, 'Paolo e Silvia', 5, 'Consigliatissimo', 'Giuseppe ha gestito la vendita del nostro vecchio appartamento e l\'acquisto del nuovo con grande professionalità. Sempre disponibile e onesto nei consigli.', TRUE, '2025-10-28 11:15:00'),
(5, 7, 'Roberto F.', 4, 'Buon servizio', 'Alessandro mi ha seguito bene nella trattativa. Apprezzata la trasparenza e la chiarezza nelle comunicazioni. Buon agente immobiliare.', TRUE, '2025-10-30 15:00:00'),
(2, NULL, 'Elena R.', 5, 'La migliore!', 'Ho lavorato con diversi agenti immobiliari, ma Laura è senza dubbio la migliore. Preparata, seria e molto umana. Grazie di tutto!', TRUE, '2025-11-01 12:00:00'),
(3, NULL, 'Luca G.', 5, 'Ottimo professionista', 'Giuseppe è un vero professionista del settore. Mi ha consigliato al meglio e mi ha evitato diversi problemi. Affidabilità al 100%.', TRUE, '2025-11-02 14:30:00');

INSERT INTO faq (categoria, domanda, risposta, ordine) VALUES
('GENERALE', 'Chi è RoofTop Immobiliare?', 'RoofTop Immobiliare è un\'agenzia immobiliare moderna e dinamica, specializzata nella compravendita di immobili residenziali e commerciali. Operiamo con professionalità e trasparenza, mettendo al centro le esigenze dei nostri clienti.', 1),
('VENDITA', 'Come posso vendere il mio immobile con voi?', 'È molto semplice! Puoi contattarci tramite il form sul sito, via email o telefono. Un nostro agente ti contatterà per fissare un appuntamento gratuito di valutazione. Dopo la valutazione, se decidi di affidarti a noi, ti proporremo un contratto di incarico e ci occuperemo di tutto il processo di vendita.', 1),
('VENDITA', 'Quanto costa la valutazione del mio immobile?', 'La valutazione preliminare del tuo immobile è completamente gratuita e senza impegno. Un nostro esperto valuterà l\'immobile entro 72 ore dalla richiesta.', 2),
('VENDITA', 'Quali sono le provvigioni dell\'agenzia?', 'Le nostre provvigioni variano dal 2% al 4% a seconda del tipo di contratto (esclusiva o non esclusiva) e del valore dell\'immobile. Tutti i costi vengono comunicati in modo trasparente prima della firma del contratto.', 3),
('ACQUISTO', 'Come posso cercare un immobile?', 'Puoi utilizzare il nostro motore di ricerca sul sito web, filtrando per zona, tipologia, prezzo e caratteristiche. In alternativa, puoi contattare direttamente un nostro agente che ti aiuterà a trovare l\'immobile perfetto per le tue esigenze.', 1),
('ACQUISTO', 'Posso visitare gli immobili?', 'Certamente! Puoi prenotare una visita compilando il form presente nella scheda dell\'immobile o chiamando direttamente il nostro ufficio. Organizzeremo la visita nel più breve tempo possibile.', 2),
('ACQUISTO', 'Offrite assistenza per il mutuo?', 'Sì, collaboriamo con i principali istituti di credito e possiamo assisterti nella ricerca della migliore soluzione di finanziamento per l\'acquisto del tuo immobile.', 3),
('VALUTAZIONE', 'Quanto tempo ci vuole per la valutazione?', 'Ci impegniamo a completare la valutazione entro 72 ore dalla richiesta. Riceverai un report dettagliato con la stima di valore del tuo immobile e suggerimenti per massimizzare il prezzo di vendita.', 1),
('VALUTAZIONE', 'La valutazione è vincolante?', 'No, la valutazione è un servizio informativo gratuito e non ti vincola in alcun modo. Puoi decidere liberamente se affidarti a noi per la vendita del tuo immobile.', 2),
('CONTRATTI', 'Cosa significa contratto in esclusiva?', 'Il contratto in esclusiva significa che affidi solo a RoofTop l\'incarico di vendere il tuo immobile per un periodo definito (generalmente 6-12 mesi). In cambio, garantiamo massimo impegno nella promozione e vendita, con provvigioni più vantaggiose.', 1),
('CONTRATTI', 'Posso recedere dal contratto?', 'Le condizioni di recesso sono specificate nel contratto di incarico. Generalmente, per i contratti in esclusiva è previsto un periodo minimo, mentre per i contratti non esclusivi c\'è maggiore flessibilità.', 2),
('DOCUMENTI', 'Quali documenti servono per vendere un immobile?', 'I documenti principali sono: atto di proprietà, planimetria catastale, APE (Attestato di Prestazione Energetica), visura catastale, regolamento condominiale (se applicabile). Il nostro team ti guiderà passo passo nella raccolta di tutta la documentazione necessaria.', 1),
('DOCUMENTI', 'Cos\'è l\'APE e chi deve fornirlo?', 'L\'APE (Attestato di Prestazione Energetica) è un documento obbligatorio che attesta la classe energetica dell\'immobile. Deve essere fornito dal venditore e realizzato da un tecnico certificato. Possiamo assisterti nel procurarlo.', 2),
('SERVIZI', 'Offrite servizi fotografici professionali?', 'Sì, per tutti gli immobili in vendita con noi offriamo un servizio fotografico professionale gratuito. Riteniamo che foto di qualità siano fondamentali per valorizzare al meglio il tuo immobile.', 1),
('SERVIZI', 'Fate pubblicità online degli immobili?', 'Assolutamente sì! Pubblichiamo gli immobili sul nostro sito, sui principali portali immobiliari nazionali e sui social media. Utilizziamo anche campagne pubblicitarie mirate per raggiungere il maggior numero di potenziali acquirenti.', 2),
('MUTUI', 'Mi aiutate a ottenere un mutuo?', 'Sì, il nostro servizio include la consulenza per il mutuo. Ti aiuteremo a preparare la documentazione necessaria e ti metteremo in contatto con i nostri partner bancari per ottenere le migliori condizioni.', 1);

INSERT INTO note (id_immobile, id_agente, tipo, contenuto, visibilita, created_at) VALUES
(1, 2, 'INTERNO', 'Proprietario molto collaborativo, ha accettato di fare piccoli interventi di tinteggiatura prima della vendita. Da programmare per fine novembre.', 'TEAM', '2025-09-18 09:30:00'),

(1, 2, 'INTERNO', 'Primo interessato (Davide Greco) ha chiesto tempo per valutare. Richiamare tra 1 settimana per follow-up.', 'PRIVATA', '2025-10-15 15:00:00'),

(2, 3, 'INTERNO', 'Villa in ottime condizioni, giardino molto curato. Proprietaria disponibile per visite anche nei weekend. Da valorizzare il garage doppio nelle foto.', 'TEAM', '2025-09-22 11:00:00'),

(2, 3, 'INTERNO', 'Famiglia Benedetti molto interessata, hanno fatto seconda visita. Probabile proposta a breve. Preparare bozza preliminare.', 'TEAM', '2025-10-16 16:30:00'),

(3, 2, 'INTERNO', 'Attico di pregio, terrazzo è il punto di forza. Consigliato servizio fotografico con drone per vista panoramica. Approvato dal proprietario.', 'TEAM', '2025-10-02 14:00:00'),

(3, 2, 'INTERNO', 'Andrea L. molto interessato ma deve vendere prima il suo attuale appartamento. Tenere monitorato, potenziale serio.', 'PRIVATA', '2025-10-29 10:30:00'),

(4, 5, 'INTERNO', 'Monolocale perfetto per studenti/giovani. Zona universitaria molto richiesta. Già diverse richieste di informazioni.', 'TEAM', '2025-10-06 09:00:00'),

(5, 3, 'INTERNO', 'Proprietario aperto a piccole trattative sul prezzo per vendita rapida. Non divulgare, informazione riservata.', 'PRIVATA', '2025-10-13 11:45:00'),

(6, 2, 'INTERNO', 'Terreno edificabile, verificare bene conformità urbanistica. Geometra ha confermato tutto ok. Potenziale costruttore interessato.', 'TEAM', '2025-10-14 15:00:00'),

(7, 5, 'INTERNO', 'Ristrutturazione completa recente, tutto a norma. Proprietario ha tutte le fatture dei lavori. Punto di forza per la vendita.', 'TEAM', '2025-10-16 17:00:00'),

(8, 3, 'INTERNO', 'Villa da ristrutturare, serve preventivo dettagliato lavori. Contattare geometra/architetto per stima costi. Potrebbe interessare investitori.', 'TEAM', '2025-10-20 12:00:00'),

(8, 3, 'INTERNO', 'Proprietario disponibile a ridurre prezzo se troviamo acquirente che compra "visto e piaciuto". Da valutare strategia commerciale.', 'PRIVATA', '2025-10-21 14:30:00'),

(9, 2, 'INTERNO', 'In attesa completamento valutazione. Proprietario ha fretta di vendere per trasferimento lavoro. Priorità alta.', 'TEAM', '2025-10-26 10:00:00'),

(10, 5, 'INTERNO', 'Attico di lusso, segmento premium. Pubblicità mirata su canali high-end. Cliente tipo: imprenditore/professionista affermato.', 'TEAM', '2025-10-30 15:30:00'),

(10, 5, 'INTERNO', 'Primo interessato molto serio (imprenditore), visita programmata per il 10 novembre. Preparare documentazione completa cantiere.', 'TEAM', '2025-11-02 09:00:00'),

(12, 2, 'INTERNO', 'Locale commerciale ottima posizione. Da evidenziare possibilità utilizzo come ufficio professionale o showroom. Zona alta visibilità.', 'TEAM', '2025-11-02 11:00:00');
