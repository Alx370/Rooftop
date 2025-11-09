Database Portale

L’obiettivo principale di questa applicazione è gestire l’intero percorso di comunicazione e informazione per un immobile.

Il processo inizia quando un proprietario compila un form online, fornendo i suoi dati personali e le informazioni sull’immobile che vuole vendere o far valutare. Tramite il portale degli agenti professionali elaborano la richiesta del proprietario dove riceve una stima del valore dell’immobile entro 72 ore, che sia automatica o manuale, in modo che il cliente sappia quanto può aspettarsi dalla vendita. 

Con la stima disponibile, si genera una proposta di contratto (tipicamente in ESCLUSIVA) e se accettata si procede con la fase operativa. La fase operativa prevede la pianificazione di appuntamenti (telefonate, visite, sopralluoghi) collegati all’immobile, agli eventuali clienti interessati e agli utenti interni per vendere. Gli utenti interni possono registrare in caso note (interne o esterne) associate all’immobile.

In sostanza, il sistema funge da gestionale immobiliare interno, che consente di:
- registrare proprietari, clienti e relativi immobili;
- produrre e tracciare valutazioni con stato e metodo;
- emettere e seguire contratti (proposto → accettato/rifiutato/scaduto);
- organizzare appuntamenti di lavoro;
- salvare note operative per memoria e coordinamento del team.

Proposta Database per il portale : 

