const BASE_URL = "http://localhost:8080/api";

/**
 * Esegue una richiesta GET
 * @param {string} endpoint - "/immobili", "/auth/me", ecc.
 * @param {boolean} authenticated - se true aggiunge Authorization Bearer
 */

export async function apiGet(endpoint, authenticated = false) {
  const headers = {};

  if (authenticated) {
    const token = localStorage.getItem("token");
    if (token) headers["Authorization"] = `Bearer ${token}`;
  }

  const res = await fetch(`${BASE_URL}${endpoint}`, { headers });

  if (!res.ok) {
    const text = await res.text();
    throw new Error(`GET ${endpoint} -> ${res.status}: ${text}`);
  }

  return res.json();
}

/**
 * Esegue una richiesta POST
 */
export async function apiPost(endpoint, body, authenticated = false) {
  const headers = { "Content-Type": "application/json" };

  if (authenticated) {
    const token = localStorage.getItem("token");
    if (token) headers["Authorization"] = `Bearer ${token}`;
  }

  const res = await fetch(`${BASE_URL}${endpoint}`, {
    method: "POST",
    headers,
    body: JSON.stringify(body),
  });

  if (!res.ok) {
    const text = await res.text();
    throw new Error(`POST ${endpoint} -> ${res.status}: ${text}`);
  }

  return res.json();
}

/**
 * Esegue una richiesta PUT
 */
export async function apiPut(endpoint, body, authenticated = false) {
  const headers = { "Content-Type": "application/json" };

  if (authenticated) {
    const token = localStorage.getItem("token");
    if (token) headers["Authorization"] = `Bearer ${token}`;
  }

  const res = await fetch(`${BASE_URL}${endpoint}`, {
    method: "PUT",
    headers,
    body: JSON.stringify(body),
  });

  if (!res.ok) {
    const text = await res.text();
    throw new Error(`PUT ${endpoint} -> ${res.status}: ${text}`);
  }

  return res.json();
}

/**
 * Esegue una DELETE
 */
export async function apiDelete(endpoint, authenticated = false) {
  const headers = {};

  if (authenticated) {
    const token = localStorage.getItem("token");
    if (token) headers["Authorization"] = `Bearer ${token}`;
  }

  const res = await fetch(`${BASE_URL}${endpoint}`, {
    method: "DELETE",
    headers,
  });

  if (!res.ok) {
    const text = await res.text();
    throw new Error(`DELETE ${endpoint} -> ${res.status}: ${text}`);
  }

  return true;
}
