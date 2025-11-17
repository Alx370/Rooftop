# Integrazione Frontend-Backend: Pagina Not Found

## Architettura

### Frontend (React)
```
Frontend
├── src/
│   ├── pages/NotFound/
│   │   ├── notfound.jsx (Componente React - AGGIORNATO)
│   │   └── notfound.css (Stili - NUOVO)
│   └── services/
│       └── notFoundService.js (Servizio NotFound - NUOVO)
```

### Backend (Spring Boot)
```
Backend
└── src/main/java/Immobiliaris/Progetto_Rooftop/
    └── Controller/
        └── ControllerErrorHandler.java (Error Handler - Opzionale)
```

---

## Flusso NotFound

### 1. **User Flow - Errore 404**

```
┌─────────────────────────────────────────┐
│ 1. Utente naviga a URL non esistente    │
│    Esempio: /pagina-inesistente         │
└─────────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────────┐
│ 2. React Router cattura il percorso     │
│    e lo indirizza a <NotFound />        │
└─────────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────────┐
│ 3. Componente carica:                   │
│    - Suggerimenti per URL                │
│    - Pagine comuni                       │
│    - Log errore nel backend              │
└─────────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────────┐
│ 4. Utente visualizza:                   │
│    - Numero errore (404, 403, 500, etc) │
│    - Messaggio personalizzato            │
│    - Ricerca pagine                      │
│    - Link di navigazione rapida          │
└─────────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────────┐
│ 5. Countdown di 10 secondi               │
│    Reindirizzamento a /                  │
│    (cancellabile da utente)              │
└─────────────────────────────────────────┘
```

### 2. **Funzionalità Feedback**

```
┌─────────────────────────────────────────┐
│ Utente clicca "Segnala il problema"     │
└─────────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────────┐
│ Form si espande con:                    │
│ - Email                                 │
│ - Messaggio                             │
│ - URL automatico                        │
└─────────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────────┐
│ POST /api/errors/feedback                │
│ Backend riceve segnalazione              │
└─────────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────────┐
│ Success message visualizzato             │
│ Form si chiude                           │
└─────────────────────────────────────────┘
```

---

## Componenti

### 1. **Pagina NotFound (notfound.jsx)**
   - Hero section con icona e numero errore
   - Countdown reindirizzamento automatico
   - Ricerca pagine in tempo reale
   - Link di navigazione rapida
   - Pulsanti azioni (home, indietro, segnala)
   - Form segnalazione errore
   - Info box con suggerimenti
   - Integrazione con useAuth
   - Logging automatico errore 404

### 2. **Servizio NotFound (notFoundService.js)**
   - `logNotFoundError(errorData)` - Log errore 404 nel backend
   - `getSuggestions(path)` - Suggerimenti personalizzati per URL
   - `getErrorMessage(statusCode)` - Messaggi per diversi status code
   - `formatUrl(url)` - Formatta URL per visualizzazione
   - `generateErrorReport(errorInfo)` - Genera rapporto errore
   - `sendErrorFeedback(feedbackData)` - Invia feedback errore
   - `getCommonPages()` - Lista pagine comuni
   - `searchPages(query, pages)` - Ricerca fuzzy match
   - `formatTime(seconds)` - Formatta tempo
   - `isValidUrl(url)` - Valida URL

### 3. **HTTP Status Codes Supportati**
   - `404` - Pagina Non Trovata (default)
   - `403` - Accesso Negato
   - `500` - Errore Server
   - `503` - Servizio Indisponibile

---

## Endpoint API

### Log Errori (Opzionale - Backend)

| Metodo | Endpoint | Descrizione | Body |
|--------|----------|-------------|------|
| POST | `/api/errors/log-404` | Log errore 404 | { url, userAgent, timestamp, userId } |
| POST | `/api/errors/feedback` | Invia feedback errore | { email, message, errorPath } |
| GET | `/api/errors/stats` | Statistiche errori | - |

---

## Struttura dei Dati

### Request Log Error
```json
{
  "url": "/pagina-inesistente",
  "userAgent": "Mozilla/5.0...",
  "timestamp": "2025-11-17T10:30:00Z",
  "userId": "user_123"
}
```

### Request Feedback
```json
{
  "email": "utente@example.com",
  "message": "Non riesco a trovare la pagina valutazione",
  "errorPath": "/valutazine"
}
```

### Messaggi Errore

