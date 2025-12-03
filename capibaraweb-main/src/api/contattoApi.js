import { apiPost } from "./apiClient.js";

export function inviaRichiestaContatto(data) {
  return apiPost("/contatto", data);
}

// Alias per compatibilità con faq.jsx
export const sendContactRequest = inviaRichiestaContatto;
