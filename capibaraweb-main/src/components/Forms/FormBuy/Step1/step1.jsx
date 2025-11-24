import React, { useState } from "react";
import ProgressBar from "../ProgressBar/ProgressBar";
import styles from "./step1.module.css";

export default function Step1({ formData, setFormData, nextStep, prevStep }) {
  const [error, setError] = useState("");

  const handleContinue = () => {
    if (!formData.indirizzo) {
      setError("Inserisci un indirizzo valido");
      return;
    }

    setError("");
    nextStep(); // Vai allo step 2
  };

  return (
    <div className={styles.step}>
      <ProgressBar currentStep={1} totalSteps={9} />
      <h2>Dove si trova l'immobile da valutare?</h2>

      <div className={styles.stepContent}>
        <div className={styles.leftColumn}>
          <p>Indirizzo dell'immobile</p>

          <input
            type="text"
            placeholder="Inserisci l'indirizzo"
            value={formData.indirizzo}
            onChange={(e) =>
              setFormData({ ...formData, indirizzo: e.target.value })
            }
          />

          {error && <div className={styles.error}>{error}</div>}

          <div className={styles.buttons}>
            <button className={styles.btnBack} onClick={prevStep}>
              Indietro
            </button>
            <button className={styles.btnContinue} onClick={handleContinue}>
              Continua
            </button>
          </div>
        </div>

        <div className={styles.rightColumn}>
          <iframe
            title="mappa"
            src="https://www.google.com/maps/embed?..." 
            width="100%"
            height="100%"
            style={{ border: 0 }}
            allowFullScreen
            loading="lazy"
          />
        </div>
      </div>
    </div>
  );
}
