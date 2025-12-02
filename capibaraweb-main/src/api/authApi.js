import { apiPost, apiGet } from "./apiClient.js";

// LOGIN → ricevi token
export async function login(credentials) {
  const data = await apiPost("/auth/login", credentials);

  if (!data.token) {
    throw new Error("Token non presente nella risposta");
  }

  return data; // { token: "..." }
}

// GET /auth/me
export function getMe() {
  return apiGet("/auth/me", true);
}
