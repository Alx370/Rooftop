import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { registrationService } from "../../services/registrationService";
import "./registrati.css";


const Register = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    password: "",
    confirmPassword: "",
    telefono: "",
  });
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");
  const [loading, setLoading] = useState(false);
  const [passwordErrors, setPasswordErrors] = useState([]);
  const [emailAvailable, setEmailAvailable] = useState(null);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });

    // Validazione password in tempo reale
    if (name === "password") {
      if (value) {
        const validation = registrationService.validatePassword(value);
        setPasswordErrors(validation.errors);
      } else {
        setPasswordErrors([]);
      }
    }

    // Validazione email in tempo reale
    if (name === "email") {
      if (value && registrationService.validateEmailFormat(value)) {
        setEmailAvailable(null); // Reset stato durante modifica
      } else if (value) {
        setEmailAvailable(false); // Email format non valido
      } else {
        setEmailAvailable(null);
      }
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    const { name, email, password, confirmPassword, telefono } = formData;

    // Validazioni
    if (!name || !email || !password || !confirmPassword) {
      setError("Tutti i campi sono obbligatori.");
      return;
    }

    if (!registrationService.validateEmailFormat(email)) {
      setError("Formato email non valido.");
      return;
    }

    if (password !== confirmPassword) {
      setError("Le password non coincidono.");
      return;
    }

    const passwordValidation = registrationService.validatePassword(password);
    if (!passwordValidation.valid) {
      setError("La password non rispetta i requisiti di sicurezza.");
      setPasswordErrors(passwordValidation.errors);
      return;
    }

    if (telefono && !registrationService.validatePhoneFormat(telefono)) {
      setError("Formato telefono non valido.");
      return;
    }

    setError("");
    setSuccess("");
    setLoading(true);

    const parts = name.trim().split(/\s+/);
    const nome = parts.shift() || name.trim();
    const cognome = parts.join(" ") || "";

    registrationService
      .registerProprietario({
        nome,
        cognome,
        email: email.trim().toLowerCase(),
        password: password.trim(),
        telefono: telefono.trim() || null,
      })
      .then(() => {
        setSuccess("Registrazione completata! Reindirizzamento al login...");
        setFormData({ name: "", email: "", password: "", confirmPassword: "", telefono: "" });
        setPasswordErrors([]);
        
        // Reindirizza al login dopo 2 secondi
        setTimeout(() => {
          navigate("/login", { replace: true });
        }, 2000);
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
              required
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
              required
            />
            {emailAvailable === false && formData.email && !registrationService.validateEmailFormat(formData.email) && (
              <p className="validation-error">Formato email non valido</p>
            )}
          </div>

          <div className="form-group">
            <label>Telefono (opzionale)</label>
            <input
              type="tel"
              name="telefono"
              placeholder="Inserisci il tuo numero di telefono"
              value={formData.telefono}
              onChange={handleChange}
            />
          </div>

          <div className="form-group">
            <label>Password</label>
            <input
              type="password"
              name="password"
              placeholder="Crea una password sicura"
              value={formData.password}
              onChange={handleChange}
              required
            />
            {passwordErrors.length > 0 && (
              <div className="password-requirements">
                <p className="requirements-title">La password deve contenere:</p>
                <ul>
                  {passwordErrors.map((err, idx) => (
                    <li key={idx} className="requirement-error">{err}</li>
                  ))}
                </ul>
              </div>
            )}
          </div>

          <div className="form-group">
            <label>Conferma Password</label>
            <input
              type="password"
              name="confirmPassword"
              placeholder="Ripeti la password"
              value={formData.confirmPassword}
              onChange={handleChange}
              required
            />
          </div>

          {error && <p className="register-error">{error}</p>}
          {success && <p className="register-success">{success}</p>}

          <button type="submit" className="register-button" disabled={loading}>
            {loading ? "Registrazione in corso..." : "Registrati"}
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
