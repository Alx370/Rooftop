import axios from "axios";

// Base URL del backend - usa il proxy nginx in produzione
// In sviluppo locale usa vite proxy, in produzione nginx proxy
const API_BASE_URL = import.meta.env.MODE === 'development' 
  ? "http://localhost:8080/api"  // Sviluppo locale
  : "/api";                       // Produzione (usa nginx proxy)

// crea l'istanza axios qui (era mancante -> ReferenceError)
const api = axios.create({ baseURL: API_BASE_URL, headers: { "Content-Type": "application/json" } });

// Helper per gestire l'Authorization header
export const setAuthToken = (token) => {
  if (token) api.defaults.headers.common["Authorization"] = `Bearer ${token}`;
  else delete api.defaults.headers.common["Authorization"];
};

export const clearAuthToken = () => setAuthToken(null);


// POST /api/auth/login -> restituisce { token }
// credentials = { email, password }
export const login = async (credentials) => {
  try {
    // debug: log minimale prima della chiamata (non stampare la password)
    console.log("POST", API_BASE_URL + "/auth/login", { email: credentials.email });
    const res = await api.post("/auth/login", credentials);
    return res.data; // es. { token: '...' }
  } catch (err) {
    console.error("Errore login: status", err.response?.status, "data:", err.response?.data);
    const message = err.response?.data?.message || err.response?.data || err.message;
    throw new Error(message);
  }
};

// POST /api/utenti -> crea utente (ATTENZIONE: il backend attualmente richiede ROLE_AMMINISTRATORE)
// userData: { nome, cognome, email, password, telefono?, ruolo?, stato? }
export const createUser = async (userData) => {
  if (!userData?.nome || !userData?.cognome || !userData?.email || !userData?.password) {
    throw new Error("Compila tutti i campi obbligatori: nome, cognome, email, password");
  }

  // Non hashare la password lato client: il server si occupa dell'hashing
  const payload = {
    nome: userData.nome,
    cognome: userData.cognome,
    email: userData.email,
    password: userData.password,
    telefono: userData.telefono || null,
    // ruolo e stato sono opzionali; il server applicherà default se mancanti
    ruolo: userData.ruolo,
    stato: userData.stato,
  };

  try {
    const res = await api.post("/utenti", payload);
    return res.data; // oggetto Utente creato
  } catch (err) {
    console.error("Errore createUser:", err.response?.data || err.message);
    // mappa i messaggi più comuni
    if (err.response?.status === 409) throw new Error("Email già registrata");
    const message = err.response?.data?.message || err.response?.data || err.message;
    throw new Error(message);
  }
};

export default api;
