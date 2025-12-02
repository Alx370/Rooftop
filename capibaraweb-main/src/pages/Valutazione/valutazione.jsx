import { Link } from "react-router-dom";
import "../Valutazione/valutazione.css";
import React, { useEffect } from "react";
import { setPageMeta, setStructuredData } from "../../utils/seo";
import Affitto from "../../assets/images/valutazione/affitto.png";
import Vendita from "../../assets/images/valutazione/vendita.png";
import Icon1 from "../../assets/images/valutazione/icon1.png";
import Icon2 from "../../assets/images/valutazione/icon2.png";
import Icon3 from "../../assets/images/valutazione/icon3.png";

const Valutazione = () => {
  useEffect(() => {
    setPageMeta({
      title: 'Valutazione immobiliare',
      description:
        'Valutiamo il tuo immobile in pochi step: servizi per vendita e affitto, analisi professionale e report dettagliato.',
      url: window.location.href,
      image: '/src/assets/images/valutazione/vendita.png',
    });

    setStructuredData({
      "@context": "https://schema.org",
      "@type": "WebPage",
      "name": "Valutazione immobiliare — Capibara Web",
      "description": "Valutiamo il tuo immobile in pochi step: servizi per vendita e affitto, analisi professionale e report dettagliato.",
      "url": window.location.href
    });
  }, []);
  return (
    <main className="valutazione-container">

      {/* --- HERO --- */}
      <section className="hero-section">
        <h1>Valutiamo il tuo<br />immobile in pochi step</h1>
        <p>Dati reali, competenza umana e trasparenza in ogni fase.</p>
      </section>

      {/* --- CARDS --- */}
      <section className="servizi-section">

        {/* CARD VENDITA */}
        <div className="card">
          <div className="card-bg">
            <img src={Vendita} alt="Vendita" />
          </div>
          <div className="card-overlay"></div>

          <div className="card-content">
            <h2>Vendita</h2>
            <p>
              Ti affianchiamo con professionalità e una valutazione precisa dei
              punti di forza della tua casa.
            </p>
            <Link to="/formbuy/step0">
              <button className="btn blu">Valuta ora</button>
            </Link>
            <Link to="/formbuy-manuale/step0" style={{ marginLeft: 12 }}>
              <button className="btn bianco">Valutazione manuale</button>
            </Link>
            <p style={{ marginTop: 8, fontSize: 14 }}>Per la valutazione manuale è necessario effettuare l’accesso.</p>
          </div>
        </div>

        {/* CARD AFFITTO */}
        <div className="card">
          <div className="card-bg">
            <img src={Affitto} alt="Affitto" />
          </div>
          <div className="card-overlay"></div>

          <div className="card-content">
            <h2>Affitto</h2>
            <p>
              Ci occupiamo di tutto per l’affitto della tua casa, dagli
              inquilini alla gestione dei contratti.
            </p>
            <Link to="/formrent/step0">
              <button className="btn arancio">Stima affitto</button>
            </Link>
            <Link to="/formrent-manuale/step0" style={{ marginLeft: 12 }}>
              <button className="btn bianco">Stima manuale</button>
            </Link>
            <p style={{ marginTop: 8, fontSize: 14 }}>Per la stima manuale è necessario effettuare l’accesso.</p>
          </div>
        </div>

      </section>

      {/* --- STEPS --- */}
      <section className="steps-section">
        <div className="step">
          <img src={Icon1} alt="Raccolta dati" />
          <h3>Raccolta dati</h3>
          <p>Inserisci le informazioni principali sull’immobile</p>
        </div>

        <div className="step">
          <img src={Icon2} alt="Analisi" />
          <h3>Analisi degli esperti</h3>
          <p>I nostri consulenti valutano mercato e caratteristiche</p>
        </div>

        <div className="step">
          <img src={Icon3} alt="Report" />
          <h3>Consegna valutazione</h3>
          <p>Ricevi il report completo entro 72 ore</p>
        </div>
      </section>

      {/* --- ACCESSO --- */}
      <section className="access-section">
  <h2>Hai già compilato il nostro form o hai<br />già ricevuto la valutazione?</h2>
  <p>
    Se hai già iniziato il percorso, siamo pronti ad accompagnarti nel passo successivo
    con competenza e trasparenza.
  </p>

  <Link to="/login">
    <button className="btn arancio access-btn">Accedi</button>
  </Link>
</section>
    </main>
  );
};

export default Valutazione;
