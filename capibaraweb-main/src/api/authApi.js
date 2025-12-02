import { apiPost, apiGet } from "./apiClient.js";
import { jwtDecode } from "jwt-decode";

// LOGIN → ottieni solo il token
export async function login(credentials) {
  return apiPost("/auth/login", credentials);
}

// Decodifica JWT e pulisci i campi
export function decodeJwt(token) {
  try {
    const decoded = jwtDecode(token);

    return {
      email: decoded.sub || decoded.email || null,
      id: decoded.id_utente || decoded.id || null,
      ruolo:
        decoded.role ||
        decoded.ruolo ||
        decoded.authorities?.[0] ||
        null,
    };
  } catch (e) {
    console.error("Errore decode:", e);
    return null;
  }
}

// Ritorna l’utente dalla sessione
export function getUserFromToken() {
  const token = localStorage.getItem("token");
  if (!token) return null;

  return decodeJwt(token);
}

// Logout
export function logout() {
  localStorage.removeItem("token");
  localStorage.removeItem("ruolo");
  localStorage.removeItem("id");
}

// ESEMPIO: se vuoi chiamare /auth/me
export function getMe() {
  return apiGet("/auth/me", true);
}
