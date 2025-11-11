import React, { useState } from "react";
import { Link } from "react-router-dom";
import googleIcon from "../../assets/icons/Google-icon1.png";
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
        <h2 className="login-title">Accedi ora</h2>
        <p className="login-subtitle">
          Bentornato a casa!
        </p>

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
