import React, { useState } from "react";
import { Link } from "react-router-dom";

import "./registrati.css";


const Register = () => {
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    password: "",
    confirmPassword: "",
  });
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    const { name, email, password, confirmPassword } = formData;

    if (!name || !email || !password || !confirmPassword) {
      setError("Tutti i campi sono obbligatori.");
      return;
    }
    if (password !== confirmPassword) {
      setError("Le password non coincidono.");
      return;
    }
    setError("");
    setSuccess("");
    setLoading(true);

    const parts = name.trim().split(/\s+/);
    const nome = parts.shift() || name.trim();
    const cognome = parts.join(" ") || "";

    const payload = {
      nome,
      cognome,
      email: email.trim().toLowerCase(),
      password: password.trim(),
    };

    const base = import.meta.env.VITE_API_BASE_URL || "http://localhost:8080";
    fetch(`${base}/api/utenti/registrati`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(payload),
    })
      .then(async (res) => {
        const data = await res.json().catch(() => null);
        if (!res.ok) {
          const message = (data && (data.message || data.error)) || "Errore di registrazione";
          throw new Error(message);
        }
        return data;
      })
      .then(() => {
        setSuccess("Registrazione completata. Ora puoi accedere.");
        setFormData({ name: "", email: "", password: "", confirmPassword: "" });
      })
      .catch((err) => {
        setError(err.message);
      })
      .finally(() => setLoading(false));
  };

  return (
    <section className="register-section">
      <div className="register-card">
        <h2 className="register-title">Registrati ora</h2>
        <p className="register-subtitle">Crea il tuo account</p>

        <form className="register-form" onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Nome e Cognome</label>
            <input
              type="text"
              name="name"
              placeholder="Inserisci il tuo nome completo"
              value={formData.name}
              onChange={handleChange}
            />
          </div>

          <div className="form-group">
            <label>Email</label>
            <input
              type="email"
              name="email"
              placeholder="Inserisci la tua email"
              value={formData.email}
              onChange={handleChange}
            />
          </div>

          <div className="form-group">
            <label>Password</label>
            <input
              type="password"
              name="password"
              placeholder="Crea una password"
              value={formData.password}
              onChange={handleChange}
            />
          </div>

          <div className="form-group">
            <label>Conferma Password</label>
            <input
              type="password"
              name="confirmPassword"
              placeholder="Ripeti la password"
              value={formData.confirmPassword}
              onChange={handleChange}
            />
          </div>

          {error && <p className="register-error">{error}</p>}

          <button type="submit" className="register-button" disabled={loading}>
            {loading ? "Invio..." : "Registrati"}
          </button>

          <p className="register-login">
            Hai già un account? <Link to="/login">Accedi</Link>
          </p>
          {success && <p className="register-subtitle">{success} <Link to="/login">Vai al login</Link></p>}
        </form>
      </div>
    </section>
  );
};

export default Register;
