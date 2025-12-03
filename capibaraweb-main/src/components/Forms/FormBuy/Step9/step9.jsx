import { useState } from "react";
import styles from "./step9.module.css";
import ProgressBar from "../ProgressBar/ProgressBar";
import { valutaImmobileAutomatico } from "../../../../api/valutazioneApi";

export default function Step9({ formData, prevStep, nextStep }) {
  const [loading, setLoading] = useState(false);
  const [result, setResult] = useState(null);
  const [error, setError] = useState("");

  const handleValutazione = async () => {
    setLoading(true);
    setError("");
    setResult(null);

    const payload = {
      provincia: formData.provincia,
      cap: formData.cap,
      indirizzo: formData.indirizzo,
      civico: formData.civico || "",
      metriQuadri: Number(formData.surface),
      tipologia: formData.tipologia,
      statoImmobile: formData.serviceType,
      piano: formData.floor || null,
      locali: Number(formData.rooms),
      bagni: Number(formData.bathrooms),
      annoCost: formData.annoCost || null,
      ascensore: formData.ascensore || false,
      parcheggio: formData.parcheggio || false,
      postiAuto: formData.postiAuto || 0,
      garage: formData.garage || false,
      balconeMq: formData.balconeMq || null,
      terrazzoMq: formData.terrazzoMq || null,
      giardinoMq: formData.giardinoMq || null,
      cantina: formData.externalFeatures?.includes("cellar") || false,
      arredato: formData.furnishing === "ARREDATO",
      ariaCondizionata: formData.externalFeatures?.includes("veranda") || false,
      allarme: false,
      riscaldamento: formData.riscaldamento || "CENTRALIZZATO",
      classeEnergetica: formData.classeEnergetica || "C",
      orientamento: formData.orientamento || "NORD",
    };

    try {
      const response = await valutaImmobileAutomatico(payload);
      setResult(response);
    } catch (err) {
      setError(err.message || "Errore durante la valutazione");
    }

    setLoading(false);
  };

  return (
    <div className={styles.container}>
      <ProgressBar currentStep={9} totalSteps={10} />

      <h2 className={styles.title}>Pronto a ottenere la valutazione?</h2>

      {!result && (
        <>
          {error && <p className={styles.error}>{error}</p>}

          <div className={styles.buttons}>
            <button className={styles.backButton} onClick={prevStep}>
              Indietro
            </button>

            <button
              className={styles.submitButton}
              onClick={handleValutazione}
              disabled={loading}
            >
              {loading ? "Calcolo in corso..." : "Ottieni Valutazione"}
            </button>
          </div>
        </>
      )}

      {result && (
        <div className={styles.resultBox}>
          <h3 className={styles.resultTitle}>Valutazione Stimata</h3>

          <p className={styles.resultValue}>€ {result.valoreStimato}</p>

          <div className={styles.rangeBox}>
            <div className={styles.rangeItem}>
              <span className={styles.rangeLabel}>Min:</span>
              <span className={styles.rangeValue}>€ {result.valoreMin}</span>
            </div>

            <div className={styles.rangeItem}>
              <span className={styles.rangeLabel}>Max:</span>
              <span className={styles.rangeValue}>€ {result.valoreMax}</span>
            </div>
          </div>

          <div className={styles.buttonsResult}>
            <button className={styles.backButton} onClick={prevStep}>
              Indietro
            </button>

            <button className={styles.continueButton} onClick={nextStep}>
              Continua
            </button>
          </div>
        </div>
      )}
    </div>
  );
}
