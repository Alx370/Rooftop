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

Utente Database per mettere il db Portale : 
```SQL
CREATE USER 'rooftop'@'%' IDENTIFIED BY 'back3nd'; -- bisogna poi configurarlo
```

il database si chiama `rooftop_immobiliare` ed è in questo file [[init.sql]](init.sql)