import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import ProgressBar from "../ProgressBar/ProgressBar";
import styles from "./step8.module.css";
import { inviaValutazioneApi } from "@api/formBuyApi.js";

export default function Step8({ formData }) {
  const navigate = useNavigate();
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const sendFinalData = async () => {
      try {
        await inviaValutazioneApi({ ...formData, step: 8 });
        setLoading(false);
      } catch (err) {
        setError("Si è verificato un errore durante l'invio dei dati.");
        setLoading(false);
      }
    };

    sendFinalData();
  }, []);

  return (
    <div className={styles.container}>
      <ProgressBar currentStep={9} totalSteps={9} />

      <div className={styles.content}>
        <div className={styles.iconCircle}>
          <span className={styles.checkmark}>✔</span>
        </div>

        <h2 className={styles.title}>Registrazione completata con successo</h2>

        {loading && <p className={styles.text}>Invio dati in corso...</p>}
        {!loading && !error && (
          <p className={styles.text}>
            Abbiamo ricevuto tutte le informazioni sul tuo immobile. Entro 72 ore un nostro agente ti contatterà con una valutazione preliminare.
          </p>
        )}
        {error && <p className={styles.error}>{error}</p>}

        <button className={styles.homeButton} onClick={() => navigate("/")}>
          Torna alla home
        </button>
      </div>
    </div>
  );
}
