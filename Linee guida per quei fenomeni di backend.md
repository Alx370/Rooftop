# Linee guida per quei fenomeni di backend

In questo documento definiremo la struttura, il modus operandi, naming convention, task e criteri di priorità. (e poi sicuramente altro che non mi viene in mente)

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
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

CREATE DATABASE rooftop_immobiliare;
USE rooftop_immobiliare;

-- Utenti che accedono al sistema.
CREATE TABLE utenti (
  id_utente INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(100) NOT NULL,
  cognome VARCHAR(100) NOT NULL,
  email VARCHAR(150) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL, -- sarà cifrata
  ruolo ENUM('ADMIN','VALUTATORE','AGENTE','MARKETING') NOT NULL DEFAULT 'AGENTE',
  telefono VARCHAR(20) NULL,
  stato ENUM('ATTIVO','DISABILITATO') NOT NULL DEFAULT 'ATTIVO',
);

-- Profilo dei proprietari di chi vuole vendere o far stimare la casa.
CREATE TABLE proprietari (
  id_proprietario INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(100) NOT NULL,
  cognome VARCHAR(100) NOT NULL,
  email VARCHAR(150) UNIQUE NOT NULL,
  telefono VARCHAR(20) NULL
);

-- Persone interessate ad acquistare un immobile.
CREATE TABLE clienti (
  id_cliente INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(100) NOT NULL,
  email VARCHAR(150) UNIQUE NOT NULL,
  telefono VARCHAR(20) NULL
);

CREATE TABLE immobili (
  id_immobile INT AUTO_INCREMENT PRIMARY KEY,
  id_proprietario INT,
  titolo VARCHAR(150) NOT NULL,
  descrizione TEXT,
  indirizzo VARCHAR(255) NOT NULL,
  citta VARCHAR(100) NOT NULL,
  provincia VARCHAR(50) NULL,
  cap VARCHAR(10) NULL,
  tipologia ENUM('Appartamento', 'Villa', 'Monolocale', 'Attico', 'Terreno', 'Altro') NOT NULL,
  stato_immobile ENUM('NUOVO', 'BUONO', 'DA_RISTRUTTURARE') DEFAULT 'BUONO',
  prezzo_richiesto DECIMAL(10,2) NOT NULL,
  data_inserimento DATETIME DEFAULT CURRENT_TIMESTAMP,
  stato_annuncio ENUM('VALUTAZIONE', 'TRATTATIVA', 'PUBBLICATO', 'VENDUTO', 'RIFIUTATO') DEFAULT 'VALUTAZIONE',
  FOREIGN KEY (id_proprietario) REFERENCES proprietari(id)
);

-- Dettagli tecnici separati per non appesantire la tabella principale
CREATE TABLE caratteristiche_immobile (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_immobile INT,
    metri_quadri DECIMAL(6,2) NOT NULL,
    locali INT NOT NULL,
    bagni INT NULL,
    piano INT NULL,
    riscaldamento ENUM('AUTONOMO', 'CENTRALIZZATO', 'ASSENTE'),
    classe_energetica ENUM('A4','A3','A2','A1','B','C','D','E','F','G'),
    orientamento ENUM('NORD', 'SUD', 'EST', 'OVEST', 'NORD-EST', 'NORD-OVEST', 'SUD-EST', 'SUD-OVEST') NULL,
    ascensore BOOLEAN DEFAULT FALSE,
  FOREIGN KEY (id_immobile) REFERENCES immobili(id)
);

CREATE TABLE valutazioni (
    id_valutazione INT AUTO_INCREMENT PRIMARY KEY,
    id_immobile INT,
    valore_stimato DECIMAL(10,2) NOT NULL,
    stato ENUM('IN_ATTESA', 'IN_CORSO', 'COMPLETATA', 'FALLITA') DEFAULT 'IN_ATTESA',
    metodo ENUM('AUTOMATICO', 'MANUALE') DEFAULT 'AUTOMATICO',
    data_valutazione DATETIME DEFAULT CURRENT_TIMESTAMP,
    note TEXT NULL,
    FOREIGN KEY (id_immobile) REFERENCES immobili(id)
);

-- Permette di gestire le proposte di contratto per ogni immobile, compreso lo stato.
CREATE TABLE contratti (
    id_contratto INT AUTO_INCREMENT PRIMARY KEY,
    id_immobile INT,
    tipo_contratto ENUM('ESCLUSIVA', 'NON_ESCLUSIVA') DEFAULT 'ESCLUSIVA',
    data_proposta DATETIME DEFAULT CURRENT_TIMESTAMP,
    data_accettazione DATETIME DEFAULT NULL,
    stato_contratto ENUM('PROPOSTO', 'ACCETTATO', 'RIFIUTATO', 'SCADUTO') DEFAULT 'PROPOSTO',
    -- file_contratto VARCHAR(255) NULL,
    FOREIGN KEY (id_immobile) REFERENCES immobili(id)
);

CREATE TABLE appuntamenti (
  id_appuntamento INT AUTO_INCREMENT PRIMARY KEY,
  id_immobile INT NOT NULL,
  id_cliente INT,
  id_utente INT,
  tipo_appuntamento ENUM('TELEFONATA', 'VISITA', 'SOPRALLUOGO') NOT NULL,
  data_ora_inizio DATETIME,
  data_ora_fine DATETIME,
  stato ENUM('PROGRAMMATO', 'COMPLETATO', 'ANNULLATO') DEFAULT 'PROGRAMMATO',
  FOREIGN KEY (id_immobile) REFERENCES immobili(id),
  FOREIGN KEY (id_cliente) REFERENCES clienti(id),
  FOREIGN KEY (id_utente) REFERENCES utenti(id)
);

-- opzionale, Permette una gestione interna della applicazione dove aggiunge note interne o pubbliche legate a ogni immobile.
CREATE TABLE note (
  id_nota INT AUTO_INCREMENT PRIMARY KEY,
  id_immobile INT,
  id_utente INT,
  tipo_nota ENUM('INTERNO', 'ESTERNO') DEFAULT 'INTERNO',
  contenuto TEXT NOT NULL,
  visibilita ENUM('PUBBLICA', 'RISERVATA') DEFAULT 'RISERVATA',
  FOREIGN KEY (id_immobile) REFERENCES immobili(id),
  FOREIGN KEY (id_utente) REFERENCES utenti(id)
);

```
