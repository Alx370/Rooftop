# Integrazione Frontend-Backend: Pagina Home

## Architettura

### Frontend (React)
```
Frontend
└── src/
    ├── pages/Home/
    │   └── home.jsx (Componente React - AGGIORNATO)
    └── services/
        └── homeService.js (Servizio API - NUOVO)
```

### Backend (Spring Boot)
```
Backend
└── src/main/java/Immobiliaris/Progetto_Rooftop/Controller/
    ├── ControllerHome.java (Controller REST Home - NUOVO)
    ├── ControllerImmobile.java (Già esistente)
    └── ControllerRecensione.java (Già esistente)
```

---

## Flusso di Comunicazione

### 1. **Pagina Home (home.jsx)**
   - Componente React che gestisce lo stato e il ciclo di vita
   - Effettua il fetch dei dati tramite `homeService`
   - Visualizza proprietà in primo piano, recensioni, statistiche
   - Gestisce l'iscrizione alla newsletter
   - **Endpoints utilizzati**:
     - `GET /api/immobili` - Proprietà disponibili
     - `GET /api/recensioni` - Recensioni verificate
     - `GET /api/home/stats` - Statistiche piattaforma
     - `GET /api/home/services` - Servizi offerti
     - `GET /api/home/usp` - Vantaggi competitivi
     - `POST /api/home/newsletter/subscribe` - Iscrizione newsletter

### 2. **Servizio Home (homeService.js)**
   - Intermediario tra il componente React e il backend
   - Gestisce le chiamate HTTP con error handling
   - Utilizza `import.meta.env.VITE_API_URL` per la base URL
   - **Metodi**:
     - `getFeaturedProperties()` - Recupera gli immobili in primo piano
     - `getReviews()` - Recupera recensioni verificate
     - `getStats()` - Recupera statistiche
     - `getServices()` - Recupera i servizi
     - `subscribeNewsletter()` - Iscrivi alla newsletter

### 3. **Controller Backend (ControllerHome.java)**
   - REST Controller di Spring Boot
   - Base URL: `/api/home`
   - CORS abilitato per tutte le origini
   - **Endpoints**:
     - `GET /stats` - Statistiche della piattaforma
     - `GET /services` - Servizi offerti
     - `GET /usp` - Vantaggi competitivi (Unique Selling Points)
     - `POST /newsletter/subscribe` - Sottoscrizione newsletter
     - `GET /featured-properties` - Info proprietà in primo piano
     - `GET /test` - Test di connessione

### 4. **Controller Esistenti Utilizzati**
   - **ControllerImmobile.java**: `GET /api/immobili` per le proprietà
   - **ControllerRecensione.java**: `GET /api/recensioni` per le recensioni

---

## Struttura dei Dati

### Immobili
```json
{
  "id": 1,
  "titolo": "Appartamento moderno con vista mare",
  "descrizione": "Luminoso trilocale completamente ristrutturato",
  "indirizzo": "Via Garibaldi",
  "prezzo_richiesto": 250000,
  "metri_quadri": 85.50
}
```

### Recensioni (Filtrate - solo verificate)
```json
{
  "id": 1,
  "nome_cliente": "Marco Rossi",
  "commento": "Esperienza fantastica, servizio impeccabile",
  "rating": 5,
  "verificata": true
}
```

### Statistiche
```json
{
  "immobili_totali": 150,
  "valutazioni_completate": 320,
  "clienti_soddisfatti": 285,
  "agenti_esperti": 12
}
```

### Servizi
```json
[
  {
    "id": 1,
    "titolo": "Per venderlo",
    "descrizione": "Ti guidiamo nella vendita...",
    "icona": "finestra.png",
    "link": "/valutazione"
  }
]
```

### USP (Unique Selling Points)
```json
[
  {
    "id": 1,
    "titolo": "Valutazione in 72 ore",
    "sottotitolo": "Rapida, accurata, umana.",
    "descrizione": "Ti offriamo una valutazione...",
    "icona": "edificio.png"
  }
]
```

