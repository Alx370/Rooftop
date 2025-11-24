import React, { useState } from 'react';
import styles from './step3.module.css';
import ProgressBar from "../ProgressBar/ProgressBar";

import newImg from '../../../../assets/images/nuovo.png';
import goodImg from '../../../../assets/images/buonostato.png';
import renovatedImg from '../../../../assets/images/ottimostato.png';
import normalImg from '../../../../assets/images/normale.png';
import renovateImg from '../../../../assets/images/darestrutturare.png';

export default function Step3({ formData, setFormData, nextStep, prevStep }) {
  const [selectedCondition, setSelectedCondition] = useState(formData.serviceType || '');
  const [customerMessage, setCustomerMessage] = useState(formData.message || '');
  const [error, setError] = useState('');

  const conditions = [
    { id: 'new', label: 'Nuovo ', img: newImg },
    { id: 'good', label: 'Da ristrutturare', img: goodImg },
    { id: 'renovated', label: 'Ottimo stato', img: renovatedImg },
    { id: 'normal', label: 'Ottime condizioni', img: normalImg },
    { id: 'renovate', label: 'In ristrutturazione', img: renovateImg }
  ];

  const handleContinue = () => {
    if (!selectedCondition) {
      setError("⚠️ Devi selezionare una condizione per continuare.");
      return;
    }

    setError("");

    // Aggiorno i dati globali del form
    setFormData({
      ...formData,
      serviceType: selectedCondition,
      message: customerMessage
    });

    nextStep(); // Nessuna API, si va allo step successivo
  };

  const selectedImage = conditions.find(c => c.id === selectedCondition)?.img;

  return (
    <div className={styles.container}>
      <ProgressBar currentStep={3} totalSteps={9} />

      <div className={styles.content}>
        <div className={styles.leftColumn}>
          <h2>Quali sono le sue condizioni?</h2>

          <label className={styles.label}>Condizione immobile</label>
          <select
            value={selectedCondition}
            onChange={(e) => setSelectedCondition(e.target.value)}
            className={styles.select}
          >
            <option value="">Seleziona condizione</option>
            {conditions.map((c) => (
              <option key={c.id} value={c.id}>{c.label}</option>
            ))}
          </select>

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

        <div className={styles.rightColumn}>
          {selectedImage && <img src={selectedImage} alt="Condizione immobile" />}
        </div>
      </div>
    </div>
  );
}
