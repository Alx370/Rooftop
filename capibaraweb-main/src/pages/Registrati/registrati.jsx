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

/**
 * Register Page Component for Immobiliaris Real Estate Agency.
 *
 * Renders a registration form for new user account creation.
 * Handles form validation, user registration, success/error states, and redirects to login page.
 * Processes name input by splitting into first and last name for API payload.
 *
 * Features:
 * - Full name, email, password, and password confirmation fields
 * - Client-side validation for required fields and password matching
 * - Error and success message display
 * - Automatic redirect to login page after successful registration
 * - Name parsing (splits full name into nome and cognome)
 *
 * @component
 * @returns {JSX.Element} The rendered Register page
 *
 * @example
 * <Register />
 *
 * @typedef {Object} FormData
 * @property {string} name - Full name of the user
 * @property {string} email - Email address of the user
 * @property {string} password - User's chosen password
 * @property {string} confirmPassword - Password confirmation
 *
 * @typedef {Object} RegisterPayload
 * @property {string} nome - First name
 * @property {string} cognome - Last name
 * @property {string} email - Email address
 * @property {string} password - Password
 *
 * @function handleChange
 * @description Updates form data state when input fields change
 * @param {React.ChangeEvent<HTMLInputElement>} e - Input change event
 *
 * @function handleSubmit
 * @description Handles form submission, validates data, and registers user
 * @param {React.FormEvent<HTMLFormElement>} e - Form submit event
 */
