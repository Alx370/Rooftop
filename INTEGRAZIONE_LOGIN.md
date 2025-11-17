# Integrazione Frontend-Backend: Pagina Login

## Architettura

### Frontend (React)
```
Frontend
├── src/
│   ├── pages/Login/
│   │   └── login.jsx (Componente React - AGGIORNATO)
│   ├── services/
│   │   └── authService.js (Servizio Autenticazione - NUOVO)
│   ├── context/
│   │   └── AuthContext.jsx (Context Autenticazione - NUOVO)
│   └── components/
│       └── ProtectedRoute.jsx (Componente Route Protetta - NUOVO)
```

### Backend (Spring Boot)
```
Backend
└── src/main/java/Immobiliaris/Progetto_Rooftop/
    └── Security/
        ├── AuthController.java (Già esistente)
        └── ControllerAuthExtended.java (Controller Esteso - NUOVO)
```

---

## Flusso di Autenticazione

### 1. **Login Flow**

```
┌─────────────────────────────────────────────────────┐
│ 1. Utente inserisce email e password in login.jsx   │
└─────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────┐
│ 2. handleSubmit chiama authService.login()          │
└─────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────┐
│ 3. POST /api/auth/login (AuthController)            │
│    Body: { email, password }                        │
└─────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────┐
│ 4. Backend verifica credenziali e genera JWT        │
│    Response: { token: "eyJhbGc..." }               │
└─────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────┐
│ 5. Token salvato in localStorage/sessionStorage     │
└─────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────┐
│ 6. GET /api/auth/me per recuperare info utente      │
│    Header: Authorization: Bearer <token>            │
└─────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────┐
│ 7. Dati utente salvati e utente reindirizzato a /   │
└─────────────────────────────────────────────────────┘
```

### 2. **Logout Flow**

```
┌────────────────────────────────────────────┐
│ 1. Utente clicca logout                    │
└────────────────────────────────────────────┘
                   ↓
┌────────────────────────────────────────────┐
│ 2. authService.logout() richiamato         │
└────────────────────────────────────────────┘
                   ↓
┌────────────────────────────────────────────┐
│ 3. POST /api/auth/logout (AuthController)  │
│    Header: Authorization: Bearer <token>   │
└────────────────────────────────────────────┘
                   ↓
┌────────────────────────────────────────────┐
│ 4. Token rimosso da localStorage/sessionSt │
└────────────────────────────────────────────┘
                   ↓
┌────────────────────────────────────────────┐
│ 5. Utente reindirizzato a /login           │
└────────────────────────────────────────────┘
```

### 3. **Verificazione Token**

```
┌─────────────────────────────────────────────┐
│ 1. Ad ogni caricamento, AuthProvider        │
│    controlla il token salvato               │
└─────────────────────────────────────────────┘
                     ↓
┌─────────────────────────────────────────────┐
│ 2. Se token esiste: GET /api/auth/me        │
│    Header: Authorization: Bearer <token>    │
└─────────────────────────────────────────────┘
                     ↓
┌─────────────────────────────────────────────┐
│ 3. Se valido: user state aggiornizzato      │
│    Se invalido: token rimosso, logout       │
└─────────────────────────────────────────────┘
```

---

## Componenti

### 1. **Pagina Login (login.jsx)**
   - Form email/password
   - Bottone "Ricordami" (salva in localStorage)
   - Visualizzazione errori
   - Loading state
   - Integrazione con authService

### 2. **Servizio Autenticazione (authService.js)**
   - `login(email, password)` - Autentica utente
   - `logout()` - Effettua logout
   - `getCurrentUser()` - Recupera info utente
   - `getToken()` - Ottiene token salvato
   - `isAuthenticated()` - Verifica autenticazione
   - `saveToken()` - Salva token
   - `saveUser()` - Salva dati utente
   - `getUser()` - Recupera dati utente
   - `verifyToken()` - Valida il token

### 3. **Auth Context (AuthContext.jsx)**
   - Gestisce lo stato globale di autenticazione
   - Fornisce hook `useAuth()` per accedere ai dati
   - Effettua verifica autenticazione al load
   - Metodi: `login()`, `logout()`, `refreshUser()`

### 4. **Protected Route (ProtectedRoute.jsx)**
   - Protegge le rotte autenticate
   - Verifica ruoli (opzionale)
   - Reindirizza a /login se non autenticato
   - Loading state durante verifica

---

## Endpoint API

### Backend (AuthController.java)

| Metodo | Endpoint | Descrizione | Autorizzazione |
|--------|----------|-------------|----------------|
| POST | `/api/auth/login` | Login utente | Pubblico |
| POST | `/api/auth/logout` | Logout utente | Pubblico |
| GET | `/api/auth/me` | Info utente autenticato | Autenticato |

### Backend Esteso (ControllerAuthExtended.java)

| Metodo | Endpoint | Descrizione | Autorizzazione |
|--------|----------|-------------|----------------|
| GET | `/api/auth/status` | Stato autenticazione | Pubblico |
| POST | `/api/auth/validate-token` | Valida token | Pubblico |
| GET | `/api/auth/token-info` | Info token | Autenticato |
| GET | `/api/auth/test` | Test connessione | Pubblico |

