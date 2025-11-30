// src/api/registerApi.js
import { apiPost } from "./apiClient.js";

export async function registerUser(userData) {
  return await apiPost("/utenti/registrati", userData);
}
