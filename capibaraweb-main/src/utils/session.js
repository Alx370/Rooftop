import { decodeJWT } from "./jwt_decoder.js";

/**
 * Recupera la sessione utente dal token JWT salvato nel localStorage.
 * @returns {Object|null} Oggetto con i dati della sessione o null se non autenticato
 */
export function getSession() {
  const token = localStorage.getItem("token");
  
  if (!token) {
    return null;
  }

  const payload = decodeJWT(token);
  
  if (!payload) {
    return null;
  }

  // Controlla se il token è scaduto
  const now = Math.floor(Date.now() / 1000);
  if (payload.exp && payload.exp < now) {
    // Token scaduto, pulisci localStorage
    clearSession();
    return null;
  }

  return {
    userId: payload.sub,
    ruolo: payload.ruolo,
    email: payload.email,
    issuedAt: payload.iat,
    expiresAt: payload.exp,
    token: token
  };
}

/**
 * Recupera solo il ruolo dell'utente.
 * @returns {string|null} Il ruolo dell'utente o null
 */
export function getRuolo() {
  const session = getSession();
  return session?.ruolo || null;
}

/**
 * Controlla se l'utente è autenticato.
 * @returns {boolean}
 */
export function isAuthenticated() {
  return getSession() !== null;
}

/**
 * Controlla se l'utente ha un ruolo specifico.
 * @param {string|string[]} ruoli - Ruolo o array di ruoli da verificare
 * @returns {boolean}
 */
export function hasRole(ruoli) {
  const session = getSession();
  if (!session) return false;
  
  if (Array.isArray(ruoli)) {
    return ruoli.includes(session.ruolo);
  }
  return session.ruolo === ruoli;
}

/**
 * Pulisce la sessione (logout).
 */
export function clearSession() {
  localStorage.removeItem("token");
  localStorage.removeItem("ruolo");
  localStorage.removeItem("email");
}

/**
 * Recupera il token JWT.
 * @returns {string|null}
 */
export function getToken() {
  return localStorage.getItem("token");
}
