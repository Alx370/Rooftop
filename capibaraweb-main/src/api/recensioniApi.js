import { apiGet, apiPost, apiPut, apiDelete } from "./apiClient";

export const getRecensioni = () => apiGet("/recensioni");
export const getRecensioneById = (id) => apiGet(`/recensioni/${id}`);
export const getRecensioniByAgente = (idAgente) => apiGet(`/recensioni/agente/${idAgente}`);
export const getRecensioniByImmobile = (idImmobile) => apiGet(`/recensioni/immobile/${idImmobile}`);

export const creaRecensione = (data) => apiPost("/recensioni", data, true);
export const aggiornaRecensione = (id, data) => apiPut(`/recensioni/${id}`, data, true);

export const verificaRecensione = (id, stato) =>
  apiPut(`/recensioni/${id}/verifica`, { verificata: stato }, true);

export const eliminaRecensione = (id) => apiDelete(`/recensioni/${id}`, true);
