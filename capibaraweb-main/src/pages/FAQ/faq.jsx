import React from "react";
import "./Faq.css";

// immagini
import CasaFaq from "../../assets/images/casafaq.png";
import GardenFaq from "../../assets/images/gardenfaq.png";

const Faq = () => {
  return (
    <div className="faq-page">

      {/* --- FAQ INTRO --- */}
      <section className="faq-section">
        <div className="faq-text">
          <h2>Qui per rispondere a tutte<br />le tue domande</h2>
          <p>
            Non hai trovato la risposta che cercavi? Nessun problema.
            Ogni situazione è unica, e siamo qui proprio per ascoltarti.
          </p>
        </div>

        {/* FAQ ITEMS */}
        <div className="faq-boxes">

          <details className="faq-item">
            <summary><span>Come selezionate gli inquilini?</span></summary>
            <p>
              La nostra agenzia immobiliare seleziona gli inquilini verificando <b>documenti, reddito</b> e <b>referenze</b>, così da garantire che ogni immobile sia affidato a persone affidabili.
            </p>
          </details>

          <details className="faq-item">
            <summary><span>Quali documenti servono per la valutazione?</span></summary>
            <p>
              Per la valutazione sono necessari: <b>titolo di proprietà</b>, <b>visura catastale</b>, <b>planimetria</b> e <b>APE</b>. Altri documenti possono rendere la stima più precisa.
            </p>
          </details>

          <details className="faq-item">
            <summary><span>Offrite supporto anche dopo la firma?</span></summary>
            <p>
              Sì, offriamo un <b>supporto continuativo</b> per documenti, spese, adempimenti e assistenza diretta dei nostri consulenti.
            </p>
          </details>

          <details className="faq-item">
            <summary><span>Quanto dura in media una trattativa?</span></summary>
            <p>
              Una trattativa può durare da poche settimane a diversi mesi per le vendite, mentre per gli affitti i tempi variano da <b>15 a 30 giorni</b>.
            </p>
          </details>

          <details className="faq-item">
            <summary><span>Posso affidare la vendita a distanza?</span></summary>
            <p>
              Certo, grazie a strumenti digitali, documenti online e firma elettronica puoi gestire tutto anche senza essere presente.
            </p>
          </details>

        </div>
      </section>

      {/* --- FORM + IMMAGINE --- */}
      <section className="faq-contact">
        <div className="contact-column">
          <h2>Non trovi la risposta alla tua domanda?</h2>
          <p>
            Scrivici e un nostro consulente ti risponderà <b>personalmente</b> con la soluzione più adatta.
          </p>
        </div>

        <div className="contact-row">
          <form className="faq-form">
            <div className="input-wrap">
              <input type="text" placeholder="Nome Cognome" />
            </div>
            <div className="input-wrap">
              <input type="email" placeholder="Mail" />
            </div>
            <div className="input-wrap">
              <textarea placeholder="Messaggio..." />
            </div>

            <button type="submit" className="btn-primary">Invia</button>
          </form>

          <div className="faq-contact-image">
            <img src={CasaFaq} alt="Casa FAQ" className="faq-image-main" />
            <img src={GardenFaq} alt="Garden FAQ" className="faq-image-bg" />
          </div>
        </div>
      </section>

    </div>
  );
};

export default Faq;
