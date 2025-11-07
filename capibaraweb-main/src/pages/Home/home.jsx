import React from "react";
import "./Home.css";

const Home = () => {
  return (
    <div className="home">
      {/* ---------------- HERO ---------------- */}
      <section className="hero">
        <div className="hero-content">
          <div className="hero-text">
            <h1>Immobiliaris</h1>
            <p>Vendere casa è un percorso importante. Noi lo rendiamo più semplice. Affidati ai nostri esperti per una valutazione accurata e un supporto concreto, dal primo passo alla firma.</p>
            <div className="hero-buttons">
              <button className="btn primary"> Ottieni valutazione</button>
            </div>
          </div>
          <div className="hero-image">
            <img src="src\pages\Home\img\PalazzoModerno.png" alt="Palazzo moderno" />
          </div>
          
        </div>
      </section>

      {/* ---------------- INTRO ---------------- */}
  <section className="intro">
  <h2>Titolo</h2>
  <p className="subtitle">
    Borem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vulputate
  </p>

  <div className="intro-cards">
    <div className="card">
      <img src="src\pages\Home\img\finestra.png" alt="Interno casa" />
      <div className="card-content">
        <h3>Per venderlo</h3>
        <p>
          Lorem ipsum dolor sit amet consectetur. Eu ac enim nibh interdum orci
          ultricies egestas.
        </p>
        <button className="btn primary">Valuta</button>
      </div>
    </div>

    <div className="card">
      <img src="src\pages\Home\img\letto.png" alt="Interno ufficio" />
      <div className="card-content">
        <h3>Per affittarlo</h3>
        <p>
          Lorem ipsum dolor sit amet consectetur. Eu ac enim nibh interdum orci
          ultricies egestas.
        </p>
        <button className="btn primary">Valuta</button>
      </div>
    </div>
  </div>
</section>


      {/* ---------------- USP ---------------- */}
   <section className="usp">
  <h2>Perché scegliere noi</h2>
  <div className="usp-grid">
    {/* Testo in alto a sinistra */}
    <div className="usp-item text-item">
      <div>
        <h3>USP del brand</h3>
        <p> Borem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vulputate</p>
      </div>
    </div>
    {/* Immagine in alto a destra */}
    <div className="usp-item image-item">
      <img src="src/pages/Home/img/letto.png" alt="Esperienza" />
    </div>
    {/* Immagine in basso a sinistra */}
    <div className="usp-item image-item">
      <img src="src/pages/Home/img/letto.png" alt="Trasparenza" />
    </div>
    {/* Testo in basso a destra */}
    <div className="usp-item text-item">
      <div>
        <h3>USP del brand</h3>
        <p> Borem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vulputate</p>
      </div>
    </div>
    {/* Testo centrale */}
    <div className="usp-item centered">
      <div>
        <h3>USP del brand</h3>
        <p> Borem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vulputate</p>
      </div>
    </div>
  </div>
</section>


      {/* ---------------- TESTIMONIALS ---------------- */}
<section className="testimonials">
  <h2>Cosa dicono di noi</h2>
  <p className="subtitle">
    Lorem ipsum dolor sit amet consectetur. Eu ac enim nibh interdum orci
    ultricies egestas.
  </p>

  <div className="testimonial-grid">
    <div className="testimonial-card">
      <p>
        "Esperienza fantastica, servizio impeccabile e staff super disponibile.
        Consigliatissimo!"
      </p>
      <img src="src/pages/Home/img/laura.png" alt="Marco Rossi" />
       <h5>- Marco Rossi</h5>
    </div>

    <div className="testimonial-card">
      <p>
        "Mi sono sentita seguita passo dopo passo. Hanno reso tutto semplice e
        veloce, anche nei momenti più stressanti."
      </p>
      <img src="src/pages/Home/img/laura.png" alt="Giulia Verdi" />
        <h5>- Giulia Verdi</h5>
    </div>

    <div className="testimonial-card">
      <p>
        "Servizio eccellente! Il team è stato professionale e attento ai
        dettagli. Tornerò sicuramente per il prossimo progetto!"
      </p>
      <img src="src/pages/Home/img/laura.png" alt="Luca Bianchi" />
      <h5>- Luca Bianchi</h5>
    </div>
  </div>
</section>

      {/* ---------------- NEWSLETTER ---------------- */}
      <section className="newsletter">
        <h2>Iscriviti alla newsletter</h2>
        <p>Lorem ipsum dolor sit amet consectetur. Condimentum eget vitae ligula sed urna sit sagittis.</p>
        <form className="newsletter-form">
          <input type="email" placeholder="Inserisci la tua email" />
          <button className="btn primary">Iscriviti</button>
        </form>
      </section>
    </div>
  );
};

export default Home;
