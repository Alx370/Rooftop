import { Link } from "react-router-dom";
import "../Valutazione/valutazione.css";

const Valutazione = () => {
  return (
    <main className="valutazione-container">

      {/* HERO SECTION */}
      <section className="hero-section">
        <h1>Valutiamo il tuo immobile in pochi step</h1>
        <p>Dati reali, competenza umana e trasparenza in ogni fase.</p>
      </section>

      {/* SCELTA SERVIZIO */}
      <section className="servizi-section">
        <div className="card">
          <img src="./" alt="Vendita" />
          <div className="card-content">
            <h2>Vendita</h2>
            <p>
              Ti affianchiamo con professionalità e una valutazione precisa dei
              punti di forza della tua casa.
            </p>
            <button className="btn blu">Valuta ora</button>
          </div>
        </div>

        <div className="card">
          <img src="/images/affitto.jpg" alt="Affitto" />
          <div className="card-content">
            <h2>Affitto</h2>
            <p>
              Ci occupiamo di tutto per l’affitto della tua casa, dagli inquilini
              alla gestione dei contratti.
            </p>
            <button className="btn arancio">Stima affitto</button>
          </div>
        </div>
      </section>

      {/* INFO STEPS */}
      <section className="steps-section">
        <div className="step">
          <img src="/icons/raccolta.svg" alt="Raccolta dati" />
          <h3>Raccolta dati</h3>
          <p>
            Inserisci le <strong>informazioni principali</strong> sull’immobile
          </p>
        </div>
        <div className="step">
          <img src="/icons/analisi.svg" alt="Analisi degli esperti" />
          <h3>Analisi degli esperti</h3>
          <p>
            I nostri consulenti valutano <strong>mercato</strong> e{" "}
            <strong>caratteristiche</strong>
          </p>
        </div>
        <div className="step">
          <img src="/icons/consegna.svg" alt="Consegna valutazione" />
          <h3>Consegna valutazione</h3>
          <p>
            Ricevi il report completo entro <strong>72 ore</strong>
          </p>
        </div>
      </section>

      {/* ACCESSO */}
      <section className="access-section">
        <h2>
          Hai già compilato il nostro form o hai già ricevuto la valutazione?
        </h2>
        <p>
          Se hai già iniziato il percorso, siamo pronti ad accompagnarti nel
          passo successivo con competenza e trasparenza.
        </p>
        <button className="btn arancio">Accedi</button>
      </section>
    </main>
  );
};

export default Valutazione;
