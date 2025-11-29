import { apiGet, apiPost, apiPut, apiDelete } from "./apiClient";

export const getFaq = () => apiGet("/faq");
export const getFaqById = (id) => apiGet(`/faq/${id}`);
export const getFaqCategorie = () => apiGet("/faq/categorie");
export const getFaqByCategoria = (cat) => apiGet(`/faq/categoria/${cat}`);

export const creaFaq = (data) => apiPost("/faq", data, true);
export const aggiornaFaq = (id, data) => apiPut(`/faq/${id}`, data, true);

export const eliminaFaq = (id) => apiDelete(`/faq/${id}`, true);
