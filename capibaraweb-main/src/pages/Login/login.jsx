import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { login } from "../../api/authApi.js";
import { decodeJWT } from "../../utils/jwt_decoder.js";

export default function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    try {
      const data = await login({ email, password });
      const token = data.token;

      if (!token) {
        setError("Token non ricevuto dal server.");
        return;
      }

      // Salva il token nel localStorage
      localStorage.setItem("token", token);

      // Decodifica il payload del JWT
      const payload = decodeJWT(token);
      console.log("PAYLOAD JWT:", payload);

      if (!payload) {
        setError("Token non valido.");
        return;
      }

      // Il ruolo è nel claim "ruolo" del token
      const ruolo = payload.ruolo;

      if (!ruolo) {
        setError("Ruolo non trovato nel token.");
        return;
      }

      // Salva il ruolo nel localStorage per accesso rapido
      localStorage.setItem("ruolo", ruolo);
      localStorage.setItem("email", payload.email || "");

      // Redirect in base al ruolo
      switch (ruolo) {
        case "AGENTE":
          navigate("/agente");
          break;
        case "AMMINISTRATORE":
          navigate("/admin");
          break;
        case "VALUTATORE":
          navigate("/agente");
          break;
        case "PROPRIETARIO":
          navigate("/utente");
          break;
        default:
          navigate("/");
      }

    } catch (err) {
      console.error("Errore login:", err);
      setError("Credenziali non valide");
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Accedi</h2>

      <input
        type="email"
        placeholder="Email"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
      />

      <input
        type="password"
        placeholder="Password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />

      {error && <p style={{ color: "red" }}>{error}</p>}

      <button type="submit">Login</button>
    </form>
  );
}
