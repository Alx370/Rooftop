
import { apiPost } from "./apiClient.js";

export const inviaValutazioneApi = async (formData) => {
  try {
    const payload = { ...formData };
    if (payload && payload.tipologia) payload.tipologia = String(payload.tipologia).toUpperCase();

    const response = await apiPost("/immobili", payload);
    return response;
  } catch (err) {
    console.error("Errore invio valutazione:", err);
    throw err;
  }
};
