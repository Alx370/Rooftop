# Integrazione Frontend-Backend: Pagina FAQ

## Architettura

### Frontend (React)
```
Frontend
├── src/
│   ├── pages/FAQ/
│   │   ├── faq.jsx (Componente React - AGGIORNATO)
│   │   └── Faq.css (Stili - AGGIORNATO)
│   └── services/
│       └── faqService.js (Servizio FAQ - NUOVO)
```

### Backend (Spring Boot)
```
Backend
└── src/main/java/Immobiliaris/Progetto_Rooftop/
    └── Controller/
        └── ControllerFaq.java (Controller FAQ - AGGIORNATO)
```

---

## Flusso FAQ

### 1. **User Flow - Visualizzazione FAQ**

```
┌─────────────────────────────────────────┐
│ 1. Utente accede a /faq                 │
└─────────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────────┐
│ 2. Carica tutte le FAQ dal backend      │
│    GET /api/faq (ordinate per ordine)   │
└─────────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────────┐
│ 3. Visualizza categorie disponibili     │
│    GET /api/faq/categorie (enum)        │
└─────────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────────┐
│ 4. Utente può:                          │
│    - Cercare per testo                  │
│    - Filtrare per categoria             │
│    - Espandere domanda per risposta      │
└─────────────────────────────────────────┘
```

### 2. **Admin Flow - Gestione FAQ**

```
┌─────────────────────────────────────────┐
│ 1. Admin accede al pannello             │
└─────────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────────┐
│ 2. Operazioni disponibili:              │
│    - CREATE: POST /api/faq              │
│    - UPDATE: PUT /api/faq/{id}          │
│    - DELETE: DELETE /api/faq/{id}       │
└─────────────────────────────────────────┘
```

---

## Componenti

### 1. **Pagina FAQ (faq.jsx)**
   - Caricamento FAQ da backend
   - Filtri per categoria (bottoni dinamici)
   - Ricerca in tempo reale
   - Accordione espandibile per domanda/risposta
   - Integrazione con useAuth
   - Loading, error, empty states

### 2. **Servizio FAQ (faqService.js)**
   - `getAllFaqs()` - Recupera tutte le FAQ
   - `getFaqById(id)` - Recupera una FAQ per ID
   - `getCategories()` - Recupera categorie disponibili
   - `getFaqsByCategory(categoria)` - Filtra per categoria
   - `createFaq(data, token)` - Crea FAQ (admin)
   - `updateFaq(id, data, token)` - Aggiorna FAQ (admin)
   - `deleteFaq(id, token)` - Elimina FAQ (admin)
   - `validateFaqData()` - Valida dati
   - `sortFaqs()` - Ordina FAQ
   - `filterFaqsBySearch()` - Ricerca full-text
   - `groupFaqsByCategory()` - Raggruppa per categoria

### 3. **Controller Backend (ControllerFaq.java)**
   - GET `/api/faq` - Tutte le FAQ
   - GET `/api/faq/{id}` - FAQ singola
   - GET `/api/faq/categorie` - Categorie enum
   - GET `/api/faq/categoria/{categoria}` - Per categoria
   - POST `/api/faq` - Crea FAQ (admin)
   - PUT `/api/faq/{id}` - Aggiorna FAQ (admin)
   - DELETE `/api/faq/{id}` - Elimina FAQ (admin)
   - GET `/api/faq/stats` - Statistiche
   - GET `/api/faq/test` - Test connessione

---

## Endpoint API

| Metodo | Endpoint | Descrizione | Autorizzazione | Body |
|--------|----------|-------------|----------------|------|
| GET | `/api/faq` | Recupera tutte le FAQ ordinate | Pubblico | - |
| GET | `/api/faq/{id}` | Recupera una FAQ per ID | Pubblico | - |
| GET | `/api/faq/categorie` | Lista categorie (enum) | Pubblico | - |
| GET | `/api/faq/categoria/{categoria}` | FAQ per categoria | Pubblico | Path: `categoria` |
| POST | `/api/faq` | Crea nuova FAQ | AMMINISTRATORE, AGENTE | { domanda, risposta, categoria, ordine } |
| PUT | `/api/faq/{id}` | Aggiorna FAQ | AMMINISTRATORE, AGENTE | Path: `id`, Body: { dati aggiornati } |
| DELETE | `/api/faq/{id}` | Elimina FAQ | AMMINISTRATORE | Path: `id` |
| GET | `/api/faq/stats` | Statistiche FAQ | Pubblico | - |
| GET | `/api/faq/test` | Test connessione | Pubblico | - |

---

## Struttura dei Dati

### Modello FAQ (Backend - Entity)
```java
@Entity
@Table(name = "faq")
public class Faq {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_faq;
    
    @Column(nullable = false)
    private String domanda;
    
    @Column(nullable = false, length = 2000)
    private String risposta;
    
    @Enumerated(EnumType.STRING)
    private CategoriaFaq categoria;
    
    private Integer ordine;
    
    // Timestamp
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
```

