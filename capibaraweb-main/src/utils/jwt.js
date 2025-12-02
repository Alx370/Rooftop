export function decodeJwt(token) {
  try {
    const base64 = token.split(".")[1];
    const payload = atob(base64);
    return JSON.parse(payload);
  } catch (e) {
    console.error("Errore decodifica JWT:", e);
    return null;
  }
}
