import { apiPost } from "./apiClient";

export const inviaValutazioneManuale = (data) =>
  apiPost("/valutazioni/manuale", data, true);

export const inviaRichiestaContatto = (data) =>
  apiPost("/contatto", data);

export const valutaAutomatica = (data) =>
  apiPost("/valutazione/automatica", data, true);

