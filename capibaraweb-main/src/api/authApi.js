
import { apiPost, apiGet } from "./apiClient.js";

// LOGIN
export async function login(credentials) {
  return await apiPost("/auth/login", credentials);
}

// SALVA TOKEN IN LOCALSTORAGE
export function setAuthToken(token) {
  if (token) localStorage.setItem("token", token);
  else localStorage.removeItem("token");
}

// INFO UTENTE AUTENTICATO
export async function getMe() {
  return await apiGet("/auth/me", true);
}
