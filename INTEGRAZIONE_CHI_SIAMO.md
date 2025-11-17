# Integrazione Frontend-Backend: Pagina Chi Siamo

## Architettura

### Frontend (React)
```
Frontend
├── src/pages/ChiSiamo/
│   ├── chisiamo.jsx (Componente React - AGGIORNATO)
│   └── chisiamo.css (Stili - NUOVO)
└── src/services/
    └── chiSiamoService.js (Servizio API - NUOVO)
```

### Backend (Spring Boot)
```
Backend
└── src/main/java/Immobiliaris/Progetto_Rooftop/Controller/
    └── ControllerChiSiamo.java (Controller REST - NUOVO)
```

---

## Flusso di Comunicazione

### 1. **Pagina ChiSiamo (chisiamo.jsx)**
   - Componente React che gestisce lo stato e il ciclo di vita
   - Effettua il fetch dei dati tramite `chiSiamoService`
   - Mostra loading, errori e i dati recuperati
   - **Endpoints utilizzati**:
     - `GET /api/chi-siamo` - Informazioni generali
     - `GET /api/chi-siamo/missione` - Missione e visione
     - `GET /api/chi-siamo/valori` - Valori aziendali
     - `GET /api/chi-siamo/team` - Dati del team

### 2. **Servizio ChiSiamo (chiSiamoService.js)**
   - Intermediario tra il componente React e il backend
   - Gestisce le chiamate HTTP
   - Utilizza `import.meta.env.VITE_API_URL` per la base URL
   - Implementa gestione errori robusta
   - **Metodi**:
     - `getChiSiamo()` - Recupera info generali
     - `getTeam()` - Recupera dati team
     - `getMissione()` - Recupera missione
     - `getValori()` - Recupera valori aziendali

### 3. **Controller Backend (ControllerChiSiamo.java)**
   - REST Controller di Spring Boot
   - Base URL: `/api/chi-siamo`
   - CORS abilitato per tutte le origini
   - **Endpoints**:
     - `GET /` - Informazioni generali
     - `GET /missione` - Dati missione
     - `GET /valori` - Valori aziendali
     - `GET /team` - Dati team
     - `GET /test` - Test di connessione

---

## Installazione e Configurazione

### Frontend

#### 1. Configurare le variabili d'ambiente
Creare un file `.env` nella root del progetto frontend:
```bash
VITE_API_URL=http://localhost:8080/api
VITE_API_TIMEOUT=10000
VITE_CHI_SIAMO_ENABLED=true
```

#### 2. Verificare le dipendenze
Il progetto utilizza React già installato. Non sono necessarie librerie aggiuntive.

#### 3. Avviare il server di sviluppo
```bash
npm install
npm run dev
```

### Backend

#### 1. Verificare che Spring Boot sia configurato
Il controller utilizza annotazioni standard di Spring:
- `@RestController` - Per esporre endpoint REST
- `@RequestMapping` - Per mappare il percorso base
- `@GetMapping` - Per gli endpoint GET
- `@CrossOrigin` - Per gestire CORS

#### 2. Compilare e avviare
```bash
mvn clean install
mvn spring-boot:run
```

---

## Test di Connessione

### 1. Test Backend
```bash
curl http://localhost:8080/api/chi-siamo/test
```
Risposta attesa:
```json
{
  "status": "success",
  "message": "Connessione al backend Chi Siamo stabilita con successo"
}
```

### 2. Test Frontend
Aprire la pagina http://localhost:5173/chi-siamo (o la porta indicata da Vite)

---

## Struttura dei Dati Ritornati

### Chi Siamo
```json
{
  "titolo": "La Nostra Storia",
  "descrizione": "Descrizione dell'azienda"
}
```

### Missione
```json
{
  "titolo": "La Nostra Missione",
  "descrizione": "Descrizione della missione",
  "visione": "Descrizione della visione"
}
```

### Valori
```json
[
  {
    "id": 1,
    "titolo": "Trasparenza",
    "descrizione": "Descrizione del valore"
  }
]
```

### Team
```json
[
  {
    "id": 1,
    "nome": "Marco Rossi",
    "ruolo": "CEO & Founder",
    "biografia": "Bio",
    "foto": "/images/team/marco.jpg"
  }
]
```

---

## Gestione Errori

### Frontend
- **Loading**: Mostra "Caricamento in corso..."
- **Errore di rete**: Mostra messaggio di errore generico
- **Dati mancanti**: Visualizza solo le sezioni con dati disponibili

### Backend
- Errori HTTP: Status code 400+ con messaggi descrittivi
- Timeout CORS: Richieste bloccate (abilitato per tutte le origini)

---

## Prossimi Passi (Opzionali)

1. **Persistenza dati**: Collegare a database per dati dinamici
2. **Admin Panel**: Creare una pagina admin per modificare i contenuti
3. **Internazionalizzazione**: Aggiungere supporto multilingue
4. **SEO**: Aggiungere meta tags e structured data
5. **Cache**: Implementare caching dei dati nel frontend

---

## File Modificati/Creati

| File | Tipo | Descrizione |
|------|------|-------------|
| `src/pages/ChiSiamo/chisiamo.jsx` | Modificato | Aggiunta integrazione API |
| `src/pages/ChiSiamo/chisiamo.css` | Creato | Stili della pagina |
| `src/services/chiSiamoService.js` | Creato | Servizio API |
| `src/main/java/.../ControllerChiSiamo.java` | Creato | Controller backend |
| `.env.example` | Creato | Configurazione d'esempio |

---

## Verifica

Per verificare che tutto funziona correttamente:

1. ✅ Accertarsi che il backend sia in esecuzione
2. ✅ Accertarsi che il frontend sia in esecuzione
3. ✅ Navigare a `/chi-siamo`
4. ✅ Verificare che i dati vengono caricati
5. ✅ Controllare la console per eventuali errori
