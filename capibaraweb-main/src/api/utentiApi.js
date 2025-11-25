import { apiGet, apiPost, apiPut, apiDelete } from "./apiClient";

export const getUtenti = () => apiGet("/utenti", true);
export const getUtenteById = (id) => apiGet(`/utenti/${id}`, true);
export const getUtenteByEmail = (email) => apiGet(`/utenti/email/${email}`, true);
export const getUtentiByRuolo = (ruolo) => apiGet(`/utenti/ruolo/${ruolo}`, true);
export const getUtentiByStato = (stato) => apiGet(`/utenti/stato/${stato}`, true);

export const getRuoli = () => apiGet("/utenti/ruoli", true);
export const getStati = () => apiGet("/utenti/stati");

export const creaUtente = (data) => apiPost("/utenti", data, true);
export const registraProprietario = (data) => apiPost("/utenti/registrati", data);

export const aggiornaUtente = (id, data) => apiPut(`/utenti/${id}`, data, true);
export const aggiornaPassword = (id, nuovaPassword) =>
  apiPut(`/utenti/${id}/password`, { newPassword: nuovaPassword }, true);

export const eliminaUtente = (id) => apiDelete(`/utenti/${id}`, true);
