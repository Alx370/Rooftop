import React, { useState } from "react";
import ProgressBar from "../ProgressBar/ProgressBar";
import styles from "./step1.module.css";

export default function Step1({ formData, setFormData, nextStep, prevStep }) {
  const [error, setError] = useState("");

  const handleContinue = () => {
    if (!formData.indirizzo.trim()) {
      setError("Inserisci un indirizzo valido");
      return;
    }

    setError("");
    nextStep();
  };

  return (
    <div className={styles.container}>
      <ProgressBar currentStep={1} totalSteps={8} />

      <h2 className={styles.title}>Dove si trova l'immobile da valutare?</h2>

      <div className={styles.content}>
        {/* COLONNA SINISTRA */}
        <div className={styles.left}>
          <label className={styles.label}>Indirizzo dell'immobile</label>

          <input
            type="text"
            placeholder="Inserisci l'indirizzo"
            value={formData.indirizzo}
            onChange={(e) =>
              setFormData({ ...formData, indirizzo: e.target.value })
            }
            className={styles.input}
          />

          {error && <p className={styles.error}>{error}</p>}

          <div className={styles.buttons}>
            <button className={styles.back} onClick={prevStep}>
              Indietro
            </button>

            <button className={styles.next} onClick={handleContinue}>
              Continua
            </button>
          </div>
        </div>

        {/* COLONNA DESTRA */}
        <div className={styles.right}>
          <iframe
            title="mappa"
            src="https://www.google.com/maps/embed?pb=!1m18!..." 
            className={styles.map}
            allowFullScreen
            loading="lazy"
          ></iframe>
        </div>
      </div>
    </div>
  );
}
