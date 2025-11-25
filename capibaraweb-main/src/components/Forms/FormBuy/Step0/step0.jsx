import React from "react";
import ProgressBar from "../ProgressBar/ProgressBar";
import styles from "./step0.module.css";

export default function Step0({ nextStep }) {
  return (
    <div className={styles.container}>
      <ProgressBar currentStep={0} totalSteps={8} />

      <div className={styles.content}>
        <h2 className={styles.title}>Benvenuto nel form di valutazione</h2>

        <p className={styles.description}>
          Compilando questo form fornirai tutte le informazioni necessarie per
          stimare il valore dell’affitto del tuo immobile. <br />
          Una volta completata la registrazione, i tuoi dati saranno trattati in
          modo sicuro e, entro 72 ore, riceverai una mail da un nostro agente con
          un riepilogo e un primo range di prezzo stimato.
        </p>

        <button className={styles.button} onClick={nextStep}>
          Continua
        </button>
      </div>
    </div>
  );
}
