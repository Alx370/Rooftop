import React, { useState } from "react";
import ProgressBar from "../ProgressBar/ProgressBar";
import styles from "./step3.module.css";

import newImg from "../../../../assets/images/nuovo.png";
import goodImg from "../../../../assets/images/buonostato.png";
import renovatedImg from "../../../../assets/images/ottimostato.png";
import normalImg from "../../../../assets/images/normale.png";
import renovateImg from "../../../../assets/images/darestrutturare.png";

export default function Step3({ formData, setFormData, nextStep, prevStep }) {
  const [selectedCondition, setSelectedCondition] = useState(
    formData.serviceType || ""
  );
  const [error, setError] = useState("");

  // Condizioni disponibili (Normale è default, NON una scelta)
  const conditions = [
    { id: "new", label: "Nuovo", img: newImg },
    { id: "good", label: "Buono stato", img: goodImg },
    { id: "renovated", label: "Ottimo stato", img: renovatedImg },
    { id: "renovate", label: "Da ristrutturare", img: renovateImg },
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
