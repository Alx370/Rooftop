import React from "react";
import ProgressBar from "../ProgressBar/ProgressBar";
import styles from "./step0.module.css";

export default function Step0({ nextStep }) {
  return (
    <div className={styles.container}>
      <ProgressBar currentStep={0} totalSteps={10} />

      <div className={styles.content}>
        <h2 className={styles.title}>Benvenuto nel form di valutazione</h2>

        <p className={styles.description}>
         Compilando questo form fornirai tutte le informazioni necessarie per <br/>
         <b>stimare il valore della tua casa.</b> Una volta completata la registrazione, i <br/>
         tuoi dati saranno trattati in <b>modo sicuro</b> e, entro <b>72 ore</b>, riceverai una mail <br/> 
         da un nostro agente con un <b>riepilogo</b> e un primo range di <b>prezzo stimato.</b> 
        </p>

        <button className={styles.button} onClick={nextStep}>
          Continua
        </button>
      </div>
    </div>
  );
}
