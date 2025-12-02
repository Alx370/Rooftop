# Documentazione API - Rooftop Backend

## Indice
- [Autenticazione](#autenticazione)
- [Utenti](#utenti)
- [Clienti](#clienti)
- [Proprietari](#proprietari)
- [Immobili](#immobili)
- [Recensioni](#recensioni)
- [Note](#note)
- [FAQ](#faq)
- [Contact Requests](#contact-requests-richieste-di-contatto)
- [Newsletter](#newsletter)
- [Quick FAQ Creation](#quick-faq-creation-click-to-create)
- [Autenticazione e Autorizzazioni](#autenticazione-e-autorizzazioni)

---

## Autenticazione
**Base URL**: `/api/auth`

| Metodo | Endpoint | Descrizione | Autorizzazione | Body/Params |
|--------|----------|-------------|----------------|-------------|
| POST | `/api/auth/login` | Login utente e generazione JWT token | Pubblico | Body: `LoginReq` JSON |
| POST | `/api/auth/logout` | Logout utente (client-side token removal) | Pubblico | - |
| GET | `/api/auth/me` | Recupera info utente autenticato | Utente autenticato | - |

### Esempio Body Login
```json
{
  "email": "mario.rossi@example.com",
  "password": "securePassword123"
}
```

### Esempio Response Login
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### Esempio Response /me
```json
{
  "authenticated": true,
  "nome": "Mario",
  "cognome": "Rossi",
  "email": "mario.rossi@example.com",
  "telefono": "+39 333 1234567",
  "stato": "ATTIVO",
  "authorities": ["ROLE_AGENTE"]
}
```

### Esempio Response Logout
```json
{
  "message": "Logout effettuato con successo"
}
```

---

## Autenticazione e Autorizzazioni

### Ruoli Disponibili
- **AMMINISTRATORE**: Accesso completo a tutte le funzionalità
- **AGENTE**: Gestione clienti, proprietari, note e recensioni
- **VALUTATORE**: Visualizzazione e verifica recensioni
- **CLIENTE**: Utente registrato
- **PROPRIETARIO**: Proprietario di immobili

### Autenticazione
Tutti gli endpoint (tranne quelli esplicitamente marcati come pubblici) richiedono un token JWT nell'header:
```
Authorization: Bearer <token>
```

---

## Utenti
**Base URL**: `/api/utenti`

| Metodo | Endpoint | Descrizione | Autorizzazione | Body/Params |
|--------|----------|-------------|----------------|-------------|
| GET | `/api/utenti` | Recupera tutti gli utenti | AMMINISTRATORE | - |
| GET | `/api/utenti/{id}` | Recupera un utente per ID | AMMINISTRATORE | Path: `id` |
| GET | `/api/utenti/email/{email}` | Recupera un utente per email | AMMINISTRATORE | Path: `email` |
| GET | `/api/utenti/ruolo/{ruolo}` | Recupera utenti per ruolo | AMMINISTRATORE | Path: `ruolo` (enum) |
| GET | `/api/utenti/stato/{stato}` | Recupera utenti per stato | AMMINISTRATORE, AGENTE, VALUTATORE | Path: `stato` (enum) |
| GET | `/api/utenti/ruoli` | Lista dei ruoli disponibili (enum) | AMMINISTRATORE, AGENTE, VALUTATORE | - |
| GET | `/api/utenti/stati` | Lista degli stati disponibili (enum) | Pubblico | - |
| POST | `/api/utenti` | Crea un nuovo utente | AMMINISTRATORE | Body: `Utente` JSON |
| POST | `/api/utenti/registrati` | Registrazione nuovo proprietario | Pubblico | Body: `Utente` JSON (ruolo e stato forzati) |
| PUT | `/api/utenti/{id}` | Aggiorna un utente esistente | AMMINISTRATORE | Path: `id`, Body: `Utente` JSON |
| PATCH | `/api/utenti/{id}/password` | Aggiorna solo la password | AMMINISTRATORE o utente stesso | Path: `id`, Body: `{"newPassword": "..."}` |
| DELETE | `/api/utenti/{id}` | Elimina un utente | AMMINISTRATORE | Path: `id` |

### Esempio Body Utente
```json
{
  "nome": "Mario",
  "cognome": "Rossi",
  "email": "mario.rossi@example.com",
  "password": "securePassword123",
  "ruolo": "AGENTE",
  "telefono": "+39 333 1234567",
  "stato": "ATTIVO"
}
```

### Esempio Body Registrazione Proprietario
```json
{
  "nome": "Giuseppe",
  "cognome": "Verdi",
  "email": "giuseppe.verdi@example.com",
  "password": "securePassword123",
  "telefono": "+39 333 9876543"
}
```
**Nota**: L'endpoint `/api/utenti/registrati` forza automaticamente `ruolo: "PROPRIETARIO"` e `stato: "ATTIVO"`, indipendentemente dai valori inviati nel body.

---

## Clienti
**Base URL**: `/api/clienti`

| Metodo | Endpoint | Descrizione | Autorizzazione | Body/Params |
|--------|----------|-------------|----------------|-------------|
| GET | `/api/clienti` | Recupera tutti i clienti | AMMINISTRATORE, AGENTE, VALUTATORE | - |
| GET | `/api/clienti/{id}` | Recupera un cliente per ID | AMMINISTRATORE, AGENTE, VALUTATORE | Path: `id` |
| GET | `/api/clienti/email/{email}` | Recupera un cliente per email | AMMINISTRATORE, AGENTE, VALUTATORE | Path: `email` |
| POST | `/api/clienti/registrati` | Registrazione nuovo cliente | Pubblico | Body: `Cliente` JSON |
| PUT | `/api/clienti/{id}` | Aggiorna un cliente esistente | AMMINISTRATORE | Path: `id`, Body: `Cliente` JSON |
| DELETE | `/api/clienti/{id}` | Elimina un cliente | AMMINISTRATORE | Path: `id` |

### Esempio Body Cliente
```json
{
  "nome": "Laura",
  "cognome": "Bianchi",
  "email": "laura.bianchi@example.com",
  "telefono": "+39 333 1234567",
  "indirizzo": "Via Roma 123, Milano"
}
```

---

## Immobili
**Base URL**: `/api/immobili`

| Metodo | Endpoint | Descrizione | Autorizzazione | Body/Params |
|--------|----------|-------------|----------------|-------------|
| GET | `/api/immobili` | Recupera tutti gli immobili | Pubblico | - |
| GET | `/api/immobili/{id}` | Recupera un immobile per ID | Pubblico | Path: `id` |
| GET | `/api/immobili/{id}/contratto-esclusiva` | Genera contratto esclusiva per immobile | AMMINISTRATORE, AGENTE | Path: `id` |
| POST | `/api/immobili` | Crea un nuovo immobile | AMMINISTRATORE, AGENTE, PROPRIETARIO | Body: `Immobile` JSON |
| PUT | `/api/immobili/{id}` | Aggiorna un immobile esistente | AMMINISTRATORE, AGENTE, PROPRIETARIO | Path: `id`, Body: `Immobile` JSON |
| DELETE | `/api/immobili/{id}` | Elimina un immobile | AMMINISTRATORE | Path: `id` |

### Esempio Body Immobile
```json
{
  "id_proprietario": 5,
  "id_agente": 3,
  "titolo": "Appartamento moderno con vista mare",
  "descrizione": "Luminoso trilocale completamente ristrutturato...",
  "indirizzo": "Via Garibaldi",
  "civico": "42",
  "citta": "Napoli",
  "provincia": "NA",
  "cap": "80121",
  "quartiere": "Centro Storico",
  "tipologia": "APPARTAMENTO",
  "metri_quadri": 85.50,
  "piano": "3",
  "anno_costruzione": 1980,
  "prezzo_richiesto": 250000.00,
  "stato_immobile": "BUONO",
  "stato_annuncio": "VALUTAZIONE"
}
```

### Enum Tipologia Immobile
```
APPARTAMENTO, VILLA, CASA_INDIPENDENTE, ATTICO, LOFT, MANSARDA, RUSTICO, CASALE
```

### Enum Stato Immobile
```
OTTIMO, BUONO, DA_RISTRUTTURARE, NUOVO
```

### Esempio Response Contratto Esclusiva
```json
{
  "html": "<html>Contratto di incarico di vendita in esclusiva...</html>"
}
```

---

## Recensioni
**Base URL**: `/api/recensioni`

| Metodo | Endpoint | Descrizione | Autorizzazione | Body/Params |
|--------|----------|-------------|----------------|-------------|
| GET | `/api/recensioni` | Recupera tutte le recensioni (ordinate per data desc) | Pubblico | - |
| GET | `/api/recensioni/{id}` | Recupera una recensione per ID | Pubblico | Path: `id` |
| GET | `/api/recensioni/agente/{idAgente}` | Recupera recensioni per un agente | Pubblico | Path: `idAgente` |
| GET | `/api/recensioni/immobile/{idImmobile}` | Recupera recensioni per un immobile | Pubblico | Path: `idImmobile` |
| POST | `/api/recensioni` | Crea una nuova recensione | Utente autenticato | Body: `Recensione` JSON |
| PUT | `/api/recensioni/{id}` | Aggiorna una recensione | AMMINISTRATORE, AGENTE | Path: `id`, Body: `Recensione` JSON |
| PATCH | `/api/recensioni/{id}/verifica` | Modifica stato di verifica recensione | AMMINISTRATORE, VALUTATORE | Path: `id`, Body: `{"verificata": true/false}` |
| DELETE | `/api/recensioni/{id}` | Elimina una recensione | AMMINISTRATORE | Path: `id` |

### Esempio Body Recensione
```json
{
  "idAgente": 5,
  "idImmobile": 12,
  "voto": 5,
  "testo": "Ottimo servizio, molto professionale!",
  "verificata": false
}
```

---

## Note
**Base URL**: `/api/note`

| Metodo | Endpoint | Descrizione | Autorizzazione | Body/Params |
|--------|----------|-------------|----------------|-------------|
| GET | `/api/note` | Recupera tutte le note (ordinate per data desc) | AMMINISTRATORE, AGENTE, VALUTATORE | - |
| GET | `/api/note/{id}` | Recupera una nota per ID | AMMINISTRATORE, AGENTE, VALUTATORE | Path: `id` |
| GET | `/api/note/immobili/{idImmobile}` | Recupera note per immobile | AMMINISTRATORE, AGENTE, VALUTATORE | Path: `idImmobile`, Query: `visibilita` (opzionale) |
| GET | `/api/note/agenti/{idAgente}` | Recupera note per agente | AMMINISTRATORE, AGENTE, VALUTATORE | Path: `idAgente` |
| POST | `/api/note` | Crea una nuova nota | AMMINISTRATORE, AGENTE, VALUTATORE | Body: `Nota` JSON |
| PUT | `/api/note/{id}` | Aggiorna una nota | AMMINISTRATORE, AGENTE, VALUTATORE | Path: `id`, Body: `Nota` JSON |
| DELETE | `/api/note/{id}` | Elimina una nota | AMMINISTRATORE | Path: `id` |

### Esempio Body Nota
```json
{
  "idAgente": 3,
  "idImmobile": 8,
  "testo": "Cliente interessato, richiamare entro venerdì",
  "visibilita": "PRIVATA"
}
```

### Query Parameter per Note Immobile
```
GET /api/note/immobili/8?visibilita=PRIVATA
```

---

## FAQ
**Base URL**: `/api/faq`

| Metodo | Endpoint | Descrizione | Autorizzazione | Body/Params |
|--------|----------|-------------|----------------|-------------|
| GET | `/api/faq` | Recupera tutte le FAQ (ordinate per ordine asc) | Pubblico | - |
| GET | `/api/faq/{id}` | Recupera una FAQ per ID | Pubblico | Path: `id` |
| GET | `/api/faq/categorie` | Lista delle categorie FAQ (enum) | Pubblico | - |
| GET | `/api/faq/categoria/{categoria}` | Recupera FAQ per categoria | Pubblico | Path: `categoria` (nome enum) |
| POST | `/api/faq` | Crea una nuova FAQ | AMMINISTRATORE, AGENTE | Body: `Faq` JSON |
| PUT | `/api/faq/{id}` | Aggiorna una FAQ | AMMINISTRATORE, AGENTE | Path: `id`, Body: `Faq` JSON |
| DELETE | `/api/faq/{id}` | Elimina una FAQ | AMMINISTRATORE | Path: `id` |

### Esempio Body FAQ
```json
{
  "domanda": "Come posso pubblicare un annuncio?",
  "risposta": "Per pubblicare un annuncio devi prima registrarti come proprietario...",
  "categoria": "VENDITA",
  "ordine": 1
}
```

---

## Contact Requests (Richieste di Contatto)
**Base URL**: `/api/contatto`

| Metodo | Endpoint | Descrizione | Autorizzazione | Body/Params |
|--------|----------|-------------|----------------|-------------|
| POST | `/api/contatto` | Invia una nuova richiesta di contatto (form pubblico) | Pubblico | Body: `{ nome, cognome, email, telefono, messaggio }` |
| GET | `/api/contatto` | Recupera tutte le richieste | AMMINISTRATORE | - |
| GET | `/api/contatto/stato/{stato}` | Recupera richieste per stato (`NUOVA`, `IN_LAVORAZIONE`, `COMPLETATA`) | AMMINISTRATORE | Path: `stato` |
| PUT | `/api/contatto/{id}/in-lavorazione` | Marca la richiesta come in lavorazione | AMMINISTRATORE | Path: `id` |
| PUT | `/api/contatto/{id}/faq` | Salva la richiesta come FAQ (crea una FAQ con la domanda = messaggio) | AMMINISTRATORE | Path: `id` |
| DELETE | `/api/contatto/{id}` | Elimina una richiesta | AMMINISTRATORE | Path: `id` |

### Esempio: salvare come FAQ (curl)
```bash
curl -X PUT http://localhost:8080/api/contatto/123/faq \\
  -H "Authorization: Bearer <TOKEN_ADMIN>"
```

Quando un utente invia una richiesta con `POST /api/contatto`, il sistema invia una email di notifica all'indirizzo configurato (`app.email.agente`). Nell'email viene riportato l'ID della richiesta e un esempio `curl` per eseguire rapidamente l'azione di salvataggio come FAQ.

---

## Newsletter
**Base URL**: `/api/newsletter`

| Metodo | Endpoint | Descrizione | Autorizzazione | Body/Params |
|--------|----------|-------------|----------------|-------------|
| POST | `/api/newsletter/iscriviti` | Iscrizione alla newsletter | Pubblico | Body: `{ "email": "..." }` |
| DELETE | `/api/newsletter/disiscrivi/{email}` | Disiscrizione dalla newsletter | Pubblico | Path: `email` |

### Esempio Body Iscrizione Newsletter
```json
{
  "email": "mario.rossi@example.com"
}
```

### Esempio Response Iscrizione (Success)
```json
{
  "success": true,
  "message": "Iscrizione completata! Ti abbiamo inviato una email di conferma.",
  "data": {
    "id_newsletter": 1,
    "email": "mario.rossi@example.com",
    "data_iscrizione": "2025-12-02T10:30:00"
  }
}
```

### Esempio Response Disiscrizione (Success)
```json
{
  "success": true,
  "message": "Disiscrizione completata."
}
```

### Esempio Response (Error)
```json
{
  "success": false,
  "message": "Email già iscritta alla newsletter"
}
```

---

## Quick FAQ Creation (Click-to-Create)
**Base URL**: `/api/faq`

| Metodo | Endpoint | Descrizione | Autorizzazione | Body/Params |
|--------|----------|-------------|----------------|-------------|
| POST | `/api/faq/from-request/{richiestaId}` | Crea una FAQ da una richiesta di contatto | AMMINISTRATORE, AGENTE | Path: `richiestaId`, Body: `{ risposta, categoria }` |

### Endpoint Details
- **URL**: `POST /api/faq/from-request/{richiestaId}`
- **Authentication**: Bearer Token (JWT)
- **Authorization**: AMMINISTRATORE, AGENTE
- **Path Parameter**: `richiestaId` (Integer) - ID della richiesta di contatto
- **Request Body**: JSON object with required fields
- **Response**: JSON object with created FAQ details

### Esempio Body (Click-to-Create FAQ)
```json
{
  "risposta": "Per pubblicare un annuncio devi prima registrarti come proprietario e verificare la tua identità. Puoi farlo seguendo questi passaggi...",
  "categoria": "VENDITA"
}
```

### Esempio Response (Success)
```json
{
  "success": true,
  "message": "FAQ creata con successo dalla richiesta",
  "data": {
    "id_faq": 42,
    "categoria": "VENDITA",
    "domanda": "Come posso pubblicare un annuncio?",
    "risposta": "Per pubblicare un annuncio devi prima registrarti come proprietario e verificare la tua identità. Puoi farlo seguendo questi passaggi...",
    "ordine": 0
  }
}
```

### Esempio Response (Error - Categoria non valida)
```json
{
  "error": "Categoria non valida: INVALIDA"
}
```

### Esempio Response (Error - Risposta mancante)
```json
{
  "error": "Risposta è richiesta"
}
```

### Test via cURL per prototipizzazione
```bash
# 1. Get a list of contact requests to find a richiestaId
curl -X GET http://localhost:8080/api/contatto \
  -H "Authorization: Bearer <TOKEN_ADMIN>"

# 2. Create a FAQ from request with ID 5
curl -X POST http://localhost:8080/api/faq/from-request/5 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <TOKEN_ADMIN>" \
  -d '{
    "risposta": "La risposta alla domanda è...",
    "categoria": "GENERALE"
  }'

# 3. Verify the FAQ was created
curl -X GET http://localhost:8080/api/faq \
  -H "Authorization: Bearer <TOKEN_ADMIN>"
```

=======
| POST | `/api/contatto` | Crea una nuova richiesta di contatto | Pubblico | Body: `{"nome": "...", "cognome": "...", "email": "...", "telefono": "...", "messaggio": "..."}` |
| GET | `/api/contatto` | Recupera tutte le richieste di contatto | AMMINISTRATORE, AGENTE | - |
| GET | `/api/contatto/stato/{stato}` | Recupera richieste per stato | AMMINISTRATORE, AGENTE | Path: `stato` |
| PUT | `/api/contatto/{id}/in-lavorazione` | Marca richiesta come in lavorazione | AMMINISTRATORE, AGENTE | Path: `id` |
| PUT | `/api/contatto/{id}/faq` | Salva richiesta come FAQ | AMMINISTRATORE, AGENTE | Path: `id` |
| DELETE | `/api/contatto/{id}` | Elimina richiesta | AMMINISTRATORE | Path: `id` |

### Esempio Body Richiesta Contatto
```json
{
  "nome": "Mario",
  "cognome": "Rossi",
  "email": "mario.rossi@example.com",
  "telefono": "+39 333 1234567",
  "messaggio": "Vorrei informazioni su un appartamento in centro."
}
```

### Esempio Response Richiesta Contatto
```json
{
  "success": true,
  "message": "Richiesta inviata! Ti contatteremo presto.",
  "data": {
    "id": 1,
    "nome": "Mario",
    "cognome": "Rossi",
    "email": "mario.rossi@example.com",
    "telefono": "+39 333 1234567",
    "messaggio": "Vorrei informazioni su un appartamento in centro.",
    "stato": "NUOVA",
    "dataCreazione": "2025-12-02T10:00:00Z"
  }
}
```

## Newsletter
**Base URL**: `/api/newsletter`

| Metodo | Endpoint | Descrizione | Autorizzazione | Body/Params |
|--------|----------|-------------|----------------|-------------|
| POST | `/api/newsletter/iscriviti` | Iscrivi email alla newsletter | Pubblico | Body: `{"email": "..."}` |
| DELETE | `/api/newsletter/disiscrivi/{email}` | Disiscrivi email dalla newsletter | Pubblico | Path: `email` |

### Esempio Body Iscrizione Newsletter
```json
{
  "email": "user@example.com"
}
```

### Esempio Response Iscrizione Newsletter
```json
{
  "success": true,
  "message": "Iscrizione alla newsletter effettuata con successo."
}
```

## Valutazione
**Base URL**: `/api/valutazione`

| Metodo | Endpoint | Descrizione | Autorizzazione | Body/Params |
|--------|----------|-------------|----------------|-------------|
| POST | `/api/valutazione/automatica` | Valutazione automatica immobile | Pubblico | Body: `RichiestaValutazioneDTO` |

### Esempio Body Valutazione Automatica
```json
{
  "provincia": "TO",
  "cap": "10121",
  "indirizzo": "Via Garibaldi",
  "civico": "45",
  "metriQuadri": 85.0,
  "tipologia": "APPARTAMENTO",
  "statoImmobile": "BUONO",
  "piano": "3",
  "locali": 4,
  "bagni": 2,
  "annoCost": 1980,
  "ascensore": true,
  "parcheggio": false,
  "postiAuto": 0,
  "garage": false,
  "balconeMq": 8.0,
  "terrazzoMq": null,
  "giardinoMq": null,
  "cantina": true,
  "arredato": false,
  "ariaCondizionata": true,
  "allarme": false,
  "riscaldamento": "CENTRALIZZATO",
  "classeEnergetica": "C",
  "orientamento": "SUD_EST"
}
```

### Esempio Response Valutazione Automatica
```json
{
  "valoreStimato": 285000.00,
  "valoreMin": 270000.00,
  "valoreMax": 300000.00,
  "zonaOMI": "Torino Centro",
  "coefficienteZona": 1.2,
  "dataValutazione": "2025-12-02T10:00:00Z"
}
```
---

## Note Tecniche

### Formati Date/Timestamp
- Tutte le date sono in formato ISO 8601: `2025-11-12T14:30:00Z`

### Gestione Errori
Le risposte di errore seguono questo formato:
```json
{
  "timestamp": "2025-11-12T14:30:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Descrizione dettagliata dell'errore",
  "path": "/api/utenti/123"
}
```

### Codici di Stato HTTP
- `200 OK`: Richiesta completata con successo
- `201 Created`: Risorsa creata con successo
- `204 No Content`: Operazione completata (es. DELETE)
- `400 Bad Request`: Dati inviati non validi
- `401 Unauthorized`: Token mancante o non valido
- `403 Forbidden`: Permessi insufficienti
- `404 Not Found`: Risorsa non trovata
- `500 Internal Server Error`: Errore del server

### Enum

#### Ruolo
```
AMMINISTRATORE, AGENTE, VALUTATORE, CLIENTE, PROPRIETARIO
```

#### Stato
```
ATTIVO, INATTIVO, SOSPESO, ELIMINATO
```

#### CategoriaFaq
```
GENERALE, VENDITA, ACQUISTO, VALUTAZIONE, CONTATTI
```

#### VisibilitaNota
```
PRIVATA, CONDIVISA, PUBBLICA
```

**Versione Documento**: 1.2  
**Data Ultimo Aggiornamento**: 2 Dicembre 2025
