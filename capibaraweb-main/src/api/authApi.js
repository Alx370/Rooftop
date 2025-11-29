import { apiGet, apiPost } from "./apiClient";

export function login(email, password) {
  return apiPost("/auth/login", { email, password });
}

export function logout() {
  localStorage.removeItem("token");
}

export function getMe() {
  return apiGet("/auth/me", true);
}
