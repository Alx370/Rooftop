import { useState } from "react";
import styles from "./step5.module.css";
import ProgressBar from "../ProgressBar/ProgressBar";

export default function Step5({ formData, setFormData, nextStep, prevStep }) {
  const [floor, setFloor] = useState(
    formData.floor === 0 || formData.floor ? formData.floor : ""
  );
  const [error, setError] = useState("");

  const handleChange = (e) => {
    const rawValue = e.target.value;

    // Permetti di cancellare il campo
    if (rawValue === "") {
      setFloor("");
      setError("");
      return;
    }

    const value = Number(rawValue);

    // Se l'utente prova a inserire un numero negativo
    if (value < 0) {
      setError("Non è possibile inserire un numero negativo");
      setFloor(""); // svuota il campo
      return;
    }

    // Controllo max 45 in tempo reale (opzionale)
    if (value > 45) {
      setError("Il piano massimo consentito è 45");
    } else {
      setError("");
    }

    setFloor(rawValue);
  };

  const handleContinue = () => {
    const numericFloor = Number(floor);

    // Campo vuoto o non valido
    if (floor === "" || Number.isNaN(numericFloor)) {
      setError("Inserisci il piano dell'immobile");
      return;
    }

    // Ulteriore safety check per negativi
    if (numericFloor < 0) {
      setError("Non è possibile inserire un numero negativo");
      return;
    }

    if (numericFloor > 45) {
      setError("Il piano massimo consentito è 45");
      return;
    }

    setError("");

    setFormData({
      ...formData,
      floor: numericFloor,
    });

    nextStep();
  };

  return (
    <div className={styles.container}>
      <ProgressBar currentStep={5} totalSteps={10} />

      <h2 className={styles.title}>A quale piano è ubicato l’immobile?</h2>
      <p className={styles.subtitle}>Inserisci il piano dell’immobile</p>

      <div className={styles.inputWrapper}>
        <input
          type="number"
          className={styles.inputNumber}
          value={floor}
          onChange={handleChange}
          min={0}
          max={45}
          placeholder="0"
          onKeyDown={(e) => {
            // Blocca la digitazione diretta del "-" e di caratteri non numerici comuni
            if (e.key === "-" || e.key === "+" || e.key === "e") {
              e.preventDefault();
            }
          }}
        />
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
