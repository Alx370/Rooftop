import React from "react";
import ProgressBar from "../ProgressBar/ProgressBar";
import "./step8.css";

export default function Step8({ formData, setFormData, nextStep, prevStep }) {

  const sellOptions = [
    {
      value: "standard",
      title: "Vendita standard",
      desc: "Percorso tradizionale con analisi accurata e supporto completo dell’agente.",
    },
    {
      value: "veloce",
      title: "Vendita veloce",
      desc: "Procedura accelerata con selezione rapida di acquirenti qualificati.",
    },
  ];

  const selectOption = (value) => {
    setFormData({ ...formData, sellingType: value });
  };

  return (
    <div className="sell-step8-wrapper">

      {/* Progress Bar */}
      <div className="sell-step8-progress">
        <ProgressBar currentStep={8} totalSteps={10} />
      </div>

      {/* Title */}
      <h2 className="sell-step8-title">
        In che modalità preferisci vendere il tuo immobile?
      </h2>

      {/* Cards */}
      <div className="sell-step8-grid">
        {sellOptions.map((opt) => (
          <div
            key={opt.value}
            className={
              "sell-step8-card" +
              (formData.sellingType === opt.value ? " sell-active" : "")
            }
            onClick={() => selectOption(opt.value)}
          >
            <div className="sell-step8-card-header">
              <h3>{opt.title}</h3>
              <span
                className={
                  "sell-step8-radio" +
                  (formData.sellingType === opt.value ? " sell-radio-on" : "")
                }
              ></span>
            </div>
            <p>{opt.desc}</p>
          </div>
        ))}
      </div>

      {/* Buttons */}
      <div className="sell-step8-buttons">
        <button className="sell-btn sell-btn-secondary" onClick={prevStep}>
          Indietro
        </button>

        <button
          className="sell-btn sell-btn-primary"
          onClick={nextStep}
          disabled={!formData.sellingType}
        >
          Continua
        </button>
      </div>
    </div>
  );
}
