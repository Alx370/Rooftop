import { apiGet, apiPost, apiPut, apiDelete } from "./apiClient";

export const getImmobili = () => apiGet("/immobili");
export const getImmobileById = (id) => apiGet(`/immobili/${id}`);

export const creaImmobile = (data) => {
	const payload = { ...data };
	if (payload && payload.tipologia) payload.tipologia = String(payload.tipologia).toUpperCase();
	return apiPost("/immobili", payload, true);
};

export const aggiornaImmobile = (id, data) => {
	const payload = { ...data };
	if (payload && payload.tipologia) payload.tipologia = String(payload.tipologia).toUpperCase();
	return apiPut(`/immobili/${id}`, payload, true);
};

export const eliminaImmobile = (id) => apiDelete(`/immobili/${id}`, true);
