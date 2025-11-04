import React, { useState } from "react";
import "./FormLogin.css";

const FormLogin = () => {
  const [formData, setFormData] = useState({
    email: "",
    password: "",
  });

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("Dati login:", formData);
    alert(`Accesso effettuato come: ${formData.email}`);
  };

  return (
    <form className="login-form" onSubmit={handleSubmit}>
      <div className="form-group">
        <label htmlFor="email">Email</label>
        <input
          type="email"
          id="email"
          name="email"
          placeholder="Inserisci la tua email"
          value={formData.email}
          onChange={handleChange}
          required
        />
      </div>

      <div className="form-group">
        <label htmlFor="password">Password</label>
        <input
          type="password"
          id="password"
          name="password"
          placeholder="Inserisci la tua password"
          value={formData.password}
          onChange={handleChange}
          required
        />
      </div>

      <button type="submit" className="login-button">
        Accedi
      </button>
    </form>
  );
};

export default FormLogin;
