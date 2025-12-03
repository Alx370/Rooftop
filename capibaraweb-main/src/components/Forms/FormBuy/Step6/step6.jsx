import { useState } from "react";
import styles from "./step6.module.css";
import ProgressBar from "../ProgressBar/ProgressBar";

export default function Step6({ formData, setFormData, nextStep, prevStep }) {
  const [surface, setSurface] = useState(formData.surface || "");
  const [error, setError] = useState("");

  const handleContinue = () => {
    if (!surface || Number(surface) <= 0) {
      setError("Inserisci una superficie valida");
      return;
    }

    if (Number(surface) > 999) {
      setError("La superficie massima consentita è 999 m²");
      return;
    }

    setError("");

    setFormData({
      ...formData,
      surface,
    });

    nextStep();
  };

  return (
    <div className={styles.container}>
      <ProgressBar currentStep={6} totalSteps={10} />

      <h2 className={styles.title}>Qual è la superficie totale dell’immobile?</h2>
      <p className={styles.subtitle}>Indica la superficie in metri quadrati</p>

      <div className={styles.surfaceInputContainer}>
        <input
          type="number"
          className={styles.inputNumber}
          value={surface}
          onChange={(e) => setSurface(e.target.value)}
          min={1}
          max={999}
          placeholder="0"
        />
        <span className={styles.m2}>m²</span>
      </div>

      {error && <p className={styles.error}>{error}</p>}

      <div className={styles.buttonContainer}>
        <button className={styles.backButton} onClick={prevStep}>
          Indietro
        </button>
        <button className={styles.continueButton} onClick={handleContinue}>
          Continua
        </button>
      </div>
    </div>
  );
}