```SQL
  CREATE USER 'rooftop'@'%' IDENTIFIED BY 'back3nd'; -- bisogna poi configurarlo

CREATE DATABASE rooftop_immobiliare;
USE rooftop_immobiliare;

-- DROP DATABASE rooftop_immobiliare;

-- Utenti che accedono al sistema.
CREATE TABLE utenti (
  id_utente INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(100) NOT NULL,
  cognome VARCHAR(100) NOT NULL,
  email VARCHAR(150) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL, -- sarà cifrata
  ruolo ENUM('AMMINISTRATORE', 'AGENTE', 'VALUTATORE') NOT NULL,
  telefono VARCHAR(20) NULL,
  stato ENUM('ATTIVO','DISABILITATO','BLOCCATO') NOT NULL DEFAULT 'ATTIVO'
);

-- Profilo del proprietario dove forzeremo il proprietario a registrare i suoi dati per mettere l'immobile per valutarlo o in vendita.
CREATE TABLE proprietari (
  id_proprietario INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(100) NOT NULL,
  cognome VARCHAR(100) NOT NULL,
  email VARCHAR(150) UNIQUE,
  telefono VARCHAR(20),
  consenso_gdpr BOOLEAN NOT NULL DEFAULT FALSE,
  consenso_marketing BOOLEAN NOT NULL DEFAULT FALSE,
  stato ENUM('ATTIVO','DISABILITATO','BLOCCATO') NOT NULL DEFAULT 'ATTIVO'
);

-- Persone interessate ad acquistare un immobile tramite piccolo form da compilare.
CREATE TABLE clienti (
  id_cliente INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(100) NOT NULL,
  cognome VARCHAR(100) NOT NULL,
  email VARCHAR(150) UNIQUE NOT NULL,
  telefono VARCHAR(20) NULL,
  consenso_gdpr BOOLEAN NOT NULL DEFAULT FALSE,
  consenso_marketing BOOLEAN NOT NULL DEFAULT FALSE
);

-- TABELLA IMMOBILI
CREATE TABLE immobili (
  id_immobile INT AUTO_INCREMENT PRIMARY KEY,
  id_proprietario INT NOT NULL,
  id_agente INT, -- sarà assegnato un agente referente automaticamente
  titolo VARCHAR(200) NOT NULL,
  descrizione TEXT,
  
  indirizzo VARCHAR(255) NOT NULL,
  civico VARCHAR(20),
  citta VARCHAR(100) NOT NULL,
  provincia VARCHAR(2) NOT NULL,
  cap VARCHAR(10) NOT NULL,
  quartiere VARCHAR(100),

  tipologia ENUM('APPARTAMENTO', 'VILLA', 'MONOLOCALE', 'ATTICO', 'TERRENO', 'ALTRO') NOT NULL,
  metri_quadri DECIMAL(7,2) NOT NULL,
  locali INT,
  bagni INT,
  piano VARCHAR(15),
  anno_costruzione INT,
  
  -- Prezzo e stato
  prezzo_richiesto DECIMAL(10,2) NOT NULL,
  stato_immobile ENUM('NUOVO', 'BUONO', 'DA_RISTRUTTURARE') DEFAULT 'BUONO',
  stato_annuncio ENUM('VALUTAZIONE', 'TRATTATIVA', 'PUBBLICATO', 'VENDUTO', 'RIFIUTATO') DEFAULT 'VALUTAZIONE',
  
  creato_il DATETIME DEFAULT CURRENT_TIMESTAMP,
  
  FOREIGN KEY (id_proprietario) REFERENCES proprietari(id_proprietario) ON DELETE RESTRICT,
  FOREIGN KEY (id_agente) REFERENCES utenti(id_utente) ON DELETE SET NULL
);

CREATE TABLE caratteristiche_immobile (
  id_caratteristica INT AUTO_INCREMENT PRIMARY KEY,
  id_immobile INT NOT NULL UNIQUE,
  
  -- Dotazioni (nomi semplici in italiano)
  ascensore BOOLEAN DEFAULT FALSE,
  parcheggio BOOLEAN DEFAULT FALSE,
  posti_auto INT DEFAULT 0,
  garage BOOLEAN DEFAULT FALSE,
  balcone BOOLEAN DEFAULT FALSE,
  balcone_mq DECIMAL(6,2),
  terrazzo BOOLEAN DEFAULT FALSE,
  terrazzo_mq DECIMAL(6,2),
  giardino BOOLEAN DEFAULT FALSE,
  giardino_mq DECIMAL(7,2),
  cantina BOOLEAN DEFAULT FALSE,
  arredato BOOLEAN DEFAULT FALSE,
  aria_condizionata BOOLEAN DEFAULT FALSE,
  allarme BOOLEAN DEFAULT FALSE,
  
  -- Impianti
  riscaldamento ENUM('AUTONOMO', 'CENTRALIZZATO', 'ASSENTE'),
  classe_energetica ENUM('A4', 'A3', 'A2', 'A1', 'B', 'C', 'D', 'E', 'F', 'G', 'NC'),
  orientamento ENUM('NORD', 'SUD', 'EST', 'OVEST', 'NORD_EST', 'NORD_OVEST', 'SUD_EST', 'SUD_OVEST'),
  
  FOREIGN KEY (id_immobile) REFERENCES immobili(id_immobile) ON DELETE CASCADE
);

-- TABELLA VALUTAZIONI (con SLA 72 ore)
CREATE TABLE valutazioni (
  id_valutazione INT AUTO_INCREMENT PRIMARY KEY,
  id_immobile INT NOT NULL,
  id_valutatore INT,
  valore_stimato DECIMAL(10,2) NOT NULL,
  valore_min DECIMAL(10,2),
  valore_max DECIMAL(10,2),
  stato ENUM('IN_LAVORAZIONE', 'COMPLETATA', 'SCADUTA') DEFAULT 'IN_LAVORAZIONE',
  metodo ENUM('AUTOMATICO', 'MANUALE') DEFAULT 'AUTOMATICO',

  data_valutazione DATETIME DEFAULT CURRENT_TIMESTAMP,
  data_scadenza DATETIME,
  note TEXT,
  
  FOREIGN KEY (id_immobile) REFERENCES immobili(id_immobile) ON DELETE CASCADE,
  FOREIGN KEY (id_valutatore) REFERENCES utenti(id_utente) ON DELETE SET NULL
);

-- Trigger per scadenza automatica 72 ore
-- questo trigger serve a impostare automaticamente la data di scadenza di una valutazione, senza doverla calcolare manualmente ogni volta da codice.
DELIMITER $$
CREATE TRIGGER calcola_scadenza_valutazione
BEFORE INSERT ON valutazioni
FOR EACH ROW
BEGIN
  SET NEW.data_scadenza = DATE_ADD(NEW.data_valutazione, INTERVAL 72 HOUR);
END$$
DELIMITER ;

-- TABELLA CONTRATTI
CREATE TABLE contratti (
  id_contratto INT AUTO_INCREMENT PRIMARY KEY,
  id_immobile INT NOT NULL,
  id_proprietario INT NOT NULL,
  numero_contratto VARCHAR(50) UNIQUE,
  tipo_contratto ENUM('ESCLUSIVA', 'NON_ESCLUSIVA') DEFAULT 'ESCLUSIVA',
  durata_mesi INT DEFAULT 6,
  percentuale_provvigione DECIMAL(5,2),
  prezzo_concordato DECIMAL(10,2),
  data_proposta DATETIME DEFAULT CURRENT_TIMESTAMP,
  data_accettazione DATETIME,
  data_scadenza DATE,
  stato ENUM('BOZZA', 'PROPOSTO', 'ACCETTATO', 'RIFIUTATO', 'SCADUTO') DEFAULT 'BOZZA',
  file_contratto VARCHAR(255) NULL,
  
  FOREIGN KEY (id_immobile) REFERENCES immobili(id_immobile) ON DELETE RESTRICT,
  FOREIGN KEY (id_proprietario) REFERENCES proprietari(id_proprietario) ON DELETE RESTRICT
);

-- TABELLA APPUNTAMENTI
CREATE TABLE appuntamenti (
  id_appuntamento INT AUTO_INCREMENT PRIMARY KEY,
  id_immobile INT,
  id_cliente INT,
  id_agente INT NOT NULL,
  tipo ENUM('TELEFONATA', 'VISITA', 'SOPRALLUOGO') NOT NULL,
  data_ora_inizio DATETIME NOT NULL,
  data_ora_fine DATETIME,
  luogo VARCHAR(255),
  stato ENUM('PROGRAMMATO', 'CONFERMATO', 'COMPLETATO', 'ANNULLATO') DEFAULT 'PROGRAMMATO',
  note TEXT,
  
  FOREIGN KEY (id_immobile) REFERENCES immobili(id_immobile) ON DELETE SET NULL,
  FOREIGN KEY (id_cliente) REFERENCES clienti(id_cliente) ON DELETE SET NULL,
  FOREIGN KEY (id_agente) REFERENCES utenti(id_utente) ON DELETE RESTRICT
);

-- TABELLA DOCUMENTI
CREATE TABLE documenti (
  id_documento INT AUTO_INCREMENT PRIMARY KEY,
  id_immobile INT NOT NULL,
  tipo ENUM('PLANIMETRIA', 'APE', 'FOTO', 'CATASTALE', 'ALTRO') NOT NULL,
  nome_file VARCHAR(255) NOT NULL,
  path_file VARCHAR(500) NULL,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  
  FOREIGN KEY (id_immobile) REFERENCES immobili(id_immobile) ON DELETE CASCADE
);

-- TABELLA RECENSIONI
CREATE TABLE recensioni (
  id_recensione INT AUTO_INCREMENT PRIMARY KEY,
  id_agente INT NOT NULL,
  id_immobile INT,
  nome_cliente VARCHAR(100),
  rating INT NOT NULL CHECK (rating BETWEEN 1 AND 5),
  titolo VARCHAR(255),
  commento TEXT NOT NULL,
  verificata BOOLEAN DEFAULT FALSE,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  
  FOREIGN KEY (id_agente) REFERENCES utenti(id_utente) ON DELETE CASCADE,
  FOREIGN KEY (id_immobile) REFERENCES immobili(id_immobile) ON DELETE SET NULL
);

-- TABELLA FAQ 
CREATE TABLE faq (
  id_faq INT AUTO_INCREMENT PRIMARY KEY,
  categoria ENUM('VENDITA', 'ACQUISTO', 'VALUTAZIONE', 'CONTRATTI', 'DOCUMENTI', 'MUTUI', 'SERVIZI', 'GENERALE') NOT NULL,
  domanda TEXT NOT NULL,
  risposta TEXT NOT NULL,
  ordine INT DEFAULT 0
);

-- TABELLA NOTE 
CREATE TABLE note (
  id_nota INT AUTO_INCREMENT PRIMARY KEY,
  id_immobile INT,
  id_agente INT NOT NULL,
  tipo ENUM('INTERNO') NOT NULL DEFAULT 'INTERNO',
  contenuto TEXT NOT NULL,
  visibilita ENUM('TEAM', 'PRIVATA') DEFAULT 'TEAM',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  
  FOREIGN KEY (id_immobile) REFERENCES immobili(id_immobile) ON DELETE CASCADE,
  FOREIGN KEY (id_agente) REFERENCES utenti(id_utente) ON DELETE RESTRICT
);

```
