import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

import { registerUser } from "../../api/registerApi.js";
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
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const { name, email, password, confirmPassword } = formData;

    // VALIDAZIONE
    if (!name || !email || !password || !confirmPassword) {
      setError("Tutti i campi sono obbligatori.");
      return;
    }

    if (password !== confirmPassword) {
      setError("Le password non coincidono.");
      return;
    }

    // Dividere in nome e cognome
    const [nome, ...cognomeArray] = name.trim().split(" ");
    const cognome = cognomeArray.join(" ") || "";

    const payload = { nome, cognome, email, password };

    try {
      await registerUser(payload);

      setError("");
      setSuccess("Registrazione completata! Reindirizzamento…");

      setTimeout(() => navigate("/login"), 1500);
    } catch (err) {
      console.error("Errore registrazione:", err);
      setError("Registrazione fallita. Email già registrata o dati non validi.");
    }
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
              placeholder="Mario Rossi"
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
          {success && <p className="register-success">{success}</p>}

          <button type="submit" className="register-button">
            Registrati
          </button>

          <p className="register-login">
            Hai già un account? <Link to="/login">Accedi</Link>
          </p>

        </form>
      </div>
    </section>
  );
};

export default Register;
