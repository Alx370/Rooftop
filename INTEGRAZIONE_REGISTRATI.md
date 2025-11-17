# Integrazione Frontend-Backend: Pagina Registrati

## Architettura

### Frontend (React)
```
Frontend
├── src/
│   ├── pages/Registrati/
│   │   ├── registrati.jsx (Componente React - AGGIORNATO)
│   │   └── registrati.css (Stili - AGGIORNATO)
│   └── services/
│       └── registrationService.js (Servizio Registrazione - NUOVO)
```

### Backend (Spring Boot)
```
Backend
└── src/main/java/Immobiliaris/Progetto_Rooftop/
    └── Controller/
        ├── ControllerUtente.java (Endpoint /api/utenti/registrati - Già esistente)
        ├── ControllerCliente.java (Endpoint /api/clienti/registrati - Già esistente)
        └── ControllerRegistrazione.java (Controller Esteso - NUOVO)
```

---

## Flusso di Registrazione

### 1. **User Flow**

```
┌──────────────────────────────────────────────┐
│ 1. Utente compila form di registrazione      │
│    - Nome e Cognome                          │
│    - Email                                   │
│    - Password (con validazione in tempo reale)│
│    - Telefono (opzionale)                    │
└──────────────────────────────────────────────┘
                     ↓
┌──────────────────────────────────────────────┐
│ 2. Validazioni Frontend                      │
│    - Formato email                           │
│    - Complessità password                    │
│    - Corrispondenza password                 │
│    - Formato telefono                        │
└──────────────────────────────────────────────┘
                     ↓
┌──────────────────────────────────────────────┐
│ 3. POST /api/utenti/registrati               │
│    Body: { nome, cognome, email, password,   │
│            telefono }                        │
└──────────────────────────────────────────────┘
                     ↓
┌──────────────────────────────────────────────┐
│ 4. Backend                                   │
│    - Valida dati                             │
│    - Controlla email unica                   │
│    - Cripta password                         │
│    - Crea utente con ruolo PROPRIETARIO      │
└──────────────────────────────────────────────┘
                     ↓
┌──────────────────────────────────────────────┐
│ 5. Response: Utente creato                   │
└──────────────────────────────────────────────┘
                     ↓
┌──────────────────────────────────────────────┐
│ 6. Reindirizza a /login                      │
└──────────────────────────────────────────────┘
```

### 2. **Validazione Password (Real-Time)**

```
Mentre l'utente digita la password:
    ↓
Visualizza requisiti non soddisfatti:
    - ✗ Almeno 8 caratteri
    - ✗ Una lettera maiuscola
    - ✗ Una lettera minuscola
    - ✗ Un numero
    - ✗ Un carattere speciale (!@#$%^&*)
    ↓
Quando tutti i requisiti sono soddisfatti:
    - ✓ Tutti gli errori scompaiono
```

---

## Componenti

### 1. **Pagina Registrati (registrati.jsx)**
   - Form con campi: nome, email, password, conferma password, telefono
   - Validazione in tempo reale della password
   - Validazione formato email
   - Validazione formato telefono
   - Messaggi di errore/successo
   - Integrazione con registrationService

### 2. **Servizio Registrazione (registrationService.js)**
   - `registerProprietario()` - Registra proprietario
   - `registerCliente()` - Registra cliente
   - `validatePassword()` - Valida complessità password
   - `validateEmailFormat()` - Valida formato email
   - `validatePhoneFormat()` - Valida formato telefono
   - `validateEmail()` - Controlla se email esiste

### 3. **Controller Backend (ControllerRegistrazione.java)**
   - `/api/registrazione/email-disponibile/{email}` - Verifica email
   - `/api/registrazione/password-requirements` - Requisiti password
   - `/api/registrazione/validate` - Valida dati completi
   - `/api/registrazione/test` - Test connessione

---

## Endpoint API

### Backend Principale (ControllerUtente.java)

| Metodo | Endpoint | Descrizione | Autorizzazione | Body |
|--------|----------|-------------|----------------|------|
| POST | `/api/utenti/registrati` | Registra nuovo proprietario | Pubblico | { nome, cognome, email, password, telefono } |

### Backend Ausiliare (ControllerCliente.java)

