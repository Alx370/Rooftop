import React, { useState } from "react";
import ProgressBar from "../ProgressBar/ProgressBar";
import styles from "./step1.module.css";

export default function Step1({ formData, setFormData, nextStep, prevStep }) {
  const [error, setError] = useState("");

  const provincePiemonte = {
    TORINO: "TO",
    VERCELLI: "VC",
    BIELLA: "BI",
    VERBANIA: "VB",
    NOVARA: "NO",
    CUNEO: "CN",
    ASTI: "AT",
    ALESSANDRIA: "AL",
  };

  // VALIDAZIONE CAP (solo numeri, 5 cifre)
  const validateCAP = (cap) => {
    const capRegex = /^[0-9]{5}$/;
    return capRegex.test(cap);
  };

  // VALIDAZIONE INDIRIZZO (formato realistico)
  const validateIndirizzo = (indirizzo) => {
    const indirizzoRegex =
      /^(via|viale|corso|piazza|vicolo|lungo|piazzale)\s+[a-zA-ZÀ-ÿ\s']+\s+[0-9]{1,4}[a-zA-Z]?$/i;
    return indirizzoRegex.test(indirizzo);
  };

  // VALIDAZIONE PROVINCIA
  const validateProvincia = (provincia) => {
    if (!provincia) return false;

    const upper = provincia.trim().toUpperCase();

    // controllo abbreviazione (TO)
    if (Object.values(provincePiemonte).includes(upper)) return true;

    // controllo nome provincia (Torino)
    return Object.keys(provincePiemonte).includes(upper);
  };

  const handleContinue = () => {
    if (
      !formData.cap.trim() ||
      !formData.indirizzo.trim() ||
      !formData.provincia.trim()
    ) {
      setError("Compila tutti i campi richiesti");
      return;
    }

    if (!validateCAP(formData.cap)) {
      setError("Il CAP deve contenere solo numeri ed essere lungo 5 cifre.");
      return;
    }

    if (!validateIndirizzo(formData.indirizzo)) {
      setError(
        "Inserisci un indirizzo valido (Es: Via Roma 12, Corso Francia 22)."
      );
      return;
    }

    if (!validateProvincia(formData.provincia)) {
      setError(
        "Inserisci una provincia valida del Piemonte (Es: Torino o TO)."
      );
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
        <div className={styles.left}>
          {/* CAP */}
          <label className={styles.label}>CAP</label>
          <input
            type="text"
            placeholder="Inserisci il CAP"
            value={formData.cap}
            onChange={(e) =>
              setFormData({ ...formData, cap: e.target.value })
            }
            className={styles.input}
          />

          {/* Indirizzo */}
          <label className={styles.label}>Indirizzo dell'immobile</label>
          <input
            type="text"
            placeholder="Via Roma 10"
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
            placeholder="Es. Torino o TO"
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

        {/* MAPPA */}
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
