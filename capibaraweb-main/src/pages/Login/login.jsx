import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import "./login.css";

import googleIcon from "../../assets/icons/Google-icon1.png";
import { login, decodeJwt } from "../../api/authApi.js";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!email || !password) {
      setError("Compila tutti i campi.");
      return;
    }

    try {
      // Login → ricevi il token
      const data = await login({ email, password });

      if (!data?.token) {
        setError("Token non ricevuto dal server.");
        return;
      }

      // Salva token
      localStorage.setItem("token", data.token);

      // Decodifica token
      const user = decodeJwt(data.token);

      if (!user) {
        setError("Errore nel token ricevuto.");
        return;
      }

      console.log("Utente loggato:", user);

      // Salva ruolo e id nella sessione
      localStorage.setItem("ruolo", user.ruolo);
      localStorage.setItem("id", user.id);

      // Redirect dinamico in base al ruolo
      if (user.ruolo === "AGENTE" || user.ruolo === "ROLE_AGENTE") {
        navigate("/agente");
      } else {
        navigate("/utente");
      }
    } catch (err) {
      console.error("Errore login:", err);
      setError("Credenziali non valide.");
    }
  };

  return (
    <section className="login-section">
      <div className="login-card">
        <h2 className="login-title">Accedi ora</h2>
        <p className="login-subtitle">Bentornato a casa!</p>

        <button className="google-btn">
          <img src={googleIcon} alt="Google" className="google-icon" />
          Accedi con Google
        </button>

        <div className="divider">
          <span>o accedi con l'email</span>
        </div>

        <form className="login-form" onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Email</label>
            <input
              type="email"
              placeholder="Inserisci la tua email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          </div>

          <div className="form-group">
            <label>Password</label>
            <input
              type="password"
              placeholder="Inserisci la tua password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
          </div>

          {error && <p className="login-error">{error}</p>}

          <div className="options">
            <label className="remember">
              <input type="checkbox" /> Ricordami
            </label>
            <a href="#" className="forgot-password">
              Password dimenticata?
            </a>
          </div>

          <button type="submit" className="login-button">
            Login
          </button>

          <p className="login-register">
            Non hai un account? <Link to="/registrati">Registrati</Link>
          </p>
        </form>
      </div>
    </section>
  );
};

export default Login;
