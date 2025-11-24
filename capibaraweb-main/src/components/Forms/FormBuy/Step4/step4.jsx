import React, { useState } from "react";
import styles from "./step4.module.css";
import ProgressBar from "../ProgressBar/ProgressBar";

export default function Step4({ formData, setFormData, nextStep, prevStep }) {
  const [rooms, setRooms] = useState(formData.rooms || "");
  const [bathrooms, setBathrooms] = useState(formData.bathrooms || "");
  const [furnishing, setFurnishing] = useState(formData.furnishing || "");
  const [doubleExposure, setDoubleExposure] = useState(formData.doubleExposure || "");
  const [error, setError] = useState("");

  const roomOptions = ["1", "2", "3", "4", "5", "6+"];
  const bathroomOptions = ["1", "2", "3", "4", "5", "6+"];

  const handleContinue = () => {
    if (!rooms || !bathrooms || !furnishing || !doubleExposure) {
      setError("Devi selezionare tutte le opzioni per continuare");
      return;
    }

    const updatedData = {
      ...formData,
      rooms,
      bathrooms,
      furnishing,
      doubleExposure,
    };

    setFormData(updatedData);
    setError("");

    nextStep(); // NESSUNA API QUI
  };

  return (
    <div className={styles.container}>
      <ProgressBar currentStep={4} totalSteps={9} />

      <h2 className={styles.title}>Da quanti locali è composto l'immobile?</h2>

      <div className={styles.grid}>
        {/* 🔹 ALTO SINISTRA */}
        <div className={styles.box}>
          <h3 className={styles.subtitle}>Locali immobile</h3>
          <div className={styles.selectorGrid}>
            {roomOptions.map((num) => (
              <div
                key={num}
                className={`${styles.optionBox} ${rooms === num ? styles.selected : ""}`}
                onClick={() => setRooms(num)}
              >
                {num}
              </div>
            ))}
          </div>
        </div>

        {/* 🔹 ALTO DESTRA */}
        <div className={styles.box}>
          <h3 className={styles.subtitle}>Cosa c’è dentro</h3>
          <select
            className={styles.select}
            value={furnishing}
            onChange={(e) => setFurnishing(e.target.value)}
          >
            <option value="">Seleziona</option>
            <option value="arredato">Arredato</option>
            <option value="da-arredare">Da arredare</option>
            <option value="parzialmente-arredato">Parzialmente arredato</option>
          </select>
        </div>

        {/* 🔹 BASSO SINISTRA */}
        <div className={styles.box}>
          <h3 className={styles.subtitle}>Bagni immobile</h3>
          <div className={styles.selectorGrid}>
            {bathroomOptions.map((num) => (
              <div
                key={num}
                className={`${styles.optionBox} ${bathrooms === num ? styles.selected : ""}`}
                onClick={() => setBathrooms(num)}
              >
                {num}
              </div>
            ))}
          </div>
        </div>

        {/* 🔹 BASSO DESTRA */}
        <div className={styles.box}>
          <h3 className={styles.subtitle}>Doppia esposizione</h3>
          <div className={styles.selectorGrid}>
            {["Sì", "No"].map((val) => (
              <div
                key={val}
                className={`${styles.optionBox} ${doubleExposure === val ? styles.selected : ""}`}
                onClick={() => setDoubleExposure(val)}
              >
                {val}
              </div>
            ))}
          </div>
        </div>
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