### Richiesta Creazione FAQ
```json
{
  "domanda": "Come posso pubblicare un annuncio?",
  "risposta": "Per pubblicare un annuncio devi prima registrarti come proprietario...",
  "categoria": "PROCESSO",
  "ordine": 1
}
```

### Response Creazione FAQ
```json
{
  "id_faq": 1,
  "domanda": "Come posso pubblicare un annuncio?",
  "risposta": "Per pubblicare un annuncio devi prima registrarti come proprietario...",
  "categoria": "PROCESSO",
  "ordine": 1,
  "createdAt": "2025-11-17T10:30:00Z",
  "updatedAt": "2025-11-17T10:30:00Z"
}
```

### Categorie Disponibili
```json
[
  "VENDITA",
  "AFFITTO",
  "VALUTAZIONE",
  "PROCESSO",
  "PAGAMENTO",
  "GENERALI"
]
```

### Response Tutte le FAQ
```json
[
  {
    "id_faq": 1,
    "domanda": "Come posso registrarmi?",
    "risposta": "Clicca su 'Registrati' e compila il modulo...",
    "categoria": "GENERALI",
    "ordine": 1
  },
  {
    "id_faq": 2,
    "domanda": "Qual è la commissione?",
    "risposta": "La commissione è del 5% sul prezzo di vendita...",
    "categoria": "PAGAMENTO",
    "ordine": 2
  }
]
```

---

## Validazioni

### Frontend (faqService.js)

```javascript
// Domanda: 5-500 caratteri
// Risposta: 10-5000 caratteri
// Categoria: Una delle categorie enum
// Ordine: Numero non negativo opzionale
```

### Backend (ControllerFaq.java)

```java
// @PreAuthorize: Ruoli AMMINISTRATORE, AGENTE
// Validazione domini, range, formato
// Gestione eccezioni con messaggi utente
```

---

## Funzionalità Frontend

### ✅ Ricerca
- Input search in tempo reale
- Cerca sia domanda che risposta
- Case-insensitive

### ✅ Filtri Categoria
- Pulsanti categoria dinamici
- Caricati da backend
- Toggle "Tutti" + categorie singole
- Stato attivo/inattivo

### ✅ Accordione
- Espandi/collassa domanda
- Animazione slide-down
- Tag categoria nella risposta
- Icona +/- dinamica

### ✅ Loading State
- Spinner durante caricamento
- Messaggio "Caricamento FAQ in corso..."

### ✅ Error State
- Alert rosso con errore
- Pulsante "Riprova"
- Retry della richiesta

### ✅ Empty State
- Messaggio quando nessuna FAQ trovata
- Suggerimento di modificare filtri

### ✅ Counter
- Conta domande trovate
- "X domande trovate"

---

## Utilizzo nel Progetto

### 1. **Importare il servizio**

```jsx
import { faqService } from "../../services/faqService";
import { useAuth } from "../../context/AuthContext";
```

### 2. **Caricare tutte le FAQ**

```jsx
useEffect(() => {
  const loadFaqs = async () => {
    try {
      const faqs = await faqService.getAllFaqs();
      setFaqs(faqService.sortFaqs(faqs));
    } catch (error) {
      setError(error.message);
    }
  };
  
  loadFaqs();
}, []);
```

### 3. **Filtrare per categoria**

```jsx
const filtered = await faqService.getFaqsByCategory('VENDITA');
setFaqs(faqService.sortFaqs(filtered));
```

### 4. **Ricerca**

```jsx
const searchResults = faqService.filterFaqsBySearch(faqs, searchText);
```

### 5. **Validare FAQ**

```jsx
const validation = faqService.validateFaqData(faqData);
if (!validation.valid) {
  console.error("Errori:", validation.errors);
}
```

### 6. **Raggruppare per categoria**

```jsx
const grouped = faqService.groupFaqsByCategory(faqs);
// Output: { "VENDITA": [...], "AFFITTO": [...], ... }
```

---

## Gestione Errori

### Frontend

| Errore | Messaggio | Azione |
|--------|-----------|--------|
| Rete | "Errore nel recupero delle FAQ" | Mostra alert, offri retry |
| Non trovato | "FAQ non trovata" | Mostra messaggio |
| Categoria non valida | "Errore nel recupero per categoria" | Mostra alert |
| Validazione | "La domanda è obbligatoria" | Evidenzia campo |
| Non autenticato | "Token non disponibile" | Reindirizza a login |

### Backend

