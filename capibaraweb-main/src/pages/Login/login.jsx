import React, { useState } from "react";
import { Link } from "react-router-dom";
import "./Login.css";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();

    // ✅ Validazione semplice
    if (!email || !password) {
      setError("Compila tutti i campi per procedere.");
      return;
    }

    // Simulazione invio login
    console.log("Login effettuato con:", { email, password });
    setError("");
  };

  return (
    <section className="login-section">
      <div className="login-card">
        <h2 className="login-title">Accedi al tuo account</h2>
        <p className="login-subtitle">
          Bentornato! Inserisci le credenziali per continuare.
        </p>

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

          <button type="submit" className="login-button">
            Login
          </button>

          {/* Testo aggiuntivo sotto */}
          <p className="login-register">
            Non hai un account? <Link to="/registrati">Registrati</Link>
          </p>
        </form>
      </div>
    </section>
  );
};

export default Login;
