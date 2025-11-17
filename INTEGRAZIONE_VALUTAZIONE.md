# Integrazione Frontend-Backend: Pagina Valutazione

## Architettura

### Frontend (React)
```
Frontend
├── src/
│   ├── pages/Valutazione/
│   │   ├── valutazione.jsx (Componente React - AGGIORNATO)
│   │   └── valutazione.css (Stili - NUOVO)
│   └── services/
│       └── valuationService.js (Servizio Valutazione - NUOVO)
```

### Backend (Spring Boot)
```
Backend
└── src/main/java/Immobiliaris/Progetto_Rooftop/
    └── Controller/
        ├── ControllerImmobile.java (Endpoint immobili - Già esistente)
        └── ControllerValutazione.java (Controller Esteso - NUOVO)
```

---

## Flusso di Valutazione

### 1. **User Flow - Valutazione Immobile**

```
┌─────────────────────────────────────────┐
│ 1. Utente accede a /valutazione         │
└─────────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────────┐
│ 2. STEP 1: Compila dati generali        │
│    - Titolo, descrizione                │
│    - Localizzazione (indirizzo, CAP)    │
│    - Quartiere                          │
└─────────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────────┐
│ 3. STEP 2: Compila caratteristiche      │
│    - Tipologia, stato                   │
│    - Metratura, piano, anno             │
│    - Prezzo richiesto                   │
└─────────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────────┐
│ 4. Calcola stima prezzo                 │
│    POST /api/valutazione/stima-prezzo   │
└─────────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────────┐
│ 5. Invia valutazione                    │
│    POST /api/immobili                   │
│    (autentica con token JWT)            │
└─────────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────────┐
│ 6. Immobile creato in stato VALUTAZIONE │
└─────────────────────────────────────────┘
```

### 2. **Calcolo Stima Automatica**

```
Dati Input:
- Tipologia (VILLA, APPARTAMENTO, ecc.)
- Stato immobile (NUOVO, OTTIMO, ecc.)
- Metratura (m²)

Calcolo:
1. Base price: €1000/m²
2. Moltiplicatore stato (0.6x - 1.3x)
3. Moltiplicatore tipologia (0.5x - 1.4x)
4. Prezzo = Base × Stato × Tipologia × Metratura

Esempio:
- VILLA in OTTIMO stato, 200 m²
- Base: 1000 × 1.2 (ottimo) × 1.4 (villa) × 200
- Stima: €336,000
```

---

## Componenti

### 1. **Pagina Valutazione (valutazione.jsx)**
   - Form multi-step (2 passaggi)
   - STEP 1: Dati generali e localizzazione
   - STEP 2: Caratteristiche immobile
   - Calcolo stima automatica
   - Validazione completa
   - Integrazione con valuationService e authService

### 2. **Servizio Valutazione (valuationService.js)**
   - `getAllProperties()` - Recupera tutti gli immobili
   - `getPropertyById(id)` - Recupera immobile per ID
   - `createProperty(data, token)` - Crea nuovo immobile
   - `updateProperty(id, data, token)` - Aggiorna immobile
   - `deleteProperty(id, token)` - Elimina immobile
   - `validatePropertyData()` - Valida dati immobile
   - `estimatePrice()` - Calcola stima prezzo
   - `formatPrice()` - Formatta prezzo in valuta
   - `getPropertyTypes()` - Ottiene tipologie
   - `getPropertyStates()` - Ottiene stati
   - `getAnnouncementStates()` - Ottiene stati annuncio

### 3. **Controller Backend (ControllerValutazione.java)**
   - `/api/valutazione/tipologie` - Tipologie immobili
   - `/api/valutazione/stati` - Stati immobili
   - `/api/valutazione/stati-annuncio` - Stati annuncio
   - `/api/valutazione/stima-prezzo` - Calcola stima
   - `/api/valutazione/in-valutazione` - Immobili in valutazione
   - `/api/valutazione/per-tipologia/{tipologia}` - Per tipologia
   - `/api/valutazione/statistiche` - Statistiche
   - `/api/valutazione/test` - Test connessione

---

## Endpoint API

### Backend Principale (ControllerImmobile.java)

| Metodo | Endpoint | Descrizione | Autorizzazione | Body |
|--------|----------|-------------|----------------|------|
| POST | `/api/immobili` | Crea nuovo immobile | AMMINISTRATORE, AGENTE, PROPRIETARIO | { titolo, descrizione, indirizzo, ... } |
| GET | `/api/immobili` | Recupera tutti gli immobili | Pubblico | - |
| GET | `/api/immobili/{id}` | Recupera immobile per ID | Pubblico | - |
| PUT | `/api/immobili/{id}` | Aggiorna immobile | AMMINISTRATORE, AGENTE, PROPRIETARIO | { dati aggiornati } |
| DELETE | `/api/immobili/{id}` | Elimina immobile | AMMINISTRATORE | - |

