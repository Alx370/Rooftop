
import { apiGet } from "./apiClient.js";

// Ottieni TUTTI i clienti (Agente, Admin)
export function getClienti() {
  return apiGet("/clienti", true);
}

// Ottieni TUTTI gli immobili (pubblico, ma mettiamo auth)
export function getImmobili() {
  return apiGet("/immobili", true);
}

// Ottieni dati utente loggato
export function getMeAgente() {
  return apiGet("/auth/me", true);
}
