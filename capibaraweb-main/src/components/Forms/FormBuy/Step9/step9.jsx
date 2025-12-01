import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import ProgressBar from "../ProgressBar/ProgressBar";
import styles from "./step9.module.css";
import { createUser } from "../../../../services/api";

export default function Step9({ formData }) {
  const navigate = useNavigate();
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const sendFinalData = async () => {
      try {
        await createUser({ ...formData, step: 8 });
        setLoading(false);
      } catch (err) {
        console.error("Errore invio dati finali:", err);
        setError("Si è verificato un errore durante l'invio dei dati. Riprova.");
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

        {/* MESSAGGI */}
        {loading && <p className={styles.text}>Invio dei dati in corso...</p>}

        {!loading && !error && (
          <p className={styles.text}>
            Abbiamo ricevuto tutte le informazioni relative al tuo immobile.
            Entro 72 ore un nostro agente ti contatterà via email con un
            riepilogo completo e una prima stima del valore di affitto.
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
