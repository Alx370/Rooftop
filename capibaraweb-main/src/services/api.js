import axios from "axios";

// Base URL del backend
const API_BASE_URL = "http://localhost:8080/api";

// -----------------------------
// Funzione per ottenere tutti gli utenti (GET JSON)
// -----------------------------
export const getUsers = async () => {
  try {
    const response = await axios.get(`${API_BASE_URL}/users`);
    return response.data;
  } catch (error) {
    console.error("Errore nel fetch degli utenti:", error);
    throw error;
  }
};

// -----------------------------
// Funzione per login (POST JSON)
// credentials = { email, password }
// -----------------------------
export const login = async (credentials) => {
  try {
    console.log("Login inviato:", credentials); 
    const response = await axios.post(`${API_BASE_URL}/login`, credentials, {
      headers: { "Content-Type": "application/json" },
    });
    console.log("Risposta backend login:", response.data);
    return response.data;
  } catch (error) {
    console.error("Errore nel login:", {
      message: error.message,
      status: error.response?.status,
      data: error.response?.data
    });
    throw new Error(error.response?.data?.message || "Errore nel login");
  }
};

// -----------------------------
// Funzione per creare un nuovo utente
// userData deve contenere: { nome, cognome, email, password, ruolo (opzionale) }
// -----------------------------
export const createUser = async (userData) => {
  // Validazione rapida
  if (!userData.nome || !userData.cognome || !userData.email || !userData.password) {
    throw new Error("Compila tutti i campi obbligatori");
  }

  const payload = {
    nome: userData.nome,
    cognome: userData.cognome,
    email: userData.email,
    password: userData.password,
    ruolo: userData.ruolo || "PROPRIETARIO",
    stato: "ATTIVO",
    telefono: userData.telefono || null,
  };

  console.log("Payload inviato al backend:", payload);

  try {
    const response = await axios.post(`${API_BASE_URL}/users`, payload, {
      headers: { "Content-Type": "application/json" },
    });
    return response.data;
  } catch (error) {
    console.error("Errore createUser:", error.response?.data || error.message);
    if (error.response?.status === 400 && error.response.data?.message?.includes("Email")) {
      throw new Error("Email già registrata");
    }
    throw new Error(error.response?.data?.message || "Si è verificato un errore. Riprova.");
  }
};
