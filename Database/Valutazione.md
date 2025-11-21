## Strategia per effettuare una valutazione precisa

### Valori.sql

Il file ***valori.sql*** contiene i campi ***Fascia***, ***Zona***, ***Descr_tipologia*** e ***Stato***. 

Ovviamente contiene i campi prezzo Compr_min e Compr_max (di cui sarà necessario avere la media).

Dovremmo basarci su questi dati per riuscire a fornire una valutazione approssimativa, ma realistica.

Il campo Fascia indica la macro zona di cui fa parta una certa area.

> Ad esempio la zona A è una zona centrale (centro di Torino) oppure la zona B
> è una zona semicentrale o in prossimità della zona centrale (esempio zona Crocetta).

Il campo Zona indica la qualità abitativa dei servizi della zona. Le zone hanno dei campi come: A1, A2, B1, B2, Bx, eccetera.

> Ad esempio una zona periferica di tipo D (generalmente) può essere servita meglio con mezzi
> e altri servizi per cui risulterà una D1 mentre sempre una stessa zona periferica non servita 
> sarà di tipo D4.

Il campo Desc_tipologia definisce la tipologia dell'immobile. Ho potuto vedere che si distingono in: 

- Abitazioni civili
- Abitazioni signorili
- Abitazioni di tipo economico
- Abitazioni tipiche dei luoghi
- Ville e Villini

Infine il campo Stato ed ho potuto notare che si distingono i campi:

- Normale
- Ottimo

---

### Zone.sql

Questa tabella ci interessa per fornire all'utetnte i dati per ricavare la sua zona di abitazione e fornire una valutazione accurata. 

In particolare ci interessano i campi:

- **Comune_descrizione**: il nome del Comune
- **Fascia**: il campo di cui abbiamo parlato prima A, B, C, ec...
- **Zona_Descr**: questo campo corrisponde alle zone con cui l'Omi ha organizzato le aree geografiche. Di sotto un esempio di Torino:

> ROMA, CARLO EMANUELE II, SOLFERINO, VINZAGLIO, GARIBALDI, CASTELLO, ECCETERA...

- Zona: la zona di riferimento di prima A1, B4, C4, eccetera.

---

### La strategia

La soluzione migliore e più efficiente per la soluzione è:

1. Far inserire all'utente il COMUNE e la ZONA di riferimento.
   - Inoltre sarà necessario mostrare all'utente sempre sotto forma di lista le possibili soluzioni di tipologie di casa (elencate sopra). **Questo è determinante ai fini di una corretta valutazione poichè nel database ci sono anche tipologie come Box, parcheggi, uffici.**

> [!WARNING]
> **Cosa intendo con zona di riferimento?** Intendo fornire all'utente in base al suo Comune di residenza una lista di tutte le zone disponibili nel nostro database. 
>
> Grazie a questo stratagemma evitiamo di dover utilizzare per forza il CAP.

1. Dopo aver individuato questi due campi saranno necessari il campo Zona (A1, A2, ...).

2. Avendo il Comune e la Zona possiamo ricavare la media del prezzo di compravendita.

**Tutto questo procedimento si tradurrà in operazioni SQL.**

### Calcolo per la valutazione


