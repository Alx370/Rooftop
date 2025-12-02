import { apiPost, apiGet } from "./apiClient.js";

export async function sendMessage(message) {
  return await apiPost("/chat", { message });
}

export async function getMessage() {
    return await apiGet("/chat");
}