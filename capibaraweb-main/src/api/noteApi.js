import { apiGet, apiPost, apiPut, apiDelete } from "./apiClient";

export const getNote = () => apiGet("/note", true);
export const getNotaById = (id) => apiGet(`/note/${id}`, true);
export const getNoteByImmobile = (idImmobile, visibilita) =>
  apiGet(`/note/immobili/${idImmobile}?visibilita=${visibilita}`, true);

export const getNoteByAgente = (idAgente) => apiGet(`/note/agenti/${idAgente}`, true);

export const creaNota = (data) => apiPost("/note", data, true);
export const aggiornaNota = (id, data) => apiPut(`/note/${id}`, data, true);

export const eliminaNota = (id) => apiDelete(`/note/${id}`, true);
