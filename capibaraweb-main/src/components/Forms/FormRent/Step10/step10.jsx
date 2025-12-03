import React, { useEffect, useState, useRef } from "react";
import { useNavigate } from "react-router-dom";
import ProgressBar from "../ProgressBar/ProgressBar";
import styles from "./step10.module.css";
import { inviaValutazioneManuale } from "../../../../api/valutazioneApi.js";
import { registraCliente } from "../../../../api/clientiApi.js";

export default function Step10({ formData, manual = false }) {
  const navigate = useNavigate();
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(true);
  const [result, setResult] = useState(null);

  const hasSent = useRef(false);
  useEffect(() => {
    if (hasSent.current) return;
    hasSent.current = true;
    const sendFinalData = async () => {
      try {
        const isManual = manual || formData.evaluationType === "manuale";
        if (isManual) {
          await inviaValutazioneManuale({ ...formData, step: 10, tipo: "AFFITTO" });
        } else {
          await registraCliente({ ...formData, step: 10 });
        }
        setLoading(false);
      } catch (err) {
        console.error("Errore invio dati finali:", err);
        setError("Si è verificato un errore durante l'invio dei dati. Riprova.");
        setLoading(false);
      }
    };
    sendFinalData();
  }, [formData, manual]);

  return (
    <div className={styles.container}>
      <ProgressBar currentStep={10} totalSteps={10} />

      <div className={styles.content}>
        <div className={styles.iconCircle}>
          <span className={styles.checkmark}>✔</span>
        </div>

        <h2 className={styles.title}>Registrazione completata con successo</h2>

        {/* MESSAGGI */}
        {loading && <p className={styles.text}>Invio dei dati in corso...</p>}

        {!loading && !error && (
          <p className={styles.text}>
            {manual || formData.evaluationType === "manuale"
              ? "Abbiamo inviato i tuoi dati a un agente. Riceverai una risposta entro tre giorni."
              : "Abbiamo ricevuto tutte le informazioni relative al tuo immobile. Entro 72 ore un nostro agente ti contatterà via email con un riepilogo completo."}
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
