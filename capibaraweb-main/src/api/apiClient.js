const BASE_URL = "http://localhost:8080/api";

// GET
export async function apiGet(endpoint, authenticated = false) {
  const headers = {};

  if (authenticated) {
    const token = localStorage.getItem("token");
    if (token) headers["Authorization"] = `Bearer ${token}`;
  }

  const res = await fetch(`${BASE_URL}${endpoint}`, { headers });
  if (!res.ok) throw new Error(`GET ${endpoint} -> ${res.status}`);
  return res.json();
}

// POST
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

  if (!res.ok) throw new Error(`POST ${endpoint} -> ${res.status}`);
  return res.json();
}

// PUT
export async function apiPut(endpoint, body, authenticated = false) {
  const headers = { "Content-Type": "application/json" };

  if (authenticated) {
    const token = localStorage.getItem("token");
    headers["Authorization"] = `Bearer ${token}`;
  }

  const res = await fetch(`${BASE_URL}${endpoint}`, {
    method: "PUT",
    headers,
    body: JSON.stringify(body),
  });

  if (!res.ok) throw new Error(`PUT ${endpoint} -> ${res.status}`);
  return res.json();
}

// DELETE
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

  if (!res.ok) throw new Error(`DELETE ${endpoint} -> ${res.status}`);
  return true;
}
