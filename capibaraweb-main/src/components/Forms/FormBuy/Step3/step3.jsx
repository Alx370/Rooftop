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
  const [customerMessage, setCustomerMessage] = useState(
    formData.message || ""
  );
  const [error, setError] = useState("");

  const conditions = [
    { id: "new", label: "Nuovo", img: newImg },
    { id: "good", label: "Buono stato", img: goodImg },
    { id: "renovated", label: "Ottimo stato", img: renovatedImg },
    { id: "normal", label: "Normale", img: normalImg },
    { id: "renovate", label: "Da ristrutturare", img: renovateImg },
  ];

  const handleContinue = () => {
    if (!selectedCondition) {
      setError("Seleziona una condizione per continuare");
      return;
    }

    setError("");
    setFormData({
      ...formData,
      serviceType: selectedCondition,
      message: customerMessage,
    });

    nextStep();
  };

  const selectedImage = conditions.find((c) => c.id === selectedCondition)?.img;

  return (
    <div className={styles.container}>
      <ProgressBar currentStep={3} totalSteps={8} />

      <h2 className={styles.title}>Quali sono le condizioni dell'immobile?</h2>

      <div className={styles.content}>
        {/* COLONNA SINISTRA */}
        <div className={styles.leftColumn}>
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

          <label className={styles.label}>
            Note aggiuntive (opzionale)
          </label>
          <textarea
            className={styles.textarea}
            placeholder="Inserisci eventuali dettagli sulle condizioni dell'immobile..."
            value={customerMessage}
            onChange={(e) => setCustomerMessage(e.target.value)}
          />

          {error && <p className={styles.error}>{error}</p>}

          <div className={styles.buttonContainer}>
            <button className={styles.backButton} onClick={prevStep}>
              Indietro
            </button>

            <button
              className={styles.continueButton}
              onClick={handleContinue}
              disabled={!selectedCondition}
            >
              Continua
            </button>
          </div>
        </div>

        {/* COLONNA DESTRA */}
        <div className={styles.rightColumn}>
          {selectedImage && (
            <img
              src={selectedImage}
              alt="Condizione immobile"
              className={styles.image}
            />
          )}
        </div>
      </div>
    </div>
  );
}
