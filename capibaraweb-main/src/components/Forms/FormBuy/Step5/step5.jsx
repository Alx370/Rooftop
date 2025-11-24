import { useState } from "react";
import styles from './step5.module.css';
import ProgressBar from "../ProgressBar/ProgressBar";

export default function Step5({ formData, setFormData, nextStep, prevStep }) {
  const [floor, setFloor] = useState(formData.floor || '');
  const [error, setError] = useState('');

  const handleContinue = () => {
    if (!floor) {
      setError("Inserisci il piano dell'immobile");
      return;
    }

    if (Number(floor) > 45) {
      setError("Il piano massimo è 45");
      return;
    }

    setError("");

    const updatedData = { ...formData, floor };
    setFormData(updatedData);

    nextStep();  // NESSUNA chiamata API qui!
  };

  return (
    <div className={styles.container}>
      <div className={styles.progressWrapper}>
        <ProgressBar currentStep={5} totalSteps={9} />
      </div>

      <h2>A quale piano è ubicato l’immobile</h2>
      <p>Piano immobile</p>

      <input
        type="number"
        className={styles.inputNumber}
        value={floor}
        onChange={(e) => setFloor(e.target.value)}
        min={0}
        max={45}
        placeholder="0"
      />

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
