import React from "react";
import ProgressBar from "../ProgressBar/ProgressBar";
import "./step9.css";

export default function Step9({ formData, setFormData, nextStep, prevStep }) {

  const options = [
    {
      value: "automatica",
      title: "Valutazione automatica",
      desc: "Ricevi una valutazione immediata basata sui dati del mercato e su algoritmi aggiornati.",
    },
    {
      value: "manuale",
      title: "Valutazione con esperto",
      desc: "Un consulente analizzerà il tuo immobile manualmente e ti invierà una valutazione approfondita.",
    },
  ];

  const selectOption = (value) => {
    setFormData({ ...formData, evaluationType: value });
  };

  return (
    <div className="rent-step9-wrapper">

      {/* PROGRESS BAR */}
      <div className="rent-step9-progress">
        <ProgressBar currentStep={9} totalSteps={10} />
      </div>

      {/* TITRE */}
      <h2 className="rent-step9-title">
        Come preferisci ricevere la valutazione?
      </h2>

      {/* GRID OPTIONS */}
      <div className="rent-step9-grid">
        {options.map((opt) => (
          <div
            key={opt.value}
            className={
              "rent-step9-card" +
              (formData.evaluationType === opt.value ? " rent-active" : "")
            }
            onClick={() => selectOption(opt.value)}
          >
            <div className="rent-step9-card-header">
              <h3>{opt.title}</h3>
              <span
                className={
                  "rent-step9-radio" +
                  (formData.evaluationType === opt.value ? " rent-radio-on" : "")
                }
              ></span>
            </div>

            <p>{opt.desc}</p>
          </div>
        ))}
      </div>

      {/* BUTTONS */}
      <div className="rent-step9-buttons">
        <button className="rent-btn rent-btn-secondary" onClick={prevStep}>
          Indietro
        </button>

        <button
          className="rent-btn rent-btn-primary"
          disabled={!formData.evaluationType}
          onClick={nextStep}
        >
          Continua
        </button>
      </div>
    </div>
  );
}