---

## Struttura dei Dati

### Login Request
```json
{
  "email": "user@example.com",
  "password": "password123"
}
```

### Login Response
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### Get Me Response
```json
{
  "authenticated": true,
  "nome": "Mario",
  "cognome": "Rossi",
  "email": "mario.rossi@example.com",
  "telefono": "+39 333 1234567",
  "stato": "ATTIVO",
  "authorities": [
    {
      "authority": "ROLE_AGENTE"
    }
  ]
}
```

### Auth Status Response
```json
{
  "authenticated": true,
  "principal": "1",
  "authorities": [
    {
      "authority": "ROLE_AGENTE"
    }
  ]
}
```

---

## Utilizzo nel Progetto

### 1. **Setup AuthProvider in main.jsx**

```jsx
import { AuthProvider } from './context/AuthContext';
import App from './App';

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <AuthProvider>
      <App />
    </AuthProvider>
  </React.StrictMode>
);
```

### 2. **Utilizzo useAuth() in Componenti**

```jsx
import { useAuth } from '../context/AuthContext';

function Header() {
  const { user, isAuthenticated, logout } = useAuth();

  return (
    <header>
      {isAuthenticated && <span>Ciao {user?.nome}!</span>}
      {isAuthenticated && <button onClick={logout}>Logout</button>}
    </header>
  );
}
```

### 3. **Proteggere Rotte**

```jsx
import { ProtectedRoute } from '../components/ProtectedRoute';
import DashboardAdmin from '../pages/DashboardAdmin';

<Route 
  path="/admin" 
  element={
    <ProtectedRoute requiredRole="AMMINISTRATORE">
      <DashboardAdmin />
    </ProtectedRoute>
  }
/>
```

---

## Gestione Errori

### Frontend

| Errore | Gestione | Azione |
|--------|----------|--------|
| Credenziali non valide | Message visualizzato | Rimanere in login |
| Token scaduto | Logout automatico | Reindirizzare a /login |
| Network error | Messaggio generico | Riprova |
| Unauthorized (401) | Logout e redirect | Reindirizzare a /login |

### Backend

| Status | Messaggio | Causa |
|--------|-----------|-------|
| 200 OK | Token/Dati | Success |
| 400 Bad Request | Credenziali non valide | Email/password errati |
| 401 Unauthorized | Token mancante/invalido | Autenticazione richiesta |
| 500 Internal Server Error | Errore interno | Errore server |

---

## Token JWT

### Payload
```json
{
  "sub": "1",
  "ruolo": "AGENTE",
  "email": "user@example.com",
  "iat": 1700000000,
  "exp": 1700086400
}
```

### Utilizzo
- Salvato in localStorage/sessionStorage
- Inviato in header: `Authorization: Bearer <token>`
- Verificato dal JwtAuthFilter
- Decodificato e validato dal JwtService

---

## Flusso Stato Globale (AuthContext)

```
AuthProvider (wraps app)
    ↓
[user, isAuthenticated, loading, login, logout, refreshUser]
    ↓
useAuth() hook
    ↓
Componenti figli accedono stato globale
```

---

## Testing

### Test Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","password":"password"}'
```

### Test Get User
```bash
curl -X GET http://localhost:8080/api/auth/me \
  -H "Authorization: Bearer <token>"
```

### Test Status
```bash
curl -X GET http://localhost:8080/api/auth/status
```

---

## Configurazione

### .env Frontend
```env
VITE_API_URL=http://localhost:8080/api
VITE_API_TIMEOUT=10000
```

### Backend
- JWT Secret configurato in `application.properties`
- CORS abilitato per tutte le origini
- JwtAuthFilter attivo su tutte le rotte

---

## Sicurezza

✅ JWT tokens in localStorage/sessionStorage
✅ Password encoder (BCrypt) nel backend
✅ CORS configurato correttamente
✅ Header Authorization obbligatorio
✅ Token validato in ogni richiesta
✅ Logout lato client (tokens sono stateless)
✅ Filtro JWT su tutte le rotte protette

---

## File Modificati/Creati

| File | Tipo | Descrizione |
|------|------|-------------|
| `src/pages/Login/login.jsx` | Modificato | Integrazione authService |
| `src/services/authService.js` | Creato | Servizio autenticazione |
| `src/context/AuthContext.jsx` | Creato | Context autenticazione globale |
| `src/components/ProtectedRoute.jsx` | Creato | Componente rotte protette |
| `src/main/java/.../ControllerAuthExtended.java` | Creato | Controller auth esteso |

---

## Prossimi Passi (Opzionali)

1. **Refresh Token**: Implementare refresh token per prolungare sessione
2. **Social Login**: Aggiungere login Google/Facebook
3. **Email Verification**: Verificare email all'iscrizione
4. **Password Reset**: Funzionalità reset password
5. **2FA**: Two-Factor Authentication
6. **Session Timeout**: Logout automatico dopo inattività
7. **Remember Me**: Persistenza login più sicura
