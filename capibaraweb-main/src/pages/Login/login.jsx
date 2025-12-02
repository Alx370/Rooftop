import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

import "./login.css";
import googleIcon from "../../assets/icons/Google-icon1.png";

import { login } from "../../api/authApi.js";
import { decodeJwt } from "../../utils/jwt.js";

export default function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!email || !password) {
      setError("Inserisci email e password.");
      return;
    }

    try {
      const res = await login({ email, password });

      console.log("TOKEN RICEVUTO:", res.token);

      const payload = decodeJwt(res.token);

      console.log("PAYLOAD JWT:", payload);

      if (!payload || !payload.authorities) {
        setError("Token non valido o senza ruolo.");
        return;
      }

      const ruolo = payload.authorities[0];

      // salviamo tutto
      localStorage.setItem("token", res.token);
      localStorage.setItem("ruolo", ruolo);
      localStorage.setItem("user", JSON.stringify(payload));

      // redirect dinamico
      if (ruolo === "AGENTE") {
        navigate("/agente");
      } else if (ruolo === "PROPRIETARIO") {
        navigate("/utente");
      } else {
        navigate("Ruolo Null");
        console.log("Errore di ruolo");
      }
    } catch (err) {
      console.error("ERRORE LOGIN:", err);
      setError("Credenziali non valide");
    }
  };

  return (
    <section className="login-section">
      <div className="login-card">

        <h2>Accedi ora</h2>
        <p className="login-subtitle">Bentornato!</p>

        <button className="google-btn">
          <img src={googleIcon} alt="" className="google-icon" />
          Accedi con Google
        </button>

        <div className="divider"><span>oppure</span></div>

        <form onSubmit={handleSubmit} className="login-form">
          <label>Email</label>
          <input type="email" onChange={(e) => setEmail(e.target.value)} />

          <label>Password</label>
          <input type="password" onChange={(e) => setPassword(e.target.value)} />

          {error && <p className="login-error">{error}</p>}

          <button className="login-button">Login</button>

          <p className="login-register">
            Non hai un account? <Link to="/registrati">Registrati</Link>
          </p>
        </form>
      </div>
    </section>
  );
}