| Metodo | Endpoint | Descrizione | Autorizzazione | Body |
|--------|----------|-------------|----------------|------|
| POST | `/api/clienti/registrati` | Registra nuovo cliente | Pubblico | { nome, cognome, email, telefono, indirizzo } |

### Backend Esteso (ControllerRegistrazione.java)

| Metodo | Endpoint | Descrizione | Autorizzazione |
|--------|----------|-------------|----------------|
| GET | `/api/registrazione/email-disponibile/{email}` | Verifica disponibilità email | Pubblico |
| GET | `/api/registrazione/password-requirements` | Requisiti password | Pubblico |
| POST | `/api/registrazione/validate` | Valida dati completi | Pubblico |
| GET | `/api/registrazione/test` | Test connessione | Pubblico |

---

## Struttura dei Dati

### Registrazione Request (Proprietario)
```json
{
  "nome": "Mario",
  "cognome": "Rossi",
  "email": "mario.rossi@example.com",
  "password": "SecurePass123!",
  "telefono": "+39 333 1234567"
}
```

### Registrazione Response
```json
{
  "id_utente": 5,
  "nome": "Mario",
  "cognome": "Rossi",
  "email": "mario.rossi@example.com",
  "ruolo": "PROPRIETARIO",
  "stato": "ATTIVO",
  "telefono": "+39 333 1234567",
  "createdAt": "2025-11-17T10:30:00Z"
}
```

### Email Disponibilità Response
```json
{
  "disponibile": true,
  "email": "mario.rossi@example.com"
}
```

### Password Requirements Response
```json
{
  "minLength": 8,
  "requireUppercase": true,
  "requireLowercase": true,
  "requireNumbers": true,
  "requireSpecialChars": true,
  "specialCharsAllowed": "!@#$%^&*",
  "description": "La password deve contenere almeno 8 caratteri..."
}
```

### Validate Registration Response
```json
{
  "valid": true,
  "errors": {}
}
```

---

## Regole di Validazione

