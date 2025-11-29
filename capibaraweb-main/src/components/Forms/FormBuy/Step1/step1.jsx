import React, { useState } from "react";
import ProgressBar from "../ProgressBar/ProgressBar";
import styles from "./step1.module.css";

export default function Step1({ formData, setFormData, nextStep, prevStep }) {
  const [error, setError] = useState("");

  const handleContinue = () => {
    if (
      !formData.cap.trim() ||
      !formData.indirizzo.trim() ||
      !formData.provincia.trim()
    ) {
      setError("Compila tutti i campi richiesti");
      return;
    }

    setError("");
    nextStep();
  };

  return (
    <div className={styles.container}>
      <ProgressBar currentStep={1} totalSteps={9} />

      <h2 className={styles.title}>Dove si trova l'immobile da valutare?</h2>

      <div className={styles.content}>
        {/* COLONNA SINISTRA */}
        <div className={styles.left}>
          {/* CAP */}
          <label className={styles.label}>CAP</label>
          <input
            type="text"
            placeholder="Inserisci il CAP"
            value={formData.cap}
            onChange={(e) => setFormData({ ...formData, cap: e.target.value })}
            className={styles.input}
          />

          {/* Indirizzo */}
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

          {/* Provincia */}
          <label className={styles.label}>Provincia</label>
          <input
            type="text"
            placeholder="Es. Torino, Roma, Milano"
            value={formData.provincia}
            onChange={(e) =>
              setFormData({ ...formData, provincia: e.target.value })
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

        {/* COLONNA DESTRA: MAPPA */}
        <div className={styles.right}>
          <iframe
            title="mappa"
            src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d716827.8493599535!2d7.207447099999999!3d45.0703396!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x47887f8b370ecdcd%3A0x30e8d4c7f7f3f0ab!2sTorino!5e0!3m2!1sit!2sit!4v1700000000000!5m2!1sit!2sit"
            className={styles.map}
            allowFullScreen
            loading="lazy"
          ></iframe>
        </div>
      </div>
    </div>
  );
}
