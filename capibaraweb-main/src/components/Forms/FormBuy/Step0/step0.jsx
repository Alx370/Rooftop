import React from "react";
import ProgressBar from "../ProgressBar/ProgressBar";
import styles from "./Step0.module.css";

export default function Step0({ nextStep }) {
  const handleContinue = () => {
    nextStep(); // Passa direttamente allo step successivo
  };

  return (
    <div className={styles.step}>
      <ProgressBar currentStep={0} totalSteps={9} />
      <h2>Benvenuto nel form di valutazione</h2>
      <p>
        Compilando questo form fornirai tutte le informazioni necessarie per
        stimare il valore dell’affitto del tuo immobile.<br />
        Una volta completata la registrazione, i tuoi dati saranno trattati in
        modo sicuro e,<br /> entro 72 ore, riceverai una mail da un nostro agente
        con un riepilogo e un primo range di prezzo stimato.
      </p>
      <div className={styles.buttons}>
        <button className={styles.btn} onClick={handleContinue}>
          Continua
        </button>
      </div>
    </div>
  );
}
