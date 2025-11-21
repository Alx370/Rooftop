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

      {/* HERO SECTION */}
      <section className="hero-section">
        <h1>Valutiamo il tuo immobile in pochi step</h1>
        <p>Dati reali, competenza umana e trasparenza in ogni fase.</p>
      </section>

      {/* SCELTA SERVIZIO */}
      <section className="servizi-section">
        <div className="card">
          <img src={Vendita} alt="Immagine di background di un appartamento" />
          <div className="card-content">
            <h2>Vendita</h2>
            <p className="testo-card">
              Ti affianchiamo con professionalità e una valutazione precisa dei
              punti di forza della tua casa.
            </p>
            <button className="btn blu">Valuta ora</button>
          </div>
        </div>

        <div className="card">
          <img src={Affitto} alt="Immagine di background di un appartamento" />
          <div className="card-content">
            <h2>Affitto</h2>
            <p className="testo-card">
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
          <img src={Icon1} alt="Immagine di un icona" />
          <h3>Raccolta dati</h3>
          <p>
            Inserisci le <strong>informazioni principali</strong> sull’immobile
          </p>
        </div>
        <div className="step">
          <img src={Icon2} alt="Immagine di un icona" />
          <h3>Analisi degli esperti</h3>
          <p>
            I nostri consulenti valutano <strong>mercato</strong> e{" "}
            <strong>caratteristiche</strong>
          </p>
        </div>
        <div className="step">
          <img src={Icon3} alt="Immagine di un icona" />
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
