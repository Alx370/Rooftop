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

    console.log("Registrazione effettuata con:", formData);
    setError("");
  };

  return (
    <section className="register-section">
      <div className="register-card">
        <h2 className="register-title">Crea un nuovo account</h2>
        <p className="register-subtitle">Unisciti a noi in pochi secondi.</p>

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
