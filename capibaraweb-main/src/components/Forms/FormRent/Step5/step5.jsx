import { useState } from "react";
import styles from "./step5.module.css";
import ProgressBar from "../ProgressBar/Progressbar";

export default function Step5({ formData, setFormData, nextStep, prevStep }) {
  const [floor, setFloor] = useState(formData.floor || "");
  const [error, setError] = useState("");

  const handleContinue = () => {
    if (!floor && floor !== 0) {
      setError("Inserisci il piano dell'immobile");
      return;
    }

    if (Number(floor) > 45) {
      setError("Il piano massimo consentito è 45");
      return;
    }

    setError("");

    setFormData({
      ...formData,
      floor,
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
          onChange={(e) => setFloor(e.target.value)}
          min={0}
          max={45}
          placeholder="0"
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
