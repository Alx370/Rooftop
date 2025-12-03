import { apiPost } from "./apiClient.js";

export function inviaRichiestaContatto(data) {
  return apiPost("/contatto", data);
}
