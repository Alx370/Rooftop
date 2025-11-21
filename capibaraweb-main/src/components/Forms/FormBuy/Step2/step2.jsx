import React, { useState } from "react";
import ProgressBar from "../ProgressBar/ProgressBar";
import styles from "./Step2.module.css";
import villaImg from "../../../../assets/images/Villa.png";
import appartamentoImg from "../../../../assets/images/Appartamento.png";

export default function Step2({ formData, setFormData, nextStep, prevStep }) {
  const [error, setError] = useState("");
  const [selectedType, setSelectedType] = useState(formData.tipologia || "");

  const handleContinue = () => {
    if (!selectedType) {
      setError("*Inserire tipologia");
      return;
    }

    // Salvo la tipologia nello stato globale
    setFormData({ ...formData, tipologia: selectedType });

    setError("");
    nextStep(); // Vai allo step 3
  };

  return (
    <div className={styles.step}>
      <ProgressBar currentStep={2} totalSteps={9} />
      <h2>Qual è la tipologia del tuo immobile?</h2>

      <div className={styles.stepContent}>
        <div className={styles.leftColumn}>
          <p>Tipologia immobile</p>

          <select
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
          {selectedType === "villa" && <img src={villaImg} alt="Villa" />}
          {selectedType === "appartamento" && <img src={appartamentoImg} alt="Appartamento" />}
        </div>
      </div>
    </div>
  );
}
