import React, { useState } from "react";
import ProgressBar from "../ProgressBar/Progressbar";
import styles from "./step2.module.css";

import defaultImg from "../../../../assets/images/tipologia di casa.png";
import villaImg from "../../../../assets/images/Villa.png";
import appartamentoImg from "../../../../assets/images/Appartamento.png";

export default function Step2({ formData, setFormData, nextStep, prevStep }) {
  const [selectedType, setSelectedType] = useState(formData.tipologia || "");
  const [error, setError] = useState("");

  const handleContinue = () => {
    if (!selectedType) {
      setError("Seleziona una tipologia");
      return;
    }

    setFormData({ ...formData, tipologia: selectedType });
    nextStep();
  };

  return (
    <div className={styles.container}>
      <ProgressBar currentStep={2} totalSteps={10} />

      <h2 className={styles.title}>Qual è la tipologia del tuo immobile?</h2>

      <div className={styles.content}>
        
        {/* SINISTRA */}
        <div className={styles.left}>
          <label className={styles.label}>Tipologia immobile</label>

          <select
            className={styles.select}
            value={selectedType}
            onChange={(e) => {
              setSelectedType(e.target.value);
              setError("");
            }}
          >
            <option value="">Seleziona tipologia</option>
            <option value="villa">Villa indipendente</option>
            <option value="appartamento">Appartamento</option>
          </select>

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

        {/* DESTRA (IMMAGINI) */}
        <div className={styles.right}>
          {!selectedType && (
            <img src={defaultImg} alt="Tipologia immobile" className={styles.image} />
          )}
          {selectedType === "villa" && (
            <img src={villaImg} alt="Villa" className={styles.image} />
          )}
          {selectedType === "appartamento" && (
            <img src={appartamentoImg} alt="Appartamento" className={styles.image} />
          )}
        </div>

      </div>
    </div>
  );
}