### Password
- ✅ Minimo 8 caratteri
- ✅ Almeno una lettera maiuscola (A-Z)
- ✅ Almeno una lettera minuscola (a-z)
- ✅ Almeno un numero (0-9)
- ✅ Almeno un carattere speciale (!@#$%^&*)

### Email
- ✅ Formato valido (user@domain.com)
- ✅ Univoca nel sistema
- ✅ Convertita a minuscole

### Telefono (Opzionale)
- ✅ Almeno 10 cifre
- ✅ Consente spazi, trattini, + e parentesi
- ✅ Facoltativo

### Nome e Cognome
- ✅ Obbligatori
- ✅ Almeno 1 carattere

---

## Flusso Stato Frontend

```
┌─────────────────────────┐
│ formData                │
├─────────────────────────┤
│ - name                  │
│ - email                 │
│ - password              │
│ - confirmPassword       │
│ - telefono              │
└─────────────────────────┘
          ↓
┌─────────────────────────┐
│ passwordErrors[]        │ (validazione real-time)
│ emailAvailable          │ (disponibilità email)
│ error                   │ (messaggi errore)
│ success                 │ (successo)
│ loading                 │ (stato invio)
└─────────────────────────┘
```

---

## Utilizzo nel Progetto

### 1. **Importare il servizio**

```jsx
import { registrationService } from "../../services/registrationService";
```

### 2. **Registrare un proprietario**

```jsx
const handleRegister = async (formData) => {
  try {
    const user = await registrationService.registerProprietario({
      nome: "Mario",
      cognome: "Rossi",
      email: "mario.rossi@example.com",
      password: "SecurePass123!",
      telefono: "+39 333 1234567"
    });
    console.log("Utente creato:", user);
  } catch (error) {
    console.error("Errore:", error.message);
  }
};
```

### 3. **Validare password**

```jsx
const validation = registrationService.validatePassword("MyPassword123!");
if (validation.valid) {
  console.log("Password valida!");
} else {
  console.log("Errori:", validation.errors);
}
```

### 4. **Validare email format**

```jsx
const isValid = registrationService.validateEmailFormat("user@example.com");
```

---

## Gestione Errori

### Frontend

| Errore | Messaggio | Azione |
|--------|-----------|--------|
| Nome mancante | "Tutti i campi sono obbligatori" | Mostra campo focus |
| Email invalida | "Formato email non valido" | Mostra helper text |
| Password debole | "La password non rispetta i requisiti" | Mostra lista requisiti |
| Password mismatch | "Le password non coincidono" | Cancella conferma password |
| Telefono invalido | "Formato telefono non valido" | Mostra formato richiesto |
| Email duplicata | "Email già registrata" | Chiedi di inserire altra email |
| Network error | "Errore di registrazione" | Riprova |

### Backend

| Status | Messaggio | Causa |
|--------|-----------|-------|
| 201 Created | Utente creato | Success |
| 400 Bad Request | Email già registrata | Email duplicata |
| 400 Bad Request | Dati mancanti | Body incompleto |
| 500 Internal Server Error | Errore server | Errore backend |

---

## Sicurezza

✅ Validazione frontend e backend
✅ Password hashata con BCrypt
✅ Email controllata per unicità
✅ Email normalizzata (minuscole)
✅ CORS configurato
✅ Input sanitization
✅ Regole password robuste
✅ Telefono validato solo se fornito

---

## Testing

### Test Email Disponibilità
```bash
curl http://localhost:8080/api/registrazione/email-disponibile/test@example.com
```

### Test Password Requirements
```bash
curl http://localhost:8080/api/registrazione/password-requirements
```

### Test Registrazione
```bash
curl -X POST http://localhost:8080/api/utenti/registrati \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Mario",
    "cognome": "Rossi",
    "email": "mario.rossi@example.com",
    "password": "SecurePass123!",
    "telefono": "+39 333 1234567"
  }'
```

### Test Validazione Completa
```bash
curl -X POST http://localhost:8080/api/registrazione/validate \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Mario",
    "cognome": "Rossi",
    "email": "mario.rossi@example.com",
    "password": "SecurePass123!"
  }'
```

---

## Configurazione

### .env Frontend
```env
VITE_API_URL=http://localhost:8080/api
VITE_API_TIMEOUT=10000
```

### Backend
- PasswordEncoder configurato con BCrypt
- CORS abilitato per tutte le origini
- Servizio Utente con validazione email

---

## Prossimi Passi (Opzionali)

1. **Email Verification**: Inviare email di conferma
2. **OTP**: One-Time Password per verifica email
3. **Captcha**: Google reCAPTCHA per prevenire bot
4. **Terms & Conditions**: Checkbox accettazione T&C
5. **Privacy Policy**: Link e acceptazione policy
6. **Rate Limiting**: Limite tentativi registrazione per IP
7. **Password Strength Meter**: Visualizzazione graficaforza password
8. **Auto-Login**: Login automatico dopo registrazione
9. **Social Registration**: Registrazione tramite Google/Facebook
10. **Profile Completion**: Form a più step

---

## File Modificati/Creati

| File | Tipo | Descrizione |
|------|------|-------------|
| `src/pages/Registrati/registrati.jsx` | Modificato | Integrazione registrationService e validazione |
| `src/pages/Registrati/registrati.css` | Modificato | Stili validazione e messaggi |
| `src/services/registrationService.js` | Creato | Servizio registrazione |
| `src/main/java/.../ControllerRegistrazione.java` | Creato | Controller esteso registrazione |

---

## Requisiti di Complessità Password

La password deve rispettare i seguenti requisiti:

1. **Lunghezza**: Minimo 8 caratteri
2. **Maiuscole**: Almeno una lettera A-Z
3. **Minuscole**: Almeno una lettera a-z
4. **Numeri**: Almeno una cifra 0-9
5. **Speciali**: Almeno un carattere tra: !@#$%^&*

**Esempio di password valida**: `MySecret123!`

---

## Messaggi Validazione Real-Time

Durante la digitazione della password, vengono visualizzati in tempo reale:

- ✗ La password deve contenere almeno 8 caratteri
- ✗ La password deve contenere almeno una lettera maiuscola
- ✗ La password deve contenere almeno una lettera minuscola
- ✗ La password deve contenere almeno un numero
- ✗ La password deve contenere almeno un carattere speciale (!@#$%^&*)

Quando tutti i requisiti sono soddisfatti, i messaggi scompaiono.
