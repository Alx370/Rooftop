import { apiPost } from "./apiClient.js";

// Iscrizione newsletter
export function subscribeNewsletter(email) {
  return apiPost("/newsletter/iscriviti", { email });
}
