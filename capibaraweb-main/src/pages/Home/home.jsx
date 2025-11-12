import React, { useRef } from "react";
import "./Home.css";
import { Link } from "react-router-dom";

const Home = () => {
  // Ref per la griglia dei testimonial
  const testimonialGridRef = useRef(null);

  // Funzione per scroll fluido
  const smoothScroll = (direction) => {
    if (!testimonialGridRef.current) return;

    const grid = testimonialGridRef.current;
    const card = grid.querySelector(".testimonial-card");
    if (!card) return;

    const cardWidth = card.offsetWidth + 32; // 32 = gap tra card in px
    const newScroll = grid.scrollLeft + direction * cardWidth;

    grid.scrollTo({
      left: newScroll,
      behavior: "smooth",
    });
  };


  // Array dinamico dei testimonial
  const testimonials = [
    { text: "Esperienza fantastica, servizio impeccabile e staff super disponibile. Consigliatissimo!", img: "laura.png", name: "Marco Rossi" },
    { text: "Mi sono sentita seguita passo dopo passo. Hanno reso tutto semplice e veloce, anche nei momenti più stressanti.", img: "laura.png", name: "Giulia Verdi" },
    { text: "Servizio eccellente! Il team è stato professionale e attento ai dettagli. Tornerò sicuramente per il prossimo progetto!", img: "laura.png", name: "Luca Bianchi" },
    { text: "Professionalità e cortesia ineguagliabili. Consiglio vivamente a chiunque voglia vendere o affittare.", img: "laura.png", name: "Anna Neri" },
    { text: "Ottima esperienza, tutto semplice e veloce grazie al team. Tornerò sicuramente.", img: "laura.png", name: "Marco L." }
  ];


  return (
    <div className="home">
      {/* ---------------- HERO ---------------- */}
      <section className="hero">
        <div className="hero-bg-logo"></div>
        <div className="hero-bg-palazzo"></div>

        <div className="hero-content">
          <div className="hero-text">
            <h1>Immobiliaris</h1>
            <p>
              Vendere casa è un percorso importante. Noi lo rendiamo più semplice. Affidati ai nostri esperti per una valutazione accurata e un supporto concreto, dal primo passo alla firma.
            </p>
            <div className="hero-buttons">
              <Link className="btn primary" to="/valutazione" >Ottieni valutazione</Link>
            </div>
          </div>
        </div>
      </section>

      {/* ---------------- INTRO ---------------- */}
      <section className="intro">
        <h2>I nostri servizi</h2>
        <p className="subtitle">Ti aiutiamo a valorizzare la tua casa e a venderla al giusto valore.</p>
        <div className="intro-cards">
          <div className="card">
            <img src="src/pages/Home/img/finestra.png" alt="Interno casa" />
            <div className="card-content">
              <h3>Per venderlo</h3>
              <p>Ti guidiamo nella vendita con metodo, trasparenza e attenzione al valore.</p>
              <button className="btn primary">Valuta</button>
            </div>
          </div>
          <div className="card">
            <img src="src/pages/Home/img/letto.png" alt="Interno ufficio" />
            <div className="card-content">
              <h3>Per affittarlo</h3>
              <p>Gestiamo tutto noi: inquilini,
                contratti e sicurezza del
                tuo immobile.</p>
              <button className="btn primary">Valuta</button>
            </div>
          </div>
        </div>
      </section>

      {/* ---------------- USP ---------------- */}
      <section className="usp-section">
        <h2>Perchè scegliere noi</h2>
        <div className="usp-grid">
          <div className="usp-item">
            <div className="usp-text">
              <h3 className="highlight1">Valutazione in 72 ore</h3>
              <p><strong>Rapida, accurata, umana.</strong> Ti offriamo una valutazione <strong>entro 72 ore</strong>, verificata dai nostri esperti e basata su <strong>dati reali</strong>.</p>
            </div>
            <div className="usp-img">
              <img src="src/pages/Home/img/edificio.png" alt="Valutazione in 72 ore" />
            </div>
          </div>

          <div className="usp-item reverse">
            <div className="usp-img">
              <img src="src/pages/Home/img/chiavi.png" alt="Metodo user-centred" />
            </div>
            <div className="usp-text">
              <h3 className="highlight1">Metodo user-centred</h3>
              <p><strong>Sarai al centro di ogni scelta.</strong> Verrai affiancato con competenza per valorizzare al meglio il tuo immobile.</p>
            </div>
          </div>

          <div className="usp-item centered">
            <div className="usp-text">
              <h3 className="highlight">Vendita in esclusiva</h3>
              <p>Un impegno reciproco per risultati concreti. Ci dedichiamo al tuo immobile con <strong>strategie mirate</strong> e <strong>trasparenza</strong>, per vendere al giusto valore.</p>
            </div>
          </div>
        </div>
      </section>

      {/* ---------------- TESTIMONIALS ---------------- */}
      <section className="testimonials">
        <h2>Cosa dicono di noi</h2>
        <p className="subtitle">
          La fiducia dei nostri clienti, raccontata da loro. Le loro parole parlano
          di ascolto, professionalità e risultati concreti.
        </p>

        <div className="testimonial-container">
          <button className="scroll-btn left" onClick={() => smoothScroll(-1)}>←</button>
          <div className="testimonial-grid" ref={testimonialGridRef}>
            {testimonials.map((t, index) => (
              <div key={index} className="testimonial-card">
                <p>{t.text}</p>
                <img src={`src/pages/Home/img/${t.img}`} alt={t.name} />
                <h5>- {t.name}</h5>
              </div>
            ))}
          </div>
          <button className="scroll-btn right" onClick={() => smoothScroll(1)}>→</button>
        </div>
      </section>

      {/* ---------------- NEWSLETTER ---------------- */}
      <section className="newsletter">
        <h2>Iscriviti alla newsletter</h2>
        <p>Resta aggiornato con consigli e approfondimenti dai nostri esperti per </p>
        <p>vendere o affittare in modo consapevole.</p>
        <form className="newsletter-form">
          <input type="email" placeholder="Inserisci la tua email" />
          <button className="btn primary">Iscriviti</button>
        </form>
      </section>
    </div>
  );
};

export default Home;
