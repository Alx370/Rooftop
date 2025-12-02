import React, { useEffect, useState, useRef } from "react";
import { useNavigate } from "react-router-dom";
import ProgressBar from "../ProgressBar/ProgressBar";
import styles from "./step9.module.css";
import { inviaValutazioneManuale, valutaAutomatica } from "../../../../api/valutazioneApi.js";

export default function Step9({ formData, manual = false }) {
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
        const isManual = manual || formData?.evaluationType === "manuale";
        if (isManual) {
          await inviaValutazioneManuale({ ...formData, step: 9, tipo: "VENDITA" });
        } else {
          const provinciaMap = { torino: "TO", cuneo: "CN", asti: "AT", alessandria: "AL" };
          const provinciaKey = (formData.provincia || "").trim().toLowerCase();
          const provincia = provinciaMap[provinciaKey] || provinciaKey.slice(0,2).toUpperCase();
          const tipologiaMap = { villa: "VILLA", appartamento: "APPARTAMENTO" };
          const statoMap = { new: "NUOVO", good: "BUONO", renovated: "OTTIMO", renovate: "DA_RISTRUTTURARE" };

          const payload = {
            provincia,
            cap: formData.cap,
            indirizzo: formData.indirizzo,
            metriQuadri: formData.surface ? Number(formData.surface) : null,
            tipologia: tipologiaMap[formData.tipologia] || "APPARTAMENTO",
            statoImmobile: statoMap[formData.serviceType] || null,
            piano: formData.floor != null ? String(formData.floor) : null,
            locali: formData.rooms ? Number(formData.rooms) : null,
            bagni: formData.bathrooms ? Number(formData.bathrooms) : null,
            ascensore: null,
            parcheggio: Array.isArray(formData.externalFeatures) ? formData.externalFeatures.includes("parking") : null,
            garage: Array.isArray(formData.externalFeatures) ? formData.externalFeatures.includes("garage") : null,
            cantina: Array.isArray(formData.externalFeatures) ? formData.externalFeatures.includes("cellar") : null,
            balconeMq: null,
            terrazzoMq: null,
            giardinoMq: null,
          };

          const resp = await valutaAutomatica(payload);
          setResult(resp);
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
      <ProgressBar currentStep={9} totalSteps={9} />

      <div className={styles.content}>
        <div className={styles.iconCircle}>
          <span className={styles.checkmark}>✔</span>
        </div>

        <h2 className={styles.title}>Registrazione completata con successo</h2>

        {/* MESSAGGI */}
        {loading && <p className={styles.text}>Invio dei dati in corso...</p>}

        {!loading && !error && (
          result ? (
            <div className={styles.text}>
              <div>Valore stimato: <strong>{result.valoreStimato} €</strong></div>
              <div>Range: <strong>{result.valoreMin} €</strong> — <strong>{result.valoreMax} €</strong></div>
              {result.prezzoMqZona && <div>Prezzo mq zona: <strong>{result.prezzoMqZona} €</strong></div>}
              {result.zonaOmi && <div>Zona OMI: <strong>{result.zonaOmi}</strong></div>}
            </div>
          ) : (
            <p className={styles.text}>
              {(manual || formData?.evaluationType === "manuale")
                ? "Abbiamo inviato i tuoi dati a un agente. Riceverai una risposta entro tre giorni."
                : "Stima completata. Consulta il valore stimato qui sopra."}
            </p>
          )
        )}

        {error && <p className={styles.error}>{error}</p>}

        <button className={styles.homeButton} onClick={() => navigate("/")}>
          Torna alla home
        </button>
      </div>
    </div>
  );
}
