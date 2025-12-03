import React, { useState } from "react";
import ProgressBar from "../ProgressBar/ProgressBar";
import "./step8.css";

export default function Step8({ formData, setFormData, nextStep, prevStep }) {
  const [error, setError] = useState("");

  const contractOptions = [
    {
      value: "4+4",
      label: "Contratto 4+4",
      desc: "Durata standard, rinnovabile, è pensato per affitti a lungo termine.",
    },
    {
      value: "transitorio",
      label: "Contratto transitorio",
      desc: "Valido da 1 a 18 mesi, per esigenze temporanee (lavoro, trasferte).",
    },
    {
      value: "studenti",
      label: "Contratto per studenti",
      desc: "Durata da 6 a 36 mesi, agevolazioni fiscali, vincolato alla presenza di iscrizione universitaria.",
    },
    {
      value: "breve",
      label: "Contratto breve",
      desc: "Affitti inferiori a 30 giorni o stagionali, spesso gestiti come locazioni turistiche.",
    },
  ];

  const selectContract = (val) => {
    setError("");
    setFormData({ ...formData, contractType: val });
  };

  const handleNext = () => {
    if (!formData.contractType) {
      setError("Seleziona una tipologia di contratto per continuare.");
      return;
    }
    nextStep();
  };

  return (
    <div className="rent-step8-wrapper">
      <div className="rent-step8-progress">
        <ProgressBar currentStep={8} totalSteps={10} />
      </div>

      <h2 className="rent-step8-title">Che tipologia di contratto preferisce?</h2>

      <div className="rent-step8-grid">
        {contractOptions.map((opt) => (
          <div
            key={opt.value}
            className={
              "rent-step8-card" +
              (formData.contractType === opt.value ? " rent-active" : "")
            }
            onClick={() => selectContract(opt.value)}
          >
            <div className="rent-step8-card-header">
              <h3>{opt.label}</h3>
              <span
                className={
                  "rent-step8-radio" +
                  (formData.contractType === opt.value ? " rent-radio-on" : "")
                }
              ></span>
            </div>
            <p>{opt.desc}</p>
          </div>
        ))}
      </div>

      {error && <p className="rent-error">{error}</p>}

      <div className="rent-step8-buttons">
        <button className="rent-btn rent-btn-secondary" onClick={prevStep}>
          Indietro
        </button>

        <button className="rent-btn rent-btn-primary" onClick={handleNext}>
          Continua
        </button>
      </div>
    </div>
  );
}
