import { useState } from "react";
import { Routes, Route, useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";
import "../Valutazione/valutazione.css";
import Affitto from "../../assets/images/valutazione/affitto.png";
import Vendita from "../../assets/images/valutazione/vendita.png";
import Icon1 from "../../assets/images/valutazione/icon1.png";
import Icon2 from "../../assets/images/valutazione/icon2.png";
import Icon3 from "../../assets/images/valutazione/icon3.png";  

const Valutazione = () => {
  return (
    <main className="valutazione-container">

      <section className="hero-section">
        <h1>Valutiamo il tuo immobile in pochi step</h1>
        <p>Dati reali, competenza umana e trasparenza in ogni fase.</p>
      </section>

      <section className="servizi-section">
        <div className="card">
          <img src={Vendita} alt="Immagine di background di un appartamento" />
          <div className="card-content">
            <h2>Vendita</h2>
            <p className="testo-card">
              Ti affianchiamo con professionalità e una valutazione precisa dei
              punti di forza della tua casa.
            </p>

            <Link to="/formbuy/step0">
              <button className="btn blu">Valuta ora</button>
            </Link>
          </div>
        </div>

        <div className="card">
          <img src={Affitto} alt="Immagine di background di un appartamento" />
          <div className="card-content">
            <h2>Affitto</h2>
            <p className="testo-card">
              Ci occupiamo di tutto per l’affitto della tua casa.
            </p>
            <button className="btn arancio">Stima affitto</button>
          </div>
        </div>
      </section>

      <section className="steps-section">
        <div className="step">
          <img src={Icon1} alt="" />
          <h3>Raccolta dati</h3>
          <p>Inserisci le <strong>informazioni principali</strong> sull’immobile</p>
        </div>
        <div className="step">
          <img src={Icon2} alt="" />
          <h3>Analisi degli esperti</h3>
          <p>I nostri consulenti valutano <strong>mercato</strong> e caratteristiche</p>
        </div>
        <div className="step">
          <img src={Icon3} alt="" />
          <h3>Consegna valutazione</h3>
          <p>Ricevi il report entro <strong>72 ore</strong></p>
        </div>
      </section>

    </main>
  );
};

export default Valutazione;
