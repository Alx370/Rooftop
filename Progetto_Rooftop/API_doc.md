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

### Enum Stato Annuncio
```
VALUTAZIONE, PUBBLICATO, VENDUTO, RITIRATO, SOSPESO
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

**Versione Documento**: 1.1  
**Data Ultimo Aggiornamento**: 17 Novembre 2025
