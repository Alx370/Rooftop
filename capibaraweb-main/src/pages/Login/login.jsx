import React, { useState } from "react";
import { Link, useNavigate, useLocation } from "react-router-dom";

import { login, setAuthToken, getMe } from "../../api/authApi.js";
import "./login.css";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);
  const redirectTo = searchParams.get("redirect");

  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!email || !password) {
      setError("Compila tutti i campi.");
      return;
    }

    try {
      // 1️⃣ LOGIN → ottieni token
      const data = await login({ email, password });
      setAuthToken(data.token);

      // 2️⃣ PRENDI INFO UTENTE / RUOLO
      const me = await getMe();
      const ruolo = me.authorities?.[0]; // esempio: "ROLE_AGENTE"

      console.log("Ruolo utente:", ruolo);

      // 3️⃣ REDIRECT
      if (redirectTo) {
        navigate(redirectTo);
      } else if (ruolo === "ROLE_AGENTE") {
        navigate("/agente");
      } else if (ruolo === "ROLE_CLIENTE") {
        navigate("/utente");
      } else {
        navigate("/dashboard");
      }

      setError("");

    } catch (err) {
      console.error("Errore login:", err);
      setError("Credenziali non valide o server non raggiungibile.");
    }
  };

  

  return (
    <section className="login-section">
      <div className="login-card">
        <h2 className="login-title">Accedi ora</h2>
        <p className="login-subtitle">Bentornato!</p>

        

        <div className="divider"><span>oppure</span></div>

        <form className="login-form" onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Email</label>
            <input type="email" value={email}
              onChange={(e) => setEmail(e.target.value)}
              placeholder="Inserisci email"
            />
          </div>

          <div className="form-group">
            <label>Password</label>
            <input type="password" value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="Inserisci password"
            />
          </div>

          {error && <p className="login-error">{error}</p>}

          <div className="options">
            <label className="remember"><input type="checkbox" /> Ricordami</label>
            <a className="forgot-password" href="#">Dimenticata?</a>
          </div>

          <button type="submit" className="login-button">Login</button>

          <p className="login-register">Non hai un account?
            <Link to="/registrati"> Registrati</Link>
          </p>
        </form>
      </div>
    </section>
  );
};

export default Login;