### Newsletter Subscribe
```json
{
  "success": true,
  "message": "Grazie per la sottoscrizione!",
  "email": "user@example.com"
}
```

---

## Installazione e Configurazione

### Frontend

#### 1. Configurare le variabili d'ambiente
Il file `.env.example` dovrebbe contenere:
```bash
VITE_API_URL=http://localhost:8080/api
VITE_API_TIMEOUT=10000
```

Creare un file `.env` con i valori appropriati.

#### 2. Avviare il server di sviluppo
```bash
npm install
npm run dev
```

### Backend

#### 1. Compilare il progetto
```bash
mvn clean install
```

#### 2. Avviare il server
```bash
mvn spring-boot:run
```

Il backend sarà disponibile su `http://localhost:8080`

---

## Test di Connessione

### 1. Test Backend
```bash
# Test Home controller
curl http://localhost:8080/api/home/test

# Test Immobili
curl http://localhost:8080/api/immobili

# Test Recensioni
curl http://localhost:8080/api/recensioni
```

### 2. Test Frontend
- Aprire http://localhost:5173 (o la porta indicata da Vite)
- Verificare che le sezioni della home carichino i dati
- Controllare la console del browser per eventuali errori

---

## Sezioni della Home Page e Loro Origine Dati

| Sezione | Dati | Origine | Fallback |
|---------|------|---------|----------|
| Hero Banner | Statico | Frontend (hardcoded) | N/A |
| Servizi | Dinamici | `/api/home/services` | Valori di default |
| USP (Vantaggi) | Dinamici | `/api/home/usp` | Valori di default |
| Proprietà In Primo Piano | Dinamiche | `/api/immobili` | Mostra messaggio |
| Testimoniali/Recensioni | Dinamiche | `/api/recensioni` (filtrate) | Valori di default |
| Newsletter | Form | POST `/api/home/newsletter/subscribe` | Alert di errore |

---

## Gestione Errori

### Frontend
- **Loading**: Mostra contenuti di default in attesa
- **Errore di rete**: Usa i fallback/valori di default
- **Errore API**: Registra in console, continua con fallback
- **Newsletter**: Mostra alert all'utente

### Backend
- Errori HTTP: Status code 400+ con messaggi descrittivi
- CORS: Abilitato per tutte le origini (`*`)
- Timeout: Gestito lato client con fallback

---

## Prossimi Passi (Opzionali)

1. **Persistenza Newsletter**: Salvare email in database
2. **Filtri Immobili**: Aggiungere ricerca/filtri avanzati
3. **Paginazione**: Implementare paginazione per proprietà
4. **Caching**: Cache client-side per statistiche e servizi
5. **Real-time Updates**: WebSocket per aggiornamenti live
6. **Analytics**: Tracciare click e interazioni

---

## File Modificati/Creati

| File | Tipo | Descrizione |
|------|------|-------------|
| `src/pages/Home/home.jsx` | Modificato | Aggiunta integrazione API |
| `src/services/homeService.js` | Creato | Servizio API Home |
| `src/main/java/.../ControllerHome.java` | Creato | Controller backend Home |

---

## Variabili d'Ambiente Frontend

```env
# .env
VITE_API_URL=http://localhost:8080/api
VITE_API_TIMEOUT=10000
VITE_CHI_SIAMO_ENABLED=true
```

---

## Endpoint Disponibili

### Public (Pubblici)
- `GET /api/immobili` - Lista proprietà
- `GET /api/recensioni` - Lista recensioni
- `GET /api/home/stats` - Statistiche
- `GET /api/home/services` - Servizi
- `GET /api/home/usp` - Vantaggi
- `POST /api/home/newsletter/subscribe` - Newsletter

### Protected (Protetti)
- Nessuno richiede autenticazione sulla home page

---

## Verifiche Finali

✅ Pagina Home carica correttamente
✅ Sezione testimoniali mostra recensioni dal backend
✅ Form newsletter funziona
✅ Fallback visibili quando API non disponibile
✅ Console browser senza errori critici
✅ CORS funzionante tra frontend e backend