```javascript
{
  404: {
    title: "Pagina Non Trovata",
    message: "La pagina che stai cercando non esiste o è stata spostata.",
    icon: "🔍"
  },
  403: {
    title: "Accesso Negato",
    message: "Non hai i permessi per accedere a questa pagina.",
    icon: "🚫"
  },
  500: {
    title: "Errore Server",
    message: "Si è verificato un errore sul server. Riproviamo più tardi.",
    icon: "⚠️"
  },
  503: {
    title: "Servizio Indisponibile",
    message: "Il servizio è temporaneamente indisponibile.",
    icon: "🔧"
  }
}
```

### Pagine Comuni
```json
[
  { "name": "Home", "path": "/", "icon": "🏠" },
  { "name": "Chi Siamo", "path": "/chi-siamo", "icon": "ℹ️" },
  { "name": "FAQ", "path": "/faq", "icon": "❓" },
  { "name": "Valutazione Immobile", "path": "/valutazione", "icon": "💎" },
  { "name": "Accedi", "path": "/login", "icon": "🔓" },
  { "name": "Registrati", "path": "/registrati", "icon": "📝" }
]
```

---

## Features Frontend

### ✅ Hero Section
- Icona animata che rimbalza
- Numero errore gigante
- Titolo e messaggio personalizzati per status code
- Countdown reindirizzamento

### ✅ Ricerca Pagine
- Input di ricerca
- Filtraggio in tempo reale
- Fuzzy matching sul nome e percorso
- Grid responsive con card pagine
- Click per navigare

### ✅ Navigazione Rapida
- Suggerimenti intelligenti basati su URL tentato
- Link rapidi a pagine comuni
- Bottoni con icone e testo

### ✅ Pulsanti Azioni
- Torna alla Home
- Pagina Precedente
- Segnala il Problema

### ✅ Form Segnalazione
- Email
- Messaggio
- URL automatico
- Feedback di successo
- Loading state durante invio

### ✅ Info Box
- Suggerimenti di navigazione
- Link a pagine alternative
- Messaggio di benvenuto personalizzato

### ✅ Responsive Design
- Mobile-first
- Desktop optimized
- Tablet optimized

---

## Utilizzo nel Progetto

### 1. **Setup in AppRoutes.jsx**

```jsx
import NotFound from "../pages/NotFound/notfound";

<Routes>
  {/* Tutte le altre route */}
  
  {/* Catch-all per 404 */}
  <Route path="*" element={<NotFound statusCode={404} />} />
</Routes>
```

### 2. **Per altri errori (optional)**

```jsx
// In error boundary o error handler
<Route path="/access-denied" element={<NotFound statusCode={403} />} />
<Route path="/server-error" element={<NotFound statusCode={500} />} />
```

### 3. **Utilizzo del Service**

```jsx
import { notFoundService } from "../../services/notFoundService";

// Suggerimenti per URL
const suggestions = notFoundService.getSuggestions("/admin");
// Output: [{ title: "Home", path: "/", icon: "🏠" }, ...]

// Ricerca pagine
const results = notFoundService.searchPages("valuta");
// Output: [{ name: "Valutazione Immobile", path: "/valutazione", icon: "💎" }]

// Messaggi errore
const msg = notFoundService.getErrorMessage(500);
// Output: { title: "Errore Server", message: "...", icon: "⚠️" }
```

---

## Funzionalità Logging (Optional)

### Request Log Errore
```bash
curl -X POST http://localhost:8080/api/errors/log-404 \
  -H "Content-Type: application/json" \
  -d '{
    "url": "/pagina-inesistente",
    "userAgent": "Mozilla/5.0...",
    "timestamp": "2025-11-17T10:30:00Z",
    "userId": "guest"
  }'
```

### Request Feedback
```bash
curl -X POST http://localhost:8080/api/errors/feedback \
  -H "Content-Type: application/json" \
  -d '{
    "email": "utente@example.com",
    "message": "Non riesco a trovare la pagina valutazione",
    "errorPath": "/valutazine"
  }'
```

---

## CSS Classes

```css
.notfound-page              /* Container principale */
.notfound-hero              /* Hero section */
.hero-content               /* Content hero */
.error-icon                 /* Icona animata */
.error-code                 /* Numero errore */
.error-title                /* Titolo errore */
.error-message              /* Messaggio errore */
.redirect-notice            /* Messaggio countdown */
.countdown                  /* Numero countdown */

.notfound-search            /* Search section */
.search-container           /* Container search */
.search-box                 /* Box input */
.search-input               /* Input ricerca */
.search-results             /* Risultati ricerca */
.pages-grid                 /* Grid pagine */
.page-card                  /* Card pagina */
.page-icon                  /* Icona pagina */
.page-name                  /* Nome pagina */
.no-results                 /* Messaggio no results */

.notfound-quick             /* Quick links section */
.quick-links                /* Container link rapidi */
.quick-link                 /* Link rapido */

.notfound-actions           /* Actions section */
.btn                        /* Bottone generico */
.btn-primary                /* Bottone primario */
.btn-secondary              /* Bottone secondario */
.btn-outline                /* Bottone outline */

.notfound-feedback          /* Feedback section */
.feedback-container         /* Container form */
.feedback-form              /* Form feedback */
.form-group                 /* Gruppo form */
.form-info                  /* Info form */
.feedback-success           /* Messaggio success */

.notfound-info              /* Info section */
.info-box                   /* Box info */
```

