import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import googleIcon from "../../assets/icons/Google-icon1.png";
import { login, setAuthToken } from "../../services/api"; 
import "./login.css";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const navigate = useNavigate(); // per il redirect dopo login

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Validazione semplice
    if (!email || !password) {
      setError("Compila tutti i campi per procedere.");
      return;
    }

    try {
      // Chiamata al backend: passiamo un oggetto { email, password }
      const data = await login({ email, password });
      console.log("Token ricevuto dal backend:", data.token);

      // Salvataggio token in localStorage
      localStorage.setItem("token", data.token);

      // Imposta l'Authorization header per le chiamate future
      setAuthToken(data.token);

      setError("");

      // Redirect alla dashboard o pagina protetta
      navigate("/dashboard"); // modifica con la tua route
    } catch (err) {
      setError(err.message || "Credenziali non valide");
      console.error(err);
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
