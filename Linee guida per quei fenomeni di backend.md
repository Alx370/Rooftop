# Linee guida per quei fenomeni di backend

In questo documento definiremo la struttura, il modus operandi, naming convention, task e criteri di priorità. (e poi sicuramente altro che non mi viene in mente)

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
DATABASE APPLICAZIONE (Bozza)

L’obiettivo principale di questa applicazione è gestire l’intero percorso di un proprietario che vuole vendere o far valutare il proprio immobile, dall’invio della richiesta iniziale fino alla firma del contratto di esclusiva.

Il processo inizia quando un proprietario compila un form online, fornendo i suoi dati personali e le informazioni sull’immobile. L’azienda, tramite l’applicazione, riceve la richiesta e deve fornire una stima del valore dell’immobile entro 72 ore, in modo che il cliente sappia quanto può aspettarsi dalla vendita.

Dopo la stima, l’azienda propone al proprietario un contratto di esclusiva, cioè un accordo che autorizza solo l’agenzia a gestire la vendita per un certo periodo. Se il contratto viene firmato, l’immobile passa alla fase operativa, che comprende sopralluoghi, fotografie, pubblicazione dell’annuncio e gestione dei potenziali acquirenti.

In sostanza, il sistema funge da gestionale immobiliare interno, che consente di:
- Tenere traccia di chi ha richiesto una valutazione;
- Verificare quali immobili sono in attesa di stima o già valutati;
- Gestire lo stato dei contratti (bozza, inviato, firmato);
- Assegnare e controllare le attività operative (es. appuntamenti, caricamento documenti);
- Automatizzare passaggi e scadenze, migliorando i tempi e l’organizzazione interna

proporrei una struttura del genere con per ora 7 tabelle :

| Tabella                             | Descrizione                                                                                                                | Motivazione                                                                                                   |
| ----------------------------------- | -------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------- |
| **utenti**                          | Contiene le credenziali e i ruoli degli utenti che accedono al backoffice (amministratori, valutatori, agenti, marketing). | Serve a distinguere chi utilizza l’app e quali operazioni può compiere.                                       |
| **proprietari**                     | Raccoglie le informazioni di chi propone l’immobile (nome, contatti, consensi).                                            | È la base di partenza di ogni pratica, ogni proprietario può avere più immobili.                              |
| **immobili**                        | Scheda principale di ogni immobile con informazioni base (indirizzo, tipologia, stato di avanzamento).                     | Rappresenta il cuore operativo del sistema: su ogni immobile si collegano stime, contratti, appuntamenti ecc. |
| **caratteristiche_immobile**        | Contiene dettagli tecnici come superficie, numero di stanze, piano, riscaldamento, classe energetica ecc.                  | Mantiene la tabella “immobili” leggera e facilita eventuali ricerche avanzate.                                |
| **stime**                           | Registra le valutazioni di prezzo degli immobili, automatiche o manuali, con scadenza di 72 ore.                           | Permette di rispettare l’SLA e conservare lo storico delle stime nel tempo.                                   |
| **contratti**                       | Gestisce la proposta di esclusiva per la vendita (stato: bozza → inviato → firmato).                                       | Consente di sapere quali immobili sono coperti da un contratto valido e per quanto tempo.                     |
| **attivita** *(opzionale)*          | Potrebbe raccogliere task semplici legati a ogni immobile (es. “caricare foto”, “contattare proprietario”).                | Aiuta l’organizzazione interna e il rispetto delle scadenze.                                                  |

Tabelle Opzionali :

| Tabella          | Descrizione                                                                              | Per                                                                         |
| ---------------- | ---------------------------------------------------------------------------------------- | --------------------------------------------------------------------------- |
| **appuntamenti** | Pianifica telefonate, visite o sopralluoghi collegati a un immobile o a un proprietario. | Quando l’agenzia comincia a gestire più visite e serve un’agenda condivisa. |
| **note_crm**     | Permette di aggiungere commenti, osservazioni o appunti interni.                         | Quando il team inizia ad avere bisogno di lasciare note sulle pratiche.     |

