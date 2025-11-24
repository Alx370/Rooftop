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
        await createUser({ ...formData, step: 9 }); // invio dati finali
        setLoading(false);
      } catch (err) {
        console.error("Errore invio dati finali:", err);
        setError("Si è verificato un errore durante l'invio dei dati. Riprova.");
        setLoading(false);
      }
    };

    sendFinalData();
  }, []); // <-- esegue solo al montaggio

  const handleGoHome = () => {
    navigate("/"); // torna alla home
  };

  return (
    <div className={styles.container}>
      <div className={styles.progressWrapper}>
        <ProgressBar currentStep={9} totalSteps={9} />
      </div>

      <div className={styles.content}>
        <div className={styles.iconCircle}>
          <span className={styles.checkmark}>✔</span>
        </div>

        <h2 className={styles.title}>Registrazione completata con successo</h2>

        {loading && <p className={styles.text}>Invio dati in corso...</p>}

        {!loading && !error && (
          <p className={styles.text}>
            Abbiamo ricevuto tutte le informazioni sul tuo immobile. Entro 72 ore un nostro agente ti invierà una mail di riepilogo con il range di prezzo stimato e ti accompagnerà nel percorso di valutazione dettagliata e acquisizione con Immobiliaris.
          </p>
        )}

        {error && <p className={styles.error}>{error}</p>}

        <button className={styles.homeButton} onClick={handleGoHome}>
          Torna alla home
        </button>
      </div>
    </div>
  );
}
