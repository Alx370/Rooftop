import { useState } from "react";
import styles from './Step6.module.css';
import ProgressBar from "../ProgressBar/ProgressBar";

export default function Step6({ formData, setFormData, nextStep, prevStep }) {
  const [surface, setSurface] = useState(formData.surface || '');
  const [error, setError] = useState('');

  const handleContinue = () => {
    if (!surface) {
      setError("Inserisci la superficie dell'immobile");
      return;
    }
    setError('');

    const updatedData = { ...formData, surface };
    setFormData(updatedData);

    nextStep();  // nessuna chiamata API
  };

  return (
    <div className={styles.container}>
      <div className={styles.progressWrapper}>
        <ProgressBar currentStep={6} totalSteps={9} />
      </div>

      <h2>Qual è la superficie totale dell’immobile</h2>
      <p>Superficie immobile in m²</p>

      <div className={styles.surfaceInputContainer}>
        <input
          type="number"
          className={styles.inputNumber}
          value={surface}
          onChange={(e) => setSurface(e.target.value)}
          min={0}
          max={999}
          placeholder="0"
        />
        <span className={styles.m2}>m²</span>
      </div>

      {error && <div style={{ color: 'red', marginTop: '5px' }}>{error}</div>}

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