### Backend Esteso (ControllerValutazione.java)

| Metodo | Endpoint | Descrizione | Autorizzazione |
|--------|----------|-------------|----------------|
| GET | `/api/valutazione/tipologie` | Tipologie disponibili | Pubblico |
| GET | `/api/valutazione/stati` | Stati disponibili | Pubblico |
| GET | `/api/valutazione/stati-annuncio` | Stati annuncio | Pubblico |
| POST | `/api/valutazione/stima-prezzo` | Calcola stima | Pubblico |
| GET | `/api/valutazione/in-valutazione` | Immobili in valutazione | Pubblico |
| GET | `/api/valutazione/per-tipologia/{tipologia}` | Per tipologia | Pubblico |
| GET | `/api/valutazione/statistiche` | Statistiche | Pubblico |
| GET | `/api/valutazione/test` | Test connessione | Pubblico |

---

## Struttura dei Dati

### Richiesta Creazione Immobile
```json
{
  "titolo": "Appartamento moderno con vista mare",
  "descrizione": "Luminoso trilocale completamente ristrutturato",
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
  "prezzo_richiesto": 250000,
  "stato_immobile": "BUONO",
  "stato_annuncio": "VALUTAZIONE"
}
```

### Response Creazione Immobile
```json
{
  "id_immobile": 1,
  "titolo": "Appartamento moderno con vista mare",
  "descrizione": "Luminoso trilocale completamente ristrutturato",
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
  "prezzo_richiesto": 250000,
  "stato_immobile": "BUONO",
  "stato_annuncio": "VALUTAZIONE",
  "createdAt": "2025-11-17T10:30:00Z"
}
```

### Richiesta Stima Prezzo
```json
{
  "tipologia": "VILLA",
  "stato_immobile": "OTTIMO",
  "metri_quadri": 200
}
```

### Response Stima Prezzo
```json
{
  "estimatedPrice": 336000,
  "pricePerSquareMeter": 1680,
  "formatted": "€ 336.000"
}
```

### Tipologie Disponibili
```json
[
  "APPARTAMENTO",
  "VILLA",
  "CASA_INDIPENDENTE",
  "ATTICO",
  "LOFT",
  "MANSARDA",
  "RUSTICO",
  "CASALE"
]
```

### Stati Immobili
```json
[
  "NUOVO",
  "OTTIMO",
  "BUONO",
  "DA_RISTRUTTURARE"
]
```

### Stati Annuncio
```json
[
  "VALUTAZIONE",
  "PUBBLICATO",
  "VENDUTO",
  "RITIRATO",
  "SOSPESO"
]
```

---

## Validazioni

### Campi Obbligatori
- ✅ Titolo
- ✅ Indirizzo
- ✅ Città
- ✅ Provincia
- ✅ CAP
- ✅ Tipologia
- ✅ Metri quadri (> 0)
- ✅ Anno costruzione (>= 1900)
- ✅ Prezzo richiesto (> 0)
- ✅ Stato immobile

### Campi Opzionali
- 📝 Descrizione
- 📝 Civico
- 📝 Quartiere
- 📝 Piano

---

## Moltiplicatori Prezzo

### Per Stato Immobile
| Stato | Moltiplicatore |
|-------|-----------------|
| NUOVO | 1.3x |
| OTTIMO | 1.2x |
| BUONO | 1.0x (base) |
| DA_RISTRUTTURARE | 0.6x |

### Per Tipologia
| Tipologia | Moltiplicatore |
|-----------|-----------------|
| VILLA | 1.4x |
| ATTICO | 1.3x |
| CASA_INDIPENDENTE | 1.2x |
| LOFT | 1.1x |
| APPARTAMENTO | 1.0x (base) |
| MANSARDA | 0.8x |
| CASALE | 0.7x |
| RUSTICO | 0.5x |

---

## Utilizzo nel Progetto

### 1. **Importare il servizio**

```jsx
import { valuationService } from "../../services/valuationService";
```

### 2. **Creare un nuovo immobile**

```jsx
const handleCreateProperty = async (formData) => {
  try {
    const property = await valuationService.createProperty(formData, token);
    console.log("Immobile creato:", property);
  } catch (error) {
    console.error("Errore:", error.message);
  }
};
```

### 3. **Calcolare stima prezzo**

```jsx
const estimated = valuationService.estimatePrice({
  tipologia: 'VILLA',
  stato_immobile: 'OTTIMO',
  metri_quadri: 200
});
console.log("Stima:", valuationService.formatPrice(estimated));
// Output: "€ 336.000"
```