---

## Animazioni

```css
@keyframes bounce           /* Rimbalzo icona */
@keyframes slideDown        /* Slide down form */
@keyframes gradientShift    /* Shift gradient background */
```

---

## File Modificati/Creati

| File | Tipo | Descrizione |
|------|------|-------------|
| `src/pages/NotFound/notfound.jsx` | Modificato | Componente React completo |
| `src/pages/NotFound/notfound.css` | Creato | Stili responsivi |
| `src/services/notFoundService.js` | Creato | Servizio utilità notfound |

---

## Features Avanzate

### ✅ Countdown Automatico
- 10 secondi prima di reindirizzamento
- Cancellabile cliccando su azioni
- Display in tempo reale

### ✅ Logging Automatico
- Cattura URL tentato
- UserAgent browser
- Timestamp
- User ID (se autenticato)

### ✅ Ricerca Intelligente
- Fuzzy matching
- Case-insensitive
- Ricerca su nome e path

### ✅ Personalizzazione per URL
- URL admin → suggerimenti admin
- URL immobili → suggerimenti immobili
- URL dashboard → suggerimenti dashboard

### ✅ Error Boundaries (Optional)
- Gestione errori runtime
- Fallback UI
- Error reporting

---

## Performance

- Componente leggero (solo quello necessario)
- CSS ottimizzato
- Animazioni smooth (GPU accelerate)
- Zero dipendenze esterne
- Caricamento istantaneo

---

## Accessibilità

- ✅ Semantic HTML
- ✅ ARIA labels
- ✅ Focus indicators
- ✅ Keyboard navigation
- ✅ Color contrast compliant
- ✅ Mobile accessible

---

## Prossimi Passi (Opzionali)

1. **Error Tracking**: Integrazione con Sentry o simile
2. **Analytics**: Tracciamento errori 404 più frequenti
3. **Suggestion ML**: Machine learning per suggerimenti migliori
4. **Breadcrumb**: Percorso di navigazione utente
5. **Sitemap dinamica**: Mappa sito in realtime
6. **Error Recovery**: Suggerimenti di correzione URL
7. **Custom 5xx**: Pagine custom per errori 500
8. **Multi-lingua**: Messaggi in lingue diverse
9. **Dark Mode**: Tema scuro per errori
10. **Retry Logic**: Riprova automatica connessione

---

## Testing

### Test Navigazione
```bash
# Nel browser
http://localhost:5173/pagina-inesistente
http://localhost:5173/admin
http://localhost:5173/random-url
```

### Test Feedback
1. Clicca "Segnala il problema"
2. Compila email e messaggio
3. Clicca "Invia Segnalazione"
4. Verifica success message

### Test Countdown
1. Accedi a pagina 404
2. Vedi countdown 10s
3. Attendi reindirizzamento automatico a /

### Test Ricerca
1. Accedi a 404
2. Digita "valuta" in search
3. Vedi solo "Valutazione Immobile"

---

## Sicurezza

✅ No sensitive info visualizzate
✅ Email validata nel form
✅ CORS configurato
✅ Input sanitization
✅ Rate limiting feedback (backend)
✅ Token JWT per logging (opzionale)

---

## Configurazione

### .env Frontend
```env
VITE_API_URL=http://localhost:8080/api
VITE_API_TIMEOUT=10000
```

### Backend (Opzionale)
- Endpoint per logging errori
- Endpoint per feedback
- Rate limiting
- Database per storare errori

---

## SEO (Meta Tags - Opzionale)

```jsx
// In <head>
<title>404 - Pagina Non Trovata | Rooftop</title>
<meta name="robots" content="noindex, nofollow" />
<meta name="description" content="La pagina che stai cercando non esiste." />
```

---

## Browser Support

- ✅ Chrome 90+
- ✅ Firefox 88+
- ✅ Safari 14+
- ✅ Edge 90+
- ✅ Mobile browsers moderni

---

## Compatibilità

- ✅ React 19.1.1+
- ✅ React Router 7.x+
- ✅ Spring Boot 3.x+ (opzionale backend)
- ✅ No external dependencies (frontend)
