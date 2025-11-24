import { apiGet, apiPost, apiPut, apiDelete } from "./apiClient";

export const getClienti = () => apiGet("/clienti", true);
export const getClienteById = (id) => apiGet(`/clienti/${id}`, true);
export const getClienteByEmail = (email) => apiGet(`/clienti/email/${email}`, true);

export const registraCliente = (data) => apiPost("/clienti/registrati", data);

export const aggiornaCliente = (id, data) => apiPut(`/clienti/${id}`, data, true);

export const eliminaCliente = (id) => apiDelete(`/clienti/${id}`, true);
