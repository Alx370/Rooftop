import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import googleIcon from "../../assets/icons/Google-icon1.png";
import { login } from "../../api/authApi.js";
import { decodeJWT } from "../../utils/jwt_decoder.js";
import "./login.css";

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
          window.location.reload();
          break;
        case "AMMINISTRATORE":
          navigate("/admin");
          window.location.reload();
          break;
        case "VALUTATORE":
          navigate("/agente");
          window.location.reload();
          break;
        case "PROPRIETARIO":
          navigate("/utente");
          window.location.reload();
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

        {/* FORM CON ONSUBMIT FUNZIONANTE */}
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
}

/**
 * Login Page Component for Immobiliaris Real Estate Agency.
 *
 * Renders a login form for user authentication via email and password.
 * Handles authentication, error display, and redirects users based on their role.
 * Stores JWT token, role, and email in localStorage for session management.
 *
 * Features:
 * - Email/password login with validation and error handling
 * - Google login button (UI only, not implemented)
 * - Role-based navigation after successful login
 * - LocalStorage usage for token, role, and email
 * - JWT token decoding and validation
 * - Automatic page reload after successful authentication
 *
 * Role-based redirections:
 * - AGENTE: /agente
 * - AMMINISTRATORE: /admin
 * - VALUTATORE: /agente
 * - PROPRIETARIO: /utente
 * - Default: /
 *
 * @component
 * @returns {JSX.Element} The rendered Login page
 *
 * @example
 * <Login />
 *
 * @see ../../api/authApi.js for login API
 * @see ../../utils/jwt_decoder.js for JWT decoding
 * @see ./login.css for styles
 *
 * @typedef {Object} LoginResponse
 * @property {string} token - JWT token returned from the server
 *
 * @function handleSubmit
 * @description Handles form submission, authenticates user, manages localStorage, and redirects
 * @param {React.FormEvent<HTMLFormElement>} e - Form submit event
 */
