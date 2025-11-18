import React, { useState } from "react";
import { Link } from "react-router-dom";
import ProgressBar from "../ProgressBar/ProgressBar";
import googleIcon from "../../../../assets/icons/Google-icon1.png"; 
import styles from "./Step8.module.css";
import { getUsers, login, createUser } from "../../../../services/api";

export default function Step8({ formData, setFormData, nextStep, prevStep }) {
  const [nome, setNome] = useState(formData.nome || "");
  const [cognome, setCognome] = useState(formData.cognome || "");
  const [email, setEmail] = useState(formData.email || "");
  const [telefono, setTelefono] = useState(formData.telefono || "");
  const [password, setPassword] = useState(formData.password || "");
  const [error, setError] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!nome || !cognome || !email || !telefono || !password) {
      setError("Compila tutti i campi per procedere.");
      return;
    }

    const updatedData = { ...formData, nome, cognome, email, telefono, password };
    setFormData(updatedData);
    setError("");

    try {
      // invio al backend
      await createUser(updatedData); 
      nextStep(); // passa allo step successivo solo se la richiesta ha successo
    } catch (err) {
      console.error("Errore invio dati:", err);
      setError("Si è verificato un errore. Riprova.");
    }
  };

  return (
    <div className={styles.container}>
      <ProgressBar currentStep={8} totalSteps={9} />
      <h2 className={styles.title}>Registrati</h2>

      <button className={styles.googleButton}>
        <img src={googleIcon} alt="Google" className={styles.googleIcon} />
        Registrati con Google
      </button>

      <p className={styles.divider}>----------------- o registrati con l’email -----------------</p>

      <form className={styles.form} onSubmit={handleSubmit}>
        <div className={styles.leftColumn}>
          <label>Nome</label>
          <input type="text" placeholder="Nome" value={nome} onChange={(e) => setNome(e.target.value)} />
          <label>Email</label>
          <input type="email" placeholder="Email" value={email} onChange={(e) => setEmail(e.target.value)} />
          <label>Numero di telefono</label>
          <input type="text" placeholder="Telefono" value={telefono} onChange={(e) => setTelefono(e.target.value)} />
        </div>

        <div className={styles.rightColumn}>
          <label>Cognome</label>
          <input type="text" placeholder="Cognome" value={cognome} onChange={(e) => setCognome(e.target.value)} />
          <label>Password</label>
          <input type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} />
          <p className={styles.loginText}>
            Hai già un account? <Link to="/login" className={styles.loginLink}>Accedi al tuo account</Link>
          </p>
        </div>

        {error && <p className={styles.error}>{error}</p>}

        <div className={styles.buttonContainer}>
          <button type="submit" className={styles.registerButton}>Registrati</button>
        </div>
      </form>
    </div>
  );
}
