// src/api/authApi.js
import { apiPost } from "./apiClient.js";

// LOGIN
export async function login(credentials) {
  return await apiPost("/auth/login", credentials);
}

// Imposta il token per future richieste (opzionale)
export function setAuthToken(token) {
  if (token) {
    localStorage.setItem("token", token);
  } else {
    localStorage.removeItem("token");
  }
}

// Info utente loggato
export async function getMe() {
  return await apiPost("/auth/me", null, true);
}
