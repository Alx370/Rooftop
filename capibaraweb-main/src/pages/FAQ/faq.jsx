import React from "react";
import "./Faq.css";
import Postino from "../../assets/images/Postino.png";
import CasaHero from "../../assets/images/CasaHero.png";

const Faq = () => {
  return (
    <div className="faq-page">
      <section className="faq-hero">
        <div className="container hero-container">
          <div className="hero-text">
            <h1>Domande più frequenti</h1>
            <p className="hero-sub">Lorem ipsum dolor sit amet consectetur</p>
          </div>

          <div className="hero-image">
            <img src={CasaHero} alt="Casa Hero" />
          </div>
        </div>
      </section>

      <section className="faq-section">
        <div className="faq-text">
          <h2>Qui per rispondere a tutte le tue domande</h2>
          <p>
            Lorem ipsum dolor sit amet consectetur. Sit senectus amet nunc
            viverra. Est diam nulla risus nam vitae quis.
          </p>
        </div>

        <div className="faq-boxes">
          <details className="faq-item">
            <summary><span>Lorem ipsum dolor sit amet consectetur</span></summary>
            <p>
              Lorem ipsum dolor sit amet consectetur. Pulvinar arcu mattis in
              at sodales condimentum. Gravida arcu aliquet rutrum erat varius.
            </p>
          </details>

          <details className="faq-item">
            <summary><span>Lorem ipsum dolor sit amet consectetur</span></summary>
            <p>
              Lorem ipsum dolor sit amet consectetur adipiscing elit sed do
              eiusmod tempor incididunt.
            </p>
          </details>

          <details className="faq-item">
            <summary><span>Lorem ipsum dolor sit amet consectetur</span></summary>
            <p>
              Lorem ipsum dolor sit amet consectetur adipiscing elit sed do
              eiusmod tempor incididunt.
            </p>
          </details>
        </div>
      </section>

      {/* ---- FORM CON IMMAGINE ---- */}
      <section className="faq-contact">
        <div className="contact-column">
          <h2>Non trovi la risposta alla tua domanda?</h2>
          <p>
            Lorem ipsum dolor sit amet consectetur. Sit senectus amet nunc
            viverra. Est diam nulla risus nam vitae quis.
          </p>
        </div>

        <div className="contact-row">
          <div className="faq-contact-text">
            <form className="faq-form">
              <div className="input-wrap">
                <input type="text" placeholder="Nome e cognome" />
              </div>
              <div className="input-wrap">
                <input type="email" placeholder="Mail" />
              </div>
              <div className="input-wrap">
                <textarea placeholder="Messaggio..." />
              </div>
              <button type="submit" className="btn-primary">Invia</button>
            </form>
          </div>
          <div className="faq-contact-image">
            <img src={Postino} alt="Postino" />
          </div>
        </div>
      </section>

      <section className="faq-reviews">
        <div className="container">
          <h2 className="reviews-title">Cosa dicono i nostri clienti</h2>
          <div className="reviews-grid">
            <div className="review-card">
              <p className="review-text">
                “Servizio impeccabile! Gli agenti sono stati sempre disponibili e hanno trovato la casa perfetta per noi.”
              </p>
              <p className="review-author">— Martina B.</p>
            </div>

            <div className="review-card">
              <p className="review-text">
                “Esperienza molto positiva. Piattaforma chiara e contatti rapidi con i consulenti immobiliari.”
              </p>
              <p className="review-author">— Luca F.</p>
            </div>

            <div className="review-card">
              <p className="review-text">
                “Ho venduto il mio appartamento in due settimane. Consigliatissimo per la professionalità.”
              </p>
              <p className="review-author">— Giulia M.</p>
            </div>
          </div>
        </div>
      </section>

    </div>
  );
};

export default Faq;
