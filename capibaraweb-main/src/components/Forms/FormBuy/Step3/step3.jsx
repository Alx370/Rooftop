import React, { useState } from "react";
import ProgressBar from "../ProgressBar/ProgressBar";
import styles from "./step3.module.css";

import newImg from "../../../../assets/images/nuovo.png";
import goodImg from "../../../../assets/images/buonostato.png";
import renovatedImg from "../../../../assets/images/ottimostato.png";
import normalImg from "../../../../assets/images/normale.png";
import renovateImg from "../../../../assets/images/darestrutturare.png";

export default function Step3({ formData, setFormData, nextStep, prevStep }) {
  // mappa i possibili valori UI legacy a token enum backend
  const toToken = {
    new: "NUOVO",
    good: "BUONO",
    renovated: "OTTIMO",
    renovate: "DA_RISTRUTTURARE",
  };

  const initialCondition = (() => {
    if (!formData.serviceType) return "";
    const raw = String(formData.serviceType);
    const mapped = toToken[raw.toLowerCase()];
    return mapped || raw;
  })();

  const [selectedCondition, setSelectedCondition] = useState(initialCondition);
  const [error, setError] = useState("");

  // Condizioni disponibili (usiamo i token enum attesi dal backend)
  const conditions = [
    { id: "NUOVO", label: "Nuovo", img: newImg },
    { id: "BUONO", label: "Buono stato", img: goodImg },
    { id: "OTTIMO", label: "Ottimo stato", img: renovatedImg },
    { id: "DA_RISTRUTTURARE", label: "Da ristrutturare", img: renovateImg },
  ];

  const handleContinue = () => {
    if (!selectedCondition) {
      setError("Seleziona una condizione per continuare");
      return; // NON passa allo step successivo
    }

    setFormData({
      ...formData,
      serviceType: selectedCondition,
    });

    nextStep(); // ok
  };

  const selectedImage = selectedCondition
    ? conditions.find((c) => c.id === selectedCondition)?.img
    : normalImg; // Immagine di default

  return (
    <div className={styles.container}>
      <ProgressBar currentStep={3} totalSteps={10} />

      <h2 className={styles.title}>Quali sono le condizioni dell'immobile?</h2>

      <div className={styles.content}>

        {/* COLONNA SINISTRA */}
        <div className={styles.left}>
          <label className={styles.label}>Condizione immobile</label>

          <select
            value={selectedCondition}
            onChange={(e) => {
              setSelectedCondition(e.target.value);
              setError("");
            }}
            className={styles.select}
          >
            <option value="">Seleziona condizione</option>
            {conditions.map((c) => (
              <option key={c.id} value={c.id}>
                {c.label}
              </option>
            ))}
          </select>

          {error && <p className={styles.error}>{error}</p>}

          <div className={styles.buttons}>
            <button className={styles.back} onClick={prevStep}>
              Indietro
            </button>

            {/* SEMPRE CLICCABILE */}
            <button className={styles.next} onClick={handleContinue}>
              Continua
            </button>
          </div>
        </div>

        {/* COLONNA DESTRA */}
        <div className={styles.right}>
          <img src={selectedImage} alt="Condizione immobile" className={styles.image} />
        </div>

      </div>
    </div>
  );
}
