
import { apiPost, apiGet } from "./apiClient.js";

export async function login(credentials) {
  return apiPost("/auth/login", credentials);
}

export function setAuthToken(token) {
  if (token) localStorage.setItem("token", token);
}

export async function getMe() {
  return apiGet("/auth/me", true);
}

export async function googleLogin(idToken) {
  return apiPost("/auth/google", { idToken });
}