| Status | Messaggio | Causa |
|--------|-----------|-------|
| 200 OK | FAQ trovata/e | Success |
| 201 Created | FAQ creata | Success |
| 204 No Content | FAQ eliminata | Success |
| 400 Bad Request | Dati non validi | Body incompleto |
| 401 Unauthorized | Token mancante | Non autenticato |
| 403 Forbidden | Accesso negato | Ruolo insufficiente |
| 404 Not Found | FAQ non trovata | ID inesistente |
| 500 Internal Server Error | Errore server | Errore backend |

---

## Flusso Stato Frontend

```
┌─────────────────┐
│ Caricamento     │
├─────────────────┤
│ Loading = true  │
│ Fetch da API    │
└─────────────────┘
         ↓
┌─────────────────┐
│ Successo        │
├─────────────────┤
│ Faqs = data     │
│ Loading = false │
│ Error = null    │
└─────────────────┘
         ↓
┌─────────────────┐
│ Utente interagisce│
├─────────────────┤
│ Cerca/Filtra    │
│ Espandi FAQ     │
└─────────────────┘
```

---

## CSS Classes

```css
.faq-page              /* Container principale */
.faq-hero              /* Hero section */
.faq-section           /* Sezione FAQ */
.faq-filters           /* Container filtri */
.search-box            /* Box ricerca */
.search-input          /* Input ricerca */
.category-filters      /* Container categorie */
.category-btn          /* Pulsante categoria */
.faq-boxes             /* Container FAQ list */
.faq-item              /* Singola FAQ */
.faq-question          /* Domanda cliccabile */
.question-text         /* Testo domanda */
.faq-icon              /* Icona +/- */
.faq-answer            /* Risposta espanso */
.faq-category-tag      /* Tag categoria */
.faq-loading           /* Loading state */
.faq-error             /* Error state */
.faq-empty             /* Empty state */
.faq-counter           /* Counter FAQ */
```

---

## File Modificati/Creati

| File | Tipo | Descrizione |
|------|------|-------------|
| `src/pages/FAQ/faq.jsx` | Modificato | Componente React con filtri e ricerca |
| `src/pages/FAQ/Faq.css` | Modificato | Stili aggiornati con nuovi elementi |
| `src/services/faqService.js` | Creato | Servizio completo FAQ |
| `src/main/java/.../ControllerFaq.java` | Modificato | Aggiunti endpoint stats e test |

---

## Testing

### Test Tutte le FAQ
```bash
curl http://localhost:8080/api/faq
```

### Test FAQ per ID
```bash
curl http://localhost:8080/api/faq/1
```

### Test Categorie
```bash
curl http://localhost:8080/api/faq/categorie
```

### Test per Categoria
```bash
curl http://localhost:8080/api/faq/categoria/VENDITA
```

### Test Creazione FAQ (richiede token)
```bash
curl -X POST http://localhost:8080/api/faq \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "domanda": "Come funziona il pagamento?",
    "risposta": "Accettiamo bonifico, carta di credito e PayPal...",
    "categoria": "PAGAMENTO",
    "ordine": 5
  }'
```

### Test Statistiche
```bash
curl http://localhost:8080/api/faq/stats
```

### Test Connessione
```bash
curl http://localhost:8080/api/faq/test
```

---

## Prossimi Passi (Opzionali)

1. **Drag & Drop Ordinamento**: Ordinare FAQ via drag and drop
2. **Markdown Support**: Supporto markdown nelle risposte
3. **FAQ Popolari**: Tracking visualizzazioni e mostra top 5
4. **Feedback Utilità**: "È stato utile?" sotto ogni risposta
5. **AI Assistant**: Chatbot che suggerisce FAQ rilevanti
6. **Multi-lingua**: Traduzioni per FAQ in lingue diverse
7. **Rating**: Sistema di valutazione FAQ
8. **Analytics**: Dashboard con metriche utilizzo
9. **Scheduled**: Programmazione visibilità FAQ per date
10. **Correlate**: Suggerimenti FAQ correlate

---

## Sicurezza

✅ Autenticazione richiesta per create/update/delete
✅ Autorizzazione per ruoli specifici
✅ CORS configurato
✅ Input sanitization
✅ Validazione frontend e backend
✅ Token JWT in header Authorization

---

## Configurazione

### .env Frontend
```env
VITE_API_URL=http://localhost:8080/api
VITE_API_TIMEOUT=10000
```

### Backend
- SecurityConfig con JWT filter
- CORS abilitato
- ServiceFaq per gestione business logic
- CategoriaFaq enum

---

## Performance

- FAQ ordinate per default (no sorting dinamico lato client)
- Ricerca eseguita in memoria su dati già caricati
- Filtri categoria applicati sequenzialmente
- Lazy loading accordion (una domanda alla volta)
- Caching possibile con React Query (futuro)

---

## Compatibilità

- ✅ React 19.1.1+
- ✅ Spring Boot 3.x+
- ✅ Java 11+
- ✅ Browser moderni (Chrome, Firefox, Safari, Edge)
- ✅ Mobile responsive