### 4. **Validare dati immobile**

```jsx
const validation = valuationService.validatePropertyData(formData);
if (!validation.valid) {
  console.error("Errori:", validation.errors);
}
```

---

## Gestione Errori

### Frontend

| Errore | Messaggio | Azione |
|--------|-----------|--------|
| Campo mancante | "Campo è obbligatorio" | Evidenzia campo |
| Metratura <= 0 | "I metri quadri devono essere > 0" | Cancella campo |
| Anno < 1900 | "Anno non valido" | Suggerisci anno valido |
| Prezzo <= 0 | "Prezzo deve essere > 0" | Cancella campo |
| Non autenticato | "Devi accedere" | Reindirizza a login |
| Network error | "Errore nell'invio" | Mostra alert |

### Backend

| Status | Messaggio | Causa |
|--------|-----------|-------|
| 201 Created | Immobile creato | Success |
| 400 Bad Request | Dati mancanti | Body incompleto |
| 401 Unauthorized | Token mancante | Non autenticato |
| 403 Forbidden | Accesso negato | Ruolo insufficiente |
| 500 Internal Server Error | Errore server | Errore backend |

---

## Flusso Stato Frontend

```
┌─────────────────────────┐
│ Step 1 - Dati Generali  │
├─────────────────────────┤
│ - titolo                │
│ - descrizione           │
│ - localizzazione        │
└─────────────────────────┘
          ↓
┌─────────────────────────┐
│ Step 2 - Caratteristiche│
├─────────────────────────┤
│ - tipologia             │
│ - stato                 │
│ - metratura             │
│ - prezzo                │
└─────────────────────────┘
          ↓
┌─────────────────────────┐
│ Validazione Completa    │
│ Calcolo Stima           │
│ Invia Immobile          │
└─────────────────────────┘
```

---

## Features

### ✅ Form Multi-Step
- Step 1: Dati generali
- Step 2: Caratteristiche
- Navigazione avanti/indietro

### ✅ Validazione Real-Time
- Validazione campi obbligatori
- Validazione formati
- Messaggi di errore chiari

### ✅ Stima Prezzo
- Calcolo automatico
- Moltiplicatori realistici
- Formattazione valuta

### ✅ Responsive Design
- Mobile-first
- Tablet optimized
- Desktop optimized

### ✅ Messaggi Feedback
- Alert di successo
- Alert di errore
- Loading state

---

## Testing

### Test Tipologie
```bash
curl http://localhost:8080/api/valutazione/tipologie
```

### Test Stima Prezzo
```bash
curl -X POST http://localhost:8080/api/valutazione/stima-prezzo \
  -H "Content-Type: application/json" \
  -d '{
    "tipologia": "VILLA",
    "stato_immobile": "OTTIMO",
    "metri_quadri": 200
  }'
```

### Test Creazione Immobile
```bash
curl -X POST http://localhost:8080/api/immobili \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "titolo": "Villa lussuosa",
    "indirizzo": "Via Roma",
    "citta": "Milano",
    "provincia": "MI",
    "cap": "20100",
    "tipologia": "VILLA",
    "metri_quadri": 200,
    "anno_costruzione": 2020,
    "prezzo_richiesto": 500000,
    "stato_immobile": "NUOVO"
  }'
```

### Test Statistiche
```bash
curl http://localhost:8080/api/valutazione/statistiche
```

---

## Prossimi Passi (Opzionali)

1. **Galleria Foto**: Upload e gestione immagini
2. **Planimetria**: Upload planimetria immobile
3. **Video Tour**: Video 360° o tour virtuale
4. **Documenti**: Upload certificati energetici, planimetrie
5. **Storico Prezzi**: Grafico andamento prezzo
6. **Comparazione**: Confronto con immobili simili
7. **Map Integration**: Mappa interattiva
8. **Schedule Visita**: Prenotazione visite
9. **Notifiche**: Email conferma valutazione
10. **Analytics**: Dashboard vendite

---

## File Modificati/Creati

| File | Tipo | Descrizione |
|------|------|-------------|
| `src/pages/Valutazione/valutazione.jsx` | Modificato | Form multi-step per valutazione |
| `src/pages/Valutazione/valutazione.css` | Creato | Stili pagina valutazione |
| `src/services/valuationService.js` | Creato | Servizio valutazione immobili |
| `src/main/java/.../ControllerValutazione.java` | Creato | Controller esteso valutazione |

---

## Sicurezza

✅ Autenticazione richiesta per creare immobili
✅ Autorizzazione per ruoli specifici
✅ Validazione dati frontend e backend
✅ CORS configurato
✅ Input sanitization
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
- ServiceImmobile per gestione dati
