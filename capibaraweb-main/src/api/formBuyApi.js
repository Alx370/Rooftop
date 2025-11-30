
import { apiPost } from "./apiClient.js";

export const inviaValutazioneApi = async (formData) => {
  try {
    const response = await apiPost("/immobili", formData);
    return response;
  } catch (err) {
    console.error("Errore invio valutazione:", err);
    throw err;
  }
};
