import { apiGet, apiPost, apiPut, apiDelete } from "./apiClient";

export const getImmobili = () => apiGet("/immobili");
export const getImmobileById = (id) => apiGet(`/immobili/${id}`);

export const creaImmobile = (data) => apiPost("/immobili", data, true);

export const aggiornaImmobile = (id, data) => apiPut(`/immobili/${id}`, data, true);

export const eliminaImmobile = (id) => apiDelete(`/immobili/${id}`, true);
